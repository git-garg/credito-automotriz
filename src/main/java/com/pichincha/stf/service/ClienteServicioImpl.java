/**
 * Clase: ClienteServicioImpl.java
 * Fecha: 29 jun. 2022
 */
package com.pichincha.stf.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.opencsv.exceptions.CsvException;
import com.pichincha.stf.entity.Cliente;
import com.pichincha.stf.entity.EstadoCivilEnum;
import com.pichincha.stf.entity.SiNoEnum;
import com.pichincha.stf.respository.ClienteRepository;
import com.pichincha.stf.service.exception.CreditoAutomotrizException;
import com.pichincha.stf.util.FechaUtil;
import com.pichincha.stf.util.Util;

/**
 * Clase de implentacion para la carga de clientes
 */
@Service
public class ClienteServicioImpl implements ClienteServicio {

	private static final Logger log = LoggerFactory.getLogger(ClienteServicioImpl.class);

	@Autowired
	private ProcesadorCsvServicio procesadorCsvServicio;

	@Autowired
	private ClienteRepository clienteRepository;

	/**
	 * Carga los clientes a partir de un archivo csv
	 */
	@Override
	public void cargarClientes(String ruta, String archivo) throws IOException, URISyntaxException, CsvException {

		List<String[]> registrosArchivoCsv = procesadorCsvServicio.obtenerRegistrosArchivoCsv(ruta, archivo);
		int posicionColumnaIdentificaion = 0;
		Collection<String[]> duplicados = procesadorCsvServicio.obtenerDuplicados(registrosArchivoCsv,
				posicionColumnaIdentificaion);

		if (duplicados.isEmpty()) {

			registrosArchivoCsv.stream().forEach(registroCsv -> {
				try {
					clienteRepository.save(obtenerCliente(registroCsv));
				} catch (CreditoAutomotrizException e) {
					log.error("No se pudo crear el cliente con la identificacion: "
							+ registroCsv[posicionColumnaIdentificaion] + " Error: " + e.getMessage());
				}
			});

			log.info("Total clientes ingresados: " + clienteRepository.findAll().size());
			clienteRepository.findAll().forEach(cliente -> log.info("Identificacion: " + cliente.getIdentificacion()));
		} else {
			log.error("Existen clientes duplicados");
			duplicados.stream().forEach(
					duplicado -> log.error("Identificacion duplicada: " + duplicado[posicionColumnaIdentificaion]));
		}
	}

	/**
	 * Conviete un arreglo de String en objeto Cliente
	 * 
	 * @param registroCsv
	 * @return
	 * @throws CreditoAutomotrizException
	 */
	private Cliente obtenerCliente(String[] registroCsv) throws CreditoAutomotrizException {
		Cliente cliente = new Cliente();

		cliente.setIdentificacion(registroCsv[0]);
		cliente.setNombre(registroCsv[1]);
		cliente.setApellido(registroCsv[2]);
		cliente.setEdad(Util.obtenerIntegerDeString(registroCsv[3]));
		cliente.setDireccion(registroCsv[4]);
		cliente.setTelefono(registroCsv[5]);
		cliente.setFechaNacimiento(FechaUtil.obtenerFechaFormato(registroCsv[6], "dd/MM/yyyy"));
		cliente.setEstadoCivil(EstadoCivilEnum.getEstadoCivilEnumPorAbreviatura(registroCsv[7]));
		cliente.setIdentificacionConyugue(registroCsv[8]);
		cliente.setNombreConyugue(registroCsv[9]);
		cliente.setSujetoCredito(SiNoEnum.obtenerSiNOEnumPorAbreviatura(registroCsv[10]));

		return cliente;
	}

}
