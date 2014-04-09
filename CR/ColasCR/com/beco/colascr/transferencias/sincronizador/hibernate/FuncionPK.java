package com.beco.colascr.transferencias.sincronizador.hibernate;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class FuncionPK implements Serializable {

    /** identifier field */
    private int codfuncion;

    /** identifier field */
    private int codmodulo;

    /** full constructor */
    public FuncionPK(int codfuncion, int codmodulo) {
        this.codfuncion = codfuncion;
        this.codmodulo = codmodulo;
    }

    /** default constructor */
    public FuncionPK() {
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("codfuncion", getCodfuncion())
            .append("codmodulo", getCodmodulo())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof FuncionPK) ) return false;
		FuncionPK castOther = (FuncionPK) other;
        return new EqualsBuilder()
            .append(this.getCodfuncion(), castOther.getCodfuncion())
            .append(this.getCodmodulo(), castOther.getCodmodulo())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
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

}
