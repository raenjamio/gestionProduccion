package com.gestion.util;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Set;

import javax.servlet.annotation.WebListener;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.dbcp.AbandonedConfig;
import org.apache.commons.dbcp.AbandonedObjectPool;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.gestion.webapp.listener.StartupListener;

@WebListener // register it as you wish
public class ContainerContextClosedHandler implements ServletContextListener {
	private static final Log log = LogFactory.getLog(ContainerContextClosedHandler.class);
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		
	}

	@SuppressWarnings("deprecation")
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		 	Enumeration<Driver> drivers = DriverManager.getDrivers();     

	        Driver driver = null;

	        // clear drivers
	        while(drivers.hasMoreElements()) {
	            try {
	                driver = drivers.nextElement();
	                DriverManager.deregisterDriver(driver);
	                log.debug("Se desregistro driver " + driver.getClass().getName());
	            } catch (SQLException ex) {
	            	 log.debug("Error al desregistrar driver" + driver.getClass().getName());
	                // deregistration failed, might want to do something, log at the very least
	            }
	        }

	        Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
			Thread[] threadArray = threadSet.toArray(new Thread[threadSet.size()]);
			for(Thread t:threadArray) {
			    if(t.getName().contains("Abandoned connection cleanup thread")) {
			        synchronized(t) {
			            t.stop(); //don't complain, it works
			        }
			    }
			}
			log.debug("Se finalizo con el containerContextClosedHandler");
	}

}
