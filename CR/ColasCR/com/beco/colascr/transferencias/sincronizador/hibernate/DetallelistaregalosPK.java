/*
 * Creado el 14/11/2006
 *
 * Para cambiar la plantilla para este archivo generado vaya a
 * Ventana&gt;Preferencias&gt;Java&gt;Generación de código&gt;Código y comentarios
 */
package com.beco.colascr.transferencias.sincronizador.hibernate;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * @author rabreu
 *
 * Para cambiar la plantilla para este comentario de tipo generado vaya a
 * Ventana&gt;Preferencias&gt;Java&gt;Generación de código&gt;Código y comentarios
 */
public class DetallelistaregalosPK implements Serializable {
	
	/** identifier field */
	private int codlista;
	
	/** identifier field */
	private String codproducto;
	
	/** default constructor */
	public DetallelistaregalosPK(){
	}
	
	/** full constructor */
	public DetallelistaregalosPK(int codlista,String codproducto){
		this.codlista = codlista;
		this.codproducto = codproducto;
	}
	/**
	 * @return
	 */
	public int getCodlista() {
		return codlista;
	}

	/**
	 * @return
	 */
	public String getCodproducto() {
		return codproducto;
	}

	/**
	 * @param i
	 */
	public void setCodlista(int i) {
		codlista = i;
	}

	/**
	 * @param string
	 */
	public void setCodproducto(String string) {
		codproducto = string;
	}

	public String toString() {
		 return new ToStringBuilder(this)
			.append("codlista", getCodlista())
			.append("codproducto", getCodproducto())
			.toString();
	 }

	 public boolean equals(Object other) {
		 if ( !(other instanceof DetallelistaregalosPK) ) return false;
			DetallelistaregalosPK castOther = (DetallelistaregalosPK) other;
		 return new EqualsBuilder()
			.append(this.getCodlista(), castOther.getCodlista())
			.append(this.getCodproducto(), castOther.getCodproducto())
			.isEquals();
	 }

	 public int hashCode() {
		 return new HashCodeBuilder()
			.append(getCodlista())
			.append(getCodproducto())
			.toHashCode();
	 }
}
