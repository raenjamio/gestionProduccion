package com.gestion.dao;


import com.gestion.model.FileUpload;


public interface FileUploadDAO  extends GenericDao<FileUpload, Long>{
	
	FileUpload saveFileUpload(FileUpload file);
	 
	FileUpload deleteFileUpload(FileUpload file);
	 
	FileUpload updateFileUpload(FileUpload file);
	
	FileUpload saveFileUpload(com.gestion.webapp.action.FileUpload file);
}
