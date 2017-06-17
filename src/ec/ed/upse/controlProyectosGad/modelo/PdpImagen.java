package ec.ed.upse.controlProyectosGad.modelo;

import java.io.Serializable;
import javax.persistence.*;

import org.eclipse.persistence.annotations.AdditionalCriteria;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * The persistent class for the pdp_imagen database table.
 * 
 */
@Entity
@Table(name="pdp_imagen")
@NamedQueries({
@NamedQuery(name="PdpImagen.findAll", query="SELECT p FROM PdpImagen p")
})
@AdditionalCriteria("this.estado = 'A'")
@NoArgsConstructor
public class PdpImagen implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Getter @Setter private int id;

	@Getter @Setter private String estado;

	//bi-directional many-to-one association to AdArchivo
	@ManyToOne
	@JoinColumn(name="ID_ARCHIVO")
	@Getter @Setter private AdArchivo adArchivo;

	//bi-directional many-to-one association to PdpProyecto
	@ManyToOne
	@JoinColumn(name="ID_PROYECTO")
	@Getter @Setter private PdpProyecto pdpProyecto;

}