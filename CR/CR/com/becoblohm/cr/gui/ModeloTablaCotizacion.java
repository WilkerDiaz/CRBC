/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblohm.cr.gui
 * Programa   : ModeloTablaCotizacion.java
 * Creado por : gmartinelli
 * Creado en  : 18/05/2004 10:41:50 AM
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
import java.text.SimpleDateFormat;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import org.apache.log4j.Logger;

import com.becoblohm.cr.CR;
import com.becoblohm.cr.manejadorsesion.Sesion;
import com.becoblohm.cr.manejarservicio.Cotizacion;
import com.becoblohm.cr.manejarservicio.DetalleServicio;

/*
* En esta clase se realizaron modificaciones referentes a la migración a java 1.6 por jperez
* Sólo se parametrizó el tipo de dato contenido en los 'Class'
* Fecha: agosto 2011
*/
public class ModeloTablaCotizacion extends AbstractTableModel implements Serializable
{
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(ModeloTablaCotizacion.class);

	DecimalFormat df = new DecimalFormat("#,##0.00");
	DecimalFormat dfEntero = new DecimalFormat("00");
	SimpleDateFormat fechaServ = new SimpleDateFormat("dd/MM/yyyy");
	String[] titulos;
	Object[][] datos;
	Class<?>[] types;
	char edoServicio;
	boolean tablaRegistro = false;

	/**
	 * Constructor para ModeloTabla.
	 */
	public ModeloTablaCotizacion(char edoServ)
	{
		edoServicio = edoServ;
		tablaRegistro = true;
		if (edoServicio==Sesion.COTIZACION_ACTIVA) {
			titulos = new String[]{"","Cod. Articulo","Descripcion","Cotizado","Vendido","P.Regular", "P.Final", "Monto"};
			Object[][] datos1 = {{new Boolean(false),"","",new Float(0),new Float(0),new Double(0),new Double(0),new Double(0)}};
			datos = datos1;
			types = new Class[]{Boolean.class,String.class, String.class, Number.class, Number.class, Number.class, Number.class, Number.class};
		} else {
			titulos = new String[]{"Cod. Articulo","Descripcion","Cantidad","P.Regular", "P.Final", "Monto", "Cvta"};
			Object[][] datos1 = {{"","",new Float(0),new Double(0),new Double(0),new Double(0),""}};
			datos = datos1;
			types = new Class[]{String.class, String.class, Number.class, Number.class, Number.class, Number.class, String.class};
		}
	}
	
	/**
	 * Constructor para ModeloTabla.
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Se parametrizó el tipo de dato contenido en los 'Vector' y
	* se comentó variable sin uso
	* Fecha: agosto 2011
	*/
	public ModeloTablaCotizacion(Vector<Cotizacion> cotizaciones)
	{
		titulos = new String[]{"Fecha","Número","Cedula Cte","Nombre Cliente","Monto Cotización","Estado"};
		types = new Class[]{String.class,Number.class, String.class,String.class, Number.class, String.class};
		//Object[][] datos1 = {{"","","","",""}};
		
		this.llenarCotizaciones(cotizaciones);
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

		if ((tablaRegistro)&&(edoServicio==Sesion.COTIZACION_ACTIVA)&&((col==0)||(col==8)))
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
			} else if (this.getTypes()[i].getName().equals("java.lang.Double")){
				datos[0][i] = new Double(0.00);
			} else if (this.getTypes()[i].getName().equals("java.lang.Float")){
				datos[0][i] = new Float(0.00);
			}
			else if (this.getTypes()[i].getName().equals("java.lang.Boolean")){
				datos[0][i] = new Boolean(false);
			}
			else
				datos[0][i] = "";
		}
		fireTableDataChanged();

		if (logger.isDebugEnabled()) {
			logger.debug("iniciarDatosTabla(int) - end");
		}
	}
	
	/**
	 * Método llenarTablaDetalleApartado.
	 * 	Llena la tabla correspondiente al detalle del apartado, asociada
	 * al modelo de tabla. 
	 * 
	 */
	public void llenarTablaDetalle()
	{
		if (logger.isDebugEnabled()) {
			logger.debug("llenarTablaDetalle() - start");
		}

		try{
			if (CR.meServ.getCotizacion().getDetallesServicio().size() > 0) {
				datos = new Object[CR.meServ.getCotizacion().getDetallesServicio().size()][titulos.length];
				for(int i=0; i<CR.meServ.getCotizacion().getDetallesServicio().size(); i++)
				{
					String codProd = ((DetalleServicio)CR.meServ.getCotizacion().getDetallesServicio().elementAt(i)).getProducto().getCodProducto().toString();
					int longitud = codProd.length();
					if ((Sesion.getLongitudCodigoInterno()>-1)&&(longitud>Sesion.getLongitudCodigoInterno())) {
						codProd = codProd.substring(codProd.length()-Sesion.getLongitudCodigoInterno()); 
						for (int j=0;j<longitud-Sesion.getLongitudCodigoInterno();j++) {
							codProd = " " + codProd; 
						}
					}
					if (edoServicio==Sesion.COTIZACION_ACTIVA) {
						datos[i][0]=(Boolean)CR.meServ.getCotizacion().getEntregar().elementAt(i);
						datos[i][1]=codProd;
						datos[i][2]=((DetalleServicio)CR.meServ.getCotizacion().getDetallesServicio().elementAt(i)).getProducto().getDescripcionCorta().toString();
						datos[i][3]= df.format(new Float(((DetalleServicio)CR.meServ.getCotizacion().getDetallesServicio().elementAt(i)).getCantidad()));
						datos[i][4]= df.format((Float)CR.meServ.getCotizacion().getDetalleVendidos().elementAt(i));
						datos[i][5]= df.format(new Double(((DetalleServicio)CR.meServ.getCotizacion().getDetallesServicio().elementAt(i)).getProducto().getPrecioRegular()));
						datos[i][6]= df.format(new Double(((DetalleServicio)CR.meServ.getCotizacion().getDetallesServicio().elementAt(i)).getPrecioFinal()));
						datos[i][7]= df.format(new Double(((DetalleServicio) CR.meServ.getCotizacion().getDetallesServicio().elementAt(i)).getCantidad() * ((DetalleServicio) CR.meServ.getCotizacion().getDetallesServicio().elementAt(i)).getPrecioFinal()));
					} else {
						datos[i][0]=codProd;
						datos[i][1]=((DetalleServicio)CR.meServ.getCotizacion().getDetallesServicio().elementAt(i)).getProducto().getDescripcionCorta().toString();
						datos[i][2]= df.format(new Float(((DetalleServicio)CR.meServ.getCotizacion().getDetallesServicio().elementAt(i)).getCantidad()));
						datos[i][3]= df.format(new Double(((DetalleServicio)CR.meServ.getCotizacion().getDetallesServicio().elementAt(i)).getProducto().getPrecioRegular()));
						datos[i][4]= df.format(new Double(((DetalleServicio)CR.meServ.getCotizacion().getDetallesServicio().elementAt(i)).getPrecioFinal()));
						datos[i][5]= df.format(new Double(((DetalleServicio) CR.meServ.getCotizacion().getDetallesServicio().elementAt(i)).getCantidad() * ((DetalleServicio) CR.meServ.getCotizacion().getDetallesServicio().elementAt(i)).getPrecioFinal()));
						datos[i][6]=((DetalleServicio)CR.meServ.getCotizacion().getDetallesServicio().elementAt(i)).getCondicionVenta();
					}
				}
				fireTableDataChanged();
			}
		} catch (NullPointerException e){
			logger.error("llenarTablaDetalle()", e);

			this.iniciarDatosTabla(titulos.length);
		} catch (Exception e){
			logger.error("llenarTablaDetalle()", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("llenarTablaDetalle() - end");
		}
	}

	/**
	 * Método llenarCotizaciones.
	 * 	Llena la tabla correspondiente a las cotizaciones recuperadas. 
	 * 
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public void llenarCotizaciones(Vector<Cotizacion> cotizaciones)
	{
		if (logger.isDebugEnabled()) {
			logger.debug("llenarCotizaciones(Vector) - start");
		}

		try{
			datos = new Object[cotizaciones.size()][titulos.length];
			for (int i=0; i<cotizaciones.size(); i++) {
				Cotizacion cotActual = (Cotizacion)cotizaciones.elementAt(i);
				datos[i][0] = fechaServ.format(cotActual.getFechaServicio());
				datos[i][1] = new Integer(cotActual.getNumServicio());
				datos[i][2] = (cotActual.getCliente().getCodCliente()!=null) ? cotActual.getCliente().getCodCliente() : "N/A" ;
				datos[i][3] = (cotActual.getCliente().getCodCliente()!=null) ? cotActual.getCliente().getNombreCompleto() : "N/A" ;
				datos[i][4] = df.format(cotActual.consultarMontoServ());
				datos[i][5] = String.valueOf(cotActual.getEstadoServicio());
			}
			fireTableDataChanged();
		} catch (NullPointerException e){
			logger.error("llenarCotizaciones(Vector)", e);

			this.iniciarDatosTabla(titulos.length);
		} catch (Exception e){
			logger.error("llenarCotizaciones(Vector)", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("llenarCotizaciones(Vector) - end");
		}
	}
}