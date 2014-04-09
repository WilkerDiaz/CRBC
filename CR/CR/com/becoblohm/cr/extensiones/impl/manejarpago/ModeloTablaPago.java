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
 * Versi�n     : 
 * Fecha       : 
 * Analista    : 
 * Descripci�n : 
 * =============================================================================
 */
package com.becoblohm.cr.extensiones.impl.manejarpago; 

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

/*
* En esta funci�n se realizaron modificaciones referentes a la migraci�n a java 1.6 por jperez
* S�lo se parametriz� el tipo de dato contenido en los 'Class'
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
	 * M�todo getColumnCount.
	 * @return int
	 */
	public int getColumnCount()
	{
		return titulos.length;
	}

	/**
	 * M�todo getRowCount.
	 * @return int
	 */
	public int getRowCount()
	{
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
		return datos[row][col];
	}

	/**
	 * M�todo getColumnName.
	 * @param col
	 * @return String
	 */
	public String getColumnName(int col)
	{
		return titulos[col];
	}

	/**
	 * M�todo isCellEditable.
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
	 * M�todo setValueAt
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
	 * M�todo getColumnClass.
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
	 * Retorna el listado de t�tulos.
	 * @return String[]
	 */
	public String[] getTitulos() {
		return titulos;
	}

	/**
	 * M�todo getTypes
	 * @return Class[]
	 */
	public Class<?>[] getTypes() {
		return types;
	}

	/**
	 * M�todo setTypes
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
	 * Establece los t�tulos.
	 * @param titulos 
	 */
	public void setTitulos(String[] titulos) {
		this.titulos = titulos;
	}

	/**
	 * M�todo iniciarDatosTabla.
	 * 	Inicializa los datos de la tabla.
	 * @param columnas - N�mero de columnas del modelo de tabla actual. 
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
