/*
 * $Id: DatosOperacionPuntoAgil.java,v 1.8 2006/08/25 18:17:36 programa4 Exp $
 * ===========================================================================
 * Proyecto : PuntoAgil Paquete : com.epa.ventas.cr.puntoAgil Programa :
 * DatosOperacionPuntoAgil.java Creado por : programa4 Creado en : 09/06/2006
 * 10:57:29 AM (C) Copyright 2006 Todos los Derechos Reservados
 * ---------------------------------------------------------------------------
 * Información de Repositorio:
 * ---------------------------------------------------------------------------
 * Rama de Desarrollo : $Name:  $ Bloqueado por : $Locker:  $ Estado de Revisión :
 * $State: Exp $
 * ---------------------------------------------------------------------------
 * Actualizaciones:
 * ---------------------------------------------------------------------------
 * $Log: DatosOperacionPuntoAgil.java,v $
 * Revision 1.8  2006/08/25 18:17:36  programa4
 * Correccion en anulacion de cheque (para que no solicite lectura banda)
 * Y convertido PuntoAgilSubSistema en singleton en lugar de metodos estaticos
 *
 * Revision 1.7  2006/07/21 18:44:14  programa4
 * Corregido bug que al haber excepcion en el guardado de base de datos no imprimia recibo
 * Movido textos a constantes
 * revisado toString de DatosOperacionPuntoAgil
 * formateo de codigo
 * Revision 1.6 2006/07/13 16:07:26
 * programa4 Correcion de mensaje de "Punto Agil" a "El Punto Agil" Manejo de
 * autorizacion si no se utiliza punto agil No se solicita autorizacion si hubo
 * error VA (Falla de comunicacion con merchant), o se trata de cheque Asignados
 * mensajes a mostrar mientras se comunica con pinpad Revision 1.5 2006/07/05
 * 15:25:46 programa4 Agregado soporte a provimillas y actualizado anulacion
 * Revision 1.4 2006/06/27 15:33:54 programa4 Agregada revision de
 * regactualizado para pasar de S --> N y que la caja sepa que hay que
 * actualizar servidor Revision 1.3 2006/06/26 16:05:11 programa4 Renombrados
 * algunos campos Revision 1.2 2006/06/16 21:32:42 programa4 Agregada anulacion
 * de pagos. Actualizacion de datos con datos de los pagos de ventas, tarjeta de
 * cred. epa y abonos. Agregado aviso de cierre si han pasado mas de 24 horas
 * del ultimo cierre. Agregado numServicio para poder referir a abonos Agregadas
 * consultas de puntos Revision 1.1 2006/06/10 02:11:28 programa4 Objetos dto
 * para almacenar datos de tarjetas, pagos, respuestas, y archivos maestros
 * ===========================================================================
 */
/**
 * Clase DatosOperacionPuntoAgil
 *
 * @author programa4 - $Author: programa4 $
 * @version $Revision: 1.8 $ - $Date: 2006/08/25 18:17:36 $
 * @since 09/06/2006
 */
package com.epa.ventas.cr.puntoAgil;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.becoblohm.cr.excepciones.PagoExcepcion;
import com.becoblohm.cr.manejadorsesion.Sesion;
import com.becoblohm.cr.utiles.Control;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * @author programa4 - $Author: programa4 $
 * @version $Revision: 1.8 $ - $Date: 2006/08/25 18:17:36 $
 * @since 09/06/2006
 */
public class DatosOperacionPuntoAgil {

	private final Integer tipoOperacion;

	private final Integer tipoProceso;

	private String codCajero;

	private String codformadepago;

	private Integer correlativoPagoProceso;

	private String de_cedulaCliente;

	private String de_ctaEspecial;

	private BigDecimal de_monto;

	private BigDecimal de_nroCheque;

	private BigDecimal de_nroCuenta;

	private Integer de_numSeqAnular;

	private String de_planCreditoEPA;

	private String de_tipoCuenta;

	private BigDecimal de_porcentajeProvimillas;

	private String do_codRespuesta;

	private String do_mensajeError;

	private String do_mensajeRespuesta;

	private String do_nombreAutorizador;

	private String do_nombreVoucher;

	private String do_numeroAutorizacion;

	private String dt_nombreCliente;

	private String dt_numeroTarjeta;

	private String dt_TipoTarjeta;

	private Date fecha;

	private Time horaFinaliza;

	private Time horaInicia;

	private Integer numcaja;

	private Integer numSeq;

	private Integer numtienda;

	private Integer numproceso;

	private Integer numservicio;

	private String status;

	private String vtId;

	private String regActualizado;

	/**
	 * Constructor por defecto solo se inicializan los valores que indican el
	 * tipo de operacion.
	 *
	 * @param tipProceso
	 * @param tipOperacion
	 */
	public DatosOperacionPuntoAgil(Integer tipProceso, Integer tipOperacion) {
		this.tipoProceso = tipProceso;
		this.tipoOperacion = tipOperacion;
	}

	/**
	 * Genera un objeto DatosOperacionPuntoAgil a con los datos de estos otros
	 * objetos.
	 *
	 * @param datosPagoPuntoAgil
	 * @param datosResultadosPuntoAgil
	 * @throws PagoExcepcion
	 *             si datosExternos es null.
	 */
	public DatosOperacionPuntoAgil(DatosPagoPuntoAgil datosPagoPuntoAgil,
			DatosResultadosPuntoAgil datosResultadosPuntoAgil)
			throws PagoExcepcion {
		this(
			datosPagoPuntoAgil.toDatosExternosPuntoAgil(),
			datosResultadosPuntoAgil);
		DatosFormaDePagoPuntoAgil fdp = datosPagoPuntoAgil
			.getDatosFormaDePagoPuntoAgil();
		this.setCodformadepago(fdp.getCodigo());
		this.setDe_monto(datosPagoPuntoAgil.getMonto());
		DatosTarjetaPuntoAgil datosTarjetaPuntoAgil = datosPagoPuntoAgil
			.getDatosTarjetaPuntoAgil();
		if (datosTarjetaPuntoAgil != null) {
			this.setDt_numeroTarjeta(datosTarjetaPuntoAgil
				.getNumeroTarjetaFormateada());
			this.setDt_nombreCliente(datosTarjetaPuntoAgil.getNombreCliente());
			this.setDt_TipoTarjeta(datosTarjetaPuntoAgil.getTipoTarjeta());
		}
	}

	/**
	 * @param datosExternosPuntoAgil
	 * @param datosResultadosPuntoAgil
	 * @throws PagoExcepcion
	 */
	public DatosOperacionPuntoAgil(
			DatosExternosPuntoAgil datosExternosPuntoAgil,
			DatosResultadosPuntoAgil datosResultadosPuntoAgil)
			throws PagoExcepcion {
		this.setNumtienda(Sesion.getTienda().getNumero());
		this.setFecha(Sesion.getFechaSistema());
		this.setNumcaja(Sesion.getNumCaja());
		this.setVtId(PuntoAgilSubSistema.getInstance()
			.getVPos()
			.getSistConfiguracion()
			.getVtId());
		this.setCodCajero(Sesion.getUsuarioActivo().getNumFicha());
		if (datosExternosPuntoAgil != null) {
			if (StringUtils.isNotEmpty(datosExternosPuntoAgil.getCedula())) {
				this.setDe_cedulaCliente(datosExternosPuntoAgil.getCedula());
			}
			if (StringUtils.isNotEmpty(datosExternosPuntoAgil
				.getCtasEspeciales())) {
				this.setDe_ctaEspecial(datosExternosPuntoAgil
					.getCtasEspeciales());
			}
			if (StringUtils.isNotEmpty(datosExternosPuntoAgil.getTipoCuenta())) {
				this.setDe_tipoCuenta(datosExternosPuntoAgil.getTipoCuenta());
			}
			if (StringUtils.isNotEmpty(datosExternosPuntoAgil.getNroCuenta())) {
				this.setDe_nroCuenta(new BigDecimal(datosExternosPuntoAgil
					.getNroCuenta()));
			}
			if (StringUtils.isNotEmpty(datosExternosPuntoAgil.getNroCheque())) {
				this.setDe_nroCheque(new BigDecimal(datosExternosPuntoAgil
					.getNroCheque()));
			}
			if (StringUtils.isNotEmpty(datosExternosPuntoAgil
				.getNroTransaccion())) {
				this.setDe_numSeqAnular(Integer.valueOf(datosExternosPuntoAgil
					.getNroTransaccion()));
			}
			if (StringUtils.isNotEmpty(datosExternosPuntoAgil.getPlan())) {
				this.setDe_planCreditoEPA(datosExternosPuntoAgil.getPlan());
			}
			if (StringUtils.isNotEmpty(datosExternosPuntoAgil.getPorcentaje())) {
				this.setDe_porcentajeProvimillas(new BigDecimal(
					datosExternosPuntoAgil.getPorcentaje()));
			}

			this.tipoOperacion = datosExternosPuntoAgil.getTipoOperacion();
			this.tipoProceso = datosExternosPuntoAgil.getTipoProceso();
		} else {
			throw new PagoExcepcion(
				"DATOS PARA DE PARAMETROS DE EL PUNTO AGIL INCOMPLETOS");

		}
		if (datosResultadosPuntoAgil != null) {
			this.setNumSeq(datosResultadosPuntoAgil.getNumSeq());
			this.setHoraFinaliza(datosResultadosPuntoAgil.getHoraFinaliza());
			this.setHoraInicia(datosResultadosPuntoAgil.getHoraInicia());
			this.setDo_codRespuesta(datosResultadosPuntoAgil.getCodRespuesta());
			this.setDo_mensajeError(datosResultadosPuntoAgil.getMensajeError());
			this.setDo_mensajeRespuesta(datosResultadosPuntoAgil
				.getMensajeRespuesta());
			this.setDo_nombreAutorizador(datosResultadosPuntoAgil
				.getNombreAutorizador());
			this.setDo_numeroAutorizacion(datosResultadosPuntoAgil
				.getNumeroAutorizacion());
			this.setDo_nombreVoucher(datosResultadosPuntoAgil
				.getNombreVoucher());
		} else {
			throw new PagoExcepcion(
				"DATOS PARA GUARDAR RESULTADOS DE EL PUNTO AGIL INCOMPLETOS");
		}
		this.setStatus(Constantes.OPERACION_VIGENTE);
		this.setRegActualizado(Constantes.REGISTRO_ACTUALIZADO_CREADO);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(final Object other) {
		if (!(other instanceof DatosOperacionPuntoAgil))
			return false;
		DatosOperacionPuntoAgil castOther = (DatosOperacionPuntoAgil) other;
		return new EqualsBuilder()
			.append(this.numtienda, castOther.numtienda)
			.append(this.fecha, castOther.fecha)
			.append(this.numcaja, castOther.numcaja)
			.append(this.vtId, castOther.vtId)
			.append(this.numSeq, castOther.numSeq)
			.isEquals();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#h ashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-2025012491, 407373423).append(
			this.numtienda).append(this.fecha).append(this.numcaja).append(
			this.vtId).append(this.numSeq).toHashCode();
	}

	/**
	 * @return Returns el codCajero.
	 */
	public String getCodCajero() {
		return this.codCajero;
	}

	/**
	 * @return Returns el codformadepago.
	 */
	public String getCodformadepago() {
		return this.codformadepago;
	}

	/**
	 * @return Returns el correlativoPagoProceso.
	 */
	public Integer getCorrelativoPagoProceso() {
		return this.correlativoPagoProceso;
	}

	/**
	 * @return Returns el de_cedulaCliente.
	 */
	public String getDe_cedulaCliente() {
		return this.de_cedulaCliente;
	}

	/**
	 * @return Returns el de_ctaEspecial.
	 */
	public String getDe_ctaEspecial() {
		return this.de_ctaEspecial;
	}

	/**
	 * @return Returns el de_monto.
	 */
	public BigDecimal getDe_monto() {
		return this.de_monto;
	}

	/**
	 * @return Returns el de_nroCheque.
	 */
	public BigDecimal getDe_nroCheque() {
		return this.de_nroCheque;
	}

	/**
	 * @return Returns el de_nroCuenta.
	 */
	public BigDecimal getDe_nroCuenta() {
		return this.de_nroCuenta;
	}

	/**
	 * @return Returns el de_numSeqAnular.
	 */
	public Integer getDe_numSeqAnular() {
		return this.de_numSeqAnular;
	}

	/**
	 * @return Returns el de_planCreditoEPA.
	 */
	public String getDe_planCreditoEPA() {
		return this.de_planCreditoEPA;
	}

	/**
	 * @return Returns el de_tipoCuenta.
	 */
	public String getDe_tipoCuenta() {
		return this.de_tipoCuenta;
	}

	/**
	 * @return Returns el do_codRespuesta.
	 */
	public String getDo_codRespuesta() {
		return this.do_codRespuesta;
	}

	/**
	 * @return Returns el do_mensajeError.
	 */
	public String getDo_mensajeError() {
		return this.do_mensajeError;
	}

	/**
	 * @return Returns el do_mensajeRespuesta.
	 */
	public String getDo_mensajeRespuesta() {
		return this.do_mensajeRespuesta;
	}

	/**
	 * @return Returns el do_nombreAutorizador.
	 */
	public String getDo_nombreAutorizador() {
		return this.do_nombreAutorizador;
	}

	/**
	 * @return Returns el do_nombreVoucher.
	 */
	public String getDo_nombreVoucher() {
		return this.do_nombreVoucher;
	}

	/**
	 * @return Returns el do_numeroAutorizacion.
	 */
	public String getDo_numeroAutorizacion() {
		return this.do_numeroAutorizacion;
	}

	/**
	 * @return Returns el dt_nombreCliente.
	 */
	public String getDt_nombreCliente() {
		return this.dt_nombreCliente;
	}

	/**
	 * @return Returns el dt_numeroTarjeta.
	 */
	public String getDt_numeroTarjeta() {
		return this.dt_numeroTarjeta;
	}

	/**
	 * @return Returns el dt_TipoTarjeta.
	 */
	public String getDt_TipoTarjeta() {
		return this.dt_TipoTarjeta;
	}

	/**
	 * @return Returns el fecha.
	 */
	public Date getFecha() {
		return this.fecha;
	}

	/**
	 * @return Returns el horaFinaliza.
	 */
	public Time getHoraFinaliza() {
		return this.horaFinaliza;
	}

	/**
	 * @return Returns el horaInicia.
	 */
	public Time getHoraInicia() {
		return this.horaInicia;
	}

	/**
	 * @return Returns el numcaja.
	 */
	public Integer getNumcaja() {
		return this.numcaja;
	}

	/**
	 * @return Returns el numSeq.
	 */
	public Integer getNumSeq() {
		return this.numSeq;
	}

	/**
	 * @return Returns el numtienda.
	 */
	public Integer getNumtienda() {
		return this.numtienda;
	}

	/**
	 * @return Returns el numproceso.
	 */
	public Integer getNumproceso() {
		return this.numproceso;
	}

	/**
	 * @return Returns el status.
	 */
	public String getStatus() {
		return this.status;
	}

	/**
	 * @return Returns el tipoOperacion.
	 */
	public Integer getTipoOperacion() {
		return this.tipoOperacion;
	}

	/**
	 * @return Returns el vtId.
	 */
	public String getVtId() {
		return this.vtId;
	}

	/**
	 * @param _codCajero
	 *            El codCajero a asignar.
	 */
	public void setCodCajero(String _codCajero) {
		this.codCajero = _codCajero;
	}

	/**
	 * @param _codformadepago
	 *            El codformadepago a asignar.
	 */
	public void setCodformadepago(String _codformadepago) {
		this.codformadepago = _codformadepago;
	}

	/**
	 * @param _correlativoPagoTransaccion
	 *            El correlativoPagoProceso a asignar.
	 */
	public void setCorrelativoPagoProceso(Integer _correlativoPagoTransaccion) {
		this.correlativoPagoProceso = _correlativoPagoTransaccion;
	}

	/**
	 * @param _de_cedulaCliente
	 *            El de_cedulaCliente a asignar.
	 */
	public void setDe_cedulaCliente(String _de_cedulaCliente) {
		this.de_cedulaCliente = _de_cedulaCliente;
	}

	/**
	 * @param _de_ctaEspecial
	 *            El de_ctaEspecial a asignar.
	 */
	public void setDe_ctaEspecial(String _de_ctaEspecial) {
		this.de_ctaEspecial = _de_ctaEspecial;
	}

	/**
	 * @param _de_monto
	 *            El de_monto a asignar.
	 */
	public void setDe_monto(BigDecimal _de_monto) {
		this.de_monto = _de_monto;
	}

	/**
	 * @param _de_nroCheque
	 *            El de_nroCheque a asignar.
	 */
	public void setDe_nroCheque(BigDecimal _de_nroCheque) {
		this.de_nroCheque = _de_nroCheque;
	}

	/**
	 * @param _de_numCuenta
	 *            El de_nroCuenta a asignar.
	 */
	public void setDe_nroCuenta(BigDecimal _de_numCuenta) {
		this.de_nroCuenta = _de_numCuenta;
	}

	/**
	 * @param _de_numSeqAnular
	 *            El de_numSeqAnular a asignar.
	 */
	public void setDe_numSeqAnular(Integer _de_numSeqAnular) {
		this.de_numSeqAnular = _de_numSeqAnular;
	}

	/**
	 * @param _de_planCreditoEPA
	 *            El de_planCreditoEPA a asignar.
	 */
	public void setDe_planCreditoEPA(String _de_planCreditoEPA) {
		this.de_planCreditoEPA = _de_planCreditoEPA;
	}

	/**
	 * @param _de_tipoCuenta
	 *            El de_tipoCuenta a asignar.
	 */
	public void setDe_tipoCuenta(String _de_tipoCuenta) {
		this.de_tipoCuenta = _de_tipoCuenta;
	}

	/**
	 * @param _do_codRespuesta
	 *            El do_codRespuesta a asignar.
	 */
	public void setDo_codRespuesta(String _do_codRespuesta) {
		this.do_codRespuesta = _do_codRespuesta;
	}

	/**
	 * @param _do_mensajeError
	 *            El do_mensajeError a asignar.
	 */
	public void setDo_mensajeError(String _do_mensajeError) {
		this.do_mensajeError = _do_mensajeError;
	}

	/**
	 * @param _do_mensajeRespuesta
	 *            El do_mensajeRespuesta a asignar.
	 */
	public void setDo_mensajeRespuesta(String _do_mensajeRespuesta) {
		this.do_mensajeRespuesta = _do_mensajeRespuesta;
	}

	/**
	 * @param _do_nombreAutorizador
	 *            El do_nombreAutorizador a asignar.
	 */
	public void setDo_nombreAutorizador(String _do_nombreAutorizador) {
		this.do_nombreAutorizador = _do_nombreAutorizador;
	}

	/**
	 * @param _do_nombreVoucher
	 *            El do_nombreVoucher a asignar.
	 */
	public void setDo_nombreVoucher(String _do_nombreVoucher) {
		this.do_nombreVoucher = _do_nombreVoucher;
	}

	/**
	 * @param _do_numeroAutorizacion
	 *            El do_numeroAutorizacion a asignar.
	 */
	public void setDo_numeroAutorizacion(String _do_numeroAutorizacion) {
		this.do_numeroAutorizacion = _do_numeroAutorizacion;
	}

	/**
	 * @param _dt_nombreCliente
	 *            El dt_nombreCliente a asignar.
	 */
	public void setDt_nombreCliente(String _dt_nombreCliente) {
		this.dt_nombreCliente = _dt_nombreCliente;
	}

	/**
	 * @param _dt_numeroTarjeta
	 *            El dt_numeroTarjeta a asignar.
	 */
	public void setDt_numeroTarjeta(String _dt_numeroTarjeta) {
		this.dt_numeroTarjeta = _dt_numeroTarjeta;
	}

	/**
	 * @param _dt_TipoTarjeta
	 *            El dt_TipoTarjeta a asignar.
	 */
	public void setDt_TipoTarjeta(String _dt_TipoTarjeta) {
		this.dt_TipoTarjeta = _dt_TipoTarjeta;
	}

	/**
	 * @param _fecha
	 *            El fecha a asignar.
	 */
	public void setFecha(Date _fecha) {
		this.fecha = _fecha;
	}

	/**
	 * @param _horaFinaliza
	 *            El horaFinaliza a asignar.
	 */
	public void setHoraFinaliza(Time _horaFinaliza) {
		this.horaFinaliza = _horaFinaliza;
	}

	/**
	 * @param _horaInicia
	 *            El horaInicia a asignar.
	 */
	public void setHoraInicia(Time _horaInicia) {
		this.horaInicia = _horaInicia;
	}

	/**
	 * @param _fecha
	 *            El fecha a asignar.
	 */
	public void setFecha(java.util.Date _fecha) {
		this.fecha = new Date(_fecha.getTime());
	}

	/**
	 * @param _horaFinaliza
	 *            El horaFinaliza a asignar.
	 */
	public void setHoraFinaliza(java.util.Date _horaFinaliza) {
		this.horaFinaliza = new Time(_horaFinaliza.getTime());
	}

	/**
	 * @param _horaInicia
	 *            El horaInicia a asignar.
	 */
	public void setHoraInicia(java.util.Date _horaInicia) {
		this.horaInicia = new Time(_horaInicia.getTime());
	}

	/**
	 * @param _numcaja
	 *            El numcaja a asignar.
	 */
	public void setNumcaja(Integer _numcaja) {
		this.numcaja = _numcaja;
	}

	/**
	 * @param _numSeq
	 *            El numSeq a asignar.
	 */
	public void setNumSeq(Integer _numSeq) {
		this.numSeq = _numSeq;
	}

	/**
	 * @param _numtienda
	 *            El numtienda a asignar.
	 */
	public void setNumtienda(Integer _numtienda) {
		this.numtienda = _numtienda;
	}

	/**
	 * @param _numtransaccion
	 *            El numproceso a asignar.
	 */
	public void setNumproceso(Integer _numtransaccion) {
		this.numproceso = _numtransaccion;
	}

	/**
	 * @param _status
	 *            El status a asignar.
	 */
	public void setStatus(String _status) {
		this.status = _status;
	}

	/**
	 * @param _vtId
	 *            El vtId a asignar.
	 */
	public void setVtId(String _vtId) {
		this.vtId = _vtId;
	}

	/**
	 * @param _correlativoPagoProceso
	 *            El correlativoPagoProceso a asignar.
	 */
	public void setCorrelativoPagoProceso(int _correlativoPagoProceso) {
		this.correlativoPagoProceso = new Integer(_correlativoPagoProceso);
	}

	/**
	 * @param _de_monto
	 *            El de_monto a asignar.
	 */
	public void setDe_monto(double _de_monto) {
		this.de_monto = new BigDecimal(_de_monto);
	}

	/**
	 * @param _de_numSeqAnular
	 *            El de_numSeqAnular a asignar.
	 */
	public void setDe_numSeqAnular(int _de_numSeqAnular) {
		this.de_numSeqAnular = new Integer(_de_numSeqAnular);
	}

	/**
	 * @param _numcaja
	 *            El numcaja a asignar.
	 */
	public void setNumcaja(int _numcaja) {
		this.numcaja = new Integer(_numcaja);
	}

	/**
	 * @param _numSeq
	 *            El numSeq a asignar.
	 */
	public void setNumSeq(int _numSeq) {
		this.numSeq = new Integer(_numSeq);
	}

	/**
	 * @param _numtienda
	 *            El numtienda a asignar.
	 */
	public void setNumtienda(int _numtienda) {
		this.numtienda = new Integer(_numtienda);
	}

	/**
	 * @param _numtransaccion
	 *            El numproceso a asignar.
	 */
	public void setNumproceso(int _numtransaccion) {
		this.numproceso = new Integer(_numtransaccion);
	}

	/**
	 * @return Returns el tipoProceso.
	 */
	public Integer getTipoProceso() {
		return this.tipoProceso;
	}

	/**
	 * @param _number
	 *            numtienda
	 */
	public void setNumtienda(Number _number) {
		this.setNumtienda(Control.number2Integer(_number));
	}

	/**
	 * @param _number
	 *            numcaja
	 */
	public void setNumcaja(Number _number) {
		this.setNumcaja(Control.number2Integer(_number));
	}

	/**
	 * @param _number
	 *            numSeq
	 */
	public void setNumSeq(Number _number) {
		this.setNumSeq(Control.number2Integer(_number));
	}

	/**
	 * @param _number
	 *            Num Transaccion
	 */
	public void setNumproceso(Number _number) {
		this.setNumproceso(Control.number2Integer(_number));

	}

	/**
	 * Correlativo pago de transaccion
	 *
	 * @param _number
	 */
	public void setCorrelativoPagoProceso(Number _number) {
		this.setCorrelativoPagoProceso(Control.number2Integer(_number));
	}

	/**
	 * @param _number
	 */
	public void setDe_monto(Number _number) {
		this.setDe_monto(Control.number2BigDecimal(_number));
	}

	/**
	 * @param _number
	 *            void
	 */
	public void setDe_numSeqAnular(Number _number) {
		this.setDe_numSeqAnular(Control.number2Integer(_number));
	}

	/**
	 * @return Returns el regActualizado.
	 */
	public String getRegActualizado() {
		return this.regActualizado;
	}

	/**
	 * @param _regActualizado
	 *            El regActualizado a asignar.
	 */
	public void setRegActualizado(String _regActualizado) {
		this.regActualizado = _regActualizado;
	}

	/**
	 * @return Returns el numservicio.
	 */
	public Integer getNumservicio() {
		return this.numservicio;
	}

	/**
	 * @param _numservicio
	 *            El numservicio a asignar.
	 */
	public void setNumservicio(Integer _numservicio) {
		this.numservicio = _numservicio;
	}

	/**
	 * @param _numservicio
	 *            El numservicio a asignar.
	 */
	public void setNumservicio(int _numservicio) {
		this.setNumservicio(new Integer(_numservicio));
	}

	/**
	 * @param _numservicio
	 *            El numservicio a asignar.
	 */
	public void setNumservicio(Number _numservicio) {
		this.setNumservicio(Control.number2Integer(_numservicio));
	}

	/**
	 * @return Returns el de_porcentajeProvimillas.
	 */
	public BigDecimal getDe_porcentajeProvimillas() {
		return this.de_porcentajeProvimillas;
	}

	/**
	 * @param _de_porcentajeProvimillas
	 *            El de_porcentajeProvimillas a asignar.
	 */
	public void setDe_porcentajeProvimillas(BigDecimal _de_porcentajeProvimillas) {
		this.de_porcentajeProvimillas = _de_porcentajeProvimillas;
	}

	/**
	 * Genera los datos externos necesarios para anular esta transaccion.
	 *
	 * @return DatosExternosPuntoAgil
	 */
	public DatosExternosPuntoAgil toDatosExternosAnulacion() {
		DatosExternosPuntoAgil depa = new DatosExternosPuntoAgil(
			this.tipoProceso, Constantes.TIPO_OPERACION_ANULACION);
		depa.setCedula(this.getDe_cedulaCliente());
		depa.setNroTransaccion(String.valueOf(this.getNumSeq()));
		return depa;
	}

	/**
	 * Cambia regActualizado de 'S' a 'N' (solo si esta en 'S')
	 */
	public void cambiarRegActualizado() {
		if (Constantes.REGISTRO_ACTUALIZADO_SI.equalsIgnoreCase(this
			.getRegActualizado())) {
			this.setRegActualizado(Constantes.REGISTRO_ACTUALIZADO_NO);
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
			.append("numtienda", this.numtienda)
			.append("numcaja", this.numcaja)
			.append("vtId", this.vtId)
			.append("numSeq", this.numSeq)
			.append("tipoOperacion", this.tipoOperacion)
			.append("tipoProceso", this.tipoProceso)
			.append("numproceso", this.numproceso)
			.append("numservicio", this.numservicio)
			.append("codformadepago", this.codformadepago)
			.append("correlativoPagoProceso", this.correlativoPagoProceso)
			.append("codCajero", this.codCajero)
			.append("fecha", this.fecha)
			.append("horaFinaliza", this.horaFinaliza)
			.append("horaInicia", this.horaInicia)
			.append("de_cedulaCliente", this.de_cedulaCliente)
			.append("de_ctaEspecial", this.de_ctaEspecial)
			.append("de_monto", this.de_monto)
			.append("de_nroCheque", this.de_nroCheque)
			.append("de_nroCuenta", this.de_nroCuenta)
			.append("de_numSeqAnular", this.de_numSeqAnular)
			.append("de_planCreditoEPA", this.de_planCreditoEPA)
			.append("de_tipoCuenta", this.de_tipoCuenta)
			.append("de_porcentajeProvimillas", this.de_porcentajeProvimillas)
			.append("do_codRespuesta", this.do_codRespuesta)
			.append("do_mensajeError", this.do_mensajeError)
			.append("do_mensajeRespuesta", this.do_mensajeRespuesta)
			.append("do_nombreAutorizador", this.do_nombreAutorizador)
			.append("do_nombreVoucher", this.do_nombreVoucher)
			.append("do_numeroAutorizacion", this.do_numeroAutorizacion)
			.append("dt_nombreCliente", this.dt_nombreCliente)
			.append("dt_numeroTarjeta", this.dt_numeroTarjeta)
			.append("dt_TipoTarjeta", this.dt_TipoTarjeta)
			.append("status", this.status)
			.append("regActualizado", this.regActualizado)
			.toString();
	}

}
