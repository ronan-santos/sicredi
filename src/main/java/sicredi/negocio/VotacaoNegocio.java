package sicredi.negocio;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import sicredi.negocio.dto.ResultadoVotacaoDto;
import sicredi.negocio.enuns.ResultadoVotacaoEnum;
import sicredi.negocio.excecao.NegocioException;
import sicredi.negocio.excecao.enuns.MensagemEnum;
import sicredi.negocio.integracao.CpfAssociadoIntegracao;
import sicredi.negocio.vo.CpfRetornoVo;
import sicredi.persistencia.dao.VotoAssociadoDao;
import sicredi.persistencia.entidade.Pauta;
import sicredi.persistencia.entidade.VotoAssociado;
import sicredi.persistencia.entidade.VotoAssociadoId;
import sicredi.persistencia.enuns.VotoEnum;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class VotacaoNegocio {

	@Inject
	private PautaNegocio pautaNegocio;
	
	@Inject
	private AssociadoNegocio associadoNegocio;
	
	@Inject
	private CpfAssociadoIntegracao cpfIntegracao;
	
	@Inject
	private VotoAssociadoDao dao;
	
	private final String VOTO_HABILITADO = "ABLE_TO_VOTE";
	
	
	public ResultadoVotacaoDto obterResultadorVotacaoPor(Long idPauta) {
		
		Pauta pauta = pautaNegocio.obterPor(idPauta);
		
		if(pauta.getDataFimVotacao().isAfter(LocalDateTime.now()))
			throw new NegocioException(MensagemEnum.MSG019);
		
		return montarResultadoVotacaoPor(pauta, obterResultadoVotacaoPor(pauta.getVotos()));
		
	}
	
	private ResultadoVotacaoDto montarResultadoVotacaoPor(Pauta pauta, ResultadoVotacaoEnum resultado) {
		
		return ResultadoVotacaoDto
					.builder()
						.dataFimVotacao(pauta.getDataFimVotacao())
						.idPauta(pauta.getId())
						.nomePauta(pauta.getNome())
						.quantidadeVotos(Objects.nonNull(pauta.getVotos())
											? pauta.getVotos().size()
											: 0)
						.resultado(resultado.getResultado())
					.build();
	}
	
	private ResultadoVotacaoEnum obterResultadoVotacaoPor(List<VotoAssociado> votos ) {
		
		if(Objects.isNull(votos) || votos.isEmpty() )
			return ResultadoVotacaoEnum.SEM_VOTO;
		
		Long votosSim = obterQuantidadeVotosPor(votos, VotoEnum.SIM);
		Long votosNao = obterQuantidadeVotosPor(votos, VotoEnum.NAO);
		
		if(votosSim == votosNao)
			return ResultadoVotacaoEnum.EMPATE;
		else if(votosSim > votosNao)
			return ResultadoVotacaoEnum.APROVADO;
		else 
			return ResultadoVotacaoEnum.REPROVADO;
								
	}
	
	private Long obterQuantidadeVotosPor(List<VotoAssociado> votos, VotoEnum tipoVoto) {
		
		return votos
					.stream()
						.filter(voto -> voto.getVoto().equals(tipoVoto))
					.count();
	}

	
	public void receberVoto(VotoAssociado votoAssociado ) {
		
		validarVoto(votoAssociado);
		validarPauta(votoAssociado.getId().getIdPauta());
		validarAssociado(votoAssociado.getId().getIdAssociado());
		validarVotoExistente(votoAssociado.getId());
		
		dao.salvar(votoAssociado);
		
	}
	
	
	
	private void validarVotoExistente(VotoAssociadoId id) {
		
		if(Objects.nonNull(dao.obterPor(id)))
			throw new NegocioException(MensagemEnum.MSG018);
	}
	
	private void validarVoto(VotoAssociado voto) {
		
		if(Objects.isNull(voto.getId()) 
				|| Objects.isNull(voto.getId().getIdAssociado())
				|| Objects.isNull(voto.getId().getIdPauta()))
			throw new NegocioException(MensagemEnum.MSG013);
		
			if(Objects.isNull(voto.getVoto()))
				throw new NegocioException(MensagemEnum.MSG014);
	}
	
	private void validarPauta(Long idPauta) {
		
		Pauta pauta = pautaNegocio.obterPor(idPauta);
		
		if(Objects.isNull(pauta) 
				|| pauta.getDataInicioVotacao().isAfter(LocalDateTime.now())
				|| pauta.getDataFimVotacao().isBefore(LocalDateTime.now()))
			throw new NegocioException(MensagemEnum.MSG015);
	}
	
	private void validarAssociado(Long idUsuario) {

		CpfRetornoVo cpfRetorno = cpfIntegracao
									.obterHabilitacoVotoPor(associadoNegocio.obterAssociadoPor(idUsuario).getCpf());
		
		if(!cpfRetorno.getStatus().equalsIgnoreCase(VOTO_HABILITADO)) {
			
			throw new NegocioException(MensagemEnum.MSG017);
		}
	}
}
