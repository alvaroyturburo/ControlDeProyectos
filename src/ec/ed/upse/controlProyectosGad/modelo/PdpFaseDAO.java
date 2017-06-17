package ec.ed.upse.controlProyectosGad.modelo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import lombok.extern.java.Log;

@Log
public class PdpFaseDAO extends ClaseDAO{
	@SuppressWarnings("unchecked")
	public List<PdpFase> buscarFasePorProyecto(int proyecto){
		List<PdpFase> retorno = new ArrayList<PdpFase>();
		try {
			Query query = getEntityManager().createNamedQuery("PdpFase.buscarPorProyecto");
					query.setHint("javax.persistence.cache.storeMode", "REFRESH");
					query.setParameter("proyecto", proyecto);
			retorno = (List<PdpFase>) query.getResultList();
			log.info("El resultado es: " + retorno);
			return retorno;
		} catch (Exception e) {
			e.printStackTrace();
			return retorno;
		}
	}

}
