package com.gestion.webapp.action;


import java.util.ArrayList;
import java.util.List;

import com.gestion.service.FileUploadManager;
import com.gestion.service.ProductoManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;

import com.gestion.model.Producto;


import static org.junit.Assert.*;

public class FileNecesidadesFormTest extends BasePageTestCase {
    private FileNecesidadesForm bean;
    private FileNecesidadesForm beanError;
    @Autowired
    private FileUploadManager fileUploadManager;

    @Override
    @Before
    public void onSetUp() {
        super.onSetUp();
        bean = new FileNecesidadesForm();
        beanError = new FileNecesidadesForm();
        
        bean.setFileNecesidadesManager(fileUploadManager);
        beanError.setFileNecesidadesManager(fileUploadManager);
        
        assertNotNull(bean);

        ProductoManager productoManager = Mockito.mock(ProductoManager.class);
        ProductoManager productoManagerError = Mockito.mock(ProductoManager.class);
        UploadedFile uploadedFile = Mockito.mock(UploadedFile.class);
        Producto producto1 = Mockito.mock(Producto.class);
        Producto producto2 = Mockito.mock(Producto.class);
        FileUploadManager filemanager = Mockito.mock(FileUploadManager.class);
        
        List<Producto> productos = new ArrayList<Producto>();
        productos.add(producto1);
        productos.add(producto2);
        
        Mockito.when(producto1.getCodigo()).thenReturn("509");
        Mockito.when(producto2.getCodigo()).thenReturn("510");
        Mockito.when(producto1.getDescripcion()).thenReturn("Mock 1");
        Mockito.when(producto2.getDescripcion()).thenReturn("Mock 2");
        Mockito.when(producto1.getId()).thenReturn(1L);
        Mockito.when(producto2.getId()).thenReturn(2L);
        Mockito.when(filemanager.saveFileUpload(uploadedFile)).thenReturn(uploadedFile);
        

        Mockito.when(uploadedFile.getFileName()).thenReturn("prueba");
        Mockito.when(productoManager.getProductosExcel(uploadedFile)).thenReturn(productos);
        Mockito.when(productoManager.getCantInsertados()).thenReturn(2L);
        Mockito.when(productoManagerError.getProductosExcel(uploadedFile)).thenReturn(productos);
        Mockito.when(productoManagerError.getCantInsertados()).thenReturn(0L);
        
        
        bean.setFile(uploadedFile);
        bean.setProductoManager(productoManager);
        bean.setFileNecesidadesManager(filemanager);
        beanError.setFile(uploadedFile);
        beanError.setProductoManager(productoManagerError);
        beanError.setFileNecesidadesManager(filemanager);
    }

    @Override
    @After
    public void onTearDown() {
        super.onTearDown();
        bean = null;
    }

    @Test
    public void testImportar() throws Exception {
 	
        
        assertEquals("success", bean.importar());
        assertNotNull(bean);
        assertFalse(bean.hasErrors());
    }
    
    @Test
    public void testImportarError() throws Exception {
 	
        
        assertEquals("error", beanError.importar());
        assertNotNull(bean);
    }

}
