package com.beco.colascr.transferencias.sincronizador.hibernate;

import java.io.Serializable;
import java.math.BigDecimal;

import net.sf.hibernate.CallbackException;
import net.sf.hibernate.Session;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.beco.colascr.transferencias.sincronizador.hibernate.base.EntidadMarcable;

/** @author Hibernate CodeGenerator */
public class Detalleservicio implements net.sf.hibernate.Lifecycle,Serializable, EntidadMarcable {

    /** identifier field */
    private com.beco.colascr.transferencias.sincronizador.hibernate.DetalleservicioPK comp_id;

	/** nullable persistent field */
	private String codcondicionventa;

    /** nullable persistent field */
    private BigDecimal cantidad;

	/** nullable persistent field */
	private BigDecimal precioregular;
	
	/** nullable persistent field */
	private BigDecimal preciofinal;
	
	/** nullable persistent field */
	private BigDecimal montoimpuesto;
	
	/** nullable persistent field */
	private String codtipocaptura;
	
	/** nullable persistent field */
	private Integer codpromocion;
	
	/** nullable persistent field */
	private String estadoregistro;
	
	/** nullable persistent field */
	private String regactualizado;

    /** full constructor */
    public Detalleservicio(com.beco.colascr.transferencias.sincronizador.hibernate.DetalleservicioPK comp_id, String codcondicionventa, BigDecimal cantidad, BigDecimal precioregular, BigDecimal preciofinal, BigDecimal montoimpuesto, String codtipocaptura, Integer codpromocion, String estadoregistro, String regactualizado) {
        this.comp_id = comp_id;
        this.codcondicionventa = codcondicionventa;
        this.cantidad = cantidad;
		this.precioregular = precioregular;
		this.preciofinal = preciofinal;
		this.montoimpuesto = montoimpuesto;
		this.codtipocaptura = codtipocaptura;
		this.codpromocion = codpromocion;
		this.estadoregistro = estadoregistro;
        this.regactualizado = regactualizado;
    }

    /** default constructor */
    public Detalleservicio() {
    }

    public com.beco.colascr.transferencias.sincronizador.hibernate.DetalleservicioPK getComp_id() {
        return this.comp_id;
    }

    public void setComp_id(com.beco.colascr.transferencias.sincronizador.hibernate.DetalleservicioPK comp_id) {
        this.comp_id = comp_id;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("comp_id", getComp_id())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof Detalleservicio) ) return false;
        Detalleservicio castOther = (Detalleservicio) other;
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
	 * Método onSave
	 *
	 * @param arg0
	 * @return
	 * @throws CallbackException
	 */
	public boolean onSave(Session arg0) throws CallbackException {
		return false;
	}

	/**
	 * Método onUpdate
	 *
	 * @param arg0
	 * @return
	 * @throws CallbackException
	 */
	public boolean onUpdate(Session arg0) throws CallbackException {
		return false;
	}

	/**
	 * Método onDelete
	 *
	 * @param arg0
	 * @return
	 * @throws CallbackException
	 */
	public boolean onDelete(Session arg0) throws CallbackException {
		return false;
	}

	/**
	 * Método onLoad
	 *
	 * @param arg0
	 * @param arg1
	 */
	public void onLoad(Session arg0, Serializable arg1) {
	}
	/**
	 * Método getCantidad
	 * 
	 * @return
	 * BigDecimal
	 */
	public BigDecimal getCantidad() {
		return cantidad;
	}

	/**
	 * Método getCodpromocion
	 * 
	 * @return
	 * int
	 */
	public Integer getCodpromocion() {
		return codpromocion;
	}

	/**
	 * Método getCodtipocaptura
	 * 
	 * @return
	 * String
	 */
	public String getCodtipocaptura() {
		return codtipocaptura;
	}

	/**
	 * Método getEstadoregistro
	 * 
	 * @return
	 * String
	 */
	public String getEstadoregistro() {
		return estadoregistro;
	}

	/**
	 * Método getMontoimpuesto
	 * 
	 * @return
	 * BigDecimal
	 */
	public BigDecimal getMontoimpuesto() {
		return montoimpuesto;
	}

	/**
	 * Método getPreciofinal
	 * 
	 * @return
	 * BigDecimal
	 */
	public BigDecimal getPreciofinal() {
		return preciofinal;
	}

	/**
	 * Método getPrecioregular
	 * 
	 * @return
	 * BigDecimal
	 */
	public BigDecimal getPrecioregular() {
		return precioregular;
	}

	/**
	 * Método getRegactualizado
	 * 
	 * @return
	 * String
	 */
	public String getRegactualizado() {
		return regactualizado;
	}

	/**
	 * Método setCantidad
	 * 
	 * @param decimal
	 * void
	 */
	public void setCantidad(BigDecimal decimal) {
		cantidad = decimal;
	}

	/**
	 * Método setCodpromocion
	 * 
	 * @param i
	 * void
	 */
	public void setCodpromocion(Integer i) {
		codpromocion = i;
	}

	/**
	 * Método setCodtipocaptura
	 * 
	 * @param string
	 * void
	 */
	public void setCodtipocaptura(String string) {
		codtipocaptura = string;
	}

	/**
	 * Método setEstadoregistro
	 * 
	 * @param string
	 * void
	 */
	public void setEstadoregistro(String string) {
		estadoregistro = string;
	}

	/**
	 * Método setMontoimpuesto
	 * 
	 * @param decimal
	 * void
	 */
	public void setMontoimpuesto(BigDecimal decimal) {
		montoimpuesto = decimal;
	}

	/**
	 * Método setPreciofinal
	 * 
	 * @param decimal
	 * void
	 */
	public void setPreciofinal(BigDecimal decimal) {
		preciofinal = decimal;
	}

	/**
	 * Método setPrecioregular
	 * 
	 * @param decimal
	 * void
	 */
	public void setPrecioregular(BigDecimal decimal) {
		precioregular = decimal;
	}

	/**
	 * Método setRegactualizado
	 * 
	 * @param string
	 * void
	 */
	public void setRegactualizado(String string) {
		regactualizado = string;
	}

	/**
	 * @return
	 */
	public String getCodcondicionventa() {
		return codcondicionventa;
	}

	/**
	 * @param string
	 */
	public void setCodcondicionventa(String string) {
		codcondicionventa = string;
	}

}
