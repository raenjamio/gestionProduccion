package com.gestion.model;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

import javassist.bytecode.Descriptor.Iterator;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;
import java.util.List;

/**
 * This class is used to represent available roles in the database.
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 *         Version by Dan Kibler dan@getrolling.com
 *         Extended to implement Acegi GrantedAuthority interface
 *         by David Carter david@carter.net
 */
@Entity
@Table(name = "productos")
@Indexed
@XmlRootElement
public class Producto extends BaseObject implements Serializable,  Comparable<Producto> {
    private static final long serialVersionUID = 3690197650654049848L;
    private Long id;
    private String codigo;
	private String descripcion;
    private Long prioridad;
	//private Necesidad necesidad;
	private FileUpload fileUploadHeader;
	private List<Necesidad> necesidades;
	
    /**
     * Default constructor - creates a new instance with no values set.
     */
    public Producto() {
    }

    /**
     * Create a new instance and set the name.
     *
     * @param name name of the role.
     */
    public Producto(final String name) {
        this.codigo = name;
    }
    
    public Long getPrioridad() {
		return prioridad;
	}

	public void setPrioridad(Long prioridad) {
		this.prioridad = prioridad;
	}

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
    public Long getId() {
        return id;
    }
	
	//@OneToOne (mappedBy="producto", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	//@JoinColumn //(name="producto_id", unique= true, nullable=true, insertable=true, updatable=true)
	/*@OneToOne(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
	//@JoinColumn(name="id")
    public Necesidad getNecesidad() {
		return necesidad;
	}

	public void setNecesidad(Necesidad necesidad) {
		this.necesidad = necesidad;
	}*/
	
	@OneToMany (fetch=FetchType.EAGER, cascade = CascadeType.ALL, mappedBy="producto")
    public List<Necesidad> getNecesidades() {
		return necesidades;
	}

	public void setNecesidades(List<Necesidad> necesidades) {
		this.necesidades = necesidades;
	}

	@Column (nullable = false, length = 20, unique = true)
    @Field
    public String getCodigo() {
        return this.codigo;
    }

    @Column(length = 64)
    public String getDescripcion() {
        return this.descripcion;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setDescripcion(String description) {
        this.descripcion = description;
    }
    
    
	@ManyToOne//(cascade=CascadeType.ALL)
	@JoinColumn(nullable = true)
    public FileUpload getFileUploadHeader() {
		return fileUploadHeader;
	}

	public void setFileUploadHeader(FileUpload fileUploadHeader) {
		this.fileUploadHeader = fileUploadHeader;
	}

	/**
     * {@inheritDoc}
     */
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Producto)) {
            return false;
        }

        final Producto producto = (Producto) o;

        return !(codigo != null ? !codigo.equals(producto.codigo) : producto.codigo != null);

    }

    /**
     * {@inheritDoc}
     */
    public int hashCode() {
        return (codigo != null ? codigo.hashCode() : 0);
    }

    /**
     * {@inheritDoc}
     */
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE)
                .append(this.codigo)
                .toString();
    }

	@Override
	public int compareTo(Producto arg0) {
		// TODO Auto-generated method stub

		String codigo = ((Producto) arg0).getCodigo(); 
 
		//ascending order
		return this.getCodigo().compareTo(codigo);
	}


}
