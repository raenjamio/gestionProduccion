package com.gestion.webapp.action;

import com.gestion.Constants;
import com.gestion.dao.SearchException;
import com.gestion.model.Actividad;
import com.gestion.model.Estado;
import com.gestion.model.Necesidad;
import com.gestion.model.Producto;
import com.gestion.service.ActividadManager;
import com.gestion.service.EstadoManager;
import com.gestion.service.NecesidadManager;
import com.gestion.util.ConvertUtil;
import com.gestion.webapp.util.RequestUtil;

import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Ordering;
import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.comparators.ComparatorChain;
import org.apache.commons.lang.SerializationUtils;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;
import org.springframework.mail.MailException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;

import javax.faces.component.UIData;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.hibernate.Hibernate;
import org.omnifaces.util.Ajax;

/**
 * JSF Page class to handle editing a user with a form.
 *
 * @author mraible
 */
public class DtEditPlanificacionForm extends BasePage implements Serializable {
    private static final long serialVersionUID = -1141119853856863204L;
    private String id;
    private NecesidadManager necesidadManager;
    private String query;
    public String codEstado;
    private Necesidad necesidad = new Necesidad();
    private Necesidad necesidadSelected = new Necesidad();
    private EstadoManager estadoManager;
    private String prioridad;
    private List<Necesidad> necesidades;
    private List<Necesidad> necesidadesNoFinalizadasList;
    private ActividadManager actividadManager;
    private Actividad actividad = new Actividad();
    private boolean changePrioridad = false;
    private List<Necesidad> selectedNecesidades;
    private List<Necesidad> necesidadesCreadasPorCantidadPintada = new ArrayList<>();
    private String estadoProduccion;
    private String cantidadPintada;

    public String getCantidadPintada() {
        return cantidadPintada;
    }

    public void setCantidadPintada(String cantidadPintada) {
        this.cantidadPintada = cantidadPintada;
    }

    public String getEstadoProduccion() {
        return estadoProduccion;
    }

    public void setEstadoProduccion(String estadoProduccion) {
        this.estadoProduccion = estadoProduccion;
    }

    public String getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(String prioridad) {
        this.prioridad = prioridad;
    }

    public String getCodEstado() {
        return codEstado;
    }

    public void setCodEstado(String codEstado) {
        if (codEstado != null && !codEstado.equals("") && (this.codEstado != null && !this.codEstado.equals("") || this.codEstado == null)) {
            this.codEstado = codEstado;
        }
        else if (codEstado == null) {
            this.codEstado = codEstado;
        }
    }

    public DtEditPlanificacionForm() {
        setSortColumn("prioridad");
        super.nullsAreHigh = true;
        this.setNecesidadesNoFinalizadasList(new ArrayList<Necesidad>());
    }


    public ActividadManager getActividadManager() {
        return actividadManager;
    }

    public void setActividadManager(ActividadManager actividadManager) {
        this.actividadManager = actividadManager;
    }

    public void setNecesidades(List<Necesidad> necesidades) {
        this.necesidades = necesidades;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setNecesidadManager(NecesidadManager necesidadManager) {
        this.necesidadManager = necesidadManager;
    }


    public EstadoManager getEstadoManager() {
        return estadoManager;
    }

    public void setEstadoManager(EstadoManager estadoManager) {
        this.estadoManager = estadoManager;
    }

    public NecesidadManager getNecesidadManager() {
        return necesidadManager;
    }

    public Necesidad getNecesidad() {
        return necesidad;
    }

    public void setNecesidad(Necesidad necesidad) {
        this.necesidad = necesidad;
    }

    public String cancel() {
        if (log.isDebugEnabled()) {
            log.debug("Entering 'cancel' method");
        }

        if (!"list".equals(getParameter("from"))) {
            return "home";
        } else {
            return "cancel";
        }
    }

    /**
     * Convenience method to determine if the user came from the list screen
     *
     * @return String
     */

    public void setQuery(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }

    public List getNecesidades() {
        try {
            return sort(getNecesidadesNoFinalizadas());
        } catch (SearchException se) {
            addError(se.getMessage());
            return sort(necesidadManager.search(query));
        }
    }

    public List getNecesidadesNoFinalizadas() {
        try {
            return sort(necesidadManager.getNecesidadesNoFinalizadas());
        } catch (SearchException se) {
            addError(se.getMessage());
            return sort(necesidadManager.search(query));
        }
    }

    public List getNecesidadesFinalizadas() {
        try {
            return sort(necesidadManager.getNecesidadesFinalizadas());
        } catch (SearchException se) {
            addError(se.getMessage());
            return sort(necesidadManager.search(query));
        }
    }


    public List<Necesidad> getNecesidadesNoFinalizadasList() {
        return necesidadesNoFinalizadasList;
    }

    public void setNecesidadesNoFinalizadasList(
            List<Necesidad> necesidadesNoFinalizadasList) {
        this.necesidadesNoFinalizadasList = necesidadesNoFinalizadasList;
    }

    public String search() {
        return "success";
    }

    public List<Necesidad> getSelectedNecesidades() {
        return selectedNecesidades;
    }

    public void setSelectedNecesidades(List<Necesidad> selectedNecesidades) {
        this.selectedNecesidades = selectedNecesidades;
    }

    public void onRowEdit(RowEditEvent event) {
        Necesidad necesidadDiferencia = null;
        //asignamos la necesidad que editamos
        Necesidad necesidad = (Necesidad) event.getObject();
        this.necesidad = necesidadManager.getNecesidad(necesidad.getId().toString());

        if (this.cantidadPintada != null && isNumeric(cantidadPintada)  && !"".equals(this.cantidadPintada)){
            necesidad.setCantidadPintada(new Integer(this.cantidadPintada));
        }

        if (necesidad.getCantidadPintada() != null && necesidad.getCantidadPintada() > 0) {
            if (necesidad.getCantidadPintada() > necesidad.getCantidad()) {
                necesidad.setCantidadPintada(necesidad.getCantidad());
            }
            if (necesidad.getCantidadPintada() < necesidad.getCantidad()) {
                //creamos una necesidad nueva
                necesidadDiferencia = new Necesidad();
                necesidadDiferencia.setPrioridad(necesidad.getPrioridad());
                necesidadDiferencia.setCantidad(necesidad.getCantidad() - necesidad.getCantidadPintada());
                necesidadDiferencia.setEstadoProduccion(necesidad.getEstadoProduccion());
                necesidadDiferencia.setEstadoSoldadura(necesidad.getEstadoSoldadura());
                necesidadDiferencia.setFechaControlProduccion(necesidad.getFechaControlProduccion());
                necesidadDiferencia.setFechaCreacion(new Date());
                necesidadDiferencia.setProducto(necesidad.getProducto());
                List<Estado> estados = new ArrayList<>(necesidad.getEstados());
                necesidadDiferencia.setEstados(estados);
            }
            this.necesidad.setCantidadPintada(necesidad.getCantidadPintada());
        }

        //buscamos el estado de acuerdo al codigo seleccionado y se lo agregamos a la lista de estados de la necesidad
        Estado estado = estadoManager.getEstadoByCodigo(codEstado);

        if (this.prioridad != null && !"".equals(this.prioridad)) {
            if (isNumeric(prioridad)) {
                actividad.setDescripcion("Se modifico prioridad: " + necesidad.getPrioridad() + " por : " + this.prioridad);
                this.necesidad.setPrioridad(new Long(this.prioridad));
                necesidad.setPrioridad(new Long(this.prioridad));
                actividad.setRolUser(getRequest().getRemoteUser());
                actividad.setNecesidad(necesidad);
                actividad.setFechaCreacion(new Date());
                //necesidad.getActividades().add(actividad);
                this.setChangePrioridad(true);
                //necesidadManager.getNecesidad(necesidad.getId().toString()).getActividades().add(actividad);
            } else {
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage("El campo prioridad debe ser num�rico", "No se puede cambiar el estado"));

                return;
            }
        }

        if (estado != null) {
            //si le voy a poner finalizado, lo pongo sin importar otro estado
            if (codEstado.contains("FINALIZADO")) {
                this.necesidad.getEstados().add(estado);
                necesidad.getEstados().add(estado);
            } else if (this.necesidad.estaFinalizado(codEstado)) {
                this.necesidad.getEstados().add(estado);
                necesidad.getEstados().add(estado);
            } else {
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage("El producto no se encuentra finalizado", "No se puede cambiar el estado"));

                return;
            }
            //si el codigo es igual al ultimo proceso lo damos por finalizado
            if (estado.getCodigo().equals(Constants.PINTURA_CONTROLADO)) {
                necesidad.setFinalizado(true);
                necesidad.setFechaFinalizacion(new Date());
                necesidad.setFechaControlPintado(new Date());
            } else if (estado.getCodigo().equals(Constants.PINTURA_FINALIZADO)) {
                necesidad.setFechaFinalPintado(new Date());
            } else if (estado.getCodigo().equals(Constants.PRODUCCION_FINALIZADO)) {
                necesidad.setFechaFinalProduccion(new Date());
            } else if (estado.getCodigo().equals(Constants.PRODUCCION_CONTROLADO)) {
                necesidad.setFechaControlProduccion(new Date());
            }
        } else {
            if (codEstado != null && !"".equals(codEstado)) {
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage("Necesidad no actualizada", "No esta parametrizado el estado"));
                return;
            }
        }

        if (Constants.CONTROLADO.equals(necesidad.getEstadoPintado())) {
            necesidad.setFinalizado(true);
            necesidad.setFechaFinalizacion(new Date());
            necesidad.setFechaControlPintado(new Date());
        }

        //guardamos el cambio
        necesidadManager.saveNecesidad(necesidad);
        if (necesidadDiferencia != null && !existeNecesidadEn(this.getNecesidadesNoFinalizadasList(), necesidadDiferencia)) {
            necesidadDiferencia = necesidadManager.saveNecesidad(necesidadDiferencia);
            this.getNecesidadesNoFinalizadasList().add(necesidadDiferencia);
        }

        FacesContext context = FacesContext.getCurrentInstance();


        this.buscarNecesidadLista(this.getNecesidadesNoFinalizadasList(), necesidad);

        if (necesidad.getPrioridad() != null || necesidadDiferencia != null) {
            //si se cambio la prioridad reordenamos
            //this.setNecesidadesNoFinalizadasList(sort(this.getNecesidadesNoFinalizadasList()));
            Collections.sort(getNecesidadesNoFinalizadasList(), new Comparator<Necesidad>() {
                @Override
                public int compare(Necesidad p1, Necesidad p2) {
                    return ComparisonChain.start()
                       .compare(p1.getPrioridad(), p2.getPrioridad(), Ordering.natural().nullsLast())
                       .compare(p1.getProducto().getCodigo(), p2.getProducto().getCodigo(), Ordering.natural().nullsLast())
                       .result();
                }
            });
        }
        setCodEstado(null);
        //setPrioridad(null);
        setCantidadPintada(null);
        //necesidades = getNecesidadesNoFinalizadas();
        context.addMessage(null, new FacesMessage("Producto editado", "Se cambio de estado y/o prioridad"));
    }

    private boolean existeNecesidadEn(List<Necesidad> necesidadesNoFinalizadasList, Necesidad necesidadDiferencia) {
        boolean exist = false;
        for (Necesidad necesidad: necesidadesNoFinalizadasList) {
            if (necesidad.getProducto().getCodigo() != null && necesidadDiferencia.getProducto().getCodigo() != null &&
            necesidad.getProducto().getCodigo().equals(necesidadDiferencia.getProducto().getCodigo())) {
                if (necesidad.getCantidad() != null && necesidadDiferencia.getCantidad() != null
                        && necesidad.getCantidad().compareTo(necesidadDiferencia.getCantidad()) == 0){
                    exist = true;
                }
            }
        }
        return exist;
    }

    //busco la necesidad de la tabla para actualizarla y no tener que cargar toda la tabla
    private void buscarNecesidadLista(List<Necesidad> necesidadesNoFinalizadasList2, Necesidad necesidad2) {
        for (Necesidad necesidad : necesidadesNoFinalizadasList2) {
            if   (necesidad.equals(necesidad2)) {
                if (necesidad2.getFinalizado()) {
                    //si esta finalizado lo eliminamos de la coleccion sino se sigue viendo en pantalla
                    necesidadesNoFinalizadasList2.remove(necesidad2);
                    break;
                }
                necesidad.setPrioridad(necesidad2.getPrioridad());
                necesidad.setEstadoProduccion(necesidad2.   getEstadoProduccion());
                necesidad.setEstadoPintado(necesidad2.getEstadoPintado());
                necesidad.setEstadoSoldadura(necesidad2.getEstadoSoldadura());
                necesidad.setEstados(necesidad2.getEstados());
                break;
            }
        }

    }

    private boolean isNumeric(String prioridad2) {
        try {
            Integer num = Integer.parseInt(prioridad2);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Se cancelo la edicion", ((Necesidad) event.getObject()).getProducto().getCodigo());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onCellEdit(CellEditEvent event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();

        if (newValue != null && !newValue.equals(oldValue)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cell Changed", "Old: " + oldValue + ", New:" + newValue);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public Integer totalFinalizadosByProd(Producto producto) {
        Integer total = new Integer(0);
        List<Necesidad> necesidades = necesidadManager.getNecesidadesByProd(producto.getCodigo());

        for (Necesidad necesidad : necesidades) {
            total += necesidad.getCantidad();
        }
        return total;

    }

    @PostConstruct
    public void init() {
        if (this.getNecesidadesNoFinalizadasList() != null && this.getNecesidadesNoFinalizadasList().isEmpty()) {
            this.setNecesidadesNoFinalizadasList(sort(necesidadManager.getNecesidadesNoFinalizadas()));
        }
        //necesidades = getNecesidadesNoFinalizadas();
    }

    public void refresh() {
        //necesidades = getNecesidadesNoFinalizadas();
    }

    public boolean isChangePrioridad() {
        return changePrioridad;
    }

    public void setChangePrioridad(boolean changePrioridad) {
        this.changePrioridad = changePrioridad;
    }

    public Necesidad getNecesidadSelected() {
        return necesidadSelected;
    }

    public void setNecesidadSelected(Necesidad necesidadSelected) {
        this.necesidadSelected = necesidadSelected;
    }

    public String controlarNecesidades() {
        Estado estado = estadoManager.getEstadoByCodigo(Constants.PINTURA_CONTROLADO);
        for (Necesidad necesidad : selectedNecesidades) {
                necesidad.setFinalizado(true);
                necesidad.setFechaFinalizacion(new Date());
                necesidad.setFechaControlPintado(new Date());
                necesidad.setEstadoPintado(Constants.CONTROLADO);
                necesidad.getEstados().add(estado);
                necesidadManager.saveNecesidad(necesidad);
        }
        this.setNecesidadesNoFinalizadasList(sort(necesidadManager.getNecesidadesNoFinalizadas()));
        return "success";
    }

    public void updateEstados() {
        RequestContext.getCurrentInstance().update(":formPlanificacion:necesidades:estadoSoldaduraOutput");
    }
}
