package com.gestion.dao;


import org.primefaces.model.UploadedFile;

import com.gestion.model.FileUpload;


public interface FileUploadDAO  extends GenericDao<FileUpload, Long>{
	
	FileUpload saveFileUpload(FileUpload file);
	 
	FileUpload deleteFileUpload(FileUpload file);
	 
	FileUpload updateFileUpload(FileUpload file);
	
	UploadedFile saveFileUpload(UploadedFile file);
}
