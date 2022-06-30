/**
 * Clase: PatioClienteServicioImpl.java
 * Fecha: 30 jun. 2022
 */
package com.pichincha.stf.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pichincha.stf.entity.Cliente;
import com.pichincha.stf.entity.Patio;
import com.pichincha.stf.entity.PatioCliente;
import com.pichincha.stf.respository.PatioClienteRespository;
import com.pichincha.stf.service.PatioClienteServicio;

/**
 *
 */
@Service
public class PatioClienteServicioImpl implements PatioClienteServicio {

	@Autowired
	private PatioClienteRespository patioClienteRespository;

	@Override
	public PatioCliente guardarPatioCliente(Patio patio, Cliente cliente) {
		return patioClienteRespository.save(obtenerPatioCliente(patio, cliente));
	}

	private PatioCliente obtenerPatioCliente(Patio patio, Cliente cliente) {
		PatioCliente patioCliente = new PatioCliente();
		patioCliente.setPatio(patio);
		patioCliente.setCliente(cliente);
		return patioCliente;
	}

}
