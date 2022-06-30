package com.pichincha.stf.service.implementation;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pichincha.stf.entity.Marca;
import com.pichincha.stf.entity.Vehiculo;
import com.pichincha.stf.entity.enumeration.EstadoVehiculoEnum;
import com.pichincha.stf.entity.to.VehiculoTo;
import com.pichincha.stf.respository.MarcaRepository;
import com.pichincha.stf.respository.VehiculoRepository;
import com.pichincha.stf.service.VehiculoServicio;
import com.pichincha.stf.service.exception.CreditoAutomotrizException;

@Service
public class VehiculoServicioImpl implements VehiculoServicio {

	@Autowired
	private VehiculoRepository vehiculoRepository;

	@Autowired
	private MarcaRepository marcaRepository;

	@Override
	public Vehiculo guardarAPartirDeTo(VehiculoTo vehiculoTo) throws CreditoAutomotrizException {

		if (existeVehiculo(vehiculoTo)) {
			throw new CreditoAutomotrizException(
					"Ya existe un vehiculo con placa: " + vehiculoTo.getVehiculo().getPlaca());
		} else {
			Vehiculo vehiculo = vehiculoTo.getVehiculo();
			String abreviaturaMarca = vehiculoTo.getAbreviaturaMarca();
			Marca marca = marcaRepository.findByAbreviatura(abreviaturaMarca);

			if (null == marca) {
				throw new CreditoAutomotrizException("No existe marca para: " + abreviaturaMarca);
			} else {
				vehiculo.setMarca(marca);
				try {
					return this.guardar(vehiculo);
				} catch (ConstraintViolationException e) {
					throw new CreditoAutomotrizException(
							"No se pudo guardar el registro.".concat(" Error: ".concat(e.getMessage())));
				}
			}
		}

	}

	@Override
	public Vehiculo obtenerPorPlacaEstado(String placa, EstadoVehiculoEnum estadoVehiculo) {
		return vehiculoRepository.obtenerPorPlacaEstado(placa, estadoVehiculo);
	}

	@Override
	public void actualizarVehiculo(Vehiculo vehiculo) {
		vehiculoRepository.save(vehiculo);
	}

	@Override
	public void eliminarVahiculo(Vehiculo vehiculo) {
		vehiculoRepository.delete(vehiculo);
	}

	public Vehiculo guardar(Vehiculo vehiculo) {
		vehiculo.setEstadoVehiculo(EstadoVehiculoEnum.DISPONIBLE);
		Vehiculo vehiculoAlmacenado = vehiculoRepository.save(vehiculo);
		return vehiculoAlmacenado;
	}

	private boolean existeVehiculo(VehiculoTo vehiculoTo) {
		return null != vehiculoRepository.findByPlaca(vehiculoTo.getVehiculo().getPlaca());
	}

}
