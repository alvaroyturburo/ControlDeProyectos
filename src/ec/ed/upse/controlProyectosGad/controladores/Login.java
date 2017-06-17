package ec.ed.upse.controlProyectosGad.controladores;

/*import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;*/

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Textbox;

import ec.ed.upse.controlProyectosGad.modelo.PdpsUsuario;
import ec.ed.upse.controlProyectosGad.modelo.PdpsUsuarioDAO;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;

@Log
public class Login {

	//@Wire private Textbox txtUsuario;
	//@Wire private Textbox txtClave;
	
	@Getter @Setter private String alias;
	@Getter @Setter private String contrasena;
	@Getter @Setter private String mensaje;

	
	private PdpsUsuarioDAO usuarioDao = new PdpsUsuarioDAO();
	private PdpsUsuario usuarioLogin;
	
	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view){
		Selectors.wireComponents(view, this, false);
		log.info("Login");
	}
	
	/*private static String encryptPassword(String password)
	{
	    String sha1 = "";
	    try
	    {
	        MessageDigest crypt = MessageDigest.getInstance("SHA-1");
	        crypt.reset();
	        crypt.update(password.getBytes("UTF-8"));
	        sha1 = byteToHex(crypt.digest());
	    }
	    catch(NoSuchAlgorithmException e)
	    {
	        e.printStackTrace();
	    }
	    catch(UnsupportedEncodingException e)
	    {
	        e.printStackTrace();
	    }
	    return sha1;
	}

	private static String byteToHex(final byte[] hash)
	{
	    Formatter formatter = new Formatter();
	    for (byte b : hash)
	    {
	        formatter.format("%02x", b);
	    }
	    String result = formatter.toString();
	    formatter.close();
	    return result;
	}*/
	
	@Command
	@NotifyChange({"mensaje"})
	public void ingresar(){
		usuarioLogin = usuarioDao.login(alias, contrasena);
		//usuarioLogin = usuarioDao.login(txtUsuario.getText(), encryptPassword(txtClave.getText()));
		if(usuarioLogin == null){
			mensaje = "Error en las credenciales de acceso";
		}else{
			Session session;
			session=Sessions.getCurrent();
			session.setAttribute("_USUARIO_", usuarioLogin);
			Executions.getCurrent().sendRedirect("index.zul");
		}
	}
}

