/*
 * Creado el 30/05/2007
 *
 * Para cambiar la plantilla para este archivo generado vaya a
 * Ventana&gt;Preferencias&gt;Java&gt;Generación de código&gt;Código y comentarios
 */
package com.epa.testdriver;



import java.sql.Time;
import java.util.GregorianCalendar;

import com.epa.crprinterdriver.*;
/**
 * @author lgomez
 *
 * Para cambiar la plantilla para este comentario de tipo generado vaya a
 * Ventana&gt;Preferencias&gt;Java&gt;Generación de código&gt;Código y comentarios
 */
public class TestDriverIBM {
	public CRFiscalPrinterOperations crFiscalPrinterOperations;
	
	static String justificar (String textoIzq, String textoDer, int anchoColumna) {
		String lineaJustificada = textoIzq;
		int espaciosEnBlanco = anchoColumna - textoIzq.length() - textoDer.length();
		for (int j=0; j<espaciosEnBlanco; j++)
			lineaJustificada += " ";
		return lineaJustificada += textoDer;
	}
	
	static String centrar (String linea, int anchoColumna) {
		String lineaCentrada = "";
		int espaciosEnBlanco = (anchoColumna - linea.length()) / 2;
		for (int i=0; i<espaciosEnBlanco; i++)
			lineaCentrada += " ";
		return lineaCentrada += linea;
	}
	
	static String crearLineaDeDivision(int anchoColumna) {
		return crearLineaDeDivision(anchoColumna, '-');
	}
	
	static String crearLineaDeDivision(int anchoColumna, char caracter) {
		String linea = "";
		for (int i=0; i<anchoColumna; i++)
			linea += String.valueOf(caracter);
		return linea;
	}
	
	public void pruebaImpresoraDriver(){
		crFiscalPrinterOperations = new CRFiscalPrinterOperations("COM1","9600", "None", "None", "8", "1","None");
		crFiscalPrinterOperations.setPrinterConfig();
		crFiscalPrinterOperations.abrirPuertoImpresora();
		
		//PrinterListen listener = new PrinterListen(); 
		
		//crFiscalPrinterOperations.addFiscalPrinterListener(listener);
		
		//crFiscalPrinterOperations.abrirGaveta();
		//crFiscalPrinterOperations.crearDocumento(true);
		//crFiscalPrinterOperations.cerrarComprobanteFiscal("A");
		//crFiscalPrinterOperations.cancelarComprobante();
		
		//crFiscalPrinterOperations.resetDocument();
		//crFiscalPrinterOperations.abrirDocumentoNoFiscal();
		
		/*crFiscalPrinterOperations.enviarLineaNoFiscal("COMERCIAL AMAND C.A.", 3);
		crFiscalPrinterOperations.enviarLineaNoFiscal(justificar("00/00/0000", "00:00:00", 38), 0);

		String cajero = "Cajero: 00000000";
		String caja = "POS: 000";
		crFiscalPrinterOperations.enviarLineaNoFiscal(justificar(caja, cajero, 38), 0);
		DecimalFormat numFactura = new DecimalFormat("00000000000");
		String trx = "Ult. Tr Caja:" + numFactura.format(0);
		crFiscalPrinterOperations.enviarLineaNoFiscal(centrar(trx, 38), 0);

		crFiscalPrinterOperations.enviarLineaNoFiscal(crearLineaDeDivision(38), 0);
		crFiscalPrinterOperations.enviarLineaNoFiscal("APERTURA DE CAJERO", 3);
		crFiscalPrinterOperations.cerrarDocumentoNoFiscal();
		crFiscalPrinterOperations.cortarPapel();*/

		
		/*crFiscalPrinterOperations.setCalculoMetodoExclusivo(0);
		crFiscalPrinterOperations.abrirComprobanteFiscal("Razon Social", "Rif Cliente");
		
		double precio=0;
		double total =0;
		double impuesto=0;
		int cantidad =0;
		crFiscalPrinterOperations.imprimirTextoFiscal("CLIENTE:  FACTURA INCL..");
		crFiscalPrinterOperations.imprimirTextoFiscal("======================================");
		precio=45; impuesto=9; cantidad=2;
		crFiscalPrinterOperations.imprimirItemVenta("DESCRIPCION DEL ART1",0, 0,impuesto);
		crFiscalPrinterOperations.imprimirItemVenta("DESCRIPCION DEL ART2",0, 0,impuesto);
		crFiscalPrinterOperations.imprimirItemVenta("DESCRIPCION DEL ART3",0, 0,impuesto);
		crFiscalPrinterOperations.imprimirItemVenta("DESCRIPCION DEL ART4",0, 0,impuesto);
		crFiscalPrinterOperations.imprimirItemVenta("PRODUCTO 1",cantidad, precio,impuesto);
		total+= (precio*cantidad) + ((cantidad*precio)*impuesto/100);
		precio=54; impuesto=0; cantidad=1;
		crFiscalPrinterOperations.imprimirItemVenta("PRODUCTO 2",cantidad, precio,impuesto);
		total+= (precio*cantidad) + (cantidad*precio)*impuesto/100;
		precio=8; impuesto=8;
		crFiscalPrinterOperations.imprimirItemVenta("PRODUCTO 3",1, precio,impuesto);
		total= total + precio + precio*impuesto/100;
		precio=16; impuesto=19;
		crFiscalPrinterOperations.imprimirItemVenta("PRODUCTO 4",1, precio,impuesto);
		total= total + precio + precio*impuesto/100;
		crFiscalPrinterOperations.subTotalTransaccion(total,1,3);
		crFiscalPrinterOperations.realizarPago("CHEQUE",total-
				5.0,2,0);
		crFiscalPrinterOperations.realizarPago("EFECTIVO",7,1,5);
		pruebaCheque();
		crFiscalPrinterOperations.cerrarComprobanteFiscalVentas("A");
		//crFiscalPrinterOperations.imprimirCodigoBarra("1234567890123");
		crFiscalPrinterOperations.cortarPapel();
		//crFiscalPrinterOperations.c*/
		//crFiscalPrinterOperations.abrirDocumentoNoFiscal();
		//crFiscalPrinterOperations.imprimirTextoFiscal("WWWWWWWWWWWWWWWWWWWW");// 1);*/
		pruebaCheque();
	/*	try {
			crFiscalPrinterOperations.commit();
		} catch (PrinterNotConnectedException e) {
		}
		/*crFiscalPrinterOperations.crearDocumento(false);
		crFiscalPrinterOperations.abrirDocumentoNoFiscal();*/
		//crFiscalPrinterOperations.enviarLineaNoFiscal("WWWWWWWWWWWWWWWWWWWW", 1);
		//crFiscalPrinterOperations.cerrarDocumentoNoFiscal();
		//crFiscalPrinterOperations.abrirComprobanteFiscal();
		//crFiscalPrinterOperations.cerrarComprobanteFiscal("A");
		//crFiscalPrinterOperations.cortarPapel();

		/*


		/**
		 * PARA REALIZAR LAS PRUEBAS ES NECESARIO QUITAR EL COMENTARIO
		 * DE LAS LLAMADAS A LOS METODOS. POR LOGICA DEL DRIVER CRFISCALPRINTEROPERATIONS
		 * NO SE PUEDEN ENVIAR DOS TIPOS DE DOCUMENTOS ANTES DE EMITIR EL COMMIT
		 * */
		/**
		 * Prueba de comandos de Lectura de Tablas y Status:
		 * 
		 * */
		//crFiscalPrinterOperations.getTablaTotales();
		/*try {
		 		System.out.println(crFiscalPrinterOperations.obtenerSerialImpresora());
		 		System.out.println(crFiscalPrinterOperations.obtenerFechaImpresoraFiscal());
		 		System.out.println(crFiscalPrinterOperations.obtenerHoraImpresoraFiscal());
		 	}catch(PrinterNotConnectedException e){
		 	}*/
		//crFiscalPrinterOperations.getStatus('E');		
		/**
		 * Cambio de Parametros en impresora
		 * */
		
		//crFiscalPrinterOperations.setFechaHora();
		/**
		 * Impresion de Reportes
		 * */
		//crFiscalPrinterOperations.imprimirCodigoBarra("654564684534654")*;
		
		//crFiscalPrinterOperations.cancelarCheque();
		//crFiscalPrinterOperations.reporteZ();
		//crFiscalPrinterOperations.reporteX();
		//crFiscalPrinterOperations.cortarPapel()*;
		//pruebaCodigoBarras();
		//pruebaComandosGet();
	//	crFiscalPrinterOperations.cancelarCheque();
		//crFiscalPrinterOperations.cancelarComprobanteVentas();
		//crFiscalPrinterOperations.cortarPapel();*
		/**
		 * Factura Venta
		 * */
	//	crFiscalPrinterOperations.abrirComprobanteDevolucion("prueba ca", "854325534135", 56, "564534654", GregorianCalendar.getInstance().getTime(),new Time(10));
		
	
		
		//pruebaComandosFactura();
		//pruebaComandosFacturaImpIncluido();
		
		//pruebaReporteNoFiscal();
		//pruebaComandosFactura();
		//pruebaReporteCheque();
		/**
		 * Devoluciones
		 * */
		//pruebaComandosFactura();
		//pruebaComandosNotaCreditoImpIncl();

		//crFiscalPrinterOperations.cerrarDocumentoNoFiscal();

		//crFiscalPrinterOperations.crearDocumento(true);
		//crFiscalPrinterOperations.cancelarComprobante();
		
		/*crFiscalPrinterOperations.abrirDocumentoNoFiscal();
//
		crFiscalPrinterOperations.enviarLineaNoFiscal("Prueba", 3);
//
		crFiscalPrinterOperations.enviarLineaNoFiscal("Prueba", 0);*/

		//crFiscalPrinterOperations.cerrarDocumentoNoFiscal();
		//crFiscalPrinterOperations.cortarPapel();
		//crFiscalPrinterOperations.crearDocumento(true);
		//crFiscalPrinterOperations.cancelarComprobante();
		
		
		//pruebaComandosFacturaImpIncluido();
		
		try {
			crFiscalPrinterOperations.commit();
		} catch (PrinterNotConnectedException e) {
		}
		crFiscalPrinterOperations.cerrarPuertoImpresora();
	
	}
	public  void pruebaReporteNoFiscal(){
		/**
		 * Activacion de Slip: Activar codigo comentado para hacer la pruebas
		 * de Impresion por estación DI
		 ***/
		//crFiscalPrinterOperations.activarSlip();
		/*try{
			crFiscalPrinterOperations.commit();
		}catch(PrinterNotConnectedException ex){
		}*/
		
		crFiscalPrinterOperations.abrirDocumentoNoFiscal();
		crFiscalPrinterOperations.enviarLineaNoFiscal("CENTRAR",3);
		crFiscalPrinterOperations.enviarLineaNoFiscal("01234567890123456789012345678901234567890",0);
		crFiscalPrinterOperations.enviarLineaNoFiscal("TEXTO EN REPORTE NO FISCAL LINEA 2",0);
		crFiscalPrinterOperations.enviarLineaNoFiscal("TEXTO EN REPORTE NO FISCAL LINEA 3",0);
		crFiscalPrinterOperations.enviarLineaNoFiscal("TEXTO EN REPORTE NO FISCAL LINEA 4",0);
		crFiscalPrinterOperations.enviarLineaNoFiscal("TEXTO EN REPORTE NO FISCAL LINEA 5",0);
		crFiscalPrinterOperations.enviarLineaNoFiscal("TEXTO EN REPORTE NO FISCAL LINEA 6",0);
		crFiscalPrinterOperations.enviarLineaNoFiscal("TEXTO EN REPORTE NO FISCAL LINEA 7",0);
		crFiscalPrinterOperations.enviarLineaNoFiscal("TEXTO EN REPORTE NO FISCAL LINEA 8",0);
		crFiscalPrinterOperations.enviarLineaNoFiscal("TEXTO EN REPORTE NO FISCAL LINEA 9",0);
		crFiscalPrinterOperations.enviarLineaNoFiscal("TEXTO EN REPORTE NO FISCAL LINEA 10",1);
		crFiscalPrinterOperations.enviarLineaNoFiscal("TEXTO EN REPORTE NO FISCAL LINEA 11",1);
		crFiscalPrinterOperations.enviarLineaNoFiscal("TEXTO EN REPORTE NO FISCAL LINEA 12",1);
		crFiscalPrinterOperations.enviarLineaNoFiscal("TEXTO EN REPORTE NO FISCAL LINEA 13",1);
		crFiscalPrinterOperations.cerrarDocumentoNoFiscal();
		crFiscalPrinterOperations.cortarPapel();

	}

	public  void pruebaReporteCheque(){
		/**
		 * Activacion de Slip: Activar codigo comentado para hacer la pruebas
		 * de Impresion por estación DI
		 ***/
		/*crFiscalPrinterOperations.activarSlip();
		try{
			crFiscalPrinterOperations.commit();
		}catch(PrinterNotConnectedException ex){
		}*/
		crFiscalPrinterOperations.crearDocumento(true);
		crFiscalPrinterOperations.activarSlip();
		crFiscalPrinterOperations.impresionCheque(7777.77, "Super Ferreteria EPA", GregorianCalendar.getInstance().getTime(),"BSFF.");
		//crFiscalPrinterOperations.cerrarDocumentoNoFiscal();

	}

	public  void pruebaCodigoBarras(){
		crFiscalPrinterOperations.imprimirCodigoBarra("1234567890123");
		crFiscalPrinterOperations.imprimirCodigoBarra("00320070606008436737");
		crFiscalPrinterOperations.imprimirCodigoBarra("8436737");
	}

	public  void pruebaComandosGet()
	{
		/*
		System.out.println(crFiscalPrinterOperations.obtenerHoraImpresoraFiscal());
		*/
		//Activar Codigo para Probar comando: prox. # CF
		/*try{
		System.out.println(crFiscalPrinterOperations.proximoNumeroComprobanteFiscal());
		}catch(PrinterNotConnectedException ex){
		}*/		
		
		crFiscalPrinterOperations.getStatus('N');
	}	
	
	public  void pruebaCheque(){
		
		crFiscalPrinterOperations.activarSlip();
		crFiscalPrinterOperations.impresionCheque(7777.77, "CentroBeco", GregorianCalendar.getInstance().getTime(),"BSF.");
		//crFiscalPrinterOperations.voltearDocumentoEstacionDI();
		//crFiscalPrinterOperations.imprimirEndosoCheque("Corriente", "1125121", "Debe ser depositado en la cuenta... ");
//		crFiscalPrinterOperations.voltearDocumentoEstacionDI();
//		crFiscalPrinterOperations.imprimirEndosoCheque("CORRIENTE", "1051546541654", "Super Ferreteria EPA");
	}
	
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se comentó variable sin uso
	* Fecha: agosto 2011
	*/
	public  void pruebaComandosFactura(){
		//String texto="";
		//montos sin impuestos
		//crFiscalPrinterOperations.crearDocumento(true);
		crFiscalPrinterOperations.abrirComprobanteFiscal("X","X");
		double precio=0;
		double total =0;
		double impuesto=0;
		int cantidad =0;
		crFiscalPrinterOperations.imprimirTextoFiscal("CLIENTE:  LENIN GOMEZ.");
		crFiscalPrinterOperations.imprimirTextoFiscal("======================================");
		precio=45.75; impuesto=0; cantidad=2;
		crFiscalPrinterOperations.imprimirItemVenta("DESCRIPCION DEL ART1",0, 0,impuesto);
		crFiscalPrinterOperations.imprimirItemVenta("DESCRIPCION DEL ART2",0, 0,impuesto);
		crFiscalPrinterOperations.imprimirItemVenta("DESCRIPCION DEL ART3",0, 0,impuesto);
		crFiscalPrinterOperations.imprimirItemVenta("DESCRIPCION DEL ART4",0, 0,impuesto);
		impuesto=9;
		crFiscalPrinterOperations.imprimirItemVenta("PRODUCTO 1",cantidad, precio,impuesto);
		total+= (precio*cantidad) + (cantidad*precio)*impuesto/100;
		precio=54.91; impuesto=0; cantidad=1;
		//crFiscalPrinterOperations.cancelarComprobanteVentas();
		crFiscalPrinterOperations.imprimirItemVenta("PRODUCTO 2",cantidad, precio,impuesto);
		total+= (precio*cantidad) + (cantidad*precio)*impuesto/100;
	
		precio=8.16; impuesto=0;
		crFiscalPrinterOperations.imprimirItemVenta("PRODUCTO 3",1, precio,impuesto);
		total= total + precio + precio*impuesto/100;

		precio=16.71; impuesto=19;
		crFiscalPrinterOperations.imprimirItemVenta("PRODUCTO 4",1, precio,impuesto);
		total= total + precio + precio*impuesto/100;

		crFiscalPrinterOperations.subTotalTransaccion(total,1,3);
		crFiscalPrinterOperations.realizarPago("CHEQUE",total-
				5,2,0);
		crFiscalPrinterOperations.realizarPago("EFECTIVO",7,1,5);
		;
		/**
		 * ACTIVAR CODIGO: PRUEBA DE COMANDOS DE IMPRESION DE CHEQUE.
		 * */
		//pruebaCheque();
		
		crFiscalPrinterOperations.cerrarComprobanteFiscalVentas("A");
	//	crFiscalPrinterOperations.imprimirCodigoBarra("1234567890123");
		crFiscalPrinterOperations.enviarLineaNoFiscal("ffffffffffffffffffffffffff", 2);
	
		crFiscalPrinterOperations.cortarPapel();
	}


	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se comentó variable sin uso
	* Fecha: agosto 2011
	*/
	public  void pruebaComandosFacturaImpIncluido(){
		//String texto="";
		//montos sin impuestos
		crFiscalPrinterOperations = new CRFiscalPrinterOperations("COM1","57600", "None", "None", "8", "1","None");
		crFiscalPrinterOperations.setPrinterConfig();
		crFiscalPrinterOperations.abrirPuertoImpresora();
		crFiscalPrinterOperations.crearDocumento(true);
		crFiscalPrinterOperations.setCalculoMetodoExclusivo(1);
		crFiscalPrinterOperations.abrirComprobanteFiscal();
		
		double precio=0;
		double total =0;
		double impuesto=0;
		int cantidad =0;
		crFiscalPrinterOperations.imprimirTextoFiscal("CLIENTE:  FACTURA INCL..");
		crFiscalPrinterOperations.imprimirTextoFiscal("======================================");
		precio=45; impuesto=9; cantidad=2;
		crFiscalPrinterOperations.imprimirItemVenta("DESCRIPCION DEL ART1",0, 0,impuesto);
		crFiscalPrinterOperations.imprimirItemVenta("DESCRIPCION DEL ART2",0, 0,impuesto);
		crFiscalPrinterOperations.imprimirItemVenta("DESCRIPCION DEL ART3",0, 0,impuesto);
		crFiscalPrinterOperations.imprimirItemVenta("DESCRIPCION DEL ART4",0, 0,impuesto);
		crFiscalPrinterOperations.imprimirItemVenta("PRODUCTO 1",cantidad, precio+(precio*impuesto/100),impuesto);
		total+= (precio*cantidad) + (cantidad*precio)*impuesto/100;
		precio=54; impuesto=0; cantidad=1;
		crFiscalPrinterOperations.imprimirItemVenta("PRODUCTO 2",cantidad, precio+(precio*impuesto/100),impuesto);
		total+= (precio*cantidad) + (cantidad*precio)*impuesto/100;
		precio=8; impuesto=8;
		crFiscalPrinterOperations.imprimirItemVenta("PRODUCTO 3",1, precio+(precio*impuesto/100),impuesto);
		total= total + precio + precio*impuesto/100;
		precio=16; impuesto=19;
		crFiscalPrinterOperations.imprimirItemVenta("PRODUCTO 4",1, precio+(precio*impuesto/100),impuesto);
		total= total + precio + precio*impuesto/100;
		crFiscalPrinterOperations.subTotalTransaccion(total,1,3);
		crFiscalPrinterOperations.realizarPago("CHEQUE",total-
				5.0,2,0);
		crFiscalPrinterOperations.realizarPago("EFECTIVO",7,1,5);
		
		/**
		 * PRUEBA DE COMANDOS DE IMPRESION DE CHEQUE.
		 **/
		pruebaCheque();
		crFiscalPrinterOperations.cerrarComprobanteFiscalVentas("A");
		try {
			crFiscalPrinterOperations.commit();
		} catch (PrinterNotConnectedException e) {
			e.printStackTrace();
		}
		crFiscalPrinterOperations.cerrarPuertoImpresora();
		//crFiscalPrinterOperations.enviarLineaNoFiscal("WWWWWWWWWWWWWWWWWWWW", 1);
		//crFiscalPrinterOperations.imprimirCodigoBarra("1234567890123");
		//crFiscalPrinterOperations.cortarPapel();
		
	}

	
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se comentó variable sin uso
	* Fecha: agosto 2011
	*/
	public  void pruebaComandosNotaCredito(){
		//String texto="";
		//montos sin impuestos
		crFiscalPrinterOperations.crearDocumento(true);
		Time horaVta=null;
		crFiscalPrinterOperations.abrirComprobanteDevolucion("","",0,"",new java.util.Date(),horaVta);
		double precio=0;
		//double total =0;
		double impuesto=0;
		precio=95; impuesto=9;
		crFiscalPrinterOperations.imprimirItemNotaCredito("PRODUCTO 1",1, precio,impuesto);
		precio=5;
		crFiscalPrinterOperations.imprimirItemNotaCredito("PRODUCTO 2",1, precio,impuesto);
		precio=195;
		crFiscalPrinterOperations.imprimirItemNotaCredito("PRODUCTO 3",1, precio,impuesto);
		precio=0;
		crFiscalPrinterOperations.imprimirItemNotaCredito("PRODUCTO X",1, precio,impuesto);
		precio=985;
		crFiscalPrinterOperations.imprimirItemNotaCredito("PRODUCTO 4",1, precio,impuesto);
		crFiscalPrinterOperations.cerrarComprobanteFiscalNotaCredito("A");
	}
	
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se comentaron variables sin uso
	* Fecha: agosto 2011
	*/
	public  void pruebaComandosNotaCreditoImpIncl(){
		//String texto="";
		//montos sin impuestos
		crFiscalPrinterOperations.crearDocumento(true);
		Time horaVta=null;
		crFiscalPrinterOperations.setCalculoMetodoExclusivo(1);
		crFiscalPrinterOperations.abrirComprobanteDevolucion("","",0,"",new java.util.Date(),horaVta);
		double precio=0;
		//double total =0;
		double impuesto=0;
		precio=95; impuesto=9;
		crFiscalPrinterOperations.imprimirItemNotaCredito("PRODUCTO 1",1, precio+(precio*impuesto/100),impuesto);
		precio=5;
		crFiscalPrinterOperations.imprimirItemNotaCredito("PRODUCTO 2",1, precio+(precio*impuesto/100),impuesto);
		precio=195;
		crFiscalPrinterOperations.imprimirItemNotaCredito("PRODUCTO 3",1, precio+(precio*impuesto/100),impuesto);
		precio=985;
		crFiscalPrinterOperations.imprimirItemNotaCredito("PRODUCTO 4",1, precio+(precio*impuesto/100),impuesto);
		precio=95;
		crFiscalPrinterOperations.cerrarComprobanteFiscalNotaCredito("A");
	}	
	
	public void desbloquearImpresora(int o){
		crFiscalPrinterOperations = new CRFiscalPrinterOperations("COM1","57600", "None", "None", "8", "1","None");
		crFiscalPrinterOperations.setPrinterConfig();
		crFiscalPrinterOperations.abrirPuertoImpresora();
		switch(o) {
		case 1:
			//OPCION 1:
			crFiscalPrinterOperations.crearDocumento(true);
			crFiscalPrinterOperations.cancelarComprobante();
			break;
		case 2:
			//OPCION 2:
			crFiscalPrinterOperations.crearDocumento(true);
			crFiscalPrinterOperations.resetDocument();
			break;
		case 3:
			//OPCION 3:
			crFiscalPrinterOperations.crearDocumento(false);
			crFiscalPrinterOperations.cancelarComprobante();
			break;
		case 4:
			//OPCION 4:
			crFiscalPrinterOperations.crearDocumento(false);
			crFiscalPrinterOperations.cerrarDocumentoNoFiscal();
			break;
		case 5:
			//OPCION 5:
			crFiscalPrinterOperations.crearDocumento(true);
			crFiscalPrinterOperations.cerrarComprobanteFiscal("A");
			break;
		case 6:
			//OPCION 6:
			crFiscalPrinterOperations.crearDocumento(true);
			crFiscalPrinterOperations.cerrarComprobanteFiscalNotaCredito("A");
			break;
		default:
			crFiscalPrinterOperations.crearDocumento(false);
			crFiscalPrinterOperations.abrirDocumentoNoFiscal();
			crFiscalPrinterOperations.enviarLineaNoFiscal("TEXTO DE PRUEBA", 1);
			crFiscalPrinterOperations.cortarPapel();
			crFiscalPrinterOperations.cerrarDocumentoNoFiscal();
			break;
		}
		
		try {
			crFiscalPrinterOperations.commit();
		} catch (PrinterNotConnectedException e) {
		}
		crFiscalPrinterOperations.cerrarPuertoImpresora();
	
	}

	public static void main(String[] args) {
		new TestDriverIBM();
		//System.out.println(System.getProperty("java.library.path"));
		
		/*String hola = "Hola";
		hola = hola.replaceAll("hola", "chao");
		System.out.println(hola);*/
		
	}
	
	public TestDriverIBM() {
		//pruebaImpresoraDriver();
		//desbloquearImpresora(Integer.parseInt(opc));
		pruebaComandosFacturaImpIncluido();
	}

}

