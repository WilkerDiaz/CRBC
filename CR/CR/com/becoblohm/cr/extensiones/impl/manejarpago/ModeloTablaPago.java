/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblohm.cr.manejarpago
 * Programa   : ModeloTablaPago.java
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
package com.becoblohm.cr.extensiones.impl.manejarpago; 

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

/*
* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
* Sólo se parametrizó el tipo de dato contenido en los 'Class'
* Fecha: agosto 2011
*/
public class ModeloTablaPago extends AbstractTableModel implements Serializable
{
	DecimalFormat df = new DecimalFormat("#,##0.00");
	DecimalFormat dfEntero = new DecimalFormat("00");
	SimpleDateFormat fechaServ = new SimpleDateFormat("dd/MM/yyyy");
	String[] titulos = new String[]{"Forma de Pago","Monto"};
	Object[][] datos = {{"", new Double(0.00)}};
	Class<?>[] types = new Class[]{String.class, Number.class};

	/**
	 * Constructor para ModeloTabla.
	 */
	public ModeloTablaPago()
	{
		super();
	}

	/**
	 * Método getColumnCount.
	 * @return int
	 */
	public int getColumnCount()
	{
		return titulos.length;
	}

	/**
	 * Método getRowCount.
	 * @return int
	 */
	public int getRowCount()
	{
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
		return datos[row][col];
	}

	/**
	 * Método getColumnName.
	 * @param col
	 * @return String
	 */
	public String getColumnName(int col)
	{
		return titulos[col];
	}

	/**
	 * Método isCellEditable.
	 * @param row
	 * @param col
	 * @return boolean
	 */
	public boolean isCellEditable(int row,int col)
	{
		if (col==1)
		{
			return true;
		}
		else
		{
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
		datos[row][col] = valorObjeto;
		fireTableCellUpdated(row,col);
	} 

	/**
	 * Método getColumnClass.
	 * @param c
	 * @return Class
	 */
	public Class<?> getColumnClass(int c)
	{
	  return types[c];
	}

	/**
	 * Retorna los datos de la tabla.
	 * @return Object[][]
	 */
	public Object[][] getDatos() {
		return datos;
	}

	/**
	 * Retorna el listado de títulos.
	 * @return String[]
	 */
	public String[] getTitulos() {
		return titulos;
	}

	/**
	 * Método getTypes
	 * @return Class[]
	 */
	public Class<?>[] getTypes() {
		return types;
	}

	/**
	 * Método setTypes
	 * @param classes
	 */
	public void setTypes(Class<?>[] classes) {
		types = classes;
	}

	/**
	 * Establece los datos.
	 * @param datos
	 */
	public void setDatos(Object[][] datos) {
		this.datos = datos;
	}

	/**
	 * Establece los títulos.
	 * @param titulos 
	 */
	public void setTitulos(String[] titulos) {
		this.titulos = titulos;
	}

	/**
	 * Método iniciarDatosTabla.
	 * 	Inicializa los datos de la tabla.
	 * @param columnas - Número de columnas del modelo de tabla actual. 
	 */
	public void iniciarDatosTabla(int columnas) {
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
	}
	
	public void llenarTablaPagos (Vector<Pago> pagos)
	{
		try{
			if (pagos.size() > 0) {
				datos = new Object[pagos.size()][titulos.length];
				for(int i=0; i< pagos.size(); i++)
				{
					datos[i][0]=(pagos.elementAt(i)).getFormaPago().getNombre();
					datos[i][1]= df.format(new Double((pagos.elementAt(i)).getMonto()));
				}
				fireTableDataChanged();
			}
		} catch (NullPointerException e){
			this.iniciarDatosTabla(titulos.length);
		} catch (Exception e){
			e.printStackTrace();
		}
	}
}
