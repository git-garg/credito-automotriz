/**
 * Clase: Ejecutivo.java
 * Fecha: 27 jun. 2022
 * Usuario: GABRIEL
 */
package com.pichincha.stf.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

/**
 * @author GABRIEL
 *
 */

@Entity
@Table
@Data
public class Ejecutivo extends Persona {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "codigo_ejecutivo")
	private Long codigoEjecutivo;

	@Column
	private String celular;

	@ManyToOne
	@JoinColumn(name = "codigo_patio", nullable = false)
	private Patio patio;

}
