/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblohm.cr.gui
 * Programa   : ModeloTablaFunciones.java
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
import java.util.ArrayList;
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
import com.becoblohm.cr.manejarsistema.Funcion;
import com.becoblohm.cr.manejarusuario.ListaFuncion;
import com.becoblohm.cr.manejarusuario.Perfil;
import com.becoblohm.cr.utiles.MensajesVentanas;

/*
* En esta clase se realizaron modificaciones referentes a la migración a java 1.6 por jperez
* Sólo se parametrizó el tipo de dato contenido en los 'Class'
* Fecha: agosto 2011
*/
public class ModeloTablaFunciones extends AbstractTableModel implements Serializable
{
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(ModeloTablaFunciones.class);

	String[] titulos;
	Object[][] datos;
	Class<?>[] types;

	/**
	 * Constructor para ModeloTabla.
	 * @param xDatos
	 * @param xTitulos
	 */
	public ModeloTablaFunciones(Object[][] xDatos, String[] xTitulos, Class<?>[]xTipos)
	{
		datos = xDatos;
		titulos = xTitulos;
		types = xTipos;
	}

	public ModeloTablaFunciones(Object[][] xDatos,String[] xTitulos)
	{
		datos = xDatos;
		titulos = xTitulos;
	}

	public ModeloTablaFunciones(Object[][] xDatos)
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
	* Sólo se parametrizó el tipo de dato de los distintos ArrayList, Iterator, LinkedHashMap y
	* LinkHashSet
	* Fecha: agosto 2011
	*/
	public int[] llenarTabla(Perfil xPerfil){
		if (logger.isDebugEnabled()) {
			logger.debug("llenarTabla(Perfil) - start");
		}

		int[] totalFunciones = new int[2];
		try{
			Iterator<?> ciclo = null;
			ListaFuncion xMenu = null;
			LinkedHashMap<String,ListaFuncion> conjunto = new LinkedHashMap<String,ListaFuncion>();
			ArrayList<ListaFuncion> misFunciones = xPerfil.getFunciones();
			if(misFunciones != null){
				totalFunciones[0] = misFunciones.size();
				// Carga las funciones asignadas al perfil
				ciclo = misFunciones.iterator();
				while(ciclo.hasNext()){
					xMenu = new ListaFuncion();
					xMenu = (ListaFuncion)ciclo.next();
					String key = xMenu.getFuncion().getRaiz().getCodModulo()+""+xMenu.getFuncion().getCodFuncion();
					if(!(conjunto.containsKey(key)))
						conjunto.put(key, xMenu);
				}
			} else totalFunciones[0] = 0; 
			
			Vector<?> funciones = MaquinaDeEstado.cargarRegistros(new Funcion(), true);
			totalFunciones[1] = funciones.size();
			
			// Carga el resto de las funciones definidas en el sistema
			ciclo = funciones.iterator();				
			while(ciclo.hasNext()){
				xMenu = new ListaFuncion();
				Funcion xFuncion = new Funcion();
				xFuncion = (Funcion)ciclo.next();
				xMenu.setFuncion(xFuncion);
				xMenu.setHabilitado(false);
				xMenu.setAutorizado(false);
				String key = xMenu.getFuncion().getRaiz().getCodModulo()+""+xMenu.getFuncion().getCodFuncion();
				if(!(conjunto.containsKey(key)))
					conjunto.put(key, xMenu);
			}

			// Visualiza el conjunto de funciones diponibles
			LinkedHashSet<ListaFuncion> valores = new LinkedHashSet<ListaFuncion>(conjunto.values());
			ciclo = valores.iterator();
			datos = new Object[valores.size()][titulos.length];
			int i=0;
			while(ciclo.hasNext()){
				xMenu = new ListaFuncion();
				xMenu = (ListaFuncion)ciclo.next();
				datos[i][0]=xMenu.getFuncion().getRaiz().getDescripcion();
				datos[i][1]=xMenu.getFuncion().getDescripcion();
				datos[i][2]=xMenu.isHabilitado() == Boolean.TRUE.booleanValue() ? Boolean.TRUE : Boolean.FALSE;
				datos[i++][3]=xMenu.isAutorizado() == Boolean.TRUE.booleanValue() ? Boolean.TRUE : Boolean.FALSE;
			}
			fireTableDataChanged();
		} catch (NullPointerException e){
			logger.error("llenarTabla(Perfil)", e);

			this.iniciarDatosTabla(titulos.length);
		} catch (BaseDeDatosExcepcion e) {
			logger.error("llenarTabla(Perfil)", e);

			MensajesVentanas.mensajeError(e.getMensaje());
		} catch (PerfilExcepcion e) {
			logger.error("llenarTabla(Perfil)", e);

			MensajesVentanas.mensajeError(e.getMensaje());
		} catch (ConexionExcepcion e) {
			MensajesVentanas.mensajeError(e.getMensaje());
			logger.error("llenarTabla(Perfil)", e);
		} catch (ExcepcionCr e) {
			MensajesVentanas.mensajeError(e.getMensaje());
			logger.error("llenarTabla(Perfil)", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("llenarTabla(Perfil) - end");
		}
		return totalFunciones;
	}

}