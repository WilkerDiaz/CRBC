/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblohm.cr.gui
 * Programa   : AnularProducto.java
 * Creado por : Gabriel Martinelli <gmartinelli@beco.com.ve>
 * Creado en  : Mar 29, 2004 - 3:16:30 PM
 *
 * (c) Becoblohm, C.A.
 * -----------------------------------------------------------------------------
 * 				Actualizaciones
 * -----------------------------------------------------------------------------
 * Versión     : 2.0
 * Fecha       : 20-jun-2006
 * Analista    : yzambrano
 * Descripción : Opción para registro de nuevo cliente en apartados 
 * =============================================================================
 * Versión     : 2.0
 * Fecha       : 20-jun-2006
 * Analista    : yzambrano
 * Descripción : Opción para modificar datos de afiliado al asignar afiliado en 
 * 				 apartado.
 * =============================================================================
 * Versión     : 2.0
 * Fecha       : 17-ag-2006
 * Analista    : yzambrano
 * Descripción : Cantidad a devolver para ejecutar devolución de producto por cambio.
 * =============================================================================
 */
package com.becoblohm.cr.gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.StringTokenizer;

import javax.swing.ImageIcon;
import javax.swing.JDialog;

import org.apache.log4j.Logger;

import com.becoblohm.cr.CR;
import com.becoblohm.cr.excepciones.AfiliadoUsrExcepcion;
import com.becoblohm.cr.excepciones.AutorizacionExcepcion;
import com.becoblohm.cr.excepciones.ConexionExcepcion;
import com.becoblohm.cr.excepciones.ExcepcionCr;
import com.becoblohm.cr.excepciones.ProductoExcepcion;
import com.becoblohm.cr.excepciones.UsuarioExcepcion;
import com.becoblohm.cr.manejadorsesion.Sesion;
import com.becoblohm.cr.manejarventa.Cliente;
import com.becoblohm.cr.utiles.Control;
import com.becoblohm.cr.utiles.MensajesVentanas;
/**
 * @author vmedina
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class EjecutarConCantidad extends JDialog implements ComponentListener, KeyListener, MouseListener, EventListener{

	private static final long serialVersionUID = 1L;

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(EjecutarConCantidad.class);

	private javax.swing.JPanel jContentPane = null;

	private javax.swing.JPanel jPanel2 = null;
	private javax.swing.JButton jButton = null;
	private javax.swing.JButton jButton1 = null;
	private javax.swing.JPanel jPanel3 = null;
	private javax.swing.JPanel jPanel4 = null;
	private javax.swing.JLabel jLabel1 = null;
	private javax.swing.JPanel jPanel1 = null;
	private javax.swing.JTextField jTextField1 = null;
	private javax.swing.JPanel jPanel5 = null;
	private int renglonCambiar;
	private int caso;
	private boolean ejecutado = false;

	private static RegistroClientesNuevos registroCliente = null;
	
	/**
	 * This is the default constructor
	 */
	public EjecutarConCantidad(String mensaje, int filaACambiar, float defecto, int caso) {
		super(MensajesVentanas.ventanaActiva);
		initialize(caso);
		
		jTextField1.addKeyListener(this);
		jTextField1.addMouseListener(this);

		jButton.addKeyListener(this);
		jButton.addMouseListener(this);
		
		jButton1.addKeyListener(this);
		jButton1.addMouseListener(this);
		
		this.renglonCambiar = filaACambiar;
		DecimalFormat df = new DecimalFormat("#,##0.00");
		this.getJTextField1().setText(df.format(defecto));
		this.getJTextField1().selectAll();
		this.getJLabel1().setText(mensaje);
	}

	/**
	 * This is the default constructor
	 */
	public EjecutarConCantidad(String mensaje, String defecto, int caso) {
		super(MensajesVentanas.ventanaActiva);
		initialize(caso);
		
		jTextField1.addKeyListener(this);
		jTextField1.addMouseListener(this);

		jButton.addKeyListener(this);
		jButton.addMouseListener(this);
		
		jButton1.addKeyListener(this);
		jButton1.addMouseListener(this);
		
		this.getJTextField1().setText(defecto);
		this.getJLabel1().setText(mensaje);
		
	}
	
	/**
	 * This method initializes this
	 * 
	 * @return void-
	 */
	private void initialize(int c) {
		if (logger.isDebugEnabled())
			logger.debug("initialize(int) - start");

		this.caso = c;
		this.setSize(430, 188);
		this.setContentPane(getJContentPane());
		this.setUndecorated(true);
		this.setModal(true);
		this.setBackground(new java.awt.Color(226,226,222));
		this.addComponentListener(this);

		switch (caso) {
			case 3: this.setTitle("Facturación");
					break;
			case 4: this.setTitle("Apartado");
					break;
			case 5: 
			case 6: 
			case 7: 
			case 10: this.setTitle("Clientes");
					break;
			default:this.setTitle("Devolución");		
		}
		/*if (c == 3)
			this.setTitle("Facturación");
		else if (c==4)
			this.setTitle("Apartado");
		else if (c==6)
			this.setTitle("Clientes");
		else if (c==7)
			this.setTitle("Lista de Regalos");
		else
			this.setTitle("Devolución");
		*/
		if (logger.isDebugEnabled())
			logger.debug("initialize(int) - end");
	}
	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if (logger.isDebugEnabled())
			logger.debug("getJContentPane() - start");

		if (jContentPane == null) {
			jContentPane = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout14 = new java.awt.FlowLayout();
			layFlowLayout14.setHgap(0);
			layFlowLayout14.setVgap(0);
			jContentPane.setLayout(layFlowLayout14);
			jContentPane.add(getJPanel3(), null);
			jContentPane.add(getJPanel2(), null);
			jContentPane.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.SoftBevelBorder.RAISED));
			jContentPane.setBackground(new java.awt.Color(226,226,222));
		}

		if (logger.isDebugEnabled())
			logger.debug("getJContentPane() - end");
		return jContentPane;
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato de los distintos ArrayList
	* Fecha: agosto 2011
	*/
	public void keyPressed(KeyEvent e) {
		if (logger.isDebugEnabled())
			logger.debug("keyPressed(KeyEvent) - start");
		String id = getJTextField1().getText();
		if((e.getKeyCode() == KeyEvent.VK_ENTER)&&(e.getSource().equals(jButton1)||e.getSource().equals(jTextField1))) {
			switch (caso) {
				case 1: // Queremos ejecutar una devolucion de Producto
						try {
							CR.meVenta.devolverProducto(this.renglonCambiar,Sesion.capturaTeclado,new Float(CR.me.formatoNumerico(getJTextField1().getText())).floatValue());
							dispose();
						}catch(ExcepcionCr f){
							logger.error("keyPressed(KeyEvent)", f);

							MensajesVentanas.mensajeError(f.getMensaje());
						}catch(Exception f){
							logger.error("keyPressed(KeyEvent)", f);

							MensajesVentanas.mensajeError("Cantidad Invalida");
						}
						break;
				case 2: // Queremos ejecutar una anulacion de producto
						try {
							CR.meVenta.anularProducto(this.renglonCambiar,new Float(CR.me.formatoNumerico(getJTextField1().getText())).floatValue());
							dispose();
						}catch(ExcepcionCr f){
							logger.error("keyPressed(KeyEvent)", f);

							MensajesVentanas.mensajeError(f.getMensaje());
						}catch(Exception f){
							logger.error("keyPressed(KeyEvent)", f);

							MensajesVentanas.mensajeError("Cantidad Invalida");
						}
						break;
				case 3: // Ejecutamos un ingreso de producto para Venta
						try {
							ArrayList<Object> valorLeido = Control.codigoValido(getJTextField1().getText());
							int tipoCodigo = ((Integer)valorLeido.get(0)).intValue();
							String codigo = (String)valorLeido.get(1);
							switch (tipoCodigo){
								case Control.PRODUCTO:
									CR.meVenta.ingresarLineaProducto(codigo, Sesion.capturaTeclado);
									break;
								case Control.CLIENTE:
									MensajesVentanas.mensajeError("Código no registrado");
									break;
								case Control.COLABORADOR:
									MensajesVentanas.mensajeError("Código no registrado");
									break;
								case Control.CODIGO_DESCONOCIDO: 
									CR.meVenta.ingresarLineaProducto(codigo, Sesion.capturaTeclado);
									break;
							}
							dispose();					
						} catch (AfiliadoUsrExcepcion e1) {
							logger.error("keyPressed(KeyEvent)", e1);

							dispose();
							MensajesVentanas.mensajeError(e1.getMensaje());
						} catch (UsuarioExcepcion e1) {
							logger.error("keyPressed(KeyEvent)", e1);

							dispose();
							MensajesVentanas.mensajeError(e1.getMensaje());
						} catch (AutorizacionExcepcion e1) {
							logger.error("keyPressed(KeyEvent)", e1);

							dispose();
							MensajesVentanas.mensajeError(e1.getMensaje());
						} catch (ProductoExcepcion e1) {
							logger.error("keyPressed(KeyEvent) "+ e1.getMensaje());

							dispose();
							MensajesVentanas.mensajeError(e1.getMensaje());
						} catch (ExcepcionCr e1) {
							logger.error("keyPressed(KeyEvent)", e1);

							dispose();
							MensajesVentanas.mensajeError(e1.getMensaje());
						} catch (ConexionExcepcion e1) {
							logger.error("keyPressed(KeyEvent)", e1);

							dispose();
							MensajesVentanas.mensajeError(e1.getMensaje());
						}
						break;
				case 4: // Ejecutamos un ingreso de producto para Apartado
						try {
							ArrayList <Object>valorLeido = Control.codigoValido(getJTextField1().getText());
							int tipoCodigo = ((Integer)valorLeido.get(0)).intValue();
							String codigo = (String)valorLeido.get(1);
							switch (tipoCodigo){
								case Control.PRODUCTO:
									CR.meServ.ingresarProductoApartado(codigo, Sesion.capturaTeclado);
									break;
								case Control.CLIENTE:
									Cliente ctemp = CR.meServ.buscarClienteTemporal(codigo); //Buscar el cliente temporal
									//String id = getJTextField1().getText();
									dispose();
									registroCliente = null;			
									if(ctemp != null) { //Si lo encuentra, cargar ventana de registro para verificar datos
										registroCliente = new RegistroClientesNuevos(ctemp);
										MensajesVentanas.centrarVentanaDialogo(registroCliente);
									} else if(MensajesVentanas.preguntarSiNo("Cliente no encontrado.\n¿Desea registrar los datos del cliente?") == 0)
									{								
										registroCliente = new RegistroClientesNuevos(id);
										MensajesVentanas.centrarVentanaDialogo(registroCliente);
									}
								
									break;
								case Control.COLABORADOR:
									CR.meServ.asignarCliente(codigo);
									break;
								case Control.CODIGO_DESCONOCIDO: 
									try {
										CR.meServ.ingresarProductoApartado(codigo, Sesion.capturaTeclado);
									} catch (ExcepcionCr ex2) {
										logger.error("keyPressed(KeyEvent)",
												ex2);
										ctemp = CR.meServ.buscarClienteTemporal(codigo);
										id = getJTextField1().getText();
										dispose();
										registroCliente = null;			
										if(ctemp != null) {
											registroCliente = new RegistroClientesNuevos(ctemp);
											MensajesVentanas.centrarVentanaDialogo(registroCliente);
										} else if (ex2.getMensaje().equals("Código no registrado"))
											MensajesVentanas.mensajeError(ex2.getMensaje());
										else if(MensajesVentanas.preguntarSiNo("Cliente no encontrado.\n¿Desea registrar los datos del cliente?") == 0)
										{								
											registroCliente = new RegistroClientesNuevos(id);
											MensajesVentanas.centrarVentanaDialogo(registroCliente);
										}
									}
									break;
							}
							dispose();
						} catch (AfiliadoUsrExcepcion e1) {
							logger.error("keyPressed(KeyEvent)", e1);

							dispose();
							MensajesVentanas.mensajeError(e1.getMensaje());
						} catch (UsuarioExcepcion e1) {
							logger.error("keyPressed(KeyEvent)", e1);

							dispose();
							MensajesVentanas.mensajeError(e1.getMensaje());
						} catch (AutorizacionExcepcion e1) {
							logger.error("keyPressed(KeyEvent)", e1);

							dispose();
							MensajesVentanas.mensajeError(e1.getMensaje());
						} catch (ExcepcionCr e1) {
							logger.error("keyPressed(KeyEvent)", e1);

							dispose();
							MensajesVentanas.mensajeError("Código no registrado");
						} catch (ConexionExcepcion e1) {
							logger.error("keyPressed(KeyEvent)", e1);

							dispose();
							MensajesVentanas.mensajeError(e1.getMensaje());
						}
						break;
				case 5: // Ejecutamos un ingreso de cliente para devolucion
						try {
							ArrayList <Object>valorLeido = Control.codigoValido(getJTextField1().getText());
							String codigo = (String)valorLeido.get(1);
							CR.meVenta.asignarClienteDevolucion(codigo);
							dispose();
						} catch (ExcepcionCr e1) {
							logger.error("keyPressed(KeyEvent)", e1);

							dispose();
							MensajesVentanas.mensajeError("Código no registrado");
						} catch (ConexionExcepcion e1) {
							logger.error("keyPressed(KeyEvent)", e1);

							dispose();
							MensajesVentanas.mensajeError(e1.getMensaje());
						}
						break;
				case 6: // Ejecutamos una asignacion de clientes para la venta
					try {
						ArrayList <Object>valorLeido = Control.codigoValido(getJTextField1().getText());
						int tipoCodigo = ((Integer)valorLeido.get(0)).intValue();
						String codigo = (String)valorLeido.get(1);
						switch (tipoCodigo){
							case Control.PRODUCTO:
								MensajesVentanas.mensajeError("Código no registrado");
								break;
							case Control.CLIENTE:
								CR.meVenta.asignarCliente(codigo);
								break;
							case Control.COLABORADOR:
								CR.meVenta.asignarCliente(codigo);
								break;
							case Control.CODIGO_DESCONOCIDO: 
								CR.meVenta.asignarCliente(codigo);
								break;
						}
						dispose();					
					} catch (AfiliadoUsrExcepcion e1) {
						logger.error("keyPressed(KeyEvent)", e1);

						dispose();
						MensajesVentanas.mensajeError(e1.getMensaje());
					} catch (UsuarioExcepcion e1) {
						logger.error("keyPressed(KeyEvent)", e1);

						dispose();
						MensajesVentanas.mensajeError(e1.getMensaje());
					} catch (AutorizacionExcepcion e1) {
						logger.error("keyPressed(KeyEvent)", e1);

						dispose();
						MensajesVentanas.mensajeError(e1.getMensaje());
					} catch (ExcepcionCr e1) {
						logger.error("keyPressed(KeyEvent)", e1);

						dispose();
						MensajesVentanas.mensajeError(/*"Código no registrado"*/ e1.getMensaje());
					} catch (ConexionExcepcion e1) {
						logger.error("keyPressed(KeyEvent)", e1);

						dispose();
						MensajesVentanas.mensajeError(e1.getMensaje());
					}
					break;
				case 7: //Cliente nuevo en apartados				
					try {
						ArrayList <Object>valorLeido = Control.codigoValido(getJTextField1().getText());
						String codigo = (String)valorLeido.get(1);
		
						Cliente ctemp = CR.meServ.buscarClienteTemporal(codigo);
						//String id = getJTextField1().getText();
						dispose();
						registroCliente = null;			
						if(ctemp != null) {
							registroCliente = new RegistroClientesNuevos(ctemp);
							MensajesVentanas.centrarVentanaDialogo(registroCliente);
						} else if(MensajesVentanas.preguntarSiNo("Cliente no encontrado.\n¿Desea registrar los datos del cliente?") == 0) {		
							//Eliminar presencia de mas de un guión(-) en el código
							StringTokenizer tokenizer = new StringTokenizer(id, "-");
							String identificador="";
							while (tokenizer.hasMoreTokens()){
								identificador = identificador + tokenizer.nextToken();	
							}
							registroCliente = new RegistroClientesNuevos(identificador);
							MensajesVentanas.centrarVentanaDialogo(registroCliente);
						}
					} catch (AfiliadoUsrExcepcion e1) {
						logger.error("keyPressed(KeyEvent)", e1);
						dispose();
						MensajesVentanas.mensajeError(e1.getMensaje());
					} catch (UsuarioExcepcion e1) {
						logger.error("keyPressed(KeyEvent)", e1);
						dispose();
						MensajesVentanas.mensajeError(e1.getMensaje());
					} catch (AutorizacionExcepcion e1) {
						logger.error("keyPressed(KeyEvent)", e1);
						dispose();
						MensajesVentanas.mensajeError(e1.getMensaje());
					} catch (ExcepcionCr e1) {
						logger.error("keyPressed(KeyEvent)", e1);
						dispose();
						if(MensajesVentanas.preguntarSiNo("Cliente no encontrado.\n¿Desea registrar los datos del cliente?") == 0)
						{								
							//Eliminar presencia de mas de un guión(-) en el código
							StringTokenizer tokenizer = new StringTokenizer(id, "-");
							String identificador="";
							while (tokenizer.hasMoreTokens()){
								identificador = identificador + tokenizer.nextToken();	
							}
							registroCliente = new RegistroClientesNuevos(identificador);
							MensajesVentanas.centrarVentanaDialogo(registroCliente);
						}
						
						//MensajesVentanas.mensajeError(/*"Código no registrado"*/e1.getMensaje());
					} catch (ConexionExcepcion e1) {
						logger.error("keyPressed(KeyEvent)", e1);
						dispose();
						MensajesVentanas.mensajeError(e1.getMensaje());
					}
					break;
				case 8: // Queremos ejecutar una devolucion de Producto por cambio
					try {
						CR.meVenta.devolverProductoXCambio(this.renglonCambiar,Sesion.capturaTeclado,new Float(CR.me.formatoNumerico(getJTextField1().getText())).floatValue());
						
						dispose();
					}catch(ExcepcionCr f){
						logger.error("keyPressed(KeyEvent)", f);

						MensajesVentanas.mensajeError(f.getMensaje());
					}catch(Exception f){
						logger.error("keyPressed(KeyEvent)", f);

						MensajesVentanas.mensajeError("Cantidad Invalida");
					}
					break;
				case 9: // Queremos ejecutar una anulacion de producto
					try {
						CR.meVenta.anularProductoXCambio(this.renglonCambiar,new Float(CR.me.formatoNumerico(getJTextField1().getText())).floatValue());
						dispose();
					}catch(ExcepcionCr f){
						logger.error("keyPressed(KeyEvent)", f);

						MensajesVentanas.mensajeError(f.getMensaje());
					}catch(Exception f){
						logger.error("keyPressed(KeyEvent)", f);

						MensajesVentanas.mensajeError("Cantidad Invalida");
					}
				break;
				case 10: // Ejecutamos una asignacion de clientes para la venta en DevXCambio
					try {
						ArrayList <Object>valorLeido = Control.codigoValido(getJTextField1().getText());
						int tipoCodigo = ((Integer)valorLeido.get(0)).intValue();
						String codigo = (String)valorLeido.get(1);
						switch (tipoCodigo){
							case Control.PRODUCTO:
								MensajesVentanas.mensajeError("Código no registrado");
								break;
							case Control.CLIENTE:
								CR.meVenta.asignarCliente(codigo, false);
								break;
							case Control.COLABORADOR:
								CR.meVenta.asignarCliente(codigo, false);
								break;
							case Control.CODIGO_DESCONOCIDO: 
								CR.meVenta.asignarCliente(codigo, false);
								break;
						}
						dispose();					
					} catch (AfiliadoUsrExcepcion e1) {
						logger.error("keyPressed(KeyEvent)", e1);

						dispose();
						MensajesVentanas.mensajeError(e1.getMensaje());
					} catch (UsuarioExcepcion e1) {
						logger.error("keyPressed(KeyEvent)", e1);

						dispose();
						MensajesVentanas.mensajeError(e1.getMensaje());
					} catch (AutorizacionExcepcion e1) {
						logger.error("keyPressed(KeyEvent)", e1);
					} catch (ExcepcionCr e1){
						logger.error("keyPressed(KeyEvent)", e1);
		
						MensajesVentanas.mensajeError(e1.getMensaje());
					} catch (ConexionExcepcion e1){
						logger.error("keyPressed(KeyEvent)", e1);
				
						MensajesVentanas.mensajeError(e1.getMensaje());
					}
					break;
				case 11: // Ejecutamos un ingreso de producto para Lista de Regalos
					try {
						ArrayList <Object>valorLeido = Control.codigoValido(getJTextField1().getText());
						int tipoCodigo = ((Integer)valorLeido.get(0)).intValue();
						String codigo = (String)valorLeido.get(1);
						switch (tipoCodigo){
							case Control.PRODUCTO:
								CR.meServ.ingresarProductoLR(codigo, Sesion.capturaTeclado);
								break;
							case Control.CLIENTE:
								MensajesVentanas.mensajeError("Código no registrado");
								break;
							case Control.COLABORADOR:
								MensajesVentanas.mensajeError("Código no registrado");
								break;
							case Control.CODIGO_DESCONOCIDO: 
								try {
									CR.meServ.ingresarProductoLR(codigo, Sesion.capturaTeclado);
								} catch (ExcepcionCr ex2) {
									logger.error("keyPressed(KeyEvent)",ex2);
									MensajesVentanas.mensajeError("Código no registrado");
								}
								break;
						}
						dispose();
					} catch (AfiliadoUsrExcepcion e1) {
						logger.error("keyPressed(KeyEvent)", e1);

						dispose();
						MensajesVentanas.mensajeError(e1.getMensaje());
					} catch (UsuarioExcepcion e1) {
						logger.error("keyPressed(KeyEvent)", e1);

						dispose();
						MensajesVentanas.mensajeError(e1.getMensaje());
					} catch (AutorizacionExcepcion e1) {
						logger.error("keyPressed(KeyEvent)", e1);

						dispose();
						MensajesVentanas.mensajeError(e1.getMensaje());
					} catch (ExcepcionCr e1) {
						logger.error("keyPressed(KeyEvent)", e1);

						dispose();
						MensajesVentanas.mensajeError("Código no registrado");
					} catch (ConexionExcepcion e1) {
						logger.error("keyPressed(KeyEvent)", e1);

						dispose();
						MensajesVentanas.mensajeError(e1.getMensaje());
					}
					break;
				case 12: // Ejecutamos un ingreso de producto para venta de Lista de Regalos
					try {
						ArrayList <Object>valorLeido = Control.codigoValido(getJTextField1().getText());
						int tipoCodigo = ((Integer)valorLeido.get(0)).intValue();
						String codigo = (String)valorLeido.get(1);
						boolean pertenece = CR.meServ.getListaRegalos().perteneceProducto(codigo, Sesion.capturaTeclado);
						switch (tipoCodigo){
							case Control.PRODUCTO:
								if(pertenece)
									CR.meVenta.ingresarLineaProductoVentaLR(codigo, Sesion.capturaTeclado,0);
								else
									MensajesVentanas.mensajeError("El producto no pertenece a la Lista o\nIngresó una cantidad mayor a la cantidad restante");
								break;
							case Control.CLIENTE:
								MensajesVentanas.mensajeError("Código no registrado");
								break;
							case Control.COLABORADOR:
								MensajesVentanas.mensajeError("Código no registrado");
								break;
							case Control.CODIGO_DESCONOCIDO:
								if(pertenece)
									CR.meVenta.ingresarLineaProductoVentaLR(codigo, Sesion.capturaTeclado,0);
								else
									MensajesVentanas.mensajeError("El producto no pertenece a la Lista o\nIngresó una cantidad mayor a la cantidad restante");
								break;
						}
						dispose();
					} catch (AfiliadoUsrExcepcion e1) {
						logger.error("keyPressed(KeyEvent)", e1);
		
						dispose();
						MensajesVentanas.mensajeError(e1.getMensaje());
					} catch (UsuarioExcepcion e1) {
						logger.error("keyPressed(KeyEvent)", e1);
		
						dispose();
						MensajesVentanas.mensajeError(e1.getMensaje());
					} catch (AutorizacionExcepcion e1) {
						logger.error("keyPressed(KeyEvent)", e1);
		
						dispose();
						MensajesVentanas.mensajeError(e1.getMensaje());
					} catch (ExcepcionCr e1) {
						logger.error("keyPressed(KeyEvent)", e1);
		
						dispose();
						MensajesVentanas.mensajeError("Código no registrado");
					} catch (ConexionExcepcion e1) {
						logger.error("keyPressed(KeyEvent)", e1);
		
						dispose();
						MensajesVentanas.mensajeError(e1.getMensaje());
					}
					break;
				case 13: // Ejecutamos un ingreso de cliente para venta de Lista de Regalos
					try {
						ArrayList <Object>valorLeido = Control.codigoValido(getJTextField1().getText());
						int tipoCodigo = ((Integer)valorLeido.get(0)).intValue();
						String codigo = (String)valorLeido.get(1);
						switch (tipoCodigo){
							case Control.PRODUCTO:
								MensajesVentanas.mensajeError("Código no registrado");
								break;
							case Control.CLIENTE:
								CR.meServ.asignarClienteLR(codigo);
								break;
							case Control.COLABORADOR:
								CR.meServ.asignarClienteLR(codigo);
								break;
							case Control.CODIGO_DESCONOCIDO:
								try{
									CR.meServ.asignarClienteLR(codigo);
								}catch(ExcepcionCr e2){
									MensajesVentanas.mensajeError("Código no registrado");
								}
							break;
						}
						dispose();
					} catch (AfiliadoUsrExcepcion e1) {
						logger.error("keyPressed(KeyEvent)", e1);
		
						dispose();
						MensajesVentanas.mensajeError(e1.getMensaje());
					} catch (UsuarioExcepcion e1) {
						logger.error("keyPressed(KeyEvent)", e1);
		
						dispose();
						MensajesVentanas.mensajeError(e1.getMensaje());
					} catch (AutorizacionExcepcion e1) {
						logger.error("keyPressed(KeyEvent)", e1);
		
						dispose();
						MensajesVentanas.mensajeError(e1.getMensaje());
					} catch (ExcepcionCr e1) {
						logger.error("keyPressed(KeyEvent)", e1);
		
						dispose();
						MensajesVentanas.mensajeError(/*"Código no registrado"*/e1.getMensaje());
					} catch (ConexionExcepcion e1) {
						logger.error("keyPressed(KeyEvent)", e1);
		
						dispose();
						MensajesVentanas.mensajeError(e1.getMensaje());
					}
					break;
				case 14: // Ejecutamos un ingreso de titular de Lista de Regalos
					try {
						ArrayList <Object>valorLeido = Control.codigoValido(getJTextField1().getText());
						int tipoCodigo = ((Integer)valorLeido.get(0)).intValue();
						String codigo = (String)valorLeido.get(1);
						switch (tipoCodigo){
							case Control.PRODUCTO:
								MensajesVentanas.mensajeError("Código no registrado");
								break;
							case Control.CLIENTE:
								CR.meServ.asignarTitularLR(codigo);
								break;
							case Control.COLABORADOR:
								CR.meServ.asignarTitularLR(codigo);
								break;
							case Control.CODIGO_DESCONOCIDO:
								try{
									CR.meServ.asignarTitularLR(codigo);
								}catch(ExcepcionCr e2){
									MensajesVentanas.mensajeError("Código no registrado");
								}
							break;
						}
						dispose();
					} catch (AfiliadoUsrExcepcion e1) {
						logger.error("keyPressed(KeyEvent)", e1);
		
						dispose();
						MensajesVentanas.mensajeError(e1.getMensaje());
					} catch (UsuarioExcepcion e1) {
						logger.error("keyPressed(KeyEvent)", e1);
		
						dispose();
						MensajesVentanas.mensajeError(e1.getMensaje());
					} catch (AutorizacionExcepcion e1) {
						logger.error("keyPressed(KeyEvent)", e1);
		
						dispose();
						MensajesVentanas.mensajeError(e1.getMensaje());
					} catch (ExcepcionCr e1) {
						logger.error("keyPressed(KeyEvent)", e1);
		
						dispose();
						MensajesVentanas.mensajeError("Código no registrado");
					} catch (ConexionExcepcion e1) {
						logger.error("keyPressed(KeyEvent)", e1);
		
						dispose();
						MensajesVentanas.mensajeError(e1.getMensaje());
					}
					break;
				case 15: // Ejecutamos un ingreso de Vendedor Corporativo para BR Electrónico
					try {
						ArrayList <Object>valorLeido = Control.codigoValido(getJTextField1().getText());
						String codigo = (String)valorLeido.get(1);
						CR.meServ.asignarVendedorCorporativoBR(codigo);
						dispose();
					} catch (ExcepcionCr e1) {
						logger.error("keyPressed(KeyEvent)", e1);
						MensajesVentanas.mensajeError("Código no registrado");
					} catch (ConexionExcepcion e1) {
						logger.error("keyPressed(KeyEvent)", e1);
						MensajesVentanas.mensajeError(e1.getMensaje());
					}
					break;
			}
			this.ejecutado = true;
		} 
		else if((e.getKeyCode() == KeyEvent.VK_ENTER)&&(e.getSource().equals(jButton)))
			dispose();
		else if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
			dispose();

		if (logger.isDebugEnabled())
			logger.debug("keyPressed(KeyEvent) - end");
	}

	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
	 */
	public void keyReleased(KeyEvent e) {
		if (logger.isDebugEnabled())
			logger.debug("keyReleased(KeyEvent) - start");

		if (e.getSource().equals(getJTextField1())&&(caso<3)) {
			String textoFormateado = CR.me.formatearCampoNumerico(getJTextField1().getText(), e);
			if (textoFormateado!=null)
				getJTextField1().setText(textoFormateado);
	}

		if (logger.isDebugEnabled())
			logger.debug("keyReleased(KeyEvent) - end");
	}

	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
	 */
	public void keyTyped(KeyEvent e) {
		if (logger.isDebugEnabled())
			logger.debug("keyTyped(KeyEvent) - start");
				
		if (logger.isDebugEnabled())
			logger.debug("keyTyped(KeyEvent) - end");
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato de los distintos ArrayList
	* Fecha: agosto 2011
	*/
	public void mouseReleased(MouseEvent e) {
		if (logger.isDebugEnabled())
			logger.debug("mouseClicked(MouseEvent) - start");

		if(e.getSource().equals(jButton))
			dispose();
		else if(e.getSource().equals(jButton1)) {
			switch (caso) {
				case 1: // Queremos ejecutar una devolucion de Producto
						try {
							CR.meVenta.devolverProducto(this.renglonCambiar,Sesion.capturaTeclado,new Float(CR.me.formatoNumerico(getJTextField1().getText())).floatValue());
							dispose();
						}catch(ExcepcionCr f){
							logger.error("mouseClicked(MouseEvent)", f);

							MensajesVentanas.mensajeError(f.getMensaje());
						}catch(Exception f){
							logger.error("mouseClicked(MouseEvent)", f);

							MensajesVentanas.mensajeError("Cantidad Invalida");
						}
						break;
				case 2: // Queremos ejecutar una anulacion de producto
						try {
							CR.meVenta.anularProducto(this.renglonCambiar,new Float(CR.me.formatoNumerico(getJTextField1().getText())).floatValue());
							dispose();
						}catch(ExcepcionCr f){
							logger.error("mouseClicked(MouseEvent)", f);

							MensajesVentanas.mensajeError(f.getMensaje());
						}catch(Exception f){
							logger.error("mouseClicked(MouseEvent)", f);

							MensajesVentanas.mensajeError("Cantidad Invalida");
						}
						break;
				case 3: // Ejecutamos un ingreso de producto para Venta
					try {
						ArrayList <Object>valorLeido = Control.codigoValido(getJTextField1().getText());
						int tipoCodigo = ((Integer)valorLeido.get(0)).intValue();
						String codigo = (String)valorLeido.get(1);
						switch (tipoCodigo){
							case Control.PRODUCTO:
								CR.meVenta.ingresarLineaProducto(codigo, Sesion.capturaTeclado);
								break;
							case Control.CLIENTE:
								MensajesVentanas.mensajeError("Código no registrado");
								break;
							case Control.COLABORADOR:
								MensajesVentanas.mensajeError("Código no registrado");
								break;
							case Control.CODIGO_DESCONOCIDO: 
								CR.meVenta.ingresarLineaProducto(codigo, Sesion.capturaTeclado);
								break;
						}
						dispose();					
					} catch (AfiliadoUsrExcepcion e1) {
						logger.error("mouseClicked(MouseEvent)", e1);

						dispose();
						MensajesVentanas.mensajeError(e1.getMensaje());
					} catch (UsuarioExcepcion e1) {
						logger.error("mouseClicked(MouseEvent)", e1);

						dispose();
						MensajesVentanas.mensajeError(e1.getMensaje());
					} catch (AutorizacionExcepcion e1) {
						logger.error("mouseClicked(MouseEvent)", e1);

						dispose();
						MensajesVentanas.mensajeError(e1.getMensaje());
					} catch (ProductoExcepcion e1) {
						logger.error("keyPressed(KeyEvent) "+ e1.getMensaje());

						dispose();
						MensajesVentanas.mensajeError("Código no registrado");
					} catch (ExcepcionCr e1) {
						logger.error("mouseClicked(MouseEvent)", e1);

						dispose();
						MensajesVentanas.mensajeError("Código no registrado");
					} catch (ConexionExcepcion e1) {
						logger.error("mouseClicked(MouseEvent)", e1);

						dispose();
						MensajesVentanas.mensajeError(e1.getMensaje());
					}
					break;
				case 4: // Ejecutamos un ingreso de producto para Apartado
					try {
						ArrayList <Object>valorLeido = Control.codigoValido(getJTextField1().getText());
						int tipoCodigo = ((Integer)valorLeido.get(0)).intValue();
						String codigo = (String)valorLeido.get(1);
						switch (tipoCodigo){
							case Control.PRODUCTO:
								CR.meServ.ingresarProductoApartado(codigo, Sesion.capturaTeclado);
								break;
							case Control.CLIENTE:
								Cliente ctemp = CR.meServ.buscarClienteTemporal(codigo); //Buscar el cliente temporal
								String id = getJTextField1().getText();
								dispose();
								registroCliente = null;			
								if(ctemp != null) { //Si lo encuentra, cargar ventana de registro para verificar datos
									registroCliente = new RegistroClientesNuevos(ctemp);
									MensajesVentanas.centrarVentanaDialogo(registroCliente);
								} else if(MensajesVentanas.preguntarSiNo("Cliente no encontrado.\n¿Desea registrar los datos del cliente?") == 0)
								{								
									registroCliente = new RegistroClientesNuevos(id);
									MensajesVentanas.centrarVentanaDialogo(registroCliente);
								}
									
								break;
							case Control.COLABORADOR:
								CR.meServ.asignarCliente(codigo);
								break;
							case Control.CODIGO_DESCONOCIDO: 
								try {
									CR.meServ.ingresarProductoApartado(codigo, Sesion.capturaTeclado);
								} catch (ExcepcionCr ex2) {
									logger.error("keyPressed(KeyEvent)",
											ex2);
									ctemp = CR.meServ.buscarClienteTemporal(codigo);
									id = getJTextField1().getText();
									dispose();
									registroCliente = null;			
									if(ctemp != null) {
										registroCliente = new RegistroClientesNuevos(ctemp);
										MensajesVentanas.centrarVentanaDialogo(registroCliente);
									} else if(MensajesVentanas.preguntarSiNo("Cliente no encontrado.\n¿Desea registrar los datos del cliente?") == 0)
									{								
										registroCliente = new RegistroClientesNuevos(id);
										MensajesVentanas.centrarVentanaDialogo(registroCliente);
									}
								}
								break;
						}
						dispose();
					} catch (AfiliadoUsrExcepcion e1) {
						logger.error("keyPressed(KeyEvent)", e1);
	
						dispose();
						MensajesVentanas.mensajeError(e1.getMensaje());
					} catch (UsuarioExcepcion e1) {
						logger.error("keyPressed(KeyEvent)", e1);
	
						dispose();
						MensajesVentanas.mensajeError(e1.getMensaje());
					} catch (AutorizacionExcepcion e1) {
						logger.error("keyPressed(KeyEvent)", e1);
	
						dispose();
						MensajesVentanas.mensajeError(e1.getMensaje());
					} catch (ExcepcionCr e1) {
						logger.error("keyPressed(KeyEvent)", e1);
	
						dispose();
						MensajesVentanas.mensajeError("Código no registrado");
					} catch (ConexionExcepcion e1) {
						logger.error("keyPressed(KeyEvent)", e1);
	
						dispose();
						MensajesVentanas.mensajeError(e1.getMensaje());
					}
					break;
				case 5: // Ejecutamos un ingreso de cliente para devolucion
						try {
							ArrayList <Object>valorLeido = Control.codigoValido(getJTextField1().getText());
							String codigo = (String)valorLeido.get(1);
							CR.meVenta.asignarClienteDevolucion(codigo);
							dispose();
						} catch (ExcepcionCr e1) {
							logger.error("mouseClicked(MouseEvent)", e1);

							dispose();
							MensajesVentanas.mensajeError("Código no registrado");
						} catch (ConexionExcepcion e1) {
							logger.error("mouseClicked(MouseEvent)", e1);

							dispose();
							MensajesVentanas.mensajeError(e1.getMensaje());
						}
						break;
				case 6: // Ejecutamos una asignacion de clientes para la venta
					try {
						ArrayList <Object>valorLeido = Control.codigoValido(getJTextField1().getText());
						int tipoCodigo = ((Integer)valorLeido.get(0)).intValue();
						String codigo = (String)valorLeido.get(1);
						switch (tipoCodigo){
							case Control.PRODUCTO:
								MensajesVentanas.mensajeError("Código no registrado");
								break;
							case Control.CLIENTE:
								CR.meVenta.asignarCliente(codigo);
								break;
							case Control.COLABORADOR:
								CR.meVenta.asignarCliente(codigo);
								break;
							case Control.CODIGO_DESCONOCIDO: 
								CR.meVenta.asignarCliente(codigo);
								break;
						}
						dispose();					
					} catch (AfiliadoUsrExcepcion e1) {
						logger.error("mouseClicked(MouseEvent)", e1);

						dispose();
						MensajesVentanas.mensajeError(e1.getMensaje());
					} catch (UsuarioExcepcion e1) {
						logger.error("mouseClicked(MouseEvent)", e1);

						dispose();
						MensajesVentanas.mensajeError(e1.getMensaje());
					} catch (AutorizacionExcepcion e1) {
						logger.error("mouseClicked(MouseEvent)", e1);

						dispose();
						MensajesVentanas.mensajeError(e1.getMensaje());
					} catch (ExcepcionCr e1) {
						logger.error("mouseClicked(MouseEvent)", e1);

						dispose();
						MensajesVentanas.mensajeError("Código no registrado");
					} catch (ConexionExcepcion e1) {
						logger.error("mouseClicked(MouseEvent)", e1);

						dispose();
						MensajesVentanas.mensajeError(e1.getMensaje());
					}
					break;
				case 7: //Cliente nuevo en apartados
					
				try {
						ArrayList <Object>valorLeido = Control.codigoValido(getJTextField1().getText());
						String codigo = (String)valorLeido.get(1);
			
						Cliente ctemp = CR.meServ.buscarClienteTemporal(codigo);
						String id = getJTextField1().getText();
						dispose();
						registroCliente = null;			
						if(ctemp != null) {
							registroCliente = new RegistroClientesNuevos(ctemp);
							MensajesVentanas.centrarVentanaDialogo(registroCliente);
						} else if(MensajesVentanas.preguntarSiNo("Cliente no encontrado.\n¿Desea registrar los datos del cliente?") == 0)
						{								
							registroCliente = new RegistroClientesNuevos(id);
							MensajesVentanas.centrarVentanaDialogo(registroCliente);
						}
							
					} catch (AfiliadoUsrExcepcion e1) {
						logger.error("keyPressed(KeyEvent)", e1);
						dispose();
						MensajesVentanas.mensajeError(e1.getMensaje());
					} catch (UsuarioExcepcion e1) {
						logger.error("keyPressed(KeyEvent)", e1);
						dispose();
						MensajesVentanas.mensajeError(e1.getMensaje());
					} catch (AutorizacionExcepcion e1) {
						logger.error("keyPressed(KeyEvent)", e1);
						dispose();
						MensajesVentanas.mensajeError(e1.getMensaje());
					} catch (ExcepcionCr e1) {
						logger.error("keyPressed(KeyEvent)", e1);
						dispose();
						MensajesVentanas.mensajeError("Código no registrado");
					} catch (ConexionExcepcion e1) {
						logger.error("keyPressed(KeyEvent)", e1);
						dispose();
						MensajesVentanas.mensajeError(e1.getMensaje());
					}
					break;
				case 8: // Queremos ejecutar una devolucion de Producto
					try {
						CR.meVenta.devolverProductoXCambio(this.renglonCambiar,Sesion.capturaTeclado,new Float(CR.me.formatoNumerico(getJTextField1().getText())).floatValue());
						dispose();
					}catch(ExcepcionCr f){
						logger.error("keyPressed(KeyEvent)", f);

						MensajesVentanas.mensajeError(f.getMensaje());
					}catch(Exception f){
						logger.error("keyPressed(KeyEvent)", f);

						MensajesVentanas.mensajeError("Cantidad Invalida");
					}
				break;
				case 9: // Queremos ejecutar una anulacion de producto
					try {
						CR.meVenta.anularProductoXCambio(this.renglonCambiar,new Float(CR.me.formatoNumerico(getJTextField1().getText())).floatValue());
						dispose();
					}catch(ExcepcionCr f){
						logger.error("keyPressed(KeyEvent)", f);

						MensajesVentanas.mensajeError(f.getMensaje());
					}catch(Exception f){
						logger.error("keyPressed(KeyEvent)", f);

						MensajesVentanas.mensajeError("Cantidad Invalida");
					}
				break;				
				case 10: // Ejecutamos una asignacion de clientes para la venta en DevXCambio
					try {
						ArrayList <Object>valorLeido = Control.codigoValido(getJTextField1().getText());
						int tipoCodigo = ((Integer)valorLeido.get(0)).intValue();
						String codigo = (String)valorLeido.get(1);
						switch (tipoCodigo){
							case Control.PRODUCTO:
								MensajesVentanas.mensajeError("Código no registrado");
								break;
							case Control.CLIENTE:
								CR.meVenta.asignarCliente(codigo, false);
								break;
							case Control.COLABORADOR:
								CR.meVenta.asignarCliente(codigo, false);
								break;
							case Control.CODIGO_DESCONOCIDO: 
								CR.meVenta.asignarCliente(codigo, false);
								break;
						}
						dispose();					
					} catch (AfiliadoUsrExcepcion e1) {
						logger.error("keyPressed(KeyEvent)", e1);

						dispose();
						MensajesVentanas.mensajeError(e1.getMensaje());
					} catch (UsuarioExcepcion e1) {
						logger.error("keyPressed(KeyEvent)", e1);

						dispose();
						MensajesVentanas.mensajeError(e1.getMensaje());
					} catch (AutorizacionExcepcion e1) {
						logger.error("keyPressed(KeyEvent)", e1);

						dispose();
						MensajesVentanas.mensajeError(e1.getMensaje());
					} catch (ExcepcionCr e1) {
						logger.error("keyPressed(KeyEvent)", e1);

						dispose();
						MensajesVentanas.mensajeError("Código no registrado");
					} catch (ConexionExcepcion e1) {
						logger.error("keyPressed(KeyEvent)", e1);

						dispose();
						MensajesVentanas.mensajeError(e1.getMensaje());
					}
				break;
				case 11: // Ejecutamos un ingreso de producto para Lista de Regalos
					try {
						ArrayList <Object>valorLeido = Control.codigoValido(getJTextField1().getText());
						int tipoCodigo = ((Integer)valorLeido.get(0)).intValue();
						String codigo = (String)valorLeido.get(1);
						switch (tipoCodigo){
							case Control.PRODUCTO:
								CR.meServ.ingresarProductoLR(codigo, Sesion.capturaTeclado);
								break;
							case Control.CLIENTE:
								MensajesVentanas.mensajeError("Código no registrado");
								break;
							case Control.COLABORADOR:
								MensajesVentanas.mensajeError("Código no registrado");
								break;
							case Control.CODIGO_DESCONOCIDO: 
								try {
									CR.meServ.ingresarProductoLR(codigo, Sesion.capturaTeclado);
								} catch (ExcepcionCr ex2) {
									logger.error("keyPressed(KeyEvent)",ex2);
									MensajesVentanas.mensajeError("Código no registrado");
								}
								break;
						}
						dispose();
					} catch (AfiliadoUsrExcepcion e1) {
						logger.error("keyPressed(KeyEvent)", e1);

						dispose();
						MensajesVentanas.mensajeError(e1.getMensaje());
					} catch (UsuarioExcepcion e1) {
						logger.error("keyPressed(KeyEvent)", e1);

						dispose();
						MensajesVentanas.mensajeError(e1.getMensaje());
					} catch (AutorizacionExcepcion e1) {
						logger.error("keyPressed(KeyEvent)", e1);

						dispose();
						MensajesVentanas.mensajeError(e1.getMensaje());
					} catch (ExcepcionCr e1) {
						logger.error("keyPressed(KeyEvent)", e1);

						dispose();
						MensajesVentanas.mensajeError("Código no registrado");
					} catch (ConexionExcepcion e1) {
						logger.error("keyPressed(KeyEvent)", e1);

						dispose();
						MensajesVentanas.mensajeError(e1.getMensaje());
					}
				break;
				case 12: // Ejecutamos un ingreso de producto para venta de Lista de Regalos
					try {
						ArrayList <Object>valorLeido = Control.codigoValido(getJTextField1().getText());
						int tipoCodigo = ((Integer)valorLeido.get(0)).intValue();
						String codigo = (String)valorLeido.get(1);
						boolean pertenece = CR.meServ.getListaRegalos().perteneceProducto(codigo, Sesion.capturaTeclado);
						switch (tipoCodigo){
							case Control.PRODUCTO:
								if(pertenece)
									CR.meVenta.ingresarLineaProductoVentaLR(codigo, Sesion.capturaTeclado,0);
								else
									MensajesVentanas.mensajeError("El producto no pertenece a la Lista o\nIngresó una cantidad mayor a la cantidad restante");
								break;
							case Control.CLIENTE:
								MensajesVentanas.mensajeError("Código no registrado");
								break;
							case Control.COLABORADOR:
								MensajesVentanas.mensajeError("Código no registrado");
								break;
							case Control.CODIGO_DESCONOCIDO:
								if(pertenece)
									CR.meVenta.ingresarLineaProductoVentaLR(codigo, Sesion.capturaTeclado,0);
								else
									MensajesVentanas.mensajeError("El producto no pertenece a la Lista o\nIngresó una cantidad mayor a la cantidad restante");
								break;
						}
						dispose();
					} catch (AfiliadoUsrExcepcion e1) {
						logger.error("keyPressed(KeyEvent)", e1);
		
						dispose();
						MensajesVentanas.mensajeError(e1.getMensaje());
					} catch (UsuarioExcepcion e1) {
						logger.error("keyPressed(KeyEvent)", e1);
		
						dispose();
						MensajesVentanas.mensajeError(e1.getMensaje());
					} catch (AutorizacionExcepcion e1) {
						logger.error("keyPressed(KeyEvent)", e1);
		
						dispose();
						MensajesVentanas.mensajeError(e1.getMensaje());
					} catch (ExcepcionCr e1) {
						logger.error("keyPressed(KeyEvent)", e1);
		
						dispose();
						MensajesVentanas.mensajeError("Código no registrado");
					} catch (ConexionExcepcion e1) {
						logger.error("keyPressed(KeyEvent)", e1);
		
						dispose();
						MensajesVentanas.mensajeError(e1.getMensaje());
					}
				break;
				case 13: // Ejecutamos un ingreso de cliente para venta de Lista de Regalos
					try {
						ArrayList <Object>valorLeido = Control.codigoValido(getJTextField1().getText());
						int tipoCodigo = ((Integer)valorLeido.get(0)).intValue();
						String codigo = (String)valorLeido.get(1);
						switch (tipoCodigo){
							case Control.PRODUCTO:
								MensajesVentanas.mensajeError("Código no registrado");
								break;
							case Control.CLIENTE:
								CR.meServ.asignarClienteLR(codigo);
								break;
							case Control.COLABORADOR:
								CR.meServ.asignarClienteLR(codigo);
								break;
							case Control.CODIGO_DESCONOCIDO:
								try{
									CR.meServ.asignarClienteLR(codigo);
								}catch(ExcepcionCr e2){
									MensajesVentanas.mensajeError("Código no registrado");
								}
							break;
						}
						dispose();
					} catch (AfiliadoUsrExcepcion e1) {
						logger.error("keyPressed(KeyEvent)", e1);
		
						dispose();
						MensajesVentanas.mensajeError(e1.getMensaje());
					} catch (UsuarioExcepcion e1) {
						logger.error("keyPressed(KeyEvent)", e1);
		
						dispose();
						MensajesVentanas.mensajeError(e1.getMensaje());
					} catch (AutorizacionExcepcion e1) {
						logger.error("keyPressed(KeyEvent)", e1);
		
						dispose();
						MensajesVentanas.mensajeError(e1.getMensaje());
					} catch (ExcepcionCr e1) {
						logger.error("keyPressed(KeyEvent)", e1);
		
						dispose();
						MensajesVentanas.mensajeError("Código no registrado");
					} catch (ConexionExcepcion e1) {
						logger.error("keyPressed(KeyEvent)", e1);
		
						dispose();
						MensajesVentanas.mensajeError(e1.getMensaje());
					}
					break;
				case 14: // Ejecutamos un ingreso de titular de Lista de Regalos
					try {
						ArrayList <Object>valorLeido = Control.codigoValido(getJTextField1().getText());
						int tipoCodigo = ((Integer)valorLeido.get(0)).intValue();
						String codigo = (String)valorLeido.get(1);
						switch (tipoCodigo){
							case Control.PRODUCTO:
								MensajesVentanas.mensajeError("Código no registrado");
								break;
							case Control.CLIENTE:
								CR.meServ.asignarTitularLR(codigo);
								break;
							case Control.COLABORADOR:
								CR.meServ.asignarTitularLR(codigo);
								break;
							case Control.CODIGO_DESCONOCIDO:
								try{
									CR.meServ.asignarTitularLR(codigo);
								}catch(ExcepcionCr e2){
									MensajesVentanas.mensajeError("Código no registrado");
								}
							break;
						}
						dispose();
					} catch (AfiliadoUsrExcepcion e1) {
						logger.error("keyPressed(KeyEvent)", e1);
		
						dispose();
						MensajesVentanas.mensajeError(e1.getMensaje());
					} catch (UsuarioExcepcion e1) {
						logger.error("keyPressed(KeyEvent)", e1);
		
						dispose();
						MensajesVentanas.mensajeError(e1.getMensaje());
					} catch (AutorizacionExcepcion e1) {
						logger.error("keyPressed(KeyEvent)", e1);
		
						dispose();
						MensajesVentanas.mensajeError(e1.getMensaje());
					} catch (ExcepcionCr e1) {
						logger.error("keyPressed(KeyEvent)", e1);
		
						dispose();
						MensajesVentanas.mensajeError("Código no registrado");
					} catch (ConexionExcepcion e1) {
						logger.error("keyPressed(KeyEvent)", e1);
		
						dispose();
						MensajesVentanas.mensajeError(e1.getMensaje());
					}
					break;
				case 15: // Ejecutamos un ingreso de Vendedor Corporativo para BR Electrónico
					try {
						ArrayList <Object>valorLeido = Control.codigoValido(getJTextField1().getText());
						String codigo = (String)valorLeido.get(1);
						CR.meServ.asignarVendedorCorporativoBR(codigo);
						dispose();
					} catch (ExcepcionCr e1) {
						logger.error("keyPressed(KeyEvent)", e1);
						MensajesVentanas.mensajeError("Código no registrado");
					} catch (ConexionExcepcion e1) {
						logger.error("keyPressed(KeyEvent)", e1);
						MensajesVentanas.mensajeError(e1.getMensaje());
					}
					break;
			}
			this.ejecutado = true;
		}

		if (logger.isDebugEnabled())
			logger.debug("mouseClicked(MouseEvent) - end");
	}

	public boolean isEjecutado() {
		return ejecutado;
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 */
	public void mouseEntered(MouseEvent e) {
		if (logger.isDebugEnabled())
			logger.debug("mouseEntered(MouseEvent) - start");
	
		if (logger.isDebugEnabled())
			logger.debug("mouseEntered(MouseEvent) - end");
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	 */
	public void mouseExited(MouseEvent e) {
		if (logger.isDebugEnabled())
			logger.debug("mouseExited(MouseEvent) - start");
		
		if (logger.isDebugEnabled())
			logger.debug("mouseExited(MouseEvent) - end");
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	public void mousePressed(MouseEvent e) {
		if (logger.isDebugEnabled())
			logger.debug("mousePressed(MouseEvent) - start");
		
		if (logger.isDebugEnabled())
			logger.debug("mousePressed(MouseEvent) - end");
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 */
	public void mouseClicked(MouseEvent e) {
		if (logger.isDebugEnabled())
			logger.debug("mouseReleased(MouseEvent) - start");
		
		if (logger.isDebugEnabled())
			logger.debug("mouseReleased(MouseEvent) - end");
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
			layFlowLayout11.setVgap(3);
			jPanel2.setLayout(layFlowLayout11);
			jPanel2.add(getJButton1(), null);
			jPanel2.add(getJButton(), null);
			jPanel2.setPreferredSize(new java.awt.Dimension(410,35));
			jPanel2.setBackground(new java.awt.Color(226,226,222));
		}

		if (logger.isDebugEnabled())
			logger.debug("getJPanel2() - end");
		return jPanel2;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private javax.swing.JButton getJButton() {
		if (logger.isDebugEnabled())
			logger.debug("getJButton() - start");

		if(jButton == null) {
			jButton = new JHighlightButton();
			jButton.setText("Cancelar");
			jButton.setBackground(new java.awt.Color(226,226,222));
			jButton.setIcon(new ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix16x16/delete2.png")));
		}

		if (logger.isDebugEnabled())
			logger.debug("getJButton() - end");
		return jButton;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private javax.swing.JButton getJButton1() {
		if (logger.isDebugEnabled())
			logger.debug("getJButton1() - start");

		if(jButton1 == null) {
			jButton1 = new JHighlightButton();
			jButton1.setText("Aceptar");
			jButton1.setBackground(new java.awt.Color(226,226,222));
			jButton1.setIcon(new ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix16x16/check2.png")));
		}

		if (logger.isDebugEnabled())
			logger.debug("getJButton1() - end");
		return jButton1;
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
			jPanel3.add(getJPanel5(), null);
			jPanel3.setPreferredSize(new java.awt.Dimension(410,112));
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
			layFlowLayout2.setVgap(5);
			layFlowLayout2.setHgap(5);
			jPanel4.setLayout(layFlowLayout2);
			jPanel4.add(getJLabel1(), null);
			jPanel4.setPreferredSize(new java.awt.Dimension(410,40));
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
			jLabel1.setText("");
			jLabel1.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 18));
			jLabel1.setForeground(java.awt.Color.white);
			jLabel1.setIcon(new ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix32x32/help2.png")));
		}

		if (logger.isDebugEnabled())
			logger.debug("getJLabel1() - end");
		return jLabel1;
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
			java.awt.FlowLayout layFlowLayout13 = new java.awt.FlowLayout();
			layFlowLayout13.setHgap(1);
			layFlowLayout13.setVgap(1);
			layFlowLayout13.setAlignment(java.awt.FlowLayout.LEFT);
			jPanel1.setLayout(layFlowLayout13);
			jPanel1.add(getJTextField1(), null);
			jPanel1.setPreferredSize(new java.awt.Dimension(395,53));
			jPanel1.setBackground(new java.awt.Color(242,242,238));
			
			/*if ((caso!=3)&&(caso!=4)&&(caso!=6))*/
			if (caso<3)
				jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Cantidad", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12), java.awt.Color.black));
			else
				jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Código", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12), java.awt.Color.black));
		}

		if (logger.isDebugEnabled())
			logger.debug("getJPanel1() - end");
		return jPanel1;
	}
	/**
	 * This method initializes jTextField1
	 * 
	 * @return javax.swing.JTextField
	 */
	public javax.swing.JTextField getJTextField1() {
		if (logger.isDebugEnabled())
			logger.debug("getJTextField1() - start");

		if(jTextField1 == null) {
			jTextField1 = new UpperCaseField();
			jTextField1.setPreferredSize(new java.awt.Dimension(200,20));
			jTextField1.setBackground(java.awt.Color.white);
		}

		if (logger.isDebugEnabled())
			logger.debug("getJTextField1() - end");
		return jTextField1;
	}
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
			layFlowLayout21.setHgap(5);
			layFlowLayout21.setVgap(5);
			layFlowLayout21.setAlignment(java.awt.FlowLayout.CENTER);
			jPanel5.setLayout(layFlowLayout21);
			jPanel5.add(getJPanel1(), null);
			jPanel5.setPreferredSize(new java.awt.Dimension(395,60));
			jPanel5.setBackground(new java.awt.Color(242,242,238));
		}

		if (logger.isDebugEnabled())
			logger.debug("getJPanel5() - end");
		return jPanel5;
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
