package com.gestion.service;

import com.gestion.dao.ProductoDAO;
import com.gestion.model.Producto;

import org.primefaces.model.UploadedFile;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;


/**
 * Business Service Interface to handle communication between web and
 * persistence layer.
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 *  Modified by <a href="mailto:dan@getrolling.com">Dan Kibler </a>
 */
public interface ProductoManager extends GenericManager<Producto, Long> {
    /**
     * Convenience method for testing - allows you to mock the DAO and set it on an interface.
     * @param userDao the UserDao implementation to use
     */
    void setProductoDao(ProductoDAO prodDao);


    /**
     * Retrieves a user by userId.  An exception is thrown if user not found
     *
     * @param userId the identifier for the user
     * @return User
     */
    Producto getProducto(String prodId);

    /**
     * Finds a user by their username.
     * @param username the user's username used to login
     * @return User a populated user object
     * @throws org.springframework.security.core.userdetails.UsernameNotFoundException
     *         exception thrown when user not found
     */
    Producto getProductoByCodigo(String codigo) throws UsernameNotFoundException;

    /**
     * Retrieves a list of all users.
     * @return List
     */
    List<Producto> getProductos();

    /**
     * Saves a user's informaticon.
     *
     * @param user the user's information
     * @throws UserExistsException thrown when user already exists
     * @return user the updated user object
     */
    Producto saveProducto(Producto prod) throws UserExistsException;

    /**
     * Removes a user from the database
     *
     * @param user the user to remove
     */
    void removeProducto(Producto prod);

    /**
     * Removes a user from the database by their userId
     *
     * @param userId the user's id
     */
    void removeProducto(String prodId);

    /**
     * Search a user for search terms.
     * @param searchTerm the search terms.
     * @return a list of matches, or all if no searchTerm.
     */
    List<Producto> search(String searchTerm);
    
    List<Producto> getProductosByCodigo (String prodId);
    
    List<Producto> getProductosByCodigoSinNecesidad (String prodId);
    
    List<Producto> getProductosExcel(UploadedFile file);
    
    Long getCantInsertados() ;
    Long getCantActualizados();
    Long getCantBorrados();

 }
