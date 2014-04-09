/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblohm.cr.gui.listaregalos
 * Programa   : ModeloTablaDetalleRegalo.java
 * Creado por : rabreu
 * Creado en  : 03/07/2006
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
package com.becoblohm.cr.gui.listaregalos;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Vector;

import javax.swing.event.ChangeEvent;
import javax.swing.table.AbstractTableModel;

import com.becoblohm.cr.CR;
import com.becoblohm.cr.manejarservicio.OperacionLR;


/*
* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
* Sólo se parametrizó el tipo de dato contenido en los 'Class'
* Fecha: agosto 2011
*/
public class ModeloTablaDetalleRegalo extends AbstractTableModel implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected ChangeEvent changeEvent = new ChangeEvent(this);

	DecimalFormat df = new DecimalFormat("#,##0.00");
	DecimalFormat dfEntero = new DecimalFormat("00");
	String[] titulos;
	Object[][] datos;
	Class<?>[] types;

	/**
	 * Constructor para ModeloTabla.
	 */
	public ModeloTablaDetalleRegalo() {
		titulos = new String[]{"Fecha","Operacion","Cant./Monto","Invitado","Dedicatoria"};
		Object[][] datos6 = {{"","",new Double(0),"",""}};
		datos = datos6;
		types = new Class[]{String.class, String.class, Number.class, String.class, String.class};
	}

	/**
	 * Método getColumnCount.
	 * @return int
	 */
	public int getColumnCount() {
		return titulos.length;
	}

	/**
	 * Método getRowCount.
	 * @return int
	 */
	public int getRowCount() {
		return datos.length;
	}

	/**
	 * Método getValueAt.
	 * @param row
	 * @param col
	 * @return Object
	 */
	public Object getValueAt(int row,int col) {
		Object returnObject = datos[row][col];
		return returnObject;
	}

	/**
	 * Método getColumnName.
	 * @param col
	 * @return String
	 */
	public String getColumnName(int col) {
		String returnString = titulos[col];
		return returnString;
	}

	/**
	 * Método isCellEditable.
	 * @param row
	 * @param col
	 * @return boolean
	 */
	public boolean isCellEditable(int row,int col) {
		return false;
	}

	/**
	 * Método setValueAt
	 * @param valorObjeto
	 * @param row
	 * @param col
	 */
	public void setValueAt(Object valorObjeto, int row, int col) {
		datos[row][col] = valorObjeto;
		fireTableCellUpdated(row,col);
	} 

	/**
	 * Método getColumnClass.
	 * @param c
	 * @return Class
	 */
	public Class<?> getColumnClass(int c) {
		Class<?> returnClass = types[c];
		return returnClass;
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
		for(int i=0; i<columnas; i++)
			if (this.getTypes()[i].getName().equals("java.lang.Number"))
				datos[0][i] = new Integer(0);
			else if (this.getTypes()[i].getName().equals("java.lang.Double"))
				datos[0][i] = new Double(0.00);
			else if (this.getTypes()[i].getName().equals("java.lang.Float"))
				datos[0][i] = new Float(0.00);
			else datos[0][i] = "";
		fireTableDataChanged();
	}

	/**
	 * Método llenarTablaDetalleLR.
	 * 	Llena la tabla correspondiente al detalle del apartado, asociada
	 * al modelo de tabla. 
	 * 
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public void llenarTabla(String codProd) {
		int tamaño=0;
		Vector<OperacionLR> operaciones = CR.meServ.getListaRegalos().getOperaciones();
		for(int i=0;i<operaciones.size();i++){
			OperacionLR operacion = (OperacionLR)operaciones.get(i);
			if(operacion.getCodProducto().substring(0, 9).equals(codProd.substring(0, 9)))
				tamaño++;
		}

		Object[][] datos = new Object[tamaño++][getColumnCount()];
		int fila=0;		
		for(int i=0;i<operaciones.size();i++){
			OperacionLR operacion = (OperacionLR)operaciones.get(i);
			if(operacion.getCodProducto().substring(0, 9).equals(codProd.substring(0, 9))) {
				int columna=0;
				datos[fila][columna++] = new SimpleDateFormat("dd-MM-yyyy").format(operacion.getFecha());;
				if(operacion.getTipoOper() == 'A'){
					datos[fila][columna++] = new String("ABONO");
					datos[fila][columna++] = df.format((operacion.getMontoBase() + operacion.getMontoImpuesto()) * operacion.getCantidad());
				}else if(operacion.getTipoOper() == 'T'){
						datos[fila][columna++] = new String("ABONO TOTAL");
						datos[fila][columna++] = df.format((operacion.getMontoBase() + operacion.getMontoImpuesto()) * operacion.getCantidad());				
				}else if(operacion.getTipoOper() == 'V'){
					datos[fila][columna++] = new String("COMPRA");
					datos[fila][columna++] = df.format(operacion.getCantidad());				
				}else if(operacion.getTipoOper() == 'L'){
					datos[fila][columna++] = new String("ABONO A LISTA");
					datos[fila][columna++] = df.format((operacion.getMontoBase() + operacion.getMontoImpuesto()) * operacion.getCantidad());
				}
				datos[fila][columna++] = operacion.getNomCliente();
				datos[fila][columna++] = operacion.getDedicatoria();
				fila++;
			}
		}
		if(datos.length > 0)
			this.datos = datos;
		fireTableDataChanged();
	}

	/**
	 * @param vector
	 */
	public void refrescarTabla(Vector<Object> nuevaEstructura) {
		// separamos la estructura en titulos y tipos
		this.titulos = (String[])nuevaEstructura.elementAt(0);
		this.types = (Class[])nuevaEstructura.elementAt(1);
		fireTableStructureChanged();
				
		// Agregamos los datos
		this.datos = (Object[][])nuevaEstructura.elementAt(2);
		fireTableDataChanged();
	}
	
	/**
	 * @param vector
	 */
	public void refrescarDatos(Object[][] nuevosDatos) {
		// Agregamos los datos
		this.datos = nuevosDatos;
		fireTableDataChanged();
	}

	/**
	 * @param codLista
	 * @param codProducto
	 */
	public void refrescarTabla(String codLista, String codProducto) {
		
	}
}
