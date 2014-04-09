/*
 * $Id: WindowsConsultaVoucher.java,v 1.9 2006/09/28 19:08:27 programa4 Exp $
 * ===========================================================================
 *
 * Proyecto : PuntoAgil Paquete : com.epa.ventas.cr.puntoAgil Programa :
 * WindowsConsultaVoucher.java Creado por : programa4 Creado en : 17/05/2006
 * 09:43:57 AM (C) Copyright 2006 Todos los Derechos Reservados
 * ---------------------------------------------------------------------------
 * Información de Repositorio:
 * ---------------------------------------------------------------------------
 * Rama de Desarrollo : $Name:  $ Bloqueado por : $Locker:  $ Estado de Revisión :
 * $State: Exp $
 * ---------------------------------------------------------------------------
 * Actualizaciones:
 * ---------------------------------------------------------------------------
 * $Log: WindowsConsultaVoucher.java,v $
 * Revision 1.9  2006/09/28 19:08:27  programa4
 * Actualizacion por requerimientos credicard
 * Se cargan datos de programas de lealtad segun banco seleccionado
 * Modificados mensajes de error para hacerlos mas legibles
 * Verificados titulos para indicar version del punto agil
 *
 * Revision 1.8  2006/08/25 18:17:37  programa4
 * Correccion en anulacion de cheque (para que no solicite lectura banda)
 * Y convertido PuntoAgilSubSistema en singleton en lugar de metodos estaticos
 * Revision 1.7 2006/07/05 15:25:39
 * programa4 Agregado soporte a provimillas y actualizado anulacion
 *
 * Revision 1.6 2006/06/27 15:34:28 programa4 Renombrado metodo que imprime
 * contenido
 *
 * Revision 1.5 2006/06/16 21:32:38 programa4 Agregada anulacion de pagos.
 * Actualizacion de datos con datos de los pagos de ventas, tarjeta de cred. epa
 * y abonos. Agregado aviso de cierre si han pasado mas de 24 horas del ultimo
 * cierre. Agregado numServicio para poder referir a abonos Agregadas consultas
 * de puntos
 *
 * Revision 1.4 2006/06/10 02:12:19 programa4 Eliminado mensaje de tarea
 *
 * Revision 1.3 2006/05/18 20:26:55 programa4 Agregada autorizacion a
 * MenuPuntoAgil y habilitada reimpresion de voucher
 *
 * Revision 1.2 2006/05/18 18:26:04 programa4 Agregado constructores ant para
 * automatizar compilacion Funcionan - Consulta Ultimo Voucher (con reimpresion
 * sin anulacion). - Pre cierre - Cierre
 *
 * Revision 1.1 2006/05/17 20:00:22 programa4 Version Inicial con Menu,
 * ConsultaUltimoVoucher y SubSistema
 *
 *
 * ===========================================================================
 */
/**
 * Clase WindowsConsultaVoucher
 *
 * @author programa4 - $Author: programa4 $
 * @version $Revision: 1.9 $ - $Date: 2006/09/28 19:08:27 $
 * @since 17/05/2006
 */
package com.epa.ventas.cr.puntoAgil;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.border.BevelBorder;

import org.apache.log4j.Logger;

import com.becoblohm.cr.gui.BarraTitulo;
import com.becoblohm.cr.gui.JHighlightButton;
import com.becoblohm.cr.utiles.MensajesVentanas;

/**
 * @author programa4 - $Author: programa4 $
 * @version $Revision: 1.9 $ - $Date: 2006/09/28 19:08:27 $
 * @since 17/05/2006
 */
public class WindowsConsultaVoucher extends JDialog implements KeyListener {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
		.getLogger(WindowsConsultaVoucher.class);

	private static final String VOUCHER = "Voucher:";

	private static final String REIMPRIMIR = "Reimprimir";

	private static final String SALIR = "Salir";

	private static final String ANULAR = "Anular";

	/**
	 * long serialVersionUID
	 */
	private static final long serialVersionUID = -8759031982255447460L;

	private JPanel jContentPane = null;

	private JPanel jPanelBotones = null;

	private JPanel jPanelVoucher = null;

	private JLabel jLabelVoucher = null;

	private JScrollPane jScrollPaneVoucher = null;

	private JTextPane jTextPaneVoucher = null;

	private JButton jButtonReimprimir = null;

	private JButton jButtonAnular = null;

	private JButton jButtonSalir = null;

	private JPanel jPanelTemp = null;

	private JPanel jPanelContenido = null;

	private JPanel jPanelTitulo = null;

	private DatosOperacionPuntoAgil datosOperacionPuntoAgil;

	private Thread hiloTimeOut;

	protected static final long TIMEOUT = 60;

	/**
	 * This is the default constructor
	 */
	public WindowsConsultaVoucher() {
		super();
		initialize();
	}

	/**
	 * @param dopa
	 *            Datos de la operacion punto agil asociada.
	 */
	public WindowsConsultaVoucher(DatosOperacionPuntoAgil dopa) {
		this();
		this.datosOperacionPuntoAgil = dopa;
	}

	/**
	 * This method initializes this
	 */
	private void initialize() {
		setSize(550, 390);
		this
			.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE); // Generated
		this.setTitle("Consulta de Voucher" + Constantes.TITULO_VERSION); // Generated
		this.setName("consultaVoucher"); // Generated
		this.setModal(true); // Generated
		setContentPane(getJContentPane());
		this.hiloTimeOut = new Thread(new Runnable() {
			public void run() {
				int i = 0;
				Thread myThread = Thread.currentThread();
				try {
					while (myThread == WindowsConsultaVoucher.this.hiloTimeOut
							&& i < WindowsConsultaVoucher.TIMEOUT) {
						i++;
						Thread.sleep(1000);
					}
				} catch (InterruptedException e) {
					logger.error("run()", e);
				}
				if (i >= WindowsConsultaVoucher.TIMEOUT) {
					MensajesVentanas
						.mensajeError("EXCEDIDO TIEMPO MAXIMO DE CONSULTA");
					WindowsConsultaVoucher.this.dispose();
				}
			}
		}, "Hilo TimeOut Consulta Voucher");
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowOpened(java.awt.event.WindowEvent _e) {
				super.windowOpened(_e);
				WindowsConsultaVoucher.this.hiloTimeOut.start();
				WindowsConsultaVoucher.this
					.getJButtonReimprimir()
					.requestFocus();
			}

			public void windowClosed(WindowEvent _e) {
				super.windowClosed(_e);
				WindowsConsultaVoucher.this.apagarHilo();
			}
		});
	}

	/**
	 * This method initializes jContentPane
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getJPanelTitulo(), java.awt.BorderLayout.NORTH); // Generated
			jContentPane
				.add(getJPanelContenido(), java.awt.BorderLayout.CENTER); // Generated
		}
		return jContentPane;
	}

	/**
	 * This method initializes jPanelContenido
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelContenido() {
		if (jPanelContenido == null) {
			jPanelContenido = new JPanel();
			jPanelContenido.setLayout(new BorderLayout()); // Generated
			jPanelContenido.add(
				getJPanelVoucher(), java.awt.BorderLayout.CENTER); // Generated
			jPanelContenido.add(getJPanelTemp(), java.awt.BorderLayout.EAST); // Generated
		}
		return jPanelContenido;
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

	/**
	 * This method initializes jPanelBotones
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelBotones() {
		if (jPanelBotones == null) {
			GridLayout gridLayout = new GridLayout();
			gridLayout.setColumns(1); // Generated
			gridLayout.setRows(3); // Generated
			gridLayout.setHgap(15); // Generated
			gridLayout.setVgap(15); // Generated
			jPanelBotones = new JPanel();

			jPanelBotones.setLayout(gridLayout); // Generated
			jPanelBotones.add(getJButtonReimprimir(), null); // Generated
			jPanelBotones.add(getJButtonAnular(), null); // Generated
			jPanelBotones.add(getJButtonSalir(), null); // Generated
		}
		return jPanelBotones;
	}

	/**
	 * This method initializes jPanelVoucher
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelVoucher() {
		if (jPanelVoucher == null) {
			jLabelVoucher = new JLabel();
			jLabelVoucher.setText(VOUCHER); // Generated
			jPanelVoucher = new JPanel();
			jPanelVoucher.setLayout(new BorderLayout()); // Generated
			jPanelVoucher.add(jLabelVoucher, java.awt.BorderLayout.NORTH); // Generated
			jPanelVoucher.add(
				getJScrollPaneVoucher(), java.awt.BorderLayout.CENTER); // Generated
		}
		return jPanelVoucher;
	}

	/**
	 * This method initializes jScrollPaneVoucher
	 *
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPaneVoucher() {
		if (jScrollPaneVoucher == null) {
			jScrollPaneVoucher = new JScrollPane();
			jScrollPaneVoucher.setViewportView(getJTextPaneVoucher()); // Generated
		}
		return jScrollPaneVoucher;
	}

	/**
	 * This method initializes jTextPaneVoucher
	 *
	 * @return javax.swing.JTextPane
	 */
	private JTextPane getJTextPaneVoucher() {
		if (jTextPaneVoucher == null) {
			jTextPaneVoucher = new JTextPane();
			jTextPaneVoucher.setFont(new java.awt.Font(
				"Monospaced", java.awt.Font.PLAIN, 12)); // Generated
			jTextPaneVoucher.setEnabled(false); // Generated
			jTextPaneVoucher.setEditable(false); // Generated
		}
		return jTextPaneVoucher;
	}

	/**
	 * This method initializes jButtonReimprimir
	 *
	 * @return javax.swing.JButton
	 */
	private JButton getJButtonReimprimir() {
		if (jButtonReimprimir == null) {
			jButtonReimprimir = new JHighlightButton(new java.awt.Color(
				192, 192, 255));
			jButtonReimprimir.setText(REIMPRIMIR); // Generated
			jButtonReimprimir.setBackground(new java.awt.Color(226, 226, 222)); // Generated
			jButtonReimprimir.addKeyListener(this);
			jButtonReimprimir.setBorder(new javax.swing.border.SoftBevelBorder(
				BevelBorder.RAISED));
			jButtonReimprimir.setAction(new AbstractAction(REIMPRIMIR) {
				public void actionPerformed(ActionEvent e) {
					reimprimir();
				}
			});
		}
		return jButtonReimprimir;
	}

	/**
	 * This method initializes jButtonAnular
	 *
	 * @return javax.swing.JButton
	 */
	private JButton getJButtonAnular() {
		if (jButtonAnular == null) {
			jButtonAnular = new JHighlightButton(new java.awt.Color(
				192, 192, 255));
			jButtonAnular.setEnabled(false);
			jButtonAnular.setText(ANULAR); // Generated
			jButtonAnular.setBackground(new java.awt.Color(226, 226, 222)); // Generated
			jButtonAnular.setBorder(new javax.swing.border.SoftBevelBorder(
				BevelBorder.RAISED));
			jButtonAnular.addKeyListener(this);
			jButtonAnular.setAction(new AbstractAction(ANULAR) {
				public void actionPerformed(ActionEvent e) {
					anular();
				}
			});

		}
		return jButtonAnular;
	}

	/**
	 * This method initializes jButtonSalir
	 *
	 * @return javax.swing.JButton
	 */
	private JButton getJButtonSalir() {
		if (jButtonSalir == null) {
			jButtonSalir = new JHighlightButton(new java.awt.Color(
				192, 192, 255));
			jButtonSalir.setText(SALIR); // Generated
			jButtonSalir.setBackground(new java.awt.Color(226, 226, 222)); // Generated
			jButtonSalir.addKeyListener(this);
			jButtonSalir.setBorder(new javax.swing.border.SoftBevelBorder(
				BevelBorder.RAISED));
			jButtonSalir.setAction(new AbstractAction(SALIR) {
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
		}
		return jButtonSalir;
	}

	/**
	 * This method initializes jPaneltEMP
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelTemp() {
		if (jPanelTemp == null) {
			jPanelTemp = new JPanel();
			jPanelTemp.add(getJPanelBotones(), null); // Generated
		}
		return jPanelTemp;
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
			if (arg0.getSource().equals(getJButtonReimprimir())) {
				reimprimir();
			} else if (arg0.getSource().equals(getJButtonAnular())) {
				anular();
			} else if (arg0.getSource().equals(getJButtonSalir())) {
				dispose();
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
	 * Asigna el texto del voucher
	 *
	 * @param _t
	 *            texto del voucher
	 */
	void setTextoVoucher(String _t) {
		this.getJTextPaneVoucher().setText(_t);
	}

	/**
	 * Retorna el texto del voucher
	 *
	 */
	String getTextoVoucher() {
		return this.getJTextPaneVoucher().getText();
	}

	void apagarHilo() {
		this.hiloTimeOut = null;
	}

	/**
	 * Activa o desactiva el botón de anular voucher
	 *
	 * @param _b
	 *            si estara disponible o no el botón de anulación
	 */
	void setVisibleAnular(boolean _b) {
		this.getJButtonAnular().setVisible(_b);
	}

	void reimprimir() {
		apagarHilo();
		PuntoAgilSubSistema.getInstance().imprimirContenido(
			this.getTextoVoucher(), true);
		dispose();
	}

	void anular() {
		if (this.getJButtonAnular().isEnabled()) {
			apagarHilo();
			PuntoAgilSubSistema.getInstance().anularOperacionPuntoAgil(
				this.datosOperacionPuntoAgil);
			dispose();
		}
	}
}
