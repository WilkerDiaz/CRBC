/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblohm.cr.gui
 * Programa   : ModeloTablaConsulta.java
 * Creado por : Programador3 - Alexis Guédez López
 * Creado en  : 11/02/2004 06:35:34 PM
 *
 * (c) Becoblohm, C.A.
 * -----------------------------------------------------------------------------
 * 				Actualizaciones
 * -----------------------------------------------------------------------------
 * Versión     : 1.4 (según CVS)
 * Fecha       : 17/02/2004 03:54:34 PM
 * Analista    : Programador3 - Alexis Guédez López
 * Descripción : Agregado el método iniciarDatosTabla y los constructores:
 * 					ModeloTabla(Object[][] xDatos, String[] xTitulos, Class[]xTipos)
 * 					ModeloTabla(Object[][] xDatos, String[] xTitulos)
 * 					ModeloTabla(Object[][] xDatos)  
 * =============================================================================
 */
package com.becoblohm.cr.gui;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import org.apache.log4j.Logger;

/*
* En esta clase se realizaron modificaciones referentes a la migración a java 1.6 por jperez
* Sólo se parametrizó el tipo de dato contenido en los 'Class'
* Fecha: agosto 2011
*/
public class ModeloTablaConsulta extends AbstractTableModel implements Serializable
{
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(ModeloTablaConsulta.class);

	DecimalFormat df = new DecimalFormat("#,##0.00");
	DecimalFormat dfEntero = new DecimalFormat("00");
	String[] titulos = new String[]{"Cod. Articulo","Cod. Externo","Descripcion","Precio"};
	Object[][] datos = {{"","","",new Double(0)}};
	Class<?>[] types = new Class[]{String.class, String.class, String.class, Number.class};

	/**
	 * Constructor para ModeloTabla.
	 * 
	 */
	public ModeloTablaConsulta()
	{
		super();
	}

	/**
	 * Constructor para ModeloTabla.
	 * @param xDatos
	 * @param xTitulos
	 */
	public ModeloTablaConsulta(Object[][] xDatos, String[] xTitulos, Class<?>[]xTipos)
	{
		datos = xDatos;
		titulos = xTitulos;
		types = xTipos;
	}

	public ModeloTablaConsulta(Object[][] xDatos,String[] xTitulos)
	{
		datos = xDatos;
		titulos = xTitulos;
	}

	public ModeloTablaConsulta(Object[][] xDatos)
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

		if (col==1)
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
			} else if (this.getTypes()[i].getName().equals("java.lang.Float")){
				datos[0][i] = new Float(0.00);
			}
			else datos[0][i] = "";
		}
		fireTableDataChanged();

		if (logger.isDebugEnabled()) {
			logger.debug("iniciarDatosTabla(int) - end");
		}
	}
	
	/**
	 * Método cambiarDatosTabla
	 * 
	 * @param nuevaEstructura
	 */
	public void cambiarDatosTabla(Vector<Object> nuevaEstructura) {
		if (logger.isDebugEnabled()) {
			logger.debug("cambiarDatosTabla(Vector) - start");
		}

		// separamos la estructura en titulosy tipos
		this.titulos = (String[])nuevaEstructura.elementAt(0);
		this.types = (Class[])nuevaEstructura.elementAt(1);
		fireTableStructureChanged();
		
		// Agregamos los datos
		this.datos = (Object[][])nuevaEstructura.elementAt(2);
		fireTableDataChanged();

		if (logger.isDebugEnabled()) {
			logger.debug("cambiarDatosTabla(Vector) - end");
		}
	}
}