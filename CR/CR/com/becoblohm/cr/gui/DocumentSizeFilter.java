/*
 * $Id: DocumentSizeFilter.java,v 1.1 2006/09/18 16:52:22 programa4 Exp $
 * ===========================================================================
 *
 * Proyecto		: CR
 * Paquete		: com.becoblohm.cr.gui
 * Programa		: DocumentSizeFilter.java
 * Creado por	: programa4
 * Creado en 	: 18/09/2006 09:09:08 AM
 * (C) Copyright 2006 Todos los Derechos Reservados
 * ---------------------------------------------------------------------------
 * Información de Repositorio:
 * ---------------------------------------------------------------------------
 * Rama de Desarrollo	: $Name:  $
 * Bloqueado por		: $Locker:  $
 * Estado de Revisión	: $State: Exp $
 * ---------------------------------------------------------------------------
 * Actualizaciones:
 * ---------------------------------------------------------------------------
 * $Log: DocumentSizeFilter.java,v $
 * Revision 1.1  2006/09/18 16:52:22  programa4
 * Implementacion de maxlength para textfields y contenido permitido.
 *
 *
 * ===========================================================================
 */
/**
 * Clase DocumentSizeFilter
 *
 * @author programa4 - $Author: programa4 $
 * @version $Revision: 1.1 $ - $Date: 2006/09/18 16:52:22 $
 * @since 18/09/2006
 */
package com.becoblohm.cr.gui;

import org.apache.log4j.Logger;

import java.awt.Toolkit;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

/**
 * Filtro que se asigna a textfield u otros text component para asegurar que no se escriba mas del tamaño indicado.
 * @author programa4 - $Author: programa4 $
 * @version $Revision: 1.1 $ - $Date: 2006/09/18 16:52:22 $
 * @since 18/09/2006
 */
public class DocumentSizeFilter extends DocumentFilter {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
		.getLogger(DocumentSizeFilter.class);

	int maxCharacters;

	/**
	 * @param maxChars
	 */
	public DocumentSizeFilter(int maxChars) {
		this.maxCharacters = maxChars;
	}

	public void insertString(FilterBypass fb, int offs, String str,
			AttributeSet a) throws BadLocationException {
		if (logger.isDebugEnabled()) {
			logger.debug("in DocumentSizeFilter's insertString method");
		}

		//This rejects the entire insertion if it would make
		//the contents too long. Another option would be
		//to truncate the inserted string so the contents
		//would be exactly maxCharacters in length.
		if ((fb.getDocument().getLength() + str.length()) <= this.maxCharacters) {
			super.insertString(fb, offs, str, a);
		} else {
			Toolkit.getDefaultToolkit().beep();
			//super.insertString(fb,  )
		}

	}

	public void replace(FilterBypass fb, int offs, int length, String str,
			AttributeSet a) throws BadLocationException {
		if (logger.isDebugEnabled()) {
			logger.debug("in DocumentSizeFilter's replace method");
		}
		//This rejects the entire replacement if it would make
		//the contents too long. Another option would be
		//to truncate the replacement string so the contents
		//would be exactly maxCharacters in length.
		if ((fb.getDocument().getLength() + str.length() - length) <= this.maxCharacters)
			super.replace(fb, offs, length, str, a);
		else
			Toolkit.getDefaultToolkit().beep();
	}

}