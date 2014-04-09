/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblohm.cr.gui
 * Programa   : ModeloTablaComanda.java
 * Creado por : Programador3
 * Creado en  : 27/07/2004 11:19:07 PM
 *
 * (c) Becoblohm, C.A.
 * -----------------------------------------------------------------------------
 * 				Actualizaciones
 * -----------------------------------------------------------------------------
 * Versi�n     : 1.1
 * Fecha       : 27/07/2004 11:19:07 PM
 * Analista    : Programador3
 * Descripci�n : Implementaci�n inicial.
 * =============================================================================
 */
package com.becoblohm.cr.gui;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import org.apache.log4j.Logger;

import com.becoblohm.cr.manejadorsesion.Sesion;

/*
* En esta clase se realizaron modificaciones referentes a la migraci�n a java 1.6 por jperez
* S�lo se parametriz� el tipo de dato contenido en los 'Class'
* Fecha: agosto 2011
*/
public class ModeloTablaComanda extends AbstractTableModel implements Serializable
{
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(ModeloTablaComanda.class);

	DecimalFormat df = new DecimalFormat("#,###.00");
	String[] titulos;
	Object[][] datos;
	Class<?>[] types;

	public ModeloTablaComanda()
	{
		super();
		
		if (Sesion.isUtilizarVendedor()) {
			titulos = new String[]{"Comanda","Cod. Articulo","Descripcion","Cantidad","Facturado","Vendedor"};
			Object[][] datos1 = {{"","","",new Float(0.00),"",""}};
			datos = datos1;
			types = new Class[]{String.class, String.class, String.class, Number.class, String.class, String.class};
		} else {
			titulos = new String[]{"Comanda","Cod. Articulo","Descripcion","Cantidad","Facturado"};
			Object[][] datos1 = {{"","","",new Float(0.00),""}};
			datos = datos1;
			types = new Class[]{String.class, String.class, String.class, Number.class, String.class};
		}
	}

	/**
	 * Constructor para ModeloTabla.
	 * @param xDatos
	 * @param xTitulos
	 */
	public ModeloTablaComanda(Object[][] xDatos, String[] xTitulos, Class<?>[]xTipos)
	{
		datos = xDatos;
		titulos = xTitulos;
		types = xTipos;
	}

	public ModeloTablaComanda(Object[][] xDatos,String[] xTitulos)
	{
		datos = xDatos;
		titulos = xTitulos;
	}

	public ModeloTablaComanda(Object[][] xDatos)
	{
		datos = xDatos;
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

		if (col==7)
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
	public void iniciarDatosTabla() {
		if (logger.isDebugEnabled()) {
			logger.debug("iniciarDatosTabla() - start");
		}

		iniciarDatosTabla(titulos.length);

		if (logger.isDebugEnabled()) {
			logger.debug("iniciarDatosTabla() - end");
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
	 * M�todo llenarTabla.
	 * 	Llena la tabla correspondiente al detalle de la factura, asociada
	 * al modelo de tabla. 
	 * 
	 */
	/*
	* En esta funci�n se realizaron modificaciones referentes a la migraci�n a java 1.6 por jperez
	* S�lo se parametriz� el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public void llenarTabla(Vector<Vector<Object>> comandas)
	{
		if (logger.isDebugEnabled()) {
			logger.debug("llenarTabla(Vector) - start");
		}

		try{
			datos = new Object[comandas.size()][titulos.length];
			Vector<Object> detalle = new Vector<Object>();
			
			for(int i=0; i<comandas.size(); i++)
			{
				detalle = comandas.get(i);
				float cantidad = new Float(detalle.elementAt(3).toString()).floatValue(); 
				String codProd = new String(detalle.elementAt(1).toString());
				int longitud = codProd.length();
				if ((Sesion.getLongitudCodigoInterno()>-1)&&(longitud>Sesion.getLongitudCodigoInterno())) {
					codProd = codProd.substring(codProd.length()-Sesion.getLongitudCodigoInterno()); 
					for (int j=0;j<longitud-Sesion.getLongitudCodigoInterno();j++) {
						codProd = " " + codProd; 
					}
				}
				datos[i][0] = new String(detalle.elementAt(0).toString());
				datos[i][1] = codProd;
				datos[i][2] = new String(detalle.elementAt(2).toString());
				datos[i][3] = df.format(cantidad);
				char facturado = new String(detalle.elementAt(5).toString()).toUpperCase().charAt(0) == Sesion.VIGENTE ? Sesion.NO:Sesion.SI;
				datos[i][4] = new Character(facturado);
				if (Sesion.isUtilizarVendedor()) {
					datos[i][5]=new String(detalle.elementAt(6).toString());
				}
			}
			fireTableDataChanged();
		} catch (NullPointerException e){
			logger.error("llenarTabla(Vector)", e);

			this.iniciarDatosTabla(titulos.length);
		} catch (Exception e){
			logger.error("llenarTabla(Vector)", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("llenarTabla(Vector) - end");
		}
	}

}