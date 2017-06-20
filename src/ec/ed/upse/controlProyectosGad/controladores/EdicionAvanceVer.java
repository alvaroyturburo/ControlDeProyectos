package ec.ed.upse.controlProyectosGad.controladores;

import java.text.SimpleDateFormat;
import java.util.Date;

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
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import ec.ed.upse.controlProyectosGad.modelo.PdpAvance;
import ec.ed.upse.controlProyectosGad.modelo.PdpAvanceDAO;
import ec.ed.upse.controlProyectosGad.modelo.PdpEtapa;
import ec.ed.upse.controlProyectosGad.modelo.PdpProyecto;
import ec.ed.upse.controlProyectosGad.modelo.PdpProyectoDAO;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;

@Log
public class EdicionAvanceVer {
	
	SimpleDateFormat sdfDate;
	
	@Wire private Window winAvance;
	//@Wire private Toolbarbutton tolGrabar;
	@Wire private Textbox txtRegistro;
	
	@Getter @Setter private PdpAvance avance;
	@Getter @Setter private PdpProyecto proyecto;
	@Getter @Setter private String strDate;
	protected PdpAvanceDAO avanceDao = new PdpAvanceDAO();
	protected PdpProyectoDAO proyectoDAo = new PdpProyectoDAO();
	
	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view){
		Selectors.wireComponents(view, this, false);
		Session session;
		session=Sessions.getCurrent();
		//usuario_login = (PdpsUsuario) session.getAttribute("_USUARIO_");
		proyecto = (PdpProyecto) Executions.getCurrent().getArg().get("Proyecto");
		avance = (PdpAvance) Executions.getCurrent().getArg().get("Avance");
						
		log.info("Edicion de proyectos por etapas");
		
		
		if(avance.getId()>0){
			txtRegistro.setText(avance.getFechaRegistro());
		}else{
		sdfDate = new SimpleDateFormat("yyyy-MM-dd");//dd/MM/yyyy
 	    Date now = new Date();
 	    strDate = sdfDate.format(now);
 	    txtRegistro.setText(strDate);
 	    }
	}
	
	@Command
	public void salir(){
		winAvance.detach();
	}


}
