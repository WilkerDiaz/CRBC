package com.becoblohm.cr.sincronizador.hibernate;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class Atcm24 implements Serializable {

    /** identifier field */
    private com.becoblohm.cr.sincronizador.hibernate.Atcm24PK comp_id;

    /** persistent field */
    private String desciu;

	/** persistent field */
	private int codarea1;

	/** nullable persistent field */
	private String staeli;

	/** nullable persistent field */
	private String usreli;

	/** nullable persistent field */
	private Date actualizacion;

    /** full constructor */
    public Atcm24(com.becoblohm.cr.sincronizador.hibernate.Atcm24PK comp_id, String desciu,
    				int codarea1, String staeli, String usreli, Date actualizacion) {
        this.comp_id = comp_id;
        this.desciu = desciu;
        this.codarea1 = codarea1;
        this.staeli = staeli;
		this.usreli = usreli;
		this.actualizacion = actualizacion;
    }

    /** default constructor */
    public Atcm24() {
    }

    public com.becoblohm.cr.sincronizador.hibernate.Atcm24PK getComp_id() {
        return this.comp_id;
    }

    public void setComp_id(com.becoblohm.cr.sincronizador.hibernate.Atcm24PK comp_id) {
        this.comp_id = comp_id;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("comp_id", getComp_id())
			.append("desciu", getDesciu())
			.append("codarea1", getCodarea1())
			.append("staeli", getStaeli())
			.append("usreli", getUsreli())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof Atcm24) ) return false;
        Atcm24 castOther = (Atcm24) other;
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
	public int getCodarea1() {
		return codarea1;
	}

	/**
	 * @return
	 */
	public String getDesciu() {
		return desciu;
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
	public void setCodarea1(int i) {
		codarea1 = i;
	}

	/**
	 * @param string
	 */
	public void setDesciu(String string) {
		desciu = string;
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
