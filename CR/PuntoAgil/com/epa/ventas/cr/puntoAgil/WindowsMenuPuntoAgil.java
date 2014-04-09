/*
 * $Id: WindowsMenuPuntoAgil.java,v 1.10 2007/04/25 18:46:04 programa8 Exp $
 * ===========================================================================
 * Material Propiedad SuperFerretería EPA C.A.
 *
 * Proyecto		: TarjetaCreditoEPA
 * Paquete		: com.epa.sistemas.ventas.caja.TarjetaCreditoEPA
 * Programa		: WindowsMenuPuntoAgil.java
 * Creado por	: analista5
 * Creado en 	: 20-jun-2005 14:49:11
 * (C) Copyright 2005 SuperFerretería EPA C.A. Todos los Derechos Reservados
 * ---------------------------------------------------------------------------
 * Información de Repositorio:
 * ---------------------------------------------------------------------------
 * Rama de Desarrollo	: $Name:  $
 * Bloqueado por		: $Locker:  $
 * Estado de Revisión	: $State: Exp $
 * ---------------------------------------------------------------------------
 * Actualizaciones:
 * ---------------------------------------------------------------------------
 * $Log: WindowsMenuPuntoAgil.java,v $
 * Revision 1.10  2007/04/25 18:46:04  programa8
 * Version Estable Pto Agil, controlando cargos dos dobles , una sola instancia a datosExternos , control fuera de linea
 *
 * Revision 1.9  2006/09/28 19:08:28  programa4
 * Actualizacion por requerimientos credicard
 * Se cargan datos de programas de lealtad segun banco seleccionado
 * Modificados mensajes de error para hacerlos mas legibles
 * Verificados titulos para indicar version del punto agil
 *
 * Revision 1.8  2006/09/18 16:54:46  programa4
 * Puesta en uso de los DocumentFilter
 *
 * Revision 1.7  2006/09/05 15:29:19  programa4
 * Incrementado campo de version
 *
 * Revision 1.6  2006/08/25 18:17:38  programa4
 * Correccion en anulacion de cheque (para que no solicite lectura banda)
 * Y convertido PuntoAgilSubSistema en singleton en lugar de metodos estaticos
 *
 * Revision 1.5  2006/07/05 15:25:39  programa4
 * Agregado soporte a provimillas y actualizado anulacion
 *
 * Revision 1.4  2006/06/16 21:32:38  programa4
 * Agregada anulacion de pagos.
 * Actualizacion de datos con datos de los pagos de ventas, tarjeta de cred. epa
 * y abonos.
 * Agregado aviso de cierre si han pasado mas de 24 horas del ultimo cierre.
 * Agregado numServicio para poder referir a abonos
 * Agregadas consultas de puntos
 *
 * Revision 1.3  2006/06/10 02:13:04  programa4
 * Corregido bug, en dodne al boton de consultaVoucher, obtenia el texto
 * que correspondia a consultar ultimo voucher
 *
 * Revision 1.2  2006/05/18 18:26:05  programa4
 * Agregado constructores ant para automatizar compilacion
 * Funcionan
 * - Consulta Ultimo Voucher (con reimpresion sin anulacion).
 * - Pre cierre
 * - Cierre
 *
 * Revision 1.1  2006/05/17 20:00:23  programa4
 * Version Inicial con Menu, ConsultaUltimoVoucher y SubSistema
 *
 * Revision 1.8  2006/01/13 14:49:43  sventas
 * Actualizacion 2006-01-13 Reimpresion_Control_de_Pagos_Duplicados
 *
 * Revision 1.7  2005/09/13 00:11:42  sventas
 * actualizacion 12-09-2005
 *
 * Revision 1.6  2005/08/10 21:46:43  analista5
 * Actualización 10/08/2005
 *
 * Revision 1.5  2005/08/06 21:55:09  analista5
 * Actualizacion 06/08/2005
 *
 * Revision 1.4  2005/07/26 23:13:29  programa8
 * Actualizacion_Final 26/07/2005
 *
 * Revision 1.3  2005/06/28 22:41:29  analista5
 * Actualizacion280605_02
 *
 * Revision 1.2  2005/06/27 19:41:45  analista5
 * Actualizacion nuevo(15:32) 27062005
 *
 * Revision 1.1  2005/06/21 13:53:24  analista5
 * Registro Inicial en CVS
 *
 * ===========================================================================
 */
package com.epa.ventas.cr.puntoAgil;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.text.AbstractDocument;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.becoblohm.cr.gui.BarraTitulo;
import com.becoblohm.cr.gui.DocumentContentFilter;
import com.becoblohm.cr.gui.JHighlightButton;
import com.becoblohm.cr.gui.TextVerifier;
import com.becoblohm.cr.utiles.MensajesVentanas;

/**
 * Proyecto: TarjetaCreditoEPA Clase: WindowsMenuPuntoAgil
 *
 * <p>
 * <a href="WindowsMenuPuntoAgil.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author analista5 - $Author: programa8 $
 * @version $Revision: 1.10 $ - $Date: 2007/04/25 18:46:04 $
 * @since 20-jun-2005
 */
public class WindowsMenuPuntoAgil extends JDialog implements KeyListener {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
		.getLogger(WindowsMenuPuntoAgil.class);

	private static final String CONSULTA_VOUCHER = "Consultar Voucher";

	private static final String CONSULTA_ULT_VOUCHER = "Consultar Último Voucher";

	private static final String PRECIERRE = "Pre Cierre del Punto Ágil";

	private static final String CIERRE_PUNTO = "Cierre del Punto Ágil";

	private static final String TEST_CONEXION = "Test de Conexión con los Bancos";
	
	private static final String REIMPRESION_CIERRE = "Reimpresión Último Cierre del Punto";  //  @jve:decl-index=0:

	/**
	 * long serialVersionUID
	 */
	private static final long serialVersionUID = 9076488643490551292L;

	private JPanel jContentPane = null;

	private JPanel jContentPane1 = null;

	private JPanel jPanelConsultaVoucher = null;

	private JPanel jPanelConsultaUltimoVoucher = null;

	private JPanel jPanelPreCierrePunto = null;

	private JPanel jPanelCierrePunto = null;

	private JButton jButtonCierrePunto = null;

	private JButton jButtonPreCierrePunto = null;

	private JButton jButtonConsultaVoucher = null;

	private JButton jButtonConsultaUltimoVoucher = null;

	private JPanel jLabelTitulo = null;

	private JPanel jPanelTestConexion = null;
	
	private JPanel jPanelReimpresionCierre = null;

	private JButton jButtonTestConexion = null;
	
	private JButton jButtonReimpresionCierre = null;

	private JTextField jTextFieldNumeroVoucher = null;

	/**
	 * This is the default constructor
	 */
	public WindowsMenuPuntoAgil() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 */
	private void initialize() {
		this
			.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setComponentOrientation(java.awt.ComponentOrientation.UNKNOWN);
		this.setTitle(Constantes.TITULO_VERSION);
		this.setSize(300, 250);
		this.setContentPane(getJContentPane());
	}

	/**
	 * This method initializes jContentPane
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(new BorderLayout(5, 5));
			jContentPane.setBackground(new java.awt.Color(226, 226, 222));
			jContentPane.setBorder(new javax.swing.border.SoftBevelBorder(
				BevelBorder.RAISED));
			jContentPane.add(getJLabelTitulo(), BorderLayout.NORTH); // Generated
			jContentPane.add(getJContentPane1(), BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jContentPane1
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane1() {
		if (jContentPane1 == null) {

			jContentPane1 = new javax.swing.JPanel();
			jContentPane1.setLayout(new GridLayout(6, 1, 5, 5));
			jContentPane1.setBackground(new java.awt.Color(226, 226, 222));
			jContentPane1.setBorder(new javax.swing.border.SoftBevelBorder(
				BevelBorder.RAISED));
			jContentPane1.add(getJPanelConsultaVoucher(), null);
			jContentPane1.add(getJPanelConsultaUltimoVoucher(), null);
			jContentPane1.add(getJPanelPreCierrePunto(), null); // Generated
			jContentPane1.add(getJPanelCierrePunto(), null); // Generated
			jContentPane1.add(getJPanelTestConexion(), null); // Generated
			jContentPane1.add(getJPanelReimpresionCierre(), null);
		}
		return jContentPane1;
	}

	/**
	 * void
	 */
	private JPanel getJLabelTitulo() {
		if (jLabelTitulo == null) {
			jLabelTitulo = new BarraTitulo(this);
		}
		return jLabelTitulo;
	}

	/**
	 * This method initializes jPanel
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelConsultaVoucher() {
		if (jPanelConsultaVoucher == null) {
			jPanelConsultaVoucher = new JPanel();
			jPanelConsultaVoucher.setLayout(new BorderLayout());
			jPanelConsultaVoucher
				.setBorder(new javax.swing.border.SoftBevelBorder(
					BevelBorder.RAISED));
			jPanelConsultaVoucher.add(
				getJTextFieldNumeroVoucher(), java.awt.BorderLayout.WEST); // Generated
			jPanelConsultaVoucher.add(getJButtonConsultaVoucher(), null); // Generated
		}
		return jPanelConsultaVoucher;
	}

	/**
	 * This method initializes jPanel1
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelConsultaUltimoVoucher() {
		if (jPanelConsultaUltimoVoucher == null) {
			jPanelConsultaUltimoVoucher = new JPanel();
			jPanelConsultaUltimoVoucher.setLayout(new BorderLayout());
			jPanelConsultaUltimoVoucher
				.setBorder(new javax.swing.border.SoftBevelBorder(
					BevelBorder.RAISED));
			jPanelConsultaUltimoVoucher.add(
				getJButtonConsultaUltimoVoucher(), null); // Generated
		}
		return jPanelConsultaUltimoVoucher;
	}

	/**
	 * This method initializes jPanel2
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelPreCierrePunto() {
		if (jPanelPreCierrePunto == null) {
			jPanelPreCierrePunto = new JPanel();
			jPanelPreCierrePunto.setLayout(new BorderLayout());
			jPanelPreCierrePunto
				.setBorder(new javax.swing.border.SoftBevelBorder(
					BevelBorder.RAISED));
			jPanelPreCierrePunto.add(getJButtonPreCierrePunto(), null);
		}
		return jPanelPreCierrePunto;
	}

	/**
	 * This method initializes jPanel3
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelCierrePunto() {
		if (jPanelCierrePunto == null) {
			jPanelCierrePunto = new JPanel();
			jPanelCierrePunto.setLayout(new BorderLayout());
			jPanelCierrePunto.setBorder(new javax.swing.border.SoftBevelBorder(
				BevelBorder.RAISED));
			jPanelCierrePunto.add(getJButtonCierrePunto(), null);
		}
		return jPanelCierrePunto;
	}

	/**
	 * This method initializes jButton
	 *
	 * @return javax.swing.JButton
	 */
	private JButton getJButtonCierrePunto() {
		if (jButtonCierrePunto == null) {
			jButtonCierrePunto = new JHighlightButton(new java.awt.Color(
				192, 192, 255));
			jButtonCierrePunto.setBackground(new java.awt.Color(226, 226, 222));
			jButtonCierrePunto.setText(CIERRE_PUNTO);
			jButtonCierrePunto.addKeyListener(this);
			jButtonCierrePunto.setAction(new AbstractAction(CIERRE_PUNTO) {
				public void actionPerformed(ActionEvent e) {
					iniciarCierrePunto();
				}
			});
		}
		return jButtonCierrePunto;
	}

	/**
	 * This method initializes jButton
	 *
	 * @return javax.swing.JButton
	 */
	private JButton getJButtonPreCierrePunto() {
		if (jButtonPreCierrePunto == null) {
			jButtonPreCierrePunto = new JHighlightButton(new java.awt.Color(
				192, 192, 255));
			jButtonPreCierrePunto.setBackground(new java.awt.Color(
				226, 226, 222));
			jButtonPreCierrePunto.setText(PRECIERRE);
			jButtonPreCierrePunto.addKeyListener(this);
			jButtonPreCierrePunto.setAction(new AbstractAction(PRECIERRE) {
				public void actionPerformed(ActionEvent e) {
					iniciarPreCierrePunto();
				}
			});
		}
		return jButtonPreCierrePunto;
	}

	/**
	 * This method initializes jButtonConsultaVoucher
	 *
	 * @return javax.swing.JButton
	 */
	private JButton getJButtonConsultaVoucher() {
		if (jButtonConsultaVoucher == null) {
			jButtonConsultaVoucher = new JHighlightButton(new java.awt.Color(
				192, 192, 255));
			jButtonConsultaVoucher.setBackground(new java.awt.Color(
				226, 226, 222));
			jButtonConsultaVoucher.addKeyListener(this);
			jButtonConsultaVoucher.setText(CONSULTA_VOUCHER);
			jButtonConsultaVoucher.setAction(new AbstractAction(
					CONSULTA_VOUCHER) {
				public void actionPerformed(ActionEvent e) {
					iniciarConsultaVoucher();
				}
			});

		}
		return jButtonConsultaVoucher;
	}

	/**
	 * This method initializes jButtonConsultaUltimoVoucher
	 *
	 * @return javax.swing.JButton
	 */
	private JButton getJButtonConsultaUltimoVoucher() {
		if (jButtonConsultaUltimoVoucher == null) {
			jButtonConsultaUltimoVoucher = new JHighlightButton(
				new java.awt.Color(192, 192, 255));
			jButtonConsultaUltimoVoucher.setBackground(new java.awt.Color(
				226, 226, 222));
			jButtonConsultaUltimoVoucher.addKeyListener(this);
			jButtonConsultaUltimoVoucher.setText(CONSULTA_ULT_VOUCHER);
			jButtonConsultaUltimoVoucher.setAction(new AbstractAction(
					CONSULTA_ULT_VOUCHER) {
				public void actionPerformed(ActionEvent e) {
					iniciarConsultaUltimoVoucher();
				}
			});
		}
		return jButtonConsultaUltimoVoucher;
	}

	/**
	 * Inicia test de conexion hacia los bancos.
	 *
	 */
	public void iniciarTestConexion() {
		//if (PuntoAgilSubSistema.chequearLineaMerchant()){ //IROJAS 09-07-2009, eliminacion de depenencia de servTda
			PuntoAgilSubSistema.getInstance().testConexionBancos();
		/*}else{
			MensajesVentanas.aviso("La caja se encuentra fuera de linea");
		}*/
		
		dispose();
	}
	
	/**
	 * Inicia Reimpresión del cierre de Punto Ágil.
	 *
	 */
	public void iniciarReimpresionUltCierre() {
		PuntoAgilSubSistema.getInstance().reimprimirUltCierre();
		dispose();
	}

	/**
	 * Inicia la consulta de un voucher
	 *
	 */
	public void iniciarConsultaVoucher() {
		String numSeq = this.getJTextFieldNumeroVoucher().getText();
		if (StringUtils.isNotBlank(numSeq) || StringUtils.isNumeric(numSeq)) {
			PuntoAgilSubSistema.getInstance().mostrarVentanaConsultaVoucher(
				Integer.parseInt(numSeq));
			dispose();
		} else {
			MensajesVentanas
				.mensajeError("Numero de secuencia de Operación Punto Ágil inválido.");
		}
	}

	/**
	 * Inicia la consulta de ultimo voucher. Si no consigue el archivo muestra
	 * un mensaje de error.
	 */
	public void iniciarConsultaUltimoVoucher() {
		PuntoAgilSubSistema.getInstance().mostrarVentanaUltimoVoucher();
		dispose();
	}

	/**
	 * Imprime un reporte de precierre del punto agil.
	 */
	public void iniciarPreCierrePunto() {
		PuntoAgilSubSistema.getInstance().preCierre();
		dispose();
	}

	/**
	 * Imprime un reporte de cierre del punto agil.
	 */
	public void iniciarCierrePunto() {
		//if (PuntoAgilSubSistema.chequearLineaMerchant()){//IROJAS 09-07-2009, eliminacion de depenencia de servTda
		PuntoAgilSubSistema.getInstance().cierre();
	/*	}else{
			MensajesVentanas.aviso("La caja se encuentra fuera de linea");
		}*/
		
		dispose();
	}

	/*
	 * (sin Javadoc)
	 *
	 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
	 *      @param arg0
	 * @since 10-ago-2005
	 */
	public void keyPressed(KeyEvent arg0) {
		if (arg0.getKeyCode() == KeyEvent.VK_ESCAPE) {
			dispose();
		} else if (arg0.getKeyCode() == KeyEvent.VK_ENTER
				|| arg0.getKeyCode() == KeyEvent.VK_SPACE) {
			if (arg0.getSource().equals(getJButtonCierrePunto())) {
				//PuntoAgilSubSistema.chequearLineaMerchant(); //IROJAS 09-07-2009, eliminacion de depenencia de servTda
				iniciarCierrePunto();
			} else if (arg0.getSource().equals(getJButtonPreCierrePunto())) {
				iniciarPreCierrePunto();
			} else if (arg0.getSource().equals(
				getJButtonConsultaUltimoVoucher())) {				
				iniciarConsultaUltimoVoucher();
			} else if (arg0.getSource().equals(getJButtonConsultaVoucher())) {				
				iniciarConsultaVoucher();
			} else if (arg0.getSource().equals(getJButtonTestConexion())) {
				//PuntoAgilSubSistema.chequearLineaMerchant(); //IROJAS 09-07-2009, eliminacion de depenencia de servTda
				iniciarTestConexion();
			}  else if (arg0.getSource().equals(getJButtonReimpresionCierre())) {
				iniciarReimpresionUltCierre();
			}  else if (arg0.getSource().equals(getJTextFieldNumeroVoucher())) {
				getJTextFieldNumeroVoucher().transferFocus();
			}
		} else if (arg0.getSource() instanceof JComponent
				&& (arg0.getKeyCode() == KeyEvent.VK_KP_DOWN
						|| arg0.getKeyCode() == KeyEvent.VK_KP_RIGHT
						|| arg0.getKeyCode() == KeyEvent.VK_DOWN || arg0
					.getKeyCode() == KeyEvent.VK_RIGHT)) {
			JComponent component = (JComponent) arg0.getSource();
			component.transferFocus();
		} else if (arg0.getSource() instanceof JComponent
				&& (arg0.getKeyCode() == KeyEvent.VK_KP_UP
						|| arg0.getKeyCode() == KeyEvent.VK_KP_LEFT
						|| arg0.getKeyCode() == KeyEvent.VK_UP || arg0
					.getKeyCode() == KeyEvent.VK_LEFT)) {
			JComponent component = (JComponent) arg0.getSource();
			component.transferFocusBackward();
		}
	}

	/*
	 * (sin Javadoc)
	 *
	 * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
	 *      @param arg0
	 * @since 10-ago-2005
	 */
	public void keyReleased(KeyEvent arg0) {
		// NO HACER NADA
	}

	/*
	 * (sin Javadoc)
	 *
	 * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent) @param
	 *      arg0
	 * @since 10-ago-2005
	 */
	public void keyTyped(KeyEvent arg0) {
		// NO HACER NADA
	}

	/**
	 * This method initializes jPanelTestConexion
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelTestConexion() {
		if (jPanelTestConexion == null) {
			jPanelTestConexion = new JPanel();
			jPanelTestConexion.setLayout(new BorderLayout());
			jPanelTestConexion
				.setBorder(new javax.swing.border.SoftBevelBorder(
					BevelBorder.RAISED));
			jPanelTestConexion.add(
				getJButtonTestConexion(), java.awt.BorderLayout.NORTH); // Generated
		}
		return jPanelTestConexion;
	}

	/**
	 * This method initializes jButtonTestConexion
	 *
	 * @return javax.swing.JButton
	 */
	private JButton getJButtonTestConexion() {
		if (jButtonTestConexion == null) {
			jButtonTestConexion = new JHighlightButton(new java.awt.Color(
				192, 192, 255));
			jButtonTestConexion
				.setBackground(new java.awt.Color(226, 226, 222));
			jButtonTestConexion.setText(TEST_CONEXION);
			jButtonTestConexion.addKeyListener(this);
			jButtonTestConexion.setAction(new AbstractAction(TEST_CONEXION) {
				public void actionPerformed(ActionEvent e) {
					jButtonTestConexion.setEnabled(false);
					if(jButtonTestConexion.isEnabled())
						iniciarTestConexion();
				}
			});
		}
		jButtonTestConexion.setEnabled(false);
		return jButtonTestConexion;
	}
	
	/**
	 * This method initializes jButtonReimpresionCierre
	 *
	 * @return javax.swing.JButton
	 */
	private JButton getJButtonReimpresionCierre() {
		if (jButtonReimpresionCierre == null) {
			jButtonReimpresionCierre = new JHighlightButton(new java.awt.Color(
				192, 192, 255));
			jButtonReimpresionCierre
				.setBackground(new java.awt.Color(226, 226, 222));
			jButtonReimpresionCierre.setText(REIMPRESION_CIERRE);
			jButtonReimpresionCierre.addKeyListener(this);
			jButtonReimpresionCierre.setAction(new AbstractAction(REIMPRESION_CIERRE) {
				public void actionPerformed(ActionEvent e) {
					iniciarReimpresionUltCierre();
				}
			});
		}
		return jButtonReimpresionCierre;
	}

	/**
	 * This method initializes jTextFieldNumeroVoucher
	 *
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextFieldNumeroVoucher() {
		if (jTextFieldNumeroVoucher == null) {
			jTextFieldNumeroVoucher = new JTextField();
			jTextFieldNumeroVoucher.setPreferredSize(new Dimension(80, 50));
			jTextFieldNumeroVoucher
				.setToolTipText("Número de Secuencia de Operación Punto Ágil"); // Generated
			jTextFieldNumeroVoucher.setInputVerifier(new TextVerifier(
				10, "0123456789"));
			AbstractDocument doc = (AbstractDocument) jTextFieldNumeroVoucher
				.getDocument();
			doc.setDocumentFilter(new DocumentContentFilter(10, "0123456789"));
			jTextFieldNumeroVoucher.addKeyListener(this);

		}
		return jTextFieldNumeroVoucher;
	}
	
	/**
	 * This method initializes jPanelTestConexion
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelReimpresionCierre() {
		if (jPanelReimpresionCierre == null) {
			jPanelReimpresionCierre = new JPanel();
			jPanelReimpresionCierre.setLayout(new BorderLayout());
			jPanelReimpresionCierre
				.setBorder(new javax.swing.border.SoftBevelBorder(
					BevelBorder.RAISED));
			jPanelReimpresionCierre.add(
				getJButtonReimpresionCierre(), java.awt.BorderLayout.NORTH); // Generated
		}
		return jPanelReimpresionCierre;
	}

} // @jve:decl-index=0:visual-constraint="10,10"

