package com.gestion.webapp.action;

import com.gestion.Constants;
import com.gestion.dao.SearchException;
import com.gestion.model.Estado;
import com.gestion.model.Necesidad;
import com.gestion.model.Producto;
import com.gestion.service.EstadoManager;
import com.gestion.service.NecesidadManager;
import com.gestion.util.ConvertUtil;
import com.gestion.webapp.util.RequestUtil;

import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;
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

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * JSF Page class to handle editing a user with a form.
 * 
 * @author mraible
 */
public class DtHistorial extends BasePage implements Serializable {
	private static final long serialVersionUID = -1141119853856863204L;
	private String id;
	private NecesidadManager necesidadManager;
	private String query;
	public String codEstado;
	private Necesidad necesidad;
	private Necesidad selectedNecesidad;

	public String getCodEstado() {
		return codEstado;
	}

	public void setCodEstado(String codEstado) {
		this.codEstado = codEstado;
	}

	public DtHistorial() {
		setSortColumn("producto");
		super.nullsAreHigh = true;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setNecesidadManager(NecesidadManager necesidadManager) {
		this.necesidadManager = necesidadManager;
	}

	public NecesidadManager getNecesidadManager() {
		return necesidadManager;
	}

	public Necesidad getNecesidad() {
		return necesidad;
	}

	public void setNecesidad(Necesidad necesidad) {
		this.necesidad = necesidad;
	}
	

	public Necesidad getSelectedNecesidad() {
		return selectedNecesidad;
	}

	public void setSelectedNecesidad(Necesidad selectedNecesidad) {
		this.selectedNecesidad = selectedNecesidad;
	}

	public String cancel() {
		if (log.isDebugEnabled()) {
			log.debug("Entering 'cancel' method");
		}

		if (!"list".equals(getParameter("from"))) {
			return "home";
		} else {
			return "cancel";
		}
	}

	/**
	 * Convenience method to determine if the user came from the list screen
	 * 
	 * @return String
	 */

	public void setQuery(String query) {
		this.query = query;
	}

	public String getQuery() {
		return query;
	}

	public List getNecesidades() {
		try {
			return sort(necesidadManager.search(query));
		} catch (SearchException se) {
			addError(se.getMessage());
			return sort(necesidadManager.search(query));
		}
	}
	
	public List getNecesidadesNoFinalizadas() {
		try {
			return sort(necesidadManager.getNecesidadesNoFinalizadas());
		} catch (SearchException se) {
			addError(se.getMessage());
			return sort(necesidadManager.search(query));
		}
	}
	
	public List getNecesidadesFinalizadas() {
		try {
			return sort(necesidadManager.getNecesidadesFinalizadas());
		} catch (SearchException se) {
			addError(se.getMessage());
			return sort(necesidadManager.search(query));
		}
	}

	public String search() {
		return "success";
	}
	

    
    public Integer totalFinalizadosByProd(Producto producto) {
    	Integer total= new Integer(0);
    	List<Necesidad> necesidades = necesidadManager.getNecesidadesByProd(producto.getCodigo());
    	
    	for(Necesidad necesidad : necesidades) {
            total += necesidad.getCantidad();
        }
    	return total;
    	
    }
    

}
