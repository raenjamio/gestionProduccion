package com.gestion.service;


import javax.jws.WebService;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import com.gestion.model.Necesidad;

import java.util.List;

/**
 * Web Service interface so hierarchy of Generic Manager isn't carried through.
 */
@WebService
@Path("/users")
public interface FileUploadService {
    /**
     * Retrieves a user by userId.  An exception is thrown if user not found
     *
     * @param userId the identifier for the user
     * @return User
     */
    @GET
    @Path("{id}")
    Necesidad getFileUpload(@PathParam("id") String id);


    /**
     * Retrieves a list of all users.
     *
     * @return List
     */
    @GET
    List<Necesidad> getFilesUploads();

    /**
     * Saves a user's information
     *
     * @param user the user's information
     * @return updated user
     * @throws UserExistsException thrown when user already exists
     */
    @POST
    Necesidad saveFileUpload(FileUpload fu);

    /**
     * Removes a user from the database by their userId
     *
     * @param userId the user's id
     */
    @DELETE
    void removeFileUpload(String id);
}
