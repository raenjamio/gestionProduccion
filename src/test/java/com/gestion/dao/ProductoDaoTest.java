package com.gestion.dao;

import com.gestion.Constants;
import com.gestion.model.Producto;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;

public class ProductoDaoTest extends BaseDaoTestCase {
    @Autowired
    private ProductoDAO dao;

    @Test
    public void testGetProductoInvalid() throws Exception {
        Producto producto = dao.getProductoByCodigo("");
        assertNull(producto);
    }

    @Test
    public void testGetProducto() throws Exception {
        Producto producto = dao.getProductoByCodigo("ZZTEST");
        assertNotNull(producto);
    }

    @Test
    public void testUpdateProducto() throws Exception {
        Producto producto = dao.getProductoByCodigo("ZZTEST");
        producto.setDescripcion("test descripcion");
        dao.save(producto);
        flush();

        producto = dao.getProductoByCodigo("ZZTEST");
        assertEquals("test descripcion", producto.getDescripcion());
    }

    @Test
    public void testAddAndRemoveProducto() throws Exception {
    	Producto producto = new Producto("testproducto");
        producto.setDescripcion("new prod descr");
        producto.setCodigo("TEST");
        dao.save(producto);
        flush();

        producto = dao.getProductoByCodigo("TEST");
        assertNotNull(producto.getDescripcion());

        dao.deleteProduto(producto);
        flush();

        producto = dao.getProductoByCodigo("TEST");
        assertNull(producto);
    }
    
    @Before 
    public void setUp() { 
        Producto prod= new Producto("ZZTEST"); 
        prod.setDescripcion("Prod JUnit Test");
        dao.save(prod);
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
