package ec.ed.upse.controlProyectosGad.controladores;

import java.util.Random;

/*import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;*/

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
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

	@Wire private Textbox txtC;
	@Wire private Textbox txtCG;
	
	@Getter @Setter private String alias;
	@Getter @Setter private String contrasena;
	@Getter @Setter private String mensaje;
	@Getter @Setter private String captchaGenerado;
	@Getter @Setter private String captcha;

	
	private PdpsUsuarioDAO usuarioDao = new PdpsUsuarioDAO();
	private PdpsUsuario usuarioLogin;
	
	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view){
		Selectors.wireComponents(view, this, false);
		log.info("Login");
		txtCG.setText(getCadenaAlfanumAleatoria(5));
		//System.out.println(""+getCadenaAlfanumAleatoria(4));
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
	
	String getCadenaAlfanumAleatoria (int longitud){
		String cadenaAleatoria = "";
		long milis = new java.util.GregorianCalendar().getTimeInMillis();
		Random r = new Random(milis);
		int i = 0;
		while ( i < longitud){
		char c = (char)r.nextInt(255);
		if ( (c >= '0' && c <='9') || (c >='A' && c <='Z') ){
		cadenaAleatoria += c;
		i ++;
		}
		}
		return cadenaAleatoria;
		}

	
	@Command
	@NotifyChange({"mensaje"})
	public void ingresar(){
		usuarioLogin = usuarioDao.login(alias, contrasena);
		//usuarioLogin = usuarioDao.login(txtUsuario.getText(), encryptPassword(txtClave.getText()));
		if(usuarioLogin == null){
			mensaje = "Error en las credenciales de acceso";
			txtCG.setText(getCadenaAlfanumAleatoria(5));
			txtC.setDisabled(false);
		}else{
			if(txtC.getText().equals(txtCG.getText())){
			Session session;
			session=Sessions.getCurrent();
			session.setAttribute("_USUARIO_", usuarioLogin);
			Executions.getCurrent().sendRedirect("index.zul");}
			else{
				mensaje = "Error en la captcha";
				txtCG.setText(getCadenaAlfanumAleatoria(5));
				txtC.setDisabled(false);
			}
		}
	}
	
	@Command
	public void comprobar(@BindingParam("valor") String valor){
		if(valor.equals(txtCG.getText().toString())){
			txtC.setDisabled(true);
		}
	}
	
	@Command
	public void reGenerar(){
		txtCG.setText(getCadenaAlfanumAleatoria(5));
	}
}

