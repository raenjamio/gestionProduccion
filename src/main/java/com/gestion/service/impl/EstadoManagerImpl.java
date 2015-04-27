package com.gestion.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.gestion.dao.EstadoDAO;
import com.gestion.model.Estado;
import com.gestion.service.EstadoManager;

import java.util.List;


/**
 * Implementation of UserManager interface.
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
@Service("EstadoManager")
public class EstadoManagerImpl extends GenericManagerImpl<Estado, Long> implements EstadoManager {
	
    private EstadoDAO EstadoDao;
    

    @Autowired
    public EstadoManagerImpl(EstadoDAO dao) {
        super(dao);
        this.EstadoDao = dao;
    }

    @Autowired
    public void setEstadoDao(final EstadoDAO dao) {
    	this.dao = dao;
        this.EstadoDao = dao;
    }

	@Override
	public Estado getEstado(String id) {
		// TODO Auto-generated method stub
		return dao.get(new Long(id));
	}

	@Override
	public List<Estado> getEstados() {
		// TODO Auto-generated method stub
		dao.getAll();
		return null;
	}

	@Override
	public Estado saveEstado(Estado Estado) {
		return dao.save(Estado);
	}

	@Override
	public void removeEstado(Estado Estado) {
		// TODO Auto-generated method stub
		dao.remove(Estado);
		
	}

	@Override
	public List<Estado> search(String searchTerm) {
		// TODO Auto-generated method stub
		return super.search(searchTerm, Estado.class);
	}

}
