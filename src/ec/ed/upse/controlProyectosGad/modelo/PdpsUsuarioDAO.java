package ec.ed.upse.controlProyectosGad.modelo;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;



import lombok.extern.java.Log;

@Log
public class PdpsUsuarioDAO extends ClaseDAO{
	
	public PdpsUsuario login(String alias, String contrasena){
		PdpsUsuario r_usuario = null;		
		try {
			Query query = getEntityManager().createNamedQuery("PdpsUsuario.login")
					.setParameter("alias", alias)
					.setParameter("contrasena", contrasena);
			r_usuario = (PdpsUsuario) query.getSingleResult();
			log.info("El resultado es: " + r_usuario);
			return r_usuario;
		} catch (Exception e) {
			e.printStackTrace();
			return r_usuario;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<PdpsUsuario> buscarUsuarioPorNombre(String nombre){
		List<PdpsUsuario> retorno = new ArrayList<PdpsUsuario>();
		String patron = nombre;

		if (nombre == null || nombre.length() == 0) {
			patron = "%";
		}else{
			patron = "%" + patron + "%";
		}
		try {
			Query query = getEntityManager().createNamedQuery("PdpsUsuario.buscarPorNombre");
					query.setHint("javax.persistence.cache.storeMode", "REFRESH");
					query.setParameter("nombre", patron);
			retorno = (List<PdpsUsuario>) query.getResultList();
			log.info("El resultado es: " + retorno);
			return retorno;
		} catch (Exception e) {
			e.printStackTrace();
			return retorno;
		}
	}

}
