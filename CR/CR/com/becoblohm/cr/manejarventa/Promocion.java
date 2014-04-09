/**
 * =============================================================================
 * Proyecto   : CajaRegistradora
 * Paquete    : com.becoblohm.producto
 * Programa   : Promocion.java
 * Creado por : irojas
 * Creado en  : 06-oct-03 14:05:25
 *
 * (c) Becoblohm, C.A.
 * -----------------------------------------------------------------------------
 * 				Actualizaciones
 * -----------------------------------------------------------------------------
 * Versión     : 1.2
 * Fecha       : 03/07/2008
 * Analista    : jgraterol
 * Descripción : Todos los atributos cambiados a protected para que sean visto desde
 * 				la clase PromocionExt (que extiende a esta) en la extension de promociones
 * =============================================================================
 * Versión     : 1.1 (según CVS antes del cambio) 
 * Fecha       : 10/02/2004 09:43 AM
 * Analista    : IROJAS
 * Descripción : Se modificaron las siguientes líneas:
 * 					Línea 42: Descripción de variables: Cambiada variable codProducto 
 * 							  de Long a String
 *					Línea 65: Constructor de la clase: Cambiado parámetro codProducto 
 *							  de Long a String
 * =============================================================================
 */
package com.becoblohm.cr.manejarventa;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

/** 
 * Descripción: 
 * 		Maneja las promociones aplicadas a los productos. Se tienen para ellas
 * todos los datos necesarios (Codigos, Fechas y horas de vigencia, etc.)
 */
public class Promocion implements Serializable {

	protected int codPromocion;
	protected char tipoPromocion; // -----> Dpto, Linea/seccion,Producto,Cupon
	protected Date fechaInicio; 
	protected Time horaInicio; 
	protected Date fechaFinaliza;
	protected Time horaFinaliza;
	protected int prioridad;
	protected int numDetalle;
	protected int numCupon;
	protected String codDepartamento;
	protected String codLineaSeccion;
	protected String codProducto;
	protected double porcentajeDescuento;
	protected double precioFinal;
                   
	/**
	 * Constructor de la clase Promocion.
	 * @param codPromocion Codigo de la promocion.
	 * @param tipoPromocion Tipo de promocion (Dpto, linea/Secc, Producto, Cupon).
	 * @param fechaInicio Fecha de inicio.
	 * @param horaInicio Hora de inicio.
	 * @param fechaFinaliza Fecha de culminación.
	 * @param horaFinaliza Hora de culminación.
	 * @param prioridad Prioridad de la promocion (Para decidir cuando aplican varias promociones por producto.
	 * @param numDetalle Numero de detalle (Un mismo codigo de promocion aplicado a varios productos, o dptos, etc).
	 * @param numCupon Numero de cupon (Para las promociones por cupon).
	 * @param codDepartamento Departamento al que aplica la promocion.
	 * @param codLineaSeccion Linea/Secc al que aplica la promocion.
	 * @param codProducto Producto al que aplica la promocion.
	 * @param porcentajeDescuento Porcentaje de descuento.
	 * @param precioFinal Precio final
	 */
	public Promocion (int codPromocion, char tipoPromocion, Date fechaInicio, Time horaInicio, Date fechaFinaliza,
			Time horaFinaliza, int prioridad, int numDetalle, int numCupon, String codDepartamento,
			String codLineaSeccion, String codProducto, double porcentajeDescuento, double precioFinal) {
		this.codPromocion = codPromocion;
		this.tipoPromocion = tipoPromocion;
		this.fechaInicio = fechaInicio;
		this.horaInicio = horaInicio;
		this.fechaFinaliza = fechaFinaliza;
		this.horaFinaliza = horaFinaliza;
		this.prioridad = prioridad;
		this.numDetalle = numDetalle;
		this.numCupon = numCupon;
		this.codDepartamento = codDepartamento;
		this.codLineaSeccion = codLineaSeccion;
		this.codProducto = codProducto;
		this.porcentajeDescuento = porcentajeDescuento;
		this.precioFinal = precioFinal;				
	}
	
	/**
	 * Constructor agregado por la actualizacion BECO del 3/07/2008
	 * **/
	public Promocion(int codPromocion, 
			char tipoPromocion, 
			Date fechaInicio, 
			Time horaInicio, 
			Date fechaFinaliza, 
			Time horaFinaliza, 
			String codDepartamento,
			String codLineaSeccion,
			String codProducto,
			double porcentajeDescuento){
		
		this.codPromocion = codPromocion;
		this.tipoPromocion = tipoPromocion;
		this.fechaInicio = fechaInicio;
		this.horaInicio = horaInicio;
		this.fechaFinaliza = fechaFinaliza;
		this.horaFinaliza = horaFinaliza;
		this.codDepartamento = codDepartamento;
		this.codLineaSeccion = codLineaSeccion;
		this.codProducto = codProducto;
		this.porcentajeDescuento = porcentajeDescuento;
		
	}
	
	/**
	 * Constructor agregado por la actualizacion BECO del 3/07/2008
	 * **/
	public Promocion(int codPromocion, 
			char tipoPromocion, 
			Date fechaInicio, 
			Time horaInicio, 
			Date fechaFinaliza, 
			Time horaFinaliza, 
			String codProducto,
			double porcentajeDescuento){
		
		this.codPromocion = codPromocion;
		this.tipoPromocion = tipoPromocion;
		this.fechaInicio = fechaInicio;
		this.horaInicio = horaInicio;
		this.fechaFinaliza = fechaFinaliza;
		this.horaFinaliza = horaFinaliza;
		this.codProducto = codProducto;
		this.porcentajeDescuento = porcentajeDescuento;
		
	}
	
	/**
	 * Constructor agregado por la actualizacion BECO del 3/07/2008
	 * **/
	public Promocion(int codPromocion, 
			char tipoPromocion, 
			Date fechaInicio, 
			Time horaInicio, 
			Date fechaFinaliza, 
			Time horaFinaliza,
			int numDetalle){
		
		this.codPromocion = codPromocion;
		this.tipoPromocion = tipoPromocion;
		this.fechaInicio = fechaInicio;
		this.horaInicio = horaInicio;
		this.fechaFinaliza = fechaFinaliza;
		this.horaFinaliza = horaFinaliza;
		this.numDetalle = numDetalle;
		
	}
	
	/**
	 * Constructor agregado por la actualizacion BECO del 3/07/2008
	 * **/
	public Promocion(int codPromocion, 
			double porcentajeDescuento,
			String codProducto,
			int numDetalle,
			int prioridad,
			char tipoPromocion){
		
		this.codPromocion = codPromocion;
		this.codProducto = codProducto;
		this.porcentajeDescuento = porcentajeDescuento;
		this.prioridad = prioridad;
		this.tipoPromocion = tipoPromocion;
		
	}
	
	/**
	 * Constructor de la clase solo con la clave primaria
	 * Actualizacion BECO del 3/07/2008
	 * **/
	public Promocion(int codPromocion,int numDetalle){
		this.codPromocion = codPromocion;
		this.numDetalle = numDetalle;
	}
	
	/**
	 * Constructor de la clase solo con la clave primaria
	 * Actualizacion BECO del 3/07/2008
	 * **/
	public Promocion(int codPromocion,int numDetalle, String codProducto){
		this.codPromocion = codPromocion;
		this.numDetalle = numDetalle;
		this.codProducto = codProducto;
	}
	
	/**
	 * Constructor agregado por la actualizacion BECO del 3/07/2008
	 * **/
	public Promocion(int codPromocion, 
			char tipoPromocion, 
			Date fechaInicio, 
			Time horaInicio, 
			Date fechaFinaliza, 
			Time horaFinaliza,
			int prioridad,
			int numDetalle,
			double porcDescuento){
		
		this.codPromocion = codPromocion;
		this.tipoPromocion = tipoPromocion;
		this.fechaInicio = fechaInicio;
		this.horaInicio = horaInicio;
		this.fechaFinaliza = fechaFinaliza;
		this.horaFinaliza = horaFinaliza;
		this.numDetalle = numDetalle;
		this.prioridad = prioridad;
		this.porcentajeDescuento = porcDescuento;
		
	}
	
	/**
	 * Constructor agregado por la actualizacion BECO del 3/07/2008
	 * **/
	public Promocion(int codPromocion, 
			char tipoPromocion, 
			Date fechaInicio, 
			Time horaInicio, 
			Date fechaFinaliza, 
			Time horaFinaliza,
			int prioridad,
			double porcDescuento){
		
		this.codPromocion = codPromocion;
		this.tipoPromocion = tipoPromocion;
		this.fechaInicio = fechaInicio;
		this.horaInicio = horaInicio;
		this.fechaFinaliza = fechaFinaliza;
		this.horaFinaliza = horaFinaliza;
		this.prioridad = prioridad;
		this.porcentajeDescuento = porcDescuento;
		
	}
	

	/**
	 * Obtiene la prioridad de la promocion.
	 * @return int - Entero que representa la prioridad.
	 */
	public int getPrioridad() {
		return prioridad;
	}

	/**
	 * Obtiene el porcentaje de descuento.
	 * @return double - El porcentaje de descuento.
	 */
	public double getPorcentajeDescuento() {
		return porcentajeDescuento;
	}

	/**
	 * Obtiene el precio final.
	 * @return double - El precio final.
	 */
	public double getPrecioFinal() {
		return precioFinal;
	}

	/**
	 * Obtiene el codigo de la promocion.
	 * @return int - Entero que representa el codigo de la promocion.
	 */
	public int getCodPromocion() {
		return codPromocion;
	}

	/**
	 * @return el codDepartamento
	 */
	public String getCodDepartamento() {
		return codDepartamento;
	}

	/**
	 * @param codDepartamento el codDepartamento a establecer
	 */
	public void setCodDepartamento(String codDepartamento) {
		this.codDepartamento = codDepartamento;
	}

	/**
	 * @return el codLineaSeccion
	 */
	public String getCodLineaSeccion() {
		return codLineaSeccion;
	}

	/**
	 * @param codLineaSeccion el codLineaSeccion a establecer
	 */
	public void setCodLineaSeccion(String codLineaSeccion) {
		this.codLineaSeccion = codLineaSeccion;
	}

	/**
	 * @return el codProducto
	 */
	public String getCodProducto() {
		return codProducto;
	}

	/**
	 * @param codProducto el codProducto a establecer
	 */
	public void setCodProducto(String codProducto) {
		this.codProducto = codProducto;
	}

	/**
	 * @return el fechaFinaliza
	 */
	public Date getFechaFinaliza() {
		return fechaFinaliza;
	}

	/**
	 * @param fechaFinaliza el fechaFinaliza a establecer
	 */
	public void setFechaFinaliza(Date fechaFinaliza) {
		this.fechaFinaliza = fechaFinaliza;
	}

	/**
	 * @return el fechaInicio
	 */
	public Date getFechaInicio() {
		return fechaInicio;
	}

	/**
	 * @param fechaInicio el fechaInicio a establecer
	 */
	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	/**
	 * @return el horaFinaliza
	 */
	public Time getHoraFinaliza() {
		return horaFinaliza;
	}

	/**
	 * @param horaFinaliza el horaFinaliza a establecer
	 */
	public void setHoraFinaliza(Time horaFinaliza) {
		this.horaFinaliza = horaFinaliza;
	}

	/**
	 * @return el horaInicio
	 */
	public Time getHoraInicio() {
		return horaInicio;
	}

	/**
	 * @param horaInicio el horaInicio a establecer
	 */
	public void setHoraInicio(Time horaInicio) {
		this.horaInicio = horaInicio;
	}

	/**
	 * @return el numCupon
	 */
	public int getNumCupon() {
		return numCupon;
	}

	/**
	 * @param numCupon el numCupon a establecer
	 */
	public void setNumCupon(int numCupon) {
		this.numCupon = numCupon;
	}

	/**
	 * @return el numDetalle
	 */
	public int getNumDetalle() {
		return numDetalle;
	}

	/**
	 * @param numDetalle el numDetalle a establecer
	 */
	public void setNumDetalle(int numDetalle) {
		this.numDetalle = numDetalle;
	}

	/**
	 * @return el tipoPromocion
	 */
	public char getTipoPromocion() {
		return tipoPromocion;
	}

	/**
	 * @param tipoPromocion el tipoPromocion a establecer
	 */
	public void setTipoPromocion(char tipoPromocion) {
		this.tipoPromocion = tipoPromocion;
	}

	/**
	 * @param codPromocion el codPromocion a establecer
	 */
	public void setCodPromocion(int codPromocion) {
		this.codPromocion = codPromocion;
	}

	/**
	 * @param porcentajeDescuento el porcentajeDescuento a establecer
	 */
	public void setPorcentajeDescuento(double porcentajeDescuento) {
		this.porcentajeDescuento = porcentajeDescuento;
	}

	/**
	 * @param precioFinal el precioFinal a establecer
	 */
	public void setPrecioFinal(double precioFinal) {
		this.precioFinal = precioFinal;
	}

	/**
	 * @param prioridad el prioridad a establecer
	 */
	public void setPrioridad(int prioridad) {
		this.prioridad = prioridad;
	}
	
	public Object clone(){
		Promocion p = new Promocion(this.codPromocion,this.tipoPromocion, this.fechaInicio, this.horaInicio, this.fechaFinaliza, this.horaFinaliza, this.codDepartamento, this.codLineaSeccion, this.codProducto, this.porcentajeDescuento);
		p.setPrioridad(this.prioridad);
		p.setNumDetalle(this.numDetalle);
		p.setNumCupon(this.numCupon);
		p.setPrecioFinal(this.precioFinal);
		
		return p;
	}
}
