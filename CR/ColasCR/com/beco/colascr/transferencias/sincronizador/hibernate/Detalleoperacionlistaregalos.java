/*
 * Creado el 14/11/2006
 *
 * Para cambiar la plantilla para este archivo generado vaya a
 * Ventana&gt;Preferencias&gt;Java&gt;Generación de código&gt;Código y comentarios
 */
package com.beco.colascr.transferencias.sincronizador.hibernate;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * @author rabreu
 *
 * Para cambiar la plantilla para este comentario de tipo generado vaya a
 * Ventana&gt;Preferencias&gt;Java&gt;Generación de código&gt;Código y comentarios
 */
public class Detalleoperacionlistaregalos implements Serializable {

	private static final long serialVersionUID = 1L;

	/** identifier field */
	private com.beco.colascr.transferencias.sincronizador.hibernate.DetalleoperacionlistaregalosPK comp_id;

	/** persistent field */
	private double monto;

	/** persistent field */
	private double montovuelto;
		
	/** nullable persistent field */
	private Date actualizacion;
	
	/** default constructor */
	public Detalleoperacionlistaregalos(){
	}
	
	/** full constructor */
	public Detalleoperacionlistaregalos(com.beco.colascr.transferencias.sincronizador.hibernate.DetalleoperacionlistaregalosPK comp_id,Date actualizacion){
		this.comp_id = comp_id;
		this.actualizacion = actualizacion;		
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
	public double getMonto() {
		return monto;
	}

	/**
	 * @return
	 */
	public double getMontovuelto() {
		return montovuelto;
	}

	/**
	 * @param d
	 */
	public void setMonto(double d) {
		monto = d;
	}

	/**
	 * @param d
	 */
	public void setMontovuelto(double d) {
		montovuelto = d;
	}

	/**
	 * @return
	 */
	public com
		.beco
		.colascr
		.transferencias
		.sincronizador
		.hibernate
		.DetalleoperacionlistaregalosPK getComp_id() {
		return comp_id;
	}

	/**
	 * @param date
	 */
	public void setActualizacion(Date date) {
		actualizacion = date;
	}

	/**
	 * @param detallelistaregalosPK
	 */
	public void setComp_id(
		com
			.beco
			.colascr
			.transferencias
			.sincronizador
			.hibernate
			.DetalleoperacionlistaregalosPK detalleoperacionlistaregalosPK) {
		comp_id = detalleoperacionlistaregalosPK;
	}
	
	/* (no Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object other) {
		if (!(other instanceof Detalleoperacionlistaregalos)) return false;
		Detalleoperacionlistaregalos castOther = (Detalleoperacionlistaregalos) other;
		return new EqualsBuilder()
			.append(this.getComp_id(), castOther.getComp_id())
			.isEquals();
	}

	/* (no Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getComp_id())
			.toHashCode();
	}

	/* (no Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
			.toString();
	}

}