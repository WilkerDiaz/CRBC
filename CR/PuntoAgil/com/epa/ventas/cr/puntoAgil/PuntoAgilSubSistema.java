/*
 * $Id: PuntoAgilSubSistema.java,v 1.27 2007/07/26 18:57:12 programa1 Exp $
 * ===========================================================================
 * Proyecto : PuntoAgil Paquete : com.epa.ventas.cr.puntoAgil Programa :
 * PuntoAgilSubSistema.java Creado por : programa4 Creado en : 16/05/2006
 * 02:14:52 PM (C) Copyright 2006 Todos los Derechos Reservados
 * ---------------------------------------------------------------------------
 * Información de Repositorio:
 * ---------------------------------------------------------------------------
 * Rama de Desarrollo : $Name:  $ Bloqueado por : $Locker:  $ Estado de Revisión :
 * $State: Exp $
 * ---------------------------------------------------------------------------
 * Actualizaciones:
 * ---------------------------------------------------------------------------
 * $Log: PuntoAgilSubSistema.java,v $
 * Revision 1.27  2007/07/26 18:57:12  programa1
 * arreglos para imprimir voucher en secuencia 0 y ventana de tarjeta de credito EPA
 *
 * Revision 1.26  2007/05/14 15:22:56  programa8
 * Version Estable EPA3 2007-05-14
 *
 * Revision 1.25  2007/05/14 13:48:36  programa8
 * Version En EPA3 Prueba Piloto 2007-05-14
 *
 * Revision 1.24  2007/05/07 15:36:47  programa8
 * Version estable Punto Agil 2007-05-07
 *
 * Revision 1.23  2007/04/25 18:46:04  programa8
 * Version Estable Pto Agil, controlando cargos dos dobles , una sola instancia a datosExternos , control fuera de linea
 *
 * Revision 1.22  2006/09/28 19:08:28  programa4
 * Actualizacion por requerimientos credicard
 * Se cargan datos de programas de lealtad segun banco seleccionado
 * Modificados mensajes de error para hacerlos mas legibles
 * Verificados titulos para indicar version del punto agil
 *
 * Revision 1.21  2006/09/15 21:34:44  programa4
 * Correcciones solicitadas por credicard
 * Ajuestes solicitados sobre mensajes de respuesta al cajero
 *
 * Revision 1.20  2006/09/12 14:49:48  programa4
 * Agregada validacion de que toda linea mayor a 40 caracteres fuera truncada
 *
 * Revision 1.19  2006/09/07 22:55:26  programa4
 * Creadas constantes con nombre de modulos y metodos
 *
 * Revision 1.18  2006/09/06 23:02:05  programa4
 * Corregido bug de recursividad cuando carga archivos
 * Corregida cancelacion de provimillas
 *
 * Revision 1.17  2006/09/05 22:49:25  programa4
 * Agregado msg final para diferenciar precierre de reporte de cierre
 *
 * Revision 1.16  2006/09/04 18:59:10  programa4
 * Agregado comparacion con numSeqMerchant en la obtencion de numSeq.
 *
 * De esta forma se puede asignar en la tabla caja el proximo numSeqMerchant a utilizar
 *
 * Revision 1.15  2006/08/25 18:17:38  programa4
 * Correccion en anulacion de cheque (para que no solicite lectura banda)
 * Y convertido PuntoAgilSubSistema en singleton en lugar de metodos estaticos
 *
 * Revision 1.14  2006/07/21 18:44:16  programa4
 * Corregido bug que al haber excepcion en el guardado de base de datos no imprimia recibo
 * Movido textos a constantes
 * revisado toString de DatosOperacionPuntoAgil
 * formateo de codigo
 * Revision 1.13 2006/07/18 12:58:39
 * programa4 Corregido mensaje cuando se trata de tarjeta de debito para
 * solicitar clave Revision 1.12 2006/07/17 19:26:46 programa4 Corregido bug en
 * comparacion de fecha ultimo cierre con fecha actual Revision 1.11 2006/07/13
 * 16:07:28 programa4 Correcion de mensaje de "Punto Agil" a "El Punto Agil"
 * Manejo de autorizacion si no se utiliza punto agil No se solicita
 * autorizacion si hubo error VA (Falla de comunicacion con merchant), o se
 * trata de cheque Asignados mensajes a mostrar mientras se comunica con pinpad
 * Revision 1.10 2006/07/06 20:58:12 programa4 Eliminado mensaje cuando
 * operacion no esta relacionada a pago (como consultas de puntos y anulaciones)
 * Revision 1.9 2006/07/05 15:25:40 programa4 Agregado soporte a provimillas y
 * actualizado anulacion Revision 1.8 2006/06/27 15:51:57 programa4
 * Refactorizadas consultas Agregados metodos sincronizacion Modificado manejo
 * de excepciones Revision 1.7 2006/06/26 16:06:49 programa4 Corregido bug de
 * obtencion de voucherCliente y refactorizacion por renombrado de campo en
 * Datosoperacionpuntoagil Revision 1.6 2006/06/16 21:32:39 programa4 Agregada
 * anulacion de pagos. Actualizacion de datos con datos de los pagos de ventas,
 * tarjeta de cred. epa y abonos. Agregado aviso de cierre si han pasado mas de
 * 24 horas del ultimo cierre. Agregado numServicio para poder referir a abonos
 * Agregadas consultas de puntos Revision 1.5 2006/06/10 02:14:20 programa4
 * Agregados metodos para procesar transacciones, registrandolas en la base de
 * datos y basandose en la data de los maestros para decidir si imprime.
 * Adicoonalmente se agrega metodo para lectura de banda. Se asigna valor al
 * vPos y datos del cajero a la operacion para cumplir con el programa juntos
 * Revision 1.4 2006/05/25 20:40:44 programa4 Primeros pasos para procesamiento
 * de cheques por Punto Agil Revision 1.3 2006/05/18 20:26:57 programa4 Agregada
 * autorizacion a MenuPuntoAgil y habilitada reimpresion de voucher Revision 1.2
 * 2006/05/18 18:26:07 programa4 Agregado constructores ant para automatizar
 * compilacion Funcionan - Consulta Ultimo Voucher (con reimpresion sin
 * anulacion). - Pre cierre - Cierre Revision 1.1 2006/05/17 20:00:23 programa4
 * Version Inicial con Menu, ConsultaUltimoVoucher y SubSistema
 * ===========================================================================
 */

package com.epa.ventas.cr.puntoAgil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.SystemUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;

import ve.com.megasoft.VPos;
import ve.com.megasoft.bean.Cajero;
import ve.com.megasoft.bean.Mensaje;
import ve.com.megasoft.error.VPosException;
import ve.com.megasoft.universal.VPosUniversal;
import ve.com.megasoft.universal.error.VposUniversalException;

import com.becoblohm.cr.excepciones.BaseDeDatosExcepcion;
import com.becoblohm.cr.excepciones.BonoRegaloException;
import com.becoblohm.cr.excepciones.ConexionExcepcion;
import com.becoblohm.cr.excepciones.MaquinaDeEstadoExcepcion;
import com.becoblohm.cr.excepciones.PagoExcepcion;
import com.becoblohm.cr.extensiones.ManejoPagosFactory;
import com.becoblohm.cr.extensiones.impl.manejarpago.FormaDePago;
import com.becoblohm.cr.extensiones.impl.manejarpago.Pago;
import com.becoblohm.cr.extensiones.impl.manejarpago.PantallaDatosAdicionales;
import com.becoblohm.cr.gui.VentanaEspera;
import com.becoblohm.cr.manejadorauditoria.Auditoria;
import com.becoblohm.cr.manejadorsesion.MaquinaDeEstado;
import com.becoblohm.cr.manejadorsesion.MaquinaDeEstadoVenta;
import com.becoblohm.cr.manejadorsesion.Sesion;
import com.becoblohm.cr.manejarusuario.Colaborador;
import com.becoblohm.cr.manejarusuario.Usuario;
import com.becoblohm.cr.mediadoresbd.BaseDeDatosServicio;
import com.becoblohm.cr.mediadoresbd.BaseDeDatosVenta;
import com.becoblohm.cr.mediadoresbd.MediadorBD;
import com.becoblohm.cr.extensiones.impl.reportes.Reporte;
import com.becoblohm.cr.utiles.Control;
import com.becoblohm.cr.utiles.MensajesVentanas;
import com.epa.crprinterdriver.CRFiscalPrinterOperations;
import com.epa.crprinterdriver.PrinterNotConnectedException;
//import com.epa.sistemas.ventas.caja.TarjetaCreditoEPA.BuscarPagoAnuladoDAO;
import com.epa.ventas.cr.puntoAgil.manejarpago.ManejoPagosPuntoAgil;
import com.epa.ventas.cr.puntoAgil.manejarpago.PantallaDatosAdicionalesPuntoAgil;

/**
 * Clase PuntoAgilSubSistema
 * 
 * @author programa4 - $Author: programa1 $
 * @version $Revision: 1.27 $ - $Date: 2007/07/26 18:57:12 $
 * @since 16/05/2006
 */
public class PuntoAgilSubSistema {

	private static final String METODO_OPERACIONPTOAGIL = "OPERACIONPTOAGIL";

	private static final String MODULO_PUNTO_AGIL = "PuntoAgil";
	
	private static final String CARPETA_CIERRES = "cierres/";

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(PuntoAgilSubSistema.class);

	private VPos vPos = initVPos();
	
	/**
	 * Agregado por migración a VPosUniversal
	 */
	//VPosUniversal vposUniversal = initVPosUniversal();

	private static PuntoAgilSubSistema instance;

	//private int llamadasDatosExternos = 0;

	private PuntoAgilSubSistema() {
		// CONSTRUCTOR PRIVADO PARA QUE SOLO SE PUEDA USAR SINGLETON.
	}
	private static boolean sw = false;
	

	/**
	 * Instancia singleton del objeto.
	 * 
	 * @return PuntoAgilSubSistema singleton
	 */
	public synchronized static PuntoAgilSubSistema getInstance() {
		if (instance == null) {
			instance = new PuntoAgilSubSistema();
		}
		return instance;
	}

	/**
	 * @return Returns el vPos.
	 */
	private VPos initVPos() {
		try {
			VPos newVPos = new VPos(Constantes.RUTA_CONF_VPOS);
			// ASIGNAR VALOR DE VTID
			/*String tienda = StringUtils.leftPad(String.valueOf(Sesion
					.getTienda().getNumero()), 3, '0');
			String caja = StringUtils.leftPad(String.valueOf(Sesion.getCaja()
					.getNumero()), 3, '0');
			String vtId = "B" + tienda + "C" + caja;
			try {
				int newNumSeq = maxNumeroSecuencia(newVPos, vtId);
				newVPos.getSistConfiguracion().setSeqnum(newNumSeq);
				Sesion.getCaja().setNumSeqMerchant(newNumSeq);
				Sesion.getCaja().actualizarNumSeqMerchant();
				verificarRequiereCierre();
			} catch (BaseDeDatosExcepcion e) {
				throw new VPosException(e);
			} catch (ConexionExcepcion e) {
				throw new VPosException(e);
			}
			//newVPos.getSistConfiguracion().setTienda("BECO " + tienda);
			//newVPos.getSistConfiguracion().setTienda("BECO " + Sesion.getTienda().getNombreSucursal().trim());
			newVPos.getSistConfiguracion().setId(tienda + caja);
			newVPos.getSistConfiguracion().setVtId(vtId);
			logger.info("Inicializado vPos para caja: " + vtId);*/
			return newVPos;
		} catch (VPosException e) {
			String mensaje = "ERROR INICIALIZANDO EL PUNTO AGIL";
			mostrarMensajeErrorException(e, mensaje);
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Agregado por migración a VPosUniversal
	 * @return Returns el VPosUniversal
	 */
	@SuppressWarnings("unused")
	private VPosUniversal initVPosUniversal() {
		VPosUniversal newVPos = null;
		try {
			newVPos = new VPosUniversal(Constantes.RUTA_CONF_VPOS);
		} catch (VposUniversalException e) {
			e.printStackTrace();
		}
		return newVPos;
			
	}

	/**
	 * Verifica si vPos esta inicializado y lo retorna.
	 * 
	 * @return VPos
	 */
	VPos getVPos() {
		if (this.vPos == null) {
			this.vPos = initVPos();
		}
		return this.vPos;
	}
	
	/**
	 * Verifica si vPosUniversal esta inicializado y lo retorna.
	 * 
	 * @return VPos
	 */
	/*VPosUniversal getVPosUniversal() {
		if (this.vposUniversal == null) {
			this.vposUniversal = initVPosUniversal();
		}
		return this.vposUniversal;
	}*/

	/**
	 * <p>
	 * Buscar el mayor numero de secuencia entre el archivo de configuracion, y
	 * las tablas de operaciones.
	 * </p>
	 * 
	 * <p>
	 * Solo se tomara en cuenta el archivo de configuracion si coincide con el
	 * vtId dado.
	 * </p>
	 * 
	 * @param _newVPos
	 * @param vtId
	 * @return newNumSeq
	 * @throws BaseDeDatosExcepcion
	 */
	@SuppressWarnings("unused")
	private int maxNumeroSecuencia(VPos _newVPos, String vtId)
			throws BaseDeDatosExcepcion {
		ManejoPagosPuntoAgil mp = (ManejoPagosPuntoAgil) ManejoPagosFactory
				.getInstance();
		int oldNumSeqVPosConf = (_newVPos.getSistConfiguracion().getVtId()
				.equals(vtId)) ? _newVPos.getSistConfiguracion().getSeqnum()
				: 0;
		int oldNumSeqCR_Caja = Sesion.getCaja().getNumSeqMerchant();
		int maxNumSeqTablasOperaciones = mp.obtenerMaximoNumSeq(vtId);

		int newNumSeq = Math.max(maxNumSeqTablasOperaciones, oldNumSeqVPosConf);
		newNumSeq = Math.max(newNumSeq, oldNumSeqCR_Caja);
		if (newNumSeq == 0) {
			// CONSULTAR CON SERVIDOR MERCHANT
			if (newNumSeq == 0) {
				newNumSeq = 1;
			}
		}
		return newNumSeq;
	}

	/**
	 * Levanta el menu inicial del SubSistema de Punto Ágil
	 */
	public void mostrarVentanaInicial() {
		try {
			 MensajesVentanas.detenerVerificadorFoco();
			ManejoPagosPuntoAgil manejoPagosPuntoAgil = (ManejoPagosPuntoAgil) ManejoPagosFactory
					.getInstance();
			Integer tipoProceso = manejoPagosPuntoAgil
					.obtenerTipoProcesoSegunEstadoCaja(Sesion.getCaja()
							.getEstado());
			if (tipoProceso == null) {
				String codautorizante = MaquinaDeEstado.autorizarFuncion(
						MODULO_PUNTO_AGIL, METODO_OPERACIONPTOAGIL);
				if (codautorizante != null) {
					
					WindowsMenuPuntoAgil windowsMenuPuntoAgil = new WindowsMenuPuntoAgil();
					MensajesVentanas
							.centrarVentanaDialogo(windowsMenuPuntoAgil);
					
				}
			} else {
				throw new MaquinaDeEstadoExcepcion("Estado de Caja Incorrecto");
			}
		} catch (Exception e) {
			mostrarMensajeErrorException(e, "");
		} finally {
			MensajesVentanas.iniciarVerificadorFoco();
		}
	}

	/**
	 * Levanta el menu inicial del SubSistema de Punto Ágil
	 */
	public void mostrarVentanaConsultaPuntos() {
		WindowsMenuConsultasPuntoAgil windowsMenuConsultasPuntoAgil = new WindowsMenuConsultasPuntoAgil();
		MensajesVentanas.centrarVentanaDialogo(windowsMenuConsultasPuntoAgil);
	}

	/**
	 * Levanta el menu inicial del SubSistema de Punto Ágil
	 */
	public void mostrarVentanaUltimoVoucher() {
		try {
			String textoUltimoVoucher = obtenerUltimoVoucher();
			if (textoUltimoVoucher != null) {
				WindowsConsultaVoucher windowsConsultaVoucher = new WindowsConsultaVoucher();
				windowsConsultaVoucher.setVisibleAnular(false);
				windowsConsultaVoucher.setTextoVoucher(textoUltimoVoucher);
				Auditoria.registrarAuditoria("Consultando Ultimo Voucher", 'T');
				MensajesVentanas.centrarVentanaDialogo(windowsConsultaVoucher);
			} else {
				MensajesVentanas.aviso("No se pudo obtener el último voucher");
			}
		} catch (IOException e) {
			mostrarMensajeErrorException(e, "ERROR CARGANDO ULTIMO RECIBO");
		}
	}

	/**
	 * Levanta la ventana de consulta de voucher.
	 * 
	 * @param numSeq
	 */
	public void mostrarVentanaConsultaVoucher(int numSeq) {
		ManejoPagosPuntoAgil mp = (ManejoPagosPuntoAgil) ManejoPagosFactory
				.getInstance();
		DatosOperacionPuntoAgil datosOperacionPuntoAgil = null;
		try {
			datosOperacionPuntoAgil = mp.cargarOperacionPuntoAgil(this
					.getVPos().getSistConfiguracion().getVtId(), numSeq);
		} catch (BaseDeDatosExcepcion e) {
			mostrarMensajeErrorException(e,
					"PROBLEMA OBTENIENDO DATOS OPERACION");
			return;
		}

		if (datosOperacionPuntoAgil == null) {
			MensajesVentanas
					.mensajeError("OPERACION DE EL PUNTO AGIL NO ENCONTRADA");
		} else {
			short numCaja = Sesion.getNumCaja();
			String cajero = Sesion.getUsuarioActivo().getNumFicha();
			Date fechaActual = Sesion.getFechaSistema();

			if (DateUtils.isSameDay(datosOperacionPuntoAgil.getFecha(),
					fechaActual)) {
				if (datosOperacionPuntoAgil.getCodCajero().equals(cajero)) {
					if (datosOperacionPuntoAgil.getNumcaja().intValue() == numCaja) {
						if (!datosOperacionPuntoAgil.getStatus().equals(
								Constantes.OPERACION_ANULADA)) {
							String textoVoucher;
							try {
								textoVoucher = cargarContenidoArchivo(datosOperacionPuntoAgil
										.getDo_nombreVoucher());
							} catch (IOException e) {
								mostrarMensajeErrorException(e,
										"PROBLEMA CARGANDO ARCHIVO RECIBO DE OPERACION");
								return;
							}
							if (textoVoucher != null) {
								WindowsConsultaVoucher windowsConsultaVoucher = new WindowsConsultaVoucher(
										datosOperacionPuntoAgil);
								windowsConsultaVoucher.setVisibleAnular(true);
								windowsConsultaVoucher
										.setTextoVoucher(textoVoucher);
								Auditoria.registrarAuditoria(
										"Consultando Voucher de Operacion: "
												+ numSeq, 'T');
								MensajesVentanas
										.centrarVentanaDialogo(windowsConsultaVoucher);
							} else {
								MensajesVentanas
										.aviso("No se pudo obtener el voucher de la operacion"
												+ numSeq);
							}
						} else {
							MensajesVentanas
									.mensajeError("OPERACION DE EL PUNTO AGIL YA FUE ANULADA");
						}
					} else {
						MensajesVentanas
								.mensajeError("OPERACION DE EL PUNTO AGIL NO ES DE LA MISMA CAJA");
					}
				} else {
					MensajesVentanas
							.mensajeError("OPERACION DE EL PUNTO AGIL NO ES DEL MISMO CAJERO ACTIVO");
				}
			} else {
				MensajesVentanas
						.mensajeError("OPERACION DE EL PUNTO AGIL NO ES DEL DIA DE HOY");
			}
		}
	}

	/**
	 * Rutina para leer el contenido del ultimo Voucher
	 * 
	 * @return String
	 * @throws IOException
	 */
	private String obtenerUltimoVoucher() throws IOException {
		return cargarContenidoArchivo(Constantes.ULTIMO_VOUCHER);
	}

	/**
	 * @param nombreArchivo
	 * @return String
	 * @throws IOException
	 */
	private String cargarContenidoArchivo(String nombreArchivo)
			throws IOException {
		return cargarContenidoArchivo(nombreArchivo, null);
	}

	/**
	 * @param nombreArchivo
	 * @param piePagina
	 *            Mensaje para agregar al final
	 * @return String
	 * @throws IOException
	 */
	private String cargarContenidoArchivo(String nombreArchivo, String piePagina)
			throws IOException {
		String salida = null;
		validarArchivo(nombreArchivo);
		File archivo = new File(nombreArchivo);
		int largo = (int) archivo.length();
		StringWriter buffer = new StringWriter(largo);
		PrintWriter pWriter = new PrintWriter(buffer);
		LineNumberReader reader = null;
		try {
			reader = new LineNumberReader(new FileReader(archivo));
			String linea;
			while ((linea = reader.readLine()) != null) {
				pWriter.println(linea);
				if (logger.isDebugEnabled()) {
					logger.debug(reader.getLineNumber() + "\t" + linea);
				}
			}
			if (StringUtils.isNotBlank(piePagina)) {
				pWriter.println(piePagina);
			}
			salida = buffer.toString();
		} finally {
			if (reader != null) {
				reader.close();
			}
			pWriter.close();
		}
		return salida;
	}

	/**
	 * Verifica si se requiere cierre del punto y de ser necesario lo ejecuta.
	 * 
	 * @return true si se requiere un cierre
	 * @throws BaseDeDatosExcepcion
	 */
	@SuppressWarnings("unused")
	private boolean verificarRequiereCierre() throws BaseDeDatosExcepcion {
		boolean requiereCierre = false;
		ManejoPagosPuntoAgil mp = (ManejoPagosPuntoAgil) ManejoPagosFactory
				.getInstance();
		DatosCajaPuntoAgil cajaPuntoAgil = mp.cargarDatosCajaPuntoAgil();

		Calendar calHoy = DateUtils.truncate(Calendar.getInstance(),
				Calendar.DATE);
		calHoy.add(Calendar.DATE, -1);

		Date fechaCierre = (cajaPuntoAgil != null && cajaPuntoAgil
				.getFechaCierreMerchant() != null) ? DateUtils.truncate(
				cajaPuntoAgil.getFechaCierreMerchant(), Calendar.DATE) : null;

		String fechaSistema = (new SimpleDateFormat("yyyy-MM-dd"))
				.format(Sesion.getFechaSistema());
		String fechaCierreCaja = (new SimpleDateFormat("yyyy-MM-dd"))
				.format(fechaCierre);

		if (Integer.parseInt(fechaCierreCaja.substring(5, 7)) == Integer
				.parseInt(fechaSistema.substring(5, 7))) {
			if (Integer.parseInt(fechaCierreCaja.substring(8, 10)) != Integer
					.parseInt(fechaSistema.substring(8, 10))
					&& Integer.parseInt(fechaCierreCaja.substring(8, 10)) != Integer
							.parseInt(fechaSistema.substring(8, 10)) - 1) {
				requiereCierre = true;
			}
		} else {
			if (Integer.parseInt(fechaCierreCaja.substring(8, 10)) - 30 == 0) {
				if (Integer.parseInt(fechaSistema.substring(8, 10)) > 1) {
					requiereCierre = true;
				}
			} else if (Integer.parseInt(fechaCierreCaja.substring(8, 9)) - 31 == 0) {
				if (Integer.parseInt(fechaSistema.substring(8, 10)) > 1) {
					requiereCierre = true;
				}
			} else if (Integer.parseInt(fechaCierreCaja.substring(8, 9)) - 28 == 0) {
				if (Integer.parseInt(fechaSistema.substring(8, 10)) > 1) {
					requiereCierre = true;
				}
			}

		}

		//		if (!fechaCierreCaja.equalsIgnoreCase(fechaSistema)) {
		//			requiereCierre = true;
		//		}
		//		requiereCierre = (fechaCierre == null || !DateUtils.isSameDay(
		//			calHoy.getTime(), fechaCierre));

		if (requiereCierre) {
			MensajesVentanas.aviso("EL PUNTO AGIL NO TIENE CIERRE");
		}
		return requiereCierre;
	}

	/**
	 * Realiza un cierre de las operaciones de punto agil y emite el reporte
	 * respectivo.
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se comentó variable sin uso
	* Fecha: agosto 2011
	*/
	public synchronized void cierre() {
		if (logger.isDebugEnabled()) {
			logger.debug("Ha seleccionado cierre...");
		}
		ManejoPagosPuntoAgil mp = (ManejoPagosPuntoAgil) ManejoPagosFactory
				.getInstance();
		try {
			//String[] params = { "", "", "", "", "", "", "", "", "", "", "" };
			try {
				/*ejecutarVPosDatosExternos(params,
						Constantes.PROCESANDO_CON_EL_BANCO);*/
				/**
				 * Modificado por CENTROBECO, C.A. por migración a VPosUniversal.
				 */
				VPosUniversal vposUniversal = new VPosUniversal(Constantes.RUTA_CONF_VPOS);
				vposUniversal.cierre();
				
				Auditoria.registrarAuditoria(
						"Realizado Cierre de EL PUNTO AGIL", 'T');
				String archivoCierre = vposUniversal.getArchivoCierre();
				MensajesVentanas
						.aviso("A CONTINUACION SE EMITIRA REPORTE DE CIERRE DE EL PUNTO AGIL");
				imprimirArchivo(archivoCierre, false,
						"Ejecutado Cierre de El Punto Agil");

				DatosCajaPuntoAgil cajaPuntoAgil = new DatosCajaPuntoAgil(true);
				boolean actualizado = false;
				try {
					actualizado = mp
							.actualizarInsertarDatosCajaPuntoAgil(cajaPuntoAgil);
					if (!actualizado) {
						MensajesVentanas
								.mensajeError("NO SE PUDO ACTUALIZAR FECHA DE CIERRE DE VPOS EN BASE DE DATOS");
					}
				} catch (BaseDeDatosExcepcion e) {
					mostrarMensajeErrorException(e,
							"NO SE PUDO ACTUALIZAR FECHA DE CIERRE DE VPOS EN BASE DE DATOS");
					actualizado = false;
				}
			} catch (Throwable e1) {
				if (e1 instanceof VPosException) {
					throw (VPosException) e1;
				} else {
					throw new VPosException(e1);
				}
			}
		} catch (VPosException e) {
			mostrarMensajeErrorException(e,
					"PROBLEMA EFECTUANDO EL CIERRE DE EL PUNTO AGIL");
		}
	}

	/**
	 * Ejecuta el precierre de las operaciones de punto ágil. Emitiendo el
	 * reporte respectivo.
	 * 
	 * @see VPos#voucherPrecierre(java.lang.String)
	 * @see VPos#getArchivoCierre()
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se comentaron variables sin uso
	* Fecha: agosto 2011
	*/
	void preCierre() {
		if (logger.isDebugEnabled()) {
			logger.debug("Ha seleccionado Pre Cierre...");
		}
		try {
			//String[] params = { "" };
			//Class[] paramClass = { String.class };
			try {
				/*Method voucherPrecierre = this.getVPos().getClass().getMethod(
						"voucherPrecierre", paramClass);
				VentanaEspera.ejecutarEsperar(voucherPrecierre, this.getVPos(),
						params, Constantes.PROCESANDO_CON_EL_BANCO);*/
				VPosUniversal vposUniversal = new VPosUniversal(Constantes.RUTA_CONF_VPOS);
				vposUniversal.precierre();
				Auditoria.registrarAuditoria(
						"Realizado Pre Cierre de EL PUNTO AGIL", 'T');
				String archivoCierre = vposUniversal.getArchivoCierre();
				imprimirArchivo(archivoCierre, false);
			} catch (Throwable e) {
				if (e instanceof VPosException) {
					throw (VPosException) e;
				} else {
					throw new VPosException(e);
				}
			}
		} catch (VPosException e) {
			mostrarMensajeErrorException(e,
					"PROBLEMA EFECTUANDO PRECIERRE DE EL PUNTO AGIL");
		}
	}

	/**
	 * @param archivo
	 * @return Archivo encontrado
	 * @throws FileNotFoundException
	 */
	File validarArchivo(String archivo) throws FileNotFoundException {
		File file = new File(archivo);
		if (StringUtils.isBlank(archivo) || !file.exists() || !file.isFile()) {
			throw new FileNotFoundException("Archivo invalido o inexistente: "
					+ archivo);
		}
		return file;
	}

	void imprimirArchivo(String nombreArchivo, boolean reimpresion)
			throws IOException {
		imprimirArchivo(nombreArchivo, reimpresion, null);
	}

	void imprimirArchivo(String nombreArchivo, boolean reimpresion,
			String piePagina) throws IOException {
		String contenido = cargarContenidoArchivo(nombreArchivo, piePagina);
		if (StringUtils.isBlank(contenido)) {
			MensajesVentanas
					.mensajeError("No se pudo cargar contenido de archivo, o está en blanco: "
							+ SystemUtils.LINE_SEPARATOR + nombreArchivo);
			return;
		}

		imprimirContenido(contenido, reimpresion);
	}

	void imprimirContenido(String contenido, boolean reimpresion) {
		if (StringUtils.isBlank(contenido)) {
			MensajesVentanas
					.aviso("ERROR IMPRIMIENDO COMPROBANTE EL PUNTO AGIL."
							+ SystemUtils.LINE_SEPARATOR
							+ "CONTENIDO EN BLANCO.");
		}
		if (Sesion.impresoraFiscal) {
			// Cadenas de secciones de la factura
			LineNumberReader reader = null;
			try {
//				ACTUALIZACION CENTROBECO C.A.
				//CAMBIOS POR IMPRESORA FISCAL GD4
				while (CRFiscalPrinterOperations.isImpresionFiscalEnCurso() ||
						MaquinaDeEstadoVenta.isDocumentoNoFiscalPorImprimir()) {
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						// TODO Bloque catch generado automáticamente
						e.printStackTrace();
					}
					if(MaquinaDeEstadoVenta.errorAtencionUsuario){
						MensajesVentanas.aviso("Hay un documento en curso \nEspere un momento e intente nuevamente");
					}
				}
				if (Sesion.impresoraFiscal) {
					MaquinaDeEstadoVenta.setDocumentoNoFiscalPorImprimir(true);
				}
				Sesion.crFiscalPrinterOperations.abrirDocumentoNoFiscal();
				reader = new LineNumberReader(new StringReader(contenido));
				String linea;
				while ((linea = reader.readLine()) != null) {
					if (reader.getLineNumber() == 5 && reimpresion) {
						enviarLineaNoFiscal("*** DUPLICADO ***", 3);
					}
//					if(linea.indexOf("MONTO") >= 0){
//						linea = StringUtils.deleteWhitespace(linea);
//						linea = linea.substring(0,5)+" "+linea.substring(5);
//						enviarLineaNoFiscal(linea, 1);
//					}else{
//						enviarLineaNoFiscal(linea, 0);
//					}
					linea = linea.replaceAll("TOTAL", "TOT...");
					linea = linea.replaceAll("Total", "Tot...");
					linea = linea.replaceAll("total", "tot...");
					enviarLineaNoFiscal(linea, 0);
					
				}
			} catch (IOException e1) {
				enviarLineaNoFiscal("*** ERROR IMPRIMIENDO COMPROBANTE ***", 2);
				mostrarMensajeErrorException(e1,
						"PROBLEMA IMPRIMIENDO COMPROBANTE");
			} finally {
				if (reader != null) {
					try {
						reader.close();
					} catch (IOException e) {
						mostrarMensajeErrorException(e,
								"PROBLEMA CERRANDO LECTURA COMPROBANTE");
					}
				}
				Sesion.crFiscalPrinterOperations.cerrarDocumentoNoFiscal();
				
				
				if(Sesion.crFiscalPrinterOperations.getSystem().getEngine() instanceof com.epa.crprinterdriver.GD4Engine)
					Sesion.crFiscalPrinterOperations.cortarPapel();
				
				try {
					Sesion.crFiscalPrinterOperations.commit();
				} catch (PrinterNotConnectedException e) {
					mostrarMensajeErrorException(e,
							"PROBLEMA CERRANDO DOCUMENTO NO FISCAL EN IMPRESORA");
				}

			}
		} else {
			// TODO VACIO - DESPUES SE IMPLEMENTARA PARA IMPRESORAS NO FISCALES
		}
	}

	/**
	 * Imprime una linea no fiscal a la impresora, aplicando filtro de
	 * caracteres
	 * 
	 * @param linea
	 * @param tipo
	 */
	private void enviarLineaNoFiscal(String linea, int tipo) {
		if (linea.length() > 40) {
			linea = linea.substring(0, 40);
		}
	Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(Reporte
				.validarCaracteresBasicos(linea), tipo);

	}

	/**
	 * Procesa el pago a través del punto ágil.
	 * 
	 * @return DatosResultadosPuntoAgil
	 */
	public synchronized DatosOperacionPuntoAgil consultaPuntos() {
		if (logger.isDebugEnabled()) {
			logger.debug("Ha seleccionado consultaPuntos...");
		}
		DatosOperacionPuntoAgil salida = null;
		DatosTarjetaPuntoAgil datosTarjetaPuntoAgil = null;
		datosTarjetaPuntoAgil = lecturaBanda();
		if (datosTarjetaPuntoAgil != null) {
			try {
				PantallaDatosAdicionales pda = null;
				if (datosTarjetaPuntoAgil.getTipoTarjeta().equals(
						Constantes.TARJETA_CREDITO)) {
					pda = new PantallaDatosAdicionalesPuntoAgil(
							Constantes.FDP_CONSULTA_PUNTOS_CREDITO,
							datosTarjetaPuntoAgil, 0);
				} else if (datosTarjetaPuntoAgil.getTipoTarjeta().equals(
						Constantes.TARJETA_DEBITO)) {
					pda = new PantallaDatosAdicionalesPuntoAgil(
							Constantes.FDP_CONSULTA_PUNTOS_DEBITO,
							datosTarjetaPuntoAgil, 0);
				} else {
					MensajesVentanas
							.mensajeError("ESTA TARJETA NO PERMITE CONSULTA PUNTOS");
				}
				if (pda != null) {
					MensajesVentanas.centrarVentanaDialogo(pda);
				}
			} catch (Throwable e) {
				mostrarMensajeErrorException(e, "PROBLEMA CONSULTANDO PUNTOS");
			}
		} else {
			// SE ASUME QUE LA OPERACION FUE CANCELADA O HUBO ERROR DE LECTURA Y
			// YA lecturaBanda() MOSTRO EL MENSAJE RESPECTIVO

		}
		return salida;

	}

	/**
	 * Procesa el pago a través del punto ágil.
	 * 
	 * @param pago
	 * @return DatosResultadosPuntoAgil
	 * @throws PagoExcepcion
	 */
	public synchronized DatosOperacionPuntoAgil procesarPago(
			DatosPagoPuntoAgil pago) throws PagoExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("Ha seleccionado procesarPago...");
		}
		DatosOperacionPuntoAgil salida = null;
		final DatosFormaDePagoPuntoAgil fdp = pago
				.getDatosFormaDePagoPuntoAgil();

		try {
			if (isTipoProcesoValido(pago.getTipoProceso())) {
				salida = ejecutarVPos(pago, fdp.isImprimirVoucher());
			} else {
				throw new VPosException("TIPO DE PROCESO NO ES VALIDO "
						//+ "(TIENE QUE SER VENTA, ABONO O PAGO TDC EPA)");
						+ "(TIENE QUE SER VENTA O ABONO)");
			}
		} catch (VPosException e) {
			mostrarArrojarVPosException(e, "PROBLEMA PROCESANDO PAGO");
			logger.error(e,e);
		} catch (Throwable e) {
			mostrarMensajeErrorException(e, "PROBLEMA PROCESANDO PAGO");
			logger.error(e,e);
			throw new PagoExcepcion("PROBLEMA PROCESANDO PAGO");
		} 
		if (logger.isDebugEnabled()) {
			logger.debug("Saliendo de procesarPago...: " + salida);
		}
		return salida;
	}

	/**
	 * @param tipoProceso
	 * @return boolean
	 */
	private boolean isTipoProcesoValido(final Integer tipoProceso) {
		boolean tipoProcesoValido = (Constantes.TIPO_PROCESO_VENTA
				.equals(tipoProceso)
				|| Constantes.TIPO_PROCESO_ABONO.equals(tipoProceso)
				|| Constantes.TIPO_PROCESO_PAGO_TC_EPA.equals(tipoProceso) || Constantes.TIPO_PROCESO_PSEUDO_TRANSACCION_PUNTO_AGIL
				.equals(tipoProceso));
		return tipoProcesoValido;
	}

	/**
	 * @param pago
	 *            DTO con los datos indicados
	 * @param imprimirVoucher
	 *            indica si del resultado de la operacion se imprime un voucher
	 * @return Resultado de la ejecucion.
	 * @throws VPosException
	 * @throws NoSuchMethodException
	 * @throws Throwable
	 */
	DatosOperacionPuntoAgil ejecutarVPos(DatosPagoPuntoAgil pago,
			boolean imprimirVoucher) throws VPosException,
			NoSuchMethodException, Throwable {
		return ejecutarVPos(pago, null, imprimirVoucher);
	}

	/**
	 * @param datos
	 *            DTO con los datos indicados
	 * @param imprimirVoucher
	 *            indica si del resultado de la operacion se imprime un voucher
	 * @return Resultado de la ejecucion.
	 * @throws VPosException
	 * @throws NoSuchMethodException
	 * @throws Throwable
	 */
	DatosOperacionPuntoAgil ejecutarVPos(DatosExternosPuntoAgil datos,
			boolean imprimirVoucher) throws VPosException,
			NoSuchMethodException, Throwable {
		return ejecutarVPos(null, datos, imprimirVoucher);
	}

	/**
	 * @param pago
	 * @param datos
	 *            DTO con los datos indicados
	 * @param imprimirVoucher
	 *            indica si del resultado de la operacion se imprime un voucher
	 * @return Resultado de la ejecucion.
	 * @throws VPosException
	 * @throws NoSuchMethodException
	 * @throws Throwable
	 */
	private DatosOperacionPuntoAgil ejecutarVPos(DatosPagoPuntoAgil pago,
			DatosExternosPuntoAgil datos, boolean imprimirVoucher)
			throws VPosException, NoSuchMethodException, Throwable {
		if (pago != null && datos == null) {
			datos = pago.toDatosExternosPuntoAgil();
		} else if (datos == null) {
			throw new VPosException(
					"DATOS PARA GUARDAR PROCESAR TRANSACCION POR EL PUNTO AGIL INCOMPLETOS");
		}
		//verificarRequiereCierre();
		Usuario user = Sesion.getUsuarioActivo();
		Colaborador colab = user.getDatosPersonales();
		if (colab != null) {
			Cajero cajero = new Cajero();
			cajero.setCedula(Control.removerCaracteresNoListados(colab
					.getCodAfiliado(), "0123456789"));
			cajero.setNombre(colab.getNombre());
			this.getVPos().setCajero(cajero);
		}
		
		DatosOperacionPuntoAgil datosOperacionPuntoAgil = ejecutarVPosDatos(
				pago, datos, imprimirVoucher);

		// SI LA CUENTA ESPECIAL CORRESPONDE A PROVIMILLAS HAY QUE CONSULTAR
		// PORCENTAJE MAXIMO
		if (logger.isDebugEnabled()) {
			logger.debug("entrada cod. cuenta especial : "+ datos.getCtasEspeciales());
		}
		if (Constantes.CTA_ESPECIAL_PROVIMILLAS.equals(datos
				.getCtasEspeciales())) {
			/*String porcentaje = this.getVPos().getPorcentaje();
			if (StringUtils.isBlank(porcentaje)
					|| !StringUtils.isNumeric(porcentaje)) {
				throw new VPosException(
						"PORCENTAJE PROVIMILLAS DEVUELTO POR MERCHANT INVALIDO: "
								+ porcentaje);
			} else {
				MensajesVentanas.aviso("% PROVIMILLAS: " + porcentaje);
			}
			int porc = Integer.parseInt(porcentaje);
			WindowsPagoProvimillasPuntoAgil windowsPagoProvimillasPuntoAgil = new WindowsPagoProvimillasPuntoAgil(
					porc);
			MensajesVentanas
					.centrarVentanaDialogo(windowsPagoProvimillasPuntoAgil);
			DatosPagoProvimillasPuntoAgil provi = windowsPagoProvimillasPuntoAgil
					.getDatosPagoProvimillas();
			if (provi == null) {
				throw new VPosException("OPERACION CANCELADA");
			}
			datos.setPorcentaje(provi.getPorcentaje());
			datos.setPlan(provi.getPlan());
			if ("99".equals(datos.getPlan())) {
				BigDecimal montoOriginal = new BigDecimal(pago.getMonto());
				BigDecimal cien = BigDecimal.valueOf(100);
				BigDecimal porcAplicado = new BigDecimal(datos.getPorcentaje());
				BigDecimal monto = porcAplicado.multiply(montoOriginal).divide(
						cien, BigDecimal.ROUND_HALF_UP);
				pago.setMonto(monto.doubleValue());
			}*/
			datosOperacionPuntoAgil = ejecutarVPosDatos(pago, datos,
					imprimirVoucher);
		
			if (logger.isDebugEnabled()) {
				logger.debug("salida cod. cuenta especial : "+ datos.getCtasEspeciales());
			}
		}

		return datosOperacionPuntoAgil;
	}

	/**
	 * @param pago
	 * @param datos
	 * @param imprimirVoucher
	 * @return DatosOperacionPuntoAgil
	 * @throws Throwable
	 * @throws NoSuchMethodException
	 */
	private DatosOperacionPuntoAgil ejecutarVPosDatos(DatosPagoPuntoAgil pago,
			DatosExternosPuntoAgil datos, boolean imprimirVoucher)
			throws NoSuchMethodException, Throwable {
		if (Constantes.CTA_ESPECIAL_PROVIMILLAS.equals(datos
				.getCtasEspeciales())
				&& datos.getPorcentaje() == null) {
			imprimirVoucher = false;
		}
		Date horaInicia = Sesion.getFechaSistema();
		String[] params = datos.toArray();
		String msgVentanaEspera = (pago != null
				&& pago.getDatosTarjetaPuntoAgil() != null && pago
				.getDatosTarjetaPuntoAgil().getTipoTarjeta().equalsIgnoreCase(
						Constantes.TARJETA_DEBITO)) ? Constantes.OBSERVE_MENSAJE_EN_EL_PINPAD
				: Constantes.PROCESANDO_CON_EL_BANCO;

		try{
			ejecutarVPosDatosExternos(params, msgVentanaEspera);
		}catch (Exception e) {
			StringBuffer impresionError= new StringBuffer();
			impresionError.append(Sesion.getTienda().getRazonSocial());
			impresionError.append("\n");
			impresionError.append("RIF :"+Sesion.getTienda().getRif());
			//impresionError.append("\n");
			//impresionError.append(Sesion.getTienda().getDireccion());
			impresionError.append("\n");
			//impresionError.append("TIENDA :BECO "+Sesion.getTienda().getNumero());
			impresionError.append("TIENDA :BECO "+Sesion.getTienda().getNombreSucursal());
			impresionError.append("\n");
			impresionError.append("CAJA :"+Sesion.getCaja().getNumero()+ "			CAJERO: "+Sesion.getUsuarioActivo().getNumFicha());
			impresionError.append("\n");
			impresionError.append("\n");
			if (pago != null) {
				//***** BECO: Eliminado 03/03/2008 por observaciones de certificacion de banco Mercantil
				//impresionError.append("CLIENTE:"+pago.getDatosTarjetaPuntoAgil().getNombreCliente());
				//impresionError.append("\n");
				impresionError.append(pago.getDatosTarjetaPuntoAgil().getNumeroTarjeta());
				impresionError.append("\n");
			}
			impresionError.append("TRANSACCION NO PROCESADA:\n");
			impresionError.append("FALLA COMUNICACION (SEQ "+this.getVPos().getNumSeq()+")\n");
			this.imprimirContenido(impresionError.toString(), false);
			Auditoria.registrarAuditoria("Error de secuencia 0 punto agil de Caja " + Sesion.getCaja().getNumero()+" codigo respuesta="+this.getVPos().getCodRespuesta(),'E'); 
			throw e;
		}
		DatosResultadosPuntoAgil resultados = datosTransaccion();
		resultados.setHoraInicia(horaInicia);
		resultados.setHoraFinaliza(Sesion.getFechaSistema());

		// GUARDAR RESULTADO EN BASE DE DATOS
		DatosOperacionPuntoAgil datosOperacionPuntoAgil;
		if (pago != null) {
			datosOperacionPuntoAgil = new DatosOperacionPuntoAgil(pago,
					resultados);
			if (pago.addDatosOperacionPuntoAgil(datosOperacionPuntoAgil)) {
				// NO HACER NADA
			} else {
				MensajesVentanas
						.mensajeError("NO SE PUDO AGREGAR OPERACION EL PUNTO AGIL A PAGO");
			}
		} else {
			datosOperacionPuntoAgil = new DatosOperacionPuntoAgil(datos,
					resultados);
		}

		boolean fallaComunicacion1 = StringUtils.isBlank(resultados
				.getCodRespuesta())
				&& Constantes.INTEGER_ZERO.equals(resultados.getNumSeq());
		boolean fallaComunicacion2 = Constantes.COD_RESPUESTA_FALLA_COMUNICACION
				.equalsIgnoreCase(resultados.getCodRespuesta());

		
		String mensaje = resultados.toString();
		if (imprimirVoucher
				&& StringUtils.isBlank(resultados.getMensajeError())) {
			mensaje = mensaje + SystemUtils.LINE_SEPARATOR
					+ SystemUtils.LINE_SEPARATOR + "A continuación se "
					+ SystemUtils.LINE_SEPARATOR + "imprimirá recibo -->";
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug(mensaje);
		}
		//IMPRESION MENSAJE DE RESULTADOS DE MERCHANT
//		MensajesVentanas.aviso(mensaje);
//		MensajesVentanas.aviso(mensaje,true);
//		VentanaEspera.ejecutarEsperar(null,mensaje,null,5000);
		boolean insertado = false;
		if (!fallaComunicacion1){
			insertado = guardarDatosOperacion(datosOperacionPuntoAgil);
		}
		if (!insertado) {
			MensajesVentanas
					.mensajeError("NO SE GUARDO RESULTADOS EN BASE DE DATOS");
			logger.error("NO SE GUARDO RESULTADOS EN BASE DE DATOS"
					+ datosOperacionPuntoAgil);	
		}
		
		//SI ESTA EN BLANCO ES PORQUE NO HAY NINGUN ERROR
		if (StringUtils.isBlank(resultados.getMensajeError())) {
			//imprimirVoucher(imprimirVoucher, resultados, datosOperacionPuntoAgil, mensaje);
			if (!Constantes.COD_RESPUESTA_APROBADO.equalsIgnoreCase(resultados
					.getCodRespuesta())) {
				if (fallaComunicacion1 || fallaComunicacion2) {
					ManejoPagosPuntoAgil mppa = (ManejoPagosPuntoAgil) ManejoPagosFactory
							.getInstance();
					mppa.setLineaPuntoAgil(false);
					throw new VPosException("OPERACION NO APROBADA: FALLA DE COMUNICACION");
				}
				throw new VPosException("OPERACION NO APROBADA: "
						+ resultados.getCodRespuesta());
			}
		} else {
			MensajesVentanas
					.aviso("MENSAJE DE ERROR DE SERVIDOR MERCHANT: "
							+ SystemUtils.LINE_SEPARATOR
							+ resultados.getMensajeError());
			throw new VPosException(resultados.getMensajeError());
		}
		return datosOperacionPuntoAgil;
	}

	private void imprimirVoucher(DatosResultadosPuntoAgil resultados, DatosOperacionPuntoAgil datosOperacionPuntoAgil, String mensaje) {
		try {
			imprimirArchivo(resultados.getNombreVoucher(), false);
			if (Constantes.COD_RESPUESTA_APROBADO
					.equalsIgnoreCase(resultados.getCodRespuesta())) {
				//***** 04/01/2008
				//**** Modificacin BECO: Agregada pregunta para no imprimir copias cuando la operacin es de consultas
				//**** Esto porque la nueva versin de MegaVpos no genera el archivo de copia del Cliente y 
				//**** muestra el error de que no encontr el archivo por pantalla
				
				if (datosOperacionPuntoAgil.getTipoProceso().intValue() != 
					Constantes.TIPO_PROCESO_PSEUDO_TRANSACCION_PUNTO_AGIL.intValue()) {
					String fileVoucherCliente = resultados
							.getNombreVoucher().substring(
									0,
									resultados.getNombreVoucher()
											.lastIndexOf("."))
							+ "a.txt";
					imprimirArchivo(fileVoucherCliente, false);
				}
				
				// IMPRESION MENSAJE DE RESULTADOS DE MERCHANT
				MensajesVentanas.aviso(mensaje);
			}
		} catch (IOException e) {
			mostrarMensajeErrorException(e, "PROBLEMA IMPRIMIENDO VOUCHER");
		}
	}

	/**
	 * @param params
	 * @param mensaje
	 * @throws NoSuchMethodException
	 * @throws Throwable
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Class'
	* Fecha: agosto 2011
	*/
	private synchronized void ejecutarVPosDatosExternos(String[] params,
			String mensaje) throws NoSuchMethodException, Throwable {
		/*
		 * Asignando parametro como array de array, para cumplir con especificacion
		 * de Method#getMethod(String nombremetodo, Object[] arrayParametros);
		 */
		String[][] params1 = new String[1][1];
		params1[0] = params;
		
		Class<?>[] paramClass = new Class[params1.length];
		for (int i = 0; i < paramClass.length; i++) {
			paramClass[i] = params.getClass();
		}
		//if (logger.isDebugEnabled()) {
		String log = "Llamando a vPos.datosExternos("
				+ ArrayUtils.toString(params) + ")";
		if (logger.isDebugEnabled()) {
			logger.debug(log);
		}
		//}

		Method datosExternos = this.getClass().getMethod(
				"datosExternos", paramClass);
		VentanaEspera.ejecutarEsperar(datosExternos, this, params1,
				mensaje);
		String log1 = "Culminada llamada a vPos.datosExternos("
				+ ArrayUtils.toString(params) + ")";
		if (logger.isDebugEnabled()) {
			logger.debug(log1);
		}
	}

	/**
	 * @param datosOperacionPuntoAgil
	 * @return si pudo guardar
	 */
	private boolean guardarDatosOperacion(
			DatosOperacionPuntoAgil datosOperacionPuntoAgil) {
		boolean insertado = false;
		try {
			insertado = ((ManejoPagosPuntoAgil) ManejoPagosFactory
					.getInstance())
					.insertarDatosOperacionPuntoAgil(datosOperacionPuntoAgil);
			Sesion.getCaja().setNumSeqMerchant(
					datosOperacionPuntoAgil.getNumSeq().intValue());
			Sesion.getCaja().actualizarNumSeqMerchant();
		} catch (BaseDeDatosExcepcion e) {
			mostrarMensajeErrorException(e,
					"PROBLEMA PROCESANDO PAGO GUARDANDO OPERACION EN BASE DE DATOS");
		} catch (ConexionExcepcion e) {
			String mensajeSeccion = "PROBLEMA PROCESANDO PAGO GUARDANDO OPERACION EN BASE DE DATOS";
			mostrarMensajeErrorException(e, mensajeSeccion);
		}
		return insertado;
	}

	/**
	 * @param e
	 * @param mensajeSeccion
	 * @throws PagoExcepcion
	 */
	private void mostrarArrojarVPosException(VPosException e,
			String mensajeSeccion) throws PagoExcepcion {
		String mensaje = mostrarMensajeErrorException(e, mensajeSeccion);
		throw new PagoExcepcion(mensaje, e);
	}
	
	/**
	 * @param e
	 * @param mensajeSeccion
	 * @throws PagoExcepcion
	 */
	private void mostrarArrojarVPosUniversalException(VposUniversalException e,
			String mensajeSeccion) throws PagoExcepcion {
		String mensaje = mostrarMensajeErrorException(e, mensajeSeccion);
		throw new PagoExcepcion(mensaje, e);
	}
	
	/**
	 * @param e
	 * @param mensajeSeccion
	 * @throws PagoExcepcion
	 */
	private void mostrarArrojarBonoRegaloException(VposUniversalException e,
			String mensajeSeccion) throws BonoRegaloException {
		String mensaje = mostrarMensajeErrorException(e, mensajeSeccion);
		throw new BonoRegaloException(mensaje, e);
	}

	/**
	 * @param e
	 * @param mensajeSeccion
	 * @return String
	 */
	private String mostrarMensajeErrorException(Throwable e,
			String mensajeSeccion) {
		String mensaje = mensajeSeccion + SystemUtils.LINE_SEPARATOR
				+ e.getLocalizedMessage();
		if(this.getVPos()!=null)
			if (StringUtils.isNotEmpty(this.getVPos().getMensajeError())) {
				mensaje = mensaje + SystemUtils.LINE_SEPARATOR
						+ this.getVPos().getMensajeError();
			}
		logger.error(mensaje, e);
		mensaje = StringUtils.remove(mensaje, "_ERROR_");
		MensajesVentanas.aviso(mensaje);
		return mensaje;
	}

	/**
	 * Realiza lectura de la banda magnetica de la tarjeta
	 * 
	 * @return DatosTarjetaPuntoAgil
	 */
	public synchronized DatosTarjetaPuntoAgil lecturaBanda() {
		DatosTarjetaPuntoAgil datosTarjetaPuntoAgil = null;
		setSw(false);
		do {
			try {
				int opcion = MensajesVentanas.preguntarOpcionesTimeOut(
						"Preparese para deslizar Tarjeta", "Aceptar",
						"Cancelar", 0, 5000);
				
				if (opcion == 1) {
					return null;
				}
				
				Method lecturaBanda = this.getVPos().getClass().getMethod(
						"lecturaBanda", new Class[] { String.class });
				VentanaEspera.ejecutarEsperar(lecturaBanda, this.getVPos(),
						new String[] { "" },
						Constantes.OBSERVE_MENSAJE_EN_EL_PINPAD);
				
				datosTarjetaPuntoAgil = new DatosTarjetaPuntoAgil();
				datosTarjetaPuntoAgil.setFechaVencimiento(this.getVPos()
						.getFechaVencimiento());
				datosTarjetaPuntoAgil.setNombreCliente(this.getVPos()
						.getNombreCliente());
				datosTarjetaPuntoAgil.setNumeroTarjeta(this.getVPos()
						.getNumeroTarjeta());
				datosTarjetaPuntoAgil.setTipoTarjeta(this.getVPos()
						.getTipoTarjeta());
				
				
				setSw(true);
				
			} catch (Throwable e) {
				mostrarMensajeErrorException(e,
						"PROBLEMA EN LA LECTURA DE BANDA DEL PINPAD");
			}
		} while (datosTarjetaPuntoAgil == null
				|| StringUtils
						.isBlank(datosTarjetaPuntoAgil.getNumeroTarjeta()));
		
		
		
		
		return datosTarjetaPuntoAgil;
	}

	/**
	 * Devuelve los datos de la última transacción.
	 * 
	 * @return DatosResultadosPuntoAgil
	 */
	private DatosResultadosPuntoAgil datosTransaccion() {
		DatosResultadosPuntoAgil datos = new DatosResultadosPuntoAgil();
		datos.setNumSeq(this.getVPos().getNumSeq());
		datos.setCodRespuesta(this.getVPos().getCodRespuesta());
		datos.setMensajeError(this.getVPos().getMensajeError());
		datos.setNombreAutorizador(this.getVPos().getNombreAutorizador());
		datos.setNumeroAutorizacion(this.getVPos().getNumeroAutorizacion());
		datos.setNombreVoucher(this.getVPos().getNombreVoucher());
		datos.setMensajeRespuesta(this.getVPos().getMensajeRespuesta());
		return datos;
	}
	
	/**
	 * Devuelve los datos de la última transacción.
	 * 
	 * @return DatosResultadosPuntoAgil
	 */
	private DatosResultadosPuntoAgil datosTransaccion(VPosUniversal vpos) {
		DatosResultadosPuntoAgil datos = new DatosResultadosPuntoAgil();
		datos.setNumSeq(vpos.getNumSeq());
		datos.setCodRespuesta(vpos.getCodRespuesta());
		datos.setMensajeError("");
		datos.setNombreAutorizador(vpos.getNombreAutorizador());
		datos.setNumeroAutorizacion(vpos.getNumeroAutorizacion());
		datos.setNombreVoucher(vpos.getNombreVoucher());
		String montoAprobado = vpos.getMontoTransaccion()==null? "000":vpos.getMontoTransaccion();
		datos.setMontoAprobado(Double.parseDouble(montoAprobado.substring(0, montoAprobado.length()-2)+"."+montoAprobado.substring(montoAprobado.length()-2,montoAprobado.length())));
		datos.setMensajeRespuesta(vpos.getMensajeRespuesta());
		return datos;
	}

	/**
	 * Realiza el test de conexion con los bancos.
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Class'
	* Fecha: agosto 2011
	*/
	public synchronized void testConexionBancos() {
		try {
			String[] params = { "" };
			Class<?>[] paramClass = { String.class };
			try {
				Method TestPayments = this.getVPos().getClass().getMethod(
						"TestPayments", paramClass);
				VentanaEspera.ejecutarEsperar(TestPayments, this.getVPos(),
						params, Constantes.PROCESANDO_CON_EL_BANCO);
				MensajesVentanas
						.aviso("A continuación se imprimirá resultado de test de conexión.");
				imprimirArchivo(this.getVPos().getArchivoTest(), false);
			} catch (Throwable e) {
				if (e instanceof VPosException) {
					throw (VPosException) e;
				} else {
					throw new VPosException(e);
				}
			}
		} catch (VPosException e) {
			mostrarMensajeErrorException(e,
					"PROBLEMA REALIZANDO TEST DE CONEXION CON BANCOS");
		}
	}
	
	/**
	 * Realiza la reimpresión del último cierre de Merchant
	 */
	public synchronized void reimprimirUltCierre() {
		if (logger.isDebugEnabled()) {
			logger.debug("Ha seleccionado Reimprimir Ult.Cierre...");
		}
		
		String rutaCierres = this.getVPos().getSistConfiguracion().getPath() + CARPETA_CIERRES;
		File archivo = new File(rutaCierres);
		Calendar fechaHoy = Calendar.getInstance();
		String dia2Digitos =  (fechaHoy.get(Calendar.DAY_OF_MONTH) < 10) ? "0" + fechaHoy.get(Calendar.DAY_OF_MONTH) : ""+fechaHoy.get(Calendar.DAY_OF_MONTH);
    	String mes2Digitos =  ((fechaHoy.get(Calendar.MONTH) + 1) < 10) ? "0" + (fechaHoy.get(Calendar.MONTH)+1) : ""+(fechaHoy.get(Calendar.MONTH)+1);
    	String fechaHoyString = fechaHoy.get(Calendar.YEAR) +  mes2Digitos + dia2Digitos;
				
		String [] listaCierres = archivo.list(new Filtro(fechaHoyString.trim(), "Cierre"));
		
		try {
			if (listaCierres != null && listaCierres.length > 0) {
				String nombreArchivo1 =  listaCierres[0];
		        File archivo1 = new File(rutaCierres + nombreArchivo1);
		        Date fecha1 = new Date(archivo1.lastModified());
				Calendar archFechaReciente = Calendar.getInstance();
				archFechaReciente.setTime(fecha1);
				String archNombreImprimir = "";
				
	 	        for(int i=0; i<listaCierres.length; i++){
					String nombreArchivo =  listaCierres[i];
			        File archivo2 = new File(rutaCierres + nombreArchivo);
				    try {
			        	 Date fecha = new Date(archivo2.lastModified());
			 	         Calendar fechaArch = Calendar.getInstance();
			 	         fechaArch.setTime(fecha);
				         if (fechaArch.equals(archFechaReciente) || fechaArch.after(archFechaReciente)) {
				        	 archFechaReciente = fechaArch;
				        	 archNombreImprimir = nombreArchivo;
				         }
				    }catch (Throwable e) {
						if (e instanceof VPosException) {
							throw (VPosException) e;
						} else {
							throw new VPosException(e);
						}
					}
				}
	 	        
	 	       Auditoria.registrarAuditoria(
	 	    		   "Realizado Reimpresión del Último Cierre de EL PUNTO AGIL " + archNombreImprimir, 'T');
	 	       
				MensajesVentanas.aviso("A continuación se realizará la reimpresión del último cierre del día");
				imprimirArchivo(rutaCierres.trim() + archNombreImprimir.trim(), true);
			} else {
				MensajesVentanas.aviso(
						"No se encontraron registros para reimpresión de cierre de PuntoAgil del día de Hoy");
				 logger.error("** No se encontraron registros para reimpresión de cierre de PuntoAgil");
			}
		}catch (Exception e1){
			mostrarMensajeErrorException(e1,
			"PROBLEMA EFECTUANDO REIMPRESION DE ULTIMO CIERRE DE EL PUNTO AGIL ");
		} 
	}

	/**
	 * @param _agil
	 *            void
	 */
	public synchronized void anularOperacionPuntoAgil(
			DatosOperacionPuntoAgil _agil) {
		if (_agil != null) {
			if (_agil.getTipoOperacion().equals(Constantes.TIPO_OPERACION_PAGO)) {
				try {
					if (_agil.getNumproceso() == null
							&& _agil.getCorrelativoPagoProceso() == null) {
						anular(_agil);
					} else {
						boolean operacionOriginalAnulada = false;
						Integer tipoProceso = _agil.getTipoProceso();
						if (isTipoProcesoValido(tipoProceso)) {
							int tipo = tipoProceso.intValue();
							switch (tipo) {
							case Constantes.TIPO_PROCESO_VENTA_INT:
								operacionOriginalAnulada = BaseDeDatosVenta
										.anuladaTransaccion(_agil
												.getNumtienda().intValue(),
												Control.FECHA_FORMAT
														.format(_agil
																.getFecha()),
												_agil.getNumcaja().intValue(),
												_agil.getNumproceso()
														.intValue(),
												Sesion.VENTA, false);
								break;
							case Constantes.TIPO_PROCESO_ABONO_INT:
								operacionOriginalAnulada = BaseDeDatosServicio
										.anuladoAbono(_agil.getNumtienda()
												.intValue(), _agil.getNumcaja()
												.intValue(), _agil
												.getNumproceso().intValue(),
												Control.FECHA_FORMAT
														.format(_agil
																.getFecha()),
												_agil.getNumservicio()
														.intValue(), false);
								break;
							case Constantes.TIPO_PROCESO_PAGO_TC_EPA_INT:
							/*	BuscarPagoAnuladoDAO pagoAnuladoDAO = new BuscarPagoAnuladoDAO();
								operacionOriginalAnulada = pagoAnuladoDAO
										.buscarPagoAnulado(_agil.getNumtienda()
												.intValue(), _agil.getNumcaja()
												.intValue(), _agil
												.getNumproceso().intValue());
								pagoAnuladoDAO.close();*/
								break;

							}
							if (operacionOriginalAnulada) {
								anular(_agil);
							} else {
								MensajesVentanas
										//.mensajeError("EL PROCESO ASOCIADO (VENTA, ABONO O PAGO DE T. CRED. EPA) NO HA SIDO ANULADO."
										.mensajeError("EL PROCESO ASOCIADO (VENTA O ABONO) NO HA SIDO ANULADO."
												+ SystemUtils.LINE_SEPARATOR
												+ "ANULE LA TRANSACCION ANTES DE EFECTUAR LA OPERACION.");
							}
						} else {
							throw new VPosException(
									"TIPO DE PROCESO NO ES VALIDO "
											//+ "(TIENE QUE SER VENTA, ABONO O PAGO TDC EPA)");
											+ "(TIENE QUE SER VENTA O ABONO)");
						}
					}
				} catch (Throwable e) {
					mostrarMensajeErrorException(e,
							"PROBLEMA ANULANDO OPERACION EL PUNTO AGIL");
				}
			} else {
				MensajesVentanas
						.mensajeError("LA OPERACION ES DE CONSULTA O ANULACION, NO PUEDE SER ANULADA");
			}
		} else {
			MensajesVentanas
					.mensajeError("VALOR DE OPERACION EL PUNTO AGIL NULO");
		}
	}

	/**
	 * @param _agil
	 * @throws VPosException
	 * @throws NoSuchMethodException
	 * @throws Throwable
	 */
	private synchronized void anular(DatosOperacionPuntoAgil _agil)
			throws VPosException, NoSuchMethodException, Throwable {
		ManejoPagosPuntoAgil mp = (ManejoPagosPuntoAgil) ManejoPagosFactory
				.getInstance();

		DatosFormaDePagoPuntoAgil fdp = Constantes.FDP_ANULACIONES;

		String codFdp = _agil.getCodformadepago();
		DatosFormaDePagoPuntoAgil fdpOriginal = (DatosFormaDePagoPuntoAgil) mp
				.cargarFormaDePago(codFdp);

		fdp.setRequiereLecturaBanda(fdpOriginal.isRequiereLecturaBanda());

		DatosExternosPuntoAgil datosExternos = _agil.toDatosExternosAnulacion();
		Date horaInicia = Sesion.getFechaSistema();

		try{
			VPosUniversal vPosUniversal = new VPosUniversal(Constantes.RUTA_CONF_VPOS);
			vPosUniversal.anulacion(_agil.getNumSeq().intValue());
			
			DatosResultadosPuntoAgil resultados = datosTransaccion(vPosUniversal);
			resultados.setHoraInicia(horaInicia);
			resultados.setHoraFinaliza(Sesion.getFechaSistema());

			// GUARDAR RESULTADO EN BASE DE DATOS
			DatosOperacionPuntoAgil datosOperacionPuntoAgil;
			datosOperacionPuntoAgil = new DatosOperacionPuntoAgil(datosExternos, resultados);

			boolean fallaComunicacion1 = StringUtils.isBlank(resultados
					.getCodRespuesta())
					&& Constantes.INTEGER_ZERO.equals(resultados.getNumSeq());
			boolean fallaComunicacion2 = Constantes.COD_RESPUESTA_FALLA_COMUNICACION
					.equalsIgnoreCase(resultados.getCodRespuesta());
			
			String mensaje = resultados.toString();
			if (StringUtils.isBlank(resultados.getMensajeError())) {
				mensaje = mensaje + SystemUtils.LINE_SEPARATOR
						+ SystemUtils.LINE_SEPARATOR + "A continuación se "
						+ SystemUtils.LINE_SEPARATOR + "imprimirá recibo -->";
			}
			
			if (logger.isDebugEnabled()) {
				logger.debug(mensaje);
			}
			//IMPRESION MENSAJE DE RESULTADOS DE MERCHANT
//			MensajesVentanas.aviso(mensaje);
//			MensajesVentanas.aviso(mensaje,true);
//			VentanaEspera.ejecutarEsperar(null,mensaje,null,5000);
			boolean insertado = false;
			if (!fallaComunicacion1){
				insertado = guardarDatosOperacion(datosOperacionPuntoAgil);
			}
			if (!insertado) {
				/*MensajesVentanas
						.mensajeError("NO SE GUARDO RESULTADOS EN BASE DE DATOS");*/
				logger.error("NO SE GUARDO RESULTADOS EN BASE DE DATOS"
						+ datosOperacionPuntoAgil);	
			}
			
			imprimirVoucher(resultados, datosOperacionPuntoAgil, mensaje);
			//SI LA OPERACION NO ES APROBADA
			if (!Constantes.COD_RESPUESTA_APROBADO.equalsIgnoreCase(resultados
					.getCodRespuesta())) {
				if (fallaComunicacion1 || fallaComunicacion2) {
					ManejoPagosPuntoAgil mppa = (ManejoPagosPuntoAgil) ManejoPagosFactory
							.getInstance();
					mppa.setLineaPuntoAgil(false);
					throw new VposUniversalException("OPERACION NO APROBADA: FALLA DE COMUNICACION");
				}
				throw new VposUniversalException("OPERACION NO APROBADA: "
						+ resultados.getCodRespuesta());
			}

			_agil.setStatus(Constantes.OPERACION_ANULADA);
			_agil.cambiarRegActualizado();
	
			mp.actualizarOperacionPuntoAgil(_agil);
			Auditoria.registrarAuditoria("Anulada Operacion de EL PUNTO AGIL: "
					+ _agil.getVtId() + "/" + _agil.getNumSeq(), 'T');
		} catch (VposUniversalException e) {
			mostrarArrojarVPosUniversalException(e, "PROBLEMA PROCESANDO ANULACION");
			logger.error(e,e);
		} catch (BaseDeDatosExcepcion e) {
			logger.error(e,e);
			throw new PagoExcepcion("PROBLEMA PROCESANDO ANULACION");
		} catch (Throwable e) {
			logger.error(e,e);
			throw new PagoExcepcion("PROBLEMA PROCESANDO ANULACION");
		}
	}

	public void datosExternos(String[] arg) throws VPosException {
		// llamadasDatosExternos++;
		// if (llamadasDatosExternos == 1) {
		if (logger.isDebugEnabled()) {
			logger.debug("LLAMANDO A DATOS EXTERNOS[" + /* llamadasDatosExternos */
		"-" + arg.length + "]: " + ArrayUtils.toString(arg));
		}
		if (arg.length == 11) {
			logger.error("***LLAMADA 11: vPos.datosExternos(" + arg[0] + " " + arg[1] + " " + arg[2] + " " + arg[3] + " " + arg[4] + " " + arg[5] + " " +
					arg[6] + " " + arg[7] + " " + arg[8] + " " + arg[9] + " " + arg[10] + ")");
			vPos.datosExternos(arg[0], arg[1], arg[2], arg[3], arg[4], arg[5],
					arg[6], arg[7], arg[8], arg[9], arg[10]);
		} else if (arg.length == 12) {
			logger.error("***LLAMADA 12: vPos.datosExternos(" + arg[0] + " " + arg[1] + " " + arg[2] + " " + arg[3] + " " + arg[4] + " " + arg[5] + " " +
					arg[6] + " " + arg[7] + " " + arg[8] + " " + arg[9] + " " + arg[10] + " " +  arg[11] + ")");
			/*vPos.datosExternos(arg[0], arg[1], arg[2], arg[3], arg[4], arg[5],
					arg[6], arg[7], arg[8], arg[9], arg[10], arg[11]);*/
			
		} else {
			// llamadasDatosExternos =0;
			throw new VPosException(
					"CANTIDAD DE PARAMETROS DE DATOS EXTERNOS INVALIDO");
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug("SALIO DE LLAMADA A DATOS EXTERNOS["
		/* llamadasDatosExternos */+ "-" + arg.length + "]: "
				+ ArrayUtils.toString(arg));
		}
		// llamadasDatosExternos = 0;
		// } else {
		// logger.error("LLAMADA SALTADA A DATOS EXTERNOS["
		// + llamadasDatosExternos + "-" + arg.length + "]: "
		// + ArrayUtils.toString(arg));
		// llamadasDatosExternos = 0;
		//			
		// }

	}
	
	public static boolean isSw() {
		return sw;
	}
	
	public static void setSw(boolean swi) {
		sw = swi;
	}
	
	/**
	 * Método agregado por IROJAS (03/07/2009) para eliminar la dependencia de MERCHANT 
	 * con el Serv de Tda. Ahora se pregunta es por este chequear en PuntoAgil.
	 * Valida que se esté en línea con el servidor Central y no con el de Tda.
	 */
	public static boolean chequearLineaMerchant() {
		if (logger.isDebugEnabled()) {
			logger.debug("chequearLineaMerchant() - start");
		}
		String ipServMerchant = getInstance().getVPos().getSistConfiguracion().getHost();
		int puertoServMerchant = getInstance().getVPos().getSistConfiguracion().getPort();
		if (logger.isDebugEnabled()) {
			logger.debug("chequearLineaMerchant() - end");
		}

		return MediadorBD.isConexion(ipServMerchant,puertoServMerchant);
	}
	
	/**
	 * Arma el pago dependiendo de la respuesta de VPosUniversal
	 * @param formaDePago
	 * @param monto
	 * @param cedula
	 * @return Pago
	 * @throws PagoExcepcion
	 * @throws  
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public Pago tarjetas(FormaDePago formaDePago, double monto, String cedula) throws PagoExcepcion, VposUniversalException{
		DatosPagoPuntoAgil pago =null;
		DatosResultadosPuntoAgil resultados = null;
		VPosUniversal vPosUniversal = null;
		try {
			vPosUniversal = new VPosUniversal(Constantes.RUTA_CONF_VPOS);
			DecimalFormat df = new DecimalFormat("#,##0.00");
			vPosUniversal.tarjeta(df.format(monto)+"", cedula!=null?cedula.substring(2):"");
			
			/**
			 * Transformando el tipo de tarjeta seleccionada a la que realmente se proceso
			 */
			String tipoTarjeta = vPosUniversal.getTipoTarjeta()+"";
			Vector<String> tarjetasCredito = new Vector<String>();
			Vector<String> tarjetasDebito = new Vector<String>();
			Vector<String> chequeServicio = new Vector<String>();
			Vector<String> tarjetaBonoRegalo = new Vector<String>();
			tarjetasCredito.addElement(Constantes.TARJETA_CREDITO);
			tarjetasCredito.addElement(Constantes.TARJETA_VISA_ELECTRON_NO_PIN);
			tarjetasCredito.addElement(Constantes.TARJETA_VISA_ELECTRON_PIN);
			tarjetasDebito.addElement(Constantes.TARJETA_DEBITO);
			chequeServicio.addElement(Constantes.TARJETA_CESTA_TICKET);
			chequeServicio.addElement(Constantes.TARJETA_SODEXHO);
			tarjetaBonoRegalo.addElement(Constantes.TARJETA_BONO_REGALO);
			if(tarjetasDebito.contains(tipoTarjeta)){
				formaDePago = (new ManejoPagosPuntoAgil()).getInstanceBaseDeDatosPago().cargarFormaDePagoPorCodigo(Sesion.FORMA_PAGO_DEBITO);
			} else if(tarjetasCredito.contains(tipoTarjeta)){
				formaDePago = (new ManejoPagosPuntoAgil()).getInstanceBaseDeDatosPago().cargarFormaDePagoPorCodigo(Sesion.FORMA_PAGO_CREDITO);
			} else if(chequeServicio.contains(tipoTarjeta)){
				formaDePago = (new ManejoPagosPuntoAgil()).getInstanceBaseDeDatosPago().cargarFormaDePagoPorCodigo(Sesion.FORMA_PAGO_CHEQUE_SERVICIO);
			} else if(tarjetaBonoRegalo.contains(tipoTarjeta)){
				formaDePago = (new ManejoPagosPuntoAgil()).getInstanceBaseDeDatosPago().cargarFormaDePagoPorCodigo(formaDePago.getCodigo());
			}
			
			//Obtener respuesta de VPosUniversal
			Pago pagoTemp = new Pago(formaDePago,
					monto, 
					"0", 
					vPosUniversal.getNumeroTarjeta().substring(vPosUniversal.getNumeroTarjeta().length()-4, vPosUniversal.getNumeroTarjeta().length()), 
					vPosUniversal.getNumeroCuenta(),
					0, 
					vPosUniversal.getNumeroReferencia()==""?0:Integer.parseInt(vPosUniversal.getNumeroReferencia()), 
					cedula);
			Integer tipoProceso = ((ManejoPagosPuntoAgil) ManejoPagosFactory.getInstance()).
				obtenerTipoProcesoSegunEstadoCaja(Sesion.getCaja().getEstado());
			pago = new DatosPagoPuntoAgil(pagoTemp,tipoProceso);
			DatosTarjetaPuntoAgil datos = new DatosTarjetaPuntoAgil();
			datos.setNumeroTarjeta(vPosUniversal.getNumeroTarjeta());
			//datos.setFechaVencimiento(vposUniversal.);
			datos.setTipoTarjeta(vPosUniversal.getTipoTarjeta());
			//datos.setNombreCliente(vposUniversal.);
			pago.setDatosTarjetaPuntoAgil(datos);
			
			//Finalizar operacion de punto agil
			resultados = datosTransaccion(vPosUniversal);
			pago.setMonto(resultados.getMontoAprobado());
			finalizarOperacionPuntoAgil(pago, 
					null, 
					resultados, 
					pago.getDatosFormaDePagoPuntoAgil().isImprimirVoucher());
		} catch (VposUniversalException e) {
			//IROJAS 18/10/2011: CAMBIO POR FALTA DE IMPRESION DE TICKET DE ERROR POR FALLA DE COMUNICACIÓN 
			try {
				MensajesVentanas.aviso(e.getMessage());
				imprimirArchivo(vPosUniversal.getNombreVoucher(), false);			
        	 } catch (IOException e1) {
					mostrarMensajeErrorException(e, "PROBLEMA IMPRIMIENDO VOUCHER DE EXCEPCION");
        	 }
        	 logger.error(e,e); 
        	 throw  e;
		} catch (BaseDeDatosExcepcion e) {
			logger.error(e,e);
			throw new PagoExcepcion("PROBLEMA PROCESANDO PAGO");
		} catch (NoSuchMethodException e) {
			logger.error(e,e);
			throw new PagoExcepcion("PROBLEMA PROCESANDO PAGO");
		} catch (Throwable e) {
			logger.error(e,e);
			e.printStackTrace();
			throw new PagoExcepcion("PROBLEMA PROCESANDO PAGO");
		}
		
		//Dependiendo de la respuesta imprimir el voucher
		
		return pago;
	}
	
	/**
	 * Metodo para almacenar en base de datos el resultado de la operacion e imprimir
	 * los vouchers necesarios
	 * NOTA: Es una copia de ejecutarVPosDatos con las actualizaciones necesarias para
	 * VPosUniversal.
	 * @param pago
	 * @param datos
	 * @param resultados
	 * @param imprimirVoucher
	 * @return
	 * @throws NoSuchMethodException
	 * @throws Throwable
	 */
	private DatosOperacionPuntoAgil finalizarOperacionPuntoAgil(DatosPagoPuntoAgil pago,
			DatosExternosPuntoAgil datos, DatosResultadosPuntoAgil resultados, boolean imprimirVoucher)
			throws NoSuchMethodException, Throwable {
		
		if (pago != null && datos == null) {
			datos = pago.toDatosExternosPuntoAgil();
		} else if (datos == null) {
			throw new VPosException(
					"DATOS PARA GUARDAR PROCESAR TRANSACCION POR EL PUNTO AGIL INCOMPLETOS");
		}
		
		if (Constantes.CTA_ESPECIAL_PROVIMILLAS.equals(datos
				.getCtasEspeciales())
				&& datos.getPorcentaje() == null) {
			imprimirVoucher = false;
		}
		Date horaInicia = Sesion.getFechaSistema();
		
		/*try{
			ejecutarVPosDatosExternos(params, msgVentanaEspera);
		}catch (Exception e) {
			StringBuffer impresionError= new StringBuffer();
			impresionError.append(Sesion.getTienda().getRazonSocial());
			impresionError.append("\n");
			impresionError.append("RIF :"+Sesion.getTienda().getRif());
			//impresionError.append("\n");
			//impresionError.append(Sesion.getTienda().getDireccion());
			impresionError.append("\n");
			//impresionError.append("TIENDA :BECO "+Sesion.getTienda().getNumero());
			impresionError.append("TIENDA :BECO "+Sesion.getTienda().getNombreSucursal());
			impresionError.append("\n");
			impresionError.append("CAJA :"+Sesion.getCaja().getNumero()+ "			CAJERO: "+Sesion.getUsuarioActivo().getNumFicha());
			impresionError.append("\n");
			impresionError.append("\n");
			if (pago != null) {
				//***** BECO: Eliminado 03/03/2008 por observaciones de certificacion de banco Mercantil
				//impresionError.append("CLIENTE:"+pago.getDatosTarjetaPuntoAgil().getNombreCliente());
				//impresionError.append("\n");
				impresionError.append(pago.getDatosTarjetaPuntoAgil().getNumeroTarjeta());
				impresionError.append("\n");
			}
			impresionError.append("TRANSACCION NO PROCESADA:\n");
			impresionError.append("FALLA COMUNICACION (SEQ "+this.getVPos().getNumSeq()+")\n");
			this.imprimirContenido(impresionError.toString(), false);
			Auditoria.registrarAuditoria("Error de secuencia 0 punto agil de Caja " + Sesion.getCaja().getNumero()+" codigo respuesta="+this.getVPos().getCodRespuesta(),'E'); 
			throw e;
		}*/
		//DatosResultadosPuntoAgil resultados = datosTransaccion();
		resultados.setHoraInicia(horaInicia);
		resultados.setHoraFinaliza(Sesion.getFechaSistema());

		// GUARDAR RESULTADO EN BASE DE DATOS
		DatosOperacionPuntoAgil datosOperacionPuntoAgil;
		if (pago != null) {
			datosOperacionPuntoAgil = new DatosOperacionPuntoAgil(pago,
					resultados);
			if (pago.addDatosOperacionPuntoAgil(datosOperacionPuntoAgil)) {
				// NO HACER NADA
			} else {
				MensajesVentanas
						.mensajeError("NO SE PUDO AGREGAR OPERACION EL PUNTO AGIL A PAGO");
			}
		} else {
			datosOperacionPuntoAgil = new DatosOperacionPuntoAgil(datos,
					resultados);
		}

		boolean fallaComunicacion1 = StringUtils.isBlank(resultados
				.getCodRespuesta())
				&& Constantes.INTEGER_ZERO.equals(resultados.getNumSeq());
		boolean fallaComunicacion2 = Constantes.COD_RESPUESTA_FALLA_COMUNICACION
				.equalsIgnoreCase(resultados.getCodRespuesta());

		
		String mensaje = resultados.toString();
		if (imprimirVoucher
				&& StringUtils.isBlank(resultados.getMensajeError())) {
			mensaje = mensaje + SystemUtils.LINE_SEPARATOR
					+ SystemUtils.LINE_SEPARATOR + "A continuación se "
					+ SystemUtils.LINE_SEPARATOR + "imprimirá recibo -->";
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug(mensaje);
		}
		//IMPRESION MENSAJE DE RESULTADOS DE MERCHANT
		//MensajesVentanas.aviso(mensaje);
		//MensajesVentanas.aviso(mensaje,true);
		//VentanaEspera.ejecutarEsperar(null,mensaje,null,5000);
		boolean insertado = false;
		if (!fallaComunicacion1){
			insertado = guardarDatosOperacion(datosOperacionPuntoAgil);
		}
		if (!insertado) {
			/*MensajesVentanas
					.mensajeError("NO SE GUARDO RESULTADOS EN BASE DE DATOS");*/
			logger.error("NO SE GUARDO RESULTADOS EN BASE DE DATOS"
					+ datosOperacionPuntoAgil);	
		}
		
		if (imprimirVoucher) {
			try {
				imprimirArchivo(resultados.getNombreVoucher(), false);
				if (Constantes.COD_RESPUESTA_APROBADO
						.equalsIgnoreCase(resultados.getCodRespuesta())) {
					//***** 04/01/2008
					//**** Modificacin BECO: Agregada pregunta para no imprimir copias cuando la operacin es de consultas
					//**** Esto porque la nueva versin de MegaVpos no genera el archivo de copia del Cliente y 
					//**** muestra el error de que no encontr el archivo por pantalla
					
					if (datosOperacionPuntoAgil.getTipoProceso().intValue() != 
						Constantes.TIPO_PROCESO_PSEUDO_TRANSACCION_PUNTO_AGIL.intValue()) {
						String fileVoucherCliente = resultados
								.getNombreVoucher().substring(
										0,
										resultados.getNombreVoucher()
												.lastIndexOf("."))
								+ "a.txt";
						imprimirArchivo(fileVoucherCliente, false);
					}
					
//						IMPRESION MENSAJE DE RESULTADOS DE MERCHANT
					MensajesVentanas.aviso(mensaje);
				}
			} catch (IOException e) {
				mostrarMensajeErrorException(e, "PROBLEMA IMPRIMIENDO VOUCHER");
			}
		}
		if (!Constantes.COD_RESPUESTA_APROBADO.equalsIgnoreCase(resultados
				.getCodRespuesta())) {
			if (fallaComunicacion1 || fallaComunicacion2) {
				ManejoPagosPuntoAgil mppa = (ManejoPagosPuntoAgil) ManejoPagosFactory
						.getInstance();
				mppa.setLineaPuntoAgil(false);
				throw new VposUniversalException("OPERACION NO APROBADA: FALLA DE COMUNICACION");
			}
			throw new VposUniversalException("OPERACION NO APROBADA: "
					+ resultados.getCodRespuesta());
		}

		return datosOperacionPuntoAgil;
	}
	
	/**
	 * Obtener operacion de consulta de saldo CARDS
	 * @throws BonoRegaloException 
	 */
	public void obtenerOperacionConsultaCards() throws BonoRegaloException{
		VPosUniversal vPosUniversal = null;
		try {
			vPosUniversal = new VPosUniversal(Constantes.RUTA_CONF_VPOS);
			vPosUniversal.consultas("","");
			imprimirArchivo(vPosUniversal.getNombreVoucher(), false);
		} catch (VposUniversalException e) {
			mostrarArrojarBonoRegaloException(e, "PROBLEMA PROCESANDO CONSULTA DE SALDO");
			logger.error(e,e);
		} catch (Throwable e) {
			logger.error(e,e);
			e.printStackTrace();
			throw new BonoRegaloException("PROBLEMA REALIZANDO CONSULTA");
		}
	}
	
	/**
	 * Obtener operacion de recarga de saldo CARDS
	 * @throws BonoRegaloException 
	 */
	public VPosUniversal obtenerOperacionRecargaSaldoCards(String monto, String cedula) throws BonoRegaloException{
		VPosUniversal vPosUniversal = null;
		try {
			String nroCedula="";
			try {
				 nroCedula = cedula.substring(2);
			} catch(Exception e){
				nroCedula="";
			}
			vPosUniversal = new VPosUniversal(Constantes.RUTA_CONF_VPOS);
			vPosUniversal.consultas(monto,nroCedula);
		} catch (VposUniversalException e) {
			logger.error(e,e);
			throw new BonoRegaloException(e.getMessage());
		} catch (Throwable e) {
			logger.error(e,e);
			e.printStackTrace();
			throw new BonoRegaloException("PROBLEMA EN RECARGA DE SALDO DE BONO REGALO");
		} 
		return vPosUniversal;
	}
	
	/**
	 * Obtener operacion de recarga de saldo CARDS
	 * @throws BonoRegaloException 
	 */
	public VPosUniversal anulacionTransaccionBR(int numseq) throws BonoRegaloException{
		VPosUniversal vPosUniversal = null;
		try {
			
			vPosUniversal = new VPosUniversal(Constantes.RUTA_CONF_VPOS);
			vPosUniversal.anulacion(numseq);
		} catch (VposUniversalException e) {
			logger.error(e,e);
			throw new BonoRegaloException(e.getMessage());
		} catch (Throwable e) {
			logger.error(e,e);
			e.printStackTrace();
			throw new BonoRegaloException("PROBLEMA EN REVERSO DE OPERACIÓN DE BONO REGALO");
		} 
		return vPosUniversal;
	}
}



