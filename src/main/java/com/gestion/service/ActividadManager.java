package com.gestion.service;

import com.gestion.dao.ActividadDAO;
import com.gestion.model.Actividad;


import java.util.List;


/**
 * Business Service Interface to handle communication between web and
 * persistence layer.
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 *  Modified by <a href="mailto:dan@getrolling.com">Dan Kibler </a>
 */
public interface ActividadManager extends GenericManager<Estado, Long> {
    /**
     * Convenience method for testing - allows you to mock the DAO and set it on an interface.
     * @param userDao the UserDao implementation to use
     */
    void setActividadoDao(ActividadDAO actividadDao);


    /**
     * Retrieves a user by userId.  An exception is thrown if user not found
     *
     * @param userId the identifier for the user
     * @return User
     */
    Actividad getEstado(String id);
    

    /**
     * Retrieves a list of all users.
     * @return List
     */
    List<Actividad> getActividades();


    Actividad saveActividad(Actividad actividad);

    /**
     * Removes a user from the database
     *
     * @param user the user to remove
     */
    void removeActividad(Actividad actividad);

    /**
     * Search a user for search terms.
     * @param searchTerm the search terms.
     * @return a list of matches, or all if no searchTerm.
     */
    List<Actividad> search(String searchTerm);

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
