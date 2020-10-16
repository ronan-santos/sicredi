package sicredi.persistencia.entidade;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sicredi.persistencia.enuns.VotoEnum;

@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "TB03_VOTACAO_PAUTA", schema = "SICREDI")
public class VotoAssociado implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private VotoAssociadoId id;
	
	@Column(name = "DH_VOTO")
	private LocalDateTime data;
	
	@Column(name = "IC_VOTO")
	@Enumerated(EnumType.ORDINAL)
	private VotoEnum voto;
}
