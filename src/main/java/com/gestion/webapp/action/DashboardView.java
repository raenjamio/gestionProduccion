package com.gestion.webapp.action;

import com.gestion.Constants;
import com.gestion.dao.SearchException;
import com.gestion.model.Necesidad;
import com.gestion.model.Producto;
import com.gestion.model.FileUpload;
import com.gestion.service.FileUploadManager;
import com.gestion.service.NecesidadManager;
import com.gestion.service.ProductoManager;
import com.gestion.service.RoleManager;
import com.gestion.util.ConvertUtil;
import com.gestion.webapp.util.RequestUtil;

import org.primefaces.context.RequestContext;
import org.primefaces.event.CloseEvent;
import org.primefaces.event.DashboardReorderEvent;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.ToggleEvent;
import org.primefaces.model.DashboardColumn;
import org.primefaces.model.DashboardModel;
import org.primefaces.model.DefaultDashboardColumn;
import org.primefaces.model.DefaultDashboardModel;
import org.primefaces.model.UploadedFile;
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
public class DashboardView extends BasePage implements Serializable {
	  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DashboardModel model;
	private List<Necesidad> ulimasNecBalancinadas;
	private NecesidadManager necesidadManager;

	     
	    @PostConstruct
	    public void init() {
	        model = new DefaultDashboardModel();
	        DashboardColumn column1 = new DefaultDashboardColumn();
	        DashboardColumn column2 = new DefaultDashboardColumn();
	         
	        column2.addWidget("sports");
	        column2.addWidget("finance");
	         
	        column1.addWidget("lifestyle");
	        column1.addWidget("weather");
	        
	        this.setUlimasNecBalancinadas(necesidadManager.getTopBalancFinalizadas());
	 
	        model.addColumn(column1);
	        model.addColumn(column2);

	    }
	     
	    public void handleReorder(DashboardReorderEvent event) {
	        FacesMessage message = new FacesMessage();
	        message.setSeverity(FacesMessage.SEVERITY_INFO);
	        message.setSummary("Reordered: " + event.getWidgetId());
	        message.setDetail("Item index: " + event.getItemIndex() + ", Column index: " + event.getColumnIndex() + ", Sender index: " + event.getSenderColumnIndex());
	         
	        addMessage(message);
	    }
	     
	    public void handleClose(CloseEvent event) {
	        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Panel Closed", "Closed panel id:'" + event.getComponent().getId() + "'");
	         
	        addMessage(message);
	    }
	     
	    public void handleToggle(ToggleEvent event) {
	        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, event.getComponent().getId() + " toggled", "Status:" + event.getVisibility().name());
	         
	        addMessage(message);
	    }
	     
	    private void addMessage(FacesMessage message) {
	        FacesContext.getCurrentInstance().addMessage(null, message);
	    }
	     
	    public DashboardModel getModel() {
	        return model;
	    }

		public List<Necesidad> getUlimasNecBalancinadas() {
			return ulimasNecBalancinadas;
		}

		public void setUlimasNecBalancinadas(List<Necesidad> ulimasNecBalancinadas) {
			this.ulimasNecBalancinadas = ulimasNecBalancinadas;
		}

		public NecesidadManager getNecesidadManager() {
			return necesidadManager;
		}

		public void setNecesidadManager(NecesidadManager necesidadManager) {
			this.necesidadManager = necesidadManager;
		}
	    
	    
	    
	
 
}
