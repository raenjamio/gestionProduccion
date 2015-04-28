package com.gestion.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.search.annotations.Indexed;
import org.primefaces.model.UploadedFile;

@Entity
@Table(name = "head_file_upload")
@Indexed
public class FileUpload extends BaseObject implements Serializable {
    private static final long serialVersionUID = 3690197650654049848L;
    private Long id;
    private String nombreArchivo;
    private Date fechaImpo;
    private String usuario;
    private List<Producto> productos;
    private Long cantInsertados;
    private Long cantActualizados;
    private Long cantBorrados;
   
    public FileUpload() {
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNombreArchivo() {
		return nombreArchivo;
	}
	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}
	public Date getFechaImpo() {
		return fechaImpo;
	}
	public void setFechaImpo(Date fechaImpo) {
		this.fechaImpo = fechaImpo;
	}
	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.nombreArchivo;
	}
	@Override
	public boolean equals(Object o) {
		 if (this == o) {
	            return true;
	        }
	        if (!(o instanceof FileUpload)) {
	            return false;
	        }

	        final FileUpload file = (FileUpload) o;

	        return !(id != null ? !id.equals(file.id) : file.id != null);
	}
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		 return (id != null ? id.hashCode() : 0);
	}
	
	@OneToMany (fetch=FetchType.LAZY, cascade = CascadeType.ALL,mappedBy="fileUploadHeader")
	public List<Producto> getProductos() {
		return productos;
	}
	public void setProductos(List<Producto> productos) {
		this.productos = productos;
	}

	public Long getCantInsertados() {
		return cantInsertados;
	}

	public void setCantInsertados(Long cantInsertados) {
		this.cantInsertados = cantInsertados;
	}

	public Long getCantActualizados() {
		return cantActualizados;
	}

	public void setCantActualizados(Long cantActualizados) {
		this.cantActualizados = cantActualizados;
	}

	public Long getCantBorrados() {
		return cantBorrados;
	}

	public void setCantBorrados(Long cantBorrados) {
		this.cantBorrados = cantBorrados;
	}
	
	
    

}
