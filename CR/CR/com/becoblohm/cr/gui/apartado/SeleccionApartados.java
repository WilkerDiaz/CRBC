/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblohm.cr.gui
 * Programa   : SeleccionApartados.java
 * Creado por : Ileana Rojas <irojas@beco.com.ve>
 * Creado en  : Sept 15, 2004 - 15:45 PM
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
package com.becoblohm.cr.gui.apartado;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Date;
import java.util.Vector;

import javax.swing.JDialog;

import org.apache.log4j.Logger;

import com.becoblohm.cr.gui.JHighlightButton;
import com.becoblohm.cr.manejarservicio.Apartado;
import com.becoblohm.cr.utiles.MensajesVentanas;

/**
 * @author vmedina
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
/*
* En esta clase se realizaron modificaciones referentes a la migración a java 1.6 por jperez
* Sólo se parametrizó el tipo de dato contenido en los 'Vector' 
* Fecha: agosto 2011
*/
public class SeleccionApartados extends JDialog implements ComponentListener, KeyListener, MouseListener, ActionListener {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(SeleccionApartados.class);

	private javax.swing.JPanel jContentPane = null;

	private javax.swing.JPanel jPanel = null;
	private javax.swing.JPanel jPanel6 = null;
	private javax.swing.JScrollPane jScrollPane = null;
	private javax.swing.JTable jTable = null;
	private javax.swing.JPanel jPanel7 = null;
	private javax.swing.JButton jButton = null;
	private javax.swing.JButton jButton1 = null;
	private javax.swing.JPanel jPanel8 = null;
	private javax.swing.JPanel jPanel9 = null;
	private javax.swing.JLabel jLabel11 = null;
	private ModeloTablaApartado apartadosObtenidos;
	
	private javax.swing.JLabel jLabel = null;
	private int filaSeleccionada = -1;
	private Vector<Apartado> apartadosObt;
	
	/**
	 * This is the default constructor
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector' 
	* Fecha: agosto 2011
	*/
	public SeleccionApartados(String identificador, Vector<Apartado> apartados) {
		super(MensajesVentanas.ventanaActiva);
		apartadosObtenidos = new ModeloTablaApartado(apartados);
		initialize();
		this.getJLabel().setText(identificador);
		this.getJTable().setRowSelectionInterval(0,0);
		this.apartadosObt = apartados;
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		if (logger.isDebugEnabled()) {
			logger.debug("initialize() - start");
		}
		
		this.setSize(640, 393);
		this.setContentPane(getJContentPane());
		this.setUndecorated(true);
		this.setModal(true);
		this.setBackground(new java.awt.Color(226,226,222));
		
		jTable.addKeyListener(this);
		jTable.addMouseListener(this);
		
		jButton.addKeyListener(this);
		jButton.addMouseListener(this);
		
		jButton1.addKeyListener(this);
		jButton1.addMouseListener(this);
		
		this.addComponentListener(this);

		if (logger.isDebugEnabled()) {
			logger.debug("initialize() - end");
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
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(new FlowLayout());
			jContentPane.add(getJPanel8(), null);
			jContentPane.add(getJPanel7(), null);
			jContentPane.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.SoftBevelBorder.RAISED));
			jContentPane.setBackground(new java.awt.Color(226,226,222));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJContentPane() - end");
		}
		return jContentPane;
	}

	/**
	 * Método actionPerformed
	 *
	 * @param e
	 */
	public void actionPerformed(ActionEvent e) {
		if (logger.isDebugEnabled()) {
			logger.debug("actionPerformed(ActionEvent) - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("actionPerformed(ActionEvent) - end");
		}
	}

	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
	 */
	public void keyPressed(KeyEvent e) {
		if (logger.isDebugEnabled()) {
			logger.debug("keyPressed(KeyEvent) - start");
		}
		
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
			filaSeleccionada = -1;
			dispose();
		}
		
		if((e.getKeyCode() == KeyEvent.VK_SPACE)||(e.getKeyCode() == KeyEvent.VK_ENTER)){
			if(e.getSource().equals(jButton)){
				// Boton de Aceptar
				filaSeleccionada = jTable.getSelectedRow();
				dispose();
			}
			
			else if(e.getSource().equals(jButton1)){
				filaSeleccionada = -1;
				dispose();
			}

			else if (e.getSource().equals(jTable)) {
				if (jTable.getSelectedRow()!=-1) {
					filaSeleccionada = jTable.getSelectedRow();
					dispose();
				}
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("keyPressed(KeyEvent) - end");
		}
	}
	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
	 */
	public void keyReleased(KeyEvent e) {
		if (logger.isDebugEnabled()) {
			logger.debug("keyReleased(KeyEvent) - start");
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug("keyReleased(KeyEvent) - end");
		}
	}
	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
	 */
	public void keyTyped(KeyEvent e) {
		if (logger.isDebugEnabled()) {
			logger.debug("keyTyped(KeyEvent) - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("keyTyped(KeyEvent) - end");
		}
	}
	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	public void mouseReleased(MouseEvent e) {
		if (logger.isDebugEnabled()) {
			logger.debug("mouseClicked(MouseEvent) - start");
		}
		
		//Mapeo de MouseClicked sobre ACEPTAR
		if(e.getSource().equals(jButton)){
			// Boton de Aceptar
			filaSeleccionada = jTable.getSelectedRow();
			dispose();
		}
		
		//Mapeo de MouseClicked sobre CANCELAR
		else if(e.getSource().equals(jButton1)){
			filaSeleccionada = -1;
			dispose();
		}

		if (logger.isDebugEnabled()) {
			logger.debug("mouseClicked(MouseEvent) - end");
		}
	}
	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 */
	public void mouseEntered(MouseEvent e) {
		if (logger.isDebugEnabled()) {
			logger.debug("mouseEntered(MouseEvent) - start");
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug("mouseEntered(MouseEvent) - end");
		}
	}
	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	 */
	public void mouseExited(MouseEvent e) {
		if (logger.isDebugEnabled()) {
			logger.debug("mouseExited(MouseEvent) - start");
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug("mouseExited(MouseEvent) - end");
		}
	}
	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	public void mousePressed(MouseEvent e) {
		if (logger.isDebugEnabled()) {
			logger.debug("mousePressed(MouseEvent) - start");
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug("mousePressed(MouseEvent) - end");
		}
	}
	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 */
	public void mouseClicked(MouseEvent e) {
		if (logger.isDebugEnabled()) {
			logger.debug("mouseReleased(MouseEvent) - start");
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug("mouseReleased(MouseEvent) - end");
		}
	}
	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel() - start");
		}

		if(jPanel == null) {
			jPanel = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout1 = new java.awt.FlowLayout();
			layFlowLayout1.setHgap(5);
			layFlowLayout1.setVgap(1);
			layFlowLayout1.setAlignment(java.awt.FlowLayout.LEFT);
			jPanel.setLayout(layFlowLayout1);
			jPanel.add(getJLabel(), null);
			jPanel.setPreferredSize(new java.awt.Dimension(600,45));
			jPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.gray,1), "Identificador de Apartado: ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12), java.awt.Color.darkGray));
			jPanel.setBackground(new java.awt.Color(242,242,238));
			
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel() - end");
		}
		return jPanel;
	}
	/**
	 * This method initializes jPanel6
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel6() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel6() - start");
		}

		if(jPanel6 == null) {
			jPanel6 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout11 = new java.awt.FlowLayout();
			layFlowLayout11.setHgap(1);
			layFlowLayout11.setVgap(1);
			jPanel6.setLayout(layFlowLayout11);
			jPanel6.add(getJScrollPane(), null);
			jPanel6.setPreferredSize(new java.awt.Dimension(600,205));
			jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Resultados: ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null));
			jPanel6.setBackground(new java.awt.Color(242,242,238));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel6() - end");
		}
		return jPanel6;
	}
	/**
	 * This method initializes jTable
	 * 
	 * @return javax.swing.JTable
	 */
	private javax.swing.JTable getJTable() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJTable() - start");
		}

		if(jTable == null) {
			jTable = new javax.swing.JTable(apartadosObtenidos);
			jTable.getTableHeader().setReorderingAllowed(false) ;
			jTable.setLocale(new java.util.Locale("es", "VE", ""));
			jTable.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			jTable.setBackground(new java.awt.Color(242,242,238));
			jTable.getColumnModel().getColumn(0).setPreferredWidth(105);
			jTable.getColumnModel().getColumn(1).setPreferredWidth(70);
			jTable.getColumnModel().getColumn(2).setPreferredWidth(100);
			jTable.getColumnModel().getColumn(3).setPreferredWidth(200);
			jTable.getColumnModel().getColumn(4).setPreferredWidth(150);
			jTable.getColumnModel().getColumn(5).setPreferredWidth(105);
			jTable.getColumnModel().getColumn(6).setPreferredWidth(60);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJTable() - end");
		}
		return jTable;
	}
	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private javax.swing.JScrollPane getJScrollPane() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJScrollPane() - start");
		}

		if(jScrollPane == null) {
			jScrollPane = new javax.swing.JScrollPane();
			jScrollPane.setViewportView(getJTable());
			jScrollPane.setPreferredSize(new java.awt.Dimension(585,180));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJScrollPane() - end");
		}
		return jScrollPane;
	}
	/**
	 * This method initializes jPanel7
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel7() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel7() - start");
		}

		if(jPanel7 == null) {
			jPanel7 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout22 = new java.awt.FlowLayout();
			layFlowLayout22.setAlignment(java.awt.FlowLayout.RIGHT);
			layFlowLayout22.setHgap(0);
			layFlowLayout22.setVgap(0);
			jPanel7.setLayout(layFlowLayout22);
			jPanel7.add(getJButton(), null);
			jPanel7.add(getJButton1(), null);
			jPanel7.setPreferredSize(new java.awt.Dimension(610,35));
			jPanel7.setBackground(new java.awt.Color(226,226,222));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel7() - end");
		}
		return jPanel7;
	}
	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private javax.swing.JButton getJButton() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJButton() - start");
		}

		if(jButton == null) {
			jButton = new JHighlightButton();
			jButton.setText("Aceptar");
			jButton.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 12));
			jButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix16x16/check2.png")));
			jButton.setBackground(new java.awt.Color(226,226,222));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJButton() - end");
		}
		return jButton;
	}
	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private javax.swing.JButton getJButton1() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJButton1() - start");
		}

		if(jButton1 == null) {
			jButton1 = new JHighlightButton();
			jButton1.setText("Cancelar");
			jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix16x16/delete2.png")));
			jButton1.setBackground(new java.awt.Color(226,226,222));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJButton1() - end");
		}
		return jButton1;
	}
	/**
	 * This method initializes jPanel8
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel8() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel8() - start");
		}

		if(jPanel8 == null) {
			jPanel8 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout12 = new java.awt.FlowLayout();
			layFlowLayout12.setAlignment(java.awt.FlowLayout.CENTER);
			layFlowLayout12.setVgap(0);
			layFlowLayout12.setHgap(0);
			jPanel8.setLayout(layFlowLayout12);
			jPanel8.add(getJPanel9(), null);
			jPanel8.add(getJPanel(), null);
			jPanel8.add(getJPanel6(), null);
			jPanel8.setPreferredSize(new java.awt.Dimension(610,305));
			jPanel8.setBackground(new java.awt.Color(242,242,238));
			jPanel8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(69,107,127),1));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel8() - end");
		}
		return jPanel8;
	}
	/**
	 * This method initializes jPanel9
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel9() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel9() - start");
		}

		if(jPanel9 == null) {
			jPanel9 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout13 = new java.awt.FlowLayout();
			layFlowLayout13.setAlignment(java.awt.FlowLayout.LEFT);
			layFlowLayout13.setVgap(0);
			layFlowLayout13.setHgap(0);
			jPanel9.setLayout(layFlowLayout13);
			jPanel9.add(getJLabel11(), null);
			jPanel9.setBackground(new java.awt.Color(69,107,127));
			jPanel9.setPreferredSize(new java.awt.Dimension(610,50));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel9() - end");
		}
		return jPanel9;
	}
	/**
	 * This method initializes jLabel11
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel11() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel11() - start");
		}

		if(jLabel11 == null) {
			jLabel11 = new javax.swing.JLabel();
			jLabel11.setText("Recuperar Apartados ");
			jLabel11.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 14));
			jLabel11.setForeground(java.awt.Color.white);
			jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/i48x48/apps/AddContact.png")));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel11() - end");
		}
		return jLabel11;
	}
	/**
	 * This method initializes jLabel
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel() - start");
		}

		if(jLabel == null) {
			jLabel = new javax.swing.JLabel();
			jLabel.setPreferredSize(new java.awt.Dimension(180,16));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel() - end");
		}
		return jLabel;
	}
	/**
	 * Método filaSeleccionada
	 * 
	 * @return
	 * boolean
	 */
	public boolean filaSeleccionada() {
		if (logger.isDebugEnabled()) {
			logger.debug("filaSeleccionada() - start");
		}

		boolean returnboolean = (filaSeleccionada != -1);
		if (logger.isDebugEnabled()) {
			logger.debug("filaSeleccionada() - end");
		}
		return returnboolean;
	}
	/**
	 * Método obtenerTiendaSeleccionada
	 * 
	 * @return
	 * int
	 */
	public int obtenerTiendaSeleccionada() {
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerTiendaSeleccionada() - start");
		}

		int returnint = ((Apartado) apartadosObt.elementAt(filaSeleccionada))
				.getCodTienda();
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerTiendaSeleccionada() - end");
		}
		return returnint;
	}
	/**
	 * Método obtenerNumServSeleccionado
	 * 
	 * @return
	 * int
	 */
	public int obtenerNumServSeleccionado() {
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerNumServSeleccionado() - start");
		}

		int returnint = ((Apartado) apartadosObt.elementAt(filaSeleccionada))
				.getNumServicio();
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerNumServSeleccionado() - end");
		}
		return returnint;
	}
	/**
	 * Método obtenerFechaSeleccionada
	 * 
	 * @return
	 * Date
	 */
	public Date obtenerFechaSeleccionada() {
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerFechaSeleccionada() - start");
		}

		Date returnDate = ((Apartado) apartadosObt.elementAt(filaSeleccionada))
				.getFechaServicio();
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerFechaSeleccionada() - end");
		}
		return returnDate;
	}
	
	/**
	 * Método obtenerTipoApartado
	 * 
	 * @return caracter con tipo de apartado 'E' Espera 'P' pendiente 'V' Vencido
	 * 
	 */
	public char obtenerTipoApartado() {
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerTipoApartado() - start");
		}
	
		char tipo = ((Apartado) apartadosObt.elementAt(filaSeleccionada)).getEstadoServicio();
		
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerTipoApartado() - end");
		}
		return tipo;
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
}  //  @jve:visual-info  decl-index=0 visual-constraint="10,10"