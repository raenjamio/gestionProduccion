package com.gestion.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.gestion.dao.NecesidadDAO;
import com.gestion.model.Necesidad;
import com.gestion.service.NecesidadManager;

import java.util.List;


/**
 * Implementation of UserManager interface.
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
@Service("necesidadManager")
public class NecesidadManagerImpl extends GenericManagerImpl<Necesidad, Long> implements NecesidadManager {
	
    private NecesidadDAO necesidadDao;
    

    @Autowired
    public NecesidadManagerImpl(NecesidadDAO dao) {
        super(prodDao);
        this.NecesidadDao = dao;
    }

    @Autowired
    public void setNecesidadDao(final NecesidadDAO dao) {
    	this.dao = dao;
        this.NecesidadDao = dao;
    }

	@Override
	public Producto getNecesidad(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Necesidad> getNecesidades() {
		// TODO Auto-generated method stub
		dao.getAll();
		return null;
	}

	@Override
	public Necesidad saveProducto(Necesidad necesidad) {
		return dao.save(necesidad);
	}

	@Override
	public void removeNecesidad(Necesidad necesidad) {
		// TODO Auto-generated method stub
		dao.remove(necesidad);
		
	}

	@Override
	public List<Necesidad> search(String searchTerm) {
		// TODO Auto-generated method stub
		return super.search(searchTerm, Necesidad.class);
	}

}
