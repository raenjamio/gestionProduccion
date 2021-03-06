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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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

import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Fetch;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.gestion.Constants;

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
	private Date fechaFinalizacion;
	private Date fechaFinalProduccion;
	private Date fechaControlProduccion;
	private Date fechaFinalPintado;
	private Date fechaControlPintado;
	private List<Actividad> actividades = new ArrayList<Actividad>();
	private Integer cantidadPintada;
	private String estadoProduccion;
	private String estadoSoldadura;
	private String estadoPintado;
	

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
	
	@Transactional(propagation=Propagation.REQUIRED)
	@OneToMany (fetch=FetchType.LAZY, cascade = CascadeType.ALL,mappedBy="necesidad")
	public List<Actividad> getActividades() {
		return actividades;
	}

	public void setActividades(List<Actividad> actividades) {
		this.actividades = actividades;
	}

	//@OneToOne(mappedBy="necesidad", cascade=CascadeType.ALL)
	@ManyToOne//(cascade=CascadeType.MERGE)
	@JoinColumn(nullable = false)
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


	public Date getFechaFinalProduccion() {
		return fechaFinalProduccion;
	}

	public void setFechaFinalProduccion(Date fechaFinalProduccion) {
		this.fechaFinalProduccion = fechaFinalProduccion;
	}

	public Date getFechaControlProduccion() {
		return fechaControlProduccion;
	}

	public void setFechaControlProduccion(Date fechaControlProduccion) {
		this.fechaControlProduccion = fechaControlProduccion;
	}

	public Date getFechaFinalPintado() {
		return fechaFinalPintado;
	}

	public void setFechaFinalPintado(Date fechaFinalPintado) {
		this.fechaFinalPintado = fechaFinalPintado;
	}

	public Date getFechaControlPintado() {
		return fechaControlPintado;
	}

	public void setFechaControlPintado(Date fechaControlPintado) {
		this.fechaControlPintado = fechaControlPintado;
	}

	public Integer getCantidadPintada() {
		return cantidadPintada;
	}

	public void setCantidadPintada(Integer cantidadPintada) {
		this.cantidadPintada = cantidadPintada;
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
	
	/*public EstadoManager getEstadoManager() {
		return estadoManager;
	}

	public void setEstadoManager(EstadoManager estadoManager) {
		this.estadoManager = estadoManager;
	}*/

	public String getEstadoProduccion() {
		return estadoProduccion;
	}

	public void setEstadoProduccion(String estadoProduccion) {
		this.estadoProduccion = estadoProduccion;
	}

	public String getEstadoSoldadura() {
		return estadoSoldadura;
	}

	public void setEstadoSoldadura(String estadoSoldadura) {
		this.estadoSoldadura = estadoSoldadura;
	}

	public String getEstadoPintado() {
		return estadoPintado;
	}

	public void setEstadoPintado(String estadoPintado) {
		this.estadoPintado = estadoPintado;
	}

	/*
        public String getEstadoProduccion() {
            Iterator<Estado> estadosI = this.getEstados().iterator();
            String codEstado = "";

            while(estadosI.hasNext()) {
                 Estado estado = estadosI.next();
                 if (estado.getCodigo() != null && estado.getCodigo().equals(Constants.PRODUCCION_CONTROLADO)){
                     return Constants.CONTROLADO;
                 }
                 if (estado.getCodigo() != null && estado.getCodigo().equals(Constants.PRODUCCION_FINALIZADO)){
                     codEstado = Constants.FINALIZADO;
                 }

            }
            return codEstado;
        }

        public String getEstadoPintado() {
            Iterator<Estado> estadosI = this.getEstados().iterator();
            String codEstado = "";

            while(estadosI.hasNext()) {
                 Estado estado = estadosI.next();
                 if (estado.getCodigo() != null && estado.getCodigo().equals(Constants.PINTURA_CONTROLADO)){
                     return Constants.CONTROLADO;
                 }
                 if (estado.getCodigo() != null && estado.getCodigo().equals(Constants.PINTURA_FINALIZADO)){
                     codEstado = Constants.FINALIZADO;
                 }

            }
            return codEstado;
        }

        public String getEstadoSoldadura() {
            Iterator<Estado> estadosI = this.getEstados().iterator();
            String codEstado = "";
            while(estadosI.hasNext()) {
                 Estado estado = estadosI.next();
                 if (estado.getCodigo() != null && estado.getCodigo().equals(Constants.SOLDADO_CONTROLADO)){
                     return Constants.CONTROLADO;
                 }
                 if (estado.getCodigo() != null && estado.getCodigo().equals(Constants.SOLDADO_FINALIZADO)){
                     codEstado = Constants.FINALIZADO;
                 }

            }
            return codEstado;
        }

        public void setEstadoPintado(String codigo) {
            //hay q ver que se selecciono
            //private EstadoManager estadoManager;
            Estado estado = null;// = estadoManager.getEstado(codigo);
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
            Estado estado = null;//= estadoManager.getEstado(codigo);
            if (estado != null) {
                if (estado.getCodigo().contains("FIN")) {
                    this.setFinalizado(true);
                    this.setFechaFinalizacion(new Date());
                }
                this.getEstados().add(estado);
            }

        }

        public void setEstadoProduccion (String codigo) {
            //hay q ver que se selecciono
            Estado estado = null;//= estadoManager.getEstado(codigo);
            if (estado != null) {
                //si el codigo incluye FIN es que es un estado de finalizado
                if (estado.getCodigo().contains("FIN")) {
                    this.setFinalizado(true);
                    this.setFechaFinalizacion(new Date());
                }
                this.getEstados().add(estado);
            }

        }
        */
	public String createStyleSoldadura(){
		String estado = getEstadoSoldadura();


		if (estado == null) {
			return "DEFAULT";
		}
		
	    switch (estado){
	    case Constants.FINALIZADO:
	        return "outputEstadoAzul";
		case Constants.CONTROLADO:
	        return "outputEstadoVerde";
		default: 
	        return "DEFAULT";   
	    }
	}

	public String createStylePintado(){
		String estado = getEstadoPintado();


		if (estado == null) {
			return "DEFAULT";
		}
		
	    switch (estado){
	    case Constants.FINALIZADO:
	        return "outputEstadoAzul";
		case Constants.CONTROLADO:
	        return "outputEstadoVerde";
		default: 
	        return "DEFAULT";   
	    }
	}
	
	public String createStyleProduccion(){
		String estado = getEstadoProduccion();

		if (estado == null) {
			return "DEFAULT";
		}

	    switch (estado){
	    case Constants.FINALIZADO:
	        return "outputEstadoAzul";
		case Constants.CONTROLADO:
	        return "outputEstadoVerde";
		default:
			return "DEFAULT";
	        //return "DEFAULT";   
	    }
	}
	

	public boolean estaFinalizado(String codEstado) {
		// TODO Auto-generated method stub
		if (codEstado != null && !codEstado.contains("FINALIZADO")) {
			String estado = codEstado.substring(0, codEstado.indexOf("_")+1);
			//recorro los estados y me fijo si para ese estado (soldado, balancinado o pintado) esta finalizado
			Iterator<Estado> estadosI = this.getEstados().iterator();
			while(estadosI.hasNext()) {
		         Estado estado_ = estadosI.next();
		         if (estado_.getCodigo() != null && estado_.getCodigo().equals(estado + "FINALIZADO")){
		        	 return true;
		         }   
		    }

		}
		return false;
	}
	
}
