/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblohm.cr.gui
 * Programa   : ModeloTablaApartado.java
 * Creado por : irojas
 * Creado en  : 12/04/2004 11:03:50 AM
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
package com.becoblohm.cr.gui.apartado;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import org.apache.log4j.Logger;

import com.becoblohm.cr.CR;
import com.becoblohm.cr.excepciones.BaseDeDatosExcepcion;
import com.becoblohm.cr.excepciones.ConexionExcepcion;
import com.becoblohm.cr.excepciones.FuncionExcepcion;
import com.becoblohm.cr.excepciones.MaquinaDeEstadoExcepcion;
import com.becoblohm.cr.excepciones.ProductoExcepcion;
import com.becoblohm.cr.excepciones.XmlExcepcion;
import com.becoblohm.cr.manejadorsesion.Sesion;
import com.becoblohm.cr.extensiones.impl.manejarpago.Pago;
import com.becoblohm.cr.manejarservicio.Abono;
import com.becoblohm.cr.manejarservicio.Apartado;
import com.becoblohm.cr.manejarservicio.DetalleServicio;

public class ModeloTablaApartado extends AbstractTableModel implements Serializable
{
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(ModeloTablaApartado.class);

	DecimalFormat df = new DecimalFormat("#,##0.00");
	DecimalFormat dfEntero = new DecimalFormat("00");
	SimpleDateFormat fechaServ = new SimpleDateFormat("dd/MM/yyyy");
	String[] titulos;
	Object[][] datos;
	Class<?>[] types;

	/**
	 * Constructor para ModeloTabla.
	 * @param tipoTabla Entero que indica el tipo de tabla a dibujar:
						1->Tabla de Detalles de Apartados
						2->Tabla de Detalles de Abonos
						3->Tabla de Pagos del Abono seleccionado en la tabla de Abono
	 */
	public ModeloTablaApartado(int tipoTabla)
	{
		switch (tipoTabla) {
			case 1:
				titulos = new String[]{"Cod. Articulo","Descripcion","Cantidad","P.Regular","P.Final","Monto","Cvta"};
				Object[][] datos1 = {{"","",new Float(0.00),new Double(0.00),new Double(0.00),new Double(0.00),""}};
				datos = datos1;
				types = new Class[]{String.class, String.class, Number.class, Number.class,Number.class,Number.class, String.class};
				break;
			case 2:
				titulos = new String[]{"Nro.Abono","Monto","Fecha","Tipo","Estado"};
				Object[][] datos2 = {{new Integer(0),new Double(0.00),/*new Date()*/"","","",}};
				datos = datos2;
				types = new Class[]{Number.class, Number.class, String.class/*Date.class*/, String.class, String.class};
				break;
					
			case 3:
				titulos = new String[]{"Forma de Pago","Monto"};
				Object[][] datos3 = {{"", new Double(0.00)}};
				datos = datos3;
				types = new Class[]{String.class, Number.class};
				break;
		}
	}
	
	/**
	 * Constructor para ModeloTablaApartado.
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector' 
	* Se eliminaron variables sin uso
	* Fecha: agosto 2011
	*/
	public ModeloTablaApartado(Vector<Apartado> apartados)
	{
		titulos = new String[]{"Fecha","Número","Cedula Cte","Nombre Cliente","Monto Apartado","Vence","Estado"};
		types = new Class[]{String.class,Number.class, String.class,String.class, Number.class, String.class, String.class};
		
		this.llenarApartados(apartados);
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

		if (logger.isDebugEnabled()) {
			logger.debug("isCellEditable(int, int) - end");
		}
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
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Class' 
	* Fecha: agosto 2011
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
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Class' 
	* Fecha: agosto 2011
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
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Class' 
	* Fecha: agosto 2011
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
			else datos[0][i] = "";
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
	public void llenarTablaDetalleApartado()
	{
		if (logger.isDebugEnabled()) {
			logger.debug("llenarTablaDetalleApartado() - start");
		}

		try{
			if (CR.meServ.getApartado().getDetallesServicio().size() > 0) {
				datos = new Object[CR.meServ.getApartado().getDetallesServicio().size()][titulos.length];
				for(int i=0; i<CR.meServ.getApartado().getDetallesServicio().size(); i++)
				{
					String codProd = ((DetalleServicio)CR.meServ.getApartado().getDetallesServicio().elementAt(i)).getProducto().getCodProducto().toString();
					int longitud = codProd.length();
					if ((Sesion.getLongitudCodigoInterno()>-1)&&(longitud>Sesion.getLongitudCodigoInterno())) {
						codProd = codProd.substring(codProd.length()-Sesion.getLongitudCodigoInterno()); 
						for (int j=0;j<longitud-Sesion.getLongitudCodigoInterno();j++) {
							codProd = " " + codProd; 
						}
					}
					datos[i][0]=codProd;
					datos[i][1]=((DetalleServicio)CR.meServ.getApartado().getDetallesServicio().elementAt(i)).getProducto().getDescripcionCorta().toString();
					datos[i][2]= df.format(new Float(((DetalleServicio)CR.meServ.getApartado().getDetallesServicio().elementAt(i)).getCantidad()));
					datos[i][3]= df.format(new Double(((DetalleServicio)CR.meServ.getApartado().getDetallesServicio().elementAt(i)).getProducto().getPrecioRegular()));
					datos[i][4]= df.format(new Double(((DetalleServicio)CR.meServ.getApartado().getDetallesServicio().elementAt(i)).getPrecioFinal()));
					datos[i][5]= df.format(new Double(((DetalleServicio) CR.meServ.getApartado().getDetallesServicio().elementAt(i)).getCantidad() * ((DetalleServicio) CR.meServ.getApartado().getDetallesServicio().elementAt(i)).getPrecioFinal()));
					datos[i][6]=((DetalleServicio)CR.meServ.getApartado().getDetallesServicio().elementAt(i)).getCondicionVenta();
				}
				fireTableDataChanged();
			} else {
				datos = new Object[CR.meServ.getApartado().getDetallesServicio().size()][titulos.length];
				fireTableDataChanged();
			}
		} catch (NullPointerException e){
			logger.error("llenarTablaDetalleApartado()", e);

			this.iniciarDatosTabla(titulos.length);
		} catch (Exception e){
			logger.error("llenarTablaDetalleApartado()", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("llenarTablaDetalleApartado() - end");
		}
	}

	/**
	 * Método llenarTablaDetalleApartado.
	 * 	Llena la tabla correspondiente al detalle del apartado, asociada
	 * al modelo de tabla. 
	 * 
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector' 
	* Fecha: agosto 2011
	*/
	public void llenarTablaDetalleApartado(String codProducto, boolean isCodigoExterno) throws ProductoExcepcion, MaquinaDeEstadoExcepcion, XmlExcepcion, FuncionExcepcion, BaseDeDatosExcepcion, ConexionExcepcion
	{
		if (logger.isDebugEnabled()) {
			logger.debug("llenarTablaDetalleApartado(String) - start");
		}

		int nuevoIndice;
	
		try{
			Vector<Integer> renglones = CR.meServ.obtenerRenglones(codProducto, isCodigoExterno);
			datos = new Object[renglones.size()][titulos.length];
			for(int i=0; i<renglones.size(); i++)
			{				
				nuevoIndice = new Integer(renglones.elementAt(i).toString()).intValue();
				String codProd = ((DetalleServicio)CR.meServ.getApartado().getDetallesServicio().elementAt(nuevoIndice)).getProducto().getCodProducto().toString();
				int longitud = codProd.length();
				if ((Sesion.getLongitudCodigoInterno()>-1)&&(longitud>Sesion.getLongitudCodigoInterno())) {
					codProd = codProd.substring(codProd.length()-Sesion.getLongitudCodigoInterno()); 
					for (int j=0;j<longitud-Sesion.getLongitudCodigoInterno();j++) {
						codProd = " " + codProd; 
					}
				}
				datos[i][0]=codProd;
				datos[i][1]=((DetalleServicio)CR.meServ.getApartado().getDetallesServicio().elementAt(nuevoIndice)).getProducto().getDescripcionCorta();
				datos[i][2]=df.format(new Float(((DetalleServicio)CR.meServ.getApartado().getDetallesServicio().elementAt(nuevoIndice)).getCantidad()));
				datos[i][3]= df.format(new Double(((DetalleServicio)CR.meServ.getApartado().getDetallesServicio().elementAt(nuevoIndice)).getProducto().getPrecioRegular()));
				datos[i][4]=df.format(new Double(((DetalleServicio)CR.meServ.getApartado().getDetallesServicio().elementAt(nuevoIndice)).getPrecioFinal()));
				datos[i][5]=df.format(new Double(((DetalleServicio) CR.meServ.getApartado().getDetallesServicio().elementAt(nuevoIndice)).getCantidad() * ((DetalleServicio)CR.meServ.getApartado().getDetallesServicio().elementAt(nuevoIndice)).getPrecioFinal()));
				datos[i][6]=((DetalleServicio)CR.meServ.getApartado().getDetallesServicio().elementAt(nuevoIndice)).getCondicionVenta();
			}
			fireTableDataChanged();
		} catch (NullPointerException e){
			logger.error("llenarTablaDetalleApartado(String)", e);

			this.iniciarDatosTabla(titulos.length);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("llenarTablaDetalleApartado(String) - end");
		}
	}

	public void llenarTablaAbonos()
	{
		if (logger.isDebugEnabled()) {
			logger.debug("llenarTablaAbonos() - start");
		}

		try{
			if (CR.meServ.getApartado().getAbonos().size() > 0) {
				datos = new Object[CR.meServ.getApartado().getAbonos().size()][titulos.length];
				for(int i=0; i<CR.meServ.getApartado().getAbonos().size(); i++)
				{
					datos[i][0]= new Integer(((Abono)CR.meServ.getApartado().getAbonos().elementAt(i)).getNumAbono());
					datos[i][1]= df.format(new Double(((Abono)CR.meServ.getApartado().getAbonos().elementAt(i)).getMontoBase()));
					datos[i][2]= fechaServ.format(((Abono)CR.meServ.getApartado().getAbonos().elementAt(i)).getFechaAbono()).toString();
					datos[i][3]= String.valueOf(((Abono)CR.meServ.getApartado().getAbonos().elementAt(i)).getTipoTransAbono());
					
					if(((Abono)CR.meServ.getApartado().getAbonos().elementAt(i)).getEstadoAbono() == Sesion.ABONO_ANULADO) {
						datos[i][4]= "ANULADO";
					} else if (((Abono)CR.meServ.getApartado().getAbonos().elementAt(i)).getEstadoAbono() == Sesion.ABONO_ACTIVO){
						datos[i][4]= "ACTIVO";
					} else {
						datos[i][4]= String.valueOf(((Abono)CR.meServ.getApartado().getAbonos().elementAt(i)).getEstadoAbono());
					}
					
				}
				fireTableDataChanged();
			}
		} catch (NullPointerException e){
			logger.error("llenarTablaAbonos()", e);

			this.iniciarDatosTabla(titulos.length);
		} catch (Exception e){
			logger.error("llenarTablaAbonos()", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("llenarTablaAbonos() - end");
		}
	}

	public void llenarTablaPagosAbono(int renglonAbono)
	{
		if (logger.isDebugEnabled()) {
			logger.debug("llenarTablaPagosAbono(int) - start");
		}

		try{
			if ((CR.meServ.getApartado().getAbonos().size() > 0) && (((Abono)CR.meServ.getApartado().getAbonos().elementAt(renglonAbono)).getPagos().size() > 0)) {
				Abono abono = (Abono)CR.meServ.getApartado().getAbonos().elementAt(renglonAbono);
				datos = new Object[abono.getPagos().size()][titulos.length];
				for(int i=0; i< abono.getPagos().size(); i++) {
					datos[i][0]=((Pago)abono.getPagos().elementAt(i)).getFormaPago().getNombre();
					datos[i][1]= df.format(new Double(((Pago)abono.getPagos().elementAt(i)).getMonto()));
				}
				fireTableDataChanged();
			}
		} catch (NullPointerException e){
			logger.error("llenarTablaPagosAbono(int)", e);

			this.iniciarDatosTabla(titulos.length);
		} catch (Exception e){
			logger.error("llenarTablaPagosAbono(int)", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("llenarTablaPagosAbono(int) - end");
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
	public void llenarApartados(Vector<Apartado> apartados)
	{
		if (logger.isDebugEnabled()) {
			logger.debug("llenarApartados(Vector) - start");
		}

		try{
			datos = new Object[apartados.size()][titulos.length];
			for (int i=0; i<apartados.size(); i++) {
				Apartado apartadoActual = (Apartado)apartados.elementAt(i);
				datos[i][0] = fechaServ.format(apartadoActual.getFechaServicio());
				datos[i][1] = new Integer(apartadoActual.getNumServicio());
				datos[i][2] = (apartadoActual.getCliente().getCodCliente()!=null) ? apartadoActual.getCliente().getCodCliente() : "N/A" ;
				datos[i][3] = (apartadoActual.getCliente().getCodCliente()!=null) ? apartadoActual.getCliente().getNombreCompleto() : "N/A" ;
				datos[i][4] = df.format(apartadoActual.consultarMontoServ());
				
				//Calculamos la fecha de vencimiento del apartado para mostrarlo en la tabla
				Date fechaVencimiento = apartadoActual.verificarEstadoVencimiento();
				
				datos[i][5] = fechaServ.format(fechaVencimiento);
				datos[i][6] = String.valueOf(apartadoActual.getEstadoServicio());
			}
			fireTableDataChanged();
		} catch (NullPointerException e){
			logger.error("llenarApartados(Vector)", e);

			this.iniciarDatosTabla(titulos.length);
		} catch (Exception e){
			logger.error("llenarApartados(Vector)", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("llenarApartados(Vector) - end");
		}
	}
	
}