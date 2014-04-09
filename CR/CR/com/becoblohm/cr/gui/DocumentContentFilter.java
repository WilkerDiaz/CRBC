/*
 * $Id: DocumentContentFilter.java,v 1.1 2006/09/18 16:52:21 programa4 Exp $
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
 * $Log: DocumentContentFilter.java,v $
 * Revision 1.1  2006/09/18 16:52:21  programa4
 * Implementacion de maxlength para textfields y contenido permitido.
 *
 *
 * ===========================================================================
 */
/**
 * Clase DocumentSizeFilter
 *
 * @author programa4 - $Author: programa4 $
 * @version $Revision: 1.1 $ - $Date: 2006/09/18 16:52:21 $
 * @since 18/09/2006
 */
package com.becoblohm.cr.gui;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;

import org.apache.log4j.Logger;

import com.becoblohm.cr.utiles.Control;

/**
 * Filtro que se asigna a textfield u otros text component para asegurar que
 * solo se escriban los caracteres permitidos.
 *
 * @author programa4 - $Author: programa4 $
 * @version $Revision: 1.1 $ - $Date: 2006/09/18 16:52:21 $
 * @since 18/09/2006
 */
public class DocumentContentFilter extends DocumentSizeFilter {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
		.getLogger(DocumentContentFilter.class);

	private final String caracteres;

	/**
	 * @param max cantidad maxima de caracteres permitidos
	 * @param caracteresPermitidos
	 */
	public DocumentContentFilter(int max, String caracteresPermitidos) {
		super(max);
		this.caracteres = caracteresPermitidos;
	}

	/**
	 * @param caracteresPermitidos
	 */
	public DocumentContentFilter(String caracteresPermitidos) {
		this(Integer.MAX_VALUE, caracteresPermitidos);
	}

	public void insertString(FilterBypass fb, int offs, String str,
			AttributeSet a) throws BadLocationException {
		if (logger.isDebugEnabled()) {
			logger.debug("in DocumentContentFilter's insertString method");
		}

		super.insertString(fb, offs, Control.removerCaracteresNoListados(
			str, this.caracteres), a);
	}

	public void replace(FilterBypass fb, int offs, int length, String str,
			AttributeSet a) throws BadLocationException {
		if (logger.isDebugEnabled()) {
			logger.debug("in DocumentContentFilter's replace method");
		}

		super.replace(fb, offs, length, Control.removerCaracteresNoListados(
			str, this.caracteres), a);
	}

}