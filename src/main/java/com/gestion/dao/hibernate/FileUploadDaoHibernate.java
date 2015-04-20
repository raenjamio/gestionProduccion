package com.gestion.dao.hibernate;

import com.gestion.dao.FileUploadDAO;
import com.gestion.model.FileUpload;
import com.gestion.service.impl.Calendar;
import com.gestion.service.impl.Cell;
import com.gestion.service.impl.FacesMessage;
import com.gestion.service.impl.GregorianCalendar;
import com.gestion.service.impl.HSSFSheet;
import com.gestion.service.impl.HSSFWorkbook;
import com.gestion.service.impl.Iterator;
import com.gestion.service.impl.Job;
import com.gestion.service.impl.Row;
import com.gestion.webapp.action.HttpServletRequest;

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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
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
	
	public FileUpload saveFileUpload(com.gestion.webapp.action.FileUpload file){
		 HttpServletRequest request = getRequest();
		  
		FileUpload fileHeader = new FileUpload();
		fileHeader.setFechaImpo(new Date());
		fileHeader.setNombreArchivo(file.getName());
		fileHeader.setUsuario(request.getRemoteUser());
		
		  try {
				 InputStream file;
				 HSSFWorkbook workbook = null;
				 try {
					 file = event.getFile().getInputstream();
					 workbook = new HSSFWorkbook(file);
				 } catch (IOException e) {
					 facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error reading file" + e, null));
				 }
				 
				HSSFSheet sheet = workbook.getSheetAt(1);
				 
				Iterator<Row> rowIterator = sheet.iterator();
				 Calendar calendar = new GregorianCalendar();
				 while (rowIterator.hasNext()) {
					 Row row = rowIterator.next();
					 
					Iterator<Cell> cellIterator = row.cellIterator();
					 Job job = new Job();
					 while (cellIterator.hasNext()) {
						 Cell cell = cellIterator.next();
						 
						switch (cell.getCellType()) {
							 case Cell.CELL_TYPE_NUMERIC:		 
								if (HSSFDateUtil.isCellDateFormatted(cell) || HSSFDateUtil.isCellInternalDateFormatted(cell)) {
									calendar.setTime(cell.getDateCellValue());
								} else {
									System.out.print(cell.getNumericCellValue() + "\t\t");
								}
								break;
							 case Cell.CELL_TYPE_STRING:
							 System.out.print(cell.getStringCellValue() + "\t\t");
							 break;
						}
					 
					}
				 }
				 file.close();
			 } catch (FileNotFoundException e) {
		         e.printStackTrace();
		     } catch (IOException e) {
		            e.printStackTrace();
		     }
		
	}
}
