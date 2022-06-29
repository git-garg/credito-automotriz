package com.pichincha.stf.service;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pichincha.stf.entity.Marca;
import com.pichincha.stf.entity.Vehiculo;
import com.pichincha.stf.entity.VehiculoTo;
import com.pichincha.stf.respository.MarcaRepository;
import com.pichincha.stf.respository.VehiculoRepository;
import com.pichincha.stf.service.exception.CreditoAutomotrizException;

@Service
public class VehiculoServicioImpl implements VehiculoServicio {

	@Autowired
	private VehiculoRepository vehiculoRepository;

	@Autowired
	private MarcaRepository marcaRepository;

	@Override
	public Vehiculo guardar(VehiculoTo vehiculoTo) throws CreditoAutomotrizException {
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
				throw new CreditoAutomotrizException("No se pudo guarda el registro. Placa: "
						+ vehiculo.getPlaca().concat(" Error: ".concat(e.getMessage())));
			}

		}

	}

	public Vehiculo guardar(Vehiculo vehiculo) {
		Vehiculo vehiculoAlmacenado = vehiculoRepository.save(vehiculo);
		return vehiculoAlmacenado;
	}

}
