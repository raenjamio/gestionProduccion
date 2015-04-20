package com.gestion.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.hibernate.search.annotations.Indexed;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.annotations.GenericGenerator;  
import org.hibernate.annotations.Parameter;  

@Entity
@Table(name = "necesidades")
@Indexed
@XmlRootElement
public class Necesidad extends BaseObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private Long prioridad;
	private Integer cantidad;
	private boolean finalizado;
	private Date fechaCreacion;
	private Producto producto;
	

    @Id  
    @Column(name="id") 
    @GeneratedValue(strategy = GenerationType.AUTO)
    //@GeneratedValue(generator="gen")  
    //@GenericGenerator(name="gen", strategy="foreign",   
    //parameters=@Parameter(name="property", value="producto"))  
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	//@OneToOne(fetch = FetchType.LAZY)
	@OneToOne(mappedBy="necesidad", cascade=CascadeType.ALL)
	//@PrimaryKeyJoinColumn
	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}


	public Long getPrioridad() {
		return prioridad;
	}

	public void setPrioridad(Long prioridad) {
		this.prioridad = prioridad;
	}
  

	@Column(name = "finalizado")
	public boolean getFinalizado() {
		return finalizado;
	}

	public void setFinalizado(boolean finalizado) {
		this.finalizado = finalizado;
	}


	
	public Necesidad(){
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}


	@Override
	public boolean equals(Object o) {
		  if (this == o) {
	            return true;
	        }
	        if (!(o instanceof Necesidad)) {
	            return false;
	        }

	        final Necesidad necesidad = (Necesidad) o;

	        return !(id != null ? !id.equals(necesidad.id) : necesidad.id != null);
	}

    /**
     * {@inheritDoc}
     */
    public int hashCode() {
        return (id != null ? id.hashCode() : 0);
    }

    /**
     * {@inheritDoc}
     */
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE)
                .append(this.id)
                .toString();
    }


}
