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
 * The persistent class for the ad_archivo database table.
 * 
 */
@Entity
@Table(name="ad_archivo")
@NamedQueries({
@NamedQuery(name="AdArchivo.findAll", query="SELECT a FROM AdArchivo a")
})
@AdditionalCriteria("this.estado = 'A'")
@NoArgsConstructor
public class AdArchivo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Getter @Setter private int id;

	@Getter @Setter private String archivo;

	@Getter @Setter private String autor;

	@Getter @Setter private String estado;

	@Temporal(TemporalType.DATE)
	@Getter @Setter private Date fecha;

	//bi-directional many-to-one association to PdpImagen
	@OneToMany(mappedBy="adArchivo", cascade=CascadeType.ALL)
	@Getter @Setter private List<PdpImagen> pdpImagens;

	public PdpImagen addPdpImagen(PdpImagen pdpImagen) {
		getPdpImagens().add(pdpImagen);
		pdpImagen.setAdArchivo(this);

		return pdpImagen;
	}

	public PdpImagen removePdpImagen(PdpImagen pdpImagen) {
		getPdpImagens().remove(pdpImagen);
		pdpImagen.setAdArchivo(null);

		return pdpImagen;
	}

}