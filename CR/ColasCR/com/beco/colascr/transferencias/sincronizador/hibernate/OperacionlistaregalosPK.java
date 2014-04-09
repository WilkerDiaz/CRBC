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
public class OperacionlistaregalosPK implements Serializable{
	
	/** identifier field */
	private int numoperacion;
	
	/** identifier field */
	private int codlista;
	
	/** identifier field */
	private String codproducto;
	
	/** identifier field */
	private int correlativoitem;
	
	/** default constructor */
	public OperacionlistaregalosPK(){
	}
	
	/** full constructor */
	public OperacionlistaregalosPK(int numoperacion,int codlista,String codproducto, int correlativoitem){
		this.numoperacion = numoperacion;
		this.codlista = codlista;
		this.codproducto = codproducto;
		this.correlativoitem = correlativoitem;
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
	 * @return
	 */
	public int getNumoperacion() {
		return numoperacion;
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

	/**
	 * @param i
	 */
	public void setNumoperacion(int i) {
		numoperacion = i;
	}

	public String toString() {
		 return new ToStringBuilder(this)
			.append("numoperacion", getNumoperacion())
			.append("codlista", getCodlista())
			.append("codproducto", getCodproducto())
			.append("correlativoitem", getCorrelativoitem())
			.toString();
	 }

	 public boolean equals(Object other) {
		 if ( !(other instanceof OperacionlistaregalosPK) ) return false;
		 	OperacionlistaregalosPK castOther = (OperacionlistaregalosPK) other;
		 return new EqualsBuilder()
			.append(this.getNumoperacion(), castOther.getNumoperacion())
			.append(this.getCodlista(), castOther.getCodlista())
			.append(this.getCodproducto(), castOther.getCodproducto())
			.append(this.getCorrelativoitem(), castOther.getCorrelativoitem())
			.isEquals();
	 }

	 public int hashCode() {
		 return new HashCodeBuilder()
			.append(getNumoperacion())
			.append(getCodlista())
			.append(getCodproducto())
			.append(getCorrelativoitem())
			.toHashCode();
	 }

	public int getCorrelativoitem() {
		return correlativoitem;
	}

	public void setCorrelativoitem(int correlativoitem) {
		this.correlativoitem = correlativoitem;
	}
}
