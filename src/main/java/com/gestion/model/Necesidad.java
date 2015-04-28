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
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.annotations.GenericGenerator;  
import org.hibernate.annotations.Parameter;  
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Fetch;

import com.gestion.service.EstadoManager;

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
	private List<Estado> estados = new ArrayList<Estado>();
	private EstadoManager estadoManager;
	private Date fechaFinalizacion;
	

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

	
	
	public Date getFechaFinalizacion() {
		return fechaFinalizacion;
	}

	public void setFechaFinalizacion(Date fechaFinalizacion) {
		this.fechaFinalizacion = fechaFinalizacion;
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
    
	@ManyToMany(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    @JoinTable(
            name = "estado_necesidad",
            joinColumns = { @JoinColumn(name = "necesidad_id") },
            inverseJoinColumns = @JoinColumn(name = "estado_id")
    )
	public List<Estado> getEstados() {
		return estados;
	}

	public void setEstados(List<Estado> estados) {
		this.estados = estados;
	}
	
	public EstadoManager getEstadoManager() {
		return estadoManager;
	}

	public void setEstadoManager(EstadoManager estadoManager) {
		this.estadoManager = estadoManager;
	}

	public String getEstadoMatrizado() {
		Iterator<Estado> estadosI = this.getEstados().iterator();
		
		while(estadosI.hasNext()) {
	         Estado estado = estadosI.next();
	         if (estado.getCodigo() != null && estado.getCodigo().equals("MATRIZADO_FIN")){
	        	 return "Finalizado";
	         }
	         if (estado.getCodigo() != null && estado.getCodigo().equals("MATRIZADO_QA")){
	        	 return "Controlado";
	         }
	    }
		return "";
	}
	
	public String getEstadoPintado() {
		Iterator<Estado> estadosI = this.getEstados().iterator();
		
		while(estadosI.hasNext()) {
	         Estado estado = estadosI.next();
	         if (estado.getCodigo() != null && estado.getCodigo().equals("PINTURA_FIN")){
	        	 return "Finalizado";
	         }
	         if (estado.getCodigo() != null && estado.getCodigo().equals("PINTURA_QA")){
	        	 return "Controlado";
	         }
	    }
		return "";
	}
	
	public String getEstadoSoldadura() {
		Iterator<Estado> estadosI = this.getEstados().iterator();
		
		while(estadosI.hasNext()) {
	         Estado estado = estadosI.next();
	         if (estado.getCodigo() != null && estado.getCodigo().equals("SOLDADURA_FIN")){
	        	 return "Finalizado";
	         }
	         if (estado.getCodigo() != null && estado.getCodigo().equals("SOLDADURA_QA")){
	        	 return "Controlado";
	         }
	    }
		return "";
	}

	public void setEstadoPintado(String codigo) {
		//hay q ver que se selecciono
		Estado estado = estadoManager.getEstado(codigo);
		if (estado != null) {
			if (estado.getCodigo().contains("FIN")) {
				this.setFinalizado(true);
				this.setFechaFinalizacion(new Date());
			}

			this.getEstados().add(estado);
		}
	}
	
	public void setEstadoSoldadura(String codigo) {
		//hay q ver que se selecciono
		Estado estado = estadoManager.getEstado(codigo);
		if (estado != null) {
			if (estado.getCodigo().contains("FIN")) {
				this.setFinalizado(true);
				this.setFechaFinalizacion(new Date());
			}
			this.getEstados().add(estado);
		}
		
	}
	
	public void setEstadoMatrizado(String codigo) {
		//hay q ver que se selecciono
		Estado estado = estadoManager.getEstado(codigo);
		if (estado != null) {
			//si el codigo incluye FIN es que es un estado de finalizado
			if (estado.getCodigo().contains("FIN")) {
				this.setFinalizado(true);
				this.setFechaFinalizacion(new Date());
			}
			this.getEstados().add(estado);
		}
		
	}
	
	public String createStyleSoldadura(){
		String estado = getEstadoSoldadura();
		
	    switch (estado){
	    case "Finalizado":
	        return "AZUL";
	        break;
	    case "Controlado":
	        return "VERDE";
	        break;
	    default: 
	        return "DEFAULT";   
	    }
	}
	
	public String createStylePintado(){
		String estado = getEstadoPintado();
		
	    switch (estado){
	    case "Finalizado":
	        return "AZUL";
	        break;
	    case "Controlado":
	        return "VERDE";
	        break;
	    default: 
	        return "DEFAULT";   
	    }
	}
	
	public String createStyleMatrizado(){
		String estado = getEstadoMatrizado();
		
	    switch (estado){
	    case "Finalizado":
	        return "AZUL";
	        break;
	    case "Controlado":
	        return "VERDE";
	        break;
	    default: 
	        return "DEFAULT";   
	    }
	}
    
}
