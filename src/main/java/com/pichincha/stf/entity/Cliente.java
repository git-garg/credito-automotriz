/**
 * Clase: Cliente.java
 * Fecha: 27 jun. 2022
 */
package com.pichincha.stf.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.pichincha.stf.entity.enumeration.EstadoCivilEnum;
import com.pichincha.stf.entity.enumeration.SiNoEnum;

import lombok.Data;

/**
 * Clase entidad que reprsenta la tabla cliente
 *
 */

@Entity
@Table
@Data
public class Cliente extends Persona {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "codigo_cliente")
	private Long codigoCliente;

	@Column(name = "fecha_nacimiento")
	@NotNull(message = "El campo fecha de hacimiento es obligatorio")
	private LocalDate fechaNacimiento;

	@Enumerated(EnumType.STRING)
	@Column(name = "estado_civil")
	@NotNull(message = "El campo estado civil es obligatorio")
	private EstadoCivilEnum estadoCivil;

	@Column(name = "identificacion_conyugue")
	@NotEmpty(message = "El campo identificacion del cónyugue es obligatorio")
	private String identificacionConyugue;

	@Column(name = "nombre_conyugue")
	@NotEmpty(message = "El campo nombre del cónyugue es obligatorio")
	private String nombreConyugue;

	@Enumerated(EnumType.STRING)
	@Column(name = "sujto_credito")
	@NotNull(message = "El campo sujeto de credito es obligatorio")
	private SiNoEnum sujetoCredito;

}
