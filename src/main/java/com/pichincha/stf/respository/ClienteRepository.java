/**
 * Clase: ClienteRepository.java
 * Fecha: 28 jun. 2022
 */
package com.pichincha.stf.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pichincha.stf.entity.Cliente;

/**
 * @author GABRIEL
 *
 */

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

}
