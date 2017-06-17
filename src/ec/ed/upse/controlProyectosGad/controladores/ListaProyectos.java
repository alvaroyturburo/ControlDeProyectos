package ec.ed.upse.controlProyectosGad.controladores;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Constraint;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;


import ec.ed.upse.controlProyectosGad.modelo.PdpAvance;
import ec.ed.upse.controlProyectosGad.modelo.PdpAvanceDAO;
import ec.ed.upse.controlProyectosGad.modelo.PdpEtapa;
import ec.ed.upse.controlProyectosGad.modelo.PdpEtapaDAO;
import ec.ed.upse.controlProyectosGad.modelo.PdpFase;
import ec.ed.upse.controlProyectosGad.modelo.PdpFaseDAO;
import ec.ed.upse.controlProyectosGad.modelo.PdpProyecto;
import ec.ed.upse.controlProyectosGad.modelo.PdpProyectoDAO;
import ec.ed.upse.controlProyectosGad.modelo.PdpsUsuario;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;


@Log
public class ListaProyectos {
	
	@Wire private Datebox dtbFechaInicial;
	@Wire private Datebox dtbFechaFinal;
	@Wire private Listbox avanceListbox;
	@Wire private Listbox etapaListbox;
	@Wire private Listbox faseListbox;
	
	
	@Getter @Setter private String textoBuscar;
	@Getter @Setter private Date fechaIniBuscar;
	@Getter @Setter private Date fechaFinBuscar;
	@Getter @Setter private PdpProyecto proyectoSeleccionado;
	@Getter @Setter private List<PdpProyecto> proyectos;
	private PdpProyectoDAO proyectosDao = new PdpProyectoDAO();
	
	
	/*Avances-Etapa-Fase*/
	@Getter @Setter private PdpAvance avanceSeleccionado;
	@Getter @Setter private List<PdpAvance> avances;
	private PdpAvanceDAO avancesDao = new PdpAvanceDAO();
	@Getter @Setter private PdpEtapa etapaSeleccionado;
	@Getter @Setter private List<PdpEtapa> etapas;
	private PdpEtapaDAO etapasDao = new PdpEtapaDAO();
	@Getter @Setter private PdpFase faseSeleccionado;
	@Getter @Setter private List<PdpFase> fases;
	private PdpFaseDAO fasesDao = new PdpFaseDAO();
	
		
	
	@Getter @Setter private PdpsUsuario usuario_login;
	
	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view){
		Selectors.wireComponents(view, this, false);
		Session session;
		session=Sessions.getCurrent();
		usuario_login = (PdpsUsuario) session.getAttribute("_USUARIO_");
		BindUtils.postGlobalCommand(null, null, "ListaProyectos.buscar", null);
		avanceListbox.setVisible(false);
		etapaListbox.setVisible(false);
		faseListbox.setVisible(false);
		log.info("Administración de Usuarios");
	}
	
	@GlobalCommand("ListaProyectos.buscar")
	@Command
	@NotifyChange({"*"})
	public void buscar(){
		if (proyectos != null) {
			proyectos = null; 
		}
		
		if (avances != null) {
			avances = null; 
		}
		
		if (fases != null) {
			fases = null; 
		}
		
		if (etapas != null) {
			etapas = null; 
		}
		
		/*if(dtbFechaInicial.getValue()!=null && dtbFechaFinal!=null){
		proyectos = proyectosDao.buscarProyectoPorNombreFechas(textoBuscar, fechaIniBuscar.getYear()+"-"+fechaIniBuscar.getMonth()+"-"+fechaIniBuscar.getDate(), fechaFinBuscar.getYear()+"-"+fechaFinBuscar.getMonth()+"-"+fechaFinBuscar.getDate());
		}else{*/
		proyectos = proyectosDao.buscarProyectoPorNombreFechas(textoBuscar,null,null);
		//}
		proyectoSeleccionado = null; 
		avanceSeleccionado = null; 
		etapaSeleccionado = null; 
		faseSeleccionado = null; 


	}
	
	@Command
	@NotifyChange({"*"})
	public void seleccionarTipoAvance(){
		if(proyectoSeleccionado.getPdpTipoAvance().getDescripcion().equals("Trimestral")){
			etapas = etapasDao.buscarEtapaPorProyecto(proyectoSeleccionado.getId());
			avanceListbox.setVisible(false);
			etapaListbox.setVisible(true);
			faseListbox.setVisible(false);
		}
		if(proyectoSeleccionado.getPdpTipoAvance().getDescripcion().equals("Mensual")){
			avances = avancesDao.buscarAvancePorProyecto(proyectoSeleccionado.getId());
			avanceListbox.setVisible(true);
			etapaListbox.setVisible(false);
			faseListbox.setVisible(false);
		}
		if(proyectoSeleccionado.getPdpTipoAvance().getDescripcion().equals("Fase")){
			fases = fasesDao.buscarFasePorProyecto(proyectoSeleccionado.getId());
			avanceListbox.setVisible(false);
			etapaListbox.setVisible(false);
			faseListbox.setVisible(true);
		}
	}
	
	public Constraint getValidaFecha() {
		return new Constraint() {
			
			@Override
			public void validate(Component comp, Object value){
				Date valor = (Date) value;
								
				if (valor.after(new Date())) {
					Clients.showNotification("La fecha no debe ser posterior a la fecha actual", "error", dtbFechaInicial, "end_center",2000,true);
					return;
				}
			}
		};		
	}
	
	public Constraint getValidaFechaAnterior() {
		return new Constraint() {
			
			@Override
			public void validate(Component comp, Object value){
				Date valor = (Date) value;
								
				if (valor.before(dtbFechaInicial.getValue())) {
					Clients.showNotification("La fecha no debe ser anterior a la fecha inicial", "error", dtbFechaFinal, "end_center",2000,true);
					return;
				}
			}
		};		
	}
	
	
	@Command
	@NotifyChange({""})
	public void nuevoProyecto(){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("Proyecto", new PdpProyecto());
		Window ventanaCargar = (Window) Executions.createComponents("proyectos/edicionProyecto.zul", null, params);
		ventanaCargar.doModal();
	}
	
	
	@Command
	@NotifyChange({""})
	public void editarProyecto(){
		if(getProyectoSeleccionado() == null){
			Messagebox.show("Debe seleccionar un Proyecto","Administración",Messagebox.OK,Messagebox.ERROR);
			return;
		}
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("Proyecto", proyectoSeleccionado);
		Window ventanaCargar = (Window) Executions.createComponents("proyectos/edicionProyecto.zul", null, params);
		ventanaCargar.doModal();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Command
	@NotifyChange({""})
	public void eliminarProyecto(){
		if(getProyectoSeleccionado() == null){
			Messagebox.show("Debe seleccionar un proyecto","Administración",Messagebox.OK,Messagebox.ERROR);
			return;
		}
		
		Messagebox.show("Desea eliminar el registro seleccionado? Recuerde que esta acción no puede ser revertida y"
				+ "puede ocasionar un mal funcionamiento de la aplicación", "Confirmación de Eliminación", Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, new EventListener() {
			
			@Override
			public void onEvent(Event event) throws Exception {
				if (event.getName().equals("onYes")) {
					
					try {
						proyectosDao.getEntityManager().getTransaction().begin();
						proyectoSeleccionado.setEstado("I");
						proyectoSeleccionado = proyectosDao.getEntityManager().merge(proyectoSeleccionado);
						proyectosDao.getEntityManager().getTransaction().commit();	
						Clients.showNotification("Transaccion ejecutada con exito");

						BindUtils.postGlobalCommand(null, null, "ListaProyectos.buscar", null);
					} catch (Exception e) {
						e.printStackTrace();
						proyectosDao.getEntityManager().getTransaction().rollback();
					}	
					
				}
			}
		});
	}
	
	//Avances
	@Command
	@NotifyChange({""})
	public void nuevoAvance(){
		if(getProyectoSeleccionado() == null){
			Messagebox.show("Debe seleccionar un proyecto","Administración",Messagebox.OK,Messagebox.ERROR);
			return;
		}
		
		
		if(proyectoSeleccionado.getPdpTipoAvance().getDescripcion().equals("Trimestral")){
			
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("Proyecto", proyectoSeleccionado);
				params.put("Etapa", new PdpEtapa());
				Window ventanaCargar = (Window) Executions.createComponents("proyectos/edicionEtapa.zul", null, params);
				ventanaCargar.doModal();
			
		}else{
			if(proyectoSeleccionado.getPdpTipoAvance().getDescripcion().equals("Mensual")){
				
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("Proyecto", proyectoSeleccionado);
					params.put("Avance", new PdpAvance());
					Window ventanaCargar = (Window) Executions.createComponents("proyectos/edicionAvance.zul", null, params);
					ventanaCargar.doModal();
				
			}else{
				if(proyectoSeleccionado.getPdpTipoAvance().getDescripcion().equals("Fase")){
					
						Map<String, Object> params = new HashMap<String, Object>();
						params.put("Proyecto", proyectoSeleccionado);
						params.put("Fase", new PdpFase());
						Window ventanaCargar = (Window) Executions.createComponents("proyectos/edicionFase.zul", null, params);
						ventanaCargar.doModal();
				}
			}
		}
		
	}
	
	@Command
	@NotifyChange({""})
	public void editarAvance(){
		if(getProyectoSeleccionado() == null){
			Messagebox.show("Debe seleccionar un proyecto","Administración",Messagebox.OK,Messagebox.ERROR);
			return;
		}
		
		
		if(proyectoSeleccionado.getPdpTipoAvance().getDescripcion().equals("Trimestral")){
			if(getEtapaSeleccionado() == null){
				Messagebox.show("Debe seleccionar una etapa","Administración",Messagebox.OK,Messagebox.ERROR);
				return;
			}else{
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("Proyecto", proyectoSeleccionado);
				params.put("Etapa", etapaSeleccionado);
				Window ventanaCargar = (Window) Executions.createComponents("proyectos/edicionEtapa.zul", null, params);
				ventanaCargar.doModal();
			}
		}else{
			if(proyectoSeleccionado.getPdpTipoAvance().getDescripcion().equals("Mensual")){
				if(getAvanceSeleccionado() == null){
					Messagebox.show("Debe seleccionar un avance","Administración",Messagebox.OK,Messagebox.ERROR);
					return;
				}else{
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("Proyecto", proyectoSeleccionado);
					params.put("Avance", avanceSeleccionado);
					Window ventanaCargar = (Window) Executions.createComponents("proyectos/edicionAvance.zul", null, params);
					ventanaCargar.doModal();
				}
			}else{
				if(proyectoSeleccionado.getPdpTipoAvance().getDescripcion().equals("Fase")){
					if(getFaseSeleccionado() == null){
						Messagebox.show("Debe seleccionar una fase","Administración",Messagebox.OK,Messagebox.ERROR);
						return;
					}else{
						Map<String, Object> params = new HashMap<String, Object>();
						params.put("Proyecto", proyectoSeleccionado);
						params.put("Fase", faseSeleccionado);
						Window ventanaCargar = (Window) Executions.createComponents("proyectos/edicionFase.zul", null, params);
						ventanaCargar.doModal();
					}
				}
			}
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Command
	@NotifyChange({""})
	public void eliminarAvance(){
		if(getProyectoSeleccionado() == null){
			Messagebox.show("Debe seleccionar un proyecto","Administración",Messagebox.OK,Messagebox.ERROR);
			return;
		}
		
		
		if(proyectoSeleccionado.getPdpTipoAvance().getDescripcion().equals("Trimestral")){
			if(getEtapaSeleccionado() == null){
				Messagebox.show("Debe seleccionar una etapa","Administración",Messagebox.OK,Messagebox.ERROR);
				return;
			}
		}else{
			if(proyectoSeleccionado.getPdpTipoAvance().getDescripcion().equals("Mensual")){
				if(getAvanceSeleccionado() == null){
					Messagebox.show("Debe seleccionar un avance","Administración",Messagebox.OK,Messagebox.ERROR);
					return;
				}
			}else{
				if(proyectoSeleccionado.getPdpTipoAvance().getDescripcion().equals("Fase")){
					if(getFaseSeleccionado() == null){
						Messagebox.show("Debe seleccionar una fase","Administración",Messagebox.OK,Messagebox.ERROR);
						return;
					}
				}
			}
		}
		
		Messagebox.show("Desea eliminar el registro seleccionado? Recuerde que esta acción no puede ser revertida"
				+ " y puede ocasionar un mal funcionamiento de la aplicación", "Confirmación de Eliminación", Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, new EventListener() {
			
			@Override
			public void onEvent(Event event) throws Exception {
				if (event.getName().equals("onYes")) {
					try {
						proyectosDao.getEntityManager().getTransaction().begin();
						
						
						
						if(proyectoSeleccionado.getPdpTipoAvance().getDescripcion().equals("Trimestral")){
							etapaSeleccionado.setEstado("E");	
							etapaSeleccionado = proyectosDao.getEntityManager().merge(etapaSeleccionado);
						}else{
							if(proyectoSeleccionado.getPdpTipoAvance().getDescripcion().equals("Mensual")){
								avanceSeleccionado.setEstado("E");
								avanceSeleccionado = proyectosDao.getEntityManager().merge(avanceSeleccionado);	
							}else{
								if(proyectoSeleccionado.getPdpTipoAvance().getDescripcion().equals("Fase")){
									faseSeleccionado.setEstado("E");
									faseSeleccionado = proyectosDao.getEntityManager().merge(faseSeleccionado);
								}
							}
						}

						proyectosDao.getEntityManager().getTransaction().commit();
						Clients.showNotification("Transaccion ejecutada con exito");
						
						BindUtils.postGlobalCommand(null, null, "ListaProyectos.buscar", null);
						
					} catch (Exception e) {
						e.printStackTrace();
						proyectosDao.getEntityManager().getTransaction().rollback();
					}
				}
			}
		});
	}
	
	
	
	
}
