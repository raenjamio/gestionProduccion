package com.gestion.dao.hibernate;

import com.gestion.dao.NecesidadDAO;
import com.gestion.model.Necesidad;
import com.gestion.model.Producto;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
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
		List<Necesidad> necesidades = getSession().createCriteria(Necesidad.class).add(Restrictions.isNull("fechaFinalizacion")).list();
	    if (necesidades.isEmpty()) {
	        return necesidades;
	    } else {
	        return necesidades;
	    }
		
	}
	
	public List<Necesidad> getAllFinalizadas() {
		List<Necesidad> necesidades = getSession().createCriteria(Necesidad.class).add(Restrictions.isNotNull("fechaFinalizacion")).list();
	    if (necesidades.isEmpty()) {
	        return necesidades;
	    } else {
	        return necesidades;
	    }
		
	}

	public 	List<Necesidad> getNecesidadesByProd(String codigo) {
		List<Necesidad> necesidades = null;

		if (codigo != null) {
			//productos = getSession().createSQLQuery("select prod.*	from productos prod where not exists (select * from necesidades nec where nec.producto_id = prod.id AND nec.finalizado = true)").list();
			SQLQuery query = getSession().createSQLQuery("select nec.id, nec.cantidad, nec.estadoBalancinado, nec.estadoPintado, nec.estadoSoldadura, nec.fechaCreacion, nec.fechaFinalizacion, nec.prioridad, nec.finalizado, nec.producto_id from necesidades nec inner join productos prod on prod.id = nec.producto_id where nec.finalizado = true and prod.codigo = '"+ codigo +"'")
					//.addScalar("id", new LongType())
					//.addScalar("codigo")
					//.addScalar("descripcion");
					.addEntity(Necesidad.class);
			necesidades = query.list();
		}
		return necesidades;
	}
   
}
