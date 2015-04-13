package com.gestion.dao;


import com.gestion.model.Producto;
import com.gestion.model.User;

public interface ProductoDAO  extends GenericDao<Producto, Long>{
	
	 Producto saveProduto(Producto prod);
	 
	 Producto deleteProduto(Producto prod);
	 
	 Producto updateProduto(Producto prod);

}
