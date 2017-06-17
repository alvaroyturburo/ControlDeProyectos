package ec.ed.upse.controlProyectosGad.modelo;

import java.io.Serializable;
import javax.persistence.*;

import org.eclipse.persistence.annotations.AdditionalCriteria;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


/**
 * The persistent class for the pdp_tipo_avance database table.
 * 
 */
@Entity
@Table(name="pdp_tipo_avance")
@NamedQueries({
@NamedQuery(name="PdpTipoAvance.findAll", query="SELECT p FROM PdpTipoAvance p")
})
@AdditionalCriteria("this.estado = 'A'")
@NoArgsConstructor
public class PdpTipoAvance implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Getter @Setter private int codigo;

	@Getter @Setter private String descripcion;

	@Getter @Setter private String estado;

	//bi-directional many-to-one association to PdpProyecto
	@OneToMany(mappedBy="pdpTipoAvance", cascade=CascadeType.ALL)
	@Getter @Setter private List<PdpProyecto> pdpProyectos;

	public PdpProyecto addPdpProyecto(PdpProyecto pdpProyecto) {
		getPdpProyectos().add(pdpProyecto);
		pdpProyecto.setPdpTipoAvance(this);

		return pdpProyecto;
	}

	public PdpProyecto removePdpProyecto(PdpProyecto pdpProyecto) {
		getPdpProyectos().remove(pdpProyecto);
		pdpProyecto.setPdpTipoAvance(null);

		return pdpProyecto;
	}

}