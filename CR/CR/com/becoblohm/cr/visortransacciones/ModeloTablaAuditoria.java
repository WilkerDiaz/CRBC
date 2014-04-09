/*
 * Creado el 21/10/2004
 *
 * Para cambiar la plantilla para este archivo generado vaya a
 * Ventana&gt;Preferencias&gt;Java&gt;Generación de código&gt;Código y comentarios
 */
package com.becoblohm.cr.visortransacciones;

import java.text.NumberFormat;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import com.becoblohm.cr.manejarventa.SavedTransaccion;

/**
 * @author Arístides Castillo
 *
 * Clase de implementación del modelo de la tabla usada en el Applet Visor de Auditoria
 */
public class ModeloTablaAuditoria extends AbstractTableModel {
	
	Vector<SavedTransaccion> transacciones;
	/**
	 * Constructor por defecto. Coloca un vector nulo.
	 *
	 */
	public ModeloTablaAuditoria() {
		this(null);
	}
	/**
	 * Constructor de la clase. Espera el vector de transacciones
	 * @param trans
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public ModeloTablaAuditoria(Vector<SavedTransaccion> trans) {
		super();
		transacciones = trans;
	}

	/* (no Javadoc)
	 * @see javax.swing.table.TableModel#getColumnCount()
	 */
	public int getColumnCount() {
		return 6;
	}
	
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Class'
	* Fecha: agosto 2011
	*/
	public Class<?> getColumnClass(int columnIndex) {
		Class<?> colClass = Object.class;
		switch (columnIndex) {
			case 0 : 
				colClass = Integer.class;
				break;
			case 1 : 
				colClass = String.class;
				break;
			case 2 : 
				colClass = String.class;
				break;
			case 3 : 
				colClass = String.class;
				break;
			case 4 : 
				colClass = String.class;
				break;
			case 5 : 
				colClass = String.class;
				break;
		}
		return colClass;
	}
	
	public String getColumnName(int columnIndex) {
		String colName = null;
		switch (columnIndex) {
			case 0 : 
				colName = "# Trans.";
				break;
			case 1 : 
				colName = "Hora";
				break;
			case 2 : 
				colName = "Tipo";
				break;
			case 3 : 
				colName = "Cliente";
				break;
			case 4 : 
				colName = "Subtotal";
				break;
			case 5 : 
				colName = "Total";
				break;
		}
		return colName;
	}

	/* (no Javadoc)
	 * @see javax.swing.table.TableModel#getRowCount()
	 */
	 
	public int getRowCount() {
		int rowCnt = 0;		
		if (transacciones != null) {
			rowCnt = transacciones.size();
		} 
		return rowCnt;
	}

	/* (no Javadoc)
	 * @see javax.swing.table.TableModel#getValueAt(int, int)
	 */
	public Object getValueAt(int rowIndex, int columnIndex) {
		Object o = null;
		SavedTransaccion t;
		NumberFormat nf; 		
		if (transacciones != null) {
			switch (columnIndex) {
				case 0 : // Num. transaccion
				 	o = new Integer(((SavedTransaccion)transacciones.elementAt(rowIndex)).getNumTransaccion());
					break;
				case 1 : // Hora
					o = ((SavedTransaccion)transacciones.elementAt(rowIndex)).getHoraFin().toString();
					break;
				case 2 : // Tipo
					char[] cad = new char[1];
					cad[0] = ((SavedTransaccion)transacciones.elementAt(rowIndex)).getTipoTransaccion();
					o = new String(cad);
					break;
				case 3 : // Cliente
					o = ((SavedTransaccion)transacciones.elementAt(rowIndex)).getCliente().getCodCliente();
					break;
				case 4 : // Subtotal\
					nf = NumberFormat.getCurrencyInstance();
					t = (SavedTransaccion)transacciones.elementAt(rowIndex);
					o = nf.format(t.getMontoBase());
					break;
				case 5 : // Total
					nf = NumberFormat.getCurrencyInstance();
					t = (SavedTransaccion)transacciones.elementAt(rowIndex);
					o = nf.format(t.getMontoBase() + t.getMontoImpuesto());
					break;			
			}
		}
		return o;
	}
	
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public void setTransacciones(Vector<SavedTransaccion> transacciones) {
		this.transacciones = transacciones;
		fireTableDataChanged();
	}
	
	public SavedTransaccion getTransaccion(int index) {
		SavedTransaccion t = null;
		if (transacciones != null) {
			t = (SavedTransaccion)transacciones.elementAt(index);
		}
		return t;
	}
	
}
