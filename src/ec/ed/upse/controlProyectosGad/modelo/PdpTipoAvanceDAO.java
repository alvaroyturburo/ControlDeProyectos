package ec.ed.upse.controlProyectosGad.modelo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import lombok.extern.java.Log;
@Log
public class PdpTipoAvanceDAO extends ClaseDAO{
	
	@SuppressWarnings("unchecked")
	public List<PdpTipoAvance> mostrarTiposAvances(){
		List<PdpTipoAvance> retorno = new ArrayList<PdpTipoAvance>();
		try {
			Query query = getEntityManager().createNamedQuery("PdpTipoAvance.findAll");
					query.setHint("javax.persistence.cache.storeMode", "REFRESH");
			retorno = (List<PdpTipoAvance>) query.getResultList();
			log.info("El resultado es: " + retorno);
			return retorno;
		} catch (Exception e) {
			e.printStackTrace();
			return retorno;
		}
	}

}
