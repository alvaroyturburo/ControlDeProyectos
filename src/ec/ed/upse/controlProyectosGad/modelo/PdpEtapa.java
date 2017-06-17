package ec.ed.upse.controlProyectosGad.modelo;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.persistence.*;

import org.eclipse.persistence.annotations.AdditionalCriteria;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * The persistent class for the pdp_etapa database table.
 * 
 */
@Entity
@Table(name="pdp_etapa")
@NamedQueries({
@NamedQuery(name="PdpEtapa.findAll", query="SELECT p FROM PdpEtapa p"),
@NamedQuery(name="PdpEtapa.buscarPorProyecto", query="SELECT p FROM PdpEtapa p where p.pdpProyecto.id = :proyecto")
})
@AdditionalCriteria("this.estado = 'A'")
@NoArgsConstructor
public class PdpEtapa implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Getter @Setter private int id;

	@Getter @Setter private String descripcion;

	@Getter @Setter private String estado;

	@Column(name="FECHA_FIN")
	@Getter @Setter private String fechaFin;

	@Column(name="FECHA_INICIO")
	@Getter @Setter private String fechaInicio;

	@Column(name="TIEMPO_EJECUTADO")
	@Getter @Setter private double tiempoEjecutado;

	@Column(name="TIEMPO_PROGRAMADO")
	@Getter @Setter private double tiempoProgramado;

	//bi-directional many-to-one association to PdpProyecto
	@ManyToOne
	@JoinColumn(name="ID_PROYECTO")
	@Getter @Setter private PdpProyecto pdpProyecto;
	
	
	public Date getFechaInicioConvertidad() {
		SimpleDateFormat formatoDelTexto = new SimpleDateFormat("yyyy-MM-dd");
		String strFecha = fechaInicio+"";
		Date fecha = null;
		if(strFecha.equals("")){
			Calendar calendario = GregorianCalendar.getInstance();
			fecha= calendario.getTime();
		}else{
			try {
				fecha = formatoDelTexto.parse(strFecha);
			} catch (ParseException ex) {
				ex.printStackTrace();
			}
		}
		return fecha;
	}
	
	public Date getFechaFinConvertidad() {
		SimpleDateFormat formatoDelTexto = new SimpleDateFormat("yyyy-MM-dd");
		String strFecha = fechaFin+"";
		Date fecha = null;
		if(strFecha.equals("")){
			Calendar calendario = GregorianCalendar.getInstance();
			fecha= calendario.getTime();
		}else{
			try {
				fecha = formatoDelTexto.parse(strFecha);
			} catch (ParseException ex) {
				ex.printStackTrace();
			}
		}
		return fecha;
	}
	
	public void setFechaInicioConvertidad(Date fecha) {
		DateFormat fechaHora = new SimpleDateFormat("yyyy-MM-dd");
		fechaInicio = fechaHora.format(fecha);
	}
	
	public void setFechaFinConvertidad(Date fecha) {
		DateFormat fechaHora = new SimpleDateFormat("yyyy-MM-dd");
		fechaFin = fechaHora.format(fecha);
	}

}