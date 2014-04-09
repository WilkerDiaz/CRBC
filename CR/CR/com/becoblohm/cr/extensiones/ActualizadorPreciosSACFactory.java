/*
 * ===========================================================================
 * Material Propiedad CentroBeco C.A. 
 *
 * Proyecto		: CR
 * Paquete		: com.becoblohm.cr.extensiones
 * Programa		: ActualizadorPreciosFactory.java
 * Creado por	: Jesus Graterol
 * Creado en 	: 30-may-2008 10:00:00
 * (C) Copyright 2005 CentroBeco C.A. Todos los Derechos Reservados
 * ---------------------------------------------------------------------------
 * Actualizaciones:
 * ---------------------------------------------------------------------------
 * ===========================================================================
 */
package com.becoblohm.cr.extensiones;

/**
 * <pre>
 * Proyecto: CR 
 * Clase: ActualizadorPreciosSACFactory
 * </pre>
 * <p>
 * <a href="ActualizadorPreciosSACFactory.java.html"><i>View Source</i></a>
 * </p>
 * 
 */
public class ActualizadorPreciosSACFactory extends CRExtensionFactory{

	/**
	 * @since 30-may-2008
	 * 
	 */
	public ActualizadorPreciosSACFactory() {
		super();
	}

	/* (sin Javadoc)
	 * @see com.becoblohm.cr.extensiones.CRExtensionFactory#getDefaultImplClass()
	 * @return
	 * @since 30-may-2008
	 */
	public String getDefaultImplClass() {
		return "com.becoblohm.cr.extensiones.impl.DefaultActualizadorPreciosSAC";
	}

	/* (sin Javadoc)
	 * @see com.becoblohm.cr.extensiones.CRExtensionFactory#getExtensionIntfName()
	 * @return
	 * @since 30-may-2008
	 */
	public String getExtensionIntfName() {
		return "ActualizadorPreciosSAC";
	}
	
	public ActualizadorPreciosSAC getActualizadorPreciosSACInstance() {
		return (ActualizadorPreciosSAC)getExtensionInstance();
	}
	
}
