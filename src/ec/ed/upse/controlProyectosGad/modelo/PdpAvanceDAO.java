package ec.ed.upse.controlProyectosGad.modelo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import lombok.extern.java.Log;

@Log
public class PdpAvanceDAO extends ClaseDAO{
	
	@SuppressWarnings("unchecked")
	public List<PdpAvance> buscarAvancePorProyecto(int proyecto){
		List<PdpAvance> retorno = new ArrayList<PdpAvance>();
		try {
			Query query = getEntityManager().createNamedQuery("PdpAvance.buscarPorProyecto");
					query.setHint("javax.persistence.cache.storeMode", "REFRESH");
					query.setParameter("proyecto", proyecto);
			retorno = (List<PdpAvance>) query.getResultList();
			log.info("El resultado es: " + retorno);
			return retorno;
		} catch (Exception e) {
			e.printStackTrace();
			return retorno;
		}
	}

}
