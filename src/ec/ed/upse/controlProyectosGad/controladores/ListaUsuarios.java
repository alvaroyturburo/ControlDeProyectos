package ec.ed.upse.controlProyectosGad.controladores;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.AfterCompose;
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
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import ec.ed.upse.controlProyectosGad.modelo.PdpsRole;
import ec.ed.upse.controlProyectosGad.modelo.PdpsUsuario;
import ec.ed.upse.controlProyectosGad.modelo.PdpsUsuarioDAO;
import ec.ed.upse.controlProyectosGad.modelo.PgPersona;
import ec.ed.upse.controlProyectosGad.modelo.ThFuncionario;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;


@Log
public class ListaUsuarios {
	
	@Getter @Setter private String textoBuscar;
	@Getter @Setter private PdpsUsuario usuarioSeleccionado;
	@Getter @Setter private List<PdpsUsuario> usuarios;
	private PdpsUsuarioDAO usuariosDao = new PdpsUsuarioDAO();

	
	@Getter @Setter private PdpsUsuario usuario_login;
	
	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view){
		Selectors.wireComponents(view, this, false);
		Session session;
		session=Sessions.getCurrent();
		usuario_login = (PdpsUsuario) session.getAttribute("_USUARIO_");
		BindUtils.postGlobalCommand(null, null, "ListaUsuarios.buscar", null);
		log.info("Administración de Usuarios");
	}
	
	@GlobalCommand("ListaUsuarios.buscar")
	@Command
	@NotifyChange({"usuarios", "usuarioSeleccionado"})
	public void buscar(){
		if (usuarios != null) {
			usuarios = null; 
		}
		usuarios = usuariosDao.buscarUsuarioPorNombre(textoBuscar);
		
		usuarioSeleccionado = null; 
		/*PdpsUsuario u = usuariosDao.getEntityManager().find(PdpsUsuario.class, 1);
		Clients.showNotification("a " + u.getPdpsRole().getDescripcion() + u.getThFuncionario().getPgDepartamento().getDescripcion()
				+ u.getThFuncionario().getPgPersona().getNombres());*/

	}
	
	@Command
	@NotifyChange({""})
	public void nuevoUsuario(){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("Usuario", new PdpsUsuario());
		params.put("Persona", new PgPersona());
		params.put("Funcionario", new ThFuncionario());
		params.put("Rol", new PdpsRole());
		Window ventanaCargar = (Window) Executions.createComponents("usuarios/edicionUsuario.zul", null, params);
		ventanaCargar.doModal();
	}
	
	@Command
	@NotifyChange({""})
	public void editarUsuario(){
		if(getUsuarioSeleccionado() == null){
			Messagebox.show("Debe seleccionar un Usuario","Administración",Messagebox.OK,Messagebox.ERROR);
			return;
		}
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("Usuario", usuarioSeleccionado);
		params.put("Persona", usuarioSeleccionado.getThFuncionario().getPgPersona());
		params.put("Funcionario", usuarioSeleccionado.getThFuncionario());
		params.put("Rol", usuarioSeleccionado.getPdpsRole());
		Window ventanaCargar = (Window) Executions.createComponents("usuarios/edicionUsuario.zul", null, params);
		ventanaCargar.doModal();
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Command
	@NotifyChange({""})
	public void eliminarUsuario(){
		if(getUsuarioSeleccionado() == null){
			Messagebox.show("Debe seleccionar un usuario","Administración",Messagebox.OK,Messagebox.ERROR);
			return;
		}
		
		Messagebox.show("Desea eliminar el registro seleccionado? Recuerde que esta acción no puede ser revertida y"
				+ "puede ocasionar un mal funcionamiento de la aplicación", "Confirmación de Eliminación", Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, new EventListener() {
			
			@Override
			public void onEvent(Event event) throws Exception {
				if (event.getName().equals("onYes")) {
					
					try {
						usuariosDao.getEntityManager().getTransaction().begin();
						usuarioSeleccionado.setEstado("I");
						usuarioSeleccionado.getThFuncionario().setEstado("I");
						usuarioSeleccionado.getThFuncionario().getPgPersona().setEstado("I");
						usuarioSeleccionado = usuariosDao.getEntityManager().merge(usuarioSeleccionado);
						usuariosDao.getEntityManager().getTransaction().commit();	
						Clients.showNotification("Transaccion ejecutada con exito");

						BindUtils.postGlobalCommand(null, null, "ListaUsuarios.buscar", null);
					} catch (Exception e) {
						e.printStackTrace();
						usuariosDao.getEntityManager().getTransaction().rollback();
					}	
					
					/*try {
						circuitoDao.getEntityManager().getTransaction().begin();
						circuitoSeleccionado.setEstado("E");
						if(!circuitoSeleccionado.getCampeonatos().isEmpty()){
							for (Campeonato campeonato : circuitoSeleccionado.getCampeonatos()){
								campeonato.setEstado("E");
							}
						}
						circuitoSeleccionado = circuitoDao.getEntityManager().merge(circuitoSeleccionado);
						circuitoDao.getEntityManager().getTransaction().commit();
						Clients.showNotification("Transaccion ejecutada con exito");
						
						BindUtils.postGlobalCommand(null, null, "ListaCircuitos.buscar", null);
						
					} catch (Exception e) {
						e.printStackTrace();
						circuitoDao.getEntityManager().getTransaction().rollback();
					}*/
				}
			}
		});
	}
	
}
