package ec.ed.upse.controlProyectosGad.modelo;

import java.io.Serializable;
import javax.persistence.*;

import org.eclipse.persistence.annotations.AdditionalCriteria;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


/**
 * The persistent class for the pdp_fase database table.
 * 
 */
@Entity
@Table(name="pdp_fase")
@NamedQueries({
@NamedQuery(name="PdpFase.findAll", query="SELECT p FROM PdpFase p"),
@NamedQuery(name="PdpFase.buscarPorProyecto", query="SELECT p FROM PdpFase p where p.pdpProyecto.id = :proyecto")
})
@AdditionalCriteria("this.estado = 'A'")
@NoArgsConstructor
public class PdpFase implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Getter @Setter private int id;

	@Getter @Setter private String descripcion;

	@Column(name="DIAS_PLAZO")
	@Getter @Setter private int diasPlazo;

	@Getter @Setter private String estado;

	@Column(name="FECHA_FIN")
	@Getter @Setter private String fechaFin;

	@Column(name="FECHA_INICIO")
	@Getter @Setter private String fechaInicio;

	@Column(name="N_FASE")
	@Getter @Setter private String nFase;

	@Column(name="TIEMPO_EJECUTADO")
	@Getter @Setter private double tiempoEjecutado;

	@Column(name="TIEMPO_PROGRAMADO")
	@Getter @Setter private double tiempoProgramado;

	//bi-directional many-to-one association to PdpProyecto
	@ManyToOne
	@JoinColumn(name="ID_PROYECTO")
	@Getter @Setter private PdpProyecto pdpProyecto;

	//bi-directional many-to-one association to PdpProducto
	@OneToMany(mappedBy="pdpFase", cascade=CascadeType.ALL)
	@Getter @Setter private List<PdpProducto> pdpProductos;

	public PdpProducto addPdpProducto(PdpProducto pdpProducto) {
		getPdpProductos().add(pdpProducto);
		pdpProducto.setPdpFase(this);

		return pdpProducto;
	}

	public PdpProducto removePdpProducto(PdpProducto pdpProducto) {
		getPdpProductos().remove(pdpProducto);
		pdpProducto.setPdpFase(null);

		return pdpProducto;
	}

}