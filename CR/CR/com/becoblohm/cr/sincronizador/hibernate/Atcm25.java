package com.becoblohm.cr.sincronizador.hibernate;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class Atcm25 implements Serializable {

    /** identifier field */
    private com.becoblohm.cr.sincronizador.hibernate.Atcm25PK comp_id;

    /** persistent field */
    private String desurb;

	/** persistent field */
	private String zonpos;

	/** nullable persistent field */
	private String staeli;

	/** nullable persistent field */
	private String usreli;

	/** nullable persistent field */
	private Date actualizacion;

    /** full constructor */
    public Atcm25(com.becoblohm.cr.sincronizador.hibernate.Atcm25PK comp_id, String desurb,
    				String zonpos, String staeli, String usreli, Date actualizacion) {
        this.comp_id = comp_id;
        this.desurb = desurb;
        this.zonpos = zonpos;
        this.staeli = staeli;
		this.usreli = usreli;
		this.actualizacion = actualizacion;
    }

    /** default constructor */
    public Atcm25() {
    }

    public com.becoblohm.cr.sincronizador.hibernate.Atcm25PK getComp_id() {
        return this.comp_id;
    }

    public void setComp_id(com.becoblohm.cr.sincronizador.hibernate.Atcm25PK comp_id) {
        this.comp_id = comp_id;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("comp_id", getComp_id())
			.append("desurb", getDesurb())
			.append("zonpos", getZonpos())
			.append("staeli", getStaeli())
			.append("usreli", getUsreli())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof Atcm25) ) return false;
        Atcm25 castOther = (Atcm25) other;
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
	 * @return
	 */
	public Date getActualizacion() {
		return actualizacion;
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

	/**
	 * @return
	 */
	public String getDesurb() {
		return desurb;
	}

	/**
	 * @return
	 */
	public String getZonpos() {
		return zonpos;
	}

	/**
	 * @param string
	 */
	public void setDesurb(String string) {
		desurb = string;
	}

	/**
	 * @param string
	 */
	public void setZonpos(String string) {
		zonpos = string;
	}

}
