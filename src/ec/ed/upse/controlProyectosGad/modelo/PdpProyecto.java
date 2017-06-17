package ec.ed.upse.controlProyectosGad.modelo;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.persistence.*;

import org.eclipse.persistence.annotations.AdditionalCriteria;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;


/**
 * The persistent class for the pdp_proyecto database table.
 * 
 */
@Entity
@Table(name="pdp_proyecto")
@NamedQueries({
@NamedQuery(name="PdpProyecto.findAll", query="SELECT p FROM PdpProyecto p"),
@NamedQuery(name="PdpProyecto.buscarPorNombre", query="SELECT p FROM PdpProyecto p where p.descripcion like :nombre OR (:inicio >= p.fechaInicioProyecto AND  :fin <= p.fechaFinProyecto)"),
@NamedQuery(name="PdpProyecto.buscarPorModalidadEstado", query="SELECT p FROM PdpProyecto p ")//where p.pdpModalidad.descripcion like :modalidad OR p.pdpEstadoProyecto.descripcion like :estado
})
@AdditionalCriteria("this.estado = 'A'")
@NoArgsConstructor
public class PdpProyecto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Getter @Setter private int id;

	@Column(name="ANIO_ARRASTRE")
	@Getter @Setter private int anioArrastre;

	@Column(name="COSTO_EJECUCION")
	@Getter @Setter private double costoEjecucion;

	@Column(name="COSTO_ESTUDIO")
	@Getter @Setter private double costoEstudio;

	@Getter @Setter private String descripcion;

	@Column(name="DIRECCION_UBICACION_AREA")
	@Getter @Setter private String direccionUbicacionArea;

	@Getter @Setter private String estado;

	@Column(name="FECHA_FIN_PROYECTO")
	@Getter @Setter private String fechaFinProyecto;

	@Column(name="FECHA_INICIO_PROYECTO")
	@Getter @Setter private String fechaInicioProyecto;

	@Getter @Setter private String funcionario;

	@Getter @Setter private String latitud;

	@Getter @Setter private String longitud;

	@Column(name="MONTO_EXCEDENTE")
	@Getter @Setter private double montoExcedente;

	@Column(name="NUMERO_HABITANTES")
	@Getter @Setter private int numeroHabitantes;

	@Getter @Setter private String observacion;

	@Column(name="TIEMPO_PREVISTO")
	@Getter @Setter private int tiempoPrevisto;

	@Column(name="TIPO_ESTADO_PROYECTO")
	@Getter @Setter private String tipoEstadoProyecto;

	@Column(name="UNIDAD_RESPONSABLE")
	@Getter @Setter private String unidadResponsable;

	//bi-directional many-to-one association to PdpAvance
	@OneToMany(mappedBy="pdpProyecto", cascade=CascadeType.ALL)
	@Getter @Setter private List<PdpAvance> pdpAvances;

	//bi-directional many-to-one association to PdpEtapa
	@OneToMany(mappedBy="pdpProyecto", cascade=CascadeType.ALL)
	@Getter @Setter private List<PdpEtapa> pdpEtapas;

	//bi-directional many-to-one association to PdpFase
	@OneToMany(mappedBy="pdpProyecto", cascade=CascadeType.ALL)
	@Getter @Setter private List<PdpFase> pdpFases;

	//bi-directional many-to-one association to PdpFinanciamiento
	@OneToMany(mappedBy="pdpProyecto", cascade=CascadeType.ALL)
	@Getter @Setter private List<PdpFinanciamiento> pdpFinanciamientos;

	//bi-directional many-to-one association to PdpImagen
	@OneToMany(mappedBy="pdpProyecto", cascade=CascadeType.ALL)
	@Getter @Setter private List<PdpImagen> pdpImagens;

	//bi-directional many-to-one association to PdpBarrioComunidad
	@ManyToOne
	@JoinColumn(name="COD_BARRIO_COMUNIDAD")
	@Getter @Setter private PdpBarrioComunidad pdpBarrioComunidad;

	//bi-directional many-to-one association to PdpEstadoProyecto
	@ManyToOne
	@JoinColumn(name="COD_ESTADO_PROYECTO")
	@Getter @Setter private PdpEstadoProyecto pdpEstadoProyecto;

	//bi-directional many-to-one association to PdpLugar
	@ManyToOne
	@JoinColumn(name="COD_LUGAR")
	@Getter @Setter private PdpLugar pdpLugar;

	//bi-directional many-to-one association to PdpModalidad
	@ManyToOne
	@JoinColumn(name="COD_MODALIDAD")
	@Getter @Setter private PdpModalidad pdpModalidad;

	//bi-directional many-to-one association to PdpTipoAvance
	@ManyToOne
	@JoinColumn(name="COD_TIPO_AVANCE")
	@Getter @Setter private PdpTipoAvance pdpTipoAvance;

	//bi-directional many-to-one association to PgDepartamento
	@ManyToOne
	@JoinColumn(name="COD_DEPARTAMENTO")
	@Getter @Setter private PgDepartamento pgDepartamento;

	//bi-directional many-to-one association to PgPersona
	@ManyToOne
	@JoinColumn(name="ID_CONTRATISTA")
	@Getter @Setter private PgPersona pgPersona;

	public PdpAvance addPdpAvance(PdpAvance pdpAvance) {
		getPdpAvances().add(pdpAvance);
		pdpAvance.setPdpProyecto(this);

		return pdpAvance;
	}

	public PdpAvance removePdpAvance(PdpAvance pdpAvance) {
		getPdpAvances().remove(pdpAvance);
		pdpAvance.setPdpProyecto(null);

		return pdpAvance;
	}

	public PdpEtapa addPdpEtapa(PdpEtapa pdpEtapa) {
		getPdpEtapas().add(pdpEtapa);
		pdpEtapa.setPdpProyecto(this);
		return pdpEtapa;
	}

	public PdpEtapa removePdpEtapa(PdpEtapa pdpEtapa) {
		getPdpEtapas().remove(pdpEtapa);
		pdpEtapa.setPdpProyecto(null);

		return pdpEtapa;
	}

	public PdpFase addPdpFas(PdpFase pdpFas) {
		getPdpFases().add(pdpFas);
		pdpFas.setPdpProyecto(this);

		return pdpFas;
	}

	public PdpFase removePdpFas(PdpFase pdpFas) {
		getPdpFases().remove(pdpFas);
		pdpFas.setPdpProyecto(null);

		return pdpFas;
	}

	public PdpFinanciamiento addPdpFinanciamiento(PdpFinanciamiento pdpFinanciamiento) {
		getPdpFinanciamientos().add(pdpFinanciamiento);
		pdpFinanciamiento.setPdpProyecto(this);

		return pdpFinanciamiento;
	}

	public PdpFinanciamiento removePdpFinanciamiento(PdpFinanciamiento pdpFinanciamiento) {
		getPdpFinanciamientos().remove(pdpFinanciamiento);
		pdpFinanciamiento.setPdpProyecto(null);

		return pdpFinanciamiento;
	}

	public PdpImagen addPdpImagen(PdpImagen pdpImagen) {
		getPdpImagens().add(pdpImagen);
		pdpImagen.setPdpProyecto(this);

		return pdpImagen;
	}

	public PdpImagen removePdpImagen(PdpImagen pdpImagen) {
		getPdpImagens().remove(pdpImagen);
		pdpImagen.setPdpProyecto(null);

		return pdpImagen;
	}
	
	public Date getFechaInicioConvertidad() {
		SimpleDateFormat formatoDelTexto = new SimpleDateFormat("yyyy-MM-dd");
		String strFecha = fechaInicioProyecto+"";
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
		String strFecha = fechaFinProyecto+"";
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
		fechaInicioProyecto = fechaHora.format(fecha);
	}
	
	public void setFechaFinConvertidad(Date fecha) {
		DateFormat fechaHora = new SimpleDateFormat("yyyy-MM-dd");
		fechaFinProyecto = fechaHora.format(fecha);
	}

}