/**
 * Clase: EjecutivoServicioImpl.java
 * Fecha: 29 jun. 2022
 */
package com.pichincha.stf.service.implementation;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.opencsv.exceptions.CsvException;
import com.pichincha.stf.entity.Ejecutivo;
import com.pichincha.stf.entity.Patio;
import com.pichincha.stf.respository.EjecutivoRepository;
import com.pichincha.stf.respository.PatioRepository;
import com.pichincha.stf.service.EjecutivoServicio;
import com.pichincha.stf.service.ProcesadorCsvServicio;
import com.pichincha.stf.service.exception.ClienteException;
import com.pichincha.stf.service.exception.CreditoAutomotrizException;
import com.pichincha.stf.util.Util;

/**
 * Clase de implentacion para la carga de ejecutivos
 */

@Service
public class EjecutivoServicioImpl implements EjecutivoServicio {

	private static final Logger log = LoggerFactory.getLogger(EjecutivoServicioImpl.class);

	@Autowired
	private ProcesadorCsvServicio procesadorCsvServicio;

	@Autowired
	private EjecutivoRepository ejecutivoRepository;

	@Autowired
	private PatioRepository patioRepository;

	@Override
	public void cargarEjecutivos(String ruta, String archivo)
			throws IOException, URISyntaxException, CsvException, ClienteException, CreditoAutomotrizException {
		List<String[]> registrosArchivoCsv = procesadorCsvServicio.obtenerRegistrosArchivoCsv(ruta, archivo);
		int posicionColumnaIdentificaion = 0;

		Collection<String[]> duplicados = procesadorCsvServicio.obtenerDuplicados(registrosArchivoCsv,
				posicionColumnaIdentificaion);
		if (duplicados.isEmpty()) {

			registrosArchivoCsv.stream().forEach(registroCsv -> {
				try {

					Patio patio = obtenerPatioPorNumero(registroCsv);
					ejecutivoRepository.save(obtenerEjecutivo(registroCsv, patio));
				} catch (CreditoAutomotrizException e) {
					log.error("No se pudo crear el ejecutivo con la identificacion: "
							+ registroCsv[posicionColumnaIdentificaion] + " Error: " + e.getMessage());
				}
			});

			log.info("Total ejecutivos ingresados: " + ejecutivoRepository.count());
			ejecutivoRepository.findAll()
					.forEach(ejecutivo -> log.info("Identificacion: " + ejecutivo.getIdentificacion()));

		} else {
			log.error("Existen ejecutivos duplicados");
			duplicados.stream().forEach(
					duplicado -> log.error("Identificacion duplicada: " + duplicado[posicionColumnaIdentificaion]));
		}
	}

	@Override
	public Ejecutivo obtenerPorIdentificacion(String identificacionEjecutivo) {
		return ejecutivoRepository.obtenerPorIdentificacion(identificacionEjecutivo);
	}

	private Patio obtenerPatioPorNumero(String[] registroCsv) throws CreditoAutomotrizException {
		String numeroPuntoVenta = registroCsv[7];

		Patio patio = patioRepository.findByNumeroPuntoVenta(numeroPuntoVenta);
		if (null == patio) {
			throw new CreditoAutomotrizException("No existe patio para el numero: ".concat(numeroPuntoVenta));
		}
		return patio;
	}

	private Ejecutivo obtenerEjecutivo(String[] registroCsv, Patio patio) throws CreditoAutomotrizException {

		Ejecutivo ejecutivo = new Ejecutivo();
		ejecutivo.setIdentificacion(registroCsv[0]);
		ejecutivo.setNombre(registroCsv[1]);
		ejecutivo.setApellido(registroCsv[2]);
		ejecutivo.setEdad(Util.obtenerIntegerDeString(registroCsv[3]));
		ejecutivo.setDireccion(registroCsv[4]);
		ejecutivo.setTelefono(registroCsv[5]);
		ejecutivo.setCelular(registroCsv[6]);
		ejecutivo.setPatio(patio);

		return ejecutivo;
	}

}
