package sicredi.negocio.integracao;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;

import javax.enterprise.context.RequestScoped;

import lombok.extern.slf4j.Slf4j;
import sicredi.negocio.excecao.NegocioException;
import sicredi.negocio.excecao.enuns.MensagemEnum;
import sicredi.negocio.vo.CpfRetornoVo;
import sicredi.utils.JsonBodyHandler;

@Slf4j
@RequestScoped
public class CpfAssociadoIntegracao {

	private static final String ENDERECO_VALIDADOR = "https://user-info.herokuapp.com/users/";

	
	public CpfRetornoVo obterHabilitacoVotoPor(String cpf) {
		
		try {
			
			validarCpf(cpf);
			
			HttpClient cliente = HttpClient.newHttpClient();
			HttpRequest requesicao = HttpRequest
										.newBuilder()
											.uri(new URI(ENDERECO_VALIDADOR.concat(cpf)))
											.header("Accept", "application/json")
											.GET()
											.build();
		
			return cliente.send(requesicao, new JsonBodyHandler<>(CpfRetornoVo.class)).body();

			
		} catch (URISyntaxException | IOException | InterruptedException  e     ) {

			log.error("ERRO AO CONSULTAR O CPF DO ASSOCIADO", e);
			
			throw new NegocioException(MensagemEnum.MSG001);
		}
		
	}
	
	private void validarCpf(String cpf ) {
		
		if(cpf.length() != 11)
			throw new NegocioException(MensagemEnum.MSG008);
	}
}
