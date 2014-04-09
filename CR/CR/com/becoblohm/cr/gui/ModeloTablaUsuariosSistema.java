/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblohm.cr.gui
 * Programa   : ModeloTablaUsuariosSistema.java
 * Creado por : Programador3 - Alexis Guédez López
 * Creado en  : 30/07/2004 03:38 AM
 *
 * (c) Becoblohm, C.A.
 * -----------------------------------------------------------------------------
 * 				Actualizaciones
 * -----------------------------------------------------------------------------
 * Versión     : 1.0.0
 * Fecha       : 25/05/2004 01:27:34 PM
 * Analista    : Programador3 - Alexis Guédez López
 * Descripción : Implementación inicial.
 * =============================================================================
 */
package com.becoblohm.cr.gui;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import org.apache.log4j.Logger;

import com.becoblohm.cr.excepciones.BaseDeDatosExcepcion;
import com.becoblohm.cr.excepciones.ConexionExcepcion;
import com.becoblohm.cr.excepciones.ExcepcionCr;
import com.becoblohm.cr.excepciones.PerfilExcepcion;
import com.becoblohm.cr.manejadorsesion.MaquinaDeEstado;
import com.becoblohm.cr.manejarusuario.Usuario;
import com.becoblohm.cr.utiles.MensajesVentanas;

/*
* En esta clase se realizaron modificaciones referentes a la migración a java 1.6 por jperez
* Sólo se parametrizó el tipo de dato contenido en los 'Class'
* Fecha: agosto 2011
*/
public class ModeloTablaUsuariosSistema extends AbstractTableModel implements Serializable
{
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(ModeloTablaUsuariosSistema.class);

	String[] titulos;
	Object[][] datos;
	Class<?>[] types;

	/**
	 * Constructor para ModeloTabla.
	 * @param xDatos
	 * @param xTitulos
	 */
	public ModeloTablaUsuariosSistema(Object[][] xDatos, String[] xTitulos, Class<?>[]xTipos)
	{
		datos = xDatos;
		titulos = xTitulos;
		types = xTipos;
	}

	public ModeloTablaUsuariosSistema(Object[][] xDatos,String[] xTitulos)
	{
		datos = xDatos;
		titulos = xTitulos;
	}

	public ModeloTablaUsuariosSistema(Object[][] xDatos)
	{
		datos = xDatos;
	}

	/**
	 * Método getColumnCount.
	 * @return int
	 */
	public int getColumnCount()
	{
		if (logger.isDebugEnabled()) {
			logger.debug("getColumnCount() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getColumnCount() - end");
		}
		return titulos.length;
	}

	/**
	 * Método getRowCount.
	 * @return int
	 */
	public int getRowCount()
	{
		if (logger.isDebugEnabled()) {
			logger.debug("getRowCount() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getRowCount() - end");
		}
		return datos.length;
	}

	/**
	 * Método getValueAt.
	 * @param row
	 * @param col
	 * @return Object
	 */
	public Object getValueAt(int row,int col)
	{
		if (logger.isDebugEnabled()) {
			logger.debug("getValueAt(int, int) - start");
		}

		Object returnObject = datos[row][col];
		if (logger.isDebugEnabled()) {
			logger.debug("getValueAt(int, int) - end");
		}
		return returnObject;
	}

	/**
	 * Método getColumnName.
	 * @param col
	 * @return String
	 */
	public String getColumnName(int col)
	{
		if (logger.isDebugEnabled()) {
			logger.debug("getColumnName(int) - start");
		}

		String returnString = titulos[col];
		if (logger.isDebugEnabled()) {
			logger.debug("getColumnName(int) - end");
		}
		return returnString;
	}

	/**
	 * Método isCellEditable.
	 * @param row
	 * @param col
	 * @return boolean
	 */
	public boolean isCellEditable(int row,int col)
	{
		if (logger.isDebugEnabled()) {
			logger.debug("isCellEditable(int, int) - start");
		}

		if ((col==2) || (col==3))
		{
			if (logger.isDebugEnabled()) {
				logger.debug("isCellEditable(int, int) - end");
			}
			return true;
		}
		else
		{
			if (logger.isDebugEnabled()) {
				logger.debug("isCellEditable(int, int) - end");
			}
			return false;
		}
	}

	/**
	 * Método setValueAt
	 * @param valorObjeto
	 * @param row
	 * @param col
	 */
	public void setValueAt(Object valorObjeto, int row, int col)
	{
		if (logger.isDebugEnabled()) {
			logger.debug("setValueAt(Object, int, int) - start");
		}

		datos[row][col] = valorObjeto;
		fireTableCellUpdated(row,col);

		if (logger.isDebugEnabled()) {
			logger.debug("setValueAt(Object, int, int) - end");
		}
	} 

	/**
	 * Método getColumnClass.
	 * @param c
	 * @return Class
	 */
	public Class<?> getColumnClass(int c)
	{
		if (logger.isDebugEnabled()) {
			logger.debug("getColumnClass(int) - start");
		}

		Class<?> returnClass = types[c];
		if (logger.isDebugEnabled()) {
			logger.debug("getColumnClass(int) - end");
		}
	  return returnClass;
	}

	/**
	 * Retorna los datos de la tabla.
	 * @return Object[][]
	 */
	public Object[][] getDatos() {
		if (logger.isDebugEnabled()) {
			logger.debug("getDatos() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getDatos() - end");
		}
		return datos;
	}

	/**
	 * Retorna el listado de títulos.
	 * @return String[]
	 */
	public String[] getTitulos() {
		if (logger.isDebugEnabled()) {
			logger.debug("getTitulos() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getTitulos() - end");
		}
		return titulos;
	}

	/**
	 * Método getTypes
	 * @return Class[]
	 */
	public Class<?>[] getTypes() {
		if (logger.isDebugEnabled()) {
			logger.debug("getTypes() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getTypes() - end");
		}
		return types;
	}

	/**
	 * Método setTypes
	 * @param classes
	 */
	public void setTypes(Class<?>[] classes) {
		if (logger.isDebugEnabled()) {
			logger.debug("setTypes(Class[]) - start");
		}

		types = classes;

		if (logger.isDebugEnabled()) {
			logger.debug("setTypes(Class[]) - end");
		}
	}

	/**
	 * Establece los datos.
	 * @param datos
	 */
	public void setDatos(Object[][] datos) {
		if (logger.isDebugEnabled()) {
			logger.debug("setDatos(Object[][]) - start");
		}

		this.datos = datos;

		if (logger.isDebugEnabled()) {
			logger.debug("setDatos(Object[][]) - end");
		}
	}

	/**
	 * Establece los títulos.
	 * @param titulos 
	 */
	public void setTitulos(String[] titulos) {
		if (logger.isDebugEnabled()) {
			logger.debug("setTitulos(String[]) - start");
		}

		this.titulos = titulos;

		if (logger.isDebugEnabled()) {
			logger.debug("setTitulos(String[]) - end");
		}
	}

	/**
	 * Método iniciarDatosTabla.
	 * 	Inicializa los datos de la tabla.
	 * @param columnas - Número de columnas del modelo de tabla actual. 
	 */
	public void iniciarDatosTabla(int columnas) {
		if (logger.isDebugEnabled()) {
			logger.debug("iniciarDatosTabla(int) - start");
		}

		datos = new Object[1][columnas];
		for(int i=0; i<columnas; i++){
			if (this.getTypes()[i].getName().equals("java.lang.Number")){
				datos[0][i] = new Integer(0);
			}
			else if (this.getTypes()[i].getName().equals("java.lang.Double")){
				datos[0][i] = new Double(0.00);
			}
			else if (this.getTypes()[i].getName().equals("java.lang.Float")){
				datos[0][i] = new Float(0.00);
			}
			else if (this.getTypes()[i].getName().equals("java.lang.Boolean")){
				datos[0][i] = Boolean.FALSE;
			}
			else datos[0][i] = "";
		}
		fireTableDataChanged();

		if (logger.isDebugEnabled()) {
			logger.debug("iniciarDatosTabla(int) - end");
		}
	}
	
	/**
	 * Método llenarTabla.
	 * 	Llena la tabla correspondiente a las funciones asignadas al perfil indicado.
	 * 
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'LinkedHashMap', 'Vector', 'LinkedHashSet'
	*  e 'Iterator'
	* Fecha: agosto 2011
	*/
	public void llenarTabla(LinkedHashMap<String,Usuario> miembros){
		if (logger.isDebugEnabled()) {
			logger.debug("llenarTabla(LinkedHashMap) - start");
		}

		try{
			Iterator<?> ciclo = null;
			Usuario xUsuario = null;
			LinkedHashMap<String,Usuario> conjunto = new LinkedHashMap<String,Usuario>();
			Vector<?> usuarios = MaquinaDeEstado.cargarRegistros(new Usuario(), true);
			// Carga el resto de los usuarios registrados en el sistema
			ciclo = usuarios.iterator();				
			while(ciclo.hasNext()){
				xUsuario = new Usuario();
				xUsuario = (Usuario)ciclo.next();
				String key = xUsuario.getNumFicha();
				if((!(miembros.containsKey(key))) && (!key.equals("00000000")) && (!key.equals("00009999")))
					conjunto.put(key, xUsuario);
			}

			// Visualiza el conjunto de usuarios
			LinkedHashSet<Usuario> valores = new LinkedHashSet<Usuario>(conjunto.values());
			ciclo = valores.iterator();
			datos = new Object[valores.size()][titulos.length];
			int i=0;
			String nombre = "";
			while(ciclo.hasNext()){
				xUsuario = new Usuario();
				xUsuario = (Usuario)ciclo.next();
				datos[i][0]=xUsuario.getNumFicha();
				if(xUsuario.getNombre() == null)
					nombre = new String(xUsuario.getDatosPersonales().getNombre());
				else
					nombre = new String(xUsuario.getNombre());
				datos[i++][1] = nombre;
			}
			fireTableDataChanged();
		} catch (NullPointerException e){
			logger.error("llenarTabla(LinkedHashMap)", e);

			this.iniciarDatosTabla(titulos.length);
		} catch (BaseDeDatosExcepcion e) {
			logger.error("llenarTabla(LinkedHashMap)", e);

			MensajesVentanas.mensajeError(e.getMensaje());
		} catch (PerfilExcepcion e) {
			logger.error("llenarTabla(LinkedHashMap)", e);

			MensajesVentanas.mensajeError(e.getMensaje());
		} catch (ConexionExcepcion e) {
			MensajesVentanas.mensajeError(e.getMensaje());
			logger.error("llenarTabla(LinkedHashMap)", e);
		} catch (ExcepcionCr e) {
			MensajesVentanas.mensajeError(e.getMensaje());
			logger.error("llenarTabla(LinkedHashMap)", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("llenarTabla(LinkedHashMap) - end");
		}
	}

}