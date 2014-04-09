package com.beco.colascr.transferencias.sincronizador.hibernate;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Time;
import java.util.Date;

import net.sf.hibernate.CallbackException;
import net.sf.hibernate.Session;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.beco.colascr.transferencias.sincronizador.hibernate.base.EntidadMarcable;

/** @author Hibernate CodeGenerator */
public class Servicio implements net.sf.hibernate.Lifecycle,Serializable, EntidadMarcable {

    /** identifier field */
    private com.beco.colascr.transferencias.sincronizador.hibernate.ServicioPK comp_id;

    /** nullable persistent field */
    private String codcliente;

	/** nullable persistent field */
	private BigDecimal montobase;
	
	/** nullable persistent field */
	private BigDecimal montoimpuesto;
	
	/** nullable persistent field */
	private int lineasfacturacion;
	
	/** nullable persistent field */
	private String condicionabono;
	
	/** nullable persistent field */
	private String codcajero;
	
	/** nullable persistent field */
	private Integer numtransaccionventa;
	
	/** nullable persistent field */
	private Date fechatransaccionvnta;
	
	/** nullable persistent field */
	private Integer numcajaventa;
	
	/** nullable persistent field */
	private String direcciondespacho;
	
    /** persistent field */
    private String cambiadireccion;
    
	/** nullable persistent field */
	private Time horainicia;
	
	/** nullable persistent field */
	private Time horafinaliza;
	
	/** nullable persistent field */
	private Integer numcajaanula;
	
	/** nullable persistent field */
	private String codusuarioanula;
	
	/** nullable persistent field */
	private String estadoservicio;
	
	/** nullable persistent field */
	private String regactualizado;
	
	/** nullable persistent field */
	private Integer tipoapartado;
	
	/** nullable persistent field */
	private Date fechavence;

    /** full constructor */
    public Servicio(com.beco.colascr.transferencias.sincronizador.hibernate.ServicioPK comp_id, String codcliente, BigDecimal montobase, BigDecimal montoimpuesto, int lineasfacturacion, String condicionabono, String codcajero, Integer numtransaccionventa, Date fechatransaccionvnta, Integer numcajaventa, String direcciondespacho, String cambiadireccion, Time horainicia, Time horafinaliza, String estadoservicio, Integer numcajaanula, String codusuarioanula, String regactualizado, Integer tipoapartado, Date fechavence) {
        this.comp_id = comp_id;
        this.codcliente = codcliente;
		this.montobase = montobase;
		this.montoimpuesto = montoimpuesto;
		this.lineasfacturacion = lineasfacturacion;
		this.condicionabono = condicionabono;
		this.codcajero = codcajero;
		this.numtransaccionventa = numtransaccionventa;
		this.fechatransaccionvnta = fechatransaccionvnta;
		this.numcajaventa = numcajaventa;
		this.direcciondespacho = direcciondespacho;
		this.cambiadireccion = cambiadireccion;
		this.horainicia = horainicia;
		this.horafinaliza = horafinaliza;
		this.estadoservicio = estadoservicio;
		this.numcajaanula = numcajaanula;
		this.codusuarioanula = codusuarioanula;
        this.regactualizado = regactualizado;
        this.tipoapartado = tipoapartado;
        this.fechavence = fechavence;
    }

	/** default constructor */
    public Servicio() {
    }

    public com.beco.colascr.transferencias.sincronizador.hibernate.ServicioPK getComp_id() {
        return this.comp_id;
    }

    public void setComp_id(com.beco.colascr.transferencias.sincronizador.hibernate.ServicioPK comp_id) {
        this.comp_id = comp_id;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("comp_id", getComp_id())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof Servicio) ) return false;
        Servicio castOther = (Servicio) other;
        return new EqualsBuilder()
            .append(this.getComp_id(), castOther.getComp_id())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getComp_id())
            .toHashCode();
    }

	/**
	 * M�todo onSave
	 *
	 * @param arg0
	 * @return
	 * @throws CallbackException
	 */
	public boolean onSave(Session arg0) throws CallbackException {
		return false;
	}

	/**
	 * M�todo onUpdate
	 *
	 * @param arg0
	 * @return
	 * @throws CallbackException
	 */
	public boolean onUpdate(Session arg0) throws CallbackException {
		return false;
	}

	/**
	 * M�todo onDelete
	 *
	 * @param arg0
	 * @return
	 * @throws CallbackException
	 */
	public boolean onDelete(Session arg0) throws CallbackException {
		return false;
	}

	/**
	 * M�todo onLoad
	 *
	 * @param arg0
	 * @param arg1
	 */
	public void onLoad(Session arg0, Serializable arg1) {
	}

	/**
	 * M�todo getCambiadireccion
	 * 
	 * @return
	 * String
	 */
	public String getCambiadireccion() {
		return cambiadireccion;
	}

	/**
	 * M�todo getCodcajero
	 * 
	 * @return
	 * String
	 */
	public String getCodcajero() {
		return codcajero;
	}

	/**
	 * M�todo getCodcliente
	 * 
	 * @return
	 * String
	 */
	public String getCodcliente() {
		return codcliente;
	}

	/**
	 * M�todo getCondicionabono
	 * 
	 * @return
	 * String
	 */
	public String getCondicionabono() {
		return condicionabono;
	}

	/**
	 * M�todo getDirecciondespacho
	 * 
	 * @return
	 * String
	 */
	public String getDirecciondespacho() {
		return direcciondespacho;
	}

	/**
	 * M�todo getEstadoservicio
	 * 
	 * @return
	 * String
	 */
	public String getEstadoservicio() {
		return estadoservicio;
	}

	/**
	 * M�todo getFechatransacciovnta
	 * 
	 * @return
	 * Date
	 */
	public Date getFechatransaccionvnta() {
		return fechatransaccionvnta;
	}

	/**
	 * M�todo getHorafinaliza
	 * 
	 * @return
	 * Time
	 */
	public Time getHorafinaliza() {
		return horafinaliza;
	}

	/**
	 * M�todo getHorainicia
	 * 
	 * @return
	 * Time
	 */
	public Time getHorainicia() {
		return horainicia;
	}

	/**
	 * M�todo getLineasfacturacion
	 * 
	 * @return
	 * int
	 */
	public int getLineasfacturacion() {
		return lineasfacturacion;
	}

	/**
	 * M�todo getMontobase
	 * 
	 * @return
	 * BigDecimal
	 */
	public BigDecimal getMontobase() {
		return montobase;
	}

	/**
	 * M�todo getMontoimpuesto
	 * 
	 * @return
	 * BigDecimal
	 */
	public BigDecimal getMontoimpuesto() {
		return montoimpuesto;
	}

	/**
	 * M�todo getNumtransaccionventa
	 * 
	 * @return
	 * int
	 */
	public Integer getNumtransaccionventa() {
		return numtransaccionventa;
	}

	/**
	 * M�todo getRegactualizado
	 * 
	 * @return
	 * String
	 */
	public String getRegactualizado() {
		return regactualizado;
	}

	/**
	 * M�todo setCambiadireccion
	 * 
	 * @param string
	 * void
	 */
	public void setCambiadireccion(String string) {
		cambiadireccion = string;
	}

	/**
	 * M�todo setCodcajero
	 * 
	 * @param string
	 * void
	 */
	public void setCodcajero(String string) {
		codcajero = string;
	}

	/**
	 * M�todo setCodcliente
	 * 
	 * @param string
	 * void
	 */
	public void setCodcliente(String string) {
		codcliente = string;
	}

	/**
	 * M�todo setCondicionabono
	 * 
	 * @param string
	 * void
	 */
	public void setCondicionabono(String string) {
		condicionabono = string;
	}

	/**
	 * M�todo setDirecciondespacho
	 * 
	 * @param string
	 * void
	 */
	public void setDirecciondespacho(String string) {
		direcciondespacho = string;
	}

	/**
	 * M�todo setEstadoservicio
	 * 
	 * @param string
	 * void
	 */
	public void setEstadoservicio(String string) {
		estadoservicio = string;
	}

	/**
	 * M�todo setFechatransacciovnta
	 * 
	 * @param date
	 * void
	 */
	public void setFechatransaccionvnta(Date date) {
		fechatransaccionvnta = date;
	}

	/**
	 * M�todo setHorafinaliza
	 * 
	 * @param time
	 * void
	 */
	public void setHorafinaliza(Time time) {
		horafinaliza = time;
	}

	/**
	 * M�todo setHorainicia
	 * 
	 * @param time
	 * void
	 */
	public void setHorainicia(Time time) {
		horainicia = time;
	}

	/**
	 * M�todo setLineasfacturacion
	 * 
	 * @param i
	 * void
	 */
	public void setLineasfacturacion(int i) {
		lineasfacturacion = i;
	}

	/**
	 * M�todo setMontobase
	 * 
	 * @param decimal
	 * void
	 */
	public void setMontobase(BigDecimal decimal) {
		montobase = decimal;
	}

	/**
	 * M�todo setMontoimpuesto
	 * 
	 * @param decimal
	 * void
	 */
	public void setMontoimpuesto(BigDecimal decimal) {
		montoimpuesto = decimal;
	}

	/**
	 * M�todo setNumtransaccionventa
	 * 
	 * @param i
	 * void
	 */
	public void setNumtransaccionventa(Integer i) {
		numtransaccionventa = i;
	}

	/**
	 * M�todo setRegactualizado
	 * 
	 * @param string
	 * void
	 */
	public void setRegactualizado(String string) {
		regactualizado = string;
	}

	/**
	 * M�todo getCodusuarioanula
	 * 
	 * @return
	 * String
	 */
	public String getCodusuarioanula() {
		return codusuarioanula;
	}

	/**
	 * M�todo setCodusuarioanula
	 * 
	 * @param i
	 * void
	 */
	public void setCodusuarioanula(String i) {
		codusuarioanula = i;
	}

	/**
	 * M�todo getNumcajaanula
	 * 
	 * @return
	 * Integer
	 */
	public Integer getNumcajaanula() {
		return numcajaanula;
	}

	/**
	 * M�todo setNumcajaanula
	 * 
	 * @param integer
	 * void
	 */
	public void setNumcajaanula(Integer integer) {
		numcajaanula = integer;
	}

	/**
	 * M�todo getNumcajaventa
	 * 
	 * @return
	 * Integer
	 */
	public Integer getNumcajaventa() {
		return numcajaventa;
	}

	/**
	 * M�todo setNumcajaventa
	 * 
	 * @param integer
	 * void
	 */
	public void setNumcajaventa(Integer integer) {
		numcajaventa = integer;
	}
	
    public Date getFechavence() {
		return fechavence;
	}

	public void setFechavence(Date fecha) {
		fechavence = fecha;
	}

	public Integer getTipoapartado() {
		return tipoapartado;
	}

	public void setTipoapartado(Integer tipo) {
		tipoapartado = tipo;
	}

}
