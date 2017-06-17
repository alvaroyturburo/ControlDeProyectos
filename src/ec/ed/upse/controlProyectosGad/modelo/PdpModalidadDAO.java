package ec.ed.upse.controlProyectosGad.modelo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import lombok.extern.java.Log;
@Log
public class PdpModalidadDAO extends ClaseDAO{
	
	@SuppressWarnings("unchecked")
	public List<PdpModalidad> mostrarModalidades(){
		List<PdpModalidad> retorno = new ArrayList<PdpModalidad>();
		try {
			Query query = getEntityManager().createNamedQuery("PdpModalidad.findAll");
					query.setHint("javax.persistence.cache.storeMode", "REFRESH");
			retorno = (List<PdpModalidad>) query.getResultList();
			log.info("El resultado es: " + retorno);
			return retorno;
		} catch (Exception e) {
			e.printStackTrace();
			return retorno;
		}
	}

}
