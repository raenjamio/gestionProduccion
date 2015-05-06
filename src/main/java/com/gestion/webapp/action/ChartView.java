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

import org.primefaces.event.CloseEvent;
import org.primefaces.event.DashboardReorderEvent;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.ToggleEvent;
import org.primefaces.model.DashboardColumn;
import org.primefaces.model.DashboardModel;
import org.primefaces.model.DefaultDashboardColumn;
import org.primefaces.model.DefaultDashboardModel;
import org.primefaces.model.UploadedFile;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;
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
public class ChartView extends BasePage implements Serializable {
	  private LineChartModel areaModel;
	  
	    @PostConstruct
	    public void init() {
	        createAreaModel();
	    }
	 
	    public LineChartModel getAreaModel() {
	        return areaModel;
	    }
	     
	    private void createAreaModel() {
	        areaModel = new LineChartModel();
	 
	        LineChartSeries boys = new LineChartSeries();
	        boys.setFill(true);
	        boys.setLabel("Boys");
	        boys.set("2004", 120);
	        boys.set("2005", 100);
	        boys.set("2006", 44);
	        boys.set("2007", 150);
	        boys.set("2008", 25);
	 
	        LineChartSeries girls = new LineChartSeries();
	        girls.setFill(true);
	        girls.setLabel("Girls");
	        girls.set("2004", 52);
	        girls.set("2005", 60);
	        girls.set("2006", 110);
	        girls.set("2007", 90);
	        girls.set("2008", 120);
	 
	        areaModel.addSeries(boys);
	        areaModel.addSeries(girls);
	         
	        areaModel.setTitle("Area Chart");
	        areaModel.setLegendPosition("ne");
	        areaModel.setStacked(true);
	        areaModel.setShowPointLabels(true);
	         
	        Axis xAxis = new CategoryAxis("Years");
	        areaModel.getAxes().put(AxisType.X, xAxis);
	        Axis yAxis = areaModel.getAxis(AxisType.Y);
	        yAxis.setLabel("Births");
	        yAxis.setMin(0);
	        yAxis.setMax(300);
	    }
	     
	}