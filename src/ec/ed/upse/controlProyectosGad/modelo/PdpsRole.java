package ec.ed.upse.controlProyectosGad.modelo;

import java.io.Serializable;
import javax.persistence.*;

import org.eclipse.persistence.annotations.AdditionalCriteria;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


/**
 * The persistent class for the pdps_roles database table.
 * 
 */
@Entity
@Table(name="pdps_roles")
@NamedQueries({
@NamedQuery(name="PdpsRole.findAll", query="SELECT p FROM PdpsRole p")
})
@AdditionalCriteria("this.estado = 'A'")
@NoArgsConstructor
public class PdpsRole implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Getter @Setter private int id;

	@Getter @Setter private String descripcion;

	@Getter @Setter private String estado;

	//bi-directional many-to-one association to PdpsRolesFormulario
	@OneToMany(mappedBy="pdpsRole", cascade=CascadeType.ALL)
	@Getter @Setter private List<PdpsRolesFormulario> pdpsRolesFormularios;

	//bi-directional many-to-one association to PdpsUsuario
	@OneToMany(mappedBy="pdpsRole", cascade=CascadeType.ALL)
	@Getter @Setter private List<PdpsUsuario> pdpsUsuarios;

	public PdpsRolesFormulario addPdpsRolesFormulario(PdpsRolesFormulario pdpsRolesFormulario) {
		getPdpsRolesFormularios().add(pdpsRolesFormulario);
		pdpsRolesFormulario.setPdpsRole(this);

		return pdpsRolesFormulario;
	}

	public PdpsRolesFormulario removePdpsRolesFormulario(PdpsRolesFormulario pdpsRolesFormulario) {
		getPdpsRolesFormularios().remove(pdpsRolesFormulario);
		pdpsRolesFormulario.setPdpsRole(null);

		return pdpsRolesFormulario;
	}

	public PdpsUsuario addPdpsUsuario(PdpsUsuario pdpsUsuario) {
		getPdpsUsuarios().add(pdpsUsuario);
		pdpsUsuario.setPdpsRole(this);

		return pdpsUsuario;
	}

	public PdpsUsuario removePdpsUsuario(PdpsUsuario pdpsUsuario) {
		getPdpsUsuarios().remove(pdpsUsuario);
		pdpsUsuario.setPdpsRole(null);

		return pdpsUsuario;
	}

}