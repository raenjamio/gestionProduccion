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

import javax.annotation.PostConstruct;
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

import javax.faces.component.UIData;
import org.omnifaces.util.Ajax;

/**
 * JSF Page class to handle editing a user with a form.
 * 
 * @author mraible
 */
public class DtEditPlanificacionForm extends BasePage implements Serializable {
	private static final long serialVersionUID = -1141119853856863204L;
	private String id;
	private NecesidadManager necesidadManager;
	private String query;
	public String codEstado;
	private Necesidad necesidad;
	private EstadoManager estadoManager;
	private String prioridad;
	private List<Necesidad> necesidades;

	public String getPrioridad() {
		return prioridad;
	}

	public void setPrioridad(String prioridad) {
		this.prioridad = prioridad;
	}

	public String getCodEstado() {
		return codEstado;
	}

	public void setCodEstado(String codEstado) {
		this.codEstado = codEstado;
	}

	public DtEditPlanificacionForm() {
		setSortColumn("prioridad");
		super.nullsAreHigh = true;

	}
	

	public void setNecesidades(List<Necesidad> necesidades) {
		this.necesidades = necesidades;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setNecesidadManager(NecesidadManager necesidadManager) {
		this.necesidadManager = necesidadManager;
	}
	
	
	public EstadoManager getEstadoManager() {
		return estadoManager;
	}

	public void setEstadoManager(EstadoManager estadoManager) {
		this.estadoManager = estadoManager;
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
			return sort(getNecesidadesNoFinalizadas());
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
	
	public void onRowEdit(RowEditEvent event) {
		//asignamos la necesidad que editamos
		this.necesidad = (Necesidad) event.getObject();
		
		//buscamos el estado de acuerdo al codigo seleccionado y se lo agregamos a la lista de estados de la necesidad
		Estado estado = estadoManager.getEstadoByCodigo(codEstado);
		
		if (this.prioridad != null && !"".equals(this.prioridad)) {	
			if (isNumeric(prioridad)) {
				this.necesidad.setPrioridad(new Long (this.prioridad));
			} else {
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage("El campo prioridad debe ser numerico",  "No se puede cambiar el estado"));
				
		        return;
			}
		} 
		
		if (estado  != null) {
			//si le voy a poner finalizado, lo pongo sin importar otro estado
			if (codEstado.contains("FINALIZADO")) {
				this.necesidad.getEstados().add(estado);
			} else if (this.necesidad.estaFinalizado(codEstado))
				{
					this.necesidad.getEstados().add(estado);
				}
			
			else {
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage("El producto no se encuentra finalizado",  "No se puede cambiar el estado"));
				
		        return;
			}
			//si el codigo es igual al ultimo proceso lo damos por finalizado
			if (estado.getCodigo().equals(Constants.PINTURA_CONTROLADO)) {
				necesidad.setFinalizado(true);
				necesidad.setFechaFinalizacion(new Date());
			}
		} else {
			if (codEstado != null) {
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage("Necesidad no actualizada",  "No esta parametrizado el estado"));
		        return;
			}
		}
		
		//guardamos el cambio
		necesidadManager.saveNecesidad(necesidad);

		FacesContext context = FacesContext.getCurrentInstance();
		necesidades = getNecesidadesNoFinalizadas();
		context.addMessage(null, new FacesMessage("Producto editado",  "Se cambio de estado y/o prioridad"));
    }
	
    private boolean isNumeric(String prioridad2) {
		 try  
		  {  
		    Integer num = Integer.parseInt(prioridad2);  
		  }  
		  catch(NumberFormatException nfe)  
		  {  
		    return false;  
		  }  
		  return true;  
	}

	public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edit Cancelled", ((Necesidad) event.getObject()).getId().toString());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
     
    public void onCellEdit(CellEditEvent event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();
         
        if(newValue != null && !newValue.equals(oldValue)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cell Changed", "Old: " + oldValue + ", New:" + newValue);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
    
    public Integer totalFinalizadosByProd(Producto producto) {
    	Integer total= new Integer(0);
    	List<Necesidad> necesidades = necesidadManager.getNecesidadesByProd(producto.getCodigo());
    	
    	for(Necesidad necesidad : necesidades) {
            total += necesidad.getCantidad();
        }
    	return total;
    	
    }
    
    @PostConstruct
    public void init(){
    	necesidades = getNecesidadesNoFinalizadas();
    }
    
    public void refresh(){
    	necesidades = getNecesidadesNoFinalizadas();
    }


}
