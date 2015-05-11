package com.gestion.service;

import com.gestion.dao.NecesidadDAO;
import com.gestion.model.Chart;
import com.gestion.model.Necesidad;

import java.util.List;


/**
 * Business Service Interface to handle communication between web and
 * persistence layer.
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 *  Modified by <a href="mailto:dan@getrolling.com">Dan Kibler </a>
 */
public interface ChartManager {
    /**
     * Convenience method for testing - allows you to mock the DAO and set it on an interface.
     * @param userDao the UserDao implementation to use
     */
    void setNecesidadDao(NecesidadDAO necesidadDao);

    /**
     * Retrieves a list of all users.
     * @return List
     */
    List<Necesidad> getNecesidades();


	List<Necesidad> getNecesidadesFinalizadas();
	
	List<Chart> getNecesidadesSoldadasFinalizadas();
	List<Chart> getNecesidadesBalancinadasFinalizadas();
	List<Chart> getNecesidadesPintadasFinalizadas();


    /**
     * Builds a recovery password url by replacing placeholders with username and generated recovery token.
     * 
     * UrlTemplate should include two placeholders '{username}' for username and '{token}' for the recovery token.
     * 
     * @param user
     * @param urlTemplateurl
     *            template including two placeholders '{username}' and '{token}'
     * @return
     */

 }
