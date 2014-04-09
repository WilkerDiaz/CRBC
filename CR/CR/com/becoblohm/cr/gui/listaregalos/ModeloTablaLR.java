/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblohm.cr.gui.listaregalos
 * Programa   : ModeloTablaLR.java
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
import java.util.Vector;

import javax.swing.event.ChangeEvent;
import javax.swing.table.AbstractTableModel;

import com.becoblohm.cr.CR;
import com.becoblohm.cr.excepciones.BaseDeDatosExcepcion;
import com.becoblohm.cr.excepciones.ConexionExcepcion;
import com.becoblohm.cr.excepciones.FuncionExcepcion;
import com.becoblohm.cr.excepciones.MaquinaDeEstadoExcepcion;
import com.becoblohm.cr.excepciones.ProductoExcepcion;
import com.becoblohm.cr.excepciones.XmlExcepcion;
import com.becoblohm.cr.manejadorsesion.Sesion;
import com.becoblohm.cr.manejarservicio.Abono;
import com.becoblohm.cr.manejarservicio.DetalleServicio;
import com.becoblohm.cr.manejarventa.DetalleTransaccion;


/*
* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
* Sólo se parametrizó el tipo de dato contenido en los 'Class'
* Fecha: agosto 2011
*/
public class ModeloTablaLR extends AbstractTableModel implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected ChangeEvent changeEvent = new ChangeEvent(this);
	public static int REGISTRO = 1;
	public static int BUSQUEDA = 2;
	public static int CONSULTAINVITADO = 3;
	public static int CONSULTATITULAR = 4;
	public static int OPERACIONESINVITADO = 5;

	DecimalFormat df = new DecimalFormat("#,##0.00");
	DecimalFormat dfEntero = new DecimalFormat("00");
	String[] titulos;
	Object[][] datos;
	Class<?>[] types;

	/**
	 * Constructor para ModeloTabla.
	 * tabla 1: Registro Lista
	 * tabla 2: Buscar Lista
	 * tabla 3: Invitado consulta lista (Tabla Superior)
	 * tabla 4: Titular consulta lista (Tabla Superior Version 2)
	 * tabla 5: Abonos a regalos en consultar lista (Tabla inferior)
	 */
	public ModeloTablaLR(int tabla) {
		super();
		switch(tabla){
		
		case 1:
		/** Registro Lista **/ 
			titulos = new String[]{"Cod. Articulo","Descripcion","Cantidad","P.Regular","P.Final","Monto"};
			Object[][] datos1 = {{"","",new Integer(0),new Double(0),new Double(0),new Double(0)}};
			datos = datos1;
			types = new Class[]{String.class, String.class, Number.class, Number.class, Number.class, Number.class};
			break;
		case 2:
		/** Buscar Lista **/
			titulos = new String[]{"Cod. Lista", "Titular", "Titular Sec.", "Evento", "Fecha Evento", "Tienda"};
			Object[][] datos2 = {{"","","","","",""}};
			datos = datos2;
			types = new Class[]{String.class, String.class, String.class, String.class, String.class, String.class};
			break;
		case 3:
		/** Invitado consulta lista **/
			titulos = new String[]{"Cod. Articulo","Descripcion","Pedidos","Restan","Precio","Precio + IVA","Abonos"};
			Object[][] datos3 = {{"","",new Integer(0),new Integer(0),new Double(0),new Double(0),new Double(0)}};
			datos = datos3;
			types = new Class[]{String.class, String.class, Number.class, Number.class, Number.class, Number.class, Number.class};
			break;
		case 4:
		/** Titular consulta lista **/
			titulos = new String[]{"Cod. Articulo","Descripcion","Precio","Pedidos","Abonados","Vendidos","Abonos"};
			Object[][] datos4 = {{"","",new Double(0),new Double(0),new Integer(0),new Integer(0),new Integer(0),new Double(0)}};
			datos = datos4;
			types = new Class[]{String.class, String.class, Number.class, Number.class, Number.class, Number.class, Number.class, Number.class};
			break;
		case 5:
		/** Invitado abona a regalos/lista **/
			titulos = new String[]{"Cod. Articulo","Descripcion","Cantidad","P.Regular","P.Final","P.Final+IVA","Monto Abono","Tipo"};
			Object[][] datos5 = {{"","",new Integer(0),new Double(0),new Double(0),new Double(0),new Double(0),""}};
			datos = datos5;
			types = new Class[]{String.class, String.class, Number.class, Number.class, Number.class, Number.class, Number.class, String.class};
			break;
			
		case 6:
		/** Cierre lista 1 **/
			titulos = new String[]{"Cod. Articulo","Descripcion","Precio","Pedidos","Abonados","Vendidos"};
			Object[][] datos6 = {{"","",new Double(0),new Double(0),new Integer(0),new Integer(0),new Integer(0)}};
			datos = datos6;
			types = new Class[]{String.class, String.class, Number.class, Number.class, Number.class, Number.class, Number.class};
			break;
		case 7:
		/** Cierre lista 2 **/
			titulos = new String[]{"Cod. Articulo","Descripcion","Precio","Pedidos","Vendidos","Abonos"};
			Object[][] datos7 = {{"","",new Double(0),new Double(0),new Integer(0),new Integer(0),new Double(0)}};
			datos = datos7;
			types = new Class[]{String.class, String.class, Number.class, Number.class, Number.class, Number.class, Number.class};
			break;
		case 8:
		/** Cierre lista 3 **/
			titulos = new String[]{"Cod. Articulo","Descripcion","Cantidad","P.Regular","P.Final", "Monto", "Cvta","Entrega"};
			Object[][] datos8 = {{"","",new Integer(0),new Double(0),new Double(0),new Double(0),"",""}};
			datos = datos8;
			types = new Class[]{String.class, String.class, Number.class, Number.class, Number.class, Number.class, String.class,String.class};
			break;
		}	
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
	 * Método llenarTablaDetalleApartado.
	 * 	Llena la tabla correspondiente al detalle del apartado, asociada
	 * al modelo de tabla. 
	 * 
	 */
	public void llenarTablaRegistroLR() {
		try {
			if (CR.meServ.getListaRegalos().getDetallesServicio().size() > 0) {
				datos = new Object[CR.meServ.getListaRegalos().getDetallesServicio().size()][titulos.length];
				for(int i=0; i<CR.meServ.getListaRegalos().getDetallesServicio().size(); i++) {
					DetalleServicio detalle = (DetalleServicio)CR.meServ.getListaRegalos().getDetallesServicio().elementAt(i);
					String codProd = detalle.getProducto().getCodProducto().toString();
					int longitud = codProd.length();
					if ((Sesion.getLongitudCodigoInterno()>-1)&&(longitud>Sesion.getLongitudCodigoInterno())) {
						codProd = codProd.substring(codProd.length()-Sesion.getLongitudCodigoInterno()); 
						for (int j=0;j<longitud-Sesion.getLongitudCodigoInterno();j++)
							codProd = " " + codProd;
					}
					datos[i][0] = codProd;
					datos[i][1] = detalle.getProducto().getDescripcionCorta().toString();
					datos[i][2] = new Integer((int)detalle.getCantidad());
					datos[i][3] = df.format(new Double(detalle.getProducto().getPrecioRegular()));
					datos[i][4] = df.format(new Double(detalle.getPrecioFinal()));
					datos[i][5] = df.format(new Double(detalle.getCantidad() * detalle.getPrecioFinal()));
				}
				fireTableDataChanged();
			} else {
				datos = new Object[CR.meServ.getListaRegalos().getDetallesServicio().size()][titulos.length];
				fireTableDataChanged();
			}
		} catch (NullPointerException e){
			this.iniciarDatosTabla(titulos.length);
		} catch (Exception e){
		}
	}

	/**
	 * Método llenarTablaDetalleApartado.
	 * 	Llena la tabla correspondiente al detalle del apartado, asociada
	 * al modelo de tabla. 
	 * 
	 */
	public void llenarTablaConsultaInvitado() {
		try {
			if (CR.meServ.getListaRegalos().getDetallesServicio().size() > 0) {
				datos = new Object[CR.meServ.getListaRegalos().getDetallesServicio().size()][titulos.length];
				for(int i=0; i<CR.meServ.getListaRegalos().getDetallesServicio().size(); i++) {
					DetalleServicio detalle = (DetalleServicio)CR.meServ.getListaRegalos().getDetallesServicio().elementAt(i);
					String codProd = detalle.getProducto().getCodProducto().toString();
					int longitud = codProd.length();
					if ((Sesion.getLongitudCodigoInterno()>-1)&&(longitud>Sesion.getLongitudCodigoInterno())) {
						codProd = codProd.substring(codProd.length()-Sesion.getLongitudCodigoInterno()); 
						for (int j=0;j<longitud-Sesion.getLongitudCodigoInterno();j++)
							codProd = " " + codProd;
					}
					float cantRestantes = (detalle.getCantRestantes()>0)
										? detalle.getCantRestantes()
										: 0;

					if(cantRestantes < 1)
					cantRestantes = 0;
					datos[i][0] = detalle.getProducto().getCodProducto();
					datos[i][1] = detalle.getProducto().getDescripcionCorta();
					datos[i][2] = new Integer((int)detalle.getCantidad());
					datos[i][3] = new Integer((int)cantRestantes);
					datos[i][4] = df.format(new Double(detalle.getPrecioFinal()));
					datos[i][5] = df.format(new Double(detalle.getPrecioFinal()*((detalle.getProducto().getImpuesto().getPorcentaje()/100)+1)));
					datos[i][6] = df.format(new Double(detalle.getAbonos()));
				}
				fireTableDataChanged();
			} else {
				datos = new Object[CR.meServ.getListaRegalos().getDetallesServicio().size()][titulos.length];
				fireTableDataChanged();
			}
		} catch (NullPointerException e){
			this.iniciarDatosTabla(titulos.length);
		} catch (Exception e){
		}
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
	public void llenarTablaAnularProd(String codProducto, int caso, boolean isCodigoExterno) throws ProductoExcepcion, MaquinaDeEstadoExcepcion, XmlExcepcion, FuncionExcepcion, BaseDeDatosExcepcion, ConexionExcepcion {
		int nuevoIndice;
	
		try{
			Vector<Integer> renglones = CR.meServ.obtenerRenglones(codProducto, isCodigoExterno);
			datos = new Object[renglones.size()][titulos.length];
			for(int i=0; i<renglones.size(); i++) {				
				nuevoIndice = new Integer(renglones.elementAt(i).toString()).intValue();
				DetalleServicio detalle = (DetalleServicio)CR.meServ.getListaRegalos().getDetallesServicio().elementAt(nuevoIndice);
				String codProd = detalle.getProducto().getCodProducto().toString();
				int longitud = codProd.length();
				if ((Sesion.getLongitudCodigoInterno()>-1)&&(longitud>Sesion.getLongitudCodigoInterno())) {
					codProd = codProd.substring(codProd.length()-Sesion.getLongitudCodigoInterno()); 
					for (int j=0;j<longitud-Sesion.getLongitudCodigoInterno();j++)
						codProd = " " + codProd;
				}
				switch (caso) {
					case 1:
						datos[i][0]=codProd;
						datos[i][1]=detalle.getProducto().getDescripcionCorta();
						datos[i][2]=new Integer((int)detalle.getCantidad());
						datos[i][3]=df.format(new Double(detalle.getProducto().getPrecioRegular()));
						datos[i][4]=df.format(new Double(detalle.getPrecioFinal()));
						datos[i][5]=df.format(new Double(detalle.getCantidad() * detalle.getPrecioFinal()));
						break;
				}
			}
			fireTableDataChanged();
		} catch (NullPointerException e){
			this.iniciarDatosTabla(titulos.length);
		}
	}

	/**
	 * @param vector
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
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
	 * 
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public void llenarTablaAbonos() {
		Vector<Abono> detalleAbonos = CR.meServ.getListaRegalos().getDetalleAbonos();
		datos = new Object[detalleAbonos.size()][8];
		for(int i=0;i<detalleAbonos.size();i++){
			Abono abono = (Abono)detalleAbonos.get(i);
			String codProd = null;
			if(abono.getProducto()==null)
				codProd = "000000000000";
			else codProd = abono.getProducto().getCodProducto();			
			DetalleServicio detalle = CR.meServ.getListaRegalos().getDetalle(codProd);
			
			if(abono.getTipoTransAbono()=='L'){
				datos[i][0] = codProd; 
				datos[i][1] = "ABONO A LISTA";
				datos[i][2] = new Integer(1);
				datos[i][3] = df.format(new Double(0));
				datos[i][4] = df.format(new Double(0));
				datos[i][5] = df.format(new Double(0));
				datos[i][6] = df.format(new Double(abono.getMontoBase()));
				datos[i][7] = new Character(abono.getTipoTransAbono());
			} else {
				datos[i][0] = codProd;
				datos[i][1] = abono.getProducto().getDescripcionCorta();
				datos[i][2] = new Integer((int)abono.getCantidad());
				datos[i][3] = df.format(new Double(detalle.getProducto().getPrecioRegular()));
				datos[i][4] = df.format(new Double(detalle.getPrecioFinal()));
				datos[i][5] = df.format(new Double(detalle.getPrecioFinal()*((abono.getProducto().getImpuesto().getPorcentaje()/100)+1)));
				datos[i][6] = df.format(new Double((abono.getMontoBase()+abono.getImpuestoProducto())*abono.getCantidad()));
				datos[i][7] = new Character(abono.getTipoTransAbono());
			}
		}
		fireTableDataChanged();
	}
	
	public void llenarTablaDetallesTitular(){
		int j;
		
		if(CR.meServ.getListaRegalos().getMontoAbonosLista()>0) {
			datos = new Object[CR.meServ.getListaRegalos().getDetallesServicio().size()+1][7];
			// Si la lista tiene abonos
			// Detalle de los abonos
			j=0;
			datos[0][j++] = new String("000000000000");
			datos[0][j++] = new String("ABONO A LISTA");
			datos[0][j++] = null;
			datos[0][j++] = null;
			datos[0][j++] = null;
			datos[0][j++] = null;
			datos[0][j++] = df.format(CR.meServ.getListaRegalos().getMontoAbonosLista());
			
			//Detalles de la lista
			for(int i=0;i<CR.meServ.getListaRegalos().getDetallesServicio().size();i++) {
				j=0;
				DetalleServicio detalle = (DetalleServicio)CR.meServ.getListaRegalos().getDetallesServicio().get(i);
						
				datos[i+1][j++] = detalle.getProducto().getCodProducto(); //codproducto
				datos[i+1][j++] = detalle.getProducto().getDescripcionCorta(); //descproducto
				datos[i+1][j++] = df.format(detalle.getProducto().getPrecioRegular() + detalle.getMontoImpuesto()); //precio
				datos[i+1][j++] = new Integer((int)detalle.getCantidad()); //cantidad pedidos
				datos[i+1][j++] = new Integer((int)detalle.getCantAbonadosT());
				datos[i+1][j++] = new Integer((int)detalle.getCantVendidos());
				datos[i+1][j++] = df.format(detalle.getAbonos());
			}
		}else{
			datos = new Object[CR.meServ.getListaRegalos().getDetallesServicio().size()][7];
			for(int i=0;i<CR.meServ.getListaRegalos().getDetallesServicio().size();i++) {
				j=0;
				DetalleServicio detalle = (DetalleServicio)CR.meServ.getListaRegalos().getDetallesServicio().get(i);
						
				datos[i][j++] = detalle.getProducto().getCodProducto(); //codproducto
				datos[i][j++] = detalle.getProducto().getDescripcionCorta(); //descproducto
				datos[i][j++] = df.format(detalle.getProducto().getPrecioRegular() + detalle.getMontoImpuesto()); //precio
				datos[i][j++] = new Integer((int)detalle.getCantidad()); //cantidad pedidos
				datos[i][j++] = new Integer((int)detalle.getCantAbonadosT());
				datos[i][j++] = new Integer((int)detalle.getCantVendidos());
				datos[i][j++] = df.format(detalle.getAbonos());
			}
		}
		fireTableDataChanged();
	}
	
	public void llenarTablaReporteLista(Vector<DetalleServicio> detallesLista) {
		try {
			if (detallesLista.size() > 0) {
				datos = new Object[detallesLista.size()][titulos.length];
				for(int i=0; i<detallesLista.size(); i++) {
					DetalleServicio detalle = (DetalleServicio)detallesLista.elementAt(i);
					String codProd = detalle.getProducto().getCodProducto().toString();
					int longitud = codProd.length();
					if ((Sesion.getLongitudCodigoInterno()>-1)&&(longitud>Sesion.getLongitudCodigoInterno())) {
						codProd = codProd.substring(codProd.length()-Sesion.getLongitudCodigoInterno()); 
						for (int j=0;j<longitud-Sesion.getLongitudCodigoInterno();j++)
							codProd = " " + codProd;
					}
					float cantRestante = detalle.getCantidad() - detalle.getCantVendidos();
					if(cantRestante < 1)
						cantRestante = 0;
					datos[i][0] = detalle.getProducto().getCodProducto();
					datos[i][1] = detalle.getProducto().getDescripcionCorta();
					datos[i][2] = new Integer((int)detalle.getCantidad());
					datos[i][3] = new Integer((int)cantRestante);
					datos[i][4] = df.format(new Double(detalle.getProducto().getPrecioRegular()));
					datos[i][5] = df.format(new Double(detalle.getPrecioFinal()));
					datos[i][6] = df.format(new Double(detalle.getAbonos()));
				}
				fireTableDataChanged();
			} else {
				datos = new Object[detallesLista.size()][titulos.length];
				fireTableDataChanged();
			}
		} catch (NullPointerException e){
			this.iniciarDatosTabla(titulos.length);
		} catch (Exception e){
		}
	}
	
	public void llenarTablaCierre1(){
		int j;
			
		datos = new Object[CR.meServ.getListaRegalos().getDetalleAbonadosTotales().size()][6];
		for(int i=0;i<CR.meServ.getListaRegalos().getDetalleAbonadosTotales().size();i++) {
			j=0;
			DetalleServicio detalle = (DetalleServicio)CR.meServ.getListaRegalos().getDetalleAbonadosTotales().get(i);
			datos[i][j++] = detalle.getProducto().getCodProducto(); // Codigo Producto
			datos[i][j++] = detalle.getProducto().getDescripcionCorta(); // Descripcion producto
			datos[i][j++] = df.format(detalle.getProducto().getPrecioRegular()); // Precio
			datos[i][j++] = new Integer((int)detalle.getCantidad()); // Pedidos
			datos[i][j++] = new Integer((int)detalle.getCantAbonadosT()); // Abonos totales
			datos[i][j++] = new Integer((int)detalle.getCantVendidos()); // Vendidos
		}
		fireTableDataChanged();
	}
	
	public void llenarTablaCierre2(){
		int j;
			
		datos = new Object[CR.meServ.getListaRegalos().getDetalleNoVendidos().size()][6];
		for(int i=0;i<CR.meServ.getListaRegalos().getDetalleNoVendidos().size();i++) {
			j=0;
			DetalleServicio detalle = (DetalleServicio)CR.meServ.getListaRegalos().getDetalleNoVendidos().get(i);
			datos[i][j++] = detalle.getProducto().getCodProducto(); //codproducto
			datos[i][j++] = detalle.getProducto().getDescripcionCorta(); //descproducto
			datos[i][j++] = df.format(detalle.getProducto().getPrecioRegular()); //precio
			datos[i][j++] = new Integer((int)detalle.getCantidad()); //cantidad pedidos
			datos[i][j++] = new Integer((int)detalle.getCantVendidos());  
			datos[i][j++] = df.format(detalle.getAbonos());
		}
		fireTableDataChanged();
	}
	
	public void llenarTablaCierre3(){
		Vector<DetalleTransaccion> detalles = CR.meServ.getListaRegalos().getVentasParciales1y2();
		
		datos = new Object[detalles.size()][8];
		for(int i=0;i<detalles.size();i++) {
			DetalleTransaccion detalle = (DetalleTransaccion)detalles.get(i);
			datos[i][0]=detalle.getProducto().getCodProducto();
			datos[i][1]=detalle.getProducto().getDescripcionCorta();
			datos[i][2]=new Integer((int)detalle.getCantidad());
			datos[i][3]=df.format(new Double(detalle.getProducto().getPrecioRegular()));
			datos[i][4]=df.format(new Double(detalle.getPrecioFinal()));
			datos[i][5]=df.format(new Double(detalle.getCantidad() * detalle.getPrecioFinal()));
			datos[i][6]=detalle.getCondicionVenta();
			datos[i][7]=detalle.getTipoEntrega();
		}
		fireTableDataChanged();
	}
}
