package com.beco.colascr.transferencias.sincronizador.hibernate;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class Atcm23 implements Serializable {

    /** identifier field */
    private int codedo;

    /** persistent field */
    private String desedo;

    /** nullable persistent field */
    private String staeli;

	/** nullable persistent field */
	private String usreli;

    /** nullable persistent field */
    private Date actualizacion;

    /** full constructor */
    public Atcm23(int codedo, String desedo, String staeli, String usreli, Date actualizacion) {
    	this.codedo = codedo;
    	this.desedo = desedo;
    	this.staeli = staeli;
    	this.usreli = usreli;
        this.actualizacion = actualizacion;
    }

    /** default constructor */
    public Atcm23() {
    }

    /** minimal constructor */
    public Atcm23(int codedo, String desedo) {
        this.codedo = codedo;
        this.desedo = desedo;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("codedo", getCodedo())
            .append("desedo", getDesedo())
            .append("staeli", getStaeli())
			.append("usreli", getUsreli())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof Atcm23) ) return false;
        Atcm23 castOther = (Atcm23) other;
        return new EqualsBuilder()
            .append(this.getCodedo(), castOther.getCodedo())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getCodedo())
            .toHashCode();
    }
	/**
	 * @return
	 */
	public Date getActualizacion() {
		return actualizacion;
	}

	/**
	 * @return
	 */
	public int getCodedo() {
		return codedo;
	}

	/**
	 * @return
	 */
	public String getDesedo() {
		return desedo;
	}

	/**
	 * @return
	 */
	public String getStaeli() {
		return staeli;
	}

	/**
	 * @return
	 */
	public String getUsreli() {
		return usreli;
	}

	/**
	 * @param date
	 */
	public void setActualizacion(Date date) {
		actualizacion = date;
	}

	/**
	 * @param i
	 */
	public void setCodedo(int i) {
		codedo = i;
	}

	/**
	 * @param string
	 */
	public void setDesedo(String string) {
		desedo = string;
	}

	/**
	 * @param string
	 */
	public void setStaeli(String string) {
		staeli = string;
	}

	/**
	 * @param string
	 */
	public void setUsreli(String string) {
		usreli = string;
	}

}
