package com.gestion.service;


import javax.jws.WebService;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import com.gestion.model.Producto;

import java.util.List;

/**
 * Web Service interface so hierarchy of Generic Manager isn't carried through.
 */
@WebService
@Path("/users")
public interface ProductoService {
    /**
     * Retrieves a user by userId.  An exception is thrown if user not found
     *
     * @param userId the identifier for the user
     * @return User
     */
    @GET
    @Path("{id}")
    Producto getProducto(@PathParam("id") String prodId);


    Producto getProductoByCodigo (@PathParam("codigo") String codigo);

    /**
     * Retrieves a list of all users.
     *
     * @return List
     */
    @GET
    List<Producto> getProductos();

    /**
     * Saves a user's information
     *
     * @param user the user's information
     * @return updated user
     * @throws UserExistsException thrown when user already exists
     */
    @POST
    Producto saveUser(Producto producto) throws UserExistsException;

    /**
     * Removes a user from the database by their userId
     *
     * @param userId the user's id
     */
    @DELETE
    void removeProducto(String prodId);
}
