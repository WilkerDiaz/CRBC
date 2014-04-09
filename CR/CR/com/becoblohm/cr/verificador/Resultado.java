package com.becoblohm.cr.verificador;

import java.text.DecimalFormat;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JDialog;

import com.becoblohm.cr.utiles.MathUtil;

/*
 * Creado el 05-ene-05
 *
 * Para cambiar la plantilla para este archivo generado vaya a
 * Ventana&gt;Preferencias&gt;Java&gt;Generación de código&gt;Código y comentarios
 */

/**
 * @author gmartinelli
 *
 * Para cambiar la plantilla para este comentario de tipo generado vaya a
 * Ventana&gt;Preferencias&gt;Java&gt;Generación de código&gt;Código y comentarios
 */
public class Resultado extends JDialog {

	private javax.swing.JPanel jContentPane = null;
	
	private int ancho;
	private int alto;

	private javax.swing.JLabel jLabel = null;
	private javax.swing.JPanel jPanel = null;
	private javax.swing.JPanel jPanel1 = null;
	private javax.swing.JPanel jPanel2 = null;
	private javax.swing.JPanel jPanel3 = null;
	private javax.swing.JPanel jPanel4 = null;
	private javax.swing.JLabel jLabel1 = null;
	private javax.swing.JLabel jLabel2 = null;
	private javax.swing.JLabel jLabel3 = null;
	private javax.swing.JLabel jLabel4 = null;
	private javax.swing.JLabel jLabelIvaFuerte = null; 
	private javax.swing.JLabel jLabelFinalFuerte = null; 
	private javax.swing.JLabel jLabelTotalFuerte = null; 
	private javax.swing.JPanel jPanelPrecio1 = null; 
	private javax.swing.JPanel jPanelPrecio2 = null;
	private javax.swing.JPanel jPanelPrecio3 = null;
	private javax.swing.JPanel jPanelPrecio4 = null;
	private javax.swing.JPanel jPanelEspacio = null;
	private javax.swing.JPanel jPanelEspacio1 = null;
	private javax.swing.JPanel jPanelEspacio2 = null;
	private javax.swing.JPanel jPanelBlanco = null;
	private javax.swing.JLabel jLabelEspacioAdelante = null;
	private javax.swing.JLabel jLabel5 = null;
	private javax.swing.JLabel jLabel6 = null;
	private javax.swing.JLabel jLabel7 = null;
	private javax.swing.JLabel jLabel8 = null;
	private javax.swing.JLabel jLabel9 = null;
	private javax.swing.JLabel jLabel10 = null;
	private javax.swing.JLabel jLabel11 = null;
	private javax.swing.JLabel jLabel12 = null;
	private javax.swing.JPanel jPanel5 = null;
	private javax.swing.JPanel jPanel7 = null;
	private javax.swing.JPanel jPanel8 = null;
	private javax.swing.JPanel jPanel9 = null;
	private javax.swing.JPanel jPanel10 = null;
	private javax.swing.JPanel jPanel12 = null;
	private javax.swing.JPanel jPanel13 = null;
	private javax.swing.JPanel jPanel14 = null;
	private javax.swing.JPanel jPanel15 = null;
	private javax.swing.JPanel jPanel16 = null;
	private javax.swing.JLabel jLabel13 = null;
	/**
	 * This is the default constructor
	 */
	public Resultado(int ancho, int alto, String codProd, BaseDeDatos bd) {
		super();
		this.ancho = ancho;
		this.alto = alto;
		initialize();
		cargarProducto(codProd, bd);
	}
	
	/** 22/10. Cambio BsF
	 * Mtodo que redondea el precio al tercer decimal
	 * @param precioPromo Double, precio a redondear
	 * @return double double que representa el precio redondeado
	 */	
	public double redondear(double precioPromo) {
		double varTemp = precioPromo/10;
		int entero = new Double (varTemp).intValue();
		double decimal = varTemp - entero;
		if (decimal >= 0.5)
			entero = entero + 1;
		double valor = entero * 0.01;
		return valor;
	}

	private void cargarProducto(String codigo, BaseDeDatos bd) {
		Vector<Object> producto = bd.obtenerProducto(codigo);
		// Si el resultado es distinto de nulo se obtuvo un producto
		 if (producto!=null) {
			double precioReg = Double.parseDouble(producto.elementAt(1).toString());
			double precioPromo = Double.parseDouble(producto.elementAt(2).toString());
			double precioFin = Double.parseDouble(producto.elementAt(3).toString());
			double mtoIVA = Double.parseDouble(producto.elementAt(4).toString());
			double total = Double.parseDouble(producto.elementAt(5).toString());
			DecimalFormat df = new DecimalFormat("#,##0.00");
			
			// Llenamos las variables 
//			this.getJLabel3().setText((String)producto.elementAt(0));
//			this.getJLabel4().setText("Precio Regular: Bs.  " + df.format(precioReg));
//			this.getJLabel5().setText("Precio Promocional: Bs.  " + df.format(precioPromo));
//			this.getJLabel8().setText("Bs.  "+ df.format(precioFin));
//		    this.getJLabel10().setText("Bs.  "+df.format(mtoIVA));
//			this.getJLabel12().setText("Bs.  "+ df.format(total));
//		    this.getJLabelTotalFuerte().setText("Bs.F. "+ df.format(redondear(total)));

			this.getJLabel3().setText((String)producto.elementAt(0));
			this.getJLabel4().setText("Precio Regular: Bs.F.  " + df.format(precioReg));
			this.getJLabel5().setText("Precio Promocional: Bs.F.  " + df.format(precioPromo));
			this.getJLabel8().setText("Bs.F.  "+ df.format(precioFin));
		    this.getJLabel10().setText("Bs.F.  "+df.format(mtoIVA));
			this.getJLabel12().setText("Bs.F.  "+ df.format(MathUtil.roundDouble(total)));
		    this.getJLabelTotalFuerte().setText("Bs. "+ df.format(MathUtil.roundDouble(total)*1000));
			
			if (precioPromo==0) {
				jLabel5.setVisible(false);
			}
			this.getJPanel14().setVisible(false);
			this.getJPanel15().setVisible(false);
			this.getJPanel16().setVisible(false);
		} else {
			this.getJPanel14().setVisible(true);
			this.getJPanel15().setVisible(true);
			this.getJPanel16().setVisible(true);
		}
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(ancho, alto);
		this.setContentPane(getJContentPane());
		this.setTitle("Verificador de Precios");
		this.setResizable(false);
		this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
	}
	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(new java.awt.BorderLayout());
			jContentPane.add(getJPanel(), java.awt.BorderLayout.NORTH);
			jContentPane.add(getJPanel1(), java.awt.BorderLayout.EAST);
			jContentPane.add(getJPanel2(), java.awt.BorderLayout.WEST);
			jContentPane.add(getJPanel3(), java.awt.BorderLayout.CENTER);
			jContentPane.add(getJPanel4(), java.awt.BorderLayout.SOUTH);
			jContentPane.setBackground(new java.awt.Color(112,149,182));
		}
		return jContentPane;
	}
	/**
	 * This method initializes jLabel
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel() {
		if(jLabel == null) {
			jLabel = new javax.swing.JLabel();
			jLabel.setText("");
			jLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/becoblohm/cr/verificador/imagenes/tope.png")));
			jLabel.setBackground(new java.awt.Color(151,177,202));
		}
		return jLabel;
	}
	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel() {
		if(jPanel == null) {
			jPanel = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout2 = new java.awt.FlowLayout();
			layFlowLayout2.setHgap(0);
			layFlowLayout2.setVgap(0);
			layFlowLayout2.setAlignment(java.awt.FlowLayout.LEFT);
			jPanel.setLayout(layFlowLayout2);
			jPanel.add(getJLabel(), null);
			jPanel.setBackground(new java.awt.Color(151,177,202));
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
			jPanel1 = new javax.swing.JPanel();
			jPanel1.setBackground(new java.awt.Color(112,149,182));
		}
		return jPanel1;
	}
	/**
	 * This method initializes jPanel2
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel2() {
		if(jPanel2 == null) {
			jPanel2 = new javax.swing.JPanel();
			jPanel2.setBackground(new java.awt.Color(112,149,182));
		}
		return jPanel2;
	}
	/**
	 * This method initializes jPanel3
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel3() {
		if(jPanel3 == null) {
			jPanel3 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout21 = new java.awt.FlowLayout();
			layFlowLayout21.setHgap(5);
			layFlowLayout21.setVgap(10);
			layFlowLayout21.setAlignment(java.awt.FlowLayout.LEFT);
			jPanel3.setLayout(layFlowLayout21);
			jPanel3.add(getJPanel15(), null);
			jPanel3.add(getJPanel14(), null);
			jPanel3.add(getJPanel16(), null);
			jPanel3.add(getJPanel13(), null);
			jPanel3.add(getJPanel5(), null);
			jPanel3.add(getJPanel7(), null);
			jPanel3.add(getJPanelEspacio(), null);
			jPanel3.add(getJPanel8(), null);
			jPanel3.add(getJPanelEspacio1(), null);
			jPanel3.add(getJPanel9(), null);
			jPanel3.add(getJPanelBlanco(), null);
			jPanel3.setBackground(new java.awt.Color(112,149,182));
		}
		return jPanel3;
	}
	
	/**
		 * This method initializes jPanelPrecio1
		 * 
		 * @return javax.swing.JPanel
		 */
		private javax.swing.JPanel getJPanelPrecio1() {
			if(jPanelPrecio1 == null) {
				jPanelPrecio1 = new javax.swing.JPanel();
				java.awt.GridLayout layGridLayout2 = new java.awt.GridLayout();
				layGridLayout2.setRows(2);
				layGridLayout2.setColumns(1);
				layGridLayout2.setVgap(5);
				layGridLayout2.setHgap(2);
				jPanelPrecio1.setLayout(layGridLayout2);
				jPanelPrecio1.add(getJLabel10(), null);
				jPanelPrecio1.add(getJLabelIvaFuerte(), null);
				jPanelPrecio1.setBackground(new java.awt.Color(112,149,182));
				jPanelPrecio1.setPreferredSize(new java.awt.Dimension(245,40));
			}
			return jPanelPrecio1;
		}
	
	/**
			 * This method initializes jPanelPrecio2
			 * 
			 * @return javax.swing.JPanel
			 */
			private javax.swing.JPanel getJPanelPrecio2() {
				if(jPanelPrecio2 == null) {
					jPanelPrecio2 = new javax.swing.JPanel();
					java.awt.GridLayout layGridLayout1 = new java.awt.GridLayout();
					layGridLayout1.setRows(1);
					layGridLayout1.setColumns(1);
					layGridLayout1.setHgap(5);
					layGridLayout1.setVgap(5);
					jPanelPrecio2.setLayout(layGridLayout1);
					jPanelPrecio2.add(getJLabel4(), null);
					jPanelPrecio2.setBackground(new java.awt.Color(112,149,182));
					jPanelPrecio2.setPreferredSize(new java.awt.Dimension(328,40));
					jPanelPrecio2.setAlignmentY(0.5F);
				}
				return jPanelPrecio2;
			}
	
	
	/**
				 * This method initializes jPanelPrecio4
				 * 
				 * @return javax.swing.JPanel
				 */
				private javax.swing.JPanel getJPanelPrecio4() {
					if(jPanelPrecio4 == null) {
						jPanelPrecio4 = new javax.swing.JPanel();
						java.awt.GridLayout layGridLayout1 = new java.awt.GridLayout();
						layGridLayout1.setRows(1);
						layGridLayout1.setColumns(1);
						layGridLayout1.setHgap(2);
						layGridLayout1.setVgap(5);
						jPanelPrecio4.setLayout(layGridLayout1);
						jPanelPrecio4.add(getJLabel5(), null);
						jPanelPrecio4.setBackground(new java.awt.Color(112,149,182));
						jPanelPrecio4.setPreferredSize(new java.awt.Dimension(250,40));
					}
					return jPanelPrecio4;
				}
	
	
	
	/**
	 * This method initializes jPanel4
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel4() {
		if(jPanel4 == null) {
			jPanel4 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout1 = new java.awt.FlowLayout();
			layFlowLayout1.setHgap(0);
			layFlowLayout1.setVgap(0);
			jPanel4.setLayout(layFlowLayout1);
			jPanel4.add(getJLabel1(), null);
			jPanel4.setBackground(new java.awt.Color(112,149,182));
		}
		return jPanel4;
	}
	/**
	 * This method initializes jLabel1
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel1() {
		if(jLabel1 == null) {
			jLabel1 = new javax.swing.JLabel();
			jLabel1.setText("");
			jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/becoblohm/cr/verificador/imagenes/bordeInferior.png")));
		}
		return jLabel1;
	}
	public void iniciarTimer(int tiempo) {
		new Temporizador(this, tiempo);
	}
	/**
	 * This method initializes jLabel2
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel2() {
		if(jLabel2 == null) {
			jLabel2 = new javax.swing.JLabel();
			jLabel2.setText("DESCRIPCIÓN:    ");
			jLabel2.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 16));
			jLabel2.setForeground(java.awt.Color.white);
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
			jLabel3 = new javax.swing.JLabel();
			jLabel3.setText("JLabel");
			jLabel3.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 18));
			jLabel3.setForeground(java.awt.Color.white);
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
			jLabel4 = new javax.swing.JLabel();
			jLabel4.setText("Precio Regular:");
			jLabel4.setForeground(java.awt.Color.white);
			jLabel4.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 13));
			jLabel4.setPreferredSize(new java.awt.Dimension(330,17));
			jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
		}
		return jLabel4;
	}
	
	/**
		 * This method initializes jLabelEspacioAdelante
		 * 
		 * @return javax.swing.JLabel
		 */
		private javax.swing.JLabel getJLabelEspacioAdelante() {
			if(jLabelEspacioAdelante == null) {
				jLabelEspacioAdelante = new javax.swing.JLabel();
				jLabelEspacioAdelante.setForeground(java.awt.Color.white);
				jLabelEspacioAdelante.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 14));
				jLabelEspacioAdelante.setPreferredSize(new java.awt.Dimension(127,35));
				jLabelEspacioAdelante.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
			}
			return jLabelEspacioAdelante;
		}
		
	/**
	
	/**
	 * This method initializes jLabel5
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel5() {
		if(jLabel5 == null) {
			jLabel5 = new javax.swing.JLabel();
			jLabel5.setText("Precio Promocional:");
			jLabel5.setForeground(java.awt.Color.white);
			jLabel5.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 12));
			jLabel5.setPreferredSize(new java.awt.Dimension(200,17));
			jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
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
			jLabel6 = new javax.swing.JLabel();
			jLabel6.setText("PRECIO");
			jLabel6.setForeground(java.awt.Color.white);
			jLabel6.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 18));
			jLabel6.setPreferredSize(new java.awt.Dimension(330,22));
			jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
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
			jLabel7 = new javax.swing.JLabel();
			jLabel7.setText("IVA");
			jLabel7.setForeground(java.awt.Color.white);
			jLabel7.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 18));
			jLabel7.setPreferredSize(new java.awt.Dimension((ancho/2)-240,22));
			jLabel7.setHorizontalTextPosition(javax.swing.SwingConstants.TRAILING);
			jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
		}
		return jLabel7;
	}
	/**
	 * This method initializes jLabel8
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel8() {
		if(jLabel8 == null) {
			jLabel8 = new javax.swing.JLabel();
			jLabel8.setText("JLabel");
			jLabel8.setForeground(java.awt.Color.yellow);
			jLabel8.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 20));
			jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
		}
		return jLabel8;
	}
	
	/**
		 * This method initializes jLabelFinalFuerte
		 * 
		 * @return javax.swing.JLabel
		 */
		private javax.swing.JLabel getJLabelFinalFuerte() {
			if(jLabelFinalFuerte == null) {
				jLabelFinalFuerte = new javax.swing.JLabel();
				jLabelFinalFuerte.setBorder( BorderFactory.createEmptyBorder(5,0,5,0));
				jLabelFinalFuerte.setForeground(java.awt.Color.yellow);
				jLabelFinalFuerte.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 17));
				jLabelFinalFuerte.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
			}
			return jLabelFinalFuerte;
		}
	
	/**
	 * This method initializes jLabel9
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel9() {
		if(jLabel9 == null) {
			jLabel9 = new javax.swing.JLabel();
			jLabel9.setText("+");
			jLabel9.setForeground(java.awt.Color.white);
			jLabel9.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 24));
			jLabel9.setPreferredSize(new java.awt.Dimension(80,22));
		}
		return jLabel9;
	}
	/**
	 * This method initializes jLabel10
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel10() {
		if(jLabel10 == null) {
			jLabel10 = new javax.swing.JLabel();
			jLabel10.setText("JLabel");
			jLabel10.setForeground(java.awt.Color.yellow);
			jLabel10.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 20));
			jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
			jLabel10.setPreferredSize(new java.awt.Dimension((ancho/2)-80,22));
		}
		return jLabel10;
	}
	
	/**
		 * This method initializes jLabelIvaFuerte
		 * 
		 * @return javax.swing.JLabel
		 */
		private javax.swing.JLabel getJLabelIvaFuerte() {
			if(jLabelIvaFuerte == null) {
				jLabelIvaFuerte = new javax.swing.JLabel();
				jLabelIvaFuerte.setForeground(java.awt.Color.yellow);
				jLabelIvaFuerte.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 17));
				jLabelIvaFuerte.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
				jLabelIvaFuerte.setPreferredSize(new java.awt.Dimension((ancho/2)-60,22));
			}
			return jLabelIvaFuerte;
		}
	
	/**
	 * This method initializes jLabel11
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel11() {
		if(jLabel11 == null) {
			jLabel11 = new javax.swing.JLabel();
			jLabel11.setText("Total a Pagar:");
			jLabel11.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 24));
			jLabel11.setForeground(new java.awt.Color(151,177,201));
			jLabel11.setPreferredSize(new java.awt.Dimension(332,43));
			jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
			jLabel11.setBackground(java.awt.Color.white);
		}
		return jLabel11;
	}
	
	/**
	 * This method initializes jLabel12
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel12() {
		if(jLabel12 == null) {
			jLabel12 = new javax.swing.JLabel();
			jLabel12.setText("JLabel");
			jLabel12.setForeground(new java.awt.Color(0,0,102));
			jLabel12.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 24));
			jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
			jLabel12.setPreferredSize(new java.awt.Dimension(ancho/2,22));
		}
		return jLabel12;
	}
	
	/**
		 * This method initializes jLabelTotalFuerte
		 * 
		 * @return javax.swing.JLabel
		 */
		private javax.swing.JLabel getJLabelTotalFuerte() {
			if(jLabelTotalFuerte == null) {
				jLabelTotalFuerte = new javax.swing.JLabel();
				jLabelTotalFuerte.setText("JLabel");
				jLabelTotalFuerte.setForeground(new java.awt.Color(0,0,102));
				jLabelTotalFuerte.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 20));
				jLabelTotalFuerte.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
				jLabelTotalFuerte.setPreferredSize(new java.awt.Dimension(270,22));
			}
			return jLabelTotalFuerte;
		}
	
	/**
	 * This method initializes jPanel5
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel5() {
		if(jPanel5 == null) {
			jPanel5 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout3 = new java.awt.FlowLayout();
			layFlowLayout3.setAlignment(java.awt.FlowLayout.LEFT);
			jPanel5.setLayout(layFlowLayout3);
			jPanel5.add(getJLabel2(), null);
			jPanel5.add(getJLabel3(), null);
			jPanel5.setPreferredSize(new java.awt.Dimension(ancho-50,40));
			jPanel5.setBackground(new java.awt.Color(112,149,182));
		}
		return jPanel5;
	}
	/**
	 * This method initializes jPanel7
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel7() {
		if(jPanel7 == null) {
			jPanel7 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout4 = new java.awt.FlowLayout();
			layFlowLayout4.setAlignment(java.awt.FlowLayout.LEFT);
			layFlowLayout4.setVgap(3);
			jPanel7.setLayout(layFlowLayout4);
			jPanel7.add(getJLabelEspacioAdelante(), null);
			jPanel7.add(getJPanelPrecio2(), null);
			jPanel7.add(getJPanelPrecio4(), null);
			jPanel7.setPreferredSize(new java.awt.Dimension(ancho-50,50));
			jPanel7.setBackground(new java.awt.Color(112,149,182));
			jPanel7.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.white,2));
		}
		return jPanel7;
	}
	
	/**
			 * This method initializes jPanelPrecio3
			 * 
			 * @return javax.swing.JPanel
			 */
			private javax.swing.JPanel getJPanelPrecio3() {
				if(jPanelPrecio3 == null) {
					jPanelPrecio3 = new javax.swing.JPanel();
					java.awt.GridLayout layGridLayout2 = new java.awt.GridLayout();
					layGridLayout2.setRows(2);
					layGridLayout2.setColumns(1);
					layGridLayout2.setVgap(5);
					layGridLayout2.setHgap(2);
					jPanelPrecio3.setLayout(layGridLayout2);
					jPanelPrecio3.add(getJLabel8(), null);
					jPanelPrecio3.add(getJLabelFinalFuerte(), null);
					jPanelPrecio3.setBackground(new java.awt.Color(112,149,182));
					jPanelPrecio3.setPreferredSize(new java.awt.Dimension(245,40));
				}
				return jPanelPrecio3;
			}
	
	
	
	/**
	 * This method initializes jPanel8
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel8() {
		if(jPanel8 == null) {
			jPanel8 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout8 = new java.awt.FlowLayout();
			layFlowLayout8.setAlignment(java.awt.FlowLayout.LEFT);
			jPanel8.setLayout(layFlowLayout8);
			jPanel8.add(getJLabel6(), null);
			jPanel8.add(getJLabel7(), null);
			jPanel8.setPreferredSize(new java.awt.Dimension(ancho-185,32));
			jPanel8.setBackground(new java.awt.Color(112,149,182));
			
		}
		return jPanel8;
	}

	/**
		 * This method initializes jPanelEspacio
		 * 
		 * @return javax.swing.JPanel
		 */
		private javax.swing.JPanel getJPanelEspacio() {
			if(jPanelEspacio == null) {
				jPanelEspacio = new javax.swing.JPanel();
				java.awt.FlowLayout layFlowLayout8 = new java.awt.FlowLayout();
				layFlowLayout8.setAlignment(java.awt.FlowLayout.LEFT);
				jPanelEspacio.setLayout(layFlowLayout8);
				jPanelEspacio.setPreferredSize(new java.awt.Dimension(130,32));
				jPanelEspacio.setBackground(new java.awt.Color(112,149,182));
			
			}
			return jPanelEspacio;
		}

	/**
		 * This method initializes jPanelEspacio1
		 * 
		 * @return javax.swing.JPanel
		 */
		private javax.swing.JPanel getJPanelEspacio1() {
			if(jPanelEspacio1 == null) {
				jPanelEspacio1 = new javax.swing.JPanel();
				java.awt.FlowLayout layFlowLayout8 = new java.awt.FlowLayout();
				layFlowLayout8.setAlignment(java.awt.FlowLayout.LEFT);
				jPanelEspacio1.setLayout(layFlowLayout8);
				jPanelEspacio1.setPreferredSize(new java.awt.Dimension(130,50));
				jPanelEspacio1.setBackground(new java.awt.Color(112,149,182));
			
			}
			return jPanelEspacio1;
		}

	/**
			 * This method initializes jPanelEspacio2
			 * 
			 * @return javax.swing.JPanel
			 */
			private javax.swing.JPanel getJPanelEspacio2() {
				if(jPanelEspacio2 == null) {
					jPanelEspacio2 = new javax.swing.JPanel();
					java.awt.FlowLayout layFlowLayout8 = new java.awt.FlowLayout();
					layFlowLayout8.setAlignment(java.awt.FlowLayout.LEFT);
					jPanelEspacio2.setLayout(layFlowLayout8);
					jPanelEspacio2.setPreferredSize(new java.awt.Dimension(128,50));
					jPanelEspacio2.setBackground(java.awt.Color.white);
					jPanelEspacio2.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.white,2));
				}
				return jPanelEspacio2;
			}

	/**
	 * This method initializes jPanel9
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel9() {
		if(jPanel9 == null) {
			jPanel9 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout9 = new java.awt.FlowLayout();
			layFlowLayout9.setAlignment(java.awt.FlowLayout.LEFT);
			layFlowLayout9.setVgap(5);
			jPanel9.setLayout(layFlowLayout9);
			jPanel9.add(getJPanelPrecio3(), null);
			jPanel9.add(getJLabel9(), null);
			jPanel9.add(getJPanelPrecio1(), null);
			jPanel9.setPreferredSize(new java.awt.Dimension(ancho-185,50));
			jPanel9.setBackground(new java.awt.Color(112,149,182));
			
		}
		return jPanel9;
	}
	
	/**
		 * This method initializes jPanelBlanco
		 * 
		 * @return javax.swing.JPanel
		 */
		private javax.swing.JPanel getJPanelBlanco() {
			if(jPanelBlanco == null) {
				jPanelBlanco = new javax.swing.JPanel();
				java.awt.FlowLayout layFlowLayout9 = new java.awt.FlowLayout();
				layFlowLayout9.setAlignment(java.awt.FlowLayout.LEFT);
				layFlowLayout9.setVgap(0);
				layFlowLayout9.setHgap(5);
				jPanelBlanco.setLayout(layFlowLayout9);
				//jPanelBlanco.add(getJPanelEspacio2(), null);
				jPanelBlanco.add(getJPanelEspacio2(), null);
				jPanelBlanco.add(getJLabel11(), null);
				jPanelBlanco.add(getJPanel12(), null);
				//jPanelBlanco.add(getJPanel10(), null);
				jPanelBlanco.setPreferredSize(new java.awt.Dimension(ancho-50,50));
				jPanelBlanco.setBackground(java.awt.Color.white);
				
				
			
			}
			return jPanelBlanco;
		}
	
	
	/**
	 * This method initializes jPanel10
	 * 
	 * @return javax.swing.JPanel
	 */
	@SuppressWarnings("unused")
	private javax.swing.JPanel getJPanel10() {
		if(jPanel10 == null) {
			jPanel10 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout7 = new java.awt.FlowLayout();
			layFlowLayout7.setAlignment(java.awt.FlowLayout.LEFT);
			layFlowLayout7.setHgap(2);
			layFlowLayout7.setVgap(0);
			jPanel10.setLayout(layFlowLayout7);
			jPanel10.setPreferredSize(new java.awt.Dimension(ancho-185,50));
			
		}
		return jPanel10;
	}
	/**
	 * This method initializes jPanel12
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel12() {
		if(jPanel12 == null) {
			jPanel12 = new javax.swing.JPanel();
			java.awt.GridLayout layGridLayout3 = new java.awt.GridLayout();
			layGridLayout3.setRows(2);
			layGridLayout3.setColumns(1);
			layGridLayout3.setVgap(1);
			jPanel12.setLayout(layGridLayout3);
			jPanel12.add(getJLabel12(), null);
			jPanel12.add(getJLabelTotalFuerte(), null);
			jPanel12.setPreferredSize(new java.awt.Dimension(260,50));
			jPanel12.setBackground(java.awt.Color.white);
		}
		return jPanel12;
	}

	/**
		 * This method initializes jPanel13
		 * 
		 * @return javax.swing.JPanel
		 */
		private javax.swing.JPanel getJPanel13() {
			if(jPanel13 == null) {
				jPanel13 = new javax.swing.JPanel();
				java.awt.FlowLayout layFlowLayout5 = new java.awt.FlowLayout();
				layFlowLayout5.setAlignment(java.awt.FlowLayout.RIGHT);
				jPanel13.setLayout(layFlowLayout5);
				jPanel13.setBackground(new java.awt.Color(112,149,182));
				if (alto==600)
					jPanel13.setPreferredSize(new java.awt.Dimension(ancho-50,(40*alto)/600));
				else
					jPanel13.setPreferredSize(new java.awt.Dimension(ancho-50,(80*alto)/600));
			}
			return jPanel13;
		}

	/**
		 * This method initializes jPanel14
		 * 
		 * @return javax.swing.JPanel
		 */
		private javax.swing.JPanel getJPanel14() {
			if(jPanel14 == null) {
				jPanel14 = new javax.swing.JPanel();
				java.awt.FlowLayout layFlowLayout5 = new java.awt.FlowLayout();
				layFlowLayout5.setAlignment(java.awt.FlowLayout.CENTER);
				jPanel14.setLayout(layFlowLayout5);
				jPanel14.add(getJLabel13(), null);
				jPanel14.setBackground(new java.awt.Color(151,177,202));
				jPanel14.setPreferredSize(new java.awt.Dimension(ancho-50,50));
				jPanel14.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 18));
			}
			return jPanel14;
		}
	/**
		 * This method initializes jPanel15
		 * 
		 * @return javax.swing.JPanel
		 */
		private javax.swing.JPanel getJPanel15() {
			if(jPanel15 == null) {
				jPanel15 = new javax.swing.JPanel();
				java.awt.FlowLayout layFlowLayout5 = new java.awt.FlowLayout();
				layFlowLayout5.setAlignment(java.awt.FlowLayout.RIGHT);
				jPanel15.setLayout(layFlowLayout5);
				jPanel15.setBackground(new java.awt.Color(112,149,182));
				jPanel15.setPreferredSize(new java.awt.Dimension(ancho-50,(alto/2)-150));
				jPanel15.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 18));
			}
			return jPanel15;
		}
	/**
		 * This method initializes jPanel16
		 * 
		 * @return javax.swing.JPanel
		 */
		private javax.swing.JPanel getJPanel16() {
			if(jPanel16 == null) {
				jPanel16 = new javax.swing.JPanel();
				java.awt.FlowLayout layFlowLayout5 = new java.awt.FlowLayout();
				layFlowLayout5.setAlignment(java.awt.FlowLayout.RIGHT);
				jPanel16.setLayout(layFlowLayout5);
				jPanel16.setBackground(new java.awt.Color(112,149,182));
				jPanel16.setPreferredSize(new java.awt.Dimension(ancho-50,(alto/2)-150));
				jPanel16.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 18));
			}
			return jPanel16;
		}
	/**
	 * This method initializes jLabel13
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel13() {
		if(jLabel13 == null) {
			jLabel13 = new javax.swing.JLabel();
			jLabel13.setText("ETIQUETA INVALIDA");
			jLabel13.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 36));
			jLabel13.setForeground(new java.awt.Color(3,86,149));
		}
		return jLabel13;
	}
}
