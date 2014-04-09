/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.beco.colascr.transferencias.gui
 * Programa   : ModeloTabla.java
 * Creado por : gmartinelli - Gabriel Martinelli
 * Creado en  : 19/05/2005
 *
 * (c) Beco
 * -----------------------------------------------------------------------------
 * 				Actualizaciones
 * -----------------------------------------------------------------------------
 * Versión     : 
 * Fecha       : 
 * Analista    : 
 * Descripción : 
 * =============================================================================
 */
package com.beco.colascr.transferencias.gui; 

import java.io.Serializable;
import java.text.DecimalFormat;

import javax.swing.table.AbstractTableModel;

import com.beco.colascr.transferencias.ServidorCR;
import com.beco.colascr.transferencias.configuracion.PoliticaTarea;

/*
* En esta clase se realizaron modificaciones referentes a la migración a java 1.6 por jperez
* Sólo se parametrizó el tipo de dato contenido en los 'Class' 
* Fecha: agosto 2011
*/
public class ModeloTabla extends AbstractTableModel implements Serializable
{
	private DecimalFormat df = new DecimalFormat("#0.##");
	String[] titulos;
	Object[][] datos;
	Class<?>[] types;

	/**
	 * Constructor para ModeloTabla.
	 * 
	 */
	public ModeloTabla()
	{
		super();
		titulos = new String[]{"Tarea", "Ultima Ejecución", "Duración", "Próxima Ejecución", "Estado"};
		Object[][] datos1 = {{"","","","",""}};
		datos = datos1;
		types = new Class<?>[]{String.class, String.class, String.class, String.class, String.class};
	}

	/**
	 * Constructor para ModeloTabla.
	 * @param xDatos
	 * @param xTitulos
	 */
	public ModeloTabla(Object[][] xDatos, String[] xTitulos, Class<?>[]xTipos)
	{
		datos = xDatos;
		titulos = xTitulos;
		types = xTipos;
	}

	public ModeloTabla(Object[][] xDatos,String[] xTitulos)
	{
		datos = xDatos;
		titulos = xTitulos;
	}

	public ModeloTabla(Object[][] xDatos)
	{
		datos = xDatos;
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
			}
			else if (this.getTypes()[i].getName().equals("java.lang.Double")){
				datos[0][i] = new Double(0.00);
			} else if (this.getTypes()[i].getName().equals("java.lang.Float")){
				datos[0][i] = new Float(0.00);
			}
			else datos[0][i] = "";
		}
		fireTableDataChanged();
	}
	
	/**
	 * Método llenarTabla.
	 * 	Llena la tabla correspondiente al detalle de la factura, asociada
	 * al modelo de tabla. 
	 * 
	 */
	public void llenarTabla(PoliticaTarea bases, PoliticaTarea afiliados, PoliticaTarea afiliadosTemp, PoliticaTarea productos, PoliticaTarea ventas, PoliticaTarea listaregalos) {
		try{
			datos = new Object[6][titulos.length];
			datos[0][0] = bases.getNombreTarea();
			datos[0][1] = (bases.getUltimaSincronizacion()==null) ? "Nunca" : bases.getUltimaSincronizacion().toString();
			datos[0][2] = df.format(bases.getDuracionSinc()/1000) + " Segs";
			datos[0][3] = (bases.getProximaSincronizacion()==null) ? "Al Iniciar" : bases.getProximaSincronizacion().toString();
			datos[0][4] = (ServidorCR.getEstadoServ()==ServidorCR.SERVICIO_DETENIDO) ? "DETENIDA" : (bases.getEstadoTarea()==PoliticaTarea.INICIADA) ? "EJECUTANDO" : (bases.getEstadoTarea()==PoliticaTarea.FINALIZANDO) ? "FINALIZANDO" : "FINALIZADA";

			datos[1][0] = afiliados.getNombreTarea();
			datos[1][1] = (afiliados.getUltimaSincronizacion()==null) ? "Nunca" : afiliados.getUltimaSincronizacion().toString();
			datos[1][2] = df.format(afiliados.getDuracionSinc()/1000) + " Segs";
			datos[1][3] = (afiliados.getProximaSincronizacion()==null) ? "Al Iniciar" : afiliados.getProximaSincronizacion().toString();
			datos[1][4] = (ServidorCR.getEstadoServ()==ServidorCR.SERVICIO_DETENIDO) ? "DETENIDA" : (afiliados.getEstadoTarea()==PoliticaTarea.INICIADA) ? "EJECUTANDO" : (afiliados.getEstadoTarea()==PoliticaTarea.FINALIZANDO) ? "FINALIZANDO" : "FINALIZADA";

			datos[2][0] = afiliadosTemp.getNombreTarea();
			datos[2][1] = (afiliadosTemp.getUltimaSincronizacion()==null) ? "Nunca" : afiliadosTemp.getUltimaSincronizacion().toString();
			datos[2][2] = df.format(afiliadosTemp.getDuracionSinc()/1000) + " Segs";
			datos[2][3] = (afiliadosTemp.getProximaSincronizacion()==null) ? "Al Iniciar" : afiliadosTemp.getProximaSincronizacion().toString();
			datos[2][4] = (ServidorCR.getEstadoServ()==ServidorCR.SERVICIO_DETENIDO) ? "DETENIDA" : (afiliadosTemp.getEstadoTarea()==PoliticaTarea.INICIADA) ? "EJECUTANDO" : (afiliados.getEstadoTarea()==PoliticaTarea.FINALIZANDO) ? "FINALIZANDO" : "FINALIZADA";

			datos[3][0] = productos.getNombreTarea();
			datos[3][1] = (productos.getUltimaSincronizacion()==null) ? "Nunca" : productos.getUltimaSincronizacion().toString();
			datos[3][2] = df.format(productos.getDuracionSinc()/1000) + " Segs";
			datos[3][3] = (productos.getProximaSincronizacion()==null) ? "Al Iniciar" : productos.getProximaSincronizacion().toString();
			datos[3][4] = (ServidorCR.getEstadoServ()==ServidorCR.SERVICIO_DETENIDO) ? "DETENIDA" : (productos.getEstadoTarea()==PoliticaTarea.INICIADA) ? "EJECUTANDO" : (productos.getEstadoTarea()==PoliticaTarea.FINALIZANDO) ? "FINALIZANDO" : "FINALIZADA";

			datos[4][0] = ventas.getNombreTarea();
			datos[4][1] = (ventas.getUltimaSincronizacion()==null) ? "Nunca" : ventas.getUltimaSincronizacion().toString();
			datos[4][2] = df.format(ventas.getDuracionSinc()/1000) + " Segs";
			datos[4][3] = (ventas.getProximaSincronizacion()==null) ? "Al Iniciar" : ventas.getProximaSincronizacion().toString();
			datos[4][4] = (ServidorCR.getEstadoServ()==ServidorCR.SERVICIO_DETENIDO) ? "DETENIDA" : (ventas.getEstadoTarea()==PoliticaTarea.INICIADA) ? "EJECUTANDO" : (ventas.getEstadoTarea()==PoliticaTarea.FINALIZANDO) ? "FINALIZANDO" : "FINALIZADA";

			datos[5][0] = listaregalos.getNombreTarea();
			datos[5][1] = (listaregalos.getUltimaSincronizacion()==null) ? "Nunca" : listaregalos.getUltimaSincronizacion().toString();
			datos[5][2] = df.format(listaregalos.getDuracionSinc()/1000) + " Segs";
			datos[5][3] = (listaregalos.getProximaSincronizacion()==null) ? "Al Iniciar" : listaregalos.getProximaSincronizacion().toString();
			datos[5][4] = (ServidorCR.getEstadoServ()==ServidorCR.SERVICIO_DETENIDO) ? "DETENIDA" : (listaregalos.getEstadoTarea()==PoliticaTarea.INICIADA) ? "EJECUTANDO" : (listaregalos.getEstadoTarea()==PoliticaTarea.FINALIZANDO) ? "FINALIZANDO" : "FINALIZADA";

			fireTableDataChanged();
		} catch (NullPointerException e){
			this.iniciarDatosTabla(titulos.length);
		} catch (Exception e){
			e.printStackTrace();
		}
	}
}