/*
 * $$Id: Prodcodigoexterno.java,v 1.3 2005/03/10 15:50:53 programa8 Exp $$
 * ===========================================================================
 * Material Propiedad SuperFerretería EPA C.A. 
 *
 * Creado por	: Superferretería EPA / Hibernate Synchronizer
 * Creado en 	: Tue Feb 15 08:03:32 GMT-04:00 2005
 * (C) Copyright SuperFerretería EPA C.A. Todos los Derechos Reservados
 * 
 * ---------------------------------------------------------------------------
 * Actualizaciones:
 * ---------------------------------------------------------------------------
 * $$Log: Prodcodigoexterno.java,v $
 * $Revision 1.3  2005/03/10 15:50:53  programa8
 * $CR al 10/03/2005. Merging desde branch STABLE
 * $
 * $Revision 1.2.4.2  2005/03/07 12:55:07  programa8
 * $Integración Versiones Estable e Inestable al 07/03/2005
 * $
 * $Revision 1.2.6.1  2005/02/28 18:13:23  programa8
 * $Version Inestable al 28/02/2005
 * $    *-Preparación para trabajar sin administrador de ventanas.
 * $    *-Reordenamiento de GUI
 * $    *-Mejoras en scroll y pantallas de pagos
 * $    *- Mantenimiento de estado en Cliente en Espera
 * $    *- Avisos del sistema manejados por la aplicacion
 * $    *- Desbloqueo de caja por otros usuarios reparado.
 * $
 * $Revision 1.2.4.1  2005/02/15 17:43:39  acastillo
 * $Ajuste de estructura prodcodigoexterno
 * $$
 * ===========================================================================
 */

package com.becoblohm.cr.sincronizador.hibernate;

import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.becoblohm.cr.sincronizador.hibernate.base.BaseProdcodigoexterno;

/**
 * This is the object class that relates to the prodcodigoexterno table.
 * Any customizations belong here.
 *
 * @author Hibernate Synchronizer - $$Author: programa8 $$
 * @version $$Revision: 1.3 $$<br>$$Date: 2005/03/10 15:50:53 $$
 * @since Tue Feb 15 08:03:32 GMT-04:00 2005 \
 */
public class Prodcodigoexterno extends BaseProdcodigoexterno {
	/** Log para este objeto. */
	private static Log log = LogFactory.getLog(Prodcodigoexterno.class);
	
	private String codproducto;
	private String codexterno;
    private Date actualizacion;
    
	/** 
	 * Default constructor 
	 * @since Tue Feb 15 08:03:32 GMT-04:00 2005
	 */
	public Prodcodigoexterno () {
		super();
	}

	/**
	 * Constructor for primary key
	 * @param _codexterno an java.lang.String object
	 * @since Tue Feb 15 08:03:32 GMT-04:00 2005 
	 */
	public Prodcodigoexterno (java.lang.String _codexterno) {
		super(_codexterno);
	}

	/** full constructor */
	public Prodcodigoexterno(String codproducto, String codexterno, Date actualizacion) {
		this.codproducto = codproducto;
		this.codexterno = codexterno;
		this.actualizacion = actualizacion;
	}
	
	public String toString() {
		return new ToStringBuilder(this)
			.append("codexterno", getCodexterno())
			.toString();
	}

	public boolean equals(Object other) {
		if ( !(other instanceof Prodcodigoexterno) ) return false;
		Prodcodigoexterno castOther = (Prodcodigoexterno) other;
		return new EqualsBuilder()
			.append(this.getCodexterno(), castOther.getCodexterno())
			.isEquals();
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