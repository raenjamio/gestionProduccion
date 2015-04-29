package com.gestion.webapp.action;

import com.gestion.Constants;
import com.gestion.dao.SearchException;
import com.gestion.model.Estado;
import com.gestion.model.Necesidad;
import com.gestion.model.Producto;
import com.gestion.service.EstadoManager;
import com.gestion.util.ConvertUtil;
import com.gestion.webapp.util.RequestUtil;
import com.gestion.Constants;

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
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * JSF Page class to handle editing a user with a form.
 *
 * @author mraible
 */
public class EstadoForm extends BasePage implements Serializable {
    private static final long serialVersionUID = -1141119853856863204L;
    private String id;
    private Estado estado = new Estado();
    private EstadoManager estadoManager;
    private String query;

    
    public EstadoForm() {
        setSortColumn("id");
    }
    
    public String add() {
        estado = new Estado();
              
        return "nuevoEstado";
    }

	public void setId(String id) {
        this.id = id;
    }
  

    public String getId() {
		return id;
	}

	public EstadoManager getEstadoManager() {
		return estadoManager;
	}

	public void setEstadoManager(EstadoManager estadoManager) {
		this.estadoManager = estadoManager;
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

    public String edit() {
        HttpServletRequest request = getRequest();
        id = request.getParameter("id");
        // if a user's id is passed in
        if (id != null) {
            log.debug("Editing user, id is: " + id);
            // lookup the user using that id
            estado = estadoManager.getEstado(id);
        } 
        return "nuevaEstado";
    }

 
    public String save() throws IOException {
        // workaround for plain ol' HTML input tags that don't seem to set
        // properties on the managed bean
    	//productoManager.getProducto(selectedProducto);
        // Check for Integers set to 0: happens in Tomcat, not in Jetty

        if (estado.getId() != null && estado.getId() == 0 ) {
        	estado.setId(null);
        }

        try {
        	estado = estadoManager.save(estado);
        } catch (AccessDeniedException ade) {
            // thrown by UserSecurityAdvice configured in aop:advisor userManagerSecurity
            log.warn(ade.getMessage());
            getResponse().sendError(HttpServletResponse.SC_FORBIDDEN);
            return null;
        } 

        if (!"list".equals(getParameter("from"))) {
            // add success messages
            addMessage("Estado.saved");

            // return to main Menu
            return "list";
        } else {
            // add success messages
            if ("".equals(getParameter("EstadoForm:version"))) {
                addMessage("Estado.added", estado.getDescripcion());

                return "list"; // return to list screen
            } else {
                addMessage("Estado.updated", estado.getDescripcion());
                return "nuevaEstado"; // return to current page
            }
        }
    }
    
  
	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public String delete() {
    	estadoManager.remove(estado.getId());
        addMessage("Estado.deleted", estado.getDescripcion());

        return "list";
    }

    /**
     * Convenience method to determine if the user came from the list screen
     * @return String
     */
    public String getFrom() {
        if ((id != null) || (getParameter("editEstado:add") != null) ||
                ("list".equals(getParameter("from")))) {
            return "list";
        }

        return "";
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }
    
    public Estado getEstadoInicial() {
    	return estadoManager.getEstado("1");
    }
    
    public List getEstados() {
        try {
            return sort(estadoManager.search(query));
        } catch (SearchException se) {
            addError(se.getMessage());
            return sort(estadoManager.search(query));
        }
    }

    public String search() {
        return "success";
    }
   
    
    public Map<String,String> getEstadosDisponibles() {
    	//List<String> estados = new ArrayList<String>();
    	HttpServletRequest request = getRequest();
    	Map<String,String> estados = new HashMap<String, String>();
		List<Estado> listEstados = new ArrayList<Estado>();
    	
    	if (request.isUserInRole("ROLE_CALIDAD")) {
    		//estados.add("Controlado");
    		estados.put("", "");
    		estados.put(Constants.CONTROLADO, Constants.PINTURA_CONTROLADO);
    	} else if (request.isUserInRole("ROLE_PINTADO") || request.isUserInRole("ROLE_BALANCINADO") || request.isUserInRole("ROLE_SOLDADO") || request.isUserInRole("ROLE_ADMIN")) {
    		//estados.add("");
    		//estados.add("Finalizado");	
    		estados.put("", "");
    		estados.put(Constants.FINALIZADO, Constants.PINTURA_FINALIZADO);

    		//Estado estadoModel = new Estado();
    		//estadoModel.setCodigo("Algo");
    		//estadoModel.setDescripcion("descrip");
    		//listEstados.add(estadoModel);
    	}
    	return estados; 
    }
    
    public Map<String,String> getEstadosDisponiblesSoldadura () {
    	//List<String> estados = new ArrayList<String>();
    	HttpServletRequest request = getRequest();
    	Map<String,String> estados = new HashMap<String, String>();

    	
    	if (request.isUserInRole("ROLE_CALIDAD")) {
    		estados.put("", "");
    		estados.put(Constants.CONTROLADO, Constants.SOLDADO_CONTROLADO);
    		//estados.add("Controlado");
    	} else if (request.isUserInRole("ROLE_SOLDADURA")) {
    		estados.put("", "");
    		estados.put(Constants.FINALIZADO, Constants.SOLDADO_FINALIZADO);
    		//estados.add("Finalizado");	
    	}
    	return estados;
    }
    
    public Map<String,String> getEstadosDisponiblesPintura () {
    	//List<String> estados = new ArrayList<String>();
    	//Map<String,String> estados = new HashMap<String, String>();
    	HttpServletRequest request = getRequest();
    	Map<String,String> estados = new HashMap<String, String>();
    	
    	if (request.isUserInRole("ROLE_CALIDAD")) {
    		estados.put("", "");
    		estados.put(Constants.CONTROLADO, Constants.PINTURA_CONTROLADO);
    		//estados.add("Controlado");
    		//estados.put("Controlado","PINTURA_QA");
    	} else if (request.isUserInRole("ROLE_PINTURA")) {
    		estados.put("", "");
    		estados.put(Constants.FINALIZADO, Constants.PINTURA_FINALIZADO);
    		//estados.add("Finalizado");
    		//estados.put("Finalizado","PINTURA_FINALIZADO");
    	}
    	return estados;
    }
    
    public Map<String,String> getEstadosDisponiblesBalancinado () {
    	//List<String> estados = new ArrayList<String>();
    	HttpServletRequest request = getRequest();
    	Map<String,String> estados = new HashMap<String, String>();
    	
    	if (request.isUserInRole("ROLE_CALIDAD")) {
    		estados.put("", "");
    		estados.put(Constants.CONTROLADO, Constants.BALANCINADO_CONTROLADO);
    		//estados.add("Controlado");
    	} else if (request.isUserInRole("ROLE_BALANCINADO")) {
    		estados.put("", "");
    		estados.put(Constants.FINALIZADO, Constants.BALANCINADO_FINALIZADO);
    		//estados.add("Finalizado");	
    	}
    	return estados;
    }

	public Estado getEstado(String codigo) {
		// TODO Auto-generated method stub
		estadoManager.getEstadoByCodigo(codigo);
		return null;
	}
}
