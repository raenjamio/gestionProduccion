package com.gestion.dao;


import com.gestion.model.Actividad;


public interface ActividadDAO  extends GenericDao<Actividad, Long>{
	
	Actividad saveActividad(Actividad Actividad);
	 
	Actividad deleteActividad(Actividad Actividad);
	 
	Actividad updateActividad(Actividad Actividad);


}
