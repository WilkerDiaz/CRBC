/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblohm.cr.manejarpago
 * Programa   : ModeloTablaDetallesCambiados.java
 * Creado por : jgraterol
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
package com.beco.cr.actualizadorPrecios.promociones.interfaz; 

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import com.becoblohm.cr.manejarventa.Detalle;

/*
* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
* Sólo se parametrizó el tipo de dato contenido en los 'Class'
* Fecha: agosto 2011
*/
public class ModeloTablaDetallesCambiados extends AbstractTableModel implements Serializable
{
	DecimalFormat df = new DecimalFormat("#,##0.00");
	DecimalFormat dfEntero = new DecimalFormat("00");
	SimpleDateFormat fechaServ = new SimpleDateFormat("dd/MM/yyyy");
	String[] titulos = new String[]{"Cod. Articulo","Descripcion", "Cantidad", "P. Regular", "P.Final", "Monto", "Cvta", "P.Dcto"};
	Object[][] datos = {{"","",new Float(0),new Double(0),new Double(0),new Double(0),"",""}};
	Class<?>[] types = new Class[]{String.class, String.class, Number.class, Number.class, Number.class, Number.class, String.class,String.class};

	/**
	 * Constructor para ModeloTabla.
	 */
	public ModeloTablaDetallesCambiados()
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
		return false;
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
	
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public void llenarTabla (Vector<Vector<Object>> detalles)
	{
		String[] titulos = new String[]{"Cod. Articulo","Descripcion", "Cantidad", "P. Regular", "P.Final", "Monto", "Cvta", "P.Dcto"};
		//Object[][] datos = {{"","",new Float(0),new Double(0),new Double(0),new Double(0),"",""}};
		
		
		try{
			if (detalles.size() > 0) {
				datos = new Object[detalles.size()][titulos.length];
				for(int i=0; i< detalles.size(); i++)
				{
					Detalle dt = (Detalle)(detalles.elementAt(i)).elementAt(0); 
					Double porcentaje = (Double)(detalles.elementAt(i)).elementAt(1);
					datos[i][0]= dt.getProducto().getCodProducto();
					datos[i][1]= dt.getProducto().getDescripcionLarga();
					datos[i][2]= new Float(dt.getCantidad());
					datos[i][3]= new Double(dt.getProducto().getPrecioRegular());
					datos[i][4]= new Double(dt.getPrecioFinal());
					datos[i][5]= new Double(dt.getPrecioFinal()*dt.getCantidad());
					datos[i][6]= dt.getCondicionVenta();
					datos[i][7]= porcentaje;				
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