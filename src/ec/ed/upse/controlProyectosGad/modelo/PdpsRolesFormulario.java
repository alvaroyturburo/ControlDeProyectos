package ec.ed.upse.controlProyectosGad.modelo;

import java.io.Serializable;
import javax.persistence.*;

import org.eclipse.persistence.annotations.AdditionalCriteria;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * The persistent class for the pdps_roles_formulario database table.
 * 
 */
@Entity
@Table(name="pdps_roles_formulario")
@NamedQueries({
@NamedQuery(name="PdpsRolesFormulario.findAll", query="SELECT p FROM PdpsRolesFormulario p")
})
@AdditionalCriteria("this.estado = 'A'")
@NoArgsConstructor
public class PdpsRolesFormulario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Getter @Setter private int id;

	@Getter @Setter private String estado;

	//bi-directional many-to-one association to PdpsFormulario
	@ManyToOne
	@JoinColumn(name="ID_FORMULARIO")
	@Getter @Setter private PdpsFormulario pdpsFormulario;

	//bi-directional many-to-one association to PdpsRole
	@ManyToOne
	@JoinColumn(name="ID_ROLES")
	@Getter @Setter private PdpsRole pdpsRole;

}