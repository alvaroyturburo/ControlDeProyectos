package ec.ed.upse.controlProyectosGad.modelo;

import java.io.Serializable;
import javax.persistence.*;

import org.eclipse.persistence.annotations.AdditionalCriteria;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * The persistent class for the pdps_auditoria database table.
 * 
 */
@Entity
@Table(name="pdps_auditoria")
@NamedQueries({
@NamedQuery(name="PdpsAuditoria.findAll", query="SELECT p FROM PdpsAuditoria p")
})
@AdditionalCriteria("this.estado = 'A'")
@NoArgsConstructor
public class PdpsAuditoria implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Getter @Setter private int id;

	@Getter @Setter private String accion;

	@Getter @Setter private String cedula;

	@Getter @Setter private String estado;

	@Getter @Setter private String fecha;

	@Getter @Setter private String formulario;

	@Getter @Setter private String hora;

	@Column(name="NOMBRE_SESION")
	@Getter @Setter private String nombreSesion;

	@Column(name="NOMBRE_USUARIO")
	@Getter @Setter private String nombreUsuario;

}