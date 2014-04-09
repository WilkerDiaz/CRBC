/*
 * $Id: WindowsPagoProvimillasPuntoAgil.java,v 1.5 2006/09/18 16:54:46 programa4 Exp $
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
 * $Log: WindowsPagoProvimillasPuntoAgil.java,v $
 * Revision 1.5  2006/09/18 16:54:46  programa4
 * Puesta en uso de los DocumentFilter
 *
 * Revision 1.4  2006/09/15 21:34:44  programa4
 * Correcciones solicitadas por credicard
 * Ajuestes solicitados sobre mensajes de respuesta al cajero
 *
 * Revision 1.3  2006/09/06 23:04:04  programa4
 * Agregada validacion para que no permita porcentaje cero 0
 *
 * Revision 1.2  2006/07/13 16:06:04  programa4
 * Ampliada ventana
 *
 * Revision 1.1  2006/07/05 15:25:42  programa4
 * Agregado soporte a provimillas y actualizado anulacion
 *
 * Revision 1.2  2006/06/16 21:32:41  programa4
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
import java.awt.FlowLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.text.AbstractDocument;

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
 * @author analista5 - $Author: programa4 $
 * @version $Revision: 1.5 $ - $Date: 2006/09/18 16:54:46 $
 * @since 20-jun-2005
 */
public class WindowsPagoProvimillasPuntoAgil extends JDialog implements
		KeyListener {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
		.getLogger(WindowsPagoProvimillasPuntoAgil.class);

	/**
	 * long serialVersionUID
	 */
	private static final long serialVersionUID = 9076488643490551292L;

	private JPanel jContentPane = null;

	private JPanel jContentPane1 = null;

	private JPanel jPanelLabelPorcentaje = null;

	private JPanel jLabelTitulo = null;

	private JPanel jPanelTextPorcentaje = null;

	private JPanel jPanelLabelPagoResto = null;

	private JPanel jPanelComboPagoResto = null;

	private JPanel jPanelBotones = null;

	private JButton jButtonAceptar = null;

	private JLabel jLabelPorcentaje = null;

	private JLabel jLabelPagoResto = null;

	private JTextField jTextFieldPorcentaje = null;

	private JButton jButtonCancelar = null;

	private JComboBox jComboBoxPagoResto = null;

	private final int porcentaje;

	private DatosPagoProvimillasPuntoAgil datosPagoProvimillas = null;

	/**
	 * This is the default constructor
	 *
	 * @param _porcentaje
	 *            Porcentaje maximo a cancelar
	 */
	public WindowsPagoProvimillasPuntoAgil(int _porcentaje) {
		super();
		this.porcentaje = _porcentaje;
		initialize();
	}

	/**
	 * This method initializes this
	 */
	private void initialize() {
		this
			.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setComponentOrientation(java.awt.ComponentOrientation.UNKNOWN);
		this.setTitle("Pago Provimillas");
		this.setSize(300, 185);
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
			jContentPane.add(getJContentPane1(), java.awt.BorderLayout.CENTER); // Generated
			jContentPane.add(
				getJPanelBotonAceptar(), java.awt.BorderLayout.SOUTH); // Generated
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
			jContentPane1.setLayout(new FlowLayout(FlowLayout.LEFT));
			jContentPane1.setBackground(new java.awt.Color(226, 226, 222));
			jContentPane1.setBorder(new javax.swing.border.SoftBevelBorder(
				BevelBorder.RAISED));
			jContentPane1.add(getJPanelConsultaPuntos(), null);
			jContentPane1.add(getJPanelTextPorcentaje(), null); // Generated
			jContentPane1.add(getJPanelLabelPagoResto(), null); // Generated
			jContentPane1.add(getJPanelComboPagoResto(), null); // Generated
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
	private JPanel getJPanelConsultaPuntos() {
		if (jPanelLabelPorcentaje == null) {
			jLabelPorcentaje = new JLabel();
			jLabelPorcentaje.setText("Porcentaje a Utilizar:"); // Generated
			jLabelPorcentaje.setPreferredSize(new java.awt.Dimension(200, 50)); // Generated
			jPanelLabelPorcentaje = new JPanel();
			jPanelLabelPorcentaje.setLayout(new BorderLayout()); // Generated
			jPanelLabelPorcentaje.setPreferredSize(new java.awt.Dimension(
				200, 30)); // Generated
			jPanelLabelPorcentaje.setBackground(new java.awt.Color(
				226, 226, 222));
			jPanelLabelPorcentaje.add(
				jLabelPorcentaje, java.awt.BorderLayout.CENTER); // Generated
		}
		return jPanelLabelPorcentaje;
	}

	/**
	 * Imprime un reporte de cierre del punto agil.
	 */
	public void aceptar() {
		boolean error = false;
		// COMPROBAR VALORES
		int porc = Integer.parseInt(getJTextFieldPorcentaje().getText());
		if (porc > 100 || porc > this.porcentaje) {
			error = true;
			MensajesVentanas
				.mensajeError("PORCENTAJE MAYOR AL MAXIMO PERMITIDO");
		}
		if (porc <= 0) {
			error = true;
			MensajesVentanas
				.mensajeError("PORCENTAJE DEBE SER MAYOR A CERO (0)");
		}
		if (!error) {
			LabelValueBean bean = (LabelValueBean) this
				.getJComboBoxPagoResto()
				.getSelectedItem();
			this.datosPagoProvimillas = new DatosPagoProvimillasPuntoAgil(
				String.valueOf(porc), bean.getValue());
			dispose();
		}
	}

	/**
	 * Imprime un reporte de cierre del punto agil.
	 */
	public void cancelar() {
		this.datosPagoProvimillas = null;
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
			cancelar();
		} else if (arg0.getSource().equals(getJButtonAceptar())
				&& (arg0.getKeyCode() == KeyEvent.VK_ENTER || arg0.getKeyCode() == KeyEvent.VK_SPACE)) {
			aceptar();
		} else if (arg0.getSource().equals(getJButtonCancelar())
				&& (arg0.getKeyCode() == KeyEvent.VK_ENTER || arg0.getKeyCode() == KeyEvent.VK_SPACE)) {
			cancelar();
		} else if ((arg0.getKeyCode() == KeyEvent.VK_ENTER)
				&& (arg0.getSource().equals(getJTextFieldPorcentaje()) || arg0
					.getSource()
					.equals(getJComboBoxPagoResto()))) {
			JComponent component = (JComponent) arg0.getSource();
			component.transferFocus();
		} else if (arg0.getSource().equals(getJButtonAceptar())
				|| arg0.getSource().equals(getJButtonCancelar())) {
			if (arg0.getKeyCode() == KeyEvent.VK_KP_DOWN
					|| arg0.getKeyCode() == KeyEvent.VK_KP_RIGHT
					|| arg0.getKeyCode() == KeyEvent.VK_DOWN
					|| arg0.getKeyCode() == KeyEvent.VK_RIGHT) {
				JComponent component = (JComponent) arg0.getSource();
				component.transferFocus();
			} else if (arg0.getKeyCode() == KeyEvent.VK_KP_UP
					|| arg0.getKeyCode() == KeyEvent.VK_KP_LEFT
					|| arg0.getKeyCode() == KeyEvent.VK_UP
					|| arg0.getKeyCode() == KeyEvent.VK_LEFT) {
				JComponent component = (JComponent) arg0.getSource();
				component.transferFocusBackward();
			}
		}
	}

	/*
	 * (sin Javadoc)
	 *
	 *
	 * see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
	 * @param arg0
	 *
	 * @since 10-ago-2005
	 */
	public void keyReleased(KeyEvent arg0) {
		// NO HACER NADA
	}

	/*
	 * (sin Javadoc)
	 *
	 *
	 * see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent) @param
	 * arg0
	 *
	 * @since 10-ago-2005
	 */
	public void keyTyped(KeyEvent arg0) {
		// NO HACER NADA
	}

	/**
	 * This method initializes jPanelTextPorcentaje
	 *
	 *
	 * return javax.swing.JPanel
	 */
	private JPanel getJPanelTextPorcentaje() {
		if (jPanelTextPorcentaje == null) {
			jPanelTextPorcentaje = new JPanel();
			jPanelTextPorcentaje
				.setBackground(new java.awt.Color(226, 226, 222));
			jPanelTextPorcentaje
				.setPreferredSize(new java.awt.Dimension(60, 30)); // Generated
			jPanelTextPorcentaje.add(getJTextFieldPorcentaje(), null); // Generated
		}
		return jPanelTextPorcentaje;
	}

	/**
	 * This method initializes jPanelLabelPagoResto
	 *
	 *
	 * return javax.swing.JPanel
	 */
	private JPanel getJPanelLabelPagoResto() {
		if (jPanelLabelPagoResto == null) {
			jLabelPagoResto = new JLabel();
			jLabelPagoResto.setText("Cancelar resto con esta tarjeta:"); // Generated
			jLabelPagoResto.setPreferredSize(new java.awt.Dimension(200, 50)); // Generated
			jPanelLabelPagoResto = new JPanel();
			jPanelLabelPagoResto.setLayout(new BorderLayout()); // Generated
			jPanelLabelPagoResto.setPreferredSize(new java.awt.Dimension(
				200, 30)); // Generated
			jPanelLabelPagoResto
				.setBackground(new java.awt.Color(226, 226, 222));

			jPanelLabelPagoResto.add(
				jLabelPagoResto, java.awt.BorderLayout.CENTER); // Generated
		}
		return jPanelLabelPagoResto;
	}

	/**
	 * This method initializes jPanelComboPagoResto
	 *
	 *
	 * return javax.swing.JPanel
	 */
	private JPanel getJPanelComboPagoResto() {
		if (jPanelComboPagoResto == null) {
			jPanelComboPagoResto = new JPanel();
			jPanelComboPagoResto
				.setBackground(new java.awt.Color(226, 226, 222));
			jPanelComboPagoResto
				.setPreferredSize(new java.awt.Dimension(60, 30)); // Generated
			jPanelComboPagoResto.add(getJComboBoxPagoResto(), null); // Generated
		}
		return jPanelComboPagoResto;
	}

	/**
	 * This method initializes jPanelBotonAceptar
	 *
	 *
	 * return javax.swing.JPanel
	 */
	private JPanel getJPanelBotonAceptar() {
		if (jPanelBotones == null) {
			jPanelBotones = new JPanel();
			jPanelBotones.setBackground(new java.awt.Color(226, 226, 222));
			jPanelBotones.setSize(new Dimension(300, 100));
			jPanelBotones.add(getJButtonAceptar(), null); // Generated
			jPanelBotones.add(getJButtonCancelar(), null); // Generated
		}
		return jPanelBotones;
	}

	/**
	 * This method initializes jButtonAceptar
	 *
	 *
	 * return javax.swing.JButton
	 */
	private JButton getJButtonAceptar() {
		if (jButtonAceptar == null) {
			jButtonAceptar = new JHighlightButton();
			jButtonAceptar.setText("Aceptar");
			jButtonAceptar.setBackground(new java.awt.Color(226, 226, 222));
			jButtonAceptar
				.setIcon(new javax.swing.ImageIcon(getClass().getResource(
					"/com/becoblohm/cr/gui/resources/icons/ix16x16/check2.png")));
			jButtonAceptar.addKeyListener(this);
		}
		return jButtonAceptar;
	}

	/**
	 * This method initializes jTextField
	 *
	 *
	 * return javax.swing.JTextField
	 */
	private JTextField getJTextFieldPorcentaje() {
		if (jTextFieldPorcentaje == null) {
			jTextFieldPorcentaje = new JTextField();
			jTextFieldPorcentaje
				.setPreferredSize(new java.awt.Dimension(50, 20)); // Generated
			jTextFieldPorcentaje.setText(String.valueOf(this.porcentaje));
			jTextFieldPorcentaje.setInputVerifier(new TextVerifier(
				3, "0123456789"));
			AbstractDocument doc = (AbstractDocument) jTextFieldPorcentaje
				.getDocument();
			doc.setDocumentFilter(new DocumentContentFilter(10, "0123456789"));
			jTextFieldPorcentaje.addKeyListener(this);
		}
		return jTextFieldPorcentaje;
	}

	/**
	 * This method initializes jButtonCancelar
	 *
	 *
	 * return javax.swing.JButton
	 */
	private JButton getJButtonCancelar() {
		if (jButtonCancelar == null) {
			jButtonCancelar = new JHighlightButton();
			jButtonCancelar.setText("Cancelar");
			jButtonCancelar.setBackground(new java.awt.Color(226, 226, 222));
			jButtonCancelar
				.setIcon(new javax.swing.ImageIcon(
					getClass()
						.getResource(
							"/com/becoblohm/cr/gui/resources/icons/ix16x16/delete2.png")));
			jButtonCancelar.addKeyListener(this);
		}
		return jButtonCancelar;
	}

	/**
	 * This method initializes jComboBoxPagoResto
	 *
	 *
	 * return javax.swing.JComboBox
	 */
	private JComboBox getJComboBoxPagoResto() {
		if (jComboBoxPagoResto == null) {
			jComboBoxPagoResto = new JComboBox();
			jComboBoxPagoResto.setBackground(new java.awt.Color(224, 224, 222));
			jComboBoxPagoResto.setPreferredSize(new java.awt.Dimension(50, 20));
			jComboBoxPagoResto.addItem(new LabelValueBean("SI", "0"));
			jComboBoxPagoResto.addItem(new LabelValueBean("NO", "99"));
			jComboBoxPagoResto.addKeyListener(this);
		}
		return jComboBoxPagoResto;
	}

	/**
	 * @return Returns el datosPagoProvimillas.
	 */
	public DatosPagoProvimillasPuntoAgil getDatosPagoProvimillas() {
		return this.datosPagoProvimillas;
	}

} // @jve:decl-index=0:visual-constraint="10,10"

