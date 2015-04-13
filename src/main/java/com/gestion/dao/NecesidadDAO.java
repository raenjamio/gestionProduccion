package com.gestion.dao;


import com.gestion.model.Necesidad;


public interface NecesidadDAO  extends GenericDao<Necesidad, Long>{
	
	Necesidad saveNecesidad(Necesidad necesidad);
	 
	Necesidad deleteNecesidad(Necesidad necesidad);
	 
	Necesidad updateNecesidad(Necesidad necesidad);

}
