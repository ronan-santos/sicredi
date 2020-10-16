package sicredi.negocio.dto;

import java.time.LocalDateTime;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sicredi.persistencia.entidade.Pauta;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PautaDto {

	private long id;
	
	private AssociadoDto proponente;
	
	private String nome;
	
	private Integer minutosDuracao;
	
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime cadastro;

	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime dataInicioVotacao;
	
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime dataFimVotacao;
	
	
	public static PautaDto toDto(Pauta pauta) {
		
		return Objects.nonNull(pauta)
					? PautaDto
							.builder()
								.id(pauta.getId())
								.dataInicioVotacao(pauta.getDataInicioVotacao())
								.dataFimVotacao(pauta.getDataFimVotacao())
								.nome(pauta.getNome())
								.cadastro(pauta.getCadastro())
								.proponente(AssociadoDto.toDto(pauta.getProponente()))
							.build()
					: PautaDto.builder().build();
 
	}
	
	public static Pauta criarNovaPauta(PautaDto pauta) {
		
		return Objects.nonNull(pauta)
				? Pauta
						.builder()
						.dataFimVotacao(Objects.nonNull(pauta.getMinutosDuracao()) 
											&& pauta.getMinutosDuracao() > 0 
											&& Objects.nonNull(pauta.getDataInicioVotacao())
												? pauta.getDataInicioVotacao().plusMinutes(pauta.getMinutosDuracao().longValue())
												: pauta.getDataInicioVotacao().plusMinutes(1))
						.dataInicioVotacao(pauta.getDataInicioVotacao())
						.nome(pauta.getNome())
						.proponente(AssociadoDto.toEntidade(pauta.getProponente()))
						.cadastro(LocalDateTime.now())
					.build()
				: Pauta.builder().build();
		
	}
	

}
