package ec.ed.upse.controlProyectosGad.modelo;

import java.io.Serializable;
import javax.persistence.*;

import org.eclipse.persistence.annotations.AdditionalCriteria;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


/**
 * The persistent class for the pg_departamento database table.
 * 
 */
@Entity
@Table(name="pg_departamento")
@NamedQueries({
@NamedQuery(name="PgDepartamento.findAll", query="SELECT p FROM PgDepartamento p")
})
@AdditionalCriteria("this.estado = 'A'")
@NoArgsConstructor
public class PgDepartamento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Getter @Setter private int codigo;

	@Getter @Setter private String descripcion;

	@Getter @Setter private String estado;

	//bi-directional many-to-one association to PdpProyecto
	@OneToMany(mappedBy="pgDepartamento", cascade=CascadeType.ALL)
	@Getter @Setter private List<PdpProyecto> pdpProyectos;

	//bi-directional many-to-one association to ThFuncionario
	@OneToMany(mappedBy="pgDepartamento", cascade=CascadeType.ALL)
	@Getter @Setter private List<ThFuncionario> thFuncionarios;

	public PdpProyecto addPdpProyecto(PdpProyecto pdpProyecto) {
		getPdpProyectos().add(pdpProyecto);
		pdpProyecto.setPgDepartamento(this);

		return pdpProyecto;
	}

	public PdpProyecto removePdpProyecto(PdpProyecto pdpProyecto) {
		getPdpProyectos().remove(pdpProyecto);
		pdpProyecto.setPgDepartamento(null);

		return pdpProyecto;
	}

	public ThFuncionario addThFuncionario(ThFuncionario thFuncionario) {
		getThFuncionarios().add(thFuncionario);
		thFuncionario.setPgDepartamento(this);

		return thFuncionario;
	}

	public ThFuncionario removeThFuncionario(ThFuncionario thFuncionario) {
		getThFuncionarios().remove(thFuncionario);
		thFuncionario.setPgDepartamento(null);

		return thFuncionario;
	}

}