package com.beco.sinccompieretda.controlador;
/**
 *
 * **/


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.Vector;

import com.beco.sinccompieretda.modelo.CondicionPromocion;
import com.beco.sinccompieretda.modelo.DetallePromocion;
import com.beco.sinccompieretda.modelo.DetallePromocionExt;
import com.beco.sinccompieretda.modelo.Donacion;
import com.beco.sinccompieretda.modelo.Dupla;
import com.beco.sinccompieretda.modelo.Promociones;
import com.beco.sinccompieretda.modelo.PromocionesPorProducto;
import com.beco.sinccompieretda.modelo.transaccionPremControl;



/*
* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
* Sólo se parametrizó el tipo de dato contenido en los 'Vector' y se comentó variable sin uso
* Fecha: agosto 2011
*/
public class ConstructoraSinc {
	
	static ManejadorBDSinc mBD = new ManejadorBDSinc();
	static int promocion=0;
	//static Vector v = new Vector();
	static Vector<Promociones> w = new Vector<Promociones>();
	static Vector<transaccionPremControl> x = new Vector<transaccionPremControl>();
	static Vector<DetallePromocion> y = new Vector<DetallePromocion>();
	static Vector<Donacion> z = new Vector<Donacion>();
	static Vector<DetallePromocionExt> a = new Vector<DetallePromocionExt>();
	static Vector<CondicionPromocion> b = new Vector<CondicionPromocion>();
	static Vector<PromocionesPorProducto> listaPromocionesPorActualizar = new Vector<PromocionesPorProducto>();
	static String promociones = "", detallePromocion = "", donacion = "", detallePromocionExt = "", condicionPromocion = "", transaccionPremControl = "";
	static File archivopromo = null;
	static Vector<Integer> promocionesInactivas = new Vector<Integer>();
	
	/**
	 * Aqui se crea el archivo para posteriormente llenar la tabla PROMOCION de caja 
	 * **/
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	protected void obtenerPromociones(String tienda, String directorio, boolean caso, int codigo, int detalle) throws IOException {

		mBD.conectarOXE();
		try {
			archivopromo = new File(directorio, PrincipalSinc.rutapromociones);  
			// Solo para verifivar
			if (archivopromo.createNewFile())System.out.println("El archivo promociones se ha creado correctamente");
			else System.out.println("No ha podido ser creado el archivo promociones");
			} catch (IOException ioe) {ioe.printStackTrace();}
			try{
				String query="";
				if (detalle==0){
					if (caso)
						query = "SELECT a.xx_promociones_id, a.tipopromocion, a.fechainicio, b.horainicio, a.fechafin" +
							", b.horafin, a.prioridad, a.REGACTUALIZADO, b.REGACTUALIZADO as REGACTUALIZADO, a.isactive" +
							", a.SELECCION, b.tipodedescuento FROM xx_promociones a INNER JOIN xx_combo b ON " +
							"a.xx_promociones_id=b.xx_promociones_id WHERE (a.aprobadoMer like 'Y' or a.aprobadoMar like 'Y' or a.aprobadoGG like 'Y') " +
							"AND (a.REGACTUALIZADO<>a.ISACTIVE)"+
							"union "+
							"SELECT a.xx_promociones_id, a.tipopromocion, a.fechainicio, b.horainicio, a.fechafin, b.horafin, a.prioridad" +
							", a.REGACTUALIZADO, b.REGACTUALIZADO, a.isactive, b.Tipodescuento as SELECCION, null FROM xx_promociones a " +
							"INNER JOIN xx_desctrans b ON a.xx_promociones_id=b.xx_promociones_id WHERE " +
							"(a.aprobadoMer like 'Y' or a.aprobadoMar like 'Y' or a.aprobadoGG like 'Y') AND " +
							"(a.REGACTUALIZADO<>a.ISACTIVE )"+
							"union "+
							"SELECT a.xx_promociones_id, a.tipopromocion, a.fechainicio, b.horainicio , a.fechafin, b.horafin" +
							", a.prioridad, a.REGACTUALIZADO, b.REGACTUALIZADO, a.isactive, null, null FROM xx_promociones a " +
							"INNER JOIN xx_entregables b ON a.xx_promociones_id=b.xx_promociones_id WHERE " +
							"(a.aprobadoMer like 'Y' or a.aprobadoMar like 'Y' or a.aprobadoGG like 'Y') AND (a.REGACTUALIZADO<>a.ISACTIVE)"+
							"union "+
							"SELECT a.xx_promociones_id, a.tipopromocion, a.fechainicio, b.horainicio , a.fechafin, b.horafin" +
							", a.prioridad, a.REGACTUALIZADO, b.REGACTUALIZADO, a.isactive, null, null FROM xx_promociones a " +
							"INNER JOIN xx_promomasivas b ON a.xx_promociones_id=b.xx_promociones_id WHERE " +
							"(a.aprobadoMer like 'Y' or a.aprobadoMar like 'Y' or a.aprobadoGG like 'Y') " +
							"AND (a.REGACTUALIZADO<>a.ISACTIVE )"+
							"union "+
							"SELECT a.xx_promociones_id, a.tipopromocion, a.fechainicio, b.horainicio , a.fechafin, b.horafin, a.prioridad" +
							", a.REGACTUALIZADO, b.REGACTUALIZADO, a.isactive, null, null FROM xx_promociones a INNER JOIN xx_regalopporcompra " +
							"b ON a.xx_promociones_id=b.xx_promociones_id WHERE " +
							"(a.aprobadoMer like 'Y' or a.aprobadoMar like 'Y' or a.aprobadoGG like 'Y') AND (a.REGACTUALIZADO<>a.ISACTIVE)";
					else
						query = "SELECT a.xx_promociones_id, a.tipopromocion, a.fechainicio, b.horainicio, a.fechafin" +
							", b.horafin, a.prioridad, a.REGACTUALIZADO, b.REGACTUALIZADO as REGACTUALIZADO, a.isactive" +
							", a.SELECCION, b.tipodedescuento FROM xx_promociones a INNER JOIN xx_combo b ON " +
							"a.xx_promociones_id=b.xx_promociones_id WHERE (a.aprobadoMer like 'Y' or a.aprobadoMar like 'Y' or a.aprobadoGG like 'Y')" +
							" and a.ISACTIVE='Y' and a.xx_promociones_id="+codigo+
							" union "+
							"SELECT a.xx_promociones_id, a.tipopromocion, a.fechainicio, b.horainicio, a.fechafin, b.horafin, a.prioridad" +
							", a.REGACTUALIZADO, b.REGACTUALIZADO, a.isactive, b.Tipodescuento as SELECCION, null FROM xx_promociones a " +
							"INNER JOIN xx_desctrans b ON a.xx_promociones_id=b.xx_promociones_id WHERE (a.aprobadoMer like 'Y' or a.aprobadoMar like 'Y' or a.aprobadoGG like 'Y') " +
							" and a.ISACTIVE='Y' and a.xx_promociones_id="+codigo+
							" union "+
							"SELECT a.xx_promociones_id, a.tipopromocion, a.fechainicio, b.horainicio , a.fechafin, b.horafin" +
							", a.prioridad, a.REGACTUALIZADO, b.REGACTUALIZADO, a.isactive, null, null FROM xx_promociones a " +
							"INNER JOIN xx_entregables b ON a.xx_promociones_id=b.xx_promociones_id WHERE (a.aprobadoMer like 'Y' or a.aprobadoMar like 'Y' or a.aprobadoGG like 'Y') " +
							" and a.ISACTIVE='Y' and a.xx_promociones_id="+codigo+
							" union "+
							"SELECT a.xx_promociones_id, a.tipopromocion, a.fechainicio, b.horainicio , a.fechafin, b.horafin" +
							", a.prioridad, a.REGACTUALIZADO, b.REGACTUALIZADO, a.isactive, null, null FROM xx_promociones a " +
							"INNER JOIN xx_promomasivas b ON a.xx_promociones_id=b.xx_promociones_id WHERE (a.aprobadoMer like 'Y' or a.aprobadoMar like 'Y' or a.aprobadoGG like 'Y') " +
							" and a.ISACTIVE='Y' and a.xx_promociones_id="+codigo+
							" union "+
							"SELECT a.xx_promociones_id, a.tipopromocion, a.fechainicio, b.horainicio , a.fechafin, b.horafin, a.prioridad" +
							", a.REGACTUALIZADO, b.REGACTUALIZADO, a.isactive, null, null FROM xx_promociones a INNER JOIN xx_regalopporcompra " +
							"b ON a.xx_promociones_id=b.xx_promociones_id WHERE (a.aprobadoMer like 'Y' or a.aprobadoMar like 'Y' or a.aprobadoGG like 'Y') " +
							" and a.ISACTIVE='Y' and a.xx_promociones_id="+codigo;
					}else;
				if(!query.equals("")){
					ResultSet resultado= mBD.realizarConsultaOXE(query);
					resultado.beforeFirst();
					while(resultado.next()){
						int xx_promociones_id = resultado.getInt("xx_promociones_id");
						String tipopromocion = resultado.getString("tipopromocion");
						Date fechainicio = resultado.getDate("fechainicio");
						Time horainicio = resultado.getTime("horainicio"); 
						Date fechafinalizada = resultado.getDate("fechafin");
						Time horafinalizada = resultado.getTime("horafin");
						int prioridad = resultado.getInt("prioridad");
						String activo = resultado.getString("isactive");
						int tipo = resultado.getInt("SELECCION");// esto es solo para tipo de promociones de combo por catidad
						int tipo2 = resultado.getInt("tipodedescuento");
						w.addElement(new Promociones(xx_promociones_id, tipopromocion, fechainicio, horainicio, fechafinalizada
								, horafinalizada, prioridad, activo, tipo, tipo2));
					}
				}
				Iterator<Promociones> i = w.iterator();
				//Se cambia el tipo de promoción
				while(i.hasNext()){
					Promociones p = (Promociones)i.next();
					//Ahorro en compra
					if (p.getTipopromocion().equals("1000100"))	p.setTipopromocion("F");
					//Producto gratis
					else if (p.getTipopromocion().equals("1000200")) p.setTipopromocion("H");
					//Descuento en productos publicados
					else if (p.getTipopromocion().equals("1000300")) p.setTipopromocion("G");
					//1000400 no esta aqui porque es donaciones y no se guarda en la tabla promocion
					/**
					 * Combos por cantidad
					 * */
					//Descuento en el producto  X+1 por categoria (promoción combo cantidades)
//					Descuento en el producto  X+1 por categoria (promoción combo cantidades)
					else if (p.getTipopromocion().equals("1000500") && this.getCriterioSeleccion(p.getXx_promociones_id())==1) p.setTipopromocion("X");
					//Productos gratis al comprar X+1 por categoria (Combo por cantidad)
					else if (p.getTipopromocion().equals("1000600") && this.getCriterioSeleccion(p.getXx_promociones_id())==1) p.setTipopromocion("X");
					//Descuento en el producto  X+1 por departamento (promoción combo cantidades)
					else if (p.getTipopromocion().equals("1000500") && this.getCriterioSeleccion(p.getXx_promociones_id())==2) p.setTipopromocion("S");
					//Productos gratis al comprar X+1 por departamento (Combo por cantidad)
					else if (p.getTipopromocion().equals("1000600") && this.getCriterioSeleccion(p.getXx_promociones_id())==2) p.setTipopromocion("S");
					//Descuento en el producto  X+1 por linea (promoción combo cantidades)
					else if (p.getTipopromocion().equals("1000500") && this.getCriterioSeleccion(p.getXx_promociones_id())==3) p.setTipopromocion("T");
					//Productos gratis al comprar X+1 por linea (Combo por cantidad)
					else if (p.getTipopromocion().equals("1000600") && this.getCriterioSeleccion(p.getXx_promociones_id())==3) p.setTipopromocion("T");
					//Descuento en el producto  X+1 por seccion (promoción combo cantidades)
					else if (p.getTipopromocion().equals("1000500") && this.getCriterioSeleccion(p.getXx_promociones_id())==4) p.setTipopromocion("U");
					//Productos gratis al comprar X+1 por seccion (Combo por cantidad)
					else if (p.getTipopromocion().equals("1000600") && this.getCriterioSeleccion(p.getXx_promociones_id())==4) p.setTipopromocion("U");
					//Descuento en el producto  X+1 por producto (promoción combo cantidades)
					else if (p.getTipopromocion().equals("1000500") && this.getCriterioSeleccion(p.getXx_promociones_id())==5) p.setTipopromocion("I");
					//Productos gratis al comprar X+1 por producto (Combo por cantidad)
					else if (p.getTipopromocion().equals("1000600") && this.getCriterioSeleccion(p.getXx_promociones_id())==5) p.setTipopromocion("I");
					//Descuento en el producto  X+1 por marca (promoción combo cantidades)
					else if (p.getTipopromocion().equals("1000500") && this.getCriterioSeleccion(p.getXx_promociones_id())==5) p.setTipopromocion("V");
					//Productos gratis al comprar X+1 por marca (Combo por cantidad)
					else if (p.getTipopromocion().equals("1000600") && this.getCriterioSeleccion(p.getXx_promociones_id())==5) p.setTipopromocion("V");
					//Descuento en el producto  X+1 por referencia de proveedor (promoción combo cantidades)
					else if (p.getTipopromocion().equals("1000500") && this.getCriterioSeleccion(p.getXx_promociones_id())==5) p.setTipopromocion("W");
					//Productos gratis al comprar X+1 por referencia de proveedor (Combo por cantidad)
					else if (p.getTipopromocion().equals("1000600") && this.getCriterioSeleccion(p.getXx_promociones_id())==5) p.setTipopromocion("W");
					/***/
					// Hora Feliz
					else if (p.getTipopromocion().equals("1000700")) p.setTipopromocion("P");
					// Promocion coorporativa
					else if (p.getTipopromocion().equals("1000750")) p.setTipopromocion("O");
					//calcomania
					else if (p.getTipopromocion().equals("1000800")) p.setTipopromocion("C");
					//transacción premiada
					else if (p.getTipopromocion().equals("1000900")) p.setTipopromocion("A");
					//bono regalo
					else if (p.getTipopromocion().equals("1001000")) p.setTipopromocion("B");
					//cupon por BS
					else if (p.getTipopromocion().equals("1001100") && p.getTipo()==2000000) p.setTipopromocion("K");
					//cupon por %
					else if (p.getTipopromocion().equals("1001100") && p.getTipo()==1000000) p.setTipopromocion("L");
					//producto complementario
					else if (p.getTipopromocion().equals("1001200")) p.setTipopromocion("E");
					// Limites de productos en descuento (descuento en combo)
					else if (p.getTipopromocion().equals("1001300") 
							&& p.getTipo2()==2000000) 
						p.setTipopromocion("G");
					else if (p.getTipopromocion().equals("1001300") 
							&& p.getTipo2()==1000000) 
						p.setTipopromocion("R");
					//promocion masiva
					else if(p.getTipopromocion().equals("1001350")) p.setTipopromocion("P");
					//cupon para sorteo (premio ilusión)
					else if (p.getTipopromocion().equals("1001400")) p.setTipopromocion("D");
	
					if (p.getHorainicio()==null) p.setHorainicio(PrincipalSinc.horaInicio);
					if (p.getHorafinalizada()==null) p.setHorafinalizada(PrincipalSinc.horaFin);
					if (p.getActivo().equals("N")) {
						promocionesInactivas.add(p.getXx_promociones_id());
						//desactivando detalles porque la promocion esta inacctiva
						if (p.getTipopromocion().equals("X") || p.getTipopromocion().equals("S") 
								|| p.getTipopromocion().equals("T") || p.getTipopromocion().equals("U") 
								|| p.getTipopromocion().equals("I") || p.getTipopromocion().equals("V") 
								|| p.getTipopromocion().equals("W") || p.getTipopromocion().equals("G") 
								|| p.getTipopromocion().equals("R"))
							mBD.realizarSentenciaOXE("update XX_COMBO set regactualizado='N' where XX_PROMOCIONES_ID = "
									+p.getXx_promociones_id());
						else if (p.getTipopromocion().equals("P"))
							mBD.realizarSentenciaOXE("update XX_PROMOMASIVAS set regactualizado='N' where XX_PROMOCIONES_ID = "
									+p.getXx_promociones_id());
						else if (p.getTipopromocion().equals("C") || p.getTipopromocion().equals("D") || p.getTipopromocion().equals("B"))
							mBD.realizarSentenciaOXE("update XX_ENTREGABLES set regactualizado='N' where XX_PROMOCIONES_ID = "
									+p.getXx_promociones_id());
						else if (p.getTipopromocion().equals("F") || p.getTipopromocion().equals("J") 
								|| p.getTipopromocion().equals("E"))
							mBD.realizarSentenciaOXE("update XX_REGALOPPORCOMPRA set regactualizado='N' where XX_PROMOCIONES_ID = "
									+p.getXx_promociones_id());
						else if (p.getTipopromocion().equals("H") || p.getTipopromocion().equals("O") 
								|| p.getTipopromocion().equals("A") || p.getTipopromocion().equals("K") 
								|| p.getTipopromocion().equals("L"))
							mBD.realizarSentenciaOXE("update XX_DESCTRANS set regactualizado='N' where XX_PROMOCIONES_ID = "
									+p.getXx_promociones_id());		
					}
					promociones += p.getXx_promociones_id()+"["+p.getTipopromocion()+"["+p.getFechainicio()+"["+p.getHorainicio()
					+"["+p.getFechafinalizada()+"["+p.getHorafinalizada()+"["+p.getPrioridad()+"[\r\n";
				}
				if (archivopromo.exists()){
					BufferedWriter bw;
					if(caso)
						bw = new BufferedWriter(new FileWriter(directorio+PrincipalSinc.rutapromociones));
					else
						bw = new BufferedWriter(new FileWriter(directorio+TransferenciaInmediata.rutapromociones));
					bw.write(promociones);
					bw.close();
					promociones = "";
					w.clear();
				}
			}catch(SQLException e){e.printStackTrace();}
		}
	/**
	 * transaccion premiada
	 * */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector' e 'Iterator'
	* Fecha: agosto 2011
	*/
	protected void transaccionPremControl(String tienda, String directorio, boolean caso, int codigo ) throws IOException {
		// TODO Apéndice de método generado automáticamente
		File archivo = null;
		mBD.conectarOXE();
		try {
			archivo = new File(directorio, PrincipalSinc.rutatransaccionPremControl); 
			if (archivo.createNewFile()) System.out.println("El archivo transaccionPremControl se ha creado correctamente");
			else System.out.println("No ha podido ser creado el archivo transaccionPremControl");
		}catch(IOException ioe){ioe.printStackTrace();}
		try{
			String query;
			if (caso)
				query = "select a.NROTRANSACCIONES, a.NROTRANSACCIONESXDIA, a.MAXPREMBS, a.MAXIPREMBSXDIA, " +
						"a.XX_PROMOCIONES_ID, a.XX_DESCTRANS_ID, a.tienda, a.REGACTUALIZADO from XX_DESCTRANS a " +
						"inner join xx_promociones b on a.xx_promociones_id=B.xx_promociones_id where A.ISACTIVE like 'Y' " +
						"and A.TIPOPROMOCION like '1000900' and A.REGACTUALIZADO='N' AND B.APROBADOMAR='Y'";
			else
				query = "select a.NROTRANSACCIONES, a.NROTRANSACCIONESXDIA, a.MAXPREMBS, a.MAXIPREMBSXDIA, " +
				"a.XX_PROMOCIONES_ID, a.XX_DESCTRANS_ID, a.tienda, a.REGACTUALIZADO from XX_DESCTRANS a " +
				"inner join xx_promociones b on a.xx_promociones_id=B.xx_promociones_id where a.xx_promociones_id="+codigo;
			ResultSet resultado = mBD.realizarConsultaOXE(query);
			resultado.beforeFirst();
			while (resultado.next()){
				codigo = resultado.getInt("XX_PROMOCIONES_ID");
				int detalle = resultado.getInt("XX_DESCTRANS_ID");
				int nrotransacciones = resultado.getInt("NROTRANSACCIONES");
				int nrotransaccionesxdia = resultado.getInt("NROTRANSACCIONESXDIA");
				double maxprembs = resultado.getDouble("MAXPREMBS");
				double maxprembsxdia = resultado.getDouble("MAXIPREMBSXDIA");
				String tiendas = resultado.getString("tienda");
				x.add(new transaccionPremControl(codigo, detalle, nrotransacciones, nrotransaccionesxdia, maxprembs, maxprembsxdia, tiendas));
			}	
		}catch(SQLException e){e.printStackTrace();}
		Iterator<transaccionPremControl> i = x.iterator();
		while (i.hasNext()){
			transaccionPremControl tp = (transaccionPremControl)i.next();
			Iterator<Integer> j = promocionesInactivas.iterator();
			while (j.hasNext())
				if(tp.getCodigo()!=((Integer)j.next()).intValue())
					if (tp.getTienda().equals(tienda)||tp.getTienda().equals("00"));
						transaccionPremControl += tp.getNrotransacciones()+"["+tp.getNrotransaccionesxdia()
						+"["+tp.getMaxprembs()+"["+tp.getMaxprembsxdia()+"["+tp.getCodigo()+"[\r\n";	
		}
		if (archivo.exists()){
			BufferedWriter bw;
			if (caso)
				bw = new BufferedWriter(new FileWriter(directorio+PrincipalSinc.rutatransaccionPremControl));
			else
				bw = new BufferedWriter(new FileWriter(directorio+TransferenciaInmediata.rutatransaccionPremControl));
			bw.write(transaccionPremControl);
			bw.close();
			transaccionPremControl = "";
			x.clear();
		}
	}
	/**
	 * Aqui se crea el archivo para posteriormente llenar la tabla DETALLEPROMOCION de caja 
	 * **/
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Iterator' y 'Vector'
	* Fecha: agosto 2011
	*/
	protected void detallePromocion(String tienda, String directorio, boolean caso, int codigoP) throws IOException {
		File archivo = null;
		int categoria=0, departamento=0, linea=0;
		DecimalFormat relleno = new DecimalFormat("00");
		mBD.conectarOXE();
		try {
			archivo = new File(directorio, PrincipalSinc.rutadetPromociones);
			//Solo para verifivar
			if (archivo.createNewFile())System.out.println("El archivo se ha creado correctamente detPromociones");
			else System.out.println("No ha podido ser creado el archivo detPromociones");
			} catch (IOException ioe) {ioe.printStackTrace();}
		try{
			String query;
			if (caso)
				query = "SELECT a.xx_promociones_id, a.XX_PROMOMASIVAS_ID, b.NAME, a.xx_categoria_id" +
				", (select value from M_PRODUCT where M_product_id = a.xx_departamento_id) as departamento" +
				", (select value from M_PRODUCT where M_product_id = a.xx_linea_id) as linea, a.PORCDESCUENTO, a.TIENDA" +
				", a.REGACTUALIZADO, a.APROBADO, a.ISACTIVE, a.XX_todaLaTienda FROM XX_PROMOMASIVAS a INNER JOIN XX_PROMOCIONES b " +
				"on a.XX_PROMOCIONES_ID=b.XX_PROMOCIONES_ID and " +
				"(b.aprobadoMer like 'Y' or b.aprobadoMar like 'Y' or b.aprobadoGG like 'Y') " +
				"WHERE a.ISACTIVE<>a.REGACTUALIZADO";
			else 
				query = "SELECT a.xx_promociones_id, a.XX_PROMOMASIVAS_ID, b.NAME, a.xx_categoria_id" +
				", (select value from M_PRODUCT where M_product_id = a.xx_departamento_id) as departamento" +
				", (select value from M_PRODUCT where M_product_id = a.xx_linea_id) as linea, a.PORCDESCUENTO, a.TIENDA" +
				", a.REGACTUALIZADO, a.APROBADO, a.ISACTIVE, a.XX_todaLaTienda FROM XX_PROMOMASIVAS a INNER JOIN XX_PROMOCIONES b " +
				"on a.XX_PROMOCIONES_ID=b.XX_PROMOCIONES_ID and " +
				"(b.aprobadoMer like 'Y' or b.aprobadoMar like 'Y' or b.aprobadoGG like 'Y') " +
				"WHERE b.xx_promociones_id="+codigoP;
			ResultSet resultado = mBD.realizarConsultaOXE(query);
			resultado.beforeFirst();
			while (resultado.next()){
				int codigo = resultado.getInt("xx_promociones_id");
				int detalle = resultado.getInt("XX_PROMOMASIVAS_ID");
				String name = resultado.getString("NAME");
				categoria = resultado.getInt("xx_categoria_id");
				departamento = resultado.getInt("departamento")%100;
				linea = resultado.getInt("linea")%100;
				double porcdescuento = resultado.getDouble("PORCDESCUENTO");
				String tiendas = resultado.getString("TIENDA");
				String aprobado = resultado.getString("APROBADO");
				String activo = resultado.getString("ISACTIVE");
				String todaLaTienda = resultado.getString("XX_todaLaTienda");
				name=limpiarNombre(name);
				y.add(new DetallePromocion(codigo, detalle, name, categoria, departamento, linea, porcdescuento, tiendas, aprobado, activo, todaLaTienda));
			}
		}catch(SQLException e){e.printStackTrace();}
		Iterator<DetallePromocion> i = y.iterator();
		int n=1;
		while (i.hasNext()){
			DetallePromocion dp = (DetallePromocion)i.next();
			Iterator<Integer> j = promocionesInactivas.iterator();
			while (j.hasNext()){
				if(dp.getCodigo()==((Integer)j.next()).intValue())
					dp.setActivo("N");
			}
			if (dp.getTodaLaTienda().equals("Y")){
				Vector<Integer> departamentosDeCategoria = new Vector<Integer>();	
				String query = "select value from M_PRODUCT where XX_tipoproducto=2000000 and isactive='Y'";
				try {
					ResultSet resultado = mBD.realizarConsultaOXE(query);
					while (resultado.next()) departamentosDeCategoria.add(new Integer(resultado.getInt("value")%100));
				} catch (SQLException e) {e.printStackTrace();}
				if (dp.getTienda().equals(tienda)||dp.getTienda().equals("00")){
					Iterator<Integer> k = departamentosDeCategoria.iterator();
					while (k.hasNext())
						detallePromocion += dp.getCodigo()+"["+n+++"[["+relleno.format(((Integer)k.next()).intValue())+"[\\N[\\N["+ dp.getPorcdescuento()+
						"[0.00["+dp.getActivo()+"\r\n";
				}
			}else{
				if (dp.getCategoria()!=0 && dp.getDepartamento()==0 && dp.getLinea()==0){
					Vector<Integer> departamentosDeCategoria = new Vector<Integer>();
					String query = "select value from M_product where value like " +
							"(select value from M_PRODUCT where M_product_id = "+dp.getCategoria()+")||'__'";
					try {
						ResultSet resultado = mBD.realizarConsultaOXE(query);
						while (resultado.next()) departamentosDeCategoria.add(new Integer(resultado.getInt("value")%100));
					} catch (SQLException e) {e.printStackTrace();}
					if (dp.getTienda().equals(tienda)||dp.getTienda().equals("00")){
						Iterator<Integer> k = departamentosDeCategoria.iterator();
						while (k.hasNext())
							detallePromocion += dp.getCodigo()+"["+n+++"[["+relleno.format(((Integer)k.next()).intValue())+"[\\N[\\N["+ dp.getPorcdescuento()+
							"[0.00["+dp.getActivo()+"\r\n";
					}
				}else{
					if (dp.getTienda().equals(tienda)||dp.getTienda().equals("00"))
						detallePromocion += dp.getCodigo()+"["+n+++"[["+ relleno.format(dp.getDepartamento())
						+"["+ ((dp.getLinea()==0)?"\\N":relleno.format(dp.getLinea()))+"[\\N["+ dp.getPorcdescuento()+"[0.00["+dp.getActivo()+"[\r\n";
				}
			}
		}
		if (archivo.exists()){
			BufferedWriter bw;
			if (caso)
				bw = new BufferedWriter(new FileWriter(directorio+PrincipalSinc.rutadetPromociones));
			else 
				bw = new BufferedWriter(new FileWriter(directorio+TransferenciaInmediata.rutadetPromociones));
			bw.write(detallePromocion);
			bw.close();
			detallePromocion = "";
			y.clear();
		}
		mBD.desconectarOXE();
	}
	/**
	 * Aqui se crea el archivo para posteriormente llenar la tabla DONACION de caja 
	 * **/
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector' e 'Iterator'
	* Fecha: agosto 2011
	*/
	protected void donacion(String tienda, String directorio, boolean caso, int codigoP) throws IOException {
		File archivo = null;
		mBD.conectarOXE();
		try {
			archivo = new File(directorio, PrincipalSinc.rutadonacion);
			//Solo para verifivar
			if (archivo.createNewFile()) System.out.println("El archivo donacion se ha creado correctamente");
			else System.out.println("No ha podido ser creado el archivo donacion");
			} catch (IOException ioe) {ioe.printStackTrace();}
		try{
			String query;
			if(caso)
				query= "SELECT a.xx_promociones_id, a.XX_DONACION_ID, b.NAME, a.TIENDA, a.TIPODONACION" +
					", a.MONTODONACION, a.REGACTUALIZADO, a.ISACTIVE, b.ISACTIVE as activo, b.fechainicio, b.FECHAFIN " +
					", a.MostrarAlTotalizar FROM XX_DONACION a inner join XX_PROMOCIONES b on a.XX_PROMOCIONES_ID = b.XX_PROMOCIONES_ID " +
					"and a.aprobadoMar like 'Y' and b.aprobadoGG like 'Y' WHERE ((a.REGACTUALIZADO='N' and a.ISACTIVE='Y') " +
					"or (a.REGACTUALIZADO='Y' and a.ISACTIVE='N')) or ((b.REGACTUALIZADO='N' and b.ISACTIVE='Y') " +
					"or (b.REGACTUALIZADO='Y' and b.ISACTIVE='N'))";
			else
				query= "SELECT a.xx_promociones_id, a.XX_DONACION_ID, b.NAME, a.TIENDA, a.TIPODONACION" +
				", a.MONTODONACION, a.REGACTUALIZADO, a.ISACTIVE, b.ISACTIVE as activo, b.fechainicio, b.FECHAFIN " +
				", a.MostrarAlTotalizar FROM XX_DONACION a inner join XX_PROMOCIONES b on a.XX_PROMOCIONES_ID = b.XX_PROMOCIONES_ID " +
				"and a.aprobadoMar like 'Y' and b.aprobadoGG like 'Y' WHERE b.xx_promociones_id="+codigoP;
			ResultSet resultado = mBD.realizarConsultaOXE(query);
			resultado.beforeFirst();
			while (resultado.next()){
				int codigo = resultado.getInt("xx_promociones_id");
				int detalle = resultado.getInt("XX_DONACION_ID");
				String name = resultado.getString("NAME");
				String tiendas = resultado.getString("TIENDA");
				String tipodonacion = resultado.getString("TIPODONACION");
				double montodonacion = resultado.getDouble("MONTODONACION");
				String activo = resultado.getString("ISACTIVE");
				String activo1 = resultado.getString("activo");
				Date fechainicio = resultado.getDate("fechainicio");
				Date fechafin = resultado.getDate("FECHAFIN");
				String alTotalizar = resultado.getString("MostrarAlTotalizar");
				z.add(new Donacion(codigo, detalle, name, tipodonacion, montodonacion, tiendas
						, activo, activo1, fechainicio, fechafin, alTotalizar));
			}
		}catch(SQLException e){e.printStackTrace();}
		Iterator<Donacion> i = z.iterator();
		while (i.hasNext()){
			Donacion d = (Donacion)i.next();
			if (d.getTienda().equals(tienda)||d.getTienda().equals("00"))
				donacion += d.getCodigo()+"["+d.getDetalle()+"["+d.getFechainicio()+"["+d.getFechafin()+"["+d.getName()+"["+d.getName()+"["+
				d.getTipodonacion()+"["+d.getActivo()*d.getActivo1()+"["+d.getMontodonacion()+"[N["+d.getAlTotalizar()+"[\r\n";
		}
		if (archivo.exists()){
			BufferedWriter bw;
			if (caso)
				bw = new BufferedWriter(new FileWriter(directorio+PrincipalSinc.rutadonacion));
			else
				bw = new BufferedWriter(new FileWriter(directorio+TransferenciaInmediata.rutadonacion));
			bw.write(donacion);
			bw.close();
			donacion = "";
			z.clear();
		}
	}
	/**
	 * Aqui se crea el archivo para posteriormente llenar la tabla DETALLEPROMOCIONeXT de caja 
	 * @throws SQLException 
	 * **/
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Iterator'
	* Fecha: agosto 2011
	*/
	protected void detallePromocionExt(String tienda, String directorio, boolean caso, int codigoP, int detalle) throws SQLException {
		File archivo = null;
		DecimalFormat relleno = new DecimalFormat("00");
		mBD.conectarOXE();
		int n=0;
		try {
			archivo = new File(directorio, PrincipalSinc.rutadetPromocionesExt); 
			 //Solo para verifivar
			if (archivo.createNewFile())System.out.println("El archivo detPromocionesExt se ha creado correctamente");
			else System.out.println("No ha podido ser creado el archivo detPromocionesExt");
			} catch (IOException ioe) {ioe.printStackTrace();}
	//Para sincornizar codigos de productos para los combo
			if(caso){
				Vector<Promociones> promociones = new Vector<Promociones>();		
				String query="select a.xx_promociones_id, a.tipopromocion " +
						" from xx_combo a inner join xx_promociones b on a.xx_promociones_id=b.xx_promociones_id" +
						" where (APROBADOMER like 'Y' OR APROBADOMAR like 'Y' OR APROBADOGG like 'Y') AND a.isactive<>a.REGACTUALIZADO" +
						" union" +
						" select a.xx_promociones_id, a.tipopromocion" +
						" from XX_DESCTRANS a inner join xx_promociones b on a.xx_promociones_id=b.xx_promociones_id" +
						" where (APROBADOMER like 'Y' OR APROBADOMAR like 'Y' OR APROBADOGG like 'Y') AND a.isactive<>a.REGACTUALIZADO" +
						" union" +
						" select a.xx_promociones_id, a.tipopromocion" +
						" from XX_ENTREGABLES a inner join xx_promociones b on a.xx_promociones_id=b.xx_promociones_id" +
						" where (APROBADOMER like 'Y' OR APROBADOMAR like 'Y' OR APROBADOGG like 'Y') AND a.isactive<>a.REGACTUALIZADO" +
						" union" +
						" select a.xx_promociones_id, a.tipopromocion" +
						" from XX_REGALOPPORCOMPRA a inner join xx_promociones b on a.xx_promociones_id=b.xx_promociones_id" +
						" where (APROBADOMER like 'Y' OR APROBADOMAR like 'Y' OR APROBADOGG like 'Y') AND a.isactive<>a.REGACTUALIZADO";
				Statement sentenciaOXE=mBD.getStatement();
				ResultSet resultado= mBD.realizarConsultaOXE(query, sentenciaOXE);
				resultado.beforeFirst();
				while(resultado.next()){	
					try{
						int codigo = resultado.getInt("XX_PROMOCIONES_ID");
						String tipo = resultado.getString("tipopromocion");
						promociones.add(new Promociones(codigo, tipo));
					}catch(ArithmeticException f){f.printStackTrace();}
				}
				resultado.close();
				sentenciaOXE.close();
				for (int i=0; i<promociones.size();i++){
					if (Integer.parseInt(((Promociones)promociones.elementAt(i)).getTipopromocion())==1000100
						||Integer.parseInt(((Promociones)promociones.elementAt(i)).getTipopromocion())==1000300 
						||Integer.parseInt(((Promociones)promociones.elementAt(i)).getTipopromocion())==1001200){
						query="SELECT a.XX_PROMOCIONES_ID, a.XX_REGALOPPORCOMPRA_ID, a.PorcDescuento" +
							", (select value from M_PRODUCT where M_product_id = a.xx_categoria_id) as categoria" +
							", (select value from M_PRODUCT where M_product_id = a.xx_departamento_id) as departamento" +
							", (select value from M_PRODUCT where M_product_id = a.xx_linea_id) as linea" +
							", a.XX_MONTOMINIMO, a.CANTPRODCOMPRAR, b.VALUE, c.NAME" +
							", (select value from M_PRODUCT where M_product_id = a.xx_seccion_id) as seccion" +
							", a.TIENDA, a.ISACTIVE, a.TipoPromocion, a.Regalo, a.REGACTUALIZADO, XX_ACUMULAPREMIO  " +
							"FROM M_PRODUCT b INNER JOIN XX_REGALOPPORCOMPRA a on  b.xx_producto_id = a.xx_producto_id and " +
							"b.ISSUMMARY like 'N' and b.ISACTIVE like 'Y' inner join XX_PROMOCIONES c on a.XX_PROMOCIONES_ID = " +
							"c.XX_PROMOCIONES_ID WHERE (c.APROBADOMER like 'Y' OR c.APROBADOMAR like 'Y' OR c.APROBADOGG like 'Y') " +
							"AND a.isactive<>a.REGACTUALIZADO and xx_promociones_id="+promociones.elementAt(i).getXx_promociones_id();
						sentenciaOXE=mBD.getStatement();
						resultado= mBD.realizarConsultaOXE(query, sentenciaOXE);
						resultado.beforeFirst();
						while(resultado.next()){	
							try{
								int codigo = resultado.getInt("XX_PROMOCIONES_ID");
								int detalleC = resultado.getInt("XX_REGALOPPORCOMPRA_ID");
								double porcdescuento = resultado.getDouble("PORCDESCUENTO");
								int categoria = resultado.getInt("categoria");
								int departamento = resultado.getInt("departamento")%100;
								int linea = resultado.getInt("linea")%100;
								int seccion = resultado.getInt("seccion")%100;
								double montominimo = resultado.getDouble("XX_MONTOMINIMO");
								int cantidadproducto = resultado.getInt("CANTPRODCOMPRAR");
								String producto = resultado.getString("VALUE");
								String name = resultado.getString("NAME");
								String tiendas = resultado.getString("TIENDA");
								String activo = resultado.getString("isactive");
								int tipoPromocion = resultado.getInt("TipoPromocion");
								String regalo = resultado.getString("Regalo");
								String regactualizado = resultado.getString("REGACTUALIZADO");
								String acumulado =resultado.getString("XX_ACUMULAPREMIO");
								name=limpiarNombre(name);
								a.add(new DetallePromocionExt(codigo, detalleC, porcdescuento, categoria, departamento, null, null
										, linea, seccion, montominimo, cantidadproducto, producto, 0, 0, name, 
										1, tiendas, activo, tipoPromocion, regalo, regactualizado, acumulado));
							}catch(ArithmeticException f){f.printStackTrace();}
						}
						resultado.close();
						sentenciaOXE.close();
	
						resultado.beforeFirst();
						Iterator<DetallePromocionExt> iterator = a.iterator();
						while (iterator.hasNext()){
							DetallePromocionExt dpe = (DetallePromocionExt)iterator.next();
							Iterator<Integer> j = promocionesInactivas.iterator();
							while (j.hasNext()){
								int x = ((Integer)j.next()).intValue();
								if(dpe.getCodigo()==x)
									dpe.setActivo("N");
								mBD.realizarSentenciaOXE("update XX_PromProductoDetalleCaja set regactualizado='Y', isactive='N' where XX_PROMOCIONES_ID = "+x);
							}
							try{
								sentenciaOXE=mBD.getStatement();
								ResultSet c =mBD.realizarConsultaOXE("select max(detallepromocion) as max from XX_PromProductoDetalleCaja where isactive='Y' and xx_promociones_id = "+dpe.getCodigo(), sentenciaOXE);
								c.beforeFirst();
								if (c.first()) 
									n = c.getInt("max");
								c.close();
								sentenciaOXE.close();
							}catch(Exception e){n=0;}
							if (!dpe.getRegactualizado().equals(dpe.getActivo())){
								if (dpe.getTienda().equals(tienda)||dpe.getTienda().equals("00")){
									n++;
									String sentencia="insert into XX_PromProductoDetalleCaja (CREATED, CREATEDBY, UPDATED, UPDATEDBY" +
									", AD_CLIENT_ID, AD_ORG_ID, XX_PROMOCIONES_ID, DETALLEPROMOCION, PORCDESCUENTO, CATEGORIA, DEPARTAMENTO" +
									", XX_MARCA, LINEA, XX_REFPROV, MONTOPREMIADO, CANTIDADPRODUCTO, NROPAGAR, VALUE, BSBONOREGALO, NAME" +
									", GRUPO, SECCION, TIENDA, ISACTIVE, REGALO, regactualizado, XX_ACUMULAPREMIO) " +
									"SELECT  a.CREATED, a.CREATEDBY, a.UPDATED, a.UPDATEDBY, a.AD_CLIENT_ID, a.AD_ORG_ID" +
									", a.XX_PROMOCIONES_ID, " +n+", a.PorcDescuento, (select value from M_PRODUCT where M_product_id = a.xx_categoria_id) " +
									"as categoria, (select value from M_PRODUCT where M_product_id = a.xx_departamento_id) as departamento, null" +
									", (select value from M_PRODUCT where M_product_id = a.xx_linea_id) as linea, null, a.XX_MONTOMINIMO" +
									", a.CANTPRODCOMPRAR, null, b.VALUE, NULL, c.NAME, 1, (select value from M_PRODUCT where M_product_id " +
									"= a.xx_seccion_id) as seccion, a.TIENDA, '"+dpe.getActivo1()+"', a.Regalo, '"+dpe.getRegactualizado()+"', a.XX_ACUMULAPREMIO FROM M_PRODUCT b INNER JOIN " +
									"XX_REGALOPPORCOMPRA a on  b.xx_producto_id = a.xx_producto_id and b.ISSUMMARY like 'N' and b.ISACTIVE like 'Y' " +
									"inner join XX_PROMOCIONES c on a.XX_PROMOCIONES_ID = c.XX_PROMOCIONES_ID AND c.xx_promociones_id = "+
									dpe.getCodigo()+" AND b.VALUE like '" +dpe.getProducto()+"' and a.regactualizado<>a.isactive and (b.VALUE not in " +
									"(Select VALUE from XX_PROMPRODUCTODETALLECAJA where xx_promociones_id='"+dpe.getCodigo()+"' and regactualizado<>isactive))";
									try{
										mBD.realizarSentenciaOXE(sentencia);
									}catch(SQLException e){;}
									Statement sentenciaOXE1=mBD.getStatement();
									resultado = mBD.realizarConsultaOXE("select * from XX_PromProductoDetalleCaja where " +
											"regactualizado<>isactive and xx_promociones_id = "+dpe.getCodigo()+" AND VALUE like '" +dpe.getProducto()+"' and TIENDA='"+dpe.getTienda()+
											"' and value = '"+dpe.getProducto()+"'", sentenciaOXE1);	
									while(resultado.next()){
										try{
											int codigo = resultado.getInt("XX_PROMOCIONES_ID");
											int detalleC = resultado.getInt("DETALLEPROMOCION");
											double porcdescuento = resultado.getDouble("PORCDESCUENTO");
											int categoria = resultado.getInt("categoria");
											int departamento = resultado.getInt("departamento")%100;
											String marca = resultado.getString("XX_MARCA");
											String refproveedor = resultado.getString("XX_REFPROV");
											int linea = resultado.getInt("linea")%100;
											int seccion = resultado.getInt("seccion")%100;
											double montominimo = resultado.getDouble("montopremiado");
											int cantidadproducto = resultado.getInt("CANTIDADPRODUCTO");
											String producto = resultado.getString("VALUE");
											int cantproddesc = resultado.getInt("NROPAGAR");
											double bsbonoregalo = resultado.getDouble("BSBONOREGALO");
											String name = resultado.getString("NAME");
											int grupo =resultado.getInt("grupo");
											String acumular = resultado.getString("XX_acumulaPremio");
											name=limpiarNombre(name);
											detallePromocionExt += codigo+"["+detalleC+"["+porcdescuento+"["+categoria+"["+relleno.format(departamento)
											+"["+((marca==null)?"NULL":marca)+"["+relleno.format(linea)+"["+((refproveedor==null)?"NULL":refproveedor)
											+"["+montominimo+"["+cantidadproducto+"["+cantproddesc+"["+((producto==null)?"NULL":producto)
											+"["+bsbonoregalo+"["+dpe.getActivo()+"["+name+"["+grupo+"["+seccion+"["+acumular+"[\r\n";
											listaPromocionesPorActualizar.add(new PromocionesPorProducto(dpe.getCodigo(), detalleC, true));

										}catch(ArithmeticException f){f.printStackTrace();}
									}
									resultado.close();
									sentenciaOXE1.close();

								}
							}
						}
					}else if(Integer.parseInt(((Promociones)promociones.elementAt(i)).getTipopromocion())==1000200
							|| Integer.parseInt(((Promociones)promociones.elementAt(i)).getTipopromocion())==1000750
							|| Integer.parseInt(((Promociones)promociones.elementAt(i)).getTipopromocion())==1000900
							|| Integer.parseInt(((Promociones)promociones.elementAt(i)).getTipopromocion())==1001100){
						query="SELECT a.XX_PROMOCIONES_ID, a.xx_DESCTRANS_id, a.PORCDESCUENTO, null, null, null, null, null, a.MONTOMINIMO" +
								",a.CantidadProducto, b.NAME, a.TIENDA, a.ISACTIVE, a.REGACTUALIZADO  FROM XX_DESCTRANS a inner join " +
								"XX_PROMOCIONES b on a.XX_PROMOCIONES_ID = b.XX_PROMOCIONES_ID WHERE " +
								"(b.APROBADOMER like 'Y' OR b.APROBADOMAR like 'Y' OR b.APROBADOGG like 'Y') AND a.isactive<>a.REGACTUALIZADO" +
								" and a.xx_promociones_id="+promociones.elementAt(i).getXx_promociones_id();
						sentenciaOXE=mBD.getStatement();
						resultado= mBD.realizarConsultaOXE(query, sentenciaOXE);
						resultado.beforeFirst();
						while(resultado.next()){	
							try{
								int codigo = resultado.getInt("XX_PROMOCIONES_ID");
								int detalleC = resultado.getInt("xx_DESCTRANS_id");
								double porcdescuento = resultado.getDouble("PORCDESCUENTO");
								double montominimo = resultado.getDouble("MONTOMINIMO");
								int cantidadproducto = resultado.getInt("CantidadProducto");
								String name = resultado.getString("NAME");
								String tiendas = resultado.getString("TIENDA");
								String activo = resultado.getString("ISACTIVE");
								String regactualizado = resultado.getString("REGACTUALIZADO");
								name=limpiarNombre(name);
								a.add(new DetallePromocionExt(codigo, detalleC, porcdescuento, 0, 0, null, null
										, 0, 0, montominimo, cantidadproducto, null, 0, 0, name, 1, tiendas, activo
										, Integer.parseInt(((Promociones)promociones.elementAt(i)).getTipopromocion())
										, null, regactualizado, null));
							}catch(ArithmeticException f){f.printStackTrace();}
						}
						resultado.close();
						sentenciaOXE.close();
						Iterator<DetallePromocionExt> it = a.iterator();
						while (it.hasNext()){
							n++;
							DetallePromocionExt dpe = (DetallePromocionExt)it.next();
							detallePromocionExt += dpe.getCodigo()+"["+n+"["+dpe.getPorcdescuento()+"["+dpe.getCategoria()
							+"["+relleno.format(dpe.getDepartamento())+"["+((dpe.getMarca()==null)?"NULL":dpe.getMarca())
							+"["+relleno.format(dpe.getLinea())+"["+((dpe.getRefproveedor()==null)?"NULL":dpe.getRefproveedor())
							+"["+dpe.getMontominimo()+"["+dpe.getCantidadproducto()
							+"["+dpe.getCantproddesc()+"["+((dpe.getProducto()==null)?"NULL":dpe.getProducto())+"["+dpe.getBsbonoregalo()
							+"["+dpe.getActivo()+"["+dpe.getName()+"["+dpe.getGrupo()+"["+relleno.format(dpe.getSeccion())+"[NULL[\r\n";
						}
				
						
					} else if (Integer.parseInt(((Promociones)promociones.elementAt(i)).getTipopromocion())==1000500
								|| Integer.parseInt(((Promociones)promociones.elementAt(i)).getTipopromocion())==1000600
								|| Integer.parseInt(((Promociones)promociones.elementAt(i)).getTipopromocion())==1001300){
							
						int seleccion=getCriterioSeleccion(((Promociones)promociones.elementAt(i)).getXx_promociones_id());
						switch (seleccion){
							case 1: //Categorias
								query =	"SELECT a.XX_PROMOCIONES_ID, a.XX_COMBO_ID, a.PORCDESCUENTO" +
										", (select value from M_PRODUCT where M_product_id = a.xx_categoria_id) as categoria" +
										", null as departamento, null as linea, null as seccion, null as producto, null as referencia, null as marca" +
										", a.CANTIDADPRODUCTO, a.NROPAGAR, c.NAME, a.grupo, a.TIENDA, a.ISACTIVE, a.REGACTUALIZADO  " +
										"FROM XX_COMBO a inner join XX_PROMOCIONES c on a.XX_PROMOCIONES_ID = c.XX_PROMOCIONES_ID " +
										"WHERE (c.APROBADOMER like 'Y' OR c.APROBADOMAR like 'Y' OR c.APROBADOGG like 'Y') AND " +
										"a.isactive<>a.REGACTUALIZADO and a.xx_promociones_id="+
										((Promociones)promociones.elementAt(i)).getXx_promociones_id();
								break;
							case 2: // Departamentos
								query =	"SELECT a.XX_PROMOCIONES_ID, a.XX_COMBO_ID, a.PORCDESCUENTO" +
										", (select value from M_PRODUCT where M_product_id = a.xx_CATEGORIA_id) as categoria" +
										", (select value from M_PRODUCT where M_product_id = a.xx_departamento_id) as departamento" +
										", null as linea, null as seccion, null as producto, null as referencia, null as marca" +
										", a.CANTIDADPRODUCTO, a.NROPAGAR, c.NAME, a.grupo, a.TIENDA, a.ISACTIVE" +
										", a.REGACTUALIZADO  FROM XX_COMBO a inner join XX_PROMOCIONES c on a.XX_PROMOCIONES_ID = c.XX_PROMOCIONES_ID " +
										"WHERE (c.APROBADOMER like 'Y' OR c.APROBADOMAR like 'Y' OR c.APROBADOGG like 'Y') " +
										"AND a.isactive<>a.REGACTUALIZADO and XX_DEPARTAMENTO_ID IS NOT NULL AND a.xx_promociones_id= " +
										((Promociones)promociones.elementAt(i)).getXx_promociones_id()+
										" UNION " +
										"SELECT a.XX_PROMOCIONES_ID, a.XX_COMBO_ID, a.PORCDESCUENTO" +
										", (select value from M_PRODUCT where M_product_id = a.xx_CATEGORIA_id) as categoria" +
										", b.value as departamento, null, null, null, null, null, a.CANTIDADPRODUCTO, a.NROPAGAR, c.NAME, a.grupo, a.TIENDA" +
										", a.ISACTIVE, a.REGACTUALIZADO FROM XX_COMBO a inner join XX_PROMOCIONES c " +
										"on a.XX_PROMOCIONES_ID = c.XX_PROMOCIONES_ID inner join M_Product b on " +
										"a.xx_categoria_id=b.xx_categoria_id and b.xx_tipoproducto=2000000 and a.xx_departamento_id is null " +
										"WHERE (c.APROBADOMER like 'Y' OR c.APROBADOMAR like 'Y' OR c.APROBADOGG like 'Y') " +
										"AND a.isactive<>a.REGACTUALIZADO and XX_DEPARTAMENTO_ID IS NULL AND a.xx_promociones_id= "+
										((Promociones)promociones.elementAt(i)).getXx_promociones_id();
								break;
							case 3: //Lineas
								query=	"SELECT a.XX_PROMOCIONES_ID, a.XX_COMBO_ID, a.PORCDESCUENTO" +
										", (select value from M_PRODUCT where M_product_id = a.xx_CATEGORIA_id) as categoria " +
										", (select value from M_PRODUCT where M_product_id = a.xx_departamento_id) as departamento " +
										", (select value from M_PRODUCT where M_product_id = a.xx_linea_id) as linea" +
										", null as seccion, null as producto, null as referencia, null as marca " +
										", a.CANTIDADPRODUCTO, a.NROPAGAR, c.NAME, a.grupo, a.TIENDA, a.ISACTIVE " +
										", a.REGACTUALIZADO  FROM XX_COMBO a inner join XX_PROMOCIONES c on a.XX_PROMOCIONES_ID = c.XX_PROMOCIONES_ID " +
										"WHERE (c.APROBADOMER like 'Y' OR c.APROBADOMAR like 'Y' OR c.APROBADOGG like 'Y') " +
										"AND a.isactive<>a.REGACTUALIZADO and XX_linea_ID IS NOT NULL AND a.xx_promociones_id=" +
										((Promociones)promociones.elementAt(i)).getXx_promociones_id()+
										" UNION " +
										"SELECT a.XX_PROMOCIONES_ID, a.XX_COMBO_ID, a.PORCDESCUENTO " +
										", (select value from M_PRODUCT where M_product_id = a.xx_CATEGORIA_id) as CATEGORIA " +
										", (select value from M_PRODUCT where M_product_id = b.xx_departamento_id) as departamento " +
										", b.value as linea, null, null, null, null, a.CANTIDADPRODUCTO, a.NROPAGAR, c.NAME, a.grupo, a.TIENDA, a.ISACTIVE " +
										", a.REGACTUALIZADO  FROM XX_COMBO a inner join XX_PROMOCIONES c " +
										"on a.XX_PROMOCIONES_ID = c.XX_PROMOCIONES_ID inner join M_Product b " +
										"on a.xx_departamento_id=b.xx_departamento_id and b.xx_tipoproducto=3000000 and a.xx_linea_id is null " +
										"WHERE (c.APROBADOMER like 'Y' OR c.APROBADOMAR like 'Y' OR c.APROBADOGG like 'Y') " +
										"AND a.isactive<>a.REGACTUALIZADO and a.xx_promociones_id=" +
										((Promociones)promociones.elementAt(i)).getXx_promociones_id()+
										" UNION " +
										"SELECT a.XX_PROMOCIONES_ID, a.XX_COMBO_ID, a.PORCDESCUENTO " +
										", (select value from M_PRODUCT where M_product_id = a.xx_CATEGORIA_id) as CATEGORIA " +
										", (select value from M_PRODUCT where M_product_id = b.xx_departamento_id) as departamento " +
										", b.value as linea, null, null, null, null, a.CANTIDADPRODUCTO, a.NROPAGAR, c.NAME, a.grupo, a.TIENDA, a.ISACTIVE " +
										", a.REGACTUALIZADO " +
										"FROM XX_COMBO a inner join XX_PROMOCIONES c on a.XX_PROMOCIONES_ID = c.XX_PROMOCIONES_ID " +
										"inner join M_Product b on a.xx_categoria_id=b.xx_categoria_id and b.xx_tipoproducto=3000000 " +
										"and a.xx_departamento_id is null and a.xx_linea_id is null WHERE " +
										"(c.APROBADOMER like 'Y' OR c.APROBADOMAR like 'Y' OR c.APROBADOGG like 'Y') " +
										"AND a.isactive<>a.REGACTUALIZADO and a.xx_promociones_id="+
										((Promociones)promociones.elementAt(i)).getXx_promociones_id();
								break;
							case 4: //Secciones
								query=	"SELECT a.XX_PROMOCIONES_ID, a.XX_COMBO_ID, a.PORCDESCUENTO" +
										", (select value from M_PRODUCT where M_product_id = a.xx_CATEGORIA_id) as CATEGORIA" +
										", (select value from M_PRODUCT where M_product_id = a.xx_departamento_id) as departamento" +
										", (select value from M_PRODUCT where M_product_id = a.xx_linea_id) as linea" +
										", (select value from M_PRODUCT where M_product_id = a.xx_seccion_id) as seccion" +
										", null as producto, null as referencia, null as marca" +
										", a.CANTIDADPRODUCTO, a.NROPAGAR, c.NAME, a.grupo, a.TIENDA, a.ISACTIVE, a.REGACTUALIZADO  " +
										"FROM XX_COMBO a inner join XX_PROMOCIONES c on a.XX_PROMOCIONES_ID = c.XX_PROMOCIONES_ID WHERE " +
										"(c.APROBADOMER like 'Y' OR c.APROBADOMAR like 'Y' OR c.APROBADOGG like 'Y') AND " +
										"a.isactive<>a.REGACTUALIZADO and XX_seccion_ID IS NOT NULL AND a.xx_promociones_id="+
										((Promociones)promociones.elementAt(i)).getXx_promociones_id()+
										" UNION " +
										"SELECT a.XX_PROMOCIONES_ID, a.XX_COMBO_ID, a.PORCDESCUENTO" +
										", (select value from M_PRODUCT where M_product_id = a.xx_CATEGORIA_id) as CATEGORIA" +
										", (select value from M_PRODUCT where M_product_id = b.xx_departamento_id) as departamento" +
										", (select value from M_PRODUCT where M_product_id = b.xx_linea_id) as linea" +
										", b.value as seccion, null, null, null, a.CANTIDADPRODUCTO, a.NROPAGAR, c.NAME, a.grupo, a.TIENDA, a.ISACTIVE" +
										", a.REGACTUALIZADO  FROM XX_COMBO a inner join XX_PROMOCIONES c on " +
										"a.XX_PROMOCIONES_ID = c.XX_PROMOCIONES_ID inner join M_Product b on a.xx_linea_id=b.xx_linea_id " +
										"and b.xx_tipoproducto=4000000 and a.xx_seccion_id is null and a.xx_linea_id is not NULL " +
										"WHERE (c.APROBADOMER like 'Y' OR c.APROBADOMAR like 'Y' OR c.APROBADOGG like 'Y') " +
										"AND a.isactive<>a.REGACTUALIZADO and a.xx_promociones_id="+
										((Promociones)promociones.elementAt(i)).getXx_promociones_id()+
										" UNION " +
										"SELECT a.XX_PROMOCIONES_ID, a.XX_COMBO_ID, a.PORCDESCUENTO" +
										", (select value from M_PRODUCT where M_product_id = a.xx_CATEGORIA_id) as CATEGORIA" +
										", (select value from M_PRODUCT where M_product_id = b.xx_departamento_id) as departamento" +
										", (select value from M_PRODUCT where M_product_id = b.xx_linea_id) as linea, b.value as seccion, null, null, null" +
										", a.CANTIDADPRODUCTO, a.NROPAGAR, c.NAME, a.grupo, a.TIENDA, a.ISACTIVE, a.REGACTUALIZADO  " +
										"FROM XX_COMBO a inner join XX_PROMOCIONES c on a.XX_PROMOCIONES_ID = c.XX_PROMOCIONES_ID " +
										"inner join M_Product b on a.xx_departamento_id=b.xx_departamento_id and b.xx_tipoproducto=4000000 " +
										"and a.xx_linea_id is null and a.xx_departamento_id is not null " +
										"WHERE (c.APROBADOMER like 'Y' OR c.APROBADOMAR like 'Y' OR c.APROBADOGG like 'Y') " +
										"AND a.isactive<>a.REGACTUALIZADO and a.xx_promociones_id="+
										((Promociones)promociones.elementAt(i)).getXx_promociones_id()+
										" UNION " +
										"SELECT a.XX_PROMOCIONES_ID, a.XX_COMBO_ID, a.PORCDESCUENTO" +
										", (select value from M_PRODUCT where M_product_id = a.xx_CATEGORIA_id) as CATEGORIA" +
										", (select value from M_PRODUCT where M_product_id = b.xx_departamento_id) as departamento" +
										", (select value from M_PRODUCT where M_product_id = b.xx_linea_id) as linea  , b.value as seccion, null, null, null" +
										", a.CANTIDADPRODUCTO, a.NROPAGAR, c.NAME, a.grupo, a.TIENDA, a.ISACTIVE, a.REGACTUALIZADO  " +
										"FROM XX_COMBO a inner join XX_PROMOCIONES c on a.XX_PROMOCIONES_ID = c.XX_PROMOCIONES_ID " +
										"inner join M_Product b on a.xx_categoria_id=b.xx_categoria_id and b.xx_tipoproducto=4000000 " +
										"and a.xx_linea_id is null and a.xx_departamento_id is null " +
										"WHERE (c.APROBADOMER like 'Y' OR c.APROBADOMAR like 'Y' OR c.APROBADOGG like 'Y') " +
										"AND a.isactive<>a.REGACTUALIZADO and a.xx_promociones_id="+((Promociones)promociones.elementAt(i)).getXx_promociones_id();
								break;
							case 5: // producto, marca y refrencia
								query="SELECT a.XX_PROMOCIONES_ID, a.XX_COMBO_ID, a.PORCDESCUENTO" +
										", (select value from M_PRODUCT where M_product_id = a.xx_CATEGORIA_id) as CATEGORIA" +
										", (select value from M_PRODUCT where M_product_id = a.xx_departamento_id) as departamento" +
										", (select value from M_PRODUCT where M_product_id = a.xx_linea_id) as linea" +
										", (select value from M_PRODUCT where M_product_id = a.xx_seccion_id) as seccion" +
										", (select value from M_PRODUCT where M_product_id = a.xx_producto_id) as producto" +
										", (select XX_REFPROV from M_PRODUCT where M_product_id = a.xx_producto_id) as referencia" +
										", (select xx_marca from M_PRODUCT where M_product_id = a.xx_producto_id) as marca" +
										", a.CANTIDADPRODUCTO, a.NROPAGAR, c.NAME, a.grupo, a.TIENDA, a.ISACTIVE, a.REGACTUALIZADO  " +
										"FROM XX_COMBO a inner join XX_PROMOCIONES c on a.XX_PROMOCIONES_ID = c.XX_PROMOCIONES_ID " +
										"WHERE (c.APROBADOMER like 'Y' OR c.APROBADOMAR like 'Y' OR c.APROBADOGG like 'Y') " +
										"AND a.isactive<>a.REGACTUALIZADO and XX_producto_ID IS NOT NULL AND a.xx_promociones_id=" +
										((Promociones)promociones.elementAt(i)).getXx_promociones_id()+
										" union " +
										"SELECT a.XX_PROMOCIONES_ID, a.XX_COMBO_ID, a.PORCDESCUENTO" +
										", (select value from M_PRODUCT where M_product_id = a.xx_CATEGORIA_id) as CATEGORIA" +
										", (select value from M_PRODUCT where M_product_id = a.xx_departamento_id) as departamento" +
										", (select value from M_PRODUCT where M_product_id = a.xx_linea_id) as linea" +
										", (select value from M_PRODUCT where M_product_id = a.xx_seccion_id) as seccion" +
										", b.value" +
										", (Select XX_REFPROV from M_Product where m_product_id=a.XX_REFERENCIAPROVEEDOR_ID) as referencia" +
										", (select xx_marca from M_PRODUCT where M_product_id = b.m_product_id) as marca" +
										", a.CANTIDADPRODUCTO, a.NROPAGAR, c.NAME, a.grupo, a.TIENDA, a.ISACTIVE, a.REGACTUALIZADO  " +
										"FROM XX_COMBO a inner join XX_PROMOCIONES c on a.XX_PROMOCIONES_ID = c.XX_PROMOCIONES_ID " +
										"inner join m_product b on b.xx_categoria_id=a.xx_categoria_id " +
										"and b.xx_departamento_id=a.xx_departamento_id and b.xx_linea_id=a.xx_linea_id " +
										"and b.xx_seccion_id=a.xx_seccion_id and b.XX_REFPROV=" +
										"(Select XX_REFPROV from M_Product where m_product_id=a.XX_REFERENCIAPROVEEDOR_ID) " +
										"WHERE (c.APROBADOMER like 'Y' OR c.APROBADOMAR like 'Y' OR c.APROBADOGG like 'Y') AND " +
										"a.isactive<>a.REGACTUALIZADO and issummary='Y' and a.xx_promociones_id=" +
										((Promociones)promociones.elementAt(i)).getXx_promociones_id()+
										" union " +
										"SELECT a.XX_PROMOCIONES_ID, a.XX_COMBO_ID, a.PORCDESCUENTO" +
										", (select value from M_PRODUCT where M_product_id = a.xx_CATEGORIA_id) as CATEGORIA" +
										", (select value from M_PRODUCT where M_product_id = a.xx_departamento_id) as departamento" +
										", (select value from M_PRODUCT where M_product_id = a.xx_linea_id) as linea" +
										", (select value from M_PRODUCT where M_product_id = a.xx_seccion_id) as seccion" +
										", (select value from M_PRODUCT where M_product_id = b.m_product_id) as producto" +
										", (select XX_REFPROV from M_PRODUCT where M_product_id = b.m_product_id) as referencia" +
										", (select XX_MARCA from M_PRODUCT where M_product_id = b.m_product_id) as marca" +
										", a.CANTIDADPRODUCTO, a.NROPAGAR, c.NAME, a.grupo, a.TIENDA, a.ISACTIVE " +
										", a.REGACTUALIZADO  FROM XX_COMBO a inner join XX_PROMOCIONES c on a.XX_PROMOCIONES_ID = c.XX_PROMOCIONES_ID " +
										"inner join m_product b on a.XX_MARCA=b.XX_MARCA and b.xx_categoria_id=a.xx_categoria_id " +
										"and b.xx_departamento_id=a.xx_departamento_id and b.xx_linea_id=a.xx_linea_id and b.xx_seccion_id=a.xx_seccion_id " +
										"WHERE (c.APROBADOMER like 'Y' OR c.APROBADOMAR like 'Y' OR c.APROBADOGG like 'Y') " +
										"AND a.isactive<>a.REGACTUALIZADO and issummary='Y' and " +
										"XX_producto_ID IS NULL AND a.xx_promociones_id="+
										((Promociones)promociones.elementAt(i)).getXx_promociones_id()+
										" UNION " +
										"SELECT a.XX_PROMOCIONES_ID, a.XX_COMBO_ID, a.PORCDESCUENTO" +
										", (select value from M_PRODUCT where M_product_id = a.xx_CATEGORIA_id) as CATEGORIA" +
										", (select value from M_PRODUCT where M_product_id = b.xx_departamento_id) as departamento" +
										", (select value from M_PRODUCT where M_product_id = b.xx_linea_id) as linea" +
										", (select value from M_PRODUCT where M_product_id = b.xx_seccion_id) as seccion" +
										", b.value as producto" +
										", (select XX_REFPROV from M_PRODUCT where M_product_id = b.m_product_id) as referencia" +
										", (select XX_MARCA from M_PRODUCT where M_product_id = b.m_product_id) as marca" +
										", a.CANTIDADPRODUCTO, a.NROPAGAR, c.NAME, a.grupo, a.TIENDA, a.ISACTIVE, a.REGACTUALIZADO  " +
										"FROM XX_COMBO a inner join XX_PROMOCIONES c on a.XX_PROMOCIONES_ID = c.XX_PROMOCIONES_ID " +
										"inner join M_Product b on a.xx_seccion_id=b.xx_seccion_id and b.xx_tipoproducto=5000000 " +
										"and a.xx_producto_id is NULL and issummary='Y'" +
										"WHERE (c.APROBADOMER like 'Y' OR c.APROBADOMAR like 'Y' OR c.APROBADOGG like 'Y') " +
										"AND a.isactive<>a.REGACTUALIZADO and a.xx_promociones_id=" +
										((Promociones)promociones.elementAt(i)).getXx_promociones_id()+
										" UNION " +
										"SELECT a.XX_PROMOCIONES_ID, a.XX_COMBO_ID, a.PORCDESCUENTO" +
										", (select value from M_PRODUCT where M_product_id = a.xx_CATEGORIA_id) as CATEGORIA" +
										", (select value from M_PRODUCT where M_product_id = b.xx_departamento_id) as departamento" +
										", (select value from M_PRODUCT where M_product_id = b.xx_linea_id) as linea" +
										", (select value from M_PRODUCT where M_product_id = b.xx_seccion_id) as seccion  " +
										", b.value as producto" +
										", (select XX_REFPROV from M_PRODUCT where M_product_id = b.m_product_id) as referencia" +
										", (select XX_MARCA from M_PRODUCT where M_product_id = b.m_product_id) as marca" +
										", a.CANTIDADPRODUCTO, a.NROPAGAR, c.NAME, a.grupo, a.TIENDA, a.ISACTIVE, a.REGACTUALIZADO  " +
										"FROM XX_COMBO a inner join XX_PROMOCIONES c on a.XX_PROMOCIONES_ID = c.XX_PROMOCIONES_ID " +
										"inner join M_Product b on a.xx_linea_id=b.xx_linea_id and b.xx_tipoproducto=5000000 " +
										"and a.xx_seccion_id is null and issummary='Y'" +
										"WHERE (c.APROBADOMER like 'Y' OR c.APROBADOMAR like 'Y' OR c.APROBADOGG like 'Y') " +
										"AND a.isactive<>a.REGACTUALIZADO and a.xx_promociones_id=" +
										((Promociones)promociones.elementAt(i)).getXx_promociones_id()+
										" UNION " +
										"SELECT a.XX_PROMOCIONES_ID, a.XX_COMBO_ID, a.PORCDESCUENTO" +
										", (select value from M_PRODUCT where M_product_id = a.xx_CATEGORIA_id) as CATEGORIA" +
										", (select value from M_PRODUCT where M_product_id = b.xx_departamento_id) as departamento" +
										", (select value from M_PRODUCT where M_product_id = b.xx_linea_id) as linea  " +
										", (select value from M_PRODUCT where M_product_id = b.xx_seccion_id) as seccion  " +
										", b.value as producto" +
										", (select XX_REFPROV from M_PRODUCT where M_product_id = b.m_product_id) as referencia" +
										", (select XX_MARCA from M_PRODUCT where M_product_id = b.m_product_id) as marca" +
										", a.CANTIDADPRODUCTO, a.NROPAGAR, c.NAME, a.grupo, a.TIENDA, a.ISACTIVE, a.REGACTUALIZADO  " +
										"FROM XX_COMBO a inner join XX_PROMOCIONES c on a.XX_PROMOCIONES_ID = c.XX_PROMOCIONES_ID " +
										"inner join M_Product b on a.xx_departamento_id=b.xx_departamento_id and b.xx_tipoproducto=5000000 and a.xx_linea_id is null " +
										"and a.xx_seccion_id is null and issummary='Y' " +
										"WHERE (c.APROBADOMER like 'Y' OR c.APROBADOMAR like 'Y' OR c.APROBADOGG like 'Y') " +
										"AND a.isactive<>a.REGACTUALIZADO and a.xx_promociones_id=" +
										((Promociones)promociones.elementAt(i)).getXx_promociones_id()+
										" UNION " +
										"SELECT a.XX_PROMOCIONES_ID, a.XX_COMBO_ID, a.PORCDESCUENTO" +
										", (select value from M_PRODUCT where M_product_id = a.xx_CATEGORIA_id) as CATEGORIA" +
										", (select value from M_PRODUCT where M_product_id = b.xx_departamento_id) as departamento" +
										", (select value from M_PRODUCT where M_product_id = b.xx_linea_id) as linea  " +
										", (select value from M_PRODUCT where M_product_id = b.xx_seccion_id) as seccion" +
										", b.value" +
										", (select XX_REFPROV from M_PRODUCT where M_product_id = b.m_product_id) as referencia" +
										", (select XX_MARCA from M_PRODUCT where M_product_id = b.m_product_id) as marca" +
										", a.CANTIDADPRODUCTO, a.NROPAGAR, c.NAME, a.grupo, a.TIENDA, a.ISACTIVE, a.REGACTUALIZADO  " +
										"FROM XX_COMBO a inner join XX_PROMOCIONES c on a.XX_PROMOCIONES_ID = c.XX_PROMOCIONES_ID " +
										"inner join M_Product b on a.xx_categoria_id=b.xx_categoria_id and b.xx_tipoproducto=5000000 and a.xx_linea_id is null " +
										"and a.xx_departamento_id is null and a.xx_seccion_id is null and issummary='Y' " +
										"WHERE (c.APROBADOMER like 'Y' OR c.APROBADOMAR like 'Y' OR c.APROBADOGG like 'Y')  " +
										"AND a.isactive<>a.REGACTUALIZADO and a.xx_promociones_id="+((Promociones)promociones.elementAt(i)).getXx_promociones_id();
								break;
						}
	
						sentenciaOXE=mBD.getStatement();
						resultado= mBD.realizarConsultaOXE(query, sentenciaOXE);
						resultado.beforeFirst();
						while(resultado.next()){	
							try{
								int codigo = resultado.getInt("XX_PROMOCIONES_ID");
								int detalleC = resultado.getInt("XX_COMBO_ID");
								double porcdescuento = resultado.getDouble("PORCDESCUENTO");
								int categoria = resultado.getInt("categoria");
								int departamento = resultado.getInt("departamento")%100;
								String marca = resultado.getString("marca");
								String refproveedor = resultado.getString("referencia");
								int linea = resultado.getInt("linea")%100;
								int seccion = resultado.getInt("seccion")%100;
								int cantidadproducto = resultado.getInt("CANTIDADPRODUCTO");
								String producto = resultado.getString("producto");
								int cantproddesc = resultado.getInt("NROPAGAR");
								String name = resultado.getString("NAME");
								int grupo =resultado.getInt("grupo");
								String tiendas = resultado.getString("TIENDA");
								String activo = resultado.getString("ISACTIVE");
								String regactualizado = resultado.getString("REGACTUALIZADO");
								name=limpiarNombre(name);
								a.add(new DetallePromocionExt(codigo, detalleC, porcdescuento, categoria, departamento, marca, refproveedor
										, linea, seccion, 0, cantidadproducto, producto, cantproddesc, 0, name, 
										grupo, tiendas, activo, 0, null, regactualizado, regactualizado));
							}catch(ArithmeticException f){f.printStackTrace();}
						}
						resultado.close();
						sentenciaOXE.close();
						Iterator<DetallePromocionExt> it = a.iterator();
						while (it.hasNext()){
							DetallePromocionExt dpe = it.next();
							Iterator<Integer> j = promocionesInactivas.iterator();
							while (j.hasNext()){
								int x = (j.next()).intValue();
								if(dpe.getCodigo()==x)
									dpe.setActivo("N");
								mBD.realizarSentenciaOXE("update XX_PromProductoDetalleCaja set regactualizado='Y', isactive='N' where XX_PROMOCIONES_ID = "+x);
							}

							if (dpe.getRegactualizado().equals("N")||dpe.getActivo1().equals("N")){
								if (dpe.getActivo1().equals("N") && dpe.getRegactualizado().equals("Y"))
									sobreEscribirProductosAnulados(dpe.getCodigo(), dpe.getProducto());
								if (dpe.getProducto() == null){
									if (dpe.getTienda().equals(tienda)||dpe.getTienda().equals("00")){
										n++;
										if(dpe.getTipoPromocion()==1001200)
											detallePromocionExt += dpe.getCodigo()+"["+n+"["+dpe.getPorcdescuento()+"["+dpe.getCategoria()
											+"["+relleno.format(dpe.getDepartamento())+"["+((dpe.getMarca()==null)?"NULL":dpe.getMarca())+"["+relleno.format(dpe.getLinea())+"["
											+((dpe.getRefproveedor()==null)?"NULL":dpe.getRefproveedor())+"["+dpe.getMontominimo()+"["+dpe.getCantidadproducto()
											+"["+dpe.getCantproddesc()+"["+((dpe.getProducto()==null)?"NULL":dpe.getProducto())+"["+dpe.getBsbonoregalo()
											+"["+dpe.getActivo()+"["+dpe.getRegalo()+"["+dpe.getGrupo()+"["+relleno.format(dpe.getSeccion())+"[NULL[\r\n";
										else
											detallePromocionExt += dpe.getCodigo()+"["+n+"["+dpe.getPorcdescuento()+"["+dpe.getCategoria()
											+"["+relleno.format(dpe.getDepartamento())+"["+((dpe.getMarca()==null)?"NULL":dpe.getMarca())+"["+relleno.format(dpe.getLinea())+"["
											+((dpe.getRefproveedor()==null)?"NULL":dpe.getRefproveedor())+"["+dpe.getMontominimo()+"["+dpe.getCantidadproducto()
											+"["+dpe.getCantproddesc()+"["+((dpe.getProducto()==null)?"NULL":dpe.getProducto())+"["+dpe.getBsbonoregalo()
											+"["+dpe.getActivo()+"["+dpe.getName()+"["+dpe.getGrupo()+"["+relleno.format(dpe.getSeccion())+"[NULL[\r\n";
									}
								}else{//Si tiene producto
									try{
										sentenciaOXE=mBD.getStatement();
										ResultSet c =mBD.realizarConsultaOXE("select max(detallepromocion) as max from XX_PromProductoDetalleCaja where xx_promociones_id =  "+dpe.getCodigo(), sentenciaOXE);
										c.beforeFirst();
										if (c.first()) n = c.getInt("max");
										c.close();
										sentenciaOXE.close();
									}catch(Exception e){n=0;}
									if (dpe.getTienda().equals(tienda)||dpe.getTienda().equals("00")){	
										query = "select value from m_product a where issummary='N' and a.value like '"+dpe.getProducto()+"___' " +
												"and a.value not in (select value from XX_PromProductoDetalleCaja where xx_promociones_id="+dpe.getCodigo()+
												" and value=a.value)";
										sentenciaOXE=mBD.getStatement();
										resultado= mBD.realizarConsultaOXE(query, sentenciaOXE);
										resultado.beforeFirst();
										while (resultado.next()){
											n++;
											String consecutivo=resultado.getString("value");
											String sentencia="insert into XX_PromProductoDetalleCaja (CREATED, CREATEDBY, UPDATED, UPDATEDBY, AD_CLIENT_ID, AD_ORG_ID" +
												", XX_PROMOCIONES_ID, DETALLEPROMOCION, PORCDESCUENTO, CATEGORIA, DEPARTAMENTO" +
												", LINEA, SECCION, VALUE, XX_REFPROV, XX_MARCA, CANTIDADPRODUCTO, NROPAGAR, NAME" +
												", GRUPO, TIENDA, ISACTIVE, regactualizado)  " +
												"select a.CREATED, a.CREATEDBY, a.UPDATED, a.UPDATEDBY, a.AD_CLIENT_ID, a.AD_ORG_ID, " +
												dpe.getCodigo()+" as XX_PROMOCIONES_ID, "+n+", "+dpe.getPorcdescuento()+", "+dpe.getCategoria()+", "+
												dpe.getDepartamento()+", "+dpe.getLinea()+", "+dpe.getSeccion()+", '"+consecutivo+"' as VALUE, '" +
												dpe.getRefproveedor()+"', '"+dpe.getMarca()+"', "+dpe.getCantidadproducto()+", "+
												dpe.getCantproddesc()+", '"+dpe.getName()+"', "+dpe.getGrupo()+", '"+dpe.getTienda()+"', '"+
												dpe.getActivo1()+"', '"+dpe.getRegactualizado()+
												"' from xx_promociones a " +"where a.xx_promociones_id="+dpe.getCodigo()
												+" and  VALUE not in (select VALUE from XX_PromProductoDetalleCaja where XX_PROMOCIONES_ID="+dpe.getCodigo()+" and VALUE='"+consecutivo+"')";
											//System.out.println(sentencia);
											try{
												mBD.realizarSentenciaOXE(sentencia);
											}catch (SQLException e){;}
											//resultado.close();
										}
										sentenciaOXE.close();
										resultado.close();
										Statement sentenciaOXE1=mBD.getStatement();
										query ="select * from XX_PromProductoDetalleCaja where " +
											"regactualizado<>isactive and xx_promociones_id = "+dpe.getCodigo()+" and TIENDA='"+dpe.getTienda()+
											"' and value like '"+dpe.getProducto()+"___'";
										resultado = mBD.realizarConsultaOXE(query, sentenciaOXE1);	
										while(resultado.next()){
											try{
												int codigo = resultado.getInt("XX_PROMOCIONES_ID");
												int detallep = resultado.getInt("DETALLEPROMOCION");
												double porcdescuento = resultado.getDouble("PORCDESCUENTO");
												int categoria = resultado.getInt("categoria");
												int departamento = resultado.getInt("departamento")%100;
												String marca = resultado.getString("XX_MARCA");
												String refproveedor = resultado.getString("XX_REFPROV");
												int linea = resultado.getInt("linea")%100;
												int seccion = resultado.getInt("seccion")%100;
												double montominimo = resultado.getDouble("montopremiado");
												int cantidadproducto = resultado.getInt("CANTIDADPRODUCTO");
												String producto = resultado.getString("VALUE");
												int cantproddesc = resultado.getInt("NROPAGAR");
												double bsbonoregalo = resultado.getDouble("BSBONOREGALO");
												String name = resultado.getString("NAME");
												int grupo =resultado.getInt("grupo");
												name=limpiarNombre(name);
												detallePromocionExt += codigo+"["+detallep+"["+porcdescuento+"["+categoria+"["+relleno.format(departamento)
												+"["+((marca==null)?"NULL":marca)+"["+relleno.format(linea)+"["+((refproveedor==null)?"NULL":refproveedor)
												+"["+montominimo+"["+cantidadproducto+"["+cantproddesc+"["+((producto==null)?"NULL":producto)
												+"["+bsbonoregalo+"["+dpe.getActivo()+"["+name+"["+grupo+"["+seccion+"[NULL[\r\n";
												listaPromocionesPorActualizar.add(new PromocionesPorProducto(dpe.getCodigo(), detallep, true));
											}catch(ArithmeticException f){f.printStackTrace();}
										}
										resultado.close();
										sentenciaOXE1.close();
									}
								}
							}
						}
					} else if (Integer.parseInt(((Promociones)promociones.elementAt(i)).getTipopromocion())==1000800
							|| Integer.parseInt(((Promociones)promociones.elementAt(i)).getTipopromocion())==1001000
							|| Integer.parseInt(((Promociones)promociones.elementAt(i)).getTipopromocion())==1001400){
						query="SELECT a.XX_PROMOCIONES_ID, a.XX_ENTREGABLES_ID, a.MONTOPREMIADO, a.BSBONOREGALO, b.NAME" +
							", a.TIENDA, a.ISACTIVE, a.REGACTUALIZADO FROM XX_ENTREGABLES a inner join XX_PROMOCIONES b on " +
							"a.XX_PROMOCIONES_ID=b.XX_PROMOCIONES_ID WHERE (b.APROBADOMER like 'Y' OR b.APROBADOMAR like 'Y' OR " +
							"b.APROBADOGG like 'Y') AND a.isactive<>a.REGACTUALIZADO ";
						sentenciaOXE=mBD.getStatement();
						resultado= mBD.realizarConsultaOXE(query, sentenciaOXE);
						resultado.beforeFirst();
						while(resultado.next()){	
							try{
								int codigo = resultado.getInt("XX_PROMOCIONES_ID");
								int detalleC = resultado.getInt("XX_ENTREGABLES_ID");
								double montominimo = resultado.getDouble("MONTOPREMIADO");
								double bsbonoregalo = resultado.getDouble("BSBONOREGALO");
								String name = resultado.getString("NAME");
								String tiendas = resultado.getString("TIENDA");
								String activo = resultado.getString("isactive");
								String regactualizado = resultado.getString("REGACTUALIZADO");
								name=limpiarNombre(name);
								a.add(new DetallePromocionExt(codigo, detalleC, 0, 0, 0, null, null
										, 0, 0, montominimo, 0, null, 0, bsbonoregalo, name, 
										0, tiendas, activo, 0, null, regactualizado, null));
							}catch(ArithmeticException f){f.printStackTrace();}	
						}
						Iterator<DetallePromocionExt> it = a.iterator();
						while (it.hasNext()){
							DetallePromocionExt dpe = it.next();
							detallePromocionExt += dpe.getCodigo()+"["+dpe.getDetalle()+"["+dpe.getPorcdescuento()+"["+dpe.getCategoria()
							+"["+relleno.format(dpe.getDepartamento())+"["+((dpe.getMarca()==null)?"NULL":dpe.getMarca())+"["+relleno.format(dpe.getLinea())+"["
							+((dpe.getRefproveedor()==null)?"NULL":dpe.getRefproveedor())+"["+dpe.getMontominimo()+"["+dpe.getCantidadproducto()
							+"["+dpe.getCantproddesc()+"["+((dpe.getProducto()==null)?"NULL":dpe.getProducto())+"["+dpe.getBsbonoregalo()
							+"["+dpe.getActivo()+"["+dpe.getName()+"["+dpe.getGrupo()+"["+relleno.format(dpe.getSeccion())+"[NULL[\r\n";
						}
					}
				}//Cierra el for
			}else{//Caso Transferencia inmediata
				if (detalle==0){		
					String query = "select a.xx_promociones_id, a.tipopromocion " +
							" from xx_combo a inner join xx_promociones b on a.xx_promociones_id=b.xx_promociones_id" +
							" where b.xx_promociones_id=" +codigoP+
							" union" +
							" select a.xx_promociones_id, a.tipopromocion" +
							" from XX_DESCTRANS a inner join xx_promociones b on a.xx_promociones_id=b.xx_promociones_id" +
							" where b.xx_promociones_id=" +codigoP+
							" union" +
							" select a.xx_promociones_id, a.tipopromocion" +
							" from XX_ENTREGABLES a inner join xx_promociones b on a.xx_promociones_id=b.xx_promociones_id" +
							" where b.xx_promociones_id=" +codigoP+
							" union" +
							" select a.xx_promociones_id, a.tipopromocion" +
							" from XX_REGALOPPORCOMPRA a inner join xx_promociones b on a.xx_promociones_id=b.xx_promociones_id" +
							" where b.xx_promociones_id=" +codigoP;
					Statement sentenciaOXE=mBD.getStatement();
					ResultSet resultado= mBD.realizarConsultaOXE(query, sentenciaOXE);
					resultado.beforeFirst();
					while(resultado.next()){	
						try{
							String tipo = resultado.getString("tipopromocion");
							if (tipo.equals("1000100")||tipo.equals("1000300")||tipo.equals("1001200")){
								query="SELECT a.XX_PROMOCIONES_ID, a.XX_REGALOPPORCOMPRA_ID, a.PorcDescuento" +
										", (select value from M_PRODUCT where M_product_id = a.xx_categoria_id) as categoria" +
										", (select value from M_PRODUCT where M_product_id = a.xx_departamento_id) as departamento" +
										", (select value from M_PRODUCT where M_product_id = a.xx_linea_id) as linea" +
										", a.XX_MONTOMINIMO, a.CANTPRODCOMPRAR, b.VALUE, c.NAME" +
										", (select value from M_PRODUCT where M_product_id = a.xx_seccion_id) as seccion" +
										", a.TIENDA, a.ISACTIVE, a.TipoPromocion, a.Regalo, a.REGACTUALIZADO, XX_ACUMULAPREMIO  " +
										"FROM M_PRODUCT b INNER JOIN XX_REGALOPPORCOMPRA a on  b.xx_producto_id = a.xx_producto_id and " +
										"b.ISSUMMARY like 'N' and b.ISACTIVE like 'Y' inner join XX_PROMOCIONES c on a.XX_PROMOCIONES_ID = " +
										"c.XX_PROMOCIONES_ID WHERE xx_promociones_id="+codigoP;
								sentenciaOXE=mBD.getStatement();
								resultado= mBD.realizarConsultaOXE(query, sentenciaOXE);
								resultado.beforeFirst();
								while(resultado.next()){	
									try{
										int codigo = resultado.getInt("XX_PROMOCIONES_ID");
										int detalleC = resultado.getInt("XX_REGALOPPORCOMPRA_ID");
										double porcdescuento = resultado.getDouble("PORCDESCUENTO");
										int categoria = resultado.getInt("categoria");
										int departamento = resultado.getInt("departamento")%100;
										int linea = resultado.getInt("linea")%100;
										int seccion = resultado.getInt("seccion")%100;
										double montominimo = resultado.getDouble("XX_MONTOMINIMO");
										int cantidadproducto = resultado.getInt("CANTPRODCOMPRAR");
										String producto = resultado.getString("VALUE");
										String name = resultado.getString("NAME");
										String tiendas = resultado.getString("TIENDA");
										String activo = resultado.getString("isactive");
										int tipoPromocion = resultado.getInt("TipoPromocion");
										String regalo = resultado.getString("Regalo");
										String regactualizado = resultado.getString("REGACTUALIZADO");
										String acumulado =resultado.getString("XX_ACUMULAPREMIO");
										name=limpiarNombre(name);
										a.add(new DetallePromocionExt(codigo, detalleC, porcdescuento, categoria, departamento, null, null
												, linea, seccion, montominimo, cantidadproducto, producto, 0, 0, name, 
												1, tiendas, activo, tipoPromocion, regalo, regactualizado, acumulado));
									}catch(ArithmeticException f){f.printStackTrace();}
								}
								//resultado.close();
								//sentenciaOXE.close();
								resultado.beforeFirst();
								Iterator<DetallePromocionExt> iterator = a.iterator();
								while (iterator.hasNext()){
									DetallePromocionExt dpe = iterator.next();
									Iterator<Integer> j = promocionesInactivas.iterator();
									while (j.hasNext()){
										int x = (j.next()).intValue();
										if(dpe.getCodigo()==x){
											dpe.setActivo("N");
											mBD.realizarSentenciaOXE("update XX_PromProductoDetalleCaja set regactualizado='Y', isactive='N' where XX_PROMOCIONES_ID = "+x);
										}
									}
									try{
										sentenciaOXE=mBD.getStatement();
										ResultSet c =mBD.realizarConsultaOXE("select max(detallepromocion) as max from XX_PromProductoDetalleCaja where xx_promociones_id = "+dpe.getCodigo(), sentenciaOXE);
										c.beforeFirst();
										if (c.first()) 
											n = c.getInt("max");
										c.close();
										sentenciaOXE.close();
									}catch(Exception e){n=0;}
									if (!dpe.getRegactualizado().equals(dpe.getActivo())){
										if (dpe.getTienda().equals(tienda)||dpe.getTienda().equals("00")){
											n++;
											String sentencia="insert into XX_PromProductoDetalleCaja (CREATED, CREATEDBY, UPDATED, UPDATEDBY" +
											", AD_CLIENT_ID, AD_ORG_ID, XX_PROMOCIONES_ID, DETALLEPROMOCION, PORCDESCUENTO, CATEGORIA, DEPARTAMENTO" +
											", XX_MARCA, LINEA, XX_REFPROV, MONTOPREMIADO, CANTIDADPRODUCTO, NROPAGAR, VALUE, BSBONOREGALO, NAME" +
											", GRUPO, SECCION, TIENDA, ISACTIVE, REGALO, regactualizado, XX_ACUMULAPREMIO) " +
											"SELECT  a.CREATED, a.CREATEDBY, a.UPDATED, a.UPDATEDBY, a.AD_CLIENT_ID, a.AD_ORG_ID" +
											", a.XX_PROMOCIONES_ID, " +n+", a.PorcDescuento, (select value from M_PRODUCT where M_product_id = a.xx_categoria_id) " +
											"as categoria, (select value from M_PRODUCT where M_product_id = a.xx_departamento_id) as departamento, null" +
											", (select value from M_PRODUCT where M_product_id = a.xx_linea_id) as linea, null, a.XX_MONTOMINIMO" +
											", a.CANTPRODCOMPRAR, null, b.VALUE, NULL, c.NAME, 1, (select value from M_PRODUCT where M_product_id " +
											"= a.xx_seccion_id) as seccion, a.TIENDA, '"+dpe.getActivo1()+"', a.Regalo, '"+dpe.getRegactualizado()+"', a.XX_ACUMULAPREMIO FROM M_PRODUCT b INNER JOIN " +
											"XX_REGALOPPORCOMPRA a on  b.xx_producto_id = a.xx_producto_id and b.ISSUMMARY like 'N' and b.ISACTIVE like 'Y' " +
											"inner join XX_PROMOCIONES c on a.XX_PROMOCIONES_ID = c.XX_PROMOCIONES_ID AND c.xx_promociones_id = "+
											dpe.getCodigo()+" AND b.VALUE like '" +dpe.getProducto()+"' and a.regactualizado<>a.isactive and (b.VALUE not in " +
											"(Select VALUE from XX_PROMPRODUCTODETALLECAJA where xx_promociones_id='"+dpe.getCodigo()+"' and isactive<>regactualizado) or a.isactive='N')";
											try{
												mBD.realizarSentenciaOXE(sentencia);
											}catch(SQLException e){;}
											Statement sentenciaOXE1=mBD.getStatement();
											query="select * from XX_PromProductoDetalleCaja where " +
												"xx_promociones_id = "+dpe.getCodigo()+" and TIENDA='"+dpe.getTienda()+
												"' and value = '"+dpe.getProducto()+"'";
											resultado = mBD.realizarConsultaOXE(query , sentenciaOXE1);	
											while(resultado.next()){
												try{
													int codigo = resultado.getInt("XX_PROMOCIONES_ID");
													int detalleC = resultado.getInt("DETALLEPROMOCION");
													double porcdescuento = resultado.getDouble("PORCDESCUENTO");
													int categoria = resultado.getInt("categoria");
													int departamento = resultado.getInt("departamento")%100;
													String marca = resultado.getString("XX_MARCA");
													String refproveedor = resultado.getString("XX_REFPROV");
													int linea = resultado.getInt("linea")%100;
													int seccion = resultado.getInt("seccion")%100;
													double montominimo = resultado.getDouble("montopremiado");
													int cantidadproducto = resultado.getInt("CANTIDADPRODUCTO");
													String producto = resultado.getString("VALUE");
													int cantproddesc = resultado.getInt("NROPAGAR");
													double bsbonoregalo = resultado.getDouble("BSBONOREGALO");
													String name = resultado.getString("NAME");
													int grupo =resultado.getInt("grupo");
													String acumular = resultado.getString("XX_acumulaPremio");
													name=limpiarNombre(name);
													detallePromocionExt += codigo+"["+detalleC+"["+porcdescuento+"["+categoria+"["+relleno.format(departamento)
													+"["+((marca==null)?"NULL":marca)+"["+relleno.format(linea)+"["+((refproveedor==null)?"NULL":refproveedor)
													+"["+montominimo+"["+cantidadproducto+"["+cantproddesc+"["+((producto==null)?"NULL":producto)
													+"["+bsbonoregalo+"["+dpe.getActivo()+"["+name+"["+grupo+"["+seccion+"["+acumular+"[\r\n";
													listaPromocionesPorActualizar.add(new PromocionesPorProducto(dpe.getCodigo(), detalleC, true));
												}catch(ArithmeticException f){f.printStackTrace();}
											}
											//resultado.close();
											//sentenciaOXE1.close();
										}
									}
								}
							}else if(tipo.equals("1000200")||tipo.equals("1000750")||tipo.equals("1000900")||tipo.equals("1001100")){
								query="SELECT a.XX_PROMOCIONES_ID, a.xx_DESCTRANS_id, a.PORCDESCUENTO, null, null, null, null, null, a.MONTOMINIMO" +
										",a.CantidadProducto, b.NAME, a.TIENDA, a.ISACTIVE, a.REGACTUALIZADO  FROM XX_DESCTRANS a inner join " +
										"XX_PROMOCIONES b on a.XX_PROMOCIONES_ID = b.XX_PROMOCIONES_ID WHERE a.xx_promociones_id="+codigoP+"";
								sentenciaOXE=mBD.getStatement();
								resultado= mBD.realizarConsultaOXE(query, sentenciaOXE);
								resultado.beforeFirst();
								while(resultado.next()){	
									try{
										int codigo = resultado.getInt("XX_PROMOCIONES_ID");
										int detalleC = resultado.getInt("xx_DESCTRANS_id");
										double porcdescuento = resultado.getDouble("PORCDESCUENTO");
										double montominimo = resultado.getDouble("MONTOMINIMO");
										int cantidadproducto = resultado.getInt("CantidadProducto");
										String name = resultado.getString("NAME");
										String tiendas = resultado.getString("TIENDA");
										String activo = resultado.getString("ISACTIVE");
										String regactualizado = resultado.getString("REGACTUALIZADO");
										name=limpiarNombre(name);
										a.add(new DetallePromocionExt(codigo, detalleC, porcdescuento, 0, 0, null, null
												, 0, 0, montominimo, cantidadproducto, null, 0, 0, name, 1, tiendas, activo
												, Integer.parseInt(tipo)
												, null, regactualizado, null));
									}catch(ArithmeticException f){f.printStackTrace();}
								}
								//resultado.close();
								//sentenciaOXE.close();
								Iterator<DetallePromocionExt> it = a.iterator();
								while (it.hasNext()){
									DetallePromocionExt dpe = it.next();
									detallePromocionExt += dpe.getCodigo()+"["+dpe.getDetalle()+"["+dpe.getPorcdescuento()+"["+dpe.getCategoria()
									+"["+relleno.format(dpe.getDepartamento())+"["+((dpe.getMarca()==null)?"NULL":dpe.getMarca())
									+"["+relleno.format(dpe.getLinea())+"["+((dpe.getRefproveedor()==null)?"NULL":dpe.getRefproveedor())
									+"["+dpe.getMontominimo()+"["+dpe.getCantidadproducto()
									+"["+dpe.getCantproddesc()+"["+((dpe.getProducto()==null)?"NULL":dpe.getProducto())+"["+dpe.getBsbonoregalo()
									+"["+dpe.getActivo()+"["+dpe.getName()+"["+dpe.getGrupo()+"["+relleno.format(dpe.getSeccion())+"[NULL[\r\n";
								}
					
							
						} else if (tipo.equals("1000500")||tipo.equals("1000600")||tipo.equals("1001300")){
							int seleccion=getCriterioSeleccion(codigoP);
							switch (seleccion){
								case 1: //Categorias
									query =	"SELECT a.XX_PROMOCIONES_ID, a.XX_COMBO_ID, a.PORCDESCUENTO" +
											", (select value from M_PRODUCT where M_product_id = a.xx_categoria_id) as categoria" +
											", null as departamento, null as linea, null as seccion, null as producto, null as referencia, null as marca" +
											", a.CANTIDADPRODUCTO, a.NROPAGAR, c.NAME, a.grupo, a.TIENDA, a.ISACTIVE, a.REGACTUALIZADO  " +
											"FROM XX_COMBO a inner join XX_PROMOCIONES c on a.XX_PROMOCIONES_ID = c.XX_PROMOCIONES_ID " +
											"WHERE xx_promociones_id="+codigoP;
									break;
								case 2: // Departamentos
									query =	"SELECT a.XX_PROMOCIONES_ID, a.XX_COMBO_ID, a.PORCDESCUENTO" +
											", (select value from M_PRODUCT where M_product_id = a.xx_CATEGORIA_id) as categoria" +
											", (select value from M_PRODUCT where M_product_id = a.xx_departamento_id) as departamento" +
											", null as linea, null as seccion, null as producto, null as referencia, null as marca" +
											", a.CANTIDADPRODUCTO, a.NROPAGAR, c.NAME, a.grupo, a.TIENDA, a.ISACTIVE" +
											", a.REGACTUALIZADO  FROM XX_COMBO a inner join XX_PROMOCIONES c on a.XX_PROMOCIONES_ID = c.XX_PROMOCIONES_ID " +
											"WHERE a.xx_promociones_id= "+codigoP+
											" UNION " +
											"SELECT a.XX_PROMOCIONES_ID, a.XX_COMBO_ID, a.PORCDESCUENTO" +
											", (select value from M_PRODUCT where M_product_id = a.xx_CATEGORIA_id) as categoria" +
											", b.value as departamento, null, null, null, null, null, a.CANTIDADPRODUCTO, a.NROPAGAR, c.NAME, a.grupo, a.TIENDA" +
											", a.ISACTIVE, a.REGACTUALIZADO FROM XX_COMBO a inner join XX_PROMOCIONES c " +
											"on a.XX_PROMOCIONES_ID = c.XX_PROMOCIONES_ID inner join M_Product b on " +
											"a.xx_categoria_id=b.xx_categoria_id and b.xx_tipoproducto=2000000 and a.xx_departamento_id is null " +
											"WHERE a.xx_promociones_id="+codigoP;
									break;
								case 3: //Lineas
									query=	"SELECT a.XX_PROMOCIONES_ID, a.XX_COMBO_ID, a.PORCDESCUENTO" +
											", (select value from M_PRODUCT where M_product_id = a.xx_CATEGORIA_id) as categoria " +
											", (select value from M_PRODUCT where M_product_id = a.xx_departamento_id) as departamento " +
											", (select value from M_PRODUCT where M_product_id = a.xx_linea_id) as linea" +
											", null as seccion, null as producto, null as referencia, null as marca " +
											", a.CANTIDADPRODUCTO, a.NROPAGAR, c.NAME, a.grupo, a.TIENDA, a.ISACTIVE " +
											", a.REGACTUALIZADO  FROM XX_COMBO a inner join XX_PROMOCIONES c on a.XX_PROMOCIONES_ID = c.XX_PROMOCIONES_ID " +
											"WHERE a.xx_promociones_id="+codigoP+
											" UNION " +
											"SELECT a.XX_PROMOCIONES_ID, a.XX_COMBO_ID, a.PORCDESCUENTO " +
											", (select value from M_PRODUCT where M_product_id = a.xx_CATEGORIA_id) as CATEGORIA " +
											", (select value from M_PRODUCT where M_product_id = b.xx_departamento_id) as departamento " +
											", b.value as linea, null, null, null, null, a.CANTIDADPRODUCTO, a.NROPAGAR, c.NAME, a.grupo, a.TIENDA, a.ISACTIVE " +
											", a.REGACTUALIZADO  FROM XX_COMBO a inner join XX_PROMOCIONES c " +
											"on a.XX_PROMOCIONES_ID = c.XX_PROMOCIONES_ID inner join M_Product b " +
											"on a.xx_departamento_id=b.xx_departamento_id and b.xx_tipoproducto=3000000 and a.xx_linea_id is null " +
											"WHERE a.xx_promociones_id="+codigoP+
											" UNION " +
											"SELECT a.XX_PROMOCIONES_ID, a.XX_COMBO_ID, a.PORCDESCUENTO " +
											", (select value from M_PRODUCT where M_product_id = a.xx_CATEGORIA_id) as CATEGORIA " +
											", (select value from M_PRODUCT where M_product_id = b.xx_departamento_id) as departamento " +
											", b.value as linea, null, null, null, null, a.CANTIDADPRODUCTO, a.NROPAGAR, c.NAME, a.grupo, a.TIENDA, a.ISACTIVE " +
											", a.REGACTUALIZADO " +
											"FROM XX_COMBO a inner join XX_PROMOCIONES c on a.XX_PROMOCIONES_ID = c.XX_PROMOCIONES_ID " +
											"inner join M_Product b on a.xx_categoria_id=b.xx_categoria_id and b.xx_tipoproducto=3000000 " +
											"and a.xx_departamento_id is null and a.xx_linea_id is null WHERE a.xx_promociones_id="+codigoP;
									break;
								case 4: //Secciones
									query=	"SELECT a.XX_PROMOCIONES_ID, a.XX_COMBO_ID, a.PORCDESCUENTO" +
											", (select value from M_PRODUCT where M_product_id = a.xx_CATEGORIA_id) as CATEGORIA" +
											", (select value from M_PRODUCT where M_product_id = a.xx_departamento_id) as departamento" +
											", (select value from M_PRODUCT where M_product_id = a.xx_linea_id) as linea" +
											", (select value from M_PRODUCT where M_product_id = a.xx_seccion_id) as seccion" +
											", null as producto, null as referencia, null as marca" +
											", a.CANTIDADPRODUCTO, a.NROPAGAR, c.NAME, a.grupo, a.TIENDA, a.ISACTIVE, a.REGACTUALIZADO  " +
											"FROM XX_COMBO a inner join XX_PROMOCIONES c on a.XX_PROMOCIONES_ID = c.XX_PROMOCIONES_ID " +
											"WHERE a.xx_promociones_id="+codigoP+
											" UNION " +
											"SELECT a.XX_PROMOCIONES_ID, a.XX_COMBO_ID, a.PORCDESCUENTO" +
											", (select value from M_PRODUCT where M_product_id = a.xx_CATEGORIA_id) as CATEGORIA" +
											", (select value from M_PRODUCT where M_product_id = b.xx_departamento_id) as departamento" +
											", (select value from M_PRODUCT where M_product_id = b.xx_linea_id) as linea" +
											", b.value as seccion, null, null, null, a.CANTIDADPRODUCTO, a.NROPAGAR, c.NAME, a.grupo, a.TIENDA, a.ISACTIVE" +
											", a.REGACTUALIZADO  FROM XX_COMBO a inner join XX_PROMOCIONES c on " +
											"a.XX_PROMOCIONES_ID = c.XX_PROMOCIONES_ID inner join M_Product b on a.xx_linea_id=b.xx_linea_id " +
											"and b.xx_tipoproducto=4000000 and a.xx_seccion_id is null and a.xx_linea_id is not NULL " +
											"WHERE a.xx_promociones_id="+codigoP+
											" UNION " +
											"SELECT a.XX_PROMOCIONES_ID, a.XX_COMBO_ID, a.PORCDESCUENTO" +
											", (select value from M_PRODUCT where M_product_id = a.xx_CATEGORIA_id) as CATEGORIA" +
											", (select value from M_PRODUCT where M_product_id = b.xx_departamento_id) as departamento" +
											", (select value from M_PRODUCT where M_product_id = b.xx_linea_id) as linea, b.value as seccion, null, null, null" +
											", a.CANTIDADPRODUCTO, a.NROPAGAR, c.NAME, a.grupo, a.TIENDA, a.ISACTIVE, a.REGACTUALIZADO  " +
											"FROM XX_COMBO a inner join XX_PROMOCIONES c on a.XX_PROMOCIONES_ID = c.XX_PROMOCIONES_ID " +
											"inner join M_Product b on a.xx_departamento_id=b.xx_departamento_id and b.xx_tipoproducto=4000000 " +
											"and a.xx_linea_id is null and a.xx_departamento_id is not null " +
											"WHERE a.xx_promociones_id="+codigoP+
											" UNION " +
											"SELECT a.XX_PROMOCIONES_ID, a.XX_COMBO_ID, a.PORCDESCUENTO" +
											", (select value from M_PRODUCT where M_product_id = a.xx_CATEGORIA_id) as CATEGORIA" +
											", (select value from M_PRODUCT where M_product_id = b.xx_departamento_id) as departamento" +
											", (select value from M_PRODUCT where M_product_id = b.xx_linea_id) as linea  , b.value as seccion, null, null, null" +
											", a.CANTIDADPRODUCTO, a.NROPAGAR, c.NAME, a.grupo, a.TIENDA, a.ISACTIVE, a.REGACTUALIZADO  " +
											"FROM XX_COMBO a inner join XX_PROMOCIONES c on a.XX_PROMOCIONES_ID = c.XX_PROMOCIONES_ID " +
											"inner join M_Product b on a.xx_categoria_id=b.xx_categoria_id and b.xx_tipoproducto=4000000 " +
											"and a.xx_linea_id is null and a.xx_departamento_id is null " +
											"WHERE a.xx_promociones_id="+codigoP;
									break;
								case 5: //referencia, marca, producto
									query="SELECT a.XX_PROMOCIONES_ID, a.XX_COMBO_ID, a.PORCDESCUENTO" +
											", (select value from M_PRODUCT where M_product_id = a.xx_CATEGORIA_id) as CATEGORIA" +
											", (select value from M_PRODUCT where M_product_id = a.xx_departamento_id) as departamento" +
											", (select value from M_PRODUCT where M_product_id = a.xx_linea_id) as linea" +
											", (select value from M_PRODUCT where M_product_id = a.xx_seccion_id) as seccion" +
											", (select value from M_PRODUCT where M_product_id = a.xx_producto_id) as producto" +
											", (select XX_REFPROV from M_PRODUCT where M_product_id = a.xx_producto_id) as referencia" +
											", (select xx_marca from M_PRODUCT where M_product_id = a.xx_producto_id) as marca" +
											", a.CANTIDADPRODUCTO, a.NROPAGAR, c.NAME, a.grupo, a.TIENDA, a.ISACTIVE, a.REGACTUALIZADO  " +
											"FROM XX_COMBO a inner join XX_PROMOCIONES c on a.XX_PROMOCIONES_ID = c.XX_PROMOCIONES_ID " +
											"WHERE a.xx_promociones_id=" +codigoP+
											" union " +
											"SELECT a.XX_PROMOCIONES_ID, a.XX_COMBO_ID, a.PORCDESCUENTO" +
											", (select value from M_PRODUCT where M_product_id = a.xx_CATEGORIA_id) as CATEGORIA" +
											", (select value from M_PRODUCT where M_product_id = a.xx_departamento_id) as departamento" +
											", (select value from M_PRODUCT where M_product_id = a.xx_linea_id) as linea" +
											", (select value from M_PRODUCT where M_product_id = a.xx_seccion_id) as seccion" +
											", b.value" +
											", (Select XX_REFPROV from M_Product where m_product_id=a.XX_REFERENCIAPROVEEDOR_ID) as referencia" +
											", (select xx_marca from M_PRODUCT where M_product_id = b.m_product_id) as marca" +
											", a.CANTIDADPRODUCTO, a.NROPAGAR, c.NAME, a.grupo, a.TIENDA, a.ISACTIVE, a.REGACTUALIZADO  " +
											"FROM XX_COMBO a inner join XX_PROMOCIONES c on a.XX_PROMOCIONES_ID = c.XX_PROMOCIONES_ID " +
											"inner join m_product b on b.xx_categoria_id=a.xx_categoria_id " +
											"and b.xx_departamento_id=a.xx_departamento_id and b.xx_linea_id=a.xx_linea_id " +
											"and b.xx_seccion_id=a.xx_seccion_id and b.XX_REFPROV=" +
											"(Select XX_REFPROV from M_Product where m_product_id=a.XX_REFERENCIAPROVEEDOR_ID) " +
											"WHERE a.xx_promociones_id=" +codigoP+
											" union " +
											"SELECT a.XX_PROMOCIONES_ID, a.XX_COMBO_ID, a.PORCDESCUENTO" +
											", (select value from M_PRODUCT where M_product_id = a.xx_CATEGORIA_id) as CATEGORIA" +
											", (select value from M_PRODUCT where M_product_id = a.xx_departamento_id) as departamento" +
											", (select value from M_PRODUCT where M_product_id = a.xx_linea_id) as linea" +
											", (select value from M_PRODUCT where M_product_id = a.xx_seccion_id) as seccion" +
											", (select value from M_PRODUCT where M_product_id = b.m_product_id) as producto" +
											", (select XX_REFPROV from M_PRODUCT where M_product_id = b.m_product_id) as referencia" +
											", (select XX_MARCA from M_PRODUCT where M_product_id = b.m_product_id) as marca" +
											", a.CANTIDADPRODUCTO, a.NROPAGAR, c.NAME, a.grupo, a.TIENDA, a.ISACTIVE " +
											", a.REGACTUALIZADO  FROM XX_COMBO a inner join XX_PROMOCIONES c on a.XX_PROMOCIONES_ID = c.XX_PROMOCIONES_ID" +
											" inner join m_product b on a.XX_MARCA=b.XX_MARCA and b.xx_categoria_id=a.xx_categoria_id " +
											"and b.xx_departamento_id=a.xx_departamento_id and b.xx_linea_id=a.xx_linea_id and b.xx_seccion_id=a.xx_seccion_id " +
											"WHERE a.xx_promociones_id="+codigoP+
											" UNION " +
											"SELECT a.XX_PROMOCIONES_ID, a.XX_COMBO_ID, a.PORCDESCUENTO" +
											", (select value from M_PRODUCT where M_product_id = a.xx_CATEGORIA_id) as CATEGORIA" +
											", (select value from M_PRODUCT where M_product_id = b.xx_departamento_id) as departamento" +
											", (select value from M_PRODUCT where M_product_id = b.xx_linea_id) as linea" +
											", (select value from M_PRODUCT where M_product_id = b.xx_seccion_id) as seccion" +
											", b.value as producto" +
											", (select XX_REFPROV from M_PRODUCT where M_product_id = b.m_product_id) as referencia" +
											", (select XX_MARCA from M_PRODUCT where M_product_id = b.m_product_id) as marca " +
											", a.CANTIDADPRODUCTO, a.NROPAGAR, c.NAME, a.grupo, a.TIENDA, a.ISACTIVE, a.REGACTUALIZADO  " +
											"FROM XX_COMBO a inner join XX_PROMOCIONES c on a.XX_PROMOCIONES_ID = c.XX_PROMOCIONES_ID " +
											"inner join M_Product b on a.xx_seccion_id=b.xx_seccion_id and b.xx_tipoproducto=5000000 " +
											"and a.xx_producto_id is NULL and issummary='Y'" +
											"WHERE a.xx_promociones_id="+codigoP+
											" UNION " +
											"SELECT a.XX_PROMOCIONES_ID, a.XX_COMBO_ID, a.PORCDESCUENTO" +
											", (select value from M_PRODUCT where M_product_id = a.xx_CATEGORIA_id) as CATEGORIA" +
											", (select value from M_PRODUCT where M_product_id = b.xx_departamento_id) as departamento" +
											", (select value from M_PRODUCT where M_product_id = b.xx_linea_id) as linea" +
											", (select value from M_PRODUCT where M_product_id = b.xx_seccion_id) as seccion  " +
											", b.value as producto" +
											", (select XX_REFPROV from M_PRODUCT where M_product_id = b.m_product_id) as referencia" +
											", (select XX_MARCA from M_PRODUCT where M_product_id = b.m_product_id) as marca" +
											", a.CANTIDADPRODUCTO, a.NROPAGAR, c.NAME, a.grupo, a.TIENDA, a.ISACTIVE, a.REGACTUALIZADO  " +
											"FROM XX_COMBO a inner join XX_PROMOCIONES c on a.XX_PROMOCIONES_ID = c.XX_PROMOCIONES_ID " +
											"inner join M_Product b on a.xx_linea_id=b.xx_linea_id and b.xx_tipoproducto=5000000 " +
											"and a.xx_seccion_id is null and issummary='Y'" +
											"WHERE a.xx_promociones_id=" +codigoP+
											" UNION " +
											"SELECT a.XX_PROMOCIONES_ID, a.XX_COMBO_ID, a.PORCDESCUENTO" +
											", (select value from M_PRODUCT where M_product_id = a.xx_CATEGORIA_id) as CATEGORIA" +
											", (select value from M_PRODUCT where M_product_id = b.xx_departamento_id) as departamento" +
											", (select value from M_PRODUCT where M_product_id = b.xx_linea_id) as linea  " +
											", (select value from M_PRODUCT where M_product_id = b.xx_seccion_id) as seccion  " +
											", b.value as producto" +
											", (select XX_REFPROV from M_PRODUCT where M_product_id = b.m_product_id) as referencia" +
											", (select XX_MARCA from M_PRODUCT where M_product_id = b.m_product_id) as marca" +
											", a.CANTIDADPRODUCTO, a.NROPAGAR, c.NAME, a.grupo, a.TIENDA, a.ISACTIVE, a.REGACTUALIZADO  " +
											"FROM XX_COMBO a inner join XX_PROMOCIONES c on a.XX_PROMOCIONES_ID = c.XX_PROMOCIONES_ID " +
											"inner join M_Product b on a.xx_departamento_id=b.xx_departamento_id and b.xx_tipoproducto=5000000 and a.xx_linea_id is null " +
											"and a.xx_seccion_id is null and issummary='Y' " +
											"WHERE a.xx_promociones_id="+codigoP+
											" UNION " +
											"SELECT a.XX_PROMOCIONES_ID, a.XX_COMBO_ID, a.PORCDESCUENTO" +
											", (select value from M_PRODUCT where M_product_id = a.xx_CATEGORIA_id) as CATEGORIA" +
											", (select value from M_PRODUCT where M_product_id = b.xx_departamento_id) as departamento" +
											", (select value from M_PRODUCT where M_product_id = b.xx_linea_id) as linea  " +
											", (select value from M_PRODUCT where M_product_id = b.xx_seccion_id) as seccion" +
											", b.value" +
											", (select XX_REFPROV from M_PRODUCT where M_product_id = b.m_product_id) as referencia" +
											", (select XX_MARCA from M_PRODUCT where M_product_id = b.m_product_id) as marca" +
											", a.CANTIDADPRODUCTO, a.NROPAGAR, c.NAME, a.grupo, a.TIENDA, a.ISACTIVE, a.REGACTUALIZADO  " +
											"FROM XX_COMBO a inner join XX_PROMOCIONES c on a.XX_PROMOCIONES_ID = c.XX_PROMOCIONES_ID " +
											"inner join M_Product b on a.xx_categoria_id=b.xx_categoria_id and b.xx_tipoproducto=5000000 and a.xx_linea_id is null " +
											"and a.xx_departamento_id is null and a.xx_seccion_id is null and issummary='Y' " +
											"WHERE a.xx_promociones_id="+codigoP;
									break;
							}
							sentenciaOXE=mBD.getStatement();
							resultado= mBD.realizarConsultaOXE(query, sentenciaOXE);
							resultado.beforeFirst();
							while(resultado.next()){	
								try{
									int codigo = resultado.getInt("XX_PROMOCIONES_ID");
									int detalleC = resultado.getInt("XX_COMBO_ID");
									double porcdescuento = resultado.getDouble("PORCDESCUENTO");
									int categoria = resultado.getInt("categoria");
									int departamento = resultado.getInt("departamento")%100;
									String marca = resultado.getString("marca");
									String refproveedor = resultado.getString("referencia");
									int linea = resultado.getInt("linea")%100;
									int seccion = resultado.getInt("seccion")%100;
									int cantidadproducto = resultado.getInt("CANTIDADPRODUCTO");
									String producto = resultado.getString("producto");
									int cantproddesc = resultado.getInt("NROPAGAR");
									String name = resultado.getString("NAME");
									int grupo =resultado.getInt("grupo");
									String tiendas = resultado.getString("TIENDA");
									String activo = resultado.getString("ISACTIVE");
									String regactualizado = resultado.getString("REGACTUALIZADO");
									name=limpiarNombre(name);
									a.add(new DetallePromocionExt(codigo, detalleC, porcdescuento, categoria, departamento, marca, refproveedor
											, linea, seccion, 0, cantidadproducto, producto, cantproddesc, 0, name, 
											grupo, tiendas, activo, 0, null, regactualizado, regactualizado));
								}catch(ArithmeticException f){f.printStackTrace();}
							}
							//resultado.close();
							//sentenciaOXE.close();
							Iterator<DetallePromocionExt> it = a.iterator();
	
							while (it.hasNext()){
								DetallePromocionExt dpe = it.next();
								Iterator<Integer> j = promocionesInactivas.iterator();
								while (j.hasNext()){
									int x = (j.next()).intValue();
									if(dpe.getCodigo()==x)
										dpe.setActivo("N");
									mBD.realizarSentenciaOXE("update XX_PromProductoDetalleCaja set regactualizado='Y', isactive='N' where XX_PROMOCIONES_ID = "+x);
								}
	
								if (dpe.getRegactualizado().equals("N") || !caso){
									if (dpe.getProducto() == null){
										if (dpe.getTienda().equals(tienda)||dpe.getTienda().equals("00"))
											if(dpe.getTipoPromocion()==1001200)
												detallePromocionExt += dpe.getCodigo()+"["+dpe.getDetalle()+"["+dpe.getPorcdescuento()+"["+dpe.getCategoria()
												+"["+relleno.format(dpe.getDepartamento())+"["+((dpe.getMarca()==null)?"NULL":dpe.getMarca())+"["+relleno.format(dpe.getLinea())+"["
												+((dpe.getRefproveedor()==null)?"NULL":dpe.getRefproveedor())+"["+dpe.getMontominimo()+"["+dpe.getCantidadproducto()
												+"["+dpe.getCantproddesc()+"["+((dpe.getProducto()==null)?"NULL":dpe.getProducto())+"["+dpe.getBsbonoregalo()
												+"["+dpe.getActivo()+"["+dpe.getRegalo()+"["+dpe.getGrupo()+"["+relleno.format(dpe.getSeccion())+"[NULL[\r\n";
											else
												detallePromocionExt += dpe.getCodigo()+"["+dpe.getDetalle()+"["+dpe.getPorcdescuento()+"["+dpe.getCategoria()
												+"["+relleno.format(dpe.getDepartamento())+"["+((dpe.getMarca()==null)?"NULL":dpe.getMarca())+"["+relleno.format(dpe.getLinea())+"["
												+((dpe.getRefproveedor()==null)?"NULL":dpe.getRefproveedor())+"["+dpe.getMontominimo()+"["+dpe.getCantidadproducto()
												+"["+dpe.getCantproddesc()+"["+((dpe.getProducto()==null)?"NULL":dpe.getProducto())+"["+dpe.getBsbonoregalo()
												+"["+dpe.getActivo()+"["+dpe.getName()+"["+dpe.getGrupo()+"["+relleno.format(dpe.getSeccion())+"[NULL[\r\n";
									}else{
										try{
											sentenciaOXE=mBD.getStatement();
											ResultSet c =mBD.realizarConsultaOXE("select max(detallepromocion) as max from XX_PromProductoDetalleCaja where xx_promociones_id = "+dpe.getCodigo(), sentenciaOXE);
											c.beforeFirst();
											if (c.first()) n = c.getInt("max");
											c.close();
											sentenciaOXE.close();
										}catch(Exception e){n=0;}
										if (dpe.getTienda().equals(tienda)||dpe.getTienda().equals("00")){
												
											query = "select value from m_product a where issummary='N' and a.value like '"+dpe.getProducto()+"___' " +
													"and a.value not in (select value from XX_PromProductoDetalleCaja where xx_promociones_id="+dpe.getCodigo()+
													" and value=a.value and isactive='N' and regactualizado='Y')";
											sentenciaOXE=mBD.getStatement();
											resultado= mBD.realizarConsultaOXE(query, sentenciaOXE);
											resultado.beforeFirst();
											while (resultado.next()){
												n++;
												String consecutivo=resultado.getString("value");
												String sentencia="insert into XX_PromProductoDetalleCaja (CREATED, CREATEDBY, UPDATED, UPDATEDBY, AD_CLIENT_ID, AD_ORG_ID" +
													", XX_PROMOCIONES_ID, DETALLEPROMOCION, PORCDESCUENTO, CATEGORIA, DEPARTAMENTO" +
													", LINEA, SECCION, VALUE, XX_REFPROV, XX_MARCA, CANTIDADPRODUCTO, NROPAGAR, NAME" +
													", GRUPO, TIENDA, ISACTIVE, regactualizado)  " +
													"select a.CREATED, a.CREATEDBY, a.UPDATED, a.UPDATEDBY, a.AD_CLIENT_ID, a.AD_ORG_ID, " +
													dpe.getCodigo()+", "+n+", "+dpe.getPorcdescuento()+", "+dpe.getCategoria()+", "+
													dpe.getDepartamento()+", "+dpe.getLinea()+", "+dpe.getSeccion()+", '"+consecutivo+"', '" +
													dpe.getRefproveedor()+"', '"+dpe.getMarca()+"', "+dpe.getCantidadproducto()+", "+
													dpe.getPorcdescuento()+", '"+dpe.getName()+"', "+dpe.getGrupo()+", '"+dpe.getTienda()+"', '"+
													dpe.getActivo1()+"', '"+dpe.getRegactualizado()+
													"' from xx_promociones a " +
													"where a.xx_promociones_id="+dpe.getCodigo();
												try{
													mBD.realizarSentenciaOXE(sentencia);
												}catch(SQLException e){;}
											//
											
											//resultado.close();
											}
										sentenciaOXE.close();
										resultado.close();
											
											Statement sentenciaOXE1=mBD.getStatement();
											query ="select * from XX_PromProductoDetalleCaja where " +
											"xx_promociones_id = "+dpe.getCodigo()+" and TIENDA='"+dpe.getTienda()+
											"' and value like '"+dpe.getProducto()+"___'";
											resultado = mBD.realizarConsultaOXE(query, sentenciaOXE1);	
											while(resultado.next()){
												try{
													int codigo = resultado.getInt("XX_PROMOCIONES_ID");
													int detalleC = resultado.getInt("DETALLEPROMOCION");
													double porcdescuento = resultado.getDouble("PORCDESCUENTO");
													int categoria = resultado.getInt("categoria");
													int departamento = resultado.getInt("departamento")%100;
													String marca = resultado.getString("XX_MARCA");
													String refproveedor = resultado.getString("XX_REFPROV");
													int linea = resultado.getInt("linea")%100;
													int seccion = resultado.getInt("seccion")%100;
													double montominimo = resultado.getDouble("montopremiado");
													int cantidadproducto = resultado.getInt("CANTIDADPRODUCTO");
													String producto = resultado.getString("VALUE");
													int cantproddesc = resultado.getInt("NROPAGAR");
													double bsbonoregalo = resultado.getDouble("BSBONOREGALO");
													String name = resultado.getString("NAME");
													int grupo =resultado.getInt("grupo");
													name=limpiarNombre(name);
													detallePromocionExt += codigo+"["+detalleC+"["+porcdescuento+"["+categoria+"["+relleno.format(departamento)
													+"["+((marca==null)?"NULL":marca)+"["+relleno.format(linea)+"["+((refproveedor==null)?"NULL":refproveedor)
													+"["+montominimo+"["+cantidadproducto+"["+cantproddesc+"["+((producto==null)?"NULL":producto)
													+"["+bsbonoregalo+"["+dpe.getActivo()+"["+name+"["+grupo+"["+seccion+"[NULL[\r\n";
													listaPromocionesPorActualizar.add(new PromocionesPorProducto(dpe.getCodigo(), detalleC, true));
												}catch(ArithmeticException f){f.printStackTrace();}
											}
											//resultado.close();
											//sentenciaOXE1.close();
										}
									}
								}
							}
						} else if (tipo.equals("1000800")|| tipo.equals("1001000")||tipo.equals("1001400")){
							query="SELECT a.XX_PROMOCIONES_ID, a.XX_ENTREGABLES_ID, a.MONTOPREMIADO, a.BSBONOREGALO, b.NAME" +
								", a.TIENDA, a.ISACTIVE, a.REGACTUALIZADO FROM XX_ENTREGABLES a inner join XX_PROMOCIONES b on " +
								"a.XX_PROMOCIONES_ID=b.XX_PROMOCIONES_ID WHERE b.XX_PROMOCIONES_ID= "+codigoP;
							sentenciaOXE=mBD.getStatement();
							resultado= mBD.realizarConsultaOXE(query, sentenciaOXE);
							resultado.beforeFirst();
							while(resultado.next()){	
								try{
									int codigo = resultado.getInt("XX_PROMOCIONES_ID");
									int detalleC = resultado.getInt("XX_ENTREGABLES_ID");
									double montominimo = resultado.getDouble("MONTOPREMIADO");
									double bsbonoregalo = resultado.getDouble("BSBONOREGALO");
									String name = resultado.getString("NAME");
									String tiendas = resultado.getString("TIENDA");
									String activo = resultado.getString("isactive");
									String regactualizado = resultado.getString("REGACTUALIZADO");
									name=limpiarNombre(name);
									a.add(new DetallePromocionExt(codigo, detalleC, 0, 0, 0, null, null
											, 0, 0, montominimo, 0, null, 0, bsbonoregalo, name, 
											0, tiendas, activo, 0, null, regactualizado, null));
								}catch(ArithmeticException f){f.printStackTrace();}
								
							}
							Iterator<DetallePromocionExt> it = a.iterator();
							while (it.hasNext()){
								DetallePromocionExt dpe = it.next();
								detallePromocionExt += dpe.getCodigo()+"["+dpe.getDetalle()+"["+dpe.getPorcdescuento()+"["+dpe.getCategoria()
								+"["+relleno.format(dpe.getDepartamento())+"["+((dpe.getMarca()==null)?"NULL":dpe.getMarca())+"["+relleno.format(dpe.getLinea())+"["
								+((dpe.getRefproveedor()==null)?"NULL":dpe.getRefproveedor())+"["+dpe.getMontominimo()+"["+dpe.getCantidadproducto()
								+"["+dpe.getCantproddesc()+"["+((dpe.getProducto()==null)?"NULL":dpe.getProducto())+"["+dpe.getBsbonoregalo()
								+"["+dpe.getActivo()+"["+dpe.getName()+"["+dpe.getGrupo()+"["+relleno.format(dpe.getSeccion())+"[NULL[\r\n";
							}
						}
					}catch(ArithmeticException f){f.printStackTrace();}
				}
				}else{// si el detalle es diferente de cero
					String query = "select a.xx_promociones_id, a.tipopromocion " +
						" from xx_combo a inner join xx_promociones b on a.xx_promociones_id=b.xx_promociones_id" +
						" where a.xx_promociones_id=" +codigoP+
						" union" +
						" select a.xx_promociones_id, a.tipopromocion" +
						" from XX_DESCTRANS a inner join xx_promociones b on a.xx_promociones_id=b.xx_promociones_id" +
						" where a.xx_promociones_id=" +codigoP+
						" union" +
						" select a.xx_promociones_id, a.tipopromocion" +
						" from XX_ENTREGABLES a inner join xx_promociones b on a.xx_promociones_id=b.xx_promociones_id" +
						" where a.xx_promociones_id=" +codigoP+
						" union" +
						" select a.xx_promociones_id, a.tipopromocion" +
						" from XX_REGALOPPORCOMPRA a inner join xx_promociones b on a.xx_promociones_id=b.xx_promociones_id" +
						" where a.xx_promociones_id=" +codigoP;
					Statement sentenciaOXE=mBD.getStatement();
					ResultSet resultado= mBD.realizarConsultaOXE(query, sentenciaOXE);
					resultado.beforeFirst();
					while(resultado.next()){	
						try{
							String tipo = resultado.getString("tipopromocion");
							if (tipo.equals("1000100")||tipo.equals("1000300")||tipo.equals("1001200")){
								query="SELECT a.XX_PROMOCIONES_ID, a.XX_REGALOPPORCOMPRA_ID, a.PorcDescuento" +
										", (select value from M_PRODUCT where M_product_id = a.xx_categoria_id) as categoria" +
										", (select value from M_PRODUCT where M_product_id = a.xx_departamento_id) as departamento" +
										", (select value from M_PRODUCT where M_product_id = a.xx_linea_id) as linea" +
										", a.XX_MONTOMINIMO, a.CANTPRODCOMPRAR, b.VALUE, c.NAME" +
										", (select value from M_PRODUCT where M_product_id = a.xx_seccion_id) as seccion" +
										", a.TIENDA, a.ISACTIVE, a.TipoPromocion, a.Regalo, a.REGACTUALIZADO, XX_ACUMULAPREMIO  " +
										"FROM M_PRODUCT b INNER JOIN XX_REGALOPPORCOMPRA a on  b.xx_producto_id = a.xx_producto_id and " +
										"b.ISSUMMARY like 'N' and b.ISACTIVE like 'Y' inner join XX_PROMOCIONES c on a.XX_PROMOCIONES_ID = " +
										"c.XX_PROMOCIONES_ID WHERE xx_promociones_id="+codigoP+" and XX_REGALOPPORCOMPRA_ID="+detalle;
								sentenciaOXE=mBD.getStatement();
								resultado= mBD.realizarConsultaOXE(query, sentenciaOXE);
								resultado.beforeFirst();
								while(resultado.next()){	
									try{
										int codigo = resultado.getInt("XX_PROMOCIONES_ID");
										int detalleC = resultado.getInt("XX_REGALOPPORCOMPRA_ID");
										double porcdescuento = resultado.getDouble("PORCDESCUENTO");
										int categoria = resultado.getInt("categoria");
										int departamento = resultado.getInt("departamento")%100;
										int linea = resultado.getInt("linea")%100;
										int seccion = resultado.getInt("seccion")%100;
										double montominimo = resultado.getDouble("XX_MONTOMINIMO");
										int cantidadproducto = resultado.getInt("CANTPRODCOMPRAR");
										String producto = resultado.getString("VALUE");
										String name = resultado.getString("NAME");
										String tiendas = resultado.getString("TIENDA");
										String activo = resultado.getString("isactive");
										int tipoPromocion = resultado.getInt("TipoPromocion");
										String regalo = resultado.getString("Regalo");
										String regactualizado = resultado.getString("REGACTUALIZADO");
										String acumulado =resultado.getString("XX_ACUMULAPREMIO");
										name=limpiarNombre(name);
										a.add(new DetallePromocionExt(codigo, detalleC, porcdescuento, categoria, departamento, null, null
												, linea, seccion, montominimo, cantidadproducto, producto, 0, 0, name, 
												1, tiendas, activo, tipoPromocion, regalo, regactualizado, acumulado));
									}catch(ArithmeticException f){f.printStackTrace();}
								}
								//resultado.close();
								//sentenciaOXE.close();
			
								resultado.beforeFirst();
								Iterator<DetallePromocionExt> iterator = a.iterator();
								while (iterator.hasNext()){
									DetallePromocionExt dpe = iterator.next();
									try{
										sentenciaOXE=mBD.getStatement();
										ResultSet c =mBD.realizarConsultaOXE("select max(detallepromocion) as max from XX_PromProductoDetalleCaja where xx_promociones_id = "+dpe.getCodigo(), sentenciaOXE);
										c.beforeFirst();
										if (c.first()) 
											n = c.getInt("max");
										c.close();
										sentenciaOXE.close();
									}catch(Exception e){n=0;}
									if (dpe.getActivo().equals("A")){
										if (dpe.getTienda().equals(tienda)||dpe.getTienda().equals("00")){
											n++;
											String sentencia="insert into XX_PromProductoDetalleCaja (CREATED, CREATEDBY, UPDATED, UPDATEDBY" +
											", AD_CLIENT_ID, AD_ORG_ID, XX_PROMOCIONES_ID, DETALLEPROMOCION, PORCDESCUENTO, CATEGORIA, DEPARTAMENTO" +
											", XX_MARCA, LINEA, XX_REFPROV, MONTOPREMIADO, CANTIDADPRODUCTO, NROPAGAR, VALUE, BSBONOREGALO, NAME" +
											", GRUPO, SECCION, TIENDA, ISACTIVE, REGALO, regactualizado, XX_ACUMULAPREMIO) " +
											"SELECT  a.CREATED, a.CREATEDBY, a.UPDATED, a.UPDATEDBY, a.AD_CLIENT_ID, a.AD_ORG_ID" +
											", a.XX_PROMOCIONES_ID, " +n+", a.PorcDescuento, (select value from M_PRODUCT where M_product_id = a.xx_categoria_id) " +
											"as categoria, (select value from M_PRODUCT where M_product_id = a.xx_departamento_id) as departamento, null" +
											", (select value from M_PRODUCT where M_product_id = a.xx_linea_id) as linea, null, a.XX_MONTOMINIMO" +
											", a.CANTPRODCOMPRAR, null, b.VALUE, NULL, c.NAME, 1, (select value from M_PRODUCT where M_product_id " +
											"= a.xx_seccion_id) as seccion, a.TIENDA, '"+dpe.getActivo1()+"', a.Regalo, '"+dpe.getRegactualizado()+"', a.XX_ACUMULAPREMIO FROM M_PRODUCT b INNER JOIN " +
											"XX_REGALOPPORCOMPRA a on  b.xx_producto_id = a.xx_producto_id and b.ISSUMMARY like 'N' and b.ISACTIVE like 'Y' " +
											"inner join XX_PROMOCIONES c on a.XX_PROMOCIONES_ID = c.XX_PROMOCIONES_ID AND c.xx_promociones_id = "+
											dpe.getCodigo()+" AND b.VALUE like '" +dpe.getProducto()+"' and a.regactualizado<>a.isactive and (b.VALUE not in " +
											"(Select VALUE from XX_PROMPRODUCTODETALLECAJA where xx_promociones_id='"+dpe.getCodigo()+"' and isactive<>regactualizado) or a.isactive='N')";
											try{
												mBD.realizarSentenciaOXE(sentencia);
											}catch(SQLException e){;}
											Statement sentenciaOXE1=mBD.getStatement();
											String consulta="";
											consulta="select * from XX_PromProductoDetalleCaja where xx_promociones_id = "+dpe.getCodigo()+
												" and (TIENDA='"+dpe.getTienda()+"' or TIENDA='00') and value = '"+dpe.getProducto()+"'";
											resultado = mBD.realizarConsultaOXE(consulta, sentenciaOXE1);	
											while(resultado.next()){
												try{
													int codigo = resultado.getInt("XX_PROMOCIONES_ID");
													int detalleC = resultado.getInt("DETALLEPROMOCION");
													double porcdescuento = resultado.getDouble("PORCDESCUENTO");
													int categoria = resultado.getInt("categoria");
													int departamento = resultado.getInt("departamento")%100;
													String marca = resultado.getString("XX_MARCA");
													String refproveedor = resultado.getString("XX_REFPROV");
													int linea = resultado.getInt("linea")%100;
													int seccion = resultado.getInt("seccion")%100;
													double montominimo = resultado.getDouble("montopremiado");
													int cantidadproducto = resultado.getInt("CANTIDADPRODUCTO");
													String producto = resultado.getString("VALUE");
													int cantproddesc = resultado.getInt("NROPAGAR");
													double bsbonoregalo = resultado.getDouble("BSBONOREGALO");
													String name = resultado.getString("NAME");
													int grupo =resultado.getInt("grupo");
													String acumular = resultado.getString("XX_acumulaPremio");
													name=limpiarNombre(name);
													detallePromocionExt += codigo+"["+detalleC+"["+porcdescuento+"["+categoria+"["+relleno.format(departamento)
													+"["+((marca==null)?"NULL":marca)+"["+relleno.format(linea)+"["+((refproveedor==null)?"NULL":refproveedor)
													+"["+montominimo+"["+cantidadproducto+"["+cantproddesc+"["+((producto==null)?"NULL":producto)
													+"["+bsbonoregalo+"["+dpe.getActivo()+"["+name+"["+grupo+"["+seccion+"["+acumular+"[\r\n";
													listaPromocionesPorActualizar.add(new PromocionesPorProducto(dpe.getCodigo(), detalleC, true));
												}catch(ArithmeticException f){f.printStackTrace();}
											}
											//resultado.close();
											//sentenciaOXE1.close();
										}
									}
								}
							}else if(tipo.equals("1000200")||tipo.equals("1000750")||tipo.equals("1000900")||tipo.equals("1001100")){
								query="SELECT a.XX_PROMOCIONES_ID, a.xx_DESCTRANS_id, a.PORCDESCUENTO, null, null, null, null, null, a.MONTOMINIMO" +
										",a.CantidadProducto, b.NAME, a.TIENDA, a.ISACTIVE, a.REGACTUALIZADO  FROM XX_DESCTRANS a inner join " +
										"XX_PROMOCIONES b on a.XX_PROMOCIONES_ID = b.XX_PROMOCIONES_ID WHERE xx_promociones_id="+codigoP+" and xx_DESCTRANS_id"+detalle;
								sentenciaOXE=mBD.getStatement();
								resultado= mBD.realizarConsultaOXE(query, sentenciaOXE);
								resultado.beforeFirst();
								while(resultado.next()){	
									try{
										int codigo = resultado.getInt("XX_PROMOCIONES_ID");
										int detalleC = resultado.getInt("xx_DESCTRANS_id");
										double porcdescuento = resultado.getDouble("PORCDESCUENTO");
										double montominimo = resultado.getDouble("XX_MONTOMINIMO");
										int cantidadproducto = resultado.getInt("CantidadProducto");
										String name = resultado.getString("NAME");
										String tiendas = resultado.getString("TIENDA");
										String activo = resultado.getString("activo");
										String regactualizado = resultado.getString("REGACTUALIZADO");
										name=limpiarNombre(name);
										a.add(new DetallePromocionExt(codigo, detalleC, porcdescuento, 0, 0, null, null
												, 0, 0, montominimo, cantidadproducto, null, 0, 0, name, 1, tiendas, activo
												, Integer.parseInt(tipo)
												, null, regactualizado, null));
									}catch(ArithmeticException f){f.printStackTrace();}
								}
								resultado.close();
								sentenciaOXE.close();
								Iterator<DetallePromocionExt> it = a.iterator();
								while (it.hasNext()){
									DetallePromocionExt dpe = it.next();
									detallePromocionExt += dpe.getCodigo()+"["+dpe.getDetalle()+"["+dpe.getPorcdescuento()+"["+dpe.getCategoria()
									+"["+relleno.format(dpe.getDepartamento())+"["+((dpe.getMarca()==null)?"NULL":dpe.getMarca())
									+"["+relleno.format(dpe.getLinea())+"["+((dpe.getRefproveedor()==null)?"NULL":dpe.getRefproveedor())
									+"["+dpe.getMontominimo()+"["+dpe.getCantidadproducto()
									+"["+dpe.getCantproddesc()+"["+((dpe.getProducto()==null)?"NULL":dpe.getProducto())+"["+dpe.getBsbonoregalo()
									+"["+dpe.getActivo()+"["+dpe.getName()+"["+dpe.getGrupo()+"["+relleno.format(dpe.getSeccion())+"[NULL[\r\n";
								}		
							} else if (tipo.equals("1000500")||tipo.equals("1000600")||tipo.equals("1001300")){
								int seleccion=getCriterioSeleccion(codigoP);
								switch (seleccion){
									case 1: //Categorias
										query =	"SELECT a.XX_PROMOCIONES_ID, a.XX_COMBO_ID, a.PORCDESCUENTO" +
												", (select value from M_PRODUCT where M_product_id = a.xx_categoria_id) as categoria" +
												", null as departamento, null as linea, null as seccion, null as producto, null as referencia, null as marca" +
												", a.CANTIDADPRODUCTO, a.NROPAGAR, c.NAME, a.grupo, a.TIENDA, a.ISACTIVE, a.REGACTUALIZADO  " +
												"FROM XX_COMBO a inner join XX_PROMOCIONES c on a.XX_PROMOCIONES_ID = c.XX_PROMOCIONES_ID " +
												"WHERE xx_promociones_id="+codigoP+" and XX_COMBO_ID="+detalle;
										break;
									case 2: // Departamentos
										query =	"SELECT a.XX_PROMOCIONES_ID, a.XX_COMBO_ID, a.PORCDESCUENTO" +
												", (select value from M_PRODUCT where M_product_id = a.xx_CATEGORIA_id) as categoria" +
												", (select value from M_PRODUCT where M_product_id = a.xx_departamento_id) as departamento" +
												", null as linea, null as seccion, null as producto, null as referencia, null as marca" +
												", a.CANTIDADPRODUCTO, a.NROPAGAR, c.NAME, a.grupo, a.TIENDA, a.ISACTIVE" +
												", a.REGACTUALIZADO  FROM XX_COMBO a inner join XX_PROMOCIONES c on a.XX_PROMOCIONES_ID = c.XX_PROMOCIONES_ID " +
												"WHERE a.xx_promociones_id= "+codigoP+" and XX_COMBO_ID="+detalle+
												" UNION " +
												"SELECT a.XX_PROMOCIONES_ID, a.XX_COMBO_ID, a.PORCDESCUENTO" +
												", (select value from M_PRODUCT where M_product_id = a.xx_CATEGORIA_id) as categoria" +
												", b.value as departamento, null, null, null, null, null, a.CANTIDADPRODUCTO, a.NROPAGAR, c.NAME, a.grupo, a.TIENDA" +
												", a.ISACTIVE, a.REGACTUALIZADO FROM XX_COMBO a inner join XX_PROMOCIONES c " +
												"on a.XX_PROMOCIONES_ID = c.XX_PROMOCIONES_ID inner join M_Product b on " +
												"a.xx_categoria_id=b.xx_categoria_id and b.xx_tipoproducto=2000000 and a.xx_departamento_id is null " +
												"WHERE a.xx_promociones_id="+codigoP+" and XX_COMBO_ID="+detalle;
										break;
									case 3: //Lineas
										query=	"SELECT a.XX_PROMOCIONES_ID, a.XX_COMBO_ID, a.PORCDESCUENTO" +
												", (select value from M_PRODUCT where M_product_id = a.xx_CATEGORIA_id) as categoria " +
												", (select value from M_PRODUCT where M_product_id = a.xx_departamento_id) as departamento " +
												", (select value from M_PRODUCT where M_product_id = a.xx_linea_id) as linea" +
												", null as seccion, null as producto, null as referencia, null as marca " +
												", a.CANTIDADPRODUCTO, a.NROPAGAR, c.NAME, a.grupo, a.TIENDA, a.ISACTIVE " +
												", a.REGACTUALIZADO  FROM XX_COMBO a inner join XX_PROMOCIONES c on a.XX_PROMOCIONES_ID = c.XX_PROMOCIONES_ID " +
												"WHERE a.xx_promociones_id="+codigoP+" and XX_COMBO_ID="+detalle+
												" UNION " +
												"SELECT a.XX_PROMOCIONES_ID, a.XX_COMBO_ID, a.PORCDESCUENTO " +
												", (select value from M_PRODUCT where M_product_id = a.xx_CATEGORIA_id) as CATEGORIA " +
												", (select value from M_PRODUCT where M_product_id = b.xx_departamento_id) as departamento " +
												", b.value as linea, null, null, null, null, a.CANTIDADPRODUCTO, a.NROPAGAR, c.NAME, a.grupo, a.TIENDA, a.ISACTIVE " +
												", a.REGACTUALIZADO  FROM XX_COMBO a inner join XX_PROMOCIONES c " +
												"on a.XX_PROMOCIONES_ID = c.XX_PROMOCIONES_ID inner join M_Product b " +
												"on a.xx_departamento_id=b.xx_departamento_id and b.xx_tipoproducto=3000000 and a.xx_linea_id is null " +
												"WHERE a.xx_promociones_id="+codigoP+" and XX_COMBO_ID="+detalle+
												" UNION " +
												"SELECT a.XX_PROMOCIONES_ID, a.XX_COMBO_ID, a.PORCDESCUENTO " +
												", (select value from M_PRODUCT where M_product_id = a.xx_CATEGORIA_id) as CATEGORIA " +
												", (select value from M_PRODUCT where M_product_id = b.xx_departamento_id) as departamento " +
												", b.value as linea, null, null, null, null, a.CANTIDADPRODUCTO, a.NROPAGAR, c.NAME, a.grupo, a.TIENDA, a.ISACTIVE " +
												", a.REGACTUALIZADO " +
												"FROM XX_COMBO a inner join XX_PROMOCIONES c on a.XX_PROMOCIONES_ID = c.XX_PROMOCIONES_ID " +
												"inner join M_Product b on a.xx_categoria_id=b.xx_categoria_id and b.xx_tipoproducto=3000000 " +
												"and a.xx_departamento_id is null and a.xx_linea_id is null WHERE a.xx_promociones_id="+codigoP+" and XX_COMBO_ID="+detalle;
										break;
									case 4: //Secciones
										query=	"SELECT a.XX_PROMOCIONES_ID, a.XX_COMBO_ID, a.PORCDESCUENTO" +
												", (select value from M_PRODUCT where M_product_id = a.xx_CATEGORIA_id) as CATEGORIA" +
												", (select value from M_PRODUCT where M_product_id = a.xx_departamento_id) as departamento" +
												", (select value from M_PRODUCT where M_product_id = a.xx_linea_id) as linea" +
												", (select value from M_PRODUCT where M_product_id = a.xx_seccion_id) as seccion" +
												", null as producto, null as referencia, null as marca" +
												", a.CANTIDADPRODUCTO, a.NROPAGAR, c.NAME, a.grupo, a.TIENDA, a.ISACTIVE, a.REGACTUALIZADO  " +
												"FROM XX_COMBO a inner join XX_PROMOCIONES c on a.XX_PROMOCIONES_ID = c.XX_PROMOCIONES_ID " +
												"WHERE a.xx_promociones_id="+codigoP+" and XX_COMBO_ID="+detalle+
												" UNION " +
												"SELECT a.XX_PROMOCIONES_ID, a.XX_COMBO_ID, a.PORCDESCUENTO" +
												", (select value from M_PRODUCT where M_product_id = a.xx_CATEGORIA_id) as CATEGORIA" +
												", (select value from M_PRODUCT where M_product_id = b.xx_departamento_id) as departamento" +
												", (select value from M_PRODUCT where M_product_id = b.xx_linea_id) as linea" +
												", b.value as seccion, null, null, null, a.CANTIDADPRODUCTO, a.NROPAGAR, c.NAME, a.grupo, a.TIENDA, a.ISACTIVE" +
												", a.REGACTUALIZADO  FROM XX_COMBO a inner join XX_PROMOCIONES c on " +
												"a.XX_PROMOCIONES_ID = c.XX_PROMOCIONES_ID inner join M_Product b on a.xx_linea_id=b.xx_linea_id " +
												"and b.xx_tipoproducto=4000000 and a.xx_seccion_id is null and a.xx_linea_id is not NULL " +
												"WHERE a.xx_promociones_id="+codigoP+" and XX_COMBO_ID="+detalle+
												" UNION " +
												"SELECT a.XX_PROMOCIONES_ID, a.XX_COMBO_ID, a.PORCDESCUENTO" +
												", (select value from M_PRODUCT where M_product_id = a.xx_CATEGORIA_id) as CATEGORIA" +
												", (select value from M_PRODUCT where M_product_id = b.xx_departamento_id) as departamento" +
												", (select value from M_PRODUCT where M_product_id = b.xx_linea_id) as linea, b.value as seccion, null, null, null" +
												", a.CANTIDADPRODUCTO, a.NROPAGAR, c.NAME, a.grupo, a.TIENDA, a.ISACTIVE, a.REGACTUALIZADO  " +
												"FROM XX_COMBO a inner join XX_PROMOCIONES c on a.XX_PROMOCIONES_ID = c.XX_PROMOCIONES_ID " +
												"inner join M_Product b on a.xx_departamento_id=b.xx_departamento_id and b.xx_tipoproducto=4000000 " +
												"and a.xx_linea_id is null and a.xx_departamento_id is not null " +
												"WHERE a.xx_promociones_id="+codigoP+" and XX_COMBO_ID="+detalle+
												" UNION " +
												"SELECT a.XX_PROMOCIONES_ID, a.XX_COMBO_ID, a.PORCDESCUENTO" +
												", (select value from M_PRODUCT where M_product_id = a.xx_CATEGORIA_id) as CATEGORIA" +
												", (select value from M_PRODUCT where M_product_id = b.xx_departamento_id) as departamento" +
												", (select value from M_PRODUCT where M_product_id = b.xx_linea_id) as linea  , b.value as seccion, null, null, null" +
												", a.CANTIDADPRODUCTO, a.NROPAGAR, c.NAME, a.grupo, a.TIENDA, a.ISACTIVE, a.REGACTUALIZADO  " +
												"FROM XX_COMBO a inner join XX_PROMOCIONES c on a.XX_PROMOCIONES_ID = c.XX_PROMOCIONES_ID " +
												"inner join M_Product b on a.xx_categoria_id=b.xx_categoria_id and b.xx_tipoproducto=4000000 " +
												"and a.xx_linea_id is null and a.xx_departamento_id is null " +
												"WHERE a.xx_promociones_id="+codigoP+" and XX_COMBO_ID="+detalle;
										break;
									case 5: //referencia, marca, producto
										query="SELECT a.XX_PROMOCIONES_ID, a.XX_COMBO_ID, a.PORCDESCUENTO" +
												", (select value from M_PRODUCT where M_product_id = a.xx_CATEGORIA_id) as CATEGORIA" +
												", (select value from M_PRODUCT where M_product_id = a.xx_departamento_id) as departamento" +
												", (select value from M_PRODUCT where M_product_id = a.xx_linea_id) as linea" +
												", (select value from M_PRODUCT where M_product_id = a.xx_seccion_id) as seccion" +
												", (select value from M_PRODUCT where M_product_id = a.xx_producto_id) as producto" +
												", (select XX_REFPROV from M_PRODUCT where M_product_id = a.xx_producto_id) as referencia" +
												", (select xx_marca from M_PRODUCT where M_product_id = a.xx_producto_id) as marca" +
												", a.CANTIDADPRODUCTO, a.NROPAGAR, c.NAME, a.grupo, a.TIENDA, a.ISACTIVE, a.REGACTUALIZADO  " +
												"FROM XX_COMBO a inner join XX_PROMOCIONES c on a.XX_PROMOCIONES_ID = c.XX_PROMOCIONES_ID " +
												"WHERE a.xx_promociones_id=" +codigoP+" and XX_COMBO_ID="+detalle+
												" union " +
												"SELECT a.XX_PROMOCIONES_ID, a.XX_COMBO_ID, a.PORCDESCUENTO" +
												", (select value from M_PRODUCT where M_product_id = a.xx_CATEGORIA_id) as CATEGORIA" +
												", (select value from M_PRODUCT where M_product_id = a.xx_departamento_id) as departamento" +
												", (select value from M_PRODUCT where M_product_id = a.xx_linea_id) as linea" +
												", (select value from M_PRODUCT where M_product_id = a.xx_seccion_id) as seccion" +
												", b.value" +
												", (Select XX_REFPROV from M_Product where m_product_id=a.XX_REFERENCIAPROVEEDOR_ID) as referencia" +
												", (select xx_marca from M_PRODUCT where M_product_id = b.m_product_id) as marca" +
												", a.CANTIDADPRODUCTO, a.NROPAGAR, c.NAME, a.grupo, a.TIENDA, a.ISACTIVE, a.REGACTUALIZADO  " +
												"FROM XX_COMBO a inner join XX_PROMOCIONES c on a.XX_PROMOCIONES_ID = c.XX_PROMOCIONES_ID " +
												"inner join m_product b on b.xx_categoria_id=a.xx_categoria_id " +
												"and b.xx_departamento_id=a.xx_departamento_id and b.xx_linea_id=a.xx_linea_id " +
												"and b.xx_seccion_id=a.xx_seccion_id and b.XX_REFPROV=" +
												"(Select XX_REFPROV from M_Product where m_product_id=a.XX_REFERENCIAPROVEEDOR_ID) " +
												"WHERE a.xx_promociones_id=" +codigoP+" and XX_COMBO_ID="+detalle+
												" union " +
												"SELECT a.XX_PROMOCIONES_ID, a.XX_COMBO_ID, a.PORCDESCUENTO" +
												", (select value from M_PRODUCT where M_product_id = a.xx_CATEGORIA_id) as CATEGORIA" +
												", (select value from M_PRODUCT where M_product_id = a.xx_departamento_id) as departamento" +
												", (select value from M_PRODUCT where M_product_id = a.xx_linea_id) as linea" +
												", (select value from M_PRODUCT where M_product_id = a.xx_seccion_id) as seccion" +
												", (select value from M_PRODUCT where M_product_id = b.m_product_id) as producto" +
												", (select XX_REFPROV from M_PRODUCT where M_product_id = b.m_product_id) as referencia" +
												", (select XX_MARCA from M_PRODUCT where M_product_id = b.m_product_id) as marca" +
												", a.CANTIDADPRODUCTO, a.NROPAGAR, c.NAME, a.grupo, a.TIENDA, a.ISACTIVE" +
												", a.REGACTUALIZADO  FROM XX_COMBO a inner join XX_PROMOCIONES c on a.XX_PROMOCIONES_ID = c.XX_PROMOCIONES_ID" +
												" inner join m_product b on a.XX_MARCA=b.XX_MARCA and b.xx_categoria_id=a.xx_categoria_id " +
												"and b.xx_departamento_id=a.xx_departamento_id and b.xx_linea_id=a.xx_linea_id and b.xx_seccion_id=a.xx_seccion_id " +
												"WHERE a.xx_promociones_id="+codigoP+" and XX_COMBO_ID="+detalle+
												" UNION " +
												"SELECT a.XX_PROMOCIONES_ID, a.XX_COMBO_ID, a.PORCDESCUENTO" +
												", (select value from M_PRODUCT where M_product_id = a.xx_CATEGORIA_id) as CATEGORIA" +
												", (select value from M_PRODUCT where M_product_id = b.xx_departamento_id) as departamento" +
												", (select value from M_PRODUCT where M_product_id = b.xx_linea_id) as linea" +
												", (select value from M_PRODUCT where M_product_id = b.xx_seccion_id) as seccion" +
												", b.value as producto" +
												", (select XX_REFPROV from M_PRODUCT where M_product_id = b.m_product_id) as referencia" +
												", (select XX_MARCA from M_PRODUCT where M_product_id = b.m_product_id) as marca" +
												", a.CANTIDADPRODUCTO, a.NROPAGAR, c.NAME, a.grupo, a.TIENDA, a.ISACTIVE, a.REGACTUALIZADO  " +
												"FROM XX_COMBO a inner join XX_PROMOCIONES c on a.XX_PROMOCIONES_ID = c.XX_PROMOCIONES_ID " +
												"inner join M_Product b on a.xx_seccion_id=b.xx_seccion_id and b.xx_tipoproducto=5000000 " +
												"and a.xx_producto_id is NULL and issummary='Y'" +
												"WHERE a.xx_promociones_id="+codigoP+" and XX_COMBO_ID="+detalle+
												" UNION " +
												"SELECT a.XX_PROMOCIONES_ID, a.XX_COMBO_ID, a.PORCDESCUENTO" +
												", (select value from M_PRODUCT where M_product_id = a.xx_CATEGORIA_id) as CATEGORIA" +
												", (select value from M_PRODUCT where M_product_id = b.xx_departamento_id) as departamento" +
												", (select value from M_PRODUCT where M_product_id = b.xx_linea_id) as linea" +
												", (select value from M_PRODUCT where M_product_id = b.xx_seccion_id) as seccion  " +
												", b.value as producto" +
												", (select XX_REFPROV from M_PRODUCT where M_product_id = b.m_product_id) as referencia" +
												", (select XX_MARCA from M_PRODUCT where M_product_id = b.m_product_id) as marca" +
												", a.CANTIDADPRODUCTO, a.NROPAGAR, c.NAME, a.grupo, a.TIENDA, a.ISACTIVE, a.REGACTUALIZADO  " +
												"FROM XX_COMBO a inner join XX_PROMOCIONES c on a.XX_PROMOCIONES_ID = c.XX_PROMOCIONES_ID " +
												"inner join M_Product b on a.xx_linea_id=b.xx_linea_id and b.xx_tipoproducto=5000000 " +
												"and a.xx_seccion_id is null and issummary='Y'" +
												"WHERE a.xx_promociones_id=" +codigoP+" and XX_COMBO_ID="+detalle+
												"UNION " +
												"SELECT a.XX_PROMOCIONES_ID, a.XX_COMBO_ID, a.PORCDESCUENTO" +
												", (select value from M_PRODUCT where M_product_id = a.xx_CATEGORIA_id) as CATEGORIA" +
												", (select value from M_PRODUCT where M_product_id = b.xx_departamento_id) as departamento" +
												", (select value from M_PRODUCT where M_product_id = b.xx_linea_id) as linea  " +
												", (select value from M_PRODUCT where M_product_id = b.xx_seccion_id) as seccion  " +
												", b.value as producto" +
												", (select XX_REFPROV from M_PRODUCT where M_product_id = b.m_product_id) as referencia" +
												", (select XX_MARCA from M_PRODUCT where M_product_id = b.m_product_id) as marca" +
												", a.CANTIDADPRODUCTO, a.NROPAGAR, c.NAME, a.grupo, a.TIENDA, a.ISACTIVE, a.REGACTUALIZADO  " +
												"FROM XX_COMBO a inner join XX_PROMOCIONES c on a.XX_PROMOCIONES_ID = c.XX_PROMOCIONES_ID " +
												"inner join M_Product b on a.xx_departamento_id=b.xx_departamento_id and b.xx_tipoproducto=5000000 and a.xx_linea_id is null " +
												"and a.xx_seccion_id is null and issummary='Y' " +
												"WHERE a.xx_promociones_id="+codigoP+" and XX_COMBO_ID="+detalle+
												"UNION " +
												"SELECT a.XX_PROMOCIONES_ID, a.XX_COMBO_ID, a.PORCDESCUENTO" +
												", (select value from M_PRODUCT where M_product_id = a.xx_CATEGORIA_id) as CATEGORIA" +
												", (select value from M_PRODUCT where M_product_id = b.xx_departamento_id) as departamento" +
												", (select value from M_PRODUCT where M_product_id = b.xx_linea_id) as linea  " +
												", (select value from M_PRODUCT where M_product_id = b.xx_seccion_id) as seccion" +
												", b.value" +
												", (select XX_REFPROV from M_PRODUCT where M_product_id = b.m_product_id) as referencia" +
												", (select XX_MARCA from M_PRODUCT where M_product_id = b.m_product_id) as marca" +
												", a.CANTIDADPRODUCTO, a.NROPAGAR, c.NAME, a.grupo, a.TIENDA, a.ISACTIVE, a.REGACTUALIZADO  " +
												"FROM XX_COMBO a inner join XX_PROMOCIONES c on a.XX_PROMOCIONES_ID = c.XX_PROMOCIONES_ID " +
												"inner join M_Product b on a.xx_categoria_id=b.xx_categoria_id and b.xx_tipoproducto=5000000 and a.xx_linea_id is null " +
												"and a.xx_departamento_id is null and a.xx_seccion_id is null and issummary='Y' " +
												"WHERE a.xx_promociones_id="+codigoP+" and XX_COMBO_ID="+detalle;
										break;
								}
								sentenciaOXE=mBD.getStatement();
								resultado= mBD.realizarConsultaOXE(query, sentenciaOXE);
								resultado.beforeFirst();
								while(resultado.next()){	
									try{
										int codigo = resultado.getInt("XX_PROMOCIONES_ID");
										int detalleC = resultado.getInt("XX_COMBO_ID");
										double porcdescuento = resultado.getDouble("PORCDESCUENTO");
										int categoria = resultado.getInt("categoria");
										int departamento = resultado.getInt("departamento")%100;
										String marca = resultado.getString("marca");
										String refproveedor = resultado.getString("referencia");
										int linea = resultado.getInt("linea")%100;
										int seccion = resultado.getInt("seccion")%100;
										int cantidadproducto = resultado.getInt("CANTIDADPRODUCTO");
										String producto = resultado.getString("producto");
										int cantproddesc = resultado.getInt("NROPAGAR");
										String name = resultado.getString("NAME");
										int grupo =resultado.getInt("grupo");
										String tiendas = resultado.getString("TIENDA");
										String activo = resultado.getString("ISACTIVE");
										String regactualizado = resultado.getString("REGACTUALIZADO");
										name=limpiarNombre(name);
										a.add(new DetallePromocionExt(codigo, detalleC, porcdescuento, categoria, departamento, marca, refproveedor
												, linea, seccion, 0, cantidadproducto, producto, cantproddesc, 0, name, 
												grupo, tiendas, activo, 0, null, regactualizado, regactualizado));
									}catch(ArithmeticException f){f.printStackTrace();}
								}
								//resultado.close();
								//sentenciaOXE.close();
								Iterator<DetallePromocionExt> it = a.iterator();
			
								while (it.hasNext()){
									DetallePromocionExt dpe = it.next();
									Iterator<Integer> j = promocionesInactivas.iterator();
									while (j.hasNext()){
										int x = (j.next()).intValue();
										if(dpe.getCodigo()==x)
											dpe.setActivo("N");
										mBD.realizarSentenciaOXE("update XX_PromProductoDetalleCaja set regactualizado='Y', isactive='N' where XX_PROMOCIONES_ID = "+x);
									}
			
									if (dpe.getActivo().equals("A")){//Problema con transferencia inmediata
										if (dpe.getProducto() == null){
											if (dpe.getTienda().equals(tienda)||dpe.getTienda().equals("00"))
												if(dpe.getTipoPromocion()==1001200)
													detallePromocionExt += dpe.getCodigo()+"["+dpe.getDetalle()+"["+dpe.getPorcdescuento()+"["+dpe.getCategoria()
													+"["+relleno.format(dpe.getDepartamento())+"["+((dpe.getMarca()==null)?"NULL":dpe.getMarca())+"["+relleno.format(dpe.getLinea())+"["
													+((dpe.getRefproveedor()==null)?"NULL":dpe.getRefproveedor())+"["+dpe.getMontominimo()+"["+dpe.getCantidadproducto()
													+"["+dpe.getCantproddesc()+"["+((dpe.getProducto()==null)?"NULL":dpe.getProducto())+"["+dpe.getBsbonoregalo()
													+"["+dpe.getActivo()+"["+dpe.getRegalo()+"["+dpe.getGrupo()+"["+relleno.format(dpe.getSeccion())+"[NULL[\r\n";
												else
													detallePromocionExt += dpe.getCodigo()+"["+dpe.getDetalle()+"["+dpe.getPorcdescuento()+"["+dpe.getCategoria()
													+"["+relleno.format(dpe.getDepartamento())+"["+((dpe.getMarca()==null)?"NULL":dpe.getMarca())+"["+relleno.format(dpe.getLinea())+"["
													+((dpe.getRefproveedor()==null)?"NULL":dpe.getRefproveedor())+"["+dpe.getMontominimo()+"["+dpe.getCantidadproducto()
													+"["+dpe.getCantproddesc()+"["+((dpe.getProducto()==null)?"NULL":dpe.getProducto())+"["+dpe.getBsbonoregalo()
													+"["+dpe.getActivo()+"["+dpe.getName()+"["+dpe.getGrupo()+"["+relleno.format(dpe.getSeccion())+"[NULL[\r\n";
										}else{//si es por producto
											try{
												sentenciaOXE=mBD.getStatement();
												ResultSet c =mBD.realizarConsultaOXE("select max(detallepromocion) as max from XX_PromProductoDetalleCaja where xx_promociones_id = "+dpe.getCodigo(), sentenciaOXE);
												c.beforeFirst();
												if (c.getInt("max")>1)
													if (c.first()) n = c.getInt("max");
												c.close();
												sentenciaOXE.close();
											}catch(Exception e){n=0;}
											if (dpe.getTienda().equals(tienda)||dpe.getTienda().equals("00")){
													
												query = "select value from m_product a where issummary='N' and a.value like '"+dpe.getProducto()+"___' " +
														"and a.value not in (select value from XX_PromProductoDetalleCaja where xx_promociones_id="+dpe.getCodigo()+
														" and value=a.value and isactive='N' and regactualizado='Y')";
												sentenciaOXE=mBD.getStatement();
												resultado= mBD.realizarConsultaOXE(query, sentenciaOXE);
												resultado.beforeFirst();
												while (resultado.next()){
													n++;
													String consecutivo=resultado.getString("value");
													String sentencia="insert into XX_PromProductoDetalleCaja (CREATED, CREATEDBY, UPDATED, UPDATEDBY, AD_CLIENT_ID, AD_ORG_ID" +
														", XX_PROMOCIONES_ID, DETALLEPROMOCION, PORCDESCUENTO, CATEGORIA, DEPARTAMENTO" +
														", LINEA, SECCION, VALUE, XX_REFPROV, XX_MARCA, CANTIDADPRODUCTO, NROPAGAR, NAME" +
														", GRUPO, TIENDA, ISACTIVE, regactualizado)  " +
														"select a.CREATED, a.CREATEDBY, a.UPDATED, a.UPDATEDBY, a.AD_CLIENT_ID, a.AD_ORG_ID, " +
														dpe.getCodigo()+", "+n+", "+dpe.getPorcdescuento()+", "+dpe.getCategoria()+", "+
														dpe.getDepartamento()+", "+dpe.getLinea()+", "+dpe.getSeccion()+", '"+consecutivo+"', '" +
														dpe.getRefproveedor()+"', '"+dpe.getMarca()+"', "+dpe.getCantidadproducto()+", "+
														dpe.getPorcdescuento()+", '"+dpe.getName()+"', "+dpe.getGrupo()+", '"+dpe.getTienda()+"', '"+
														dpe.getActivo1()+"', '"+dpe.getRegactualizado()+
														"' from xx_promociones a " +
														"where a.xx_promociones_id="+dpe.getCodigo();
													try{
														mBD.realizarSentenciaOXE(sentencia);
													}catch(SQLException e){;}
												//
												
												//resultado.close();
												}
											sentenciaOXE.close();
											resultado.close();
												
												Statement sentenciaOXE1=mBD.getStatement();
												query ="select * from XX_PromProductoDetalleCaja where " +
												" xx_promociones_id = "+dpe.getCodigo()+" and TIENDA='"+dpe.getTienda()+
												"' and value like '"+dpe.getProducto()+"___'";
												resultado = mBD.realizarConsultaOXE(query, sentenciaOXE1);	
												while(resultado.next()){
													try{
														int codigo = resultado.getInt("XX_PROMOCIONES_ID");
														int detalleC = resultado.getInt("DETALLEPROMOCION");
														double porcdescuento = resultado.getDouble("PORCDESCUENTO");
														int categoria = resultado.getInt("categoria");
														int departamento = resultado.getInt("departamento")%100;
														String marca = resultado.getString("XX_MARCA");
														String refproveedor = resultado.getString("XX_REFPROV");
														int linea = resultado.getInt("linea")%100;
														int seccion = resultado.getInt("seccion")%100;
														double montominimo = resultado.getDouble("montopremiado");
														int cantidadproducto = resultado.getInt("CANTIDADPRODUCTO");
														String producto = resultado.getString("VALUE");
														int cantproddesc = resultado.getInt("NROPAGAR");
														double bsbonoregalo = resultado.getDouble("BSBONOREGALO");
														String name = resultado.getString("NAME");
														int grupo =resultado.getInt("grupo");
														name=limpiarNombre(name);
														detallePromocionExt += codigo+"["+detalleC+"["+porcdescuento+"["+categoria+"["+relleno.format(departamento)
														+"["+((marca==null)?"NULL":marca)+"["+relleno.format(linea)+"["+((refproveedor==null)?"NULL":refproveedor)
														+"["+montominimo+"["+cantidadproducto+"["+cantproddesc+"["+((producto==null)?"NULL":producto)
														+"["+bsbonoregalo+"["+dpe.getActivo()+"["+name+"["+grupo+"["+seccion+"[NULL[\r\n";
														listaPromocionesPorActualizar.add(new PromocionesPorProducto(dpe.getCodigo(), detalleC, true));
													}catch(ArithmeticException f){f.printStackTrace();}
												}
												//resultado.close();
												//sentenciaOXE1.close();
											}
										}
									}
								}
							} else if (tipo.equals("1000800")|| tipo.equals("1001000")||tipo.equals("1001400")){
								query="SELECT a.XX_PROMOCIONES_ID, a.XX_ENTREGABLES_ID, a.MONTOPREMIADO, a.BSBONOREGALO, b.NAME" +
									", a.TIENDA, a.ISACTIVE, a.REGACTUALIZADO FROM XX_ENTREGABLES a inner join XX_PROMOCIONES b on " +
									"a.XX_PROMOCIONES_ID=b.XX_PROMOCIONES_ID WHERE (b.APROBADOMER like 'Y' OR b.APROBADOMAR like 'Y' OR " +
									"b.APROBADOGG like 'Y') AND a.isactive<>a.REGACTUALIZADO ";
								sentenciaOXE=mBD.getStatement();
								resultado= mBD.realizarConsultaOXE(query, sentenciaOXE);
								resultado.beforeFirst();
								while(resultado.next()){	
									try{
										int codigo = resultado.getInt("XX_PROMOCIONES_ID");
										int detalleC = resultado.getInt("XX_ENTREGABLES_ID");
										double montominimo = resultado.getDouble("MONTOPREMIADO");
										double bsbonoregalo = resultado.getDouble("BSBONOREGALO");
										String name = resultado.getString("NAME");
										String tiendas = resultado.getString("TIENDA");
										String activo = resultado.getString("isactive");
										String regactualizado = resultado.getString("REGACTUALIZADO");
										name=limpiarNombre(name);
										a.add(new DetallePromocionExt(codigo, detalleC, 0, 0, 0, null, null
												, 0, 0, montominimo, 0, null, 0, bsbonoregalo, name, 
												0, tiendas, activo, 0, null, regactualizado, null));
									}catch(ArithmeticException f){f.printStackTrace();}
									
								}
								Iterator<DetallePromocionExt> it = a.iterator();
								while (it.hasNext()){
									DetallePromocionExt dpe = it.next();
									detallePromocionExt += dpe.getCodigo()+"["+dpe.getDetalle()+"["+dpe.getPorcdescuento()+"["+dpe.getCategoria()
									+"["+relleno.format(dpe.getDepartamento())+"["+((dpe.getMarca()==null)?"NULL":dpe.getMarca())+"["+relleno.format(dpe.getLinea())+"["
									+((dpe.getRefproveedor()==null)?"NULL":dpe.getRefproveedor())+"["+dpe.getMontominimo()+"["+dpe.getCantidadproducto()
									+"["+dpe.getCantproddesc()+"["+((dpe.getProducto()==null)?"NULL":dpe.getProducto())+"["+dpe.getBsbonoregalo()
									+"["+dpe.getActivo()+"["+dpe.getName()+"["+dpe.getGrupo()+"["+relleno.format(dpe.getSeccion())+"[NULL[\r\n";
								}
							}
						}catch(ArithmeticException f){f.printStackTrace();}
					}					
				}
			}
				if (archivo.exists()){
					BufferedWriter bw;
						try {
							if (caso)
								bw = new BufferedWriter(new FileWriter(directorio+PrincipalSinc.rutadetPromocionesExt));
			
							else 
								bw = new BufferedWriter(new FileWriter(directorio+TransferenciaInmediata.rutadetPromocionesExt));
							bw.write(detallePromocionExt);
							bw.close();
							detallePromocionExt = "";
						} catch (IOException e) {
							// TODO Bloque catch generado automáticamente
							e.printStackTrace();
						}
					
					y.clear();
				}	
	}
	private void sobreEscribirProductosAnulados(int codigop, String productop) {
		DecimalFormat relleno = new DecimalFormat("00");
		String query="select * from XX_PromProductoDetalleCaja where xx_promociones_id="+codigop+" and value like '"+productop+"___'";
		Statement sentenciaOXE;
		try {
			sentenciaOXE = mBD.getStatement();
			ResultSet resultado= mBD.realizarConsultaOXE(query, sentenciaOXE);
			resultado.beforeFirst();
			while (resultado.next()){
				int codigo = resultado.getInt("XX_PROMOCIONES_ID");
				int detalleC = resultado.getInt("DETALLEPROMOCION");
				double porcdescuento = resultado.getDouble("PORCDESCUENTO");
				int categoria = resultado.getInt("categoria");
				int departamento = resultado.getInt("departamento")%100;
				String marca = resultado.getString("XX_MARCA");
				String refproveedor = resultado.getString("XX_REFPROV");
				int linea = resultado.getInt("linea")%100;
				int seccion = resultado.getInt("seccion")%100;
				double montominimo = resultado.getDouble("montopremiado");
				int cantidadproducto = resultado.getInt("CANTIDADPRODUCTO");
				String producto = resultado.getString("VALUE");
				int cantproddesc = resultado.getInt("NROPAGAR");
				double bsbonoregalo = resultado.getDouble("BSBONOREGALO");
				String name = resultado.getString("NAME");
				int grupo =resultado.getInt("grupo");
				name=limpiarNombre(name);
				detallePromocionExt += codigo+"["+detalleC+"["+porcdescuento+"["+categoria+"["+relleno.format(departamento)
				+"["+((marca==null)?"NULL":marca)+"["+relleno.format(linea)+"["+((refproveedor==null)?"NULL":refproveedor)
				+"["+montominimo+"["+cantidadproducto+"["+cantproddesc+"["+((producto==null)?"NULL":producto)
				+"["+bsbonoregalo+"["+'E'+"["+name+"["+grupo+"["+seccion+"[NULL[\r\n";
			}
			resultado.close();
			sentenciaOXE.close();
		} catch (SQLException e) {
			// TODO Bloque catch generado automáticamente
			e.printStackTrace();
		}
		
		
		
	}
	/**
	 * Aqui se crea el archivo para posteriormente llenar la tabla CONDICIONPROMOCION de caja 
	 * **/
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Iterator'
	* Fecha: agosto 2011
	*/
	protected void condicionPromocion(String tienda, String directorio, boolean caso, int codigoP) throws IOException{
		mBD.conectarOXE();
		File archivo = null;
		try {
			archivo = new File(directorio, PrincipalSinc.rutacondicionPromocion); 
			 //Solo para verifivar
			if (archivo.createNewFile()) System.out.println("El archivo condicionPromocion se ha creado correctamente");
			else System.out.println("No ha podido ser creado el archivo condicionPromocion");
			} catch (IOException ioe) {ioe.printStackTrace();}
			try {
				String query;
				if(caso)
					query = "select a.XX_PROMOCIONES_ID, a.CONDICIONES, a.tienda, a.REGACTUALIZADO from XX_ENTREGABLES a" +
						" inner join xx_promociones b on a.xx_promociones_id=b.xx_promociones_id where a.TipoPromocion = 1001400 " +
						"and a.ISACTIVE<>a.REGACTUALIZADO and (b.AprobadoMer='Y' or b.AprobadoMar='Y') " +
						"union " +
						"select a.XX_PROMOCIONES_ID, a.CONDICIONES, a.tienda, a.REGACTUALIZADO from XX_regalopporcompra a" +
						" inner join xx_promociones b on a.xx_promociones_id=b.xx_promociones_id where a.TipoPromocion = 1001200 " +
						"and a.ISACTIVE<>a.REGACTUALIZADO and (b.AprobadoMer='Y' or b.AprobadoMar='Y')";
				else
					query = "select a.XX_PROMOCIONES_ID, a.CONDICIONES, a.tienda, a.REGACTUALIZADO from XX_ENTREGABLES a" +
						" inner join xx_promociones b on a.xx_promociones_id=b.xx_promociones_id where a.xx_promociones_id="+codigoP +
						"union " +
						"select a.XX_PROMOCIONES_ID, a.CONDICIONES, a.tienda, a.REGACTUALIZADO from XX_regalopporcompra a" +
						" inner join xx_promociones b on a.xx_promociones_id=b.xx_promociones_id where  a.xx_promociones_id="+codigoP;
				ResultSet resultado= mBD.realizarConsultaOXE(query);
				resultado.beforeFirst();
				while(resultado.next()){
					String linea ="";
					int codigo = resultado.getInt("XX_PROMOCIONES_ID");
					linea = resultado.getString("Condiciones");
					String tiendas = resultado.getString("tienda");
					String linea2="";
					int orden = 0;
					Iterator<String> lin = dividirEnLineas(PrincipalSinc.nroLineaImpresora, linea).iterator();
					while (lin.hasNext()){
						linea2 = lin.next();
						orden++;
						b.add(new CondicionPromocion(codigo, orden, linea2, tiendas));
					}
				}
				Iterator<CondicionPromocion> i = b.iterator();
				while (i.hasNext()){
					CondicionPromocion cp = i.next();
					if (cp.getTienda().equals(tienda)||cp.getTienda().equals("00"))
						condicionPromocion += (cp.getCodigo()+"["+cp.getOrden()+"["+cp.getLinea()+"[N[\r\n").replace('¬','*');
				}
				if (archivo.exists()){
					BufferedWriter bw = null;
					if (caso)
						bw = new BufferedWriter(new FileWriter(directorio+PrincipalSinc.rutacondicionPromocion));
					else
						bw = new BufferedWriter(new FileWriter(directorio+TransferenciaInmediata.rutacondicionPromocion));
					bw.write(condicionPromocion);
					bw.close();
					condicionPromocion = "";
					b.clear();
				}
			}catch(SQLException e){e.printStackTrace();}		
	}
	/**
	 * Se usa para hacer que un parrafo se pueda imprimir en la factura
	 * **/
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	static Vector<String> dividirEnLineas(int tamanioLinea, String texto) {
		Vector<String> lineasResultado = new Vector<String>();
		int letras = 0;
		boolean dividir = false;
		if (texto!=null){
			do {
				
				// Colocamos las palabras de las lineas
				dividir = ((letras + tamanioLinea) < texto.length());
				String lineaActual = ((letras + tamanioLinea) < texto.length())
							? texto.substring(letras, letras + tamanioLinea)
							: texto.substring(letras);
				int j = lineaActual.length();
				if (lineaActual.contains("\n")){
					lineaActual = lineaActual.replace('\n', '¬');
					dividir=true;
			}
				
				if (dividir) {
					// Buscamos donde cortar las palabras para la siguiente linea
					while ((j>1)
							&& (lineaActual.charAt(j-1) != '.') 
							&& (lineaActual.charAt(j-1) != ' ') 
							&& (lineaActual.charAt(j-1) != ',') 
							&& (lineaActual.charAt(j-1) != ':')
							&& (lineaActual.charAt(j-1) != '¬')){
						j--;
					}
				}
				if (j<=1)
					if (j<0){
						lineaActual = lineaActual.substring(lineasResultado.lastElement().length(),lineaActual.length());
						letras += lineasResultado.lastElement().length();
					}
					j = lineaActual.length();
				lineasResultado.addElement(lineaActual.substring(0,j));
				
				letras += j;
			} while (letras < texto.length());
		}
		return lineasResultado;
	}
	/**
	 * Mantenimiento de las tablas de compiere, para llevar un control de las promociones sincronizadas
	 * */
	public void actualizarBackOffice() {
		try{
			//Actualizando el REGACTUALIZADO='Y'
			String query6 = "UPDATE XX_PROMOCIONES set REGACTUALIZADO='Y' WHERE " +
					"(aprobadomer like 'Y' or aprobadomar like 'Y' or aprobadogg like 'Y') " +
				" and REGACTUALIZADO like 'N' AND ISACTIVE like 'Y'";
			mBD.realizarSentenciaOXE(query6);
				
			String query1 = "UPDATE XX_COMBO set REGACTUALIZADO='Y' where xx_promociones_id in " +
					"(SELECT xx_promociones_id FROM XX_PROMOCIONES WHERE " +
					"(aprobadomer like 'Y' or aprobadomar like 'Y' or aprobadogg like 'Y') AND isactive like 'Y')";
			mBD.realizarSentenciaOXE(query1);
	
			String query2 = "UPDATE XX_DESCTRANS set REGACTUALIZADO='Y' where xx_promociones_id in " +
					"(SELECT xx_promociones_id FROM XX_PROMOCIONES WHERE (aprobadomer like 'Y' or " +
					"aprobadomar like 'Y' or aprobadogg like 'Y') AND isactive like 'Y')";
			mBD.realizarSentenciaOXE(query2);
	
			String query3 = "UPDATE XX_ENTREGABLES set REGACTUALIZADO='Y' where xx_promociones_id in  " +
					"(SELECT xx_promociones_id FROM XX_PROMOCIONES WHERE " +
					"(aprobadomer like 'Y' or aprobadomar like 'Y' or aprobadogg like 'Y') AND isactive like 'Y')";
			mBD.realizarSentenciaOXE(query3);
			
			String query4 = "UPDATE XX_REGALOPPORCOMPRA set REGACTUALIZADO='Y' where xx_promociones_id in  " +
				"(SELECT xx_promociones_id from XX_PROMOCIONES WHERE " +
				"(aprobadomer like 'Y' or aprobadomar like 'Y' or aprobadogg like 'Y') AND isactive like 'Y')";
			mBD.realizarSentenciaOXE(query4);
			
			String query5 = "UPDATE XX_PROMOMASIVAS set REGACTUALIZADO='Y' where xx_promociones_id in  " +
				"(SELECT xx_promociones_id FROM XX_PROMOCIONES WHERE " +
				"(aprobadomer like 'Y' or aprobadomar like 'Y' or aprobadogg like 'Y') AND isactive like 'Y')";
			mBD.realizarSentenciaOXE(query5);
			//Actualizando el APROBADO='N' y REGACTUALIZADO='N'
			String query7 = "UPDATE XX_PROMOCIONES set APROBADOMAR='N', aprobadomer='N',REGACTUALIZADO='N' WHERE " +
					"(aprobadomer like 'Y' or aprobadomar like 'Y' or aprobadogg like 'Y') " +
				" and REGACTUALIZADO like 'Y' AND ISACTIVE like 'N'";
			mBD.realizarSentenciaOXE(query7);
			
			String query8 = "UPDATE XX_PROMOMASIVAS set REGACTUALIZADO='N' where XX_PROMOCIONES_ID in " +
					"(Select XX_PROMOCIONES_ID from XX_PROMOCIONES where ISACTIVE like 'N')";
			mBD.realizarSentenciaOXE(query8);
			
			String query9 = "UPDATE XX_REGALOPPORCOMPRA set  REGACTUALIZADO='N' where XX_PROMOCIONES_ID in " +
					"(Select XX_PROMOCIONES_ID from XX_PROMOCIONES  where ISACTIVE like 'N')";
			mBD.realizarSentenciaOXE(query9);
			
			String query10 = "UPDATE XX_ENTREGABLES set REGACTUALIZADO='N' where XX_PROMOCIONES_ID in " +
					"(Select XX_PROMOCIONES_ID from XX_PROMOCIONES  where ISACTIVE like 'N')";
			mBD.realizarSentenciaOXE(query10);
			
			String query11 = "UPDATE XX_DESCTRANS set REGACTUALIZADO='N' where XX_PROMOCIONES_ID in " +
					"(Select XX_PROMOCIONES_ID from XX_PROMOCIONES where ISACTIVE like 'N')";
			mBD.realizarSentenciaOXE(query11);
			
			String query12 = "UPDATE XX_COMBO set REGACTUALIZADO='N' where XX_PROMOCIONES_ID in " +
					"(Select XX_PROMOCIONES_ID from XX_PROMOCIONES where ISACTIVE like 'N')";
			mBD.realizarSentenciaOXE(query12);
			
			String query13 = "UPDATE XX_DONACION set REGACTUALIZADO='Y' WHERE " +
					"isactive like 'Y' and REGACTUALIZADO like 'N'";
			mBD.realizarSentenciaOXE(query13);
			
			String query14 = "UPDATE XX_DONACION set REGACTUALIZADO='N' WHERE " +
					"REGACTUALIZADO like 'Y' AND ISACTIVE like 'N'";
			mBD.realizarSentenciaOXE(query14);
			
			String query15 = "UPDATE xx_PromProductoDetalleCaja set REGACTUALIZADO='N' WHERE " +
			"REGACTUALIZADO like 'Y' AND ISACTIVE like 'N'";
			mBD.realizarSentenciaOXE(query15);
	
			String query16 = "UPDATE xx_combo set REGACTUALIZADO='N' WHERE " +
			"REGACTUALIZADO like 'Y' AND ISACTIVE like 'N'";
			mBD.realizarSentenciaOXE(query16);
		
			String query17 = "UPDATE xx_regalopporcompra set REGACTUALIZADO='N' WHERE " +
			"REGACTUALIZADO like 'Y' AND ISACTIVE like 'N'";
			mBD.realizarSentenciaOXE(query17);
			
			String query18 = "UPDATE XX_DESCTRANS set REGACTUALIZADO='N' WHERE " +
			"REGACTUALIZADO like 'Y' AND ISACTIVE like 'N'";
			mBD.realizarSentenciaOXE(query18);
			
			String query19 = "UPDATE XX_ENTREGABLES set REGACTUALIZADO='N' WHERE " +
			"REGACTUALIZADO like 'Y' AND ISACTIVE like 'N'";
			mBD.realizarSentenciaOXE(query19);
			
			String query20 = "UPDATE XX_PROMOMASIVAS set REGACTUALIZADO='N' WHERE " +
			"REGACTUALIZADO like 'Y' AND ISACTIVE like 'N'";
			mBD.realizarSentenciaOXE(query20);
			
			Iterator<PromocionesPorProducto> i = listaPromocionesPorActualizar.iterator();
			while (i.hasNext()){
				String query="";
				PromocionesPorProducto ppp = i.next();
				if(ppp.isActivo()){
					query = "update XX_PROMPRODUCTODETALLECAJA set regactualizado='Y' where xx_promociones_id="+ppp.getCodigo()+
					" and detallepromocion="+ppp.getDetalle()+" and isactive='Y'";
					mBD.realizarSentenciaOXE(query);
				} else {
					query = "update XX_PROMPRODUCTODETALLECAJA set regactualizado='N', isactive='N' where xx_promociones_id="+ppp.getCodigo()+
					" and value='"+ppp.getValue()+"'";
					mBD.realizarSentenciaOXE(query);
				}
					
			}
			Iterator<Integer> j = promocionesInactivas.iterator();
			while (j.hasNext()){
				String query="";
				Integer pi=j.next();
				query="update XX_PromProductoDetalleCaja set regactualizado='N', isactive='Y' where XX_PROMOCIONES_ID = "+pi.intValue();
				mBD.realizarSentenciaOXE(query);
			}
						
		}catch(SQLException e1){e1.printStackTrace();}
	}
	/**
	 * Obtiene el numero de detalle en las promociones por productos
	 * */
	public int obtenerNroDetalle(int codigoPromocion) throws SQLException{
		ResultSet r=mBD.realizarConsultaOXE("Select regactualizado from xx_promociones where xx_promociones_id="+codigoPromocion);
		r.beforeFirst();
		if (r.first()){
			if (r.getString("regactualizado").equals("Y")) {
				r=mBD.realizarConsultaOXE("Select count(*) as x from XX_REGALOPPORCOMPRA where xx_promociones_id="
						+codigoPromocion+" union Select count(*) as x from xx_combo where xx_promociones_id="
						+codigoPromocion);
				r.beforeFirst();
				while (r.next()) if (r.getInt("x")>0)return r.getInt("x");
			}
		}
		return 1;
	}
	private String limpiarNombre(String name) {
		// TODO Apéndice de método generado automáticamente
		name=name.replace("ñ", "ni");
		name=name.replace("Ñ", "NI");
		name=name.replace("á", "a");
		name=name.replace("Á", "A");
		name=name.replace("é", "e");
		name=name.replace("É", "é");
		name=name.replace("í", "í");
		name=name.replace("Í", "I");
		name=name.replace("ó", "o");
		name=name.replace("Ó", "o");
		name=name.replace("ú", "u");
		name=name.replace("Ú", "U");
		name=name.replace("'", "");
		name=name.replace("?", "");
		name=name.replace("¿", "");
		return name;
	}
	
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public Vector<Dupla> obtenerPromocionesDeProducto(int detalle) throws Exception{
		Vector<Dupla> resultados = new Vector<Dupla>(); 
		String query= 	"select promma.xx_promociones_id promocion, xx_promomasivas_id detalle "+ 
					   	"from xx_promomasivas promma, m_product prod, xx_promociones prom "+
						"where prod.value like '%"+detalle+"'"+
							" and (	prod.xx_linea_id = promma.xx_linea_id "+ 
								"or	(prod.xx_departamento_id = promma.xx_departamento_id "+ 
									"and prod.xx_linea_id is null) "+
								"or	(prod.xx_categoria_id = promma.xx_categoria_id "+ 
									"and promma.xx_departamento_id is null "+
									"and promma.xx_linea_id is null) " +
									"OR      XX_TODALATIENDA='Y') "+
							"and promma.xx_promociones_id=prom.xx_promociones_id "+
							"and (current_date between prom.FECHAINICIO and prom.FECHAFIN+1) "+ 
						"union "+
						"select comb.xx_promociones_id, xx_combo_id "+ 
							"from xx_combo comb, m_product prod, xx_promociones prom "+ 
							"where prod.value like '%"+detalle+"'"+
								" and (	prod.m_product_id = comb.xx_producto_id "+
									"or (prod.xx_seccion_id = comb.xx_seccion_id "+
										"and comb.xx_producto_id is null "+
										"and comb.xx_referenciaproveedor_id is null) "+
									"or (prod.xx_linea_id = comb.xx_linea_id "+
										"and comb.xx_seccion_id is null "+
										"and comb.xx_producto_id is null "+
										"and comb.xx_referenciaproveedor_id is null) "+
									"or (prod.xx_departamento_id = comb.xx_departamento_id "+ 
										"and comb.xx_linea_id is null "+
										"and comb.xx_seccion_id is null "+
										"and comb.xx_producto_id is null "+
										"and comb.xx_referenciaproveedor_id is null) "+
									"or (prod.xx_categoria_id = comb.xx_categoria_id "+ 
										"and comb.xx_departamento_id is null "+
										"and comb.xx_linea_id is null "+
										"and comb.xx_seccion_id is null "+
										"and comb.xx_producto_id is null "+
										"and comb.xx_referenciaproveedor_id is null) "+
									"or prod.xx_refprov=(select xx_refprov from m_product where m_product_id=comb.xx_referenciaproveedor_id)) "+	
								"and comb.xx_promociones_id=prom.xx_promociones_id "+
								"and (current_date between prom.FECHAINICIO and prom.FECHAFIN+1) "+ 
						"union  "+
						"select reg.xx_promociones_id, xx_regalopporcompra_id "+ 
							"from xx_regalopporcompra reg, m_product prod, xx_promociones prom "+ 
							"where prod.value like '%"+detalle+"'"+
								" and (  prod.m_product_id = reg.xx_producto_id "+
									"or (prod.xx_seccion_id = reg.xx_seccion_id "+
										"and reg.xx_producto_id is null) "+
									"or (prod.xx_linea_id = reg.xx_linea_id "+ 
										"and reg.xx_seccion_id is null "+
										"and reg.xx_producto_id is null) "+
									"or (prod.xx_departamento_id = reg.xx_departamento_id "+ 
										"and reg.xx_linea_id is null "+
										"and reg.xx_seccion_id is null "+
										"and reg.xx_producto_id is null) "+
									"or (prod.xx_categoria_id = reg.xx_categoria_id "+ 
										"and reg.xx_departamento_id  is null "+
										"and reg.xx_linea_id is null "+
										"and reg.xx_seccion_id is null "+
										"and reg.xx_producto_id is null)) "+
							"and reg.xx_promociones_id=prom.xx_promociones_id "+ 
							"and (current_date between prom.FECHAINICIO and prom.FECHAFIN+1) ";
		try {
			mBD.conectarOXE();
			Statement sentenciaOXE=mBD.getStatement();
			ResultSet resultado = mBD.realizarConsultaOXE(query, sentenciaOXE);
			resultado.beforeFirst();
			if(!resultado.first()){
				throw new Exception("No hay promociones disponibles para ese producto");
			}
			resultado.beforeFirst();
			while(resultado.next()){
				int detalleProm = resultado.getInt("promocion");
				int codProm = resultado.getInt("detalle");
				Dupla promocion = new Dupla(codProm,detalleProm);
				resultados.add(promocion);
			}
			return resultados;
		} catch (SQLException e) {
			throw e;
		}
	}
	int getCriterioSeleccion(int codigo){
		int estado=0;
		String query="SELECT  XX_CATEGORIA_ID, XX_DEPARTAMENTO_ID, XX_LINEA_ID, XX_SECCION_ID, XX_PRODUCTO_ID" +
				", XX_MARCA, XX_REFERENCIAPROVEEDOR_ID" +
				" FROM XX_COMBO where xx_promociones_id="+codigo;
		ResultSet resultado= mBD.realizarConsultaOXE(query);
		try {
			resultado.beforeFirst();
			while (resultado.next()){
				int categoria = resultado.getInt("XX_CATEGORIA_ID");
				int departamento = resultado.getInt("XX_DEPARTAMENTO_ID");
				int linea = resultado.getInt("XX_LINEA_ID");
				int seccion = resultado.getInt("XX_SECCION_ID");
				int producto = resultado.getInt("XX_PRODUCTO_ID");
				String marca = resultado.getString("XX_MARCA");
				int referencia = resultado.getInt("XX_REFERENCIAPROVEEDOR_ID");
				if (categoria!=0 && departamento==0){ 
					if (estado<1) 
						estado=1;
				} else if (categoria!=0 && departamento!=0 && linea == 0) {
					if(estado<2)
						estado=2;
				}else if (categoria!=0 && departamento!=0 && linea!=0 && seccion==0) {
					if(estado<3) 
						estado=3;
				}else if (categoria!=0 && departamento!=0 && linea!=0 && seccion!=0 
						&& producto==0 && referencia==0 && marca==null) {
					if (estado<4) 
						estado=4;
				}else if (categoria!=0 && departamento!=0 && linea!=0 && seccion!=0 && producto==0 
						&& referencia!=0 && marca==null) {
					if (estado<5) 
						estado=5;
				}else if (categoria!=0 && departamento!=0 && linea!=0 && seccion!=0 && producto==0 
						&& referencia==0 && marca!=null) {
					if (estado<5) estado=5;
				}else if (categoria!=0 && departamento!=0 && linea!=0 && seccion!=0 && producto!=0 
						&& referencia==0 && marca==null) {
					if (estado<5) 
						estado=5;
					break;
				}
			}
		} catch (SQLException e) {e.printStackTrace();}
		return estado;
	}
}// fin de clase ConstructoraSinc
