/*
 * $$Id: BaseProdcodigoexterno.java,v 1.2 2005/03/10 15:54:39 programa8 Exp $$
 * ===========================================================================
 * Material Propiedad SuperFerreter�a EPA C.A. 
 *
 * Creado por	: Superferreter�a EPA / Hibernate Synchronizer
 * Creado en 	: Tue Feb 15 08:03:32 GMT-04:00 2005
 * (C) Copyright SuperFerreter�a EPA C.A. Todos los Derechos Reservados
 * 
 * ---------------------------------------------------------------------------
 * Actualizaciones:
 * ---------------------------------------------------------------------------
 * $$Log: BaseProdcodigoexterno.java,v $
 * $Revision 1.2  2005/03/10 15:54:39  programa8
 * $CR al 10/03/2005. Merging desde branch STABLE
 * $
 * $Revision 1.1.2.2  2005/03/07 12:54:35  programa8
 * $Integraci�n Versiones Estable e Inestable al 07/03/2005
 * $
 * $Revision 1.1.4.1  2005/02/28 18:39:11  programa8
 * $Version Inestable al 28/02/2005
 * $    *-Preparaci�n para trabajar sin administrador de ventanas.
 * $    *-Reordenamiento de GUI
 * $    *-Mejoras en scroll y pantallas de pagos
 * $    *- Mantenimiento de estado en Cliente en Espera
 * $    *- Avisos del sistema manejados por la aplicacion
 * $    *- Desbloqueo de caja por otros usuarios reparado.
 * $
 * $Revision 1.1.2.1  2005/02/15 17:43:39  acastillo
 * $Ajuste de estructura prodcodigoexterno
 * $$
 * ===========================================================================
 */

package com.becoblohm.cr.sincronizador.hibernate.base;

import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * This is an object that contains data related to the prodcodigoexterno table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @author Hibernate Synchronizer - $$Author: programa8 $$
 * @version $$Revision: 1.2 $$<br>$$Date: 2005/03/10 15:54:39 $$
 * @since Tue Feb 15 08:03:32 GMT-04:00 2005
 * @hibernate.class
 *    table="prodcodigoexterno" schema=""
 */
/**
 * This class has been automatically generated by Hibernate Synchronizer.
 * For more information or documentation, visit The Hibernate Synchronizer page
 * at http://www.binamics.com/hibernatesync or contact Joe Hudson at joe@binamics.com.
 */
public abstract class BaseProdcodigoexterno  implements Serializable {


	/** Log para este objeto. */
	protected static Log log = LogFactory.getLog(BaseProdcodigoexterno.class);
	
	/** static reference to class property name:  codproducto*/
	public static String PROP_CODPRODUCTO = "codproducto";
	/** static reference to class property name:  codexterno*/
	public static String PROP_CODEXTERNO = "codexterno";

	/** Value to indicate that hashCode needs to be regenerated */
	private int hashCode = Integer.MIN_VALUE;

	/** primary key */
	private java.lang.String _codexterno;

	// fields
	private java.lang.String _codproducto;


	/** 
	 * Empty Constructor and initializer
	  * @since Tue Feb 15 08:03:32 GMT-04:00 2005
	 */
	public BaseProdcodigoexterno () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 * @param _codexterno an java.lang.String
	  * @since Tue Feb 15 08:03:32 GMT-04:00 2005
	 */
	public BaseProdcodigoexterno (java.lang.String _codexterno) {
		this.setCodexterno(_codexterno);
		initialize();
	}

	/** 
	  * This method will be called by constructors, override for your initializations 
 	  * (for example, default values for new objects)
	  * @since Tue Feb 15 08:03:32 GMT-04:00 2005
      */
	protected void initialize () {
		// Put your code here
	}



    /** 
     * Return the unique identifier of this class 
	 * @return java.lang.String  the unique identifier of this class  
     * @hibernate.id 
     *  generator-class="assigned" 
     * @hibernate.column 
                                                                                                          	  * @since Tue Feb 15 08:03:32 GMT-04:00 2005
     */
	public java.lang.String getCodexterno () {
		return this._codexterno;
	}

	/**
	 * Set the unique identifier of this class
	 * @param _codexterno the new ID
	  * @since Tue Feb 15 08:03:32 GMT-04:00 2005
	 */
	public void setCodexterno (java.lang.String _codexterno) {
		this._codexterno = _codexterno;
		this.hashCode = Integer.MIN_VALUE;
	}


	/**
	 * Return the value associated with the column: codproducto
	 * @return java.lang.String the codproducto
	  * @since Tue Feb 15 08:03:32 GMT-04:00 2005
	 */
	public java.lang.String getCodproducto () {
		return this._codproducto;
	}

	/**
	 * Set the value related to the column: codproducto
	 * @param _codproducto the codproducto value
	  * @since Tue Feb 15 08:03:32 GMT-04:00 2005
	 */
	public void setCodproducto (java.lang.String _codproducto) {
		this._codproducto = _codproducto;
	}


	/** 
	 * @see java.lang.Object#equals(java.lang.Object)
	  * @since Tue Feb 15 08:03:32 GMT-04:00 2005
	 */
	public boolean equals (Object obj) {
		if (null == obj) {
			return false;
		}
		if (!(obj instanceof com.becoblohm.cr.sincronizador.hibernate.base.BaseProdcodigoexterno)) {
			return false;
		}
			com.becoblohm.cr.sincronizador.hibernate.base.BaseProdcodigoexterno mObj = (com.becoblohm.cr.sincronizador.hibernate.base.BaseProdcodigoexterno) obj;
			if (null == this.getCodexterno() || null == mObj.getCodexterno()) {
				return false;
			}
			return (this.getCodexterno().equals(mObj.getCodexterno()));
	}


	/** 
	 * @see java.lang.Object#hashCode()
	  * @since Tue Feb 15 08:03:32 GMT-04:00 2005
	 */
	public int hashCode () {
		if (Integer.MIN_VALUE == this.hashCode) {
			if (null == this.getCodexterno()) {
				return super.hashCode();
			}
			String hashStr = this.getClass().getName() + ":" + this.getCodexterno().hashCode();
			this.hashCode = hashStr.hashCode();
		}
		return this.hashCode;
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


}