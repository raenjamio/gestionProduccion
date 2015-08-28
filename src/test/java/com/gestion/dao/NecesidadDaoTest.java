package com.gestion.dao;

import com.gestion.model.Necesidad;
import com.gestion.model.Producto;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectRetrievalFailureException;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;

public class NecesidadDaoTest extends BaseDaoTestCase {
    @Autowired
    private NecesidadDAO dao;
    @Autowired
    private ProductoDAO daoProducto;
    private Necesidad necesidadG;

    @Test(expected=ObjectRetrievalFailureException.class)
    public void testGetNecesidadInvalid() throws Exception {
        dao.get(new Long(-11));
       // assertNull(necesidad);
    }

    @Test
    public void testGetNecesidad() throws Exception { 
    	Necesidad necesidad2 = dao.get(necesidadG.getId());
        assertNotNull(necesidad2);
    }
    
    @Test
    public void testUpdateNecesidad() throws Exception {
    	Necesidad necesidad = dao.get(necesidadG.getId());
        necesidad.setCantidad(10000);
        dao.save(necesidad);
        flush();

        necesidad = dao.get(necesidadG.getId());
        assertEquals(new Integer(10000), necesidad.getCantidad());
    }
    

    
    @Before 
    public void setUp() { 
        Necesidad necesidad= new Necesidad(); 
        Producto prod = new Producto("Prueba");
        prod.setDescripcion("new prod descr");
        
        List<Necesidad> necesidades = new ArrayList<Necesidad>();
        necesidad.setCantidad(new Integer(999));

        necesidad.setProducto(prod);

        necesidad.setFechaCreacion(new Date());
        necesidad.setFinalizado(false);
        necesidades.add(necesidad);
        prod.setNecesidades(necesidades);
        necesidad.setId(-1L);
        Producto prodg = daoProducto.save(prod);
        necesidadG = prodg.getNecesidades().get(0);
        //dao.saveNecesidad(necesidad);
    }

  /*  @Test
    public void testFindByNamedQuery() {
        HashMap<String, Object> queryParams = new HashMap<String, Object>();
        queryParams.put("codigo", "509");
        List<Producto> productos = dao.findByNamedQuery("findRoleByName", queryParams);
        assertNotNull(productos);
        assertTrue(productos.size() > 0);
    }*/
}
