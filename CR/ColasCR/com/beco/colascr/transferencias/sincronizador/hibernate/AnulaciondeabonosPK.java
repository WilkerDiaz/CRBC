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
	 * M�todo getFechaabono
	 * 
	 * @return
	 * Date
	 */
	public Date getFechaabono() {
		return fechaabono;
	}

	/**
	 * M�todo getFechaabonoanulado
	 * 
	 * @return
	 * Date
	 */
	public Date getFechaabonoanulado() {
		return fechaabonoanulado;
	}

	/**
	 * M�todo getNumabono
	 * 
	 * @return
	 * int
	 */
	public int getNumabono() {
		return numabono;
	}

	/**
	 * M�todo getNumabonoanulado
	 * 
	 * @return
	 * int
	 */
	public int getNumabonoanulado() {
		return numabonoanulado;
	}

	/**
	 * M�todo getNumcaja
	 * 
	 * @return
	 * int
	 */
	public int getNumcaja() {
		return numcaja;
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
	 * M�todo setFechaabono
	 * 
	 * @param date
	 * void
	 */
	public void setFechaabono(Date date) {
		fechaabono = date;
	}

	/**
	 * M�todo setFechaabonoanulado
	 * 
	 * @param date
	 * void
	 */
	public void setFechaabonoanulado(Date date) {
		fechaabonoanulado = date;
	}

	/**
	 * M�todo setNumabono
	 * 
	 * @param i
	 * void
	 */
	public void setNumabono(int i) {
		numabono = i;
	}

	/**
	 * M�todo setNumabonoanulado
	 * 
	 * @param i
	 * void
	 */
	public void setNumabonoanulado(int i) {
		numabonoanulado = i;
	}

	/**
	 * M�todo setNumcaja
	 * 
	 * @param i
	 * void
	 */
	public void setNumcaja(int i) {
		numcaja = i;
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
