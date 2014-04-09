package com.becoblohm.cr.sincronizador.hibernate;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class Producto implements Serializable {

    /** identifier field */
    private String codproducto;

    /** persistent field */
    private String descripcioncorta;

    /** nullable persistent field */
    private String descripcionlarga;

    /** persistent field */
    private int codunidadventa;

    /** nullable persistent field */
    private String referenciaproveedor;

    /** nullable persistent field */
    private String marca;

    /** nullable persistent field */
    private String modelo;

    /** persistent field */
    private String coddepartamento;

    /** persistent field */
    private String codlineaseccion;

    /** persistent field */
    private BigDecimal costolista;

    /** persistent field */
    private BigDecimal precioregular;

    /** persistent field */
    private String codimpuesto;

    /** persistent field */
    private int cantidadventaempaque;

    /** nullable persistent field */
    private BigDecimal desctoventaempaque;

    /** persistent field */
    private String indicadesctoempleado;

    /** persistent field */
    private String estadoproducto;

    /** nullable persistent field */
    private Date actualizacion;

    /** full constructor */
    public Producto(String codproducto, String descripcioncorta, String descripcionlarga, int codunidadventa, String referenciaproveedor, String marca, String modelo, String coddepartamento, String codlineaseccion, BigDecimal costolista, BigDecimal precioregular, String codimpuesto, int cantidadventaempaque, BigDecimal desctoventaempaque, String indicadesctoempleado, String estadoproducto, Date actualizacion) {
        this.codproducto = codproducto;
        this.descripcioncorta = descripcioncorta;
        this.descripcionlarga = descripcionlarga;
        this.codunidadventa = codunidadventa;
        this.referenciaproveedor = referenciaproveedor;
        this.marca = marca;
        this.modelo = modelo;
        this.coddepartamento = coddepartamento;
        this.codlineaseccion = codlineaseccion;
        this.costolista = costolista;
        this.precioregular = precioregular;
        this.codimpuesto = codimpuesto;
        this.cantidadventaempaque = cantidadventaempaque;
        this.desctoventaempaque = desctoventaempaque;
        this.indicadesctoempleado = indicadesctoempleado;
        this.estadoproducto = estadoproducto;
        this.actualizacion = actualizacion;
    }

    /** default constructor */
    public Producto() {
    }

    /** minimal constructor */
    public Producto(String codproducto, String descripcioncorta, int codunidadventa, String coddepartamento, String codlineaseccion, BigDecimal costolista, BigDecimal precioregular, String codimpuesto, int cantidadventaempaque, String indicadesctoempleado, String estadoproducto) {
        this.codproducto = codproducto;
        this.descripcioncorta = descripcioncorta;
        this.codunidadventa = codunidadventa;
        this.coddepartamento = coddepartamento;
        this.codlineaseccion = codlineaseccion;
        this.costolista = costolista;
        this.precioregular = precioregular;
        this.codimpuesto = codimpuesto;
        this.cantidadventaempaque = cantidadventaempaque;
        this.indicadesctoempleado = indicadesctoempleado;
        this.estadoproducto = estadoproducto;
    }

    public String getCodproducto() {
        return this.codproducto;
    }

    public void setCodproducto(String codproducto) {
        this.codproducto = codproducto;
    }

    public String getDescripcioncorta() {
        return this.descripcioncorta;
    }

    public void setDescripcioncorta(String descripcioncorta) {
        this.descripcioncorta = descripcioncorta;
    }

    public String getDescripcionlarga() {
        return this.descripcionlarga;
    }

    public void setDescripcionlarga(String descripcionlarga) {
        this.descripcionlarga = descripcionlarga;
    }

    public int getCodunidadventa() {
        return this.codunidadventa;
    }

    public void setCodunidadventa(int codunidadventa) {
        this.codunidadventa = codunidadventa;
    }

    public String getReferenciaproveedor() {
        return this.referenciaproveedor;
    }

    public void setReferenciaproveedor(String referenciaproveedor) {
        this.referenciaproveedor = referenciaproveedor;
    }

    public String getMarca() {
        return this.marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return this.modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getCoddepartamento() {
        return this.coddepartamento;
    }

    public void setCoddepartamento(String coddepartamento) {
        this.coddepartamento = coddepartamento;
    }

    public String getCodlineaseccion() {
        return this.codlineaseccion;
    }

    public void setCodlineaseccion(String codlineaseccion) {
        this.codlineaseccion = codlineaseccion;
    }

    public BigDecimal getCostolista() {
        return this.costolista;
    }

    public void setCostolista(BigDecimal costolista) {
        this.costolista = costolista;
    }

    public BigDecimal getPrecioregular() {
        return this.precioregular;
    }

    public void setPrecioregular(BigDecimal precioregular) {
        this.precioregular = precioregular;
    }

    public String getCodimpuesto() {
        return this.codimpuesto;
    }

    public void setCodimpuesto(String codimpuesto) {
        this.codimpuesto = codimpuesto;
    }

    public int getCantidadventaempaque() {
        return this.cantidadventaempaque;
    }

    public void setCantidadventaempaque(int cantidadventaempaque) {
        this.cantidadventaempaque = cantidadventaempaque;
    }

    public BigDecimal getDesctoventaempaque() {
        return this.desctoventaempaque;
    }

    public void setDesctoventaempaque(BigDecimal desctoventaempaque) {
        this.desctoventaempaque = desctoventaempaque;
    }

    public String getIndicadesctoempleado() {
        return this.indicadesctoempleado;
    }

    public void setIndicadesctoempleado(String indicadesctoempleado) {
        this.indicadesctoempleado = indicadesctoempleado;
    }

    public String getEstadoproducto() {
        return this.estadoproducto;
    }

    public void setEstadoproducto(String estadoproducto) {
        this.estadoproducto = estadoproducto;
    }

    public Date getActualizacion() {
        return this.actualizacion;
    }

    public void setActualizacion(Date actualizacion) {
        this.actualizacion = actualizacion;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("codproducto", getCodproducto())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof Producto) ) return false;
        Producto castOther = (Producto) other;
        return new EqualsBuilder()
            .append(this.getCodproducto(), castOther.getCodproducto())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getCodproducto())
            .toHashCode();
    }
}
