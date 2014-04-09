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
public class DetalleoperacionlistaregalosPK implements Serializable {

	/** identifier field */
	private int numoperacion;
	
	/** identifier field */
	private int codlista;
	
	/** identifier field */
	private String codformadepago;

	/** identifier field */
	private int correlativo;
	
	/** default constructor */
	public DetalleoperacionlistaregalosPK(){
	}
	
	/** full constructor */
	public DetalleoperacionlistaregalosPK(int numoperacion, int codlista, String codformadepago, int correlativo){
		this.numoperacion = numoperacion;
		this.codlista = codlista;
		this.codformadepago = codformadepago;
		this.correlativo = correlativo;
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
	public String getCodformadepago() {
		return codformadepago;
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
	public void setCodformadepago(String string) {
		codformadepago = string;
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
			.append("codformadepago", getCodformadepago())
			.append("correlativo", getCodformadepago())
			.toString();
	 }

	 public boolean equals(Object other) {
		 if ( !(other instanceof DetalleoperacionlistaregalosPK) ) return false;
			DetalleoperacionlistaregalosPK castOther = (DetalleoperacionlistaregalosPK) other;
		 return new EqualsBuilder()
		 	.append(this.getNumoperacion(), castOther.getNumoperacion())
			.append(this.getCodlista(), castOther.getCodlista())
			.append(this.getCodformadepago(), castOther.getCodformadepago())
			.append(this.getCorrelativo(), castOther.getCorrelativo())
			.isEquals();
	 }

	 public int hashCode() {
		 return new HashCodeBuilder()
			.append(getNumoperacion())
			.append(getCodlista())
			.append(getCodformadepago())
			.append(getCorrelativo())
			.toHashCode();
	 }

	public int getCorrelativo() {
		return correlativo;
	}

	public void setCorrelativo(int correlativo) {
		this.correlativo = correlativo;
	}

}
