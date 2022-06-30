package com.pichincha.stf.respository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pichincha.stf.entity.Marca;
import com.pichincha.stf.entity.Vehiculo;
import com.pichincha.stf.entity.enumeration.EstadoVehiculoEnum;
import com.pichincha.stf.entity.to.VehiculoTo;

public interface VehiculoRepository extends JpaRepository<Vehiculo, Long> {

	Vehiculo findByPlaca(String placa);

	@Query("SELECT v FROM Vehiculo v WHERE v.placa = :placa AND v.estadoVehiculo = :estadoVehiculo ")
	Vehiculo obtenerPorPlacaEstado(@Param("placa") String placa,
			@Param("estadoVehiculo") EstadoVehiculoEnum estadoVehiculo);

	@Query(" SELECT new com.pichincha.stf.entity.to.VehiculoTo(v, v.marca.abreviatura) FROM Vehiculo v WHERE v.marca = :marca ")
	Optional<List<VehiculoTo>> obtenerVehiculosPorMarca(@Param("marca") Marca marca);

}
