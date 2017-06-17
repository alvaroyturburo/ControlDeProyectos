package ec.ed.upse.controlProyectosGad.modelo;

import java.io.Serializable;
import javax.persistence.*;

import org.eclipse.persistence.annotations.AdditionalCriteria;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


/**
 * The persistent class for the pdp_fuente_financiamiento database table.
 * 
 */
@Entity
@Table(name="pdp_fuente_financiamiento")
@NamedQueries({
@NamedQuery(name="PdpFuenteFinanciamiento.findAll", query="SELECT p FROM PdpFuenteFinanciamiento p")
})
@AdditionalCriteria("this.estado = 'A'")
@NoArgsConstructor
public class PdpFuenteFinanciamiento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Getter @Setter private int codigo;

	@Getter @Setter private String descripcion;

	@Getter @Setter private String estado;

	//bi-directional many-to-one association to PdpFinanciamiento
	@OneToMany(mappedBy="pdpFuenteFinanciamiento", cascade=CascadeType.ALL)
	@Getter @Setter private List<PdpFinanciamiento> pdpFinanciamientos;

	public PdpFinanciamiento addPdpFinanciamiento(PdpFinanciamiento pdpFinanciamiento) {
		getPdpFinanciamientos().add(pdpFinanciamiento);
		pdpFinanciamiento.setPdpFuenteFinanciamiento(this);

		return pdpFinanciamiento;
	}

	public PdpFinanciamiento removePdpFinanciamiento(PdpFinanciamiento pdpFinanciamiento) {
		getPdpFinanciamientos().remove(pdpFinanciamiento);
		pdpFinanciamiento.setPdpFuenteFinanciamiento(null);

		return pdpFinanciamiento;
	}

}