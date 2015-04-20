package com.gestion.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.gestion.dao.FileUploadDAO;
import com.gestion.model.FileUpload;
import com.gestion.service.FileUploadManager;
import com.gestion.webapp.action.Calendar;
import com.gestion.webapp.action.Cell;
import com.gestion.webapp.action.FacesMessage;
import com.gestion.webapp.action.GregorianCalendar;
import com.gestion.webapp.action.HSSFSheet;
import com.gestion.webapp.action.HSSFWorkbook;
import com.gestion.webapp.action.Iterator;
import com.gestion.webapp.action.Job;
import com.gestion.webapp.action.Row;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;


/**
 * Implementation of UserManager interface.
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
@Service("FileUploadManager")
public class FileUploadManagerImpl extends GenericManagerImpl<FileUpload, Long> implements FileUploadManager {
	
    private FileUploadDAO FileUploadDao;
    

    @Autowired
    public FileUploadManagerImpl(FileUploadDAO dao) {
        super(dao);
        this.FileUploadDao = dao;
    }

    @Autowired
    public void setFileUploadDao(final FileUploadDAO dao) {
    	this.dao = dao;
        this.FileUploadDao = dao;
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

	@Override
	public FileUpload saveFileUpload (FileUpload fileUpload) {
		return dao.save(fileUpload);
	}

	@Override
	public void removeFileUpload (FileUpload fileUpload) {
		// TODO Auto-generated method stub
		dao.remove(fileUpload);
		
	}

	@Override
	public FileUpload saveFileUpload(com.gestion.webapp.action.FileUpload file) {
		// TODO Auto-generated method stub
		return dao.save(file);
		
		return null;
	}



}
