/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblohm.cr.gui
 * Programa   : ModeloTabla.java
 * Creado por : Programador3 - Alexis Guédez López
 * Creado en  : 11/02/2004 06:35:34 PM
 *
 * (c) Becoblohm, C.A.
 * -----------------------------------------------------------------------------
 * 				Actualizaciones
 * -----------------------------------------------------------------------------
 * Versión     : 1.5
 * Fecha       : 13/08/2004 02:32 PM
 * Analista    : GMARTINELLI
 * Descripción : - Agregado Campo para indicar precio regular del producto.
 * =============================================================================
 * Versión     : 1.4.1
 * Fecha       : 25/05/2004 01:27:34 PM
 * Analista    : GMARTINELLI
 * Descripción : - Condicionamiento de los títulos para la existencia o no de vendedores 
 * 				en la tienda.
 * =============================================================================
 * Versión     : 1.4 (según CVS)
 * Fecha       : 17/02/2004 03:54:34 PM
 * Analista    : Programador3 - Alexis Guédez López
 * Descripción : Agregado el método iniciarDatosTabla y los constructores:
 * 					ModeloTabla(Object[][] xDatos, String[] xTitulos, Class[]xTipos)
 * 					ModeloTabla(Object[][] xDatos, String[] xTitulos)
 * 					ModeloTabla(Object[][] xDatos)  
 * =============================================================================
 */
package com.becoblohm.cr.gui.bonoregalo;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Vector;
import javax.swing.table.AbstractTableModel;
import org.apache.log4j.Logger;
import com.becoblohm.cr.CR;
import com.becoblohm.cr.manejarbr.DetalleTransaccionBR;

/*
* En clase función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
* Sólo se parametrizó el tipo de dato contenido en los 'Class' 
* Fecha: agosto 2011
*/
public class ModeloTablaBR extends AbstractTableModel implements Serializable
{
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(ModeloTablaBR.class);

	DecimalFormat df = new DecimalFormat("#,##0.00");
	DecimalFormat dfEntero = new DecimalFormat("00");
	String[] titulos;
	Object[][] datos;
	Class<?>[] types;
	

	/**
	 * Constructor para ModeloTabla.
	 * 
	 */
	public ModeloTablaBR()
	{
		super();
		
		titulos = new String[]{"Correlativo","Cod. Tarjeta", "Monto"};
		Object[][] datos1 = {{new Integer(0),"",new Double(0)}};
		datos = datos1;
		types = new Class[]{Number.class,String.class,Number.class};
		
	}


	/**
	 * Constructor para ModeloTabla.
	 * @param xDatos
	 * @param xTitulos
	 */
	public ModeloTablaBR(Object[][] xDatos, String[] xTitulos, Class<?>[]xTipos)
	{
		datos = xDatos;
		titulos = xTitulos;
		types = xTipos;
	}

	public ModeloTablaBR(Object[][] xDatos,String[] xTitulos)
	{
		datos = xDatos;
		titulos = xTitulos;
	}

	public ModeloTablaBR(Object[][] xDatos)
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
	 * Método llenarTabla.
	 * 	Llena la tabla correspondiente al detalle de la factura, asociada
	 * al modelo de tabla. 
	 * 
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public void llenarTabla()
	{
		if (logger.isDebugEnabled()) {
			logger.debug("llenarTabla() - start");
		}

		try{
			if (CR.meServ.getVentaBR() == null) {
				if (logger.isDebugEnabled()) {
					logger.debug("llenarTabla() - end");
				}
				iniciarDatosTabla(titulos.length);
				return;
			}
			Vector<DetalleTransaccionBR>  detalles = CR.meServ.getVentaBR().getDetallesTransaccionBR();
			datos = new Object[detalles.size()][titulos.length];
			for(int i=0; i<detalles.size(); i++)
			{
				String codTarjeta = ((DetalleTransaccionBR)detalles.elementAt(i)).getCodTarjeta();
				datos[i][0] = new Integer(i+1);
				datos[i][1]=codTarjeta==null?"Por definir":codTarjeta;
				datos[i][2]=df.format(new Double(((DetalleTransaccionBR) detalles.elementAt(i)).getMonto()));
			}
			fireTableDataChanged();
		} catch (NullPointerException e){
			logger.error("llenarTabla()", e);

			this.iniciarDatosTabla(titulos.length);
		} catch (Exception e){
			logger.error("llenarTabla()", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("llenarTabla() - end");
		}
	}

	/**
	 * Método llenarTabla.
	 * 	Llena la tabla correspondiente al detalle de la factura, asociada
	 * al modelo de tabla. 
	 * 
	 */
	/*public void llenarTabla(String codTarjeta, boolean isCodigoExterno) throws  MaquinaDeEstadoExcepcion, XmlExcepcion, FuncionExcepcion, BaseDeDatosExcepcion, ConexionExcepcion 
	{
		if (logger.isDebugEnabled()) {
			logger.debug("llenarTabla(String) - start");
		}

		int nuevoIndice;
		
		try{
			if (logger.isDebugEnabled()) {
				logger.debug("llenarTabla(String)" + codProducto);
			}
			Vector renglones = CR.meVenta.obtenerRenglones(codProducto, isCodigoExterno);
			datos = new Object[renglones.size()][titulos.length];
			for(int i=0; i<renglones.size(); i++)
			{				
				nuevoIndice = new Integer(renglones.elementAt(i).toString()).intValue();
				String codProd = ((DetalleTransaccion)CR.meVenta.getVenta().getDetallesTransaccion().elementAt(nuevoIndice)).getProducto().getCodProducto().toString();
				int longitud = codProd.length();
				if ((Sesion.getLongitudCodigoInterno()>-1)&&(longitud>Sesion.getLongitudCodigoInterno())) {
					codProd = codProd.substring(codProd.length()-Sesion.getLongitudCodigoInterno()); 
					for (int j=0;j<longitud-Sesion.getLongitudCodigoInterno();j++) {
						codProd = " " + codProd; 
					}
				}
				datos[i][0]=codProd;
				datos[i][1]=((DetalleTransaccion)CR.meVenta.getVenta().getDetallesTransaccion().elementAt(nuevoIndice)).getProducto().getDescripcionCorta();
				datos[i][2]=df.format(new Float(((DetalleTransaccion)CR.meVenta.getVenta().getDetallesTransaccion().elementAt(nuevoIndice)).getCantidad()));
				datos[i][3]=df.format(new Double(((DetalleTransaccion)CR.meVenta.getVenta().getDetallesTransaccion().elementAt(nuevoIndice)).getProducto().getPrecioRegular()));
				datos[i][4]=df.format(new Double(((DetalleTransaccion)CR.meVenta.getVenta().getDetallesTransaccion().elementAt(nuevoIndice)).getPrecioFinal()));
				datos[i][5]=df.format(new Double(((DetalleTransaccion) CR.meVenta.getVenta().getDetallesTransaccion().elementAt(nuevoIndice)).getCantidad() * ((DetalleTransaccion) CR.meVenta.getVenta().getDetallesTransaccion().elementAt(nuevoIndice)).getPrecioFinal()));
				datos[i][6]=((DetalleTransaccion)CR.meVenta.getVenta().getDetallesTransaccion().elementAt(nuevoIndice)).getCondicionVenta();
				datos[i][7]=((DetalleTransaccion)CR.meVenta.getVenta().getDetallesTransaccion().elementAt(nuevoIndice)).getTipoEntrega();
				if(Sesion.isUtilizarVendedor()){
					datos[i][8]=((DetalleTransaccion)CR.meVenta.getVenta().getDetallesTransaccion().elementAt(nuevoIndice)).getCodVendedor();
				}
			}
			fireTableDataChanged();
		} catch (NullPointerException e){
			logger.error("llenarTabla(String)", e);

			this.iniciarDatosTabla(titulos.length);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("llenarTabla(String) - end");
		}
	}*/

	/*public void llenarTablaDevolucion()
	{
		if (logger.isDebugEnabled()) {
			logger.debug("llenarTablaDevolucion() - start");
		}

		try{
			datos = new Object[CR.meVenta.getDevolucion().getDetallesTransaccion().size()][titulos.length];
			for(int i=0; i<CR.meVenta.getDevolucion().getDetallesTransaccion().size(); i++)
			{
				int j=0;
				String codProd = ((DetalleTransaccion)CR.meVenta.getDevolucion().getDetallesTransaccion().elementAt(i)).getProducto().getCodProducto().toString();
				int longitud = codProd.length();
				if ((Sesion.getLongitudCodigoInterno()>-1)&&(longitud>Sesion.getLongitudCodigoInterno())) {
					codProd = codProd.substring(codProd.length()-Sesion.getLongitudCodigoInterno()); 
					for (int k=0;k<longitud-Sesion.getLongitudCodigoInterno();k++) {
						codProd = " " + codProd; 
					}
				}
				datos[i][j]=codProd;j++;
				datos[i][j]=((DetalleTransaccion)CR.meVenta.getDevolucion().getDetallesTransaccion().elementAt(i)).getProducto().getDescripcionCorta();j++;
				datos[i][j]=df.format(new Float(((DetalleTransaccion)CR.meVenta.getDevolucion().getDetallesTransaccion().elementAt(i)).getCantidad()));j++;
				datos[i][j]=df.format(new Double(((DetalleTransaccion)CR.meVenta.getDevolucion().getDetallesTransaccion().elementAt(i)).getProducto().getPrecioRegular()));j++;
				datos[i][j]=df.format(new Double(((DetalleTransaccion)CR.meVenta.getDevolucion().getDetallesTransaccion().elementAt(i)).getPrecioFinal()));j++;
				datos[i][j]=df.format(new Double(((DetalleTransaccion) CR.meVenta.getDevolucion().getDetallesTransaccion().elementAt(i)).getCantidad() * ((DetalleTransaccion) CR.meVenta.getDevolucion().getDetallesTransaccion().elementAt(i)).getPrecioFinal()));j++;
				datos[i][j]=((DetalleTransaccion)CR.meVenta.getDevolucion().getDetallesTransaccion().elementAt(i)).getCondicionVenta();j++;
				if (this.mostrarTipoEntrega) {
					datos[i][j]=((DetalleTransaccion)CR.meVenta.getVenta().getDetallesTransaccion().elementAt(i)).getTipoEntrega();
					j++;
				}
				if(Sesion.isUtilizarVendedor()){
					datos[i][j]=((DetalleTransaccion)CR.meVenta.getDevolucion().getDetallesTransaccion().elementAt(i)).getCodVendedor();
					j++;
				}
			}
			fireTableDataChanged();
		} catch (NullPointerException e){
			logger.error("llenarTablaDevolucion()", e);

			this.iniciarDatosTabla(titulos.length);
		} catch (Exception e){
			logger.error("llenarTablaDevolucion()", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("llenarTablaDevolucion() - end");
		}
	}

	public void llenarTablaAnulacion()
	{
		if (logger.isDebugEnabled()) {
			logger.debug("llenarTablaAnulacion() - start");
		}

		try{
			datos = new Object[CR.meVenta.getAnulacion().getDetallesTransaccion().size()][titulos.length];
			for(int i=0; i<CR.meVenta.getAnulacion().getDetallesTransaccion().size(); i++)
			{
				int j=0;
				String codProd = ((DetalleTransaccion)CR.meVenta.getAnulacion().getDetallesTransaccion().elementAt(i)).getProducto().getCodProducto().toString();
				int longitud = codProd.length();
				if ((Sesion.getLongitudCodigoInterno()>-1)&&(longitud>Sesion.getLongitudCodigoInterno())) {
					codProd = codProd.substring(codProd.length()-Sesion.getLongitudCodigoInterno()); 
					for (int k=0;k<longitud-Sesion.getLongitudCodigoInterno();k++) {
						codProd = " " + codProd; 
					}
				}
				datos[i][j]=codProd;j++;
				datos[i][j]=((DetalleTransaccion)CR.meVenta.getAnulacion().getDetallesTransaccion().elementAt(i)).getProducto().getDescripcionCorta();j++;
				datos[i][j]=df.format(new Float(((DetalleTransaccion)CR.meVenta.getAnulacion().getDetallesTransaccion().elementAt(i)).getCantidad()));j++;
				datos[i][j]=df.format(new Double(((DetalleTransaccion)CR.meVenta.getAnulacion().getDetallesTransaccion().elementAt(i)).getProducto().getPrecioRegular()));j++;
				datos[i][j]=df.format(new Double(((DetalleTransaccion)CR.meVenta.getAnulacion().getDetallesTransaccion().elementAt(i)).getPrecioFinal()));j++;
				datos[i][j]=df.format(new Double(((DetalleTransaccion) CR.meVenta.getAnulacion().getDetallesTransaccion().elementAt(i)).getCantidad() * ((DetalleTransaccion) CR.meVenta.getAnulacion().getDetallesTransaccion().elementAt(i)).getPrecioFinal()));j++;
				datos[i][j]=((DetalleTransaccion)CR.meVenta.getAnulacion().getDetallesTransaccion().elementAt(i)).getCondicionVenta();j++;
				if (this.mostrarTipoEntrega) {
					datos[i][j]=((DetalleTransaccion)CR.meVenta.getVenta().getDetallesTransaccion().elementAt(i)).getTipoEntrega();j++;
				}
				if(Sesion.isUtilizarVendedor()){
					datos[i][j]=((DetalleTransaccion)CR.meVenta.getAnulacion().getDetallesTransaccion().elementAt(i)).getCodVendedor();j++;
				}
			}
			fireTableDataChanged();
		} catch (NullPointerException e){
			logger.error("llenarTablaAnulacion()", e);

			this.iniciarDatosTabla(titulos.length);
		} catch (Exception e){
			logger.error("llenarTablaAnulacion()", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("llenarTablaAnulacion() - end");
		}
	}

	public void llenarTablaVentaOriginal()
	{
		if (logger.isDebugEnabled()) {
			logger.debug("llenarTablaVentaOriginal() - start");
		}

		try{
			datos = new Object[CR.meVenta.getDevolucion().getVentaOriginal().getDetallesTransaccion().size()][titulos.length];
			for(int i=0; i<CR.meVenta.getDevolucion().getVentaOriginal().getDetallesTransaccion().size(); i++)
			{
				int j=0;
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
				datos[i][j]=df.format(new Double(((DetalleTransaccion)CR.meVenta.getDevolucion().getVentaOriginal().getDetallesTransaccion().elementAt(i)).getProducto().getPrecioRegular()));j++;
				datos[i][j]=df.format(new Double(((DetalleTransaccion)CR.meVenta.getDevolucion().getVentaOriginal().getDetallesTransaccion().elementAt(i)).getPrecioFinal()));j++;
				datos[i][j]=df.format(new Double(((DetalleTransaccion) CR.meVenta.getDevolucion().getVentaOriginal().getDetallesTransaccion().elementAt(i)).getCantidad() * ((DetalleTransaccion) CR.meVenta.getDevolucion().getVentaOriginal().getDetallesTransaccion().elementAt(i)).getPrecioFinal()));j++;
				datos[i][j]=((DetalleTransaccion)CR.meVenta.getDevolucion().getVentaOriginal().getDetallesTransaccion().elementAt(i)).getCondicionVenta();j++;
				if (this.mostrarTipoEntrega) {
					datos[i][j]=((DetalleTransaccion)CR.meVenta.getVenta().getDetallesTransaccion().elementAt(i)).getTipoEntrega();j++;
				}
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
	}*/
}