package ec.ed.upse.controlProyectosGad.modelo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import lombok.extern.java.Log;

@Log
public class ThFuncionarioDAO extends ClaseDAO{
	
	@SuppressWarnings("unchecked")
	public List<ThFuncionario> mostrarFuncionarios(){
		List<ThFuncionario> retorno = new ArrayList<ThFuncionario>();
		try {
			Query query = getEntityManager().createNamedQuery("ThFuncionario.findAll");
					query.setHint("javax.persistence.cache.storeMode", "REFRESH");
			retorno = (List<ThFuncionario>) query.getResultList();
			log.info("El resultado es: " + retorno);
			return retorno;
		} catch (Exception e) {
			e.printStackTrace();
			return retorno;
		}
	}

}
