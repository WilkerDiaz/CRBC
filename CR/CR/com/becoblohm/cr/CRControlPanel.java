/*
 * Created on Jun 21, 2004
 *
 */
package com.becoblohm.cr;

import gnu.io.CommPortIdentifier;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.InetAddress;
import java.util.Enumeration;
import java.util.HashMap;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.becoblohm.cr.utiles.MensajesVentanas;
import com.epa.preferencesproxy.EPAPreferenceProxy;
import com.epa.preferencesproxy.NoSuchNodeException;
import com.epa.preferencesproxy.NodeAlreadyExistsException;
import com.epa.preferencesproxy.UnidentifiedPreferenceException;
/**
 * <p>Indicar aquí, en una línea que hace la clase</p>
 * <p>Agregar parrafos que describen como se usa la Clase y un resumen de como 
 * funciona.</p>
 * @author $Author: acastillo $  
 * @version $Revision: 1.12.4.1 $<br>$Date: 2005/01/04 18:23:01 $
 * <!-- A continuación indicar referencia a otras páginas o clases parecidas -->
 * <!-- o relacionados, pundiendo indicar inclusive los métodos relacionados -->
 * <!-- por ejemplo: @see     <a href="package-summary.html#charenc">Character encodings</a> -->
 * <!-- por ejemplo: @see     java.lang.Object 								 -->
 * <!-- por ejemplo: @see     java.lang.Object#toNewString() 				 -->
 * @see     {RutaObjeto}#NombreMetodo()
 * @since   <!-- Indique desde que versión del Proyecto existe esta clase 	 -->
 */



public class CRControlPanel extends JFrame  implements ComponentListener, MouseListener, ActionListener {

	private static final long serialVersionUID = 1L;

	private javax.swing.JPanel jContentPane = null;

	private javax.swing.JTabbedPane jTabbedPane = null;
	private javax.swing.JPanel jPanel = null;
	private javax.swing.JPanel jPanel1 = null;
	private javax.swing.JPanel jPanel2 = null;
	private javax.swing.JButton jButton1 = null;
	private javax.swing.JPanel jPanel3 = null;
	private javax.swing.JPanel jPanel4 = null;
	private javax.swing.JLabel jLabel = null;
	private javax.swing.JPanel jPanel5 = null;
	private javax.swing.JButton btnSavePort = null;
	private javax.swing.JPanel jPanel6 = null;
	private javax.swing.JPanel jPanel7 = null;
	private javax.swing.JLabel jLabel1 = null;
	private javax.swing.JComboBox cmbNomDisp = null;
	private javax.swing.JPanel jPanel8 = null;
	private javax.swing.JPanel jPanel9 = null;
	private javax.swing.JLabel jLabel2 = null;
	private javax.swing.JComboBox cmbSerialPort = null;
	private javax.swing.JPanel jPanel10 = null;
	private javax.swing.JPanel jPanel11 = null;
	private javax.swing.JPanel jPanel12 = null;
	private javax.swing.JPanel jPanel13 = null;
	private javax.swing.JPanel jPanel14 = null;
	private javax.swing.JPanel jPanel15 = null;
	private javax.swing.JPanel jPanel16 = null;
	private javax.swing.JPanel jPanel17 = null;
	private javax.swing.JPanel jPanel18 = null;
	private javax.swing.JPanel jPanel19 = null;
	private javax.swing.JPanel jPanel20 = null;
	private javax.swing.JPanel jPanel21 = null;
	private javax.swing.JPanel jPanel22 = null;
	private javax.swing.JPanel jPanel23 = null;
	private javax.swing.JPanel jPanel24 = null;
	private javax.swing.JPanel jPanel25 = null;
	private javax.swing.JPanel jPanel26 = null;
	private javax.swing.JPanel jPanel27 = null;
	private javax.swing.JPanel jPanel28 = null;
	private javax.swing.JLabel jLabel3 = null;
	private javax.swing.JLabel jLabel4 = null;
	private javax.swing.JLabel jLabel5 = null;
	private javax.swing.JLabel jLabel6 = null;
	private javax.swing.JLabel jLabel7 = null;
	private javax.swing.JLabel jLabel8 = null;
	private javax.swing.JComboBox cmbBaudRate = null;
	private javax.swing.JComboBox cmbFlowCtlOut = null;
	private javax.swing.JComboBox cmbStopBits = null;
	private javax.swing.JComboBox cmbDataBits = null;
	private javax.swing.JComboBox cmbParity = null;
	private javax.swing.JComboBox cmbFlowCtlIn = null;
	private EPAPreferenceProxy epaPreferencesProxy;
	private EPAPreferenceProxy eppCR;
	private JPanel jPanel29 = null;
	private JPanel jPanel30 = null;
	private JPanel jPanel31 = null;
	private JPanel jPanel32 = null;
	private JPanel jPanel33 = null;
	private JPanel jPanel34 = null;
	private JPanel jPanel35 = null;
	private JPanel jPanel36 = null;
	private JPanel jPanel37 = null;
	private JPanel jPanel38 = null;
	private JPanel jPanel39 = null;
	private JLabel jLabel9 = null;
	private JLabel jLabel10 = null;
	private JPanel jPanel40 = null;
	private JPanel jPanel41 = null;
	private JPanel jPanel42 = null;
	private JPanel jPanel43 = null;
	private JPanel jPanel44 = null;
	private JPanel jPanel115 = null;
	private JPanel jPanel45 = null;
	private JPanel jPanel46 = null;
	private JPanel jPanel47 = null;
	private JPanel jPanel48 = null;
	private JLabel jLabel11 = null;
	private JPanel jPanel49 = null;
	private JTextField txtLocalClass = null;
	private JLabel jLabel12 = null;
	private JTextField txtCentralServClass = null;
	private JLabel jLabel13 = null;
	private JTextField txtServerClass = null;
	private JLabel jLabel14 = null;
	private JLabel jLabel96 = null;
	private JTextField txtClave = null;
	private JTextField txtRMI = null;
	private JLabel jLabel15 = null;
	private JTextField txtUrlLocal = null;
	private JLabel jLabel16 = null;
	private JTextField txtUrlServ = null;
	private JLabel jLabel17 = null;
	private JTextField txtUrlServCentral = null;
	private JLabel jLabel18 = null;
	private JTextField txtUser = null;
	private JLabel jLabel19 = null;
	private JPanel jPanel50 = null;
	private JButton btnSaveBD = null;
	private JPanel jPanel51 = null;
	private JPanel jPanel52 = null;
	private JLabel jLabel20 = null;
	private JTextField txtDiasVig = null;
	private JPanel jPanel53 = null;
	private JLabel jLabel21 = null;
	private JPanel jPanel54 = null;
	private JLabel jLabel22 = null;
	private JPanel jPanel55 = null;
	private JPanel jPanel56 = null;
	private JLabel jLabel23 = null;
	private JComboBox cmbIDApart = null;
	private JPanel jPanel57 = null;
	private JLabel jLabel24 = null;
	private JTextField txtValorAbo = null;
	private JPanel jPanel58 = null;
	private JButton btnActAbono = null;
	private JPanel jPanel59 = null;
	private JButton btnSaveApart = null;
	private JLabel jLabel25 = null;
	private JPanel jPanel60 = null;
	private JPanel jPanel61 = null;
	private JLabel jLabel26 = null;
	private JTextField txtLongCodInt = null;
	private JPanel jPanel62 = null;
	private JButton btnSaveFact = null;
	private JLabel jLabel27 = null;
	private JPanel jPanel63 = null;
	private JPanel jPanel64 = null;
	private JPanel jPanel65 = null;
	private JPanel jPanel67 = null;
	private JPanel jPanel68 = null;
	private JPanel jPanel69 = null;
	private JPanel jPanel70 = null;
	private JPanel jPanel88 = null;
	private JPanel jPanel89 = null;
	private JPanel jPanel90 = null;
	private JPanel jPanel91 = null;
	private JPanel jPanel92 = null;
	private JPanel jPanel93 = null;
	private JPanel jPanel94 = null;
	private JPanel jPanel95 = null;
	private JPanel jPanel96 = null;
	private JPanel jPanel97 = null;
	private JLabel jLabel28 = null;
	private JTextField txtPtoSocket = null;
	private JTextField txtPtoServ = null;
	private JTextField txtPtoServCent = null;
	private JTextField txtPtoSync = null;
	private JLabel jLabel29 = null;
	private JLabel jLabel30 = null;
	private JTextField txtPathTmp = null;
	private JLabel jLabel31 = null;
	private JLabel jLabel32 = null;
	private JTextField txtUrlPrinc = null;
	private JTextField txtLongitudNombre = null;
	private JTextField txtLongitudApellido = null;
	private JLabel jLabel33 = null;
	private JTextField txtUrlVerif = null;
	private JPanel jPanel71 = null;
	private JPanel jPanel72 = null;
	private JPanel jPanel74 = null;
	private JPanel jPanel75 = null;
	private JPanel jPanel103 = null;
	private JPanel jPanel76 = null;
	private JLabel jLabel34 = null;
	private JTextField txtColorBeco = null;
	private JLabel jLabel35 = null;
	private JTextField txtColorEPA = null;
	private JLabel jLabel36 = null;
	private JLabel jLabel37 = null;
	private JTextField txtImgSplash = null;
	private JComboBox cmbColorSplash = null;
	private JLabel jLabel38 = null;
	private JLabel jLabel39 = null;
	private JTextField txtMsgSplash = null;
	private JLabel jLabel40 = null;
	private JRadioButton rbVerifLinS = null;
	private JRadioButton rbVerifLinN = null;
	private JRadioButton rbFiscalPrnS = null;
	private JRadioButton rbFiscalPrnN = null;
	private JRadioButton rbGarantFactS = null;
	private JRadioButton rbGarantFactN = null;
	private JTextField txtLogo = null;
	private JPanel jPanel77 = null;
	private JButton btnSaveSys = null;
	private JRadioButton rbReqCliS = null;
	private JRadioButton rbReqCliN = null;
	private JPanel jPanel78 = null;
	private JPanel jPanel79 = null;
	private JLabel jLabel41 = null;
	private JLabel jLabel42 = null;
	private JRadioButton rbEmpAcumS = null;
	private JRadioButton rbEmpAcumN = null;
	private JRadioButton rbRebajEmpS = null;
	private JRadioButton rbRebajEmpN = null;
	private ButtonGroup bgRebApart = new ButtonGroup();
	private ButtonGroup bgGarantFact = new ButtonGroup();
	private ButtonGroup bgEmpAcum = new ButtonGroup();
	private ButtonGroup bgFiscalPrn = new ButtonGroup();
	private ButtonGroup bgReqCli = new ButtonGroup();
	private ButtonGroup bgVerifLin = new ButtonGroup();
	private ButtonGroup bgRecalcSaldo = new ButtonGroup();
	private ButtonGroup bgAutCierre = new ButtonGroup();
	private ButtonGroup bgIgnScan = new ButtonGroup();
	private ButtonGroup bgIgnPrinter = new ButtonGroup();
	private ButtonGroup bgIgnVisor = new ButtonGroup();
	private ButtonGroup bgFacturar = new ButtonGroup();
	private ButtonGroup bgImpFteCheque = new ButtonGroup();  //  @jve:decl-index=0:
	private ButtonGroup bgImpDorsoCheque = new ButtonGroup();  //  @jve:decl-index=0:
	private ButtonGroup bgCedulaOblig = new ButtonGroup();
	private ButtonGroup bgNumConfOblig = new ButtonGroup();
	private ButtonGroup bgApagarSist = new ButtonGroup();
	private ButtonGroup bgReiniciarSist = new ButtonGroup();
	private ButtonGroup bgSyncCommander = new ButtonGroup();
	private ButtonGroup bgCambioFecha = new ButtonGroup();
	private ButtonGroup bgSincAfiliado = new ButtonGroup();
	private ButtonGroup bgActCajaTemp = new ButtonGroup();
	private ButtonGroup bgActVerificador = new ButtonGroup();
	private ButtonGroup bgEscanerRapido = new ButtonGroup();
	private ButtonGroup bgMantenerGrupo = new ButtonGroup();
	private ButtonGroup claveAlSalir = new ButtonGroup();
	private ButtonGroup bgPreguntarFacturar = new ButtonGroup();
	private JRadioButton rbRebajS = null;
	private JRadioButton rbRebajN = null;
	private int flag = 0;
	private javax.swing.JPanel jPanel80 = null;
	private javax.swing.JPanel jPanel81 = null;
	private javax.swing.JPanel jPanel82 = null;
	private javax.swing.JPanel jPanel83 = null;
	private javax.swing.JPanel jPanel98 = null;
	private javax.swing.JLabel jLabel44 = null;
	private javax.swing.JLabel jLabel43 = null;
	private javax.swing.JLabel jLabel45 = null;
	private javax.swing.JLabel jLabel46 = null;
	private javax.swing.JLabel jLabel87 = null;
	private javax.swing.JLabel jLabel95 = null;
	private javax.swing.JTextField txtNumCuenta = null;
	private javax.swing.JTextField txtPorcMsgEmp = null;
	private javax.swing.JComboBox cmbTipoCuenta = null;
	private javax.swing.JRadioButton rbAutCierreS = null;
	private javax.swing.JRadioButton rbAutCierreN = null;
	private javax.swing.JPanel jPanel84 = null;
	private javax.swing.JPanel jPanel85 = null;
	private javax.swing.JLabel jLabel47 = null;
	private javax.swing.JLabel jLabel48 = null;
	private javax.swing.JLabel jLabel49 = null;
	
	private javax.swing.JTextField txtCargoServ = null;
	private javax.swing.JRadioButton rbRecalcSaldoS = null;
	private javax.swing.JRadioButton rbRecalcSaldoN = null;
	private javax.swing.JPanel jPanel86 = null;
	private javax.swing.JRadioButton rbIgnScanS = null;
	private javax.swing.JRadioButton rbIgnScanN = null;
	private javax.swing.JLabel jLabel50 = null;
	private javax.swing.JRadioButton rbIgnPrnS = null;
	private javax.swing.JRadioButton rbIgnPrnN = null;
	private javax.swing.JLabel jLabel51 = null;
	private javax.swing.JRadioButton rbIgnVisorS = null;
	private javax.swing.JRadioButton rbIgnVisorN = null;
	private javax.swing.JLabel jLabel53 = null;
	private javax.swing.JTextField txtSyncFreq = null;
	private javax.swing.JLabel jLabel54 = null;
	private javax.swing.JLabel jLabel55 = null;
	private javax.swing.JRadioButton rbFacturarS = null;
	private javax.swing.JRadioButton rbFacturarN = null;
	private javax.swing.JRadioButton rbMsgBlancoS = null;
	private javax.swing.JRadioButton rbMsgBlancoN = null;
	private javax.swing.JPanel jPanel87 = null;
	private javax.swing.JLabel jLabel56 = null;
	private javax.swing.JTextField txtEsquema = null;
	private javax.swing.JComboBox cmbNumAbonos = null;
	private HashMap<String,Abono> listaAbonos = new HashMap<String,Abono>();
	private javax.swing.JLabel jLabel57 = null;
	private javax.swing.JLabel jLabel58 = null;
	private javax.swing.JLabel jLabel59 = null;
	private javax.swing.JLabel jLabel60 = null;
	private javax.swing.JLabel jLabel61 = null;
	private javax.swing.JLabel jLabel62 = null;
	private javax.swing.JLabel jLabel63 = null;
	private javax.swing.JTextField txtRetIVA = null;
	private javax.swing.JTextField txtMonedaLocal = null;
	private javax.swing.JTextField txtNombreBanco = null;
	private javax.swing.JRadioButton rbImpFteChequeS = null;
	private javax.swing.JRadioButton rbImpFteChequeN = null;
	private javax.swing.JRadioButton rbImpDorsoChequeS = null;
	private javax.swing.JRadioButton rbImpDorsoChequeN = null;
	private javax.swing.JRadioButton rbCedulaObligS = null;
	private javax.swing.JRadioButton rbCedulaObligN = null;
	private javax.swing.JRadioButton rbNumConfObligS = null;
	private javax.swing.JRadioButton rbNumConfObligN = null;
	private javax.swing.JLabel jLabel64 = null;
	private javax.swing.JLabel jLabel65 = null;
	private javax.swing.JLabel jLabel66 = null;
	private javax.swing.JLabel jLabel67 = null;
	private javax.swing.JLabel jLabel68 = null;
	private javax.swing.JLabel jLabel69 = null;
	private javax.swing.JLabel jLabel70 = null;
	private javax.swing.JLabel jLabel71 = null;
	private javax.swing.JLabel jLabel72 = null;
	private javax.swing.JLabel jLabel73 = null;
	private javax.swing.JLabel jLabel74 = null;
	private javax.swing.JTextField txtLongitudVisor = null;
	private javax.swing.JComboBox cmbSaltoVisor = null;
	private javax.swing.JComboBox cmbTipoVigencia = null;
	private javax.swing.JPanel jPanel200 = null;
	private javax.swing.JLabel jLabel200 = null;
	private javax.swing.JTextField txtMtoMinDevolucion = null;

	private javax.swing.JRadioButton rbApagarSistN = null;
	private javax.swing.JRadioButton rbApagarSistS = null;
	private javax.swing.JRadioButton rbReiniciarSistN = null;
	private javax.swing.JRadioButton rbReiniciarSistS = null;
	private javax.swing.JRadioButton rbSyncCommanderN = null;
	private javax.swing.JRadioButton rbSyncCommanderS = null;
	private javax.swing.JRadioButton rbCambioFechaN = null;
	private javax.swing.JRadioButton rbCambioFechaS = null;
	private javax.swing.JRadioButton rbSincAfiliadoN = null;
	private javax.swing.JRadioButton rbSincAfiliadoS = null;
	private javax.swing.JRadioButton rbActCajaTempN = null;
	private javax.swing.JRadioButton rbActCajaTempS = null;
	private javax.swing.JRadioButton rbActVerificadorS = null;
	private javax.swing.JRadioButton rbActVerificadorN = null;
	private javax.swing.JRadioButton rbEscanerRapidoS = null;
	private javax.swing.JRadioButton rbEscanerRapidoN = null;
	private javax.swing.JRadioButton rbMantenerFocoSi = null;
	private javax.swing.JRadioButton rbMantenerFocoNo = null;
	private javax.swing.JLabel jLabel75 = null;
	private javax.swing.JTextField jTextField = null;
	private javax.swing.JRadioButton rbContOrdinarioN = null;
	private javax.swing.JRadioButton rbContOrdinarioS = null;
	private JPanel jPanel101 = null;
	private JPanel jPanel102 = null;
	private javax.swing.JLabel jLabel77 = null;
	private javax.swing.JLabel jLabel78 = null;
	private javax.swing.JTextField colorCombo = null;
	private javax.swing.JLabel jLabel79 = null;
	private javax.swing.JLabel jLabel80 = null;
	private javax.swing.JLabel jLabel81 = null;
	private javax.swing.JLabel jLabel82 = null;
	private javax.swing.JPanel jPanel66 = null;
	private javax.swing.JLabel jLabel76 = null;
	private javax.swing.JComboBox manejoGaveta = null;
	private javax.swing.JPanel jPanel104 = null;
	private javax.swing.JPanel jPanel105 = null;
	private javax.swing.JLabel jLabel83 = null;
	private javax.swing.JTextField txtMtoMinApartado = null;
	private javax.swing.JPanel jPanel73 = null;
	private javax.swing.JLabel jLabel84 = null;
	private javax.swing.JCheckBox jCheckBox = null;
	private javax.swing.JCheckBox jCheckBox1 = null;
	private javax.swing.JCheckBox jCheckBox2 = null;
	private javax.swing.JLabel jLabel85 = null;
	private javax.swing.JRadioButton validarClaveS = null;
	private javax.swing.JRadioButton validarClaveN = null;
	private javax.swing.JPanel jPanel99 = null;
	private javax.swing.JLabel jLabel86 = null;
	private javax.swing.JRadioButton rbPreguntarFactSi = null;
	private javax.swing.JRadioButton rbPreguntarFactNo = null;
	private javax.swing.JPanel tabListaRegalos = null;
	private javax.swing.JPanel jPanel106 = null;
	private javax.swing.JPanel jPanel107 = null;
	private javax.swing.JPanel jPanel108 = null;
	private javax.swing.JPanel jPanel109 = null;
	private javax.swing.JLabel jLabel88 = null;
	private javax.swing.JButton btnSaveLR = null;
	private javax.swing.JPanel jPanel100 = null;
	private javax.swing.JLabel jLabel89 = null;
	private javax.swing.JPanel jPanel110 = null;
	private javax.swing.JPanel jPanel111 = null;
	private javax.swing.JLabel jLabel90 = null;
	private javax.swing.JLabel jLabel91 = null;
	private javax.swing.JTextField txtMontoMinimoLG = null;
	
	private javax.swing.JTextField txtCostoUT = null;
	private javax.swing.JTextField txtDiasAperturaLG = null;
	private javax.swing.JPanel jPanel112 = null;
	private javax.swing.JLabel jLabel92 = null;
	private javax.swing.JTextField txtDbUrlServidorPdt = null;
	private javax.swing.JPanel jPanel113 = null;
	private javax.swing.JPanel jPanel114 = null;
	private javax.swing.JLabel jLabel93 = null;
	private javax.swing.JTextField txtUsuarioDbPdt = null;
	private javax.swing.JLabel jLabel94 = null;
	private javax.swing.JTextField txtClaveDbPdt = null;
	private javax.swing.JLabel jLabel97 = null;
	private javax.swing.JLabel jLabel98 = null;
	private javax.swing.JLabel jLabel99 = null;
	private javax.swing.JLabel jLabel100 = null;
	private javax.swing.JTextField txtTimeOutPDA = null;
	private javax.swing.JTextField txtIpServPDA = null;
	private javax.swing.JTextField txtPuertoServPDA = null;
	private javax.swing.JTextField txtPuertoEscuchaCRPDA = null;
	/**
	 * This method initializes jPanel29	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel29() {
		if (jPanel29 == null) {
			jPanel29 = new JPanel();
			jPanel29.setName("");
			jPanel29.setBackground(new java.awt.Color(226,226,222));
			jPanel29.add(getJPanel33(), null);
			jPanel29.add(getJPanel50(), null);
			jPanel29.setPreferredSize(new java.awt.Dimension(1075,500));
			KeyAdapter ka = new java.awt.event.KeyAdapter() { 
				public void keyTyped(java.awt.event.KeyEvent e) {    
					cambioBD();					
				}
			};
			ChangeListener chl = new javax.swing.event.ChangeListener() { 
				public void stateChanged(javax.swing.event.ChangeEvent e) {    
					cambioBD();
				}
			};			
			txtLocalClass.addKeyListener(ka);
			txtServerClass.addKeyListener(ka);
			txtCentralServClass.addKeyListener(ka);
			txtClave.addKeyListener(ka);
			txtRMI.addKeyListener(ka);
			txtUrlLocal.addKeyListener(ka);
			txtUrlServ.addKeyListener(ka);
			txtUrlServCentral.addKeyListener(ka);
			txtUser.addKeyListener(ka);
			txtEsquema.addKeyListener(ka);
			rbGarantFactS.addChangeListener(chl);
			rbGarantFactN.addChangeListener(chl);
		}
		return jPanel29;
	}
	/**
	 * This method initializes jPanel30	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel30() {
		if (jPanel30 == null) {
			jPanel30 = new JPanel();
			jPanel30.setBackground(new java.awt.Color(226,226,222));
			jPanel30.add(getJPanel34(), null);
			jPanel30.add(getJPanel62(), null);
			jPanel30.setPreferredSize(new java.awt.Dimension(1075,620));
			KeyAdapter ka = new java.awt.event.KeyAdapter() { 
				public void keyTyped(java.awt.event.KeyEvent e) {    
					cambioFact();					
				}
			};
			ChangeListener chl = new javax.swing.event.ChangeListener() { 
				public void stateChanged(javax.swing.event.ChangeEvent e) {    
					cambioFact();
				}
			};
			txtLongCodInt.addKeyListener(ka);
			jTextField.addKeyListener(ka);			
			txtNumCuenta.addKeyListener(ka);
			txtTimeOutPDA.addKeyListener(ka);
			txtIpServPDA.addKeyListener(ka);
			txtPuertoServPDA.addKeyListener(ka);
			txtPorcMsgEmp.addKeyListener(ka);
			rbAutCierreS.addChangeListener(chl);
			rbAutCierreN.addChangeListener(chl);
			rbEmpAcumS.addChangeListener(chl);
			rbEmpAcumN.addChangeListener(chl);
			rbRebajEmpS.addChangeListener(chl);
			rbRebajEmpN.addChangeListener(chl);
			rbReqCliS.addChangeListener(chl);
			rbReqCliN.addChangeListener(chl);
			txtRetIVA.addKeyListener(ka);
			txtMtoMinDevolucion.addKeyListener(ka);
			txtMonedaLocal.addKeyListener(ka);
			txtNombreBanco.addKeyListener(ka);
			rbImpDorsoChequeN.addChangeListener(chl);
			rbImpDorsoChequeS.addChangeListener(chl);
			rbImpFteChequeN.addChangeListener(chl);
			rbImpFteChequeS.addChangeListener(chl);
			rbCedulaObligN.addChangeListener(chl);
			rbCedulaObligS.addChangeListener(chl);
			rbNumConfObligN.addChangeListener(chl);
			rbNumConfObligS.addChangeListener(chl);
			rbContOrdinarioN.addChangeListener(chl);
			rbContOrdinarioS.addChangeListener(chl);
			colorCombo.addKeyListener(ka);
			jCheckBox.addKeyListener(ka);
			jCheckBox1.addKeyListener(ka);
			jCheckBox2.addKeyListener(ka);
			jCheckBox.addChangeListener(chl);
			jCheckBox1.addChangeListener(chl);
			jCheckBox2.addChangeListener(chl);
			validarClaveS.addChangeListener(chl);
			validarClaveN.addChangeListener(chl);
			
		}
		return jPanel30;
	}
	/**
	 * This method initializes jPanel31	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel31() {
		if (jPanel31 == null) {
			jPanel31 = new JPanel();
			jPanel31.setBackground(new java.awt.Color(226,226,222));
			jPanel31.setPreferredSize(new java.awt.Dimension(530,500));
			jPanel31.add(getJPanel35(), null);
			jPanel31.add(getJPanel77(), null);
			KeyAdapter ka = new java.awt.event.KeyAdapter() { 
				public void keyTyped(java.awt.event.KeyEvent e) {    
					cambioSys();					
				}
			};
			ChangeListener chl = new javax.swing.event.ChangeListener() { 
				public void stateChanged(javax.swing.event.ChangeEvent e) {    
					cambioSys();
				}
			};
			txtPathTmp.addKeyListener(ka);
			txtUrlPrinc.addKeyListener(ka);
			txtUrlVerif.addKeyListener(ka);
			txtPtoSocket.addKeyListener(ka);
			txtSyncFreq.addKeyListener(ka);
			txtLogo.addKeyListener(ka);
			txtColorBeco.addKeyListener(ka);
			txtColorEPA.addKeyListener(ka);
			txtImgSplash.addKeyListener(ka);
			txtMsgSplash.addKeyListener(ka);
			rbIgnPrnS.addChangeListener(chl);
			rbIgnPrnN.addChangeListener(chl);
			rbIgnVisorS.addChangeListener(chl);
			rbIgnVisorN.addChangeListener(chl);
			rbIgnScanS.addChangeListener(chl);
			rbIgnScanN.addChangeListener(chl);
			rbFacturarS.addChangeListener(chl);
			rbFacturarN.addChangeListener(chl);
			rbVerifLinS.addChangeListener(chl);
			rbVerifLinN.addChangeListener(chl);
			rbFiscalPrnS.addChangeListener(chl);
			rbFiscalPrnN.addChangeListener(chl);
			rbMsgBlancoS.addChangeListener(chl);
			rbMsgBlancoN.addChangeListener(chl);
			txtPtoServ.addKeyListener(ka);
			txtPtoServCent.addKeyListener(ka);
			txtPtoSync.addKeyListener(ka);
			txtLongitudVisor.addKeyListener(ka);
			rbApagarSistN.addChangeListener(chl);
			rbApagarSistS.addChangeListener(chl);
			rbReiniciarSistN.addChangeListener(chl);
			rbReiniciarSistS.addChangeListener(chl);
			rbSyncCommanderN.addChangeListener(chl);
			rbSyncCommanderS.addChangeListener(chl);
			rbCambioFechaN.addChangeListener(chl);
			rbCambioFechaS.addChangeListener(chl);
			rbSincAfiliadoN.addChangeListener(chl);
			rbSincAfiliadoS.addChangeListener(chl);
			rbActCajaTempN.addChangeListener(chl);
			rbActCajaTempS.addChangeListener(chl);
			txtLongitudApellido.addKeyListener(ka);
			txtLongitudNombre.addKeyListener(ka);
			rbActVerificadorN.addChangeListener(chl);
			rbActVerificadorS.addChangeListener(chl);
			rbEscanerRapidoN.addChangeListener(chl);
			rbEscanerRapidoS.addChangeListener(chl);
			rbMantenerFocoNo.addChangeListener(chl);
			rbMantenerFocoSi.addChangeListener(chl);
		}
		return jPanel31;
	}
	/**
	 * This method initializes jPanel32	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel32() {
		if (jPanel32 == null) {
			java.awt.FlowLayout flowLayout1 = new FlowLayout();
			jPanel32 = new JPanel();
			jPanel32.setLayout(flowLayout1);
			jPanel32.setPreferredSize(new java.awt.Dimension(540,400));
			jPanel32.setBackground(new java.awt.Color(242,242,238));
			jPanel32.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(69,107,127),1));
			flowLayout1.setHgap(0);
			flowLayout1.setVgap(0);
			jPanel32.add(getJPanel36(), null);
			jPanel32.add(getJPanel51(), null);
		}
		return jPanel32;
	}
	/**
	 * This method initializes jPanel33	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel33() {
		if (jPanel33 == null) {
			java.awt.FlowLayout flowLayout2 = new FlowLayout();
			jPanel33 = new JPanel();
			jPanel33.setLayout(flowLayout2);
			jPanel33.setPreferredSize(new java.awt.Dimension(540,380));
			jPanel33.setBackground(new java.awt.Color(242,242,238));
			jPanel33.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(69,107,127),1));
			flowLayout2.setHgap(0);
			flowLayout2.setVgap(0);
			jPanel33.add(getJPanel37(), null);
			jPanel33.add(getJPanel40(), null);
		}
		return jPanel33;
	}
	/**
	 * This method initializes jPanel34	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel34() {
		if (jPanel34 == null) {
			java.awt.FlowLayout flowLayout3 = new FlowLayout();
			jPanel34 = new JPanel();
			jPanel34.setLayout(flowLayout3);
			jPanel34.setBackground(new java.awt.Color(242,242,238));
			jPanel34.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(69,107,127),1));
			jPanel34.setPreferredSize(new java.awt.Dimension(540,465));
			flowLayout3.setHgap(0);
			flowLayout3.setVgap(0);
			jPanel34.add(getJPanel38(), null);
			jPanel34.add(getJPanel60(), null);
		}
		return jPanel34;
	}
	/**
	 * This method initializes jPanel35	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel35() {
		if (jPanel35 == null) {
			java.awt.FlowLayout flowLayout4 = new FlowLayout();
			jPanel35 = new JPanel();
			jPanel35.setLayout(flowLayout4);
			jPanel35.setBackground(new java.awt.Color(242,242,238));
			jPanel35.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(69,107,127),1));
			jPanel35.setPreferredSize(new java.awt.Dimension(540,450));
			flowLayout4.setHgap(0);
			flowLayout4.setVgap(0);
			jPanel35.add(getJPanel39(), null);
			jPanel35.add(getJPanel63(), null);
		}
		return jPanel35;
	}
	/**
	 * This method initializes jPanel36	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel36() {
		if (jPanel36 == null) {
			jLabel9 = new JLabel();
			java.awt.FlowLayout flowLayout5 = new FlowLayout();
			jPanel36 = new JPanel();
			jPanel36.setLayout(flowLayout5);
			jPanel36.setBackground(new java.awt.Color(69,107,127));
			jPanel36.setPreferredSize(new java.awt.Dimension(540,40));
			jPanel36.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 18));
			jPanel36.setForeground(java.awt.Color.white);
			jLabel9.setText("Configuración de Apartados");
			jLabel9.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 18));
			jLabel9.setForeground(java.awt.Color.white);
			jLabel9.setIcon(new ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix32x32/transform.png")));
			flowLayout5.setAlignment(java.awt.FlowLayout.LEFT);
			jPanel36.add(jLabel9, null);
		}
		return jPanel36;
	}
	/**
	 * This method initializes jPanel37	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel37() {
		if (jPanel37 == null) {
			jLabel10 = new JLabel();
			java.awt.FlowLayout flowLayout6 = new FlowLayout();
			jPanel37 = new JPanel();
			jPanel37.setLayout(flowLayout6);
			jPanel37.setBackground(new java.awt.Color(69,107,127));
			jPanel37.setPreferredSize(new java.awt.Dimension(540,40));
			jLabel10.setText("Configuración Base de Datos");
			jLabel10.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 18));
			jLabel10.setForeground(java.awt.Color.white);
			jLabel10.setIcon(new ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix32x32/transform.png")));
			flowLayout6.setAlignment(java.awt.FlowLayout.LEFT);
			jPanel37.add(jLabel10, null);
		}
		return jPanel37;
	}
	/**
	 * This method initializes jPanel38	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel38() {
		if (jPanel38 == null) {
			jLabel25 = new JLabel();
			java.awt.FlowLayout flowLayout22 = new FlowLayout();
			jPanel38 = new JPanel();
			jPanel38.setLayout(flowLayout22);
			jPanel38.setBackground(new java.awt.Color(69,107,127));
			jPanel38.setPreferredSize(new java.awt.Dimension(540,40));
			flowLayout22.setAlignment(java.awt.FlowLayout.LEFT);
			jLabel25.setText("Configuración de la Facturación");
			jLabel25.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 18));
			jLabel25.setForeground(java.awt.Color.white);
			jLabel25.setIcon(new ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix32x32/transform.png")));
			jPanel38.add(jLabel25, null);
		}
		return jPanel38;
	}
	/**
	 * This method initializes jPanel39	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel39() {
		if (jPanel39 == null) {
			jLabel27 = new JLabel();
			java.awt.FlowLayout flowLayout42 = new FlowLayout();
			jPanel39 = new JPanel();
			jPanel39.setLayout(flowLayout42);
			jPanel39.setBackground(new java.awt.Color(69,107,127));
			jPanel39.setPreferredSize(new java.awt.Dimension(540,40));
			jLabel27.setText("Configuración del Sistema");
			jLabel27.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 18));
			jLabel27.setForeground(java.awt.Color.white);
			jLabel27.setIcon(new ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix32x32/transform.png")));
			flowLayout42.setAlignment(java.awt.FlowLayout.LEFT);
			jPanel39.add(jLabel27, null);
		}
		return jPanel39;
	}
	/**
	 * This method initializes jPanel40	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel40() {
		if (jPanel40 == null) {
			java.awt.FlowLayout flowLayout7 = new FlowLayout();
			jPanel40 = new JPanel();
			jPanel40.setLayout(flowLayout7);
			jPanel40.setPreferredSize(new java.awt.Dimension(530,290));
			jPanel40.setBackground(new java.awt.Color(242,242,238));
			flowLayout7.setAlignment(java.awt.FlowLayout.CENTER);
			jPanel40.add(getJPanel45(), null);
			jPanel40.add(getJPanel41(), null);
			jPanel40.add(getJPanel46(), null);
			jPanel40.add(getJPanel42(), null);
			jPanel40.add(getJPanel47(), null);
			jPanel40.add(getJPanel43(), null);
			jPanel40.add(getJPanel87(), null);
			jPanel40.add(getJPanel48(), null);
			jPanel40.add(getJPanel44(), null);
			jPanel40.add(getJPanel106(), null);
			jPanel40.add(getJPanel49(), null);
		}
		return jPanel40;
	}
	/**
	 * This method initializes jPanel41	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel41() {
		if (jPanel41 == null) {
			jLabel11 = new JLabel();
			java.awt.FlowLayout flowLayout10 = new FlowLayout();
			jPanel41 = new JPanel();
			jPanel41.setLayout(flowLayout10);
			jPanel41.setBackground(new java.awt.Color(242,242,238));
			jPanel41.setPreferredSize(new java.awt.Dimension(500,20));
			jLabel11.setText("Clase Local: ");
			jLabel11.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			jLabel11.setPreferredSize(new java.awt.Dimension(180,14));
			flowLayout10.setAlignment(java.awt.FlowLayout.LEFT);
			flowLayout10.setVgap(2);
			jPanel41.add(jLabel11, null);
			jPanel41.add(getTxtLocalClass(), null);
		}
		return jPanel41;
	}
	/**
	 * This method initializes jPanel42	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel42() {
		if (jPanel42 == null) {
			jLabel13 = new JLabel();
			java.awt.FlowLayout flowLayout12 = new FlowLayout();
			jPanel42 = new JPanel();
			jPanel42.setLayout(flowLayout12);
			jPanel42.setPreferredSize(new java.awt.Dimension(500,20));
			jPanel42.setBackground(new java.awt.Color(242,242,238));
			flowLayout12.setVgap(2);
			flowLayout12.setAlignment(java.awt.FlowLayout.LEFT);
			jLabel13.setText("Clase Servidor: ");
			jLabel13.setPreferredSize(new java.awt.Dimension(180,14));
			jLabel13.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			jPanel42.add(jLabel13, null);
			jPanel42.add(getTxtServerClass(), null);
		}
		return jPanel42;
	}
	/**
	 * This method initializes jPanel43	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel43() {
		if (jPanel43 == null) {
			jLabel12 = new JLabel();
			java.awt.FlowLayout flowLayout11 = new FlowLayout();
			jPanel43 = new JPanel();
			jPanel43.setLayout(flowLayout11);
			jPanel43.setBackground(new java.awt.Color(242,242,238));
			jPanel43.setPreferredSize(new java.awt.Dimension(500,20));
			jLabel12.setText("Clase Servidor Central: ");
			jLabel12.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			jLabel12.setPreferredSize(new java.awt.Dimension(180,14));
			flowLayout11.setAlignment(java.awt.FlowLayout.LEFT);
			flowLayout11.setVgap(2);
			jPanel43.add(jLabel12, null);
			jPanel43.add(getTxtCentralServClass(), null);
		}
		return jPanel43;
	}
	/**
	 * This method initializes jPanel44	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel44() {
		if (jPanel44 == null) {
			jLabel14 = new JLabel();
			java.awt.FlowLayout flowLayout13 = new FlowLayout();
			jPanel44 = new JPanel();
			jPanel44.setLayout(flowLayout13);
			jPanel44.setBackground(new java.awt.Color(242,242,238));
			jPanel44.setPreferredSize(new java.awt.Dimension(500,20));
			flowLayout13.setVgap(2);
			flowLayout13.setAlignment(java.awt.FlowLayout.LEFT);
			jLabel14.setText("Clave:");
			jLabel14.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			jLabel14.setPreferredSize(new java.awt.Dimension(180,14));
			jPanel44.add(jLabel14, null);
			jPanel44.add(getTxtClave(), null);
		}
		return jPanel44;
	}
	/**
	 * This method initializes jPanel106	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel106() {
		if (jPanel106 == null) {
			jLabel89 = new JLabel();
			java.awt.FlowLayout flowLayout13 = new FlowLayout();
			jPanel106 = new JPanel();
			jPanel106.setLayout(flowLayout13);
			jPanel106.setBackground(new java.awt.Color(242,242,238));
			jPanel106.setPreferredSize(new java.awt.Dimension(500,20));
			flowLayout13.setVgap(2);
			flowLayout13.setAlignment(java.awt.FlowLayout.LEFT);
			jLabel89.setText("Ruta Objeto RMI:");
			jLabel89.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			jLabel89.setPreferredSize(new java.awt.Dimension(180,14));
			jPanel106.add(jLabel89, null);
			jPanel106.add(getTxtRMI(), null);
		}
		return jPanel106;
	}
	/**
	 * This method initializes jPanel45	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel45() {
		if (jPanel45 == null) {
			jLabel15 = new JLabel();
			java.awt.FlowLayout flowLayout14 = new FlowLayout();
			jPanel45 = new JPanel();
			jPanel45.setLayout(flowLayout14);
			jPanel45.setPreferredSize(new java.awt.Dimension(500,20));
			jPanel45.setBackground(new java.awt.Color(242,242,238));
			flowLayout14.setAlignment(java.awt.FlowLayout.LEFT);
			flowLayout14.setHgap(5);
			flowLayout14.setVgap(2);
			jLabel15.setText("URL Local:");
			jLabel15.setPreferredSize(new java.awt.Dimension(180,14));
			jLabel15.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			jPanel45.add(jLabel15, null);
			jPanel45.add(getTxtUrlLocal(), null);
		}
		return jPanel45;
	}
	/**
	 * This method initializes jPanel46	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel46() {
		if (jPanel46 == null) {
			jLabel16 = new JLabel();
			java.awt.FlowLayout flowLayout15 = new FlowLayout();
			jPanel46 = new JPanel();
			jPanel46.setLayout(flowLayout15);
			jPanel46.setBackground(new java.awt.Color(242,242,238));
			jPanel46.setPreferredSize(new java.awt.Dimension(500,20));
			flowLayout15.setAlignment(java.awt.FlowLayout.LEFT);
			flowLayout15.setVgap(2);
			jLabel16.setText("URL Servidor:");
			jLabel16.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			jLabel16.setPreferredSize(new java.awt.Dimension(180,14));
			jPanel46.add(jLabel16, null);
			jPanel46.add(getTxtUrlServ(), null);
		}
		return jPanel46;
	}
	/**
	 * This method initializes jPanel47	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel47() {
		if (jPanel47 == null) {
			jLabel17 = new JLabel();
			java.awt.FlowLayout flowLayout16 = new FlowLayout();
			jPanel47 = new JPanel();
			jPanel47.setLayout(flowLayout16);
			jPanel47.setPreferredSize(new java.awt.Dimension(500,20));
			jPanel47.setBackground(new java.awt.Color(242,242,238));
			jLabel17.setText("URL Servidor Central:");
			jLabel17.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			jLabel17.setPreferredSize(new java.awt.Dimension(180,14));
			flowLayout16.setAlignment(java.awt.FlowLayout.LEFT);
			flowLayout16.setVgap(2);
			jPanel47.add(jLabel17, null);
			jPanel47.add(getTxtUrlServCentral(), null);
		}
		return jPanel47;
	}
	/**
	 * This method initializes jPanel48	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel48() {
		if (jPanel48 == null) {
			jLabel18 = new JLabel();
			java.awt.FlowLayout flowLayout17 = new FlowLayout();
			jPanel48 = new JPanel();
			jPanel48.setLayout(flowLayout17);
			jPanel48.setPreferredSize(new java.awt.Dimension(500,20));
			jPanel48.setBackground(new java.awt.Color(242,242,238));
			flowLayout17.setAlignment(java.awt.FlowLayout.LEFT);
			flowLayout17.setVgap(2);
			jLabel18.setText("Usuario:");
			jLabel18.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			jLabel18.setPreferredSize(new java.awt.Dimension(180,14));
			jPanel48.add(jLabel18, null);
			jPanel48.add(getTxtUser(), null);
		}
		return jPanel48;
	}
	/**
	 * This method initializes jPanel49	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel49() {
		if (jPanel49 == null) {
			jLabel19 = new JLabel();
			java.awt.FlowLayout flowLayout18 = new FlowLayout();
			jPanel49 = new JPanel();
			jPanel49.setLayout(flowLayout18);
			jPanel49.setPreferredSize(new java.awt.Dimension(500,20));
			jPanel49.setBackground(new java.awt.Color(242,242,238));
			flowLayout18.setAlignment(java.awt.FlowLayout.LEFT);
			flowLayout18.setVgap(2);
			jLabel19.setText("Garantizar Facturación: ");
			jLabel19.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			jLabel19.setPreferredSize(new java.awt.Dimension(180,14));
			jPanel49.add(jLabel19, null);
			jPanel49.add(getRbGarantFactS(), null);
			jPanel49.add(getRbGarantFactN(), null);
			bgGarantFact.add(getRbGarantFactS());
			bgGarantFact.add(getRbGarantFactN());
		}
		return jPanel49;
	}
	/**
	 * This method initializes jPanel101	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel101() {
		if (jPanel101 == null) {
			java.awt.FlowLayout flowLayout18 = new FlowLayout();
			jPanel101 = new JPanel();
			jPanel101.setLayout(flowLayout18);
			jPanel101.setPreferredSize(new java.awt.Dimension(500,20));
			jPanel101.setBackground(new java.awt.Color(242,242,238));
			flowLayout18.setAlignment(java.awt.FlowLayout.LEFT);
			flowLayout18.setVgap(2);
			jPanel101.add(getJLabel77(), null);
			jPanel101.add(getColorCombo(), null);
		}
		return jPanel101;
	}
	/**
	 * This method initializes jPanel102	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel102() {
		if (jPanel102 == null) {
			java.awt.FlowLayout flowLayout18 = new FlowLayout();
			jPanel102 = new JPanel();
			jPanel102.setLayout(flowLayout18);
			jPanel102.setPreferredSize(new java.awt.Dimension(500,20));
			jPanel102.setBackground(new java.awt.Color(242,242,238));
			flowLayout18.setAlignment(java.awt.FlowLayout.LEFT);
			flowLayout18.setVgap(2);
			jPanel102.add(getJLabel78(), null);
			jPanel102.add(getRbContOrdinarioS(), null);
			jPanel102.add(getRbContOrdinarioN(), null);
			bgGarantFact.add(getRbContOrdinarioS());
			bgGarantFact.add(getRbContOrdinarioN());
		}
		return jPanel102;
	}
	/**
	 * This method initializes txtLocalClass	
	 * 	
	 * @return javax.swing.JTextField	
	 */    
	private JTextField getTxtLocalClass() {
		if (txtLocalClass == null) {
			txtLocalClass = new JTextField();
			txtLocalClass.setPreferredSize(new java.awt.Dimension(270,17));
			txtLocalClass.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
		}
		return txtLocalClass;
	}
	/**
	 * This method initializes txtCentralServClass	
	 * 	
	 * @return javax.swing.JTextField	
	 */    
	private JTextField getTxtCentralServClass() {
		if (txtCentralServClass == null) {
			txtCentralServClass = new JTextField();
			txtCentralServClass.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			txtCentralServClass.setPreferredSize(new java.awt.Dimension(270,17));
			txtCentralServClass.setText("");
		}
		return txtCentralServClass;
	}
	/**
	 * This method initializes txtServerClass	
	 * 	
	 * @return javax.swing.JTextField	
	 */    
	private JTextField getTxtServerClass() {
		if (txtServerClass == null) {
			txtServerClass = new JTextField();
			txtServerClass.setPreferredSize(new java.awt.Dimension(270,17));
			txtServerClass.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
		}
		return txtServerClass;
	}
	/**
	 * This method initializes txtClave	
	 * 	
	 * @return javax.swing.JTextField	
	 */    
	private JTextField getTxtClave() {
		if (txtClave == null) {
			txtClave = new JTextField();
			txtClave.setPreferredSize(new java.awt.Dimension(270,17));
			txtClave.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
		}
		return txtClave;
	}
	/**
	 * This method initializes txtRMI	
	 * 	
	 * @return javax.swing.JTextField	
	 */    
	private JTextField getTxtRMI() {
		if (txtRMI == null) {
			txtRMI = new JTextField();
			txtRMI.setPreferredSize(new java.awt.Dimension(270,17));
			txtRMI.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
		}
		return txtRMI;
	}
	/**
	 * This method initializes txtUrlLocal	
	 * 	
	 * @return javax.swing.JTextField	
	 */    
	private JTextField getTxtUrlLocal() {
		if (txtUrlLocal == null) {
			txtUrlLocal = new JTextField();
			txtUrlLocal.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			txtUrlLocal.setPreferredSize(new java.awt.Dimension(270,17));
		}
		return txtUrlLocal;
	}
	/**
	 * This method initializes txtUrlServ	
	 * 	
	 * @return javax.swing.JTextField	
	 */    
	private JTextField getTxtUrlServ() {
		if (txtUrlServ == null) {
			txtUrlServ = new JTextField();
			txtUrlServ.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			txtUrlServ.setPreferredSize(new java.awt.Dimension(270,17));
		}
		return txtUrlServ;
	}
	/**
	 * This method initializes txtUrlServCentral	
	 * 	
	 * @return javax.swing.JTextField	
	 */    
	private JTextField getTxtUrlServCentral() {
		if (txtUrlServCentral == null) {
			txtUrlServCentral = new JTextField();
			txtUrlServCentral.setPreferredSize(new java.awt.Dimension(270,17));
			txtUrlServCentral.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
		}
		return txtUrlServCentral;
	}
	/**
	 * This method initializes txtUser	
	 * 	
	 * @return javax.swing.JTextField	
	 */    
	private JTextField getTxtUser() {
		if (txtUser == null) {
			txtUser = new JTextField();
			txtUser.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			txtUser.setPreferredSize(new java.awt.Dimension(270,17));
		}
		return txtUser;
	}
	/**
	 * This method initializes jPanel50	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel50() {
		if (jPanel50 == null) {
			java.awt.FlowLayout flowLayout19 = new FlowLayout();
			jPanel50 = new JPanel();
			jPanel50.setLayout(flowLayout19);
			jPanel50.setPreferredSize(new java.awt.Dimension(520,30));
			jPanel50.setBackground(new java.awt.Color(226,226,222));
			flowLayout19.setAlignment(java.awt.FlowLayout.RIGHT);
			flowLayout19.setHgap(0);
			flowLayout19.setVgap(0);
			jPanel50.add(getBtnSaveBD(), null);
		}
		return jPanel50;
	}
	/**
	 * This method initializes btnSaveBD	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getBtnSaveBD() {
		if (btnSaveBD == null) {
			btnSaveBD = new JButton();
			btnSaveBD.setText("Guardar");
			btnSaveBD.setIcon(new ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix16x16/data_disk.png")));
			btnSaveBD.setBackground(new java.awt.Color(242,242,238));
			btnSaveBD.setEnabled(false);
			btnSaveBD.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(java.awt.event.ActionEvent e) {    
					guardarValoresBD();
				}
			});
		}
		return btnSaveBD;
	}
	/**
	 * This method initializes jPanel51	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel51() {
		if (jPanel51 == null) {
			java.awt.FlowLayout flowLayout110 = new FlowLayout();
			jPanel51 = new JPanel();
			jPanel51.setLayout(flowLayout110);
			jPanel51.setPreferredSize(new java.awt.Dimension(530,340));
			jPanel51.setBackground(new java.awt.Color(242,242,238));
			flowLayout110.setAlignment(java.awt.FlowLayout.CENTER);
			jPanel51.add(getJPanel105(), null);
			jPanel51.add(getJPanel52(), null);
			jPanel51.add(getJPanel53(), null);
			jPanel51.add(getJPanel84(), null);
			jPanel51.add(getJPanel85(), null);
			jPanel51.add(getJPanel54(), null);
			jPanel51.add(getJPanel104(), null);
			jPanel51.add(getJPanel99(), null);
			jPanel51.add(getJPanel55(), null);
		}
		return jPanel51;
	}
	/**
	 * This method initializes jPanel52	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel52() {
		if (jPanel52 == null) {
			jLabel20 = new JLabel();
			java.awt.FlowLayout flowLayout21 = new FlowLayout();
			jPanel52 = new JPanel();
			jPanel52.setLayout(flowLayout21);
			jPanel52.setBackground(new java.awt.Color(242,242,238));
			jPanel52.setPreferredSize(new java.awt.Dimension(500,20));
			flowLayout21.setVgap(2);
			flowLayout21.setAlignment(java.awt.FlowLayout.LEFT);
			jLabel20.setText("Tiempo Vigencia: ");
			jLabel20.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			jLabel20.setPreferredSize(new java.awt.Dimension(180,14));
			jPanel52.add(jLabel20, null);
			jPanel52.add(getTxtDiasVig(), null);
		}
		return jPanel52;
	}
	private javax.swing.JComboBox getCmbTipoVigencia() {
		if(cmbTipoVigencia == null) {
			cmbTipoVigencia = new javax.swing.JComboBox();
			cmbTipoVigencia.setBackground(new java.awt.Color(242,242,238));
			cmbTipoVigencia.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			cmbTipoVigencia.setPreferredSize(new java.awt.Dimension(100,17));
			cmbTipoVigencia.addItem("Dia");
			cmbTipoVigencia.addItem("Mes");
			cmbTipoVigencia.addItemListener(new java.awt.event.ItemListener() { 
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					cambioApart();
				}
			});
			
		}
		return cmbTipoVigencia;
	}
	private JPanel getJPanel105() {
		if (jPanel105 == null) {
			jLabel87 = new JLabel();
			java.awt.FlowLayout flowLayout21 = new FlowLayout();
			jPanel105 = new JPanel();
			jPanel105.setLayout(flowLayout21);
			jPanel105.setBackground(new java.awt.Color(242,242,238));
			jPanel105.setPreferredSize(new java.awt.Dimension(500,20));
			flowLayout21.setVgap(2);
			flowLayout21.setAlignment(java.awt.FlowLayout.LEFT);
			jLabel87.setText("Cálculo Vigencia: ");
			jLabel87.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			jLabel87.setPreferredSize(new java.awt.Dimension(180,14));
			jPanel105.add(jLabel87, null);
			jPanel105.add(getCmbTipoVigencia(), null);
		}
		return jPanel105;
	}
	/**
	 * This method initializes txtDiasVig	
	 * 	
	 * @return javax.swing.JTextField	
	 */    
	private JTextField getTxtDiasVig() {
		if (txtDiasVig == null) {
			txtDiasVig = new JTextField();
			txtDiasVig.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			txtDiasVig.setPreferredSize(new java.awt.Dimension(50,17));
			
		}
		return txtDiasVig;
	}
	/**
	 * This method initializes jPanel53	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel53() {
		if (jPanel53 == null) {
			jLabel21 = new JLabel();
			java.awt.FlowLayout flowLayout31 = new FlowLayout();
			jPanel53 = new JPanel();
			jPanel53.setLayout(flowLayout31);
			jPanel53.setPreferredSize(new java.awt.Dimension(500,20));
			jPanel53.setBackground(new java.awt.Color(242,242,238));
			jLabel21.setText("Permitir Rebajas?");
			jLabel21.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			jLabel21.setPreferredSize(new java.awt.Dimension(180,14));
			flowLayout31.setAlignment(java.awt.FlowLayout.LEFT);
			flowLayout31.setVgap(2);
			jPanel53.add(jLabel21, null);
			jPanel53.add(getRbRebajS(), null);
			jPanel53.add(getRbRebajN(), null);
			bgRebApart.add(getRbRebajS());
			bgRebApart.add(getRbRebajN());
			
		}
		return jPanel53;
	}
	
	/**
	 * This method initializes jPanel54	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel54() {
		if (jPanel54 == null) {
			jLabel22 = new JLabel();
			java.awt.FlowLayout flowLayout41 = new FlowLayout();
			jPanel54 = new JPanel();
			jPanel54.setLayout(flowLayout41);
			jPanel54.setPreferredSize(new java.awt.Dimension(500,20));
			jPanel54.setBackground(new java.awt.Color(242,242,238));
			jLabel22.setText("Numero de Abonos: ");
			jLabel22.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			jLabel22.setPreferredSize(new java.awt.Dimension(180,14));
			flowLayout41.setAlignment(java.awt.FlowLayout.LEFT);
			flowLayout41.setVgap(2);
			jPanel54.add(jLabel22, null);
			jPanel54.add(getCmbNumAbonos(), null);
		}
		return jPanel54;
	}
	/**
	 * This method initializes jPanel55	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel55() {
		if (jPanel55 == null) {
			java.awt.FlowLayout flowLayout51 = new FlowLayout();
			jPanel55 = new JPanel();
			jPanel55.setLayout(flowLayout51);
			jPanel55.setPreferredSize(new java.awt.Dimension(500,130));
			jPanel55.setBackground(new java.awt.Color(242,242,238));
			jPanel55.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Detalle de los Abonos", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", java.awt.Font.BOLD, 10), java.awt.Color.black));
			flowLayout51.setAlignment(java.awt.FlowLayout.LEFT);
			jPanel55.add(getJPanel56(), null);
			jPanel55.add(getJPanel57(), null);
			jPanel55.add(getJPanel58(), null);
		}
		return jPanel55;
	}
	/**
	 * This method initializes jPanel56	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel56() {
		if (jPanel56 == null) {
			jLabel23 = new JLabel();
			java.awt.FlowLayout flowLayout61 = new FlowLayout();
			jPanel56 = new JPanel();
			jPanel56.setLayout(flowLayout61);
			jPanel56.setBackground(new java.awt.Color(242,242,238));
			jPanel56.setPreferredSize(new java.awt.Dimension(280,20));
			flowLayout61.setVgap(2);
			flowLayout61.setAlignment(java.awt.FlowLayout.LEFT);
			jLabel23.setText("ID Abono: ");
			jLabel23.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			jLabel23.setPreferredSize(new java.awt.Dimension(180,14));
			jPanel56.add(jLabel23, null);
			jPanel56.add(getCmbIDApart(), null);
		}
		return jPanel56;
	}
	/**
	 * This method initializes cmbIDApart	
	 * 	
	 * @return javax.swing.JComboBox	
	 */    
	private JComboBox getCmbIDApart() {
		if (cmbIDApart == null) {
			cmbIDApart = new JComboBox();
			cmbIDApart.setPreferredSize(new java.awt.Dimension(50,17));
			cmbIDApart.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 10));
			cmbIDApart.setBackground(new java.awt.Color(242,242,238));
			cmbIDApart.addItemListener(new java.awt.event.ItemListener() { 
				public void itemStateChanged(java.awt.event.ItemEvent e) {    
					// Mostrar el Abono Actual
					if (e.getItem() != null) {
						txtValorAbo.setText(Integer.toString(((Abono)listaAbonos.get(e.getItem())).getValor()));
						btnActAbono.setEnabled(false);
					}
				}
			});
		}
		return cmbIDApart;
	}
	/**
	 * This method initializes jPanel57	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel57() {
		if (jPanel57 == null) {
			jLabel24 = new JLabel();
			java.awt.FlowLayout flowLayout71 = new FlowLayout();
			jPanel57 = new JPanel();
			jPanel57.setLayout(flowLayout71);
			jPanel57.setBackground(new java.awt.Color(242,242,238));
			jPanel57.setPreferredSize(new java.awt.Dimension(280,20));
			flowLayout71.setAlignment(java.awt.FlowLayout.LEFT);
			flowLayout71.setVgap(2);
			jLabel24.setText("Valor del Abono: ");
			jLabel24.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			jLabel24.setPreferredSize(new java.awt.Dimension(180,14));
			jPanel57.add(jLabel24, null);
			jPanel57.add(getTxtValorAbo(), null);
		}
		return jPanel57;
	}
	/**
	 * This method initializes txtValorAbo	
	 * 	
	 * @return javax.swing.JTextField	
	 */    
	private JTextField getTxtValorAbo() {
		if (txtValorAbo == null) {
			txtValorAbo = new JTextField();
			txtValorAbo.setPreferredSize(new java.awt.Dimension(50,17));
			txtValorAbo.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			txtValorAbo.addKeyListener(new java.awt.event.KeyAdapter() { 
				public void keyTyped(java.awt.event.KeyEvent e) {    
					btnActAbono.setEnabled(true);
				}
			});
		}
		return txtValorAbo;
	}
	/**
	 * This method initializes jPanel58	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel58() {
		if (jPanel58 == null) {
			java.awt.FlowLayout flowLayout8 = new FlowLayout();
			jPanel58 = new JPanel();
			jPanel58.setLayout(flowLayout8);
			jPanel58.setPreferredSize(new java.awt.Dimension(480,50));
			jPanel58.setBackground(new java.awt.Color(242,242,238));
			flowLayout8.setHgap(0);
			flowLayout8.setVgap(20);
			flowLayout8.setAlignment(java.awt.FlowLayout.RIGHT);
			jPanel58.add(getBtnActAbono(), null);
		}
		return jPanel58;
	}
	/**
	 * This method initializes btnActAbono	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getBtnActAbono() {
		if (btnActAbono == null) {
			btnActAbono = new JButton();
			btnActAbono.setBackground(new java.awt.Color(226,226,222));
			btnActAbono.setText("Guardar Valor");
			btnActAbono.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 10));
			btnActAbono.setIcon(new ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix16x16/document_new.png")));
			btnActAbono.setEnabled(false);
			btnActAbono.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(java.awt.event.ActionEvent e) {    
					modifAbono(cmbIDApart.getSelectedItem().toString(), 
						Integer.parseInt(txtValorAbo.getText()));
					btnActAbono.setEnabled(false);
				}
			});
		}
		return btnActAbono;
	}
	/**
	 * This method initializes jPanel59	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel59() {
		if (jPanel59 == null) {
			java.awt.FlowLayout flowLayout111 = new FlowLayout();
			jPanel59 = new JPanel();
			jPanel59.setLayout(flowLayout111);
			jPanel59.setPreferredSize(new java.awt.Dimension(520,30));
			jPanel59.setBackground(new java.awt.Color(226,226,222));
			flowLayout111.setHgap(0);
			flowLayout111.setVgap(0);
			flowLayout111.setAlignment(java.awt.FlowLayout.RIGHT);
			jPanel59.add(getBtnSaveApart(), null);
		}
		return jPanel59;
	}
	/**
	 * This method initializes btnSaveApart	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getBtnSaveApart() {
		if (btnSaveApart == null) {
			btnSaveApart = new JButton();
			btnSaveApart.setText("Guardar");
			btnSaveApart.setIcon(new ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix16x16/data_disk.png")));
			btnSaveApart.setBackground(new java.awt.Color(242,242,238));
			btnSaveApart.setEnabled(false);
			btnSaveApart.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(java.awt.event.ActionEvent e) {    
					guardarValoresApart();
				}
			});
		}
		return btnSaveApart;
	}
	/**
	 * This method initializes jPanel60	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel60() {
		if (jPanel60 == null) {
			java.awt.FlowLayout flowLayout112 = new FlowLayout();
			jPanel60 = new JPanel();
			jPanel60.setLayout(flowLayout112);
			jPanel60.setBackground(new java.awt.Color(242,242,238));
			jPanel60.setPreferredSize(new java.awt.Dimension(530,420));
			flowLayout112.setAlignment(java.awt.FlowLayout.CENTER);
			flowLayout112.setVgap(1);
			jPanel60.add(getJPanel98(), null);
			jPanel60.add(getJPanel61(), null);
			jPanel60.add(getJPanel82(), null);
			jPanel60.add(getJPanel88(), null);
			jPanel60.add(getJPanel200(), null);
			jPanel60.add(getJPanel89(), null);
			jPanel60.add(getJPanel101(), null);
			jPanel60.add(getJPanel78(), null);
			jPanel60.add(getJPanel67(), null);
			jPanel60.add(getJPanel102(), null);
			jPanel60.add(getJPanel79(), null);
			jPanel60.add(getJPanel80(), null);
			jPanel60.add(getJPanel90(), null);
			jPanel60.add(getJPanel83(), null);
			jPanel60.add(getJPanel81(), null);
			jPanel60.add(getJPanel91(), null);
			jPanel60.add(getJPanel92(), null);
			jPanel60.add(getJPanel93(), null);
			jPanel60.add(getJPanel94(), null);
			jPanel60.add(getJPanel73(), null);
		}
		return jPanel60;
	}
	/**
	 * This method initializes jPanel61	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel61() {
		if (jPanel61 == null) {
			jLabel26 = new JLabel();
			java.awt.FlowLayout flowLayout23 = new FlowLayout();
			jPanel61 = new JPanel();
			jPanel61.setLayout(flowLayout23);
			jPanel61.setPreferredSize(new java.awt.Dimension(500,20));
			jPanel61.setBackground(new java.awt.Color(242,242,238));
			jLabel26.setText("Longitud Código Interno:");
			jLabel26.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			jLabel26.setPreferredSize(new java.awt.Dimension(180,14));
			flowLayout23.setAlignment(java.awt.FlowLayout.LEFT);
			flowLayout23.setVgap(2);
			jPanel61.add(jLabel26, null);
			jPanel61.add(getTxtLongCodInt(), null);
		}
		return jPanel61;
	}
	/**
	 * This method initializes txtLongCodInt	
	 * 	
	 * @return javax.swing.JTextField	
	 */    
	private JTextField getTxtLongCodInt() {
		if (txtLongCodInt == null) {
			txtLongCodInt = new JTextField();
			txtLongCodInt.setPreferredSize(new java.awt.Dimension(100,17));
			txtLongCodInt.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
		}
		return txtLongCodInt;
	}
	/**
	 * This method initializes jPanel62	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel62() {
		if (jPanel62 == null) {
			java.awt.FlowLayout flowLayout32 = new FlowLayout();
			jPanel62 = new JPanel();
			jPanel62.setLayout(flowLayout32);
			jPanel62.setPreferredSize(new java.awt.Dimension(520,30));
			jPanel62.setBackground(new java.awt.Color(226,226,222));
			flowLayout32.setAlignment(java.awt.FlowLayout.RIGHT);
			flowLayout32.setHgap(0);
			flowLayout32.setVgap(0);
			jPanel62.add(getBtnSaveFact(), null);
		}
		return jPanel62;
	}
	/**
	 * This method initializes btnSaveFact	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getBtnSaveFact() {
		if (btnSaveFact == null) {
			btnSaveFact = new JButton();
			btnSaveFact.setText("Guardar");
			btnSaveFact.setBackground(new java.awt.Color(242,242,238));
			btnSaveFact.setIcon(new ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix16x16/data_disk.png")));
			btnSaveFact.setEnabled(false);
			btnSaveFact.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(java.awt.event.ActionEvent e) {    
					guardarValoresFact();
				}
			});
		}
		return btnSaveFact;
	}
	/**
	 * This method initializes jPanel63	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel63() {
		if (jPanel63 == null) {
			jPanel63 = new JPanel();
			jPanel63.add(getJPanel68(), null);
			jPanel63.add(getJPanel69(), null);
			jPanel63.setBackground(new java.awt.Color(242,242,238));
			jPanel63.setPreferredSize(new java.awt.Dimension(530,405));
			jPanel63.add(getJPanel64(), null);
			jPanel63.add(getJPanel72(), null);
			jPanel63.add(getJPanel71(), null);
			jPanel63.add(getJPanel74(), null);
			jPanel63.add(getJPanel76(), null);
			jPanel63.add(getJPanel70(), null);
			jPanel63.add(getJPanel86(), null);
			jPanel63.add(getJPanel65(), null);
			jPanel63.add(getJPanel95(), null);
			jPanel63.add(getJPanel96(), null);
			jPanel63.add(getJPanel75(), null);
			jPanel63.add(getJPanel103(), null);
			jPanel63.add(getJPanel97(), null);
			jPanel63.add(getJPanel66(), null);
		}
		return jPanel63;
	}
	/**
	 * This method initializes jPanel64	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel64() {
		if (jPanel64 == null) {
			jLabel28 = new JLabel();
			java.awt.FlowLayout flowLayout52 = new FlowLayout();
			jPanel64 = new JPanel();
			jPanel64.setLayout(flowLayout52);
			jPanel64.setPreferredSize(new java.awt.Dimension(500,20));
			jPanel64.setBackground(new java.awt.Color(242,242,238));
			flowLayout52.setAlignment(java.awt.FlowLayout.LEFT);
			flowLayout52.setVgap(2);
			jLabel28.setText("Puerto Socket: ");
			jLabel28.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			jLabel28.setPreferredSize(new java.awt.Dimension(90,14));
			jPanel64.add(jLabel28, null);
			jPanel64.add(getTxtPtoSocket(), null);
			jPanel64.add(getJLabel64(), null);
			jPanel64.add(getTxtPtoServ(), null);
			jPanel64.add(getJLabel65(), null);
			jPanel64.add(getTxtPtoServCent(), null);
			jPanel64.add(getJLabel66(), null);
			jPanel64.add(getTxtPtoSync(), null);
		}
		return jPanel64;
	}
	/**
	 * This method initializes jPanel65	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel65() {
		if (jPanel65 == null) {
			java.awt.FlowLayout flowLayout62 = new FlowLayout();
			jPanel65 = new JPanel();
			jPanel65.setLayout(flowLayout62);
			jPanel65.setPreferredSize(new java.awt.Dimension(500,20));
			jPanel65.setBackground(new java.awt.Color(242,242,238));
			flowLayout62.setAlignment(java.awt.FlowLayout.LEFT);
			flowLayout62.setVgap(2);
			bgFiscalPrn.add(getRbFiscalPrnS());
			bgFiscalPrn.add(getRbFiscalPrnN());
			jLabel40 = new JLabel();			
			jLabel40.setText("Verificar Linea:");
			jLabel40.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			jLabel40.setPreferredSize(new java.awt.Dimension(90,14));
			jPanel65.add(jLabel40, null);
			jPanel65.add(getRbVerifLinS(), null);
			jPanel65.add(getRbVerifLinN(), null);
			jPanel65.add(getJLabel72(), null);
			jPanel65.add(getRbSincAfiliadoS(), null);
			jPanel65.add(getRbSincAfiliadoN(), null);
			bgVerifLin.add(getRbVerifLinS());
			bgVerifLin.add(getRbVerifLinN());			
		}
		return jPanel65;
	}
	/**
	 * This method initializes jPanel67	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel67() {
		if (jPanel67 == null) {
			jLabel31 = new JLabel();
			java.awt.FlowLayout flowLayout81 = new FlowLayout();
			jPanel67 = new JPanel();
			jPanel67.setLayout(flowLayout81);
			jPanel67.setPreferredSize(new java.awt.Dimension(500,20));
			jPanel67.setBackground(new java.awt.Color(242,242,238));
			flowLayout81.setAlignment(java.awt.FlowLayout.LEFT);
			flowLayout81.setVgap(2);
			jLabel31.setText("Requiere Cliente: ");
			jLabel31.setPreferredSize(new java.awt.Dimension(180,14));
			jLabel31.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			jPanel67.add(jLabel31, null);
			jPanel67.add(getRbReqCliS(), null);
			jPanel67.add(getRbReqCliN(), null);
			bgReqCli.add(getRbReqCliS());
			bgReqCli.add(getRbReqCliN());
	}
		return jPanel67;
	}
	/**
	 * This method initializes jPanel88	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel88() {
		if (jPanel88 == null) {
			java.awt.FlowLayout flowLayout81 = new FlowLayout();
			jPanel88 = new JPanel();
			jPanel88.setLayout(flowLayout81);
			jPanel88.setPreferredSize(new java.awt.Dimension(500,20));
			jPanel88.setBackground(new java.awt.Color(242,242,238));
			flowLayout81.setAlignment(java.awt.FlowLayout.LEFT);
			flowLayout81.setVgap(2);
			jPanel88.add(getJLabel57(), null);
			jPanel88.add(getTxtRetIVA(), null);
	}
		return jPanel88;
	}
	/**
	 * This method initializes jPanel89
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel89() {
		if (jPanel89 == null) {
			java.awt.FlowLayout flowLayout81 = new FlowLayout();
			jPanel89 = new JPanel();
			jPanel89.setLayout(flowLayout81);
			jPanel89.setPreferredSize(new java.awt.Dimension(500,20));
			jPanel89.setBackground(new java.awt.Color(242,242,238));
			flowLayout81.setAlignment(java.awt.FlowLayout.LEFT);
			flowLayout81.setVgap(2);
			jPanel89.add(getJLabel58(), null);
			jPanel89.add(getTxtMonedaLocal(), null);
	}
		return jPanel89;
	}
	/**
	 * This method initializes jPanel90	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel90() {
		if (jPanel90 == null) {
			java.awt.FlowLayout flowLayout81 = new FlowLayout();
			jPanel90 = new JPanel();
			jPanel90.setLayout(flowLayout81);
			jPanel90.setPreferredSize(new java.awt.Dimension(500,20));
			jPanel90.setBackground(new java.awt.Color(242,242,238));
			flowLayout81.setAlignment(java.awt.FlowLayout.LEFT);
			flowLayout81.setVgap(2);
			jPanel90.add(getJLabel59(), null);
			jPanel90.add(getTxtNombreBanco(), null);
	}
		return jPanel90;
	}
	/**
	 * This method initializes jPanel91	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel91() {
		if (jPanel91 == null) {
			java.awt.FlowLayout flowLayout81 = new FlowLayout();
			jPanel91 = new JPanel();
			jPanel91.setLayout(flowLayout81);
			jPanel91.setPreferredSize(new java.awt.Dimension(500,20));
			jPanel91.setBackground(new java.awt.Color(242,242,238));
			flowLayout81.setAlignment(java.awt.FlowLayout.LEFT);
			flowLayout81.setVgap(2);
			jPanel91.add(getJLabel60(), null);
			jPanel91.add(getRbImpFteChequeS(), null);
			jPanel91.add(getRbImpFteChequeN(), null);
			bgImpFteCheque.add(getRbImpFteChequeS());
			bgImpFteCheque.add(getRbImpFteChequeN());
			jPanel91.add(getJLabel61(), null);
			jPanel91.add(getRbImpDorsoChequeS(), null);
			jPanel91.add(getRbImpDorsoChequeN(), null);
			bgImpDorsoCheque.add(getRbImpDorsoChequeS());
			bgImpDorsoCheque.add(getRbImpDorsoChequeN());
	}
		return jPanel91;
	}
	/**
	 * This method initializes jPanel92	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel92() {
		if (jPanel92 == null) {
			java.awt.FlowLayout flowLayout81 = new FlowLayout();
			jPanel92 = new JPanel();
			jPanel92.setLayout(flowLayout81);
			jPanel92.setPreferredSize(new java.awt.Dimension(500,20));
			jPanel92.setBackground(new java.awt.Color(242,242,238));
			flowLayout81.setAlignment(java.awt.FlowLayout.LEFT);
			flowLayout81.setVgap(2);
			jPanel92.add(getJLabel62(), null);
			jPanel92.add(getRbCedulaObligS(), null);
			jPanel92.add(getRbCedulaObligN(), null);
			bgCedulaOblig.add(getRbCedulaObligS());
			bgCedulaOblig.add(getRbCedulaObligN());
			jPanel92.add(getJLabel63(), null);
			jPanel92.add(getRbNumConfObligS(), null);
			jPanel92.add(getRbNumConfObligN(), null);
			bgNumConfOblig.add(getRbNumConfObligS());
			bgNumConfOblig.add(getRbNumConfObligN());
	}
		return jPanel92;
	}
	/**
	 * This method initializes jPanel93	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel93() {
		if (jPanel93 == null) {
			java.awt.FlowLayout flowLayout81 = new FlowLayout();
			jPanel93 = new JPanel();
			jPanel93.setLayout(flowLayout81);
			jPanel93.setPreferredSize(new java.awt.Dimension(500,20));
			jPanel93.setBackground(new java.awt.Color(242,242,238));
			jPanel93.add(getJLabel842(), null);
			jPanel93.add(getJCheckBox(), null);
			jPanel93.add(getJCheckBox1(), null);
			jPanel93.add(getJCheckBox2(), null);
			flowLayout81.setAlignment(java.awt.FlowLayout.LEFT);
			flowLayout81.setVgap(2);
	}
		return jPanel93;
	}
	/**
	 * This method initializes jPanel94	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel94() {
		if (jPanel94 == null) {
			java.awt.FlowLayout flowLayout81 = new FlowLayout();
			jPanel94 = new JPanel();
			jPanel94.setLayout(flowLayout81);
			jPanel94.setPreferredSize(new java.awt.Dimension(500,20));
			jPanel94.setBackground(new java.awt.Color(242,242,238));
			jPanel94.add(getJLabel98(), null);
			jPanel94.add(getTxtIpServPDA(), null);
			jPanel94.add(getJLabel99(), null);
			jPanel94.add(getTxtPuertoServPDA(), null);
			flowLayout81.setAlignment(java.awt.FlowLayout.LEFT);
			flowLayout81.setVgap(2);
	}
		return jPanel94;
	}
	/**
	 * This method initializes jPanel95	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel95() {
		if (jPanel95 == null) {
			java.awt.FlowLayout flowLayout62 = new FlowLayout();
			jPanel95 = new JPanel();
			jPanel95.setLayout(flowLayout62);
			jPanel95.setPreferredSize(new java.awt.Dimension(500,20));
			jPanel95.setBackground(new java.awt.Color(242,242,238));
			flowLayout62.setAlignment(java.awt.FlowLayout.LEFT);
			flowLayout62.setVgap(2);
			jPanel95.add(getJLabel67(), null);
			jPanel95.add(getRbApagarSistS(), null);
			jPanel95.add(getRbApagarSistN(), null);
			jPanel95.add(getJLabel68(), null);
			jPanel95.add(getRbReiniciarSistS(), null);
			jPanel95.add(getRbReiniciarSistN(), null);
			jPanel95.add(getJLabel69(), null);
			jPanel95.add(getRbSyncCommanderS(), null);
			jPanel95.add(getRbSyncCommanderN(), null);
			bgApagarSist.add(getRbApagarSistS());
			bgApagarSist.add(getRbApagarSistN());
			bgReiniciarSist.add(getRbReiniciarSistS());		
			bgReiniciarSist.add(getRbReiniciarSistN());		
			bgSyncCommander.add(getRbSyncCommanderS());
			bgSyncCommander.add(getRbSyncCommanderN());
		}
		return jPanel95;
	}
	/**
	 * This method initializes jPanel96	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel96() {
		if (jPanel96 == null) {
			//jLabel29 = new JLabel();
			java.awt.FlowLayout flowLayout62 = new FlowLayout();
			jPanel96 = new JPanel();
			jPanel96.setLayout(flowLayout62);
			jPanel96.setPreferredSize(new java.awt.Dimension(500,20));
			jPanel96.setBackground(new java.awt.Color(242,242,238));
			flowLayout62.setAlignment(java.awt.FlowLayout.LEFT);
			flowLayout62.setVgap(2);
			jPanel96.add(getJLabel71(), null);
			jPanel96.add(getRbCambioFechaS(), null);
			jPanel96.add(getRbCambioFechaN(), null);
			jPanel96.add(getJLabel70(), null);
			jPanel96.add(getRbActCajaTempS(), null);
			jPanel96.add(getRbActCajaTempN(), null);
			bgCambioFecha.add(getRbCambioFechaS());
			bgCambioFecha.add(getRbCambioFechaN());
			bgSincAfiliado.add(getRbSincAfiliadoS());		
			bgSincAfiliado.add(getRbSincAfiliadoN());		
			bgActCajaTemp.add(getRbActCajaTempS());
			bgActCajaTemp.add(getRbActCajaTempN());
		}
		return jPanel96;
	}
	/**
	 * This method initializes jPanel97	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel97() {
		if (jPanel97 == null) {
			//jLabel29 = new JLabel();
			java.awt.FlowLayout flowLayout62 = new FlowLayout();
			jPanel97 = new JPanel();
			jPanel97.setLayout(flowLayout62);
			jPanel97.setPreferredSize(new java.awt.Dimension(500,20));
			jPanel97.setBackground(new java.awt.Color(242,242,238));
			flowLayout62.setAlignment(java.awt.FlowLayout.LEFT);
			flowLayout62.setVgap(2);
			jPanel97.add(getJLabel73(), null);
			jPanel97.add(getTxtLongitudVisor(), null);
			jPanel97.add(getJLabel74(), null);
			jPanel97.add(getCmbSaltoVisor(), null);
			/*jLabel29.setText("Impresora Fiscal: ");
			jLabel29.setPreferredSize(new java.awt.Dimension(90,14));
			jLabel29.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			jPanel65.add(jLabel29, null);
			jPanel65.add(getRbFiscalPrnS(), null);
			jPanel65.add(getRbFiscalPrnN(), null);
			bgFiscalPrn.add(getRbFiscalPrnS());
			bgFiscalPrn.add(getRbFiscalPrnN());
			jLabel40 = new JLabel();			
			jLabel40.setText("Verificar Linea:");
			jLabel40.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			jLabel40.setPreferredSize(new java.awt.Dimension(90,14));
			jPanel65.add(jLabel40, null);
			jPanel65.add(getRbVerifLinS(), null);
			jPanel65.add(getRbVerifLinN(), null);
			bgVerifLin.add(getRbVerifLinS());
			bgVerifLin.add(getRbVerifLinN());*/		
		}
		return jPanel97;
	}
	/**
	 * This method initializes jPanel98	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel98() {
		if (jPanel98 == null) {
			java.awt.FlowLayout flowLayout81 = new FlowLayout();
			jPanel98 = new JPanel();
			jPanel98.setLayout(flowLayout81);
			jPanel98.setPreferredSize(new java.awt.Dimension(500,20));
			jPanel98.setBackground(new java.awt.Color(242,242,238));
			flowLayout81.setAlignment(java.awt.FlowLayout.LEFT);
			flowLayout81.setVgap(2);
			jPanel98.add(getJLabel75(), null);
			jPanel98.add(getJTextField(), null);
			jPanel98.add(getJLabel85(), null);
			jPanel98.add(getValidarClaveS(), null);
			jPanel98.add(getValidarClaveN(), null);
			claveAlSalir.add(getValidarClaveS());
			claveAlSalir.add(getValidarClaveN());
	}
		return jPanel98;
	}

	/* 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel68() {
		if (jPanel68 == null) {
			jLabel32 = new JLabel();
			java.awt.FlowLayout flowLayout9 = new FlowLayout();
			jPanel68 = new JPanel();
			jPanel68.setLayout(flowLayout9);
			jPanel68.setPreferredSize(new java.awt.Dimension(500,20));
			jPanel68.setBackground(new java.awt.Color(242,242,238));
			flowLayout9.setAlignment(java.awt.FlowLayout.LEFT);
			flowLayout9.setVgap(2);
			jLabel32.setText("URL Principal: ");
			jLabel32.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			jLabel32.setPreferredSize(new java.awt.Dimension(90,14));
			jPanel68.add(jLabel32, null);
			jPanel68.add(getTxtUrlPrinc(), null);
		}
		return jPanel68;
	}
	/**
	 * This method initializes jPanel69	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel69() {
		if (jPanel69 == null) {
			jLabel33 = new JLabel();
			java.awt.FlowLayout flowLayout113 = new FlowLayout();
			jPanel69 = new JPanel();
			jPanel69.setLayout(flowLayout113);
			jPanel69.setPreferredSize(new java.awt.Dimension(500,20));
			jPanel69.setBackground(new java.awt.Color(242,242,238));
			jPanel69.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			jLabel33.setText("URL Verificador:");
			jLabel33.setPreferredSize(new java.awt.Dimension(90,14));
			jLabel33.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			flowLayout113.setVgap(2);
			flowLayout113.setAlignment(java.awt.FlowLayout.LEFT);
			jPanel69.add(jLabel33, null);
			jPanel69.add(getTxtUrlVerif(), null);
		}
		return jPanel69;
	}
	/**
	 * This method initializes jPanel70	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel70() {
		if (jPanel70 == null) {

			java.awt.FlowLayout flowLayout63 = new FlowLayout();
			jPanel70 = new JPanel();
			jPanel70.setLayout(flowLayout63);
			jPanel70.setPreferredSize(new java.awt.Dimension(500,20));
			jPanel70.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			jPanel70.setBackground(new java.awt.Color(242,242,238));
			flowLayout63.setVgap(2);
			flowLayout63.setAlignment(java.awt.FlowLayout.LEFT);

			jPanel70.add(getJLabel49(), null);
			jPanel70.add(getRbIgnScanS(), null);
			jPanel70.add(getRbIgnScanN(), null);
			bgIgnScan.add(getRbIgnScanS());
			bgIgnScan.add(getRbIgnScanN());
			jPanel70.add(getJLabel50(), null);
			jPanel70.add(getRbIgnPrnS(), null);
			jPanel70.add(getRbIgnPrnN(), null);
			bgIgnPrinter.add(getRbIgnPrnS());
			bgIgnPrinter.add(getRbIgnPrnN());
			jPanel70.add(getJLabel51(), null);
			jPanel70.add(getRbIgnVisorS(), null);
			jPanel70.add(getRbIgnVisorN(), null);
			bgIgnVisor.add(getRbIgnVisorS());
			bgIgnVisor.add(getRbIgnVisorN());
		}
		return jPanel70;
	}
	/**
	 * This method initializes txtPtoSocket	
	 * 	
	 * @return javax.swing.JTextField	
	 */    
	private JTextField getTxtPtoSocket() {
		if (txtPtoSocket == null) {
			txtPtoSocket = new JTextField();
			txtPtoSocket.setPreferredSize(new java.awt.Dimension(50,17));
			txtPtoSocket.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			txtPtoSocket.setName("txtPtoSocket");
		}
		return txtPtoSocket;
	}
	/**
	 * This method initializes txtPtoServ	
	 * 	
	 * @return javax.swing.JTextField	
	 */    
	private JTextField getTxtPtoServ() {
		if (txtPtoServ == null) {
			txtPtoServ = new JTextField();
			txtPtoServ.setPreferredSize(new java.awt.Dimension(50,17));
			txtPtoServ.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			txtPtoServ.setName("txtPtoServ");
		}
		return txtPtoServ;
	}
	/**
	 * This method initializes txtPtoServCent	
	 * 	
	 * @return javax.swing.JTextField	
	 */    
	private JTextField getTxtPtoServCent() {
		if (txtPtoServCent == null) {
			txtPtoServCent = new JTextField();
			txtPtoServCent.setPreferredSize(new java.awt.Dimension(50,17));
			txtPtoServCent.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			txtPtoServCent.setName("txtPtoServCent");
		}
		return txtPtoServCent;
	}
	/**
	 * This method initializes txtPtoSync	
	 * 	
	 * @return javax.swing.JTextField	
	 */    
	private JTextField getTxtPtoSync() {
		if (txtPtoSync == null) {
			txtPtoSync = new JTextField();
			txtPtoSync.setPreferredSize(new java.awt.Dimension(50,17));
			txtPtoSync.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			txtPtoSync.setName("txtPtoServCent");
		}
		return txtPtoSync;
	}
	/**
	 * This method initializes cmbSaltoVisor
	 * 
	 * @return javax.swing.JComboBox
	 */
	private javax.swing.JComboBox getCmbSaltoVisor() {
		if(cmbSaltoVisor == null) {
			cmbSaltoVisor = new javax.swing.JComboBox();
			cmbSaltoVisor.setBackground(new java.awt.Color(242,242,238));
			cmbSaltoVisor.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			cmbSaltoVisor.setPreferredSize(new java.awt.Dimension(100,17));
			cmbSaltoVisor.addItem("Automático ");
			cmbSaltoVisor.addItem("ENTER ");
			cmbSaltoVisor.addItemListener(new java.awt.event.ItemListener() { 
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					cambioSys();
				}
			});
			
		}
		return cmbSaltoVisor;
	}
	/**
	 * This method initializes txtLongitudNombre	
	 * 	
	 * @return javax.swing.JTextField	
	 */    
	private JTextField getTxtLongitudNombre() {
		if (txtLongitudNombre == null) {
			txtLongitudNombre = new JTextField();
			txtLongitudNombre.setPreferredSize(new java.awt.Dimension(85,17));
			txtLongitudNombre.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			txtLongitudNombre.setName("txtLongitudNombre");
		}
		return txtLongitudNombre;
	}
	/**
	 * This method initializes txtLongitudApellido	
	 * 	
	 * @return javax.swing.JTextField	
	 */    
	private JTextField getTxtLongitudApellido() {
		if (txtLongitudApellido == null) {
			txtLongitudApellido = new JTextField();
			txtLongitudApellido.setPreferredSize(new java.awt.Dimension(85,17));
			txtLongitudApellido.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			txtLongitudApellido.setName("txtLongitudVisor");
		}
		return txtLongitudApellido;
	}
	/**
	 * This method initializes txtLongitudVisor	
	 * 	
	 * @return javax.swing.JTextField	
	 */    
	private JTextField getTxtLongitudVisor() {
		if (txtLongitudVisor == null) {
			txtLongitudVisor = new JTextField();
			txtLongitudVisor.setPreferredSize(new java.awt.Dimension(85,17));
			txtLongitudVisor.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			txtLongitudVisor.setName("txtLongitudVisor");
		}
		return txtLongitudVisor;
	}
	/**
	 * This method initializes txtPathTmp	
	 * 	
	 * @return javax.swing.JTextField	
	 */    
	private JTextField getTxtPathTmp() {
		if (txtPathTmp == null) {
			txtPathTmp = new JTextField();
			txtPathTmp.setPreferredSize(new java.awt.Dimension(90,17));
			txtPathTmp.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
		}
		return txtPathTmp;
	}
	/**
	 * This method initializes txtUrlPrinc	
	 * 	
	 * @return javax.swing.JTextField	
	 */    
	private JTextField getTxtUrlPrinc() {
		if (txtUrlPrinc == null) {
			txtUrlPrinc = new JTextField();
			txtUrlPrinc.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			txtUrlPrinc.setPreferredSize(new java.awt.Dimension(270,17));
		}
		return txtUrlPrinc;
	}
	/**
	 * This method initializes jTextField18	
	 * 	
	 * @return javax.swing.JTextField	
	 */    
	private JTextField getTxtUrlVerif() {
		if (txtUrlVerif == null) {
			txtUrlVerif = new JTextField();
			txtUrlVerif.setPreferredSize(new java.awt.Dimension(270,17));
			txtUrlVerif.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
		}
		return txtUrlVerif;
	}
	/**
	 * This method initializes jPanel71	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel71() {
		if (jPanel71 == null) {
			jLabel34 = new JLabel();
			java.awt.FlowLayout flowLayout53 = new FlowLayout();
			jPanel71 = new JPanel();
			jPanel71.setLayout(flowLayout53);
			jPanel71.setBackground(new java.awt.Color(242,242,238));
			jPanel71.setPreferredSize(new java.awt.Dimension(500,20));
			jPanel71.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			flowLayout53.setAlignment(java.awt.FlowLayout.LEFT);
			flowLayout53.setVgap(2);
			jLabel34.setText("Color BECO:");
			jLabel34.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			jLabel34.setPreferredSize(new java.awt.Dimension(90,14));
			jPanel71.add(jLabel34, null);
			jPanel71.add(getTxtColorBeco(), null);
			jLabel35 = new JLabel();
			jLabel35.setText("Color EPA:");
			jLabel35.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			jLabel35.setPreferredSize(new java.awt.Dimension(60,14));
			jPanel71.add(jLabel35, null);
			jPanel71.add(getTxtColorEPA(), null);
			jLabel36 = new JLabel();
			jLabel36.setText("Color Splash:");
			jLabel36.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			jLabel36.setPreferredSize(new java.awt.Dimension(80,14));
			jPanel71.add(jLabel36, null);
			jPanel71.add(getCmbColorSplash(), null);						
		}
		return jPanel71;
	}
	/**
	 * This method initializes jPanel72	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel72() {
		if (jPanel72 == null) {
			java.awt.FlowLayout flowLayout43 = new FlowLayout();
			jPanel72 = new JPanel();
			jPanel72.setLayout(flowLayout43);
			jPanel72.setBackground(new java.awt.Color(242,242,238));
			jPanel72.setPreferredSize(new java.awt.Dimension(500,20));
			jPanel72.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			flowLayout43.setAlignment(java.awt.FlowLayout.LEFT);
			flowLayout43.setVgap(2);
			jLabel38 = new JLabel();
			jLabel38.setText("Logo:");
			jLabel38.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			jLabel38.setPreferredSize(new java.awt.Dimension(50,14));
			jPanel72.add(getJLabel53(), null);
			jPanel72.add(getTxtSyncFreq(), null);
			jPanel72.add(jLabel38, null);
			jPanel72.add(getTxtLogo(), null);
		}
		return jPanel72;
	}
	/**
	 * This method initializes jPanel74	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel74() {
		if (jPanel74 == null) {
			jLabel37 = new JLabel();
			java.awt.FlowLayout flowLayout24 = new FlowLayout();
			jPanel74 = new JPanel();
			jPanel74.setLayout(flowLayout24);
			jPanel74.setBackground(new java.awt.Color(242,242,238));
			jPanel74.setPreferredSize(new java.awt.Dimension(500,20));
			jPanel74.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			flowLayout24.setAlignment(java.awt.FlowLayout.LEFT);
			flowLayout24.setVgap(2);
			jLabel37.setText("Imagen Splash:");
			jLabel37.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			jLabel37.setPreferredSize(new java.awt.Dimension(90,14));
			jLabel30 = new JLabel();
			jLabel30.setText("Ruta Temporal:");
			jLabel30.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			jLabel30.setPreferredSize(new java.awt.Dimension(80,14));
			jPanel74.add(jLabel37, null);
			jPanel74.add(getTxtImgSplash(), null);
			jPanel74.add(jLabel30, null);
			jPanel74.add(getTxtPathTmp(), null);
		}
		return jPanel74;
	}
	/**
	 * This method initializes jPanel75	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel75() {
		if (jPanel75 == null) {
			//jLabel38 = new JLabel();
			java.awt.FlowLayout flowLayout82 = new FlowLayout();
			jPanel75 = new JPanel();
			jPanel75.setLayout(flowLayout82);
			jPanel75.setBackground(new java.awt.Color(242,242,238));
			jPanel75.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			jPanel75.setPreferredSize(new java.awt.Dimension(500,20));
			flowLayout82.setAlignment(java.awt.FlowLayout.LEFT);
			flowLayout82.setVgap(2);
			jPanel75.add(getJLabel79(), null);
			jPanel75.add(getRbEscanerRapidoS(), null);
			jPanel75.add(getRbEscanerRapidoN(), null);
			jPanel75.add(getJLabel80(), null);
			jPanel75.add(getRbActVerificadorS(), null);
			jPanel75.add(getRbActVerificadorN(), null);

			bgEscanerRapido.add(getRbEscanerRapidoS());		
			bgEscanerRapido.add(getRbEscanerRapidoN());		
			bgActVerificador.add(getRbActVerificadorS());
			bgActVerificador.add(getRbActVerificadorN());
		}
		return jPanel75;
	}
	/**
	 * This method initializes jPanel103	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel103() {
		if (jPanel103 == null) {
			//jLabel38 = new JLabel();
			java.awt.FlowLayout flowLayout82 = new FlowLayout();
			jPanel103 = new JPanel();
			jPanel103.setLayout(flowLayout82);
			jPanel103.setBackground(new java.awt.Color(242,242,238));
			jPanel103.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			jPanel103.setPreferredSize(new java.awt.Dimension(500,20));
			flowLayout82.setAlignment(java.awt.FlowLayout.LEFT);
			flowLayout82.setVgap(2);
			//jLabel38.setText("Logo:");
			jPanel103.add(getJLabel81(), null);
			jPanel103.add(getTxtLongitudNombre(), null);
			jPanel103.add(getJLabel82(), null);
			jPanel103.add(getTxtLongitudApellido(), null);
			//jLabel38.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			//jLabel38.setPreferredSize(new java.awt.Dimension(90,14));
			//jPanel75.add(jLabel38, null);
			//jPanel75.add(getTxtLogo(), null);
		}
		return jPanel103;
	}
	/**
	 * This method initializes jPanel76	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel76() {
		if (jPanel76 == null) {
			jLabel39 = new JLabel();
			java.awt.FlowLayout flowLayout73 = new FlowLayout();
			jPanel76 = new JPanel();
			jPanel76.setLayout(flowLayout73);
			jPanel76.setBackground(new java.awt.Color(242,242,238));
			jPanel76.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			jPanel76.setPreferredSize(new java.awt.Dimension(500,20));
			flowLayout73.setAlignment(java.awt.FlowLayout.LEFT);
			flowLayout73.setVgap(2);
			jLabel39.setText("Mensaje Splash:");
			jLabel39.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			jLabel39.setPreferredSize(new java.awt.Dimension(90,14));
			jPanel76.add(jLabel39, null);
			jPanel76.add(getTxtMsgSplash(), null);
			jPanel76.add(getJLabel54(), null);
			jPanel76.add(getRbMsgBlancoS(), null);
			jPanel76.add(getRbMsgBlancoN(), null);
		}
		return jPanel76;
	}
	/**
	 * This method initializes txtColorBeco	
	 * 	
	 * @return javax.swing.JTextField	
	 */    
	private JTextField getTxtColorBeco() {
		if (txtColorBeco == null) {
			txtColorBeco = new JTextField();
			txtColorBeco.setPreferredSize(new java.awt.Dimension(70,17));
			txtColorBeco.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
		}
		return txtColorBeco;
	}
	/**
	 * This method initializes txtColorEPA	
	 * 	
	 * @return javax.swing.JTextField	
	 */    
	private JTextField getTxtColorEPA() {
		if (txtColorEPA == null) {
			txtColorEPA = new JTextField();
			txtColorEPA.setPreferredSize(new java.awt.Dimension(70,17));
			txtColorEPA.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
		}
		return txtColorEPA;
	}
	/**
	 * This method initializes txtImgSplash	
	 * 	
	 * @return javax.swing.JTextField	
	 */    
	private JTextField getTxtImgSplash() {
		if (txtImgSplash == null) {
			txtImgSplash = new JTextField();
			txtImgSplash.setPreferredSize(new java.awt.Dimension(210,17));
			txtImgSplash.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
		}
		return txtImgSplash;
	}
	/**
	 * This method initializes cmbColorSplash	
	 * 	
	 * @return javax.swing.JComboBox	
	 */    
	private JComboBox getCmbColorSplash() {
		if (cmbColorSplash == null) {
			cmbColorSplash = new JComboBox();
			cmbColorSplash.setPreferredSize(new java.awt.Dimension(90,17));
			cmbColorSplash.setBackground(new java.awt.Color(242,242,238));
			cmbColorSplash.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 10));
			cmbColorSplash.addItem("BECO");
			cmbColorSplash.addItem("EPA");			
			cmbColorSplash.addItemListener(new java.awt.event.ItemListener() { 
				public void itemStateChanged(java.awt.event.ItemEvent e) {    
					cambioSys();
				}
			});

		}
		return cmbColorSplash;
	}
	/**
	 * This method initializes txtMsgSplash	
	 * 	
	 * @return javax.swing.JTextField	
	 */    
	private JTextField getTxtMsgSplash() {
		if (txtMsgSplash == null) {
			txtMsgSplash = new JTextField();
			txtMsgSplash.setPreferredSize(new java.awt.Dimension(205,17));
			txtMsgSplash.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
		}
		return txtMsgSplash;
	}
	/**
	 * This method initializes rbRebajS	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */    
	private JRadioButton getRbVerifLinS() {
		if (rbVerifLinS == null) {
			rbVerifLinS = new JRadioButton();
			rbVerifLinS.setPreferredSize(new java.awt.Dimension(40,17));
			rbVerifLinS.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 10));
			rbVerifLinS.setBackground(new java.awt.Color(242,242,238));
			rbVerifLinS.setText("Si");
		}
		return rbVerifLinS;
	}
	/**
	 * This method initializes rbVerifLinN	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */    
	private JRadioButton getRbVerifLinN() {
		if (rbVerifLinN == null) {
			rbVerifLinN = new JRadioButton();
			rbVerifLinN.setPreferredSize(new java.awt.Dimension(40,17));
			rbVerifLinN.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 10));
			rbVerifLinN.setBackground(new java.awt.Color(242,242,238));
			rbVerifLinN.setText("No");
		}
		return rbVerifLinN;
	}
	/**
	 * This method initializes rbFiscalPrnS	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */    
	private JRadioButton getRbFiscalPrnS() {
		if (rbFiscalPrnS == null) {
			rbFiscalPrnS = new JRadioButton();
			rbFiscalPrnS.setBackground(new java.awt.Color(242,242,238));
			rbFiscalPrnS.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 10));
			rbFiscalPrnS.setPreferredSize(new java.awt.Dimension(40,17));
			rbFiscalPrnS.setText("Si");
		}
		return rbFiscalPrnS;
	}
	/**
	 * This method initializes rbFiscalPrnN	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */    
	private JRadioButton getRbFiscalPrnN() {
		if (rbFiscalPrnN == null) {
			rbFiscalPrnN = new JRadioButton();
			rbFiscalPrnN.setBackground(new java.awt.Color(242,242,238));
			rbFiscalPrnN.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 10));
			rbFiscalPrnN.setPreferredSize(new java.awt.Dimension(40,17));
			rbFiscalPrnN.setText("No");
		}
		return rbFiscalPrnN;
	}
	/**
	 * This method initializes rbGarantFactS	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */    
	private JRadioButton getRbGarantFactS() {
		if (rbGarantFactS == null) {
			rbGarantFactS = new JRadioButton();
			rbGarantFactS.setBackground(new java.awt.Color(242,242,238));
			rbGarantFactS.setPreferredSize(new java.awt.Dimension(40,17));
			rbGarantFactS.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 10));
			rbGarantFactS.setText("Si");
		}
		return rbGarantFactS;
	}
	/**
	 * This method initializes rbGarantFactN	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */    
	private JRadioButton getRbGarantFactN() {
		if (rbGarantFactN == null) {
			rbGarantFactN = new JRadioButton();
			rbGarantFactN.setBackground(new java.awt.Color(242,242,238));
			rbGarantFactN.setPreferredSize(new java.awt.Dimension(40,17));
			rbGarantFactN.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 10));
			rbGarantFactN.setText("No");
		}
		return rbGarantFactN;
	}
	/**
	 * This method initializes txtLogo	
	 * 	
	 * @return javax.swing.JTextField	
	 */    
	private JTextField getTxtLogo() {
		if (txtLogo == null) {
			txtLogo = new JTextField();
			txtLogo.setPreferredSize(new java.awt.Dimension(160,17));
			txtLogo.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
		}
		return txtLogo;
	}
	/**
	 * This method initializes jPanel77	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel77() {
		if (jPanel77 == null) {
			java.awt.FlowLayout flowLayout114 = new FlowLayout();
			jPanel77 = new JPanel();
			jPanel77.setLayout(flowLayout114);
			jPanel77.setPreferredSize(new java.awt.Dimension(520,26));
			jPanel77.setBackground(new java.awt.Color(226,226,222));
			flowLayout114.setAlignment(java.awt.FlowLayout.RIGHT);
			flowLayout114.setVgap(0);
			flowLayout114.setHgap(0);
			jPanel77.add(getBtnSaveSys(), null);
		}
		return jPanel77;
	}
	/**
	 * This method initializes jButton6	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getBtnSaveSys() {
		if (btnSaveSys == null) {
			btnSaveSys = new JButton();
			btnSaveSys.setText("Guardar");
			btnSaveSys.setIcon(new ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix16x16/data_disk.png")));
			btnSaveSys.setBackground(new java.awt.Color(242,242,238));
			btnSaveSys.setEnabled(false);
			btnSaveSys.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(java.awt.event.ActionEvent e) {    
					guardarValoresSys();
				}
			});
		}
		return btnSaveSys;
	}
	/**
	 * This method initializes rbRebajN	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */    
	private JRadioButton getRbReqCliS() {
		if (rbReqCliS == null) {
			rbReqCliS = new JRadioButton();
			rbReqCliS.setBackground(new java.awt.Color(242,242,238));
			rbReqCliS.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 10));
			rbReqCliS.setPreferredSize(new java.awt.Dimension(40,17));
			rbReqCliS.setText("Si");
		}
		return rbReqCliS;
	}
	/**
	 * This method initializes rbReqCliN	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */    
	private JRadioButton getRbReqCliN() {
		if (rbReqCliN == null) {
			rbReqCliN = new JRadioButton();
			rbReqCliN.setBackground(new java.awt.Color(242,242,238));
			rbReqCliN.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 10));
			rbReqCliN.setPreferredSize(new java.awt.Dimension(40,17));
			rbReqCliN.setText("No");
		}
		return rbReqCliN;
	}
	/**
	 * This method initializes jPanel78	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel78() {
		if (jPanel78 == null) {
			jLabel41 = new JLabel();
			java.awt.FlowLayout flowLayout25 = new FlowLayout();
			jPanel78 = new JPanel();
			jPanel78.setLayout(flowLayout25);
			jPanel78.setPreferredSize(new java.awt.Dimension(500,20));
			jPanel78.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			jPanel78.setBackground(new java.awt.Color(242,242,238));
			flowLayout25.setAlignment(java.awt.FlowLayout.LEFT);
			flowLayout25.setVgap(2);
			jLabel41.setText("Empaques Acumulativos:");
			jLabel41.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			jLabel41.setBackground(new java.awt.Color(242,242,238));
			jLabel41.setPreferredSize(new java.awt.Dimension(180,14));
			jPanel78.add(jLabel41, null);
			jPanel78.add(getRbEmpAcumS(), null);
			jPanel78.add(getRbEmpAcumN(), null);
			bgEmpAcum.add(getRbEmpAcumS());
			bgEmpAcum.add(getRbEmpAcumN());
		}
		return jPanel78;
	}
	/**
	 * This method initializes jPanel79	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel79() {
		if (jPanel79 == null) {
			jLabel42 = new JLabel();
			java.awt.FlowLayout flowLayout34 = new FlowLayout();
			jPanel79 = new JPanel();
			jPanel79.setLayout(flowLayout34);
			jPanel79.setPreferredSize(new java.awt.Dimension(500,20));
			jPanel79.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			jPanel79.setBackground(new java.awt.Color(242,242,238));
			flowLayout34.setAlignment(java.awt.FlowLayout.LEFT);
			flowLayout34.setVgap(2);
			jLabel42.setText("Permitir rebajas a empleados:");
			jLabel42.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			jLabel42.setBackground(new java.awt.Color(242,242,238));
			jLabel42.setPreferredSize(new java.awt.Dimension(180,14));
			jPanel79.add(jLabel42, null);
			jPanel79.add(getRbRebajEmpS(), null);
			jPanel79.add(getRbRebajEmpN(), null);
		}
		return jPanel79;
	}
	/**
	 * This method initializes rbRebajS	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */    
	private JRadioButton getRbEmpAcumS() {
		if (rbEmpAcumS == null) {
			rbEmpAcumS = new JRadioButton();
			rbEmpAcumS.setBackground(new java.awt.Color(242,242,238));
			rbEmpAcumS.setPreferredSize(new java.awt.Dimension(40,17));
			rbEmpAcumS.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 10));
			rbEmpAcumS.setText("Si");
		}
		return rbEmpAcumS;
	}
	/**
	 * This method initializes rbActVerificadorS	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */    
	private JRadioButton getRbActVerificadorS() {
		if (rbActVerificadorS == null) {
			rbActVerificadorS = new JRadioButton();
			rbActVerificadorS.setBackground(new java.awt.Color(242,242,238));
			rbActVerificadorS.setPreferredSize(new java.awt.Dimension(40,17));
			rbActVerificadorS.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 10));
			rbActVerificadorS.setText("Si");
		}
		return rbActVerificadorS;
	}
	/**
	 * This method initializes rbActVerificadorN	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */    
	private JRadioButton getRbActVerificadorN() {
		if (rbActVerificadorN == null) {
			rbActVerificadorN = new JRadioButton();
			rbActVerificadorN.setBackground(new java.awt.Color(242,242,238));
			rbActVerificadorN.setPreferredSize(new java.awt.Dimension(40,17));
			rbActVerificadorN.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 10));
			rbActVerificadorN.setText("No");
		}
		return rbActVerificadorN;
	}
	/**
	 * This method initializes rbEscanerRapidoS
	 * 	
	 * @return javax.swing.JRadioButton	
	 */    
	private JRadioButton getRbEscanerRapidoS() {
		if (rbEscanerRapidoS == null) {
			rbEscanerRapidoS = new JRadioButton();
			rbEscanerRapidoS.setBackground(new java.awt.Color(242,242,238));
			rbEscanerRapidoS.setPreferredSize(new java.awt.Dimension(40,17));
			rbEscanerRapidoS.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 10));
			rbEscanerRapidoS.setText("Si");
		}
		return rbEscanerRapidoS;
	}
	/**
	 * This method initializes rbEscanerRapidoN	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */    
	private JRadioButton getRbEscanerRapidoN() {
		if (rbEscanerRapidoN == null) {
			rbEscanerRapidoN = new JRadioButton();
			rbEscanerRapidoN.setBackground(new java.awt.Color(242,242,238));
			rbEscanerRapidoN.setPreferredSize(new java.awt.Dimension(40,17));
			rbEscanerRapidoN.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 10));
			rbEscanerRapidoN.setText("No");
		}
		return rbEscanerRapidoN;
	}
	/**
	 * This method initializes rbMantenerFocoSi
	 * 	
	 * @return javax.swing.JRadioButton	
	 */    
	private JRadioButton getRbMantenerFocoSi() {
		if (rbMantenerFocoSi == null) {
			rbMantenerFocoSi = new JRadioButton();
			rbMantenerFocoSi.setBackground(new java.awt.Color(242,242,238));
			rbMantenerFocoSi.setPreferredSize(new java.awt.Dimension(40,17));
			rbMantenerFocoSi.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 10));
			rbMantenerFocoSi.setText("Si");
		}
		return rbMantenerFocoSi;
	}
	/**
	 * This method initializes rbMantenerFocoNo	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */    
	private JRadioButton getRbMantenerFocoNo() {
		if (rbMantenerFocoNo == null) {
			rbMantenerFocoNo = new JRadioButton();
			rbMantenerFocoNo.setBackground(new java.awt.Color(242,242,238));
			rbMantenerFocoNo.setPreferredSize(new java.awt.Dimension(40,17));
			rbMantenerFocoNo.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 10));
			rbMantenerFocoNo.setText("No");
		}
		return rbMantenerFocoNo;
	}
	/**
	 * This method initializes rbContOrdinarioS	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */    
	private JRadioButton getRbContOrdinarioS() {
		if (rbContOrdinarioS == null) {
			rbContOrdinarioS = new JRadioButton();
			rbContOrdinarioS.setBackground(new java.awt.Color(242,242,238));
			rbContOrdinarioS.setPreferredSize(new java.awt.Dimension(40,17));
			rbContOrdinarioS.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 10));
			rbContOrdinarioS.setText("Si");
		}
		return rbContOrdinarioS;
	}
	/**
	 * This method initializes rbContOrdinarioN	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */    
	private JRadioButton getRbContOrdinarioN() {
		if (rbContOrdinarioN == null) {
			rbContOrdinarioN = new JRadioButton();
			rbContOrdinarioN.setBackground(new java.awt.Color(242,242,238));
			rbContOrdinarioN.setPreferredSize(new java.awt.Dimension(40,17));
			rbContOrdinarioN.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 10));
			rbContOrdinarioN.setText("No");
		}
		return rbContOrdinarioN;
	}
	/**
	 * This method initializes rbRebajN	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */    
	private JRadioButton getRbEmpAcumN() {
		if (rbEmpAcumN == null) {
			rbEmpAcumN = new JRadioButton();
			rbEmpAcumN.setBackground(new java.awt.Color(242,242,238));
			rbEmpAcumN.setPreferredSize(new java.awt.Dimension(40,17));
			rbEmpAcumN.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 10));
			rbEmpAcumN.setText("No");
		}
		return rbEmpAcumN;
	}
	/**
	 * This method initializes rbRebajEmpS	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */    
	private JRadioButton getRbRebajEmpS() {
		if (rbRebajEmpS == null) {
			rbRebajEmpS = new JRadioButton();
			rbRebajEmpS.setBackground(new java.awt.Color(242,242,238));
			rbRebajEmpS.setPreferredSize(new java.awt.Dimension(40,17));
			rbRebajEmpS.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 10));
			rbRebajEmpS.setText("Si");
		}
		return rbRebajEmpS;
	}
	/**
	 * This method initializes rbRebajEmpN	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */    
	private JRadioButton getRbRebajEmpN() {
		if (rbRebajEmpN == null) {
			rbRebajEmpN = new JRadioButton();
			rbRebajEmpN.setBackground(new java.awt.Color(242,242,238));
			rbRebajEmpN.setPreferredSize(new java.awt.Dimension(40,17));
			rbRebajEmpN.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 10));
			rbRebajEmpN.setText("No");
		}
		return rbRebajEmpN;
	}
	/**
	 * This method initializes rbRebajS	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */    
	private JRadioButton getRbRebajS() {
		if (rbRebajS == null) {
			rbRebajS = new JRadioButton();
			rbRebajS.setBackground(new java.awt.Color(242,242,238));
			rbRebajS.setPreferredSize(new java.awt.Dimension(40,17));
			rbRebajS.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 10));
			rbRebajS.setText("Si");
		}
		return rbRebajS;
	}
	/**
	 * This method initializes rbRebajN	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */    
	private JRadioButton getRbRebajN() {
		if (rbRebajN == null) {
			rbRebajN = new JRadioButton();
			rbRebajN.setBackground(new java.awt.Color(242,242,238));
			rbRebajN.setPreferredSize(new java.awt.Dimension(40,17));
			rbRebajN.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 10));
			rbRebajN.setText("No");
		}
		return rbRebajN;
	}
    public static void main(String[] args) {
		CRControlPanel standaloneCRControlPanel = new CRControlPanel();
		standaloneCRControlPanel.setVisible(true);
	}

	
	
	/**
	 * This is the default constructor
	 */
	public CRControlPanel() {
		super();
		initialize();
		eppCR = new EPAPreferenceProxy("proyectocr");
		epaPreferencesProxy = new EPAPreferenceProxy("dispositivos");
		
		//Detectamos si existen las preferencias de Caja. Si no existen las creamos con valores por defecto
		cargarPreferenciasPorDefecto();

		/* Agregamos los métodos necesarios para manipular los eventos  */
		cmbNomDisp.addActionListener(this);
		jButton1.addMouseListener(this);
		btnSavePort.addMouseListener(this);
	}
	
	private void cargarPreferenciasPorDefecto() {
		try {
			if (!eppCR.isNodeConfigured("db")) { 
				eppCR.addNewNodeToPreferencesTop("db");
				eppCR.setConfigStringForParameter("db", "dbEsquema", "CR");
				eppCR.setConfigStringForParameter("db", "dbClaseLocal", "com.mysql.jdbc.Driver");
				eppCR.setConfigStringForParameter("db", "dbClaseServidor", "com.mysql.jdbc.Driver");
				eppCR.setConfigStringForParameter("db", "dbClaseServidorCentral", "com.ibm.as400.access.AS400JDBCDriver");
				eppCR.setConfigStringForParameter("db", "dbUrlLocal", "jdbc:mysql://127.0.0.1/"+eppCR.getConfigStringForParameter("db", "dbEsquema"));
				eppCR.setConfigStringForParameter("db", "dbUrlServidor", "jdbc:mysql://192.168.1.244/"+eppCR.getConfigStringForParameter("db", "dbEsquema"));
				eppCR.setConfigStringForParameter("db", "dbUrlServidorCentral", "jdbc:as400://192.168.1.10/"+eppCR.getConfigStringForParameter("db", "dbEsquema"));
				eppCR.setConfigStringForParameter("db", "dbUsuario", "rootcr");
				eppCR.setConfigStringForParameter("db", "dbClave", "root2003");
				eppCR.setConfigStringForParameter("db", "garantizarFacturacion", "N");
			}
		} catch(Exception e) {}
		try {
			if (!eppCR.isNodeConfigured("sistema")) {
				eppCR.addNewNodeToPreferencesTop("sistema");
				eppCR.setConfigStringForParameter("sistema", "pathtemporal", "temp/");
				eppCR.setConfigStringForParameter("sistema", "urlPrincipal", "/com/becoblohm/cr/verificador/verificadorCR.html");
				eppCR.setConfigStringForParameter("sistema", "urlVerificador", "");
				eppCR.setConfigStringForParameter("sistema", "verificarLinea", "S");
				eppCR.setConfigStringForParameter("sistema", "impresoraFiscal", "S");
				eppCR.setConfigStringForParameter("sistema", "mensajeSplash", "Sistema CR - Version ");
				eppCR.setConfigStringForParameter("sistema", "mensajeColorBlanco", "S");
				eppCR.setConfigStringForParameter("sistema", "logo", "logoBECO.png"); //Logo de EPA
				eppCR.setConfigStringForParameter("sistema", "colorFondoSplash", "colorBECO"); //ColorEPA
				eppCR.setConfigStringForParameter("sistema", "colorBECO", "59,27,100"); //Colores BECO
				eppCR.setConfigStringForParameter("sistema", "colorEPA", "0,51,153"); //Colores EPA
				eppCR.setConfigStringForParameter("sistema", "imagenSplash", "splashBECO.png"); //SplashEPA
				eppCR.setConfigStringForParameter("sistema", "longitudlineavisor", "20");
				eppCR.setConfigStringForParameter("sistema", "saltolineavisor", "");
				eppCR.setConfigStringForParameter("sistema", "puertoServCentral", "8471");
				eppCR.setConfigStringForParameter("sistema", "puertoServ", "3306");
				eppCR.setConfigStringForParameter("sistema", "PuertoSocket", "7000");
				eppCR.setConfigStringForParameter("sistema", "cambiofecha", "N");
				eppCR.setConfigStringForParameter("sistema", "apagarsistema", "S");
				eppCR.setConfigStringForParameter("sistema", "reiniciarsistema", "N");
				eppCR.setConfigStringForParameter("sistema", "estiloFactura","VE");
				eppCR.setConfigStringForParameter("sistema", "localizacionLenguaje", "es");
				eppCR.setConfigStringForParameter("sistema", "localizacionPais","VE");
				eppCR.setConfigStringForParameter("sistema", "longitudNombreCliente", "30");
				eppCR.setConfigStringForParameter("sistema", "longitudApellidoCliente", "30");
				eppCR.setConfigStringForParameter("sistema", "escanerRapido", "N");
				eppCR.setConfigStringForParameter("sistema", "funcionalidad", "0"); 
				eppCR.setConfigStringForParameter("sistema", "puedeFacturar", "S");
				eppCR.setConfigStringForParameter("sistema", "ignorarEscaner", "S");
				eppCR.setConfigStringForParameter("sistema", "ignorarImpresora", "S");
				eppCR.setConfigStringForParameter("sistema", "ignorarVisor", "S");
				eppCR.setConfigStringForParameter("sistema", "frecuenciaSincronizador", "5");
				eppCR.setConfigStringForParameter("sistema", "servicioSyncCommander", "S");
				eppCR.setConfigStringForParameter("sistema", "actCajaTemporizada", "S");
				eppCR.setConfigStringForParameter("sistema", "puertoSyncCommander", "2005");
				eppCR.setConfigStringForParameter("sistema", "sincronizarAfiliados", "S");
				eppCR.setConfigStringForParameter("sistema", "activarVerificador", "S");
				eppCR.setConfigStringForParameter("sistema", "manejoGaveta", "impresora");
			}
		} catch(Exception e) {}
		
		try {	
			if (!eppCR.isNodeConfigured("facturacion")) {
				eppCR.addNewNodeToPreferencesTop("facturacion");
				eppCR.setConfigStringForParameter("facturacion", "longitudCodigoInterno", "11");
				eppCR.setConfigStringForParameter("facturacion", "permitirRebajasAempleados", "N");
				eppCR.setConfigStringForParameter("facturacion", "empaquesAcumulativos", "S");
				eppCR.setConfigStringForParameter("facturacion", "porcentajeMensajeEmpaque", "50");
				eppCR.setConfigStringForParameter("facturacion", "imprimirFrenteCheque", "S");
				eppCR.setConfigStringForParameter("facturacion", "imprimirDorsoCheque", "S");
				eppCR.setConfigStringForParameter("facturacion", "nombreBancoCheque", "");
				eppCR.setConfigStringForParameter("facturacion", "numeroCuentaCheque", "");
				eppCR.setConfigStringForParameter("facturacion", "tipoCuentaCheque", "Corriente");
				eppCR.setConfigStringForParameter("facturacion", "requiereCliente", "N");
				eppCR.setConfigStringForParameter("facturacion", "autorizarCierreCajero", "N");
				eppCR.setConfigStringForParameter("facturacion", "porcentajeRetencionIVA", "75");
				eppCR.setConfigStringForParameter("facturacion", "montoMinimoDevolucion", "50000");
				eppCR.setConfigStringForParameter("facturacion", "simboloMonedaLocal", ""+'\302'+'\242');
				eppCR.setConfigStringForParameter("facturacion", "cedulaObligatoria", "N");
				eppCR.setConfigStringForParameter("facturacion", "numeroConfirmacionObligatorio", "S");
				eppCR.setConfigStringForParameter("facturacion", "permitirContribuyentesOrdinarios", "N");
				eppCR.setConfigStringForParameter("facturacion", "colorCombo", "69,170,252"); //Colores BECO
				eppCR.setConfigStringForParameter("facturacion", "entregaDespacho","N");
				eppCR.setConfigStringForParameter("facturacion", "entregaDomicilio","S");
				eppCR.setConfigStringForParameter("facturacion", "entregaClienteRetira","N");
				eppCR.setConfigStringForParameter("facturacion", "validarClaveAlSalir","S");
			}
		} catch(Exception e) {}
			
		try {
			if (!eppCR.isNodeConfigured("apartado")) {
				eppCR.addNewNodeToPreferencesTop("apartado");
				eppCR.setConfigStringForParameter("apartado", "NumeroTiposDeAbonos", "1");
				eppCR.setConfigStringForParameter("apartado", "Abono1", "30");
				eppCR.setConfigStringForParameter("apartado", "TipoVigencia", "Dia");
				eppCR.setConfigStringForParameter("apartado", "TiempoVigencia", "30");
				eppCR.setConfigStringForParameter("apartado", "PermiteRebajas", "N");
				eppCR.setConfigStringForParameter("apartado", "RecalcularSaldo", "S");
				eppCR.setConfigStringForParameter("apartado", "CargoPorServicio", "10");
				eppCR.setConfigStringForParameter("apartado", "MontoMinimoApartado", "50000");
				eppCR.setConfigStringForParameter("apartado", "PreguntarFacturar", "N");
			}
		} catch(Exception e) {}
		
		try{
			if(!eppCR.isNodeConfigured("listaregalos")){
				eppCR.addNewNodeToPreferencesTop("listaregalos");
				eppCR.setConfigStringForParameter("listaregalos", "MaxDiasAperturaLG", "45");
				eppCR.setConfigStringForParameter("listaregalos", "CostoUT", "33600");
				eppCR.setConfigStringForParameter("listaregalos", "MontoMinimoLG", "120");
				String iplocal = InetAddress.getLocalHost().getHostAddress();
				String ipservidor = (iplocal.substring(0,iplocal.lastIndexOf('.')+1))+"3";
				eppCR.setConfigStringForParameter("listaregalos", "DbPdtUrlServidor", "jdbc:mysql://"+ipservidor+"/mcomm");
				eppCR.setConfigStringForParameter("listaregalos", "DbPdtUsuario", "root");
				eppCR.setConfigStringForParameter("listaregalos", "DbPdtClave", "");
			}
		}catch(Exception e){}
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setTitle("Editor de Configuración - CR ver. 1.0.0");
		this.setBackground(new java.awt.Color(226,226,222));
		this.setSize(587, 595);
		this.setContentPane(getJContentPane());
		this.setResizable(false);
		this.setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
		this.addComponentListener(this);
		
	}
	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if(jContentPane == null) {
			jContentPane = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout111 = new java.awt.FlowLayout();
			layFlowLayout111.setVgap(0);
			jContentPane.setLayout(layFlowLayout111);
			jContentPane.setBackground(new java.awt.Color(226,226,222));
			jContentPane.setPreferredSize(new java.awt.Dimension(570,500));
			jContentPane.add(getJTabbedPane(), null);
			jContentPane.add(getJPanel2(), null);
		}
		return jContentPane;
	}
	/**

	 * This method initializes jTabbedPane	

	 * 	

	 * @return javax.swing.JTabbedPane	

	 */    
	private javax.swing.JTabbedPane getJTabbedPane() {
		if (jTabbedPane == null) {
			jTabbedPane = new javax.swing.JTabbedPane();
			jTabbedPane.setBackground(new java.awt.Color(226,226,222));
			jTabbedPane.setPreferredSize(new java.awt.Dimension(560,535));
			jTabbedPane.setTabPlacement(javax.swing.JTabbedPane.TOP);
			jTabbedPane.addTab("Puertos", null, getJPanel(), null);
			jTabbedPane.addTab("Apartados", null, getJPanel1(), null);
			jTabbedPane.addTab("Listas de Regalos", null, getTabListaRegalos(), null);
			jTabbedPane.addTab("Base de Datos", null, getJPanel29(), null);
			jTabbedPane.addTab("Facturación", null, getJPanel30(), null);
			jTabbedPane.addTab("Sistema", null, getJPanel31(), null);
			jTabbedPane.addChangeListener(new javax.swing.event.ChangeListener() { 
				public void stateChanged(javax.swing.event.ChangeEvent e) {    
					cambioPage(e);
				}
			});
		}
		return jTabbedPane;
	}

	/**

	 * This method initializes jPanel	

	 * 	

	 * @return javax.swing.JPanel	

	 */    
	private javax.swing.JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new javax.swing.JPanel();
			jPanel.setPreferredSize(new java.awt.Dimension(300,500));
			jPanel.setBackground(new java.awt.Color(226,226,222));
			jPanel.add(getJPanel3(), null);
			jPanel.add(getJPanel5(), null);
		}
		return jPanel;
	}

	/**

	 * This method initializes jPanel1	

	 * 	

	 * @return javax.swing.JPanel	

	 */    
	private javax.swing.JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new javax.swing.JPanel();
			jPanel1.setBackground(new java.awt.Color(226,226,222));
			jPanel1.add(getJPanel32(), null);
			jPanel1.add(getJPanel59(), null);
			jPanel1.setPreferredSize(new java.awt.Dimension(1075,500));
			KeyAdapter ka = new java.awt.event.KeyAdapter() { 
				public void keyTyped(java.awt.event.KeyEvent e) {    
					cambioApart();					
				}
			};
			ChangeListener chl = new javax.swing.event.ChangeListener() { 
				public void stateChanged(javax.swing.event.ChangeEvent e) {    
					cambioApart();
				}
			};
			
			txtDiasVig.addKeyListener(ka);
			cmbTipoVigencia.addKeyListener(ka);
			cmbNumAbonos.addKeyListener(ka);
			txtCargoServ.addKeyListener(ka);			
			rbRebajS.addChangeListener(chl);
			rbRebajN.addChangeListener(chl);
			rbRecalcSaldoS.addChangeListener(chl);
			rbRecalcSaldoN.addChangeListener(chl);
			txtMtoMinApartado.addKeyListener(ka);
			rbPreguntarFactSi.addChangeListener(chl);
			rbPreguntarFactNo.addChangeListener(chl);
		}
		return jPanel1;
	}

	/**

	 * This method initializes jPanel2	

	 * 	

	 * @return javax.swing.JPanel	

	 */    
	private javax.swing.JPanel getJPanel2() {
		if (jPanel2 == null) {
			java.awt.FlowLayout layFlowLayout1 = new java.awt.FlowLayout();

			jPanel2 = new javax.swing.JPanel();
			jPanel2.setLayout(layFlowLayout1);
			jPanel2.setPreferredSize(new java.awt.Dimension(550,30));
			jPanel2.setBackground(new java.awt.Color(226,226,222));
			layFlowLayout1.setHgap(0);
			layFlowLayout1.setVgap(0);
			layFlowLayout1.setAlignment(java.awt.FlowLayout.RIGHT);
			jPanel2.add(getJButton1(), null);
		}
		return jPanel2;
	}

	/**

	 * This method initializes jButton1	

	 * 	

	 * @return javax.swing.JButton	

	 */    
	private javax.swing.JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new javax.swing.JButton();
			jButton1.setText("Salir");
			jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix16x16/delete2.png")));
			jButton1.setBackground(new java.awt.Color(242,242,238));
		}
		return jButton1;
	}

	/**

	 * This method initializes jPanel3	

	 * 	

	 * @return javax.swing.JPanel	

	 */    
	private javax.swing.JPanel getJPanel3() {
		if (jPanel3 == null) {
			java.awt.FlowLayout layFlowLayout3 = new java.awt.FlowLayout();

			jPanel3 = new javax.swing.JPanel();
			jPanel3.setLayout(layFlowLayout3);
			jPanel3.setPreferredSize(new java.awt.Dimension(540,380));
			jPanel3.setBackground(new java.awt.Color(242,242,238));
			jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(69,107,127),1));
			layFlowLayout3.setHgap(0);
			layFlowLayout3.setVgap(0);
			jPanel3.add(getJPanel4(), null);
			jPanel3.add(getJPanel6(), null);
		}
		return jPanel3;
	}

	/**

	 * This method initializes jPanel4	

	 * 	

	 * @return javax.swing.JPanel	

	 */    
	private javax.swing.JPanel getJPanel4() {
		if (jPanel4 == null) {
			java.awt.FlowLayout layFlowLayout2 = new java.awt.FlowLayout();

			jPanel4 = new javax.swing.JPanel();
			jPanel4.setLayout(layFlowLayout2);
			layFlowLayout2.setHgap(5);
			layFlowLayout2.setVgap(5);
			layFlowLayout2.setAlignment(java.awt.FlowLayout.LEFT);
			jPanel4.setPreferredSize(new java.awt.Dimension(540,40));
			jPanel4.setBackground(new java.awt.Color(69,107,127));
			jPanel4.add(getJLabel(), null);
		}
		return jPanel4;
	}

	/**

	 * This method initializes jLabel	

	 * 	

	 * @return javax.swing.JLabel	

	 */    
	private javax.swing.JLabel getJLabel() {
		if (jLabel == null) {
			jLabel = new javax.swing.JLabel();
			jLabel.setText("Configuración de Puertos");
			jLabel.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 18));
			jLabel.setForeground(java.awt.Color.white);
			jLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix32x32/exchange.png")));
		}
		return jLabel;
	}

	/**

	 * This method initializes jPanel5	

	 * 	

	 * @return javax.swing.JPanel	

	 */    
	private javax.swing.JPanel getJPanel5() {
		if (jPanel5 == null) {
			java.awt.FlowLayout layFlowLayout4 = new java.awt.FlowLayout();

			jPanel5 = new javax.swing.JPanel();
			jPanel5.setLayout(layFlowLayout4);
			jPanel5.setPreferredSize(new java.awt.Dimension(520,30));
			jPanel5.setBackground(new java.awt.Color(226,226,222));
			layFlowLayout4.setAlignment(java.awt.FlowLayout.RIGHT);
			layFlowLayout4.setHgap(0);
			layFlowLayout4.setVgap(0);
			jPanel5.add(getBtnSavePort(), null);
		}
		return jPanel5;
	}

	/**

	 * This method initializes btnSavePort	

	 * 	

	 * @return javax.swing.JButton	

	 */    
	private javax.swing.JButton getBtnSavePort() {
		if (btnSavePort == null) {
			btnSavePort = new javax.swing.JButton();
			btnSavePort.setBackground(new java.awt.Color(242,242,238));
			btnSavePort.setText("Guardar");
			btnSavePort.setEnabled(false);
			btnSavePort.setIcon(new ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix16x16/data_disk.png")));
			btnSavePort.setPreferredSize(new java.awt.Dimension(100,26));
		}
		return btnSavePort;
	}

	/**

	 * This method initializes jPanel6	

	 * 	

	 * @return javax.swing.JPanel	

	 */    
	private javax.swing.JPanel getJPanel6() {
		if (jPanel6 == null) {
			jPanel6 = new javax.swing.JPanel();
			jPanel6.setPreferredSize(new java.awt.Dimension(530,290));
			jPanel6.setBackground(new java.awt.Color(242,242,238));
			jPanel6.add(getJPanel7(), null);
			jPanel6.add(getJPanel8(), null);
		}
		return jPanel6;
	}

	/**

	 * This method initializes jPanel7	

	 * 	

	 * @return javax.swing.JPanel	

	 */    
	private javax.swing.JPanel getJPanel7() {
		if (jPanel7 == null) {
			java.awt.FlowLayout layFlowLayout5 = new java.awt.FlowLayout();

			jPanel7 = new javax.swing.JPanel();
			jPanel7.setLayout(layFlowLayout5);
			jPanel7.setPreferredSize(new java.awt.Dimension(500,25));
			jPanel7.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
			jPanel7.setBackground(new java.awt.Color(242,242,238));
			layFlowLayout5.setAlignment(java.awt.FlowLayout.LEFT);
			layFlowLayout5.setHgap(5);
			layFlowLayout5.setVgap(2);
			jPanel7.add(getJLabel1(), null);
			jPanel7.add(getCmbNomDisp(), null);
		}
		return jPanel7;
	}

	/**

	 * This method initializes jLabel1	

	 * 	

	 * @return javax.swing.JLabel	

	 */    
	private javax.swing.JLabel getJLabel1() {
		if (jLabel1 == null) {
			jLabel1 = new javax.swing.JLabel();
			jLabel1.setText("Nombre del dispositivo:  ");
			jLabel1.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
		}
		return jLabel1;
	}

	/**

	 * This method initializes cmbNomDisp	

	 * 	

	 * @return javax.swing.JComboBox	

	 */    
	private javax.swing.JComboBox getCmbNomDisp() {
		if (cmbNomDisp == null) {
			cmbNomDisp = new javax.swing.JComboBox();
			cmbNomDisp.setPreferredSize(new java.awt.Dimension(200,18));
			cmbNomDisp.setBackground(java.awt.Color.white);
			cmbNomDisp.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			cmbNomDisp.addItem("");
			cmbNomDisp.addItem(" Impresora");
			cmbNomDisp.addItem(" Gaveta");
			cmbNomDisp.addItem(" Scanner");
			cmbNomDisp.addItem(" Visor");			
			cmbNomDisp.addItemListener(new java.awt.event.ItemListener() { 
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					cargarValoresPorts(e.getItem().toString().trim().toLowerCase());
					btnSavePort.setEnabled(false);
				}
			});

		}
		return cmbNomDisp;
	}
	

	/**

	 * This method initializes jPanel8	

	 * 	

	 * @return javax.swing.JPanel	

	 */    
	private javax.swing.JPanel getJPanel8() {
		if (jPanel8 == null) {
			javax.swing.border.TitledBorder titledBorder1 = javax.swing.BorderFactory.createTitledBorder(null,"Opciones del Dispositivo",javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,javax.swing.border.TitledBorder.DEFAULT_POSITION,null,null);
			java.awt.FlowLayout layFlowLayout6 = new java.awt.FlowLayout();

			jPanel8 = new javax.swing.JPanel();
			javax.swing.border.TitledBorder ivjTitledBorder = javax.swing.BorderFactory.createTitledBorder(null, "Datos del dispositivo", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null);
			jPanel8.setLayout(layFlowLayout6);
			jPanel8.setPreferredSize(new java.awt.Dimension(500,180));
			jPanel8.setBackground(new java.awt.Color(242,242,238));
			jPanel8.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			layFlowLayout6.setHgap(0);
			layFlowLayout6.setVgap(0);
			ivjTitledBorder.setTitleFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 10));
			titledBorder1.setTitleFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 10));
			jPanel8.add(getJPanel9(), null);
			jPanel8.add(getJPanel10(), null);
			jPanel8.setBorder(ivjTitledBorder);
			ItemListener il = new java.awt.event.ItemListener() { 
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					cambioPorts();
				}
			};	
			cmbSerialPort.addItemListener(il);
			cmbBaudRate.addItemListener(il);
			cmbStopBits.addItemListener(il);
			cmbDataBits.addItemListener(il);
			cmbParity.addItemListener(il);
			cmbFlowCtlIn.addItemListener(il);
			cmbFlowCtlOut.addItemListener(il);	
		}
		return jPanel8;
	}

	/**

	 * This method initializes jPanel9	

	 * 	

	 * @return javax.swing.JPanel	

	 */    
	private javax.swing.JPanel getJPanel9() {
		if (jPanel9 == null) {
			java.awt.FlowLayout layFlowLayout7 = new java.awt.FlowLayout();

			jPanel9 = new javax.swing.JPanel();
			jPanel9.setLayout(layFlowLayout7);
			jPanel9.setPreferredSize(new java.awt.Dimension(490,25));
			jPanel9.setBackground(new java.awt.Color(242,242,238));
			layFlowLayout7.setAlignment(java.awt.FlowLayout.LEFT);
			layFlowLayout7.setHgap(5);
			layFlowLayout7.setVgap(0);
			jPanel9.add(getJLabel2(), null);
			jPanel9.add(getCmbSerialPort(), null);
		}
		return jPanel9;
	}

	/**

	 * This method initializes jLabel2	

	 * 	

	 * @return javax.swing.JLabel	

	 */    
	private javax.swing.JLabel getJLabel2() {
		if (jLabel2 == null) {
			jLabel2 = new javax.swing.JLabel();
			jLabel2.setText("Puerto Serial Asignado: ");
			jLabel2.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
		}
		return jLabel2;
	}

	/**

	 * This method initializes cmbSerialPort	

	 * 	

	 * @return javax.swing.JComboBox	

	 */    
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Enumeration'
	* Fecha: agosto 2011
	*/
	@SuppressWarnings("unchecked")
	private javax.swing.JComboBox getCmbSerialPort() {
		if (cmbSerialPort == null) {
			cmbSerialPort = new javax.swing.JComboBox();
			cmbSerialPort.setPreferredSize(new java.awt.Dimension(110,17));
			cmbSerialPort.setBackground(java.awt.Color.white);
			cmbSerialPort.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			cmbSerialPort.setEnabled(false);
			CommPortIdentifier portId;
			Enumeration<CommPortIdentifier>	en = CommPortIdentifier.getPortIdentifiers();
			// Recorremos la lista de Puertos
			while (en.hasMoreElements()) {
				portId = (CommPortIdentifier) en.nextElement();
				if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
					cmbSerialPort.addItem(portId.getName());
				}
			}
			flag |= 1;
		}
		return cmbSerialPort;
	}

	/**

	 * This method initializes jPanel10	

	 * 	

	 * @return javax.swing.JPanel	

	 */    
	private javax.swing.JPanel getJPanel10() {
		if (jPanel10 == null) {
			java.awt.GridLayout layGridLayout8 = new java.awt.GridLayout();

			jPanel10 = new javax.swing.JPanel();
			jPanel10.setLayout(layGridLayout8);
			jPanel10.setPreferredSize(new java.awt.Dimension(490,125));
			jPanel10.setBackground(new java.awt.Color(242,242,238));
			jPanel10.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
			layGridLayout8.setRows(4);
			layGridLayout8.setColumns(2);
			layGridLayout8.setHgap(5);
			jPanel10.add(getJPanel13(), null);
			jPanel10.add(getJPanel12(), null);
			jPanel10.add(getJPanel11(), null);
			jPanel10.add(getJPanel14(), null);
			jPanel10.add(getJPanel15(), null);
			jPanel10.add(getJPanel16(), null);
		}
		return jPanel10;
	}

	/**

	 * This method initializes jPanel11	

	 * 	

	 * @return javax.swing.JPanel	

	 */    
	private javax.swing.JPanel getJPanel11() {
		if (jPanel11 == null) {
			jPanel11 = new javax.swing.JPanel();
			jPanel11.setBackground(new java.awt.Color(242,242,238));
			jPanel11.add(getJPanel21(), null);
			jPanel11.add(getJPanel22(), null);
		}
		return jPanel11;
	}

	/**

	 * This method initializes jPanel12	

	 * 	

	 * @return javax.swing.JPanel	

	 */    
	private javax.swing.JPanel getJPanel12() {
		if (jPanel12 == null) {
			jPanel12 = new javax.swing.JPanel();
			jPanel12.setBackground(new java.awt.Color(242,242,238));
			jPanel12.add(getJPanel19(), null);
			jPanel12.add(getJPanel20(), null);
		}
		return jPanel12;
	}

	/**

	 * This method initializes jPanel13	

	 * 	

	 * @return javax.swing.JPanel	

	 */    
	private javax.swing.JPanel getJPanel13() {
		if (jPanel13 == null) {
			jPanel13 = new javax.swing.JPanel();
			jPanel13.setBackground(new java.awt.Color(242,242,238));
			jPanel13.add(getJPanel17(), null);
			jPanel13.add(getJPanel18(), null);
		}
		return jPanel13;
	}

	/**

	 * This method initializes jPanel14	

	 * 	

	 * @return javax.swing.JPanel	

	 */    
	private javax.swing.JPanel getJPanel14() {
		if (jPanel14 == null) {
			jPanel14 = new javax.swing.JPanel();
			jPanel14.setBackground(new java.awt.Color(242,242,238));
			jPanel14.add(getJPanel23(), null);
			jPanel14.add(getJPanel24(), null);
		}
		return jPanel14;
	}

	/**

	 * This method initializes jPanel15	

	 * 	

	 * @return javax.swing.JPanel	

	 */    
	private javax.swing.JPanel getJPanel15() {
		if (jPanel15 == null) {
			jPanel15 = new javax.swing.JPanel();
			jPanel15.setBackground(new java.awt.Color(242,242,238));
			jPanel15.add(getJPanel25(), null);
			jPanel15.add(getJPanel26(), null);
		}
		return jPanel15;
	}

	/**

	 * This method initializes jPanel16	

	 * 	

	 * @return javax.swing.JPanel	

	 */    
	private javax.swing.JPanel getJPanel16() {
		if (jPanel16 == null) {
			jPanel16 = new javax.swing.JPanel();
			jPanel16.setBackground(new java.awt.Color(242,242,238));
			jPanel16.add(getJPanel27(), null);
			jPanel16.add(getJPanel28(), null);
		}
		return jPanel16;
	}

	/**

	 * This method initializes jPanel17	

	 * 	

	 * @return javax.swing.JPanel	

	 */    
	private javax.swing.JPanel getJPanel17() {
		if (jPanel17 == null) {
			java.awt.FlowLayout layFlowLayout10 = new java.awt.FlowLayout();

			jPanel17 = new javax.swing.JPanel();
			jPanel17.setLayout(layFlowLayout10);
			jPanel17.setPreferredSize(new java.awt.Dimension(110,25));
			jPanel17.setBackground(new java.awt.Color(242,242,238));
			layFlowLayout10.setHgap(0);
			layFlowLayout10.setAlignment(java.awt.FlowLayout.LEFT);
			jPanel17.add(getJLabel3(), null);
		}
		return jPanel17;
	}

	/**

	 * This method initializes jPanel18	

	 * 	

	 * @return javax.swing.JPanel	

	 */    
	private javax.swing.JPanel getJPanel18() {
		if (jPanel18 == null) {
			jPanel18 = new javax.swing.JPanel();
			jPanel18.setBackground(new java.awt.Color(242,242,238));
			jPanel18.add(getCmbBaudRate(), null);
		}
		return jPanel18;
	}

	/**

	 * This method initializes jPanel19	

	 * 	

	 * @return javax.swing.JPanel	

	 */    
	private javax.swing.JPanel getJPanel19() {
		if (jPanel19 == null) {
			java.awt.FlowLayout layFlowLayout13 = new java.awt.FlowLayout();

			jPanel19 = new javax.swing.JPanel();
			jPanel19.setLayout(layFlowLayout13);
			layFlowLayout13.setAlignment(java.awt.FlowLayout.LEFT);
			layFlowLayout13.setHgap(0);
			jPanel19.setPreferredSize(new java.awt.Dimension(110,25));
			jPanel19.setBackground(new java.awt.Color(242,242,238));
			jPanel19.add(getJLabel4(), null);
		}
		return jPanel19;
	}

	/**

	 * This method initializes jPanel20	

	 * 	

	 * @return javax.swing.JPanel	

	 */    
	private javax.swing.JPanel getJPanel20() {
		if (jPanel20 == null) {
			jPanel20 = new javax.swing.JPanel();
			jPanel20.setBackground(new java.awt.Color(242,242,238));
			jPanel20.add(getCmbStopBits(), null);
		}
		return jPanel20;
	}

	/**

	 * This method initializes jPanel21	

	 * 	

	 * @return javax.swing.JPanel	

	 */    
	private javax.swing.JPanel getJPanel21() {
		if (jPanel21 == null) {
			java.awt.FlowLayout layFlowLayout11 = new java.awt.FlowLayout();

			jPanel21 = new javax.swing.JPanel();
			jPanel21.setLayout(layFlowLayout11);
			layFlowLayout11.setHgap(0);
			layFlowLayout11.setAlignment(java.awt.FlowLayout.LEFT);
			jPanel21.setPreferredSize(new java.awt.Dimension(110,25));
			jPanel21.setBackground(new java.awt.Color(242,242,238));
			jPanel21.add(getJLabel5(), null);
		}
		return jPanel21;
	}

	/**

	 * This method initializes jPanel22	

	 * 	

	 * @return javax.swing.JPanel	

	 */    
	private javax.swing.JPanel getJPanel22() {
		if (jPanel22 == null) {
			jPanel22 = new javax.swing.JPanel();
			jPanel22.setBackground(new java.awt.Color(242,242,238));
			jPanel22.add(getCmbDataBits(), null);
		}
		return jPanel22;
	}

	/**

	 * This method initializes jPanel23	

	 * 	

	 * @return javax.swing.JPanel	

	 */    
	private javax.swing.JPanel getJPanel23() {
		if (jPanel23 == null) {
			java.awt.FlowLayout layFlowLayout14 = new java.awt.FlowLayout();

			jPanel23 = new javax.swing.JPanel();
			jPanel23.setLayout(layFlowLayout14);
			layFlowLayout14.setAlignment(java.awt.FlowLayout.LEFT);
			layFlowLayout14.setHgap(0);
			jPanel23.setPreferredSize(new java.awt.Dimension(110,25));
			jPanel23.setBackground(new java.awt.Color(242,242,238));
			jPanel23.add(getJLabel6(), null);
		}
		return jPanel23;
	}

	/**

	 * This method initializes jPanel24	

	 * 	

	 * @return javax.swing.JPanel	

	 */    
	private javax.swing.JPanel getJPanel24() {
		if (jPanel24 == null) {
			jPanel24 = new javax.swing.JPanel();
			jPanel24.setBackground(new java.awt.Color(242,242,238));
			jPanel24.add(getCmbParity(), null);
		}
		return jPanel24;
	}

	/**

	 * This method initializes jPanel25	

	 * 	

	 * @return javax.swing.JPanel	

	 */    
	private javax.swing.JPanel getJPanel25() {
		if (jPanel25 == null) {
			java.awt.FlowLayout layFlowLayout12 = new java.awt.FlowLayout();

			jPanel25 = new javax.swing.JPanel();
			jPanel25.setLayout(layFlowLayout12);
			layFlowLayout12.setAlignment(java.awt.FlowLayout.LEFT);
			layFlowLayout12.setHgap(0);
			jPanel25.setPreferredSize(new java.awt.Dimension(110,25));
			jPanel25.setBackground(new java.awt.Color(242,242,238));
			jPanel25.add(getJLabel7(), null);
		}
		return jPanel25;
	}

	/**

	 * This method initializes jPanel26	

	 * 	

	 * @return javax.swing.JPanel	

	 */    
	private javax.swing.JPanel getJPanel26() {
		if (jPanel26 == null) {
			jPanel26 = new javax.swing.JPanel();
			jPanel26.setBackground(new java.awt.Color(242,242,238));
			jPanel26.add(getCmbFlowCtlIn(), null);
		}
		return jPanel26;
	}

	/**

	 * This method initializes jPanel27	

	 * 	

	 * @return javax.swing.JPanel	

	 */    
	private javax.swing.JPanel getJPanel27() {
		if (jPanel27 == null) {
			java.awt.FlowLayout layFlowLayout9 = new java.awt.FlowLayout();

			jPanel27 = new javax.swing.JPanel();
			jPanel27.setLayout(layFlowLayout9);
			jPanel27.setPreferredSize(new java.awt.Dimension(110,25));
			jPanel27.setBackground(new java.awt.Color(242,242,238));
			layFlowLayout9.setAlignment(java.awt.FlowLayout.LEFT);
			layFlowLayout9.setHgap(0);
			layFlowLayout9.setVgap(5);
			jPanel27.add(getJLabel8(), null);
		}
		return jPanel27;
	}

	/**

	 * This method initializes jPanel28	

	 * 	

	 * @return javax.swing.JPanel	

	 */    
	private javax.swing.JPanel getJPanel28() {
		if (jPanel28 == null) {
			jPanel28 = new javax.swing.JPanel();
			jPanel28.setBackground(new java.awt.Color(242,242,238));
			jPanel28.add(getCmbFlowCtlOut(), null);
		}
		return jPanel28;
	}

	/**

	 * This method initializes jLabel3	

	 * 	

	 * @return javax.swing.JLabel	

	 */    
	private javax.swing.JLabel getJLabel3() {
		if (jLabel3 == null) {
			jLabel3 = new javax.swing.JLabel();
			jLabel3.setText("BAUD Rate: ");
			jLabel3.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
		}
		return jLabel3;
	}

	/**

	 * This method initializes jLabel4	

	 * 	

	 * @return javax.swing.JLabel	

	 */    
	private javax.swing.JLabel getJLabel4() {
		if (jLabel4 == null) {
			jLabel4 = new javax.swing.JLabel();
			jLabel4.setText("STOP Bits: ");
			jLabel4.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
		}
		return jLabel4;
	}

	/**

	 * This method initializes jLabel5	

	 * 	

	 * @return javax.swing.JLabel	

	 */    
	private javax.swing.JLabel getJLabel5() {
		if (jLabel5 == null) {
			jLabel5 = new javax.swing.JLabel();
			jLabel5.setText("DATA Bits: ");
			jLabel5.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
		}
		return jLabel5;
	}

	/**

	 * This method initializes jLabel6	

	 * 	

	 * @return javax.swing.JLabel	

	 */    
	private javax.swing.JLabel getJLabel6() {
		if (jLabel6 == null) {
			jLabel6 = new javax.swing.JLabel();
			jLabel6.setText("Parity: ");
			jLabel6.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
		}
		return jLabel6;
	}

	/**

	 * This method initializes jLabel7	

	 * 	

	 * @return javax.swing.JLabel	

	 */    
	private javax.swing.JLabel getJLabel7() {
		if (jLabel7 == null) {
			jLabel7 = new javax.swing.JLabel();
			jLabel7.setText("FLOW Control IN: ");
			jLabel7.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
		}
		return jLabel7;
	}

	/**

	 * This method initializes jLabel8	

	 * 	

	 * @return javax.swing.JLabel	

	 */    
	private javax.swing.JLabel getJLabel8() {
		if (jLabel8 == null) {
			jLabel8 = new javax.swing.JLabel();
			jLabel8.setText("FLOW Control OUT:");
			jLabel8.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
		}
		return jLabel8;
	}

	/**

	 * This method initializes cmbBaudRate	

	 * 	

	 * @return javax.swing.JComboBox	

	 */    
	private javax.swing.JComboBox getCmbBaudRate() {
		if (cmbBaudRate == null) {
			cmbBaudRate = new javax.swing.JComboBox();
			cmbBaudRate.setPreferredSize(new java.awt.Dimension(110,17));
			cmbBaudRate.setBackground(java.awt.Color.white);
			cmbBaudRate.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			cmbBaudRate.setEnabled(false);
			cmbBaudRate.addItem("300");
			cmbBaudRate.addItem("2400");
			cmbBaudRate.addItem("4800");
			cmbBaudRate.addItem("9600");
			cmbBaudRate.addItem("14400");
			cmbBaudRate.addItem("28800");
			cmbBaudRate.addItem("38400");
			cmbBaudRate.addItem("57600");
			cmbBaudRate.addItem("152000");
			flag |= 2;
		}
		return cmbBaudRate;
	}

	/**

	 * This method initializes cmbFlowCtlOut	

	 * 	

	 * @return javax.swing.JComboBox	

	 */    
	private javax.swing.JComboBox getCmbFlowCtlOut() {
		if (cmbFlowCtlOut == null) {
			cmbFlowCtlOut = new javax.swing.JComboBox();
			cmbFlowCtlOut.setPreferredSize(new java.awt.Dimension(110,17));
			cmbFlowCtlOut.setBackground(java.awt.Color.white);
			cmbFlowCtlOut.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			cmbFlowCtlOut.setEnabled(false);
			cmbFlowCtlOut.addItem("None");
			cmbFlowCtlOut.addItem("Xon/Xoff In");
			cmbFlowCtlOut.addItem("RTS/CTS In");
			flag |= 64;

		}
		return cmbFlowCtlOut;
	}

	/**

	 * This method initializes cmbStopBits	

	 * 	

	 * @return javax.swing.JComboBox	

	 */    
	private javax.swing.JComboBox getCmbStopBits() {
		if (cmbStopBits == null) {
			cmbStopBits = new javax.swing.JComboBox();
			cmbStopBits.setPreferredSize(new java.awt.Dimension(110,17));
			cmbStopBits.setBackground(java.awt.Color.white);
			cmbStopBits.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			cmbStopBits.setEnabled(false);
			cmbStopBits.addItem("1");
			cmbStopBits.addItem("1.5");
			cmbStopBits.addItem("2");
			flag |= 8;

		}
		return cmbStopBits;
	}

	/**

	 * This method initializes cmbDataBits	

	 * 	

	 * @return javax.swing.JComboBox	

	 */    
	private javax.swing.JComboBox getCmbDataBits() {
		if (cmbDataBits == null) {
			cmbDataBits = new javax.swing.JComboBox();
			cmbDataBits.setPreferredSize(new java.awt.Dimension(110,17));
			cmbDataBits.setBackground(java.awt.Color.white);
			cmbDataBits.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			cmbDataBits.setEnabled(false);
			cmbDataBits.addItem("5");
			cmbDataBits.addItem("6");
			cmbDataBits.addItem("7");
			cmbDataBits.addItem("8");
			flag |= 4;

		}
		return cmbDataBits;
	}

	/**

	 * This method initializes cmbParity	

	 * 	

	 * @return javax.swing.JComboBox	

	 */    
	private javax.swing.JComboBox getCmbParity() {
		if (cmbParity == null) {
			cmbParity = new javax.swing.JComboBox();
			cmbParity.setPreferredSize(new java.awt.Dimension(110,17));
			cmbParity.setBackground(java.awt.Color.white);
			cmbParity.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			cmbParity.setEnabled(false);
			cmbParity.addItem("None");
			cmbParity.addItem("Even");
			cmbParity.addItem("Odd");
			cmbParity.setSelectedItem("None");
			flag |= 16;

		}
		return cmbParity;
	}

	/**

	 * This method initializes cmbFlowCtlIn	

	 * 	

	 * @return javax.swing.JComboBox	

	 */    
	private javax.swing.JComboBox getCmbFlowCtlIn() {
		if (cmbFlowCtlIn == null) {
			cmbFlowCtlIn = new javax.swing.JComboBox();
			cmbFlowCtlIn.setPreferredSize(new java.awt.Dimension(110,17));
			cmbFlowCtlIn.setBackground(java.awt.Color.white);
			cmbFlowCtlIn.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			cmbFlowCtlIn.setEnabled(false);
			cmbFlowCtlIn.addItem("None");
			cmbFlowCtlIn.addItem("Xon/Xoff In");
			cmbFlowCtlIn.addItem("RTS/CTS In");
			flag |= 32;

		}
		return cmbFlowCtlIn;
	}
	
	private void desactivarTodo(){
		
		/* Desactivamos todos los componentes */
		
		cmbSerialPort.setEnabled(false);
		cmbBaudRate.setEnabled(false);
		cmbFlowCtlOut.setEnabled(false);
		cmbStopBits.setEnabled(false);
		cmbDataBits.setEnabled(false);
		cmbParity.setEnabled(false);
		cmbFlowCtlIn.setEnabled(false);
		btnSavePort.setEnabled(false);
	}
	
	private void activarTodo(){

		cmbSerialPort.setEnabled(true);
		cmbBaudRate.setEnabled(true);
		cmbFlowCtlOut.setEnabled(true);
		cmbStopBits.setEnabled(true);
		cmbDataBits.setEnabled(true);
		cmbParity.setEnabled(true);
		cmbFlowCtlIn.setEnabled(true);
	}



	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	public void mouseReleased(MouseEvent e) {
	
		if(e.getSource().equals(jButton1)){
			this.dispose();
			System.exit(0);
		}
		
		if(e.getSource().equals(btnSavePort)){
			if(btnSavePort.isEnabled()){
				/* Llamo a la rutina que guarda los settings */
				guardarValoresPorts();
				
			}
		}
		
	}



	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 */
	public void mouseEntered(MouseEvent e) {
		
	}



	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	 */
	public void mouseExited(MouseEvent e) {
		
	}



	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	public void mousePressed(MouseEvent e) {
	
	}



	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 */
	public void mouseClicked(MouseEvent e) {

		
	}



	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource().equals(cmbNomDisp)){
			if(cmbNomDisp.getSelectedIndex() == 0){
				desactivarTodo();
			}
			else{
				activarTodo();
			}
		}
		
	}
	
	private void cambioPage(ChangeEvent e) 
	{
		switch (((JTabbedPane)e.getSource()).getSelectedIndex()) {
			case 0 : 
				// Puertos
				break;
			case 1 : 
				// Apartados
				cargarValoresApart();
				btnSaveApart.setEnabled(false);
				break;
			case 2 :
				cargarValoresLR();
				btnSaveLR.setEnabled(false);
				break;
			case 3 : 
				// Base de Datos
				cargarValoresBD();
				btnSaveBD.setEnabled(false);
				break;
			case 4 : 
				// Facturacion 
				cargarValoresFact();
				btnSaveFact.setEnabled(false);
				break;
			case 5 :
				// Sistemas 
				cargarValoresSys();
				btnSaveSys.setEnabled(false);
				break;
			
		}
	};
	
	private void cambioPorts() {
		btnSavePort.setEnabled(true);
	};
	
	private void cambioApart() {
		btnSaveApart.setEnabled(true);
	};
	
	private void cambioLR() {
		btnSaveLR.setEnabled(true);
	};
	
	private void cambioBD () {
		btnSaveBD.setEnabled(true);
	};
	
	private void cambioFact(){
		btnSaveFact.setEnabled(true);
	};
	
	private void cambioSys() {
		btnSaveSys.setEnabled(true);
	};
	
	
	private void guardarValoresPorts(){
		if (cmbNomDisp.getSelectedIndex()<0 || cmbSerialPort.getSelectedIndex()<0 ||  
		    cmbBaudRate.getSelectedIndex()<0 || cmbStopBits.getSelectedIndex()<0 ||
		    cmbDataBits.getSelectedIndex()<0 || cmbParity.getSelectedIndex()<0 ||
		    cmbFlowCtlIn.getSelectedIndex()<0 || cmbFlowCtlOut.getSelectedIndex()<0) {
		    MensajesVentanas.aviso("Debe Especificar Todos los Datos correspondientes");
		} else {
			if(epaPreferencesProxy.isNodeConfigured(cmbNomDisp.getSelectedItem().toString().trim())){
				try {
					epaPreferencesProxy.setConfigStringForParameter(cmbNomDisp.getSelectedItem().toString().trim().toLowerCase(),"Puerto Serial",cmbSerialPort.getSelectedItem().toString());
					epaPreferencesProxy.setConfigStringForParameter(cmbNomDisp.getSelectedItem().toString().trim().toLowerCase(),"Baud Rate",cmbBaudRate.getSelectedItem().toString());
					epaPreferencesProxy.setConfigStringForParameter(cmbNomDisp.getSelectedItem().toString().trim().toLowerCase(),"Stop Bits",cmbStopBits.getSelectedItem().toString());
					epaPreferencesProxy.setConfigStringForParameter(cmbNomDisp.getSelectedItem().toString().trim().toLowerCase(),"Data Bits",cmbDataBits.getSelectedItem().toString());
					epaPreferencesProxy.setConfigStringForParameter(cmbNomDisp.getSelectedItem().toString().trim().toLowerCase(),"Parity",cmbParity.getSelectedItem().toString());
					epaPreferencesProxy.setConfigStringForParameter(cmbNomDisp.getSelectedItem().toString().trim().toLowerCase(),"Flow Control In",cmbFlowCtlIn.getSelectedItem().toString());
					epaPreferencesProxy.setConfigStringForParameter(cmbNomDisp.getSelectedItem().toString().trim().toLowerCase(),"Flow Control Out",cmbFlowCtlOut.getSelectedItem().toString());
					btnSavePort.setEnabled(false);
				} catch (NoSuchNodeException e1) {
					e1.printStackTrace();
				} catch (UnidentifiedPreferenceException e1) {
					e1.printStackTrace();
				}
			}
			else{
				
				/* La config no existe, la agregamos*/
				
				try {
					epaPreferencesProxy.addNewNodeToPreferencesTop(cmbNomDisp.getSelectedItem().toString().trim().toLowerCase());
				} catch (NodeAlreadyExistsException e) {
					e.printStackTrace();
				} catch (UnidentifiedPreferenceException e) {
					e.printStackTrace();
				}
				
				try {
					epaPreferencesProxy.setConfigStringForParameter(cmbNomDisp.getSelectedItem().toString().trim().toLowerCase(),"Puerto Serial",cmbSerialPort.getSelectedItem().toString());
					epaPreferencesProxy.setConfigStringForParameter(cmbNomDisp.getSelectedItem().toString().trim().toLowerCase(),"Baud Rate",cmbBaudRate.getSelectedItem().toString());
					epaPreferencesProxy.setConfigStringForParameter(cmbNomDisp.getSelectedItem().toString().trim().toLowerCase(),"Stop Bits",cmbStopBits.getSelectedItem().toString());
					epaPreferencesProxy.setConfigStringForParameter(cmbNomDisp.getSelectedItem().toString().trim().toLowerCase(),"Data Bits",cmbDataBits.getSelectedItem().toString());
					epaPreferencesProxy.setConfigStringForParameter(cmbNomDisp.getSelectedItem().toString().trim().toLowerCase(),"Parity",cmbParity.getSelectedItem().toString());
					epaPreferencesProxy.setConfigStringForParameter(cmbNomDisp.getSelectedItem().toString().trim().toLowerCase(),"Flow Control In",cmbFlowCtlIn.getSelectedItem().toString());
					epaPreferencesProxy.setConfigStringForParameter(cmbNomDisp.getSelectedItem().toString().trim().toLowerCase(),"Flow Control Out",cmbFlowCtlOut.getSelectedItem().toString());
					btnSavePort.setEnabled(false);
				} catch (NoSuchNodeException e1) {
					e1.printStackTrace();
				} catch (UnidentifiedPreferenceException e1) {
					e1.printStackTrace();
				}
				   						   				
			}
		}
	}
	private void cargarValoresPorts(String disp) {
		if (null != disp && !disp.equals("") && epaPreferencesProxy.isNodeConfigured(disp)) {
			// Los valores existen
			String param = null;
			try {
				param = epaPreferencesProxy.getConfigStringForParameter(disp, "Puerto Serial");
				cmbSerialPort.setSelectedItem(param);
				param = epaPreferencesProxy.getConfigStringForParameter(disp, "Baud Rate");
				cmbBaudRate.setSelectedItem(param);
				param = epaPreferencesProxy.getConfigStringForParameter(disp, "Stop Bits");
				cmbStopBits.setSelectedItem(param);
				param = epaPreferencesProxy.getConfigStringForParameter(disp, "Data Bits");
				cmbDataBits.setSelectedItem(param);
				param = epaPreferencesProxy.getConfigStringForParameter(disp, "Parity");
				cmbParity.setSelectedItem(param);
				param = epaPreferencesProxy.getConfigStringForParameter(disp, "Flow Control In");
				cmbFlowCtlIn.setSelectedItem(param);
				param = epaPreferencesProxy.getConfigStringForParameter(disp, "Flow Control Out");
				cmbFlowCtlOut.setSelectedItem(param);
			} catch (Exception e) {
				e.printStackTrace();
			} 
		} else {
			// Los valores no existen, tomar la configuracion por defecto.
			if (127 == flag) {
				cmbBaudRate.setSelectedIndex(-1);
				cmbSerialPort.setSelectedIndex(-1);
				cmbDataBits.setSelectedIndex(-1);
				cmbStopBits.setSelectedIndex(-1);
				cmbParity.setSelectedIndex(-1);
				cmbFlowCtlIn.setSelectedIndex(-1);
				cmbFlowCtlOut.setSelectedIndex(-1);
			}
		}
		
		
	};
	
	private void guardarValoresApart(){

  		if(!eppCR.isNodeConfigured("apartado")){
		// La config no existe, la agregamos
			try {
				eppCR.addNewNodeToPreferencesTop("apartado");
			} catch (NodeAlreadyExistsException e) {
				e.printStackTrace();
			} catch (UnidentifiedPreferenceException e) {
				e.printStackTrace();
			}
  		}
		
		try {
			eppCR.setConfigStringForParameter("apartado","CargoPorServicio",txtCargoServ.getText());
			eppCR.setConfigStringForParameter("apartado", "TipoVigencia", cmbTipoVigencia.getSelectedItem().toString());
			eppCR.setConfigStringForParameter("apartado", "TiempoVigencia", txtDiasVig.getText());
			eppCR.setConfigStringForParameter("apartado","MontoMinimoApartado", txtMtoMinApartado.getText());
			if (rbRebajS.isSelected()) { 
				eppCR.setConfigStringForParameter("apartado","PermiteRebajas", "S");
			} else if (rbRebajN.isSelected()) {
				eppCR.setConfigStringForParameter("apartado","PermiteRebajas", "N");
			}
			if (rbRecalcSaldoS.isSelected()) { 
				eppCR.setConfigStringForParameter("apartado","RecalcularSaldo", "S");
			} else if (rbRecalcSaldoN.isSelected()) {
				eppCR.setConfigStringForParameter("apartado","RecalcularSaldo", "N");
			}
			eppCR.setConfigStringForParameter("apartado","NumeroTiposDeAbonos",cmbNumAbonos.getSelectedItem().toString());
			// Se pone interesante el asunto
			// Guardar los valores de la lista, hasta el numero definido
			int tope = cmbNumAbonos.getSelectedIndex() + 1;
			for (int i = 1; i <= tope; i++) {
				Abono ab = (Abono)listaAbonos.get(Integer.toString(i));
				eppCR.setConfigStringForParameter("apartado","Abono" + ab.getCodigo(), Integer.toString(ab.getValor()));
			}
			// Depurar la lista
/*			listaAbonos.clear();
			for (int i = 1; i <= tope; i++) {
				String valStr = eppCR.getConfigStringForParameter("apartado", "Abono" + Integer.toString(i));
				Abono a = new Abono(Integer.parseInt(valStr));
				listaAbonos.put(a.getCodigo(), a);

			};			
*/			
			btnSaveApart.setEnabled(false);
			if (rbPreguntarFactSi.isSelected()) { 
				eppCR.setConfigStringForParameter("apartado","PreguntarFacturar", "S");
			} else if (rbPreguntarFactNo.isSelected()) {
				eppCR.setConfigStringForParameter("apartado","PreguntarFacturar", "N");
			}
		} catch (NoSuchNodeException e1) {
			e1.printStackTrace();
		} catch (UnidentifiedPreferenceException e1) {
			e1.printStackTrace();
		}

	};
	
	private void cargarValoresApart(){
		if (eppCR.isNodeConfigured("apartado")) {
			try {
				cmbTipoVigencia.setSelectedItem(eppCR.getConfigStringForParameter("apartado", "TipoVigencia"));
				txtDiasVig.setText(eppCR.getConfigStringForParameter("apartado", "TiempoVigencia"));
				txtCargoServ.setText(eppCR.getConfigStringForParameter("apartado", "CargoPorServicio"));
				txtMtoMinApartado.setText(eppCR.getConfigStringForParameter("apartado", "MontoMinimoApartado"));
				cmbNumAbonos.setSelectedItem(eppCR.getConfigStringForParameter("apartado", "NumeroTiposDeAbonos"));
				if (eppCR.getConfigStringForParameter("apartado", "PermiteRebajas").equals("S")) {
					rbRebajS.setSelected(true);
				} else {
					rbRebajN.setSelected(true);
				}
				if (eppCR.getConfigStringForParameter("apartado", "RecalcularSaldo").equals("S")) {
					rbRecalcSaldoS.setSelected(true);
				} else {
					rbRecalcSaldoN.setSelected(true);
				}
				int numAbonos = Integer.parseInt(eppCR.getConfigStringForParameter("apartado", "NumeroTiposDeAbonos"));

				cmbIDApart.removeAllItems();
				listaAbonos.clear();
				for (int i = 1; i <= numAbonos; i++) {
					String valStr = eppCR.getConfigStringForParameter("apartado", "Abono" + Integer.toString(i));
					Abono a = new Abono(Integer.parseInt(valStr), i);
					listaAbonos.put(String.valueOf(a.getCodigo()), a);
					cmbIDApart.addItem(String.valueOf(a.getCodigo()));
				};
				cmbIDApart.setSelectedIndex(-1);
				txtValorAbo.setText("");
				btnActAbono.setEnabled(false);
				if (eppCR.getConfigStringForParameter("apartado", "PreguntarFacturar").equals("S")) {
					rbPreguntarFactSi.setSelected(true);
				} else {
					rbPreguntarFactNo.setSelected(true);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			// Configuracion  por defecto
			/*  
			 * txtDiasVig
			 * rbRebajS/N
			 * cmbNumAbonos
			 * txtCargoServ
			 * rbRecalcSaldo
			 * cmbIdApart
			 * txtValorAbo 
			 */
			 txtDiasVig.setText("");
			 cmbTipoVigencia.setSelectedIndex(0);
			 rbRebajS.setSelected(false);
			 txtCargoServ.setText("");
			 rbRecalcSaldoS.setSelected(false);
			 cmbNumAbonos.setSelectedIndex(0);
			 cmbIDApart.setSelectedIndex(-1);
			 txtValorAbo.setText("");
			 rbPreguntarFactSi.setSelected(false);
		}		
	};
	
	private void cargarValoresLR(){
		if (eppCR.isNodeConfigured("listaregalos")) {
			try {
				txtMontoMinimoLG.setText(eppCR.getConfigStringForParameter("listaregalos", "MontoMinimoLG"));
				txtCostoUT.setText(eppCR.getConfigStringForParameter("listaregalos", "CostoUT"));
				txtDiasAperturaLG.setText(eppCR.getConfigStringForParameter("listaregalos", "MaxDiasAperturaLG"));
				txtDbUrlServidorPdt.setText(eppCR.getConfigStringForParameter("listaregalos", "DbPdtUrlServidor"));
				txtUsuarioDbPdt.setText(eppCR.getConfigStringForParameter("listaregalos", "DbPdtUsuario"));
				txtClaveDbPdt.setText(eppCR.getConfigStringForParameter("listaregalos", "DbPdtClave"));
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			txtMontoMinimoLG.setText("");
			txtCostoUT.setText("");
			txtDiasAperturaLG.setText("");
			txtDbUrlServidorPdt.setText("");
		}		
	};
	
	private void configListaAbono(int cuantos) {
		cmbIDApart.removeAllItems();
		for (int i = 1; i <= cuantos; i++) {
			String cod = Integer.toString(i);
			if (!listaAbonos.containsKey(cod)) {
				Abono a = new Abono(0, i);
				listaAbonos.put(String.valueOf(a.getCodigo()), a);	
			};
			cmbIDApart.addItem(cod);
		};
	};
	
	private void modifAbono (String codigo, int valor) {
		((Abono)listaAbonos.get(codigo)).setValor(valor);
		btnSaveApart.setEnabled(true);
	};
	
	private void guardarValoresBD() {
		if(!eppCR.isNodeConfigured("db")){
			/* La config no existe, la agregamos*/
			try {
				eppCR.addNewNodeToPreferencesTop("db");
			} catch (NodeAlreadyExistsException e) {
				e.printStackTrace();
			} catch (UnidentifiedPreferenceException e) {
				e.printStackTrace();
			}
		}
		try {
			eppCR.setConfigStringForParameter("db","dbClaseLocal", txtLocalClass.getText());
			eppCR.setConfigStringForParameter("db","dbClaseServidor", txtServerClass.getText());
			eppCR.setConfigStringForParameter("db","dbClaseServidorCentral", txtCentralServClass.getText());
			eppCR.setConfigStringForParameter("db","objetoRMI", txtRMI.getText());
			eppCR.setConfigStringForParameter("db","dbClave", txtClave.getText());
			eppCR.setConfigStringForParameter("db","dbEsquema", txtEsquema.getText());
			eppCR.setConfigStringForParameter("db","dbUrlLocal", txtUrlLocal.getText());
			eppCR.setConfigStringForParameter("db","dbUrlServidor", txtUrlServ.getText());
			eppCR.setConfigStringForParameter("db","dbUrlServidorCentral", txtUrlServCentral.getText());
			eppCR.setConfigStringForParameter("db","dbUsuario", txtUser.getText());
			if (rbGarantFactS.isSelected()) { 
				eppCR.setConfigStringForParameter("db","garantizarFacturacion", "S");
			} else if (rbGarantFactN.isSelected()) {
				eppCR.setConfigStringForParameter("db","garantizarFacturacion", "N");
			}
			btnSaveBD.setEnabled(false);
		} catch (NoSuchNodeException e1) {
			e1.printStackTrace();
		} catch (UnidentifiedPreferenceException e1) {
			e1.printStackTrace();
		}
	};
	
	private void cargarValoresBD() {
		if (eppCR.isNodeConfigured("db")) {
			try {
				txtLocalClass.setText(eppCR.getConfigStringForParameter("db", "dbClaseLocal"));
				txtServerClass.setText(eppCR.getConfigStringForParameter("db", "dbClaseServidor"));
				txtCentralServClass.setText(eppCR.getConfigStringForParameter("db", "dbClaseServidorCentral"));
				txtClave.setText(eppCR.getConfigStringForParameter("db", "dbClave"));
				txtRMI.setText(eppCR.getConfigStringForParameter("db", "objetoRMI"));
				txtEsquema.setText(eppCR.getConfigStringForParameter("db", "dbEsquema"));
				txtUrlLocal.setText(eppCR.getConfigStringForParameter("db", "dbUrlLocal"));
				txtUrlServ.setText(eppCR.getConfigStringForParameter("db", "dbUrlServidor"));
				txtUrlServCentral.setText(eppCR.getConfigStringForParameter("db", "dbUrlServidorCentral"));
				txtUser.setText(eppCR.getConfigStringForParameter("db", "dbUsuario"));
  			    if(eppCR.getConfigStringForParameter("db", "garantizarFacturacion").equals("S")) {
  			    	rbGarantFactS.setSelected(true);
  			    } else {
  			    	rbGarantFactN.setSelected(true);
  			    }
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			// Configuracion por defecto
			txtLocalClass.setText("");
			txtServerClass.setText("");
			txtCentralServClass.setText("");
			txtClave.setText("");
			txtRMI.setText("");
			txtUrlLocal.setText("");
			txtUrlServ.setText("");
			txtUrlServCentral.setText("");
			txtEsquema.setText("");
			txtUser.setText("");
			rbGarantFactN.setSelected(false);
			rbGarantFactS.setSelected(false);
		}
	};
	
	private void guardarValoresFact() {
		if(!eppCR.isNodeConfigured("facturacion")){
			/* La config no existe, la agregamos*/
			try {
				eppCR.addNewNodeToPreferencesTop("facturacion");
			} catch (NodeAlreadyExistsException e) {
				e.printStackTrace();
			} catch (UnidentifiedPreferenceException e) {
				e.printStackTrace();
			}
		}
		try {
			if (rbAutCierreS.isSelected()) { 
				eppCR.setConfigStringForParameter("facturacion","autorizarCierreCajero", "S");
			} else if (rbAutCierreN.isSelected()) {
				eppCR.setConfigStringForParameter("facturacion","autorizarCierreCajero", "N");
			}
			if (rbEmpAcumS.isSelected()) { 
				eppCR.setConfigStringForParameter("facturacion","empaquesAcumulativos", "S");
			} else if (rbEmpAcumN.isSelected()) {
				eppCR.setConfigStringForParameter("facturacion","empaquesAcumulativos", "N");
			}
			eppCR.setConfigStringForParameter("facturacion","longitudCodigoInterno", txtLongCodInt.getText());
			eppCR.setConfigStringForParameter("facturacion","numeroCuentaCheque", txtNumCuenta.getText());
			if (rbRebajEmpS.isSelected()) { 
				eppCR.setConfigStringForParameter("facturacion","permitirRebajasAempleados", "S");
			} else if (rbRebajEmpN.isSelected()) {
				eppCR.setConfigStringForParameter("facturacion","permitirRebajasAempleados", "N");
			}
			eppCR.setConfigStringForParameter("facturacion","porcentajeMensajeEmpaque", txtPorcMsgEmp.getText());
			if (rbReqCliS.isSelected()) { 
				eppCR.setConfigStringForParameter("facturacion","requiereCliente", "S");
			} else if (rbReqCliN.isSelected()) {
				eppCR.setConfigStringForParameter("facturacion","requiereCliente", "N");
			}
			eppCR.setConfigStringForParameter("facturacion","tipoCuentaCheque", cmbTipoCuenta.getSelectedItem().toString());
			
			eppCR.setConfigStringForParameter("facturacion","porcentajeRetencionIVA", txtRetIVA.getText());
			eppCR.setConfigStringForParameter("facturacion","montoMinimoDevolucion", txtMtoMinDevolucion.getText());
			eppCR.setConfigStringForParameter("facturacion","simboloMonedaLocal", txtMonedaLocal.getText());
			eppCR.setConfigStringForParameter("facturacion","nombreBancoCheque", txtNombreBanco.getText());

			eppCR.setConfigStringForParameter("facturacion","timeOutPDA", txtTimeOutPDA.getText());
			eppCR.setConfigStringForParameter("facturacion","ipServidorPDA", txtIpServPDA.getText());
			eppCR.setConfigStringForParameter("facturacion","puertoServidorPDA", txtPuertoServPDA.getText());
			eppCR.setConfigStringForParameter("facturacion","puertoEscuchaCRPDA", txtPuertoEscuchaCRPDA.getText());
			
			if (rbImpFteChequeS.isSelected()) { 
				eppCR.setConfigStringForParameter("facturacion","imprimirFrenteCheque", "S");
			} else if (rbImpFteChequeN.isSelected()) {
				eppCR.setConfigStringForParameter("facturacion","imprimirFrenteCheque", "N");
			}
			if (rbImpDorsoChequeS.isSelected()) { 
				eppCR.setConfigStringForParameter("facturacion","imprimirDorsoCheque", "S");
			} else if (rbImpDorsoChequeN.isSelected()) {
				eppCR.setConfigStringForParameter("facturacion","imprimirDorsoCheque", "N");
			}
			if (rbCedulaObligS.isSelected()) { 
				eppCR.setConfigStringForParameter("facturacion","cedulaObligatoria", "S");
			} else if (rbCedulaObligN.isSelected()) {
				eppCR.setConfigStringForParameter("facturacion","cedulaObligatoria", "N");
			}
			if (rbNumConfObligS.isSelected()) { 
				eppCR.setConfigStringForParameter("facturacion","numeroConfirmacionObligatorio", "S");
			} else if (rbNumConfObligN.isSelected()) {
				eppCR.setConfigStringForParameter("facturacion","numeroConfirmacionObligatorio", "N");
			}
			eppCR.setConfigStringForParameter("facturacion", "numeroCaja", jTextField.getText());
			eppCR.setConfigStringForParameter("facturacion", "colorCombo", colorCombo.getText());
			if (rbContOrdinarioS.isSelected()) { 
				eppCR.setConfigStringForParameter("facturacion","permitirContribuyentesOrdinarios", "S");
			} else if (rbContOrdinarioN.isSelected()) {
				eppCR.setConfigStringForParameter("facturacion","permitirContribuyentesOrdinarios", "N");
			}
			if (jCheckBox.isSelected()) { 
				eppCR.setConfigStringForParameter("facturacion","entregaClienteRetira", "S");
			} else if (rbAutCierreN.isSelected()) {
				eppCR.setConfigStringForParameter("facturacion","entregaClienteRetira", "N");
			}
			if (jCheckBox1.isSelected()) { 
				eppCR.setConfigStringForParameter("facturacion","entregaDespacho", "S");
			} else if (rbAutCierreN.isSelected()) {
				eppCR.setConfigStringForParameter("facturacion","entregaDespacho", "N");
			}
			if (jCheckBox2.isSelected()) { 
				eppCR.setConfigStringForParameter("facturacion","entregaDomicilio", "S");
			} else if (rbAutCierreN.isSelected()) {
				eppCR.setConfigStringForParameter("facturacion","entregaDomicilio", "N");
			}
			if (validarClaveS.isSelected()) { 
				eppCR.setConfigStringForParameter("facturacion","validarClaveAlSalir", "S");
			} else if (validarClaveN.isSelected()) {
				eppCR.setConfigStringForParameter("facturacion","validarClaveAlSalir", "N");
			}
			btnSaveFact.setEnabled(false);
		} catch (NoSuchNodeException e1) {
			e1.printStackTrace();
		} catch (UnidentifiedPreferenceException e1) {
			e1.printStackTrace();
		}
		
	};
	
	private void cargarValoresFact() {
		if (eppCR.isNodeConfigured("facturacion")) {
			try {
				if (eppCR.getConfigStringForParameter("facturacion", "autorizarCierreCajero").equals("N")) {
					rbAutCierreN.setSelected(true);
				} else {
					rbAutCierreS.setSelected(true);
				};
				if (eppCR.getConfigStringForParameter("facturacion", "empaquesAcumulativos").equals("S")) {
					rbEmpAcumS.setSelected(true);
				} else {
					rbEmpAcumN.setSelected(true);
				};
				if (eppCR.getConfigStringForParameter("facturacion", "permitirRebajasAempleados").equals("S")) {
					rbRebajEmpS.setSelected(true);
				} else {
					rbRebajEmpN.setSelected(true);
				};
				if (eppCR.getConfigStringForParameter("facturacion", "requiereCliente").equals("N")) {
					rbReqCliN.setSelected(true);
				} else {
					rbReqCliS.setSelected(true);
				};
				txtLongCodInt.setText(eppCR.getConfigStringForParameter("facturacion", "longitudCodigoInterno"));
				txtNumCuenta.setText(eppCR.getConfigStringForParameter("facturacion", "numeroCuentaCheque"));
				
				txtTimeOutPDA.setText(eppCR.getConfigStringForParameter("facturacion", "timeOutPDA"));
				txtIpServPDA.setText(eppCR.getConfigStringForParameter("facturacion", "ipServidorPDA"));
				txtPuertoServPDA.setText(eppCR.getConfigStringForParameter("facturacion", "puertoServidorPDA"));
				txtPuertoEscuchaCRPDA.setText(eppCR.getConfigStringForParameter("facturacion", "puertoEscuchaCRPDA"));

				txtPorcMsgEmp.setText(eppCR.getConfigStringForParameter("facturacion", "porcentajeMensajeEmpaque"));
				cmbTipoCuenta.setSelectedItem(eppCR.getConfigStringForParameter("facturacion", "tipoCuentaCheque"));

				txtRetIVA.setText(eppCR.getConfigStringForParameter("facturacion", "porcentajeRetencionIVA"));
				txtMtoMinDevolucion.setText(eppCR.getConfigStringForParameter("facturacion", "montoMinimoDevolucion"));
				txtMonedaLocal.setText(eppCR.getConfigStringForParameter("facturacion", "simboloMonedaLocal"));
				txtNombreBanco.setText(eppCR.getConfigStringForParameter("facturacion", "nombreBancoCheque"));
				if (eppCR.getConfigStringForParameter("facturacion", "imprimirFrenteCheque").equals("N")) {
					rbImpFteChequeN.setSelected(true);
				} else {
					rbImpFteChequeS.setSelected(true);
				};
				if (eppCR.getConfigStringForParameter("facturacion", "imprimirDorsoCheque").equals("N")) {
					rbImpDorsoChequeN.setSelected(true);
				} else {
					rbImpDorsoChequeS.setSelected(true);
				};
				if (eppCR.getConfigStringForParameter("facturacion", "cedulaObligatoria").equals("N")) {
					rbCedulaObligN.setSelected(true);
				} else {
					rbCedulaObligS.setSelected(true);
				};
				if (eppCR.getConfigStringForParameter("facturacion", "numeroConfirmacionObligatorio").equals("N")) {
					rbNumConfObligN.setSelected(true);
				} else {
					rbNumConfObligS.setSelected(true);
				};
				jTextField.setText(eppCR.getConfigStringForParameter("facturacion", "numeroCaja"));
				colorCombo.setText(eppCR.getConfigStringForParameter("facturacion", "colorCombo"));
				if (eppCR.getConfigStringForParameter("facturacion", "permitirContribuyentesOrdinarios").equals("N")) {
					rbContOrdinarioN.setSelected(true);
				} else {
					rbContOrdinarioS.setSelected(true);
				};
				if (eppCR.getConfigStringForParameter("facturacion", "entregaClienteRetira").equals("N")) {
					jCheckBox.setSelected(false);
				} else {
					jCheckBox.setSelected(true);
				};
				if (eppCR.getConfigStringForParameter("facturacion", "entregaDespacho").equals("N")) {
					jCheckBox1.setSelected(false);
				} else {
					jCheckBox1.setSelected(true);
				};
				if (eppCR.getConfigStringForParameter("facturacion", "entregaDomicilio").equals("N")) {
					jCheckBox2.setSelected(false);
				} else {
					jCheckBox2.setSelected(true);
				};
				if (eppCR.getConfigStringForParameter("facturacion", "validarClaveAlSalir").equals("S")) {
					validarClaveS.setSelected(true);
				} else {
					validarClaveN.setSelected(true);
				};
			} catch (Exception e) {	
				e.printStackTrace();
			}
			
		} else {
			// Configuracion  por defecto
			/*  
			 * rbAutCierreS/N
			 * rbEmpAcumS/N
			 * txtLongCodInt
			 * txtNumCuenta
			 * cmbTipoCuenta
			 * rbRebajEmpS
			 * txtPorcMsgEmp
			 * rbReqCliS/N
			 */
			rbAutCierreS.setSelected(false);
			rbAutCierreN.setSelected(false);
			rbEmpAcumS.setSelected(false);
			rbEmpAcumN.setSelected(false);
			rbRebajEmpS.setSelected(false);
			rbRebajEmpN.setSelected(false);
			rbReqCliS.setSelected(false); 
			rbReqCliN.setSelected(false);
			txtLongCodInt.setText("");
			jTextField.setText("");
			txtNumCuenta.setText("");
			txtIpServPDA.setText("");
			txtTimeOutPDA.setText("");
			txtPuertoServPDA.setText("");
			txtPuertoEscuchaCRPDA.setText("");
			cmbTipoCuenta.setSelectedIndex(-1);
			txtPorcMsgEmp.setText("");
			
			rbImpFteChequeN.setSelected(false);
			rbImpDorsoChequeN.setSelected(false);
			rbCedulaObligN.setSelected(false);
			rbNumConfObligN.setSelected(false);
			txtRetIVA.setText("");
			txtMtoMinDevolucion.setText("");
			txtMonedaLocal.setText("");
			txtNombreBanco.setText("");
			jCheckBox.setSelected(false);
			jCheckBox1.setSelected(false);
			jCheckBox2.setSelected(false);
			validarClaveN.setSelected(false);
			validarClaveS.setSelected(true);
		}
	};
	
	private void guardarValoresSys() {
		if(!eppCR.isNodeConfigured("sistema")){
			/* La config no existe, la agregamos*/
			try {
				eppCR.addNewNodeToPreferencesTop("sistema");
			} catch (NodeAlreadyExistsException e) {
				e.printStackTrace();
			} catch (UnidentifiedPreferenceException e) {
				e.printStackTrace();
			}
		}
		try {
			eppCR.setConfigStringForParameter("sistema","PuertoSocket",txtPtoSocket.getText());
			eppCR.setConfigStringForParameter("sistema","colorBECO", txtColorBeco.getText());
			eppCR.setConfigStringForParameter("sistema","colorEPA", txtColorEPA.getText());
			if (cmbColorSplash.getSelectedItem().equals("BECO")) { 
				eppCR.setConfigStringForParameter("sistema","colorFondoSplash", "colorBECO");
			} else {
				eppCR.setConfigStringForParameter("sistema","colorFondoSplash", "colorEPA");
			}
			eppCR.setConfigStringForParameter("sistema","frecuenciaSincronizador", txtSyncFreq.getText());
			eppCR.setConfigStringForParameter("sistema","PuertoSocket", txtPtoSocket.getText());
			if (rbIgnScanS.isSelected()) { 
				eppCR.setConfigStringForParameter("sistema","ignorarEscaner", "S");
			} else if (rbIgnScanN.isSelected()) {
				eppCR.setConfigStringForParameter("sistema","ignorarEscaner", "N");
			}
			if (rbIgnPrnS.isSelected()) { 
				eppCR.setConfigStringForParameter("sistema","ignorarImpresora", "S");
			} else if (rbIgnPrnN.isSelected()) {
				eppCR.setConfigStringForParameter("sistema","ignorarImpresora", "N");
			}
			if (rbIgnVisorS.isSelected()) { 
				eppCR.setConfigStringForParameter("sistema","ignorarVisor", "S");
			} else if (rbIgnVisorN.isSelected()) {
				eppCR.setConfigStringForParameter("sistema","ignorarVisor", "N");
			}
			eppCR.setConfigStringForParameter("sistema","imagenSplash", txtImgSplash.getText());
			if (rbFiscalPrnS.isSelected()) { 
				eppCR.setConfigStringForParameter("sistema","impresoraFiscal", "S");
			} else if (rbFiscalPrnN.isSelected()) {
				eppCR.setConfigStringForParameter("sistema","impresoraFiscal", "N");
			}
			eppCR.setConfigStringForParameter("sistema","logo", txtLogo.getText());
			if (rbMsgBlancoS.isSelected()) { 
				eppCR.setConfigStringForParameter("sistema","mensajeColorBlanco", "S");
			} else if (rbMsgBlancoN.isSelected()) {
				eppCR.setConfigStringForParameter("sistema","mensajeColorBlanco", "N");
			}
			eppCR.setConfigStringForParameter("sistema","mensajeSplash", txtMsgSplash.getText());
			eppCR.setConfigStringForParameter("sistema","pathtemporal", txtPathTmp.getText());
			if (rbFacturarS.isSelected()) { 
				eppCR.setConfigStringForParameter("sistema","puedeFacturar", "S");
			} else if (rbFacturarN.isSelected()) {
				eppCR.setConfigStringForParameter("sistema","puedeFacturar", "N");
			}
			eppCR.setConfigStringForParameter("sistema","urlPrincipal", txtUrlPrinc.getText());
			eppCR.setConfigStringForParameter("sistema","urlVerificador", txtUrlVerif.getText());
			eppCR.setConfigStringForParameter("sistema","puertoServ", txtPtoServ.getText());
			eppCR.setConfigStringForParameter("sistema","puertoServCentral", txtPtoServCent.getText());
			eppCR.setConfigStringForParameter("sistema","puertoSyncCommander", txtPtoSync.getText());
			if (txtPtoSync.getText().equals("")) {
				eppCR.setConfigStringForParameter("sistema","servicioSyncCommander", "N");
			} else {
				eppCR.setConfigStringForParameter("sistema","servicioSyncCommander", "S");
			}
			eppCR.setConfigStringForParameter("sistema","localizacionLenguaje", "es");
			eppCR.setConfigStringForParameter("sistema","localizacionPais", "VE");
			eppCR.setConfigStringForParameter("sistema","estiloFactura", "VE");
			if (cmbSaltoVisor.getSelectedIndex()==0) {
				eppCR.setConfigStringForParameter("sistema","saltolineavisor", "");
			} else {
				eppCR.setConfigStringForParameter("sistema","saltolineavisor", "\n\r");
			}

			eppCR.setConfigStringForParameter("sistema","longitudlineavisor", txtLongitudVisor.getText());
			if (rbVerifLinS.isSelected()) { 
				eppCR.setConfigStringForParameter("sistema","verificarLinea", "S");
			} else if (rbVerifLinN.isSelected()) {
				eppCR.setConfigStringForParameter("sistema","verificarLinea", "N");
			}

			if (rbActCajaTempS.isSelected()) { 
				eppCR.setConfigStringForParameter("sistema","actCajaTemporizada", "S");
			} else if (rbActCajaTempN.isSelected()) {
				eppCR.setConfigStringForParameter("sistema","actCajaTemporizada", "N");
			}
			if (rbApagarSistS.isSelected()) { 
				eppCR.setConfigStringForParameter("sistema","apagarsistema", "S");
			} else if (rbApagarSistN.isSelected()) {
				eppCR.setConfigStringForParameter("sistema","apagarsistema", "N");
			}
			if (rbCambioFechaS.isSelected()) {
				eppCR.setConfigStringForParameter("sistema","cambiofecha", "S");
			} else if (rbCambioFechaN.isSelected()) {
				eppCR.setConfigStringForParameter("sistema","cambiofecha", "N");
			}
			if (rbReiniciarSistS.isSelected()) {
				eppCR.setConfigStringForParameter("sistema","reiniciarsistema", "S");
			} else if (rbReiniciarSistN.isSelected()) {
				eppCR.setConfigStringForParameter("sistema","reiniciarsistema", "N");
			}
			if (rbSincAfiliadoS.isSelected()) {
				eppCR.setConfigStringForParameter("sistema","sincronizarAfiliados", "S");
			} else if (rbSincAfiliadoN.isSelected()) {
				eppCR.setConfigStringForParameter("sistema","sincronizarAfiliados", "N");
			}
			eppCR.setConfigStringForParameter("sistema","longitudApellidoCliente", txtLongitudApellido.getText());
			eppCR.setConfigStringForParameter("sistema","longitudNombreCliente", txtLongitudNombre.getText());
			if (rbActVerificadorS.isSelected()) {
				eppCR.setConfigStringForParameter("sistema","activarVerificador", "S");
			} else if (rbActVerificadorN.isSelected()) {
				eppCR.setConfigStringForParameter("sistema","activarVerificador", "N");
			}
			if (rbEscanerRapidoS.isSelected()) {
				eppCR.setConfigStringForParameter("sistema","escanerRapido", "S");
			} else if (rbEscanerRapidoN.isSelected()) {
				eppCR.setConfigStringForParameter("sistema","escanerRapido", "N");
			}
			if (manejoGaveta.getSelectedIndex()==0) {
				eppCR.setConfigStringForParameter("sistema","manejoGaveta", "impresora");
			} else {
				eppCR.setConfigStringForParameter("sistema","manejoGaveta", "visor");
			}
			if (rbMantenerFocoSi.isSelected()) {
				eppCR.setConfigStringForParameter("sistema","mantenerFoco", "S");
			} else if (rbMantenerFocoNo.isSelected()) {
				eppCR.setConfigStringForParameter("sistema","mantenerFoco", "N");
			}
			btnSaveSys.setEnabled(false);
		} catch (NoSuchNodeException e1) {
			e1.printStackTrace();
		} catch (UnidentifiedPreferenceException e1) {
			e1.printStackTrace();
		}
		
	};
	
	private void cargarValoresSys() {
		if (eppCR.isNodeConfigured("sistema")) {
			try {
				txtPathTmp.setText(eppCR.getConfigStringForParameter("sistema", "pathtemporal"));
				txtUrlPrinc.setText(eppCR.getConfigStringForParameter("sistema", "urlPrincipal"));
				txtUrlVerif.setText(eppCR.getConfigStringForParameter("sistema", "urlVerificador"));
				txtPtoSocket.setText(eppCR.getConfigStringForParameter("sistema", "PuertoSocket"));
				txtSyncFreq.setText(eppCR.getConfigStringForParameter("sistema", "frecuenciaSincronizador"));
				txtLogo.setText(eppCR.getConfigStringForParameter("sistema", "logo"));
				txtColorBeco.setText(eppCR.getConfigStringForParameter("sistema", "colorBECO"));
				txtColorEPA.setText(eppCR.getConfigStringForParameter("sistema", "colorEPA"));
				txtPtoServ.setText(eppCR.getConfigStringForParameter("sistema", "puertoServ"));
				txtPtoServCent.setText(eppCR.getConfigStringForParameter("sistema", "puertoServCentral"));
				txtPtoSync.setText(eppCR.getConfigStringForParameter("sistema", "puertoSyncCommander"));
				if (eppCR.getConfigStringForParameter("sistema", "saltolineavisor").equals("")) {
					cmbSaltoVisor.setSelectedIndex(0);
				} else {
					cmbSaltoVisor.setSelectedIndex(1);
				}
				txtLongitudVisor.setText(eppCR.getConfigStringForParameter("sistema", "longitudlineavisor"));
				if (eppCR.getConfigStringForParameter("sistema", "colorFondoSplash").equals("colorBECO")) {
					cmbColorSplash.setSelectedItem("BECO");
				} else {
					cmbColorSplash.setSelectedItem("EPA");
				}
				txtImgSplash.setText(eppCR.getConfigStringForParameter("sistema", "imagenSplash"));
				txtPtoSocket.setText(eppCR.getConfigStringForParameter("sistema", "PuertoSocket"));
				txtMsgSplash.setText(eppCR.getConfigStringForParameter("sistema", "mensajeSplash"));
				if (eppCR.getConfigStringForParameter("sistema", "mensajeColorBlanco").equals("N")) {
					rbMsgBlancoN.setSelected(true);
				} else {
					rbMsgBlancoS.setSelected(true);
				};
				if (eppCR.getConfigStringForParameter("sistema", "ignorarEscaner").equals("N")) {
					rbIgnScanN.setSelected(true);
				} else {
					rbIgnScanS.setSelected(true);
				};
				if (eppCR.getConfigStringForParameter("sistema", "ignorarImpresora").equals("N")) {
					rbIgnPrnN.setSelected(true);
				} else {
					rbIgnPrnS.setSelected(true);
				};
				if (eppCR.getConfigStringForParameter("sistema", "ignorarVisor").equals("N")) {
					rbIgnVisorN.setSelected(true);
				} else {
					rbIgnVisorS.setSelected(true);
				};
				if (eppCR.getConfigStringForParameter("sistema", "puedeFacturar").equals("N")) {
					rbFacturarN.setSelected(true);
				} else {
					rbFacturarS.setSelected(true);
				};
				if (eppCR.getConfigStringForParameter("sistema", "impresoraFiscal").equals("S")) {
					rbFiscalPrnS.setSelected(true);
				} else {
					rbFiscalPrnN.setSelected(true);
				};
				if (eppCR.getConfigStringForParameter("sistema", "verificarLinea").equals("N")) {
					rbVerifLinN.setSelected(true);
				} else {
					rbVerifLinS.setSelected(true);
				};

				if (eppCR.getConfigStringForParameter("sistema", "apagarsistema").equals("N")) {
					rbApagarSistN.setSelected(true);
				} else {
					rbApagarSistS.setSelected(true);
				};
				if (eppCR.getConfigStringForParameter("sistema", "reiniciarsistema").equals("N")) {
					rbReiniciarSistN.setSelected(true);
				} else {
					rbReiniciarSistS.setSelected(true);
				};
				if (eppCR.getConfigStringForParameter("sistema", "servicioSyncCommander").equals("N")) {
					rbSyncCommanderN.setSelected(true);
				} else {
					rbSyncCommanderS.setSelected(true);
				};
				if (eppCR.getConfigStringForParameter("sistema", "cambiofecha").equals("N")) {
					rbCambioFechaN.setSelected(true);
				} else {
					rbCambioFechaS.setSelected(true);
				};
				if (eppCR.getConfigStringForParameter("sistema", "sincronizarAfiliados").equals("N")) {
					rbSincAfiliadoN.setSelected(true);
				} else {
					rbSincAfiliadoS.setSelected(true);
				};
				if (eppCR.getConfigStringForParameter("sistema", "actCajaTemporizada").equals("N")) {
					rbActCajaTempN.setSelected(true);
				} else {
					rbActCajaTempS.setSelected(true);
				};
				txtLongitudNombre.setText(eppCR.getConfigStringForParameter("sistema", "longitudNombreCliente"));
				txtLongitudApellido.setText(eppCR.getConfigStringForParameter("sistema", "longitudApellidoCliente"));
				if (eppCR.getConfigStringForParameter("sistema", "activarVerificador").equals("N")) {
					rbActVerificadorN.setSelected(true);
				} else {
					rbActVerificadorS.setSelected(true);
				};
				if (eppCR.getConfigStringForParameter("sistema", "escanerRapido").equals("N")) {
					rbEscanerRapidoN.setSelected(true);
				} else {
					rbEscanerRapidoS.setSelected(true);
				};
				if (eppCR.getConfigStringForParameter("sistema", "manejoGaveta").equals("impresora")) {
					manejoGaveta.setSelectedIndex(0);
				} else {
					manejoGaveta.setSelectedIndex(1);
				}
				if (eppCR.getConfigStringForParameter("sistema", "mantenerFoco").equals("N")) {
					rbMantenerFocoNo.setSelected(true);
				} else {
					rbMantenerFocoSi.setSelected(true);
				};
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			// Configuracion  por defecto
			/*  
			 * txtPathTmp
			 * txtUrlPrinc
			 * txtUrlVerif
			 * txtPtoSocket
			 * txtFuncionalidad
			 * txtSyncFreq
			 * txtLogo
			 * txtColorBeco
			 * txtColorEPA
			 * cmbColorSplash
			 * txtImgSplash
			 * txtMsgSplash
			 * txtMsgBlanco
			 * rbIgnScanS/N
			 * rbIgnPrnS/N
			 * rbIgnVisorS/N
			 * rbFacturarS/N
			 * rbFiscalPrnS/N
			 * rbVerifLinS/N
			 * 
			 */
			txtPathTmp.setText("");
			txtUrlPrinc.setText("");
			txtUrlVerif.setText("");
			txtPtoSocket.setText("");
			txtSyncFreq.setText("");
			txtLogo.setText("");
			txtColorBeco.setText("");
			txtColorEPA.setText("");
			txtPtoServ.setText("");
			txtPtoServCent.setText("");
			txtPtoSync.setText("");
			cmbColorSplash.setSelectedIndex(-1);
			txtImgSplash.setText("");
			txtMsgSplash.setText("");
			rbMsgBlancoS.setSelected(false);
			rbMsgBlancoN.setSelected(false);
			rbIgnScanS.setSelected(false);
			rbIgnScanN.setSelected(false);
			rbIgnPrnS.setSelected(false);
			rbIgnPrnN.setSelected(false);
			rbIgnVisorS.setSelected(false);
 			rbIgnVisorN.setSelected(false);
 			rbFacturarS.setSelected(false);
			rbFacturarN.setSelected(false);
			rbFiscalPrnS.setSelected(false);
			rbFiscalPrnN.setSelected(false);
			rbVerifLinS.setSelected(false);
			rbVerifLinN.setSelected(false);
		}		
	};
	
	/**
	 * This method initializes jPanel80
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel80() {
		if(jPanel80 == null) {
			jPanel80 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout21 = new java.awt.FlowLayout();
			layFlowLayout21.setVgap(2);
			layFlowLayout21.setAlignment(java.awt.FlowLayout.LEFT);
			jPanel80.setLayout(layFlowLayout21);
			jPanel80.add(getJLabel43(), null);
			jPanel80.add(getRbAutCierreS(), null);
			jPanel80.add(getRbAutCierreN(), null);
			bgAutCierre.add(getRbAutCierreS());
			bgAutCierre.add(getRbAutCierreN());
			jPanel80.setBackground(new java.awt.Color(242,242,238));
			jPanel80.setPreferredSize(new java.awt.Dimension(500,20));
			jPanel80.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
		}
		return jPanel80;
	}
	/**
	 * This method initializes jPanel81
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel81() {
		if(jPanel81 == null) {
			jPanel81 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout15 = new java.awt.FlowLayout();
			layFlowLayout15.setVgap(2);
			layFlowLayout15.setAlignment(java.awt.FlowLayout.LEFT);
			jPanel81.setLayout(layFlowLayout15);
			jPanel81.add(getJLabel44(), null);
			jPanel81.add(getTxtNumCuenta(), null);
			jPanel81.setBackground(new java.awt.Color(242,242,238));
			jPanel81.setPreferredSize(new java.awt.Dimension(500,20));
			jPanel81.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
		}
		return jPanel81;
	}
	/**
	 * This method initializes jPanel82
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel82() {
		if(jPanel82 == null) {
			jPanel82 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout31 = new java.awt.FlowLayout();
			layFlowLayout31.setVgap(2);
			layFlowLayout31.setAlignment(java.awt.FlowLayout.LEFT);
			jPanel82.setLayout(layFlowLayout31);
			jPanel82.add(getJLabel45(), null);
			jPanel82.add(getTxtPorcMsgEmp(), null);
			jPanel82.setBackground(new java.awt.Color(242,242,238));
			jPanel82.setPreferredSize(new java.awt.Dimension(500,20));
			jPanel82.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
		}
		return jPanel82;
	}
	/**
	 * This method initializes jPanel83
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel83() {
		if(jPanel83 == null) {
			jPanel83 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout41 = new java.awt.FlowLayout();
			layFlowLayout41.setVgap(2);
			layFlowLayout41.setAlignment(java.awt.FlowLayout.LEFT);
			jPanel83.setLayout(layFlowLayout41);
			jPanel83.add(getJLabel46(), null);
			jPanel83.add(getCmbTipoCuenta(), null);
			jPanel83.setBackground(new java.awt.Color(242,242,238));
			jPanel83.setPreferredSize(new java.awt.Dimension(500,20));
			jPanel83.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
		}
		return jPanel83;
	}
	/**
	 * This method initializes jLabel43
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel43() {
		if(jLabel43 == null) {
			jLabel43 = new javax.swing.JLabel();
			jLabel43.setText("Autorizar cierre a cajero:");
			jLabel43.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			jLabel43.setPreferredSize(new java.awt.Dimension(180,14));
		}
		return jLabel43;
	}
	/**
	 * This method initializes jLabel44
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel44() {
		if(jLabel44 == null) {
			jLabel44 = new javax.swing.JLabel();
			jLabel44.setText("Numero de cuenta para cheques:");
			jLabel44.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			jLabel44.setPreferredSize(new java.awt.Dimension(180,14));
		}
		return jLabel44;
	}
	/**
	 * This method initializes jLabel45
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel45() {
		if(jLabel45 == null) {
			jLabel45 = new javax.swing.JLabel();
			jLabel45.setText("Porcentaje mensaje empaque:");
			jLabel45.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			jLabel45.setPreferredSize(new java.awt.Dimension(180,14));
		}
		return jLabel45;
	}
	/**
	 * This method initializes jLabel46
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel46() {
		if(jLabel46 == null) {
			jLabel46 = new javax.swing.JLabel();
			jLabel46.setText("Tipo de cuenta para cheques:");
			jLabel46.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			jLabel46.setPreferredSize(new java.awt.Dimension(180,14));
		}
		return jLabel46;
	}
	/**
	 * This method initializes txtNumCuenta
	 * 
	 * @return javax.swing.JTextField
	 */
	private javax.swing.JTextField getTxtNumCuenta() {
		if(txtNumCuenta == null) {
			txtNumCuenta = new javax.swing.JTextField();
			txtNumCuenta.setPreferredSize(new java.awt.Dimension(100,17));
			txtNumCuenta.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
		}
		return txtNumCuenta;
	}
	/**
	 * This method initializes txtTimeOutPDA
	 * 
	 * @return javax.swing.JTextField
	 */
	private javax.swing.JTextField getTxtTimeOutPDA() {
		if(txtTimeOutPDA == null) {
			txtTimeOutPDA = new javax.swing.JTextField();
			txtTimeOutPDA.setPreferredSize(new java.awt.Dimension(100,17));
			txtTimeOutPDA.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
		}
		return txtTimeOutPDA;
	}
	/**
	 * This method initializes txtIpServPDA
	 * 
	 * @return javax.swing.JTextField
	 */
	private javax.swing.JTextField getTxtIpServPDA() {
		if(txtIpServPDA == null) {
			txtIpServPDA = new javax.swing.JTextField();
			txtIpServPDA.setPreferredSize(new java.awt.Dimension(100,17));
			txtIpServPDA.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
		}
		return txtIpServPDA;
	}
	/**
	 * This method initializes txtPuertoServPDA
	 * 
	 * @return javax.swing.JTextField
	 */
	private javax.swing.JTextField getTxtPuertoServPDA() {
		if(txtPuertoServPDA == null) {
			txtPuertoServPDA = new javax.swing.JTextField();
			txtPuertoServPDA.setPreferredSize(new java.awt.Dimension(100,17));
			txtPuertoServPDA.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
		}
		return txtPuertoServPDA;
	}
	/**
	 * This method initializes txtPuertoEscuchaCRPDA
	 * 
	 * @return javax.swing.JTextField
	 */
	private javax.swing.JTextField getTxtPuertoEscuchaCRPDA() {
		if(txtPuertoEscuchaCRPDA == null) {
			txtPuertoEscuchaCRPDA = new javax.swing.JTextField();
			txtPuertoEscuchaCRPDA.setPreferredSize(new java.awt.Dimension(100,17));
			txtPuertoEscuchaCRPDA.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
		}
		return txtPuertoEscuchaCRPDA;
	}
	/**
	 * This method initializes txtPorcMsgEmp
	 * 
	 * @return javax.swing.JTextField
	 */
	private javax.swing.JTextField getTxtPorcMsgEmp() {
		if(txtPorcMsgEmp == null) {
			txtPorcMsgEmp = new javax.swing.JTextField();
			txtPorcMsgEmp.setPreferredSize(new java.awt.Dimension(100,17));
			txtPorcMsgEmp.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
		}
		return txtPorcMsgEmp;
	}
	/**
	 * This method initializes txtRetIVA
	 * 
	 * @return javax.swing.JTextField
	 */
	private javax.swing.JTextField getTxtRetIVA() {
		if(txtRetIVA == null) {
			txtRetIVA = new javax.swing.JTextField();
			txtRetIVA.setPreferredSize(new java.awt.Dimension(100,17));
			txtRetIVA.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
		}
		return txtRetIVA;
	}
	/**
	 * This method initializes txtMonedaLocal
	 * 
	 * @return javax.swing.JTextField
	 */
	private javax.swing.JTextField getTxtMonedaLocal() {
		if(txtMonedaLocal == null) {
			txtMonedaLocal = new javax.swing.JTextField();
			txtMonedaLocal.setPreferredSize(new java.awt.Dimension(100,17));
			txtMonedaLocal.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
		}
		return txtMonedaLocal;
	}
	/**
	 * This method initializes txtNombreBanco
	 * 
	 * @return javax.swing.JTextField
	 */
	private javax.swing.JTextField getTxtNombreBanco() {
		if(txtNombreBanco == null) {
			txtNombreBanco = new javax.swing.JTextField();
			txtNombreBanco.setPreferredSize(new java.awt.Dimension(100,17));
			txtNombreBanco.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
		}
		return txtNombreBanco;
	}
	/**
	 * This method initializes cmbTipoCuenta
	 * 
	 * @return javax.swing.JComboBox
	 */
	private javax.swing.JComboBox getCmbTipoCuenta() {
		if(cmbTipoCuenta == null) {
			cmbTipoCuenta = new javax.swing.JComboBox();
			cmbTipoCuenta.setPreferredSize(new java.awt.Dimension(100,17));
			cmbTipoCuenta.setBackground(new java.awt.Color(242,242,238));
			cmbTipoCuenta.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 10));
			cmbTipoCuenta.addItem("Ahorro");
			cmbTipoCuenta.addItem("Corriente");			
			cmbTipoCuenta.addItemListener(new java.awt.event.ItemListener() { 
				public void itemStateChanged(java.awt.event.ItemEvent e) {    
					cambioFact();
				}
			});

		}
		return cmbTipoCuenta;
	}
	/**
	 * This method initializes rbAutCierreS
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private javax.swing.JRadioButton getRbAutCierreS() {
		if(rbAutCierreS == null) {
			rbAutCierreS = new javax.swing.JRadioButton();
			rbAutCierreS.setBackground(new java.awt.Color(242,242,238));
			rbAutCierreS.setPreferredSize(new java.awt.Dimension(40,17));
			rbAutCierreS.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 10));
			rbAutCierreS.setText("Si");
		}
		return rbAutCierreS;
	}
	/**
	 * This method initializes rbAutCierreN
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private javax.swing.JRadioButton getRbAutCierreN() {
		if(rbAutCierreN == null) {
			rbAutCierreN = new javax.swing.JRadioButton();
			rbAutCierreN.setBackground(new java.awt.Color(242,242,238));
			rbAutCierreN.setPreferredSize(new java.awt.Dimension(40,17));
			rbAutCierreN.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 10));
			rbAutCierreN.setText("No");
		}
		return rbAutCierreN;
	}
	/**
	 * This method initializes jPanel84
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel84() {
		if(jPanel84 == null) {
			jPanel84 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout16 = new java.awt.FlowLayout();
			layFlowLayout16.setAlignment(java.awt.FlowLayout.LEFT);
			layFlowLayout16.setVgap(2);
			jPanel84.setLayout(layFlowLayout16);
			jPanel84.add(getJLabel47(), null);
			jPanel84.add(getTxtCargoServ(), null);
			jPanel84.setBackground(new java.awt.Color(242,242,238));
			jPanel84.setPreferredSize(new java.awt.Dimension(500,20));
			jPanel84.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
		}
		return jPanel84;
	}
	/**
	 * This method initializes jPanel85
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel85() {
		if(jPanel85 == null) {
			jPanel85 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout22 = new java.awt.FlowLayout();
			layFlowLayout22.setAlignment(java.awt.FlowLayout.LEFT);
			layFlowLayout22.setVgap(2);
			jPanel85.setLayout(layFlowLayout22);
			jPanel85.add(getJLabel48(), null);
			jPanel85.add(getRbRecalcSaldoS(), null);
			jPanel85.add(getRbRecalcSaldoN(), null);
			bgRecalcSaldo.add(getRbRecalcSaldoS());
			bgRecalcSaldo.add(getRbRecalcSaldoN());
			jPanel85.setBackground(new java.awt.Color(242,242,238));
			jPanel85.setPreferredSize(new java.awt.Dimension(500,20));
			jPanel85.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
		}
		return jPanel85;
	}
	/**
	 * This method initializes jLabel47
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel47() {
		if(jLabel47 == null) {
			jLabel47 = new javax.swing.JLabel();
			jLabel47.setText("Cargo por Servicio:");
			jLabel47.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			jLabel47.setPreferredSize(new java.awt.Dimension(180,14));
		}
		return jLabel47;
	}
	/**
	 * This method initializes jLabel48
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel48() {
		if(jLabel48 == null) {
			jLabel48 = new javax.swing.JLabel();
			jLabel48.setText("Recalcular Saldo:");
			jLabel48.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			jLabel48.setPreferredSize(new java.awt.Dimension(180,14));
		}
		return jLabel48;
	}
	/**
	 * This method initializes txtCargoServ
	 * 
	 * @return javax.swing.JTextField
	 */
	private javax.swing.JTextField getTxtCargoServ() {
		if(txtCargoServ == null) {
			txtCargoServ = new javax.swing.JTextField();
			txtCargoServ.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			txtCargoServ.setPreferredSize(new java.awt.Dimension(50,17));
		}
		return txtCargoServ;
	}
	/**
	 * This method initializes rbRecalcSaldoS
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private javax.swing.JRadioButton getRbRecalcSaldoS() {
		if(rbRecalcSaldoS == null) {
			rbRecalcSaldoS = new javax.swing.JRadioButton();
			rbRecalcSaldoS.setBackground(new java.awt.Color(242,242,238));
			rbRecalcSaldoS.setPreferredSize(new java.awt.Dimension(40,17));
			rbRecalcSaldoS.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 10));
			rbRecalcSaldoS.setText("Si");
		}
		return rbRecalcSaldoS;
	}
	/**
	 * This method initializes rbRecalcSaldoN
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private javax.swing.JRadioButton getRbRecalcSaldoN() {
		if(rbRecalcSaldoN == null) {
			rbRecalcSaldoN = new javax.swing.JRadioButton();
			rbRecalcSaldoN.setBackground(new java.awt.Color(242,242,238));
			rbRecalcSaldoN.setPreferredSize(new java.awt.Dimension(40,17));
			rbRecalcSaldoN.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 10));
			rbRecalcSaldoN.setText("No");
		}
		return rbRecalcSaldoN;
	}
	/**
	 * This method initializes jPanel86
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel86() {
		if(jPanel86 == null) {
			jLabel29 = new JLabel();
			jPanel86 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout17 = new java.awt.FlowLayout();
			layFlowLayout17.setAlignment(java.awt.FlowLayout.LEFT);
			layFlowLayout17.setVgap(2);
			jPanel86.setLayout(layFlowLayout17);
			jPanel86.add(getJLabel55(), null);
			jPanel86.add(getRbFacturarS(), null);
			jPanel86.add(getRbFacturarN(), null);
			jLabel29.setText("Impresora Fiscal: ");
			jLabel29.setPreferredSize(new java.awt.Dimension(102,14));
			jLabel29.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			jPanel86.add(jLabel29, null);
			jPanel86.add(getRbFiscalPrnS(), null);
			jPanel86.add(getRbFiscalPrnN(), null);
			bgFacturar.add(getRbFacturarS());
			bgFacturar.add(getRbFacturarN());
			jPanel86.setBackground(new java.awt.Color(242,242,238));
			jPanel86.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			jPanel86.setPreferredSize(new java.awt.Dimension(500,20));
		}
		return jPanel86;
	}
	/**
	 * This method initializes jLabel49
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel49() {
		if(jLabel49 == null) {
			jLabel49 = new javax.swing.JLabel();
			jLabel49.setText("Ignorar Escaner:");
			jLabel49.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			jLabel49.setPreferredSize(new java.awt.Dimension(90,14));
		}
		return jLabel49;
	}
	/**
	 * This method initializes rbIgnScanS
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private javax.swing.JRadioButton getRbIgnScanS() {
		if(rbIgnScanS == null) {
			rbIgnScanS = new javax.swing.JRadioButton();
			rbIgnScanS.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 10));
			rbIgnScanS.setBackground(new java.awt.Color(242,242,238));
			rbIgnScanS.setPreferredSize(new java.awt.Dimension(35,17));
			rbIgnScanS.setText("Si");
		}
		return rbIgnScanS;
	}
	/**
	 * This method initializes rbIgnScanN
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private javax.swing.JRadioButton getRbIgnScanN() {
		if(rbIgnScanN == null) {
			rbIgnScanN = new javax.swing.JRadioButton();
			rbIgnScanN.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 10));
			rbIgnScanN.setBackground(new java.awt.Color(242,242,238));
			rbIgnScanN.setPreferredSize(new java.awt.Dimension(38,17));
			rbIgnScanN.setText("No");
		}
		return rbIgnScanN;
	}
	/**
	 * This method initializes jLabel50
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel50() {
		if(jLabel50 == null) {
			jLabel50 = new javax.swing.JLabel();
			jLabel50.setText("Ignorar Printer:");
			jLabel50.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			jLabel50.setPreferredSize(new java.awt.Dimension(70,14));
		}
		return jLabel50;
	}
	/**
	 * This method initializes rbIgnPrnS
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private javax.swing.JRadioButton getRbIgnPrnS() {
		if(rbIgnPrnS == null) {
			rbIgnPrnS = new javax.swing.JRadioButton();
			rbIgnPrnS.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 10));
			rbIgnPrnS.setBackground(new java.awt.Color(242,242,238));
			rbIgnPrnS.setPreferredSize(new java.awt.Dimension(35,17));
			rbIgnPrnS.setText("Si");
		}
		return rbIgnPrnS;
	}
	/**
	 * This method initializes rbIgnPrnN
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private javax.swing.JRadioButton getRbIgnPrnN() {
		if(rbIgnPrnN == null) {
			rbIgnPrnN = new javax.swing.JRadioButton();
			rbIgnPrnN.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 10));
			rbIgnPrnN.setBackground(new java.awt.Color(242,242,238));
			rbIgnPrnN.setPreferredSize(new java.awt.Dimension(38,17));
			rbIgnPrnN.setText("No");
		}
		return rbIgnPrnN;
	}
	/**
	 * This method initializes jLabel51
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel51() {
		if(jLabel51 == null) {
			jLabel51 = new javax.swing.JLabel();
			jLabel51.setText("Ignorar Visor:");
			jLabel51.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			jLabel51.setPreferredSize(new java.awt.Dimension(65,14));
		}
		return jLabel51;
	}
	/**
	 * This method initializes rbIgnVisorS
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private javax.swing.JRadioButton getRbIgnVisorS() {
		if(rbIgnVisorS == null) {
			rbIgnVisorS = new javax.swing.JRadioButton();
			rbIgnVisorS.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 10));
			rbIgnVisorS.setBackground(new java.awt.Color(242,242,238));
			rbIgnVisorS.setPreferredSize(new java.awt.Dimension(35,17));
			rbIgnVisorS.setText("Si");
		}
		return rbIgnVisorS;
	}
	/**
	 * This method initializes rbIgnVisorN
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private javax.swing.JRadioButton getRbIgnVisorN() {
		if(rbIgnVisorN == null) {
			rbIgnVisorN = new javax.swing.JRadioButton();
			rbIgnVisorN.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 10));
			rbIgnVisorN.setBackground(new java.awt.Color(242,242,238));
			rbIgnVisorN.setPreferredSize(new java.awt.Dimension(38,17));
			rbIgnVisorN.setText("No");
		}
		return rbIgnVisorN;
	}

	/**
	 * This method initializes jLabel53
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel53() {
		if(jLabel53 == null) {
			jLabel53 = new javax.swing.JLabel();
			jLabel53.setText("Frecuencia de Sincronizador:");
			jLabel53.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			jLabel53.setPreferredSize(new java.awt.Dimension(150,14));
		}
		return jLabel53;
	}
	/**
	 * This method initializes txtSyncFreq
	 * 
	 * @return javax.swing.JTextField
	 */
	private javax.swing.JTextField getTxtSyncFreq() {
		if(txtSyncFreq == null) {
			txtSyncFreq = new javax.swing.JTextField();
			txtSyncFreq.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			txtSyncFreq.setPreferredSize(new java.awt.Dimension(100,17));
		}
		return txtSyncFreq;
	}
	/**
	 * This method initializes jLabel54
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel54() {
		if(jLabel54 == null) {
			jLabel54 = new javax.swing.JLabel();
			jLabel54.setText("Msj Color Blanco:");
			jLabel54.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			jLabel54.setPreferredSize(new java.awt.Dimension(96,14));
		}
		return jLabel54;
	}
	/**
	 * This method initializes jLabel55
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel55() {
		if(jLabel55 == null) {
			jLabel55 = new javax.swing.JLabel();
			jLabel55.setText("Puede Facturar:");
			jLabel55.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			jLabel55.setPreferredSize(new java.awt.Dimension(90,14));
		}
		return jLabel55;
	}
	/**
	 * This method initializes rbFacturarS
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private javax.swing.JRadioButton getRbFacturarS() {
		if(rbFacturarS == null) {
			rbFacturarS = new javax.swing.JRadioButton();
			rbFacturarS.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 10));
			rbFacturarS.setBackground(new java.awt.Color(242,242,238));
			rbFacturarS.setPreferredSize(new java.awt.Dimension(40,17));
			rbFacturarS.setText("Si");
		}
		return rbFacturarS;
	}
	/**
	 * This method initializes rbFacturarN
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private javax.swing.JRadioButton getRbFacturarN() {
		if(rbFacturarN == null) {
			rbFacturarN = new javax.swing.JRadioButton();
			rbFacturarN.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 10));
			rbFacturarN.setBackground(new java.awt.Color(242,242,238));
			rbFacturarN.setPreferredSize(new java.awt.Dimension(40,17));
			rbFacturarN.setText("No");
		}
		return rbFacturarN;
	}
	/**
	 * This method initializes rbMsgBlancoS
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private javax.swing.JRadioButton getRbMsgBlancoS() {
		if(rbMsgBlancoS == null) {
			rbMsgBlancoS = new javax.swing.JRadioButton();
			rbMsgBlancoS.setBackground(new java.awt.Color(242,242,238));
			rbMsgBlancoS.setPreferredSize(new java.awt.Dimension(40,17));
			rbMsgBlancoS.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 10));
			rbMsgBlancoS.setText("Si");
		}
		return rbMsgBlancoS;
	}
	/**
	 * This method initializes rbMsgBlancoN
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private javax.swing.JRadioButton getRbMsgBlancoN() {
		if(rbMsgBlancoN == null) {
			rbMsgBlancoN = new javax.swing.JRadioButton();
			rbMsgBlancoN.setBackground(new java.awt.Color(242,242,238));
			rbMsgBlancoN.setPreferredSize(new java.awt.Dimension(40,17));
			rbMsgBlancoN.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 10));
			rbMsgBlancoN.setText("No");
		}
		return rbMsgBlancoN;
	}
	/**
	 * This method initializes jPanel87
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel87() {
		if(jPanel87 == null) {
			jPanel87 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout18 = new java.awt.FlowLayout();
			layFlowLayout18.setAlignment(java.awt.FlowLayout.LEFT);
			layFlowLayout18.setVgap(2);
			jPanel87.setLayout(layFlowLayout18);
			jPanel87.add(getJLabel56(), null);
			jPanel87.add(getTxtEsquema(), null);
			jPanel87.setPreferredSize(new java.awt.Dimension(500,20));
			jPanel87.setBackground(new java.awt.Color(242,242,238));
		}
		return jPanel87;
	}
	/**
	 * This method initializes jLabel56
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel56() {
		if(jLabel56 == null) {
			jLabel56 = new javax.swing.JLabel();
			jLabel56.setText("Esquema");
			jLabel56.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			jLabel56.setPreferredSize(new java.awt.Dimension(180,14));
		}
		return jLabel56;
	}
	/**
	 * This method initializes jLabel57
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel57() {
		if(jLabel57 == null) {
			jLabel57 = new javax.swing.JLabel();
			jLabel57.setText("Porcentaje de Retención de IVA: ");
			jLabel57.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			jLabel57.setPreferredSize(new java.awt.Dimension(180,14));
		}
		return jLabel57;
	}
	/**
	 * This method initializes jLabel58
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel58() {
		if(jLabel58 == null) {
			jLabel58 = new javax.swing.JLabel();
			jLabel58.setText("Símbolo de Moneda Local: ");
			jLabel58.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			jLabel58.setPreferredSize(new java.awt.Dimension(180,14));
		}
		return jLabel58;
	}
	/**
	 * This method initializes jLabel59
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel59() {
		if(jLabel59 == null) {
			jLabel59 = new javax.swing.JLabel();
			jLabel59.setText("Banco para Dorso de Cheque: ");
			jLabel59.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			jLabel59.setPreferredSize(new java.awt.Dimension(180,14));
		}
		return jLabel59;
	}
	/**
	 * This method initializes jLabel60
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel60() {
		if(jLabel60 == null) {
			jLabel60 = new javax.swing.JLabel();
			jLabel60.setText("Imprimir Frente del Cheque: ");
			jLabel60.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			jLabel60.setPreferredSize(new java.awt.Dimension(150,14));
		}
		return jLabel60;
	}
	/**
	 * This method initializes jLabel61
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel61() {
		if(jLabel61 == null) {
			jLabel61 = new javax.swing.JLabel();
			jLabel61.setText("Imprimir Dorso del Cheque: ");
			jLabel61.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			jLabel61.setPreferredSize(new java.awt.Dimension(150,14));
		}
		return jLabel61;
	}
	/**
	 * This method initializes jLabel62
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel62() {
		if(jLabel62 == null) {
			jLabel62 = new javax.swing.JLabel();
			jLabel62.setText("Cédula Obligatoria en Pago: ");
			jLabel62.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			jLabel62.setPreferredSize(new java.awt.Dimension(150,14));
		}
		return jLabel62;
	}
	/**
	 * This method initializes jLabel64
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel64() {
		if(jLabel64 == null) {
			jLabel64 = new javax.swing.JLabel();
			jLabel64.setText("Servidor: ");
			jLabel64.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			jLabel64.setPreferredSize(new java.awt.Dimension(50,14));
		}
		return jLabel64;
	}
	/**
	 * This method initializes jLabel65
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel65() {
		if(jLabel65 == null) {
			jLabel65 = new javax.swing.JLabel();
			jLabel65.setText("Central: ");
			jLabel65.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			jLabel65.setPreferredSize(new java.awt.Dimension(50,14));
		}
		return jLabel65;
	}
	/**
	 * This method initializes jLabel66
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel66() {
		if(jLabel66 == null) {
			jLabel66 = new javax.swing.JLabel();
			jLabel66.setText("Sinc.: ");
			jLabel66.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			jLabel66.setPreferredSize(new java.awt.Dimension(50,14));
		}
		return jLabel66;
	}
	/**
	 * This method initializes jLabel67
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel67() {
		if(jLabel67 == null) {
			jLabel67 = new javax.swing.JLabel();
			jLabel67.setText("Apagar Sistema: ");
			jLabel67.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			jLabel67.setPreferredSize(new java.awt.Dimension(90,14));
		}
		return jLabel67;
	}
	/**
	 * This method initializes jLabel68
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel68() {
		if(jLabel68 == null) {
			jLabel68 = new javax.swing.JLabel();
			jLabel68.setText("Reiniciar Sistema: ");
			jLabel68.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			jLabel68.setPreferredSize(new java.awt.Dimension(102,14));
		}
		return jLabel68;
	}
	/**
	 * This method initializes jLabel69
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel69() {
		if(jLabel69 == null) {
			jLabel69 = new javax.swing.JLabel();
			jLabel69.setText("Servicio de Sinc. por Socket: ");
			jLabel69.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			jLabel69.setPreferredSize(new java.awt.Dimension(150,14));
		}
		return jLabel69;
	}
	/**
	 * This method initializes jLabel70
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel70() {
		if(jLabel70 == null) {
			jLabel70 = new javax.swing.JLabel();
			jLabel70.setText("Caja Temporizada: ");
			jLabel70.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			jLabel70.setPreferredSize(new java.awt.Dimension(102,14));
		}
		return jLabel70;
	}
	/**
	 * This method initializes jLabel71
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel71() {
		if(jLabel71 == null) {
			jLabel71 = new javax.swing.JLabel();
			jLabel71.setText("Cambio Fecha: ");
			jLabel71.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			jLabel71.setPreferredSize(new java.awt.Dimension(90,14));
		}
		return jLabel71;
	}
	/**
	 * This method initializes jLabel72
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel72() {
		if(jLabel72 == null) {
			jLabel72 = new javax.swing.JLabel();
			jLabel72.setText("Sinc. Afiliados: ");
			jLabel72.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			jLabel72.setPreferredSize(new java.awt.Dimension(102,14));
		}
		return jLabel72;
	}
	/**
	 * This method initializes jLabel73
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel73() {
		if(jLabel73 == null) {
			jLabel73 = new javax.swing.JLabel();
			jLabel73.setText("Long. Línea Visor: ");
			jLabel73.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			jLabel73.setPreferredSize(new java.awt.Dimension(90,14));
		}
		return jLabel73;
	}
	/**
	 * This method initializes jLabel74
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel74() {
		if(jLabel74 == null) {
			jLabel74 = new javax.swing.JLabel();
			jLabel74.setText("Salto Línea Visor: ");
			jLabel74.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			jLabel74.setPreferredSize(new java.awt.Dimension(102,14));
		}
		return jLabel74;
	}
	/**
	 * This method initializes jLabel63
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel63() {
		if(jLabel63 == null) {
			jLabel63 = new javax.swing.JLabel();
			jLabel63.setText("Confirmación Oblig. en Pago: ");
			jLabel63.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			jLabel63.setPreferredSize(new java.awt.Dimension(150,14));
		}
		return jLabel63;
	}
	/**
	 * This method initializes txtEsquema
	 * 
	 * @return javax.swing.JTextField
	 */
	private javax.swing.JTextField getTxtEsquema() {
		if(txtEsquema == null) {
			txtEsquema = new javax.swing.JTextField();
			txtEsquema.setPreferredSize(new java.awt.Dimension(270,17));
			txtEsquema.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
		}
		return txtEsquema;
	}
	/**
	 * This method initializes cmbNumAbonos
	 * 
	 * @return javax.swing.JComboBox
	 */
	private javax.swing.JComboBox getCmbNumAbonos() {
		if(cmbNumAbonos == null) {
			cmbNumAbonos = new javax.swing.JComboBox();
			cmbNumAbonos.setBackground(new java.awt.Color(242,242,238));
			cmbNumAbonos.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			cmbNumAbonos.setPreferredSize(new java.awt.Dimension(50,17));
			cmbNumAbonos.addItem("1");
			cmbNumAbonos.addItem("2");
			cmbNumAbonos.addItem("3");
			cmbNumAbonos.addItem("4");
			cmbNumAbonos.addItem("5");
			cmbNumAbonos.addItem("6");
			cmbNumAbonos.addItem("7");
			cmbNumAbonos.addItem("8");
			cmbNumAbonos.addItem("9");
			cmbNumAbonos.addItem("10");
			cmbNumAbonos.addItemListener(new java.awt.event.ItemListener() { 
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					if (!e.getItem().toString().equals("")) {
						configListaAbono(Integer.parseInt(e.getItem().toString()));    
					}
					cambioApart();
				}
			});
			
		}
		return cmbNumAbonos;
	}

	/**
	 * This method initializes rbRebajS	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */    
	private JRadioButton getRbImpFteChequeS() {
		if (rbImpFteChequeS == null) {
			rbImpFteChequeS = new JRadioButton();
			rbImpFteChequeS.setBackground(new java.awt.Color(242,242,238));
			rbImpFteChequeS.setPreferredSize(new java.awt.Dimension(40,17));
			rbImpFteChequeS.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 10));
			rbImpFteChequeS.setText("Si");
		}
		return rbImpFteChequeS;
	}
	/**
	 * This method initializes rbRebajN	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */    
	private JRadioButton getRbImpFteChequeN() {
		if (rbImpFteChequeN == null) {
			rbImpFteChequeN = new JRadioButton();
			rbImpFteChequeN.setBackground(new java.awt.Color(242,242,238));
			rbImpFteChequeN.setPreferredSize(new java.awt.Dimension(40,17));
			rbImpFteChequeN.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 10));
			rbImpFteChequeN.setText("No");
		}
		return rbImpFteChequeN;
	}
	/**
	 * This method initializes rbRebajS	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */    
	private JRadioButton getRbImpDorsoChequeS() {
		if (rbImpDorsoChequeS == null) {
			rbImpDorsoChequeS = new JRadioButton();
			rbImpDorsoChequeS.setBackground(new java.awt.Color(242,242,238));
			rbImpDorsoChequeS.setPreferredSize(new java.awt.Dimension(40,17));
			rbImpDorsoChequeS.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 10));
			rbImpDorsoChequeS.setText("Si");
		}
		return rbImpDorsoChequeS;
	}
	/**
	 * This method initializes rbRebajN	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */    
	private JRadioButton getRbImpDorsoChequeN() {
		if (rbImpDorsoChequeN == null) {
			rbImpDorsoChequeN = new JRadioButton();
			rbImpDorsoChequeN.setBackground(new java.awt.Color(242,242,238));
			rbImpDorsoChequeN.setPreferredSize(new java.awt.Dimension(40,17));
			rbImpDorsoChequeN.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 10));
			rbImpDorsoChequeN.setText("No");
		}
		return rbImpDorsoChequeN;
	}
	/**
	 * This method initializes rbRebajS	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */    
	private JRadioButton getRbCedulaObligS() {
		if (rbCedulaObligS == null) {
			rbCedulaObligS = new JRadioButton();
			rbCedulaObligS.setBackground(new java.awt.Color(242,242,238));
			rbCedulaObligS.setPreferredSize(new java.awt.Dimension(40,17));
			rbCedulaObligS.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 10));
			rbCedulaObligS.setText("Si");
		}
		return rbCedulaObligS;
	}
	/**
	 * This method initializes rbRebajS	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */    
	private JRadioButton getRbCedulaObligN() {
		if (rbCedulaObligN == null) {
			rbCedulaObligN = new JRadioButton();
			rbCedulaObligN.setBackground(new java.awt.Color(242,242,238));
			rbCedulaObligN.setPreferredSize(new java.awt.Dimension(40,17));
			rbCedulaObligN.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 10));
			rbCedulaObligN.setText("No");
		}
		return rbCedulaObligN;
	}
	/**
	 * This method initializes rbRebajN	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */    
	private JRadioButton getRbNumConfObligS() {
		if (rbNumConfObligS == null) {
			rbNumConfObligS = new JRadioButton();
			rbNumConfObligS.setBackground(new java.awt.Color(242,242,238));
			rbNumConfObligS.setPreferredSize(new java.awt.Dimension(40,17));
			rbNumConfObligS.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 10));
			rbNumConfObligS.setText("Si");
		}
		return rbNumConfObligS;
	}
	/**
	 * This method initializes rbRebajN	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */    
	private JRadioButton getRbNumConfObligN() {
		if (rbNumConfObligN == null) {
			rbNumConfObligN = new JRadioButton();
			rbNumConfObligN.setBackground(new java.awt.Color(242,242,238));
			rbNumConfObligN.setPreferredSize(new java.awt.Dimension(40,17));
			rbNumConfObligN.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 10));
			rbNumConfObligN.setText("No");
		}
		return rbNumConfObligN;
	}

	/**
	 * This method initializes rbRebajN	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */    
	private JRadioButton getRbApagarSistN() {
		if (rbApagarSistN == null) {
			rbApagarSistN = new JRadioButton();
			rbApagarSistN.setBackground(new java.awt.Color(242,242,238));
			rbApagarSistN.setPreferredSize(new java.awt.Dimension(40,17));
			rbApagarSistN.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 10));
			rbApagarSistN.setText("No");
		}
		return rbApagarSistN;
	}
	/**
	 * This method initializes rbRebajN	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */    
	private JRadioButton getRbApagarSistS() {
		if (rbApagarSistS == null) {
			rbApagarSistS = new JRadioButton();
			rbApagarSistS.setBackground(new java.awt.Color(242,242,238));
			rbApagarSistS.setPreferredSize(new java.awt.Dimension(40,17));
			rbApagarSistS.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 10));
			rbApagarSistS.setText("Si");
		}
		return rbApagarSistS;
	}
	/**
	 * This method initializes rbRebajN	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */    
	private JRadioButton getRbReiniciarSistN() {
		if (rbReiniciarSistN == null) {
			rbReiniciarSistN = new JRadioButton();
			rbReiniciarSistN.setBackground(new java.awt.Color(242,242,238));
			rbReiniciarSistN.setPreferredSize(new java.awt.Dimension(40,17));
			rbReiniciarSistN.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 10));
			rbReiniciarSistN.setText("No");
		}
		return rbReiniciarSistN;
	}
	/**
	 * This method initializes rbRebajN	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */    
	private JRadioButton getRbReiniciarSistS() {
		if (rbReiniciarSistS == null) {
			rbReiniciarSistS = new JRadioButton();
			rbReiniciarSistS.setBackground(new java.awt.Color(242,242,238));
			rbReiniciarSistS.setPreferredSize(new java.awt.Dimension(40,17));
			rbReiniciarSistS.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 10));
			rbReiniciarSistS.setText("Si");
		}
		return rbReiniciarSistS;
	}
	/**
	 * This method initializes rbRebajN	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */    
	private JRadioButton getRbSyncCommanderN() {
		if (rbSyncCommanderN == null) {
			rbSyncCommanderN = new JRadioButton();
			rbSyncCommanderN.setBackground(new java.awt.Color(242,242,238));
			rbSyncCommanderN.setPreferredSize(new java.awt.Dimension(40,17));
			rbSyncCommanderN.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 10));
			rbSyncCommanderN.setText("No");
		}
		return rbSyncCommanderN;
	}
	/**
	 * This method initializes rbRebajN	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */    
	private JRadioButton getRbSyncCommanderS() {
		if (rbSyncCommanderS == null) {
			rbSyncCommanderS = new JRadioButton();
			rbSyncCommanderS.setBackground(new java.awt.Color(242,242,238));
			rbSyncCommanderS.setPreferredSize(new java.awt.Dimension(40,17));
			rbSyncCommanderS.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 10));
			rbSyncCommanderS.setText("Si");
		}
		return rbSyncCommanderS;
	}
	/**
	 * This method initializes rbRebajN	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */    
	private JRadioButton getRbCambioFechaN() {
		if (rbCambioFechaN == null) {
			rbCambioFechaN = new JRadioButton();
			rbCambioFechaN.setBackground(new java.awt.Color(242,242,238));
			rbCambioFechaN.setPreferredSize(new java.awt.Dimension(40,17));
			rbCambioFechaN.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 10));
			rbCambioFechaN.setText("No");
		}
		return rbCambioFechaN;
	}
	/**
	 * This method initializes rbRebajN	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */    
	private JRadioButton getRbCambioFechaS() {
		if (rbCambioFechaS == null) {
			rbCambioFechaS = new JRadioButton();
			rbCambioFechaS.setBackground(new java.awt.Color(242,242,238));
			rbCambioFechaS.setPreferredSize(new java.awt.Dimension(40,17));
			rbCambioFechaS.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 10));
			rbCambioFechaS.setText("Si");
		}
		return rbCambioFechaS;
	}
	/**
	 * This method initializes rbRebajN	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */    
	private JRadioButton getRbSincAfiliadoN() {
		if (rbSincAfiliadoN == null) {
			rbSincAfiliadoN = new JRadioButton();
			rbSincAfiliadoN.setBackground(new java.awt.Color(242,242,238));
			rbSincAfiliadoN.setPreferredSize(new java.awt.Dimension(40,17));
			rbSincAfiliadoN.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 10));
			rbSincAfiliadoN.setText("No");
		}
		return rbSincAfiliadoN;
	}
	/**
	 * This method initializes rbRebajN	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */    
	private JRadioButton getRbSincAfiliadoS() {
		if (rbSincAfiliadoS == null) {
			rbSincAfiliadoS = new JRadioButton();
			rbSincAfiliadoS.setBackground(new java.awt.Color(242,242,238));
			rbSincAfiliadoS.setPreferredSize(new java.awt.Dimension(40,17));
			rbSincAfiliadoS.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 10));
			rbSincAfiliadoS.setText("Si");
		}
		return rbSincAfiliadoS;
	}
	/**
	 * This method initializes rbRebajN	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */    
	private JRadioButton getRbActCajaTempN() {
		if (rbActCajaTempN == null) {
			rbActCajaTempN = new JRadioButton();
			rbActCajaTempN.setBackground(new java.awt.Color(242,242,238));
			rbActCajaTempN.setPreferredSize(new java.awt.Dimension(40,17));
			rbActCajaTempN.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 10));
			rbActCajaTempN.setText("No");
		}
		return rbActCajaTempN;
	}
	/**
	 * This method initializes rbRebajN	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */    
	private JRadioButton getRbActCajaTempS() {
		if (rbActCajaTempS == null) {
			rbActCajaTempS = new JRadioButton();
			rbActCajaTempS.setBackground(new java.awt.Color(242,242,238));
			rbActCajaTempS.setPreferredSize(new java.awt.Dimension(40,17));
			rbActCajaTempS.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 10));
			rbActCajaTempS.setText("Si");
		}
		return rbActCajaTempS;
	}
	/**
	 * This method initializes jLabel75
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel75() {
		if(jLabel75 == null) {
			jLabel75 = new javax.swing.JLabel();
			jLabel75.setText("Número de Caja: ");
			jLabel75.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			jLabel75.setPreferredSize(new java.awt.Dimension(180,14));
		}
		return jLabel75;
	}
	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private javax.swing.JTextField getJTextField() {
		if(jTextField == null) {
			jTextField = new javax.swing.JTextField();
			jTextField.setPreferredSize(new java.awt.Dimension(100,17));
			jTextField.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
		}
		return jTextField;
	}
	/**
	 * This method initializes jLabel77
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel77() {
		if(jLabel77 == null) {
			jLabel77 = new javax.swing.JLabel();
			jLabel77.setText("Color Combo Facturación: ");
			jLabel77.setPreferredSize(new java.awt.Dimension(180,14));
			jLabel77.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
		}
		return jLabel77;
	}
	/**
	 * This method initializes jLabel78
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel78() {
		if(jLabel78 == null) {
			jLabel78 = new javax.swing.JLabel();
			jLabel78.setText("Permitir Contribuyentes Ordinarios:");
			jLabel78.setPreferredSize(new java.awt.Dimension(180,14));
			jLabel78.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
		}
		return jLabel78;
	}
	/**
	 * This method initializes colorCombo
	 * 
	 * @return javax.swing.JTextField
	 */
	private javax.swing.JTextField getColorCombo() {
		if(colorCombo == null) {
			colorCombo = new javax.swing.JTextField();
			colorCombo.setPreferredSize(new java.awt.Dimension(100,17));
			colorCombo.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
		}
		return colorCombo;
	}
	/**
	 * This method initializes jLabel79
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel79() {
		if(jLabel79 == null) {
			jLabel79 = new javax.swing.JLabel();
			jLabel79.setText("Escaner Rápido: ");
			jLabel79.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			jLabel79.setPreferredSize(new java.awt.Dimension(90,14));
		}
		return jLabel79;
	}
	/**
	 * This method initializes jLabel80
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel80() {
		if(jLabel80 == null) {
			jLabel80 = new javax.swing.JLabel();
			jLabel80.setText("Activar Verificador: ");
			jLabel80.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			jLabel80.setPreferredSize(new java.awt.Dimension(102,14));
		}
		return jLabel80;
	}
	/**
	 * This method initializes jLabel81
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel81() {
		if(jLabel81 == null) {
			jLabel81 = new javax.swing.JLabel();
			jLabel81.setText("Long. Nombres: ");
			jLabel81.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			jLabel81.setPreferredSize(new java.awt.Dimension(90,14));
		}
		return jLabel81;
	}
	/**
	 * This method initializes jLabel82
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel82() {
		if(jLabel82 == null) {
			jLabel82 = new javax.swing.JLabel();
			jLabel82.setText("Long. Apellidos: ");
			jLabel82.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			jLabel82.setPreferredSize(new java.awt.Dimension(102,14));
		}
		return jLabel82;
	}
	/**
	 * This method initializes jPanel66
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel66() {
		if(jPanel66 == null) {
			jPanel66 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout19 = new java.awt.FlowLayout();
			layFlowLayout19.setAlignment(java.awt.FlowLayout.LEFT);
			layFlowLayout19.setVgap(2);
			jPanel66.setLayout(layFlowLayout19);
			jPanel66.add(getJLabel76(), null);
			jPanel66.add(getManejoGaveta(), null);
			jPanel66.add(getJLabel88(), null);
			jPanel66.add(getRbMantenerFocoSi(), null);
			jPanel66.add(getRbMantenerFocoNo(), null);
			bgMantenerGrupo.add(getRbMantenerFocoSi());		
			bgMantenerGrupo.add(getRbMantenerFocoNo());		
			jPanel66.setPreferredSize(new java.awt.Dimension(500,20));
			jPanel66.setBackground(new java.awt.Color(242,242,238));
		}
		return jPanel66;
	}
	/**
	 * This method initializes jLabel76
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel76() {
		if(jLabel76 == null) {
			jLabel76 = new javax.swing.JLabel();
			jLabel76.setText("Manejo Gaveta: ");
			jLabel76.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
		}
		return jLabel76;
	}
	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private javax.swing.JComboBox getManejoGaveta() {
		if(manejoGaveta == null) {
			manejoGaveta = new javax.swing.JComboBox();
			manejoGaveta.setSize(100, 17);
			manejoGaveta.setPreferredSize(new java.awt.Dimension(100,15));
			manejoGaveta.setBackground(new java.awt.Color(242,242,238));
			manejoGaveta.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			manejoGaveta.addItem("Impresora ");
			manejoGaveta.addItem("Visor ");
			manejoGaveta.addItemListener(new java.awt.event.ItemListener() { 
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					cambioSys();
				}
			});
		}
		return manejoGaveta;
	}
	/**
	 * This method initializes jLabel76
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel88() {
		if(jLabel88 == null) {
			jLabel88 = new javax.swing.JLabel();
			jLabel88.setText("Mantener Foco: ");
			jLabel88.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
		}
		return jLabel88;
	}
	/**
	 * This method initializes jPanel104	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel104() {
		if (jPanel104 == null) {
			jLabel83 = new JLabel();
			java.awt.FlowLayout flowLayout41 = new FlowLayout();
			jPanel104 = new JPanel();
			jPanel104.setLayout(flowLayout41);
			jPanel104.setPreferredSize(new java.awt.Dimension(500,20));
			jPanel104.setBackground(new java.awt.Color(242,242,238));
			jLabel83.setText("Monto Mínimo Apartados: ");
			jLabel83.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			jLabel83.setPreferredSize(new java.awt.Dimension(180,14));
			flowLayout41.setAlignment(java.awt.FlowLayout.LEFT);
			flowLayout41.setVgap(2);
			jPanel104.add(jLabel83, null);
			jPanel104.add(getTxtMtoMinApartado(), null);
		}
		return jPanel104;
	}
	
	/**
	 * This method initializes jPanel104	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel200() {
		if (jPanel200 == null) {
			jLabel200 = new JLabel();
			java.awt.FlowLayout flowLayout200 = new FlowLayout();
			jPanel200 = new JPanel();
			jPanel200.setLayout(flowLayout200);
			jPanel200.setPreferredSize(new java.awt.Dimension(500,20));
			jPanel200.setBackground(new java.awt.Color(242,242,238));
			jLabel200.setText("Monto Autorizado Para Devoluciones: ");
			jLabel200.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			jLabel200.setPreferredSize(new java.awt.Dimension(180,14));
			flowLayout200.setAlignment(java.awt.FlowLayout.LEFT);
			flowLayout200.setVgap(2);
			jPanel200.add(jLabel200, null);
			jPanel200.add(getTxtMtoMinDevolucion(), null);
		}
		return jPanel200;
	}
	
	/**
	 * Método componentHidden
	 *
	 * @param e
	 */
	public void componentHidden(ComponentEvent e) {
	}
	/**
	 * Método componentMoved
	 *
	 * @param e
	 */
	public void componentMoved(ComponentEvent e) {
		int width = (int)this.getSize().getWidth();
		int height = (int)this.getSize().getHeight();
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int x=0,y=0;
		
		if (this.getX() < 0) {
			x=0;
			y= (int)this.getBounds().y;
		} else
		if (this.getY() < 0) {
			y=0;
			x= (int)this.getBounds().x;
		} else
		if ((this.getX() + width) > screen.width){
			x=screen.width;
			y= (int)this.getBounds().y;
		} else
		if ((this.getY() + height) > screen.height){
			y=screen.height;
			x= (int)this.getBounds().x;
		} else {
			x=this.getX();
			y=this.getY();	
		}
		
		this.setBounds(x, y, width, height);
	}
	/**
	 * Método componentResized
	 *
	 * @param e
	 */
	public void componentResized(ComponentEvent e) {
	}
	/**
	 * Método componentShown
	 *
	 * @param e
	 */
	public void componentShown(ComponentEvent e) {
	}

	/**
	 * This method initializes txtMtoMinApartado
	 * 
	 * @return javax.swing.JTextField
	 */
	private javax.swing.JTextField getTxtMtoMinApartado() {
		if(txtMtoMinApartado == null) {
			txtMtoMinApartado = new javax.swing.JTextField();
			txtMtoMinApartado.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			txtMtoMinApartado.setColumns(8);
		}
		return txtMtoMinApartado;
	}
	
	/**
	 * This method initializes txtMtoMinApartado
	 * 
	 * @return javax.swing.JTextField
	 */
	private javax.swing.JTextField getTxtMtoMinDevolucion() {
		if(txtMtoMinDevolucion == null) {
			txtMtoMinDevolucion = new javax.swing.JTextField();
			txtMtoMinDevolucion.setPreferredSize(new java.awt.Dimension(100,17));
			txtMtoMinDevolucion.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
		}
		return txtMtoMinDevolucion;
	}
	/**
	 * This method initializes jPanel73
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel73() {
		if(jPanel73 == null) {
			jPanel73 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout110 = new java.awt.FlowLayout();
			layFlowLayout110.setVgap(2);
			layFlowLayout110.setAlignment(java.awt.FlowLayout.LEFT);
			jPanel73.setLayout(layFlowLayout110);
			jPanel73.setPreferredSize(new java.awt.Dimension(500,20));
			jPanel73.setBackground(new java.awt.Color(242,242,238));
			jPanel73.add(getJLabel100(), null);
			jPanel73.add(getTxtPuertoEscuchaCRPDA(), null);
			jPanel73.add(getJLabel97(), null);
			jPanel73.add(getTxtTimeOutPDA(), null);
		}
		return jPanel73;
	}

	/**
	 * This method initializes jLabel84
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel842() {
		if(jLabel84 == null) {
			jLabel84 = new javax.swing.JLabel();
			jLabel84.setText("Tipos de Entrega:");
			jLabel84.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			jLabel84.setPreferredSize(new java.awt.Dimension(150,14));
		}
		return jLabel84;
	}
	/**
	 * This method initializes jCheckBox
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private javax.swing.JCheckBox getJCheckBox() {
		if(jCheckBox == null) {
			jCheckBox = new javax.swing.JCheckBox();
			jCheckBox.setPreferredSize(new java.awt.Dimension(80,14));
			jCheckBox.setBackground(new java.awt.Color(242,242,238));
			jCheckBox.setText("Cte.Retira");		
			jCheckBox.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
		}
		return jCheckBox;
	}
	/**
	 * This method initializes jCheckBox1
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private javax.swing.JCheckBox getJCheckBox1() {
		if(jCheckBox1 == null) {
			jCheckBox1 = new javax.swing.JCheckBox();
			jCheckBox1.setPreferredSize(new java.awt.Dimension(80,14));
			jCheckBox1.setBackground(new java.awt.Color(242,242,238));
			jCheckBox1.setText("Despacho");
			jCheckBox1.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
		}
		return jCheckBox1;
	}
	/**
	 * This method initializes jCheckBox2
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private javax.swing.JCheckBox getJCheckBox2() {
		if(jCheckBox2 == null) {
			jCheckBox2 = new javax.swing.JCheckBox();
			jCheckBox2.setPreferredSize(new java.awt.Dimension(80,14));
			jCheckBox2.setBackground(new java.awt.Color(242,242,238));
			jCheckBox2.setText("Domicilio");
			jCheckBox2.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
		}
		return jCheckBox2;
	}
	/**
	 * This method initializes jLabel85
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel85() {
		if(jLabel85 == null) {
			jLabel85 = new javax.swing.JLabel();
			jLabel85.setText("Validar Clave al Salir:");
			jLabel85.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
		}
		return jLabel85;
	}
	/**
	 * This method initializes jRadioButton
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private javax.swing.JRadioButton getValidarClaveS() {
		if(validarClaveS == null) {
			validarClaveS = new javax.swing.JRadioButton();
			validarClaveS.setBackground(new java.awt.Color(242,242,238));
			validarClaveS.setText("Si");
			validarClaveS.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			validarClaveS.setPreferredSize(new java.awt.Dimension(35,17));
		}
		return validarClaveS;
	}
	/**
	 * This method initializes jRadioButton1
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private javax.swing.JRadioButton getValidarClaveN() {
		if(validarClaveN == null) {
			validarClaveN = new javax.swing.JRadioButton();
			validarClaveN.setBackground(new java.awt.Color(242,242,238));
			validarClaveN.setText("No");
			validarClaveN.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			validarClaveN.setPreferredSize(new java.awt.Dimension(38,17));
		}
		return validarClaveN;
	}
	/**
	 * This method initializes jPanel99
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel99() {
		if(jPanel99 == null) {
			jPanel99 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout112 = new java.awt.FlowLayout();
			layFlowLayout112.setVgap(2);
			layFlowLayout112.setAlignment(java.awt.FlowLayout.LEFT);
			jPanel99.setLayout(layFlowLayout112);
			jPanel99.add(getJLabel86(), null);
			jPanel99.add(getPreguntarFactSi(), null);
			jPanel99.add(getPreguntarFactNo(), null);
			jPanel99.setPreferredSize(new java.awt.Dimension(500,20));
			jPanel99.setBackground(new java.awt.Color(242,242,238));
			bgPreguntarFacturar.add(getPreguntarFactSi());
			bgPreguntarFacturar.add(getPreguntarFactNo());
		}
		return jPanel99;
	}
	/**
	 * This method initializes jLabel86
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel86() {
		if(jLabel86 == null) {
			jLabel86 = new javax.swing.JLabel();
			jLabel86.setText("Preguntar Facturar Apartado:");
			jLabel86.setPreferredSize(new java.awt.Dimension(180,14));
			jLabel86.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
		}
		return jLabel86;
	}
	/**
	 * This method initializes jRadioButton
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private javax.swing.JRadioButton getPreguntarFactSi() {
		if(rbPreguntarFactSi == null) {
			rbPreguntarFactSi = new javax.swing.JRadioButton();
			rbPreguntarFactSi.setPreferredSize(new java.awt.Dimension(40,17));
			rbPreguntarFactSi.setBackground(new java.awt.Color(242,242,238));
			rbPreguntarFactSi.setText("Si");
			rbPreguntarFactSi.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
		}
		return rbPreguntarFactSi;
	}
	/**
	 * This method initializes jRadioButton1
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private javax.swing.JRadioButton getPreguntarFactNo() {
		if(rbPreguntarFactNo == null) {
			rbPreguntarFactNo = new javax.swing.JRadioButton();
			rbPreguntarFactNo.setPreferredSize(new java.awt.Dimension(40,17));
			rbPreguntarFactNo.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			rbPreguntarFactNo.setBackground(new java.awt.Color(242,242,238));
			rbPreguntarFactNo.setText("No");
		}
		return rbPreguntarFactNo;
	}
	/**
	 * This method initializes tabListaRegalos
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getTabListaRegalos() {
		if(tabListaRegalos == null) {
			tabListaRegalos = new javax.swing.JPanel();
			tabListaRegalos.add(getJPanel115(), null);
			tabListaRegalos.add(getJPanel107(), null);
			tabListaRegalos.setPreferredSize(new java.awt.Dimension(1075,500));
			tabListaRegalos.setBackground(new java.awt.Color(226,226,222));
			KeyAdapter ka = new java.awt.event.KeyAdapter() { 
				public void keyTyped(java.awt.event.KeyEvent e) {    
					cambioLR();					
				}
			};
			getTxtCostoUT().addKeyListener(ka);
			getTxtDiasAperturaLG().addKeyListener(ka);
			getTxtMontoMinimoLG().addKeyListener(ka);
			getTxtDbPdtUrlServidor().addKeyListener(ka);
			getTxtDbPdtUsuario().addKeyListener(ka);
			getTxtDbPdtClave().addKeyListener(ka);
		}
		return tabListaRegalos;
	}
	/**
	 * This method initializes jPanel106
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel115() {
		if(jPanel115 == null) {
			jPanel115 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout113 = new java.awt.FlowLayout();
			layFlowLayout113.setHgap(0);
			layFlowLayout113.setVgap(0);
			jPanel115.setLayout(layFlowLayout113);
			jPanel115.add(getJPanel108(), null);
			jPanel115.add(getJPanel109(), null);
			jPanel115.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(69,107,127),1));
			jPanel115.setPreferredSize(new java.awt.Dimension(540,400));
			jPanel115.setBackground(new java.awt.Color(242,242,238));
		}
		return jPanel115;
	}
	/**
	 * This method initializes jPanel107
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel107() {
		if(jPanel107 == null) {
			jPanel107 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout32 = new java.awt.FlowLayout();
			layFlowLayout32.setAlignment(java.awt.FlowLayout.RIGHT);
			layFlowLayout32.setHgap(0);
			layFlowLayout32.setVgap(0);
			jPanel107.setLayout(layFlowLayout32);
			jPanel107.add(getBtnSaveLR(), null);
			jPanel107.setPreferredSize(new java.awt.Dimension(520,30));
			jPanel107.setBackground(new java.awt.Color(226,226,222));
		}
		return jPanel107;
	}
	/**
	 * This method initializes jPanel108
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel108() {
		if(jPanel108 == null) {
			jPanel108 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout23 = new java.awt.FlowLayout();
			layFlowLayout23.setAlignment(java.awt.FlowLayout.LEFT);
			jPanel108.setLayout(layFlowLayout23);
			jPanel108.add(getJLabel95(), null);
			jPanel108.setPreferredSize(new java.awt.Dimension(540,40));
			jPanel108.setBackground(new java.awt.Color(69,107,127));
		}
		return jPanel108;
	}
	/**
	 * This method initializes jPanel109
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel109() {
		if(jPanel109 == null) {
			jPanel109 = new javax.swing.JPanel();
			jPanel109.add(getJPanel100(), null);
			jPanel109.add(getJPanel110(), null);
			jPanel109.add(getJPanel111(), null);
			jPanel109.add(getJPanel112(), null);
			jPanel109.add(getJPanel113(), null);
			jPanel109.add(getJPanel114(), null);
			jPanel109.setPreferredSize(new java.awt.Dimension(530,340));
			jPanel109.setBackground(new java.awt.Color(242,242,238));
		}
		return jPanel109;
	}
	/**
	 * This method initializes jLabel88
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel95() {
		if(jLabel95 == null) {
			jLabel95 = new javax.swing.JLabel();
			jLabel95.setText("Configuración de Listas de Regalos");
			jLabel95.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 18));
			jLabel95.setIcon(new ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix32x32/transform.png")));
			jLabel95.setForeground(java.awt.Color.white);
		}
		return jLabel95;
	}
	/**
	 * This method initializes btnSaveLR
	 * 
	 * @return javax.swing.JButton
	 */
	private javax.swing.JButton getBtnSaveLR() {
		if(btnSaveLR == null) {
			btnSaveLR = new javax.swing.JButton();
			btnSaveLR.setText("Guardar");
			btnSaveLR.setIcon(new ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix16x16/data_disk.png")));
			btnSaveLR.setPreferredSize(new java.awt.Dimension(100,26));
			btnSaveLR.setBackground(new java.awt.Color(242,242,238));
			btnSaveLR.setEnabled(false);
			btnSaveLR.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(java.awt.event.ActionEvent e) {    
					guardarValoresLR();
				}
			});
		}
		return btnSaveLR;
	}
	
	private void guardarValoresLR(){

		if(!eppCR.isNodeConfigured("listaregalos")){
		// La config no existe, la agregamos
			try {
				eppCR.addNewNodeToPreferencesTop("listaregalos");
			} catch (NodeAlreadyExistsException e) {
				e.printStackTrace();
			} catch (UnidentifiedPreferenceException e) {
				e.printStackTrace();
			}
		}
		
		try {
			eppCR.setConfigStringForParameter("listaregalos","MaxDiasAperturaLG",txtDiasAperturaLG.getText());
			eppCR.setConfigStringForParameter("listaregalos", "MontoMinimoLG", txtMontoMinimoLG.getText());
			eppCR.setConfigStringForParameter("listaregalos", "CostoUT", txtCostoUT.getText());
			eppCR.setConfigStringForParameter("listaregalos", "DbPdtUrlServidor", txtDbUrlServidorPdt.getText());
			eppCR.setConfigStringForParameter("listaregalos", "DbPdtUsuario", txtUsuarioDbPdt.getText());
			eppCR.setConfigStringForParameter("listaregalos", "DbPdtClave", txtClaveDbPdt.getText());
	
			btnSaveLR.setEnabled(false);
		} catch (NoSuchNodeException e1) {
			e1.printStackTrace();
		} catch (UnidentifiedPreferenceException e1) {
			e1.printStackTrace();
		}

	};
	/**
	 * This method initializes jPanel100
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel100() {
		if(jPanel100 == null) {
			jPanel100 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout42 = new java.awt.FlowLayout();
			layFlowLayout42.setVgap(2);
			layFlowLayout42.setAlignment(java.awt.FlowLayout.LEFT);
			jPanel100.setLayout(layFlowLayout42);
			jPanel100.add(getJLabel96(), null);
			jPanel100.add(getTxtMontoMinimoLG(), null);
			jPanel100.setPreferredSize(new java.awt.Dimension(500,20));
			jPanel100.setBackground(new java.awt.Color(242,242,238));
		}
		return jPanel100;
	}
	/**
	 * This method initializes jLabel96
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel96() {
		if(jLabel96 == null) {
			jLabel96 = new javax.swing.JLabel();
			jLabel96.setText("Monto mín. Listas Garantizadas (U.T.) :");
			jLabel96.setPreferredSize(new java.awt.Dimension(190,14));
			jLabel96.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
		}
		return jLabel96;
	}
	/**
	 * This method initializes jLabel97
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel97() {
		if(jLabel97 == null) {
			jLabel97 = new javax.swing.JLabel();
			jLabel97.setText("Timeout PDA :");
			jLabel97.setPreferredSize(new java.awt.Dimension(120,14));
			jLabel97.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
		}
		return jLabel97;
	}
	/**
	 * This method initializes jLabel98
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel98() {
		if(jLabel98 == null) {
			jLabel98 = new javax.swing.JLabel();
			jLabel98.setText("Ip Servidor PDA :");
			jLabel98.setPreferredSize(new java.awt.Dimension(150,14));
			jLabel98.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
		}
		return jLabel98;
	}
	/**
	 * This method initializes jLabel96
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel99() {
		if(jLabel99 == null) {
			jLabel99 = new javax.swing.JLabel();
			jLabel99.setText("Puerto Servidor PDA :");
			jLabel99.setPreferredSize(new java.awt.Dimension(120,14));
			jLabel99.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
		}
		return jLabel99;
	}
	/**
	 * This method initializes jLabel100
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel100() {
		if(jLabel100 == null) {
			jLabel100 = new javax.swing.JLabel();
			jLabel100.setText("Puerto Escucha CR PDA :");
			jLabel100.setPreferredSize(new java.awt.Dimension(150,14));
			jLabel100.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
		}
		return jLabel100;
	}
	/**
	 * This method initializes jPanel110
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel110() {
		if(jPanel110 == null) {
			jPanel110 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout51 = new java.awt.FlowLayout();
			layFlowLayout51.setVgap(2);
			layFlowLayout51.setAlignment(java.awt.FlowLayout.LEFT);
			jPanel110.setLayout(layFlowLayout51);
			jPanel110.add(getJLabel90(), null);
			jPanel110.add(getTxtCostoUT(), null);
			jPanel110.setPreferredSize(new java.awt.Dimension(500,20));
			jPanel110.setBackground(new java.awt.Color(242,242,238));
		}
		return jPanel110;
	}
	/**
	 * This method initializes jPanel111
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel111() {
		if(jPanel111 == null) {
			jPanel111 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout61 = new java.awt.FlowLayout();
			layFlowLayout61.setVgap(2);
			layFlowLayout61.setAlignment(java.awt.FlowLayout.LEFT);
			jPanel111.setLayout(layFlowLayout61);
			jPanel111.add(getJLabel91(), null);
			jPanel111.add(getTxtDiasAperturaLG(), null);
			jPanel111.setPreferredSize(new java.awt.Dimension(500,20));
			jPanel111.setBackground(new java.awt.Color(242,242,238));
		}
		return jPanel111;
	}
	/**
	 * This method initializes jLabel90
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel90() {
		if(jLabel90 == null) {
			jLabel90 = new javax.swing.JLabel();
			jLabel90.setText("Costo Unidad Tributaria : ");
			jLabel90.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			jLabel90.setPreferredSize(new java.awt.Dimension(190,14));
		}
		return jLabel90;
	}
	/**
	 * This method initializes jLabel91
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel91() {
		if(jLabel91 == null) {
			jLabel91 = new javax.swing.JLabel();
			jLabel91.setText("Días para apertura Listas Garantizadas :");
			jLabel91.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			jLabel91.setPreferredSize(new java.awt.Dimension(190,14));
		}
		return jLabel91;
	}
	/**
	 * This method initializes txtMontoMinimoLG
	 * 
	 * @return javax.swing.JTextField
	 */
	private javax.swing.JTextField getTxtMontoMinimoLG() {
		if(txtMontoMinimoLG == null) {
			txtMontoMinimoLG = new javax.swing.JTextField();
			txtMontoMinimoLG.setColumns(8);
			txtMontoMinimoLG.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			txtMontoMinimoLG.setPreferredSize(new java.awt.Dimension(100,18));
		}
		return txtMontoMinimoLG;
	}
	/**
	 * This method initializes txtCostoUT
	 * 
	 * @return javax.swing.JTextField
	 */
	private javax.swing.JTextField getTxtCostoUT() {
		if(txtCostoUT == null) {
			txtCostoUT = new javax.swing.JTextField();
			txtCostoUT.setPreferredSize(new java.awt.Dimension(76,18));
			txtCostoUT.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
		}
		return txtCostoUT;
	}
	/**
	 * This method initializes txtDiasAperturaLG
	 * 
	 * @return javax.swing.JTextField
	 */
	private javax.swing.JTextField getTxtDiasAperturaLG() {
		if(txtDiasAperturaLG == null) {
			txtDiasAperturaLG = new javax.swing.JTextField();
			txtDiasAperturaLG.setPreferredSize(new java.awt.Dimension(76,18));
			txtDiasAperturaLG.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
		}
		return txtDiasAperturaLG;
	}
	/**
	 * This method initializes jPanel112
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel112() {
		if(jPanel112 == null) {
			jPanel112 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout114 = new java.awt.FlowLayout();
			layFlowLayout114.setVgap(2);
			layFlowLayout114.setAlignment(java.awt.FlowLayout.LEFT);
			jPanel112.setLayout(layFlowLayout114);
			jPanel112.add(getJLabel92(), null);
			jPanel112.add(getTxtDbPdtUrlServidor(), null);
			jPanel112.setPreferredSize(new java.awt.Dimension(500,20));
			jPanel112.setBackground(new java.awt.Color(242,242,238));
		}
		return jPanel112;
	}
	/**
	 * This method initializes jLabel92
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel92() {
		if(jLabel92 == null) {
			jLabel92 = new javax.swing.JLabel();
			jLabel92.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			jLabel92.setText("URL de BD del Servidor de PDT :");
			jLabel92.setPreferredSize(new java.awt.Dimension(190,14));
		}
		return jLabel92;
	}
	/**
	 * This method initializes txtDbUrlServidorPdt
	 * 
	 * @return javax.swing.JTextField
	 */
	private javax.swing.JTextField getTxtDbPdtUrlServidor() {
		if(txtDbUrlServidorPdt == null) {
			txtDbUrlServidorPdt = new javax.swing.JTextField();
			txtDbUrlServidorPdt.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			txtDbUrlServidorPdt.setPreferredSize(new java.awt.Dimension(200,18));
		}
		return txtDbUrlServidorPdt;
	}
	/**
	 * This method initializes jPanel113
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel113() {
		if(jPanel113 == null) {
			jPanel113 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout24 = new java.awt.FlowLayout();
			layFlowLayout24.setVgap(2);
			layFlowLayout24.setAlignment(java.awt.FlowLayout.LEFT);
			jPanel113.setLayout(layFlowLayout24);
			jPanel113.add(getJLabel93(), null);
			jPanel113.add(getTxtDbPdtUsuario(), null);
			jPanel113.setPreferredSize(new java.awt.Dimension(500,20));
			jPanel113.setBackground(new java.awt.Color(242,242,238));
		}
		return jPanel113;
	}
	/**
	 * This method initializes jPanel114
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel114() {
		if(jPanel114 == null) {
			jPanel114 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout33 = new java.awt.FlowLayout();
			layFlowLayout33.setVgap(2);
			layFlowLayout33.setAlignment(java.awt.FlowLayout.LEFT);
			jPanel114.setLayout(layFlowLayout33);
			jPanel114.add(getJLabel94(), null);
			jPanel114.add(getTxtDbPdtClave(), null);
			jPanel114.setPreferredSize(new java.awt.Dimension(500,20));
			jPanel114.setBackground(new java.awt.Color(242,242,238));
		}
		return jPanel114;
	}
	/**
	 * This method initializes jLabel93
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel93() {
		if(jLabel93 == null) {
			jLabel93 = new javax.swing.JLabel();
			jLabel93.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			jLabel93.setText("Usuario de BD del servidor de PDT :");
			jLabel93.setPreferredSize(new java.awt.Dimension(190,14));
		}
		return jLabel93;
	}
	/**
	 * This method initializes txtUsuarioDbPdt
	 * 
	 * @return javax.swing.JTextField
	 */
	private javax.swing.JTextField getTxtDbPdtUsuario() {
		if(txtUsuarioDbPdt == null) {
			txtUsuarioDbPdt = new javax.swing.JTextField();
			txtUsuarioDbPdt.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			txtUsuarioDbPdt.setPreferredSize(new java.awt.Dimension(120,18));
		}
		return txtUsuarioDbPdt;
	}
	/**
	 * This method initializes jLabel94
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel94() {
		if(jLabel94 == null) {
			jLabel94 = new javax.swing.JLabel();
			jLabel94.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			jLabel94.setText("Clave de BD del servidor de PDT");
			jLabel94.setPreferredSize(new java.awt.Dimension(190,14));
		}
		return jLabel94;
	}
	/**
	 * This method initializes txtClaveDbPdt
	 * 
	 * @return javax.swing.JTextField
	 */
	private javax.swing.JTextField getTxtDbPdtClave() {
		if(txtClaveDbPdt == null) {
			txtClaveDbPdt = new javax.swing.JTextField();
			txtClaveDbPdt.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			txtClaveDbPdt.setPreferredSize(new java.awt.Dimension(120,18));
		}
		return txtClaveDbPdt;
	}
}  //  @jve:visual-info  decl-index=0 visual-constraint="10,10"  @jve:decl-index=0:visual-constraint="10,10"

class Abono {
	private int valor;
	private int codigo;
		
	public Abono (int xValor, int xNumeroAbono) {
		codigo = xNumeroAbono;
		valor = xValor;
	};
		
	public int getValor() {
		return this.valor;
	};
		
	public void setValor(int xValor) {
		this.valor = xValor;
	};
		
	public int getCodigo() {
		return this.codigo;
	};
	
};
