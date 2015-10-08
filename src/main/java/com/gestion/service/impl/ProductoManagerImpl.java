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
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;


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
    private boolean existDuplicados = false;
    

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
	public Producto getProductoByCodigo(String codigo)	 {
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
	public Producto saveProducto(Producto prod)  {
		return dao.save(prod);
	}

	@Override
	public void removeProducto(Producto prod) {
		// TODO Auto-generated method stub
		dao.remove(prod);
		
	}
	

	public boolean isExistDuplicados() {
		return existDuplicados;
	}

	public void setExistDuplicados(boolean existDuplicados) {
		this.existDuplicados = existDuplicados;
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
			return prodDao.getProductos(""); //super.search(searchTerm, Producto.class);
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
			cantActualizados=new Long(0);
			cantInsertados=new Long(0);

			HSSFRow row1 = worksheet.getRow(1);
			
			Iterator rowIterator = worksheet.rowIterator();
			Producto producto;
			while (rowIterator.hasNext())
			{
				HSSFRow hssfRow = (HSSFRow) rowIterator.next();
				
				HSSFCell codigoCell = hssfRow.getCell(0);
				if (codigoCell != null) {
					String codigo = codigoCell.getStringCellValue();
					if (!"".equals(codigo)) {
			
						//vemos si existe el producto, si es asi lo actualizamos
						producto = prodDao.getProductoByCodigo(codigo);
						//producto = prodDao.getProductoConNdadSinFinByCodigo(codigo);
						if (producto == null) {			
							producto = new Producto();
							Necesidad necesidad = new Necesidad();
							List<Necesidad> necesidades = new ArrayList<Necesidad>();
							
							producto.setCodigo(codigo);
							if (productosTemp.contains(producto)) { //si existe no importo nada
								setExistDuplicados(true);
								return null;
							}
								HSSFCell descripcionCell = hssfRow.getCell(1);
								String descripcion = descripcionCell.getStringCellValue();
								
								producto.setDescripcion(descripcion);
								
								HSSFCell faltanteCell = hssfRow.getCell(2);
								Integer faltante = (int) faltanteCell.getNumericCellValue();
								
								necesidad.setCantidad(faltante);
								necesidad.setFechaCreacion(new Date());
								necesidad.setFinalizado(false);
								necesidad.setProducto(producto);
								//producto.setNecesidad(necesidad);
								necesidades.add(necesidad);
								producto.setNecesidades(necesidades);
								productosTemp.add(producto);
								cantInsertados++;
							 
						} else {//existe el producto
							//actualizamos descripcion
							HSSFCell descripcionCell = hssfRow.getCell(1);
							String descripcion = descripcionCell.getStringCellValue();
							Necesidad necesidad = null;
							producto.setDescripcion(descripcion);
							
							//actualizamos cantidad
							HSSFCell faltanteCell = hssfRow.getCell(2);
							Integer faltante = (int) faltanteCell.getNumericCellValue();
							
							Iterator iNecesidades = producto.getNecesidades().iterator();
							//producto.getNecesidades().setCantidad(faltante);
							while (iNecesidades.hasNext()) {
								Necesidad _necesidad = (Necesidad) iNecesidades.next();
								if (!_necesidad.getFinalizado()) {
									necesidad = _necesidad; //si existe una necesidad sin terminar la actualizo
								}
							}
							//si no existe ninguna necesidad sin terminar la creo
							if (necesidad == null) {
								necesidad = new Necesidad();
								
								necesidad.setFechaCreacion(new Date());
								necesidad.setFinalizado(false);
								necesidad.setProducto(producto);
								
								producto.getNecesidades().add(necesidad);
							}
								
							necesidad.setCantidad(faltante);
							prodDao.updateProduto(producto);
							
							cantActualizados++;
						}
					}
				} //if codigocell is not null
					
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
