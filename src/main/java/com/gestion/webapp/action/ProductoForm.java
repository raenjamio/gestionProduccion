package com.gestion.webapp.action;

import com.gestion.Constants;
import com.gestion.dao.SearchException;
import com.gestion.model.Producto;
import com.gestion.service.ProductoManager;
import com.gestion.service.RoleManager;
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
public class ProductoForm extends BasePage implements Serializable {
    private static final long serialVersionUID = -1141119853856863204L;
    private String id;
    private Producto producto = new Producto();
    private ProductoManager productoManager;
  
    public void setId(String id) {
        this.id = id;
    }
  
    public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}
	
	 public void setProductoManager(ProductoManager prodManager) {
		 this.productoManager = prodManager;
	 }
	 
	public String add() {
        producto = new Producto();
        
        return "nuevoProducto";
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

        // if a user's id is passed in
        if (id != null) {
            log.debug("Editing user, id is: " + id);
            // lookup the user using that id
            producto = productoManager.getProducto(id);
        } else {
            producto = productoManager.getProductoByCodigo(request.getParameter("nombre"));
        } 

        return "nuevoProducto";
    }

 
    public String save() throws IOException {
        // workaround for plain ol' HTML input tags that don't seem to set
        // properties on the managed bean

        // Check for Integers set to 0: happens in Tomcat, not in Jetty
        if (producto.getId() != null && producto.getId() == 0 ) {
            producto.setId(null);
        }

        try {
            producto = productoManager.save(producto);
        } catch (AccessDeniedException ade) {
            // thrown by UserSecurityAdvice configured in aop:advisor userManagerSecurity
            log.warn(ade.getMessage());
            getResponse().sendError(HttpServletResponse.SC_FORBIDDEN);
            return null;
        } 

        if (!"list".equals(getParameter("from"))) {
            // add success messages
            addMessage("producto.saved");

            // return to main Menu
            return "home";
        } else {
            // add success messages
            if ("".equals(getParameter("productoForm:version"))) {
                addMessage("producto.added", producto.getDescripcion());

                return "list"; // return to list screen
            } else {
                addMessage("producto.updated.byAdmin", producto.getDescripcion());
                return "nuevoProducto"; // return to current page
            }
        }
    }

    public String delete() {
    	productoManager.removeProducto(this.getProducto().getId().toString());
        addMessage("producto.deleted", this.getProducto().getDescripcion());

        return "list";
    }

    /**
     * Convenience method to determine if the user came from the list screen
     * @return String
     */
    public String getFrom() {
        if ((id != null) || (getParameter("editProducto:add") != null) ||
                ("list".equals(getParameter("from")))) {
            return "list";
        }

        return "";
    }
    
    private String query;

    public void setQuery(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }
    
    public List getProductos() {
        try {
            return sort(productoManager.search(query));
        } catch (SearchException se) {
            addError(se.getMessage());
            return sort(productoManager.search(query));
        }
    }

    public String search() {
        return "success";
    }


}
