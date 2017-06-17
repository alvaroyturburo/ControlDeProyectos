package ec.ed.upse.controlProyectosGad.modelo;

import java.io.Serializable;
import javax.persistence.*;

import org.eclipse.persistence.annotations.AdditionalCriteria;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * The persistent class for the pdps_usuario database table.
 * 
 */
@Entity
@Table(name="pdps_usuario")
@NamedQueries({
@NamedQuery(name="PdpsUsuario.findAll", query="SELECT p FROM PdpsUsuario p"),
@NamedQuery(name="PdpsUsuario.login",	query="SELECT p FROM PdpsUsuario p where p.alias = :alias AND "
		+ "p.contrasena = :contrasena"),
@NamedQuery(name="PdpsUsuario.buscarPorNombre", query="SELECT p FROM PdpsUsuario p where p.thFuncionario.pgPersona.nombres like :nombre")
})
@AdditionalCriteria("this.estado = 'A'")
@NoArgsConstructor
public class PdpsUsuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Getter @Setter private int id;

	@Getter @Setter private String alias;

	@Getter @Setter private String contrasena;

	@Getter @Setter private String estado;

	//bi-directional many-to-one association to PdpsRole
	@ManyToOne
	@JoinColumn(name="ID_ROLES")
	@Getter @Setter private PdpsRole pdpsRole;

	//bi-directional many-to-one association to ThFuncionario
	@ManyToOne
	@JoinColumn(name="ID_FUNCIONARIO")
	@Getter @Setter private ThFuncionario thFuncionario;

}