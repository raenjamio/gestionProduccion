package com.gestion.webapp.action;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.gestion.Constants;
import com.gestion.service.EstadoManager;
import com.gestion.service.FileUploadManager;
import com.gestion.service.NecesidadManager;
import com.gestion.service.ProductoManager;
import com.gestion.service.impl.EstadoManagerImpl;
import com.gestion.service.impl.NecesidadManagerImpl;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;

import com.gestion.model.Estado;
import com.gestion.model.Necesidad;
import com.gestion.model.Producto;

import static org.junit.Assert.*;

public class DtEditPlanificacionFormTest extends BasePageTestCase {
    private DtEditPlanificacionForm bean;
    private DtEditPlanificacionForm beanError;

    @Autowired
    private FileUploadManager fileUploadManager;
 //   @Autowired
    private NecesidadManager necesidadManager;
//@Autowired
    private EstadoManager estadoManager;

    @Override
    @Before
    public void onSetUp() {
        super.onSetUp();
        bean = new DtEditPlanificacionForm();
        beanError = new DtEditPlanificacionForm();
    	necesidadManager  = Mockito.mock(NecesidadManagerImpl.class);
    	estadoManager = Mockito.mock(EstadoManagerImpl.class);
       
       /*  
        assertNotNull(bean);

        ProductoManager productoManager = Mockito.mock(ProductoManager.class);
        ProductoManager productoManagerError = Mockito.mock(ProductoManager.class);
        UploadedFile uploadedFile = Mockito.mock(UploadedFile.class);
        Producto producto1 = Mockito.mock(Producto.class);
        Producto producto2 = Mockito.mock(Producto.class);
        
        List<Producto> productos = new ArrayList<Producto>();
        productos.add(producto1);
        productos.add(producto2);
        
        Mockito.when(producto1.getCodigo()).thenReturn("509");
        Mockito.when(producto2.getCodigo()).thenReturn("510");
        Mockito.when(producto1.getDescripcion()).thenReturn("Mock 1");
        Mockito.when(producto2.getDescripcion()).thenReturn("Mock 2");
        Mockito.when(producto1.getId()).thenReturn(1L);
        Mockito.when(producto2.getId()).thenReturn(2L);

        Mockito.when(uploadedFile.getFileName()).thenReturn("prueba");
        Mockito.when(productoManager.getProductosExcel(uploadedFile)).thenReturn(productos);
        Mockito.when(productoManager.getCantInsertados()).thenReturn(2L);
        Mockito.when(productoManagerError.getProductosExcel(uploadedFile)).thenReturn(productos);
        Mockito.when(productoManagerError.getCantInsertados()).thenReturn(0L);
        
        
        bean.setFile(uploadedFile);
        bean.setProductoManager(productoManager);
        beanError.setFile(uploadedFile);
        beanError.setProductoManager(productoManagerError);*/
    }

    @Override
    @After
    public void onTearDown() {
        super.onTearDown();
        bean = null;
    }

    @Test
    public void testonRowEditProdFin() {
    	RowEditEvent event = Mockito.mock(RowEditEvent.class);
    	Necesidad necesidad = new Necesidad();
    	Estado estado = Mockito.mock(Estado.class);

    	Mockito.when(estado.getCodigo()).thenReturn(Constants.PRODUCCION_FINALIZADO);
    	Mockito.when(estado.getId()).thenReturn(1L);
    	
    	necesidad.setCantidad(200);
    	necesidad.setId(500L);
    	necesidad.setFinalizado(false);
    	necesidad.setPrioridad(null);
    	
    	Mockito.when(event.getObject()).thenReturn(necesidad);
    	
    	Mockito.when(necesidadManager.getNecesidad("500")).thenReturn(necesidad);
    	
    	Mockito.when(estadoManager.getEstadoByCodigo(Constants.PRODUCCION_FINALIZADO)).thenReturn(estado);
    	
    	bean.setEstadoManager(estadoManager);
    	bean.setNecesidadManager(necesidadManager);
    	bean.setCodEstado(Constants.PRODUCCION_FINALIZADO);
    	bean.setPrioridad("1");
    	
    	bean.onRowEdit(event);
    	assertNotNull(bean.getNecesidad().getFechaFinalProduccion());
    	assertNull(bean.getNecesidad().getFechaControlProduccion());
    	assertNull(bean.getNecesidad().getFechaControlPintado());
    	assertNull(bean.getNecesidad().getFechaFinalPintado());
    	assertNull(bean.getNecesidad().getFechaFinalizacion());
       	assertTrue(bean.getNecesidad().getActividades().size() > 0);
    	assertEquals(new Long("1"),bean.getNecesidad().getPrioridad());
        assertFalse(bean.hasErrors());
        assertNotNull(necesidadManager.getNecesidad(bean.getNecesidad().getId().toString()));
    }
    
    @Test
    public void testonRowEditProdCont() {
    	RowEditEvent event = Mockito.mock(RowEditEvent.class);
    	Necesidad necesidad = new Necesidad();
    	Estado estado = Mockito.mock(Estado.class);

    	Mockito.when(estado.getCodigo()).thenReturn(Constants.PRODUCCION_CONTROLADO);
    	Mockito.when(estado.getId()).thenReturn(1L);
    	
    	necesidad.setCantidad(200);
    	necesidad.setId(500L);
    	necesidad.setFinalizado(false);
    	necesidad.setPrioridad(null);
    	
    	Mockito.when(event.getObject()).thenReturn(necesidad);
    	
    	Mockito.when(necesidadManager.getNecesidad("500")).thenReturn(necesidad);
    	
    	Mockito.when(estadoManager.getEstadoByCodigo(Constants.PRODUCCION_CONTROLADO)).thenReturn(estado);
    	
    	bean.setEstadoManager(estadoManager);
    	bean.setNecesidadManager(necesidadManager);
    	bean.setCodEstado(Constants.PRODUCCION_CONTROLADO);
    	bean.setPrioridad("1");
    	
    	bean.onRowEdit(event);
    	assertNull(bean.getNecesidad().getFechaFinalProduccion()); //no debe setear el final balancinado ni el control, ya que antes debe estar finalizado
    	assertNull(bean.getNecesidad().getFechaControlProduccion());
    	assertNull(bean.getNecesidad().getFechaControlPintado());
    	assertNull(bean.getNecesidad().getFechaFinalPintado());
    	assertNull(bean.getNecesidad().getFechaFinalizacion());
    	assertTrue(bean.getNecesidad().getActividades().size() > 0);
    	assertEquals(new Long("1"),bean.getNecesidad().getPrioridad());
        assertFalse(bean.hasErrors());
        assertNotNull(necesidadManager.getNecesidad(bean.getNecesidad().getId().toString()));
    }
    
    @Test
    public void testonRowEditProd() {
    	RowEditEvent event = Mockito.mock(RowEditEvent.class);
    	Necesidad necesidad = new Necesidad();
    	Estado estadoFinalizado = Mockito.mock(Estado.class);
    	Estado estadoControlado = Mockito.mock(Estado.class);

    	Mockito.when(estadoFinalizado.getCodigo()).thenReturn(Constants.PRODUCCION_FINALIZADO);
    	Mockito.when(estadoFinalizado.getId()).thenReturn(1L);
    	Mockito.when(estadoControlado.getCodigo()).thenReturn(Constants.PRODUCCION_CONTROLADO);
    	Mockito.when(estadoControlado.getId()).thenReturn(2L);
    	
    	necesidad.setCantidad(200);
    	necesidad.setId(500L);
    	necesidad.setFinalizado(false);
    	necesidad.setPrioridad(null);
    	necesidad.getEstados().add(estadoFinalizado);
    	necesidad.setFechaFinalProduccion(new Date());
    	
    	Mockito.when(event.getObject()).thenReturn(necesidad);
    	
    	Mockito.when(necesidadManager.getNecesidad("500")).thenReturn(necesidad);
    	
    	Mockito.when(estadoManager.getEstadoByCodigo(Constants.PRODUCCION_CONTROLADO)).thenReturn(estadoControlado);
    	
    	bean.setEstadoManager(estadoManager);
    	bean.setNecesidadManager(necesidadManager);
    	bean.setCodEstado(Constants.PRODUCCION_CONTROLADO);
    	bean.setPrioridad("1");
    	
    	bean.onRowEdit(event);
    	assertNotNull(bean.getNecesidad().getFechaFinalProduccion()); //no debe setear el final balancinado ni el control, ya que antes debe estar finalizado
    	assertNotNull(bean.getNecesidad().getFechaControlProduccion());
    	assertNull(bean.getNecesidad().getFechaControlPintado());
    	assertNull(bean.getNecesidad().getFechaFinalPintado());
    	assertNull(bean.getNecesidad().getFechaFinalizacion());
       	assertTrue(bean.getNecesidad().getActividades().size() > 0);
    	assertEquals(new Long("1"),bean.getNecesidad().getPrioridad());
        assertFalse(bean.hasErrors());
        assertNotNull(necesidadManager.getNecesidad(bean.getNecesidad().getId().toString()));
    }
    
    @Test
    public void testonRowEditPintFin() {
    	RowEditEvent event = Mockito.mock(RowEditEvent.class);
    	Necesidad necesidad = new Necesidad();
    	Estado estado = Mockito.mock(Estado.class);

    	Mockito.when(estado.getCodigo()).thenReturn(Constants.PINTURA_FINALIZADO);
    	Mockito.when(estado.getId()).thenReturn(1L);
    	
    	necesidad.setCantidad(200);
    	necesidad.setId(500L);
    	necesidad.setFinalizado(false);
    	necesidad.setFechaFinalizacion(null);
    	necesidad.setPrioridad(null);
    	
    	Mockito.when(event.getObject()).thenReturn(necesidad);
    	
    	Mockito.when(necesidadManager.getNecesidad("500")).thenReturn(necesidad);
    	
    	Mockito.when(estadoManager.getEstadoByCodigo(Constants.PINTURA_FINALIZADO)).thenReturn(estado);
    	
    	bean.setEstadoManager(estadoManager);
    	bean.setNecesidadManager(necesidadManager);
    	bean.setCodEstado(Constants.PINTURA_FINALIZADO);
    	bean.setPrioridad("1");
    	
    	bean.onRowEdit(event);
    	assertNull(bean.getNecesidad().getFechaFinalProduccion());
    	assertNull(bean.getNecesidad().getFechaControlProduccion());
    	assertNull(bean.getNecesidad().getFechaControlPintado());
    	assertNotNull(bean.getNecesidad().getFechaFinalPintado());
    	assertNull(bean.getNecesidad().getFechaFinalizacion());
       	assertTrue(bean.getNecesidad().getActividades().size() > 0);
    	assertEquals(new Long("1"),bean.getNecesidad().getPrioridad());
        assertFalse(bean.hasErrors());
        assertNotNull(necesidadManager.getNecesidad(bean.getNecesidad().getId().toString()));
    }
    
    @Test
    public void testonRowEditPintControlado() {
    	RowEditEvent event = Mockito.mock(RowEditEvent.class);
    	Necesidad necesidad = new Necesidad();
    	Estado estadoFinalizado = Mockito.mock(Estado.class);
    	Estado estadoControlado = Mockito.mock(Estado.class);

    	Mockito.when(estadoFinalizado.getCodigo()).thenReturn(Constants.PINTURA_FINALIZADO);
    	Mockito.when(estadoFinalizado.getId()).thenReturn(1L);
    	Mockito.when(estadoControlado.getCodigo()).thenReturn(Constants.PINTURA_CONTROLADO);
    	Mockito.when(estadoControlado.getId()).thenReturn(2L);
    	
    	necesidad.setCantidad(200);
    	necesidad.setId(500L);
    	necesidad.setFinalizado(false);
    	necesidad.setPrioridad(null);
    	necesidad.getEstados().add(estadoFinalizado);
    	necesidad.setFechaFinalPintado(new Date());
    	
    	Mockito.when(event.getObject()).thenReturn(necesidad);
    	
    	Mockito.when(necesidadManager.getNecesidad("500")).thenReturn(necesidad);
    	
    	Mockito.when(estadoManager.getEstadoByCodigo(Constants.PINTURA_CONTROLADO)).thenReturn(estadoControlado);
    	
    	bean.setEstadoManager(estadoManager);
    	bean.setNecesidadManager(necesidadManager);
    	bean.setCodEstado(Constants.PINTURA_CONTROLADO);
    	bean.setPrioridad("1");
    	
    	bean.onRowEdit(event);
    	assertNull(bean.getNecesidad().getFechaFinalProduccion()); //no debe setear el final balancinado ni el control, ya que antes debe estar finalizado
    	assertNull(bean.getNecesidad().getFechaControlProduccion());
    	assertNotNull(bean.getNecesidad().getFechaControlPintado());
    	assertNotNull(bean.getNecesidad().getFechaFinalPintado());
    	assertNotNull(bean.getNecesidad().getFechaFinalizacion());
       	assertTrue(bean.getNecesidad().getActividades().size() > 0);
    	assertEquals(new Long("1"),bean.getNecesidad().getPrioridad());
        assertFalse(bean.hasErrors());
        assertNotNull(necesidadManager.getNecesidad(bean.getNecesidad().getId().toString()));
    }

    
}
