/**
 * Clase: MarcaServicioImpl.java
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
import com.pichincha.stf.entity.Marca;
import com.pichincha.stf.respository.MarcaRepository;
import com.pichincha.stf.service.MarcaServicio;
import com.pichincha.stf.service.ProcesadorCsvServicio;

/**
 * Clase de implentacion para la carga de marcas
 */

@Service
public class MarcaServicioImpl implements MarcaServicio {

	private static final Logger log = LoggerFactory.getLogger(MarcaServicioImpl.class);

	@Autowired
	private MarcaRepository marcaRepository;

	@Autowired
	private ProcesadorCsvServicio procesadorCsvServicio;

	@Override
	public void cargarMarcas(String ruta, String archivo) throws IOException, URISyntaxException, CsvException {

		List<String[]> registrosArchivoCsv = procesadorCsvServicio.obtenerRegistrosArchivoCsv(ruta, archivo);

		int posicionColumnaAbreviatura = 1;

		Collection<String[]> duplicados = procesadorCsvServicio.obtenerDuplicados(registrosArchivoCsv,
				posicionColumnaAbreviatura);

		if (duplicados.isEmpty()) {

			registrosArchivoCsv.stream().forEach(registroCsv -> {
				marcaRepository.save(obtenerMarca(registroCsv));
			});

			log.info("Total marcas ingresados: " + marcaRepository.count());
			marcaRepository.findAll().forEach(marca -> log.info("Marca: " + marca.getNombre()));

		} else {

		}

	}

	@Override
	public Marca obtenerMarcaPorAbreviatura(String abreviatura) {
		return marcaRepository.findByAbreviatura(abreviatura);
	}

	private Marca obtenerMarca(String[] registroCsv) {
		Marca marca = new Marca();
		marca.setNombre(registroCsv[0]);
		marca.setAbreviatura(registroCsv[1]);
		return marca;
	}

}
