package com.becoblohm.cr.sincronizador.hibernate;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class Atcm25PK implements Serializable {

    /** identifier field */
    private int codedo;

    /** identifier field */
    private int codciu;

	/** identifier field */
	private int codurb;

    /** full constructor */
    public Atcm25PK(int codedo, int codciu, int codurb) {
        this.codedo = codedo;
        this.codciu = codciu;
        this.codurb = codurb;
    }

    /** default constructor */
    public Atcm25PK() {
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("codedo", getCodedo())
            .append("codciu", getCodciu())
			.append("codurb", getCodurb())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof Atcm25PK) ) return false;
        Atcm25PK castOther = (Atcm25PK) other;
        return new EqualsBuilder()
            .append(this.getCodedo(), castOther.getCodedo())
            .append(this.getCodciu(), castOther.getCodciu())
			.append(this.getCodurb(), castOther.getCodurb())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getCodedo())
            .append(getCodciu())
			.append(getCodurb())
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

	/**
	 * @return
	 */
	public int getCodurb() {
		return codurb;
	}

	/**
	 * @param i
	 */
	public void setCodurb(int i) {
		codurb = i;
	}

}
