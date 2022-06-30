/**
 * Clase: Vehiculo.java
 * Fecha: 29 jun. 2022
 */
package com.pichincha.stf.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import com.pichincha.stf.entity.enumeration.EstadoVehiculoEnum;

import lombok.Data;

/**
 * Clase entidad que reprsenta la tabla vehiculo
 */

@Entity
@Table
@Data
public class Vehiculo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "codigo_vehiculo")
	private Long codigoVehiculo;

	@Column
	@NotEmpty(message = "El campo placa es obligatrio")
	private String placa;

	@Column
	@NotEmpty(message = "El campo modelo es obligatrio")
	private String modelo;

	@Column(name = "numero_chasis")
	@NotEmpty(message = "El campo numero de chasis es obligatrio")
	private String numeroChasis;

	@Column
	private String tipo;

	@Column
	private String cilindraje;

	@Column
	private BigDecimal avaluo;

	@ManyToOne
	@JoinColumn(name = "codigo_marca", nullable = false)
	private Marca marca;

	@Enumerated(EnumType.STRING)
	@Column(name = "estado_vehiculo")
	private EstadoVehiculoEnum estadoVehiculo;

}
