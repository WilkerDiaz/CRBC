package com.beco.colascr.notificaciones.controlador;

public class OperacionSobreLista {
	private String tipoOperacion;
	private double montoAbono;
	private String dedicatoria;
	private String nombreCliente;
	private String correoCliente;
	private String nombreProducto;
	private String codProducto;
	private String nombreInvitado;
	private String fecha;
	private int numeroLista;
	protected String nombreTienda;
	protected String direccionTienda;
	protected String numTelefono;
	private int numTienda;
	
	public OperacionSobreLista(){
		
	}
	public void setTipoOperacion(String t){
		tipoOperacion = t;
	}
	public void setMontoAbono(double monto){
		montoAbono = monto;
	}
	public void setDedicatoria(String d){
		dedicatoria = d;
	}
	public void setNombreCliente(String n){
		nombreCliente = n;
	}
	public void setCorreoCliente(String c){
		correoCliente = c;
	}
	public void setNombreProducto(String c){
		nombreProducto = c;
	}
	public void setNombreInvitado(String n){
		nombreInvitado = n;
	}
	public void setFecha(String f){
		fecha = f;
	}
	public void setNumeroLista(int n){
		numeroLista = n;
	}
	public void setNumeroTienda(int t){
		numTienda = t;
	}
	public void setCodProducto(String c){
		codProducto = c;
	}
	
	public String getCodProducto(){
		return codProducto;
	}
	public String getNombreTienda(){
		return nombreTienda;
	}
	public String getDireccionTienda(){
		return direccionTienda;
	}
	public String getNumTlf(){
		return numTelefono;
	}
	public int getNumTienda(){
		return numTienda;
	}
	public int getNumeroLista(){
		return numeroLista;
	}
	public String getTipoOperacion(){
		return tipoOperacion;
	}
	
	public double getMontoAbono(){
		return montoAbono;
	}
	public String getDedicatoria(){
		return dedicatoria;
	}
	public String getNombreCliente(){
		return nombreCliente;
	}
	public String getCorreoCliente(){
		return correoCliente;
	}
	public String getNombreProducto(){
		return nombreProducto;
	}
	public String getNombreInvitado(){
		return nombreInvitado;
	}
	public String getFecha(){
		return fecha;
	}
	public void setNombreTienda(String t){
		nombreTienda = t;
	}
	public void setDireccionTienda(String d){
		direccionTienda = d;
	}
	public void setNumTlf(String n){
		numTelefono = n;
	}
}
