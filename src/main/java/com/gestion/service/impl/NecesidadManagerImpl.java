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
        super(dao);
        this.necesidadDao = dao;
    }

    @Autowired
    public void setNecesidadDao(final NecesidadDAO dao) {
    	this.dao = dao;
        this.necesidadDao = dao;
    }

	@Override
	public Necesidad getNecesidad(String id) {
		// TODO Auto-generated method stub
		return dao.get(new Long(id));
	}

	@Override
	public List<Necesidad> getNecesidades() {
		// TODO Auto-generated method stub
		dao.getAll();
		return null;
	}

	@Override
	public Necesidad saveNecesidad(Necesidad necesidad) {
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
	
	public List<Necesidad> getNecesidadesNoFinalizadas(){
		return necesidadDao.getAllNoFinalizadas();
		
	}
	public List<Necesidad> getNecesidadesFinalizadas(){
		return necesidadDao.getAllFinalizadas();
		
	}

	@Override
	public List<Necesidad> getNecesidadesByProd(String codigo) {
		// TODO Auto-generated method stub
		return necesidadDao.getNecesidadesByProd(codigo);
	}
	
	public List<Necesidad> getTopBalancFinalizadas(){
		return necesidadDao.getTopBalancFinalizadas();
	}
}
