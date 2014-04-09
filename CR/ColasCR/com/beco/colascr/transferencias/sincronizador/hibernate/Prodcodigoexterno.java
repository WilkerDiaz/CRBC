package com.beco.colascr.transferencias.sincronizador.hibernate;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class Prodcodigoexterno implements Serializable {

	/** identifier field */
	private String codexterno;

	/** persistent field */
	private String codproducto;

	/** persistent field */
	private Date actualizacion;

	/** 
	 * Default constructor 
	 * @since Tue Feb 15 08:03:32 GMT-04:00 2005
	 */
	public Prodcodigoexterno () {
	}

	/**
	 * Constructor for primary key
	 * @param _codexterno an java.lang.String object
	 * @since Tue Feb 15 08:03:32 GMT-04:00 2005 
	 */
	public Prodcodigoexterno (String codexterno, String codproducto, Date actualizacion) {
		this.codexterno = codexterno;
		this.codproducto = codproducto;
		this.actualizacion = actualizacion;
	}

	/** 
	 * @see java.lang.Object#equals(java.lang.Object)
	  * @since Tue Feb 15 08:03:32 GMT-04:00 2005
	 */
	public boolean equals (Object obj) {
		if ( !(obj instanceof Prodcodigoexterno) ) return false;
		Prodcodigoexterno castOther = (Prodcodigoexterno) obj;
		return new EqualsBuilder()
			.append(this.getCodexterno(), castOther.getCodexterno())
			.isEquals();
	}


	/** 
	 * @see java.lang.Object#hashCode()
	  * @since Tue Feb 15 08:03:32 GMT-04:00 2005
	 */
	public int hashCode () {
		return new HashCodeBuilder()
			.append(getCodexterno())
			.toHashCode();
	}


	/** 
	 * @see java.lang.Object#toString()
	  * @since Tue Feb 15 08:03:32 GMT-04:00 2005
	 */
	public String toString() {
			if (null == this.getCodexterno()) {
				return super.toString();
			}
			String hashStr = this.getClass().getName() + ":" + this.getCodexterno().toString();
			return hashStr;
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
	public String getCodexterno() {
		return codexterno;
	}

	/**
	 * @return
	 */
	public String getCodproducto() {
		return codproducto;
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
	public void setCodexterno(String string) {
		codexterno = string;
	}

	/**
	 * @param string
	 */
	public void setCodproducto(String string) {
		codproducto = string;
	}

}