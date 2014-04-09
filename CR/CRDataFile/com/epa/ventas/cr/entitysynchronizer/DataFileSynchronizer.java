/*
 * $Id: DataFileSynchronizer.java,v 1.1 2005/04/05 14:03:31 programa8 Exp $
 * ===========================================================================
 * Material Propiedad SuperFerretería EPA C.A. 
 *
 * Proyecto		: EPADataFileSynchronizer
 * Paquete		: com.epa.ventas.cr.entitysynchronizer
 * Programa		: DataFileSynchronizer.java
 * Creado por	: Programa8
 * Creado en 	: 04-abr-2005 11:52:03
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
 * $Log: DataFileSynchronizer.java,v $
 * Revision 1.1  2005/04/05 14:03:31  programa8
 * Sincronizador para CR a través de DataFile
 *
 * ===========================================================================
 */
package com.epa.ventas.cr.entitysynchronizer;

import org.apache.log4j.Logger;

import com.becoblohm.cr.extensiones.EntitySynchronizer;
import com.becoblohm.cr.mediadoresbd.BeansSincronizador;
import com.becoblohm.cr.mediadoresbd.EntidadBD;
import com.epa.sincronizador.datafile.client.ObtenerDataFile;

/**
 * <pre>
 * 
 *  Proyecto: EPADataFileSynchronizer 
 *  Clase: DataFileSynchronizer
 *  
 * </pre>
 * 
 * <p>
 * <a href="DataFileSynchronizer.java.html"> <i>View Source </i> </a>
 * </p>
 * 
 * @author Programa8 - $Author: programa8 $
 * @version $Revision: 1.1 $ - $Date: 2005/04/05 14:03:31 $
 * @since 04-abr-2005 @
 */
public class DataFileSynchronizer implements EntitySynchronizer {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(DataFileSynchronizer.class);

	/**
	 * @since 04-abr-2005
	 *  
	 */

	public DataFileSynchronizer() {
		super();
	}

	/*
	 * (sin Javadoc)
	 * 
	 * @see com.becoblohm.cr.extensiones.EntitySynchronizer#synchronize(com.becoblohm.cr.mediadoresbd.EntidadBD,
	 *      boolean, boolean) @param entidad @param origenLocal @param
	 *      reemplazar
	 * @since 04-abr-2005
	 */
	public void synchronize(EntidadBD entidad, boolean origenLocal,
			boolean reemplazar, boolean esperar) {	
		if (!origenLocal) {
			try {
				if (entidad.getNombre().equalsIgnoreCase("promocion") || 
					entidad.getNombre().equalsIgnoreCase("detallepromocion") ||
					//**** 10/11 IROJAS: Modificación para soncronización de tablas de promociones
					entidad.getNombre().equalsIgnoreCase("detallepromocionext") ||
					entidad.getNombre().equalsIgnoreCase("productoparacombo") ||
					entidad.getNombre().equalsIgnoreCase("participaencombo") ||
					entidad.getNombre().equalsIgnoreCase("productoseccion") ||
					entidad.getNombre().equalsIgnoreCase("donacion") ||
					entidad.getNombre().equalsIgnoreCase("condicionpromocion") ||
					entidad.getNombre().equalsIgnoreCase("producto") ||	
					entidad.getNombre().equalsIgnoreCase("prodcodigoexterno")
					)
					//****
					{
					
					if (esperar) {
						ObtenerDataFile.obtenerVariosDataFile(new String[] { 
								entidad.getEsquema() + "." + entidad.getNombre() + ".txt.gz"});
					} else {
						ObtenerDataFile
								.obtenerVariosDataFileSinEsperarlos(new String[] {
										entidad.getEsquema() + "." + entidad.getNombre() + ".txt.gz" });
					}
				} else {
					// Las Demás entidades las sincronizamos por el método viejo
					BeansSincronizador.syncEntidadMaestra(entidad, origenLocal, reemplazar);
				}
			} catch (Exception e) {
				logger.error(
						"synchronize(EntidadBD, boolean, boolean, boolean)", e);
			}
		} else {
			throw new IllegalArgumentException(
					"Solo puede usar este metodo para sincronizacion Servidor --> Caja");
		}
	}

}