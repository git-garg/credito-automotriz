/**
 * Clase: Persona.java
 * Fecha: 27 jun. 2022
 */
package com.pichincha.stf.entity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import lombok.Data;

@Data
@MappedSuperclass
public class Persona {

	@Column
	private String identificacion;

	@Column
	private String nombre;

	@Column
	private String apellido;

	@Column
	private Integer edad;

	@Column
	private String direccion;

	@Column
	private String telefono;

}
