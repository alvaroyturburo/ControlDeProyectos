package ec.ed.upse.controlProyectosGad.controladores;

import java.util.Date;
import java.util.List;

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
import org.zkoss.zul.Button;
import org.zkoss.zul.Constraint;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Window;

import ec.ed.upse.controlProyectosGad.modelo.PdpsRole;
import ec.ed.upse.controlProyectosGad.modelo.PdpsRoleDAO;
import ec.ed.upse.controlProyectosGad.modelo.PdpsUsuario;
import ec.ed.upse.controlProyectosGad.modelo.PdpsUsuarioDAO;
import ec.ed.upse.controlProyectosGad.modelo.PgDepartamento;
import ec.ed.upse.controlProyectosGad.modelo.PgDepartamentoDAO;
import ec.ed.upse.controlProyectosGad.modelo.PgPersona;
import ec.ed.upse.controlProyectosGad.modelo.PgPersonaDAO;
import ec.ed.upse.controlProyectosGad.modelo.ThFuncionario;
import ec.ed.upse.controlProyectosGad.modelo.ThFuncionarioDAO;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;

@Log
public class EdicionUsuario {

	@Wire private Window winUsuario;
	@Wire private Button buttonGrabar;
	@Wire private Datebox dtbFechaVigencia;
	
	@Getter @Setter private PdpsUsuario usuario;
	@Getter @Setter private PgPersona persona;
	@Getter @Setter private ThFuncionario funcionario;
	@Getter @Setter private PdpsRole role;
	private PdpsUsuarioDAO usuarioDao = new PdpsUsuarioDAO();
	private PgPersonaDAO personaDao = new PgPersonaDAO();
	private PgDepartamentoDAO departamentoDao = new PgDepartamentoDAO();
	private PdpsRoleDAO roleDao = new PdpsRoleDAO();
	private ThFuncionarioDAO funcionarioDao = new ThFuncionarioDAO();
	
	@Getter @Setter private PdpsUsuario usuario_login;
	
	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view){
		Selectors.wireComponents(view, this, false);
		Session session;
		session=Sessions.getCurrent();
		usuario_login = (PdpsUsuario) session.getAttribute("_USUARIO_");
		usuario = (PdpsUsuario) Executions.getCurrent().getArg().get("Usuario");
		persona = (PgPersona) Executions.getCurrent().getArg().get("Persona");
		funcionario = (ThFuncionario) Executions.getCurrent().getArg().get("Funcionario");
		role = (PdpsRole) Executions.getCurrent().getArg().get("Rol");
		log.info("Edicion de usuarios");
	}
	
	public Constraint getValidaFecha() {
		return new Constraint() {
			
			@Override
			public void validate(Component comp, Object value){
				Date valor = (Date) value;
				
				if(valor == null){
					Clients.showNotification("Debe Seleccionar la Fecha de Nacimiento", "error", dtbFechaVigencia, "end_center",2000,true);
					funcionario.setVigenciaDesde(null);
					return;
				}
				
				if (valor.after(new Date())) {
					Clients.showNotification("La fecha no debe ser posterior a la fecha actual", "error", dtbFechaVigencia, "end_center",2000,true);
					funcionario.setVigenciaDesde(null);
					return;
				}
			}
		};		
	}
	
	
	public void guardarUsuario(){
					
		if(persona.getNombres() == null || persona.getApellido1() == null || persona.getApellido2() == null
				|| usuario.getAlias() == null || usuario.getContrasena() == null){
			Clients.showNotification("Faltan campos", "error", buttonGrabar, "end_center",1000);
			return;
		}
		
		usuario.setEstado("A");
		persona.setEstado("A");
		funcionario.setEstado("A");
		role.setEstado("A");
		
		//crear una persona
		try {
			personaDao.getEntityManager().getTransaction().begin();
			
			if(persona.getId() == 0){
				personaDao.getEntityManager().persist(persona);
			}else{
				persona = (PgPersona) personaDao.getEntityManager().merge(persona);
			}
			
			personaDao.getEntityManager().getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			personaDao.getEntityManager().getTransaction().rollback();
		}
		funcionario.setPgPersona(persona);
		
		
		//Crear un funcionario
		try {
			funcionarioDao.getEntityManager().getTransaction().begin();
			
			if(funcionario.getId() == 0){
				funcionarioDao.getEntityManager().persist(funcionario);
			}else{
				funcionario = (ThFuncionario) funcionarioDao.getEntityManager().merge(funcionario);
			}
			
			funcionarioDao.getEntityManager().getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			funcionarioDao.getEntityManager().getTransaction().rollback();
		}
		usuario.setThFuncionario(funcionario);
		
		//Crear un rol
		try {
			roleDao.getEntityManager().getTransaction().begin();

			if(role.getId() == 0){
				roleDao.getEntityManager().persist(role);
			}else{
				role = (PdpsRole) roleDao.getEntityManager().merge(role);
			}

			roleDao.getEntityManager().getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			roleDao.getEntityManager().getTransaction().rollback();
		}
		usuario.setPdpsRole(role);

		//Crear un usuario
		try {
			usuarioDao.getEntityManager().getTransaction().begin();

			if(usuario.getId() == 0){
				usuarioDao.getEntityManager().persist(usuario);
			}else{
				usuario = (PdpsUsuario) usuarioDao.getEntityManager().merge(usuario);
			}
			
			usuarioDao.getEntityManager().getTransaction().commit();
			Clients.showNotification("Usuario registrado!");
			BindUtils.postGlobalCommand(null, null, "ListaUsuarios.buscar", null);
			salir();
		} catch (Exception e) {
			e.printStackTrace();
			usuarioDao.getEntityManager().getTransaction().rollback();
			Clients.showNotification("Error en la ejecución", "error", buttonGrabar, "end_center",3);
		}
		
	}
		
	@Command
	public void guardar(){
		guardarUsuario();
	}
	
	public List<PgDepartamento> getDepartamentos(){
		return departamentoDao.mostrarDepartamentos();
	}
	
	public List<PdpsRole> getRoles(){		
		return roleDao.mostrarRoles();
	}
	
	@Command
	public void salir(){
		winUsuario.detach();
	}
}
