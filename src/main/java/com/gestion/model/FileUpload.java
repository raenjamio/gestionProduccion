package com.gestion.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
    private List<Necesidad> necesidades;
    private List<Producto> productos;
    private UploadedFile file;
    
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
	public List<Producto> getProductos() {
		return productos;
	}
	public void setProductos(List<Producto> productos) {
		this.productos = productos;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
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
	public List<Necesidad> getNecesidades() {
		return necesidades;
	}
	public void setNecesidades(List<Necesidad> necesidades) {
		this.necesidades = necesidades;
	}
	public UploadedFile getFile() {
		return file;
	}
	public void setFile(UploadedFile file) {
		this.file = file;
	}
    
    
    

}
