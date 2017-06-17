package ec.ed.upse.controlProyectosGad.modelo;

import java.io.Serializable;
import javax.persistence.*;

import org.eclipse.persistence.annotations.AdditionalCriteria;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * The persistent class for the pdp_financiamiento database table.
 * 
 */
@Entity
@Table(name="pdp_financiamiento")
@NamedQueries({
@NamedQuery(name="PdpFinanciamiento.findAll", query="SELECT p FROM PdpFinanciamiento p")
})
@AdditionalCriteria("this.estado = 'A'")
@NoArgsConstructor
public class PdpFinanciamiento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Getter @Setter private int id;

	@Getter @Setter private String descripcion;

	@Getter @Setter private String estado;

	@Column(name="VALOR_FINANCIAMIENTO")
	@Getter @Setter private double valorFinanciamiento;

	//bi-directional many-to-one association to PdpFuenteFinanciamiento
	@ManyToOne
	@JoinColumn(name="COD_FUENTE_FINANCIAMIENTO")
	@Getter @Setter private PdpFuenteFinanciamiento pdpFuenteFinanciamiento;

	//bi-directional many-to-one association to PdpProyecto
	@ManyToOne
	@JoinColumn(name="ID_PROYECTO")
	@Getter @Setter private PdpProyecto pdpProyecto;

}