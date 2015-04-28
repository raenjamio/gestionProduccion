package com.gestion.service.impl;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.gestion.dao.ProductoDAO;
import com.gestion.model.Necesidad;
import com.gestion.model.Producto;
import com.gestion.model.User;
import com.gestion.service.ProductoManager;
import com.gestion.service.UserExistsException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;


/**
 * Implementation of UserManager interface.
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
@Service("productoManager")
public class ProductoManagerImpl extends GenericManagerImpl<Producto, Long> implements ProductoManager {
	
    private ProductoDAO prodDao;
    private Long cantInsertados=new Long(0);
    private Long cantActualizados=new Long(0);
    private Long cantBorrados= new Long(0);
    

    @Autowired
    public ProductoManagerImpl(ProductoDAO prodDao) {
        super(prodDao);
        this.prodDao = prodDao;
    }

    @Autowired
    public void setProductoDao(final ProductoDAO prodDao) {
    	this.dao = prodDao;
        this.prodDao = prodDao;
    }

	@Override
	public Producto getProducto(String prodId) {
		// TODO Auto-generated method stub
		return dao.get(new Long(prodId));
	}

	@Override
	public Producto getProductoByCodigo(String codigo)			throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		return prodDao.getProductoByCodigo(codigo);
	}

	@Override
	public List<Producto> getProductos() {
		// TODO Auto-generated method stub
		dao.getAll();
		return null;
	}

	@Override
	public Producto saveProducto(Producto prod) throws UserExistsException {
		return dao.save(prod);
	}

	@Override
	public void removeProducto(Producto prod) {
		// TODO Auto-generated method stub
		dao.remove(prod);
		
	}

	@Override
	public void removeProducto(String prodId) {
		// TODO Auto-generated method stub
		prodDao.remove(new Long(prodId));
	}

	@Override
	public List<Producto> search(String searchTerm) {
		// TODO Auto-generated method stub
		//return super.search(searchTerm, Producto.class);
		if  (searchTerm == null || "".equals(searchTerm.trim())) {
			return super.search(searchTerm, Producto.class);
        } else {
        	List<Producto> productos = getProductosByCodigo(searchTerm);
        	 if (productos == null) {
        		 return super.search(searchTerm, Producto.class);
        	 }
        	return productos;
        }
	}

	@Override
	public List<Producto> getProductosByCodigo(String prodCod) {
		// TODO Auto-generated method stub
		return prodDao.getProductosByCodigo(prodCod);
	}
	
	public List<Producto> getProductosByCodigoSinNecesidad(String prodCod) {
		// TODO Auto-generated method stub
		return prodDao.getProductosByCodigoSNecesidad(prodCod);
	}
	
	public List<Producto> getProductosExcel(UploadedFile file){
		
		try {
			//FileInputStream fileInputStream = new FileInputStream(file.getFileName());
			//HSSFWorkbook workbook = new HSSFWorkbook(fileInputStream);
			HSSFWorkbook workbook = new HSSFWorkbook(file.getInputstream());
			HSSFSheet worksheet = workbook.getSheet("Importar");
			List<Producto> productosTemp = new ArrayList();

			HSSFRow row1 = worksheet.getRow(1);
			
			Iterator rowIterator = worksheet.rowIterator();
			Producto producto;
			while (rowIterator.hasNext())
			{
				HSSFRow hssfRow = (HSSFRow) rowIterator.next();
				
				HSSFCell codigoCell = hssfRow.getCell(0);
				String codigo = codigoCell.getStringCellValue();
				
				//vemos si existe el producto, si es asi lo actualizamos
				producto = prodDao.getProductoByCodigo(codigo);
				if (producto == null) {			
					producto = new Producto();
					Necesidad necesidad = new Necesidad();
					
					producto.setCodigo(codigo);
					
					HSSFCell descripcionCell = hssfRow.getCell(1);
					String descripcion = descripcionCell.getStringCellValue();
					
					producto.setDescripcion(descripcion);
					
					HSSFCell faltanteCell = hssfRow.getCell(2);
					Integer faltante = (int) faltanteCell.getNumericCellValue();
					
					necesidad.setCantidad(faltante);
					necesidad.setFechaCreacion(new Date());
					necesidad.setFinalizado(false);
					necesidad.setProducto(producto);
					producto.setNecesidad(necesidad);
					
					productosTemp.add(producto);
					cantInsertados++;
				} else {
					//actualizamos descripcion
					HSSFCell descripcionCell = hssfRow.getCell(1);
					String descripcion = descripcionCell.getStringCellValue();
					producto.setDescripcion(descripcion);
					
					//actualizamos cantidad
					HSSFCell faltanteCell = hssfRow.getCell(2);
					Integer faltante = (int) faltanteCell.getNumericCellValue();
					
					producto.getNecesidad().setCantidad(faltante);
					
					prodDao.updateProduto(producto);
					
					cantActualizados++;
				}
					
			}
			
			return productosTemp;

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}		
		
		return null;
		
	}

	public Long getCantInsertados() {
		return cantInsertados;
	}

	public void setCantInsertados(Long cantInsertados) {
		this.cantInsertados = cantInsertados;
	}

	public Long getCantActualizados() {
		return cantActualizados;
	}

	public void setCantActualizados(Long cantActualizados) {
		this.cantActualizados = cantActualizados;
	}

	public Long getCantBorrados() {
		return cantBorrados;
	}

	public void setCantBorrados(Long cantBorrados) {
		this.cantBorrados = cantBorrados;
	}
	
	

}
