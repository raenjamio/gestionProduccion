package com.gestion.dao;


import com.gestion.model.FileUpload;


public interface FileUploadDAO  extends GenericDao<FileUpload, Long>{
	
	FileUpload saveFileUpload(FileUpload necesidad);
	 
	FileUpload deleteFileUpload(FileUpload necesidad);
	 
	FileUpload updateFileUpload(FileUpload necesidad);

}
