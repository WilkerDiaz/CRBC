/*
 * Creado el 13-dic-2004
 *
 * Para cambiar la plantilla para este archivo generado vaya a
 * Ventana&gt;Preferencias&gt;Java&gt;Generación de código&gt;Código y comentarios
 */
package com.becoblohm.cr.gui;

import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

/**
 * @author Programa8
 *
 * Para cambiar la plantilla para este comentario de tipo generado vaya a
 * Ventana&gt;Preferencias&gt;Java&gt;Generación de código&gt;Código y comentarios
 */
public class UpperCaseField extends JTextField {

	/**
	 * 
	 */
	public UpperCaseField() {
		super();
	}

	/**
	 * @param columns
	 */
	public UpperCaseField(int columns) {
		super(columns);
	}

	/**
	 * @param text
	 */
	public UpperCaseField(String text) {
		super(text);
	}

	/**
	 * @param text
	 * @param columns
	 */
	public UpperCaseField(String text, int columns) {
		super(text, columns);
	}

	/**
	 * @param doc
	 * @param text
	 * @param columns
	 */
	public UpperCaseField(Document doc, String text, int columns) {
		super(doc, text, columns);
	}

	     protected Document createDefaultModel() {
	 	      return new UpperCaseDocument();
	     }
	 
	     static class UpperCaseDocument extends PlainDocument {
	 
	         public void insertString(int offs, String str, AttributeSet a) 
	 	          throws BadLocationException {
	 
	 	          if (str == null) {
	 		      return;
	 	          }
	 	          char[] upper = str.toCharArray();
	 	          for (int i = 0; i < upper.length; i++) {
	 		      upper[i] = Character.toUpperCase(upper[i]);
	 	          }
	 	          super.insertString(offs, new String(upper), a);
	 	      }
	     }

}
