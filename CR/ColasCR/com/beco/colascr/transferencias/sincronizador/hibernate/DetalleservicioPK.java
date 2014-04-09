package com.beco.colascr.transferencias.sincronizador.hibernate;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class DetalleservicioPK implements Serializable {

    /** identifier field */
    private int numtienda;

    /** identifier field */
    private String codtiposervicio;
    
	/** identifier field */
	private int numservicio;
	
	/** identifier field */
	private Date fecha;
	
	/** identifier field */
	private String codproducto;
	
	/** identifier field */
	//private String codcondicionventa;
	
	/** identifier field */
	private int correlativoitem;

    /** full constructor */
    public DetalleservicioPK(int numtienda, String codtiposervicio, int numservicio, Date fecha, String codproducto, /*String codcondicionventa, */int correlativoitem) {
        this.numtienda = numtienda;
        this.codtiposervicio = codtiposervicio;
        this.numservicio = numservicio;
        this.fecha = fecha;
        this.codproducto = codproducto;
        //this.codcondicionventa = codcondicionventa;
        this.correlativoitem = correlativoitem;
    }

    /** default constructor */
    public DetalleservicioPK() {
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("numtienda", getNumtienda())
            .append("codtiposervicio", getCodtiposervicio())
            .append("numservicio", getNumservicio())
			.append("fecha", getFecha())
			.append("codproducto", getCodproducto())
			//.append("codcondicionventa", getCodcondicionventa())
			.append("correlativoitem", getCorrelativoitem())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof DetalleservicioPK) ) return false;
        DetalleservicioPK castOther = (DetalleservicioPK) other;
        return new EqualsBuilder()
            .append(this.getNumtienda(), castOther.getNumtienda())
            .append(this.getCodtiposervicio(), castOther.getCodtiposervicio())
            .append(this.getNumservicio(), castOther.getNumservicio())
			.append(this.getFecha(), castOther.getFecha())
			.append(this.getCodproducto(), castOther.getCodproducto())
			//.append(this.getCodcondicionventa(), castOther.getCodcondicionventa())
			.append(this.getCorrelativoitem(), castOther.getCorrelativoitem())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getNumtienda())
            .append(getCodtiposervicio())
            .append(getNumservicio())
			.append(getFecha())
			.append(getCodproducto())
			//.append(getCodcondicionventa())
			.append(getCorrelativoitem())
            .toHashCode();
    }


	/**
	 * M�todo getCodcondicionventa
	 * 
	 * @return
	 * String
	 */
	/*public String getCodcondicionventa() {
		return codcondicionventa;
	}

	/**
	 * M�todo getCodproducto
	 * 
	 * @return
	 * String
	 */
	public String getCodproducto() {
		return codproducto;
	}

	/**
	 * M�todo getCodtiposervicio
	 * 
	 * @return
	 * String
	 */
	public String getCodtiposervicio() {
		return codtiposervicio;
	}

	/**
	 * M�todo getCorrelativoitem
	 * 
	 * @return
	 * int
	 */
	public int getCorrelativoitem() {
		return correlativoitem;
	}

	/**
	 * M�todo getFecha
	 * 
	 * @return
	 * Date
	 */
	public Date getFecha() {
		return fecha;
	}

	/**
	 * M�todo getNumservicio
	 * 
	 * @return
	 * int
	 */
	public int getNumservicio() {
		return numservicio;
	}

	/**
	 * M�todo getNumtienda
	 * 
	 * @return
	 * int
	 */
	public int getNumtienda() {
		return numtienda;
	}

	/**
	 * M�todo setCodcondicionventa
	 * 
	 * @param string
	 * void
	 */
	/*public void setCodcondicionventa(String string) {
		codcondicionventa = string;
	}

	/**
	 * M�todo setCodproducto
	 * 
	 * @param string
	 * void
	 */
	public void setCodproducto(String string) {
		codproducto = string;
	}

	/**
	 * M�todo setCodtiposervicio
	 * 
	 * @param string
	 * void
	 */
	public void setCodtiposervicio(String string) {
		codtiposervicio = string;
	}

	/**
	 * M�todo setCorrelativoitem
	 * 
	 * @param i
	 * void
	 */
	public void setCorrelativoitem(int i) {
		correlativoitem = i;
	}

	/**
	 * M�todo setFecha
	 * 
	 * @param date
	 * void
	 */
	public void setFecha(Date date) {
		fecha = date;
	}

	/**
	 * M�todo setNumservicio
	 * 
	 * @param i
	 * void
	 */
	public void setNumservicio(int i) {
		numservicio = i;
	}

	/**
	 * M�todo setNumtienda
	 * 
	 * @param i
	 * void
	 */
	public void setNumtienda(int i) {
		numtienda = i;
	}

}
