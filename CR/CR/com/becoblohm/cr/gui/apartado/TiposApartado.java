package com.becoblohm.cr.gui.apartado;


/**
 * @author aavila
 *
 * Para cambiar la plantilla para este comentario de tipo generado vaya a
 * Ventana&gt;Preferencias&gt;Java&gt;Generación de código&gt;Código y comentarios
 */
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.EventListener;
import javax.swing.ImageIcon;
import javax.swing.JDialog;

import java.util.Date;
import java.util.Iterator;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.becoblohm.cr.CR;
import com.becoblohm.cr.excepciones.ConexionExcepcion;
import com.becoblohm.cr.excepciones.ExcepcionCr;
import com.becoblohm.cr.excepciones.MaquinaDeEstadoExcepcion;
import com.becoblohm.cr.excepciones.UsuarioExcepcion;
import com.becoblohm.cr.gui.JHighlightButton;
import com.becoblohm.cr.utiles.MensajesVentanas;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JComboBox;
import javax.swing.JPanel;

/**
 * Descripción:
 * 
 */

public class TiposApartado extends JDialog implements ComponentListener, KeyListener, MouseListener, EventListener, ActionListener {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TiposApartado.class);  //  @jve:decl-index=0:

	private javax.swing.JPanel jContentPane = null;
	private javax.swing.JPanel jPanel = null;
	private javax.swing.JPanel jPanel2 = null;
	private javax.swing.JButton jButton = null;
	private javax.swing.JButton jButton1 = null;
	private javax.swing.JPanel jPanel3 = null;
	private javax.swing.JPanel jPanel4 = null;
	private javax.swing.JPanel jPanel5 = null;
	Vector<Vector<Object>> tipos = new Vector<Vector<Object>>();  //  @jve:decl-index=0:
	
	@SuppressWarnings("unused")
	private DecimalFormat df = new DecimalFormat("#,##0.00");  //  @jve:decl-index=0:

	private JLabel jLabel11 = null;

	private JComboBox jComboBox = null;

	private JPanel jPanel51 = null;
	private JPanel jPanel52 = null;
	private JLabel jLabel = null;
	private JLabel jLabelFechaApertura = null;
	private JLabel jLabelFechaVence = null;
	
	private Date fechaVencimiento;
	
	private SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");  //  @jve:decl-index=0:


	/**
	 * Constructor para las donaciones de tipo !=1
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector' e 'Iterator' 
	* Fecha: agosto 2011
	*/
	public TiposApartado(Vector<Vector<Object>> tipos) {
		super(MensajesVentanas.ventanaActiva);
		initialize();		
		Vector<Object> apartadoNormal = new Vector<Object>();
		apartadoNormal.addElement(new Integer(0));
		apartadoNormal.addElement("Apartado Tradicional");
		apartadoNormal.addElement("");
		apartadoNormal.addElement("");
		apartadoNormal.addElement("");
		this.tipos.addAll(tipos);
		this.tipos.addElement(apartadoNormal);
		Iterator<Vector<Object>> i = this.tipos.iterator();
		while (i.hasNext())	{
			Vector<Object> unTipo = i.next();
			jComboBox.addItem(unTipo.elementAt(1));
		}		
		jButton.addKeyListener(this);
		jButton.addMouseListener(this);	
		
		jButton1.addKeyListener(this);
		jButton1.addMouseListener(this);
		
		jComboBox.addKeyListener(this);
		jComboBox.addActionListener(this);
	}

	
	private void initialize() {
		if (logger.isDebugEnabled()) logger.debug("initialize() - start");
		this.setTitle("Tipos de apartado");
		this.setSize(360, 290);
		this.setContentPane(getJContentPane());
		this.setUndecorated(true);
		this.setModal(true);
		this.setBackground(new java.awt.Color(226,226,222));
		this.addComponentListener(this);	
		if (logger.isDebugEnabled()) logger.debug("initialize() - end");
	}
	
	private javax.swing.JPanel getJContentPane() {
		if (logger.isDebugEnabled()) logger.debug("getJContentPane() - start");
		if (jContentPane == null) {
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(new FlowLayout());
			jContentPane.add(getJPanel3(), null);
			jContentPane.add(getJPanel2(), null);
			jContentPane.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.SoftBevelBorder.RAISED));
			jContentPane.setBackground(new java.awt.Color(226,226,222));
		}
		if (logger.isDebugEnabled()) logger.debug("getJContentPane() - end");
		return jContentPane;
	}
	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
	 */
	public void keyPressed(KeyEvent e) {
		jButton.removeKeyListener(this);
		jButton.removeMouseListener(this);
		jButton1.removeKeyListener(this);
		jButton1.removeMouseListener(this);
		if (logger.isDebugEnabled()) {
			logger.debug("keyPressed(KeyEvent) - start");
		}

		if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
			try {
				CR.meServ.anularApartadoActivo();
			} catch (UsuarioExcepcion ex) {
				logger.error("Anulando apartado activo", ex);
			} catch (MaquinaDeEstadoExcepcion ex) {
				logger.error("Anulando apartado activo", ex);
			} catch (ConexionExcepcion ex) {
				logger.error("Anulando apartado activo", ex);
			} catch (ExcepcionCr ex) {
				logger.error("Anulando apartado activo", ex);
			}
			dispose();
		} else if(e.getKeyCode() == KeyEvent.VK_ENTER){
			if (e.getSource().equals(this.getJComboBox())){
				getJButton1().requestFocus();
			} else if (e.getSource().equals(this.getJButton1())){
				try{
					//Abrir registro de apartado con los datos cambiados
					abrirRegistroApartado();
					dispose();
					dispose();
				}catch(Exception regDon){
					dispose();
				}
			}else if (e.getSource().equals(this.getJButton()))dispose();
		} else if(e.getKeyCode()==KeyEvent.VK_TAB){
			this.getJComboBox().requestFocus();
		}
		jButton.addKeyListener(this);
		jButton.addMouseListener(this);
		jButton1.addKeyListener(this);
		jButton1.addMouseListener(this);

		if (logger.isDebugEnabled()) logger.debug("keyPressed(KeyEvent) - end");
	}

	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
	 */
	public void keyReleased(KeyEvent e) {
		jButton.removeKeyListener(this);
		jButton.removeMouseListener(this);
		jButton1.removeKeyListener(this);
		jButton1.removeMouseListener(this);
		if (logger.isDebugEnabled()) {
			logger.debug("keyPressed(KeyEvent) - start");
		}

		if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
			dispose();
		} else if(e.getKeyCode() == KeyEvent.VK_ENTER){
			if (e.getSource().equals(this.getJComboBox())){
				seleccionarTipo();
				getJButton1().requestFocus();
			} else if (e.getSource().equals(this.getJButton1())){
				try{
					
				}catch(Exception regDon){
					dispose();
				}
			}else if (e.getSource().equals(this.getJButton()))dispose();
		}
		jButton.addKeyListener(this);
		jButton.addMouseListener(this);
		jButton1.addKeyListener(this);
		jButton1.addMouseListener(this);

		if (logger.isDebugEnabled()) logger.debug("keyPressed(KeyEvent) - end");
	}


	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector' 
	* Fecha: agosto 2011
	*/
	private void seleccionarTipo() {
		int item = this.getJComboBox().getSelectedIndex();
		Vector<Object> tipoApartado = this.tipos.elementAt(item);
		jLabel.setText((String)tipoApartado.elementAt(1));
		String vencimiento = CR.meServ.getApartado().getTiempoVigencia()+" "+(CR.meServ.getApartado().getTipoVigencia().equalsIgnoreCase("Dia") ? "Días":"Meses");
		if(item!=this.getJComboBox().getItemCount()-1){
			vencimiento=dateFormat.format(((Date)tipoApartado.elementAt(4)));
			fechaVencimiento = ((Date)tipoApartado.elementAt(4));
		}
		jLabelFechaApertura.setText(dateFormat.format(new Date()));
		jLabelFechaVence.setText(vencimiento);
	}

	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
	 */
	public void keyTyped(KeyEvent e) {
		if (logger.isDebugEnabled()) logger.debug("keyTyped(KeyEvent) - start");
		//falta lo de la tecla enter
		if (logger.isDebugEnabled()) logger.debug("keyTyped(KeyEvent) - end");	
	}

	public void mouseReleased(MouseEvent me1) {
		jButton.removeKeyListener(this);
		jButton.removeMouseListener(this);
		jButton1.removeKeyListener(this);
		jButton1.removeMouseListener(this);
		if (logger.isDebugEnabled()) logger.debug("mouseClicked(MouseEvent) - start");
		
		if (me1.getSource().equals(this.getJButton1())){
			try{
				//Abrir registro de apartado con los datos cambiados
				abrirRegistroApartado();
				dispose();
			}catch(Exception regDon){
				regDon.printStackTrace();
				dispose();
			}
		} else if (me1.getSource().equals(this.getJButton())) {
			try {
				CR.meServ.anularApartadoActivo();
			} catch (UsuarioExcepcion e) {
				logger.error("Anulando apartado activo", e);
			} catch (MaquinaDeEstadoExcepcion e) {
				logger.error("Anulando apartado activo", e);
			} catch (ConexionExcepcion e) {
				logger.error("Anulando apartado activo", e);
			} catch (ExcepcionCr e) {
				logger.error("Anulando apartado activo", e);
			}
			dispose();
		} 
		jButton.addKeyListener(this);
		jButton.addMouseListener(this);
		jButton1.addKeyListener(this);
		jButton1.addMouseListener(this);
		if (logger.isDebugEnabled()) logger.debug("mouseClicked(MouseEvent) - end");
	}
	
	public void mouseEntered(MouseEvent e) {
		if (logger.isDebugEnabled()) logger.debug("mouseEntered(MouseEvent) - start");
		if (logger.isDebugEnabled()) logger.debug("mouseEntered(MouseEvent) - end");
	}

	public void mouseExited(MouseEvent e) {
		if (logger.isDebugEnabled()) logger.debug("mouseExited(MouseEvent) - start");
		if (logger.isDebugEnabled()) logger.debug("mouseExited(MouseEvent) - end");
	}
	public void mousePressed(MouseEvent e) {
		if (logger.isDebugEnabled()) logger.debug("mousePressed(MouseEvent) - start");
		if (logger.isDebugEnabled()) logger.debug("mousePressed(MouseEvent) - end");
	}
	
	public void mouseClicked(MouseEvent me1) {
		if (logger.isDebugEnabled()) logger.debug("mouseReleased(MouseEvent) - start");
		if (logger.isDebugEnabled()) logger.debug("mouseReleased(MouseEvent) - end");
	}
	
	private javax.swing.JPanel getJPanel() {
		if (logger.isDebugEnabled()) logger.debug("getJPanel() - start");
		if(jPanel == null) {
			jPanel = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout1 = new java.awt.FlowLayout();
			layFlowLayout1.setAlignment(java.awt.FlowLayout.CENTER);
			layFlowLayout1.setHgap(1);
			layFlowLayout1.setVgap(1);
			jPanel.setLayout(layFlowLayout1);
			jPanel.setPreferredSize(new java.awt.Dimension(315,110));
			jPanel.setBackground(new java.awt.Color(242,242,238));
			jPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Información", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12), java.awt.Color.black));
			this.jLabel = new JLabel();
			this.getJPanel51().add(jLabel);
			jPanel.add(this.getJPanel51(), null);
			jPanel.add(this.getJPanel52(),null);
		}
		if (logger.isDebugEnabled()) logger.debug("getJPanel() - end");
		return jPanel;
	}


	private javax.swing.JPanel getJPanel2() {
		if (logger.isDebugEnabled()) logger.debug("getJPanel2() - start");
		if(jPanel2 == null) {
			jPanel2 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout11 = new java.awt.FlowLayout();
			layFlowLayout11.setAlignment(java.awt.FlowLayout.RIGHT);
			layFlowLayout11.setHgap(0);
			layFlowLayout11.setVgap(0);
			jPanel2.setLayout(layFlowLayout11);
			jPanel2.add(getJButton1(), null);
			jPanel2.add(getJButton(), null);
			jPanel2.setPreferredSize(new java.awt.Dimension(320,30));
			jPanel2.setBackground(new java.awt.Color(226,226,222));
		}
		if (logger.isDebugEnabled()) logger.debug("getJPanel2() - end");
		return jPanel2;
	}

	private javax.swing.JButton getJButton() {
		if (logger.isDebugEnabled()) logger.debug("getJButton() - start");
		if(jButton == null) {
			jButton = new JHighlightButton();
			jButton.setText("Cancelar");
			jButton.setBackground(new java.awt.Color(226,226,222));
			jButton.setIcon(new ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix16x16/delete2.png")));
		}
		if (logger.isDebugEnabled()) logger.debug("getJButton() - end");
		return jButton;
	}


	private javax.swing.JButton getJButton1() {
		if (logger.isDebugEnabled()) logger.debug("getJButton1() - start");
		if(jButton1 == null) {
			jButton1 = new JHighlightButton();
			jButton1.setText("Crear apartado");
			jButton1.setBackground(new java.awt.Color(226,226,222));
			jButton1.setIcon(new ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix16x16/add2.png")));
			//jButton1.addMouseListener(this);
			jButton1.setEnabled(true);
		}
		if (logger.isDebugEnabled()) logger.debug("getJButton1() - end");
		return jButton1;
	}

	private javax.swing.JPanel getJPanel3() {
		if (logger.isDebugEnabled()) logger.debug("getJPanel3() - start");
		if(jPanel3 == null) {
			jPanel3 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout12 = new java.awt.FlowLayout();
			layFlowLayout12.setHgap(0);
			layFlowLayout12.setVgap(0);
			jPanel3.setLayout(layFlowLayout12);
			jPanel3.add(getJPanel4(), null);
			jPanel3.add(getJPanel5(), null);
			jPanel3.add(getJPanel(), null);
			jPanel3.setPreferredSize(new java.awt.Dimension(320,200));
			jPanel3.setBackground(new java.awt.Color(242,242,238));
			jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(69,107,127),1));
		}
		if (logger.isDebugEnabled()) logger.debug("getJPanel3() - end");
		return jPanel3;
	}
	
	private javax.swing.JPanel getJPanel5() {
		if (logger.isDebugEnabled()) logger.debug("getJPanel5() - start");
		if(jPanel5 == null) {
			jPanel5 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout2 = new java.awt.FlowLayout();
			layFlowLayout2.setAlignment(java.awt.FlowLayout.CENTER);
			layFlowLayout2.setVgap(5);
			layFlowLayout2.setHgap(5);
			jPanel5.setLayout(layFlowLayout2);
			jPanel5.setPreferredSize(new java.awt.Dimension(315,40));
			jPanel5.add(getJComboBox(), null);
			jPanel5.setBackground(new java.awt.Color(242,242,238));
		}
		if (logger.isDebugEnabled()) logger.debug("getJPanel5() - end");
		return jPanel5;
	}

	private javax.swing.JPanel getJPanel4() {
		if (logger.isDebugEnabled()) logger.debug("getJPanel4() - start");
		if(jPanel4 == null) {
			jLabel11 = new JLabel();
			jLabel11.setFont(new Font("Dialog", Font.BOLD, 18));
			jLabel11.setIcon(new ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix32x32/box_add.png")));
			jLabel11.setText("Tipos de apartado");
			jLabel11.setForeground(Color.white);
			jPanel4 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout2 = new java.awt.FlowLayout();
			layFlowLayout2.setAlignment(java.awt.FlowLayout.LEFT);
			layFlowLayout2.setVgap(5);
			layFlowLayout2.setHgap(5);
			jPanel4.setLayout(layFlowLayout2);
			jPanel4.setPreferredSize(new java.awt.Dimension(320,40));
			jPanel4.setBackground(new java.awt.Color(69,107,127));
			jPanel4.add(jLabel11, null);
		}
		if (logger.isDebugEnabled()) logger.debug("getJPanel4() - end");
		return jPanel4;
	}

	public void componentHidden(ComponentEvent e) {}

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

	public void componentResized(ComponentEvent e) {}
	public void componentShown(ComponentEvent e) {}


	/**
	 * This method initializes jComboBox	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getJComboBox() {
		if (jComboBox == null) {
			jComboBox = new JComboBox();
		}
		return jComboBox;
	}


	/**
	 * This method initializes jPanel51	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel51() {
		if (jPanel51 == null) {
			FlowLayout layFlowLayout21 = new FlowLayout();
			layFlowLayout21.setHgap(5);
			layFlowLayout21.setVgap(5);
			layFlowLayout21.setAlignment(FlowLayout.CENTER);
			jPanel51 = new JPanel();
			jPanel51.setPreferredSize(new Dimension(300, 20));
			jPanel51.setLayout(layFlowLayout21);
			jPanel51.setBackground(new Color(242, 242, 238));
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
			GridLayout layout21 = new GridLayout(2,2); 
			jPanel52 = new JPanel();
			jPanel52.setPreferredSize(new Dimension(300, 60));
			jPanel52.setLayout(layout21);
			jPanel52.setBackground(new Color(242, 242, 238));
			this.jLabelFechaApertura = new JLabel();
			this.jLabelFechaVence = new JLabel();
			jPanel52.add(new JLabel(" Apertura: "));
			jPanel52.add(this.jLabelFechaApertura);
			jPanel52.add(new JLabel(" Vencimiento: "));
			jPanel52.add(this.jLabelFechaVence);
		}
		return jPanel52;
	}


	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(this.getJComboBox())){
			seleccionarTipo();
		}
	}
	
	public void abrirRegistroApartado() throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr{
		if(fechaVencimiento==null && (((Integer)((Vector)tipos.elementAt(getJComboBox().getSelectedIndex())).elementAt(0))).intValue()!=0){
			fechaVencimiento = (Date)((tipos.elementAt(getJComboBox().getSelectedIndex()))).elementAt(4);
		}
		CR.meServ.asignarVariablesApartadoEspecial(fechaVencimiento,(((Integer)(tipos.elementAt(getJComboBox().getSelectedIndex())).elementAt(0))).intValue(),((String)(tipos.elementAt(getJComboBox().getSelectedIndex())).elementAt(1)));
		RegistroApartado ra = null;
		ra = new RegistroApartado();
		MensajesVentanas.centrarVentanaDialogo(ra);
	}
}
