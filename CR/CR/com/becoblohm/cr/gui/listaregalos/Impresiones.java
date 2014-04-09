/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblohm.cr.gui
 * Programa   : Utilitarios.java
 * Creado por : Victor Medina <linux@epa.com.ve>
 * Creado en  : Mar 4, 2004 - 10:52:57 AM
 *
 * (c) Becoblohm, C.A.
 * -----------------------------------------------------------------------------
 * 				Actualizaciones
 * -----------------------------------------------------------------------------
 * Versión     : 1
 * Fecha       : Mar 4, 2004
 * Analista    : Victor Medina <linux@epa.com.ve>
 * Descripción :
 * =============================================================================
 */
package com.becoblohm.cr.gui.listaregalos;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.border.BevelBorder;

import com.becoblohm.cr.CR;
import com.becoblohm.cr.excepciones.ExcepcionCr;
import com.becoblohm.cr.gui.JHighlightButton;
import com.becoblohm.cr.utiles.MensajesVentanas;
/**
 * Descripción:
 * 
 */
public class Impresiones extends JDialog implements KeyListener, ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private javax.swing.JPanel jContentPane = null;
	private javax.swing.JPanel jPanel3 = null;
	private javax.swing.JPanel jPanel4 = null;
	private javax.swing.JPanel jPanel6 = null;
	private javax.swing.JPanel jPanel5 = null;
	private javax.swing.JLabel jLabel = null;
	private javax.swing.JPanel jPanel1 = null;
		
	private javax.swing.JButton jButton3 = null;
	private javax.swing.JButton jButton1 = null;
	private javax.swing.JButton jButton5 = null;
	private javax.swing.JButton jButton2 = null;
	private javax.swing.JButton jButton4 = null;


	private javax.swing.JPanel jPanel2 = null;
	private javax.swing.JButton jButtonCancelar = null;
	/**
	 * This is the default constructor
	 */
	public Impresiones() {
		super(MensajesVentanas.ventanaActiva);
		initialize();
		agregarListeners();
	}

	/**
	 * This method initializes this
	 *
	 * @return void
	 */
	private void initialize() {
		this.setSize(360, 330);
		this.setContentPane(getJContentPane());
		this.setUndecorated(true);
		this.setModal(true);
		this.setBackground(new java.awt.Color(226,226,222));
	}

	/**
	 * This method initializes jContentPane
	 *
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout7 = new java.awt.FlowLayout();
			layFlowLayout7.setHgap(5);
			layFlowLayout7.setVgap(5);
			jContentPane.setLayout(layFlowLayout7);
			jContentPane.add(getJPanel1(), null);
			jContentPane.add(getJPanel2(), null);
			jContentPane.setBorder(new javax.swing.border.SoftBevelBorder(BevelBorder.RAISED));
			jContentPane.setBackground(new java.awt.Color(226,226,222));
			jContentPane.setPreferredSize(new java.awt.Dimension(360,330));
		}

		return jContentPane;
	}

	/**
	 * This method initializes jPanel3
	 *
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel3() {
		if(jPanel3 == null) {
			jPanel3 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout3 = new java.awt.FlowLayout();
			layFlowLayout3.setAlignment(java.awt.FlowLayout.CENTER);
			layFlowLayout3.setHgap(0);
			layFlowLayout3.setVgap(0);
			jPanel3.setLayout(layFlowLayout3);
			jPanel3.add(getJPanel5(), null);
			jPanel3.setPreferredSize(new java.awt.Dimension(336,50));
			jPanel3.setBackground(new java.awt.Color(69,107,127));
		}

		return jPanel3;
	}
	/**
	 * This method initializes jPanel4
	 *
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel4() {
		if(jPanel4 == null) {
			jPanel4 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout5 = new java.awt.FlowLayout();
			layFlowLayout5.setAlignment(java.awt.FlowLayout.CENTER);
			layFlowLayout5.setHgap(0);
			layFlowLayout5.setVgap(0);
			jPanel4.setLayout(layFlowLayout5);
			jPanel4.add(getJPanel6(), null);
			jPanel4.setPreferredSize(new java.awt.Dimension(336,192));
			jPanel4.setBackground(new java.awt.Color(242,242,238));
		}

		return jPanel4;
	}
	/**
	 * This method initializes jPanel6
	 *
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel6() {
		if(jPanel6 == null) {
			jPanel6 = new javax.swing.JPanel();
			java.awt.GridLayout layGridLayout1 = new java.awt.GridLayout();
			layGridLayout1.setRows(5);
			layGridLayout1.setColumns(1);
			layGridLayout1.setHgap(0);
			jPanel6.setLayout(layGridLayout1);
			jPanel6.add(getJButton1(), null);
			jPanel6.add(getJButton2(), null);
			jPanel6.add(getJButton3(), null);
			jPanel6.add(getJButton4(), null);
			jPanel6.add(getJButton5(), null);
			jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Reportes disponibles", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12), java.awt.Color.black));
			jPanel6.setPreferredSize(new java.awt.Dimension(332,188));
			jPanel6.setBackground(new java.awt.Color(242,242,238));
		}

		return jPanel6;
	}
	/**
	 * This method initializes jPanel5
	 *
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel5() {
		if(jPanel5 == null) {
			jPanel5 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout2 = new java.awt.FlowLayout();
			layFlowLayout2.setAlignment(java.awt.FlowLayout.LEFT);
			jPanel5.setLayout(layFlowLayout2);
			jPanel5.add(getJLabel(), null);
			jPanel5.setPreferredSize(new java.awt.Dimension(320,48));
			jPanel5.setBackground(new java.awt.Color(69,107,127));
			layFlowLayout2.setVgap(10);
		}

		return jPanel5;
	}
	/**
	 * This method initializes jLabel
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel() {
		if(jLabel == null) {
			jLabel = new javax.swing.JLabel();
			jLabel.setText("Impresiones");
			jLabel.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 18));
			jLabel.setForeground(java.awt.Color.white);
			jLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix32x32/printer.png")));
		}

		return jLabel;
	}
	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel1() {
		if(jPanel1 == null) {
			jPanel1 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout31 = new java.awt.FlowLayout();
			layFlowLayout31.setHgap(0);
			layFlowLayout31.setVgap(0);
			jPanel1.setLayout(layFlowLayout31);
			jPanel1.add(getJPanel3(), null);
			jPanel1.add(getJPanel4(), null);
			jPanel1.setPreferredSize(new java.awt.Dimension(340,245));
			jPanel1.setBackground(new java.awt.Color(242,242,238));
			jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(69,107,127),1));
		}

		return jPanel1;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private javax.swing.JButton getJButton1() {
		if(jButton1 == null) {
			jButton1 = new JHighlightButton();
			jButton1.setText("F1 - Imprimir Reporte de Lista");
			jButton1.setBackground(new java.awt.Color(226,226,222));
			jButton1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
		}

		return jButton1;
	}
	
	/**
	 * This method initializes jButton3
	 * 
	 * @return javax.swing.JButton
	 */
	private javax.swing.JButton getJButton3() {
		if(jButton3 == null) {
			jButton3 = new JHighlightButton();
			jButton3.setText("F3 - Reimprimir Contrato de Apertura");
			jButton3.setBackground(new java.awt.Color(226,226,222));
			jButton3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
		}

		return jButton3;
	}

	/**
	 * This method initializes jButton5
	 * 
	 * @return javax.swing.JButton
	 */
	private javax.swing.JButton getJButton5() {
		if(jButton5 == null) {
			jButton5 = new JHighlightButton();
			jButton5.setPreferredSize(new java.awt.Dimension(145,26));
			jButton5.setBackground(new java.awt.Color(226,226,222));
			jButton5.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 12));
			jButton5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
			jButton5.setVisible(false);
		}

		return jButton5;
	}

	private void agregarListeners() {
		jButton1.addKeyListener(this);
		jButton1.addActionListener(this);
	
		jButton2.addKeyListener(this);
		jButton2.addActionListener(this);

		jButton3.addKeyListener(this);
		jButton3.addActionListener(this);

		jButton4.addKeyListener(this);
		jButton4.addActionListener(this);
			
		jButton5.addKeyListener(this);
		jButton5.addActionListener(this);

		jButtonCancelar.addKeyListener(this);
		jButtonCancelar.addActionListener(this);			
	}	
	
	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private javax.swing.JButton getJButton2() {
		if(jButton2 == null) {
			jButton2 = new JHighlightButton();
			jButton2.setBackground(new java.awt.Color(226,226,222));
			jButton2.setText("F2 - Imprimir Precierre (para Titular)");
			jButton2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
		}

		return jButton2;
	}
	/**
	 * This method initializes jButton4
	 * 
	 * @return javax.swing.JButton
	 */
	private javax.swing.JButton getJButton4() {
		if(jButton4 == null) {
			jButton4 = new JHighlightButton();
			jButton4.setBackground(new java.awt.Color(226,226,222));
			jButton4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
			jButton4.setText("Reimprimir BonoRegalo Promo");
			jButton4.setVisible(false);
		}

		return jButton4;
	}
	/**
	 * This method initializes jPanel2
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel2() {
		if(jPanel2 == null) {
			jPanel2 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout1 = new java.awt.FlowLayout();
			layFlowLayout1.setHgap(0);
			layFlowLayout1.setVgap(0);
			layFlowLayout1.setAlignment(java.awt.FlowLayout.RIGHT);
			jPanel2.setLayout(layFlowLayout1);
			jPanel2.add(getJButtonCancelar(), null);
			jPanel2.setPreferredSize(new java.awt.Dimension(340,30));
			jPanel2.setBackground(new java.awt.Color(226,226,222));
		}
		return jPanel2;
	}
	/**
	 * This method initializes jButtonCancelar
	 *
	 * @return javax.swing.JButton
	 */
	private javax.swing.JButton getJButtonCancelar() {
		if(jButtonCancelar == null) {
			jButtonCancelar = new JHighlightButton();
			jButtonCancelar.setText("Cancelar");
			jButtonCancelar.setBackground(new java.awt.Color(226,226,222));
			jButtonCancelar.setIcon(new ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix16x16/delete2.png")));
		}

		return jButtonCancelar;
	}

	/* (no Javadoc)
	 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
	 */
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
			this.dispose();
		else if(e.getKeyCode() == KeyEvent.VK_F1)
			jButton1.doClick();
		else if(e.getKeyCode() == KeyEvent.VK_F2)
			jButton2.doClick();
	}

	/* (no Javadoc)
	 * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
	 */
	public void keyReleased(KeyEvent e) {

	}

	/* (no Javadoc)
	 * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
	 */
	public void keyTyped(KeyEvent e) {
		
	}

	/* (no Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(jButton1))
			try {
				CR.meServ.getListaRegalos().imprimirReporteLista(false);
			} catch (ExcepcionCr e1) {
				MensajesVentanas.mensajeError(e1.getMensaje());
			}
		else if(e.getSource().equals((jButton2)))
			try {
				CR.meServ.getListaRegalos().imprimirPrecierre();
			} catch (ExcepcionCr e1) {
				e1.printStackTrace();
				MensajesVentanas.aviso(e1.getMensaje());
			}
		else if(e.getSource().equals(jButtonCancelar))
			this.dispose();
		else if(e.getSource().equals(jButton3)) {
			try {
				CR.meServ.getListaRegalos().imprimirReporteLista(true);
			} catch (ExcepcionCr e1) {
				e1.printStackTrace();
				MensajesVentanas.aviso(e1.getMensaje());
			}
		}/* else if(e.getSource().equals(jButton4)) {
			try {
				double temp = (CR.meServ.getListaRegalos().getMontoVendidos()/5000)*0.05;
				double montoBonos = Math.floor(temp)*5000; //Bonos en multiplo de 5000
				PrintUtilities.imprimirComprobanteBonoRegaloPromo(CR.meServ.getListaRegalos(),montoBonos);
			} catch (ExcepcionCr e1) {
				e1.printStackTrace();
				MensajesVentanas.aviso(e1.getMensaje());
			}
		}*/
	}
}