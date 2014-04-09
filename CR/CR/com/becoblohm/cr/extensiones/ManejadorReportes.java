/*
 * $Id: BuscadorCliente.java,v 1.2 2005/03/10 15:54:31 programa8 Exp $
 * ===========================================================================
 * Material Propiedad SuperFerretería EPA C.A. 
 *
 * Proyecto		: CR
 * Paquete		: com.becoblohm.cr.extensiones
 * Programa		: ManejadorReportes.java
 * Creado por	: gmartinelli
 * Creado en 	: 28-mar-2008 9:42:31
 * ---------------------------------------------------------------------------
 * Información de Repositorio:
 * ---------------------------------------------------------------------------
 * Rama de Desarrollo	: $Name:  $
 * Bloqueado por		: $Locker:  $
 * Estado de Revisión	: $State: Exp $
 * ---------------------------------------------------------------------------
 * Actualizaciones:
 * ---------------------------------------------------------------------------
 *
 * ===========================================================================
 */
package com.becoblohm.cr.extensiones;

import java.util.Date;
import java.util.Vector;

import com.becoblohm.cr.excepciones.BaseDeDatosExcepcion;
import com.becoblohm.cr.excepciones.ConexionExcepcion;
import com.becoblohm.cr.excepciones.ExcepcionCr;
import com.becoblohm.cr.extensiones.impl.manejarpago.FormaDePago;
import com.becoblohm.cr.manejarbr.VentaBR;
import com.becoblohm.cr.manejarservicio.Apartado;
import com.becoblohm.cr.manejarservicio.ListaRegalos;
import com.becoblohm.cr.manejarusuario.Usuario;
import com.becoblohm.cr.manejarventa.Anulacion;
import com.becoblohm.cr.manejarventa.Cliente;
import com.becoblohm.cr.manejarventa.Devolucion;
import com.becoblohm.cr.manejarventa.Venta;



/**
 * <pre>
 * Proyecto: CR 
 * Clase: ManejadorReportes
 * </pre>
 * <p>
 * <a href="ManejadorReportes.java.html"><i>View Source</i></a>
 * </p>
 * 
 * @author gmartinelli - $Author: gmartinelli$
 * @version $Revision: 1.0 $ - $Date: 2008/03/28 09:42:31 $
 * @since 28-mar-2008
 * @
 */
public interface ManejadorReportes extends CRExtension {
	
	/*
	 *  Documentos relacionados con Reportes de Control de la CR 
	 */
	public void resetPrinter();
	
	public void imprimirAperturaCajero(Usuario usuario);

	public void imprimirCierreCajero(Usuario usuario);
	
	/*
	 *  Documentos relacionados con Reportes Fiscales (X, Z)
	 */
	public void imprimirReporteX(String codUsuario, Date fechaReporte) throws ExcepcionCr;

	public void imprimirReporteZ() throws BaseDeDatosExcepcion, ConexionExcepcion;
	

	/*
	 *  Documentos relacionados con Transacciones (Ventas, Anulaciones, Devoluciones) 
	 */
	public void imprimirFacturaVenta(Venta ventaActual, boolean reimpresion, boolean esContribuyenteOrdinario) 
	throws BaseDeDatosExcepcion, ConexionExcepcion;

	public void imprimirFacturaAnulacion(Anulacion anulacion) throws BaseDeDatosExcepcion, ConexionExcepcion;

	public void imprimirFacturaDevolucion(Devolucion devolucion, boolean mostrarVuelto, double condicional, double vuelto) 
	throws BaseDeDatosExcepcion, ConexionExcepcion;
	
	public void imprimirComprobanteDesctoEmpleado(Cliente cte, String codAutorizante, double monto, int numCopias);

	/*
	 *  Documentos relacionados con el Procesamiento de Pagos 
	 */
	public void imprimirCheque(double monto, Date fecha);
	
	public void imprimirVoucherPago(Cliente cte, String codAutorizante, FormaDePago fPago, double monto, int numCopias, int numTrans);
	
	/*
	 * Documentos relacionados a Servicios de Apartados / Lista de Regalos
	 */
	public void imprimirComprobanteDeApartado(Apartado apartado);
	
	public void imprimirEtiquetasApartado(Apartado apartado, int nroEtiquetas);
	
	public void imprimirComprobanteAbono(Apartado apartado);
	
	public void imprimirComprobanteAbono(int numTransaccion, double vuelto, ListaRegalos lista);
	
	public void imprimirCancelacionApartadoPedidoEspecial(Apartado apartado, boolean apartadoCancelado);
	
	public void imprimirComprobanteAnulacionApartado(Apartado apartado, double totalAbonosActivos);
	
	public void imprimirAnulacionDeAbono(Apartado apartado, int renglonAbono);
	
	public void imprimirAbonosAnterioresLR(ListaRegalos lista);

	/*
	 * Documentos No Fiscales de Despacho y Entrega
	 */
	public void imprimirNotaDeDespacho(Venta ventaActual, int numServ);
	
	public void imprimirNotaDeEntrega(Venta ventaActual, int numServ, String dirEntrega);
	
	/*
	 * Documentos No Fiscales de Bonos Regalo
	 */
	public void imprimirComprobanteBonoRegalo(VentaBR venta, boolean duplicado, boolean mostrarVuelto, boolean abrirGaveta);
	
	
	public void imprimirFacturaBR(VentaBR ventaBR, boolean abrirGaveta) throws BaseDeDatosExcepcion, ConexionExcepcion;
	
	public void imprimirConsolidadoBR(int nroComprobanteCP, Vector<Vector<Object>> fPagos, Usuario usuario, Date fecha);
	
	public void imprimirAnulacionBR(VentaBR ventaBR, Cliente clienteAnterior, boolean mostrarVuelto, boolean abrirGaveta) throws BaseDeDatosExcepcion, ConexionExcepcion;
}
