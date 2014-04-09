/*
 * ===========================================================================
 * Material Propiedad CentroBeco C.A. 
 *
 * Proyecto		: CR
 * Paquete		: com.becoblohm.cr.extensiones
 * Programa		: PDAFactory.java
 * Creado por	: Varios Autores
 * Creado en 	: 10-aug-2009
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
 * Clase: PDAFactory
 * </pre>
 * <p>
 * <a href="PDAFactory.java.html"><i>View Source</i></a>
 * </p>
 * 
 */
public class PDAFactory extends CRExtensionFactory{

	/**
	 * @since 10-aug-2009
	 * 
	 */
	public PDAFactory() {
		super();
	}

	/* (sin Javadoc)
	 * @see com.becoblohm.cr.extensiones.CRExtensionFactory#getDefaultImplClass()
	 * @return
	 * @since 10-aug-2009
	 */
	public String getDefaultImplClass() {
		return "com.becoblohm.cr.extensiones.impl.DefaultPDA";
	}

	/* (sin Javadoc)
	 * @see com.becoblohm.cr.extensiones.CRExtensionFactory#getExtensionIntfName()
	 * @return
	 * @since 10-aug-2009
	 */
	public String getExtensionIntfName() {
		return "PDA";
	}
	
	public PDA getPDAInstance() {
		return (PDA)getExtensionInstance();
	}
	
}
