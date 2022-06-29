/**
 * Clase: Marca.java
 * Fecha: 29 jun. 2022
 */
package com.pichincha.stf.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 *
 */

@Entity
@Table
@Data
public class Marca {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "codigo_marca")
	private Long codigoMarca;

	@Column
	private String nombre;

	@Column
	private String abreviatura;

}
