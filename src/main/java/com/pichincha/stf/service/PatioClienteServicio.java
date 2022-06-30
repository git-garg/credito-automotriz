package com.pichincha.stf.service;

import com.pichincha.stf.entity.Cliente;
import com.pichincha.stf.entity.Patio;
import com.pichincha.stf.entity.PatioCliente;

public interface PatioClienteServicio {

	PatioCliente guardarPatioCliente(Patio patio, Cliente cliente);

}
