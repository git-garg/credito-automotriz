/**
 * Clase: ClienteServicio.java
 * Fecha: 29 jun. 2022
 */
package com.pichincha.stf.service;

import java.io.IOException;
import java.net.URISyntaxException;

import com.opencsv.exceptions.CsvException;
import com.pichincha.stf.entity.Cliente;
import com.pichincha.stf.entity.to.ClienteTo;
import com.pichincha.stf.service.exception.ClienteException;
import com.pichincha.stf.service.exception.CreditoAutomotrizException;

/**
 *
 */
public interface ClienteServicio {

	Long cargarClientes(String ruta, String archivo)
			throws IOException, URISyntaxException, CsvException, ClienteException, CreditoAutomotrizException;

	Cliente obtenerPorIdentificacion(String identificacionCliente);

	Cliente guardarClienteDesdeTo(ClienteTo clienteTo) throws CreditoAutomotrizException;

	Cliente actualizarClienteDesdeTo(ClienteTo clienteTo) throws CreditoAutomotrizException;

	void eliminarCliente(Cliente cliente) throws CreditoAutomotrizException;

}
