package com.gestion.webapp.action;

import com.gestion.Constants;
import com.gestion.dao.SearchException;
import com.gestion.model.Necesidad;
import com.gestion.model.Producto;
import com.gestion.model.FileUpload;
import com.gestion.service.FileUploadManager;
import com.gestion.service.ProductoManager;
import com.gestion.service.RoleManager;
import com.gestion.util.ConvertUtil;
import com.gestion.webapp.util.RequestUtil;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.springframework.mail.MailException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * JSF Page class to handle editing a user with a form.
 *
 * @author mraiblefileUploadHead
 */
public class FileNecesidadesForm extends BasePage implements Serializable {
    private static final long serialVersionUID = -1141119853856863204L;
    private String id;
    private FileUploadManager fileNecesidadesManager;
    private ProductoManager productoManager;
    private UploadedFile file;
    private String nombreArchivo;
    private FileUpload fileUploadHead = new FileUpload();
    private Long cantInsertados;
    private Long cantActualizados;
    private Long cantBorrados;
    
    public FileNecesidadesForm() {
    	setSortColumn("fechaImpo");
    }
    
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

	public UploadedFile getFile() {
		return file;
	}
	public void setFile(UploadedFile file) {
		this.file = file;
	}
	public String getNombreArchivo() {
		return nombreArchivo;
	}
	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}
	
	
	
	public ProductoManager getProductoManager() {
		return productoManager;
	}

	public void setProductoManager(ProductoManager productoManager) {
		this.productoManager = productoManager;
	}

	public FileUpload getFileUploadHead() {
		return fileUploadHead;
	}

	public void setFileUploadHead(FileUpload fileUploadHead) {
		this.fileUploadHead = fileUploadHead;
	}

	public String importar(){
		fileUploadHead.setFechaImpo(new Date());
		fileUploadHead.setNombreArchivo(file.getFileName());
		fileUploadHead.setUsuario("");
		
		List<Producto> productos = productoManager.getProductosExcel(file);
		Iterator<Producto> productoIterator = productos.iterator();
		
		while (productoIterator.hasNext()) {
			productoIterator.next().setFileUploadHeader(fileUploadHead);
			
		}
		
		fileUploadHead.setProductos(productos);
		fileUploadHead.setCantInsertados(productoManager.getCantInsertados());
		fileUploadHead.setCantActualizados(productoManager.getCantActualizados());
		
		fileNecesidadesManager.saveFileUpload(fileUploadHead);
		
		FacesContext context = FacesContext.getCurrentInstance();
		
		if ((fileUploadHead.getCantActualizados()> 0) || (fileUploadHead.getCantInsertados()>0)) {
			context.addMessage(null, new FacesMessage("Importacion realizada correctamente",  "Registros insertados: " + fileUploadHead.getCantInsertados()) );
			context.addMessage(null, new FacesMessage("Importacion realizada correctamente",  "Registros actualizados: " + fileUploadHead.getCantActualizados()) );
			return "success";
		}
		else {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Importacion No realizada",  "Importacion No realizada ") );
		return "error";
		}
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
	
    public List getImportaciones() {
        try {
            return sort(fileNecesidadesManager.search(""));
        } catch (SearchException se) {
            addError(se.getMessage());
            return sort(fileNecesidadesManager.search(""));
        }
    }
	
 
}
