package com.gestion.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.search.annotations.Indexed;

import java.io.Serializable;

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
	private boolean finalizado;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean equals(Object o) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	

}
