package sicredi.negocio.dto;

import java.time.LocalDateTime;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sicredi.persistencia.entidade.VotoAssociado;
import sicredi.persistencia.entidade.VotoAssociadoId;
import sicredi.persistencia.enuns.VotoEnum;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class VotoDto {
	
	private Long idAssociado;
	private Long idPauta;
	private Integer voto;
	
	
	public static VotoAssociado criarVoto(VotoDto voto  ) {
		
		if(Objects.nonNull(voto)) {
			
			return VotoAssociado
					.builder()
						.data(LocalDateTime.now())
						.voto(VotoEnum.obterVotoPor(voto.getVoto()))
						.id(VotoAssociadoId
								.builder()
									.idAssociado(voto.getIdAssociado())
									.idPauta(voto.getIdPauta())
								.build())
						.build();
		}
		
		return VotoAssociado.builder().build();
				
	}

}
