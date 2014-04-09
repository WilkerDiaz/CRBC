/*
 * Creado el 20-dic-2004
 *
 * Para cambiar la plantilla para este archivo generado vaya a
 * Ventana&gt;Preferencias&gt;Java&gt;Generación de código&gt;Código y comentarios
 */
package com.becoblohm.cr.extensiones;

/**
 * @author Programa8
 *
 * Para cambiar la plantilla para este comentario de tipo generado vaya a
 * Ventana&gt;Preferencias&gt;Java&gt;Generación de código&gt;Código y comentarios
 */
public class TimeProxyFactory extends CRExtensionFactory {

	public String getExtensionIntfName() {
		return "TimeProxy";
	}
	/**
	 *  Constructor
	 */
	public TimeProxyFactory() {
		super();
	}

	/* (no Javadoc)
	 * @see com.becoblohm.cr.extensiones.CRExtensionFactory#getDefaultImplClass()
	 */
	public String getDefaultImplClass() {
		return "com.becoblohm.cr.extensiones.impl.DefaultTimeProxy";
	}
	
	public TimeProxy getTimeProxyInstance() {
		return (TimeProxy)getExtensionInstance();
	}

}
