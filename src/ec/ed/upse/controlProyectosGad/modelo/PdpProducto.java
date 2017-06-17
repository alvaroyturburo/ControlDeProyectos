package ec.ed.upse.controlProyectosGad.modelo;

import java.io.Serializable;
import javax.persistence.*;

import org.eclipse.persistence.annotations.AdditionalCriteria;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * The persistent class for the pdp_producto database table.
 * 
 */
@Entity
@Table(name="pdp_producto")
@NamedQueries({
@NamedQuery(name="PdpProducto.findAll", query="SELECT p FROM PdpProducto p")
})
@AdditionalCriteria("this.estado = 'A'")
@NoArgsConstructor
public class PdpProducto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Getter @Setter private int id;

	@Getter @Setter private String descripcion;

	@Getter @Setter private String estado;

	//bi-directional many-to-one association to PdpFase
	@ManyToOne
	@JoinColumn(name="ID_FASE")
	@Getter @Setter private PdpFase pdpFase;

}