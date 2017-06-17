package ec.ed.upse.controlProyectosGad.controladores;


import java.util.Map;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import ec.ed.upse.controlProyectosGad.modelo.PdpEtapa;
import ec.ed.upse.controlProyectosGad.modelo.PdpEtapaDAO;
import ec.ed.upse.controlProyectosGad.modelo.PdpProyecto;
import ec.ed.upse.controlProyectosGad.modelo.PdpProyectoDAO;
import ec.ed.upse.controlProyectosGad.modelo.PdpsUsuario;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;

@Log
public class EdicionEtapa {
	
	@Wire private Window winEtapa;
	@Wire private Toolbarbutton tolGrabar;
	
	@Getter @Setter private PdpEtapa etapa;
	@Getter @Setter private PdpProyecto proyecto;
	protected PdpEtapaDAO etapaDao = new PdpEtapaDAO();
	protected PdpProyectoDAO proyectoDAo = new PdpProyectoDAO();
	
	
	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view){
		Selectors.wireComponents(view, this, false);
		Session session;
		session=Sessions.getCurrent();
		//usuario_login = (PdpsUsuario) session.getAttribute("_USUARIO_");
		proyecto = (PdpProyecto) Executions.getCurrent().getArg().get("Proyecto");
		etapa = (PdpEtapa) Executions.getCurrent().getArg().get("Etapa");
						
		log.info("Edicion de proyectos por etapas");
	}
	
	@Command
	public void salir(){
		winEtapa.detach();
	}
	
	public void guardarProyecto(){
		etapa.setPdpProyecto(proyecto);
		etapa.setEstado("A");
		
		try {
			etapaDao.getEntityManager().getTransaction().begin();

			if(etapa.getId() == 0){
				etapaDao.getEntityManager().persist(etapa);
			}else{
				etapa = (PdpEtapa) etapaDao.getEntityManager().merge(etapa);
			}
			
			etapaDao.getEntityManager().getTransaction().commit();
			Clients.showNotification("Etapa registrado!");
			BindUtils.postGlobalCommand(null, null, "ListaProyectos.buscar", null);
			salir();
		} catch (Exception e) {
			e.printStackTrace();
			etapaDao.getEntityManager().getTransaction().rollback();
			Clients.showNotification("Error en la ejecución", "error", tolGrabar, "end_center",3);
		}
	}
		
	@Command
	public void guardar(){
		guardarProyecto();
	}

}
