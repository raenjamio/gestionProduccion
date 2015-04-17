package com.gestion.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.hibernate.search.annotations.Indexed;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "productos_soldados")
@Indexed
@XmlRootElement
public class Soldadura extends Estado implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private Date fechaCreacion;
	private Date fechaFinalizacion;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public boolean equals(Object o) {
		  if (this == o) {
	            return true;
	        }
	        if (!(o instanceof Soldadura)) {
	            return false;
	        }

	        final Soldadura soldadura = (Soldadura) o;

	        return !(id != null ? !id.equals(soldadura.id) : soldadura.id != null);
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
    
 
    public Estado cambiarEstado(){
    	
    	Pintura estadoNuevo = new Pintura;
    	
    	return estadoNuevo;
    }


}
