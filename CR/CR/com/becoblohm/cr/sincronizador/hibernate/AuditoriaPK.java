/*
 * $$Id: AuditoriaPK.java,v 1.4 2005/03/10 15:50:54 programa8 Exp $$
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
 * $$Log: AuditoriaPK.java,v $
 * $Revision 1.4  2005/03/10 15:50:54  programa8
 * $CR al 10/03/2005. Merging desde branch STABLE
 * $
 * $Revision 1.3.4.3  2005/03/07 13:55:02  programa8
 * $Integración Versiones Estable e Inestable al 07/03/2005
 * $
 * $Revision 1.3.6.3  2005/03/02 18:57:47  programa8
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

import com.becoblohm.cr.sincronizador.hibernate.base.BaseAuditoriaPK;

public class AuditoriaPK extends BaseAuditoriaPK {
	/** Log para este objeto. */
	private static Log log = LogFactory.getLog(AuditoriaPK.class);
	
/*[CONSTRUCTOR MARKER BEGIN]*/
	/** 
	 * Default constructor 
	  * @since Wed Mar 02 14:08:23 GMT-04:00 2005
	 */
	public AuditoriaPK () {
		super();
	}
	
	/** 
	 * Constructor 
	 * @param _idauditoria the java.lang.Long object value
	 * @param _numcaja the java.lang.Short object value
	 * @param _numtienda the java.lang.Short object value
	  * @since Wed Mar 02 14:08:23 GMT-04:00 2005
	 */
	public AuditoriaPK (
		java.lang.Long _idauditoria,
		java.lang.Short _numcaja,
		java.lang.Short _numtienda) {

		super (
		_idauditoria,
		_numcaja,
		_numtienda);
	}		/*[CONSTRUCTOR MARKER END]*/
}