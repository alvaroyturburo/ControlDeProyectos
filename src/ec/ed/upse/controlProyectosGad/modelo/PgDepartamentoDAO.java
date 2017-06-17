package ec.ed.upse.controlProyectosGad.modelo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import lombok.extern.java.Log;

@Log
public class PgDepartamentoDAO extends ClaseDAO{
	
	@SuppressWarnings("unchecked")
	public List<PgDepartamento> mostrarDepartamentos(){
		List<PgDepartamento> retorno = new ArrayList<PgDepartamento>();
		try {
			Query query = getEntityManager().createNamedQuery("PgDepartamento.findAll");
					query.setHint("javax.persistence.cache.storeMode", "REFRESH");
			retorno = (List<PgDepartamento>) query.getResultList();
			log.info("El resultado es: " + retorno);
			return retorno;
		} catch (Exception e) {
			e.printStackTrace();
			return retorno;
		}
	}

}
