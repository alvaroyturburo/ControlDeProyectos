package ec.ed.upse.controlProyectosGad.modelo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import lombok.extern.java.Log;


@Log
public class PdpProyectoDAO extends ClaseDAO{
	
	@SuppressWarnings("unchecked")
	public List<PdpProyecto> buscarProyectoPorNombreFechas(String nombre, String fInicio, String fFin){
		List<PdpProyecto> retorno = new ArrayList<PdpProyecto>();
		String patron = nombre;
		String fechaInicio = fInicio;
		String fechaFin = fFin;

		if (nombre == null || nombre.length() == 0) {
			patron = "%";
		}else{
			patron = "%" + patron + "%";
		}
		try {
			Query query = getEntityManager().createNamedQuery("PdpProyecto.buscarPorNombre");
					query.setHint("javax.persistence.cache.storeMode", "REFRESH");
					query.setParameter("nombre", patron);
					query.setParameter("inicio", fechaInicio);
					query.setParameter("fin", fechaFin);
			retorno = (List<PdpProyecto>) query.getResultList();
			log.info("El resultado es: " + retorno);
			return retorno;
		} catch (Exception e) {
			e.printStackTrace();
			return retorno;
		}
	}
	
	public List<PdpProyecto> buscarProyectoPorModalidadEstado(String modalidad, String estado){
		List<PdpProyecto> retorno = new ArrayList<PdpProyecto>();
		String patron1 = modalidad;
		String patron2 = estado;

		if (modalidad == null || modalidad.length() == 0) {
			modalidad = "%";
		}else{
			modalidad = "%" + modalidad + "%";
		}
		if (estado == null || estado.length() == 0) {
			estado = "%";
		}else{
			estado = "%" + estado + "%";
		}
		try {
			Query query = getEntityManager().createNamedQuery("PdpProyecto.buscarPorModalidadEstado");
					query.setHint("javax.persistence.cache.storeMode", "REFRESH");
					//query.setParameter("modalidad", patron1);
					//query.setParameter("estado", patron2);
			retorno = (List<PdpProyecto>) query.getResultList();
			log.info("El resultado es: " + retorno);
			return retorno;
		} catch (Exception e) {
			e.printStackTrace();
			return retorno;
		}
	}

}
