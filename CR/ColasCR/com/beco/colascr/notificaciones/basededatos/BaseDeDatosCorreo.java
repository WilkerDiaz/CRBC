package com.beco.colascr.notificaciones.basededatos;

import java.io.File;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.beco.colascr.notificaciones.controlador.Detalle;
import com.beco.colascr.notificaciones.controlador.OperacionSobreLista;
import com.beco.colascr.notificaciones.controlador.Servicio;
import com.epa.preferencesproxy.EPAPreferenceProxy;
import com.epa.preferencesproxy.NoSuchNodeException;
import com.epa.preferencesproxy.UnidentifiedPreferenceException;

/**
 * Clase que contiene todas las consultas a la base de datos, para realizar 
 * una se crea una conexión, hace la consulta y luego se desconecta,
 * no se mantienen conexiones abiertas.
 * @author Marcos Grillo
 *
 */
public class BaseDeDatosCorreo {
	private static ConectarConBd conexionCr;
	private static DocumentBuilderFactory docbuildfact;
	private static DocumentBuilder docbuild;
	private static String urlDb;
	private static String usuario;
	private static String clave;
	public BaseDeDatosCorreo(){
		
	}
	
	/**
	 * Consulta la base de datos para obtener los apartados que estén próximos 
	 * a vencerse
	 * @return Vector de Servicio con la información de los apartados próximos 
	 * a vencerse 
	 * @throws Exception
	 */
	public static Vector<Servicio> obtenerApartadosVencidos() throws Exception{
		try{
			crearConexionCr();
		}catch(Exception e){
			throw new Exception("No se pudo crear una conexion con la bd de caja: "+e.getMessage());
		}
		Vector<Servicio> result = new Vector<Servicio>();
		String fechaVencimiento = vencimientoApartado();
		String consulta = "select concat(a.nombre,' ',a.apellido) nombreCliente, " +
								"a.email, " +
								"t.nombresucursal, " +
								"t.direccion, " +
								"concat('(',t.codarea,')',t.numtelefono) numtelefono, " +
								"s.lineasfacturacion, " +
								"s.fechavence, " +
								"s.montobase, " +
								"s.montoimpuesto " +
								",s.montobase+s.montoimpuesto montototal, " +
								"s.numservicio " +
								"from servicio s," +
									"afiliado a," +
									"tienda t " +
								"where codtiposervicio = '02' and s.codcliente = a.codafiliado and s.fechavence = '"+fechaVencimiento+"' and s.estadoservicio = 'P'";
		System.out.println("Ejecutando consulta "+consulta);
		ResultSet res = null;
		res = conexionCr.realizarConsulta(consulta);
		System.out.println("Consulta ejecutada");
		res.beforeFirst();
		String [] separarFecha = fechaVencimiento.split("-");
		String fechaV = separarFecha[2]+"-"+separarFecha[1]+"-"+separarFecha[0];
		while(res.next()){
			Servicio serv = new Servicio();
			System.out.println("Obtenido apartado n. "+serv.getNumServicio());
			String correo = res.getString("email");
			if(correo == null)
				continue;
			if(correo.equals(""))
				continue;
			serv.setCorreo(correo);
			serv.setDireccionTienda(res.getString("direccion"));
			serv.setNumTlf(res.getString("numtelefono"));
			serv.setNombreTienda(res.getString("nombresucursal"));
			serv.setNombreCliente(res.getString("nombreCliente"));
			serv.setLineasFacturacion(res.getInt("lineasfacturacion"));
			serv.setFechaServicio(fechaV);
			serv.setMontoBase(res.getDouble("montobase"));
			serv.setMontoImpuesto(res.getDouble("montoimpuesto"));
			serv.setMontoTotal(res.getDouble("montototal"));
			serv.setNumServicio(res.getInt("numservicio"));
			serv.setTipoServicio("02");
			consulta = "select codproducto,cantidad from detalleservicio where numservicio = "+serv.getNumServicio();
			ResultSet productos = conexionCr.realizarConsulta(consulta);
			productos.beforeFirst();
			Vector<Detalle> detalles = new Vector<Detalle>();
			while(productos.next()){
				Detalle d = new Detalle();
				d.setCodigo(productos.getString("codproducto"));
				d.setCantidad(productos.getDouble("cantidad"));
				detalles.add(d);
			}
			productos.close();
			buscarDatosDetalles(detalles);
			serv.setDetalles(detalles);
			
			/**
			 * Jgraterol: Obtengo el saldo abonado al servicio
			 */
			consulta="select sum(monto) montoAbonado from transaccionabono where numservicio="+serv.getNumServicio();
			ResultSet saldoAbonado = conexionCr.realizarConsulta(consulta);
			saldoAbonado.beforeFirst();
			double saldo=0;
			if(saldoAbonado.next()){
				saldo = saldoAbonado.getDouble("montoAbonado");
			}
			saldoAbonado.close();
			serv.setMontoAbonado(saldo);
			serv.setSaldoRestante(serv.getMontoTotal()-serv.getMontoAbonado());
			result.add(serv);
		}
		res.close();
		cerrarConexionCr();
		return result;
	}
	
	/**
	 * Obtiene todas las operaciones de listas del tipo 'V' 'A' 'T' 'L' que se 
	 * hayan realizado el día en que se ejecute la función.
	 * @return Vector de OperacionSobreLista con las operaciones realizadas ese 
	 * dia
	 * @throws Exception
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public static Vector<OperacionSobreLista> obtenerOperacionesDeListas() throws Exception{
		try{
			crearConexionCr();
		}catch(Exception e){
			throw new Exception("No se pudo crear una conexion con la bd de caja: "+e.getMessage());
		}
		Vector<OperacionSobreLista> result = new Vector<OperacionSobreLista>();
		Date fechaActual = new Date();
		String fechaServString = new SimpleDateFormat("yyyy-MM-dd").format(fechaActual);
		String []fechaSplit = fechaServString.split("-");
		String fecha = fechaSplit[2]+"-"+fechaSplit[1]+"-"+fechaSplit[0];
		String consulta = "SELECT concat(a.nombre,' ',a.apellido) nombreCliente,a.email,a.codafiliado,t.nombresucursal,t.direccion,concat('(',t.codarea,')',t.numtelefono) numtelefono,o.nomcliente,o.tipooperacion, o.codproducto,o.montobase monto,o.montoimpuesto,o.dedicatoria,o.codlista,p.descripcionlarga FROM tienda t,operacionlistaregalos o,afiliado a,listaregalos l,producto p  WHERE o.fecha = '"+fechaServString+"' AND o.codlista = l.codlista AND l.codtitular = a.codafiliado AND l.codlista= o.codlista AND l.notificaciones = 'S' AND (o.tipooperacion = 'V' OR o.tipooperacion = 'A'  OR o.tipooperacion = 'T'  OR o.tipooperacion = 'L') AND (p.codproducto = o.codproducto) "+
						"UNION SELECT concat(a.nombre,' ',a.apellido) nombreCliente,a.email,a.codafiliado,t.nombresucursal,t.direccion,concat('(',t.codarea,')',t.numtelefono) numtelefono,o.nomcliente,o.tipooperacion, o.codproducto,o.montobase monto,o.montoimpuesto,o.dedicatoria,o.codlista,o.codproducto descripcionlarga FROM tienda t,operacionlistaregalos o,afiliado a,listaregalos l  WHERE o.fecha = '"+fechaServString+"' AND o.codlista = l.codlista AND l.codtitular = a.codafiliado AND l.codlista= o.codlista AND l.notificaciones = 'S' AND o.tipooperacion = 'L'  order by tipooperacion";
		ResultSet r = conexionCr.realizarConsulta(consulta);
		r.beforeFirst();
		while(r.next()){
			String correo = r.getString("email");
			if(correo == null || correo.equals(""))
				continue;
			OperacionSobreLista o = new OperacionSobreLista();
			o.setNombreProducto(r.getString("descripcionlarga"));
			o.setCorreoCliente(correo);
			o.setDedicatoria(r.getString("dedicatoria"));
			o.setFecha(fecha);
			o.setMontoAbono(r.getDouble("monto"));
			o.setNombreCliente(r.getString("nombreCliente"));
			o.setNombreInvitado(r.getString("nomcliente"));
			o.setTipoOperacion(r.getString("tipooperacion"));
			o.setNumeroLista(r.getInt("codlista"));
			o.setNombreTienda(r.getString("nombresucursal"));
			o.setDireccionTienda(r.getString("direccion"));
			o.setNumTlf(r.getString("numtelefono"));
			result.add(o);
		}
		r.close();
		cerrarConexionCr();
		return result;
	}

	/**
	 * Consulta las operaciones realizadas el dia de la ejecución de la función 
	 * sobre listas garantizadas
	 * @return Vector de OperacionSobreLista
	 * @throws Exception
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public static Vector<OperacionSobreLista> obtenerListasGarantizadas() throws Exception{
		try{
			crearConexionCr();
		}catch(Exception e){
			throw new Exception("No se pudo crear una conexion con la bd de caja: "+e.getMessage());
		}
		Vector<OperacionSobreLista> result = new Vector<OperacionSobreLista>();
		Date fechaActual = new Date();
		String fechaServString = new SimpleDateFormat("yyyy-MM-dd").format(fechaActual);
		String consulta = "select concat(a.nombre,' ',a.apellido) nombreCliente,p.descripcionlarga, o.codproducto, o.numtienda,l.codlista,l.codtitular from afiliado a,operacionlistaregalos o, listaregalos l, tienda t, producto p where l.codtitular = a.codafiliado and o.codlista = l.codlista AND l.estado = 'A' AND l.tipolista = 'G' AND o.tipooperacion = 'V' and o.fecha = '"+fechaServString+"' and t.numtienda != o.numtienda and p.codproducto = o.codproducto order by l.codlista";
		ResultSet r = conexionCr.realizarConsulta(consulta);
		r.beforeFirst();
		while(r.next()){
			OperacionSobreLista o = new OperacionSobreLista();
			o.setNombreProducto(r.getString("descripcionlarga"));
			o.setNumeroTienda(r.getInt("numtienda"));
			o.setCodProducto(r.getString("codproducto"));
			o.setNumeroLista(r.getInt("codlista"));
			o.setNombreCliente(r.getString("nombreCliente"));
			result.add(o);
		}
		r.close();
		cerrarConexionCr();
		return result;
	}
	
	/**
	 * Calcula los impuestos y los precios regulares de los productos dentro del 
	 * vector prods
	 * @param prods vector de productos que DEBEN tener los códigos 
	 * @throws Exception en caso de algun error de consulta
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public static void buscarDatosDetalles(Vector<Detalle> prods) throws Exception{
		int numProds = prods.size();
		String consulta = "";
		ResultSet result = null;
		for(int i=0; i < numProds; ++i){
			Detalle producto = (Detalle)prods.elementAt(i);
			String codigo = producto.getCodigo();
			String consulta2 = "SELECT max(ir.fechaemision) fechaemision FROM producto p,impuestoregion ir,tienda t WHERE ir.codimpuesto = p.codimpuesto AND p.codproducto = '"+codigo+"' AND ir.codregion = t.codregion GROUP by codproducto";
			result = conexionCr.realizarConsulta(consulta2);
			result.beforeFirst();
			if(!result.next())
				throw new Exception("No existe el producto con código: "+codigo);
			String fechaEmision = result.getString("fechaemision");
			consulta = "select ir.porcentaje,p.descripcionlarga,p.descripcioncorta,p.precioregular,(ir.porcentaje/100)*p.precioregular impuesto from producto p,impuestoregion ir,tienda t where ir.codimpuesto = p.codimpuesto and p.codproducto = '"+codigo+"' and ir.codregion = t.codregion and ir.fechaemision = '"+fechaEmision+"'";
			result = conexionCr.realizarConsulta(consulta);
			if(!result.first())
				throw new Exception("El producto con código "+codigo+" no esta en la base de datos");
			producto.setNombre(result.getString("descripcioncorta"));
			producto.setPrecioRegular(result.getDouble("precioregular"));
			producto.setImpuesto(result.getDouble("impuesto"));
			producto.setDescripcionLarga(result.getString("descripcionlarga"));
			producto.setPorcentaje(result.getDouble("porcentaje"));
			result.close();
			result = null;
		}
	}
	
	/**
	 * Inicializa las variables de la clase relacionadas con la creación de 
	 * documentos XML. Es necesario reiniciarlos cuando se va a atender otra 
	 * operacion para evitar que se inserten elementos erroneamente.
	*/
	private static void iniciarDocumentBuilder() throws ParserConfigurationException{
		docbuildfact = DocumentBuilderFactory.newInstance();
		docbuildfact.setNamespaceAware(true);
		docbuildfact.setCoalescing(true);
		docbuildfact.setExpandEntityReferences(true);
		docbuildfact.setIgnoringComments(true);
		docbuildfact.setIgnoringElementContentWhitespace(true);
		docbuild = docbuildfact.newDocumentBuilder();
	}
	
	/**
	 * Calcula la fecha en que se vencerán los apartados
	 * @return String
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se comento código muerto
	* Fecha: agosto 2011
	*/
	private static String vencimientoApartado() throws Exception{
		iniciarDocumentBuilder();
	    File file;
	    file = new File("notificaciones.config");
	    /*if(file == null){
	    	throw new Exception("No se encontro el archivo notificaciones.config");
	    }*/
	    Document doc = docbuild.parse(file);
	    file = null;
	    Node config = doc.getFirstChild();
	    if(!config.getNodeName().equals("configuracion")){
	    	throw new Exception("Archivo de configuracion con formato inadecuado");
	    }
	    Node bdcaja = obtenerHijo(config,"datosConsultas");
	    if(bdcaja == null){
	    	throw new Exception("Archivo de configuracion con formato inadecuado, no tiene el campo <bdcaja>");
	    }
	    String diasVencimiento = obtenerCampo(bdcaja,"diasParaVencimiento");
	    if(diasVencimiento == null)
	    	diasVencimiento = "3";
	    
	    Date fechaActual = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(fechaActual);
		cal.add(Calendar.DATE, Integer.parseInt(diasVencimiento));
		DecimalFormat format =  new DecimalFormat("00");
		
		String fechaVencimiento = cal.get(Calendar.YEAR)+"-"+format.format(cal.get(Calendar.MONTH)+1)+"-"+format.format(cal.get(Calendar.DAY_OF_MONTH));
		return fechaVencimiento;
	}
	/**
	 * Crea una nueva conexión con la base de datos del sistema de caja 
	 * registradora. Busca esta información en el registro
	 * @throws Exception
	 */
	private static void crearConexionCr() throws Exception{
	    if(!cargarPreferencias())
	    	throw new Exception("No se pudo cargar la informacion del registro");
	    System.out.println("dir: "+urlDb+", user: "+usuario+", clave: "+clave);
		conexionCr = new ConectarConBd(urlDb,usuario,clave);
	}
	
	/**
	 * Cierra una conexión con la base de datos del sistema de caja registradora
	 * @throws Exception
	 */
	private static void cerrarConexionCr()throws Exception{
		conexionCr.desconectar();
		conexionCr = null;
	}
	
	/**
	 * Dado un Node busca entre sus hijos el que tenga como nombre campo
	 * @param padre Node donde se empezará la búsqueda 
	 * @param campo nombre del Node que se está buscando
	 * @return Node o null si no se encuentra el campo buscado
	 */
	private static Node obtenerHijo(Node padre, String campo){
		NodeList hijos = padre.getChildNodes();
		Node hijo = null;
		int tam = hijos.getLength();
		for(int i = 0; i < tam; ++i ){
			hijo = hijos.item(i);
			if(hijo.getNodeName().equals(campo)){
				return hijo;
			}
		}
		return null;
	}
	
	/**
	 * Busca entre los hijos del nodo n el elemento con nombre nombreNodo
	 * @param n
	 * @param nombreNodo
	 * @return String
	 */
	private static String obtenerCampo(Node n,String nombreNodo){
		NodeList elementos = n.getChildNodes();
			for(int i = 0; i < elementos.getLength(); ++i){
				Node hijo = elementos.item(i); 
				if(hijo.getNodeName().equals(nombreNodo)){
					return hijo.getFirstChild().getNodeValue();
				}
			}
		return null;
	}
	
	/**
	 * Carga la información para la conexión con la base de datos desde el registro
	 * @return boolean indicando si resultó la carga
	 */
	@SuppressWarnings("unchecked")
	private static boolean cargarPreferencias() {
		EPAPreferenceProxy preferencias = new EPAPreferenceProxy("proyectocr");
		LinkedHashMap<String,Object> preferenciasServ = new LinkedHashMap<String,Object>();
		try {
			preferenciasServ = preferencias.getAllPreferecesForNode("colasCR");
			urlDb = preferenciasServ.get("dbUrlLocal").toString();
			usuario = preferenciasServ.get("dbUsuario").toString();
			clave = preferenciasServ.get("dbClave").toString();
		} catch (NoSuchNodeException e) {
			return false;
		} catch (UnidentifiedPreferenceException e) {
			return false;
		}
		return true;
	}
}
