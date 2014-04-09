package com.beco.colascr.transferencias.sincronizador.hibernate;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class AnulaciondeabonosPK implements Serializable {
	private int numtienda;
	private int numcaja;
	private int numabono;
	private Date fechaabono;
	private int numabonoanulado;
	private Date fechaabonoanulado;
	private int numservicio;

    /** full constructor */
    public AnulaciondeabonosPK(int numtienda, int numcaja, int numabono, Date fechaabono, int numabonoanulado, Date fechaabonoanulado, int numservicio) {
        this.numtienda = numtienda;
        this.numcaja = numcaja;
        this.numabono = numabono;
        this.fechaabono = fechaabono;
        this.numabonoanulado = numabonoanulado;
        this.numservicio = numservicio;
    }

    /** default constructor */
    public AnulaciondeabonosPK() {
    }

    public String toString() {
        return new ToStringBuilder(this)
			.append("numtienda", getNumtienda())
			.append("numcaja", getNumcaja())
			.append("numabono", getNumabono())
			.append("fechaabono", getFechaabono())
			.append("numabonoanulado", getNumabonoanulado())
			.append("fechaabonoanulado", getFechaabonoanulado())
			.append("numservicio", getNumservicio())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof AnulaciondeabonosPK) ) return false;
        AnulaciondeabonosPK castOther = (AnulaciondeabonosPK) other;
        return new EqualsBuilder()
			.append(this.getNumtienda(), castOther.getNumtienda())
			.append(this.getNumcaja(), castOther.getNumcaja())
			.append(this.getNumabono(), castOther.getNumabono())
			.append(this.getFechaabono(), castOther.getFechaabono())
			.append(this.getNumabonoanulado(), castOther.getNumabonoanulado())
			.append(this.getFechaabonoanulado(), castOther.getFechaabonoanulado())
			.append(this.getNumservicio(), castOther.getNumservicio())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
			.append(getNumtienda())
			.append(getNumcaja())
			.append(getNumabono())
			.append(getFechaabono())
			.append(getNumabonoanulado())
			.append(getFechaabonoanulado())
			.append(getNumservicio())
            .toHashCode();
    }


	/**
	 * Método getFechaabono
	 * 
	 * @return
	 * Date
	 */
	public Date getFechaabono() {
		return fechaabono;
	}

	/**
	 * Método getFechaabonoanulado
	 * 
	 * @return
	 * Date
	 */
	public Date getFechaabonoanulado() {
		return fechaabonoanulado;
	}

	/**
	 * Método getNumabono
	 * 
	 * @return
	 * int
	 */
	public int getNumabono() {
		return numabono;
	}

	/**
	 * Método getNumabonoanulado
	 * 
	 * @return
	 * int
	 */
	public int getNumabonoanulado() {
		return numabonoanulado;
	}

	/**
	 * Método getNumcaja
	 * 
	 * @return
	 * int
	 */
	public int getNumcaja() {
		return numcaja;
	}

	/**
	 * Método getNumservicio
	 * 
	 * @return
	 * int
	 */
	public int getNumservicio() {
		return numservicio;
	}

	/**
	 * Método getNumtienda
	 * 
	 * @return
	 * int
	 */
	public int getNumtienda() {
		return numtienda;
	}

	/**
	 * Método setFechaabono
	 * 
	 * @param date
	 * void
	 */
	public void setFechaabono(Date date) {
		fechaabono = date;
	}

	/**
	 * Método setFechaabonoanulado
	 * 
	 * @param date
	 * void
	 */
	public void setFechaabonoanulado(Date date) {
		fechaabonoanulado = date;
	}

	/**
	 * Método setNumabono
	 * 
	 * @param i
	 * void
	 */
	public void setNumabono(int i) {
		numabono = i;
	}

	/**
	 * Método setNumabonoanulado
	 * 
	 * @param i
	 * void
	 */
	public void setNumabonoanulado(int i) {
		numabonoanulado = i;
	}

	/**
	 * Método setNumcaja
	 * 
	 * @param i
	 * void
	 */
	public void setNumcaja(int i) {
		numcaja = i;
	}

	/**
	 * Método setNumservicio
	 * 
	 * @param i
	 * void
	 */
	public void setNumservicio(int i) {
		numservicio = i;
	}

	/**
	 * Método setNumtienda
	 * 
	 * @param i
	 * void
	 */
	public void setNumtienda(int i) {
		numtienda = i;
	}

}
