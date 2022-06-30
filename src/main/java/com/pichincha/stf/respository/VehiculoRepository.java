package com.pichincha.stf.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pichincha.stf.entity.Vehiculo;
import com.pichincha.stf.entity.enumeration.EstadoVehiculoEnum;

public interface VehiculoRepository extends JpaRepository<Vehiculo, Long> {

	Vehiculo findByPlaca(String placa);

	@Query("SELECT v FROM Vehiculo v WHERE v.placa = :placa AND v.estadoVehiculo = :estadoVehiculo ")
	Vehiculo obtenerPorPlacaEstado(@Param("placa") String placa,
			@Param("estadoVehiculo") EstadoVehiculoEnum estadoVehiculo);

}
