package ec.ed.upse.controlProyectosGad.modelo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import lombok.extern.java.Log;

@Log
public class PdpEstadoProyectoDAO extends ClaseDAO{
	
	@SuppressWarnings("unchecked")
	public List<PdpEstadoProyecto> mostrarEstadoProyectos(){
		List<PdpEstadoProyecto> retorno = new ArrayList<PdpEstadoProyecto>();
		try {
			Query query = getEntityManager().createNamedQuery("PdpEstadoProyecto.findAll");
					query.setHint("javax.persistence.cache.storeMode", "REFRESH");
			retorno = (List<PdpEstadoProyecto>) query.getResultList();
			log.info("El resultado es: " + retorno);
			return retorno;
		} catch (Exception e) {
			e.printStackTrace();
			return retorno;
		}
	}

}
