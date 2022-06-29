package com.pichincha.stf.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pichincha.stf.entity.Patio;
import com.pichincha.stf.respository.PatioRepository;

/**
 * Clase de implentacion para la carga de patios
 */
@Service
public class PatioServicioImpl implements PatioServicio {

	private static final Logger log = LoggerFactory.getLogger(PatioServicioImpl.class);

	@Autowired
	private PatioRepository patioRepository;

	@Override
	public void cargarPatios() {

		patioRepository.save(obtenerPatio("Pase San Francisco", "Cumbaya", "022856321", "123"));
		patioRepository.save(obtenerPatio("Carrión", "Santa Clara", "022856852", "456"));
		patioRepository.save(obtenerPatio("Condado", "Condado Shoping", "022856974", "789"));

		log.info("Total patios cargados: " + patioRepository.count());
		patioRepository.findAll().stream()
				.forEach(patio -> log.info(patio.getNombre().concat(" - ").concat(patio.getNumeroPuntoVenta())));

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
