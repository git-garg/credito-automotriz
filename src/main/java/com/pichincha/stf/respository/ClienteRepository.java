/**
 * Clase: ClienteRepository.java
 * Fecha: 28 jun. 2022
 */
package com.pichincha.stf.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pichincha.stf.entity.Cliente;

/**
 *
 */

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
	
	@Query("SELECT c FROM Cliente c WHERE c.identificacion = :identificacion ")
	Cliente obtenerPorIdentificacion(@Param("identificacion") String identificacion);

}
