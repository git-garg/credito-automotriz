/**
 * Clase: PatioClienteServicioImpl.java
 * Fecha: 30 jun. 2022
 */
package com.pichincha.stf.service.implementation;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pichincha.stf.entity.Cliente;
import com.pichincha.stf.entity.Patio;
import com.pichincha.stf.entity.PatioCliente;
import com.pichincha.stf.entity.SolicitudCredito;
import com.pichincha.stf.respository.PatioClienteRespository;
import com.pichincha.stf.respository.SolicitudCreditoRepository;
import com.pichincha.stf.service.ClienteServicio;
import com.pichincha.stf.service.PatioClienteServicio;
import com.pichincha.stf.service.PatioServicio;
import com.pichincha.stf.service.exception.CreditoAutomotrizException;

/**
 *
 */
@Service
public class PatioClienteServicioImpl implements PatioClienteServicio {

	@Autowired
	private PatioClienteRespository patioClienteRespository;

	@Autowired
	private ClienteServicio clienteServicio;

	@Autowired
	private PatioServicio patioServicio;

	@Autowired
	private SolicitudCreditoRepository solicitudCreditoRepository;

	@Override
	public PatioCliente guardarPatioCliente(Patio patio, Cliente cliente) {
		return patioClienteRespository.save(obtenerPatioCliente(patio, cliente));
	}

	@Override
	public PatioCliente guardarPatioCliente(String numeroPuntoVenta, String identificacion)
			throws CreditoAutomotrizException {
		Cliente cliente = obtenerClientPorIdentificacion(identificacion);
		if (null == cliente) {
			throw new CreditoAutomotrizException("No existe cliente para la identificacion: ".concat(identificacion));
		}
		Patio patio = obtenerPatioPorNumeroPuntoVenta(numeroPuntoVenta);
		if (null == patio) {
			throw new CreditoAutomotrizException(
					"No existe patio para el numero de punto de venta: ".concat(numeroPuntoVenta));
		}
		return this.guardarPatioCliente(patio, cliente);
	}

	private Patio obtenerPatioPorNumeroPuntoVenta(String numeroPuntoVenta) {
		return patioServicio.obtenerPorNumeroPuntoVenta(numeroPuntoVenta);
	}

	private Cliente obtenerClientPorIdentificacion(String identificacion) {
		return clienteServicio.obtenerPorIdentificacion(identificacion);
	}

	@Override
	public PatioCliente actualizarPatioCliente(String numeroPuntoVenta, String identificacion,
			String nuevoNumeroPuntoVenta, String nuevaIdentificacion) throws CreditoAutomotrizException {
		PatioCliente patioCliente = patioClienteRespository.obtenerPatioClientePorNumeroIdentificacion(numeroPuntoVenta,
				identificacion);
		if (null == patioCliente) {
			throw new CreditoAutomotrizException("No existe una asignacion para el numero de punto de venta: "
					.concat(numeroPuntoVenta).concat(" e identificación: ").concat(identificacion));
		} else {
			Cliente cliente = obtenerClientPorIdentificacion(nuevaIdentificacion);
			if (null == cliente) {
				throw new CreditoAutomotrizException(
						"No existe cliente para la identificacion: ".concat(nuevaIdentificacion));
			}
			Patio patio = obtenerPatioPorNumeroPuntoVenta(nuevoNumeroPuntoVenta);
			if (null == patio) {
				throw new CreditoAutomotrizException(
						"No existe patio para el numero de punto de venta: ".concat(nuevoNumeroPuntoVenta));
			}
			patioCliente.setCliente(cliente);
			patioCliente.setPatio(patio);
			return patioClienteRespository.save(patioCliente);
		}
	}

	@Override
	public void eliminarPatioCliente(String numeroPuntoVenta, String identificacion) throws CreditoAutomotrizException {
		List<SolicitudCredito> listaSolicitudes = solicitudCreditoRepository
				.obtenerSolicitudPorNumeroPuntoVentaIdentificacion(numeroPuntoVenta, identificacion)
				.orElse(new ArrayList<>());
		if (listaSolicitudes.isEmpty()) {
			Cliente cliente = obtenerClientPorIdentificacion(identificacion);
			if (null == cliente) {
				throw new CreditoAutomotrizException(
						"No existe cliente para la identificacion: ".concat(numeroPuntoVenta));
			}
			Patio patio = obtenerPatioPorNumeroPuntoVenta(numeroPuntoVenta);
			if (null == patio) {
				throw new CreditoAutomotrizException(
						"No existe patio para el numero de punto de venta: ".concat(identificacion));
			}
			PatioCliente patioCliente = patioClienteRespository
					.obtenerPatioClientePorNumeroIdentificacion(numeroPuntoVenta, identificacion);
			if (null == patioCliente) {
				throw new CreditoAutomotrizException("No existe una asignacion para el numero de punto de venta: "
						.concat(numeroPuntoVenta).concat(" e identificación: ").concat(identificacion));
			}
			patioClienteRespository.delete(patioCliente);
		} else {
			throw new CreditoAutomotrizException(
					"Existen solictudes de credito para el cliente: ".concat(identificacion));
		}

	}

	private PatioCliente obtenerPatioCliente(Patio patio, Cliente cliente) {
		PatioCliente patioCliente = new PatioCliente();
		patioCliente.setPatio(patio);
		patioCliente.setCliente(cliente);
		patioCliente.setFechaAsignacion(LocalDate.now());
		return patioCliente;
	}

}
