package com.gestion.dao.hibernate;

import com.gestion.dao.NecesidadDAO;
import com.gestion.model.Necesidad;
import com.gestion.model.Producto;

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
@Repository("necesidadDao")
public class NecesidadDaoHibernate extends GenericDaoHibernate<Necesidad, Long> implements NecesidadDAO {
	
	 public NecesidadDaoHibernate() {
		 super(Necesidad.class);
	 }
	@Override
	public Necesidad saveNecesidad (Necesidad necesidad) {
		getSession().saveOrUpdate(necesidad);
        // necessary to throw a DataIntegrityViolation and catch it in UserManager
        getSession().flush();
        return necesidad;
	}
	
	/*public Necesidad deleteNecesidad(Necesidad necesidad) {
		 Object producto = getNecesidadById(necesidad.getId());
	     Session session = getSessionFactory().getCurrentSession();
	     
	     session.delete(producto);
		return null;
	}*/
	@Override
	public Necesidad updateNecesidad(Necesidad necesidad) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Necesidad deleteNecesidad(Necesidad necesidad) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public List<Necesidad> getAllNoFinalizadas() {
		List<Necesidad> necesidades = getSession().createCriteria(Necesidad.class).add(Restrictions.isNull("fechaFinalizado")).list();
	    if (necesidades.isEmpty()) {
	        return null;
	    } else {
	        return necesidades;
	    }
		
	}


   
}
