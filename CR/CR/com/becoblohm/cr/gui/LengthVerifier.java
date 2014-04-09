/*
 * $Id: LengthVerifier.java,v 1.1.2.1 2005/03/29 20:38:47 programa8 Exp $
 * ===========================================================================
 * Material Propiedad SuperFerretería EPA C.A. 
 *
 * Proyecto		: CR
 * Paquete		: com.becoblohm.cr.gui
 * Programa		: LengthVerifier.java
 * Creado por	: Programa8
 * Creado en 	: 29-mar-2005 15:23:26
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
 * $Log: LengthVerifier.java,v $
 * Revision 1.1.2.1  2005/03/29 20:38:47  programa8
 * Validacion de datos en registro de clientes temporales
 *
 * ===========================================================================
 */
package com.becoblohm.cr.gui;

import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JTextField;

import org.apache.log4j.Logger;

import com.becoblohm.cr.utiles.MensajesVentanas;

/**
 * <pre>
 * Proyecto: CR 
 * Clase: LengthVerifier
 * </pre>
 * <p>
 * <a href="LengthVerifier.java.html"><i>View Source</i></a>
 * </p>
 * 
 * @author Programa8 - $Author: programa8 $
 * @version $Revision: 1.1.2.1 $ - $Date: 2005/03/29 20:38:47 $
 * @since 29-mar-2005
 * @
 */
public class LengthVerifier extends InputVerifier 	{
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(LengthVerifier.class);

	/**
	 * Logger for this class
	 */
	
	private int maxLength = 50;
	
	/**
	 * Constructor por defecto. Toma como longitud máxima 50 caracteres.
	 * @since 21-feb-2005
	 * 
	 */
	public LengthVerifier() {
		super();
	}
	
	/**
	 * Constructor que permite indicar una longitud específica.
	 * @since 21-feb-2005
	 * @param length Longitud máxima a verificar
	 */
	public LengthVerifier(int length) {
		this();
		maxLength = length;
	}
	
		/* (sin Javadoc)
	 * @see javax.swing.InputVerifier#verify(javax.swing.JComponent)
	 * @param input
	 * @return
	 * @since 21-feb-2005
	 */
	public boolean verify(JComponent input) {
		if (logger.isDebugEnabled()) {
			logger.debug("verify(JComponent) - start");
		}

		boolean valid = false;
		if (input instanceof JTextField) {
			//Se eliminan los espacios en blanco que pueda traer el campo
			//jperez 31-05-2012
			if (((JTextField)input).getText().trim().length() <= maxLength) {
				valid = true;
			}
			if (!valid) {
				input.setInputVerifier(null);
				MensajesVentanas.aviso("El valor no puede ser mayor a " + 
						maxLength + " caracteres");
				((JTextField)input).setText(
					((JTextField)input).getText().substring(0, maxLength));
				input.setInputVerifier(this);
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("verify(JComponent) - end");
		}
		return valid;
	}
}

