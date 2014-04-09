/*
 * $Id: TMU950Factory.java,v 1.1.2.5 2005/05/09 14:28:22 programa8 Exp $
 * ===========================================================================
 * Material Propiedad SuperFerretería EPA C.A. 
 *
 * Proyecto		: CRFiscalPrinterDriver 2.0
 * Paquete		: com.epa.crprinterdriver.impl
 * Programa		: TMU950Factory.java
 * Creado por	: Programa8
 * Creado en 	: 05-may-2004 11:32:34
 * (C) Copyright 2004 SuperFerretería EPA C.A. Todos los Derechos Reservados
 * ---------------------------------------------------------------------------
 * Información de Repositorio:
 * ---------------------------------------------------------------------------
 * Rama de Desarrollo	: $Name:  $
 * Bloqueado por		: $Locker:  $
 * Estado de Revisión	: $State: Exp $
 * ---------------------------------------------------------------------------
 * Actualizaciones:
 * ---------------------------------------------------------------------------
 * $Log: TMU950Factory.java,v $
 * Revision 1.1.2.5  2005/05/09 14:28:22  programa8
 * Ajuste de Javadoc
 *
 * Revision 1.1.2.4  2005/05/09 14:19:03  programa8
 * Ajuste de Javadoc
 *
 * Revision 1.1.2.3  2005/05/06 19:18:00  programa8
 * Ajustes en javadoc del driver
 *
 * Revision 1.1.2.2  2005/05/06 18:26:44  programa8
 * Ajustes en javadoc del driver
 *
 * Revision 1.1.2.1  2005/05/05 22:05:41  programa8
 * Versión 2.1:
 * *- Preparación para funcionamiento con EPSON TMU950PF
 * *- Toma en cuenta la solicitud de incremento en espera por timeout
 * *- Considera independencia de dispositivo en colchones de tiempo
 * *- Considera funciones que pueden estar implementadas en un dispositivo
 * no necesariamente implementadas en otro
 * *- Separa Subsistema base del driver de la fachada usadas por las
 * aplicaciones
 * *- Maneja estatus de activación del slip. Considera colchones de tiempo
 * para comando enviados al slip.
 * *- Patron de diseño Abstract Factory para la selección de la implementación
 * de dispositivo a partir de propiedad de la aplicación
 *
 * ===========================================================================
 */
package com.epa.crprinterdriver.impl;

import com.epa.crprinterdriver.CRFPEngine;
import com.epa.crprinterdriver.CRFPImplFactory;
import com.epa.crprinterdriver.CRFPResponseParser;
import com.epa.crprinterdriver.CRFPSequenceMap;
import com.epa.crprinterdriver.CRFPSubsystem;
import com.epa.crprinterdriver.NPF4610Engine;
import com.epa.crserialinterface.Parameters;

/**
 * 
 * <p>
 * Inicializador de elementos dependientes de dispositivo ajustados para la impresora
 * EPSON TMU950/PF, de matriz de puntos
 * </p> 
 * <p>
 * <a href="TMU950Factory.java.html"><i>View Source</i></a>
 * </p>
 * 
 * @author Programa8 - Arístides Castillo Colmenárez - $Author: programa8 $
 * @version $Revision: 1.1.2.5 $ - $Date: 2005/05/09 14:28:22 $
 * @since 05-may-2004
 * 
 */
public class TMU950Factory extends CRFPImplFactory {

	/* (sin Javadoc)
	 * @see com.epa.crprinterdriver.CRFPImplFactory#getResponseParser()
	 * @return
	 * @since 05-may-2004
	 */
	
	public CRFPResponseParser getResponseParserInstance() {
		TMU950ResponseParser parser = null;
		if (system == null)
			throw new IllegalStateException("Error de configuración en factory: sistema no asignado");
		if (system.getSequenceMap() == null) 
			throw new IllegalStateException("No se ha instanciado el SequenceMap");
		if (!(system.getSequenceMap() instanceof TMU950SequenceMap)) {
			throw new IllegalStateException("El SequenceMap no está diseñado para la impresora TMU950PF");
		}
		parser = new TMU950ResponseParser((TMU950SequenceMap)system.getSequenceMap());
		return parser;
	}

	/* (sin Javadoc)
	 * @see com.epa.crprinterdriver.CRFPImplFactory#getSequenceMap()
	 * @return
	 * @since 05-may-2004
	 */
	public CRFPSequenceMap getSequenceMapInstance() {
		TMU950SequenceMap seqMap = null;
		if (system == null)
			throw new IllegalStateException("Error de configuración en factory: sistema no asignado");
		
		if (system.getStatus() == null)
			throw new IllegalStateException("No se ha instanciado correctamente el driver");
		seqMap = new TMU950SequenceMap(system.getStatus());
		return seqMap;
	}

	/* (non-Javadoc)
	 * @see com.epa.crprinterdriver.CRFPImplFactory#getEngineInstance(com.epa.crprinterdriver.CRFPSubsystem, com.epa.crserialinterface.Parameters)
	 */
	public CRFPEngine getEngineInstance(CRFPSubsystem system, Parameters serialParameters) {
		NPF4610Engine engine = null;
		if (system == null)
			throw new IllegalStateException("Error de configuración en factory: sistema no asignado");
		if (system.getStatus() == null)
			throw new IllegalStateException("No se ha instanciado correctamente el driver");
		engine = new NPF4610Engine(system, serialParameters);
		return engine;
	}
}
