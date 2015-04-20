package com.gestion.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.gestion.dao.ProductoDAO;
import com.gestion.model.Producto;
import com.gestion.model.User;
import com.gestion.service.ProductoManager;
import com.gestion.service.UserExistsException;

import java.util.List;


/**
 * Implementation of UserManager interface.
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
@Service("productoManager")
public class ProductoManagerImpl extends GenericManagerImpl<Producto, Long> implements ProductoManager {
	
    private ProductoDAO prodDao;
    

    @Autowired
    public ProductoManagerImpl(ProductoDAO prodDao) {
        super(prodDao);
        this.prodDao = prodDao;
    }

    @Autowired
    public void setProductoDao(final ProductoDAO prodDao) {
    	this.dao = prodDao;
        this.prodDao = prodDao;
    }

	@Override
	public Producto getProducto(String prodId) {
		// TODO Auto-generated method stub
		return dao.get(new Long(prodId));
	}

	@Override
	public Producto getProductoByCodigo(String codigo)			throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		return prodDao.getProductoByCodigo(codigo);
	}

	@Override
	public List<Producto> getProductos() {
		// TODO Auto-generated method stub
		dao.getAll();
		return null;
	}

	@Override
	public Producto saveProducto(Producto prod) throws UserExistsException {
		return dao.save(prod);
	}

	@Override
	public void removeProducto(Producto prod) {
		// TODO Auto-generated method stub
		dao.remove(prod);
		
	}

	@Override
	public void removeProducto(String prodId) {
		// TODO Auto-generated method stub
		prodDao.remove(new Long(prodId));
	}

	@Override
	public List<Producto> search(String searchTerm) {
		// TODO Auto-generated method stub
		//return super.search(searchTerm, Producto.class);
		if  (searchTerm == null || "".equals(searchTerm.trim())) {
			return super.search(searchTerm, Producto.class);
        } else {
        	List<Producto> productos = getProductosByCodigo(searchTerm);
        	 if (productos == null) {
        		 return super.search(searchTerm, Producto.class);
        	 }
        	return productos;
        }
	}

	@Override
	public List<Producto> getProductosByCodigo(String prodCod) {
		// TODO Auto-generated method stub
		return prodDao.getProductosByCodigo(prodCod);
	}
	
	public List<Producto> getProductosByCodigoSinNecesidad(String prodCod) {
		// TODO Auto-generated method stub
		return prodDao.getProductosByCodigoSNecesidad(prodCod);
	}
	

}
