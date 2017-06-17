package ec.ed.upse.controlProyectosGad.controladores;

import java.util.List;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.gmaps.Gmaps;
import org.zkoss.gmaps.Gmarker;
import org.zkoss.gmaps.LatLng;
import org.zkoss.gmaps.event.MapMouseEvent;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;

import ec.ed.upse.controlProyectosGad.modelo.PdpEstadoProyecto;
import ec.ed.upse.controlProyectosGad.modelo.PdpEstadoProyectoDAO;
import ec.ed.upse.controlProyectosGad.modelo.PdpModalidad;
import ec.ed.upse.controlProyectosGad.modelo.PdpModalidadDAO;
import ec.ed.upse.controlProyectosGad.modelo.PdpProyecto;
import ec.ed.upse.controlProyectosGad.modelo.PdpProyectoDAO;
import ec.ed.upse.controlProyectosGad.modelo.PdpsUsuario;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;

@Log
public class Mapa {
	@Wire private Gmaps gmaps;
	protected Gmarker gMarker, gmark;
	protected LatLng latLng;
	protected Double Lat, Lng, MontoInversion;
	
	@Getter @Setter private PdpProyecto proyectoSeleccionado;
	@Getter @Setter private List<PdpProyecto> proyectos;
	private PdpProyectoDAO proyectosDao = new PdpProyectoDAO();
	private PdpEstadoProyectoDAO estadoProyectoDao = new PdpEstadoProyectoDAO();
	private PdpModalidadDAO modalidadDao = new PdpModalidadDAO();
	
	@Getter @Setter private PdpsUsuario usuario_login;
	
	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view){
		Selectors.wireComponents(view, this, false);
		Session session;
		session=Sessions.getCurrent();
		usuario_login = (PdpsUsuario) session.getAttribute("_USUARIO_");
		BindUtils.postGlobalCommand(null, null, "ListaProyectos.buscar", null);
		log.info("Administración de Usuarios");
	}
	
	@GlobalCommand("ListaProyectos.buscar")
	@Command
	@NotifyChange({"proyectos", "proyectoSeleccionado"})
	public void buscar(){
		if (proyectos != null) {
			proyectos = null; 
		}
		proyectos = proyectosDao.buscarProyectoPorModalidadEstado("","");
		for(int i=0;i<proyectos.size();i++){
			 
			 Lat =Double.valueOf(proyectos.get(i).getLatitud());
			 Lng = Double.valueOf(proyectos.get(i).getLongitud());
			 gMarker = new Gmarker(proyectos.get(i).getDescripcion(), Lat, Lng); 
			 gMarker.setParent(gmaps);
			 gMarker.setOpen(false);
			 gMarker.setId(proyectos.get(i).getId()+"");
			 gMarker.setDraggingEnabled(false);
			 
			 latLng = new LatLng(Lat, Lng);
			 gmaps.panTo(latLng.getLatitude(), latLng.getLongitude());	 
		}
		proyectoSeleccionado = null; 
	}
	
	public List<PdpEstadoProyecto> getEstadoproyect(){
		return estadoProyectoDao.mostrarEstadoProyectos();
	}
	
	public List<PdpModalidad> getModalidadproyect(){
		return modalidadDao.mostrarModalidades();
	}
	
	public void onMapClick$gmaps(MapMouseEvent event){
		gmark = (Gmarker)event.getGmarker();
		if(gmark!=null){
			System.out.println("Crear punto lat:"+gMarker.getLat()+" log:"+gMarker.getLng());
			//Clients.showNotification("Crear punto lat:"+gMarker.getLat()+" log:"+gMarker.getLng());
		    /*gmark.setOpen(false);
		    gmark.getContent();
			popup.open(gMarker);
			popup.setHeight("325px");
		    gmark.setContent(gmark.getContent());
		    PdpProyecto Markerproyecto = new PdpProyecto();
		    Markerproyecto = daoProyecto.BuscarIDMarker(gmark.getId());
		    valParam = daoValParam.buscaValor("PATH_ARCHIVO_DIGITAL");
		    try {
		    	tbbtnMasInformacion.setLabel("+ Informacion");
				MostrarImagen(valParam,Markerproyecto);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        gmark.setOpen(true);*/
		}
	}

}
