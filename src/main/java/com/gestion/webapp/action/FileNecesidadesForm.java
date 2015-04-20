package com.gestion.webapp.action;

import com.gestion.Constants;
import com.gestion.dao.SearchException;
import com.gestion.model.Necesidad;
import com.gestion.model.Producto;
import com.gestion.service.FileUploadManager;
import com.gestion.service.ProductoManager;
import com.gestion.service.RoleManager;
import com.gestion.util.ConvertUtil;
import com.gestion.webapp.util.RequestUtil;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.springframework.mail.MailException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * JSF Page class to handle editing a user with a form.
 *
 * @author mraible
 */
public class FileNecesidadesForm extends BasePage implements Serializable {
    private static final long serialVersionUID = -1141119853856863204L;
    private String id;
    private FileUploadManager fileNecesidadesManager;
    private FileUpload file;
    private String nombreArchivo;
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public FileUploadManager getFileNecesidadesManager() {
		return fileNecesidadesManager;
	}
	public void setFileNecesidadesManager(FileUploadManager fileNecesidadesManager) {
		this.fileNecesidadesManager = fileNecesidadesManager;
	}
	public FileUpload getFile() {
		return file;
	}
	public void setFile(FileUpload file) {
		this.file = file;
	}
	
	public String getNombreArchivo() {
		return nombreArchivo;
	}
	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}
	public String importar(){
		fileNecesidadesManager.saveFileUpload(file);
		return "";
	}
	
	public void handleFileUpload(FileUploadEvent event) {
		 if (event.getFile().equals(null)) {
			 facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "File is null", null));
		 } else {	 
			 //fileNecesidadesManager.saveFileUpload(file);
			 //return "success";
		 }
	}
 
}
