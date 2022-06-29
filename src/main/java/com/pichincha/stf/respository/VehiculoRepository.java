package com.pichincha.stf.respository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pichincha.stf.entity.Vehiculo;

public interface VehiculoRepository extends JpaRepository<Vehiculo, Long> {

	Vehiculo findByPlaca(String placa);

}
