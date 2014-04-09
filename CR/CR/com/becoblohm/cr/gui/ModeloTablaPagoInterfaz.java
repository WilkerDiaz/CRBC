/**
 * =============================================================================
 * Proyecto   : CRTrabajando
 * Paquete    : com.becoblohm.cr.gui
 * Programa   : ModeloTablaPagoInterfaz.java
 * Creado por : gmartinelli
 * Creado en  : 30/04/2004 08:11
 *
 * (c) Becoblohm, C.A.
 * -----------------------------------------------------------------------------
 * 				Actualizaciones
 * -----------------------------------------------------------------------------
 * Versión     : 
 * Fecha       : 
 * Analista    : 
 * Descripción : 
 * =============================================================================
 */
package com.becoblohm.cr.gui;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import org.apache.log4j.Logger;

import com.becoblohm.cr.extensiones.impl.manejarpago.Pago;

/*
* En esta clase se realizaron modificaciones referentes a la migración a java 1.6 por jperez
* Sólo se parametrizó el tipo de dato contenido en los 'Class'
* Fecha: agosto 2011
*/
public class ModeloTablaPagoInterfaz extends AbstractTableModel implements Serializable
{
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(ModeloTablaPagoInterfaz.class);

	DecimalFormat df = new DecimalFormat("#,##0.00");
	//DecimalFormat dfEntero = new DecimalFormat("00");
	//SimpleDateFormat fechaServ = new SimpleDateFormat("dd/MM/yyyy");
	String[] titulos = new String[]{"Forma de Pago","Monto"};
	Object[][] datos = {};
	Class<?>[] types = new Class[]{String.class, String.class};

	/**
	 * Constructor para ModeloTablaPagoInterfaz.java
	 *
	 * 
	 */
	public ModeloTablaPagoInterfaz()
	{
		super();
		fireTableStructureChanged();
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
	 */
	public void iniciarDatosTabla() {
		if (logger.isDebugEnabled()) {
			logger.debug("iniciarDatosTabla() - start");
		}

		datos = new Object[0][titulos.length];
		fireTableDataChanged();

		if (logger.isDebugEnabled()) {
			logger.debug("iniciarDatosTabla() - end");
		}
	}
	
	/**
	 * Método llenarTablaPagos
	 * 
	 * @param pagos
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public void llenarTablaPagos (Vector<Pago> pagos)
	{
		if (logger.isDebugEnabled()) {
			logger.debug("llenarTablaPagos(Vector) - start");
		}

		try{
			if (pagos.size() > 0) {
				datos = new Object[pagos.size()][titulos.length];
				for(int i=0; i< pagos.size(); i++)
				{
					datos[i][0]=((Pago)pagos.elementAt(i)).getFormaPago().getNombre();
					datos[i][1]= df.format(new Double(((Pago)pagos.elementAt(i)).getMonto()));
				}
				fireTableStructureChanged();
				fireTableDataChanged();
			} else 
				iniciarDatosTabla();
		} catch (NullPointerException e){
			logger.error("llenarTablaPagos(Vector)", e);

			this.iniciarDatosTabla();
		} catch (Exception e){
			logger.error("llenarTablaPagos(Vector)", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("llenarTablaPagos(Vector) - end");
		}
	}
}