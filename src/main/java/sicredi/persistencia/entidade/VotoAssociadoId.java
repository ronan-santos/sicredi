package sicredi.persistencia.entidade;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class VotoAssociadoId implements Serializable {


	private static final long serialVersionUID = 1L;

	@Column(name = "NU_ASSOCIADO")
	private Long idAssociado;
	
	@Column(name = "NU_PAUTA")
	private Long idPauta;
	

}
