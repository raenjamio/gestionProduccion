package com.gestion.service.impl;

import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.gestion.dao.FileUploadDAO;
import com.gestion.model.FileUpload;
import com.gestion.model.Necesidad;
import com.gestion.service.FileUploadManager;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;


/**
 * Implementation of UserManager interface.
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
@Service("fileUploadManager")
public class FileUploadManagerImpl extends GenericManagerImpl<FileUpload, Long> implements FileUploadManager {
	
    private FileUploadDAO fileUploadDao;
    

    @Autowired
    public FileUploadManagerImpl(FileUploadDAO dao) {
        super(dao);
        this.fileUploadDao = dao;
    }

    @Autowired
    public void setFileUploadDao(final FileUploadDAO dao) {
    	this.dao = dao;
        this.fileUploadDao = dao;
    }

	@Override
	public FileUpload getFileUpload(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<FileUpload> getFilesUploads() {
		// TODO Auto-generated method stub
		dao.getAll();
		return null;
	}

	//@Override
	//public FileUpload saveFileUpload (FileUpload fileUpload) {
	//	return dao.save(fileUpload);
	//}

	@Override
	public void removeFileUpload (FileUpload fileUpload) {
		// TODO Auto-generated method stub
		dao.remove(fileUpload);
		
	}



	@Override
	public UploadedFile saveFileUpload(UploadedFile fileUpload) {
		// TODO Auto-generated method stub
		return fileUploadDao.saveFileUpload(fileUpload);
	}

	@Override
	public FileUpload saveFileUpload(FileUpload file) {
		// TODO Auto-generated method stub
		return dao.save(file);
	}

	@Override
	public List<FileUpload> search(String searchTerm) {
		// TODO Auto-generated method stub
		return super.search(searchTerm, FileUpload.class);
	}



}
