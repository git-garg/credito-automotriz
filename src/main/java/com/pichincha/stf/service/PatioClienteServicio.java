package com.pichincha.stf.service;

import com.pichincha.stf.entity.Cliente;
import com.pichincha.stf.entity.Patio;
import com.pichincha.stf.entity.PatioCliente;
import com.pichincha.stf.service.exception.CreditoAutomotrizException;

public interface PatioClienteServicio {

	PatioCliente guardarPatioCliente(Patio patio, Cliente cliente);

	PatioCliente guardarPatioCliente(String numeroPuntoVenta, String identificacion) throws CreditoAutomotrizException;

	PatioCliente actualizarPatioCliente(String numeroPuntoVenta, String identificacion, String nuevoNumeroPuntoVenta,
			String nuevaIdentificacion) throws CreditoAutomotrizException;

	void eliminarPatioCliente(String numeroPuntoVenta, String identificacion) throws CreditoAutomotrizException;

}
