package com.gestion.service;

import com.gestion.dao.EstadoDAO;
import com.gestion.model.Estado;


import java.util.List;


/**
 * Business Service Interface to handle communication between web and
 * persistence layer.
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 *  Modified by <a href="mailto:dan@getrolling.com">Dan Kibler </a>
 */
public interface EstadoManager extends GenericManager<Estado, Long> {
    /**
     * Convenience method for testing - allows you to mock the DAO and set it on an interface.
     * @param userDao the UserDao implementation to use
     */
    void setEstadoDao(EstadoDAO EstadoDao);


    /**
     * Retrieves a user by userId.  An exception is thrown if user not found
     *
     * @param userId the identifier for the user
     * @return User
     */
    Estado getEstado(String id);
    
    
    Estado getEstadoByCodigo(String codigo);

    /**
     * Retrieves a list of all users.
     * @return List
     */
    List<Estado> getEstados();


    Estado saveEstado(Estado Estado);

    /**
     * Removes a user from the database
     *
     * @param user the user to remove
     */
    void removeEstado(Estado Estado);

    /**
     * Search a user for search terms.
     * @param searchTerm the search terms.
     * @return a list of matches, or all if no searchTerm.
     */
    List<Estado> search(String searchTerm);

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
