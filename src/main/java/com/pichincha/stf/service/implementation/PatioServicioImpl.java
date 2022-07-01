package com.pichincha.stf.service.implementation;

import java.util.ArrayList;

import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pichincha.stf.entity.Patio;
import com.pichincha.stf.entity.to.PatioTo;
import com.pichincha.stf.respository.EjecutivoRepository;
import com.pichincha.stf.respository.PatioRepository;
import com.pichincha.stf.respository.SolicitudCreditoRepository;
import com.pichincha.stf.service.PatioServicio;
import com.pichincha.stf.service.exception.CreditoAutomotrizException;

/**
 * Clase de implentacion para la carga de patios
 */
@Service
public class PatioServicioImpl implements PatioServicio {

	private static final Logger log = LoggerFactory.getLogger(PatioServicioImpl.class);

	@Autowired
	private PatioRepository patioRepository;

	@Autowired
	private SolicitudCreditoRepository solicitudCreditoRepository;

	@Autowired
	private EjecutivoRepository ejecutivoRepository;

	@Override
	public Long cargarPatios() {

		patioRepository.save(obtenerPatio("Pase San Francisco", "Cumbaya", "022856321", "123"));
		patioRepository.save(obtenerPatio("Carrión", "Santa Clara", "022856852", "456"));
		patioRepository.save(obtenerPatio("Condado", "Condado Shoping", "022856974", "789"));

		long totaPatios = patioRepository.count();
		log.info("Total patios cargados: " + totaPatios);
		patioRepository.findAll().stream()
				.forEach(patio -> log.info(patio.getNombre().concat(" - ").concat(patio.getNumeroPuntoVenta())));

		return totaPatios;
	}

	@Override
	public Patio guardarPatioAPatirDeTo(PatioTo patioTo) throws CreditoAutomotrizException {
		Patio patio = patioTo.getPatio();
		String numeroPuntoVenta = patio.getNumeroPuntoVenta();
		Patio patioConsultado = patioRepository.obtenerPorNumeroPuntoVenta(numeroPuntoVenta);
		if (null == patioConsultado) {
			try {
				return patioRepository.save(patio);
			} catch (ConstraintViolationException e) {
				throw new CreditoAutomotrizException(e.getMessage());
			}
		} else {
			throw new CreditoAutomotrizException(
					"Ya existe un patio con número de punto de venta: ".concat(numeroPuntoVenta));
		}

	}

	@Override
	public Patio actualizarPatioAPatirDeTo(PatioTo patioTo) throws CreditoAutomotrizException {
		Patio patio = patioTo.getPatio();
		String numeroPuntoVenta = patio.getNumeroPuntoVenta();
		Patio patioConsultado = patioRepository.obtenerPorNumeroPuntoVenta(numeroPuntoVenta);
		if (null == patioConsultado) {
			throw new CreditoAutomotrizException("No existe patio para: ".concat(numeroPuntoVenta));
		} else {
			modificarPatioConsultado(patioConsultado, patioTo.getPatio());
			return patioRepository.save(patioConsultado);
		}
	}

	private void modificarPatioConsultado(Patio patioConsultado, Patio patio) {
		patioConsultado.setDireccion(patio.getDireccion());
		patioConsultado.setNombre(patio.getNombre());
		patioConsultado.setNumeroPuntoVenta(patio.getNumeroPuntoVenta());
		patioConsultado.setTelefono(patio.getNumeroPuntoVenta());
	}

	@Override
	public void eliminarPatio(String numeroPuntoVenta) throws CreditoAutomotrizException {
		Patio patio = patioRepository.obtenerPorNumeroPuntoVenta(numeroPuntoVenta);
		if (tieneSolicitud(patio) || tieneEjecutivo(patio)) {
			throw new CreditoAutomotrizException("Tiene asociado solicitudes de crédito o ejecutivos");
		}
		patioRepository.delete(patio);
	}

	private boolean tieneEjecutivo(Patio patio) {
		return !ejecutivoRepository.findByPatio(patio).isEmpty();
	}

	private boolean tieneSolicitud(Patio patio) {
		return !solicitudCreditoRepository.obtenerSolicitudesPorPatio(patio).orElse(new ArrayList<>()).isEmpty();
	}

	@Override
	public Patio obtenerPorNumeroPuntoVenta(String numeroPuntoVenta) {
		return patioRepository.obtenerPorNumeroPuntoVenta(numeroPuntoVenta);
	}

	private Patio obtenerPatio(String nombre, String direccion, String telefono, String numeroPuntoVenta) {
		Patio patio = new Patio();
		patio.setNombre(nombre);
		patio.setDireccion(direccion);
		patio.setTelefono(telefono);
		patio.setNumeroPuntoVenta(numeroPuntoVenta);
		return patio;
	}

}
