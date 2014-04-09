package com.beco.colascr.transferencias.sincronizador.hibernate;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class Atcm24PK implements Serializable {

    /** identifier field */
    private int codedo;

    /** identifier field */
    private int codciu;

    /** full constructor */
    public Atcm24PK(int codedo, int codciu) {
        this.codedo = codedo;
        this.codciu = codciu;
    }

    /** default constructor */
    public Atcm24PK() {
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("codedo", getCodedo())
            .append("codciu", getCodciu())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof Atcm24PK) ) return false;
        Atcm24PK castOther = (Atcm24PK) other;
        return new EqualsBuilder()
            .append(this.getCodedo(), castOther.getCodedo())
            .append(this.getCodciu(), castOther.getCodciu())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getCodedo())
            .append(getCodciu())
            .toHashCode();
    }

	/**
	 * @return
	 */
	public int getCodciu() {
		return codciu;
	}

	/**
	 * @return
	 */
	public int getCodedo() {
		return codedo;
	}

	/**
	 * @param i
	 */
	public void setCodciu(int i) {
		codciu = i;
	}

	/**
	 * @param i
	 */
	public void setCodedo(int i) {
		codedo = i;
	}

}
