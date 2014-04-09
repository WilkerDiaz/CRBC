/*
 * $Id: DatosFormaDePagoPuntoAgil.java,v 1.3 2006/09/28 19:08:30 programa4 Exp $
 * ===========================================================================
 *
 * Proyecto		: PuntoAgil
 * Paquete		: com.epa.ventas.cr.puntoAgil
 * Programa		: DatosFormaDePagoPuntoAgil.java
 * Creado por	: programa4
 * Creado en 	: 05/06/2006 02:47:40 PM
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
 * $Log: DatosFormaDePagoPuntoAgil.java,v $
 * Revision 1.3  2006/09/28 19:08:30  programa4
 * Actualizacion por requerimientos credicard
 * Se cargan datos de programas de lealtad segun banco seleccionado
 * Modificados mensajes de error para hacerlos mas legibles
 * Verificados titulos para indicar version del punto agil
 *
 * Revision 1.2  2006/06/16 21:32:39  programa4
 * Agregada anulacion de pagos.
 * Actualizacion de datos con datos de los pagos de ventas, tarjeta de cred. epa
 * y abonos.
 * Agregado aviso de cierre si han pasado mas de 24 horas del ultimo cierre.
 * Agregado numServicio para poder referir a abonos
 * Agregadas consultas de puntos
 *
 * Revision 1.1  2006/06/10 02:11:31  programa4
 * Objetos dto para almacenar datos de tarjetas, pagos, respuestas, y archivos maestros
 *
 *
 * ===========================================================================
 */
/**
 * Clase DatosFormaDePagoPuntoAgil
 *
 * @author programa4 - $Author: programa4 $
 * @version $Revision: 1.3 $ - $Date: 2006/09/28 19:08:30 $
 * @since 05/06/2006
 */
package com.epa.ventas.cr.puntoAgil;

import com.becoblohm.cr.extensiones.impl.manejarpago.FormaDePago;

/**
 * @author programa4 - $Author: programa4 $
 * @version $Revision: 1.3 $ - $Date: 2006/09/28 19:08:30 $
 * @since 05/06/2006
 */
public class DatosFormaDePagoPuntoAgil extends FormaDePago {

	private final FormaDePago pago;

	private String tipoTarjetaPuntoAgil;

	private boolean permitePuntoAgil = false;

	private boolean requiereLecturaBanda = false;

	private boolean imprimirVoucher = false;

	private boolean indicarPlan = false;

	private boolean indicarCuentaEspecial = false;

	private boolean indicarTipoCuenta = false;

	private int longitudCodigoSeguridad = -1;

	/**
	 * @param _pago
	 */
	public DatosFormaDePagoPuntoAgil(FormaDePago _pago) {
		super();
		this.setCodigo(_pago.getCodigo());
		this.pago = _pago;
	}

	DatosFormaDePagoPuntoAgil(String codFDP, int tipoF, String nomb,
			String codBanco, boolean indicarBco, boolean indNumDoc,
			boolean indNumCta, boolean indNumConf, boolean indNumRef,
			boolean indCedTit, boolean validarSaldoCte, boolean permiteVto,
			double mtoMin, double mtoMax, double mtoCom, boolean entParcial,
			boolean reqAutorizacion, String _tipoTarjetaPuntoAgil,
			boolean _permitePuntoAgil, boolean _requiereLecturaBanda,
			boolean _imprimirVoucher, boolean _indicarPlan,
			boolean _indicarCuentaEspecial, boolean _indicarTipoCuenta,
			int _longitudCodigoSeguridad) {
		super();
		this.pago = new FormaDePago(
			codFDP, tipoF, nomb, codBanco, indicarBco, indNumDoc, indNumCta,
			indNumConf, indNumRef, indCedTit, validarSaldoCte, permiteVto,
			mtoMin, mtoMax, mtoCom, entParcial, reqAutorizacion);
		this.tipoTarjetaPuntoAgil = _tipoTarjetaPuntoAgil;
		this.permitePuntoAgil = _permitePuntoAgil;
		this.requiereLecturaBanda = _requiereLecturaBanda;
		this.imprimirVoucher = _imprimirVoucher;
		this.indicarPlan = _indicarPlan;
		this.indicarCuentaEspecial = _indicarCuentaEspecial;
		this.indicarTipoCuenta = _indicarTipoCuenta;
		this.longitudCodigoSeguridad = _longitudCodigoSeguridad;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.becoblohm.cr.extensiones.impl.manejarpago.FormaDePago#equals(java.lang.Object)
	 */
	public boolean equals(Object _obj) {
		return this.pago.equals(_obj);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.becoblohm.cr.extensiones.impl.manejarpago.FormaDePago#getCodigo()
	 */
	public String getCodigo() {
		return this.pago.getCodigo();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.becoblohm.cr.extensiones.impl.manejarpago.FormaDePago#getCodigoBanco()
	 */
	public String getCodigoBanco() {
		return this.pago.getCodigoBanco();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.becoblohm.cr.extensiones.impl.manejarpago.FormaDePago#getMontoComision()
	 */
	public double getMontoComision() {
		return this.pago.getMontoComision();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.becoblohm.cr.extensiones.impl.manejarpago.FormaDePago#getMontoMaximo()
	 */
	public double getMontoMaximo() {
		return this.pago.getMontoMaximo();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.becoblohm.cr.extensiones.impl.manejarpago.FormaDePago#getMontoMinimo()
	 */
	public double getMontoMinimo() {
		return this.pago.getMontoMinimo();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.becoblohm.cr.extensiones.impl.manejarpago.FormaDePago#getNombre()
	 */
	public String getNombre() {
		return this.pago.getNombre();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.becoblohm.cr.extensiones.impl.manejarpago.FormaDePago#getTipo()
	 */
	public int getTipo() {
		return this.pago.getTipo();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.becoblohm.cr.extensiones.impl.manejarpago.FormaDePago#hashCode()
	 */
	public int hashCode() {
		return this.pago.hashCode();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.becoblohm.cr.extensiones.impl.manejarpago.FormaDePago#isIncrementaEntregaParcial()
	 */
	public boolean isIncrementaEntregaParcial() {
		return this.pago.isIncrementaEntregaParcial();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.becoblohm.cr.extensiones.impl.manejarpago.FormaDePago#isIndicarBanco()
	 */
	public boolean isIndicarBanco() {
		return this.pago.isIndicarBanco();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.becoblohm.cr.extensiones.impl.manejarpago.FormaDePago#isIndicarCedulaTitular()
	 */
	public boolean isIndicarCedulaTitular() {
		return this.pago.isIndicarCedulaTitular();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.becoblohm.cr.extensiones.impl.manejarpago.FormaDePago#isIndicarNumConformacion()
	 */
	public boolean isIndicarNumConformacion() {
		boolean indNumConf = this.pago.isIndicarNumConformacion();
		if (indNumConf && this.isPermitePuntoAgil()) {
			indNumConf = false;
		}
		return indNumConf;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.becoblohm.cr.extensiones.impl.manejarpago.FormaDePago#isIndicarNumCuenta()
	 */
	public boolean isIndicarNumCuenta() {
		return this.pago.isIndicarNumCuenta();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.becoblohm.cr.extensiones.impl.manejarpago.FormaDePago#isIndicarNumDocumento()
	 */
	public boolean isIndicarNumDocumento() {
		return this.pago.isIndicarNumDocumento();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.becoblohm.cr.extensiones.impl.manejarpago.FormaDePago#isIndicarNumReferencia()
	 */
	public boolean isIndicarNumReferencia() {
		return this.pago.isIndicarNumReferencia();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.becoblohm.cr.extensiones.impl.manejarpago.FormaDePago#isPermiteVuelto()
	 */
	public boolean isPermiteVuelto() {
		return this.pago.isPermiteVuelto();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.becoblohm.cr.extensiones.impl.manejarpago.FormaDePago#isRequiereAutorizacion()
	 */
	public boolean isRequiereAutorizacion() {
		return this.pago.isRequiereAutorizacion();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.becoblohm.cr.extensiones.impl.manejarpago.FormaDePago#isValidarSaldoCliente()
	 */
	public boolean isValidarSaldoCliente() {
		return this.pago.isValidarSaldoCliente();
	}

	/**
	 * @return Returns el imprimirVoucher.
	 */
	public boolean isImprimirVoucher() {
		return this.imprimirVoucher;
	}

	/**
	 * @param _imprimirVoucher
	 *            El imprimirVoucher a asignar.
	 */
	public void setImprimirVoucher(boolean _imprimirVoucher) {
		this.imprimirVoucher = _imprimirVoucher;
	}

	/**
	 * @return Returns el indicarCuentaEspecial.
	 */
	public boolean isIndicarCuentaEspecial() {
		return this.indicarCuentaEspecial;
	}

	/**
	 * @param _indicarCuentaEspecial
	 *            El indicarCuentaEspecial a asignar.
	 */
	public void setIndicarCuentaEspecial(boolean _indicarCuentaEspecial) {
		this.indicarCuentaEspecial = _indicarCuentaEspecial;
	}

	/**
	 * @return Returns el indicarPlan.
	 */
	public boolean isIndicarPlan() {
		return this.indicarPlan;
	}

	/**
	 * @param _indicarPlan
	 *            El indicarPlan a asignar.
	 */
	public void setIndicarPlan(boolean _indicarPlan) {
		this.indicarPlan = _indicarPlan;
	}

	/**
	 * @return Returns el indicarTipoCuenta.
	 */
	public boolean isIndicarTipoCuenta() {
		return this.indicarTipoCuenta;
	}

	/**
	 * @param _indicarTipoCuenta
	 *            El indicarTipoCuenta a asignar.
	 */
	public void setIndicarTipoCuenta(boolean _indicarTipoCuenta) {
		this.indicarTipoCuenta = _indicarTipoCuenta;
	}

	/**
	 * @return Returns el permitePuntoAgil.
	 */
	public boolean isPermitePuntoAgil() {
		return this.permitePuntoAgil;
	}

	/**
	 * @param _permitePuntoAgil
	 *            El permitePuntoAgil a asignar.
	 */
	public void setPermitePuntoAgil(boolean _permitePuntoAgil) {
		this.permitePuntoAgil = _permitePuntoAgil;
	}

	/**
	 * @return Returns el requiereLecturaBanda.
	 */
	public boolean isRequiereLecturaBanda() {
		return this.requiereLecturaBanda;
	}

	/**
	 * @param _requiereLecturaBanda
	 *            El requiereLecturaBanda a asignar.
	 */
	public void setRequiereLecturaBanda(boolean _requiereLecturaBanda) {
		this.requiereLecturaBanda = _requiereLecturaBanda;
	}

	/**
	 * @return Returns el pago.
	 */
	public FormaDePago getPago() {
		return this.pago;
	}

	/**
	 * @return Returns el tipoTarjetaPuntoAgil.
	 */
	public String getTipoTarjetaPuntoAgil() {
		return this.tipoTarjetaPuntoAgil;
	}

	/**
	 * @param _tipoTarjetaPuntoAgil
	 *            El tipoTarjetaPuntoAgil a asignar.
	 */
	public void setTipoTarjetaPuntoAgil(String _tipoTarjetaPuntoAgil) {
		this.tipoTarjetaPuntoAgil = _tipoTarjetaPuntoAgil;
	}

	/**
	 * @return Returns el longitudCodigoSeguridad.
	 */
	public int getLongitudCodigoSeguridad() {
		return this.longitudCodigoSeguridad;
	}

	/**
	 * @param _longitudCodigoSeguridad El longitudCodigoSeguridad a asignar.
	 */
	public void setLongitudCodigoSeguridad(int _longitudCodigoSeguridad) {
		this.longitudCodigoSeguridad = _longitudCodigoSeguridad;
	}

}
