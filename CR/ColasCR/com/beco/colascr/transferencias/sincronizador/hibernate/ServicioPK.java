package com.beco.colascr.transferencias.sincronizador.hibernate;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class ServicioPK implements Serializable {

    /** identifier field */
    private int numtienda;

    /** identifier field */
    private String codtiposervicio;
    
	/** identifier field */
	private int numservicio;
	
	/** identifier field */
	private Date fecha;

    /** full constructor */
    public ServicioPK(int numtienda, String codtiposervicio, int numservicio, Date fecha) {
        this.numtienda = numtienda;
        this.codtiposervicio = codtiposervicio;
        this.numservicio = numservicio;
        this.fecha = fecha;
    }

    /** default constructor */
    public ServicioPK() {
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("numtienda", getNumtienda())
            .append("codtiposervicio", getCodtiposervicio())
            .append("numservicio", getNumservicio())
			.append("fecha", getFecha())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof ServicioPK) ) return false;
        ServicioPK castOther = (ServicioPK) other;
        return new EqualsBuilder()
            .append(this.getNumtienda(), castOther.getNumtienda())
            .append(this.getCodtiposervicio(), castOther.getCodtiposervicio())
            .append(this.getNumservicio(), castOther.getNumservicio())
			.append(this.getFecha(), castOther.getFecha())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getNumtienda())
            .append(getCodtiposervicio())
            .append(getNumservicio())
			.append(getFecha())
            .toHashCode();
    }

	/**
	 * M�todo getCodtiposervicio
	 * 
	 * @return
	 * String
	 */
	public String getCodtiposervicio() {
		return codtiposervicio;
	}

	/**
	 * M�todo getFecha
	 * 
	 * @return
	 * Date
	 */
	public Date getFecha() {
		return fecha;
	}

	/**
	 * M�todo getNumservicio
	 * 
	 * @return
	 * int
	 */
	public int getNumservicio() {
		return numservicio;
	}

	/**
	 * M�todo getNumtienda
	 * 
	 * @return
	 * int
	 */
	public int getNumtienda() {
		return numtienda;
	}

	/**
	 * M�todo setCodtiposervicio
	 * 
	 * @param string
	 * void
	 */
	public void setCodtiposervicio(String string) {
		codtiposervicio = string;
	}

	/**
	 * M�todo setFecha
	 * 
	 * @param date
	 * void
	 */
	public void setFecha(Date date) {
		fecha = date;
	}

	/**
	 * M�todo setNumservicio
	 * 
	 * @param i
	 * void
	 */
	public void setNumservicio(int i) {
		numservicio = i;
	}

	/**
	 * M�todo setNumtienda
	 * 
	 * @param i
	 * void
	 */
	public void setNumtienda(int i) {
		numtienda = i;
	}

}
