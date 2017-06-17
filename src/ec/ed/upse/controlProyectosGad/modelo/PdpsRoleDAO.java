package ec.ed.upse.controlProyectosGad.modelo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import lombok.extern.java.Log;

@Log
public class PdpsRoleDAO extends ClaseDAO{
	
	@SuppressWarnings("unchecked")
	public List<PdpsRole> mostrarRoles(){
		List<PdpsRole> retorno = new ArrayList<PdpsRole>();
		try {
			Query query = getEntityManager().createNamedQuery("PdpsRole.findAll");
					query.setHint("javax.persistence.cache.storeMode", "REFRESH");
			retorno = (List<PdpsRole>) query.getResultList();
			log.info("El resultado es: " + retorno);
			return retorno;
		} catch (Exception e) {
			e.printStackTrace();
			return retorno;
		}
	}

}
