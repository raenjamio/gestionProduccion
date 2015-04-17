package com.gestion.dao.hibernate;

import com.gestion.dao.FileUploadDAO;
import com.gestion.model.FileUpload;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate4.SessionFactoryUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import javax.persistence.Table;

import java.util.List;

/**
 * This class interacts with Hibernate session to save/delete and
 * retrieve User objects.
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 *         Modified by <a href="mailto:dan@getrolling.com">Dan Kibler</a>
 *         Extended to implement Acegi UserDetailsService interface by David Carter david@carter.net
 *         Modified by <a href="mailto:bwnoll@gmail.com">Bryan Noll</a> to work with
 *         the new BaseDaoHibernate implementation that uses generics.
 *         Modified by jgarcia (updated to hibernate 4)
 */
@Repository("fileUploadDao")
public class FileUploadDaoHibernate extends GenericDaoHibernate<FileUpload, Long> implements FileUploadDAO {
	
	 public FileUploadDaoHibernate() {
		 super(FileUpload.class);
	 }
	@Override
	public FileUpload saveFileUpload (FileUpload fileupload) {
		getSession().saveOrUpdate(fileupload);
        // necessary to throw a DataIntegrityViolation and catch it in UserManager
        getSession().flush();
        return fileupload;
	}
	
	/*public Necesidad deleteNecesidad(Necesidad necesidad) {
		 Object producto = getNecesidadById(necesidad.getId());
	     Session session = getSessionFactory().getCurrentSession();
	     
	     session.delete(producto);
		return null;
	}*/
	@Override
	public FileUpload updateFileUpload(FileUpload fileupload) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FileUpload deleteFileUpload(FileUpload fileupload) {
		// TODO Auto-generated method stub
		return null;
	}

   
}
