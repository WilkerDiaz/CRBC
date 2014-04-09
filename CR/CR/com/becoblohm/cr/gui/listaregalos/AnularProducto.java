/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblohm.cr.gui
 * Programa   : AnularProducto.java
 * Creado por : Victor Medina
 * Creado en  : Mar 1, 2004 - 9:30:22 AM
 *
 * (c) Becoblohm, C.A.
 * -----------------------------------------------------------------------------
 * 				Actualizaciones
 * -----------------------------------------------------------------------------
 * Versión     : 
 * Fecha       : 
 * Analista    : 
 * Descripción : 
 * =============================================================================
 */
package com.becoblohm.cr.gui.listaregalos;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.DecimalFormat;
import java.util.EventListener;

import javax.swing.JDialog;
import javax.swing.SwingUtilities;
import javax.swing.border.BevelBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.apache.log4j.Logger;

import com.becoblohm.cr.CR;
import com.becoblohm.cr.excepciones.ConexionExcepcion;
import com.becoblohm.cr.excepciones.ExcepcionCr;
import com.becoblohm.cr.gui.JHighlightButton;
import com.becoblohm.cr.manejarservicio.DetalleServicio;
import com.becoblohm.cr.mediadoresbd.MediadorBD;
import com.becoblohm.cr.utiles.MensajesVentanas;

/**
 * @author vmedina
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class AnularProducto extends JDialog implements ActionListener, ComponentListener, KeyListener, EventListener, DocumentListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(AnularProducto.class);

	private javax.swing.JPanel jContentPane = null;
	ModeloTablaLR modeloTablaAnularProducto = null;
	private DecimalFormat df = new DecimalFormat("#,##0.00");
	private javax.swing.JPanel jPanel = null;
	private javax.swing.JTextField jTextField = null;
	private javax.swing.JPanel jPanel1 = null;
	private javax.swing.JPanel jPanel2 = null;
	private javax.swing.JButton jButtonCancelar = null;
	private javax.swing.JButton jButtonAceptar = null;
	private javax.swing.JScrollPane jScrollPane = null;
	private javax.swing.JTable jTable = null;
	private javax.swing.JPanel jPanel3 = null;
	private javax.swing.JPanel jPanel4 = null;
	private javax.swing.JLabel jLabel1 = null;
	private int filaSeleccionada=0;
	private int filaSeleccionadaInicial=0;
	private String  productoABuscar;
	private javax.swing.JPanel jPanel5 = null;
	private javax.swing.JTextField jTextField1 = null;
	private int caso=0;

	/**
	 * This is the default constructor
	 */
	public AnularProducto(int filaACambiar, int caso) {
		super(MensajesVentanas.ventanaActiva);
		this.caso = caso;
		modeloTablaAnularProducto = new ModeloTablaLR(caso);
		initialize();
		
		filaSeleccionadaInicial = filaACambiar;
		
		jTextField.addKeyListener(this);
		jTextField.addActionListener(this);
		
		jTextField1.addKeyListener(this);
		jTextField1.addActionListener(this);
		
		jButtonCancelar.addKeyListener(this);
		jButtonCancelar.addActionListener(this);
		
		jButtonAceptar.addKeyListener(this);
		jButtonAceptar.addActionListener(this);
		
		jTable.addKeyListener(this);		
		
		productoABuscar = ((DetalleServicio)CR.meServ.getListaRegalos().getDetallesServicio().elementAt(filaSeleccionadaInicial)).getProducto().getCodProducto().toString();
		jTextField.setText(productoABuscar);
		jTextField.selectAll();
		try {
			modeloTablaAnularProducto.llenarTablaAnularProd(productoABuscar, caso, false);
			this.getJTable().setRowSelectionInterval(0,0);
			float cantidadProd = ((DetalleServicio)CR.meServ.getListaRegalos().getDetallesServicio().elementAt(filaSeleccionadaInicial)).getCantidad(); 
			jTextField1.setText(String.valueOf(cantidadProd <= 1 ? df.format(cantidadProd) : df.format(1)));
		} catch (ExcepcionCr e1) {
			modeloTablaAnularProducto.iniciarDatosTabla(this.getJTable().getColumnCount());
			MensajesVentanas.mensajeError(e1.getMensaje());
		} catch (ConexionExcepcion e1) {
			modeloTablaAnularProducto.iniciarDatosTabla(this.getJTable().getColumnCount());
			MensajesVentanas.mensajeError(e1.getMensaje());
		}
		CR.inputEscaner.getDocument().addDocumentListener(this);
	}

	
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setTitle("Anular Producto");
		this.setSize(650, 362);
		this.setContentPane(getJContentPane());
		this.setUndecorated(true);
		this.setModal(true);
		this.setBackground(new java.awt.Color(226,226,222));
		this.addComponentListener(this);
	}
	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(new FlowLayout());
			jContentPane.add(getJPanel3(), null);
			jContentPane.add(getJPanel2(), null);
			jContentPane.setBorder(new javax.swing.border.SoftBevelBorder(BevelBorder.RAISED));
			jContentPane.setBackground(new java.awt.Color(226,226,222));
		}
		return jContentPane;
	}
	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
	 */
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
			CR.inputEscaner.getDocument().removeDocumentListener(this);
			dispose();
			try {
				finalize();
			} catch (Throwable e1) {
				e1.printStackTrace();
			}
		}
		
		//Mapeo de TAB sobre el componente jTable y jTextField
		else if(e.getKeyCode() == KeyEvent.VK_TAB){
			if(e.getSource().equals(this.getJTable())){
				this.getJTextField1().requestFocus();	
				jTextField1.selectAll();		
			}
			if(e.getSource().equals(this.getJTextField1()))
				this.getJButtonAceptar().requestFocus();
		}	
		
		else if(e.getKeyCode() == KeyEvent.VK_ENTER)
			if(e.getSource().equals(this.getJTextField())) {
				CR.inputEscaner.getDocument().removeDocumentListener(this);
				try {
					modeloTablaAnularProducto.llenarTablaAnularProd(jTextField.getText().trim(), caso, true);
					productoABuscar = jTextField.getText().trim();
					this.getJTable().requestFocus();
					this.getJTable().setRowSelectionInterval(0,0);
				} catch (ExcepcionCr e1) {
					logger.error("keyPressed(KeyEvent)", e1);

					modeloTablaAnularProducto.iniciarDatosTabla(this.getJTable().getColumnCount());
					MensajesVentanas.mensajeError(e1.getMensaje());
				} catch (ConexionExcepcion e1) {
					logger.error("keyPressed(KeyEvent)", e1);

					modeloTablaAnularProducto.iniciarDatosTabla(this.getJTable().getColumnCount());
					MensajesVentanas.mensajeError(e1.getMensaje());
				}
				CR.inputEscaner.getDocument().addDocumentListener(this);
			}
			
			else if(e.getSource().equals(this.getJTable())) {
				if(jTable.getSelectedRow() != 0)
					jTable.setRowSelectionInterval(jTable.getSelectedRow()-1,jTable.getSelectedRow()-1);
				else 
					jTable.setRowSelectionInterval(jTable.getRowCount()-1,jTable.getRowCount()-1);
					
				this.getJTextField1().requestFocus();
				this.getJTextField1().selectAll();
			}

			else if(e.getSource().equals(this.getJTextField1()))
				this.getJButtonAceptar().requestFocus();
			else if(e.getSource().equals(this.getJButtonAceptar()))
				getJButtonAceptar().doClick();
			else if(e.getSource().equals(this.getJButtonCancelar()))
				getJButtonCancelar().doClick();		

		if (logger.isDebugEnabled())
			logger.debug("keyPressed(KeyEvent) - end");
	}

	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
	 */
	public void keyReleased(KeyEvent e) {
		if (e.getSource().equals(getJTextField1())) {
			String textoFormateado = CR.me.formatearCampoNumerico(getJTextField1().getText(), e);
			if (textoFormateado!=null)
				getJTextField1().setText(textoFormateado);
		}
	}

	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
	 */
	public void keyTyped(KeyEvent e) {

	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel() {
		if (logger.isDebugEnabled())
			logger.debug("getJPanel() - start");

		if(jPanel == null) {
			jPanel = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout1 = new java.awt.FlowLayout();
			layFlowLayout1.setAlignment(java.awt.FlowLayout.LEFT);
			layFlowLayout1.setHgap(1);
			layFlowLayout1.setVgap(1);
			jPanel.setLayout(layFlowLayout1);
			jPanel.add(getJTextField(), null);
			jPanel.setPreferredSize(new java.awt.Dimension(590,45));
			jPanel.setBackground(new java.awt.Color(242,242,238));
			jPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Identificador", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12), java.awt.Color.black));
		}

		if (logger.isDebugEnabled())
			logger.debug("getJPanel() - end");
		return jPanel;
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private javax.swing.JTextField getJTextField() {
		if (logger.isDebugEnabled())
			logger.debug("getJTextField() - start");

		if(jTextField == null) {
			jTextField = new javax.swing.JTextField();
			jTextField.setPreferredSize(new java.awt.Dimension(200,20));
		}

		if (logger.isDebugEnabled())
			logger.debug("getJTextField() - end");
		return jTextField;
	}

	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel1() {
		if (logger.isDebugEnabled())
			logger.debug("getJPanel1() - start");

		if(jPanel1 == null) {
			jPanel1 = new javax.swing.JPanel();
			jPanel1.add(getJScrollPane(), null);
			jPanel1.setPreferredSize(new java.awt.Dimension(590,120));
			jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Detalle de la Transacción", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12), java.awt.Color.black));
			jPanel1.setBackground(new java.awt.Color(242,242,238));
		}

		if (logger.isDebugEnabled())
			logger.debug("getJPanel1() - end");
		return jPanel1;
	}

	/**
	 * This method initializes jPanel2
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel2() {
		if (logger.isDebugEnabled())
			logger.debug("getJPanel2() - start");

		if(jPanel2 == null) {
			jPanel2 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout11 = new java.awt.FlowLayout();
			layFlowLayout11.setAlignment(java.awt.FlowLayout.RIGHT);
			layFlowLayout11.setHgap(0);
			layFlowLayout11.setVgap(0);
			jPanel2.setLayout(layFlowLayout11);
			jPanel2.add(getJButtonAceptar(), null);
			jPanel2.add(getJButtonCancelar(), null);
			jPanel2.setPreferredSize(new java.awt.Dimension(620,35));
			jPanel2.setBackground(new java.awt.Color(226,226,222));
		}

		if (logger.isDebugEnabled())
			logger.debug("getJPanel2() - end");
		return jPanel2;
	}

	/**
	 * This method initializes jButtonCancelar
	 * 
	 * @return javax.swing.JButton
	 */
	private javax.swing.JButton getJButtonCancelar() {
		if (logger.isDebugEnabled())
			logger.debug("getJButton() - start");

		if(jButtonCancelar == null) {
			jButtonCancelar = new JHighlightButton();
			jButtonCancelar.setText("Cancelar");
			jButtonCancelar.setBackground(new java.awt.Color(226,226,222));
			jButtonCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix16x16/delete2.png")));
		}

		if (logger.isDebugEnabled())
			logger.debug("getJButton() - end");
		return jButtonCancelar;
	}

	/**
	 * This method initializes jButtonAceptar
	 * 
	 * @return javax.swing.JButton
	 */
	private javax.swing.JButton getJButtonAceptar() {
		if (logger.isDebugEnabled())
			logger.debug("getJButton1() - start");

		if(jButtonAceptar == null) {
			jButtonAceptar = new JHighlightButton();
			jButtonAceptar.setText("Aceptar");
			jButtonAceptar.setBackground(new java.awt.Color(226,226,222));
			jButtonAceptar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix16x16/check2.png")));
		}

		if (logger.isDebugEnabled())
			logger.debug("getJButton1() - end");
		return jButtonAceptar;
	}
	/**
	 * This method initializes jTable
	 * 
	 * @return javax.swing.JTable
	 */
	private javax.swing.JTable getJTable() {
		if (logger.isDebugEnabled())
			logger.debug("getJTable() - start");

		if(jTable == null) {
			jTable = new javax.swing.JTable(modeloTablaAnularProducto);
			jTable.getTableHeader().setReorderingAllowed(false) ;
			jTable.getColumnModel().getColumn(0).setPreferredWidth(88);
			jTable.getColumnModel().getColumn(1).setPreferredWidth(150);
			jTable.getColumnModel().getColumn(2).setPreferredWidth(65);
			jTable.getColumnModel().getColumn(3).setPreferredWidth(75);
			jTable.getColumnModel().getColumn(4).setPreferredWidth(75);
			jTable.getColumnModel().getColumn(5).setPreferredWidth(76);
			jTable.setLocale(new java.util.Locale("es", "VE", ""));
			jTable.setRowHeight(jTable.getRowHeight()+3);
			jTable.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			jTable.setBackground(new java.awt.Color(242,242,238));
		}

		if (logger.isDebugEnabled())
			logger.debug("getJTable() - end");
		return jTable;
	}
	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private javax.swing.JScrollPane getJScrollPane() {
		if (logger.isDebugEnabled())
			logger.debug("getJScrollPane() - start");

		if(jScrollPane == null) {
			jScrollPane = new javax.swing.JScrollPane();
			jScrollPane.setViewportView(getJTable());
			jScrollPane.setPreferredSize(new java.awt.Dimension(570,80));
		}

		if (logger.isDebugEnabled())
			logger.debug("getJScrollPane() - end");
		return jScrollPane;
	}
	/**
	 * This method initializes jPanel3
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel3() {
		if (logger.isDebugEnabled())
			logger.debug("getJPanel3() - start");

		if(jPanel3 == null) {
			jPanel3 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout12 = new java.awt.FlowLayout();
			layFlowLayout12.setHgap(0);
			layFlowLayout12.setVgap(0);
			jPanel3.setLayout(layFlowLayout12);
			jPanel3.add(getJPanel4(), null);
			jPanel3.add(getJPanel(), null);
			jPanel3.add(getJPanel1(), null);
			jPanel3.add(getJPanel5(), null);
			jPanel3.setPreferredSize(new java.awt.Dimension(620,275));
			jPanel3.setBackground(new java.awt.Color(242,242,238));
			jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(69,107,127),1));
		}

		if (logger.isDebugEnabled())
			logger.debug("getJPanel3() - end");
		return jPanel3;
	}
	/**
	 * This method initializes jPanel4
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel4() {
		if (logger.isDebugEnabled())
			logger.debug("getJPanel4() - start");

		if(jPanel4 == null) {
			jPanel4 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout2 = new java.awt.FlowLayout();
			layFlowLayout2.setAlignment(java.awt.FlowLayout.LEFT);
			layFlowLayout2.setVgap(10);
			layFlowLayout2.setHgap(5);
			jPanel4.setLayout(layFlowLayout2);
			jPanel4.add(getJLabel1(), null);
			jPanel4.setPreferredSize(new java.awt.Dimension(620,50));
			jPanel4.setBackground(new java.awt.Color(69,107,127));
		}

		if (logger.isDebugEnabled())
			logger.debug("getJPanel4() - end");
		return jPanel4;
	}
	/**
	 * This method initializes jLabel1
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel1() {
		if (logger.isDebugEnabled())
			logger.debug("getJLabel1() - start");

		if(jLabel1 == null) {
			jLabel1 = new javax.swing.JLabel();
			jLabel1.setText("Anular Producto");
			jLabel1.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 18));
			jLabel1.setForeground(java.awt.Color.white);
			jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix32x32/shoppingbasket_delete.png")));
		}

		if (logger.isDebugEnabled())
			logger.debug("getJLabel1() - end");
		return jLabel1;
	}

//	private void reestablecerTabla() {
//		if (logger.isDebugEnabled())
//			logger.debug("reestablecerTabla() - start");
//
//		jTextField.setText(productoABuscar);
//		try {
//			modeloTablaAnularProducto.llenarTablaAnularProd(productoABuscar, caso);
//		} catch (ExcepcionCr e) {
//			logger.error("reestablecerTabla()", e);
//		} catch (ConexionExcepcion e) {
//			logger.error("reestablecerTabla()", e);
//		}
//
//		if (logger.isDebugEnabled())
//			logger.debug("reestablecerTabla() - end");
//	}
	/**
	 * This method initializes jPanel5
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel5() {
		if (logger.isDebugEnabled())
			logger.debug("getJPanel5() - start");

		if(jPanel5 == null) {
			jPanel5 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout21 = new java.awt.FlowLayout();
			layFlowLayout21.setAlignment(java.awt.FlowLayout.LEFT);
			layFlowLayout21.setHgap(1);
			layFlowLayout21.setVgap(1);
			jPanel5.setLayout(layFlowLayout21);
			jPanel5.add(getJTextField1(), null);
			jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Cantidad a Eliminar", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null));
			jPanel5.setBackground(new java.awt.Color(242,242,238));
			jPanel5.setPreferredSize(new java.awt.Dimension(590,45));
		}

		if (logger.isDebugEnabled())
			logger.debug("getJPanel5() - end");
		return jPanel5;
	}
	/**
	 * This method initializes jTextField1
	 * 
	 * @return javax.swing.JTextField
	 */
	private javax.swing.JTextField getJTextField1() {
		if (logger.isDebugEnabled())
			logger.debug("getJTextField1() - start");

		if(jTextField1 == null) {
			jTextField1 = new javax.swing.JTextField();
			jTextField1.setPreferredSize(new java.awt.Dimension(200,20));
		}

		if (logger.isDebugEnabled())
			logger.debug("getJTextField1() - end");
		return jTextField1;
	}
	/* (non-Javadoc)
	 * @see javax.swing.event.DocumentListener#changedUpdate(javax.swing.event.DocumentEvent)
	 */
	public void changedUpdate(DocumentEvent e) {
		if (logger.isDebugEnabled())
			logger.debug("changedUpdate(DocumentEvent) - start");
		
		if (logger.isDebugEnabled())
			logger.debug("changedUpdate(DocumentEvent) - end");
	}

	/* (non-Javadoc)
	 * @see javax.swing.event.DocumentListener#insertUpdate(javax.swing.event.DocumentEvent)
	 */
	public void insertUpdate(DocumentEvent e) {
		if (logger.isDebugEnabled())
			logger.debug("insertUpdate(DocumentEvent) - start");

		SwingUtilities.invokeLater(new Runnable(){
			public void run() {
				if (logger.isDebugEnabled())
					logger.debug("run() - start");

				lecturaEscaner(CR.inputEscaner.getText().trim());

				if (logger.isDebugEnabled())
					logger.debug("run() - end");
			}
		});

		if (logger.isDebugEnabled())
			logger.debug("insertUpdate(DocumentEvent) - end");
	}

	public void lecturaEscaner(String codEscaner) {
		if (logger.isDebugEnabled())
			logger.debug("lecturaEscaner(String) - start");

		String codProducto;
		try {
			codProducto = MediadorBD.obtenerCodigoInterno(codEscaner.substring(0,11));
			if (codProducto==null) {
				codProducto = MediadorBD.obtenerCodigoInterno(codEscaner);
				jTextField.setText(codEscaner);
			} else
				jTextField.setText(codEscaner.substring(0,11));
		} catch (Exception e1) {
			logger.error("lecturaEscaner(String)", e1);

			codProducto = MediadorBD.obtenerCodigoInterno(codEscaner);
			jTextField.setText(codEscaner);
		}

		if (codProducto==null)
			productoABuscar = codEscaner;
		else		
			productoABuscar = codProducto;
			
		try {
			modeloTablaAnularProducto.llenarTablaAnularProd(productoABuscar, caso, false);
			this.getJTable().setRowSelectionInterval(0,0);
			float cantidadProd = ((DetalleServicio)CR.meServ.getListaRegalos().getDetallesServicio().elementAt(filaSeleccionadaInicial)).getCantidad(); 
			jTextField1.setText((cantidadProd <= 1) ? df.format(cantidadProd) : df.format(1));
		} catch (ExcepcionCr e1) {
			logger.error("lecturaEscaner(String)", e1);

			modeloTablaAnularProducto.iniciarDatosTabla(this.getJTable().getColumnCount());
			MensajesVentanas.mensajeError(e1.getMensaje());
		} catch (ConexionExcepcion e1) {
			logger.error("lecturaEscaner(String)", e1);

			modeloTablaAnularProducto.iniciarDatosTabla(this.getJTable().getColumnCount());
			MensajesVentanas.mensajeError(e1.getMensaje());
		}	
		jTextField.selectAll();
		
		if (logger.isDebugEnabled())
			logger.debug("lecturaEscaner(String) - end");
	}

	/* (non-Javadoc)
	 * @see javax.swing.event.DocumentListener#removeUpdate(javax.swing.event.DocumentEvent)
	 */
	public void removeUpdate(DocumentEvent e) {
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
			y= this.getBounds().y;
		} else
		if (this.getY() < 0) {
			y=0;
			x= this.getBounds().x;
		} else
		if ((this.getX() + width) > screen.width){
			x=screen.width;
			y= this.getBounds().y;
		} else
		if ((this.getY() + height) > screen.height){
			y=screen.height;
			x= this.getBounds().x;
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


	/* (no Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(jButtonAceptar)){
			CR.inputEscaner.getDocument().removeDocumentListener(this);
			if(jTable.getSelectedRow() != -1)
				try {
					filaSeleccionada = jTable.getSelectedRow();
					CR.meServ.anularProducto(new Integer(CR.meServ.obtenerRenglones(productoABuscar, false).elementAt(filaSeleccionada).toString()).intValue(), Float.valueOf(CR.me.formatoNumerico(getJTextField1().getText())).floatValue());
					dispose();
					try {
						finalize();
					} catch (Throwable e2) {
					}
				} catch (NumberFormatException e1) {
					MensajesVentanas.mensajeError("Debe llenar correctamente todos los datos solicitados para poder realizar la función.");
					CR.inputEscaner.getDocument().addDocumentListener(this);
				} catch (ExcepcionCr e1) {
					modeloTablaAnularProducto.iniciarDatosTabla(this.getJTable().getColumnCount());
					MensajesVentanas.mensajeError(e1.getMensaje());
					CR.inputEscaner.getDocument().addDocumentListener(this);
				} catch (ConexionExcepcion e1) {
					modeloTablaAnularProducto.iniciarDatosTabla(this.getJTable().getColumnCount());
					MensajesVentanas.mensajeError(e1.getMensaje());
					CR.inputEscaner.getDocument().addDocumentListener(this);
				}
			else {
				MensajesVentanas.mensajeError("Debe Seleccionarse un renglon");
				CR.inputEscaner.getDocument().addDocumentListener(this);
			}
		}

		else if(e.getSource().equals(jButtonCancelar)){
			CR.inputEscaner.getDocument().removeDocumentListener(this);
			dispose();
			try {
				finalize();
			} catch (Throwable e1) {
			}
		}
	}
}