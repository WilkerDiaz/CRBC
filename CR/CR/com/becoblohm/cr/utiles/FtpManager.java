package com.becoblohm.cr.utiles;
/**
 * =============================================================================
 * Proyecto   : CR_Merchant
 * Paquete    : com.becoblohm.cr.utiles
 * Programa   : FtpManager.java
 * Creado por : Gabriel Martinelli
 * Creado en  : 
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
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Proxy;
import java.net.SocketAddress;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import sun.net.TelnetInputStream;
import sun.net.ftp.FtpClient;
import sun.net.ftp.FtpProtocolException;



public class FtpManager extends sun.net.ftp.FtpClient {

	String hostname = null;
	String username= null;
	String password = null;
	String shortname = null;
	String filename = null;
	String pathArchivos = null;
	
	// --------------------------- Constructores --------------------------------
	public FtpManager(String user, String pwd) {
		super();
		this.username = user;
		this.password = pwd;
	}
	
	
	
	public FtpManager(String hostname, String username, String password, String shortname, String filename,
			String pathArchivos) {
		super();
		this.hostname = hostname;
		this.username = username;
		this.password = password;
		this.shortname = shortname;
		this.filename = filename;
		this.pathArchivos = pathArchivos;
	}
	
	// ----------------------------- Getter's and Setter's -----------------------
	
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getHostname() {
		return hostname;
	}
	public void setHostname(String hostname) {
		this.hostname = hostname;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPathArchivos() {
		return pathArchivos;
	}
	public void setPathArchivos(String pathArchivos) {
		this.pathArchivos = pathArchivos;
	}
	public String getShortname() {
		return shortname;
	}
	public void setShortname(String shortname) {
		this.shortname = shortname;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	
	public void putFtpFile(String archivoOrigen, String archivoDestino, String host) throws Exception{
		String directorios[] = {""};
		this.putFtpFile(archivoOrigen, archivoDestino, host, directorios);
	}

	public void putFtpFile(String archivoOrigen, String archivoDestino, String host, String remoteDir[] ) throws Exception{
		int total = 0;
		try {
			// Realizamos la conexion ftp al servidor
			this.openServer(host); // connect to FTP server
			this.login(username, password); // login
			this.binary(); // set to binary mode transfer
			
			for (int j=0; j<remoteDir.length; j++)
				this.cd(remoteDir[j]);

			//Creamos un objeto file para el archivo destino
			File fileout = new File(archivoDestino);
			OutputStream out = this.put( fileout.getName() );
			InputStream in = new FileInputStream( archivoOrigen );
			byte c[] = new byte[4096];
			int read = 0;
			while (( read = in.read(c) ) != -1 ){
				out.write(c, 0, read);
				total+=read;
			}
			in.close(); //close the io streams
			out.close();

			this.closeServer(); //close connection
		} catch (Exception e) {
			throw e;
		}
	}
	
	public void getFtpFile(String archivoOrigen, String archivoDestino, String host ) throws Exception{
		int total = 0;
		try {
			// Realizamos la conexion ftp al servidor
			this.openServer(host); // connect to FTP server
			this.login(username, password); // login
			this.binary(); // set to binary mode transfer
			
			//Creamos un objeto file para el archivo destino
			File fileout = new File(archivoDestino);
			TelnetInputStream out = this.get( fileout.getName() );
			InputStream in = new FileInputStream( archivoOrigen );
			byte c[] = new byte[4096];
			int read = 0;
			while (( read = in.read(c) ) != -1 ){
				out.read(c, 0, read);
				total+=read;
			}
			in.close(); //close the io streams
			out.close();

			this.closeServer(); //close connection
		} catch (Exception e) {
			throw e;
		}
	}
}