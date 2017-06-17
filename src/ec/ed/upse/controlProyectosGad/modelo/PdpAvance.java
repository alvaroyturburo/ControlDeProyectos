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
 * The persistent class for the pdp_avance database table.
 * 
 */
@Entity
@Table(name="pdp_avance")
@NamedQueries({
@NamedQuery(name="PdpAvance.findAll", query="SELECT p FROM PdpAvance p"),
@NamedQuery(name="PdpAvance.buscarPorProyecto", query="SELECT p FROM PdpAvance p where p.pdpProyecto.id = :proyecto")
})
@AdditionalCriteria("this.estado = 'A'")
@NoArgsConstructor
public class PdpAvance implements Serializable {
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

	@Column(name="FECHA_REGISTRO")
	@Getter @Setter private String fechaRegistro;

	@Column(name="NUMERO_FACTURA")
	@Getter @Setter private String numeroFactura;

	@Column(name="NUMERO_PLANILLA")
	@Getter @Setter private String numeroPlanilla;

	@Getter @Setter private String observaciones;

	@Column(name="PORCENTAJE_AVANCE")
	@Getter @Setter private double porcentajeAvance;

	@Column(name="VALOR_FACTURA")
	@Getter @Setter private double valorFactura;

	//bi-directional many-to-one association to PdpProyecto
	@ManyToOne
	@JoinColumn(name="ID_PROYECTO")
	@Getter @Setter private PdpProyecto pdpProyecto;

	//bi-directional many-to-one association to ThFuncionario
	@ManyToOne
	@JoinColumn(name="ID_FISCALIZADOR")
	@Getter @Setter private ThFuncionario thFuncionario;
	
	
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
	
	public Date getFechaRegistroConvertidad() {
		SimpleDateFormat formatoDelTexto = new SimpleDateFormat("yyyy-MM-dd");
		String strFecha = fechaRegistro+"";
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
	
	public void setFechaRegistroConvertidad(Date fecha) {
		DateFormat fechaHora = new SimpleDateFormat("yyyy-MM-dd");
		fechaRegistro = fechaHora.format(fecha);
	}

}