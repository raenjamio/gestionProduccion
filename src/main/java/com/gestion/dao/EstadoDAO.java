package com.gestion.dao;


import com.gestion.model.Estado;


public interface EstadoDAO  extends GenericDao<Estado, Long>{
	
	Estado saveEstado(Estado estado);
	 
	Estado deleteEstado(Estado estado);
	 
	Estado updateEstado(Estado estado);

	Estado getEstadoByCodigo(String codigo);

}
