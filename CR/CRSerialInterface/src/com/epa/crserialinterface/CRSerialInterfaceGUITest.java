/*
 * $Id: CRSerialInterfaceGUITest.java,v 1.6 2005/03/09 20:50:04 programa8 Exp $
 * ===========================================================================
 * Material Propiedad SuperFerretería EPA C.A. 
 *
 * Proyecto		: CRSerialInterface
 * Paquete		: com.epa.crserialinterface
 * Programa		: CRSerialInterfaceGUITest.java
 * Creado por	: Victor Medina - linux@epa.com.ve -
 * Creado en 	: Mar 29, 2004 9:07:19 AM
 * (C) Copyright 2004 SuperFerretería EPA C.A. Todos los Derechos Reservados
 * 
 * ---------------------------------------------------------------------------
 * Actualizaciones:
 * ---------------------------------------------------------------------------
 * $Log: CRSerialInterfaceGUITest.java,v $
 * Revision 1.6  2005/03/09 20:50:04  programa8
 * CRSerialInterface al 09/03/2005
 *
 * Revision 1.1  2004/07/05 18:42:35  vmedina
 * *** empty log message ***
 *
 * Revision 1.6  2004/05/20 19:27:27  vmedina
 * *** empty log message ***
 *
 * Revision 1.5  2004/04/11 16:45:14  vmedina
 * *** empty log message ***
 *
 * Revision 1.4  2004/04/06 20:18:14  vmedina
 * *** empty log message ***
 *
 * Revision 1.3  2004/03/31 17:38:18  vmedina
 * Este es el primer comit funcional, todas las funciones basicas estan
 * implementadas y funcionales, esta version es tuilizable
 *
 * Revision 1.2  2004/03/29 19:45:32  vmedina
 * *** empty log message ***
 *
 * Revision 1.1  2004/03/29 19:40:11  vmedina
 * Entrada Inicial al CVS, solo tienen las estructuras basicas y los algoritmos principales
 * Falta comenzar la Integración
 *
 * ===========================================================================
 */
package com.epa.crserialinterface;

import gnu.io.CommPortIdentifier;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Enumeration;

import javax.swing.JFrame;

/**
 * <p>Indicar aquí, en una línea que hace la clase</p>
 * <p>Agregar parrafos que describen como se usa la Clase y un resumen de como 
 * funciona.</p>
 * @author Victor Medina - linux@epa.com.ve -  
 * @version $Revision: 1.6 $<br>$Date: 2005/03/09 20:50:04 $
 * <!-- A continuación indicar referencia a otras páginas o clases parecidas -->
 * <!-- o relacionados, pundiendo indicar inclusive los métodos relacionados -->
 * <!-- por ejemplo: @see     <a href="package-summary.html#charenc">Character encodings</a> -->
 * <!-- por ejemplo: @see     java.lang.Object 								 -->
 * <!-- por ejemplo: @see     java.lang.Object#toNewString() 				 -->
 * @since   CRSerialInterface 1.0
 */
public class CRSerialInterfaceGUITest extends JFrame implements MouseListener
{
	private javax.swing.JPanel jContentPane = null;

	private javax.swing.JPanel jPanel = null;
	private javax.swing.JPanel jPanel1 = null;
	private javax.swing.JLabel jLabel = null;
	private javax.swing.JPanel jPanel2 = null;
	private javax.swing.JLabel jLabel1 = null;
	private javax.swing.JPanel jPanel3 = null;
	private javax.swing.JComboBox jComboBox = null;
	private javax.swing.JPanel jPanel4 = null;
	private javax.swing.JPanel jPanel5 = null;
	private javax.swing.JPanel jPanel6 = null;
	private javax.swing.JPanel jPanel7 = null;
	private javax.swing.JPanel jPanel8 = null;
	private javax.swing.JPanel jPanel9 = null;
	private javax.swing.JPanel jPanel10 = null;
	private javax.swing.JLabel jLabel2 = null;
	private javax.swing.JLabel jLabel3 = null;
	private javax.swing.JLabel jLabel4 = null;
	private javax.swing.JLabel jLabel5 = null;
	private javax.swing.JLabel jLabel6 = null;
	private javax.swing.JLabel jLabel7 = null;
	private javax.swing.JComboBox jComboBox1 = null;
	private javax.swing.JComboBox jComboBox2 = null;
	private javax.swing.JComboBox jComboBox3 = null;
	private javax.swing.JComboBox jComboBox4 = null;
	private javax.swing.JComboBox jComboBox5 = null;
	private javax.swing.JComboBox jComboBox6 = null;
	private javax.swing.JPanel jPanel11 = null;
	private javax.swing.JPanel jPanel12 = null;
	private javax.swing.JScrollPane jScrollPane = null;
	private javax.swing.JTextArea jTextArea = null;
	private javax.swing.JScrollPane jScrollPane1 = null;
	private javax.swing.JTextArea jTextArea1 = null;
	private javax.swing.JPanel jPanel13 = null;
	private javax.swing.JButton jButton = null;
	private javax.swing.JButton jButton1 = null;
	private javax.swing.JButton jButton2 = null;
	private javax.swing.JButton jButton3 = null;
	
	private Parameters parametros;
	private Connection conexion;
	
	public static void main(String[] args)
	{
		CRSerialInterfaceGUITest crTestGUI = new CRSerialInterfaceGUITest();
		crTestGUI.show();
	}
	/**
	 * This is the default constructor
	 */
	public CRSerialInterfaceGUITest()
	{
		super();
		initialize();
		
		jButton.addMouseListener(this);
		jButton1.addMouseListener(this);
		jButton2.addMouseListener(this);
		jButton3.addMouseListener(this);
		
		parametros = new Parameters();
		conexion = new Connection(parametros, jTextArea, jTextArea1);
		
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize()
	{
		this.setSize(640, 440);
		this.setContentPane(getJContentPane());
		this.setBackground(new java.awt.Color(226,226,222));  // Generated
		this.setTitle("CRSerialInterface Test - Ver. 1.0");  // Generated
		this.setResizable(false);
	}
	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane()
	{
		if (jContentPane == null)
		{
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(new java.awt.FlowLayout());
			jContentPane.add(getJPanel(), null);  // Generated
			jContentPane.add(getJPanel13(), null);  // Generated
			jContentPane.setBackground(new java.awt.Color(226,226,226));  // Generated
		}
		return jContentPane;
	}
	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel() {
		if(jPanel == null) {
			try {
				jPanel = new javax.swing.JPanel();  // Generated
				java.awt.FlowLayout layFlowLayout1 = new java.awt.FlowLayout();  // Generated
				layFlowLayout1.setAlignment(java.awt.FlowLayout.CENTER);  // Generated
				layFlowLayout1.setHgap(0);
				layFlowLayout1.setVgap(0);
				jPanel.setLayout(layFlowLayout1);  // Generated
				jPanel.add(getJPanel1(), null);  // Generated
				jPanel.add(getJPanel2(), null);  // Generated
				jPanel.add(getJPanel11(), null);  // Generated
				jPanel.add(getJPanel12(), null);  // Generated
				jPanel.setPreferredSize(new Dimension(610,360));
				jPanel.setBackground(new java.awt.Color(242,242,238));  // Generated
				jPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(69,107,127),1));  // Generated
			}
			catch (java.lang.Throwable e) {
				//  Do Something
			}
		}
		return jPanel;
	}
	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel1() {
		if(jPanel1 == null) {
			try {
				jPanel1 = new javax.swing.JPanel();  // Generated
				java.awt.FlowLayout layFlowLayout2 = new java.awt.FlowLayout();  // Generated
				layFlowLayout2.setAlignment(java.awt.FlowLayout.LEFT);  // Generated
				jPanel1.setLayout(layFlowLayout2);  // Generated
				jPanel1.add(getJLabel(), null);  // Generated
				jPanel1.setBackground(new java.awt.Color(69,107,127));  // Generated
				jPanel1.setPreferredSize(new java.awt.Dimension(610,60));  // Generated
			}
			catch (java.lang.Throwable e) {
				//  Do Something
			}
		}
		return jPanel1;
	}
	/**
	 * This method initializes jLabel
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel() {
		if(jLabel == null) {
			try {
				jLabel = new javax.swing.JLabel();  // Generated
				jLabel.setText("CRSerialInterface GUI Test - ver. 1.0");  // Generated
				jLabel.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 18));  // Generated
				jLabel.setForeground(java.awt.Color.white);  // Generated
				jLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/epa/crserialinterface/CharDev.png")));  // Generated
			}
			catch (java.lang.Throwable e) {
				//  Do Something
			}
		}
		return jLabel;
	}
	/**
	 * This method initializes jPanel2
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel2() {
		if(jPanel2 == null) {
			try {
				jPanel2 = new javax.swing.JPanel();  // Generated
				java.awt.FlowLayout layFlowLayout11 = new java.awt.FlowLayout();  // Generated
				layFlowLayout11.setAlignment(java.awt.FlowLayout.LEFT);  // Generated
				layFlowLayout11.setHgap(5);
				layFlowLayout11.setVgap(5);
				jPanel2.setLayout(layFlowLayout11);  // Generated
				jPanel2.add(getJPanel3(), null);  // Generated
				jPanel2.add(getJPanel4(), null);  // Generated
				jPanel2.setBackground(new java.awt.Color(242,242,238));  // Generated
				jPanel2.setPreferredSize(new Dimension(580,130));
				jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.gray,1), "Parámetros del  Puerto:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10), java.awt.Color.black));  // Generated
			}
			catch (java.lang.Throwable e) {
				//  Do Something
			}
		}
		return jPanel2;
	}
	/**
	 * This method initializes jLabel1
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel1() {
		if(jLabel1 == null) {
			try {
				jLabel1 = new javax.swing.JLabel();  // Generated
				jLabel1.setText("Nombre del Puerto: ");  // Generated
				jLabel1.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));  // Generated
				jLabel1.setPreferredSize(new Dimension(110,15));
			}
			catch (java.lang.Throwable e) {
				//  Do Something
			}
		}
		return jLabel1;
	}
	/**
	 * This method initializes jPanel3
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel3() {
		if(jPanel3 == null) {
			try {
				jPanel3 = new javax.swing.JPanel();  // Generated
				java.awt.FlowLayout layFlowLayout21 = new java.awt.FlowLayout();  // Generated
				layFlowLayout21.setAlignment(java.awt.FlowLayout.LEFT);  // Generated
				layFlowLayout21.setVgap(0);
				layFlowLayout21.setHgap(0);
				jPanel3.setLayout(layFlowLayout21);  // Generated
				jPanel3.add(getJLabel1(), null);  // Generated
				jPanel3.add(getJComboBox(), null);  // Generated
				jPanel3.setBackground(new java.awt.Color(242,242,238));  // Generated
			}
			catch (java.lang.Throwable e) {
				//  Do Something
			}
		}
		return jPanel3;
	}
	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private javax.swing.JComboBox getJComboBox() {
		if(jComboBox == null) {
			try {
				jComboBox = new javax.swing.JComboBox();  // Generated
				jComboBox.setPreferredSize(new java.awt.Dimension(130,20));  // Generated
				jComboBox.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));  // Generated
				jComboBox.setBackground(new Color(242,242,238));
				CommPortIdentifier portId;
				Enumeration	       en = CommPortIdentifier.getPortIdentifiers();
				// Recorremos la lista de Puertos
				while (en.hasMoreElements()) {
					portId = (CommPortIdentifier) en.nextElement();
					if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
						jComboBox.addItem(portId.getName());
					}
				}
			}
			catch (java.lang.Throwable e) {
				//  Do Something
			}
		}
		return jComboBox;
	}
	/**
	 * This method initializes jPanel4
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel4() {
		if(jPanel4 == null) {
			try {
				jPanel4 = new javax.swing.JPanel();  // Generated
				java.awt.GridLayout layGridLayout3 = new java.awt.GridLayout();  // Generated
				layGridLayout3.setRows(3);  // Generated
				layGridLayout3.setColumns(1);
				layGridLayout3.setHgap(0);
				layGridLayout3.setVgap(0);
				jPanel4.setLayout(layGridLayout3);  // Generated
				jPanel4.add(getJPanel5(), null);  // Generated
				jPanel4.add(getJPanel6(), null);  // Generated
				jPanel4.add(getJPanel7(), null);  // Generated
				jPanel4.add(getJPanel8(), null);  // Generated
				jPanel4.add(getJPanel9(), null);  // Generated
				jPanel4.add(getJPanel10(), null);  // Generated
				jPanel4.setBackground(new java.awt.Color(242,242,238));  // Generated
				jPanel4.setPreferredSize(new Dimension(555,70));
			}
			catch (java.lang.Throwable e) {
				//  Do Something
			}
		}
		return jPanel4;
	}
	/**
	 * This method initializes jPanel5
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel5() {
		if(jPanel5 == null) {
			try {
				jPanel5 = new javax.swing.JPanel();  // Generated
				java.awt.FlowLayout layFlowLayout4 = new java.awt.FlowLayout();  // Generated
				layFlowLayout4.setAlignment(java.awt.FlowLayout.LEFT);  // Generated
				layFlowLayout4.setVgap(0);
				layFlowLayout4.setHgap(0);
				jPanel5.setLayout(layFlowLayout4);  // Generated
				jPanel5.add(getJLabel2(), null);  // Generated
				jPanel5.add(getJComboBox1(), null);  // Generated
				jPanel5.setBackground(new java.awt.Color(242,242,238));  // Generated
			}
			catch (java.lang.Throwable e) {
				//  Do Something
			}
		}
		return jPanel5;
	}
	/**
	 * This method initializes jPanel6
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel6() {
		if(jPanel6 == null) {
			try {
				jPanel6 = new javax.swing.JPanel();  // Generated
				java.awt.FlowLayout layFlowLayout5 = new java.awt.FlowLayout();  // Generated
				layFlowLayout5.setAlignment(java.awt.FlowLayout.LEFT);  // Generated
				layFlowLayout5.setVgap(0);
				layFlowLayout5.setHgap(0);
				jPanel6.setLayout(layFlowLayout5);  // Generated
				jPanel6.add(getJLabel3(), null);  // Generated
				jPanel6.add(getJComboBox2(), null);  // Generated
				jPanel6.setBackground(new java.awt.Color(242,242,238));  // Generated
			}
			catch (java.lang.Throwable e) {
				//  Do Something
			}
		}
		return jPanel6;
	}
	/**
	 * This method initializes jPanel7
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel7() {
		if(jPanel7 == null) {
			try {
				jPanel7 = new javax.swing.JPanel();  // Generated
				java.awt.FlowLayout layFlowLayout6 = new java.awt.FlowLayout();  // Generated
				layFlowLayout6.setAlignment(java.awt.FlowLayout.LEFT);  // Generated
				layFlowLayout6.setHgap(0);
				layFlowLayout6.setVgap(0);
				jPanel7.setLayout(layFlowLayout6);  // Generated
				jPanel7.add(getJLabel4(), null);  // Generated
				jPanel7.add(getJComboBox3(), null);  // Generated
				jPanel7.setBackground(new java.awt.Color(242,242,238));  // Generated
			}
			catch (java.lang.Throwable e) {
				//  Do Something
			}
		}
		return jPanel7;
	}
	/**
	 * This method initializes jPanel8
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel8() {
		if(jPanel8 == null) {
			try {
				jPanel8 = new javax.swing.JPanel();  // Generated
				java.awt.FlowLayout layFlowLayout7 = new java.awt.FlowLayout();  // Generated
				layFlowLayout7.setAlignment(java.awt.FlowLayout.LEFT);  // Generated
				layFlowLayout7.setHgap(0);
				layFlowLayout7.setVgap(0);
				jPanel8.setLayout(layFlowLayout7);  // Generated
				jPanel8.add(getJLabel5(), null);  // Generated
				jPanel8.add(getJComboBox4(), null);  // Generated
				jPanel8.setBackground(new java.awt.Color(242,242,238));  // Generated
			}
			catch (java.lang.Throwable e) {
				//  Do Something
			}
		}
		return jPanel8;
	}
	/**
	 * This method initializes jPanel9
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel9() {
		if(jPanel9 == null) {
			try {
				jPanel9 = new javax.swing.JPanel();  // Generated
				java.awt.FlowLayout layFlowLayout8 = new java.awt.FlowLayout();  // Generated
				layFlowLayout8.setAlignment(java.awt.FlowLayout.LEFT);  // Generated
				layFlowLayout8.setHgap(0);
				layFlowLayout8.setVgap(0);
				jPanel9.setLayout(layFlowLayout8);  // Generated
				jPanel9.add(getJLabel6(), null);  // Generated
				jPanel9.add(getJComboBox5(), null);  // Generated
				jPanel9.setBackground(new java.awt.Color(242,242,238));  // Generated
			}
			catch (java.lang.Throwable e) {
				//  Do Something
			}
		}
		return jPanel9;
	}
	/**
	 * This method initializes jPanel10
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel10() {
		if(jPanel10 == null) {
			try {
				jPanel10 = new javax.swing.JPanel();  // Generated
				java.awt.FlowLayout layFlowLayout9 = new java.awt.FlowLayout();  // Generated
				layFlowLayout9.setAlignment(java.awt.FlowLayout.LEFT);  // Generated
				layFlowLayout9.setHgap(0);
				layFlowLayout9.setVgap(0);
				jPanel10.setLayout(layFlowLayout9);  // Generated
				jPanel10.add(getJLabel7(), null);  // Generated
				jPanel10.add(getJComboBox6(), null);  // Generated
				jPanel10.setBackground(new java.awt.Color(242,242,238));  // Generated
			}
			catch (java.lang.Throwable e) {
				//  Do Something
			}
		}
		return jPanel10;
	}
	/**
	 * This method initializes jLabel2
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel2() {
		if(jLabel2 == null) {
			try {
				jLabel2 = new javax.swing.JLabel();  // Generated
				jLabel2.setText("BAUD Rate: ");  // Generated
				jLabel2.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));  // Generated
				jLabel2.setPreferredSize(new Dimension(110,15));
			}
			catch (java.lang.Throwable e) {
				//  Do Something
			}
		}
		return jLabel2;
	}
	/**
	 * This method initializes jLabel3
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel3() {
		if(jLabel3 == null) {
			try {
				jLabel3 = new javax.swing.JLabel();  // Generated
				jLabel3.setText("DATA Bits: ");  // Generated
				jLabel3.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));  // Generated
				jLabel3.setPreferredSize(new Dimension(110,15));
			}
			catch (java.lang.Throwable e) {
				//  Do Something
			}
		}
		return jLabel3;
	}
	/**
	 * This method initializes jLabel4
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel4() {
		if(jLabel4 == null) {
			try {
				jLabel4 = new javax.swing.JLabel();  // Generated
				jLabel4.setText("Parity: ");  // Generated
				jLabel4.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));  // Generated
				jLabel4.setPreferredSize(new Dimension(110,15));
			}
			catch (java.lang.Throwable e) {
				//  Do Something
			}
		}
		return jLabel4;
	}
	/**
	 * This method initializes jLabel5
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel5() {
		if(jLabel5 == null) {
			try {
				jLabel5 = new javax.swing.JLabel();  // Generated
				jLabel5.setText("STOP Bits: ");  // Generated
				jLabel5.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));  // Generated
				jLabel5.setPreferredSize(new Dimension(110,15));
			}
			catch (java.lang.Throwable e) {
				//  Do Something
			}
		}
		return jLabel5;
	}
	/**
	 * This method initializes jLabel6
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel6() {
		if(jLabel6 == null) {
			try {
				jLabel6 = new javax.swing.JLabel();  // Generated
				jLabel6.setText("FLOW Control In: ");  // Generated
				jLabel6.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));  // Generated
				jLabel6.setPreferredSize(new Dimension(110,15));
			}
			catch (java.lang.Throwable e) {
				//  Do Something
			}
		}
		return jLabel6;
	}
	/**
	 * This method initializes jLabel7
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel7() {
		if(jLabel7 == null) {
			try {
				jLabel7 = new javax.swing.JLabel();  // Generated
				jLabel7.setText("FLOW Control Out: ");  // Generated
				jLabel7.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));  // Generated
				jLabel7.setPreferredSize(new Dimension(110,15));
			}
			catch (java.lang.Throwable e) {
				//  Do Something
			}
		}
		return jLabel7;
	}
	/**
	 * This method initializes jComboBox1
	 * 
	 * @return javax.swing.JComboBox
	 */
	private javax.swing.JComboBox getJComboBox1() {
		if(jComboBox1 == null) {
			try {
				jComboBox1 = new javax.swing.JComboBox();  // Generated
				jComboBox1.setPreferredSize(new Dimension(130,20));
				jComboBox1.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));  // Generated
				jComboBox1.setBackground(new Color(242,242,238));
				jComboBox1.addItem("300");
				jComboBox1.addItem("2400");
				jComboBox1.addItem("4800");
				jComboBox1.addItem("9600");
				jComboBox1.addItem("14400");
				jComboBox1.addItem("28800");
				jComboBox1.addItem("38400");
				jComboBox1.addItem("57600");
				jComboBox1.addItem("152000");
			}
			catch (java.lang.Throwable e) {
				//  Do Something
			}
		}
		return jComboBox1;
	}
	/**
	 * This method initializes jComboBox2
	 * 
	 * @return javax.swing.JComboBox
	 */
	private javax.swing.JComboBox getJComboBox2() {
		if(jComboBox2 == null) {
			try {
				jComboBox2 = new javax.swing.JComboBox();  // Generated
				jComboBox2.setPreferredSize(new Dimension(130,20));
				jComboBox2.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));  // Generated
				jComboBox2.setBackground(new Color(242,242,238));
				jComboBox2.addItem("5");
				jComboBox2.addItem("6");
				jComboBox2.addItem("7");
				jComboBox2.addItem("8");
			}
			catch (java.lang.Throwable e) {
				//  Do Something
			}
		}
		return jComboBox2;
	}
	/**
	 * This method initializes jComboBox3
	 * 
	 * @return javax.swing.JComboBox
	 */
	private javax.swing.JComboBox getJComboBox3() {
		if(jComboBox3 == null) {
			try {
				jComboBox3 = new javax.swing.JComboBox();  // Generated
				jComboBox3.setPreferredSize(new Dimension(130,20));
				jComboBox3.addItem("None");
				jComboBox3.addItem("Even");
				jComboBox3.addItem("Odd");
				jComboBox3.setSelectedItem("None");
				jComboBox3.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));  // Generated
				jComboBox3.setBackground(new Color(242,242,238));
			}
			catch (java.lang.Throwable e) {
				//  Do Something
			}
		}
		return jComboBox3;
	}
	/**
	 * This method initializes jComboBox4
	 * 
	 * @return javax.swing.JComboBox
	 */
	private javax.swing.JComboBox getJComboBox4() {
		if(jComboBox4 == null) {
			try {
				jComboBox4 = new javax.swing.JComboBox();  // Generated
				jComboBox4.setPreferredSize(new Dimension(130,20));
				jComboBox4.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));  // Generated
				jComboBox4.setBackground(new Color(242,242,238));
				jComboBox4.addItem("1");
				jComboBox4.addItem("1.5");
				jComboBox4.addItem("2");
				
			}
			catch (java.lang.Throwable e) {
				//  Do Something
			}
		}
		return jComboBox4;
	}
	/**
	 * This method initializes jComboBox5
	 * 
	 * @return javax.swing.JComboBox
	 */
	private javax.swing.JComboBox getJComboBox5() {
		if(jComboBox5 == null) {
			try {
				jComboBox5 = new javax.swing.JComboBox();  // Generated
				jComboBox5.setPreferredSize(new Dimension(130,20));
				jComboBox5.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));  // Generated
				jComboBox5.setBackground(new Color(242,242,238));
				jComboBox5.addItem("None");
				jComboBox5.addItem("Xon/Xoff In");
				jComboBox5.addItem("RTS/CTS In");

			}
			catch (java.lang.Throwable e) {
				//  Do Something
			}
		}
		return jComboBox5;
	}
	/**
	 * This method initializes jComboBox6
	 * 
	 * @return javax.swing.JComboBox
	 */
	private javax.swing.JComboBox getJComboBox6() {
		if(jComboBox6 == null) {
			try {
				jComboBox6 = new javax.swing.JComboBox();  // Generated
				jComboBox6.setPreferredSize(new Dimension(130,20));
				jComboBox6.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));  // Generated
				jComboBox6.setBackground(new Color(242,242,238));
				jComboBox6.addItem("None");
				jComboBox6.addItem("Xon/Xoff Out");
				jComboBox6.addItem("RTS/CTS Out");
			}
			catch (java.lang.Throwable e) {
				//  Do Something
			}
		}
		return jComboBox6;
	}
	/**
	 * This method initializes jPanel11
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel11() {
		if(jPanel11 == null) {
			try {
				jPanel11 = new javax.swing.JPanel();  // Generated
				java.awt.FlowLayout layFlowLayout10 = new java.awt.FlowLayout();  // Generated
				layFlowLayout10.setAlignment(java.awt.FlowLayout.LEFT);  // Generated
				layFlowLayout10.setVgap(2);
				jPanel11.setLayout(layFlowLayout10);  // Generated
				jPanel11.add(getJScrollPane(), null);  // Generated
				jPanel11.setBackground(new java.awt.Color(242,242,238));  // Generated
				jPanel11.setPreferredSize(new Dimension(580,80));
				jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.gray,1), "Datos de Salida:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10), java.awt.Color.black));  // Generated
			}
			catch (java.lang.Throwable e) {
				//  Do Something
			}
		}
		return jPanel11;
	}
	/**
	 * This method initializes jPanel12
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel12() {
		if(jPanel12 == null) {
			try {
				jPanel12 = new javax.swing.JPanel();  // Generated
				java.awt.FlowLayout layFlowLayout111 = new java.awt.FlowLayout();  // Generated
				layFlowLayout111.setAlignment(java.awt.FlowLayout.LEFT);  // Generated
				layFlowLayout111.setVgap(0);
				jPanel12.setLayout(layFlowLayout111);  // Generated
				jPanel12.add(getJScrollPane1(), null);  // Generated
				jPanel12.setBackground(new java.awt.Color(242,242,238));  // Generated
				jPanel12.setPreferredSize(new Dimension(580,80));
				jPanel12.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.gray,1), "Datos de Entrada:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10), java.awt.Color.black));  // Generated
			}
			catch (java.lang.Throwable e) {
				//  Do Something
			}
		}
		return jPanel12;
	}
	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private javax.swing.JScrollPane getJScrollPane() {
		if(jScrollPane == null) {
			try {
				jScrollPane = new javax.swing.JScrollPane();  // Generated
				jScrollPane.setPreferredSize(new Dimension(560,50));
				jScrollPane.setViewportView(getJTextArea());  // Generated
			}
			catch (java.lang.Throwable e) {
				//  Do Something
			}
		}
		return jScrollPane;
	}
	/**
	 * This method initializes jTextArea
	 * 
	 * @return javax.swing.JTextArea
	 */
	private javax.swing.JTextArea getJTextArea() {
		if(jTextArea == null) {
			try {
				jTextArea = new javax.swing.JTextArea();  // Generated
				jTextArea.setEnabled(false);  // Generated
				jTextArea.setEditable(false);  // Generated
				jTextArea.setBackground(new java.awt.Color(226,226,222));  // Generated
			}
			catch (java.lang.Throwable e) {
				//  Do Something
			}
		}
		return jTextArea;
	}
	/**
	 * This method initializes jScrollPane1
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private javax.swing.JScrollPane getJScrollPane1() {
		if(jScrollPane1 == null) {
			try {
				jScrollPane1 = new javax.swing.JScrollPane();  // Generated
				jScrollPane1.setPreferredSize(new Dimension(560,50));
				jScrollPane1.setViewportView(getJTextArea1());  // Generated
			}
			catch (java.lang.Throwable e) {
				//  Do Something
			}
		}
		return jScrollPane1;
	}
	/**
	 * This method initializes jTextArea1
	 * 
	 * @return javax.swing.JTextArea
	 */
	private javax.swing.JTextArea getJTextArea1() {
		if(jTextArea1 == null) {
			try {
				jTextArea1 = new javax.swing.JTextArea();  // Generated
				jTextArea1.setEditable(false);  // Generated
				jTextArea1.setEnabled(false);  // Generated
				jTextArea1.setBackground(new java.awt.Color(226,226,222));  // Generated
			}
			catch (java.lang.Throwable e) {
				//  Do Something
			}
		}
		return jTextArea1;
	}
	/**
	 * This method initializes jPanel13
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel13() {
		if(jPanel13 == null) {
			try {
				jPanel13 = new javax.swing.JPanel();  // Generated
				java.awt.FlowLayout layFlowLayout12 = new java.awt.FlowLayout();  // Generated
				layFlowLayout12.setAlignment(java.awt.FlowLayout.RIGHT);  // Generated
				layFlowLayout12.setVgap(0);
				layFlowLayout12.setHgap(0);
				jPanel13.setLayout(layFlowLayout12);  // Generated
				jPanel13.add(getJButton(), null);  // Generated
				jPanel13.add(getJButton1(), null);  // Generated
				jPanel13.add(getJButton2(), null);  // Generated
				jPanel13.add(getJButton3(), null);  // Generated
				jPanel13.setBackground(new java.awt.Color(226,226,222));  // Generated
				jPanel13.setPreferredSize(new Dimension(610,40));
			}
			catch (java.lang.Throwable e) {
				//  Do Something
			}
		}
		return jPanel13;
	}
	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private javax.swing.JButton getJButton() {
		if(jButton == null) {
			try {
				jButton = new javax.swing.JButton();  // Generated
				jButton.setText("Abrir Puertos");  // Generated
				jButton.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));  // Generated
				jButton.setBackground(new java.awt.Color(226,226,222));  // Generated
				jButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/epa/crserialinterface/gtk-ok.png")));  // Generated
			}
			catch (java.lang.Throwable e) {
				//  Do Something
			}
		}
		return jButton;
	}
	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private javax.swing.JButton getJButton1() {
		if(jButton1 == null) {
			try {
				jButton1 = new javax.swing.JButton();  // Generated
				jButton1.setBackground(new java.awt.Color(226,226,222));  // Generated
				jButton1.setText("Cerrar Puertos");  // Generated
				jButton1.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));  // Generated
				jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/epa/crserialinterface/gtk-no.png")));  // Generated
				jButton1.setEnabled(false);  // Generated
			}
			catch (java.lang.Throwable e) {
				//  Do Something
			}
		}
		return jButton1;
	}
	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private javax.swing.JButton getJButton2() {
		if(jButton2 == null) {
			try {
				jButton2 = new javax.swing.JButton();  // Generated
				jButton2.setBackground(new java.awt.Color(226,226,222));  // Generated
				jButton2.setText("Enviar BREAK");
				jButton2.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));  // Generated
				jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/epa/crserialinterface/Refresh.png")));  // Generated
				jButton2.setEnabled(false);  // Generated
			}
			catch (java.lang.Throwable e) {
				//  Do Something
			}
		}
		return jButton2;
	}
	/**
	 * This method initializes jButton3
	 * 
	 * @return javax.swing.JButton
	 */
	private javax.swing.JButton getJButton3() {
		if(jButton3 == null) {
			try {
				jButton3 = new javax.swing.JButton();  // Generated
				jButton3.setBackground(new java.awt.Color(226,226,222));  // Generated
				jButton3.setText("Salir");  // Generated
				jButton3.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));  // Generated
				jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/epa/crserialinterface/stock_exit.png")));  // Generated
			}
			catch (java.lang.Throwable e) {
				//  Do Something
			}
		}
		return jButton3;
	}
	
	/* (no Javadoc)
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 * @param e
	 */
	public void mouseClicked(MouseEvent e)
	{
		/*
		 * Qué hacer si pisa el boton abrir
		 */
		if(e.getSource().equals(jButton)){
			deshabilitarParametros();
			configurarParametros();
			try
			{
				conexion.openConnection();
			}
			catch (SerialExceptions e1)
			{
				// TODO Bloque catch generado automáticamente
				e1.printStackTrace();
			}
		}
		
		/*
		 * Qué hacer si pisa el boton cerrar
		 */
		 if(e.getSource().equals(jButton1)){
		 	conexion.closeConnection();
		 	habilitarParametros();
		 }
		 
		 /*
		  * Qué hacer si pisa el boton enviar send break
		  */
		  if(e.getSource().equals(jButton2)){
		  	conexion.sendBreak();
		  }
		  
		  /*
		   * Qué hacer si pisa el boton salir
		   */
		   if(e.getSource().equals(jButton3)){
		   	System.exit(0);
		   }
		 
	}
	/* (no Javadoc)
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 * @param e
	 */
	public void mouseEntered(MouseEvent e)
	{
		//TODO Documentar Constructor
		// TODO Apéndice de método generado automáticamente
		 
	}
	/* (no Javadoc)
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	 * @param e
	 */
	public void mouseExited(MouseEvent e)
	{
		//TODO Documentar Constructor
		// TODO Apéndice de método generado automáticamente
		 
	}
	/* (no Javadoc)
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 * @param e
	 */
	public void mousePressed(MouseEvent e)
	{
		//TODO Documentar Constructor
		// TODO Apéndice de método generado automáticamente
		 
	}
	/* (no Javadoc)
	 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 * @param e
	 */
	public void mouseReleased(MouseEvent e)
	{
		//TODO Documentar Constructor
		// TODO Apéndice de método generado automáticamente
		 
	}
	
	/*
	 * ---------------------------------------------------------------------------
	 * void habilitarParametros()
	 * Creado por: 	vmedina
	 * Creado el:	Mar 30, 2004 9:52:13 AM
	 * ---------------------------------------------------------------------------
	 */
	/**
	 * TODO Indicar en esta oración que hace el método, iniciar con verbo infinitivo o 3ra persona.<p>
	 * TODO Describir en uno o varios párrafos, cómo se usa y/o cómo funciona el método<p>
	 * Los párrafos se separan con:  <p>
	 * <p>Se debe dejar una línea en blanco entre ésta descripción y los tags javadoc</p>
	 * 
	 * TODO @param {nombre_parámetro1}	frase indicado que significa el parámetro o para que se va a utilizar el valor de este parámetro
	 * TODO @param {nombre_parám2}		Si inicias esta oración con mayúsculas, terminala con punto (especificación de JavaDoc).
	 * 									La descripción de parámetro puede ocupar varias líneas si deseas.
	 * TODO @return void 		Describir que significa lo que se está devolviendo.
	 * TODO @exception 					Indicar el significado o posible causa de la excepción.
	 * TODO @since 						Indicar desde que versión del Archivo éxiste el método.
	 * TODO @deprecated 				Si la función se vuelve obsoleta se deja esta línea, sino se elimina.
	 * 
	 */
	public void habilitarParametros(){
		
		jComboBox.setEnabled(true);
		jComboBox1.setEnabled(true);
		jComboBox2.setEnabled(true);
		jComboBox3.setEnabled(true);
		jComboBox4.setEnabled(true);
		jComboBox5.setEnabled(true);
		jComboBox6.setEnabled(true);
		
		jTextArea.setText("");
		jTextArea.setEnabled(false);
		jTextArea.setBackground(new Color(226,226,222));
		
		jTextArea1.setText("");
		jTextArea1.setEnabled(false);
		jTextArea1.setBackground(new Color(226,226,222));
		
		jButton.setEnabled(true);
		jButton1.setEnabled(false);
		jButton2.setEnabled(false);
		
		jButton3.setEnabled(true);
		
	}
	
	/*
	 * ---------------------------------------------------------------------------
	 * void deshabilitarParametros()
	 * Creado por: 	vmedina
	 * Creado el:	Mar 30, 2004 9:52:27 AM
	 * ---------------------------------------------------------------------------
	 */
	/**
	 * TODO Indicar en esta oración que hace el método, iniciar con verbo infinitivo o 3ra persona.<p>
	 * TODO Describir en uno o varios párrafos, cómo se usa y/o cómo funciona el método<p>
	 * Los párrafos se separan con:  <p>
	 * <p>Se debe dejar una línea en blanco entre ésta descripción y los tags javadoc</p>
	 * 
	 * TODO @param {nombre_parámetro1}	frase indicado que significa el parámetro o para que se va a utilizar el valor de este parámetro
	 * TODO @param {nombre_parám2}		Si inicias esta oración con mayúsculas, terminala con punto (especificación de JavaDoc).
	 * 									La descripción de parámetro puede ocupar varias líneas si deseas.
	 * TODO @return void 		Describir que significa lo que se está devolviendo.
	 * TODO @exception 					Indicar el significado o posible causa de la excepción.
	 * TODO @since 						Indicar desde que versión del Archivo éxiste el método.
	 * TODO @deprecated 				Si la función se vuelve obsoleta se deja esta línea, sino se elimina.
	 * 
	 */
	public void deshabilitarParametros(){
		
		jComboBox.setEnabled(false);
		jComboBox1.setEnabled(false);
		jComboBox2.setEnabled(false);
		jComboBox3.setEnabled(false);
		jComboBox4.setEnabled(false);
		jComboBox5.setEnabled(false);
		jComboBox6.setEnabled(false);
		
		jTextArea.setText("");
		jTextArea.setEnabled(true);
		jTextArea.setEditable(true);
		jTextArea.setBackground(new Color(255,255,255));
		
		jTextArea1.setText("");
		jTextArea1.setEnabled(true);
		jTextArea1.setEditable(false);
		jTextArea1.setBackground(new Color(255,255,255));
		
		jButton.setEnabled(false);
		jButton1.setEnabled(true);
		jButton2.setEnabled(true);
		
		jButton3.setEnabled(false);
	}
	
	/*
	 * ---------------------------------------------------------------------------
	 * void configurarParametros()
	 * Creado por: 	vmedina
	 * Creado el:	Mar 30, 2004 9:52:37 AM
	 * ---------------------------------------------------------------------------
	 */
	/**
	 * TODO Indicar en esta oración que hace el método, iniciar con verbo infinitivo o 3ra persona.<p>
	 * TODO Describir en uno o varios párrafos, cómo se usa y/o cómo funciona el método<p>
	 * Los párrafos se separan con:  <p>
	 * <p>Se debe dejar una línea en blanco entre ésta descripción y los tags javadoc</p>
	 * 
	 * TODO @param {nombre_parámetro1}	frase indicado que significa el parámetro o para que se va a utilizar el valor de este parámetro
	 * TODO @param {nombre_parám2}		Si inicias esta oración con mayúsculas, terminala con punto (especificación de JavaDoc).
	 * 									La descripción de parámetro puede ocupar varias líneas si deseas.
	 * TODO @return void 		Describir que significa lo que se está devolviendo.
	 * TODO @exception 					Indicar el significado o posible causa de la excepción.
	 * TODO @since 						Indicar desde que versión del Archivo éxiste el método.
	 * TODO @deprecated 				Si la función se vuelve obsoleta se deja esta línea, sino se elimina.
	 * 
	 */
	public void configurarParametros(){
		
		/*
		 * Configuramos el Puerto con los nuevos parametros
		 */
		 
		parametros.setPortName(jComboBox.getSelectedItem().toString());
		System.out.println("=========================================");
		System.out.println("BAUD Rate(*ANTERIOR): "+parametros.getBaudRate());
		parametros.setBaudRate(jComboBox1.getSelectedItem().toString());
		System.out.println("BAUD Rate(*ACTUAL): "+parametros.getBaudRate());
		
		
		System.out.println("=========================================");
		System.out.println("Parity(*ANTERIOR): "+parametros.getParityString());
		parametros.setParity(jComboBox3.getSelectedItem().toString());
		System.out.println("Parity(*ACTUAL): "+parametros.getParityString());
		
		
		System.out.println("=========================================");
		System.out.println("FLOW Control In(*ANTERIOR): "+parametros.getFlowControlInString());
		parametros.setFlowControlIn(jComboBox5.getSelectedItem().toString());
		System.out.println("FLOW Control In(*ACTUAL): "+parametros.getFlowControlInString());
		
		
		System.out.println("=========================================");
		System.out.println("FLOW Control Out(*ANTERIOR): "+parametros.getFlowControlOutString());
		parametros.setFlowControlOut(jComboBox6.getSelectedItem().toString());
		System.out.println("FLOW Control Out(*Actual): "+parametros.getFlowControlOutString());
		
		
		System.out.println("=========================================");
		System.out.println("DATA Bits(*ANTERIOR): "+parametros.getDatabits());
		parametros.setDatabits(jComboBox2.getSelectedItem().toString());
		System.out.println("DATA Bits(*ACTUAL): "+parametros.getDatabits());
		
		
		System.out.println("=========================================");
		System.out.println("STOP Bits(*ANTERIOR): "+parametros.getStopbits());
		parametros.setStopbits(jComboBox4.getSelectedItem().toString());
		System.out.println("STOP Bits(*ACTUAL): "+parametros.getStopbits());
	}
}