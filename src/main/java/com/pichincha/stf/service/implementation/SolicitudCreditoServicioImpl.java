/**
 * Clase: SolicitudCreditoServicioImpl.java
 * Fecha: 29 jun. 2022
 */
package com.pichincha.stf.service.implementation;

import java.time.LocalDate;
import java.util.List;

import javax.validation.ConstraintViolationException;

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
import com.pichincha.stf.service.PatioClienteServicio;
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

	@Autowired
	private PatioClienteServicio patioClienteServicio;

	private String mensajeError = "";

	@Override
	public SolicitudCredito guardar(SolicitudCreditoTo solicitudCreditoTo) throws CreditoAutomotrizException {

		SolicitudCredito solicitudCredito = obtenerSolicitudCredito(solicitudCreditoTo);
		solicitudCredito.setEstadoSolicitud(EstadoSolicitudEnum.REGISTRADA);
		solicitudCredito.setFechaElaboracion(LocalDate.now());

		verificarSolicitudExitenten(solicitudCredito);
		verificarEjecutivoYPatio(solicitudCredito);
		try {
			SolicitudCredito solicitudCreditoAlmacenada = solicitudCreditoRepository.save(solicitudCredito);
			if (null != solicitudCreditoAlmacenada.getCodigoSolicitudCredito()) {
				Vehiculo vehiculo = solicitudCreditoAlmacenada.getVehiculo();
				vehiculo.setEstadoVehiculo(EstadoVehiculoEnum.COMPROMETIDO);
				vehiculoServicio.guardarVehiculo(vehiculo);
			}

			patioClienteServicio.guardarPatioCliente(solicitudCredito.getPatio(), solicitudCredito.getCliente());

			return solicitudCreditoAlmacenada;
		} catch (ConstraintViolationException e) {
			throw new CreditoAutomotrizException("No se pudo guardar la solicitud. Error: ".concat(e.getMessage()));
		}

	}

	private void verificarEjecutivoYPatio(SolicitudCredito solicitudCredito) throws CreditoAutomotrizException {
		Patio patioDelEjecutivo = solicitudCredito.getEjecutivo().getPatio();
		Patio patioDeLaSolicitud = solicitudCredito.getPatio();
		if (patioDelEjecutivo.equals(patioDeLaSolicitud)) {
			return;
		} else {
			throw new CreditoAutomotrizException("El ejecutivo no pertence al patio");
		}

	}

	private void verificarSolicitudExitenten(SolicitudCredito solicitudCredito) throws CreditoAutomotrizException {

		String identificacion = solicitudCredito.getCliente().getIdentificacion();
		LocalDate fechaElaboracion = solicitudCredito.getFechaElaboracion();

		SolicitudCredito solicitudExistente = this.obtenerSolicituPorClienteFecha(identificacion, fechaElaboracion);
		if (null != solicitudExistente
				&& solicitudExistente.getEstadoSolicitud().equals(EstadoSolicitudEnum.REGISTRADA)) {
			throw new CreditoAutomotrizException("Existe una solicitud registrada para: ".concat(identificacion));
		}

	}

	@Override
	public SolicitudCredito obtenerSolicituPorClienteFecha(String identificacion, LocalDate fechaElaboracion) {
		SolicitudCredito solicituPorClienteFecha = solicitudCreditoRepository
				.obtenerSolicituPorClienteFecha(identificacion, fechaElaboracion);
		return solicituPorClienteFecha;
	}

	@Override
	public List<SolicitudCredito> obtenerSolicitudesPorCliente(Cliente cliente) {
		return solicitudCreditoRepository.obtenerSolicitudesPorCliente(cliente);
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
		if (null == identificacionEjecutivo) {
			throw new CreditoAutomotrizException("La identificacion del ejecutivo es obligatoria");
		}
		Ejecutivo ejecutivo = ejecutivoServicio.obtenerPorIdentificacion(identificacionEjecutivo);
		this.mensajeError = "No existe ejecutivo para la identificacion: ";
		verificarRegistro(identificacionEjecutivo, ejecutivo, mensajeError);
		return ejecutivo;
	}

	private Vehiculo obtenerVehiculo(String placa, EstadoVehiculoEnum estadoVehiculo)
			throws CreditoAutomotrizException {
		if (null == placa) {
			throw new CreditoAutomotrizException("La placa del vhículo es obligatoria");
		}
		Vehiculo vehiculo = vehiculoServicio.obtenerPorPlacaEstado(placa, estadoVehiculo);
		this.mensajeError = "No existe vehículo o esta comprometido. Placa: ";
		verificarRegistro(placa, vehiculo, mensajeError);
		return vehiculo;
	}

	private Patio obtenerPunto(String numeroPuntoVenta) throws CreditoAutomotrizException {
		if (null == numeroPuntoVenta) {
			throw new CreditoAutomotrizException("El número de punto de venta es obligatorio");
		}
		Patio patio = patioServicio.obtenerPorNumeroPuntoVenta(numeroPuntoVenta);
		this.mensajeError = "No existe punto de venta para: ";
		verificarRegistro(numeroPuntoVenta, patio, mensajeError);
		return patio;
	}

	private Cliente obtenerCliente(String identificacionCliente) throws CreditoAutomotrizException {
		if (null == identificacionCliente) {
			throw new CreditoAutomotrizException("La identificacion del cliente es obligatoria");
		}
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
