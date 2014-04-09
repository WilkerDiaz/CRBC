/*
 * $Id: WindowsMenuUtilitariosOtrasFunciones.java,v 1.9 2007/04/25 21:59:51 programa8 Exp $
 * ===========================================================================
 * Material Propiedad SuperFerretería EPA C.A.
 *
 * Proyecto		: TarjetaCreditoEPA
 * Paquete		: com.epa.sistemas.ventas.caja.TarjetaCreditoEPA
 * Programa		: WindowsMenuUtilitariosOtrasFunciones.java
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
 * $Log: WindowsMenuUtilitariosOtrasFunciones.java,v $
 * Revision 1.9  2007/04/25 21:59:51  programa8
 * Control de la aplicacion fuera de linea
 *
 * Revision 1.8  2006/09/20 17:21:11  programa4
 * Correccion de titulo (Operaciones El Punto Agil) (le faltaba la palabra 'El')
 *
 * Revision 1.7  2006/09/04 14:12:51  programa4
 * Actualizado Llamada a PuntoAgilSubSistema que en lugar de clases estaticas es una instancia singleton
 *
 * Revision 1.6  2006/06/29 13:04:37  programa4
 * Renombrados metodos y agregado soporte a flechas en menu
 *
 * Revision 1.5  2006/06/16 21:26:56  programa4
 * Actualizada opcion que inicia consulta puntos
 *
 * Revision 1.4  2006/06/14 13:39:39  programa4
 * Reordenados botones
 *
 * Revision 1.3  2006/06/10 02:10:26  programa4
 * Agergada opcion de consultas punto agil
 *
 * Revision 1.2  2006/05/18 18:26:17  programa4
 * Agregado constructores ant para automatizar compilacion
 * Funcionan
 * - Consulta Ultimo Voucher (con reimpresion sin anulacion).
 * - Pre cierre
 * - Cierre
 *
 * Revision 1.1  2006/05/17 20:32:37  programa4
 * Version Inicial de Extension MenuUtilitarios
 * Contempla llamada a Punto Agil y a Crédito EPA
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
package com.epa.ventas.cr.menuutilitariosotrasfunciones;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import org.apache.log4j.Logger;

import com.becoblohm.cr.gui.BarraTitulo;
import com.becoblohm.cr.gui.JHighlightButton;
//import com.epa.sistemas.ventas.caja.TarjetaCreditoEPA.Subsistema;
import com.epa.ventas.cr.puntoAgil.PuntoAgilSubSistema;

/**
 * Proyecto: TarjetaCreditoEPA Clase: WindowsMenuUtilitariosOtrasFunciones
 *
 * <p>
 * <a href="WindowsMenuUtilitariosOtrasFunciones.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author analista5 - $Author: programa8 $
 * @version $Revision: 1.9 $ - $Date: 2007/04/25 21:59:51 $
 * @since 20-jun-2005
 */
public class WindowsMenuUtilitariosOtrasFunciones extends JDialog implements
        KeyListener {
    /**
     * Logger for this class
     */
   public  static final Logger logger = Logger
            .getLogger(WindowsMenuUtilitariosOtrasFunciones.class);

    private static final String CONSULTAS_PUNTO_AGIL = "Consultas de Puntos";  //  @jve:decl-index=0:

    private static final String PUNTO_AGIL = "Operaciones El Punto Ágil";

//    private static final String CREDITO_EPA = "Crédito EPA";

    /**
     * long serialVersionUID
     */
    private static final long serialVersionUID = 9076488643490551292L;

   // private JPanel jPanel1 = null;

    private JPanel jPanel2 = null;

    private JPanel jPanel3 = null;

    //private JButton jButtonCreditoEPA = null;

    private JButton jButtonPuntoAgil = null;

    private JButton jButtonConsultasPuntoAgil = null;

    private JPanel jPanel = null;

    private JPanel jContentPane = null;

    private JPanel jPanelTitulo = null;

    /**
     * This is the default constructor
     */
    public WindowsMenuUtilitariosOtrasFunciones() {
        super();
        initialize();
      //  getJButtonCreditoEPA().addKeyListener(this);
        getJButtonPuntoAgil().addKeyListener(this);
        getJButtonConsultasPuntoAgil().addKeyListener(this);
    }

    /**
     * This method initializes this
     */
    private void initialize() {
        this
                .setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        this.setBounds(new java.awt.Rectangle(0, 0, 230, 150)); // Generated
        this.setComponentOrientation(java.awt.ComponentOrientation.UNKNOWN);
        this.setTitle("Otras Funciones");
        this.setContentPane(getJContentPane());
    }

    /**
     * This method initializes jPanel
     *
     * @return javax.swing.JPanel
     */
    private JPanel getJContentPane() {
        if (jContentPane == null) {
            jContentPane = new JPanel();
            jContentPane.setLayout(new BorderLayout()); // Generated
            jContentPane.add(getJPanelTitulo(), java.awt.BorderLayout.NORTH); // Generated
            jContentPane.add(getJPanel(), java.awt.BorderLayout.CENTER); // Generated
        }
        return jContentPane;
    }

    /**
     * This method initializes jContentPane1
     *
     * @return javax.swing.JPanel
     */
    private JPanel getJPanel() {
        if (jPanel == null) {
            jPanel = new javax.swing.JPanel();
            jPanel.setLayout(new GridLayout(2, 1, 5, 5));
            jPanel.setBorder(new javax.swing.border.SoftBevelBorder(
                    BevelBorder.RAISED));
          //  jPanel.add(getJPanel1(), null);
            jPanel.add(getJPanel2(), null);
            jPanel.add(getJPanel3(), null);
            jPanel.setBackground(new java.awt.Color(226, 226, 222));
        }
        return jPanel;
    }

    /**
     * This method initializes jPanel1
     *
     * @return javax.swing.JPanel
     */
 /*   private JPanel getJPanel1() {
        if (jPanel1 == null) {
            jPanel1 = new JPanel();
            jPanel1.setLayout(new BorderLayout());
            jPanel1.add(getJButtonCreditoEPA(), null);
            jPanel1.setBorder(new javax.swing.border.SoftBevelBorder(
                    BevelBorder.RAISED));

        }
        return jPanel1;
    }*/

    /**
     * This method initializes jPanel2
     *
     * @return javax.swing.JPanel
     */
    private JPanel getJPanel2() {
        if (jPanel2 == null) {
            jPanel2 = new JPanel();
            jPanel2.setLayout(new BorderLayout());
            jPanel2.add(getJButtonPuntoAgil(), null);
            jPanel2.setBorder(new javax.swing.border.SoftBevelBorder(
                    BevelBorder.RAISED));
        }
        return jPanel2;
    }

    /**
     * This method initializes jPanel3
     *
     * @return javax.swing.JPanel
     */
    private JPanel getJPanel3() {
        if (jPanel3 == null) {
            jPanel3 = new JPanel();
            jPanel3.setLayout(new BorderLayout());
            jPanel3.add(getJButtonConsultasPuntoAgil(), null);
            jPanel3.setBorder(new javax.swing.border.SoftBevelBorder(
                    BevelBorder.RAISED));
        }
        return jPanel3;
    }

    /**
     * This method initializes jButton
     *
     * @return javax.swing.JButton
     */
    private JButton getJButtonPuntoAgil() {
        if (jButtonPuntoAgil == null) {
            jButtonPuntoAgil = new JHighlightButton(new java.awt.Color(192,
                    192, 255));
            jButtonPuntoAgil.setBackground(new java.awt.Color(226, 226, 222));
            jButtonPuntoAgil.setAction(new AbstractAction(PUNTO_AGIL) {
                /**
				 * 
				 */
				private static final long serialVersionUID = -5644188783178473950L;

				public void actionPerformed(ActionEvent e) {
                    iniciarPuntoAgil();
                }
            });
        }
        return jButtonPuntoAgil;
    }

    /**
     * This method initializes jButton
     *
     * @return javax.swing.JButton
     */
    private JButton getJButtonConsultasPuntoAgil() {
        if (jButtonConsultasPuntoAgil == null) {
            jButtonConsultasPuntoAgil = new JHighlightButton(
                    new java.awt.Color(192, 192, 255));
            jButtonConsultasPuntoAgil.setBackground(new java.awt.Color(226,
                    226, 222));
            jButtonConsultasPuntoAgil.setAction(new AbstractAction(
                    CONSULTAS_PUNTO_AGIL) {
                /**
						 * 
						 */
						private static final long serialVersionUID = -3324721427901956637L;

				public void actionPerformed(ActionEvent e) {
					iniciarConsultasPuntoAgil();
				}
            });
            //Por migración a vpos universal no es necesario un boton para consulta de puntos, se muestra la opcion siempre que se consultan los puntos
            //jButtonConsultasPuntoAgil.setEnabled(false);
        }
        return jButtonConsultasPuntoAgil;
    }

    /**
     * This method initializes jButton
     *
     * @return javax.swing.JButton
     */
/*    private JButton getJButtonCreditoEPA() {
        if (jButtonCreditoEPA == null) {
            jButtonCreditoEPA = new JHighlightButton(new java.awt.Color(192,
                    192, 255));
            jButtonCreditoEPA.setAction(new AbstractAction(CREDITO_EPA) {
                /**
				 * 
				 */
	/*			private static final long serialVersionUID = 3658049936861165571L;

				public void actionPerformed(ActionEvent e) {
                    iniciarCreditoEPA();
                }
            });
            jButtonCreditoEPA.setBackground(new java.awt.Color(226, 226, 222));
        }
        return jButtonCreditoEPA;
    }*/

    /**
     * Inicia el SubSistema de Crédito EPA
     */
    public void iniciarCreditoEPA() {
     //   Subsistema.mostrarVentanaInicial();
     //   dispose();
    }

    /**
     * Inicia el SubSistema de Punto Ágil
     */
    public void iniciarPuntoAgil() {
    //	if (PuntoAgilSubSistema.chequearLineaMerchant()){//IROJAS 09-07-2009, eliminacion de depenencia de servTda
    		PuntoAgilSubSistema.getInstance().mostrarVentanaInicial();   	
    /*	}else{
    		MensajesVentanas.aviso("la caja se encuentra fuera de linea");
    	}*/
    	
    	dispose();
    }

    /**
     * Inicia el SubSistema de Punto Ágil
     */
    public void iniciarConsultasPuntoAgil() {
   // 	if (PuntoAgilSubSistema.chequearLineaMerchant()){//IROJAS 09-07-2009, eliminacion de depenencia de servTda
    		PuntoAgilSubSistema.getInstance().mostrarVentanaConsultaPuntos();
    /*	}else{
    		MensajesVentanas.aviso("la caja se encuentra fuera de linea");
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
        } else if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
            if (arg0.getSource().equals(getJButtonConsultasPuntoAgil()) && getJButtonConsultasPuntoAgil().isEnabled()){
            //	PuntoAgilSubSistema.chequearLineaMerchant();//IROJAS 09-07-2009, eliminacion de depenencia de servTda
                iniciarConsultasPuntoAgil();
            }/*else if (arg0.getSource().equals(getJButtonCreditoEPA())){
                iniciarCreditoEPA();
            }*/
            else if (arg0.getSource().equals(getJButtonPuntoAgil())){
            	//PuntoAgilSubSistema.chequearLineaMerchant();//IROJAS 09-07-2009, eliminacion de depenencia de servTda
            	iniciarPuntoAgil();                
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
     * This method initializes jPanelTitulo
     *
     * @return javax.swing.JPanel
     */
    private JPanel getJPanelTitulo() {
        if (jPanelTitulo == null) {
            jPanelTitulo = new BarraTitulo(this);
        }
        return jPanelTitulo;
    }

} // @jve:decl-index=0:visual-constraint="10,10"

