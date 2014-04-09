/*
 * Creado el 30/01/2007
 *
 * Para cambiar la plantilla para este archivo generado vaya a
 * Ventana&gt;Preferencias&gt;Java&gt;Generación de código&gt;Código y comentarios
 */
package com.epa.crprinterdriver.impl;

import com.epa.crprinterdriver.CRFPEngine;
import com.epa.crprinterdriver.CRFPImplFactory;
import com.epa.crprinterdriver.CRFPResponseParser;
import com.epa.crprinterdriver.CRFPSequenceMap;
import com.epa.crprinterdriver.CRFPSubsystem;
import com.epa.crprinterdriver.GD4Engine;
import com.epa.crserialinterface.Parameters;

/**
 * @author rabreu
 *
 * Para cambiar la plantilla para este comentario de tipo generado vaya a
 * Ventana&gt;Preferencias&gt;Java&gt;Generación de código&gt;Código y comentarios
 */
public class GD4Factory extends CRFPImplFactory {

	/* (no Javadoc)
	 * @see com.epa.crprinterdriver.CRFPImplFactory#getResponseParserInstance()
	 */
	public CRFPResponseParser getResponseParserInstance() {
		GD4ResponseParser parser = null;
		if (system == null)
			throw new IllegalStateException("Error de configuración en factory: sistema no asignado");
		
		if (system.getSequenceMap() == null) 
			throw new IllegalStateException("No se ha instanciado el SequenceMap");
		if (!(system.getSequenceMap() instanceof GD4SequenceMap)) {
			throw new IllegalStateException("El SequenceMap no está diseñado para la impresora GD4");
		}
		parser = new GD4ResponseParser((GD4SequenceMap)system.getSequenceMap());
		return parser;
	}

	/* (no Javadoc)
	 * @see com.epa.crprinterdriver.CRFPImplFactory#getSequenceMapInstance()
	 */
	public CRFPSequenceMap getSequenceMapInstance() {
		GD4SequenceMap seqMap = null;
		if (system == null)
			throw new IllegalStateException("Error de configuración en factory: sistema no asignado");
		if (system.getStatus() == null)
			throw new IllegalStateException("No se ha instanciado correctamente el driver");
		seqMap = new GD4SequenceMap(system.getStatus());
		return seqMap;
	}

	/* (non-Javadoc)
	 * @see com.epa.crprinterdriver.CRFPImplFactory#getEngineInstance(com.epa.crprinterdriver.CRFPSubsystem, com.epa.crserialinterface.Parameters)
	 */
	public CRFPEngine getEngineInstance(CRFPSubsystem system, Parameters serialParameters) {
		GD4Engine engine = null;
		if (system == null)
			throw new IllegalStateException("Error de configuración en factory: sistema no asignado");
		if (system.getStatus() == null)
			throw new IllegalStateException("No se ha instanciado correctamente el driver");
		engine = new GD4Engine(system, serialParameters);
		return engine;
	}
}
