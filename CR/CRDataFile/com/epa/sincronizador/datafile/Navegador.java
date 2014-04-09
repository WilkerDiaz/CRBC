package com.epa.sincronizador.datafile;

import java.io.File;
import java.io.FileFilter;
import java.net.MalformedURLException;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.sql.Date;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.epa.sincronizador.datafile.server.GenerarDataFile;


import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileFilter;

public class Navegador {

	/**
     * Logger for this class
     */
    private static final Logger logger = Logger
            .getLogger(GenerarDataFile.class);

	String nombre;
	
	/**
	 * Constructor de Navegador
	 * en 'nombre' se guarda el nombre de la entidad
	 * @author jperez
	 * @param nombre
	 */
	public Navegador(String nombre) {
		super();
		this.nombre = nombre;
	}

	/**
	 * Función que lista los archivos de un directorio dado que cumple el filtro de nombre
	 * @author jperez
	 * @return
	 */
	public File[] listarArchivos(String directorio) {
		File dir = new File(directorio);
		FileFilter filtro = new FileFilter() {
			//Filtro que toma solo los archivos que comienzan con nombre.
	        public boolean accept(File archivo) {
	        	if(archivo.isFile()){
	        		String nombreArchivo = archivo.getName();
	        		return nombreArchivo.startsWith(nombre);  			
	        	}
	            return false;
	        }
	    };
 
	    //ArrayList<File> listaArchivos = new ArrayList<File>();
	    //Listar archivos con el filtro dado
	    File[] archivos = dir.listFiles(filtro);
	    /*if (archivos != null) {
	        for (int i=0; i < archivos.length; i++) {
	            String filename = archivos[i].getName();
	            System.out.print (filename + "\n");
 
	        }
	    }*/
	    return archivos;
	}
	
	/**
	 * Funcion que elimina todos los archivos que empiezan por 'nombre'
	 * en la ruta dada
	 * @author jperez
	 * @param ruta
	 */
	public void eliminarArchivos(String ruta){
		File[] archivos = listarArchivos(ruta);
		if (archivos != null) {
	        for (File archivo:archivos) {
	        	archivo.delete();
	        }
	    }
	}
	
	/**
	 * Función que lista todos los archivos que comienzan por un string 
	 * dado (nombre), y los ordena de acuerdo a la fecha en su nombre 
	 * desde un servidor remoto usando el protocolo Samba
	 * @author jperez
	 * @param url
	 * @return ArrayList<SmbFile>
	 */
	public ArrayList<SmbFile> listarArchivos(URLConnection url) {
		SmbFile dir;
		ArrayList<SmbFile> archivosOrdenados = new ArrayList<SmbFile>();
		try {
			dir = new SmbFile((SmbFile)url,"");
			/* Filtro a aplicar en los archivos a listar
			 * Filtro por nombre, devuelve todos los archivos que 
			 * empiezan con un string dado
			 */
			SmbFileFilter filtro = new SmbFileFilter() {
		        public boolean accept(SmbFile archivo) {
		        	try {
						if(archivo.isFile()){
							String nombreArchivo = archivo.getName();
							return nombreArchivo.startsWith(nombre);  			
						}
					} catch (SmbException e) {
						logger.error("ERROR AL ACCEDER AL ARCHIVO: "
			                    + archivo.getName(), e);					
						//e.printStackTrace();
					}
		            return false;
		        }
		    };
	 
		    //ArrayList<File> listaArchivos = new ArrayList<File>();
		    SmbFile[] archivos;
			try {
				//Listar los archivos del directorio remoto que cumplen la caracteristica dada
				archivos = dir.listFiles(filtro);
				//Ordenar los archivos por la fecha en su nombre
				if(archivos != null && archivos.length>0){
					archivosOrdenados = ordenarArchivosPorFecha(archivos,nombre.length());
					return archivosOrdenados;
				}
			} catch (SmbException e) {
				logger.error("NO SE PUDO LISTAR LOS ARCHIVOS DEL SERVIDOR MEDIANTE SAMBA", e);
				//e.printStackTrace();
			}
		} catch (MalformedURLException e1) {
			logger.error("EL URL DE CONEXION ES INCORRECTO: "+url, e1);
			//e1.printStackTrace();
		} catch (UnknownHostException e1) {
			logger.error("SE DESCONOCE EL HOST INDICADO PARA LA CONEXION: "+url, e1);
			//e1.printStackTrace();
		}
		return null;
	   
	    
	}
	
	/**
	 * 
	 * Funcion que devuelve una lista de SmbFile ordenados de acuerdo a la fecha que aparece en ellos
	 * @author jperez
	 * @param archivos arreglo de archivos
	 * @param start posición en la que comienza la fecha dentro del nombre de cada archivo
	 * @return
	 */
	private ArrayList<SmbFile> ordenarArchivosPorFecha(SmbFile[] archivos, int start){
		String fechaString2;
		Date fecha;
		int index;
		ArrayList<SmbFile> archivosOrdenados = new ArrayList<SmbFile>();
		try{
			//Insertamos el primer elemento
			String filename = archivos[0].getName();
			String fechaString = filename.substring(start, filename
	                .lastIndexOf(".txt.gz"));
			archivosOrdenados.add(archivos[0]);
			
			//Para cada uno de los archivos
	        for (int i=1; i < archivos.length; i++) {
	            filename = archivos[i].getName();
	            fechaString = filename.substring(start, filename
	                    .lastIndexOf(".txt.gz"));   
	            //Si el archivo tiene fecha
	            if(!fechaString.equals("")){
	            	fecha = Date.valueOf(fechaString);
	            	index = 0;
	            	//Comparamos su fecha con las fechas de los archivos ya ordenados para averiguar su ubicación
	            	for(SmbFile archivo: archivosOrdenados){
	            		fechaString2 = archivo.getName().substring(start, archivo.getName().lastIndexOf(".txt.gz"));
	            		//Si el archivo ya ordenado no tiene fecha o su fecha es menor a la del nuevo elemento
	            		if(fechaString2.equals("") || Date.valueOf(fechaString2).before(fecha)){
	            			//Insertamos en esa posicion
	            			archivosOrdenados.add(index,archivos[i]);
	            			break;
	            		} 
	            		index++;
	            	}
	            //Si no tiene fecha lo insertamos directamente al final de la lista
	            }else{
	            	archivosOrdenados.add(archivos[i]);
	            }
	 
		    }
		}catch(Exception e){
			logger.error("NO SE PUDO ORDENAR LA LISTA DE ARCHIVOS: ", e);
			//e.printStackTrace();
		}
		return archivosOrdenados;
	}
	


	
	
	
}


