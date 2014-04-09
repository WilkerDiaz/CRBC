/*
 * $Id: WindowsMenuConsultasPuntoAgil.java,v 1.6 2007/04/25 18:46:04 programa8 Exp $
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
 * $Log: WindowsMenuConsultasPuntoAgil.java,v $
 * Revision 1.6  2007/04/25 18:46:04  programa8
 * Version Estable Pto Agil, controlando cargos dos dobles , una sola instancia a datosExternos , control fuera de linea
 *
 * Revision 1.5  2006/08/25 18:17:35  programa4
 * Correccion en anulacion de cheque (para que no solicite lectura banda)
 * Y convertido PuntoAgilSubSistema en singleton en lugar de metodos estaticos
 *
 * Revision 1.4  2006/07/11 19:29:18  programa8
 * Organizadas importaciones
 *
 * Revision 1.3  2006/07/05 15:25:44  programa4
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

import com.becoblohm.cr.excepciones.BonoRegaloException;
import com.becoblohm.cr.gui.BarraTitulo;
import com.becoblohm.cr.gui.JHighlightButton;
import com.becoblohm.cr.utiles.MensajesVentanas;

/**
 * Proyecto: TarjetaCreditoEPA Clase: WindowsMenuPuntoAgil
 *
 * <p>
 * <a href="WindowsMenuPuntoAgil.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author analista5 - $Author: programa8 $
 * @version $Revision: 1.6 $ - $Date: 2007/04/25 18:46:04 $
 * @since 20-jun-2005
 */
public class WindowsMenuConsultasPuntoAgil extends JDialog implements
        KeyListener {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger
            .getLogger(WindowsMenuConsultasPuntoAgil.class);

    private static final String CONSULTA_PUNTOS = "Consulta de Puntos";

    /**
     * long serialVersionUID
     */
    private static final long serialVersionUID = 9076488643490551292L;

    private JPanel jContentPane = null;

    private JPanel jContentPane1 = null;

    private JPanel jPanelConsultaPuntos = null;

    private JButton jButtonConsultaPuntos = null;

    private JPanel jLabelTitulo = null;

    /**
     * This is the default constructor
     */
    public WindowsMenuConsultasPuntoAgil() {
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
        this.setTitle("Punto Ágil");
        this.setSize(230, 100);
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
            jContentPane1.setLayout(new GridLayout(1, 1, 5, 5));
            jContentPane1.setBackground(new java.awt.Color(226, 226, 222));
            jContentPane1.setBorder(new javax.swing.border.SoftBevelBorder(
                    BevelBorder.RAISED));
            jContentPane1.add(getJPanelConsultaPuntos(), null);
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
        if (jPanelConsultaPuntos == null) {
            jPanelConsultaPuntos = new JPanel();
            jPanelConsultaPuntos.setLayout(new BorderLayout());
            jPanelConsultaPuntos
                    .setBorder(new javax.swing.border.SoftBevelBorder(
                            BevelBorder.RAISED));
            jPanelConsultaPuntos.add(getJButtonConsultaPuntos(), null); // Generated
        }
        return jPanelConsultaPuntos;
    }

    /**
     * This method initializes jButtonConsultaVoucher
     *
     * @return javax.swing.JButton
     */
    private JButton getJButtonConsultaPuntos() {
        if (jButtonConsultaPuntos == null) {
            jButtonConsultaPuntos = new JHighlightButton(new java.awt.Color(
                    192, 192, 255));
            jButtonConsultaPuntos.setBackground(new java.awt.Color(226, 226,
                    222));
            jButtonConsultaPuntos.setText(CONSULTA_PUNTOS);
            jButtonConsultaPuntos.addKeyListener(this);
            jButtonConsultaPuntos
                    .setAction(new AbstractAction(CONSULTA_PUNTOS) {
                        public void actionPerformed(ActionEvent e) {
                            iniciarConsultaPuntos();
                        }
                    });
            //Por migración a vpos universal no es necesario un boton para consulta de puntos, se muestra la opcion siempre que se consultan los puntos
            //jButtonConsultaPuntos.setEnabled(false);
        }
        return jButtonConsultaPuntos;
    }

    /**
     * Imprime un reporte de cierre del punto agil.
     */
    public void iniciarConsultaPuntos() {
    	//if (PuntoAgilSubSistema.chequearLineaMerchant()){ // IROAS 09-07-2009, eliminacion de dependencia con servTda
        //PuntoAgilSubSistema.getInstance().consultaPuntos();
    	
    	if(MensajesVentanas.preguntarOpcionesTimeOut(
    			"Preparese para deslizar Tarjeta", "Aceptar",
    			"Cancelar", 0, 5000)==0){
    			try {
					PuntoAgilSubSistema.getInstance().obtenerOperacionConsultaCards();
				} catch (BonoRegaloException e) {
					MensajesVentanas.mensajeError(e.getMensaje());
				} finally {
					MensajesVentanas.iniciarVerificadorFoco();
				}
    	}
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
            if (arg0.getSource().equals(getJButtonConsultaPuntos())) {
            //	PuntoAgilSubSistema.chequearLineaMerchant(); //IROJAS 09-07-2009, eliminacion de depenencia de servTda
                iniciarConsultaPuntos();
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

} // @jve:decl-index=0:visual-constraint="10,10"

