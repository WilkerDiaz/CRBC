package com.becoblohm.cr.sincronizador.hibernate;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class Afiliado implements Serializable {

    /** identifier field */
    private String codafiliado;

    /** persistent field */
    private String tipoafiliado;

    /** persistent field */
    private String nombre;

	/** nullable persistent field */
	private String apellido;

    /** persistent field */
    private short numtienda;

    /** nullable persistent field */
    private String numficha;

    /** nullable persistent field */
    private String coddepartamento;

    /** nullable persistent field */
    private String codcargo;

    /** nullable persistent field */
    private String nitcliente;

    /** persistent field */
    private String direccion;

    /** nullable persistent field */
    private String direccionfiscal;

	/** nullable persistent field */
	private String email;

    /** nullable persistent field */
    private String codarea;

    /** nullable persistent field */
    private String numtelefono;

	/** nullable persistent field */
	private String codarea1;

	/** nullable persistent field */
	private String numtelefono1;

    /** persistent field */
    private Date fechaafiliacion;

    /** persistent field */
    private String exentoimpuesto;

    /** persistent field */
    private String registrado;

    /** persistent field */
    private String contactar;

    /** persistent field */
    private String codregion;

    /** persistent field */
    private String estadoafiliado;

	/** persistent field */
	private String estadocolaborador;

    /** nullable persistent field */
    private Date actualizacion;
    
    /*Nuevos atributos para la sincronizacion de afiliados CRM Wdiaz
      Estós poseen sus set y get y se colocaron en los constructores*/
    private String genero;
    private String estadocivil;
    private   Date fechanacimiento;
    //fin 

    /** full constructor */
    public Afiliado(String codafiliado, String tipoafiliado, String nombre, String apellido, short numtienda, String numficha, String coddepartamento, String codcargo, String nitcliente, String direccion, String direccionfiscal, String email, String codarea, String numtelefono, String codarea1, String numtelefono1, Date fechaafiliacion, String exentoimpuesto, String registrado, String contactar, String codregion, String estadoafiliado, String estadocolaborador, Date actualizacion, String genero, String estadocivil, Date fechanacimiento) {
        this.codafiliado = codafiliado;
        this.tipoafiliado = tipoafiliado;
        this.nombre = nombre;
		this.apellido = apellido;
        this.numtienda = numtienda;
        this.numficha = numficha;
        this.coddepartamento = coddepartamento;
        this.codcargo = codcargo;
        this.nitcliente = nitcliente;
        this.direccion = direccion;
        this.direccionfiscal = direccionfiscal;
        this.email = email;
        this.codarea = codarea;
        this.numtelefono = numtelefono;
		this.codarea1 = codarea1;
		this.numtelefono1 = numtelefono1;
        this.fechaafiliacion = fechaafiliacion;
        this.exentoimpuesto = exentoimpuesto;
        this.registrado = registrado;
        this.contactar = contactar;
        this.codregion = codregion;
        this.estadoafiliado = estadoafiliado;
		this.estadocolaborador = estadocolaborador;
        this.actualizacion = actualizacion;
        //nuevos CRM
        this.genero = genero;
        this.estadocivil = estadocivil;
        this.fechanacimiento = fechanacimiento;
        //fin Nuevos
    }

    /** default constructor */
    public Afiliado() {
    }

    /** minimal constructor */
    public Afiliado(String codafiliado, String tipoafiliado, String nombre, short numtienda, String direccion, Date fechaafiliacion, String exentoimpuesto, String registrado, String contactar, String codregion, String estadoafiliado, String estadocolaborador, String genero, String estadocivil) {
        this.codafiliado = codafiliado;
        this.tipoafiliado = tipoafiliado;
        this.nombre = nombre;
        this.numtienda = numtienda;
        this.direccion = direccion;
        this.fechaafiliacion = fechaafiliacion;
        this.exentoimpuesto = exentoimpuesto;
        this.registrado = registrado;
        this.contactar = contactar;
        this.codregion = codregion;
        this.estadoafiliado = estadoafiliado;
		this.estadocolaborador = estadocolaborador;
		//nuevo crm
		this.genero = genero;
		this.estadocivil = estadocivil;
		//fin
    }

    public String getCodafiliado() {
        return this.codafiliado;
    }

    public void setCodafiliado(String codafiliado) {
        this.codafiliado = codafiliado;
    }

    public String getTipoafiliado() {
        return this.tipoafiliado;
    }

    public void setTipoafiliado(String tipoafiliado) {
        this.tipoafiliado = tipoafiliado;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public short getNumtienda() {
        return this.numtienda;
    }

    public void setNumtienda(short numtienda) {
        this.numtienda = numtienda;
    }

    public String getNumficha() {
        return this.numficha;
    }

    public void setNumficha(String numficha) {
        this.numficha = numficha;
    }

    public String getCoddepartamento() {
        return this.coddepartamento;
    }

    public void setCoddepartamento(String coddepartamento) {
        this.coddepartamento = coddepartamento;
    }

    public String getCodcargo() {
        return this.codcargo;
    }

    public void setCodcargo(String codcargo) {
        this.codcargo = codcargo;
    }

    public String getNitcliente() {
        return this.nitcliente;
    }

    public void setNitcliente(String nitcliente) {
        this.nitcliente = nitcliente;
    }

    public String getDireccion() {
        return this.direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getDireccionfiscal() {
        return this.direccionfiscal;
    }

    public void setDireccionfiscal(String direccionfiscal) {
        this.direccionfiscal = direccionfiscal;
    }

    public String getCodarea() {
        return this.codarea;
    }

    public void setCodarea(String codarea) {
        this.codarea = codarea;
    }

    public String getNumtelefono() {
        return this.numtelefono;
    }

    public void setNumtelefono(String numtelefono) {
        this.numtelefono = numtelefono;
    }

    public Date getFechaafiliacion() {
        return this.fechaafiliacion;
    }

    public void setFechaafiliacion(Date fechaafiliacion) {
        this.fechaafiliacion = fechaafiliacion;
    }

    public String getExentoimpuesto() {
        return this.exentoimpuesto;
    }

    public void setExentoimpuesto(String exentoimpuesto) {
        this.exentoimpuesto = exentoimpuesto;
    }

    public String getRegistrado() {
        return this.registrado;
    }

    public void setRegistrado(String registrado) {
        this.registrado = registrado;
    }

    public String getContactar() {
        return this.contactar;
    }

    public void setContactar(String contactar) {
        this.contactar = contactar;
    }

    public String getCodregion() {
        return this.codregion;
    }

    public void setCodregion(String codregion) {
        this.codregion = codregion;
    }

    public String getEstadoafiliado() {
        return this.estadoafiliado;
    }

    public void setEstadoafiliado(String estadoafiliado) {
        this.estadoafiliado = estadoafiliado;
    }

	public String getEstadocolaborador() {
		return this.estadocolaborador;
	}

	public void setEstadocolaborador(String estadocolaborador) {
		this.estadocolaborador = estadocolaborador;
	}

    public Date getActualizacion() {
        return this.actualizacion;
    }

    public void setActualizacion(Date actualizacion) {
        this.actualizacion = actualizacion;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("codafiliado", getCodafiliado())
            .append("tipoafiliado", getTipoafiliado())
            .append("nombre", getNombre())
			.append("apellido", getApellido())
            .append("numtienda", getNumtienda())
            .append("numficha", getNumficha())
            .append("coddepartamento", getCoddepartamento())
            .append("codcargo", getCodcargo())
            .append("nitcliente", getNitcliente())
            .append("direccion", getDireccion())
            .append("direccionfiscal", getDireccionfiscal())
			.append("email", getDireccionfiscal())
			.append("codarea", getCodarea())
			.append("numtelefono", getNumtelefono())
            .append("codarea1", getCodarea())
            .append("numtelefono1", getNumtelefono())
            .append("fechaafiliacion", getFechaafiliacion())
            .append("exentoimpuesto", getExentoimpuesto())
            .append("registrado", getRegistrado())
            .append("contactar", getContactar())
            .append("codregion", getCodregion())
            .append("estadoafiliado", getEstadoafiliado())
			.append("estadocolaborador", getEstadocolaborador())
			//cambios CRM
			.append("genero", getGenero())
			.append("estadocivil", getEstadocivil())
			.append("fechanacimiento", getFechanacimiento())
			//fin de los Cambios
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof Afiliado) ) return false;
        Afiliado castOther = (Afiliado) other;
        return new EqualsBuilder()
            .append(this.getCodafiliado(), castOther.getCodafiliado())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getCodafiliado())
            .toHashCode();
    }

	/**
	 * Método getApellido
	 * 
	 * @return
	 * String
	 */
	public String getApellido() {
		return apellido;
	}

	/**
	 * Método setApellido
	 * 
	 * @param string
	 * void
	 */
	public void setApellido(String string) {
		apellido = string;
	}

	/**
	 * @return
	 */
	public String getCodarea1() {
		return codarea1;
	}

	/**
	 * @return
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @return
	 */
	public String getNumtelefono1() {
		return numtelefono1;
	}

	/**
	 * @param string
	 */
	public void setCodarea1(String string) {
		codarea1 = string;
	}

	/**
	 * @param string
	 */
	public void setEmail(String string) {
		email = string;
	}

	/**
	 * @param string
	 */
	public void setNumtelefono1(String string) {
		numtelefono1 = string;
	}

	/**
	 * @return el estadocivil
	 */
	public String getEstadocivil() {
		return estadocivil;
	}

	/**
	 * @param estadocivil el estadocivil a establecer
	 */
	public void setEstadocivil(String estadocivil) {
		this.estadocivil = estadocivil;
	}

	/**
	 * @return el fechanacimiento
	 */
	public Date getFechanacimiento() {
		return fechanacimiento;
	}

	/**
	 * @param fechanacimiento el fechanacimiento a establecer
	 */
	public void setFechanacimiento(Date fechanacimiento) {
		this.fechanacimiento = fechanacimiento;
	}

	/**
	 * @return el genero
	 */
	public String getGenero() {
		return genero;
	}

	/**
	 * @param genero el genero a establecer
	 */
	public void setGenero(String genero) {
		this.genero = genero;
	}

}
