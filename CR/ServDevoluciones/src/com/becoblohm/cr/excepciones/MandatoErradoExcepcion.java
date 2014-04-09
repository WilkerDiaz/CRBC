/*
 * $Id: MandatoErradoExcepcion.java,v 1.2 2005/03/10 15:54:37 programa8 Exp $
 * ===========================================================================
 * Material Propiedad SuperFerretería EPA C.A. 
 *
 * Proyecto		: CR
 * Paquete		: com.becoblohm.cr.excepciones
 * Programa		: MandatoErradoExcepcion.java
 * Creado por	: Programa8
 * Creado en 	: 22-ene-2005 21:06:43
 * (C) Copyright 2005 SuperFerretería EPA C.A. Todos los Derechos Reservados
 * ---------------------------------------------------------------------------
 * Información de Repositorio:
 * ---------------------------------------------------------------------------
 * Rama de Desarrollo	: $Name:  $
 * Bloqueado por		: $Locker:  $
 * Estado de Revisión	: $State: Exp $
 * ---------------------------------------------------------------------------
 * Actualizaciones:
 * ---------------------------------------------------------------------------
 * $Log: MandatoErradoExcepcion.java,v $
 * Revision 1.2  2005/03/10 15:54:37  programa8
 * CR al 10/03/2005. Merging desde branch STABLE
 *
 * Revision 1.1.2.3  2005/03/07 13:21:31  programa8
 * Integración Versiones Estable e Inestable al 07/03/2005
 *
 * Revision 1.1.4.1  2005/02/28 18:39:11  programa8
 * Version Inestable al 28/02/2005
 *     *-Preparación para trabajar sin administrador de ventanas.
 *     *-Reordenamiento de GUI
 *     *-Mejoras en scroll y pantallas de pagos
 *     *- Mantenimiento de estado en Cliente en Espera
 *     *- Avisos del sistema manejados por la aplicacion
 *     *- Desbloqueo de caja por otros usuarios reparado.
 *
 * Revision 1.1.2.2  2005/02/22 18:18:44  programa4
 * *** empty log message ***
 *
 * Revision 1.1.2.1  2005/01/28 19:56:37  acastillo
 * Caja Registradora EPA3 - CR RC3
 *
 * ===========================================================================
 */
package com.becoblohm.cr.excepciones;

/**
 * <pre>
 * Proyecto: CR 
 * Clase: MandatoErradoExcepcion
 * </pre>
 * <p>
 * <a href="MandatoErradoExcepcion.java.html"><i>View Source</i></a>
 * </p>
 * 
 * @author Programa8 - $Author: programa8 $
 * @version $Revision: 1.2 $ - $Date: 2005/03/10 15:54:37 $
 * @since 22-ene-2005
 * @
 */
public class MandatoErradoExcepcion extends ExcepcionCr {

	/**
	 * @since 22-ene-2005
	 * @param mensaje
	 * @param esBuscandoCodigoModulo
	 */
	public MandatoErradoExcepcion(String mensaje, boolean esBuscandoCodigoModulo) {
		super(mensaje, esBuscandoCodigoModulo);
	}

	/**
	 * @since 22-ene-2005
	 * @param mensaje
	 * @param ex
	 * @param esBuscandoCodigoModulo
	 */
	public MandatoErradoExcepcion(String mensaje, Exception ex,
			boolean esBuscandoCodigoModulo) {
		super(mensaje, ex, esBuscandoCodigoModulo);
	}

	/**
	 * @since 22-ene-2005
	 * @param mensaje
	 */
	public MandatoErradoExcepcion(String mensaje) {
		super(mensaje);
	}

	/**
	 * @since 22-ene-2005
	 * @param mensaje
	 * @param ex
	 */
	public MandatoErradoExcepcion(String mensaje, Exception ex) {
		super(mensaje, ex);
	}

}
