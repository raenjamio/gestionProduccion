package com.gestion.dao;


import java.util.List;

import com.gestion.model.Chart;
import com.gestion.model.Necesidad;


public interface NecesidadDAO  extends GenericDao<Necesidad, Long>{
	
	Necesidad saveNecesidad(Necesidad necesidad);
	 
	boolean deleteNecesidad(Necesidad necesidad);
	 
	Necesidad updateNecesidad(Necesidad necesidad);

	List<Necesidad> getAllNoFinalizadas();

	List<Necesidad> getAllFinalizadas();

	List<Necesidad> getNecesidadesByProd(String codigo);
	
	List<Necesidad> getTopBalancFinalizadas();

	List<Chart> getSoldadasFinalizadas();

	List<Chart> getBalancinadasFinalizadas();

	List<Chart> getPintadasFinalizadas();

	List<Chart> getProduccionFinalizadas();

	List<Necesidad> getTopProducidasFinalizadas();

}
