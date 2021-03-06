package ec.ed.upse.controlProyectosGad.modelo;

import java.io.Serializable;
import javax.persistence.*;

import org.eclipse.persistence.annotations.AdditionalCriteria;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


/**
 * The persistent class for the pg_persona database table.
 * 
 */
@Entity
@Table(name="pg_persona")
@NamedQueries({
@NamedQuery(name="PgPersona.findAll", query="SELECT p FROM PgPersona p")
})
@AdditionalCriteria("this.estado = 'A'")
@NoArgsConstructor
public class PgPersona implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Getter @Setter private int id;

	@Getter @Setter private String apellido1;

	@Getter @Setter private String apellido2;

	@Getter @Setter private String estado;

	@Getter @Setter private String nombres;

	//bi-directional many-to-one association to PdpProyecto
	@OneToMany(mappedBy="pgPersona", cascade=CascadeType.ALL)
	@Getter @Setter private List<PdpProyecto> pdpProyectos;

	//bi-directional many-to-one association to ThFuncionario
	@OneToMany(mappedBy="pgPersona", cascade=CascadeType.ALL)
	@Getter @Setter private List<ThFuncionario> thFuncionarios;

	public PdpProyecto addPdpProyecto(PdpProyecto pdpProyecto) {
		getPdpProyectos().add(pdpProyecto);
		pdpProyecto.setPgPersona(this);

		return pdpProyecto;
	}

	public PdpProyecto removePdpProyecto(PdpProyecto pdpProyecto) {
		getPdpProyectos().remove(pdpProyecto);
		pdpProyecto.setPgPersona(null);

		return pdpProyecto;
	}

	public ThFuncionario addThFuncionario(ThFuncionario thFuncionario) {
		getThFuncionarios().add(thFuncionario);
		thFuncionario.setPgPersona(this);

		return thFuncionario;
	}

	public ThFuncionario removeThFuncionario(ThFuncionario thFuncionario) {
		getThFuncionarios().remove(thFuncionario);
		thFuncionario.setPgPersona(null);

		return thFuncionario;
	}

}