package com.gestion.service;

import com.gestion.dao.NecesidadDAO;
import com.gestion.model.Necesidad;


import java.util.List;


/**
 * Business Service Interface to handle communication between web and
 * persistence layer.
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 *  Modified by <a href="mailto:dan@getrolling.com">Dan Kibler </a>
 */
public interface NecesidadManager extends GenericManager<Necesidad, Long> {
    /**
     * Convenience method for testing - allows you to mock the DAO and set it on an interface.
     * @param userDao the UserDao implementation to use
     */
    void setNecesidadDao(NecesidadDAO necesidadDao);


    /**
     * Retrieves a user by userId.  An exception is thrown if user not found
     *
     * @param userId the identifier for the user
     * @return User
     */
    Necesidad getNecesidad(String id);

    /**
     * Retrieves a list of all users.
     * @return List
     */
    List<Necesidad> getNecesidades();


    Necesidad saveNecesidad(Necesidad necesidad);

    /**
     * Removes a user from the database
     *
     * @param user the user to remove
     */
    void removeNecesidad(Necesidad necesidad);

    /**
     * Search a user for search terms.
     * @param searchTerm the search terms.
     * @return a list of matches, or all if no searchTerm.
     */
    List<Necesidad> search(String searchTerm);

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
