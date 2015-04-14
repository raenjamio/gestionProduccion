package com.gestion.webapp.action;

import com.gestion.Constants;
import com.gestion.dao.SearchException;
import com.gestion.model.Necesidad;
import com.gestion.service.NecesidadManager;
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
import java.util.List;
import java.util.Map;

/**
 * JSF Page class to handle editing a user with a form.
 *
 * @author mraible
 */
public class NecesidadForm extends BasePage implements Serializable {
    private static final long serialVersionUID = -1141119853856863204L;
    private String id;
    private Necesidad necesidad = new Necesidad();
    private NecesidadManager necesidadManager;
    private String query;
    
    public NecesidadForm() {
        setSortColumn("prioridad");
    }
  
    public void setId(String id) {
        this.id = id;
    }
  
    public Necesidad getNecesidad() {
		return necesidad;
	}

	public void setNecesidad(Necesidad necesidad) {
		this.necesidad = necesidad;
	}
	
	 public void setNecesidadManager(NecesidadManager necesidadManager) {
		 this.necesidadManager = necesidadManager;
	 }
	 
	public String add() {
        necesidad = new Necesidad();
        
        return "nuevaNecesidad";
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
            necesidad = necesidadManager.getNecesidad(id);
        } 
        return "nuevaNecesidad";
    }

 
    public String save() throws IOException {
        // workaround for plain ol' HTML input tags that don't seem to set
        // properties on the managed bean

        // Check for Integers set to 0: happens in Tomcat, not in Jetty
        if (necesidad.getId() != null && necesidad.getId() == 0 ) {
        	necesidad.setId(null);
        }

        try {
        	necesidad = necesidadManager.save(necesidad);
        } catch (AccessDeniedException ade) {
            // thrown by UserSecurityAdvice configured in aop:advisor userManagerSecurity
            log.warn(ade.getMessage());
            getResponse().sendError(HttpServletResponse.SC_FORBIDDEN);
            return null;
        } 

        if (!"list".equals(getParameter("from"))) {
            // add success messages
            addMessage("necesidad.saved");

            // return to main Menu
            return "nuevaNecesidad";
        } else {
            // add success messages
            if ("".equals(getParameter("necesidadForm:version"))) {
                addMessage("necesidad.added", necesidad.getPrioridad());

                return "list"; // return to list screen
            } else {
                addMessage("necesidad.updated", necesidad.getPrioridad());
                return "nuevaNecesidad"; // return to current page
            }
        }
    }

    public String delete() {
    	necesidadManager.remove(necesidad.getId());
        addMessage("necesidad.deleted", necesidad.getPrioridad());

        return "list";
    }

    /**
     * Convenience method to determine if the user came from the list screen
     * @return String
     */
    public String getFrom() {
        if ((id != null) || (getParameter("editNecesidad:add") != null) ||
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
    
    public List getProductos() {
        try {
            return sort(necesidadManager.search(query));
        } catch (SearchException se) {
            addError(se.getMessage());
            return sort(necesidadManager.search(query));
        }
    }

    public String search() {
        return "success";
    }


}
