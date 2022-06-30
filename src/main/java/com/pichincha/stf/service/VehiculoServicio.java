package com.pichincha.stf.service;

import java.util.List;

import com.pichincha.stf.entity.Marca;
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

	List<VehiculoTo> obtenerVehiculosPorMarca(Marca marca);

	List<VehiculoTo> obtenerVehiculosPorModelo(String modelo);
	
	List<VehiculoTo> obtenerVehiculosPorAnio(int anio);

	void eliminarVahiculo(Vehiculo vehiculo);

}
