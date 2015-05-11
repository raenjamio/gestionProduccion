package com.gestion.dao.hibernate;

import com.gestion.dao.ActividadDAO;
import com.gestion.model.Actividad;

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
@Repository("actividadDao")
public class ActividadDaoHibernate extends GenericDaoHibernate<Actividad, Long> implements ActividadDAO {
	
	 public ActividadDaoHibernate() {
		 super(Actividad.class);
	 }
	@Override
	public Actividad saveActividad (Actividad actividad) {
		getSession().saveOrUpdate(actividad);
        // necessary to throw a DataIntegrityViolation and catch it in UserManager
        getSession().flush();
        return actividad;
	}
	
	/*public Actividad deleteActividad(Actividad Actividad) {
		 Object producto = getActividadById(Actividad.getId());
	     Session session = getSessionFactory().getCurrentSession();
	     
	     session.delete(producto);
		return null;
	}*/
	@Override
	public Actividad updateActividad(Actividad actividad) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Actividad deleteActividad(Actividad actividad) {
		// TODO Auto-generated method stub
	     Session session = getSessionFactory().getCurrentSession();
	     
	     session.delete(actividad);
		return actividad;
	}
	


   
}
