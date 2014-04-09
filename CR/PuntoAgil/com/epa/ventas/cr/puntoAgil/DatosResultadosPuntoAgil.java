/*
 * $Id: DatosResultadosPuntoAgil.java,v 1.2 2006/09/15 21:34:43 programa4 Exp $
 * ===========================================================================
 *
 * Proyecto		: PuntoAgil
 * Paquete		: com.epa.ventas.cr.puntoAgil
 * Programa		: DatosResultadosPuntoAgil.java
 * Creado por	: programa4
 * Creado en 	: 25/05/2006 01:30:18 PM
 * (C) Copyright 2006 Todos los Derechos Reservados
 * ---------------------------------------------------------------------------
 * Información de Repositorio:
 * ---------------------------------------------------------------------------
 * Rama de Desarrollo	: $Name:  $
 * Bloqueado por		: $Locker:  $
 * Estado de Revisión	: $State: Exp $
 * ---------------------------------------------------------------------------
 * Actualizaciones:
 * ---------------------------------------------------------------------------
 * $Log: DatosResultadosPuntoAgil.java,v $
 * Revision 1.2  2006/09/15 21:34:43  programa4
 * Correcciones solicitadas por credicard
 * Ajuestes solicitados sobre mensajes de respuesta al cajero
 *
 * Revision 1.1  2006/06/10 02:11:29  programa4
 * Objetos dto para almacenar datos de tarjetas, pagos, respuestas, y archivos maestros
 *
 * Revision 1.1  2006/05/25 20:40:46  programa4
 * Primeros pasos para procesamiento de cheques por Punto Agil
 *
 *
 * ===========================================================================
 */
package com.epa.ventas.cr.puntoAgil;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Timestamp;

import org.apache.commons.lang.StringUtils;

/**
 * DTO conteniendo los datos de respuesta del Punto Ágil.
 *
 * @author programa4 - $Author: programa4 $
 * @version $Revision: 1.2 $ - $Date: 2006/09/15 21:34:43 $
 * @since 25/05/2006
 */
public class DatosResultadosPuntoAgil {

	private Integer numSeq;

	private String codRespuesta;

	private String mensajeRespuesta;

	private String mensajeError;

	private String nombreAutorizador;

	private String numeroAutorizacion;

	private String nombreVoucher;

	private Timestamp horaInicia;

	private Timestamp horaFinaliza;
	
	private double montoAprobado;

	/**
	 * @return Returns el codRespuesta.
	 */
	public String getCodRespuesta() {
		return this.codRespuesta;
	}

	/**
	 * @param _codRespuesta
	 *            El codRespuesta a asignar.
	 */
	public void setCodRespuesta(String _codRespuesta) {
		this.codRespuesta = _codRespuesta;
	}

	/**
	 * @return Returns el mensajeError.
	 */
	public String getMensajeError() {
		return this.mensajeError;
	}

	/**
	 * @param _mensajeError
	 *            El mensajeError a asignar.
	 */
	public void setMensajeError(String _mensajeError) {
		this.mensajeError = _mensajeError;
	}

	/**
	 * @return Returns el nombreAutorizador.
	 */
	public String getNombreAutorizador() {
		return this.nombreAutorizador;
	}

	/**
	 * @param _nombreAutorizador
	 *            El nombreAutorizador a asignar.
	 */
	public void setNombreAutorizador(String _nombreAutorizador) {
		this.nombreAutorizador = _nombreAutorizador;
	}

	/**
	 * @return Returns el nombreVoucher.
	 */
	public String getNombreVoucher() {
		return this.nombreVoucher;
	}

	/**
	 * @param _nombreVoucher
	 *            El nombreVoucher a asignar.
	 */
	public void setNombreVoucher(String _nombreVoucher) {
		this.nombreVoucher = _nombreVoucher;
	}

	/**
	 * @return Returns el numeroAutorizacion.
	 */
	public String getNumeroAutorizacion() {
		return this.numeroAutorizacion;
	}

	/**
	 * @param _numeroAutorizacion
	 *            El numeroAutorizacion a asignar.
	 */
	public void setNumeroAutorizacion(String _numeroAutorizacion) {
		this.numeroAutorizacion = _numeroAutorizacion;
	}

	/**
	 * @return Returns el numSeq.
	 */
	public Integer getNumSeq() {
		return this.numSeq;
	}

	/**
	 * @param _numSeq
	 *            El numSeq a asignar.
	 */
	public void setNumSeq(int _numSeq) {
		this.numSeq = new Integer(_numSeq);
	}

	/**
	 * @param _numSeq
	 *            El numSeq a asignar.
	 */
	public void setNumSeq(Integer _numSeq) {
		this.numSeq = _numSeq;
	}

	/**
	 * @return Returns el mensajeRespuesta.
	 */
	public String getMensajeRespuesta() {
		return this.mensajeRespuesta;
	}

	/**
	 * @param _mensajeRespuesta
	 *            El mensajeRespuesta a asignar.
	 */
	public void setMensajeRespuesta(String _mensajeRespuesta) {
		this.mensajeRespuesta = _mensajeRespuesta;
	}

	public String toString() {
		StringWriter sWriter = new StringWriter();
		PrintWriter pWriter = new PrintWriter(sWriter);
		if (Constantes.COD_RESPUESTA_APROBADO.equals(this.getCodRespuesta())) {
			pWriter.println("TRANSACCION APROBADA");
		} else {
			pWriter.println("TRANSACCION RECHAZADA");
		}
		pWriter.println("Cod. Respuesta:\t");
		pWriter.println(this.getCodRespuesta());
		pWriter.println("Mensaje Respuesta:\t");
		pWriter.println(this.getMensajeRespuesta());
		if (StringUtils.isNotBlank(this.getMensajeError())) {
			pWriter.println("Mensaje Respuesta 2:\t");
			pWriter.println(this.getMensajeError());
		}
		pWriter.println("Num. Sequencia:\t");
		pWriter.println(this.getNumSeq());
		pWriter.println("Autorización:\t\t");
		pWriter.println(this.getNumeroAutorizacion());
		pWriter.println("Nombre Autorizador: \t");
		pWriter.println(this.getNombreAutorizador());
		pWriter.close();
		return sWriter.toString();
	}

	/**
	 * @return Returns el horaFinaliza.
	 */
	public Timestamp getHoraFinaliza() {
		return this.horaFinaliza;
	}

	/**
	 * @param _horaFinaliza
	 *            El horaFinaliza a asignar.
	 */
	public void setHoraFinaliza(Timestamp _horaFinaliza) {
		this.horaFinaliza = _horaFinaliza;
	}

	/**
	 * @return Returns el horaInicia.
	 */
	public Timestamp getHoraInicia() {
		return this.horaInicia;
	}

	/**
	 * @param _horaInicia
	 *            El horaInicia a asignar.
	 */
	public void setHoraInicia(Timestamp _horaInicia) {
		this.horaInicia = _horaInicia;
	}

	/**
	 * @param _horaFinaliza
	 *            El horaFinaliza a asignar.
	 */
	public void setHoraFinaliza(java.util.Date _horaFinaliza) {
		this.horaFinaliza = new Timestamp(_horaFinaliza.getTime());
	}

	/**
	 * @param _horaInicia
	 *            El horaInicia a asignar.
	 */
	public void setHoraInicia(java.util.Date _horaInicia) {
		this.horaInicia = new Timestamp(_horaInicia.getTime());
	}

	/**
	 * Retorna la duracion de la transaccion restando horaFinaliza de horaInicia
	 *
	 * @return Timestamp con duracion. null si alguna de las dos es null.
	 */
	public Timestamp getDuracion() {
		if (this.getHoraFinaliza() == null || this.getHoraInicia() == null) {
			return null;
		}
		return new Timestamp(this.getHoraFinaliza().getTime()
				- this.getHoraInicia().getTime());
	}

	public double getMontoAprobado() {
		return montoAprobado;
	}

	public void setMontoAprobado(double montoAprobado) {
		this.montoAprobado = montoAprobado;
	}
}
