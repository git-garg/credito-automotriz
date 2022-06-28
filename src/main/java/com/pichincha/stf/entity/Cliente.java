/**
 * Clase: Cliente.java
 * Fecha: 27 jun. 2022
 * Usuario: GABRIEL
 */
package com.pichincha.stf.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

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
	private LocalDateTime fechaNacimiento;

	@Enumerated(EnumType.STRING)
	@Column(name = "estado_civil")
	private EstadoCivilEnum estadoCivil;

	@Column(name = "identificacion_conyugue")
	private String identificacionConyugue;

	@Column(name = "nombre_conyugue")
	private String nombreConyugue;

	@Enumerated(EnumType.STRING)
	@Column(name = "sujto_credito")
	private SiNoEnum sujetoCredito;

}
