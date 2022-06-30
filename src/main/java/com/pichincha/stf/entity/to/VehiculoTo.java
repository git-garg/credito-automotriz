package com.pichincha.stf.entity.to;

import com.pichincha.stf.entity.Vehiculo;

import lombok.Data;

@Data
public class VehiculoTo {

	private Vehiculo vehiculo;

	private String abreviaturaMarca;

	public VehiculoTo() {

	}

	public VehiculoTo(Vehiculo vehiculo, String abreviaturaMarca) {
		this.vehiculo = vehiculo;
		this.abreviaturaMarca = abreviaturaMarca;
	}

}
