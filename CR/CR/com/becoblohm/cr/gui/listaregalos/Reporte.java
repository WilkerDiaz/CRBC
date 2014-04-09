/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblohm.cr.gui.listaregalos
 * Programa   : Reporte
 * Creado por : rabreu
 * Creado en  : 07/09/2006
 *
 * (c) Becoblohm, C.A.
 * -----------------------------------------------------------------------------
 * Esta clase no es usada actualmente por la aplicación, es una plantilla a partir
 * de la cual generar el diseño de impresión de los reportes usados en lista de
 * regalos para imprimir a traves de la cola de impresión del sistema.
 *  
 */

package com.becoblohm.cr.gui.listaregalos;

import java.text.DecimalFormat;

import javax.swing.JWindow;


/**
 * @author rabreu
 */
public class Reporte extends JWindow {

	private static final long serialVersionUID = 1L;

	private DecimalFormat df = new DecimalFormat("#,##0.00");

	private int numLista;
	private String tipoEvento,fechaEvento,nombreTitular;
	private String tiendaApertura,tiendaCierre;
	private String fecha,hora,codCajero;
	private double montoVendido,montoBonoRegalo,montoCambio,montoAbonado;
	//private int posY = 0, paginas = 1;
	
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
	public Reporte(String fechaTrans, String horaTrans,int numTienda,int numLista,int tiendaOrigen,String codCajero,
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

//	private void insertarNuevaPagina() {
//		paginas++;
//		posY = (810*(paginas-1)); // Coloca posY en una nueva página
//		this.setSize(550,810*(paginas)); // Agrega otra pagina al frame
//		this.getJPanel1().setSize(550,810*paginas); // Agrega otra pagina al JPanel1
//
//		jPanel1.add(getJPanel(), null); //Agrega encabezado
//		jPanel.setLocation(0, posLineaActual());//Agrega encabezado
//		insertarLineas(11);
//		
//		JLabel pagina = new JLabel("Pág. "+paginas);
//		pagina.setSize(50, 15);
//		pagina.setFont(new java.awt.Font("Sans Serif", java.awt.Font.PLAIN, 10));
//		pagina.setLocation(245, 25+((paginas-1)*810));
//		jPanel.add(pagina,null);	
//	}
//	
//	private void insertarLinea(){
//		posY += 15;
//		if(posY>675*paginas)
//			insertarNuevaPagina();
//	}
//	
//	private void insertarLineas(int lineas){
//		int nuevasLineas = 15*lineas;
//		posY+=nuevasLineas;
//		if(posY>675*paginas)
//			insertarNuevaPagina();
//	}
//	
//	private int posLineaActual(){
//		return posY;
//	}

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
		jLabel.setLocation(0, 0);
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
		jLabel2.setLocation(389, 2);
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
		fechaTextField.setLocation(439, 2);
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
		jLabel1.setLocation(0, 25);
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
		jLabel4.setLocation(0, 40);
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
		jLabel5.setLocation(389, 17);
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
		tiendaTextField.setLocation(50, 40);
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
		horaTextField.setLocation(439, 17);
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
		jLabel10.setLocation(389, 32);
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
		codCajeroTF.setLocation(439, 32);
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
		cambioEfectivoTF.setFont(new java.awt.Font("Sans Serif", java.awt.Font.BOLD, 12));
		cambioEfectivoTF.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
		cambioEfectivoTF.setText(df.format(montoCambio));
		return cambioEfectivoTF;
	}
}