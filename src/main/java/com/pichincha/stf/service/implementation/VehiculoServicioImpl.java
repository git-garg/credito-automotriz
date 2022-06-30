package com.pichincha.stf.service.implementation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pichincha.stf.entity.Marca;
import com.pichincha.stf.entity.Vehiculo;
import com.pichincha.stf.entity.enumeration.EstadoVehiculoEnum;
import com.pichincha.stf.entity.to.VehiculoTo;
import com.pichincha.stf.respository.VehiculoRepository;
import com.pichincha.stf.service.MarcaServicio;
import com.pichincha.stf.service.VehiculoServicio;
import com.pichincha.stf.service.exception.CreditoAutomotrizException;

@Service
public class VehiculoServicioImpl implements VehiculoServicio {

	@Autowired
	private VehiculoRepository vehiculoRepository;

	@Autowired
	private MarcaServicio marcaServicio;

	@Override
	public Vehiculo guardarAPartirDeTo(VehiculoTo vehiculoTo) throws CreditoAutomotrizException {

		if (existeVehiculo(vehiculoTo)) {
			throw new CreditoAutomotrizException(
					"Ya existe un vehiculo con placa: " + vehiculoTo.getVehiculo().getPlaca());
		} else {
			return verificarMarcaYGuardar(vehiculoTo);
		}

	}

	@Override
	public Vehiculo actualizarAPartirDeTo(VehiculoTo vehiculoTo) throws CreditoAutomotrizException {
		String placa = vehiculoTo.getVehiculo().getPlaca();
		Vehiculo vehiculoConsultado = obtenerVehiculoPorPlaca(placa);
		if (null == vehiculoConsultado) {
			throw new CreditoAutomotrizException("No existe un vehiculo con placa: ".concat(placa));
		} else {
			VehiculoTo vehiculoToModificado = new VehiculoTo();
			Vehiculo obtenerVehiculoModificado = obtenerVehiculoModificado(vehiculoConsultado,
					vehiculoTo.getVehiculo());
			vehiculoToModificado.setVehiculo(obtenerVehiculoModificado);
			vehiculoToModificado.setAbreviaturaMarca(vehiculoTo.getAbreviaturaMarca());
			return verificarMarcaYGuardar(vehiculoToModificado);
		}

	}

	private Vehiculo obtenerVehiculoModificado(Vehiculo vehiculoConsultado, Vehiculo vehiculoModificado) {
		vehiculoConsultado.setAvaluo(vehiculoModificado.getAvaluo());
		vehiculoConsultado.setCilindraje(vehiculoModificado.getCilindraje());
		vehiculoConsultado.setEstadoVehiculo(vehiculoModificado.getEstadoVehiculo());
		vehiculoModificado.setModelo(vehiculoModificado.getModelo());
		vehiculoModificado.setNumeroChasis(vehiculoModificado.getNumeroChasis());
		vehiculoModificado.setPlaca(vehiculoModificado.getPlaca());
		vehiculoModificado.setTipo(vehiculoModificado.getTipo());
		return vehiculoConsultado;
	}

	private Vehiculo verificarMarcaYGuardar(VehiculoTo vehiculoTo) throws CreditoAutomotrizException {
		Vehiculo vehiculo = vehiculoTo.getVehiculo();
		String abreviaturaMarca = vehiculoTo.getAbreviaturaMarca();
		Marca marca = marcaServicio.obtenerMarcaPorAbreviatura(abreviaturaMarca);

		if (null == marca) {
			throw new CreditoAutomotrizException("No existe marca para: " + abreviaturaMarca);
		} else {
			vehiculo.setMarca(marca);
			try {
				return this.guardar(vehiculo, EstadoVehiculoEnum.DISPONIBLE);
			} catch (ConstraintViolationException e) {
				throw new CreditoAutomotrizException(
						"No se pudo guardar el registro.".concat(" Error: ".concat(e.getMessage())));
			}
		}
	}

	@Override
	public Vehiculo obtenerPorPlacaEstado(String placa, EstadoVehiculoEnum estadoVehiculo) {
		return vehiculoRepository.obtenerPorPlacaEstado(placa, estadoVehiculo);
	}

	@Override
	public void eliminarVahiculo(Vehiculo vehiculo) {
		vehiculoRepository.delete(vehiculo);
	}

	@Override
	public Vehiculo guardarVehiculo(Vehiculo vehiculo) {
		return vehiculoRepository.save(vehiculo);
	}

	@Override
	public Vehiculo obtenerVehiculoPorPlaca(String placa) {
		return vehiculoRepository.findByPlaca(placa);
	}

	@Override
	public List<VehiculoTo> obtenerVehiculosPorMarca(Marca marca) {
		return vehiculoRepository.obtenerVehiculosPorMarca(marca).orElse(new ArrayList<>());
	}

	public Vehiculo guardar(Vehiculo vehiculo, EstadoVehiculoEnum estadoVehiculo) {
		vehiculo.setEstadoVehiculo(estadoVehiculo);
		Vehiculo vehiculoAlmacenado = guardarVehiculo(vehiculo);
		return vehiculoAlmacenado;
	}

	private boolean existeVehiculo(VehiculoTo vehiculoTo) {
		return null != obtenerVehiculoPorPlaca(vehiculoTo.getVehiculo().getPlaca());
	}

}
