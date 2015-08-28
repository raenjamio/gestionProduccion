package com.gestion.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.gestion.dao.NecesidadDAO;
import com.gestion.model.Chart;
import com.gestion.model.Necesidad;
import com.gestion.service.ChartManager;

import java.util.List;


/**
 * Implementation of UserManager interface.
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
@Service("chartManager")
public class ChartManagerImpl  implements ChartManager {
	
    private NecesidadDAO necesidadDao;
    

    @Autowired
    public ChartManagerImpl(NecesidadDAO dao) {
        this.necesidadDao = dao;
    }

    @Autowired
    public void setNecesidadDao(final NecesidadDAO dao) {
        this.necesidadDao = dao;
    }


	public List<Necesidad> getNecesidadesFinalizadas(){
		return necesidadDao.getAllFinalizadas();
		
	}
	
	public List<Chart> getNecesidadesSoldadasFinalizadas() {
		return necesidadDao.getSoldadasFinalizadas();
	}
	public List<Chart> getNecesidadesBalancinadasFinalizadas() {
		List<Chart> necesidades = necesidadDao.getBalancinadasFinalizadas();

		return necesidades;
	}
	public List<Chart> getNecesidadesProduccionFinalizadas() {
		List<Chart> necesidades = necesidadDao.getProduccionFinalizadas();

		return necesidades;
	}
	public List<Chart> getNecesidadesPintadasFinalizadas() {
		return necesidadDao.getPintadasFinalizadas();
	}

	@Override
	public List<Necesidad> getNecesidades() {
		// TODO Auto-generated method stub
		return null;
	}

	

}
