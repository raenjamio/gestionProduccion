package com.gestion.dao.hibernate;

import com.gestion.dao.EstadoDAO;
import com.gestion.model.Estado;
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
@Repository("estadoDao")
public class EstadoDaoHibernate extends GenericDaoHibernate<Estado, Long> implements EstadoDAO {
	
	 public EstadoDaoHibernate() {
		 super(Estado.class);
	 }
	@Override
	public Estado saveEstado (Estado estado) {
		getSession().saveOrUpdate(estado);
        // necessary to throw a DataIntegrityViolation and catch it in UserManager
        getSession().flush();
        return estado;
	}
	
	/*public Estado deleteEstado(Estado Estado) {
		 Object producto = getEstadoById(Estado.getId());
	     Session session = getSessionFactory().getCurrentSession();
	     
	     session.delete(producto);
		return null;
	}*/
	@Override
	public Estado updateEstado(Estado estado) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Estado deleteEstado(Estado estado) {
		// TODO Auto-generated method stub
	     Session session = getSessionFactory().getCurrentSession();
	     
	     session.delete(estado);
		return null;
	}
	
	public Estado getEstadoByCodigo(String codigo) {
		List estados = getSession().createCriteria(Estado.class).add(Restrictions.eq("codigo", codigo)).list();
        if (estados.isEmpty()) {
            return null;
        } else {
            return (Estado) estados.get(0);
        }
           
	}
	@Override
	public Estado getProductosByCodigo(String codigo) {
		// TODO Auto-generated method stub
		return null;
	}


   
}
