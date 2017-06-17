package ec.ed.upse.controlProyectosGad.modelo;

import java.io.Serializable;
import javax.persistence.*;

import org.eclipse.persistence.annotations.AdditionalCriteria;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the th_funcionario database table.
 * 
 */
@Entity
@Table(name="th_funcionario")
@NamedQueries({
@NamedQuery(name="ThFuncionario.findAll", query="SELECT t FROM ThFuncionario t")
})
@AdditionalCriteria("this.estado = 'A'")
@NoArgsConstructor
public class ThFuncionario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Getter @Setter private int id;

	@Getter @Setter private String estado;

	@Temporal(TemporalType.DATE)
	@Column(name="VIGENCIA_DESDE")
	@Getter @Setter private Date vigenciaDesde;

	//bi-directional many-to-one association to PdpAvance
	@OneToMany(mappedBy="thFuncionario", cascade=CascadeType.ALL)
	@Getter @Setter private List<PdpAvance> pdpAvances;

	//bi-directional many-to-one association to PdpsUsuario
	@OneToMany(mappedBy="thFuncionario", cascade=CascadeType.ALL)
	@Getter @Setter private List<PdpsUsuario> pdpsUsuarios;

	//bi-directional many-to-one association to PgDepartamento
	@ManyToOne
	@JoinColumn(name="ID_PUESTO_DEPARTAMENTO")
	@Getter @Setter private PgDepartamento pgDepartamento;

	//bi-directional many-to-one association to PgPersona
	@ManyToOne
	@JoinColumn(name="ID_PERSONA")
	@Getter @Setter private PgPersona pgPersona;

	public PdpAvance addPdpAvance(PdpAvance pdpAvance) {
		getPdpAvances().add(pdpAvance);
		pdpAvance.setThFuncionario(this);

		return pdpAvance;
	}

	public PdpAvance removePdpAvance(PdpAvance pdpAvance) {
		getPdpAvances().remove(pdpAvance);
		pdpAvance.setThFuncionario(null);

		return pdpAvance;
	}

	public PdpsUsuario addPdpsUsuario(PdpsUsuario pdpsUsuario) {
		getPdpsUsuarios().add(pdpsUsuario);
		pdpsUsuario.setThFuncionario(this);

		return pdpsUsuario;
	}

	public PdpsUsuario removePdpsUsuario(PdpsUsuario pdpsUsuario) {
		getPdpsUsuarios().remove(pdpsUsuario);
		pdpsUsuario.setThFuncionario(null);

		return pdpsUsuario;
	}


}