package com.beco.colascr.transferencias.sincronizador.hibernate;

import java.io.Serializable;
import java.sql.Time;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.beco.colascr.transferencias.sincronizador.hibernate.base.EntidadMarcable;


public class Transaccionafiliadocrm implements Serializable, EntidadMarcable {
	
	private com.beco.colascr.transferencias.sincronizador.hibernate.TransaccionafiliadocrmPK comp_id;
	private String contribuyente;
	private String regactualizado;
	private Time horainiciacrm;
	private Time horafinalizacrm;
	
	
	//constructor por defecto
	public Transaccionafiliadocrm()
	{}
	//constructor full
	public Transaccionafiliadocrm(com.beco.colascr.transferencias.sincronizador.hibernate.TransaccionafiliadocrmPK comp_id, String contribuyente, String regactualizado, Time horainiciacrm, Time horafinalizacrm)
	{
		this.comp_id = comp_id;
		this.contribuyente = contribuyente;
		this.regactualizado = regactualizado;
		this.horainiciacrm = horainiciacrm;
		this.horafinalizacrm = horafinalizacrm;
	}

	public String getContribuyente() {
		return contribuyente;
	}

	public void setContribuyente(String contribuyente) {
		this.contribuyente = contribuyente;
	}
	
	public com.beco.colascr.transferencias.sincronizador.hibernate.TransaccionafiliadocrmPK getComp_id() {
		return comp_id;
	}

	public void setComp_id(
			com.beco.colascr.transferencias.sincronizador.hibernate.TransaccionafiliadocrmPK comp_id) {
		this.comp_id = comp_id;
	}
	 
	 public String toString() {
	        return new ToStringBuilder(this)
	            .append("comp_id", getComp_id())
	            .append("contribuyente", getContribuyente())
	            .append("regactualizado", getRegactualizado())
	            .append("horainiciacrm", getHorainiciacrm())
	            .append("horafinalizacrm", getHorafinalizacrm())
	            .toString();
	    }
	 
	 public boolean equals(Object other) {
	        if ( !(other instanceof Transaccionafiliadocrm) ) return false;
	        Transaccionafiliadocrm castOther = (Transaccionafiliadocrm) other;
	        return new EqualsBuilder()
	            .append(this.getComp_id(), castOther.getComp_id())
	            .isEquals();
	    }

	    public int hashCode() {
	        return new HashCodeBuilder()
	            .append(getComp_id())
	            .toHashCode();
	    }
	    public String getRegactualizado() {
			return regactualizado;
		}
		public void setRegactualizado(String regactualizado) {
			this.regactualizado = regactualizado;
		}
		public Time getHorafinalizacrm() {
			return horafinalizacrm;
		}
		public void setHorafinalizacrm(Time horafinalizacrm) {
			this.horafinalizacrm = horafinalizacrm;
		}
		public Time getHorainiciacrm() {
			return horainiciacrm;
		}
		public void setHorainiciacrm(Time horainiciacrm) {
			this.horainiciacrm = horainiciacrm;
		}
}
