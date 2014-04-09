/*
 * Creado el 09-dic-2004
 *
 * Para cambiar la plantilla para este archivo generado vaya a
 * Ventana&gt;Preferencias&gt;Java&gt;Generación de código&gt;Código y comentarios
 */
package com.becoblohm.cr.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import org.apache.log4j.Logger;

import com.becoblohm.cr.utiles.MensajesVentanas;

/**
 * @author Programa8
 *
 * Para cambiar la plantilla para este comentario de tipo generado vaya a
 * Ventana&gt;Preferencias&gt;Java&gt;Generación de código&gt;Código y
 * comentarios
 */
public class VentanaEspera extends JDialog implements Runnable {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(VentanaEspera.class);

    private javax.swing.JPanel jContentPane = null;

    private JLabel jLabel = null;

    private int espera;

    private Thread hiloVent;

    private Thread hilo;

    private Thread hiloTimeOut;

    private volatile Thread blinker;

    private Object returnValue;

    private Throwable throwed = null;

    private JButton jButton = null;

    private JPanel jPanel = null;

    private CancelarAction actCancelar;

    private final boolean cancelable;

    private JPanel jPanel1 = null;

    private JPanel jPanel2 = null;

    private JLabel txaMensaje = null;

    private JPanel jPanel3 = null;

    private class CancelarAction extends AbstractAction {

        CancelarAction() {
            super(
                    "Cancelar",
                    new ImageIcon(
                            CancelarAction.class
                                    .getResource("/com/becoblohm/cr/gui/resources/icons/ix24x24/delete2.png")));
            jContentPane.getInputMap().put(KeyStroke.getKeyStroke("escape"),
                    "cancelar");
            jContentPane.getActionMap().put("cancelar", this);
        }

        public void actionPerformed(ActionEvent e) {
            throwed = new InterruptedException(
                    "Acción cancelada por el usuario");
            blinker = null;
        }
    }

    protected VentanaEspera(boolean ventCancelable, String mensaje) {
        super(MensajesVentanas.ventanaActiva);
        this.cancelable = ventCancelable;
        initialize();
        if (mensaje != null) {
            this.getTxaMensaje().setText(mensaje);
        }
        hiloVent = new Thread(this, "Hilo repintado");
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent e) {
                if (logger.isDebugEnabled()) {
                    logger
                            .debug("windowOpened(java.awt.event.WindowEvent) - start");
                }

                hiloVent.start();

                if (logger.isDebugEnabled()) {
                    logger
                            .debug("windowOpened(java.awt.event.WindowEvent) - end");
                }
            }
        });
    }

    /**
     * Constructor para abrir una ventana de espera por x milisegundos
     */
    protected VentanaEspera(int milliseconds, String mensaje) {
        this(false, mensaje);
        espera = milliseconds;
        hiloTimeOut = new Thread(new Runnable() {
            public void run() {
                if (logger.isDebugEnabled()) {
                    logger.debug("run() - start");
                }

                try {
                    Thread.sleep(espera);
                } catch (InterruptedException e) {
                    logger.error("run()", e);
                } catch (Throwable t) {
                    logger.error("run()", t);
                }
                blinker = null;
                if (hilo != null) {
                    hilo.interrupt();
                    hilo = null;
                    returnValue = null;
                }

                if (logger.isDebugEnabled()) {
                    logger.debug("run() - end");
                }
            }
        });
        hiloTimeOut.setName("Hilo espera");
        blinker = hiloVent;
    }

    VentanaEspera(final Method m, final Object o, final Object[] args,
            boolean cancelable) {
        this(cancelable, null);
        initHiloMetodo(m, o, args);
    }

    VentanaEspera(final Method m, final Object o, final Object[] args,
            int timeout) {
        this(timeout, null);
        initHiloMetodo(m, o, args);
    }

    VentanaEspera(final Method m, final Object o, final Object[] args,
            boolean cancelable, String mensaje) {
        this(cancelable, mensaje);
        initHiloMetodo(m, o, args);
    }

    VentanaEspera(final Method m, final Object o, final Object[] args,
            int timeout, String mensaje) {
        this(timeout, mensaje);
        initHiloMetodo(m, o, args);
    }

    /**
     * @param m
     * @param o
     * @param args
     */
    private void initHiloMetodo(final Method m, final Object o,
            final Object[] args) {
        hilo = new Thread(new Runnable() {
            public void run() {
                if (logger.isDebugEnabled()) {
                    logger.debug("run() - start");
                }

                try {
                    returnValue = m.invoke(o, args);
                } catch (InvocationTargetException e) {
                    logger.error("run()", e);
                    throwed = e.getCause() == null ? e : e.getCause();
                } catch (Throwable t) {
                    logger.error("run()", t);
                    throwed = t;
                }
                blinker = null;

                if (logger.isDebugEnabled()) {
                    logger.debug("run() - end");
                }
            }
        });
        hilo.setName("Hilo ejecucion 2° plano");
        blinker = hiloVent;
    }

    public VentanaEspera(final Method m, final Object o, final Object[] args) {
        this(m, o, args, false);
    }

    /**
     * Centra la ventana por un número definido de milisegundos
     *
     * @param mill
     */
    public static void esperar(int mill) {
        esperar(mill, null);
    }

    /**
     * Centra la ventana por un número definido de milisegundos
     *
     * @param mill
     */
    public static void esperar(int mill, String mensaje) {
        if (logger.isDebugEnabled()) {
            logger.debug("esperar(int) - start");
        }

        VentanaEspera ventana = new VentanaEspera(mill, null);
        ventana.centrarVentana();
        ventana.setModal(true);
        ventana
                .getRootPane()
                .setBorder(
                        javax.swing.BorderFactory
                                .createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        ventana.getRootPane().setBorder(
                javax.swing.BorderFactory.createLineBorder(java.awt.Color.gray,
                        1));
        MensajesVentanas.stackDialog(ventana, false);
        ventana = null;

        if (logger.isDebugEnabled()) {
            logger.debug("esperar(int) - end");
        }
    }

    /**
     *
     * @param m
     * @param o
     * @param args
     * @return Object
     * @throws Throwable
     *             Object
     */
    public static Object ejecutarEsperar(Method m, Object o, Object[] args)
            throws Throwable {
        return ejecutarEsperar(m, o, args, false);
       
    }

    /**
     *
     * @param m
     * @param o
     * @param args
     * @param mensaje
     * @return Object
     * @throws Throwable
     *             Object
     */
    public static Object ejecutarEsperar(Method m, Object o, Object[] args,
            String mensaje) throws Throwable {
        return ejecutarEsperar(m, o, args, false, mensaje);
    }

    /**
     *
     * @param m
     * @param o
     * @param args
     * @param cancelable
     * @return Object
     * @throws Throwable
     *             Object
     */
    public static Object ejecutarEsperar(Method m, Object o, Object[] args,
            boolean cancelable) throws Throwable {
        return ejecutarEsperar(m, o, args, cancelable, null);
    }

    /**
     *
     * @param m
     * @param o
     * @param args
     * @param cancelable
     * @param mensaje
     * @return Object
     * @throws Throwable
     *             Object
     */
    public static Object ejecutarEsperar(Method m, Object o, Object[] args,
            boolean cancelable, String mensaje) throws Throwable {
        if (logger.isDebugEnabled()) {
            logger.debug("ejecutarEsperar(Method, Object, Object[]) - start");
        }
        VentanaEspera ventana = new VentanaEspera(m, o, args, cancelable,
                mensaje);
        return ejecutarEsperar(ventana);
    }

    /**
     * Este metodo ejecuta el metodo del objeto suministrado, si se cumple lapso
     * del timeout se interrumpe la ejecucion del metodo.
     *
     * @param m
     * @param o
     * @param args
     * @param timeOut
     * @return Object
     * @throws Throwable
     */
    public static Object ejecutarEsperar(Method m, Object o, Object[] args,
            int timeOut) throws Throwable {
        return ejecutarEsperar(m, o, args, timeOut, null);
    }

    /**
     * Este metodo ejecuta el metodo del objeto suministrado, si se cumple lapso
     * del timeout se interrumpe la ejecucion del metodo.
     *
     * @param m
     * @param o
     * @param args
     * @param timeOut
     * @return Object
     * @throws Throwable
     */
    public static Object ejecutarEsperar(Method m, Object o, Object[] args,
            int timeOut, String mensaje) throws Throwable {
        if (logger.isDebugEnabled()) {
            logger.debug("ejecutarEsperar(Method, Object, Object[]) - start");
        }
        VentanaEspera ventana = new VentanaEspera(m, o, args, timeOut, mensaje);
        return ejecutarEsperar(ventana);
    }

    /**
     * @param ventana
     * @return result
     * @throws Throwable
     */
    private static Object ejecutarEsperar(VentanaEspera ventana)
            throws Throwable {
        Object result = null;
        ventana.centrarVentana();
        ventana.setModal(true);
        MensajesVentanas.stackDialog(ventana, false);
        if (ventana.getThrowed() != null) {
            throw ventana.getThrowed();
        }
        result = ventana.getReturnValue();
        ventana = null;
        if (logger.isDebugEnabled()) {
            logger.debug("ejecutarEsperar(Method, Object, Object[]) - end");
        }
        return result;
    }

    /**
     * This method initializes this
     */
    private void initialize() {
        if (logger.isDebugEnabled()) {
            logger.debug("initialize() - start");
        }

        this.setResizable(false);
        this
                .setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        this.setBackground(new java.awt.Color(226, 226, 222));
        this.setSize(284, 190);
        this.setContentPane(getJContentPane());

        if (logger.isDebugEnabled()) {
            logger.debug("initialize() - end");
        }
    }

    public void centrarVentana() {
        if (logger.isDebugEnabled()) {
            logger.debug("centrarVentana() - start");
        }

        int width = (int) getSize().getWidth();
        int height = (int) getSize().getHeight();
        setUndecorated(false);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screen.width - width) / 2;
        int y = (screen.height - height) / 2;
        setBounds(x, y, width, height);

        if (logger.isDebugEnabled()) {
            logger.debug("centrarVentana() - end");
        }
    }

    /**
     * This method initializes jContentPane
     *
     * @return javax.swing.JPanel
     */
    private javax.swing.JPanel getJContentPane() {
        if (logger.isDebugEnabled()) {
            logger.debug("getJContentPane() - start");
        }

        if (jContentPane == null) {
            jLabel = new JLabel();
            jContentPane = new javax.swing.JPanel();
            jContentPane.setLayout(new BorderLayout());
            jContentPane.setBackground(new java.awt.Color(226, 226, 222));
            jContentPane.setFont(new java.awt.Font("Dialog",
                    java.awt.Font.PLAIN, 10));
            jContentPane
                    .setBorder(javax.swing.BorderFactory
                            .createCompoundBorder(
                                    javax.swing.BorderFactory.createLineBorder(
                                            java.awt.Color.darkGray, 1),
                                    javax.swing.BorderFactory
                                            .createBevelBorder(javax.swing.border.BevelBorder.RAISED)));
            jContentPane.setPreferredSize(new java.awt.Dimension(315, 180));
            jLabel.setText("Espere un momento");
            jLabel.setPreferredSize(new java.awt.Dimension(250, 50));
            jLabel
                    .setIcon(new ImageIcon(
                            getClass()
                                    .getResource(
                                            "/com/becoblohm/cr/gui/resources/icons/i48x48/apps/time.png")));
            jLabel.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 18));
            jLabel.setForeground(java.awt.Color.white);
            actCancelar = new CancelarAction();
            jContentPane.add(getJPanel1(), java.awt.BorderLayout.SOUTH);
            jContentPane.add(getJPanel(), java.awt.BorderLayout.CENTER);
            if (!cancelable) {
                actCancelar.setEnabled(false);
            }
        }

        if (logger.isDebugEnabled()) {
            logger.debug("getJContentPane() - end");
        }
        return jContentPane;
    }

    public void run() {
        if (logger.isDebugEnabled()) {
            logger.debug("run() - start");
        }

        if (hilo != null) {
            hilo.start();
        }
        if (hiloTimeOut != null) {
            hiloTimeOut.start();
        }
        Thread thisThread = Thread.currentThread();
        while (blinker == thisThread) {
            try {
                Thread.sleep(1000);
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

    public Object getReturnValue() {
        return returnValue;
    }

    public Throwable getThrowed() {
        return throwed;
    }

    /**
     * This method initializes jButton
     *
     * @return javax.swing.JButton
     */
    private JButton getJButton() {
        if (jButton == null) {
            jButton = new JHighlightButton(actCancelar);
            jButton.setBackground(new java.awt.Color(226, 226, 222));
            if (!cancelable) {
                jButton.setVisible(false);
                jButton.setPreferredSize(new java.awt.Dimension(65, 26));
            }
            jButton.addKeyListener(new java.awt.event.KeyAdapter() {
                public void keyPressed(java.awt.event.KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_ENTER
                            || e.getKeyCode() == KeyEvent.VK_SPACE) {
                        jButton.doClick();
                    }
                }
            });
        }
        return jButton;
    }

    /**
     * This method initializes jPanel
     *
     * @return javax.swing.JPanel
     */
    private JPanel getJPanel() {
        if (jPanel == null) {
            FlowLayout flowLayout2 = new FlowLayout();
            jPanel = new JPanel();
            jPanel.setLayout(flowLayout2);
            jPanel.setBackground(new java.awt.Color(242, 242, 238));
            jPanel.setPreferredSize(new java.awt.Dimension(260, 60));
            jPanel.setBorder(javax.swing.BorderFactory.createLineBorder(
                    new java.awt.Color(97, 107, 127), 1));
            flowLayout2.setVgap(0);
            jPanel.add(getJPanel2(), null);
            jPanel.add(getJPanel3(), null);
        }
        return jPanel;
    }

    /**
     * This method initializes jPanel1
     *
     * @return javax.swing.JPanel
     */
    private JPanel getJPanel1() {
        if (jPanel1 == null) {
            FlowLayout flowLayout1 = new FlowLayout();
            jPanel1 = new JPanel();
            jPanel1.setLayout(flowLayout1);
            jPanel1.setBackground(new java.awt.Color(226, 226, 222));
            jPanel1.setPreferredSize(new java.awt.Dimension(10, 40));
            flowLayout1.setAlignment(java.awt.FlowLayout.RIGHT);
            jPanel1.add(getJButton(), null);
        }
        return jPanel1;
    }

    /**
     * This method initializes jPanel2
     *
     * @return javax.swing.JPanel
     */
    private JPanel getJPanel2() {
        if (jPanel2 == null) {
            jPanel2 = new JPanel();
            jPanel2.setBackground(new java.awt.Color(69, 107, 127));
            jPanel2.setPreferredSize(new java.awt.Dimension(280, 60));
            jPanel2.add(jLabel, null);
        }
        return jPanel2;
    }

    /**
     * This method initializes txaMensaje
     *
     * @return javax.swing.JTextArea
     */
    private JLabel getTxaMensaje() {
        if (txaMensaje == null) {
            txaMensaje = new JLabel();
            txaMensaje.setText("Actividad en proceso...");
            txaMensaje.setBackground(new java.awt.Color(242, 242, 238));
            txaMensaje.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD,
                    12));
            txaMensaje
                    .setHorizontalAlignment(javax.swing.SwingConstants.CENTER); // Generated

            // txaMensaje.setPreferredSize(new java.awt.Dimension(250,25));
            // txaMensaje.setWrapStyleWord(true);
            // txaMensaje.setLineWrap(true);
            // txaMensaje.setEditable(false);
            txaMensaje.setEnabled(true);
            txaMensaje.setFocusable(false);
        }
        return txaMensaje;
    }

    /**
     * This method initializes jPanel3
     *
     * @return javax.swing.JPanel
     */
    private JPanel getJPanel3() {
        if (jPanel3 == null) {
            FlowLayout flowLayout3 = new FlowLayout();
            jPanel3 = new JPanel();
            jPanel3.setLayout(flowLayout3);
            jPanel3.setPreferredSize(new Dimension(250, 50));
            jPanel3.setBackground(new java.awt.Color(242, 242, 238));
            jPanel3.setEnabled(false);
            flowLayout3.setVgap(10);
            jPanel3.add(getTxaMensaje(), null);
        }
        return jPanel3;
    }
} // @jve:decl-index=0:visual-constraint="129,57"
