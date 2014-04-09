/*
 * $$Id: Auditoria.java,v 1.4 2005/03/10 15:50:54 programa8 Exp $$
 * ===========================================================================
 * Material Propiedad SuperFerretería EPA C.A. 
 *
 * Creado por	: Superferretería EPA / Hibernate Synchronizer
 * Creado en 	: Wed Dec 01 18:36:31 GMT-04:00 2004
 * (C) Copyright SuperFerretería EPA C.A. Todos los Derechos Reservados
 * 
 * ---------------------------------------------------------------------------
 * Actualizaciones:
 * ---------------------------------------------------------------------------
 * $$Log: Auditoria.java,v $
 * $Revision 1.4  2005/03/10 15:50:54  programa8
 * $CR al 10/03/2005. Merging desde branch STABLE
 * $
 * $Revision 1.3.4.3  2005/03/07 13:55:01  programa8
 * $Integración Versiones Estable e Inestable al 07/03/2005
 * $
 * $Revision 1.3.6.3  2005/03/02 18:57:33  programa8
 * $Cambio en tabla Auditoria - idauditoria promovido a BIGINT
 * $
 * $Revision 1.3.4.2  2005/03/02 18:23:13  programa8
 * $Cambio en tabla Auditoria - idauditoria promovido a BIGINT
 * $
 * $Revision 1.3.4.1  2005/01/04 18:23:02  acastillo
 * $Integracion CR EPA3 - Costa Rica
 * $
 * $Revision 1.3.8.1  2005/01/04 16:07:16  acastillo
 * $Integracion CR EPA3 - Costa Rica
 * $
 * $Revision 1.3.6.1  2004/12/17 16:09:25  acastillo
 * $Cambios Prueba EPA 3
 * $$
 * ===========================================================================
 */

package com.becoblohm.cr.sincronizador.hibernate;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.becoblohm.cr.sincronizador.hibernate.base.BaseAuditoria;

/**
 * This is the object class that relates to the auditoria table.
 * Any customizations belong here.
 *
 * @author Hibernate Synchronizer - $$Author: programa8 $$
 * @version $$Revision: 1.4 $$<br>$$Date: 2005/03/10 15:50:54 $$
 * @since Wed Dec 01 18:36:31 GMT-04:00 2004 \
 */
public class Auditoria extends BaseAuditoria {
	/** Log para este objeto. */
	private static Log log = LogFactory.getLog(Auditoria.class);
	
/*[CONSTRUCTOR MARKER BEGIN]*/
	/** 
	 * Default constructor 
	 * @since Wed Mar 02 14:08:23 GMT-04:00 2005
	 */
	public Auditoria () {
		super();
	}

	/**
	 * Constructor for primary key
	 * @param _id an com.becoblohm.cr.sincronizador.hibernate.AuditoriaPK object
	 * @since Wed Mar 02 14:08:23 GMT-04:00 2005 
	 */
	public Auditoria (com.becoblohm.cr.sincronizador.hibernate.AuditoriaPK _id) {
		super(_id);
	}

	/**
	 * Constructor for required fields
	 * @param _id an com.becoblohm.cr.sincronizador.hibernate.AuditoriaPK object
	 * @param _mensaje an java.lang.String object
	 * @param _regactualizado an java.lang.String object
	 * @param _nivelauditoria an java.lang.String object
	 * @param _codmodulo an java.lang.Integer object
	 * @param _codfuncion an java.lang.Integer object
	 * @param _fecha an java.util.Date object
	 * @param _codusuario an java.lang.String object
	 * @param _tiporegistro an java.lang.String object
	  * @since Wed Mar 02 14:08:23 GMT-04:00 2005
	 */
	public Auditoria (
		com.becoblohm.cr.sincronizador.hibernate.AuditoriaPK _id,
		java.lang.String _mensaje,
		java.lang.String _regactualizado,
		java.lang.String _nivelauditoria,
		java.lang.Integer _codmodulo,
		java.lang.Integer _codfuncion,
		java.util.Date _fecha,
		java.lang.String _codusuario,
		java.lang.String _tiporegistro) {

		super (
			_id,
			_mensaje,
			_regactualizado,
			_nivelauditoria,
			_codmodulo,
			_codfuncion,
			_fecha,
			_codusuario,
			_tiporegistro);
	}
/*[CONSTRUCTOR MARKER END]*/
}