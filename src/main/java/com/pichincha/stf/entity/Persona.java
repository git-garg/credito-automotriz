/**
 * Clase: Persona.java
 * Fecha: 27 jun. 2022
 */
package com.pichincha.stf.entity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@MappedSuperclass
public class Persona {

	@Column
	@NotEmpty(message = "El campo identificacion es obligatorio")
	private String identificacion;

	@Column
	@NotEmpty(message = "El campo nombre es obligatorio")
	private String nombre;

	@Column
	@NotEmpty(message = "El campo apellido es obligatorio")
	private String apellido;

	@Column
	@NotNull(message = "El campo edad es obligatorio")
	private Integer edad;

	@Column
	@NotEmpty(message = "El campo direccion es obligatorio")
	private String direccion;

	@Column
	@NotEmpty(message = "El campo telefono es obligatorio")
	private String telefono;

}
