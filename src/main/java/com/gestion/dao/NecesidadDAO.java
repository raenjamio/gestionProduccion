package com.gestion.dao;


import java.util.List;

import com.gestion.model.Necesidad;


public interface NecesidadDAO  extends GenericDao<Necesidad, Long>{
	
	Necesidad saveNecesidad(Necesidad necesidad);
	 
	Necesidad deleteNecesidad(Necesidad necesidad);
	 
	Necesidad updateNecesidad(Necesidad necesidad);

	List<Necesidad> getAllNoFinalizadas();

	List<Necesidad> getAllFinalizadas();

	List<Necesidad> getNecesidadesByProd(String codigo);

}
