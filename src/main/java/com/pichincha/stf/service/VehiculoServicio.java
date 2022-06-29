package com.pichincha.stf.service;

import com.pichincha.stf.entity.Vehiculo;
import com.pichincha.stf.entity.VehiculoTo;
import com.pichincha.stf.service.exception.CreditoAutomotrizException;

public interface VehiculoServicio {

	Vehiculo guardar(VehiculoTo vehiculoTo) throws CreditoAutomotrizException;

}
