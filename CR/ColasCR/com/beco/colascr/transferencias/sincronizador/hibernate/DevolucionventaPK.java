package com.beco.colascr.transferencias.sincronizador.hibernate;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class DevolucionventaPK implements Serializable {
	private int numtiendadevolucion;
	private Date fechadevolucion;
	private int numcajadevolucion;
	private int numtransacciondev;
	private int numtiendaventa;
	private Date fechaventa;
	private int numcajaventa;
	private int numtransaccionvta;

    /** full constructor */
    public DevolucionventaPK(int numtiendadevolucion, Date fechadevolucion, int numcajadevolucion, int numtransacciondev, int numtiendaventa, Date fechaventa, int numcajaventa, int numtransaccionvta) {
        this.numtiendadevolucion = numtiendadevolucion;
        this.fechadevolucion = fechadevolucion;
        this.numcajadevolucion = numcajadevolucion;
        this.numtransacciondev = numtransacciondev;
        this.numtiendaventa = numtiendaventa;
        this.fechaventa = fechaventa;
        this.numcajaventa = numcajaventa;
        this.numtransaccionvta = numtransaccionvta;
    }

    /** default constructor */
    public DevolucionventaPK() {
    }

    public String toString() {
        return new ToStringBuilder(this)
			.append("numtiendadevolucion", getNumtiendadevolucion())
			.append("fechadevolucion", getFechadevolucion())
			.append("numcajadevolucion", getNumcajadevolucion())
			.append("numtransacciondev", getNumtransacciondev())
			.append("numtiendaventa", getNumtiendaventa())
			.append("fechaventa", getFechaventa())
			.append("numcajaventa", getNumcajaventa())
			.append("numtransaccionvta",getNumtransaccionvta())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof DevolucionventaPK) ) return false;
        DevolucionventaPK castOther = (DevolucionventaPK) other;
        return new EqualsBuilder()
			.append(this.getNumtiendadevolucion(), castOther.getNumtiendadevolucion())
			.append(this.getFechadevolucion(), castOther.getFechadevolucion())
			.append(this.getNumcajadevolucion(), castOther.getNumcajadevolucion())
			.append(this.getNumtransacciondev(), castOther.getNumtransacciondev())
			.append(this.getNumtiendaventa(), castOther.getNumtiendaventa())
			.append(this.getFechaventa(), castOther.getFechaventa())
			.append(this.getNumcajaventa(), castOther.getNumcajaventa())
			.append(this.getNumtransaccionvta(),castOther.getNumtransaccionvta())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
			.append(getNumtiendadevolucion())
			.append(getFechadevolucion())
			.append(getNumcajadevolucion())
			.append(getNumtransacciondev())
			.append(getNumtiendaventa())
			.append(getFechaventa())
			.append(getNumcajaventa())
			.append(getNumtransaccionvta())
            .toHashCode();
    }

	/**
	 * M�todo getFechadevolucion
	 * 
	 * @return
	 * Date
	 */
	public Date getFechadevolucion() {
		return fechadevolucion;
	}

	/**
	 * M�todo getFechaventa
	 * 
	 * @return
	 * Date
	 */
	public Date getFechaventa() {
		return fechaventa;
	}

	/**
	 * M�todo getNumcajadevolucion
	 * 
	 * @return
	 * int
	 */
	public int getNumcajadevolucion() {
		return numcajadevolucion;
	}

	/**
	 * M�todo getNumcajaventa
	 * 
	 * @return
	 * int
	 */
	public int getNumcajaventa() {
		return numcajaventa;
	}

	/**
	 * M�todo getNumtiendadevolucion
	 * 
	 * @return
	 * int
	 */
	public int getNumtiendadevolucion() {
		return numtiendadevolucion;
	}

	/**
	 * M�todo getNumtiendaventa
	 * 
	 * @return
	 * int
	 */
	public int getNumtiendaventa() {
		return numtiendaventa;
	}

	/**
	 * M�todo getNumtransacciondev
	 * 
	 * @return
	 * int
	 */
	public int getNumtransacciondev() {
		return numtransacciondev;
	}

	/**
	 * M�todo getNumtransaccionvta
	 * 
	 * @return
	 * int
	 */
	public int getNumtransaccionvta() {
		return numtransaccionvta;
	}

	/**
	 * M�todo setFechadevolucion
	 * 
	 * @param date
	 * void
	 */
	public void setFechadevolucion(Date date) {
		fechadevolucion = date;
	}

	/**
	 * M�todo setFechaventa
	 * 
	 * @param date
	 * void
	 */
	public void setFechaventa(Date date) {
		fechaventa = date;
	}

	/**
	 * M�todo setNumcajadevolucion
	 * 
	 * @param i
	 * void
	 */
	public void setNumcajadevolucion(int i) {
		numcajadevolucion = i;
	}

	/**
	 * M�todo setNumcajaventa
	 * 
	 * @param i
	 * void
	 */
	public void setNumcajaventa(int i) {
		numcajaventa = i;
	}

	/**
	 * M�todo setNumtiendadevolucion
	 * 
	 * @param i
	 * void
	 */
	public void setNumtiendadevolucion(int i) {
		numtiendadevolucion = i;
	}

	/**
	 * M�todo setNumtiendaventa
	 * 
	 * @param i
	 * void
	 */
	public void setNumtiendaventa(int i) {
		numtiendaventa = i;
	}

	/**
	 * M�todo setNumtransacciondev
	 * 
	 * @param i
	 * void
	 */
	public void setNumtransacciondev(int i) {
		numtransacciondev = i;
	}

	/**
	 * M�todo setNumtransaccionvta
	 * 
	 * @param i
	 * void
	 */
	public void setNumtransaccionvta(int i) {
		numtransaccionvta = i;
	}

}
