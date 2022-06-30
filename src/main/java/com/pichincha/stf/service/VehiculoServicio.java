package com.pichincha.stf.service;

import com.pichincha.stf.entity.Vehiculo;
import com.pichincha.stf.entity.enumeration.EstadoVehiculoEnum;
import com.pichincha.stf.entity.to.VehiculoTo;
import com.pichincha.stf.service.exception.CreditoAutomotrizException;

public interface VehiculoServicio {

	Vehiculo guardarAPartirDeTo(VehiculoTo vehiculoTo) throws CreditoAutomotrizException;

	Vehiculo actualizarAPartirDeTo(VehiculoTo vehiculoTo) throws CreditoAutomotrizException;

	Vehiculo obtenerPorPlacaEstado(String placa, EstadoVehiculoEnum estadoVehiculo);

	Vehiculo guardarVehiculo(Vehiculo vehiculo);
	
	Vehiculo obtenerVehiculoPorPlaca(String placa);

	void eliminarVahiculo(Vehiculo vehiculo);

}
