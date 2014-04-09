/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblohm.cr.gui
 * Programa   : ModeloTablaVenta.java
 * Creado por : yzambrano
 * Creado en  : 17-08-2006
 *
 * (c) Becoblohm, C.A.
 * -----------------------------------------------------------------------------
 * 				Actualizaciones
 * -----------------------------------------------------------------------------
 * Versión     : 2.0
 * Fecha       : 17-08-2006
 * Analista    : 
 * Descripción : 
 * =============================================================================
 */
package com.becoblohm.cr.gui;

import java.io.Serializable;
import java.text.DecimalFormat;

import javax.swing.table.AbstractTableModel;

import org.apache.log4j.Logger;

import com.becoblohm.cr.CR;
import com.becoblohm.cr.manejadorsesion.Sesion;
import com.becoblohm.cr.manejarventa.DetalleTransaccion;

/*
* En esta clase se realizaron modificaciones referentes a la migración a java 1.6 por jperez
* Sólo se parametrizó el tipo de dato contenido en los 'Class'
* Fecha: agosto 2011
*/
public class ModeloTablaVenta extends AbstractTableModel implements Serializable
{
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(ModeloTablaVenta.class);

	String[] titulos;
	Object[][] datos;
	Class<?>[] types;

	
	DecimalFormat df = new DecimalFormat("#,##0.00");
	DecimalFormat dfEntero = new DecimalFormat("00");
	

	/**
	 * Constructor para ModeloTabla.
	 * @param xDatos
	 * @param xTitulos
	 */
	
	public ModeloTablaVenta()
	{
		titulos = new String[]{"Devolver", "Cod. Articulo","Descripcion","C. Vendida","C. Devolver","P.Regular","P.Final", "Monto", "Cvta"};
		Object[][] datos1 = {{Boolean.FALSE,"","",new Float(0),new Float(0),new Double(0),new Double(0),new Double(0),""}};
		datos = datos1;
		types = new Class[]{Boolean.class,String.class, String.class, Number.class, Number.class,Number.class, Number.class, Number.class, String.class};
	}
	
	public ModeloTablaVenta(Object[][] xDatos, String[] xTitulos, Class<?>[]xTipos)
	{
		datos = xDatos;
		titulos = xTitulos;
		types = xTipos;
	}

	public ModeloTablaVenta(Object[][] xDatos,String[] xTitulos)
	{
		datos = xDatos;
		titulos = xTitulos;
	}

	public ModeloTablaVenta(Object[][] xDatos)
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

		if (col==0)
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
	 * 	Actualiza los datos de la tabla con la seleccion de los artículos a devolver.
	 * 
	 */
	public void llenarTabla(){
		if (logger.isDebugEnabled()) {
			logger.debug("llenarTabla(Perfil) - start");
		}
		try{
			datos = new Object[CR.meVenta.getDevolucion().getVentaOriginal().getDetallesTransaccion().size()][titulos.length];
			for(int i=0; i<CR.meVenta.getDevolucion().getVentaOriginal().getDetallesTransaccion().size(); i++)
			{
				int j=1;
				String codProd = ((DetalleTransaccion)CR.meVenta.getDevolucion().getVentaOriginal().getDetallesTransaccion().elementAt(i)).getProducto().getCodProducto().toString();
				int longitud = codProd.length();
				if ((Sesion.getLongitudCodigoInterno()>-1)&&(longitud>Sesion.getLongitudCodigoInterno())) {
					codProd = codProd.substring(codProd.length()-Sesion.getLongitudCodigoInterno()); 
					for (int k=0;k<longitud-Sesion.getLongitudCodigoInterno();k++) {
						codProd = " " + codProd; 
					}
				}
				datos[i][j]=codProd;j++; 
				datos[i][j]=((DetalleTransaccion)CR.meVenta.getDevolucion().getVentaOriginal().getDetallesTransaccion().elementAt(i)).getProducto().getDescripcionCorta();j++;
				datos[i][j]=df.format(new Float(((DetalleTransaccion)CR.meVenta.getDevolucion().getVentaOriginal().getDetallesTransaccion().elementAt(i)).getCantidad()));j++;
				datos[i][j]=df.format(new Float(((DetalleTransaccion)CR.meVenta.getDevolucion().getVentaOriginal().getDetallesTransaccion().elementAt(i)).getCantidadADevolver()));j++;
				datos[i][j]=df.format(new Double(((DetalleTransaccion)CR.meVenta.getDevolucion().getVentaOriginal().getDetallesTransaccion().elementAt(i)).getProducto().getPrecioRegular()));j++;
				datos[i][j]=df.format(new Double(((DetalleTransaccion)CR.meVenta.getDevolucion().getVentaOriginal().getDetallesTransaccion().elementAt(i)).getPrecioFinal()));j++;
				datos[i][j]=df.format(new Double(((DetalleTransaccion) CR.meVenta.getDevolucion().getVentaOriginal().getDetallesTransaccion().elementAt(i)).getCantidad() * ((DetalleTransaccion) CR.meVenta.getDevolucion().getVentaOriginal().getDetallesTransaccion().elementAt(i)).getPrecioFinal()));j++;
				datos[i][j]=((DetalleTransaccion)CR.meVenta.getDevolucion().getVentaOriginal().getDetallesTransaccion().elementAt(i)).getCondicionVenta();j++;
					
				if(Sesion.isUtilizarVendedor()){
					datos[i][j]=((DetalleTransaccion)CR.meVenta.getDevolucion().getVentaOriginal().getDetallesTransaccion().elementAt(i)).getCodVendedor();j++;
				}
			}
			for(int i=0; i<CR.meVenta.getDevolucion().getDetallesTransaccion().size(); i++)
			{
				for(int j=0; (j<CR.meVenta.getDevolucion().getVentaOriginal().getDetallesTransaccion().size()); j++)
				{
					String codProd = ((DetalleTransaccion)CR.meVenta.getDevolucion().getVentaOriginal().getDetallesTransaccion().elementAt(j)).getProducto().getCodProducto().toString();
					if(((DetalleTransaccion)CR.meVenta.getDevolucion().getDetallesTransaccion().elementAt(i)).isDevuelto(codProd) == true) {
						datos[j][0] = Boolean.TRUE;
						datos[j][4] = df.format(new Float(((DetalleTransaccion)CR.meVenta.getDevolucion().getDetallesTransaccion().elementAt(i)).getCantidadADevolver()));
					}
				}	
			}
			fireTableDataChanged();
		} catch (NullPointerException e){
			logger.error("llenarTablaVentaOriginal()", e);

			this.iniciarDatosTabla(titulos.length);
		} catch (Exception e){
			logger.error("llenarTablaVentaOriginal()", e);
			e.printStackTrace();
		}
		if (logger.isDebugEnabled()) {
			logger.debug("llenarTabla(Perfil) - end");
		}
	}
	
	/**
	 * Llena la tabla con la venta original recuperada
	 *
	 */
	 
	public void llenarTablaVentaOriginal()
		{
			if (logger.isDebugEnabled()) {
				logger.debug("llenarTablaVentaOriginal() - start");
			}

			try{
				datos = new Object[CR.meVenta.getDevolucion().getVentaOriginal().getDetallesTransaccion().size()][titulos.length];
				for(int i=0; i<CR.meVenta.getDevolucion().getVentaOriginal().getDetallesTransaccion().size(); i++)
				{
					int j=1;
					String codProd = ((DetalleTransaccion)CR.meVenta.getDevolucion().getVentaOriginal().getDetallesTransaccion().elementAt(i)).getProducto().getCodProducto().toString();
					int longitud = codProd.length();
					if ((Sesion.getLongitudCodigoInterno()>-1)&&(longitud>Sesion.getLongitudCodigoInterno())) {
						codProd = codProd.substring(codProd.length()-Sesion.getLongitudCodigoInterno()); 
						for (int k=0;k<longitud-Sesion.getLongitudCodigoInterno();k++) {
							codProd = " " + codProd; 
						}
					}
					datos[i][j]=codProd;j++; 
					datos[i][j]=((DetalleTransaccion)CR.meVenta.getDevolucion().getVentaOriginal().getDetallesTransaccion().elementAt(i)).getProducto().getDescripcionCorta();j++;
					datos[i][j]=df.format(new Float(((DetalleTransaccion)CR.meVenta.getDevolucion().getVentaOriginal().getDetallesTransaccion().elementAt(i)).getCantidad()));j++;
					datos[i][j]=df.format(new Float(((DetalleTransaccion)CR.meVenta.getDevolucion().getVentaOriginal().getDetallesTransaccion().elementAt(i)).getCantidadADevolver()));j++;
					datos[i][j]=df.format(new Double(((DetalleTransaccion)CR.meVenta.getDevolucion().getVentaOriginal().getDetallesTransaccion().elementAt(i)).getProducto().getPrecioRegular()));j++;
					datos[i][j]=df.format(new Double(((DetalleTransaccion)CR.meVenta.getDevolucion().getVentaOriginal().getDetallesTransaccion().elementAt(i)).getPrecioFinal()));j++;
					datos[i][j]=df.format(new Double(((DetalleTransaccion) CR.meVenta.getDevolucion().getVentaOriginal().getDetallesTransaccion().elementAt(i)).getCantidad() * ((DetalleTransaccion) CR.meVenta.getDevolucion().getVentaOriginal().getDetallesTransaccion().elementAt(i)).getPrecioFinal()));j++;
					datos[i][j]=((DetalleTransaccion)CR.meVenta.getDevolucion().getVentaOriginal().getDetallesTransaccion().elementAt(i)).getCondicionVenta();j++;
					if(Sesion.isUtilizarVendedor()){
						datos[i][j]=((DetalleTransaccion)CR.meVenta.getDevolucion().getVentaOriginal().getDetallesTransaccion().elementAt(i)).getCodVendedor();j++;
					}
				}
						
				
			fireTableDataChanged();
			} catch (NullPointerException e){
				logger.error("llenarTablaVentaOriginal()", e);

				this.iniciarDatosTabla(titulos.length);
			} catch (Exception e){
				logger.error("llenarTablaVentaOriginal()", e);
			}

			if (logger.isDebugEnabled()) {
				logger.debug("llenarTablaVentaOriginal() - end");
			}
		}



}