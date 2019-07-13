package com.gestion.service;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.gestion.service.ProductoManager;
import com.gestion.service.impl.ProductoManagerImpl;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;

import com.gestion.dao.ProductoDAO;
import com.gestion.model.Producto;

import static org.junit.Assert.*;
/*
public class ProductoManagerTest extends BaseManagerTestCase {
    private ProductoManager bean;
    private UploadedFile uploadedFile;
    private ProductoDAO prodDAO;
    private ProductoDAO prodDAOUpdate;

    @Before
    public void onSetUp() {
    	ClassPathResource cpResource = new ClassPathResource("/prueba.xls");
    	File file = null;
    	InputStream inputStream = null;
    	
    	prodDAO = Mockito.mock(ProductoDAO.class); 	
    	prodDAOUpdate = Mockito.mock(ProductoDAO.class); 
        uploadedFile = Mockito.mock(UploadedFile.class);
        Producto mockProducto = Mockito.mock(Producto.class);
        
		try {
			file = new File(cpResource.getURL().getPath().toString());
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
    	try {
			inputStream = new FileInputStream(file);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        try {
			Mockito.when(uploadedFile.getInputstream()).thenReturn(inputStream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    

        Mockito.when(prodDAO.getProductoByCodigo(null)).thenReturn(mockProducto);
        Mockito.when(prodDAOUpdate.getProductoByCodigo("prueba1")).thenReturn(mockProducto);
        Mockito.when(prodDAOUpdate.getProductoByCodigo("prueba2")).thenReturn(mockProducto);
        Mockito.when(prodDAOUpdate.getProductoByCodigo("prueba3")).thenReturn(mockProducto);

    }

    @After
    public void onTearDown() {
        bean = null;
    }

    //@Test
    public void testGetProductosExcel() throws Exception {
        bean = new ProductoManagerImpl(prodDAO);
        assertTrue(bean.getProductosExcel(uploadedFile).size() > 0);

    }
    
    //@Test
    public void testGetProductosExcelUpdate() throws Exception {
        bean = new ProductoManagerImpl(prodDAOUpdate);
        bean.getProductosExcel(uploadedFile);
        assertTrue( bean.getCantActualizados() > 0);

    }
    
}
*/
