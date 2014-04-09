package com.beco.sinccompieretda.controlador;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;


import sun.net.ftp.FtpClient;


public class Transferencia {
	

	public static void putFtpFile(String archivoOrigen, String archivoDestino, String host, String username, String password, String nombreArchivo, boolean esInmediata ) throws Exception{
		int total = 0;
		try {
			// Realizamos la conexion ftp al servidor
			FtpClient ftpClient = new FtpClient();
			ftpClient.openServer(host); // connect to FTP server
			ftpClient.login(username, password); // login
			ftpClient.binary(); // set to binary mode transfer
			//ftpClient.cd("/home/CMELENDEZ"); // change directory
			
			//Creamos un objeto file para el archivo destino
			
			OutputStream out = null;
			if(esInmediata){
				out = ftpClient.append(nombreArchivo);
			}else
				out = ftpClient.put(nombreArchivo);
			InputStream in = new FileInputStream( archivoOrigen );
			byte c[] = new byte[4096];
			int read = 0;
			while (( read = in.read(c) ) != -1 ){
				out.write(c, 0, read);
				total+=read;
			}
			in.close(); //close the io streams
			out.close();
			//t.interrupt(); //stop the thread
			//ftpClient.cd("/");
			// change to root directory to avoid wsftp server bug
			ftpClient.closeServer(); //close connection
			System.out.println("Bytes transmitidos, para el archivo " +nombreArchivo+ " : " + total +  "       "+archivoDestino);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
