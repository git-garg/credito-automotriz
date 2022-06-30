/**
 * Clase: SolicitudCreditoServicio.java
 * Fecha: 29 jun. 2022
 */
package com.pichincha.stf.service;

import com.pichincha.stf.entity.SolicitudCredito;
import com.pichincha.stf.entity.to.SolicitudCreditoTo;
import com.pichincha.stf.service.exception.CreditoAutomotrizException;

/**
 *
 */
public interface SolicitudCreditoServicio {

	SolicitudCredito guardar(SolicitudCreditoTo solicitudCreditoTo) throws CreditoAutomotrizException;

}
