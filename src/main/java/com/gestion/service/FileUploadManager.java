package com.gestion.service;



import java.util.List;

import com.gestion.dao.FileUploadDAO;
import com.gestion.model.FileUpload;


/**
 * Business Service Interface to handle communication between web and
 * persistence layer.
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 *  Modified by <a href="mailto:dan@getrolling.com">Dan Kibler </a>
 */
public interface FileUploadManager extends GenericManager<FileUpload, Long> {
    /**
     * Convenience method for testing - allows you to mock the DAO and set it on an interface.
     * @param userDao the UserDao implementation to use
     */
    void setFileUploadDao(FileUploadDAO uploadDAO);

    
    FileUpload getFileUpload(String id);

    /**
     * Retrieves a list of all users.
     * @return List
     */
    List<FileUpload> getFilesUploads();


    FileUpload saveFileUpload(com.gestion.webapp.action.FileUpload file);

    /**
     * Removes a user from the database
     *
     * @param user the user to remove
     */
    void removeFileUpload(FileUpload fupload);


	FileUpload saveFileUpload(FileUpload fileUpload);



 }
