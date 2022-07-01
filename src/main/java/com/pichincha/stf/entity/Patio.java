/**
 * Clase: Patio.java
 * Fecha: 27 jun. 2022
 */
package com.pichincha.stf.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import lombok.Data;

/**
 * Clase entidad que reprsenta la tabla cliente
 *
 */

@Entity
@Table
@Data
public class Patio {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "codigo_patio")
	private Long codigoPatio;

	@Column
	@NotEmpty(message = "El campo nombre es obligatorio")
	private String nombre;

	@Column
	@NotEmpty(message = "El campo direccion es obligatorio")
	private String direccion;

	@Column
	@NotEmpty(message = "El campo telefono es obligatorio")
	private String telefono;

	@Column(name = "numero_punto_venta")
	@NotEmpty(message = "El campo numero de punto venta es obligatorio")
	private String numeroPuntoVenta;

}
