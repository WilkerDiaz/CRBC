/*
 * $$Id: BaseAuditoria.java,v 1.2 2005/03/10 15:51:14 programa8 Exp $$
 * ===========================================================================
 * Material Propiedad SuperFerreter�a EPA C.A. 
 *
 * Creado por	: Superferreter�a EPA / Hibernate Synchronizer
 * Creado en 	: Wed Mar 02 14:08:23 GMT-04:00 2005
 * (C) Copyright SuperFerreter�a EPA C.A. Todos los Derechos Reservados
 * 
 * ---------------------------------------------------------------------------
 * Actualizaciones:
 * ---------------------------------------------------------------------------
 * $$Log: BaseAuditoria.java,v $
 * $Revision 1.2  2005/03/10 15:51:14  programa8
 * $CR al 10/03/2005. Merging desde branch STABLE
 * $
 * $Revision 1.1.4.3  2005/03/07 13:55:31  programa8
 * $Integraci�n Versiones Estable e Inestable al 07/03/2005
 * $
 * $Revision 1.1.6.4  2005/03/02 18:59:38  programa8
 * $Cambio en tabla Auditoria - idauditoria promovido a BIGINT
 * $
 * $Revision 1.1.4.2  2005/03/02 18:23:12  programa8
 * $Cambio en tabla Auditoria - idauditoria promovido a BIGINT
 * $$
 * ===========================================================================
 */

package com.becoblohm.cr.sincronizador.hibernate.base;

import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @hibernate.class table="auditoria" schema=""
 *
 * @author Hibernate Synchronizer - $$Author: programa8 $$
 * @version $$Revision: 1.2 $$<br>$$Date: 2005/03/10 15:51:14 $$
 * @since Wed Mar 02 14:08:23 GMT-04:00 2005
 * @hibernate.class
 *    table="auditoria" schema=""
 */
/**
 * This class has been automatically generated by Hibernate Synchronizer.
 * For more information or documentation, visit The Hibernate Synchronizer page
 * at http://www.binamics.com/hibernatesync or contact Joe Hudson at joe@binamics.com.
 */
public abstract class BaseAuditoria  implements Serializable, EntidadMarcable {


	/** Log para este objeto. */
	protected static Log log = LogFactory.getLog(BaseAuditoria.class);
	
	/** static reference to class property name:  Codfuncion*/
	public static String PROP_CODFUNCION = "Codfuncion";
	/** static reference to class property name:  Codmodulo*/
	public static String PROP_CODMODULO = "Codmodulo";
	/** static reference to class property name:  Fecha*/
	public static String PROP_FECHA = "Fecha";
	/** static reference to class property name:  Nivelauditoria*/
	public static String PROP_NIVELAUDITORIA = "Nivelauditoria";
	/** static reference to class property name:  Tiporegistro*/
	public static String PROP_TIPOREGISTRO = "Tiporegistro";
	/** static reference to class property name:  Codusuario*/
	public static String PROP_CODUSUARIO = "Codusuario";
	/** static reference to class property name:  Numtransaccion*/
	public static String PROP_NUMTRANSACCION = "Numtransaccion";
	/** static reference to class property name:  Regactualizado*/
	public static String PROP_REGACTUALIZADO = "Regactualizado";
	/** static reference to class property name:  Mensaje*/
	public static String PROP_MENSAJE = "Mensaje";
	/** static reference to class property name:  Id*/
	public static String PROP_ID = "Id";

	/** Value to indicate that hashCode needs to be regenerated */
	private int hashCode = Integer.MIN_VALUE;

	/** primary key */
	private com.becoblohm.cr.sincronizador.hibernate.AuditoriaPK _id;

	// fields
	/*
	 * @hibernate.property column = "mensaje" type="string"
	 * not-null="true"
	 * length="100"
	 */
	private java.lang.String _mensaje;
	/*
	 * @hibernate.property column = "regactualizado" type="string"
	 * not-null="true"
	 * length="1"
	 */
	private java.lang.String _regactualizado;
	/*
	 * @hibernate.property column = "nivelauditoria" type="string"
	 * not-null="true"
	 * length="1"
	 */
	private java.lang.String _nivelauditoria;
	/*
	 * @hibernate.property column = "numtransaccion" type="integer"
	 * not-null="false"
	 * length="10"
	 */
	private java.lang.Integer _numtransaccion;
	/*
	 * @hibernate.property column = "codmodulo" type="integer"
	 * not-null="true"
	 */
	private java.lang.Integer _codmodulo;
	/*
	 * @hibernate.property column = "codfuncion" type="integer"
	 * not-null="true"
	 */
	private java.lang.Integer _codfuncion;
	/*
	 * @hibernate.property column = "fecha" type="timestamp"
	 * not-null="true"
	 */
	private java.util.Date _fecha;
	/*
	 * @hibernate.property column = "codusuario" type="string"
	 * not-null="true"
	 */
	private java.lang.String _codusuario;
	/*
	 * @hibernate.property column = "tiporegistro" type="string"
	 * not-null="true"
	 */
	private java.lang.String _tiporegistro;


	/** 
	 * Empty Constructor and initializer
	  * @since Wed Mar 02 14:08:23 GMT-04:00 2005
	 */
	public BaseAuditoria () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 * @param _id an com.becoblohm.cr.sincronizador.hibernate.AuditoriaPK
	  * @since Wed Mar 02 14:08:23 GMT-04:00 2005
	 */
	public BaseAuditoria (com.becoblohm.cr.sincronizador.hibernate.AuditoriaPK _id) {
		this.setId(_id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 * @param _id an com.becoblohm.cr.sincronizador.hibernate.AuditoriaPK
	* @param _mensaje an java.lang.String
	* @param _regactualizado an java.lang.String
	* @param _nivelauditoria an java.lang.String
	* @param _codmodulo an java.lang.Integer
	* @param _codfuncion an java.lang.Integer
	* @param _fecha an java.util.Date
	* @param _codusuario an java.lang.String
	* @param _tiporegistro an java.lang.String
	  * @since Wed Mar 02 14:08:23 GMT-04:00 2005
	 */
	public BaseAuditoria (
		com.becoblohm.cr.sincronizador.hibernate.AuditoriaPK _id,
		java.lang.String _mensaje,
		java.lang.String _regactualizado,
		java.lang.String _nivelauditoria,
		java.lang.Integer _codmodulo,
		java.lang.Integer _codfuncion,
		java.util.Date _fecha,
		java.lang.String _codusuario,
		java.lang.String _tiporegistro) {

		this.setId(_id);
		this.setMensaje(_mensaje);
		this.setRegactualizado(_regactualizado);
		this.setNivelauditoria(_nivelauditoria);
		this.setCodmodulo(_codmodulo);
		this.setCodfuncion(_codfuncion);
		this.setFecha(_fecha);
		this.setCodusuario(_codusuario);
		this.setTiporegistro(_tiporegistro);
		initialize();
	}

	/** 
	  * This method will be called by constructors, override for your initializations 
 	  * (for example, default values for new objects)
	  * @since Wed Mar 02 14:08:23 GMT-04:00 2005
      */
	protected void initialize () {
		// Put your code here
	}



    /** 
     * Return the unique identifier of this class 
	 * @return com.becoblohm.cr.sincronizador.hibernate.AuditoriaPK  the unique identifier of this class  
     * @hibernate.id 
	  * @since Wed Mar 02 14:08:23 GMT-04:00 2005
     */
	public com.becoblohm.cr.sincronizador.hibernate.AuditoriaPK getId () {
		return this._id;
	}

	/**
	 * Set the unique identifier of this class
	 * @param _id the new ID
	  * @since Wed Mar 02 14:08:23 GMT-04:00 2005
	 */
	public void setId (com.becoblohm.cr.sincronizador.hibernate.AuditoriaPK _id) {
		this._id = _id;
		this.hashCode = Integer.MIN_VALUE;
	}


	/**
	 * Return the value associated with the column: mensaje
	 * @return java.lang.String the mensaje
	  * @since Wed Mar 02 14:08:23 GMT-04:00 2005
	 */
	public java.lang.String getMensaje () {
		return this._mensaje;
	}

	/**
	 * Set the value related to the column: mensaje
	 * @param _mensaje the mensaje value
	  * @since Wed Mar 02 14:08:23 GMT-04:00 2005
	 */
	public void setMensaje (java.lang.String _mensaje) {
		this._mensaje = _mensaje;
	}


	/**
	 * Return the value associated with the column: regactualizado
	 * @return java.lang.String the regactualizado
	  * @since Wed Mar 02 14:08:23 GMT-04:00 2005
	 */
	public java.lang.String getRegactualizado () {
		return this._regactualizado;
	}

	/**
	 * Set the value related to the column: regactualizado
	 * @param _regactualizado the regactualizado value
	  * @since Wed Mar 02 14:08:23 GMT-04:00 2005
	 */
	public void setRegactualizado (java.lang.String _regactualizado) {
		this._regactualizado = _regactualizado;
	}


	/**
	 * Return the value associated with the column: nivelauditoria
	 * @return java.lang.String the nivelauditoria
	  * @since Wed Mar 02 14:08:23 GMT-04:00 2005
	 */
	public java.lang.String getNivelauditoria () {
		return this._nivelauditoria;
	}

	/**
	 * Set the value related to the column: nivelauditoria
	 * @param _nivelauditoria the nivelauditoria value
	  * @since Wed Mar 02 14:08:23 GMT-04:00 2005
	 */
	public void setNivelauditoria (java.lang.String _nivelauditoria) {
		this._nivelauditoria = _nivelauditoria;
	}


	/**
	 * Return the value associated with the column: numtransaccion
	 * @return java.lang.Integer the numtransaccion
	  * @since Wed Mar 02 14:08:23 GMT-04:00 2005
	 */
	public java.lang.Integer getNumtransaccion () {
		return this._numtransaccion;
	}

	/**
	 * Set the value related to the column: numtransaccion
	 * @param _numtransaccion the numtransaccion value
	  * @since Wed Mar 02 14:08:23 GMT-04:00 2005
	 */
	public void setNumtransaccion (java.lang.Integer _numtransaccion) {
		this._numtransaccion = _numtransaccion;
	}


	/**
	 * Return the value associated with the column: codmodulo
	 * @return java.lang.Integer the codmodulo
	  * @since Wed Mar 02 14:08:23 GMT-04:00 2005
	 */
	public java.lang.Integer getCodmodulo () {
		return this._codmodulo;
	}

	/**
	 * Set the value related to the column: codmodulo
	 * @param _codmodulo the codmodulo value
	  * @since Wed Mar 02 14:08:23 GMT-04:00 2005
	 */
	public void setCodmodulo (java.lang.Integer _codmodulo) {
		this._codmodulo = _codmodulo;
	}


	/**
	 * Return the value associated with the column: codfuncion
	 * @return java.lang.Integer the codfuncion
	  * @since Wed Mar 02 14:08:23 GMT-04:00 2005
	 */
	public java.lang.Integer getCodfuncion () {
		return this._codfuncion;
	}

	/**
	 * Set the value related to the column: codfuncion
	 * @param _codfuncion the codfuncion value
	  * @since Wed Mar 02 14:08:23 GMT-04:00 2005
	 */
	public void setCodfuncion (java.lang.Integer _codfuncion) {
		this._codfuncion = _codfuncion;
	}


	/**
	 * Return the value associated with the column: fecha
	 * @return java.util.Date the fecha
	  * @since Wed Mar 02 14:08:23 GMT-04:00 2005
	 */
	public java.util.Date getFecha () {
		return this._fecha;
	}

	/**
	 * Set the value related to the column: fecha
	 * @param _fecha the fecha value
	  * @since Wed Mar 02 14:08:23 GMT-04:00 2005
	 */
	public void setFecha (java.util.Date _fecha) {
		this._fecha = _fecha;
	}


	/**
	 * Return the value associated with the column: codusuario
	 * @return java.lang.String the codusuario
	  * @since Wed Mar 02 14:08:23 GMT-04:00 2005
	 */
	public java.lang.String getCodusuario () {
		return this._codusuario;
	}

	/**
	 * Set the value related to the column: codusuario
	 * @param _codusuario the codusuario value
	  * @since Wed Mar 02 14:08:23 GMT-04:00 2005
	 */
	public void setCodusuario (java.lang.String _codusuario) {
		this._codusuario = _codusuario;
	}


	/**
	 * Return the value associated with the column: tiporegistro
	 * @return java.lang.String the tiporegistro
	  * @since Wed Mar 02 14:08:23 GMT-04:00 2005
	 */
	public java.lang.String getTiporegistro () {
		return this._tiporegistro;
	}

	/**
	 * Set the value related to the column: tiporegistro
	 * @param _tiporegistro the tiporegistro value
	  * @since Wed Mar 02 14:08:23 GMT-04:00 2005
	 */
	public void setTiporegistro (java.lang.String _tiporegistro) {
		this._tiporegistro = _tiporegistro;
	}


	/** 
	 * @see java.lang.Object#equals(java.lang.Object)
	  * @since Wed Mar 02 14:08:23 GMT-04:00 2005
	 */
	public boolean equals (Object obj) {
		if (null == obj) {
			return false;
		}
		if (!(obj instanceof com.becoblohm.cr.sincronizador.hibernate.base.BaseAuditoria)) {
			return false;
		}
			com.becoblohm.cr.sincronizador.hibernate.base.BaseAuditoria mObj = (com.becoblohm.cr.sincronizador.hibernate.base.BaseAuditoria) obj;
			if (null == this.getId() || null == mObj.getId()) {
				return false;
			}
			return (this.getId().equals(mObj.getId()));
	}


	/** 
	 * @see java.lang.Object#hashCode()
	  * @since Wed Mar 02 14:08:23 GMT-04:00 2005
	 */
	public int hashCode () {
		if (Integer.MIN_VALUE == this.hashCode) {
			if (null == this.getId()) {
				return super.hashCode();
			}
			String hashStr = this.getClass().getName() + ":" + this.getId().hashCode();
			this.hashCode = hashStr.hashCode();
		}
		return this.hashCode;
	}


	/** 
	 * @see java.lang.Object#toString()
	  * @since Wed Mar 02 14:08:23 GMT-04:00 2005
	 */
	public String toString() {
			if (null == this.getId()) {
				return super.toString();
			}
			String hashStr = this.getClass().getName() + ":" + this.getId().toString();
			return hashStr;
	}


}