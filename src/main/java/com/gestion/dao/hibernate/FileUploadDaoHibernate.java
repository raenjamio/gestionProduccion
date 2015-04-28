package com.gestion.dao.hibernate;

import com.gestion.dao.FileUploadDAO;
import com.gestion.model.FileUpload;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.primefaces.model.UploadedFile;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate4.SessionFactoryUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import javax.persistence.Table;
import javax.servlet.http.HttpServletRequest;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
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
	

	@Override
	public UploadedFile saveFileUpload(UploadedFile file) {
		// TODO Auto-generated method stub
		try {
			//FileInputStream fileInputStream = new FileInputStream(file.getFileName());
			//HSSFWorkbook workbook = new HSSFWorkbook(fileInputStream);
			HSSFWorkbook workbook2 = new HSSFWorkbook(file.getInputstream());
			
			HSSFSheet worksheet2 = workbook2.getSheet("Importar");
			
			HSSFSheet worksheet = workbook2.getSheet("Importar");
			HSSFRow row1 = worksheet.getRow(0);
			HSSFCell cellA1 = row1.getCell((short) 0);
			String a1Val = cellA1.getStringCellValue();
			HSSFCell cellB1 = row1.getCell((short) 1);
			String b1Val = cellB1.getStringCellValue();
			HSSFCell cellC1 = row1.getCell((short) 2);
			boolean c1Val = cellC1.getBooleanCellValue();
			HSSFCell cellD1 = row1.getCell((short) 3);
			Date d1Val = cellD1.getDateCellValue();

			System.out.println("A1: " + a1Val);
			System.out.println("B1: " + b1Val);
			System.out.println("C1: " + c1Val);
			System.out.println("D1: " + d1Val);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}

}
