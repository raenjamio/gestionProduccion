package com.gestion.dao.hibernate;

import com.gestion.Constants;
import com.gestion.dao.ProductoDAO;
import com.gestion.model.Estado;
import com.gestion.model.Necesidad;
import com.gestion.model.Producto;
import com.gestion.model.Role;
import com.gestion.model.User;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.LongType;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate4.SessionFactoryUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import javax.persistence.Table;

import java.util.Iterator;
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
@Repository("productoDao")
public class ProductoDaoHibernate extends GenericDaoHibernate<Producto, Long> implements ProductoDAO {
	
	 public ProductoDaoHibernate() {
		 super(Producto.class);
	 }

	@Override
	public Producto saveProduto(Producto prod) {
		getSession().saveOrUpdate(prod);
        // necessary to throw a DataIntegrityViolation and catch it in UserManager
        getSession().flush();
        return prod;
	}
	
	 public Producto getProductoByCodigo(String prodCod) {
        List productos = getSession().createCriteria(Producto.class).add(Restrictions.eq("codigo", prodCod)).list();
        if (productos.isEmpty()) {
            return null;
        } else {
            return (Producto) productos.get(0);
        }
	 }
	 
	 public List<Producto> getProductosByCodigo(String prodCod) { 
	        List<Producto> productos = getSession().createCriteria(Producto.class).add(Restrictions.like("codigo","%"+prodCod+"%")).list();
	        if (productos.isEmpty()) {
	            return null;
	        } else {
	            return productos;
	        }
		 }

	@Override
	public Producto deleteProduto(Producto prod) {
		 Object producto = getProductoByCodigo(prod.getCodigo());
	     Session session = getSessionFactory().getCurrentSession();
	     
	     session.delete(producto);
		return null;
	}

	@Override
	public Producto updateProduto(Producto prod) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Producto> getProductosByCodigoSNecesidad(String prodCod) {
		// TODO Auto-generated method stub
		List<Producto> productos = null;

		if (prodCod == null) {
			//productos = getSession().createSQLQuery("select prod.*	from productos prod where not exists (select * from necesidades nec where nec.producto_id = prod.id AND nec.finalizado = true)").list();
			SQLQuery query = getSession().createSQLQuery("select prod.id, prod.codigo, prod.descripcion, prod.fileUploadHeader_id, prod.prioridad	from productos prod where not exists (select * from necesidades nec where nec.producto_id = prod.id AND nec.finalizado = false)")
					//.addScalar("id", new LongType())
					//.addScalar("codigo")
					//.addScalar("descripcion");
					.addEntity(Producto.class);
			productos = query.list();
			
			//productos = getSession().createCriteria(Producto.class).add(Restrictions.isNull("necesidad")).list();
		} else {
			productos = getSession().createCriteria(Producto.class).add(Restrictions.like("codigo","%"+prodCod+"%")).add(Restrictions.eq("necesidad", null)).list();
		}
		if (productos.isEmpty()) {
            return null;
        } else {
            return productos;
        }
		
	}

	@Override
	public Producto getProductoConNdadSinFinByCodigo(String codigo) {
		// TODO Auto-generated method stub
		List productos = getSession().createCriteria(Producto.class).add(Restrictions.eq("codigo", codigo))
				.add(Restrictions.isNotEmpty("necesidades")).list();
        if (productos.isEmpty()) {
            return null;
        } else {
        	Producto producto = (Producto)  productos.get(0);
        	
        	Iterator iNecesidades = producto.getNecesidades().iterator();
        	
        	while(iNecesidades.hasNext()) {
   	         Necesidad necesidad = (Necesidad) iNecesidades.next();
   	         
   	         if (!necesidad.getFinalizado()) {
   	        	 return producto;
   	         } 
        	}
        }
		return null;
	}

   
}
