package ec.ed.upse.controlProyectosGad.controladores;

import java.util.List;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.gmaps.Gmaps;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Button;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import ec.ed.upse.controlProyectosGad.modelo.PdpEstadoProyecto;
import ec.ed.upse.controlProyectosGad.modelo.PdpEstadoProyectoDAO;
import ec.ed.upse.controlProyectosGad.modelo.PdpModalidad;
import ec.ed.upse.controlProyectosGad.modelo.PdpModalidadDAO;
import ec.ed.upse.controlProyectosGad.modelo.PdpProyecto;
import ec.ed.upse.controlProyectosGad.modelo.PdpProyectoDAO;
import ec.ed.upse.controlProyectosGad.modelo.PdpTipoAvance;
import ec.ed.upse.controlProyectosGad.modelo.PdpTipoAvanceDAO;
import ec.ed.upse.controlProyectosGad.modelo.PdpsUsuario;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;

@Log
public class EdicionProyecto {

	@Wire private Window winProyecto;
	@Wire private Button buttonGrabar;
	@Wire private Datebox dtbFechaInicio, dtbFechaFin;
	@Wire private Groupbox grupoBoxFinan;
	@Wire private Listbox lisBoxFinan;
	@Wire private Hlayout hLayoutFinan;
	@Wire private Radio radioNuevo, radioArrastre;
	@Wire private Textbox txtArrastre;
	@Wire private Gmaps gmaps;
	private int id_validador;
	@Wire private Label lblNota;
	
	@Getter @Setter private PdpProyecto proyecto;
	private PdpProyectoDAO proyectoDao = new PdpProyectoDAO();
	private PdpEstadoProyectoDAO estadoProyectoDao = new PdpEstadoProyectoDAO();
	private PdpModalidadDAO modalidadDao = new PdpModalidadDAO();
	private PdpTipoAvanceDAO tipoAvanceDao = new PdpTipoAvanceDAO();
		
	@Getter @Setter private PdpsUsuario usuario_login;
	
	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view){
		Selectors.wireComponents(view, this, false);
		Session session;
		session=Sessions.getCurrent();
		usuario_login = (PdpsUsuario) session.getAttribute("_USUARIO_");
		proyecto = (PdpProyecto) Executions.getCurrent().getArg().get("Proyecto");
		id_validador = proyecto.getId();
		
		if(id_validador==0){
			grupoBoxFinan.setVisible(false);
			lisBoxFinan.setVisible(false);
			hLayoutFinan.setVisible(false);
			lblNota.setVisible(true);
		}else{
			grupoBoxFinan.setVisible(true);
			lisBoxFinan.setVisible(true);
			hLayoutFinan.setVisible(true);
			lblNota.setVisible(false);
		}
				
		log.info("Edicion de proyectos");
	}
	
		
	
	public void guardarProyecto(){
		proyecto.setEstado("A");
		
		try {
			proyectoDao.getEntityManager().getTransaction().begin();

			if(proyecto.getId() == 0){
				proyectoDao.getEntityManager().persist(proyecto);
			}else{
				proyecto = (PdpProyecto) proyectoDao.getEntityManager().merge(proyecto);
			}
			
			proyectoDao.getEntityManager().getTransaction().commit();
			Clients.showNotification("Proyecto registrado!");
			BindUtils.postGlobalCommand(null, null, "ListaProyectos.buscar", null);
			salir();
		} catch (Exception e) {
			e.printStackTrace();
			proyectoDao.getEntityManager().getTransaction().rollback();
			Clients.showNotification("Error en la ejecución", "error", buttonGrabar, "end_center",3);
		}
	}
		
	@Command
	public void guardar(){
		guardarProyecto();
	}
	
	@Command
	public void crearpunto(){
		Clients.showNotification("Crear punto lat:"+gmaps.getLat()+" log:"+gmaps.getLng());
	}
	
	
	@Command
	public void salir(){
		winProyecto.detach();
	}
	
	public List<PdpEstadoProyecto> getEstadoproyect(){
		return estadoProyectoDao.mostrarEstadoProyectos();
	}
	
	public List<PdpModalidad> getModalidadproyect(){
		return modalidadDao.mostrarModalidades();
	}
	
	public List<PdpTipoAvance> getTipoAvanceproyect(){
		return tipoAvanceDao.mostrarTiposAvances();
	}
}
