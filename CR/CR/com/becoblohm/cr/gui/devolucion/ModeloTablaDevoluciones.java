/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblohm.cr.gui
 * Programa   : ModeloTablaApartado.java
 * Creado por : irojas
 * Creado en  : 12/04/2004 11:03:50 AM
 *
 * (c) Becoblohm, C.A.
 * -----------------------------------------------------------------------------
 * 				Actualizaciones
 * -----------------------------------------------------------------------------
 * Versi�n     : 
 * Fecha       : 
 * Analista    : 
 * Descripci�n : 
 * =============================================================================
 */
package com.becoblohm.cr.gui.devolucion;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import org.apache.log4j.Logger;

/*
* En esta clase se realizaron modificaciones referentes a la migraci�n a java 1.6 por jperez
* S�lo se parametriz� el tipo de dato contenido en los 'Class' 
* Fecha: agosto 2011
*/
public class ModeloTablaDevoluciones extends AbstractTableModel implements Serializable
{
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(ModeloTablaDevoluciones.class);

	DecimalFormat df = new DecimalFormat("#,##0.00");
	DecimalFormat dfEntero = new DecimalFormat("00");
	SimpleDateFormat fechaServ = new SimpleDateFormat("dd/MM/yyyy");
	String[] titulos;
	Object[][] datos;
	Class<?>[] types;

	
	
	/**
	 * Constructor para ModeloTablaDevoluciones.
	 */
	/*
	* En esta funci�n se realizaron modificaciones referentes a la migraci�n a java 1.6 por jperez
	* S�lo se parametriz� el tipo de dato contenido en los 'Vector' 
	* Fecha: agosto 2011
	*/
	public ModeloTablaDevoluciones(Vector<String> devoluciones)
	{
		titulos = new String[]{"C�dula Cliente","Nombre Cliente","Tienda","Fecha de Transacci�n","Caja","No. de transacci�n"};
		types = new Class[]{String.class,String.class, String.class,String.class,String.class,String.class};
		this.llenarDevoluciones(devoluciones);
	}

	/**
	 * M�todo getColumnCount.
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
	 * M�todo getRowCount.
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
	 * M�todo getValueAt.
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
	 * M�todo getColumnName.
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
	 * M�todo isCellEditable.
	 * @param row
	 * @param col
	 * @return boolean
	 */
	public boolean isCellEditable(int row,int col)
	{
		if (logger.isDebugEnabled()) {
			logger.debug("isCellEditable(int, int) - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("isCellEditable(int, int) - end");
		}
		return false;
	}

	/**
	 * M�todo setValueAt
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
	 * M�todo getColumnClass.
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
	 * Retorna el listado de t�tulos.
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
	 * M�todo getTypes
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
	 * M�todo setTypes
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
	 * Establece los t�tulos.
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
	 * M�todo iniciarDatosTabla.
	 * 	Inicializa los datos de la tabla.
	 * @param columnas - N�mero de columnas del modelo de tabla actual. 
	 */
	public void iniciarDatosTabla(int columnas) {
		if (logger.isDebugEnabled()) {
			logger.debug("iniciarDatosTabla(int) - start");
		}

		datos = new Object[1][columnas];
		for(int i=0; i<columnas; i++){
			if (this.getTypes()[i].getName().equals("java.lang.Number")){
				datos[0][i] = new Integer(0);
			} else if (this.getTypes()[i].getName().equals("java.lang.Double")){
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
	 * M�todo llenarDevoluciones.
	 * 	Llena la tabla correspondiente a las devoluciones que se encuentran en espera en el servidor de la tienda. 
	 * 
	 */
	/*
	* En esta funci�n se realizaron modificaciones referentes a la migraci�n a java 1.6 por jperez
	* S�lo se parametriz� el tipo de dato contenido en los 'Vector' 
	* Fecha: agosto 2011
	*/
	public void llenarDevoluciones(Vector<String> devoluciones)
	{
		if (logger.isDebugEnabled()) {
			logger.debug("llenarApartados(Vector) - start");
		}

		try{
			datos = new Object[(devoluciones.size()/6)][titulos.length];
			int j = 0;
			int posicion = 0;
			while (j < devoluciones.size()){
				for (int i = 0; i < 6; i++) {
					datos[posicion][i] = devoluciones.elementAt(j);
					j++;
				}
				posicion++;
			}
			fireTableDataChanged();
		} catch (NullPointerException e){
			logger.error("llenarApartados(Vector)", e);

			this.iniciarDatosTabla(titulos.length);
		} catch (Exception e){
			logger.error("llenarApartados(Vector)", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("llenarApartados(Vector) - end");
		}
	}
	
}