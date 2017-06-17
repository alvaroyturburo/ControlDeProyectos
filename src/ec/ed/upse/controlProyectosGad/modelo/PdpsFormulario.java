package ec.ed.upse.controlProyectosGad.modelo;

import java.io.Serializable;
import javax.persistence.*;

import org.eclipse.persistence.annotations.AdditionalCriteria;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


/**
 * The persistent class for the pdps_formulario database table.
 * 
 */
@Entity
@Table(name="pdps_formulario")
@NamedQueries({
@NamedQuery(name="PdpsFormulario.findAll", query="SELECT p FROM PdpsFormulario p")
})
@AdditionalCriteria("this.estado = 'A'")
@NoArgsConstructor
public class PdpsFormulario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Getter @Setter private int id;

	@Getter @Setter private String descripcion;

	@Getter @Setter private String estado;

	@Column(name="N_FORMULARIO")
	@Getter @Setter private int nFormulario;

	@Column(name="N_MODULO")
	@Getter @Setter private int nModulo;

	@Getter @Setter private String nombre;

	//bi-directional many-to-one association to PdpsRolesFormulario
	@OneToMany(mappedBy="pdpsFormulario", cascade=CascadeType.ALL)
	@Getter @Setter private List<PdpsRolesFormulario> pdpsRolesFormularios;

	public PdpsRolesFormulario addPdpsRolesFormulario(PdpsRolesFormulario pdpsRolesFormulario) {
		getPdpsRolesFormularios().add(pdpsRolesFormulario);
		pdpsRolesFormulario.setPdpsFormulario(this);

		return pdpsRolesFormulario;
	}

	public PdpsRolesFormulario removePdpsRolesFormulario(PdpsRolesFormulario pdpsRolesFormulario) {
		getPdpsRolesFormularios().remove(pdpsRolesFormulario);
		pdpsRolesFormulario.setPdpsFormulario(null);

		return pdpsRolesFormulario;
	}

}