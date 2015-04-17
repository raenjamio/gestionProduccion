package com.gestion.model;

import java.io.Serializable;

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
    
    
    

}
