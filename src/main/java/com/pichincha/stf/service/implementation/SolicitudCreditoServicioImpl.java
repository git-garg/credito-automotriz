/**
 * Clase: SolicitudCreditoServicioImpl.java
 * Fecha: 29 jun. 2022
 */
package com.pichincha.stf.service.implementation;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pichincha.stf.entity.Cliente;
import com.pichincha.stf.entity.Ejecutivo;
import com.pichincha.stf.entity.Patio;
import com.pichincha.stf.entity.SolicitudCredito;
import com.pichincha.stf.entity.Vehiculo;
import com.pichincha.stf.entity.enumeration.EstadoSolicitudEnum;
import com.pichincha.stf.entity.enumeration.EstadoVehiculoEnum;
import com.pichincha.stf.entity.to.SolicitudCreditoTo;
import com.pichincha.stf.respository.SolicitudCreditoRepository;
import com.pichincha.stf.service.ClienteServicio;
import com.pichincha.stf.service.EjecutivoServicio;
import com.pichincha.stf.service.PatioServicio;
import com.pichincha.stf.service.SolicitudCreditoServicio;
import com.pichincha.stf.service.VehiculoServicio;
import com.pichincha.stf.service.exception.CreditoAutomotrizException;

/**
 *
 */

@Service
public class SolicitudCreditoServicioImpl implements SolicitudCreditoServicio {

	@Autowired
	private SolicitudCreditoRepository solicitudCreditoRepository;

	@Autowired
	private ClienteServicio clienteServicio;

	@Autowired
	private EjecutivoServicio ejecutivoServicio;

	@Autowired
	private PatioServicio patioServicio;

	@Autowired
	private VehiculoServicio vehiculoServicio;

	private String mensajeError = "";

	@Override
	public SolicitudCredito guardar(SolicitudCreditoTo solicitudCreditoTo) throws CreditoAutomotrizException {

		SolicitudCredito solicitudCredito = obtenerSolicitudCredito(solicitudCreditoTo);
		solicitudCredito.setEstadoSolicitud(EstadoSolicitudEnum.REGISTRADA);
		solicitudCredito.setFechaElaboracion(LocalDate.now());

		SolicitudCredito solicitudCreditoAlmacenada = solicitudCreditoRepository.save(solicitudCredito);
		if (null != solicitudCreditoAlmacenada.getCodigoSolicitudCredito()) {
			Vehiculo vehiculo = solicitudCreditoAlmacenada.getVehiculo();
			vehiculo.setEstadoVehiculo(EstadoVehiculoEnum.COMPROMETIDO);
			vehiculoServicio.actualizarVehiculo(vehiculo);
		}
		return solicitudCreditoAlmacenada;
	}

	private SolicitudCredito obtenerSolicitudCredito(SolicitudCreditoTo solicitudCreditoTo)
			throws CreditoAutomotrizException {

		SolicitudCredito solicitudCredito = solicitudCreditoTo.getSolicitudCredito();
		solicitudCredito.setCliente(obtenerCliente(solicitudCreditoTo.getIdentificacionCliente()));
		solicitudCredito.setPatio(obtenerPunto(solicitudCreditoTo.getNumeroPuntoVenta()));
		solicitudCredito.setVehiculo(obtenerVehiculo(solicitudCreditoTo.getPlaca(), EstadoVehiculoEnum.DISPONIBLE));
		solicitudCredito.setEjecutivo(obtenerEjecutivo(solicitudCreditoTo.getIdentificacionEjecutivo()));

		return solicitudCredito;

	}

	private Ejecutivo obtenerEjecutivo(String identificacionEjecutivo) throws CreditoAutomotrizException {
		Ejecutivo ejecutivo = ejecutivoServicio.obtenerPorIdentificacion(identificacionEjecutivo);
		this.mensajeError = "No existe ejecutivo para la identificacion: ";
		verificarRegistro(identificacionEjecutivo, ejecutivo, mensajeError);
		return ejecutivo;
	}

	private Vehiculo obtenerVehiculo(String placa, EstadoVehiculoEnum estadoVehiculo)
			throws CreditoAutomotrizException {
		Vehiculo vehiculo = vehiculoServicio.obtenerPorPlacaEstado(placa, estadoVehiculo);
		this.mensajeError = "No existe vehículo o esta comprometido. Placa: ";
		verificarRegistro(placa, vehiculo, mensajeError);
		return vehiculo;
	}

	private Patio obtenerPunto(String numeroPuntoVenta) throws CreditoAutomotrizException {
		Patio patio = patioServicio.obtenerPorNumeroPuntoVenta(numeroPuntoVenta);
		this.mensajeError = "No existe punto de venta para: ";
		verificarRegistro(numeroPuntoVenta, patio, mensajeError);
		return patio;
	}

	private Cliente obtenerCliente(String identificacionCliente) throws CreditoAutomotrizException {
		Cliente cliente = clienteServicio.obtenerPorIdentificacion(identificacionCliente);
		this.mensajeError = "No existe ejecutivo para la identificacion: ";
		verificarRegistro(identificacionCliente, cliente, mensajeError);
		return cliente;
	}

	private void verificarRegistro(String parametro, Object entidad, String mensaje) throws CreditoAutomotrizException {
		if (null == entidad) {
			throw new CreditoAutomotrizException(mensaje.concat(parametro));
		}
	}

}
