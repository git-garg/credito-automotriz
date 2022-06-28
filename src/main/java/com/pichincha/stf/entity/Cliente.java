/**
 * Clase: Cliente.java
 * Fecha: 27 jun. 2022
 * Usuario: GABRIEL
 */
package com.pichincha.stf.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Clase entidad que reprsenta la tabla cliente
 *
 */

@Entity
@Table
public class Cliente {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "codigo_cliente")
	private Long codigoCliente;
	
	
}
