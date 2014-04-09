/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblohm.cr.gui.listaregalos
 * Programa   : PrintUtilities.java
 * Creado por : rabreu
 * Creado en  : 07/09/2006
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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Chromaticity;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.MediaPrintableArea;
import javax.print.attribute.standard.MediaSizeName;
import javax.print.attribute.standard.PrintQuality;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JWindow;
import javax.swing.RepaintManager;
import javax.swing.SwingConstants;

import com.becoblohm.cr.excepciones.ExcepcionCr;
import com.becoblohm.cr.excepciones.ExcepcionLR;
import com.becoblohm.cr.manejadorsesion.Sesion;
import com.becoblohm.cr.manejarservicio.DetalleServicio;
import com.becoblohm.cr.manejarservicio.ListaRegalos;
import com.becoblohm.cr.manejarservicio.OperacionLR;
import com.becoblohm.cr.mediadoresbd.MediadorBD;

/**
 * @url http://www.developerdotstar.com/community/node/124
 */
public class PrintUtilities implements Printable {
	private static SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyy");
	private static SimpleDateFormat formatoHora = new SimpleDateFormat("h:mm a");
	
	private Component componentToBePrinted;
	private Component componentToBePrinted2;
//	private static int CARTA = 1;
//	private static int MEDIA_CARTA = 2;
//	private static int tamanoPapel;

	private void print(int copias) throws ExcepcionLR {
		PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
		PrinterJob printJob = PrinterJob.getPrinterJob();
		PrintService printService= PrintServiceLookup.lookupDefaultPrintService();
		//--- Add the document page using a landscape page format
		Paper paper = new Paper();
//		if(tamanoPapel == CARTA)
		paper.setImageableArea(36,54,576,810);
//		else if(tamanoPapel == MEDIA_CARTA)
//			paper.setImageableArea(36,54,340,576);
		PageFormat pageFormat = new PageFormat();
		pageFormat.setPaper(paper);
		pageFormat.setOrientation(PageFormat.PORTRAIT);
		printJob.setPrintable(this,pageFormat);
		try {
			printJob.setPrintService(printService);
		} catch (PrinterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		pras.add(new Copies(copias));
		pras.add(MediaSizeName.NA_LETTER);
		pras.add(new MediaPrintableArea((float)paper.getImageableX(),(float)paper.getImageableY(),(float)paper.getImageableHeight(),(float)paper.getImageableWidth(),MediaPrintableArea.INCH));
		pras.add(PrintQuality.NORMAL);
		pras.add(Chromaticity.MONOCHROME);
		
		String osname = System.getProperty("os.name","").toLowerCase(); //Resuelve el bug de impresion en Linux
		if (osname.startsWith("windows"))
		{
		if (printJob.printDialog(pras))  //Esta linea es la que genera la pantalla de dialogo que hace que el sistema
										// falle en linux
			try {
				printJob.setCopies(((Copies)pras.get(Copies.class)).getValue()); // Corrige el bug que no deja que imprima varias copias
				printJob.print();
				if(componentToBePrinted2!=null){
					componentToBePrinted=componentToBePrinted2;
					printJob.print();
				}
			} catch (PrinterException pe) {
				System.out.println("Error imprimiendo: " + pe);
			}
		}
		else
		{
			try {
				printJob.setCopies(((Copies)pras.get(Copies.class)).getValue()); // Corrige el bug que no deja que imprima varias copias
				printJob.print();
				if(componentToBePrinted2!=null){
					componentToBePrinted=componentToBePrinted2;
					printJob.print();
				}
			} catch (PrinterException pe) {
				System.out.println("Error imprimiendo: " + pe);
			}
		}
	}

	public int print(Graphics g, PageFormat pageFormat, int pageIndex) {
		Graphics2D g2d = (Graphics2D) g;
//		for faster printing, turn off double buffering
		disableDoubleBuffering(componentToBePrinted);
		Dimension d = componentToBePrinted.getSize(); //get size of document
//		double panelWidth = d.width; //width in pixels
		double panelHeight = d.height; //height in pixels
		double pageHeight = pageFormat.getImageableHeight(); //height of printer page
//		double pageWidth = pageFormat.getImageableWidth(); //width of printer page
//		double scale = pageWidth / panelWidth;
		int totalNumPages = (int) Math.ceil(panelHeight / pageHeight);
		// make sure not print empty pages
		if (pageIndex >= totalNumPages)
			return NO_SUCH_PAGE;
		else {
		  // shift Graphic to line up with beginning of print-imageable region
		  g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
		  // shift Graphic to line up with beginning of next page to print
		  g2d.translate(0f, -pageIndex * pageHeight);
		  // scale the page so the width fits...
//		  g2d.scale(scale, scale);
		  componentToBePrinted.paint(g2d); //repaint the page for printing
		  enableDoubleBuffering(componentToBePrinted);
		  return Printable.PAGE_EXISTS;
		}
	}
	
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public static void imprimirReporteAperturaLista(ListaRegalos lista) throws ExcepcionCr {
		String fechaApertura = formatoFecha.format(lista.getFechaApertura());
		String tipoEvento = lista.getTipoEvento();
		int numTienda = lista.getCodTienda();
		int tiendaOrigen = lista.getCodTiendaApertura();
		int numLista = lista.getNumServicio();
		String titular = lista.getTitular().getNombreCompleto();
		String titularSec = lista.getTitularSec();
		String fechaEvento = formatoFecha.format(lista.getFechaEvento());
		Vector<DetalleServicio> detallesLista = lista.getDetallesServicio();
		String notificaciones = lista.isNotificaciones() ? "Activas" : "Inactivas";
		String permitirVenta = lista.isPermitirVenta() ? "Sí" : "No";
		if(lista.getTipoLista()==ListaRegalos.GARANTIZADA) {
			try {
				ReporteListaGarantizada reporte = new ReporteListaGarantizada(fechaApertura,tipoEvento,numTienda,numLista,
						tiendaOrigen,titular,titularSec,fechaEvento,detallesLista, true, permitirVenta, notificaciones);
//				tamanoPapel = CARTA;
				reporte.toBack();
				PrintUtilities.printComponent(reporte, 2);
				reporte.dispose();
			} catch (Exception e){
				throw new ExcepcionCr("Error imprimiendo reporte de lista");
			}
		}
		else if(lista.getTipoLista()==ListaRegalos.NOGARANTIZADA) {
			try {
				ReporteListaNoGarantizada reporte = new ReporteListaNoGarantizada(fechaApertura,tipoEvento,numTienda,numLista,
						tiendaOrigen,titular,titularSec,fechaEvento,detallesLista, true, permitirVenta, notificaciones);
//				tamanoPapel = CARTA;
				reporte.toBack();
				PrintUtilities.printComponent(reporte, 2);
				reporte.dispose();
			} catch (Exception e){
				throw new ExcepcionCr("Error imprimiendo reporte de lista");
			}
		}
	}
	
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public static void imprimirReporteLista(ListaRegalos lista) throws ExcepcionCr {
		String fechaApertura = formatoFecha.format(lista.getFechaApertura());
		String tipoEvento = lista.getTipoEvento();
		int numTienda = lista.getCodTienda();
		int tiendaOrigen = lista.getCodTiendaApertura();
		int numLista = lista.getNumServicio();
		String titular = lista.getTitular().getNombreCompleto();
		String titularSec = lista.getTitularSec();
		String fechaEvento = formatoFecha.format(lista.getFechaEvento());
		Vector<DetalleServicio> detallesLista = lista.getDetallesServicio();
		String notificaciones = lista.isNotificaciones() ? "Activas" : "Inactivas";
		String permitirVenta = lista.isPermitirVenta() ? "Sí" : "No";
		if(lista.getTipoLista()==ListaRegalos.GARANTIZADA)
			try {
				ReporteListaGarantizada reporte = new ReporteListaGarantizada(fechaApertura,tipoEvento,numTienda,numLista,
						tiendaOrigen,titular,titularSec,fechaEvento,detallesLista, false, permitirVenta, notificaciones);
//				tamanoPapel = CARTA;
				reporte.toBack();
				PrintUtilities.printComponent(reporte, 1);
				reporte.dispose();
			} catch (Exception e){
				e.printStackTrace();
				throw new ExcepcionCr("Error imprimiendo reporte de lista");
			}
		else if(lista.getTipoLista()==ListaRegalos.NOGARANTIZADA)
			try {
				ReporteListaNoGarantizada reporte = new ReporteListaNoGarantizada(fechaApertura,tipoEvento,numTienda,numLista,
						tiendaOrigen,titular,titularSec,fechaEvento,detallesLista, false, permitirVenta, notificaciones);
//				tamanoPapel = CARTA;
				reporte.toBack();
				PrintUtilities.printComponent(reporte, 1);
				reporte.dispose();
			} catch (Exception e){
				throw new ExcepcionCr("Error imprimiendo reporte de lista");
			}
	}

	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public static void imprimirReporteCierreLista(ListaRegalos lista) throws ExcepcionCr {
		String fechaCierre = formatoFecha.format(lista.getFechaCierre());
		String fechaApertura = formatoFecha.format(lista.getFechaApertura());
		String tipoEvento = lista.getTipoEvento();
		int numTienda = lista.getCodTienda();
		int tiendaOrigen = lista.getCodTiendaApertura();
		int numLista = lista.getNumServicio();
		String titular = lista.getTitular().getNombreCompleto();
		String titularSec = lista.getTitularSec();
		String fechaEvento = formatoFecha.format(lista.getFechaEvento());
		Vector<DetalleServicio> detallesLista = lista.getDetallesServicio();
		
		try {
			ReporteCierreLista reporte = new ReporteCierreLista(fechaCierre,fechaApertura,tipoEvento,numTienda,numLista,tiendaOrigen,titular,titularSec,fechaEvento,detallesLista);
//			tamanoPapel = CARTA;
			reporte.toBack();
			PrintUtilities.printComponent(reporte, 2);
			reporte.dispose();
		} catch (Exception e){
			throw new ExcepcionCr("Error imprimiendo reporte de Cierre de Lista");
		}
	}

	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public static void imprimirPrecierre(ListaRegalos lista) throws ExcepcionCr {
		String fecha = formatoFecha.format(Sesion.getFechaSistema());
		String hora = formatoHora.format(Sesion.getFechaSistema());
		String tipoEvento = lista.getTipoEvento();
		int numTienda = Sesion.getTienda().getNumero();
		int numLista = lista.getNumServicio();
		String titular = lista.getTitular().getNombreCompleto();
		String titularSec = lista.getTitularSec();
		Date fechaEvento = lista.getFechaEvento();
		Vector<DetalleServicio> detalleNoVendidos = lista.getDetalleNoVendidos();
		Vector<OperacionLR> operacionesVendidos = lista.getOperacionesVendidos();
		Vector<OperacionLR> operacionesAbonosT = lista.getOperacionesAbonosTotales();
		Vector<OperacionLR> operacionesAbonosP = lista.getOperacionesAbonosParciales();
		Vector<OperacionLR> operacionesAbonosL = lista.getOperacionesAbonosLista();
		int tiendaOrigen = lista.getCodTiendaApertura();
		char tipoLista = lista.getTipoLista();

		try {
			ReporteNoVendidos reporte1 = new ReporteNoVendidos(fecha,hora,tipoEvento,numTienda,numLista,tiendaOrigen,titular,titularSec,fechaEvento,detalleNoVendidos,tipoLista);
			ReporteVendidos reporte2 = new ReporteVendidos(fecha,hora,tipoEvento,numTienda,numLista,tiendaOrigen,titular,titularSec,fechaEvento,operacionesVendidos,operacionesAbonosT,operacionesAbonosP,operacionesAbonosL,tipoLista);
//			tamanoPapel = CARTA;
			reporte1.toBack();
			reporte2.toBack();
			PrintUtilities.printComponent(reporte1,reporte2, 1);
			reporte1.dispose();
			reporte2.dispose();
		} catch (Exception e){
			e.printStackTrace();
			throw new ExcepcionCr("Error imprimiendo Precierre");
		}
	}
	
	public static void imprimirComprobanteBonoRegalo(ListaRegalos lista,double montoTrans,double montoBonos,double montoCambio) throws ExcepcionCr {
		Date fechaActual = new Date();
		String fechaCierre = formatoFecha.format(fechaActual);
		String horaCierre = formatoHora.format(fechaActual);
		int numTienda = lista.getCodTienda();
		int numLista = lista.getNumServicio();
		String titular = lista.getTitular().getNombreCompleto();
		int tiendaOrigen = lista.getCodTiendaApertura();
		String codCajero = lista.getCodCajero();
		double montoAbonos = lista.getMontoAbonosLista()+lista.getMontoAbonosProds();
		String tipoEvento = lista.getTipoEvento();
		String fechaEvento = formatoFecha.format(lista.getFechaEvento());

		try {
			ComprobanteBonoRegalo comprobante = new ComprobanteBonoRegalo(fechaCierre,horaCierre,numTienda,numLista,
						tiendaOrigen,codCajero,titular,montoAbonos,montoTrans,montoBonos,montoCambio,tipoEvento,fechaEvento);
//			tamanoPapel = CARTA;
			comprobante.toBack();
			PrintUtilities.printComponent(comprobante, 1);
			comprobante.dispose();
		} catch (Exception e){
			e.printStackTrace();
			throw new ExcepcionCr("Error imprimiendo Comprobante de Bonos Regalo");
		}
	}
	
	public static void imprimirComprobanteBonoRegaloPromo(ListaRegalos lista,double montoBonos) throws ExcepcionCr {
		Date fechaActual = new Date();
		String fechaCierre = formatoFecha.format(fechaActual);
		String horaCierre = formatoHora.format(fechaActual);
		int numTienda = lista.getCodTienda();
		int numLista = lista.getNumServicio();
		String titular = lista.getTitular().getNombreCompleto();
		int tiendaOrigen = lista.getCodTiendaApertura();
		String codCajero = lista.getCodCajero();
		double montoVendidos = lista.getMontoVendidos();
		String tipoEvento = lista.getTipoEvento();
		String fechaEvento = formatoFecha.format(lista.getFechaEvento());

		try {
			ComprobanteBonoRegaloPromo comprobante = new ComprobanteBonoRegaloPromo(fechaCierre,horaCierre,numTienda,numLista,
						tiendaOrigen,codCajero,titular,montoVendidos,montoBonos,tipoEvento,fechaEvento);
//			tamanoPapel = CARTA;
			comprobante.toBack();
			PrintUtilities.printComponent(comprobante, 1);
			comprobante.dispose();
		} catch (Exception e){
			e.printStackTrace();
			throw new ExcepcionCr("Error imprimiendo Comprobante de Bono Regalos");
		}
	}

	static void printComponent(Component c, int copias) throws ExcepcionLR {
		new PrintUtilities(c,copias);
	}
	
	static void printComponent(Component c1,Component c2, int copias) throws ExcepcionLR {
		new PrintUtilities(c1,c2,copias);
	}

	public PrintUtilities(Component componentToBePrinted, int copias) throws ExcepcionLR {
		this.componentToBePrinted = componentToBePrinted;
		print(copias);
	}
	
	public PrintUtilities(Component componentToBePrinted1, Component componentToBePrinted2, int copias) throws ExcepcionLR {
		this.componentToBePrinted = componentToBePrinted1;
		this.componentToBePrinted2 = componentToBePrinted2;
		print(copias);
		
	}

	private static void disableDoubleBuffering(Component c) {
		RepaintManager currentManager = RepaintManager.currentManager(c);
		currentManager.setDoubleBufferingEnabled(false);
	}

	private static void enableDoubleBuffering(Component c) {
		RepaintManager currentManager = RepaintManager.currentManager(c);
		currentManager.setDoubleBufferingEnabled(true);
	}
}

/**
 * @author rabreu
 *
 * Para cambiar la plantilla para este comentario de tipo generado vaya a
 * Ventana&gt;Preferencias&gt;Java&gt;Generacón de código&gt;Código y comentarios
 */
/*
* En esta clase se realizaron modificaciones referentes a la migración a java 1.6 por jperez
* Sólo se parametrizó el tipo de dato de 'Comparator'
* Fecha: agosto 2011
*/
class ReporteListaGarantizada extends JWindow implements Comparator<Object> {

	private static final long serialVersionUID = 1L;

	private DecimalFormat df = new DecimalFormat("#,##0.00");
	
	private String tipoEvento;
	private int numTienda;
	private int tiendaOrigen;
	private int numLista;
	private String titular;
	private String titularSec;
	private int posY = 0;
	private int paginas = 1;
	private String notificaciones;
	private String permitirVenta;
	private boolean apertura;
	
	private javax.swing.JPanel jContentPane = null;

	private javax.swing.JLabel jLabel = null;
	private javax.swing.JLabel jLabel2 = null;
	private javax.swing.JTextField fechaTextField = null;
	private javax.swing.JPanel jPanel = null;
	private javax.swing.JLabel jLabel1 = null;
	private javax.swing.JLabel jLabel4 = null;
	private javax.swing.JLabel jLabel5 = null;
	private javax.swing.JLabel tituloReporte = null;
	private javax.swing.JLabel jLabel7 = null;
	private javax.swing.JLabel jLabel8 = null;
	private javax.swing.JTextField tiendaTextField = null;
	private javax.swing.JTextField horaTextField = null;
	private javax.swing.JTextField titularTextField = null;
	private javax.swing.JTextField titularSecTextField = null;
	private javax.swing.JTextField tiendaOrigenTextField = null;
	private javax.swing.JLabel jLabel12 = null;
	private javax.swing.JLabel jLabel13 = null;
	private javax.swing.JLabel jLabel14 = null;
	private javax.swing.JLabel jLabel15 = null;
	private javax.swing.JLabel jLabel16 = null;
	private javax.swing.JLabel jLabel17 = null;
	private javax.swing.JPanel jPanel1 = null;
	private javax.swing.JLabel jLabel3 = null;
	private javax.swing.JLabel jLabel9 = null;
	private javax.swing.JLabel jLabelPagina = null;
	private javax.swing.JLabel jLabel6 = null;
	private javax.swing.JTextField tipoEventoTF = null;
	private javax.swing.JLabel jLabel18 = null;
	private javax.swing.JLabel jLabel10 = null;
	private javax.swing.JLabel jLabel11 = null;
	
	/**
	 * This is the default constructor
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public ReporteListaGarantizada(String fechaApertura,String tipoEvento,
			int numTienda,int numLista,int tiendaOrigen,String titular,
			String titularSec,String fechaEvento, Vector<DetalleServicio> detallesLista,
			boolean apertura, String permitirVenta,String notificaciones) {
		super();

		this.apertura = apertura;
		this.tipoEvento = tipoEvento;
		this.numTienda = numTienda;
		this.tiendaOrigen = tiendaOrigen;
		this.numLista = numLista;
		this.titular = titular;
		this.titularSec = titularSec;
		this.permitirVenta = permitirVenta;
		this.notificaciones = notificaciones;		

		initialize();
		
		jPanel1.add(getJPanel(), null); //Agrega encabezado
		jPanel.setLocation(0, getPosActual());//Agrega encabezado
		insertarLineas(11);
		
		llenarDetalles(detallesLista);
		if(apertura)
			llenarPieDePagina();
		else {
			JLabel lineaTexto = new JLabel("Precios sujetos a cambios.");
			lineaTexto.setSize(500, 15);
			lineaTexto.setLocation(0, getPosActual());
			lineaTexto.setFont(new java.awt.Font("Sans Serif", Font.PLAIN, 10));
			jPanel1.add(lineaTexto,null);
		}
		this.setVisible(true);
	}

	public int compare(Object o1, Object o2) {
		DetalleServicio detalle1 = (DetalleServicio)o1;
		DetalleServicio detalle2 = (DetalleServicio)o2;
		int d1 = Integer.parseInt(detalle1.getProducto().getCodDepartamento());
		int d2 = Integer.parseInt(detalle2.getProducto().getCodDepartamento());
		
		return (d1-d2);
	}

	/**
	 * @param detallesLista
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	private void llenarDetalles(Vector<DetalleServicio> detallesLista) {
		double total = 0, iva = 0;
		int codDepto = -1;
		Collections.sort(detallesLista,this);

		if(detallesLista.size()==0){
			insertarLinea();
			JTextField mensajeTF = new JTextField("No hay productos en la lista que cumplan este criterio");
			mensajeTF.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
			mensajeTF.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
			mensajeTF.setLocation(0,getPosActual());
			mensajeTF.setSize(400,15);
			getJPanel1().add(mensajeTF);
		}else{		
			for(int i=0;i<detallesLista.size();i++){
				DetalleServicio detalle = (DetalleServicio)detallesLista.get(i);
				int cd = Integer.parseInt(detalle.getProducto().getCodDepartamento());
				if(cd > codDepto){
					codDepto = cd;
					insertarLinea();
					String departamento = MediadorBD.obtenerDescDepto(cd);
					JTextField codDeptoTF = new JTextField("Departamento:  "+cd+"  -  "+departamento);
					codDeptoTF.setFont(new java.awt.Font("Sans Serif", java.awt.Font.BOLD, 9));
					codDeptoTF.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
					codDeptoTF.setLocation(10,getPosActual());
					codDeptoTF.setSize(490,15);
					getJPanel1().add(codDeptoTF);
					insertarLinea();
				}
				JTextField codProducto = new JTextField(detalle.getProducto().getCodProducto());
				codProducto.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
				codProducto.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 9));
				codProducto.setLocation(0,getPosActual());
				codProducto.setSize(65,15);
				getJPanel1().add(codProducto);
	
				JTextField descProducto = new JTextField(detalle.getProducto().getDescripcionCorta());
				descProducto.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
				descProducto.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 9));
				descProducto.setLocation(65,getPosActual());
				descProducto.setSize(200,15);
				descProducto.select(0,0);
				getJPanel1().add(descProducto);
	
				JTextField marcaProducto = new JTextField(detalle.getProducto().getMarca());
				marcaProducto.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
				marcaProducto.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 9));
				marcaProducto.setLocation(271, getPosActual());
				marcaProducto.setSize(59,15);
				marcaProducto.select(0,0);
				getJPanel1().add(marcaProducto);

				double precioTotal = detalle.getPrecioFinal()*(1+detalle.getProducto().getImpuesto().getPorcentaje()/100);
				JTextField precio = new JTextField(df.format(precioTotal));
				precio.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
				precio.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 9));
				precio.setHorizontalAlignment(JTextField.RIGHT);
				precio.setLocation(311,getPosActual());
				precio.setSize(63,15);
				getJPanel1().add(precio);
				
				JTextField quiere = new JTextField(String.valueOf((int)detalle.getCantidad()));
				quiere.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
				quiere.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 9));
				quiere.setHorizontalAlignment(JTextField.RIGHT);
				quiere.setLocation(374,getPosActual());
				quiere.setSize(43,15);
				getJPanel1().add(quiere);
				
				JTextField tiene = new JTextField(String.valueOf((int)detalle.getCantVendidos()+(int)detalle.getCantAbonadosT()));
				tiene.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
				tiene.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 9));
				tiene.setHorizontalAlignment(JTextField.RIGHT);
				tiene.setLocation(417,getPosActual());
				tiene.setSize(36,15);
				getJPanel1().add(tiene);
				
				JTextField necesita = new JTextField(String.valueOf((int)detalle.getCantRestantes()));
				necesita.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
				necesita.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 9));
				necesita.setHorizontalAlignment(JTextField.RIGHT);
				necesita.setLocation(455,getPosActual());
				necesita.setSize(43,15);
				getJPanel1().add(necesita);
		
				JTextField abonos = new JTextField(df.format(detalle.getAbonos()));
				abonos.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
				abonos.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 9));
				abonos.setHorizontalAlignment(JTextField.RIGHT);
				abonos.setLocation(471,getPosActual());
				abonos.setSize(73,15);
				getJPanel1().add(abonos);
				
				total += (detalle.getPrecioFinal()*detalle.getCantidad());
				iva += (detalle.getMontoImpuesto()*detalle.getCantidad());
				
				insertarLinea();		
			}
			for(int i=0;i<3;i++){
				insertarLinea();
				if(apertura)
					switch(i){
						case 0:
						JTextField izquierda = new JTextField("SUBTOTAL");
						izquierda.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
						izquierda.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 9));
						izquierda.setHorizontalAlignment(JTextField.RIGHT);
						izquierda.setLocation(310,getPosActual());
						izquierda.setSize(110,15);
						getJPanel1().add(izquierda);
		
						JTextField derecha = new JTextField(df.format(total));
						derecha.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
						derecha.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 9));
						derecha.setHorizontalAlignment(JTextField.RIGHT);
						derecha.setLocation(430,getPosActual());
						derecha.setSize(65,15);
						getJPanel1().add(derecha);
						break;
						case 1:
						JTextField izquierda1 = new JTextField("IVA");
						izquierda1.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
						izquierda1.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 9));
						izquierda1.setHorizontalAlignment(JTextField.RIGHT);
						izquierda1.setLocation(310,getPosActual());
						izquierda1.setSize(110,15);
						getJPanel1().add(izquierda1);
			
						JTextField derecha1 = new JTextField(df.format(iva));
						derecha1.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
						derecha1.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 9));
						derecha1.setHorizontalAlignment(JTextField.RIGHT);
						derecha1.setLocation(430,getPosActual());
						derecha1.setSize(65,15);
						getJPanel1().add(derecha1);
						break;
						case 2:
						JTextField izquierda2 = new JTextField("TOTAL");
						izquierda2.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
						izquierda2.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 9));
						izquierda2.setHorizontalAlignment(JTextField.RIGHT);
						izquierda2.setLocation(310,getPosActual());
						izquierda2.setSize(110,15);
						getJPanel1().add(izquierda2);
				
						JTextField derecha2 = new JTextField(df.format(total+iva));
						derecha2.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
						derecha2.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 9));
						derecha2.setHorizontalAlignment(JTextField.RIGHT);
						derecha2.setLocation(430,getPosActual());
						derecha2.setSize(65,15);
						getJPanel1().add(derecha2);
						break;
					}
			}
		}
	}
	
	private void llenarPieDePagina(){
		insertarLineas(2);
		JLabel lineaTexto;

		insertarLineas(2);
		lineaTexto = new JLabel("Condiciones:");
		lineaTexto.setSize(550, 15);
		lineaTexto.setLocation(0, getPosActual());
		lineaTexto.setFont(new java.awt.Font("Sans Serif", java.awt.Font.BOLD, 9));
		jPanel1.add(lineaTexto,null);
		insertarLineas(2);
		lineaTexto = new JLabel("- El titular de la lista es la única persona autorizada para modificar y cerrar la Lista.");
		lineaTexto.setSize(550, 15);
		lineaTexto.setLocation(0, getPosActual());
		lineaTexto.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
		jPanel1.add(lineaTexto,null);
		insertarLinea();
		lineaTexto = new JLabel("- La lista podrá ser modificada por el titular sólo cuando sean adquiridos el 75% de los productos seleccionados");
		lineaTexto.setSize(550, 15);
		lineaTexto.setLocation(0, getPosActual());
		lineaTexto.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
		jPanel1.add(lineaTexto,null);
		insertarLinea();
		lineaTexto = new JLabel("  al momento de la apertura.");
		lineaTexto.setSize(550, 15);
		lineaTexto.setLocation(0, getPosActual());
		lineaTexto.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
		jPanel1.add(lineaTexto,null);
		insertarLinea();
		lineaTexto = new JLabel("- El cierre de la Lista  y el retiro de los productos adquiridos no podrá exceder de 45 días contados a partir de");
		lineaTexto.setSize(550, 15);
		lineaTexto.setLocation(0, getPosActual());
		lineaTexto.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
		jPanel1.add(lineaTexto,null);
		insertarLinea();
		lineaTexto = new JLabel("  la fecha del evento indicada en la Lista por el Titular. Si transcurrido el plazo de 45 días no ha retirado los");
		lineaTexto.setSize(550, 15);
		lineaTexto.setLocation(0, getPosActual());
		lineaTexto.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
		jPanel1.add(lineaTexto,null);
		insertarLinea();
		lineaTexto = new JLabel("  productos que han sido pagado en su totalidad, autoriza expresamente a Centrobeco C.A. a disponer de los");
		lineaTexto.setSize(550, 15);
		lineaTexto.setLocation(0, getPosActual());
		lineaTexto.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
		jPanel1.add(lineaTexto,null);
		insertarLinea();
		lineaTexto = new JLabel("  mismos.");
		lineaTexto.setSize(550, 15);
		lineaTexto.setLocation(0, getPosActual());
		lineaTexto.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
		jPanel1.add(lineaTexto,null);
		insertarLinea();
//		lineaTexto = new JLabel("  y a cobrarle un cargo por servicio equivalente al 10% del precio del producto, excluido cualquier impuesto.");
//		lineaTexto.setSize(550, 15);
//		lineaTexto.setLocation(0, getPosActual());
//		lineaTexto.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
//		jPanel1.add(lineaTexto,null);
		lineaTexto = new JLabel("- Se garantiza el precio regular del producto indicado en este reporte hasta el cierre de la Lista.");
		lineaTexto.setSize(550, 15);
		lineaTexto.setLocation(0, getPosActual());
		lineaTexto.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
		jPanel1.add(lineaTexto,null);
		insertarLinea();
		lineaTexto = new JLabel("- En caso de posponer la fecha del evento indicada en la Lista, los precios regulares aplicables serán los que se");
		lineaTexto.setSize(550, 15);
		lineaTexto.setLocation(0, getPosActual());
		lineaTexto.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
		jPanel1.add(lineaTexto,null);
		insertarLinea();
		lineaTexto = new JLabel("  encuentren vigentes para el momento de la modificación y los abonos a productos que se hayan realizado se");
		lineaTexto.setSize(550, 15);
		lineaTexto.setLocation(0, getPosActual());
		lineaTexto.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
		jPanel1.add(lineaTexto,null);
		insertarLinea();
		lineaTexto = new JLabel("  convertirán en abonos de dinero.");
		lineaTexto.setSize(550, 15);
		lineaTexto.setLocation(0, getPosActual());
		lineaTexto.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
		jPanel1.add(lineaTexto,null);
		insertarLinea();
		lineaTexto = new JLabel("- El titular deberá retirar los productos seleccionados que hayan sido pagados en su totalidad al momento");
		lineaTexto.setSize(550, 15);
		lineaTexto.setLocation(0, getPosActual());
		lineaTexto.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
		jPanel1.add(lineaTexto,null);
		insertarLinea();
		lineaTexto = new JLabel("  del cierre de la lista.");
		lineaTexto.setSize(550, 15);
		lineaTexto.setLocation(0, getPosActual());
		lineaTexto.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
		jPanel1.add(lineaTexto,null);
		insertarLinea();
		lineaTexto = new JLabel("- Si por cualquier causa, el Titular anula definitivamente la lista, la misma quedará sin efecto, teniendo la");
		lineaTexto.setSize(550, 15);
		lineaTexto.setLocation(0, getPosActual());
		lineaTexto.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
		jPanel1.add(lineaTexto,null);
		insertarLinea();
		lineaTexto = new JLabel("  obligación de retirar los productos seleccionados que hayan sido pagados en su totalidad y de recibir los");
		lineaTexto.setSize(550, 15);
		lineaTexto.setLocation(0, getPosActual());
		lineaTexto.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
		jPanel1.add(lineaTexto,null);
		insertarLinea();
		lineaTexto = new JLabel("  Bonos Regalos equivalentes a los  abonos de dinero acumulados hasta la fecha de su anulación");
		lineaTexto.setSize(550, 15);
		lineaTexto.setLocation(0, getPosActual());
		lineaTexto.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
		jPanel1.add(lineaTexto,null);
		insertarLinea();
		lineaTexto = new JLabel("- Los productos adquiridos serán despachados sin costo alguno dentro del lapso máximo de 5 días hábiles");
		lineaTexto.setSize(550, 15);
		lineaTexto.setLocation(0, getPosActual());
		lineaTexto.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
		jPanel1.add(lineaTexto,null);
		insertarLinea();
		lineaTexto = new JLabel("  siguientes al cierre de la lista. En caso de que el Titular indique una fecha posterior a ese plazo para");
		lineaTexto.setSize(550, 15);
		lineaTexto.setLocation(0, getPosActual());
		lineaTexto.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
		jPanel1.add(lineaTexto,null);
		insertarLinea();
		lineaTexto = new JLabel("  el despacho de los productos, será por su cuenta el costo del mismo");
		lineaTexto.setSize(550, 15);
		lineaTexto.setLocation(0, getPosActual());
		lineaTexto.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
		jPanel1.add(lineaTexto,null);
		insertarLineas(2);
		lineaTexto = new JLabel("Firma del Titular: __________________________________");
		lineaTexto.setSize(550, 15);
		lineaTexto.setLocation(10, getPosActual());
		lineaTexto.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
		jPanel1.add(lineaTexto,null);
		insertarLineas(2);
		lineaTexto = new JLabel("C.I./R.I.F.: __________________________________");
		lineaTexto.setSize(550, 15);
		lineaTexto.setLocation(10, getPosActual());
		lineaTexto.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
		jPanel1.add(lineaTexto,null);
	}

	private void insertarLineas(int lineas){
		for(int i=0;i<lineas;i++)
			insertarLinea();
	}
	
	private void insertarLinea(){
		posY += 15;
		if(posY>(810*paginas)-150)
			insertarNuevaPagina();
	}

	private int getPosActual(){
		return posY;
	}
	
	private void insertarNuevaPagina() {
		paginas++;
		posY = (810*(paginas-1)); // Coloca posY en una nueva página
		this.setSize(550,810*(paginas)); // Agrega otra pagina al frame
		this.getJPanel1().setSize(550,810*paginas); // Agrega otra pagina al JPanel1

		jPanel1.add(getJPanel(), null); //Agrega encabezado
		jPanel.setLocation(0, posY);//Agrega encabezado
		posY += 165;
	}
	
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setLocation(-1000,-1000);
		this.setSize(550, 810);
		this.setContentPane(getJContentPane());
	}
	
	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(getJPanel1(), null);
			jContentPane.setBackground(Color.white);
			jContentPane.setPreferredSize(new Dimension(550,810));
		}
		return jContentPane;
	}

	/**
	 * This method initializes jLabel
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel() {
		//if(jLabel == null) {
			jLabel = new javax.swing.JLabel();
			jLabel.setSize(97, 25);
			jLabel.setText("B E C O");
			jLabel.setFont(new java.awt.Font("Sans Serif", java.awt.Font.BOLD, 24));
			jLabel.setLocation(0, 20);
		//}
		return jLabel;
	}
	/**
	 * This method initializes jLabel2
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel2() {
		//if(jLabel2 == null) {
			jLabel2 = new javax.swing.JLabel();
			jLabel2.setSize(40, 15);
			jLabel2.setText("Fecha: ");
			jLabel2.setLocation(445, 20);
			jLabel2.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
			jLabel2.setPreferredSize(new java.awt.Dimension(50,16));
		//}
		return jLabel2;
	}
	/**
	 * This method initializes fecha
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JTextField getFecha() {
		fechaTextField = new javax.swing.JTextField();
		fechaTextField.setSize(60, 15);
		fechaTextField.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
		fechaTextField.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
		fechaTextField.setLocation(483, 20);			
		fechaTextField.setHorizontalAlignment(JTextField.RIGHT);
		fechaTextField.setText(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
		return fechaTextField;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel() {
		jPanel = new javax.swing.JPanel();
		jPanel.setLayout(null);
		jPanel.add(getJLabel(), null);
		jPanel.add(getJLabel2(), null);
		jPanel.add(getFecha(), null);
		jPanel.add(getJLabel1(), null);
		jPanel.add(getJLabel4(), null);
		jPanel.add(getJLabel5(), null);
		jPanel.add(getTituloReporte(), null);
		jPanel.add(getJLabel7(), null);
		jPanel.add(getJLabel8(), null);
		jPanel.add(getTienda(), null);
		jPanel.add(getHora(), null);
		jPanel.add(getTitular(), null);
		jPanel.add(getTitularSec(), null);
		jPanel.add(getJLabel12(), null);
		jPanel.add(getJLabel13(), null);
		jPanel.add(getJLabel14(), null);
		jPanel.add(getJLabel15(), null);
		jPanel.add(getJLabel16(), null);
		jPanel.add(getJLabel17(), null);
		jPanel.add(getJLabel3(), null);
		jPanel.add(getJLabel9(), null);
		jPanel.add(getTiendaOrigen(), null);
		jPanel.add(getJLabel6(), null);
		jPanel.add(getTipoEventoTF(), null);
		jPanel.add(getJLabel18(), null);
		jPanel.add(getJLabelPagina(),null);
		if(apertura){
			jPanel.add(getJLabel10(), null);
			jPanel.add(getJLabel11(), null);
		}
		jPanel.setSize(550, 165);
		jPanel.setBackground(java.awt.Color.white);
		return jPanel;
	}
	
	private javax.swing.JLabel getJLabelPagina(){
		jLabelPagina = new JLabel();
		jLabelPagina.setText("Pág. "+paginas);
		jLabelPagina.setSize(50, 15);
		jLabelPagina.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
		jLabelPagina.setLocation(245, 25);
		return jLabelPagina;
	}
	/**
	 * This method initializes jLabel1
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel1() {
		//if(jLabel1 == null) {
			jLabel1 = new javax.swing.JLabel();
			jLabel1.setSize(210, 15);
			jLabel1.setText("Servicios al Cliente:  Lista de Regalos");
			jLabel1.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
			jLabel1.setLocation(0, 45);
		//}
		return jLabel1;
	}
	/**
	 * This method initializes jLabel4
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel4() {
		//if(jLabel4 == null) {
			jLabel4 = new javax.swing.JLabel();
			jLabel4.setSize(50, 15);
			jLabel4.setText("Tienda:");
			jLabel4.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
			jLabel4.setLocation(0, 60);
		//}
		return jLabel4;
	}
	/**
	 * This method initializes jLabel5
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel5() {
		//if(jLabel5 == null) {
			jLabel5 = new javax.swing.JLabel();
			jLabel5.setSize(40, 15);
			jLabel5.setText("Hora:");
			jLabel5.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
			jLabel5.setLocation(445, 35);
		//}
		return jLabel5;
	}
	/**
	 * This method initializes jLabel6
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getTituloReporte() {
		//if(jLabel6 == null) {
			tituloReporte = new javax.swing.JLabel();
			tituloReporte.setSize(320, 15);
			tituloReporte.setText("Reporte de Lista de Regalos Garantizada No. "+numLista);
			tituloReporte.setFont(new java.awt.Font("Sans Serif", java.awt.Font.BOLD, 10));
			tituloReporte.setLocation(85, 70);
		//}
		return tituloReporte;
	}
	/**
	 * This method initializes jLabel7
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel7() {
		//if(jLabel7 == null) {
			jLabel7 = new javax.swing.JLabel();
			jLabel7.setSize(155, 15);
			jLabel7.setText("Nombre del Titular:");
			jLabel7.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
			jLabel7.setLocation(0, 100);
		//}
		return jLabel7;
	}
	/**
	 * This method initializes jLabel8
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel8() {
		//if(jLabel8 == null) {
			jLabel8 = new javax.swing.JLabel();
			jLabel8.setSize(155, 15);
			jLabel8.setText("Nombre del Titular Secundario:");
			jLabel8.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
			jLabel8.setLocation(0, 115);
		//}
		return jLabel8;
	}
	/**
	 * This method initializes tienda
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JTextField getTienda() {
		//if(tiendaTextField == null) {
		tiendaTextField = new javax.swing.JTextField();
		tiendaTextField.setSize(160, 15);
		tiendaTextField.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
		tiendaTextField.setLocation(50, 60);
		tiendaTextField.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
		tiendaTextField.setText(String.valueOf(numTienda));
		//}
		return tiendaTextField;
	}

	/**
	 * This method initializes hora
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JTextField getHora() {
		//if(horaTextField == null) {
		horaTextField = new javax.swing.JTextField();
		horaTextField.setSize(60, 15);
		horaTextField.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
		horaTextField.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
		horaTextField.setLocation(483, 35);			
		horaTextField.setHorizontalAlignment(JTextField.RIGHT);
		horaTextField.setText(new SimpleDateFormat("hh:mm a").format(new Date()));
		//}
		return horaTextField;
	}
	/**
	 * This method initializes titular
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JTextField getTitular() {
		//if(titularTextField == null) {
			titularTextField = new javax.swing.JTextField();
			titularTextField.setSize(150, 15);
			titularTextField.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
			titularTextField.setLocation(155, 100);
			titularTextField.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
			titularTextField.setText(titular);
			titularTextField.select(0,0);
		//}
		return titularTextField;
	}
	/**
	 * This method initializes titularSec
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JTextField getTitularSec() {
		//if(titularSecTextField == null) {
			titularSecTextField = new javax.swing.JTextField();
			titularSecTextField.setSize(150, 15);
			titularSecTextField.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
			titularSecTextField.setLocation(155, 115);
			titularSecTextField.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
			if(titularSec!=null && titularSec.trim()!="")
				titularSecTextField.setText(titularSec);
			else titularSecTextField.setText("N/A");
			titularSecTextField.select(0,0);
		//}
		return titularSecTextField;
	}
	/**
	 * This method initializes jLabel12
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel12() {
		//if(jLabel12 == null) {
			jLabel12 = new javax.swing.JLabel();
			jLabel12.setSize(65, 15);
			jLabel12.setText("Producto");
			jLabel12.setLocation(0, 150);
			jLabel12.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
			jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
		//}
		return jLabel12;
	}
	/**
	 * This method initializes jLabel13
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel13() {
		//if(jLabel13 == null) {
			jLabel13 = new javax.swing.JLabel();
			jLabel13.setSize(207, 15);
			jLabel13.setText("Descripción Producto");
			jLabel13.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
			jLabel13.setLocation(65, 150);
			jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
		//}
		return jLabel13;
	}
	/**
	 * This method initializes jLabel14
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel14() {
		//if(jLabel14 == null) {
			jLabel14 = new javax.swing.JLabel();
			jLabel14.setSize(45, 15);
			jLabel14.setText("Precio");
			jLabel14.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
			jLabel14.setLocation(329, 150);
			jLabel14.setPreferredSize(new java.awt.Dimension(40,13));
			jLabel14.setHorizontalAlignment(SwingConstants.CENTER);
		//}
		return jLabel14;
	}
	/**
	 * This method initializes jLabel15
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel15() {
		//if(jLabel15 == null) {
			jLabel15 = new javax.swing.JLabel();
			jLabel15.setSize(37, 15);
			jLabel15.setText("Tiene");
			jLabel15.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
			jLabel15.setLocation(417, 150);
			jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		//}
		return jLabel15;
	}
	/**
	 * This method initializes jLabel16
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel16() {
	//	if(jLabel16 == null) {
			jLabel16 = new javax.swing.JLabel();
			jLabel16.setSize(45, 15);
			jLabel16.setText("Necesita");
			jLabel16.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
			jLabel16.setLocation(453, 150);
			jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		//}
		return jLabel16;
	}
	/**
	 * This method initializes jLabel17
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel17() {
		//if(jLabel17 == null) {
			jLabel17 = new javax.swing.JLabel();
			jLabel17.setSize(45, 15);
			jLabel17.setText("Abonos");
			jLabel17.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
			jLabel17.setLocation(497, 150);
			jLabel17.setPreferredSize(new java.awt.Dimension(50,16));
			jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		//}
		return jLabel17;
	}
	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel1() {
		if(jPanel1 == null) {
			jPanel1 = new javax.swing.JPanel();
			jPanel1.setLayout(null);
			jPanel1.setSize(550, 810);
			jPanel1.setBackground(java.awt.Color.white);
			jPanel1.setLocation(0, 0);
		}
		return jPanel1;
	}
	/**
	 * This method initializes jLabel3
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel3() {
		jLabel3 = new javax.swing.JLabel();
		jLabel3.setSize(90, 15);
		jLabel3.setText("Tienda de ingreso:");
		jLabel3.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
		jLabel3.setLocation(315, 100);
		return jLabel3;
	}
	private javax.swing.JLabel getJLabel18() {
		//if(jLabel18 == null) {
			jLabel18 = new javax.swing.JLabel();
			jLabel18.setSize(59, 15);
			jLabel18.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
			jLabel18.setText("Marca");
			jLabel18.setLocation(271, 150);
			jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
		//}
		return jLabel18;
	}

	/**
	 * This method initializes jLabel9
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel9() {
		//if(jLabel9 == null) {
			jLabel9 = new javax.swing.JLabel();
			jLabel9.setSize(45, 15);
			jLabel9.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
			jLabel9.setText("Quiere");
			jLabel9.setLocation(373, 150);
			jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
	//	}
		return jLabel9;
	}
	/**
	 * This method initializes tiendaOrigenTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private javax.swing.JTextField getTiendaOrigen() {
		tiendaOrigenTextField = new javax.swing.JTextField();
		tiendaOrigenTextField.setSize(100, 15);
		tiendaOrigenTextField.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
		tiendaOrigenTextField.setLocation(405, 100);
		tiendaOrigenTextField.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
		tiendaOrigenTextField.setText(String.valueOf(tiendaOrigen));
		return tiendaOrigenTextField;
	}
	/**
	 * This method initializes jLabel6
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel6() {
		jLabel6 = new javax.swing.JLabel();
		jLabel6.setSize(90, 15);
		jLabel6.setText("Tipo de Evento:");
		jLabel6.setLocation(315, 115);
		jLabel6.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
		return jLabel6;
	}
	/**
	 * This method initializes tipoEventoTF
	 * 
	 * @return javax.swing.JTextField
	 */
	private javax.swing.JTextField getTipoEventoTF() {
		tipoEventoTF = new javax.swing.JTextField();
		tipoEventoTF.setSize(100, 15);
		tipoEventoTF.setLocation(405, 115);
		tipoEventoTF.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
		tipoEventoTF.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
		tipoEventoTF.setText(String.valueOf(tipoEvento));
		return tipoEventoTF;
	}
	/**
	 * This method initializes jLabel10
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel10() {
		//if(jLabel10 == null) {
			jLabel10 = new javax.swing.JLabel();
			jLabel10.setSize(200, 15);
			jLabel10.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
			jLabel10.setText("Permitir que se lleven los regalos: "+permitirVenta);
			jLabel10.setLocation(0, 130);
		//}
		return jLabel10;
	}
	/**
	 * This method initializes jLabel11
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel11() {
		//if(jLabel11 == null) {
			jLabel11 = new javax.swing.JLabel();
			jLabel11.setSize(150, 15);
			jLabel11.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
			jLabel11.setText("Notificaciones: "+notificaciones);
			jLabel11.setLocation(315, 130);
		//}
		return jLabel11;
	}
}

/**
 * @author rabreu
 *
 * Para cambiar la plantilla para este comentario de tipo generado vaya a
 * Ventana&gt;Preferencias&gt;Java&gt;Generación de código&gt;Código y comentarios
 */
/*
* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
* Sólo se parametrizó el tipo de dato de  'Comparator'
* Fecha: agosto 2011
*/
class ReporteListaNoGarantizada extends JWindow implements Comparator<Object> {

	private static final long serialVersionUID = 1L;

	private DecimalFormat df = new DecimalFormat("#,##0.00");
	
	private String tipoEvento;
	private int numTienda;
	private int tiendaOrigen;
	private int numLista;
	private String titular;
	private String titularSec;
	private int posY = 0, paginas = 1;
	private boolean apertura;
	private String permitirVenta, notificaciones;
	
	private javax.swing.JPanel jContentPane = null;

	private javax.swing.JLabel jLabel = null;
	private javax.swing.JLabel jLabel2 = null;
	private javax.swing.JTextField fechaTextField = null;
	private javax.swing.JPanel jPanel = null;
	private javax.swing.JLabel jLabel1 = null;
	private javax.swing.JLabel jLabel4 = null;
	private javax.swing.JLabel jLabel5 = null;
	private javax.swing.JLabel tituloReporte = null;
	private javax.swing.JLabel jLabel7 = null;
	private javax.swing.JLabel jLabel8 = null;
	private javax.swing.JTextField tiendaTextField = null;
	private javax.swing.JTextField horaTextField = null;
	private javax.swing.JTextField titularTextField = null;
	private javax.swing.JTextField titularSecTextField = null;
	private javax.swing.JTextField tiendaOrigenTextField = null;
	private javax.swing.JLabel jLabel12 = null;
	private javax.swing.JLabel jLabel13 = null;
	private javax.swing.JLabel jLabel15 = null;
	private javax.swing.JLabel jLabel16 = null;
	private javax.swing.JLabel jLabel17 = null;
	private javax.swing.JPanel jPanel1 = null;
	private javax.swing.JLabel jLabel3 = null;
	private javax.swing.JLabel jLabel9 = null;
	private javax.swing.JLabel jLabel6 = null;
	private javax.swing.JTextField tipoEventoTF = null;
	private javax.swing.JLabel jLabel10 = null;
	private javax.swing.JLabel jLabel18 = null;
	private javax.swing.JLabel jLabel11 = null;
	private javax.swing.JLabel jLabel19 = null;

	/**
	 * This is the default constructor
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public ReporteListaNoGarantizada(String fechaApertura,String tipoEvento,int numTienda,int numLista,int tiendaOrigen,
				String titular,String titularSec,String fechaEvento,Vector<DetalleServicio> detallesLista, boolean apertura,
				String permitirVenta, String notificaciones) {
		super();

		this.tipoEvento = tipoEvento;
		this.numTienda = numTienda;
		this.tiendaOrigen = tiendaOrigen;
		this.numLista = numLista;
		this.titular = titular;
		this.titularSec = titularSec;
		this.apertura = apertura;
		this.notificaciones = notificaciones;
		this.permitirVenta = permitirVenta;

		initialize();
		jPanel1.add(getJPanel(), null); //Agrega encabezado
		jPanel.setLocation(0, getPosActual());//Agrega encabezado
		insertarLineas(11);
		llenarDetalles(detallesLista);
		if(apertura)
			llenarPieDePagina();
		else {
			JLabel lineaTexto = new JLabel("Precios sujetos a cambios.");
			lineaTexto.setSize(500, 15);
			lineaTexto.setLocation(0, getPosActual());
			lineaTexto.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
			jPanel1.add(lineaTexto,null);
		}
		this.setVisible(true);
	}

	public int compare(Object o1, Object o2) {
		DetalleServicio detalle1 = (DetalleServicio)o1;
		DetalleServicio detalle2 = (DetalleServicio)o2;
		int d1 = Integer.parseInt(detalle1.getProducto().getCodDepartamento());
		int d2 = Integer.parseInt(detalle2.getProducto().getCodDepartamento());
		
		return (d1-d2);
	}

	/**
	 * @param detallesLista
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	private void llenarDetalles(Vector<DetalleServicio> detallesLista) {
		double total = 0, iva = 0;
		int codDepto = -1;
		Collections.sort(detallesLista,this);
		
		JLabel pagina = new JLabel("Pág. "+paginas);
		pagina.setSize(50, 15);
		pagina.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
		pagina.setLocation(245, 25);
		jPanel.add(pagina,null);

		if(detallesLista.size()==0){
			insertarLinea();
			JTextField mensajeTF = new JTextField("No hay productos en la lista que cumplan este criterio");
			mensajeTF.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
			mensajeTF.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
			mensajeTF.setLocation(0,getPosActual());
			mensajeTF.setSize(400,15);
			getJPanel1().add(mensajeTF);
		}else{		
			for(int i=0;i<detallesLista.size();i++){
				DetalleServicio detalle = (DetalleServicio)detallesLista.get(i);
				int cd = Integer.parseInt(detalle.getProducto().getCodDepartamento());
				if(cd > codDepto){
					codDepto = cd;
					insertarLinea();
					String departamento = MediadorBD.obtenerDescDepto(cd);
					JTextField codDeptoTF = new JTextField("Departamento:  "+cd+"  -  "+departamento);
					codDeptoTF.setFont(new java.awt.Font("Sans Serif", java.awt.Font.BOLD, 10));
					codDeptoTF.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
					codDeptoTF.setLocation(10,getPosActual());
					codDeptoTF.setSize(490,15);
					getJPanel1().add(codDeptoTF);
					insertarLinea();
				}
	
				JTextField codProducto = new JTextField(detalle.getProducto().getCodProducto());
				codProducto.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
				codProducto.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 9));
				codProducto.setLocation(0,getPosActual());
				codProducto.setSize(65,15);
				getJPanel1().add(codProducto);
	
				JTextField descProducto = new JTextField(detalle.getProducto().getDescripcionLarga());
				descProducto.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
				descProducto.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 9));
				descProducto.setLocation(65,getPosActual());
				descProducto.setSize(200,15);
				descProducto.select(0,0);
				getJPanel1().add(descProducto);

				JTextField marcaProducto = new JTextField(detalle.getProducto().getMarca());
				marcaProducto.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
				marcaProducto.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 9));
				marcaProducto.setLocation(271, getPosActual());
				marcaProducto.setSize(59,15);
				marcaProducto.select(0,0);
				getJPanel1().add(marcaProducto);

				if(!apertura){
					double precioTotal = detalle.getPrecioFinal()*(1+detalle.getProducto().getImpuesto().getPorcentaje()/100);
					JTextField precio = new JTextField(df.format(precioTotal));
					precio.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
					precio.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 9));
					precio.setHorizontalAlignment(JTextField.RIGHT);
					precio.setLocation(311,getPosActual());
					precio.setSize(63,15);
					getJPanel1().add(precio);
				}
				
				JTextField quiere = new JTextField(String.valueOf((int)detalle.getCantidad()));
				quiere.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
				quiere.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 9));
				quiere.setHorizontalAlignment(JTextField.RIGHT);
				quiere.setLocation(374,getPosActual());
				quiere.setSize(43,15);
				getJPanel1().add(quiere);
				
				JTextField tiene = new JTextField(String.valueOf((int)detalle.getCantVendidos()+(int)detalle.getCantAbonadosT()));
				tiene.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
				tiene.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 9));
				tiene.setHorizontalAlignment(JTextField.RIGHT);
				tiene.setLocation(417,getPosActual());
				tiene.setSize(36,15);
				getJPanel1().add(tiene);
				
				JTextField necesita = new JTextField(String.valueOf((int)detalle.getCantRestantes()));
				necesita.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
				necesita.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 9));
				necesita.setHorizontalAlignment(JTextField.RIGHT);
				necesita.setLocation(455,getPosActual());
				necesita.setSize(43,15);
				getJPanel1().add(necesita);
		
				JTextField abonos = new JTextField(df.format(detalle.getAbonos()));
				abonos.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
				abonos.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 9));
				abonos.setHorizontalAlignment(JTextField.RIGHT);
				abonos.setLocation(471,getPosActual());
				abonos.setSize(73,15);
				getJPanel1().add(abonos);
				
				total += (detalle.getPrecioFinal()*detalle.getCantidad());
				iva += (detalle.getMontoImpuesto()*detalle.getCantidad());
				
				insertarLinea();
			}
	
			for(int i=0;i<3;i++) {
				insertarLinea();
			}
			insertarLinea();
		}
	}
	
	private void llenarPieDePagina(){
		insertarLineas(2);
		JLabel lineaTexto;

		insertarLineas(2);
		lineaTexto = new JLabel("Condiciones:");
		lineaTexto.setSize(550, 15);
		lineaTexto.setLocation(0, getPosActual());
		lineaTexto.setFont(new java.awt.Font("Sans Serif", java.awt.Font.BOLD, 10));
		jPanel1.add(lineaTexto,null);
		insertarLineas(2);
		lineaTexto = new JLabel("- El titular de la lista es la única persona autorizada para modificar y cerrar la Lista.");
		lineaTexto.setSize(550, 15);
		lineaTexto.setLocation(0, getPosActual());
		lineaTexto.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
		jPanel1.add(lineaTexto,null);
		insertarLinea();
		lineaTexto = new JLabel("- El cierre de la lista no podrá exceder de 45 días contados a partir de la fecha del evento indicada en la Lista");
		lineaTexto.setSize(550, 15);
		lineaTexto.setLocation(0, getPosActual());
		lineaTexto.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
		jPanel1.add(lineaTexto,null);
		insertarLinea();
		lineaTexto = new JLabel("  por el Titular.");
		lineaTexto.setSize(550, 15);
		lineaTexto.setLocation(0, getPosActual());
		lineaTexto.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
		jPanel1.add(lineaTexto,null);
		insertarLinea();
		lineaTexto = new JLabel("- Los abonos a productos que se hayan realizado se convertirán en abonos de dinero.");
		lineaTexto.setSize(550, 15);
		lineaTexto.setLocation(0, getPosActual());
		lineaTexto.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
		jPanel1.add(lineaTexto,null);
		insertarLinea();
		lineaTexto = new JLabel("- Si por cualquier causa, el Titular anula definitivamente la lista, la misma quedará sin efecto, y se entregarán");
		lineaTexto.setSize(550, 15);
		lineaTexto.setLocation(0, getPosActual());
		lineaTexto.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
		jPanel1.add(lineaTexto,null);
		insertarLinea();
		lineaTexto = new JLabel("  bonos regalos equivalentes a los abonos acumulados hasta la fecha de su anulación los cuales pueden ser");
		lineaTexto.setSize(550, 15);
		lineaTexto.setLocation(0, getPosActual());
		lineaTexto.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
		jPanel1.add(lineaTexto,null);
		insertarLinea();
		lineaTexto = new JLabel("  canjeados por productos comercializados en las Tiendas BECO.");
		lineaTexto.setSize(550, 15);
		lineaTexto.setLocation(0, getPosActual());
		lineaTexto.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
		jPanel1.add(lineaTexto,null);
		insertarLinea();
		lineaTexto = new JLabel("- Los productos adquiridos serán despachados sin costo alguno dentro del lapso máximo de 5 días hábiles");
		lineaTexto.setSize(550, 15);
		lineaTexto.setLocation(0, getPosActual());
		lineaTexto.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
		jPanel1.add(lineaTexto,null);
		insertarLinea();
		lineaTexto = new JLabel("  siguientes al cierre de la lista. En caso de que el Titular indique una fecha posterior a ese plazo para el");
		lineaTexto.setSize(550, 15);
		lineaTexto.setLocation(0, getPosActual());
		lineaTexto.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
		jPanel1.add(lineaTexto,null);
		insertarLinea();
		lineaTexto = new JLabel("  despacho de los productos, será por su cuenta el costo del mismo.");
		lineaTexto.setSize(550, 15);
		lineaTexto.setLocation(0, getPosActual());
		lineaTexto.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
		jPanel1.add(lineaTexto,null);
		insertarLineas(2);
		lineaTexto = new JLabel("Firma del Titular: __________________________________");
		lineaTexto.setSize(550, 15);
		lineaTexto.setLocation(10, getPosActual());
		lineaTexto.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
		jPanel1.add(lineaTexto,null);
		insertarLineas(2);
		lineaTexto = new JLabel("C.I./R.I.F.: __________________________________");
		lineaTexto.setSize(550, 15);
		lineaTexto.setLocation(10, getPosActual());
		lineaTexto.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
		jPanel1.add(lineaTexto,null);
	}

	private void insertarLineas(int lineas){
		for(int i=0;i<lineas;i++)
			insertarLinea();
	}
	
	private void insertarLinea(){
		posY += 15;
		if(posY>(810*paginas)-150)
			insertarNuevaPagina();
	}

	private int getPosActual(){
		return posY;
	}
	
	private void insertarNuevaPagina() {
		paginas++;
		posY = (810*(paginas-1)); // Coloca posY en una nueva página
		this.setSize(550,810*(paginas)); // Agrega otra pagina al frame
		this.getJPanel1().setSize(550,810*paginas); // Agrega otra pagina al JPanel1

		jPanel1.add(getJPanel(), null); //Agrega encabezado
		jPanel.setLocation(0, posY);//Agrega encabezado
		posY += 165;
	}
	
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setLocation(-1000,-1000);
		this.setSize(550, 810);
		this.setContentPane(getJContentPane());
	}
	
	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(getJPanel1(), null);
			jContentPane.setBackground(java.awt.Color.white);
			jContentPane.setPreferredSize(new java.awt.Dimension(550,810));
		}
		return jContentPane;
	}

	/**
	 * This method initializes jLabel
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel() {
		//if(jLabel == null) {
			jLabel = new javax.swing.JLabel();
			jLabel.setSize(97, 25);
			jLabel.setText("B E C O");
			jLabel.setFont(new java.awt.Font("Sans Serif", java.awt.Font.BOLD, 24));
			jLabel.setLocation(0, 20);
		//}
		return jLabel;
	}
	/**
	 * This method initializes jLabel2
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel2() {
		//if(jLabel2 == null) {
			jLabel2 = new javax.swing.JLabel();
			jLabel2.setSize(40, 15);
			jLabel2.setText("Fecha: ");
			jLabel2.setLocation(445, 20);
			jLabel2.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
			jLabel2.setPreferredSize(new java.awt.Dimension(50,16));
		//}
		return jLabel2;
	}
	/**
	 * This method initializes fecha
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JTextField getFecha() {
		//if(fechaTextField == null) {
			fechaTextField = new javax.swing.JTextField();
			fechaTextField.setSize(60, 15);
			fechaTextField.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
			fechaTextField.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
			fechaTextField.setLocation(483, 20);			
			fechaTextField.setHorizontalAlignment(JTextField.RIGHT);
			fechaTextField.setText(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
		//}
		return fechaTextField;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel() {
		jPanel = new javax.swing.JPanel();
		jPanel.setLayout(null);
		jPanel.add(getJLabel(), null);
		jPanel.add(getJLabel2(), null);
		jPanel.add(getFecha(), null);
		jPanel.add(getJLabel1(), null);
		jPanel.add(getJLabel4(), null);
		jPanel.add(getJLabel5(), null);
		jPanel.add(getTituloReporte(), null);
		jPanel.add(getJLabel7(), null);
		jPanel.add(getJLabel8(), null);
		jPanel.add(getTienda(), null);
		jPanel.add(getHora(), null);
		jPanel.add(getTitular(), null);
		jPanel.add(getTitularSec(), null);
		jPanel.add(getJLabel12(), null);
		jPanel.add(getJLabel13(), null);
		jPanel.add(getJLabel15(), null);
		jPanel.add(getJLabel16(), null);
		jPanel.add(getJLabel17(), null);
		jPanel.add(getJLabel3(), null);
		jPanel.add(getJLabel9(), null);
		jPanel.add(getTiendaOrigen(), null);
		jPanel.add(getJLabel6(), null);
		jPanel.add(getTipoEventoTF(), null);
		jPanel.add(getJLabel19(), null);
		if(apertura){
			jPanel.add(getJLabel11(), null);
			jPanel.add(getJLabel18(), null);
		} else
			jPanel.add(getJLabel10(), null);
		jPanel.setSize(550, 165);
		jPanel.setBackground(java.awt.Color.white);
		return jPanel;
	}
	/**
	 * This method initializes jLabel1
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel1() {
		//if(jLabel1 == null) {
			jLabel1 = new javax.swing.JLabel();
			jLabel1.setSize(210, 15);
			jLabel1.setText("Servicios al Cliente:  Lista de Regalos");
			jLabel1.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
			jLabel1.setLocation(0, 45);
		//}
		return jLabel1;
	}
	/**
	 * This method initializes jLabel4
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel4() {
		//if(jLabel4 == null) {
			jLabel4 = new javax.swing.JLabel();
			jLabel4.setSize(50, 15);
			jLabel4.setText("Tienda:");
			jLabel4.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
			jLabel4.setLocation(0, 60);
		//}
		return jLabel4;
	}
	/**
	 * This method initializes jLabel5
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel5() {
		//if(jLabel5 == null) {
			jLabel5 = new javax.swing.JLabel();
			jLabel5.setSize(40, 15);
			jLabel5.setText("Hora:");
			jLabel5.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
			jLabel5.setLocation(445, 35);
		//}
		return jLabel5;
	}
	/**
	 * This method initializes jLabel6
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getTituloReporte() {
		//if(tituloReporte == null) {
			tituloReporte = new javax.swing.JLabel();
			tituloReporte.setSize(320, 15);
			tituloReporte.setText("Reporte de Lista de Regalos No Garantizada Nro. "+numLista);
			tituloReporte.setFont(new java.awt.Font("Sans Serif", java.awt.Font.BOLD, 10));
			tituloReporte.setLocation(85, 70);
		//}
		return tituloReporte;
	}
	/**
	 * This method initializes jLabel7
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel7() {
		//if(jLabel7 == null) {
			jLabel7 = new javax.swing.JLabel();
			jLabel7.setSize(155, 15);
			jLabel7.setText("Nombre del Titular:");
			jLabel7.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
			jLabel7.setLocation(0, 100);
		//}
		return jLabel7;
	}
	/**
	 * This method initializes jLabel8
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel8() {
		//if(jLabel8 == null) {
			jLabel8 = new javax.swing.JLabel();
			jLabel8.setSize(155, 15);
			jLabel8.setText("Nombre del Titular Secundario:");
			jLabel8.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
			jLabel8.setLocation(0, 115);
		//}
		return jLabel8;
	}
	/**
	 * This method initializes tienda
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JTextField getTienda() {
		//if(tiendaTextField == null) {
			tiendaTextField = new javax.swing.JTextField();
			tiendaTextField.setSize(160, 15);
			tiendaTextField.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
			tiendaTextField.setLocation(50, 60);
			tiendaTextField.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
			tiendaTextField.setText(String.valueOf(numTienda));
		//}
		return tiendaTextField;
	}

	/**
	 * This method initializes hora
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JTextField getHora() {
		//if(horaTextField == null) {
			horaTextField = new javax.swing.JTextField();
			horaTextField.setSize(60, 15);
			horaTextField.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
			horaTextField.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
			horaTextField.setLocation(483, 35);			
			horaTextField.setHorizontalAlignment(JTextField.RIGHT);
			horaTextField.setText(new SimpleDateFormat("hh:mm a").format(new Date()));
		//}
		return horaTextField;
	}
	/**
	 * This method initializes titular
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JTextField getTitular() {
		//if(titularTextField == null) {
			titularTextField = new javax.swing.JTextField();
			titularTextField.setSize(150, 15);
			titularTextField.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
			titularTextField.setLocation(155, 100);
			titularTextField.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
			titularTextField.setText(titular);
			titularTextField.select(0,0);
		//}
		return titularTextField;
	}
	/**
	 * This method initializes titularSec
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JTextField getTitularSec() {
		//if(titularSecTextField == null) {
			titularSecTextField = new javax.swing.JTextField();
			titularSecTextField.setSize(150, 15);
			titularSecTextField.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
			titularSecTextField.setLocation(155, 115);
			titularSecTextField.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
			if(titularSec!=null && titularSec.trim()!="")
				titularSecTextField.setText(titularSec);
			else titularSecTextField.setText("N/A");
			titularSecTextField.select(0,0);
		//}
		return titularSecTextField;
	}
	/**
	 * This method initializes jLabel12
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel12() {
		//if(jLabel12 == null) {
			jLabel12 = new javax.swing.JLabel();
			jLabel12.setSize(65, 15);
			jLabel12.setText("Producto");
			jLabel12.setLocation(0, 150);
			jLabel12.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
			jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
		//}
		return jLabel12;
	}
	/**
	 * This method initializes jLabel13
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel13() {
		//if(jLabel13 == null) {
			jLabel13 = new javax.swing.JLabel();
			jLabel13.setSize(207, 15);
			jLabel13.setText("Descripción Producto");
			jLabel13.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
			jLabel13.setLocation(65, 150);
			jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
		//}
		return jLabel13;
	}
	/**
	 * This method initializes jLabel15
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel15() {
		//if(jLabel15 == null) {
			jLabel15 = new javax.swing.JLabel();
			jLabel15.setSize(37, 15);
			jLabel15.setText("Tiene");
			jLabel15.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
			jLabel15.setLocation(417, 150);
			jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		//}
		return jLabel15;
	}
	/**
	 * This method initializes jLabel16
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel16() {
		//if(jLabel16 == null) {
			jLabel16 = new javax.swing.JLabel();
			jLabel16.setSize(45, 15);
			jLabel16.setText("Necesita");
			jLabel16.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
			jLabel16.setLocation(453, 150);
			jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		//}
		return jLabel16;
	}
	/**
	 * This method initializes jLabel17
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel17() {
		//if(jLabel17 == null) {
			jLabel17 = new javax.swing.JLabel();
			jLabel17.setSize(45, 15);
			jLabel17.setText("Abonos");
			jLabel17.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
			jLabel17.setLocation(497, 150);
			jLabel17.setPreferredSize(new java.awt.Dimension(50,16));
			jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		//}
		return jLabel17;
	}
	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel1() {
		if(jPanel1 == null) {
			jPanel1 = new javax.swing.JPanel();
			jPanel1.setLayout(null);
			jPanel1.setSize(550, 810);
			jPanel1.setBackground(java.awt.Color.white);
			jPanel1.setLocation(0, 0);
		}
		return jPanel1;
	}
	/**
	 * This method initializes jLabel3
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel3() {
		//if(jLabel3 == null) {
			jLabel3 = new javax.swing.JLabel();
			jLabel3.setSize(90, 15);
			jLabel3.setText("Tienda de ingreso:");
			jLabel3.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
			jLabel3.setLocation(315, 100);
		//}
		return jLabel3;
	}
	/**
	 * This method initializes jLabel9
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel9() {
		//if(jLabel9 == null) {
			jLabel9 = new javax.swing.JLabel();
			jLabel9.setSize(45, 15);
			jLabel9.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
			jLabel9.setText("Quiere");
			jLabel9.setLocation(373, 150);
			jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		//}
		return jLabel9;
	}
	
	/**
	 * This method initializes jLabel9
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel19() {
		//if(jLabel19 == null) {
			jLabel19 = new javax.swing.JLabel();
			jLabel19.setSize(59, 15);
			jLabel19.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
			jLabel19.setText("Marca");
			jLabel19.setLocation(271, 150);
			jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
		//}
		return jLabel19;
	}
	
	/**
	 * This method initializes jLabel9
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel10() {
		//if(jLabel10 == null) {
			jLabel10 = new javax.swing.JLabel();
			jLabel10.setSize(45, 15);
			jLabel10.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
			jLabel10.setText("Precio");
			jLabel10.setLocation(329, 150);
			jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		//}
		return jLabel10;
	}
	
	/**
	 * This method initializes tiendaOrigenTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private javax.swing.JTextField getTiendaOrigen() {
		//if(tiendaOrigenTextField == null) {
			tiendaOrigenTextField = new javax.swing.JTextField();
			tiendaOrigenTextField.setSize(100, 15);
			tiendaOrigenTextField.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
			tiendaOrigenTextField.setLocation(405, 100);
			tiendaOrigenTextField.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
			tiendaOrigenTextField.setText(String.valueOf(tiendaOrigen));
		//}
		return tiendaOrigenTextField;
	}
	/**
	 * This method initializes jLabel6
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel6() {
		//if(jLabel6 == null) {
			jLabel6 = new javax.swing.JLabel();
			jLabel6.setSize(90, 15);
			jLabel6.setText("Tipo de Evento:");
			jLabel6.setLocation(315, 115);
			jLabel6.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
		//}
		return jLabel6;
	}
	/**
	 * This method initializes tipoEventoTF
	 * 
	 * @return javax.swing.JTextField
	 */
	private javax.swing.JTextField getTipoEventoTF() {
		//if(tipoEventoTF == null) {
			tipoEventoTF = new javax.swing.JTextField();
			tipoEventoTF.setSize(100, 15);
			tipoEventoTF.setLocation(405, 115);
			tipoEventoTF.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
			tipoEventoTF.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
			tipoEventoTF.setText(String.valueOf(tipoEvento));
		//}
		return tipoEventoTF;
	}
	/**
	 * This method initializes jLabel10
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel11() {
		//if(jLabel11 == null) {
			jLabel11 = new javax.swing.JLabel();
			jLabel11.setSize(200, 15);
			jLabel11.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
			jLabel11.setText("Permitir que se lleven los regalos: "+permitirVenta);
			jLabel11.setLocation(0, 130);
		//}
		return jLabel11;
	}
	/**
	 * This method initializes jLabel11
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel18() {
		//if(jLabel18 == null) {
			jLabel18 = new javax.swing.JLabel();
			jLabel18.setSize(150, 15);
			jLabel18.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
			jLabel18.setText("Notificaciones: "+notificaciones);
			jLabel18.setLocation(315, 130);
		//}
		return jLabel18;
	}
}

/*
* En esta clase se realizaron modificaciones referentes a la migración a java 1.6 por jperez
* Sólo se parametrizó el tipo de dato de 'Comparator'
* Fecha: agosto 2011
*/
class ReporteNoVendidos extends JWindow implements Comparator<Object> {

	private static final long serialVersionUID = 1L;

	private DecimalFormat df = new DecimalFormat("#,##0.00");
	
	private String fechaActual;
	private String horaActual;
	private String tipoEvento;
	private int numTienda;
	private int tiendaOrigen;
	private int numLista;
	private String titular;
	private String titularSec;
	private int posY = 0, paginas = 1;
	private char tipoLista;
	
	private javax.swing.JPanel jContentPane = null;

	private javax.swing.JLabel jLabel = null;
	private javax.swing.JLabel jLabel2 = null;
	private javax.swing.JLabel jLabel1 = null;
	private javax.swing.JLabel jLabel4 = null;
	private javax.swing.JLabel jLabel5 = null;
	private javax.swing.JLabel tituloReporte = null;
	private javax.swing.JLabel jLabel7 = null;
	private javax.swing.JLabel jLabel8 = null;
	private javax.swing.JTextField fechaTextField = null;
	private javax.swing.JTextField tiendaTextField = null;
	private javax.swing.JTextField horaTextField = null;
	private javax.swing.JTextField titularTextField = null;
	private javax.swing.JTextField titularSecTextField = null;
	private javax.swing.JTextField tiendaOrigenTextField = null;
	private javax.swing.JLabel jLabel12 = null;
	private javax.swing.JLabel jLabel13 = null;
	private javax.swing.JLabel jLabel16 = null;
	private javax.swing.JLabel jLabel17 = null;
	private javax.swing.JLabel jLabel18 = null;
	private javax.swing.JPanel jPanel1 = null;
	private javax.swing.JLabel jLabel3 = null;
	private javax.swing.JLabel jLabel9 = null;
	private javax.swing.JTextField tipoEventoTF = null;
	private javax.swing.JPanel jPanel = null;
	private javax.swing.JLabel jLabel10 = null;
	private javax.swing.JLabel jLabel11 = null;
	/**
	 * This is the default constructor
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public ReporteNoVendidos(String fechaActual,String horaActual,String tipoEvento,int numTienda,int numLista, int tiendaOrigen,
				String titular,String titularSec,Date fechaEvento,Vector<DetalleServicio> detalleNoVendidos,char tipoLista) {
		super();

		this.fechaActual = fechaActual;
		this.horaActual = horaActual;
		this.tipoEvento = tipoEvento;
		this.numTienda = numTienda;
		this.tiendaOrigen = tiendaOrigen;
		this.numLista = numLista;
		this.titular = titular;
		this.titularSec = titularSec;
		this.tipoLista=tipoLista;

		initialize();
		jPanel1.add(getJPanel(), null); //Agrega encabezado
		jPanel.setLocation(0, getPosActual());//Agrega encabezado
		insertarLineas(11);
		llenarDetalles(detalleNoVendidos);
		llenarPieDePagina();
		this.setVisible(true);
	}

	public int compare(Object o1, Object o2) {
		DetalleServicio detalle1 = (DetalleServicio)o1;
		DetalleServicio detalle2 = (DetalleServicio)o2;
		int d1 = Integer.parseInt(detalle1.getProducto().getCodDepartamento());
		int d2 = Integer.parseInt(detalle2.getProducto().getCodDepartamento());
		
		return (d1-d2);
	}

	/**
	 * @param detallesLista
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	private void llenarDetalles(Vector<DetalleServicio> detalleNoVendidos) {
		double total = 0,iva = 0;
		Collections.sort(detalleNoVendidos,this);
		int codDepto = -1;

		JLabel pagina = new JLabel("Pág. "+paginas);
		pagina.setSize(50, 15);
		pagina.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
		pagina.setLocation(245, 25);
		jPanel.add(pagina,null);
		
		if(detalleNoVendidos.size()==0){
			insertarLinea();
			JTextField mensajeTF = new JTextField("No hay productos en la lista que cumplan este criterio");
			mensajeTF.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
			mensajeTF.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
			mensajeTF.setLocation(0,getPosActual());
			mensajeTF.setSize(400,15);
			getJPanel1().add(mensajeTF);
		}else{
			for(int i=0;i<detalleNoVendidos.size();i++) {
				DetalleServicio detalle = (DetalleServicio)detalleNoVendidos.get(i);
				int cd = Integer.parseInt(detalle.getProducto().getCodDepartamento());
				if(cd > codDepto){
					codDepto = cd;
					insertarLinea();
					String departamento = MediadorBD.obtenerDescDepto(cd);
					JTextField codDeptoTF = new JTextField("Departamento:  "+cd+"  -  "+departamento);
					codDeptoTF.setFont(new java.awt.Font("Sans Serif", java.awt.Font.BOLD, 9));
					codDeptoTF.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
					codDeptoTF.setLocation(30,getPosActual());
					codDeptoTF.setSize(470,15);
					getJPanel1().add(codDeptoTF);
					insertarLinea();
				}
	
				JTextField codProducto = new JTextField(detalle.getProducto().getCodProducto());
				codProducto.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
				codProducto.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 9));
				codProducto.setLocation(0,getPosActual());
				codProducto.setSize(73,15);
				getJPanel1().add(codProducto);
	
				JTextField descProducto = new JTextField(detalle.getProducto().getDescripcionCorta());
				descProducto.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
				descProducto.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 9));
				descProducto.setLocation(75,getPosActual());
				descProducto.setSize(133,15);
				descProducto.select(0,0);
				getJPanel1().add(descProducto);
			
				JTextField cantidad = new JTextField(String.valueOf((int)detalle.getCantRestantes()));
				cantidad.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
				cantidad.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 9));
				cantidad.setHorizontalAlignment(JTextField.RIGHT);
				cantidad.setLocation(210,getPosActual());
				cantidad.setSize(33,15);
				getJPanel1().add(cantidad);
	
				double precio = detalle.getProducto().getPrecioRegular();
				JTextField precioTF = new JTextField(df.format(precio));
				precioTF.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
				precioTF.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 9));
				precioTF.setHorizontalAlignment(JTextField.RIGHT);
				precioTF.setLocation(245,getPosActual());
				precioTF.setSize(73,15);
				getJPanel1().add(precioTF);

				double precioVenta = detalle.getProducto().getPrecioRegular()*(1+detalle.getProducto().getImpuesto().getPorcentaje()/100);
				JTextField precioVentaTF = new JTextField(df.format(precioVenta));
				precioVentaTF.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
				precioVentaTF.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 9));
				precioVentaTF.setHorizontalAlignment(JTextField.RIGHT);
				precioVentaTF.setLocation(320,getPosActual());
				precioVentaTF.setSize(73,15);
				getJPanel1().add(precioVentaTF);
		
				double subTotal = detalle.getCantRestantes()*precioVenta;
				JTextField subTotalTextField = new JTextField(df.format(subTotal));
				subTotalTextField.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
				subTotalTextField.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 9));
				subTotalTextField.setHorizontalAlignment(JTextField.RIGHT);
				subTotalTextField.setLocation(395,getPosActual());
				subTotalTextField.setSize(73,15);
				getJPanel1().add(subTotalTextField);
			
				JTextField abonos = new JTextField(df.format(detalle.getAbonos()));
				abonos.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
				abonos.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 9));
				abonos.setHorizontalAlignment(JTextField.RIGHT);
				abonos.setLocation(470,getPosActual());
				abonos.setSize(78,15);
				getJPanel1().add(abonos);
				
				total += detalle.getProducto().getPrecioRegular()*(detalle.getCantPedidos()-detalle.getCantVendidos()-detalle.getCantAbonadosT());
				iva += (detalle.getProducto().getPrecioRegular()* ((detalle.getCantPedidos()-detalle.getCantVendidos()-detalle.getCantAbonadosT())))*(detalle.getProducto().getImpuesto().getPorcentaje()/100);
				
				insertarLinea();
			}
	
			for(int i=0;i<3;i++){
				insertarLinea();
				switch(i){
					case 0:
					JTextField izquierda = new JTextField("SUBTOTAL");
					izquierda.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
					izquierda.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 9));
					izquierda.setHorizontalAlignment(JTextField.RIGHT);
					izquierda.setLocation(310,getPosActual());
					izquierda.setSize(110,15);
					getJPanel1().add(izquierda);
	
					JTextField derecha = new JTextField(df.format(total));
					derecha.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
					derecha.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 9));
					derecha.setHorizontalAlignment(JTextField.RIGHT);
					derecha.setLocation(430,getPosActual());
					derecha.setSize(65,15);
					getJPanel1().add(derecha);
					break;
					case 1:
					JTextField izquierda1 = new JTextField("IVA");
					izquierda1.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
					izquierda1.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 9));
					izquierda1.setHorizontalAlignment(JTextField.RIGHT);
					izquierda1.setLocation(310,getPosActual());
					izquierda1.setSize(110,15);
					getJPanel1().add(izquierda1);
		
					JTextField derecha1 = new JTextField(df.format(iva));
					derecha1.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
					derecha1.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 9));
					derecha1.setHorizontalAlignment(JTextField.RIGHT);
					derecha1.setLocation(430,getPosActual());
					derecha1.setSize(65,15);
					getJPanel1().add(derecha1);
					break;
					case 2:
					JTextField izquierda2 = new JTextField("TOTAL");
					izquierda2.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
					izquierda2.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 9));
					izquierda2.setHorizontalAlignment(JTextField.RIGHT);
					izquierda2.setLocation(310,getPosActual());
					izquierda2.setSize(110,15);
					getJPanel1().add(izquierda2);
			
					JTextField derecha2 = new JTextField(df.format(total+iva));
					derecha2.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
					derecha2.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 9));
					derecha2.setHorizontalAlignment(JTextField.RIGHT);
					derecha2.setLocation(430,getPosActual());
					derecha2.setSize(65,15);
					getJPanel1().add(derecha2);
					break;
				}
			}
			insertarLinea();
		}
	}
		
	private void llenarPieDePagina(){
		insertarLineas(3);
		JLabel lineaTexto;
		lineaTexto = new JLabel("- El titular de la lista es la única persona autorizada para modificar y cerrar la Lista.");
		lineaTexto.setSize(550, 15);
		lineaTexto.setLocation(0, getPosActual());
		lineaTexto.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
		jPanel1.add(lineaTexto,null);
		insertarLinea();
		lineaTexto = new JLabel("- Precios vigentes al "+fechaActual);
		lineaTexto.setSize(550, 15);
		lineaTexto.setLocation(0, getPosActual());
		lineaTexto.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
		jPanel1.add(lineaTexto,null);
		if(tipoLista=='N'){
			insertarLinea();
			lineaTexto = new JLabel("- No se garantiza el precio de los productos seleccionados ni su existencia para el momento del cierre de la lista.");
			lineaTexto.setSize(550, 15);
			lineaTexto.setLocation(0, getPosActual());
			lineaTexto.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
			jPanel1.add(lineaTexto,null);
		}
	}

	private void insertarLineas(int lineas){
		for(int i=0;i<lineas;i++)
			insertarLinea();
	}
	
	private void insertarLinea(){
		posY += 15;
		if(posY>(810*paginas)-150)
			insertarNuevaPagina();
	}

	private int getPosActual(){
		return posY;
	}
	
	private void insertarNuevaPagina() {
		paginas++;
		posY = (810*(paginas-1)); // Coloca posY en una nueva página
		this.setSize(550,810*(paginas)); // Agrega otra pagina al frame
		this.getJPanel1().setSize(550,810*paginas); // Agrega otra pagina al JPanel1

		jPanel1.add(getJPanel(), null); //Agrega encabezado
		jPanel.setLocation(0, posY);//Agrega encabezado
		posY += 165;
	}
	
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setLocation(-1000,-1000);
		this.setSize(550, 810);
		this.setContentPane(getJContentPane());
	}
	
	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(getJPanel1(), null);
			jContentPane.setBackground(java.awt.Color.white);
			jContentPane.setPreferredSize(new java.awt.Dimension(550,810));
		}
		return jContentPane;
	}

	/**
	 * This method initializes jLabel
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel() {
		//if(jLabel == null) {
			jLabel = new javax.swing.JLabel();
			jLabel.setSize(97, 25);
			jLabel.setText("B E C O");
			jLabel.setFont(new java.awt.Font("Sans Serif", java.awt.Font.BOLD, 24));
			jLabel.setLocation(0, 20);
		//}
		return jLabel;
	}
	/**
	 * This method initializes jLabel2
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel2() {
		//if(jLabel2 == null) {
			jLabel2 = new javax.swing.JLabel();
			jLabel2.setSize(50, 15);
			jLabel2.setText("Fecha: ");
			jLabel2.setLocation(380, 20);
			jLabel2.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
			jLabel2.setPreferredSize(new java.awt.Dimension(50,16));
		//}
		return jLabel2;
	}
	/**
	 * This method initializes fecha
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JTextField getFecha() {
		//if(fechaTextField == null) {
			fechaTextField = new javax.swing.JTextField();
			fechaTextField.setSize(70, 15);
			fechaTextField.setText("");
			fechaTextField.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
			fechaTextField.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
			fechaTextField.setLocation(430, 20);
			fechaTextField.setText(fechaActual);
		//}
		return fechaTextField;
	}

	/**
	 * This method initializes jLabel1
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel1() {
		//if(jLabel1 == null) {
			jLabel1 = new javax.swing.JLabel();
			jLabel1.setSize(210, 15);
			jLabel1.setText("Servicios al Cliente:  Lista de Regalos");
			jLabel1.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
			jLabel1.setLocation(0, 45);
		//}
		return jLabel1;
	}
	/**
	 * This method initializes jLabel4
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel4() {
		//if(jLabel4 == null) {
			jLabel4 = new javax.swing.JLabel();
			jLabel4.setSize(50, 15);
			jLabel4.setText("Tienda:");
			jLabel4.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
			jLabel4.setLocation(0, 60);
		//}
		return jLabel4;
	}
	/**
	 * This method initializes jLabel5
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel5() {
		//if(jLabel5 == null) {
			jLabel5 = new javax.swing.JLabel();
			jLabel5.setSize(50, 15);
			jLabel5.setText("Hora:");
			jLabel5.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
			jLabel5.setLocation(380, 35);
		//}
		return jLabel5;
	}
	/**
	 * This method initializes jLabel6
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getTituloReporte() {
		//if(jLabel6 == null) {
			tituloReporte = new javax.swing.JLabel();
			tituloReporte.setSize(350, 15);
			tituloReporte.setText("Reporte de Productos No Vendidos de Lista de Regalos No. "+numLista);
			tituloReporte.setFont(new java.awt.Font("Sans Serif", java.awt.Font.BOLD, 10));
			tituloReporte.setLocation(80, 70);
		//}
		return tituloReporte;
	}
	/**
	 * This method initializes jLabel7
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel7() {
		//if(jLabel7 == null) {
			jLabel7 = new javax.swing.JLabel();
			jLabel7.setSize(135, 15);
			jLabel7.setText("Nombre Titular:");
			jLabel7.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
			jLabel7.setLocation(0, 95);
		//}
		return jLabel7;
	}
	/**
	 * This method initializes jLabel8
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel8() {
		//if(jLabel8 == null) {
			jLabel8 = new javax.swing.JLabel();
			jLabel8.setSize(135, 15);
			jLabel8.setText("Nombre Titular Secundario:");
			jLabel8.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
			jLabel8.setLocation(0, 110);
		//}
		return jLabel8;
	}
	/**
	 * This method initializes tienda
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JTextField getTienda() {
		//if(tiendaTextField == null) {
			tiendaTextField = new javax.swing.JTextField();
			tiendaTextField.setSize(160, 15);
			tiendaTextField.setText("");
			tiendaTextField.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
			tiendaTextField.setLocation(50, 60);
			tiendaTextField.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
			tiendaTextField.setText(String.valueOf(numTienda));
		//}
		return tiendaTextField;
	}

	/**
	 * This method initializes hora
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JTextField getHora() {
		//if(horaTextField == null) {
			horaTextField = new javax.swing.JTextField();
			horaTextField.setSize(70, 15);
			horaTextField.setText("");
			horaTextField.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
			horaTextField.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
			horaTextField.setLocation(430, 35);
			horaTextField.setText(horaActual);
		//}
		return horaTextField;
	}
	/**
	 * This method initializes titular
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JTextField getTitular() {
		titularTextField = new javax.swing.JTextField();
		titularTextField.setSize(150, 15);
		titularTextField.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
		titularTextField.setLocation(135, 95);
		titularTextField.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
		titularTextField.setText(titular);
		titularTextField.select(0, 0);
		return titularTextField;
	}
	/**
	 * This method initializes titularSec
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JTextField getTitularSec() {
		titularSecTextField = new javax.swing.JTextField();
		titularSecTextField.setSize(150, 15);
		titularSecTextField.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
		titularSecTextField.setLocation(135, 110);
		titularSecTextField.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
		titularSecTextField.setText(titularSec);
		titularSecTextField.select(0, 0);
		return titularSecTextField;
	}
	/**
	 * This method initializes jLabel12
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel12() {
		//if(jLabel12 == null) {
			jLabel12 = new javax.swing.JLabel();
			jLabel12.setSize(75, 15);
			jLabel12.setText(" Producto");
			jLabel12.setLocation(0, 150);
			jLabel12.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
			jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
		//}
		return jLabel12;
	}
	/**
	 * This method initializes jLabel13
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel13() {
		//if(jLabel13 == null) {
			jLabel13 = new javax.swing.JLabel();
			jLabel13.setSize(135, 15);
			jLabel13.setText(" Descripción Producto");
			jLabel13.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
			jLabel13.setLocation(75, 150);
			jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
		//}
		return jLabel13;
	}
	/**
	 * This method initializes jLabel16
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel16() {
		//if(jLabel16 == null) {
			jLabel16 = new javax.swing.JLabel();
			jLabel16.setSize(35, 15);
			jLabel16.setText("Cant.");
			jLabel16.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
			jLabel16.setLocation(210, 150);
			jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		//}
		return jLabel16;
	}
	/**
	 * This method initializes jLabel17
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel17() {
		//if(jLabel17 == null) {
			jLabel17 = new javax.swing.JLabel();
			jLabel17.setSize(75, 15);
			jLabel17.setText("Precio");
			jLabel17.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
			jLabel17.setLocation(245, 150);
			jLabel17.setPreferredSize(new java.awt.Dimension(50,16));
			jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		//}
		return jLabel17;
	}
	/**
	 * This method initializes jLabel18
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel18() {
		//if(jLabel18 == null) {
			jLabel18 = new javax.swing.JLabel();
			jLabel18.setSize(75, 15);
			jLabel18.setText("Sub-Total");
			jLabel18.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
			jLabel18.setLocation(395, 150);
			jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		//}
		return jLabel18;
	}
	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel1() {
		if(jPanel1 == null) {
			jPanel1 = new javax.swing.JPanel();
			jPanel1.setLayout(null);
			jPanel1.setSize(550, 810);
			jPanel1.setBackground(java.awt.Color.white);
			jPanel1.setLocation(0, 0);
		}
		return jPanel1;
	}
	/**
	 * This method initializes jLabel3
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel3() {
		jLabel3 = new javax.swing.JLabel();
		jLabel3.setSize(100, 15);
		jLabel3.setText("Tienda de ingreso:");
		jLabel3.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
		jLabel3.setLocation(310, 95);
		return jLabel3;
	}
	/**
	 * This method initializes tiendaOrigen
	 * 
	 * @return javax.swing.JTextField
	 */
	private javax.swing.JTextField getTiendaOrigen() {
		tiendaOrigenTextField = new javax.swing.JTextField();
		tiendaOrigenTextField.setSize(70, 15);
		tiendaOrigenTextField.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
		tiendaOrigenTextField.setLocation(410, 95);
		tiendaOrigenTextField.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
		tiendaOrigenTextField.setText(String.valueOf(tiendaOrigen));
		return tiendaOrigenTextField;
	}
	/**
	 * This method initializes jLabel9
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel9() {
		jLabel9 = new javax.swing.JLabel();
		jLabel9.setSize(80, 15);
		jLabel9.setText(" Abonos");
		jLabel9.setLocation(470, 150);
		jLabel9.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
		jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		return jLabel9;
	}
	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel() {
		jPanel = new javax.swing.JPanel();
		jPanel.setLayout(null);
		jPanel.add(getJLabel(), null);
		jPanel.add(getJLabel1(), null);
		jPanel.setBackground(java.awt.Color.white);
		jPanel.add(getJLabel18(), null);
		jPanel.add(getJLabel17(), null);
		jPanel.add(getJLabel16(), null);
		jPanel.add(getJLabel13(), null);
		jPanel.add(getJLabel12(), null);
		jPanel.add(getTienda(), null);
		jPanel.add(getTitular(), null);
		jPanel.add(getHora(), null);
		jPanel.add(getJLabel9(), null);
		jPanel.add(getJLabel8(), null);
		jPanel.add(getJLabel7(), null);
		jPanel.add(getTituloReporte(), null);
		jPanel.add(getJLabel5(), null);
		jPanel.add(getJLabel4(), null);
		jPanel.add(getJLabel3(), null);
		jPanel.add(getJLabel2(), null);
		jPanel.add(getTitularSec(), null);
		jPanel.add(getFecha(), null);
		jPanel.add(getTiendaOrigen(), null);
		jPanel.add(getTipoEventoTF(), null);
		jPanel.add(getJLabel10(), null);
		jPanel.add(getJLabel11(), null);
		jPanel.setLocation(0, 0);
		jPanel.setSize(550, 165);
		return jPanel;
	}
	/**
	 * This method initializes tipoEventoTF
	 * 
	 * @return javax.swing.JTextField
	 */	
	private javax.swing.JTextField getTipoEventoTF() {
		tipoEventoTF = new javax.swing.JTextField();
		tipoEventoTF.setSize(110, 15);
		tipoEventoTF.setLocation(410, 110);
		tipoEventoTF.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
		tipoEventoTF.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
		tipoEventoTF.setText(String.valueOf(tipoEvento));
		return tipoEventoTF;
	}
	/**
	 * This method initializes jLabel10
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel10() {
		jLabel10 = new javax.swing.JLabel();
		jLabel10.setSize(100, 15);
		jLabel10.setText("Tipo de Evento:");
		jLabel10.setLocation(310, 110);
		jLabel10.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
		return jLabel10;
	}
	/**
	 * This method initializes jLabel11
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel11() {
		if(jLabel11 == null) {
			jLabel11 = new javax.swing.JLabel();
			jLabel11.setSize(75, 15);
			jLabel11.setText("Precio c/IVA");
			jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
			jLabel11.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
			jLabel11.setLocation(320, 150);
		}
		return jLabel11;
	}
}

/*
* En esta clase se realizaron modificaciones referentes a la migración a java 1.6 por jperez
* Sólo se parametrizó el tipo de dato de 'Comparator'
* Fecha: agosto 2011
*/
class ReporteVendidos extends JWindow implements Comparator<Object> {

	private static final long serialVersionUID = 1L;

	private DecimalFormat df = new DecimalFormat("#,##0.00");
	
	private String fechaActual;
	private String horaActual;
	private String tipoEvento;
	private int numTienda;
	private int tiendaOrigen;
	private int numLista;
	private String titular;
	private String titularSec;
	private int posY = 0;
	private int paginas = 1;
	private char tipoLista;
	
	private javax.swing.JPanel jContentPane = null;

	private javax.swing.JLabel jLabel = null;
	private javax.swing.JLabel jLabel2 = null;
	private javax.swing.JPanel jPanel = null;
	private javax.swing.JLabel jLabel1 = null;
	private javax.swing.JLabel jLabel4 = null;
	private javax.swing.JLabel jLabel5 = null;
	private javax.swing.JLabel tituloReporte = null;
	private javax.swing.JLabel jLabel7 = null;
	private javax.swing.JLabel jLabel8 = null;
	private javax.swing.JTextField tiendaTextField = null;
	private javax.swing.JTextField fechaTextField = null;
	private javax.swing.JTextField horaTextField = null;
	private javax.swing.JTextField titularTextField = null;
	private javax.swing.JTextField titularSecTextField = null;
	private javax.swing.JTextField tiendaOrigenTextField = null;
	private javax.swing.JLabel jLabel12 = null;
	private javax.swing.JLabel jLabel13 = null;
	private javax.swing.JLabel jLabel14 = null;
	private javax.swing.JLabel jLabel15 = null;
	private javax.swing.JLabel jLabel16 = null;
	private javax.swing.JLabel jLabel17 = null;
	private javax.swing.JLabel jLabel18 = null;
	private javax.swing.JPanel jPanel1 = null;
	private javax.swing.JLabel jLabel3 = null;
	private javax.swing.JLabel jLabel10 = null;
	private javax.swing.JLabel jLabel9 = null;
	private javax.swing.JTextField tipoEventoTF = null;

	
	/**
	 * This is the default constructor
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public ReporteVendidos(String fechaActual,String horaActual,String tipoEvento,int numTienda,int numLista,int tiendaOrigen,
				String titular,String titularSec,Date fechaEvento,Vector<OperacionLR> operacionesVendidos,
				Vector<OperacionLR> operacionesAbonosT, Vector<OperacionLR> operacionesAbonosP,Vector<OperacionLR> operacionesAbonosL, char tipoLista) {
		super();

		this.fechaActual = fechaActual;
		this.horaActual = horaActual;
		this.tipoEvento = tipoEvento;
		this.numTienda = numTienda;
		this.numLista = numLista;
		this.titular = titular;
		this.titularSec = titularSec;
		this.tiendaOrigen = tiendaOrigen;
		this.tipoLista = tipoLista;

		initialize();
		jPanel1.add(getJPanel(), null); //Agrega encabezado
		jPanel.setLocation(0, getPosActual());//Agrega encabezado
		insertarLineas(11);
		llenarDetalles(operacionesVendidos, "Productos Vendidos", false);
		llenarDetalles(operacionesAbonosT, "Productos con Abono Total", true);
		llenarDetalles(operacionesAbonosP, "Abonos a Productos", true);
		llenarDetalles(operacionesAbonosL, "Abonos a Lista", true);
		llenarPieDePagina();
		this.setVisible(true);
	}

	public int compare(Object o1, Object o2) {
		OperacionLR detalle1 = (OperacionLR)o1;
		OperacionLR detalle2 = (OperacionLR)o2;
		int d1 = detalle1.getNumTransaccion();
		int d2 = detalle2.getNumTransaccion();
		
		return (d1-d2);
	}

	/**
	 * @param detallesLista
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	private void llenarDetalles(Vector<OperacionLR> detalleOperaciones, String titulo, boolean usarNumOperacion) {
		double total = 0,iva = 0;
		Collections.sort(detalleOperaciones,this);

		JLabel pagina = new JLabel("Pág. "+paginas);
		pagina.setSize(50, 15);
		pagina.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
		pagina.setLocation(245, 25);
		jPanel.add(pagina,null);
		
		insertarLinea();
		JTextField tituloTF = new JTextField(titulo);
		tituloTF.setFont(new java.awt.Font("Sans Serif", java.awt.Font.BOLD, 10));
		tituloTF.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
		tituloTF.setLocation(30,getPosActual());
		tituloTF.setSize(470,15);
		getJPanel1().add(tituloTF);
		insertarLinea();
		
		if(detalleOperaciones.size()==0){
			JTextField mensajeTF = new JTextField("No hay productos en la lista que cumplan este criterio");
			mensajeTF.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
			mensajeTF.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
			mensajeTF.setLocation(0,getPosActual());
			mensajeTF.setSize(400,15);
			getJPanel1().add(mensajeTF);
			insertarLinea();
		}else{
			for(int i=0;i<detalleOperaciones.size();i++){
				OperacionLR operacion = (OperacionLR)detalleOperaciones.get(i);
				JTextField numTransaccionTF = null;
				if(usarNumOperacion) numTransaccionTF = new JTextField(String.valueOf(operacion.getNumOperacion()));
				else numTransaccionTF = new JTextField(String.valueOf(operacion.getNumTransaccion()));
				numTransaccionTF.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
				numTransaccionTF.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 9));
				numTransaccionTF.setLocation(0,getPosActual());
				numTransaccionTF.setSize(28,15);
				if(!numTransaccionTF.getText().trim().equals("0"))
					getJPanel1().add(numTransaccionTF);
	
				JTextField codProductoTF = new JTextField(operacion.getCodProducto());
				codProductoTF.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
				codProductoTF.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 9));
				codProductoTF.setLocation(30,getPosActual());
				codProductoTF.setHorizontalAlignment(JTextField.LEFT);
				codProductoTF.setSize(68,15);
				getJPanel1().add(codProductoTF);
	
				JTextField descripcionTF = new JTextField(operacion.getDescripcionProducto());
				descripcionTF.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
				descripcionTF.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 9));
				descripcionTF.setLocation(100,getPosActual());
				descripcionTF.setSize(137,15);
				descripcionTF.select(0,0);
				getJPanel1().add(descripcionTF);
	
				JTextField nombreTF = new JTextField(operacion.getNomCliente());
				nombreTF.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
				nombreTF.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 9));
				nombreTF.setLocation(242,getPosActual());
				nombreTF.setSize(118,15);
				nombreTF.select(0,0);
				getJPanel1().add(nombreTF);
				
				JTextField numTiendaTF = new JTextField(String.valueOf(operacion.getNumTienda()));
				numTiendaTF.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
				numTiendaTF.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 9));
				numTiendaTF.setHorizontalAlignment(JTextField.CENTER);
				numTiendaTF.setLocation(360,getPosActual());
				numTiendaTF.setSize(23,15);
				getJPanel1().add(numTiendaTF);
				
				int cantidad = operacion.getCantidad();
				JTextField cantidadTF = new JTextField(String.valueOf((int)cantidad));
				cantidadTF.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
				cantidadTF.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 9));
				cantidadTF.setHorizontalAlignment(JTextField.CENTER);
				cantidadTF.setLocation(385,getPosActual());
				cantidadTF.setSize(23,15);
				getJPanel1().add(cantidadTF);
				
				double precioVenta = operacion.getMontoBase();
				JTextField precioTF = new JTextField(df.format(precioVenta));
				precioTF.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
				precioTF.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 9));
				precioTF.setHorizontalAlignment(JTextField.RIGHT);
				precioTF.setLocation(410,getPosActual());
				precioTF.setSize(68,15);
				getJPanel1().add(precioTF);
	
				double subtotal = operacion.getMontoBase() * operacion.getCantidad();
				JTextField subtotalTF = new JTextField(df.format(subtotal));
				subtotalTF.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
				subtotalTF.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 9));
				subtotalTF.setHorizontalAlignment(JTextField.RIGHT);
				subtotalTF.setLocation(480,getPosActual());
				subtotalTF.setSize(68,15);
				getJPanel1().add(subtotalTF);
				
				total += subtotal;
				iva += operacion.getMontoImpuesto() * operacion.getCantidad();
			
				insertarLinea();
				
				if((i==detalleOperaciones.size()-1) || ((OperacionLR)detalleOperaciones.get(i+1)).getNumTransaccion()!=operacion.getNumTransaccion()
						|| ((OperacionLR)detalleOperaciones.get(i+1)).getNumOperacion()!=operacion.getNumOperacion()){
					/* Dedicatoria */
					String dedicatoria = operacion.getDedicatoria()==null
									   ? ""
									   : operacion.getDedicatoria();
					JTextField dedicatoriaTF = new JTextField("Dedicatoria: "+dedicatoria);
					dedicatoriaTF.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
					dedicatoriaTF.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 9));
					dedicatoriaTF.setHorizontalAlignment(JTextField.LEFT);
					dedicatoriaTF.setLocation(30,getPosActual());
					dedicatoriaTF.setSize(470,15);
					getJPanel1().add(dedicatoriaTF);
					/* Fin Dedicatoria */
					insertarLinea();
				}
			}
			for(int i=0;i<3;i++){
				switch(i){
					case 0:
						insertarLinea();
						JTextField izquierdaTF = new JTextField("SUBTOTAL");
						izquierdaTF.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
						izquierdaTF.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 9));
						izquierdaTF.setHorizontalAlignment(JTextField.RIGHT);
						izquierdaTF.setLocation(310,getPosActual());
						izquierdaTF.setSize(110,15);
						getJPanel1().add(izquierdaTF);
	
						JTextField derechaTF = new JTextField(df.format(total));
						derechaTF.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
						derechaTF.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 9));
						derechaTF.setHorizontalAlignment(JTextField.RIGHT);
						derechaTF.setLocation(430,getPosActual());
						derechaTF.setSize(70,15);
						getJPanel1().add(derechaTF);
						break;
					case 1:
						if(iva>0){
							insertarLinea();
							JTextField izquierdaTF1 = new JTextField("IVA");
							izquierdaTF1.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
							izquierdaTF1.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 9));
							izquierdaTF1.setHorizontalAlignment(JTextField.RIGHT);
							izquierdaTF1.setLocation(310,getPosActual());
							izquierdaTF1.setSize(110,15);
							getJPanel1().add(izquierdaTF1);
			
							JTextField derechaTF1 = new JTextField(df.format(iva));
							derechaTF1.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
							derechaTF1.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 9));
							derechaTF1.setHorizontalAlignment(JTextField.RIGHT);
							derechaTF1.setLocation(430,getPosActual());
							derechaTF1.setSize(70,15);
							getJPanel1().add(derechaTF1);
						}
						break;
					case 2:
						insertarLinea();
						JTextField izquierdaTF2 = new JTextField("TOTAL");
						izquierdaTF2.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
						izquierdaTF2.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 9));
						izquierdaTF2.setHorizontalAlignment(JTextField.RIGHT);
						izquierdaTF2.setLocation(310,getPosActual());
						izquierdaTF2.setSize(110,15);
						getJPanel1().add(izquierdaTF2);
			
						JTextField derechaTF2 = new JTextField(df.format(total+iva));
						derechaTF2.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
						derechaTF2.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 9));
						derechaTF2.setHorizontalAlignment(JTextField.RIGHT);
						derechaTF2.setLocation(430,getPosActual());
						derechaTF2.setSize(70,15);
						getJPanel1().add(derechaTF2);
						break;
				}
			}
			insertarLineas(2);
		}
	}

	private void llenarPieDePagina(){
		insertarLineas(2);
		JLabel lineaTexto;

		lineaTexto = new JLabel("- El titular de la lista es la única persona autorizada para modificar y cerrar la Lista.");
		lineaTexto.setSize(550, 15);
		lineaTexto.setLocation(0, getPosActual());
		lineaTexto.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
		jPanel1.add(lineaTexto,null);
		insertarLinea();
		lineaTexto = new JLabel("- Precios vigentes al "+fechaActual);
		lineaTexto.setSize(550, 15);
		lineaTexto.setLocation(0, getPosActual());
		lineaTexto.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
		jPanel1.add(lineaTexto,null);
		if(tipoLista=='N'){
			insertarLinea();
			lineaTexto = new JLabel("- No se garantiza el precio de los productos seleccionados ni su existencia para el momento del cierre de la lista.");
			lineaTexto.setSize(550, 15);
			lineaTexto.setLocation(0, getPosActual());
			lineaTexto.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
			jPanel1.add(lineaTexto,null);
		}
	}

	private void insertarLineas(int lineas){
		for(int i=0;i<lineas;i++)
			insertarLinea();
	}
	
	private void insertarLinea(){
		posY += 15;
		if(posY>(810*paginas)-150)
			insertarNuevaPagina();
	}

	private int getPosActual(){
		return posY;
	}
	
	private void insertarNuevaPagina() {
		paginas++;
		posY = (810*(paginas-1)); // Coloca posY en una nueva página
		this.setSize(550,810*(paginas)); // Agrega otra pagina al frame
		this.getJPanel1().setSize(550,810*paginas); // Agrega otra pagina al JPanel1

		jPanel1.add(getJPanel(), null); //Agrega encabezado
		jPanel.setLocation(0, posY);//Agrega encabezado
		posY += 165;
	}
	
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setLocation(-1000,-1000);
		this.setSize(550, 810);
		this.setContentPane(getJContentPane());
	}
	
	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(getJPanel1(), null);
			jContentPane.setBackground(java.awt.Color.white);
			jContentPane.setPreferredSize(new java.awt.Dimension(550,810));
		}
		return jContentPane;
	}

	/**
	 * This method initializes jLabel
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel() {
		//if(jLabel == null) {
			jLabel = new javax.swing.JLabel();
			jLabel.setSize(97, 25);
			jLabel.setText("B E C O");
			jLabel.setFont(new java.awt.Font("Sans Serif", java.awt.Font.BOLD, 24));
			jLabel.setLocation(0, 20);
		//}
		return jLabel;
	}
	/**
	 * This method initializes jLabel2
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel2() {
		//if(jLabel2 == null) {
			jLabel2 = new javax.swing.JLabel();
			jLabel2.setSize(50, 15);
			jLabel2.setText("Fecha: ");
			jLabel2.setLocation(380, 20);
			jLabel2.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
			jLabel2.setPreferredSize(new java.awt.Dimension(50,16));
		//}
		return jLabel2;
	}
	/**
	 * This method initializes fecha
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JTextField getFecha() {
		//if(fechaTextField == null) {
			fechaTextField = new javax.swing.JTextField();
			fechaTextField.setSize(70, 15);
			fechaTextField.setText("");
			fechaTextField.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
			fechaTextField.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
			fechaTextField.setLocation(430, 20);
			fechaTextField.setText(fechaActual);
		//}
		return fechaTextField;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel() {
		jPanel = new javax.swing.JPanel();
		jPanel.setLayout(null);
		jPanel.add(getJLabel(), null);
		jPanel.add(getJLabel2(), null);
		jPanel.add(getFecha(), null);
		jPanel.add(getJLabel1(), null);
		jPanel.add(getJLabel4(), null);
		jPanel.add(getJLabel5(), null);
		jPanel.add(getTituloReporte(), null);
		jPanel.add(getJLabel7(), null);
		jPanel.add(getJLabel8(), null);
		jPanel.add(getTienda(), null);
		jPanel.add(getHora(), null);
		jPanel.add(getTitular(), null);
		jPanel.add(getTitularSec(), null);
		jPanel.add(getJLabel12(), null);
		jPanel.add(getJLabel13(), null);
		jPanel.add(getJLabel14(), null);
		jPanel.add(getJLabel15(), null);
		jPanel.add(getJLabel16(), null);
		jPanel.add(getJLabel17(), null);
		jPanel.add(getJLabel18(), null);
		jPanel.add(getJLabel3(), null);
		jPanel.add(getTiendaOrigen(), null);
		jPanel.add(getJLabel10(), null);
		jPanel.add(getJLabel9(), null);
		jPanel.add(getTipoEventoTF(), null);
		jPanel.setSize(550, 165);
		jPanel.setBackground(java.awt.Color.white);
		return jPanel;
	}
	/**
	 * This method initializes jLabel1
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel1() {
		//if(jLabel1 == null) {
			jLabel1 = new javax.swing.JLabel();
			jLabel1.setSize(210, 15);
			jLabel1.setText("Servicios al Cliente:  Lista de Regalos");
			jLabel1.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
			jLabel1.setLocation(0, 45);
		//}
		return jLabel1;
	}
	/**
	 * This method initializes jLabel4
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel4() {
		//if(jLabel4 == null) {
			jLabel4 = new javax.swing.JLabel();
			jLabel4.setSize(50, 15);
			jLabel4.setText("Tienda:");
			jLabel4.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
			jLabel4.setLocation(0, 60);
		//}
		return jLabel4;
	}
	/**
	 * This method initializes jLabel5
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel5() {
		//if(jLabel5 == null) {
			jLabel5 = new javax.swing.JLabel();
			jLabel5.setSize(50, 15);
			jLabel5.setText("Hora:");
			jLabel5.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
			jLabel5.setLocation(380, 35);
		//}
		return jLabel5;
	}
	/**
	 * This method initializes jLabel6
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getTituloReporte() {
		//if(jLabel6 == null) {
			tituloReporte = new javax.swing.JLabel();
			tituloReporte.setSize(290, 15);
			tituloReporte.setText("Reporte de Ventas de Lista de Regalos No. "+numLista);
			tituloReporte.setFont(new java.awt.Font("Sans Serif", java.awt.Font.BOLD, 10));
			tituloReporte.setLocation(80, 70);
		//}
		return tituloReporte;
	}
	/**
	 * This method initializes jLabel7
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel7() {
		//if(jLabel7 == null) {
			jLabel7 = new javax.swing.JLabel();
			jLabel7.setSize(155, 15);
			jLabel7.setText("Nombre del Titular:");
			jLabel7.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
			jLabel7.setLocation(0, 95);
		//}
		return jLabel7;
	}
	/**
	 * This method initializes jLabel8
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel8() {
		//if(jLabel8 == null) {
			jLabel8 = new javax.swing.JLabel();
			jLabel8.setSize(155, 15);
			jLabel8.setText("Nombre del Titular Secundario:");
			jLabel8.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
			jLabel8.setLocation(0, 110);
		//}
		return jLabel8;
	}
	/**
	 * This method initializes tienda
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JTextField getTienda() {
		//if(tiendaTextField == null) {
			tiendaTextField = new javax.swing.JTextField();
			tiendaTextField.setSize(160, 15);
			tiendaTextField.setText("");
			tiendaTextField.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
			tiendaTextField.setLocation(50, 60);
			tiendaTextField.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
			tiendaTextField.setText(String.valueOf(numTienda));
		//}
		return tiendaTextField;
	}

	/**
	 * This method initializes hora
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JTextField getHora() {
		//if(horaTextField == null) {
			horaTextField = new javax.swing.JTextField();
			horaTextField.setSize(70, 15);
			horaTextField.setText("");
			horaTextField.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
			horaTextField.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
			horaTextField.setLocation(430, 35);
			horaTextField.setText(horaActual);
		//}
		return horaTextField;
	}
	/**
	 * This method initializes titular
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JTextField getTitular() {
		titularTextField = new javax.swing.JTextField();
		titularTextField.setSize(150, 15);
		titularTextField.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
		titularTextField.setLocation(155, 95);
		titularTextField.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
		titularTextField.setText(titular);
		titularTextField.select(0,0);
		return titularTextField;
	}
	/**
	 * This method initializes titularSec
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JTextField getTitularSec() {
		titularSecTextField = new javax.swing.JTextField();
		titularSecTextField.setSize(150, 15);
		titularSecTextField.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
		titularSecTextField.setLocation(155, 110);
		titularSecTextField.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
		titularSecTextField.setText(titularSec);
		titularSecTextField.select(0,0);
		return titularSecTextField;
	}
	/**
	 * This method initializes jLabel12
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel12() {
		//if(jLabel12 == null) {
			jLabel12 = new javax.swing.JLabel();
			jLabel12.setSize(70, 15);
			jLabel12.setText("Producto");
			jLabel12.setLocation(45, 150);
			jLabel12.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
			jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
		//}
		return jLabel12;
	}
	/**
	 * This method initializes jLabel13
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel13() {
		//if(jLabel13 == null) {
			jLabel13 = new javax.swing.JLabel();
			jLabel13.setSize(140, 15);
			jLabel13.setText("Descripción Producto");
			jLabel13.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
			jLabel13.setLocation(115, 150);
			jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
		//}
		return jLabel13;
	}
	/**
	 * This method initializes jLabel14
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel14() {
		//if(jLabel14 == null) {
			jLabel14 = new javax.swing.JLabel();
			jLabel14.setSize(105, 15);
			jLabel14.setText("Nombre Invitado");
			jLabel14.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
			jLabel14.setLocation(255, 150);
			jLabel14.setPreferredSize(new java.awt.Dimension(40,13));
			jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
		//}
		return jLabel14;
	}
	/**
	 * This method initializes jLabel15
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel15() {
		//if(jLabel15 == null) {
			jLabel15 = new javax.swing.JLabel();
			jLabel15.setSize(25, 15);
			jLabel15.setText("Tda.");
			jLabel15.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
			jLabel15.setLocation(360, 150);
			jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		//}
		return jLabel15;
	}
	/**
	 * This method initializes jLabel16
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel16() {
		//if(jLabel16 == null) {
			jLabel16 = new javax.swing.JLabel();
			jLabel16.setSize(25, 15);
			jLabel16.setText("Cant.");
			jLabel16.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
			jLabel16.setLocation(385, 150);
			jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		//}
		return jLabel16;
	}
	/**
	 * This method initializes jLabel17
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel17() {
		//if(jLabel17 == null) {
			jLabel17 = new javax.swing.JLabel();
			jLabel17.setSize(70, 15);
			jLabel17.setText("Precio");
			jLabel17.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
			jLabel17.setLocation(410, 150);
			jLabel17.setPreferredSize(new java.awt.Dimension(50,16));
			jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		//}
		return jLabel17;
	}
	/**
	 * This method initializes jLabel18
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel18() {
		//if(jLabel18 == null) {
			jLabel18 = new javax.swing.JLabel();
			jLabel18.setSize(70, 15);
			jLabel18.setText("Sub-Totales");
			jLabel18.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
			jLabel18.setLocation(480, 150);
			jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		//}
		return jLabel18;
	}
	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel1() {
		if(jPanel1 == null) {
			jPanel1 = new javax.swing.JPanel();
			jPanel1.setLayout(null);
			jPanel1.setSize(550, 810);
			jPanel1.setBackground(java.awt.Color.white);
			jPanel1.setLocation(0, 0);
		}
		return jPanel1;
	}
	/**
	 * This method initializes jLabel3
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel3() {
		//if(jLabel3 == null) {
			jLabel3 = new javax.swing.JLabel();
			jLabel3.setSize(100, 15);
			jLabel3.setText("Tienda de ingreso:");
			jLabel3.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
			jLabel3.setLocation(320, 95);
		//}
		return jLabel3;
	}
	/**
	 * This method initializes tiendaOrigen
	 * 
	 * @return javax.swing.JTextField
	 */
	private javax.swing.JTextField getTiendaOrigen() {
		tiendaOrigenTextField = new javax.swing.JTextField();
		tiendaOrigenTextField.setSize(100, 15);
		tiendaOrigenTextField.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
		tiendaOrigenTextField.setLocation(420, 95);
		tiendaOrigenTextField.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
		tiendaOrigenTextField.setText(String.valueOf(tiendaOrigen));
		return tiendaOrigenTextField;
	}
	/**
	 * This method initializes jLabel10
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel10() {
		//if(jLabel10 == null) {
			jLabel10 = new javax.swing.JLabel();
			jLabel10.setSize(45, 15);
			jLabel10.setText("Trans.");
			jLabel10.setLocation(0, 150);
			jLabel10.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
			jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
		//}
		return jLabel10;
	}
	/**
	 * This method initializes jLabel9
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel9() {
		jLabel9 = new javax.swing.JLabel();
		jLabel9.setSize(100, 15);
		jLabel9.setText("Tipo de Evento:");
		jLabel9.setLocation(320, 110);
		jLabel9.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
		return jLabel9;
	}
	/**
	 * This method initializes tipoEventoTF
	 * 
	 * @return javax.swing.JTextField
	 */
	private javax.swing.JTextField getTipoEventoTF() {
		tipoEventoTF = new javax.swing.JTextField();
		tipoEventoTF.setSize(100, 15);
		tipoEventoTF.setLocation(419, 110);
		tipoEventoTF.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
		tipoEventoTF.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
		tipoEventoTF.setText(String.valueOf(tipoEvento));
		return tipoEventoTF;
	}
}

/*
* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
* Sólo se parametrizó el tipo de dato de 'Comparator'
* Fecha: agosto 2011
*/
class ReporteCierreLista extends JWindow implements Comparator<Object> {

	private static final long serialVersionUID = 1L;

	private DecimalFormat df = new DecimalFormat("#,##0.00");
	
	private String tipoEvento,fechaApertura;
	private int numTienda;
	private int tiendaOrigen;
	private int numLista;
	private String titular;
	private String titularSec;
	private int posY = 0, paginas = 1;
	
	private javax.swing.JPanel jContentPane = null;

	private javax.swing.JLabel jLabel = null;
	private javax.swing.JLabel jLabel2 = null;
	private javax.swing.JTextField fechaTextField = null;
	private javax.swing.JPanel jPanel = null;
	private javax.swing.JLabel jLabel1 = null;
	private javax.swing.JLabel jLabel4 = null;
	private javax.swing.JLabel jLabel5 = null;
	private javax.swing.JLabel tituloReporte = null;
	private javax.swing.JLabel jLabel7 = null;
	private javax.swing.JLabel jLabel8 = null;
	private javax.swing.JTextField tiendaTextField = null;
	private javax.swing.JTextField horaTextField = null;
	private javax.swing.JTextField titularTextField = null;
	private javax.swing.JTextField titularSecTextField = null;
	private javax.swing.JTextField tiendaOrigenTextField = null;
	private javax.swing.JLabel jLabel12 = null;
	private javax.swing.JLabel jLabel13 = null;
	private javax.swing.JLabel jLabel14 = null;
	private javax.swing.JLabel jLabel15 = null;
	private javax.swing.JPanel jPanel1 = null;
	private javax.swing.JPanel jPanel2 = null;
	private javax.swing.JLabel jLabel3 = null;
	private javax.swing.JLabel jLabel9 = null;
	private javax.swing.JLabel jLabel6 = null;

	private JTextField tipoEventoTF = null;
	/**
	 * This is the default constructor
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public ReporteCierreLista(String fechaCierre,String fechaApertura,String tipoEvento,int numTienda,
			int numLista,int tiendaOrigen,String titular,String titularSec,String fechaEvento,Vector<DetalleServicio> detallesLista) {
		super();
		
		this.fechaApertura = fechaApertura;
		this.tipoEvento = tipoEvento;
		this.numTienda = numTienda;
		this.tiendaOrigen = tiendaOrigen;
		this.numLista = numLista;
		this.titular = titular;
		this.titularSec = titularSec;

		initialize();

		jPanel1.add(getJPanel(), null); //Agrega encabezado
		jPanel.setLocation(0, getPosActual());//Agrega encabezado
		insertarLineas(11);
		
		llenarDetalles(detallesLista);
		llenarPieDePagina();
		this.setVisible(true);
	}

	public int compare(Object o1, Object o2) {
		DetalleServicio detalle1 = (DetalleServicio)o1;
		DetalleServicio detalle2 = (DetalleServicio)o2;
		int d1 = Integer.parseInt(detalle1.getProducto().getCodDepartamento());
		int d2 = Integer.parseInt(detalle2.getProducto().getCodDepartamento());
		
		return (d1-d2);
	}

	/**
	 * @param detallesLista
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	private void llenarDetalles(Vector<DetalleServicio> detallesLista) {
		double total = 0, iva = 0;
		int codDepto = -1;
		Collections.sort(detallesLista,this);
		
		JLabel pagina = new JLabel("Pág. "+paginas);
		pagina.setSize(50, 15);
		pagina.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
		pagina.setLocation(245, 25);
		jPanel.add(pagina,null);

		if(detallesLista.size()==0){
			insertarLinea();
			JTextField mensajeTF = new JTextField("No hay productos en la lista que cumplan este criterio");
			mensajeTF.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
			mensajeTF.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
			mensajeTF.setLocation(0,getPosActual());
			mensajeTF.setSize(400,15);
			getJPanel1().add(mensajeTF);
		}else{		
			for(int i=0;i<detallesLista.size();i++){
				DetalleServicio detalle = (DetalleServicio)detallesLista.get(i);
				if(detalle.getCantVendidos()>0){
					int cd = Integer.parseInt(detalle.getProducto().getCodDepartamento());
					if(cd > codDepto){
						codDepto = cd;
						insertarLinea();
						String departamento = MediadorBD.obtenerDescDepto(cd);
						JTextField codDeptoTF = new JTextField("Departamento:  "+cd+"  -  "+departamento);
						codDeptoTF.setFont(new java.awt.Font("Sans Serif", java.awt.Font.BOLD, 8));
						codDeptoTF.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
						codDeptoTF.setLocation(30,getPosActual());
						codDeptoTF.setSize(470,15);
						getJPanel1().add(codDeptoTF);
						insertarLinea();
					}
		
					JTextField codProducto = new JTextField(detalle.getProducto().getCodProducto());
					codProducto.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
					codProducto.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 9));
					codProducto.setLocation(0,getPosActual());
					codProducto.setSize(78,15);
					getJPanel1().add(codProducto);
		
					JTextField descProducto = new JTextField(detalle.getProducto().getDescripcionLarga());
					descProducto.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
					descProducto.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 9));
					descProducto.setHorizontalAlignment(JTextField.LEFT);
					descProducto.select(0, 0);
					descProducto.setLocation(80,getPosActual());
					descProducto.setSize(248,15);
					getJPanel1().add(descProducto);
		
					double precioTotal = detalle.getPrecioFinal()*(1+detalle.getProducto().getImpuesto().getPorcentaje()/100);
					JTextField precio = new JTextField(df.format(precioTotal));
					precio.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
					precio.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 9));
					precio.setHorizontalAlignment(JTextField.RIGHT);
					precio.setLocation(330,getPosActual());
					precio.setSize(78,15);
					getJPanel1().add(precio);
					
					JTextField pedidos = new JTextField(df.format(detalle.getCantPedidos()));
					pedidos.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
					pedidos.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 9));
					pedidos.setHorizontalAlignment(JTextField.RIGHT);
					pedidos.setLocation(410,getPosActual());
					pedidos.setSize(48,15);
					getJPanel1().add(pedidos);
					
					JTextField vendidos = new JTextField(df.format(detalle.getCantVendidos()));
					vendidos.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
					vendidos.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 9));
					vendidos.setHorizontalAlignment(JTextField.RIGHT);
					vendidos.setLocation(460,getPosActual());
					vendidos.setSize(48,15);
					getJPanel1().add(vendidos);
					
					total += (detalle.getPrecioFinal()*detalle.getCantVendidos());
					iva += (detalle.getPrecioFinal()*detalle.getCantVendidos())*(detalle.getProducto().getImpuesto().getPorcentaje()/100);
					
					insertarLinea();			
				}
			}
	
			for(int i=0;i<3;i++) {
				insertarLinea();

				switch(i){
					case 0:
					JTextField izquierda = new JTextField("SUBTOTAL VENDIDO");
					izquierda.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
					izquierda.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 9));
					izquierda.setHorizontalAlignment(JTextField.RIGHT);
					izquierda.setLocation(310,getPosActual());
					izquierda.setSize(110,15);
					getJPanel1().add(izquierda);
	
					JTextField derecha = new JTextField(df.format(total));
					derecha.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
					derecha.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 9));
					derecha.setHorizontalAlignment(JTextField.RIGHT);
					derecha.setLocation(430,getPosActual());
					derecha.setSize(65,15);
					getJPanel1().add(derecha);
					break;
					case 1:
					
					JTextField izquierda1 = new JTextField("IVA");
					izquierda1.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
					izquierda1.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 9));
					izquierda1.setHorizontalAlignment(JTextField.RIGHT);
					izquierda1.setLocation(310,getPosActual());
					izquierda1.setSize(110,15);
					getJPanel1().add(izquierda1);
		
					JTextField derecha1 = new JTextField(df.format(iva));
					derecha1.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
					derecha1.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 9));
					derecha1.setHorizontalAlignment(JTextField.RIGHT);
					derecha1.setLocation(430,getPosActual());
					derecha1.setSize(65,15);
					getJPanel1().add(derecha1);
					break;
					case 2:
					JTextField izquierda2 = new JTextField("TOTAL VENDIDO");
					izquierda2.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
					izquierda2.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 9));
					izquierda2.setHorizontalAlignment(JTextField.RIGHT);
					izquierda2.setLocation(310,getPosActual());
					izquierda2.setSize(110,15);
					getJPanel1().add(izquierda2);
			
					JTextField derecha2 = new JTextField(df.format(total+iva));
					derecha2.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
					derecha2.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 9));
					derecha2.setHorizontalAlignment(JTextField.RIGHT);
					derecha2.setLocation(430,getPosActual());
					derecha2.setSize(65,15);
					getJPanel1().add(derecha2);
					break;
				}
			}
			insertarLinea();
		}
	}
	
	private void llenarPieDePagina(){
		insertarLineas(2);
		JLabel lineaTexto;

		insertarLineas(2);
		lineaTexto = new JLabel("Los productos señalados en esta lista son los que efectivamente he decido adquirir");
		lineaTexto.setSize(550, 15);
		lineaTexto.setLocation(0, getPosActual());
		lineaTexto.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
		jPanel1.add(lineaTexto,null);
		insertarLinea();
		lineaTexto = new JLabel("y en consecuencia solicito la emisión de la factura correspondiente a esta operación.  Igualmente dejo constancia del");
		lineaTexto.setSize(550, 15);
		lineaTexto.setLocation(0, getPosActual());
		lineaTexto.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
		jPanel1.add(lineaTexto,null);
		insertarLinea();
		lineaTexto = new JLabel("cierre definitivo de la Lista de Regalo aperturada en fecha "+fechaApertura+" y sus modificaciones.");
		lineaTexto.setSize(550, 15);
		lineaTexto.setLocation(0, getPosActual());
		lineaTexto.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
		jPanel1.add(lineaTexto,null);
		insertarLinea();
		lineaTexto = new JLabel("Asimismo solicito que:");
		lineaTexto.setSize(550, 15);
		lineaTexto.setLocation(0, getPosActual());
		lineaTexto.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
		jPanel1.add(lineaTexto,null);
		insertarLinea();
		lineaTexto = new JLabel("_____ Los productos me sean entregados al momento de la emisión de la factura y renuncio al servicio de despacho.");
		lineaTexto.setSize(550, 15);
		lineaTexto.setLocation(0, getPosActual());
		lineaTexto.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
		jPanel1.add(lineaTexto,null);
		insertarLinea();
		lineaTexto = new JLabel("_____ Los productos  sean despachados a la dirección señalada en la nota de despacho dentro de los 5 días hábiles");
		lineaTexto.setSize(550, 15);
		lineaTexto.setLocation(0, getPosActual());
		lineaTexto.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
		jPanel1.add(lineaTexto,null);
		insertarLinea();
		lineaTexto = new JLabel("siguientes a la fecha indicada en este reporte.");
		lineaTexto.setSize(550, 15);
		lineaTexto.setLocation(0, getPosActual());
		lineaTexto.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
		jPanel1.add(lineaTexto,null);
		insertarLinea();
		lineaTexto = new JLabel("_____ Los productos sean despachados en fecha _________________ a la dirección señalada en la nota de despacho");
		lineaTexto.setSize(550, 15);
		lineaTexto.setLocation(0, getPosActual());
		lineaTexto.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
		jPanel1.add(lineaTexto,null);
		insertarLinea();
		lineaTexto = new JLabel("asumiendo los costos de su traslado.");
		lineaTexto.setSize(550, 15);
		lineaTexto.setLocation(0, getPosActual());
		lineaTexto.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
		jPanel1.add(lineaTexto,null);
		insertarLineas(2);
		lineaTexto = new JLabel("Cambios o devoluciones: 30 días desde la compra con la presentación de la factura que se emitirá por esta Lista.");
		lineaTexto.setSize(550, 15);
		lineaTexto.setLocation(0, getPosActual());
		lineaTexto.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
		jPanel1.add(lineaTexto,null);
		insertarLinea();
		lineaTexto = new JLabel("Productos eléctricos: aplican condiciones de la garantía. Ropa íntima, trajes de baño, tratamientos, maquillaje");
		lineaTexto.setSize(550, 15);
		lineaTexto.setLocation(0, getPosActual());
		lineaTexto.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
		jPanel1.add(lineaTexto,null);
		insertarLinea();
		lineaTexto = new JLabel("y fragancias no pueden ser cambiados o devueltos.");
		lineaTexto.setSize(550, 15);
		lineaTexto.setLocation(0, getPosActual());
		lineaTexto.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
		jPanel1.add(lineaTexto,null);
		insertarLineas(2);
		lineaTexto = new JLabel("Firma del Titular: __________________________________");
		lineaTexto.setSize(550, 15);
		lineaTexto.setLocation(10, getPosActual());
		lineaTexto.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
		jPanel1.add(lineaTexto,null);
		insertarLineas(2);
		lineaTexto = new JLabel("C.I./R.I.F.: __________________________________");
		lineaTexto.setSize(550, 15);
		lineaTexto.setLocation(10, getPosActual());
		lineaTexto.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
		jPanel1.add(lineaTexto,null);
	}

	private void insertarLineas(int lineas){
		for(int i=0;i<lineas;i++)
			insertarLinea();
	}
	
	private void insertarLinea(){
		posY += 15;
		if(posY>(810*paginas)-150)
			insertarNuevaPagina();
	}

	private int getPosActual(){
		return posY;
	}
	
	private void insertarNuevaPagina() {
		paginas++;
		posY = (810*(paginas-1)); // Coloca posY en una nueva página
		this.setSize(550,810*(paginas)); // Agrega otra pagina al frame
		this.getJPanel1().setSize(550,810*paginas); // Agrega otra pagina al JPanel1

		jPanel1.add(getJPanel(), null); //Agrega encabezado
		jPanel.setLocation(0, posY);//Agrega encabezado
		posY += 165;
	}
	
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setLocation(-1000,-1000);
		this.setSize(550, 810);
		this.setContentPane(getJContentPane());
	}
	
	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(null);
			jContentPane.setBackground(java.awt.Color.white);
			jContentPane.add(getJPanel1(), null);
			jContentPane.setPreferredSize(new java.awt.Dimension(550,810));
		}
		return jContentPane;
	}

	/**
	 * This method initializes jLabel
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel() {
		//if(jLabel == null) {
			jLabel = new javax.swing.JLabel();
			jLabel.setSize(97, 25);
			jLabel.setText("B E C O");
			jLabel.setFont(new java.awt.Font("Sans Serif", java.awt.Font.BOLD, 24));
			jLabel.setLocation(0, 20);
		//}
		return jLabel;
	}
	/**
	 * This method initializes jLabel2
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel2() {
		//if(jLabel2 == null) {
			jLabel2 = new javax.swing.JLabel();
			jLabel2.setSize(50, 15);
			jLabel2.setText("Fecha: ");
			jLabel2.setLocation(380, 20);
			jLabel2.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 9));
			jLabel2.setPreferredSize(new java.awt.Dimension(50,16));
		//}
		return jLabel2;
	}
	/**
	 * This method initializes fecha
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JTextField getFecha() {
		//if(fechaTextField == null) {
			fechaTextField = new javax.swing.JTextField();
			fechaTextField.setSize(70, 15);
			fechaTextField.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
			fechaTextField.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 9));
			fechaTextField.setLocation(430, 20);			
			fechaTextField.setText(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
		//}
		return fechaTextField;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel() {
		jPanel = new javax.swing.JPanel();
		jPanel.setLayout(new BorderLayout());
		jPanel.add(getJPanel2(),BorderLayout.NORTH);
		jPanel.add(getJLabel(), BorderLayout.CENTER);
		jPanel.add(getJLabel2(), BorderLayout.CENTER);
		jPanel.add(getFecha(), BorderLayout.CENTER);
		jPanel.add(getJLabel1(), BorderLayout.CENTER);
		jPanel.add(getJLabel4(), BorderLayout.CENTER);
		jPanel.add(getJLabel5(), BorderLayout.CENTER);
		jPanel.add(getTituloReporte(), BorderLayout.CENTER);
		jPanel.add(getJLabel7(), BorderLayout.CENTER);
		jPanel.add(getJLabel8(), BorderLayout.CENTER);
		jPanel.add(getTienda(), BorderLayout.CENTER);
		jPanel.add(getHora(), BorderLayout.CENTER);
		jPanel.add(getTitular(), BorderLayout.CENTER);
		jPanel.add(getTitularSec(), BorderLayout.CENTER);
		jPanel.add(getJLabel12(), BorderLayout.CENTER);
		jPanel.add(getJLabel13(), BorderLayout.CENTER);
		jPanel.add(getJLabel14(), BorderLayout.CENTER);
		jPanel.add(getJLabel15(), BorderLayout.CENTER);
		jPanel.add(getJLabel3(), BorderLayout.CENTER);
		jPanel.add(getJLabel9(), BorderLayout.CENTER);
		jPanel.add(getTiendaOrigen(), BorderLayout.CENTER);
		jPanel.add(getJLabel6(), BorderLayout.CENTER);
		jPanel.setSize(600, 165);
		jPanel.setBackground(java.awt.Color.white);
		jPanel.add(getTipoEventoTF(), null);
		return jPanel;
	}
	
	
	private javax.swing.JPanel getJPanel2() {
		jPanel2 = new javax.swing.JPanel();
		jPanel2.setSize(50, 165);
		jPanel2.setBackground(java.awt.Color.white);
		return jPanel2;
	}
	
	/**
	 * This method initializes jLabel1
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel1() {
		//if(jLabel1 == null) {
			jLabel1 = new javax.swing.JLabel();
			jLabel1.setSize(210, 15);
			jLabel1.setText("Servicios al Cliente:  Lista de Regalos");
			jLabel1.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 9));
			jLabel1.setLocation(0, 45);
		//}
		return jLabel1;
	}
	/**
	 * This method initializes jLabel4
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel4() {
		//if(jLabel4 == null) {
			jLabel4 = new javax.swing.JLabel();
			jLabel4.setSize(50, 15);
			jLabel4.setText("Tienda:");
			jLabel4.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 9));
			jLabel4.setLocation(0, 60);
		//}
		return jLabel4;
	}
	/**
	 * This method initializes jLabel5
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel5() {
		//if(jLabel5 == null) {
			jLabel5 = new javax.swing.JLabel();
			jLabel5.setSize(50, 15);
			jLabel5.setText("Hora:");
			jLabel5.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 9));
			jLabel5.setLocation(380, 35);
		//}
		return jLabel5;
	}
	/**
	 * This method initializes jLabel6
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getTituloReporte() {
		//if(jLabel6 == null) {
			tituloReporte = new javax.swing.JLabel();
			tituloReporte.setText("Reporte de Cierre de Lista de Regalos No. "+numLista);
			tituloReporte.setFont(new java.awt.Font("Sans Serif", java.awt.Font.BOLD, 12));
			tituloReporte.setLocation(85, 69);
			tituloReporte.setHorizontalAlignment(SwingConstants.CENTER);
			tituloReporte.setSize(370, 15);
		//}
		return tituloReporte;
	}
	/**
	 * This method initializes jLabel7
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel7() {
		//if(jLabel7 == null) {
			jLabel7 = new javax.swing.JLabel();
			jLabel7.setSize(155, 15);
			jLabel7.setText("Nombre del Titular:");
			jLabel7.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 9));
			jLabel7.setLocation(0, 90);
		//}
		return jLabel7;
	}
	/**
	 * This method initializes jLabel8
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel8() {
		//if(jLabel8 == null) {
			jLabel8 = new javax.swing.JLabel();
			jLabel8.setSize(155, 15);
			jLabel8.setText("Nombre del Titular Secundario:");
			jLabel8.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 9));
			jLabel8.setLocation(0, 105);
		//}
		return jLabel8;
	}
	/**
	 * This method initializes tienda
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JTextField getTienda() {
		tiendaTextField = new javax.swing.JTextField();
		tiendaTextField.setSize(160, 15);
		tiendaTextField.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
		tiendaTextField.setLocation(50, 60);
		tiendaTextField.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 9));
		tiendaTextField.setText(String.valueOf(numTienda));
		return tiendaTextField;
	}

	/**
	 * This method initializes hora
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JTextField getHora() {
		//if(horaTextField == null) {
			horaTextField = new javax.swing.JTextField();
			horaTextField.setSize(70, 15);
			horaTextField.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
			horaTextField.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 9));
			horaTextField.setLocation(430, 35);			
			horaTextField.setText(new SimpleDateFormat("hh:mm a").format(new Date()));
		//}
		return horaTextField;
	}
	/**
	 * This method initializes titular
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JTextField getTitular() {
		titularTextField = new javax.swing.JTextField();
		titularTextField.setSize(150, 15);
		titularTextField.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
		titularTextField.setLocation(155, 90);
		titularTextField.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 9));
		titularTextField.setText(titular);
		titularTextField.select(0,0);
		return titularTextField;
	}
	/**
	 * This method initializes titularSec
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JTextField getTitularSec() {
		titularSecTextField = new javax.swing.JTextField();
		titularSecTextField.setSize(150, 15);
		titularSecTextField.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
		titularSecTextField.setLocation(155, 105);
		titularSecTextField.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 9));
		if(titularSec.trim()!="" && titularSec!=null)
			titularSecTextField.setText(titularSec);
		else titularSecTextField.setText("N/A");
		titularSecTextField.select(0,0);
		return titularSecTextField;
	}
	/**
	 * This method initializes jLabel12
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel12() {
		//if(jLabel12 == null) {
			jLabel12 = new javax.swing.JLabel();
			jLabel12.setSize(80, 15);
			jLabel12.setText("Producto");
			jLabel12.setLocation(0, 150);
			jLabel12.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 9));
			jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
		//}
		return jLabel12;
	}
	/**
	 * This method initializes jLabel13
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel13() {
		//if(jLabel13 == null) {
			jLabel13 = new javax.swing.JLabel();
			jLabel13.setSize(250, 15);
			jLabel13.setText("Descripción Producto");
			jLabel13.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 9));
			jLabel13.setLocation(80, 150);
			jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
		//}
		return jLabel13;
	}
	/**
	 * This method initializes jLabel14
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel14() {
		//if(jLabel14 == null) {
			jLabel14 = new javax.swing.JLabel();
			jLabel14.setSize(80, 15);
			jLabel14.setText("Precio");
			jLabel14.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 9));
			jLabel14.setLocation(330, 150);
			jLabel14.setPreferredSize(new java.awt.Dimension(40,13));
			jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		//}
		return jLabel14;
	}
	/**
	 * This method initializes jLabel15
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel15() {
		//if(jLabel15 == null) {
			jLabel15 = new javax.swing.JLabel();
			jLabel15.setSize(50, 15);
			jLabel15.setText("Vendidos");
			jLabel15.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 9));
			jLabel15.setLocation(460, 150);
			jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		//}
		return jLabel15;
	}
	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel1() {
		if(jPanel1 == null) {
			jPanel1 = new javax.swing.JPanel();
			jPanel1.setLayout(null);
			jPanel1.add(getJPanel(), null);
			jPanel1.setSize(550, 810);
			jPanel1.setBackground(java.awt.Color.white);
			jPanel1.setLocation(0, 0);
		}
		return jPanel1;
	}
	/**
	 * This method initializes jLabel3
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel3() {
		jLabel3 = new javax.swing.JLabel();
		jLabel3.setSize(100, 15);
		jLabel3.setText("Tienda de ingreso:");
		jLabel3.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 9));
		jLabel3.setLocation(320, 90);
		return jLabel3;
	}
	/**
	 * This method initializes jLabel9
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel9() {
		//if(jLabel9 == null) {
			jLabel9 = new javax.swing.JLabel();
			jLabel9.setSize(50, 15);
			jLabel9.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 9));
			jLabel9.setText("Pedidos");
			jLabel9.setLocation(410, 150);
			jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
	//	}
		return jLabel9;
	}
	/**
	 * This method initializes tiendaOrigenTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private javax.swing.JTextField getTiendaOrigen() {
		tiendaOrigenTextField = new javax.swing.JTextField();
		tiendaOrigenTextField.setSize(100, 15);
		tiendaOrigenTextField.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
		tiendaOrigenTextField.setLocation(420, 90);
		tiendaOrigenTextField.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 9));
		tiendaOrigenTextField.setText(String.valueOf(tiendaOrigen));
		return tiendaOrigenTextField;
	}
	
	/**
	 * This method initializes jLabel6
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel6() {
		jLabel6 = new javax.swing.JLabel();
		jLabel6.setSize(100, 15);
		jLabel6.setText("Tipo de Evento:");
		jLabel6.setLocation(320, 105);
		jLabel6.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 9));
		return jLabel6;
	}

	/**
	 * This method initializes jTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTipoEventoTF() {
		if (tipoEventoTF == null) {
			tipoEventoTF = new JTextField();
			tipoEventoTF.setSize(120, 15);
			tipoEventoTF.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
			tipoEventoTF.setLocation(420, 105);
			tipoEventoTF.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 9));
			tipoEventoTF.setText(tipoEvento);
		}
		return tipoEventoTF;
	}
}

class ComprobanteBonoRegalo extends JWindow {
	private static final long serialVersionUID = 1L;

	private DecimalFormat df = new DecimalFormat("#,##0.00");
	private int numLista;
	private String tipoEvento,fechaEvento,nombreTitular;
	private String tiendaApertura,tiendaCierre;
	private String fecha,hora,codCajero;
	private double montoVendido,montoBonoRegalo,montoCambio,montoAbonado;
	private javax.swing.JPanel jContentPane = null;
	private javax.swing.JLabel jLabel = null;
	private javax.swing.JLabel jLabel2 = null;
	private javax.swing.JTextField fechaTextField = null;
	private javax.swing.JPanel jPanelEncabezado = null;
	private javax.swing.JLabel jLabel1 = null;
	private javax.swing.JLabel jLabel4 = null;
	private javax.swing.JLabel jLabel5 = null;
	private javax.swing.JLabel tituloReporte = null;
	private javax.swing.JLabel jLabel7 = null;
	private javax.swing.JTextField tiendaTextField = null;
	private javax.swing.JTextField horaTextField = null;
	private javax.swing.JTextField tiendaAperturaTF = null;
	private javax.swing.JTextField nombreTitularTF = null;
	private javax.swing.JPanel jPanel = null;
	private javax.swing.JLabel jLabel3 = null;
	private javax.swing.JLabel jLabel6 = null;
	private javax.swing.JLabel jLabel8 = null;
	private javax.swing.JLabel jLabel9 = null;
	private javax.swing.JTextField montoAbonadoTF = null;
	private javax.swing.JTextField montoVendidoTF = null;
	private javax.swing.JTextField montoBonoRegaloTF = null;
	private javax.swing.JLabel jLabel10 = null;
	private javax.swing.JTextField codCajeroTF = null;
	private javax.swing.JLabel jLabel11 = null;
	private javax.swing.JLabel jLabel12 = null;
	private javax.swing.JLabel jLabel13 = null;
	private javax.swing.JTextField tipoEventoTF = null;
	private javax.swing.JTextField fechaEventoTF = null;
	private javax.swing.JLabel jLabel14 = null;
	private javax.swing.JTextField cambioEfectivoTF = null;
	/**
	 * This is the default constructor
	 */
	public ComprobanteBonoRegalo(String fechaTrans, String horaTrans,int numTienda,int numLista,int tiendaOrigen,String codCajero,
				String titular,double montoAbonado,double montoVendido,double montoBonos, double montoCambio,
				String tipoEvento,String fechaEvento) {
		super();

		this.numLista = numLista;
		this.tipoEvento = tipoEvento;
		this.fechaEvento = fechaEvento;
		this.tiendaCierre = String.valueOf(numTienda);
		this.fecha = fechaTrans;
		this.hora = horaTrans;
		this.codCajero = codCajero;
		this.nombreTitular = titular;
		this.tiendaApertura = String.valueOf(tiendaOrigen);
		this.montoCambio = montoCambio;
		this.montoAbonado = montoAbonado;
		this.montoVendido = montoVendido;
		this.montoBonoRegalo = montoBonos;
		initialize();
		//insertarLineas(11);
		this.setVisible(true);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setLocation(-1000,-1000);
		this.setSize(550, 810);
		this.setContentPane(getJContentPane());
	}
	
	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(getJPanel(), null);
			jContentPane.setBackground(java.awt.Color.white);
			jContentPane.setPreferredSize(new java.awt.Dimension(345,576));
		}
		return jContentPane;
	}

	/**
	 * This method initializes jLabel
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel() {
		jLabel = new javax.swing.JLabel();
		jLabel.setSize(97, 25);
		jLabel.setText("B E C O");
		jLabel.setFont(new java.awt.Font("Sans Serif", java.awt.Font.BOLD, 24));
		jLabel.setLocation(0, 20);
		return jLabel;
	}
	/**
	 * This method initializes jLabel2
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel2() {
		jLabel2 = new javax.swing.JLabel();
		jLabel2.setSize(52, 15);
		jLabel2.setText("Fecha: ");
		jLabel2.setLocation(389, 22);
		jLabel2.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
		jLabel2.setPreferredSize(new java.awt.Dimension(50,16));
		return jLabel2;
	}
	/**
	 * This method initializes fecha
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JTextField getFecha() {
		fechaTextField = new javax.swing.JTextField();
		fechaTextField.setSize(87, 15);
		fechaTextField.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
		fechaTextField.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
		fechaTextField.setLocation(439, 22);
		fechaTextField.setText(fecha);
		return fechaTextField;
	}

	/**
	 * This method initializes jPanelEncabezado
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanelEncabezado() {
		jPanelEncabezado = new javax.swing.JPanel();
		jPanelEncabezado.setLayout(null);
		jPanelEncabezado.add(getJLabel(), null);
		jPanelEncabezado.add(getJLabel2(), null);
		jPanelEncabezado.add(getFecha(), null);
		jPanelEncabezado.add(getJLabel1(), null);
		jPanelEncabezado.add(getJLabel4(), null);
		jPanelEncabezado.add(getJLabel5(), null);
		jPanelEncabezado.add(getTituloReporte(), null);
		jPanelEncabezado.add(getJLabel7(), null);
		jPanelEncabezado.add(getTienda(), null);
		jPanelEncabezado.add(getHora(), null);
		jPanelEncabezado.add(getTiendaApertura(), null);
		jPanelEncabezado.add(getJLabel3(), null);
		jPanelEncabezado.add(getNombreTitularTF(), null);
		jPanelEncabezado.add(getJLabel10(), null);
		jPanelEncabezado.add(getCodCajero(), null);
		jPanelEncabezado.add(getJLabel11(), null);
		jPanelEncabezado.add(getJLabel12(), null);
		jPanelEncabezado.add(getJLabel13(), null);
		jPanelEncabezado.add(getTipoEventoTF(), null);
		jPanelEncabezado.add(getFechaEventoTF(), null);
		jPanelEncabezado.setSize(550, 130);
		jPanelEncabezado.setLocation(0,0);
		jPanelEncabezado.setBackground(java.awt.Color.white);
		return jPanelEncabezado;
	}
	/**
	 * This method initializes jLabel1
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel1() {
		jLabel1 = new javax.swing.JLabel();
		jLabel1.setSize(185, 15);
		jLabel1.setText("Servicios al Cliente:  Lista de Regalos");
		jLabel1.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
		jLabel1.setLocation(0, 45);
		return jLabel1;
	}
	/**
	 * This method initializes jLabel4
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel4() {
		jLabel4 = new javax.swing.JLabel();
		jLabel4.setSize(50, 15);
		jLabel4.setText("Tienda:");
		jLabel4.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
		jLabel4.setLocation(0, 60);
		return jLabel4;
	}
	/**
	 * This method initializes jLabel5
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel5() {
		jLabel5 = new javax.swing.JLabel();
		jLabel5.setSize(52, 15);
		jLabel5.setText("Hora:");
		jLabel5.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
		jLabel5.setLocation(389, 37);
		return jLabel5;
	}
	/**
	 * This method initializes jLabel6
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getTituloReporte() {
		tituloReporte = new javax.swing.JLabel();
		tituloReporte.setText("Cierre de Lista de Regalos No. "+numLista);
		tituloReporte.setFont(new java.awt.Font("Sans Serif", java.awt.Font.BOLD, 11));
		tituloReporte.setLocation(56, 75);
		tituloReporte.setSize(424, 15);
		tituloReporte.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		tituloReporte.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		return tituloReporte;
	}
	/**
	 * This method initializes jLabel7
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel7() {
		jLabel7 = new javax.swing.JLabel();
		jLabel7.setSize(80, 15);
		jLabel7.setText("Nombre Titular:");
		jLabel7.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
		jLabel7.setLocation(0, 100);
		return jLabel7;
	}
	/**
	 * This method initializes tienda
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JTextField getTienda() {
		tiendaTextField = new javax.swing.JTextField();
		tiendaTextField.setSize(80, 15);
		tiendaTextField.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
		tiendaTextField.setLocation(50, 60);
		tiendaTextField.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
		tiendaTextField.setText(tiendaCierre);
		return tiendaTextField;
	}

	/**
	 * This method initializes hora
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JTextField getHora() {
		horaTextField = new javax.swing.JTextField();
		horaTextField.setSize(87, 15);
		horaTextField.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
		horaTextField.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
		horaTextField.setLocation(439, 37);
		horaTextField.setText(hora);
		return horaTextField;
	}
	/**
	 * This method initializes titular
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JTextField getTiendaApertura() {
		tiendaAperturaTF = new javax.swing.JTextField();
		tiendaAperturaTF.setSize(144, 15);
		tiendaAperturaTF.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
		tiendaAperturaTF.setLocation(80, 115);
		tiendaAperturaTF.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
		tiendaAperturaTF.setText(tiendaApertura);
		return tiendaAperturaTF;
	}
	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel() {
		if(jPanel == null) {
			jPanel = new javax.swing.JPanel();
			jPanel.setLayout(null);
			jPanel.add(getJPanelEncabezado(), null);
			jPanel.add(getJLabel6(), null);
			jPanel.add(getJLabel8(), null);
			jPanel.add(getJLabel9(), null);
			jPanel.add(getMontoAbonado(), null);
			jPanel.add(getMontoVendido(), null);
			jPanel.add(getMontoBonoRegalo(), null);
			jPanel.add(getJLabel14(), null);
			jPanel.add(getCambioEfectivoTF(), null);
			jPanel.setSize(550, 810);
			jPanel.setBackground(java.awt.Color.white);
			jPanel.setLocation(0, 0);
			jPanel.add(getJPanelEncabezado(), null); //Agrega encabezado
		}
		return jPanel;
	}
	/**
	 * This method initializes jLabel3
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel3() {
		jLabel3 = new javax.swing.JLabel();
		jLabel3.setSize(80, 15);
		jLabel3.setText("Tienda ingreso:");
		jLabel3.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
		jLabel3.setLocation(0, 115);
		return jLabel3;
	}
	/**
	 * This method initializes tiendaOrigenTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private javax.swing.JTextField getNombreTitularTF() {
		nombreTitularTF = new javax.swing.JTextField();
		nombreTitularTF.setSize(145, 15);
		nombreTitularTF.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
		nombreTitularTF.setLocation(80, 100);
		nombreTitularTF.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
		nombreTitularTF.setText(nombreTitular);
		return nombreTitularTF;
	}
	/**
	 * This method initializes jLabel6
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel6() {
		jLabel6 = new javax.swing.JLabel();
		jLabel6.setSize(140, 20);
		jLabel6.setText("Monto Abonado a Lista:");
		jLabel6.setLocation(100, 160);
		jLabel6.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 11));
		return jLabel6;
	}
	/**
	 * This method initializes jLabel8
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel8() {
		jLabel8 = new javax.swing.JLabel();
		jLabel8.setSize(140, 20);
		jLabel8.setText("Monto Vendido de Lista:");
		jLabel8.setLocation(100, 180);
		jLabel8.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 11));
		return jLabel8;
	}
	/**
	 * This method initializes jLabel9
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel9() {
		jLabel9 = new javax.swing.JLabel();
		jLabel9.setSize(140, 20);
		jLabel9.setText("Resta en Bono Regalo:");
		jLabel9.setLocation(100, 200);
		jLabel9.setFont(new java.awt.Font("Sans Serif", java.awt.Font.BOLD, 11));
		return jLabel9;
	}
	/**
	 * This method initializes montoAbonado
	 * 
	 * @return javax.swing.JTextField
	 */
	private javax.swing.JTextField getMontoAbonado() {
		montoAbonadoTF = new javax.swing.JTextField();
		montoAbonadoTF.setSize(205, 20);
		montoAbonadoTF.setLocation(240, 160);
		montoAbonadoTF.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
		montoAbonadoTF.setFont(new java.awt.Font("Sans Serif", java.awt.Font.BOLD, 11));
		montoAbonadoTF.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
		montoAbonadoTF.setText(df.format(montoAbonado));
		return montoAbonadoTF;
	}
	/**
	 * This method initializes montoVendido
	 * 
	 * @return javax.swing.JTextField
	 */
	private javax.swing.JTextField getMontoVendido() {
		montoVendidoTF = new javax.swing.JTextField();
		montoVendidoTF.setSize(205, 20);
		montoVendidoTF.setLocation(240, 180);
		montoVendidoTF.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
		montoVendidoTF.setFont(new java.awt.Font("Sans Serif", java.awt.Font.BOLD, 11));
		montoVendidoTF.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
		montoVendidoTF.setText(df.format(montoVendido));
		return montoVendidoTF;
	}
	/**
	 * This method initializes montoBonoRegalo
	 * 
	 * @return javax.swing.JTextField
	 */
	private javax.swing.JTextField getMontoBonoRegalo() {
		montoBonoRegaloTF = new javax.swing.JTextField();
		montoBonoRegaloTF.setSize(205, 20);
		montoBonoRegaloTF.setLocation(240, 200);
		montoBonoRegaloTF.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
		montoBonoRegaloTF.setFont(new java.awt.Font("Sans Serif", java.awt.Font.BOLD, 11));
		montoBonoRegaloTF.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
		montoBonoRegaloTF.setText(df.format(montoBonoRegalo));
		return montoBonoRegaloTF;
	}
	/**
	 * This method initializes jLabel10
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel10() {
		jLabel10 = new javax.swing.JLabel();
		jLabel10.setSize(52, 15);
		jLabel10.setText("Cajero:");
		jLabel10.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
		jLabel10.setLocation(389, 52);
		return jLabel10;
	}
	/**
	 * This method initializes codCajero
	 * 
	 * @return javax.swing.JTextField
	 */
	private javax.swing.JTextField getCodCajero() {
		codCajeroTF = new javax.swing.JTextField();
		codCajeroTF.setSize(87, 15);
		codCajeroTF.setLocation(439, 52);
		codCajeroTF.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
		codCajeroTF.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
		codCajeroTF.setText(codCajero);
		return codCajeroTF;
	}
	/**
	 * This method initializes jLabel11
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel11() {
		jLabel11 = new javax.swing.JLabel();
		jLabel11.setSize(344, 15);
		jLabel11.setText("Comprobante de Bono Regalo por");
		jLabel11.setLocation(91, 60);
		jLabel11.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		jLabel11.setFont(new java.awt.Font("Sans Serif", java.awt.Font.BOLD, 11));
		return jLabel11;
	}
	/**
	 * This method initializes jLabel12
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel12() {
		jLabel12 = new javax.swing.JLabel();
		jLabel12.setSize(80, 15);
		jLabel12.setText("Tipo de Evento:");
		jLabel12.setLocation(339, 100);
		jLabel12.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
		return jLabel12;
	}
	/**
	 * This method initializes jLabel13
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel13() {
		jLabel13 = new javax.swing.JLabel();
		jLabel13.setSize(80, 15);
		jLabel13.setText("Fecha Evento:");
		jLabel13.setLocation(339, 115);
		jLabel13.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
		return jLabel13;
	}
	/**
	 * This method initializes tipoEventoTF
	 * 
	 * @return javax.swing.JTextField
	 */
	private javax.swing.JTextField getTipoEventoTF() {
		tipoEventoTF = new javax.swing.JTextField();
		tipoEventoTF.setSize(117, 15);
		tipoEventoTF.setLocation(419, 100);
		tipoEventoTF.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
		tipoEventoTF.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
		tipoEventoTF.setText(tipoEvento);
		return tipoEventoTF;
	}
	/**
	 * This method initializes fechaEventoTF
	 * 
	 * @return javax.swing.JTextField
	 */
	private javax.swing.JTextField getFechaEventoTF() {
		fechaEventoTF = new javax.swing.JTextField();
		fechaEventoTF.setSize(119, 15);
		fechaEventoTF.setLocation(419, 115);
		fechaEventoTF.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
		fechaEventoTF.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
		fechaEventoTF.setText(fechaEvento);
		return fechaEventoTF;
	}
	/**
	 * This method initializes jLabel14
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel14() {
		jLabel14 = new javax.swing.JLabel();
		jLabel14.setSize(140, 20);
		jLabel14.setText("Cambio en efectivo:");
		jLabel14.setLocation(100, 219);
		jLabel14.setFont(new java.awt.Font("Sans Serif", java.awt.Font.BOLD, 11));
		return jLabel14;
	}
	/**
	 * This method initializes cambioEfectivoTF
	 * 
	 * @return javax.swing.JTextField
	 */
	private javax.swing.JTextField getCambioEfectivoTF() {
		cambioEfectivoTF = new javax.swing.JTextField();
		cambioEfectivoTF.setSize(205, 20);
		cambioEfectivoTF.setLocation(240, 220);
		cambioEfectivoTF.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
		cambioEfectivoTF.setFont(new java.awt.Font("Sans Serif", java.awt.Font.BOLD, 11));
		cambioEfectivoTF.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
		cambioEfectivoTF.setText(df.format(montoCambio));
		return cambioEfectivoTF;
	}
}

class ComprobanteBonoRegaloPromo extends JWindow {

	private static final long serialVersionUID = 1L;

	private DecimalFormat df = new DecimalFormat("#,##0.00");

	private int numLista;
	private String tipoEvento,fechaEvento,nombreTitular;
	private String tiendaApertura,tiendaCierre;
	private String fecha,hora,codCajero;
	private double montoVendido,montoBonoRegalo;
	//private int posY = 0, paginas = 1;
	
	private javax.swing.JPanel jContentPane = null;

	private javax.swing.JLabel jLabel = null;
	private javax.swing.JLabel jLabel2 = null;
	private javax.swing.JTextField fechaTextField = null;
	private javax.swing.JPanel jPanel = null;
	private javax.swing.JLabel jLabel1 = null;
	private javax.swing.JLabel jLabel4 = null;
	private javax.swing.JLabel jLabel5 = null;
	private javax.swing.JLabel tituloReporte = null;
	private javax.swing.JLabel jLabel7 = null;
	private javax.swing.JTextField tiendaTextField = null;
	private javax.swing.JTextField horaTextField = null;
	private javax.swing.JTextField tiendaAperturaTF = null;
	private javax.swing.JTextField nombreTitularTF = null;
	private javax.swing.JPanel jPanel1 = null;
	private javax.swing.JLabel jLabel3 = null;
	private javax.swing.JLabel jLabel8 = null;
	private javax.swing.JLabel jLabel9 = null;
	private javax.swing.JTextField montoVendidoTF = null;
	private javax.swing.JTextField montoBonoRegaloTF = null;
	private javax.swing.JLabel jLabel10 = null;
	private javax.swing.JTextField codCajeroTF = null;
	private javax.swing.JLabel jLabel11 = null;
	private javax.swing.JLabel jLabel12 = null;
	private javax.swing.JLabel jLabel13 = null;
	private javax.swing.JTextField tipoEventoTF = null;
	private javax.swing.JTextField fechaEventoTF = null;

	/**
	 * This is the default constructor
	 */
	public ComprobanteBonoRegaloPromo(String fechaTrans,String horaTrans,int numTienda,int numLista,int tiendaOrigen,String codCajero,
				String titular,double montoVendido,double montoBonos,String tipoEvento,String fechaEvento) {
		super();

		this.numLista = numLista;
		this.tipoEvento = tipoEvento;
		this.fechaEvento = fechaEvento;
		this.tiendaCierre = String.valueOf(numTienda);
		this.fecha = fechaTrans;
		this.hora = horaTrans;
		this.codCajero = codCajero;
		this.nombreTitular = titular;
		this.tiendaApertura = String.valueOf(tiendaOrigen);
		this.montoVendido = montoVendido;
		this.montoBonoRegalo = montoBonos;
		
		initialize();


		//insertarLineas(11);
		this.setVisible(true);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setLocation(-1000,-1000);
		this.setSize(550, 810);
		this.setContentPane(getJContentPane());
	}
	
	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(getJPanel1(), null);
			jContentPane.setBackground(java.awt.Color.white);
			jContentPane.setPreferredSize(new java.awt.Dimension(345,576));
		}
		return jContentPane;
	}

	/**
	 * This method initializes jLabel
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel() {
		//if(jLabel == null) {
			jLabel = new javax.swing.JLabel();
			jLabel.setSize(97, 25);
			jLabel.setText("B E C O");
			jLabel.setFont(new java.awt.Font("Sans Serif", java.awt.Font.BOLD, 24));
			jLabel.setLocation(0, 20);
		//}
		return jLabel;
	}
	/**
	 * This method initializes jLabel2
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel2() {
		//if(jLabel2 == null) {
			jLabel2 = new javax.swing.JLabel();
			jLabel2.setSize(65, 15);
			jLabel2.setText("Fecha: ");
			jLabel2.setLocation(406, 20);
			jLabel2.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
			jLabel2.setPreferredSize(new java.awt.Dimension(50,16));
		//}
		return jLabel2;
	}
	/**
	 * This method initializes fecha
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JTextField getFecha() {
		if(fechaTextField == null) {
			fechaTextField = new javax.swing.JTextField();
			fechaTextField.setSize(85, 15);
			fechaTextField.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
			fechaTextField.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
			fechaTextField.setLocation(456, 20);	
			fechaTextField.setText(fecha);
		}
		return fechaTextField;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel() {
		//if(jPanel == null) {
			jPanel = new javax.swing.JPanel();
			jPanel.setLayout(null);
			jPanel.add(getJLabel(), null);
			jPanel.add(getJLabel2(), null);
			jPanel.add(getFecha(), null);
			jPanel.add(getJLabel1(), null);
			jPanel.add(getJLabel4(), null);
			jPanel.add(getJLabel5(), null);
			jPanel.add(getTituloReporte(), null);
			jPanel.add(getJLabel7(), null);
			jPanel.add(getTienda(), null);
			jPanel.add(getHora(), null);
			jPanel.add(getTiendaAperturaTF(), null);
			jPanel.add(getJLabel3(), null);
			jPanel.add(getNombreTitularTF(), null);
			jPanel.add(getJLabel10(), null);
			jPanel.add(getCodCajero(), null);
			jPanel.add(getJLabel11(), null);
			jPanel.add(getJLabel12(), null);
			jPanel.add(getJLabel13(), null);
			jPanel.add(getTipoEventoTF(), null);
			jPanel.add(getFechaEventoTF(), null);
			jPanel.setSize(550, 130);
			jPanel.setLocation(0,0);//Agrega encabezado
			jPanel.setBackground(java.awt.Color.white);
		//}
		return jPanel;
	}
	/**
	 * This method initializes jLabel1
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel1() {
		//if(jLabel1 == null) {
			jLabel1 = new javax.swing.JLabel();
			jLabel1.setSize(185, 15);
			jLabel1.setText("Servicios al Cliente:  Lista de Regalos");
			jLabel1.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
			jLabel1.setLocation(0, 45);
		//}
		return jLabel1;
	}
	/**
	 * This method initializes jLabel4
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel4() {
		//if(jLabel4 == null) {
			jLabel4 = new javax.swing.JLabel();
			jLabel4.setSize(50, 15);
			jLabel4.setText("Tienda:");
			jLabel4.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
			jLabel4.setLocation(0, 60);
		//}
		return jLabel4;
	}
	/**
	 * This method initializes jLabel5
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel5() {
		//if(jLabel5 == null) {
			jLabel5 = new javax.swing.JLabel();
			jLabel5.setSize(65, 15);
			jLabel5.setText("Hora:");
			jLabel5.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
			jLabel5.setLocation(406, 35);
		//}
		return jLabel5;
	}
	/**
	 * This method initializes jLabel6
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getTituloReporte() {
		tituloReporte = new javax.swing.JLabel();
		tituloReporte.setText("Promoción Lista de Regalos No Garantizada Nro. "+numLista);
		tituloReporte.setFont(new java.awt.Font("Sans Serif", java.awt.Font.BOLD, 11));
		tituloReporte.setLocation(70, 75);
		tituloReporte.setSize(412, 15);
		tituloReporte.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		tituloReporte.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		return tituloReporte;
	}
	/**
	 * This method initializes jLabel7
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel7() {
		//if(jLabel7 == null) {
			jLabel7 = new javax.swing.JLabel();
			jLabel7.setSize(80, 15);
			jLabel7.setText("Nombre Titular:");
			jLabel7.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
			jLabel7.setLocation(0, 100);
		//}
		return jLabel7;
	}
	/**
	 * This method initializes tienda
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JTextField getTienda() {
		if(tiendaTextField == null) {
			tiendaTextField = new javax.swing.JTextField();
			tiendaTextField.setSize(80, 15);
			tiendaTextField.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
			tiendaTextField.setLocation(50, 60);
			tiendaTextField.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
			tiendaTextField.setText(tiendaCierre);
		}
		return tiendaTextField;
	}

	/**
	 * This method initializes hora
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JTextField getHora() {
		if(horaTextField == null) {
			horaTextField = new javax.swing.JTextField();
			horaTextField.setSize(85, 15);
			horaTextField.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
			horaTextField.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
			horaTextField.setLocation(456, 35);
			horaTextField.setText(hora);
		}
		return horaTextField;
	}
	/**
	 * This method initializes titular
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JTextField getTiendaAperturaTF() {
		if(tiendaAperturaTF == null){
			tiendaAperturaTF = new javax.swing.JTextField();
			tiendaAperturaTF.setSize(132, 15);
			tiendaAperturaTF.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
			tiendaAperturaTF.setLocation(80, 115);
			tiendaAperturaTF.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
			tiendaAperturaTF.setText(tiendaApertura);
		}
		return tiendaAperturaTF;
	}
	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel1() {
		if(jPanel1 == null) {
			jPanel1 = new javax.swing.JPanel();
			jPanel1.setLayout(null);
			jPanel1.add(getJPanel(), null); //Agrega encabezado
			jPanel1.add(getJLabel8(), null);
			jPanel1.add(getJLabel9(), null);
			jPanel1.add(getMontoVendidoTF(), null);
			jPanel1.add(getMontoBonoRegaloTF(), null);
			jPanel1.setSize(550, 810);
			jPanel1.setBackground(java.awt.Color.white);
			jPanel1.setLocation(0, 0);
		}
		return jPanel1;
	}
	/**
	 * This method initializes jLabel3
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel3() {
		//if(jLabel3 == null) {
			jLabel3 = new javax.swing.JLabel();
			jLabel3.setSize(80, 15);
			jLabel3.setText("Tienda ingreso:");
			jLabel3.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
			jLabel3.setLocation(0, 115);
		//}
		return jLabel3;
	}
	/**
	 * This method initializes tiendaOrigenTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private javax.swing.JTextField getNombreTitularTF() {
		if(nombreTitularTF == null){
			nombreTitularTF = new javax.swing.JTextField();
			nombreTitularTF.setSize(132, 15);
			nombreTitularTF.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
			nombreTitularTF.setLocation(80, 100);
			nombreTitularTF.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
			nombreTitularTF.setText(nombreTitular);
		}
		return nombreTitularTF;
	}
	/**
	 * This method initializes jLabel8
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel8() {
		if(jLabel8 == null) {
			jLabel8 = new javax.swing.JLabel();
			jLabel8.setSize(140, 20);
			jLabel8.setText("Monto Vendido de Lista:");
			jLabel8.setLocation(105, 150);
			jLabel8.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 11));
		}
		return jLabel8;
	}
	/**
	 * This method initializes jLabel9
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel9() {
		if(jLabel9 == null) {
			jLabel9 = new javax.swing.JLabel();
			jLabel9.setSize(140, 20);
			jLabel9.setText("Monto en Bono Regalo:");
			jLabel9.setLocation(105, 170);
			jLabel9.setFont(new java.awt.Font("Sans Serif", java.awt.Font.BOLD, 11));
		}
		return jLabel9;
	}
	/**
	 * This method initializes montoVendido
	 * 
	 * @return javax.swing.JTextField
	 */
	private javax.swing.JTextField getMontoVendidoTF() {
		if(montoVendidoTF == null) {
			montoVendidoTF = new javax.swing.JTextField();
			montoVendidoTF.setSize(190, 20);
			montoVendidoTF.setLocation(245, 150);
			montoVendidoTF.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
			montoVendidoTF.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 11));
			montoVendidoTF.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
			montoVendidoTF.setText(df.format(montoVendido));
		}
		return montoVendidoTF;
	}
	/**
	 * This method initializes montoBonoRegalo
	 * 
	 * @return javax.swing.JTextField
	 */
	private javax.swing.JTextField getMontoBonoRegaloTF() {
		if(montoBonoRegaloTF == null) {
			montoBonoRegaloTF = new javax.swing.JTextField();
			montoBonoRegaloTF.setSize(190, 20);
			montoBonoRegaloTF.setLocation(245, 170);
			montoBonoRegaloTF.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
			montoBonoRegaloTF.setFont(new java.awt.Font("Sans Serif", java.awt.Font.BOLD, 11));
			montoBonoRegaloTF.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
			montoBonoRegaloTF.setText(df.format(montoBonoRegalo));
		}
		return montoBonoRegaloTF;
	}
	/**
	 * This method initializes jLabel10
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel10() {
		if(jLabel10 == null) {
			jLabel10 = new javax.swing.JLabel();
			jLabel10.setSize(65, 15);
			jLabel10.setText("Cajero:");
			jLabel10.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
			jLabel10.setLocation(406, 50);
		}
		return jLabel10;
	}
	/**
	 * This method initializes codCajero
	 * 
	 * @return javax.swing.JTextField
	 */
	private javax.swing.JTextField getCodCajero() {
		if(codCajeroTF == null) {
			codCajeroTF = new javax.swing.JTextField();
			codCajeroTF.setSize(85, 15);
			codCajeroTF.setLocation(456, 50);
			codCajeroTF.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
			codCajeroTF.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
			codCajeroTF.setText(codCajero);
		}
		return codCajeroTF;
	}
	/**
	 * This method initializes jLabel11
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel11() {
		if(jLabel11 == null) {
			jLabel11 = new javax.swing.JLabel();
			jLabel11.setSize(254, 15);
			jLabel11.setText("Comprobante de Bono Regalo por");
			jLabel11.setLocation(135, 60);
			jLabel11.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
			jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
			jLabel11.setFont(new java.awt.Font("Sans Serif", java.awt.Font.BOLD, 11));
		}
		return jLabel11;
	}
	/**
	 * This method initializes jLabel12
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel12() {
		if(jLabel12 == null) {
			jLabel12 = new javax.swing.JLabel();
			jLabel12.setSize(82, 15);
			jLabel12.setText("Tipo de Evento:");
			jLabel12.setLocation(325, 99);
			jLabel12.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
		}
		return jLabel12;
	}
	/**
	 * This method initializes jLabel13
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel13() {
		if(jLabel13 == null) {
			jLabel13 = new javax.swing.JLabel();
			jLabel13.setSize(82, 15);
			jLabel13.setText("Fecha Evento:");
			jLabel13.setLocation(325, 114);
			jLabel13.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
		}
		return jLabel13;
	}
	/**
	 * This method initializes tipoEventoTF
	 * 
	 * @return javax.swing.JTextField
	 */
	private javax.swing.JTextField getTipoEventoTF() {
		if(tipoEventoTF == null) {
			tipoEventoTF = new javax.swing.JTextField();
			tipoEventoTF.setSize(106, 15);
			tipoEventoTF.setLocation(405, 99);
			tipoEventoTF.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
			tipoEventoTF.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
			tipoEventoTF.setText(tipoEvento);
		}
		return tipoEventoTF;
	}
	/**
	 * This method initializes fechaEventoTF
	 * 
	 * @return javax.swing.JTextField
	 */
	private javax.swing.JTextField getFechaEventoTF() {
		if(fechaEventoTF == null) {
			fechaEventoTF = new javax.swing.JTextField();
			fechaEventoTF.setSize(106, 15);
			fechaEventoTF.setLocation(405, 114);
			fechaEventoTF.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
			fechaEventoTF.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
			fechaEventoTF.setText(fechaEvento);
		}
		return fechaEventoTF;
	}
}