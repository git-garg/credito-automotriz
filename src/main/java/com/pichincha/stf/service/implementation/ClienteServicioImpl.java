/**
 * Clase: ClienteServicioImpl.java
 * Fecha: 29 jun. 2022
 */
package com.pichincha.stf.service.implementation;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.List;

import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.opencsv.exceptions.CsvException;
import com.pichincha.stf.entity.Cliente;
import com.pichincha.stf.entity.enumeration.EstadoCivilEnum;
import com.pichincha.stf.entity.enumeration.SiNoEnum;
import com.pichincha.stf.entity.to.ClienteTo;
import com.pichincha.stf.respository.ClienteRepository;
import com.pichincha.stf.respository.SolicitudCreditoRepository;
import com.pichincha.stf.service.ClienteServicio;
import com.pichincha.stf.service.ProcesadorCsvServicio;
import com.pichincha.stf.service.exception.CreditoAutomotrizException;
import com.pichincha.stf.util.FechaUtil;
import com.pichincha.stf.util.Util;

/**
 * Clase de implentacion para la carga de clientes
 */
@Service
public class ClienteServicioImpl implements ClienteServicio {

	private static final long CERO = 0L;

	private static final Logger log = LoggerFactory.getLogger(ClienteServicioImpl.class);

	@Autowired
	private ProcesadorCsvServicio procesadorCsvServicio;

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private SolicitudCreditoRepository solicitudCreditoRepository;

	/**
	 * Carga los clientes a partir de un archivo csv
	 */
	@Override
	public Long cargarClientes(String ruta, String archivo) throws IOException, URISyntaxException, CsvException {

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

			long totalClientes = clienteRepository.count();
			log.info("Total clientes ingresados: " + totalClientes);
			clienteRepository.findAll().forEach(cliente -> log.info("Identificacion: " + cliente.getIdentificacion()));
			return totalClientes;
		} else {
			log.error("Existen clientes duplicados");
			duplicados.stream().forEach(
					duplicado -> log.error("Identificacion duplicada: " + duplicado[posicionColumnaIdentificaion]));
			return CERO;
		}
	}

	@Override
	public Cliente obtenerPorIdentificacion(String identificacionCliente) {
		return clienteRepository.obtenerPorIdentificacion(identificacionCliente);
	}

	@Override
	public Cliente guardarClienteDesdeTo(ClienteTo clienteTo) throws CreditoAutomotrizException {
		Cliente cliente = clienteTo.getCliente();
		String identificacion = cliente.getIdentificacion();
		Cliente clienteConsultado = clienteRepository.obtenerPorIdentificacion(identificacion);
		if (null == clienteConsultado) {
			try {
				return clienteRepository.save(cliente);
			} catch (ConstraintViolationException e) {
				throw new CreditoAutomotrizException(e.getMessage());
			}
		} else {
			throw new CreditoAutomotrizException("Ya existe un cliente con identificacion: ".concat(identificacion));
		}

	}

	@Override
	public Cliente actualizarClienteDesdeTo(ClienteTo clienteTo) throws CreditoAutomotrizException {
		String identificacionCliente = clienteTo.getCliente().getIdentificacion();
		Cliente clienteConsultado = obtenerClientePorIdentificacion(identificacionCliente);
		if (null == clienteConsultado) {
			throw new CreditoAutomotrizException(
					"No existe cliente con la identificacion: ".concat(identificacionCliente));
		} else {
			obtenerClienteModificado(clienteConsultado, clienteTo.getCliente());
			return clienteRepository.save(clienteConsultado);
		}
	}

	@Override
	public void eliminarCliente(Cliente cliente) throws CreditoAutomotrizException {
		verificarReferenciasDelCliente(cliente);
		clienteRepository.delete(cliente);
	}

	private void verificarReferenciasDelCliente(Cliente cliente) throws CreditoAutomotrizException {
		if (!solicitudCreditoRepository.obtenerSolicitudesPorCliente(cliente).isEmpty()) {
			throw new CreditoAutomotrizException("El cliente con identificacion ".concat(cliente.getIdentificacion())
					.concat(" tiene solicitudes ingresadas"));
		}
	}

	private void obtenerClienteModificado(Cliente clienteConsultado, Cliente cliente) {
		clienteConsultado.setApellido(cliente.getApellido());
		clienteConsultado.setDireccion(cliente.getDireccion());
		clienteConsultado.setEdad(cliente.getEdad());
		clienteConsultado.setEstadoCivil(cliente.getEstadoCivil());
		clienteConsultado.setFechaNacimiento(cliente.getFechaNacimiento());
		clienteConsultado.setIdentificacion(cliente.getIdentificacion());
		clienteConsultado.setIdentificacionConyugue(cliente.getIdentificacionConyugue());
		clienteConsultado.setNombre(cliente.getNombre());
		clienteConsultado.setSujetoCredito(cliente.getSujetoCredito());
		clienteConsultado.setTelefono(cliente.getTelefono());
	}

	private Cliente obtenerClientePorIdentificacion(String identificacion) {
		return clienteRepository.obtenerPorIdentificacion(identificacion);
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
