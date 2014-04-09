/*
 * $Id: TextVerifier.java,v 1.1.2.1 2005/03/29 20:38:49 programa8 Exp $
 * ===========================================================================
 * Material Propiedad SuperFerretería EPA C.A. 
 *
 * Proyecto		: CR
 * Paquete		: com.becoblohm.cr.gui
 * Programa		: TextVerifier.java
 * Creado por	: Programa8
 * Creado en 	: 29-mar-2005 15:24:49
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
 * $Log: TextVerifier.java,v $
 * Revision 1.1.2.1  2005/03/29 20:38:49  programa8
 * Validacion de datos en registro de clientes temporales
 *
 * ===========================================================================
 */
package com.becoblohm.cr.gui;

import javax.swing.JComponent;
import javax.swing.JTextField;

import org.apache.log4j.Logger;

import com.becoblohm.cr.utiles.MensajesVentanas;

/**
 * <pre>
 * Proyecto: CR 
 * Clase: TextVerifier
 * </pre>
 * <p>
 * <a href="TextVerifier.java.html"><i>View Source</i></a>
 * </p>
 * 
 * @author Programa8 - $Author: programa8 $
 * @version $Revision: 1.1.2.1 $ - $Date: 2005/03/29 20:38:49 $
 * @since 29-mar-2005
 * @
 */
public class TextVerifier extends LengthVerifier {
		/**
		 * Logger for this class
		 */
		private static final Logger logger = Logger.getLogger(TextVerifier.class);
		
		// Vacio permite todos los caracteres
		private String caracteres = "";
			/**
		 * @since 29-mar-2005
		 * 
		 */
		public TextVerifier() {
			super();
		}
		/**
		 * @since 29-mar-2005
		 * @param length
		 */
		public TextVerifier(int length) {
			super(length);
		}
		
		public TextVerifier (int length, String caracteres) {
			this(length);
			this.caracteres = caracteres;
		}
		
		
		/* (sin Javadoc)
		 * @see javax.swing.InputVerifier#verify(javax.swing.JComponent)
		 * @param input
		 * @return
		 * @since 29-mar-2005
		 */
		public boolean verify(JComponent input) {
			boolean valid = true;
			if (caracteres.length() > 0) {
				if (input instanceof JTextField) {
					JTextField campo = (JTextField)input;
					//Se eliminan los espacios en blanco que pueda traer el campo
					//jperez 31-05-2012
					String valor = campo.getText().trim();
					char caracInv = ' ';
					for (int i = 0; i < valor.length(); i++) {
						if (caracteres.indexOf(valor.charAt(i)) == -1) 
						{
							valid = false;
							caracInv = valor.charAt(i);
							break;
						}	 
					}
					
					
					if (!valid) {
						input.setInputVerifier(null);
						MensajesVentanas.aviso("El valor contiene caracteres invalidos: '" + caracInv + "'");
						campo.setText("");
						input.setInputVerifier(this);
					}
				}
			}
			return valid && super.verify(input);
		}
		
		public void verify1(JComponent input)
		{
			JTextField campo = (JTextField)input;
			String valor = campo.getText();
			boolean valid=true;
			String caracVal=""; 
			if (valor.length()>0)
			{
					if (valor.indexOf('@')==-1) 
					{
						valid = false;
						caracVal = "El Valor debe contener el: (@)";
					}else
					
					if (valor.indexOf('.')==-1)
					{
						valid = false;
						caracVal = "El Valor debe contener el: (.) ";
					}else
					if ((valor.indexOf('@')==0) || (valor.indexOf('.')==0))
					{
						valid = false;
						caracVal = "El (@) o el (.) Ubicado Incorrectamente";	
					}else
					if ((valor.indexOf('@')) > (valor.lastIndexOf('.')))
					{
						valid = false;
						caracVal = "Debe haber un (.) despues del (@)";
					}else
					if ( (valor.indexOf('.') - valor.lastIndexOf('@')) == 1 ||  (valor.lastIndexOf('@') - valor.indexOf('.')) == 1)
					{
						valid = false;
						caracVal = "Incorrecto (@.) caracteres Continuos";
					}	
					
					if (!valid) {
						input.setInputVerifier(null);
						MensajesVentanas.aviso(caracVal );
						campo.setText("");
						input.setInputVerifier(this);
					}
			}
		}
		
}
