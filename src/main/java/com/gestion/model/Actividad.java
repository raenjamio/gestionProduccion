package com.gestion.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.hibernate.search.annotations.Indexed;

@Entity
@Table(name = "actividades")
@Indexed
@XmlRootElement
public class Actividad extends BaseObject implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private Long id;
	private String rolUser;
	private Necesidad necesidad;
	private String descripcion;
	private Date fechaCreacion;

    @Id  
    @Column(name="id") 
    @GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		 return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE)
         .append(this.id)
         .toString();
	}

	@Override
	public boolean equals(Object o) {
		 if (this == o) {
	            return true;
	        }
	        if (!(o instanceof Estado)) {
	            return false;
	        }

	        final Actividad actividad = (Actividad) o;

	        return !(id != null ? !id.equals(actividad) : actividad.id != null);
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return (id != null ? id.hashCode() : 0);
	}

	public String getRolUser() {
		return rolUser;
	}

	public void setRolUser(String rolUser) {
		this.rolUser = rolUser;
	}
	
	@ManyToOne//(cascade=CascadeType.ALL)
	@JoinColumn(nullable = false)
	public Necesidad getNecesidad() {
		return necesidad;
	}

	public void setNecesidad(Necesidad necesidad) {
		this.necesidad = necesidad;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	
	
}
