package com.becoblohm.cr.sincronizador.hibernate;

import java.io.Serializable;
import java.util.Date;

import net.sf.hibernate.CallbackException;
import net.sf.hibernate.Session;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class Usuario implements net.sf.hibernate.Lifecycle,Serializable {

    /** identifier field */
    private com.becoblohm.cr.sincronizador.hibernate.UsuarioPK comp_id;

    /** persistent field */
    private String codigobarra;

    /** persistent field */
    private String codperfil;

    /** persistent field */
    private String clave;

    /** persistent field */
    private String nivelauditoria;

    /** nullable persistent field */
    private String nombre;

    /** persistent field */
    private String puedecambiarclave;

    /** persistent field */
    private String indicacambiarclave;

    /** persistent field */
    private Date fechacreacion;

    /** nullable persistent field */
    private Date ultimocambioclave;

    /** persistent field */
    private short tiempovigenciaclave;

    /** persistent field */
    private String regvigente;

    /** nullable persistent field */
    private Date actualizacion;

    /** full constructor */
    public Usuario(com.becoblohm.cr.sincronizador.hibernate.UsuarioPK comp_id, String codigobarra, String codperfil, String clave, String nivelauditoria, String nombre, String puedecambiarclave, String indicacambiarclave, Date fechacreacion, Date ultimocambioclave, short tiempovigenciaclave, String regvigente, Date actualizacion) {
        this.comp_id = comp_id;
        this.codigobarra = codigobarra;
        this.codperfil = codperfil;
        this.clave = clave;
        this.nivelauditoria = nivelauditoria;
        this.nombre = nombre;
        this.puedecambiarclave = puedecambiarclave;
        this.indicacambiarclave = indicacambiarclave;
        this.fechacreacion = fechacreacion;
        this.ultimocambioclave = ultimocambioclave;
        this.tiempovigenciaclave = tiempovigenciaclave;
        this.regvigente = regvigente;
        this.actualizacion = actualizacion;
    }

    /** default constructor */
    public Usuario() {
    }

    /** minimal constructor */
    public Usuario(com.becoblohm.cr.sincronizador.hibernate.UsuarioPK comp_id, String codigobarra, String codperfil, String clave, String nivelauditoria, String puedecambiarclave, String indicacambiarclave, Date fechacreacion, short tiempovigenciaclave, String regvigente) {
        this.comp_id = comp_id;
        this.codigobarra = codigobarra;
        this.codperfil = codperfil;
        this.clave = clave;
        this.nivelauditoria = nivelauditoria;
        this.puedecambiarclave = puedecambiarclave;
        this.indicacambiarclave = indicacambiarclave;
        this.fechacreacion = fechacreacion;
        this.tiempovigenciaclave = tiempovigenciaclave;
        this.regvigente = regvigente;
    }

    public com.becoblohm.cr.sincronizador.hibernate.UsuarioPK getComp_id() {
        return this.comp_id;
    }

    public void setComp_id(com.becoblohm.cr.sincronizador.hibernate.UsuarioPK comp_id) {
        this.comp_id = comp_id;
    }

    public String getCodigobarra() {
        return this.codigobarra;
    }

    public void setCodigobarra(String codigobarra) {
        this.codigobarra = codigobarra;
    }

    public String getCodperfil() {
        return this.codperfil;
    }

    public void setCodperfil(String codperfil) {
        this.codperfil = codperfil;
    }

    public String getClave() {
        return this.clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getNivelauditoria() {
        return this.nivelauditoria;
    }

    public void setNivelauditoria(String nivelauditoria) {
        this.nivelauditoria = nivelauditoria;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPuedecambiarclave() {
        return this.puedecambiarclave;
    }

    public void setPuedecambiarclave(String puedecambiarclave) {
        this.puedecambiarclave = puedecambiarclave;
    }

    public String getIndicacambiarclave() {
        return this.indicacambiarclave;
    }

    public void setIndicacambiarclave(String indicacambiarclave) {
        this.indicacambiarclave = indicacambiarclave;
    }

    public Date getFechacreacion() {
        return this.fechacreacion;
    }

    public void setFechacreacion(Date fechacreacion) {
        this.fechacreacion = fechacreacion;
    }

    public Date getUltimocambioclave() {
        return this.ultimocambioclave;
    }

    public void setUltimocambioclave(Date ultimocambioclave) {
        this.ultimocambioclave = ultimocambioclave;
    }

    public short getTiempovigenciaclave() {
        return this.tiempovigenciaclave;
    }

    public void setTiempovigenciaclave(short tiempovigenciaclave) {
        this.tiempovigenciaclave = tiempovigenciaclave;
    }

    public String getRegvigente() {
        return this.regvigente;
    }

    public void setRegvigente(String regvigente) {
        this.regvigente = regvigente;
    }

    public Date getActualizacion() {
        return this.actualizacion;
    }

    public void setActualizacion(Date actualizacion) {
        this.actualizacion = actualizacion;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("comp_id", getComp_id())
            .append("codigobarra", getCodigobarra())
            .append("codperfil", getCodperfil())
            .append("clave", getClave())
            .append("nivelauditoria", getNivelauditoria())
            .append("nombre", getNombre())
            .append("puedecambiarclave", getPuedecambiarclave())
            .append("indicacambiarclave", getIndicacambiarclave())
            .append("fechacreacion", getFechacreacion())
            .append("ultimocambioclave", getUltimocambioclave())
            .append("tiempovigenciaclave", getTiempovigenciaclave())
            .append("regvigente", getRegvigente())
            .append("actualizacion", getActualizacion())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof Usuario) ) return false;
        Usuario castOther = (Usuario) other;
        return new EqualsBuilder()
            .append(this.getComp_id(), castOther.getComp_id())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getComp_id())
            .toHashCode();
    }

	/* (no Javadoc)
	 * @see net.sf.hibernate.Lifecycle#onSave(net.sf.hibernate.Session)
	 * @param s
	 * @return
	 * @throws CallbackException
	 */
	public boolean onSave(Session s) throws CallbackException {
		return false;
	}

	/* (no Javadoc)
	 * @see net.sf.hibernate.Lifecycle#onUpdate(net.sf.hibernate.Session)
	 * @param s
	 * @return
	 * @throws CallbackException
	 */
	public boolean onUpdate(Session s) throws CallbackException {
		return false;
	}

	/* (no Javadoc)
	 * @see net.sf.hibernate.Lifecycle#onDelete(net.sf.hibernate.Session)
	 * @param s
	 * @return
	 * @throws CallbackException
	 */
	public boolean onDelete(Session s) throws CallbackException {
		return false;
	}

	/* (no Javadoc)
	 * @see net.sf.hibernate.Lifecycle#onLoad(net.sf.hibernate.Session, java.io.Serializable)
	 * @param s
	 * @param id
	 */
	public void onLoad(Session s, Serializable id) {
		
	}

}
