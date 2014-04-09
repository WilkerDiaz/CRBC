/*
 * $Id: VentanaConfirmacionTimeOut.java,v 1.1 2007/12/05 18:13:01 programa8 Exp $
 * ===========================================================================
 *
 * Proyecto		: CR
 * Paquete		: com.becoblohm.cr.gui
 * Programa		: VentanaConfirmacionTimeOut.java
 * Creado por	: programa4
 * Creado en 	: 01/06/2006 03:29:48 PM
 * (C) Copyright 2006 Todos los Derechos Reservados
 * ---------------------------------------------------------------------------
 * Información de Repositorio:
 * ---------------------------------------------------------------------------
 * Rama de Desarrollo	: $Name:  $
 * Bloqueado por		: $Locker:  $
 * Estado de Revisión	: $State: Exp $
 * ---------------------------------------------------------------------------
 * Actualizaciones:
 * ---------------------------------------------------------------------------
 * $Log: VentanaConfirmacionTimeOut.java,v $
 * Revision 1.1  2007/12/05 18:13:01  programa8
 * Veersion 27 PARCHE  2007-12-05
 *
 * Revision 1.2  2006/07/10 20:24:31  programa8
 * Integracion con rama principal
 *
 * Revision 1.1.2.1  2006/06/12 12:13:46  programa4
 * Agregada VentanaConfirmacion que tiene un timeout para responder.
 *
 * Si se pasa el tiempo ejecuta la opcion por defecto
 *
 *
 * ===========================================================================
 */
/**
 * Clase VentanaConfirmacionTimeOut
 *
 * @author programa4 - $Author: programa8 $
 * @version $Revision: 1.1 $ - $Date: 2007/12/05 18:13:01 $
 * @since 01/06/2006
 */
package com.epa.testdriver;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.apache.log4j.Logger;

/**
 * @author programa4 - $Author: programa8 $
 * @version $Revision: 1.1 $ - $Date: 2007/12/05 18:13:01 $
 * @since 01/06/2006
 */
public class VentanaConfirmacionTimeOut extends VentanaConfirmacion implements
        Runnable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 9210606855186031536L;

	/**
     * Logger for this class
     */
    private static final Logger logger = Logger
            .getLogger(VentanaConfirmacionTimeOut.class);

    private final long timeOut;

    private Thread hiloTimeOut;

    private volatile Thread blinker;

    private javax.swing.JLabel jLabelTiempo = null;

    private Thread hiloVent;

    /**
     * @param _mensaje
     * @param _timeOut
     */
    public VentanaConfirmacionTimeOut(String _mensaje, long _timeOut) {
        super(_mensaje);
        this.timeOut = _timeOut;
    }

    /**
     * @param xMensaje
     * @param txtBtn0
     * @param txtBtn1
     * @param _timeOut
     */
    public VentanaConfirmacionTimeOut(String xMensaje, String txtBtn0,
            String txtBtn1, long _timeOut) {
        super(xMensaje, txtBtn0, txtBtn1);
        this.timeOut = _timeOut;
    }

    /**
     * @param xMensaje
     * @param parent
     * @param _timeOut
     */
    public VentanaConfirmacionTimeOut(String xMensaje, JFrame parent,
            long _timeOut) {
        super(xMensaje, parent);
        this.timeOut = _timeOut;
    }

    /**
     * @param xMensaje
     * @param parent
     * @param txtBtn0
     * @param txtBtn1
     * @param _timeOut
     */
    public VentanaConfirmacionTimeOut(String xMensaje, JFrame parent,
            String txtBtn0, String txtBtn1, long _timeOut) {
        super(xMensaje, parent, txtBtn0, txtBtn1);
        this.timeOut = _timeOut;
    }

    public void run() {
        if (logger.isDebugEnabled()) {
            logger.debug("run() - start");
        }
        int segundo = 1000;
        this.hiloTimeOut.start();
        Thread thisThread = Thread.currentThread();
        while (this.blinker == thisThread) {
            try {
                Thread.sleep(segundo);
                int tiempoActual = Integer.parseInt(this.getJLabelTiempo()
                        .getText());
                this.getJLabelTiempo().setText(String.valueOf(tiempoActual--));
            } catch (InterruptedException e) {
                logger.error("run()", e);
            }
            repaint();
        }
        dispose();

        if (logger.isDebugEnabled()) {
            logger.debug("run() - end");
        }
    }

    protected void initialize() {
        super.initialize();
        this.hiloVent = new Thread(this, "Hilo repintado");
        this.hiloTimeOut = new Thread(new Runnable() {
            public void run() {
                if (logger.isDebugEnabled()) {
                    logger.debug("run() - start");
                }

                try {
                    Thread.sleep(VentanaConfirmacionTimeOut.this.timeOut);
                    if (VentanaConfirmacionTimeOut.this.blinker != null) {
                        if (VentanaConfirmacionTimeOut.this.getOpcionDefault() == 0
                                || VentanaConfirmacionTimeOut.this
                                        .getOpcionDefault() == 1) {
                            VentanaConfirmacionTimeOut.this
                                    .setOpcion(VentanaConfirmacionTimeOut.this
                                            .getOpcionDefault());
                        }
                    }
                } catch (InterruptedException e) {
                    logger.error("run()", e);
                } catch (Throwable t) {
                    logger.error("run()", t);
                }
                VentanaConfirmacionTimeOut.this.blinker = null;

                if (logger.isDebugEnabled()) {
                    logger.debug("run() - end");
                }
            }
        });
        this.hiloTimeOut.setName("Hilo espera TimeOut");
        this.blinker = this.hiloVent;
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent e) {
                VentanaConfirmacionTimeOut.this.hiloVent.start();
            }
        });
    }

    /**
     * This method initializes jLabel1
     *
     * @return javax.swing.JLabel
     */
    private javax.swing.JLabel getJLabelTiempo() {
        if (logger.isDebugEnabled()) {
            logger.debug("getJLabelTiempo() - start");
        }

        if (this.jLabelTiempo == null) {
            this.jLabelTiempo = new javax.swing.JLabel();
            this.jLabelTiempo.setText(String
                    .valueOf((int) (this.timeOut / 1000)));
            this.jLabelTiempo.setFont(new java.awt.Font("Dialog",
                    java.awt.Font.BOLD, 18));
            this.jLabelTiempo.setFocusable(false);
        }

        if (logger.isDebugEnabled()) {
            logger.debug("getJLabelTiempo() - end");
        }
        return this.jLabelTiempo;
    }

    protected JPanel getJPanel5() {
        JPanel salida = super.getJPanel5();
        salida.add(this.getJLabelTiempo(), BorderLayout.CENTER);
        return salida;
    }

    public void dispose() {
        super.dispose();
        this.blinker = null;
    }
}
