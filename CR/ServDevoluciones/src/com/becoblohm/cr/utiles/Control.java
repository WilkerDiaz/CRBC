/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblohm.cr.utiles
 * Programa   : AccesoUsuario.java
 * Creado por : Victor Medina <linux@epa.com.ve>
 * Creado en  : Feb 10, 2004 - 1:51:07 PM
 *
 * (c) Becoblohm, C.A.
 * -----------------------------------------------------------------------------
 * 				Actualizaciones
 * -----------------------------------------------------------------------------
 * Versión     : 1.2.2
 * Fecha       : 01/07/2004 11:19 AM
 * Analista    : Programador3 - Alexis Gueédez López
 * Descripción : - 		* Agregado al método accesoValido la condición que indica 
 * 				que en caso de accesar al sistema para desbloquear la caja esta 
 * 				debe indicar cierre del cajero.
 * =============================================================================
 * Versión     : 1.2.1
 * Fecha       : 26/05/2004 03:40:34 PM
 * Analista    : GMARTINELLI
 * Descripción : Modificado método accesoValido para que permita distinguir entre el 
 * 				desbloqueo de caja y un inicio normal de sesión.
 * =============================================================================
 * Versión     : 1.2 (según CVS)
 * Fecha       : 10/02/2004 10:07 AM
 * Analista    : Programador3 - Alexis Guédez López
 * Descripción : Modificada línea 343, sustituído: 
 * 					String identificador = new String();
 * 				 por:
 * 					String identificador = new String(codigo);
 * =============================================================================
 */
package com.becoblohm.cr.utiles;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.becoblohm.cr.manejadorsesion.Sesion;

/**
 * @author vmedina
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class Control {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(Control.class);
	
//	private static DesbloqueoCaja de = null;

	/**
	 * Indicador de la lectura de un código con formato inválido. 
	 */
	public final static int CODIGO_INVALIDO = 0;
	/**
	 * Indicador de la lectura de un código con formato correspondiente a un producto. 
	 */
	public final static int PRODUCTO = 1;
	/**
	 * Indicador de la lectura de un código con formato correspondiente a un cliente afiliado. 
	 */
	public final static int CLIENTE = 2;
	/**
	 * Indicador de la lectura de un código con formato correspondiente a un colaborador. 
	 */
	public final static int COLABORADOR = 3;
	/**
	 * Indicador de la lectura de un código con formato desconocido. 
	 */
	public final static int CODIGO_DESCONOCIDO = 4;
	/**
	 * Formato indicador de un código de barra perteneciente a un colaborador. 
	 */
	private static int LONGITUD_CODIGO = 12;
	/**
	 * Longitud del formato de código de barra aceptado. 
	 */
	private static int LONGITUD_ID = 8;
	/**
	 * Formato indicador de un código de barra perteneciente a un producto. 
	 */
	private static String FORMATO_PRODUCTO = "21";
	/**
	 * Formato indicador de un código de barra perteneciente a un cliente. 
	 */
	private static String FORMATO_CLIENTE = "22";
	/**
	 * Formato indicador de un código de barra perteneciente a un colaborador. 
	 */
	private static String FORMATO_COLABORADOR = "23";

	/**
	 * Formato para los timestamp 
	 */
	public static SimpleDateFormat formatoTiempo = new SimpleDateFormat("yyyyMMddHHmmss");

	/**
	 * Formato para las fechas 
	 */
	public static SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");

	/**
	 * Formato para la hora 
	 */
	public static SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm:ss");

	/**
	 * Método getCLIENTE.
	 * 	Retorna el valor definido para indicar la lectura de un código con formato correspondiente 
	 * a un cliente. 
	 * @return int - Valor del indicador.
	 */
	public static int getCLIENTE() {
		return CLIENTE;
	}

	/**
	 * Método getCODIGO_DESCONOCIDO
	 * 	Retorna el valor definido para indicar la lectura de un código con formato desconocido. 
	 * @return int - Valor del indicador.
	 */
	public static int getCODIGO_DESCONOCIDO() {
		return CODIGO_DESCONOCIDO;
	}

	/**
	 * Método getCODIGO_INVALIDO
	 * 	Retorna el valor definido para indicar la lectura de un código con formato inválido. 
	 * @return int - Valor del indicador.
	 */
	public static int getCODIGO_INVALIDO() {
		return CODIGO_INVALIDO;
	}

	/**
	 * Método getCOLABORADOR
	 * 	Retorna el valor definido para indicar la lectura de un código con formato correspondiente 
	 * a un colaborador. 
	 * @return int - Valor del indicador.
	 */
	public static int getCOLABORADOR() {
		return COLABORADOR;
	}

	/**
	 * Método getFORMATO_CLIENTE
	 * 	Retorna el valor definido para indicar la lectura de un código con formato correspondiente 
	 * a un cliente. 
	 * @return String - Cadena de caracteres que conforman el formato específico.
	 */
	public static String getFORMATO_CLIENTE() {
		return FORMATO_CLIENTE;
	}

	/**
	 * Método getFORMATO_COLABORADOR
	 * 	Retorna el valor definido para indicar la lectura de un código con formato correspondiente 
	 * a un colaborador. 
	 * @return String - Cadena de caracteres que conforman el formato específico.
	 */
	public static String getFORMATO_COLABORADOR() {
		return FORMATO_COLABORADOR;
	}

	/**
	 * Método getFORMATO_PRODUCTO
	 * 	Retorna el valor definido para indicar la lectura de un código con formato correspondiente 
	 * a un producto. 
	 * @return String - Cadena de caracteres que conforman el formato específico.
	 */
	public static String getFORMATO_PRODUCTO() {
		return FORMATO_PRODUCTO;
	}

	/**
	 * Método getLONGITUD_CODIGO
	 * 	Retorna el valor definido para indicar la longitud del formato para los código de barra 
	 * aceptados. 
	 * @return int - Valor solicitado.
	 */
	public static int getLONGITUD_CODIGO() {
		return LONGITUD_CODIGO;
	}

	/**
	 * Método getLONGITUD_ID
	 * 	Retorna el valor definido para indicar la longitud del identificador del cliente o colaborador
	 * dentro del formato establecido para los código de barra. 
	 * @return int - Valor solicitado.
	 */
	public static int getLONGITUD_ID() {
		return LONGITUD_ID;
	}

	/**
	 * Método getPRODUCTO
	 * 	Retorna el valor definido para indicar la lectura de un código con formato correspondiente 
	 * a un producto. 
	 * @return int - Valor del indicador.
	 */
	public static int getPRODUCTO() {
		return PRODUCTO;
	}

	/**
	 * Método setFORMATO_CLIENTE
	 * 	Establece el valor definido para indicar la lectura de un código con formato correspondiente 
	 * a un cliente. 
	 * @param cadena - Cadena de caracteres que conforman el formato específico.
	 */
	public static void setFORMATO_CLIENTE(String cadena) {
		FORMATO_CLIENTE = cadena;
	}

	/**
	 * Método setFORMATO_COLABORADOR
	 * 	Establece el valor definido para indicar la lectura de un código con formato correspondiente 
	 * a un colaborador. 
	 * @param cadena - Cadena de caracteres que conforman el formato específico.
	 */
	public static void setFORMATO_COLABORADOR(String cadena) {
		FORMATO_COLABORADOR = cadena;
	}

	/**
	 * Método setFORMATO_PRODUCTO
	 * 	Establece el valor definido para indicar la lectura de un código con formato correspondiente 
	 * a un producto. 
	 * @param cadena - Cadena de caracteres que conforman el formato específico.
	 */
	public static void setFORMATO_PRODUCTO(String cadena) {
		FORMATO_PRODUCTO = cadena;
	}

	/**
	 * Método setLONGITUD_CODIGO
	 * 	Establece el valor para indicar la longitud del formato para los código de barra aceptados. 
	 * @param i - Entero que establece el valor.
	 */
	public static void setLONGITUD_CODIGO(int i) {
		LONGITUD_CODIGO = i;
	}

	/**
	 * Método setLONGITUD_ID
	 * 	Establece el valor definido para indicar la longitud del identificador del cliente o colaborador
	 * dentro del formato establecido para los código de barra. 
	 * @param i - Entero que establece el valor.
	 */
	public static void setLONGITUD_ID(int i) {
		LONGITUD_ID = i;
	}

	/**
	 * Método accesoValido.
	 * 	Invoca a la ventana de Login de usuario y valida el acceso para indicar el cambio del usuario
	 * activo de la sesión.
	 * @return boolean - Retorna verdadero en caso que el acceso sea válido.
	 * @throws ExcepcionCr - Arroja una Excepcion de la Caja Registradora en caso de alguna falla 
	 * 			durante la validación del usuario.
	 */
/*	public static boolean accesoValido() throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr {
		return accesoValido(false,false);
	}

	/**
	* Método accesoValido.
	* 	Invoca a la ventana de Login de usuario y valida el acceso para indicar el cambio del usuario
	* activo de la sesión o para cargar el usuario que autoriza una función.
	* @param autorizarFuncion - Verdadero para el caso en que se desea cargar el usuarioAutorizante 
	* 			de la sesión. Falso en el caso de querer cargar el usuarioActivo de la sesión.
	* @return boolean - Retorna verdadero en caso que el acceso sea válido.
	* @throws ExcepcionCr - Arroja una Excepcion de la Caja Registradora en caso de alguna falla 
	* 			durante la validación del usuario.
	*/
/*	public static boolean accesoValido(boolean autorizarFuncion, boolean paraDesbloquear) throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr {
		if (logger.isDebugEnabled()) {
			logger.debug("accesoValido(boolean, boolean) - start");
		}

		Vector valores = new Vector();
		String login = new String();
		String clave = new String();
		String edoAnterior = Sesion.getCaja().getEstado();
		
		if(paraDesbloquear) {
			de = null;
			de = new DesbloqueoCaja();
			MensajesVentanas.centrarVentanaDialogo(de);
			valores = de.getValores();
		} else {
			LoginDeUsuario loginDeUsuario;
			if (MensajesVentanas.ventanaActiva == null)
				loginDeUsuario = new LoginDeUsuario(true);
			else
				loginDeUsuario = new LoginDeUsuario();
			MensajesVentanas.centrarVentanaDialogo(loginDeUsuario);
			valores = loginDeUsuario.getValores();
		}

		if (valores.size() > 0) {
			login = new String((String)valores.get(0));
			clave = new String((String)valores.get(1));
			boolean entradaManual = ((Boolean)valores.get(2)).booleanValue();
			boolean valido = false;
			if (entradaManual){
				MensajesVentanas.aviso("Debe acceder a la aplicación con el código de barra de su carnet");
				valido =  false; // MaquinaDeEstado.validarUsuario(login, clave, entradaManual);
			}
			else if ((login.length() == getLONGITUD_CODIGO()) || (login.length() == getLONGITUD_CODIGO() + 1)) {	
				//if (autorizarFuncion)
					valido = MaquinaDeEstado.validarUsuario(login, autorizarFuncion);
				//else
				//	valido = MaquinaDeEstado.validarUsuario(login);
			}
			if (valido) {
				Usuario xUsuario = new Usuario();
				if (!entradaManual)
					xUsuario.setCodigoBarra(login);
				else
					// Si no, van a entrar el día de mayuya
					return false;
				//else
					// xUsuario.setNumFicha(login);
				

				xUsuario.setClave(clave);
				if (autorizarFuncion)
					Sesion.usuarioAutorizante = (Usuario) MaquinaDeEstado.consultarDatos(xUsuario).get(0);
				else {
					// Chequear la fecha del ultimo reporte Z de la caja
					Calendar fechaReporte = Calendar.getInstance();
					try {fechaReporte.setTime(Sesion.getCaja().getFechaUltRepZ());}catch(Exception e) {}
					fechaReporte.add(Calendar.DATE,1);
				
					Calendar fechaActual = Calendar.getInstance();
					fechaActual.setTime(Sesion.getFechaSistema());
			 
					if(Sesion.getCaja().getFechaUltRepZ()==null || fechaActual.after(fechaReporte)) {
						Sesion.setUsuarioActivo((Usuario) MaquinaDeEstado.consultarDatos(xUsuario).get(0));
						if(paraDesbloquear) MediadorBD.asignarUsuarioLogueado(Sesion.usuarioActivo.getNumFicha(), true);
						else MediadorBD.asignarUsuarioLogueado(Sesion.usuarioActivo.getNumFicha());
						AperturaCajero.imprimirReporte(Sesion.getUsuarioActivo());
					} else {
						Sesion.getCaja().setEstado(edoAnterior);
						throw new SesionExcepcion("Caja Cerrada\nYa se ha emitido el reporte de cierre de caja");
					} 	
				}
			}
			else {
				if (logger.isDebugEnabled()) {
					logger.debug("accesoValido(boolean, boolean) - end");
				}
				return false;
			}
		}else {
			if (logger.isDebugEnabled()) {
				logger.debug("accesoValido(boolean, boolean) - end");
			}
			return false;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("accesoValido(boolean, boolean) - end");
		}
		return true;
	}

	/**
	 * Método codigoValido.
	 * 	Valida el código ingresado por el usuario y retorna un indicador del tipo de código según
	 * lógica de negocio establecida por los parámetros: LONGITUD_CODIGO, FORMATO_COLABORADOR,
	 * FORMATO_PRODUCTO, FORMATO_CLIENTE. 
	 * @param codigo - Código capurado por la GUI del sistema.(Producto, Cliente o Colaborador)
	 * @return ArrayList - Lista que guarda en la posición 1 el indicador del tipo de código,
	 * 			en la posición 2 el valor significativo del código y en la posición 3 un indicador
	 * 			del tipo de captura manual(verdadero/falso). 
	 */
	public static ArrayList codigoValido(String codigo) {
		return codigoValido(codigo, Sesion.capturaTeclado);
	}		
	
	public static ArrayList codigoValido(String codigo, String captura) {
		ArrayList resultado = new ArrayList();
		int tipoCodigo = CODIGO_DESCONOCIDO;
		String identificador = new String(codigo);

		if (codigo.length() > 4){
			if((codigo.length() == LONGITUD_CODIGO)||(codigo.length() == LONGITUD_CODIGO-1)){
				if (codigo.startsWith(FORMATO_COLABORADOR)) tipoCodigo = COLABORADOR;
				else if (codigo.startsWith(FORMATO_CLIENTE)) tipoCodigo = CLIENTE;
				else if (codigo.startsWith(FORMATO_PRODUCTO)) tipoCodigo = PRODUCTO;
				else tipoCodigo = CODIGO_DESCONOCIDO;

				if ((codigo.startsWith(FORMATO_COLABORADOR))||(codigo.startsWith(FORMATO_CLIENTE))){
					identificador = new String(codigo.substring(FORMATO_CLIENTE.length(), LONGITUD_CODIGO-(LONGITUD_CODIGO-FORMATO_CLIENTE.length()-LONGITUD_ID)));
				}
			}
			else tipoCodigo = CODIGO_DESCONOCIDO;
		} 
		resultado.add(0, new Integer(tipoCodigo));
		resultado.add(1, identificador);
		resultado.add(2, captura);
		return resultado;

	}

	/**
	 * Método iniciarCronometro
	 * 	Marca el inicio del contador para el cronómetro
	 * 
	 */ 
	public static long iniciarCronometro(){
		return System.currentTimeMillis();
	}
	
	/**
	 * Método getCronometro
	 * 		Retorna el tiempo en segundos transcurridos desde que se 
	 * inició el contador del cronómetro
	 * 
	 * @return long
	 */
	public static long getCronometro(long inicio){
		return getCronometro(inicio, 0);
	}

	/**
	 * Método getCronometro
	 * 		Retorna el tiempo en segundos transcurridos desde que se 
	 * inició el contador del cronómetro adicionales a un tiempo ya transcurrido.
	 * 
	 * @return long
	 */
	public static long getCronometro(long inicio, long segundos){
		long cronometro = segundos + ((System.currentTimeMillis() - inicio) / 1000);
		return cronometro;
	}

	/**
	 * Método completarCodigo
	 * 
	 * @param codigo
	 * @return String
	 */
	public static String completarCodigo(String codigo){
		int longitud = codigo.length();
		StringBuffer xCodigo = new StringBuffer(codigo);
		if (longitud < Control.getLONGITUD_CODIGO()){
			for (int i=0; i<((Control.getLONGITUD_CODIGO()-Control.getFORMATO_CLIENTE().length())-longitud); i++){
				xCodigo.insert(0,'0');
			}
		} else xCodigo = new StringBuffer(codigo.replace(' ', '0'));
		return xCodigo.toString();
	}

	/**
	 * Método str2Float
	 * 
	 * @param xValor
	 * @return float
	 */
	public static Float str2Float(String xValor){
		StringBuffer valor = new StringBuffer();
		for(int i=0; i<xValor.length(); i++){
			if(xValor.charAt(i) == ',') valor.append('.');
			else if(xValor.charAt(i) == '.') valor.append("");
			else valor.append(xValor.charAt(i));
		}
		return new Float(Float.parseFloat(valor.toString()));
	}	
}