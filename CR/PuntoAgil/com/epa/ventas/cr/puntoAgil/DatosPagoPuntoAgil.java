/*
 * $Id: DatosPagoPuntoAgil.java,v 1.3 2006/09/28 19:08:29 programa4 Exp $
 * ===========================================================================
 *
 * Proyecto		: PuntoAgil
 * Paquete		: com.epa.ventas.cr.puntoAgil
 * Programa		: DatosPagoPuntoAgil.java
 * Creado por	: programa4
 * Creado en 	: 31/05/2006 02:51:22 PM
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
 * $Log: DatosPagoPuntoAgil.java,v $
 * Revision 1.3  2006/09/28 19:08:29  programa4
 * Actualizacion por requerimientos credicard
 * Se cargan datos de programas de lealtad segun banco seleccionado
 * Modificados mensajes de error para hacerlos mas legibles
 * Verificados titulos para indicar version del punto agil
 *
 * Revision 1.2  2006/06/16 21:32:43  programa4
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
 * Clase DatosPagoPuntoAgil
 *
 * @author programa4 - $Author: programa4 $
 * @version $Revision: 1.3 $ - $Date: 2006/09/28 19:08:29 $
 * @since 31/05/2006
 */
package com.epa.ventas.cr.puntoAgil;

import java.util.LinkedHashSet;
import java.util.Set;

import com.becoblohm.cr.extensiones.impl.manejarpago.Pago;
import com.becoblohm.cr.utiles.Control;

/**
 * @author programa4 - $Author: programa4 $
 * @version $Revision: 1.3 $ - $Date: 2006/09/28 19:08:29 $
 * @since 31/05/2006
 */
public class DatosPagoPuntoAgil extends Pago {

    private final Integer tipoProceso;

    private String tipoCuenta;

    private String codSeguridad;

    private String ctasEspeciales;

    private String plan;

    private DatosTarjetaPuntoAgil datosTarjetaPuntoAgil;

    private final Set<DatosOperacionPuntoAgil> datosOperacionPuntoAgil = new LinkedHashSet<DatosOperacionPuntoAgil>();

    /**
     * @param _fdpPago
     * @param _mto
     * @param _codBco
     * @param _numDoc
     * @param _numCta
     * @param _numConf
     * @param _numRef
     * @param _cedTitular
     * @param _tipoProceso
     */
    public DatosPagoPuntoAgil(DatosFormaDePagoPuntoAgil _fdpPago, double _mto,
            String _codBco, String _numDoc, String _numCta, int _numConf,
            int _numRef, String _cedTitular, int _tipoProceso) {
        super(_fdpPago, _mto, _codBco, _numDoc, _numCta, _numConf, _numRef,
                _cedTitular);
        this.tipoProceso = new Integer(_tipoProceso);
    }

    /**
     * @param _codFPago
     * @param _mto
     * @param _codBco
     * @param _numDoc
     * @param _numCta
     * @param _numConf
     * @param _numRef
     * @param _cedTitular
     * @param _tipoProceso
     */
    public DatosPagoPuntoAgil(String _codFPago, double _mto, String _codBco,
            String _numDoc, String _numCta, int _numConf, int _numRef,
            String _cedTitular, int _tipoProceso) {
        super(_codFPago, _mto, _codBco, _numDoc, _numCta, _numConf, _numRef,
                _cedTitular);
        this.tipoProceso = new Integer(_tipoProceso);
    }

    /**
     * @param pago
     * @param _tipoProceso
     */
    public DatosPagoPuntoAgil(Pago pago, Integer _tipoProceso) {
        super(pago.getFormaPago(), pago.getMonto(), pago.getCodBanco(), pago
                .getNumDocumento(), pago.getNumCuenta(), pago
                .getNumConformacion(), pago.getNumReferencia(), pago
                .getCedTitular());
        this.tipoProceso = _tipoProceso;
    }

    /**
     * @return Returns el codSeguridad.
     */
    public String getCodSeguridad() {
        return this.codSeguridad;
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
        return this.ctasEspeciales;
    }

    /**
     * @param _ctasEspeciales
     *            El ctasEspeciales a asignar.
     */
    public void setCtasEspeciales(String _ctasEspeciales) {
        this.ctasEspeciales = _ctasEspeciales;
    }

    /**
     * @return Returns el plan.
     */
    public String getPlan() {
        return this.plan;
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
        return this.tipoCuenta;
    }

    /**
     * @param _tipoCuenta
     *            El tipoCuenta a asignar.
     */
    public void setTipoCuenta(String _tipoCuenta) {
        this.tipoCuenta = _tipoCuenta;
    }

    /**
     * @return DatosFormaDePagoPuntoAgil
     */
    public DatosFormaDePagoPuntoAgil getDatosFormaDePagoPuntoAgil() {
        return (DatosFormaDePagoPuntoAgil) this.getFormaPago();
    }

    /**
     * Retorna los datos de este pago, como un objeto DatosExternosPuntoAgil,
     * para ser procesado
     *
     * @return DatosExternosPuntoAgil
     */
    public DatosExternosPuntoAgil toDatosExternosPuntoAgil() {
        final DatosFormaDePagoPuntoAgil fdp = this
                .getDatosFormaDePagoPuntoAgil();
        String cedula = this.getCedTitular();
        String monto = Control.formatearMonto(this.getMonto());
        String _tipoCuenta = fdp.isIndicarTipoCuenta() ? this.getTipoCuenta()
                : null;
        String _codSeguridad = fdp.getLongitudCodigoSeguridad() > 0 ? this.getCodSeguridad()
                : null;
        //TOMA LA CUENTA ESPECIAL QUE DEBE SER '0' EN LOS CASOS DE TDC. EPA
        String _ctasEspeciales = fdp.isIndicarCuentaEspecial()
                || fdp.isIndicarPlan() ? this.getCtasEspeciales() : null;

        // SOLO EN CASO DE CHEQUES, ES QUE SE PASA NUMERO DE DOCUMENTO
        // Y COMO LA UNICA FORMA DE PAGOS QUE PROCESA REQUIERE NUMERO DE CUENTA
        // ES CHEQUE, POR ESO ES QUE SE VERIFICA SI SE PASA NUMDOCUMENTO VIENDO
        // SI SE REQUIERE NUMERO DE CUENTA
        String nroCheque = fdp.isIndicarNumCuenta() ? this.getNumDocumento()
                : null;
        String nroCuenta = fdp.isIndicarNumCuenta() ? this.getNumCuenta()
                : null;
        String nroTransaccion = null;
        String vtId = null;
        String authId = null;
        String _plan = fdp.isIndicarPlan() ? this.getPlan() : null;

        DatosExternosPuntoAgil datosExternosPuntoAgil = new DatosExternosPuntoAgil(
                this.tipoProceso, Constantes.TIPO_OPERACION_PAGO);
        datosExternosPuntoAgil.setCedula(cedula);
        datosExternosPuntoAgil.setMonto(monto);
        datosExternosPuntoAgil.setTipoCuenta(_tipoCuenta);
        datosExternosPuntoAgil.setCodSeguridad(_codSeguridad);
        datosExternosPuntoAgil.setCtasEspeciales(_ctasEspeciales);

        datosExternosPuntoAgil.setNroCheque(nroCheque);
        datosExternosPuntoAgil.setNroCuenta(nroCuenta);
        datosExternosPuntoAgil.setNroTransaccion(nroTransaccion);
        datosExternosPuntoAgil.setVtId(vtId);
        datosExternosPuntoAgil.setAuthId(authId);
        datosExternosPuntoAgil.setPlan(_plan);
        return datosExternosPuntoAgil;
    }

    /**
     * @param _agil
     *            Datos capturados de la tarjeta.
     */
    public void setDatosTarjetaPuntoAgil(DatosTarjetaPuntoAgil _agil) {
        this.datosTarjetaPuntoAgil = _agil;
    }

    /**
     * @return Returns el datosTarjetaPuntoAgil.
     */
    public DatosTarjetaPuntoAgil getDatosTarjetaPuntoAgil() {
        return this.datosTarjetaPuntoAgil;
    }

    /**
     * @return Returns el datosOperacionPuntoAgil.
     */
    /*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Set'
	* Fecha: agosto 2011
	*/
    public Set<DatosOperacionPuntoAgil> getDatosOperacionPuntoAgil() {
        return this.datosOperacionPuntoAgil;
    }

    /**
     * @return Returns el datosOperacionPuntoAgil.
     */
    public DatosOperacionPuntoAgil[] getDatosOperacionPuntoAgilArray() {
        return (DatosOperacionPuntoAgil[]) this.datosOperacionPuntoAgil
                .toArray(new DatosOperacionPuntoAgil[this.datosOperacionPuntoAgil
                        .size()]);
    }

    /**
     * @param _datosOperacionPuntoAgil
     *            El datosOperacionPuntoAgil a asignar.
     * @return si pudo ser agregado
     */
    public boolean addDatosOperacionPuntoAgil(
            DatosOperacionPuntoAgil _datosOperacionPuntoAgil) {
        return this.datosOperacionPuntoAgil.add(_datosOperacionPuntoAgil);
    }

    /**
     * Limpia la coleccion de DatosOperacionPuntoAgil
     */
    public void clearDatosOperacionPuntoAgil() {
        this.datosOperacionPuntoAgil.clear();
    }

    /**
     * @return Cantidad de registros de Datos Operacion Punto Agil
     */
    public int sizeDatosOperacionPuntoAgil() {
        return this.datosOperacionPuntoAgil.size();
    }

    /**
     * @return Returns el tipoProceso.
     */
    public Integer getTipoProceso() {
        return this.tipoProceso;
    }

}
