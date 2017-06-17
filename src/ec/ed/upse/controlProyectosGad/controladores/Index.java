package ec.ed.upse.controlProyectosGad.controladores;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.Selectors;

import ec.ed.upse.controlProyectosGad.modelo.PdpsUsuario;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;

@Log
public class Index {
	

	@Getter @Setter private String formularioActual;
	
	@Getter @Setter private PdpsUsuario usuario_login;

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view){
		Selectors.wireComponents(view, this, false);
		Session session;
		session=Sessions.getCurrent();
		usuario_login = (PdpsUsuario) session.getAttribute("_USUARIO_");
		if(usuario_login == null){
			salir();
		}
		log.info("Inicio de aplicación");
	}
	
	@Command
	@NotifyChange("formularioActual")
	public void seleccionMenu(@BindingParam("direccion") String direccion) {
		if(direccion.equals("usuarios")){
			setFormularioActual("usuarios/listaUsuarios.zul");
		}else if(direccion.equals("proyectos")){
			setFormularioActual("proyectos/listaProyectos.zul");
		}else if(direccion.equals("reportes")){
			setFormularioActual("reportes/listaReportes.zul");
		}else if(direccion.equals("mapa")){
			setFormularioActual("mapa/mapa.zul");
		}
	}
	
	@Command
	public void salir(){
		Session session;
		session=Sessions.getCurrent();
		session.invalidate();
		Executions.getCurrent().sendRedirect("login.zul");
		
		//agregar una clase filtro para que mapee antes de ingresar al index
	}
	
}
