package com.gestion.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.gestion.dao.ActividadDAO;
import com.gestion.model.Actividad;
import com.gestion.service.ActividadManager;

import java.util.List;


/**
 * Implementation of UserManager interface.
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
@Service("actividadManager")
public class ActividadManagerImpl extends GenericManagerImpl<Actividad, Long> implements ActividadManager {
	
    private ActividadDAO actividadDao;
    

    @Autowired
    public ActividadManagerImpl(ActividadDAO dao) {
        super(dao);
        this.actividadDao = dao;
    }

    @Autowired
    public void setActividadDao(final ActividadDAO dao) {
    	this.dao = dao;
        this.actividadDao = dao;
    }

	@Override
	public Actividad getActividad(String id) {
		// TODO Auto-generated method stub
		return dao.get(new Long(id));
	}

	@Override
	public List<Actividad> getActividades() {
		// TODO Auto-generated method stub
		dao.getAll();
		return null;
	}

	@Override
	public Actividad saveActividad(Actividad actividad) {
		return dao.save(actividad);
	}

	@Override
	public void removeActividad(Actividad actividad) {
		// TODO Auto-generated method stub
		dao.remove(actividad);
		
	}

	@Override
	public List<Actividad> search(String searchTerm) {
		// TODO Auto-generated method stub
		return super.search(searchTerm, Actividad.class);
	}


}
