package com.beco.colascr.transferencias.sincronizador.hibernate;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class Br_comprobantefiscalPK implements Serializable {

    /** identifier field */
	private Short numtienda;

    /** identifier field */
	private Date fecha;
	
    /** identifier field */
	private Short numcaja;

    /** identifier field */
	private int numtransaccion;

    /** identifier field */
	private int numcomprobantefiscal;

    /** identifier field */
	private String tipocomprobante;

    /** full constructor */
    public Br_comprobantefiscalPK(Short numtienda, Date fecha, Short numcaja, int numtransaccion, int numcomprobantefiscal, String tipocomprobante) {
        this.numtienda = numtienda;
        this.fecha = fecha;
        this.numcaja = numcaja;
        this.numtransaccion = numtransaccion;
        this.numcomprobantefiscal = numcomprobantefiscal;
        this.tipocomprobante = tipocomprobante;
    }

    /** default constructor */
    public Br_comprobantefiscalPK() {
    }

    public String toString() {
        return new ToStringBuilder(this)
        	.append("numtienda", getNumtienda())
            .append("fecha", getFecha())
            .append("numcaja", getNumcaja())
            .append("numtransaccion", getNumtransaccion())
            .append("numcomprobantefiscal", getNumcomprobantefiscal())
            .append("tipocomprobante", getTipocomprobante())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof Br_comprobantefiscalPK) ) return false;
        Br_comprobantefiscalPK castOther = (Br_comprobantefiscalPK) other;
        return new EqualsBuilder()
	    	.append(this.getNumtienda(), castOther.getNumtienda())
	        .append(this.getFecha(), castOther.getFecha())
	        .append(this.getNumcaja(), castOther.getNumcaja())
	        .append(this.getNumtransaccion(), castOther.getNumtransaccion())
	        .append(this.getNumcomprobantefiscal(), castOther.getNumcomprobantefiscal())
	        .append(this.getTipocomprobante(), castOther.getTipocomprobante())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
	    	.append(getNumtienda())
	        .append(getFecha())
	        .append(getNumcaja())
	        .append(getNumtransaccion())
	        .append(getNumcomprobantefiscal())
	        .append(getTipocomprobante())
            .toHashCode();
    }

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Short getNumcaja() {
		return numcaja;
	}

	public void setNumcaja(Short numcaja) {
		this.numcaja = numcaja;
	}

	public int getNumcomprobantefiscal() {
		return numcomprobantefiscal;
	}

	public void setNumcomprobantefiscal(int numcomprobantefiscal) {
		this.numcomprobantefiscal = numcomprobantefiscal;
	}

	public Short getNumtienda() {
		return numtienda;
	}

	public void setNumtienda(Short numtienda) {
		this.numtienda = numtienda;
	}

	public int getNumtransaccion() {
		return numtransaccion;
	}

	public void setNumtransaccion(int numtransaccion) {
		this.numtransaccion = numtransaccion;
	}

	public String getTipocomprobante() {
		return tipocomprobante;
	}

	public void setTipocomprobante(String tipocomprobante) {
		this.tipocomprobante = tipocomprobante;
	}


}
