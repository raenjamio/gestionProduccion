package com.gestion.webapp.action;

import com.gestion.Constants;
import com.gestion.dao.SearchException;
import com.gestion.model.Estado;
import com.gestion.model.Producto;
import com.gestion.service.EstadoManager;
import com.gestion.util.ConvertUtil;
import com.gestion.webapp.util.RequestUtil;

import org.springframework.mail.MailException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
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

	public void setId(String id) {
        this.id = id;
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


}
