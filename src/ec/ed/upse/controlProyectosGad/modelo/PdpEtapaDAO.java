package ec.ed.upse.controlProyectosGad.modelo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import lombok.extern.java.Log;

@Log
public class PdpEtapaDAO extends ClaseDAO{

	@SuppressWarnings("unchecked")
	public List<PdpEtapa> buscarEtapaPorProyecto(int proyecto){
		List<PdpEtapa> retorno = new ArrayList<PdpEtapa>();
		try {
			Query query = getEntityManager().createNamedQuery("PdpEtapa.buscarPorProyecto");
					query.setHint("javax.persistence.cache.storeMode", "REFRESH");
					query.setParameter("proyecto", proyecto);
			retorno = (List<PdpEtapa>) query.getResultList();
			log.info("El resultado es: " + retorno);
			return retorno;
		} catch (Exception e) {
			e.printStackTrace();
			return retorno;
		}
	}
}
