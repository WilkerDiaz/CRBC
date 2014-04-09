package com.becoblohm.cr.sincronizador.hibernate;
import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class TransaccionafiliadocrmPK implements Serializable {
	
	private int numtienda;
	private Date fechatransaccion;
	private int numcajafinaliza;
	private int   numtransaccion;
	private String codafiliado;
	/**
	 * @return el codafiliado
	 */
	public String getCodafiliado() {
		return codafiliado;
	}
	
	public void setCodafiliado(String codafiliado) {
		this.codafiliado = codafiliado;
	}
	
	public Date getFechatransaccion() {
		return this.fechatransaccion;
	}
	
	public void setFechatransaccion(Date fechatransaccion) {
		this.fechatransaccion = fechatransaccion;
	}
	
	public int getNumcajafinaliza() {
		return this.numcajafinaliza;
	}
	
	public void setNumcajafinaliza(int numcajafinaliza) {
		this.numcajafinaliza = numcajafinaliza;
	}
	
	public int getNumtienda() {
		return numtienda;
	}
	
	public void setNumtienda(int numtienda) {
		this.numtienda = numtienda;
	}
	
	public int getNumtransaccion() {
		return numtransaccion;
	}
	
	public void setNumtransaccion(int numtransaccion) {
		this.numtransaccion = numtransaccion;
	}
	
	//constructor full
	public TransaccionafiliadocrmPK(int numtienda, Date fechatransaccion, int numcajafinaliza,int numtransaccion, String codafiliado)
	{
		this.numtienda = numtienda;
		this.fechatransaccion = fechatransaccion;
		this.numcajafinaliza = numcajafinaliza;
		this.numtransaccion = numtransaccion;
		this.codafiliado = codafiliado;
	}
	
	//Constructor por defecto
	 public TransaccionafiliadocrmPK() {}
	 
	//Metodos de la clase por defecto
	 public String toString() {
	        return new ToStringBuilder(this)
	            .append("numtienda", getNumtienda())
	            .append("fechatransaccion", getFechatransaccion())
	            .append("numcajafinaliza", getNumcajafinaliza())
	            .append("numtransaccion", getNumtransaccion())
	            .append("codafiliado", getCodafiliado())
	            .toString();
	    }

	    public boolean equals(Object other) {
	        if ( !(other instanceof TransaccionafiliadocrmPK) ) return false;
	        TransaccionafiliadocrmPK castOther = (TransaccionafiliadocrmPK) other;
	        return new EqualsBuilder()
	            .append(this.getNumtienda(), castOther.getNumtienda())
	            .append(this.getFechatransaccion(), castOther.getFechatransaccion())
	            .append(this.getNumcajafinaliza(), castOther.getNumcajafinaliza())
	            .append(this.getNumtransaccion(), castOther.getNumtransaccion())
	            .append(this.getCodafiliado(), castOther.getCodafiliado())
	            .isEquals();
	    }

	    public int hashCode() {
	        return new HashCodeBuilder()
	            .append(getNumtienda())
	            .append(getFechatransaccion())
	            .append(getNumcajafinaliza())
	            .append(getNumtransaccion())
	            .append(getCodafiliado())
	            .toHashCode();
	    }
		
}
