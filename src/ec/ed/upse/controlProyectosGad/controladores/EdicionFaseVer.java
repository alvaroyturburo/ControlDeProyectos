package ec.ed.upse.controlProyectosGad.controladores;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;


import ec.ed.upse.controlProyectosGad.modelo.PdpFase;
import ec.ed.upse.controlProyectosGad.modelo.PdpFaseDAO;
import ec.ed.upse.controlProyectosGad.modelo.PdpProducto;
import ec.ed.upse.controlProyectosGad.modelo.PdpProductoDAO;
import ec.ed.upse.controlProyectosGad.modelo.PdpProyecto;
import ec.ed.upse.controlProyectosGad.modelo.PdpProyectoDAO;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;

@Log
public class EdicionFaseVer {

	@Wire private Window winFase;
	@Wire private Toolbarbutton tolGrabar;
	@Wire private Textbox txtFase;
	
	@Getter @Setter private PdpFase fase;
	@Getter @Setter private List<PdpFase> listaFase; 
	@Getter @Setter private PdpProyecto proyecto;
	@Getter @Setter private List<PdpProducto> productos;
	@Getter @Setter private PdpProducto producto;
	protected PdpFaseDAO faseDao = new PdpFaseDAO();
	protected PdpProyectoDAO proyectoDAo = new PdpProyectoDAO();
	protected PdpProductoDAO productoDAo = new PdpProductoDAO();
	protected Integer CantidadFase;
	
	
	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view){
		Selectors.wireComponents(view, this, false);
		Session session;
		session=Sessions.getCurrent();
		//usuario_login = (PdpsUsuario) session.getAttribute("_USUARIO_");
		proyecto = (PdpProyecto) Executions.getCurrent().getArg().get("Proyecto");
		fase = (PdpFase) Executions.getCurrent().getArg().get("Fase");
		
		if(fase.getId()==0){
			System.out.println("Id de editar: "+fase.getId());
			listaFase = faseDao.buscarFasePorProyecto(proyecto.getId());
			System.out.println("Cantidad: "+listaFase.size());
			if (listaFase.size()>0){
				CantidadFase = listaFase.size() + 1;
				txtFase.setText(""+ CantidadFase);
			}else{
				txtFase.setText(""+ 1);	
			}
		 }else{
			 System.out.println("Id de editar: "+fase.getId());
				txtFase.setText(fase.getNFase());
			    /*listProducto = daoProducto.BuscarPorIdFase(fase.getId());
			    for(int i=0;i<listProducto.size();i++){
			    listaProducto.add(new Producto(listProducto.get(i).getDescripcion()));}
			    btnAgregarProducto.setDisabled(true);
			    btnQuitarProducto.setDisabled(true);*/
		}
						
		log.info("Edicion de proyectos por etapas");
	}
	
	@Command
	public void salir(){
		winFase.detach();
	}
}
