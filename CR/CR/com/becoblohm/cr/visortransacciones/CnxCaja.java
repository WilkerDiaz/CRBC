/*
 * Creado el 26/10/2004
 *
 * Para cambiar la plantilla para este archivo generado vaya a
 * Ventana&gt;Preferencias&gt;Java&gt;Generaci�n de c�digo&gt;C�digo y comentarios
 */
package com.becoblohm.cr.visortransacciones;

import java.util.Properties;
import java.util.Vector;

/**
 * @author Programa8
 *
 * Para cambiar la plantilla para este comentario de tipo generado vaya a
 * Ventana&gt;Preferencias&gt;Java&gt;Generaci�n de c�digo&gt;C�digo y comentarios
 */
public class CnxCaja {
		private String etiqueta;
		private String ip;
		private int codigo;
	
		public CnxCaja(String etiq, String ip, int cod) {
			this.etiqueta = etiq;
			this.ip = ip;
			this.codigo = cod;
		}
		/**
		 * @return
		 */
		public String getEtiqueta() {
			return etiqueta;
		}

		/**
		 * @return
		 */
		public String getIP() {
			return ip;
		}

		/**
		 * @param string
		 */
		public void setEtiqueta(String string) {
			etiqueta = string;
		}

		/**
		 * @param string
		 */
		public void setIP(String string) {
			ip = string;
		}
	
		public String toString() {
			return etiqueta;
		}
	
		/*
		* En esta funci�n se realizaron modificaciones referentes a la migraci�n a java 1.6 por jperez
		* S�lo se parametriz� el tipo de dato contenido en los 'Vector'
		* Fecha: agosto 2011
		*/
		public static Vector<CnxCaja> getConexiones(Properties p) {
			Vector<CnxCaja> result = new Vector<CnxCaja>();
			int cnt = Integer.parseInt(p.getProperty("cnx.cuantas"));
			for (int i = 1; i <= cnt; i++) {
				String etiq, ip, propKey;
				int cod;
				propKey = "cnx.caja" + i;
				etiq = p.getProperty(propKey + ".etiqueta");
				ip = p.getProperty(propKey + ".ip");
				cod = Integer.parseInt(p.getProperty(propKey + ".codigo"));
				result.addElement(new CnxCaja(etiq, ip, cod));
			}
			return result;
		}

		/**
		 * @return
		 */
		public int getCodigo() {
			return codigo;
		}

		/**
		 * @param i
		 */
		public void setCodigo(int i) {
			codigo = i;
		}

}
