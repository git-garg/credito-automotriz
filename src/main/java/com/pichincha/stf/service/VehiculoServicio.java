package com.pichincha.stf.service;

import com.pichincha.stf.entity.Vehiculo;
import com.pichincha.stf.entity.enumeration.EstadoVehiculoEnum;
import com.pichincha.stf.entity.to.VehiculoTo;
import com.pichincha.stf.service.exception.CreditoAutomotrizException;

public interface VehiculoServicio {

	Vehiculo guardarAPartirDeTo(VehiculoTo vehiculoTo) throws CreditoAutomotrizException;

	Vehiculo obtenerPorPlacaEstado(String placa, EstadoVehiculoEnum estadoVehiculo);

	void actualizarVehiculo(Vehiculo vehiculo);

	void eliminarVahiculo(Vehiculo vehiculo);

}
