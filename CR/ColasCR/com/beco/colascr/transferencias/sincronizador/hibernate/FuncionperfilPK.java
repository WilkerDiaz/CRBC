package com.beco.colascr.transferencias.sincronizador.hibernate;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class FuncionperfilPK implements Serializable {

	/** identifier field */
	private String codperfil;

    /** identifier field */
    private int codfuncion;

    /** identifier field */
    private int codmodulo;

    /** full constructor */
    public FuncionperfilPK(String codperfil, int codfuncion, int codmodulo) {
    	this.codperfil = codperfil;
        this.codfuncion = codfuncion;
        this.codmodulo = codmodulo;
    }

    /** default constructor */
    public FuncionperfilPK() {
    }

    public String toString() {
        return new ToStringBuilder(this)
			.append("codperfil", getCodperfil())
            .append("codfuncion", getCodfuncion())
            .append("codmodulo", getCodmodulo())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof FuncionperfilPK) ) return false;
		FuncionperfilPK castOther = (FuncionperfilPK) other;
        return new EqualsBuilder()
			.append(this.getCodperfil(), castOther.getCodperfil())
            .append(this.getCodfuncion(), castOther.getCodfuncion())
            .append(this.getCodmodulo(), castOther.getCodmodulo())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
			.append(getCodperfil())
            .append(getCodfuncion())
            .append(getCodmodulo())
            .toHashCode();
    }

	/**
	 * @return
	 */
	public int getCodfuncion() {
		return codfuncion;
	}

	/**
	 * @return
	 */
	public int getCodmodulo() {
		return codmodulo;
	}

	/**
	 * @param string
	 */
	public void setCodfuncion(int string) {
		codfuncion = string;
	}

	/**
	 * @param string
	 */
	public void setCodmodulo(int string) {
		codmodulo = string;
	}

	/**
	 * @return
	 */
	public String getCodperfil() {
		return codperfil;
	}

	/**
	 * @param string
	 */
	public void setCodperfil(String string) {
		codperfil = string;
	}

}
