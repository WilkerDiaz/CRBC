/*
 * $Id: DatosExternosPuntoAgil.java,v 1.3 2006/07/05 15:25:49 programa4 Exp $
 * ===========================================================================
 *
 * Proyecto		: PuntoAgil
 * Paquete		: com.epa.ventas.cr.puntoAgil
 * Programa		: DatosExternosPuntoAgil.java
 * Creado por	: programa4
 * Creado en 	: 08/06/2006 06:17:58 PM
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
 * $Log: DatosExternosPuntoAgil.java,v $
 * Revision 1.3  2006/07/05 15:25:49  programa4
 * Agregado soporte a provimillas y actualizado anulacion
 *
 * Revision 1.2  2006/06/16 21:32:47  programa4
 * Agregada anulacion de pagos.
 * Actualizacion de datos con datos de los pagos de ventas, tarjeta de cred. epa
 * y abonos.
 * Agregado aviso de cierre si han pasado mas de 24 horas del ultimo cierre.
 * Agregado numServicio para poder referir a abonos
 * Agregadas consultas de puntos
 *
 * Revision 1.1  2006/06/10 02:11:32  programa4
 * Objetos dto para almacenar datos de tarjetas, pagos, respuestas, y archivos maestros
 *
 *
 * ===========================================================================
 */
/**
 * Clase DatosExternosPuntoAgil
 *
 * @author programa4 - $Author: programa4 $
 * @version $Revision: 1.3 $ - $Date: 2006/07/05 15:25:49 $
 * @since 08/06/2006
 */
package com.epa.ventas.cr.puntoAgil;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

/**
 * @author programa4 - $Author: programa4 $
 * @version $Revision: 1.3 $ - $Date: 2006/07/05 15:25:49 $
 * @since 08/06/2006
 */
public class DatosExternosPuntoAgil {

    private String cedula;

    private String monto;

    private String tipoCuenta;

    private String codSeguridad;

    private String ctasEspeciales;

    private String nroCheque;

    private String nroCuenta;

    private String nroTransaccion;

    private String vtId;

    private String authId;

    private String plan;

    private String porcentaje;

    private final Integer tipoOperacion;

    private final Integer tipoProceso;

    /**
     * Constructor donde se debe indicar Tipo de Operacion
     *
     * @param _tipoProceso
     *
     * @param _tipoOperacion
     */
    public DatosExternosPuntoAgil(Integer _tipoProceso, Integer _tipoOperacion) {
        this.tipoProceso = _tipoProceso;
        this.tipoOperacion = _tipoOperacion;
    }

    /**
     * @return Returns el tipoProceso.
     */
    public Integer getTipoProceso() {
        return this.tipoProceso;
    }

    /**
     * @return Returns el tipoOperacion.
     */
    public Integer getTipoOperacion() {
        return this.tipoOperacion;
    }

    /**
     * @return Returns el authId.
     */
    public String getAuthId() {
        return StringUtils.trimToEmpty(this.authId);
    }

    /**
     * @param _authId
     *            El authId a asignar.
     */
    public void setAuthId(String _authId) {
        this.authId = _authId;
    }

    /**
     * @return Returns el cedula.
     */
    public String getCedula() {
        return StringUtils.trimToEmpty(this.cedula);
    }

    /**
     * @param _cedula
     *            El cedula a asignar.
     */
    public void setCedula(String _cedula) {
        this.cedula = _cedula;
    }

    /**
     * @return Returns el codSeguridad.
     */
    public String getCodSeguridad() {
        return StringUtils.trimToEmpty(this.codSeguridad);
    }

    /**
     * @param _codSeguridad
     *            El codSeguridad a asignar.
     */
    public void setCodSeguridad(String _codSeguridad) {
        this.codSeguridad = _codSeguridad;
    }

    /**
     * @return Returns el ctasEspeciales.
     */
    public String getCtasEspeciales() {
        return StringUtils.trimToEmpty(this.ctasEspeciales);
    }

    /**
     * @param _ctasEspeciales
     *            El ctasEspeciales a asignar.
     */
    public void setCtasEspeciales(String _ctasEspeciales) {
        this.ctasEspeciales = _ctasEspeciales;
    }

    /**
     * @return Returns el monto.
     */
    public String getMonto() {
        return StringUtils.trimToEmpty(this.monto);
    }

    /**
     * @param _monto
     *            El monto a asignar.
     */
    public void setMonto(String _monto) {
        this.monto = _monto;
    }

    /**
     * @return Returns el nroCheque.
     */
    public String getNroCheque() {
        return StringUtils.trimToEmpty(this.nroCheque);
    }

    /**
     * @param _nroCheque
     *            El nroCheque a asignar.
     */
    public void setNroCheque(String _nroCheque) {
        this.nroCheque = _nroCheque;
    }

    /**
     * @return Returns el nroCuenta.
     */
    public String getNroCuenta() {
        return StringUtils.trimToEmpty(this.nroCuenta);
    }

    /**
     * @param _nroCuenta
     *            El nroCuenta a asignar.
     */
    public void setNroCuenta(String _nroCuenta) {
        this.nroCuenta = _nroCuenta;
    }

    /**
     * @return Returns el nroTransaccion.
     */
    public String getNroTransaccion() {
        return StringUtils.trimToEmpty(this.nroTransaccion);
    }

    /**
     * @param _nroTransaccion
     *            El nroTransaccion a asignar.
     */
    public void setNroTransaccion(String _nroTransaccion) {
        this.nroTransaccion = _nroTransaccion;
    }

    /**
     * @return Returns el plan.
     */
    public String getPlan() {
        return StringUtils.trimToEmpty(this.plan);
    }

    /**
     * @param _plan
     *            El plan a asignar.
     */
    public void setPlan(String _plan) {
        this.plan = _plan;
    }

    /**
     * @return Returns el tipoCuenta.
     */
    public String getTipoCuenta() {
        return StringUtils.trimToEmpty(this.tipoCuenta);
    }

    /**
     * @param _tipoCuenta
     *            El tipoCuenta a asignar.
     */
    public void setTipoCuenta(String _tipoCuenta) {
        this.tipoCuenta = _tipoCuenta;
    }

    /**
     * @return Returns el vtId.
     */
    public String getVtId() {
        return StringUtils.trimToEmpty(this.vtId);
    }

    /**
     * @param _vtId
     *            El vtId a asignar.
     */
    public void setVtId(String _vtId) {
        this.vtId = _vtId;
    }

    /**
     * @return Returns el porcentaje.
     */
    public String getPorcentaje() {
        return this.porcentaje;
    }

    /**
     * @param _porcentaje
     *            El porcentaje a asignar.
     */
    public void setPorcentaje(String _porcentaje) {
        this.porcentaje = _porcentaje;
    }

    /**
     * Retorna todos estos parametros como un array listo para pasarlo al metodo
     * del vPos.
     *
     * @return String[] array con parametros.
     */
    public String[] toArray() {
        String[] params;
        if (this.porcentaje == null) {
            String[] p = { this.getCedula(), this.getMonto(),
                    this.getTipoCuenta(), this.getCodSeguridad(),
                    this.getCtasEspeciales(), this.getNroCheque(),
                    this.getNroCuenta(), this.getNroTransaccion(),
                    this.getVtId(), this.getAuthId(), this.getPlan() };
            params = p;
        } else {
            String[] p = { this.getCedula(), this.getMonto(),
                    this.getTipoCuenta(), this.getCodSeguridad(),
                    this.getCtasEspeciales(), this.getNroCheque(),
                    this.getNroCuenta(), this.getNroTransaccion(),
                    this.getVtId(), this.getAuthId(), this.getPlan() };
            params = p;
        }
        return params;

    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return ArrayUtils.toString(toArray());
    }

}
