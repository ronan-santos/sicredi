package sicredi.negocio.enuns;

import lombok.Getter;

public enum ResultadoVotacaoEnum {

	APROVADO("Pauta aprovada"),
	REPROVADO("Pauta n�o apovada"),
	EMPATE("Houve um empate"),
	SEM_VOTO("N�o houve votos para a pauta");
	
	@Getter
	private String resultado;
	
	private ResultadoVotacaoEnum(String resultado) {
		this.resultado = resultado;
	}
	
}
