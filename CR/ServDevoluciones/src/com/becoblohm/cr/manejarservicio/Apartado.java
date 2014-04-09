/**
 * =============================================================================
 * Proyecto   : CajaRegistradora
 * Paquete    : com.becoblohm.manejarservicio
 * Programa   : Apartado.java
 * Creado por : gmartinelli
 * Creado en  : 02/04/2004 09:33 AM
 *
 * (c) Becoblohm, C.A.
 * -----------------------------------------------------------------------------
 * 				Actualizaciones
 * -----------------------------------------------------------------------------
 * Versión     : 1.1
 * Fecha       : 14-may-04 10:17
 * Analista    : gmartinelli
 * Descripción : Implementado manejo de Auditorias.
 * ============================================================================= 
 */
package com.becoblohm.cr.manejarservicio;

import java.util.Vector;

import org.apache.log4j.Logger;

import com.becoblohm.cr.excepciones.BaseDeDatosExcepcion;
import com.becoblohm.cr.excepciones.ConexionExcepcion;
import com.becoblohm.cr.excepciones.ProductoExcepcion;

/** 
 * Descripción: 
 * 		Subclase de Servicio. Maneja los servicios de Apartados desde su estado inicial (Ingreso del primer
 * producto) hasta su finalizacion.
 */
public class Apartado extends Servicio {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(Apartado.class);
	
	private char condicionAbono;
	private Vector abonos;
	private Abono abonoNuevo;
	private boolean apartadoExento = false;
	
	private int tiposDeAbonosInicial;
	private double[] porcentajesDeAbonos;
	private int tiempoVigencia;
	private String tipoVigencia;
	private boolean permiteRebajas = false;
	private boolean recalcularSaldo = false;
	private float cargoPorServicio = 0;
	private double mtoMinimoApartado = 0;
	private boolean preguntarFacturar = false;
    
	private Vector detalleAnterior;
    
	/**
	 * @return
	 */
	public Abono getAbonoNuevo() {
		return abonoNuevo;
	}

	/**
	 * @return
	 */
	public Vector getAbonos() {
		return abonos;
	}

	/**
	 * @return
	 */
	public boolean isApartadoExento() {
		return apartadoExento;
	}

	/**
	 * @return
	 */
	public float getCargoPorServicio() {
		return cargoPorServicio;
	}

	/**
	 * @return
	 */
	public char getCondicionAbono() {
		return condicionAbono;
	}

	/**
	 * @return
	 */
	public Vector getDetalleAnterior() {
		return detalleAnterior;
	}

	/**
	 * @return
	 */
	public double getMtoMinimoApartado() {
		return mtoMinimoApartado;
	}

	/**
	 * @return
	 */
	public boolean isPermiteRebajas() {
		return permiteRebajas;
	}

	/**
	 * @return
	 */
	public double[] getPorcentajesDeAbonos() {
		return porcentajesDeAbonos;
	}

	/**
	 * @return
	 */
	public boolean isPreguntarFacturar() {
		return preguntarFacturar;
	}

	/**
	 * @return
	 */
	public boolean isRecalcularSaldo() {
		return recalcularSaldo;
	}

	/**
	 * @return
	 */
	public int getTiempoVigencia() {
		return tiempoVigencia;
	}

	/**
	 * @return
	 */
	public int getTiposDeAbonosInicial() {
		return tiposDeAbonosInicial;
	}

	/**
	 * @return
	 */
	public String getTipoVigencia() {
		return tipoVigencia;
	}

	/* (no Javadoc)
	 * @see com.becoblohm.cr.manejarservicio.Servicio#anularProducto(int)
	 */
	Vector anularProducto(int renglon) throws ProductoExcepcion {
		// TODO Apéndice de método generado automáticamente
		return null;
	}

	/* (no Javadoc)
	 * @see com.becoblohm.cr.manejarservicio.Servicio#anularProducto(int, float)
	 */
	Vector anularProducto(int renglon, float cantidad) throws ProductoExcepcion {
		// TODO Apéndice de método generado automáticamente
		return null;
	}

	/* (no Javadoc)
	 * @see com.becoblohm.cr.manejarservicio.Servicio#finalizarServicio()
	 */
	protected Vector finalizarServicio() throws BaseDeDatosExcepcion, ConexionExcepcion {
		// TODO Apéndice de método generado automáticamente
		return null;
	}
	
	/*****************************************************************************
	 * Métodos agregados por promociones
	 *****************************************************************************/

	public void setDetalleAnterior(Vector detalleAnterior) {
		this.detalleAnterior = detalleAnterior;
	}

}