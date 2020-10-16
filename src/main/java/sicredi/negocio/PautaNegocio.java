package sicredi.negocio;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import sicredi.negocio.dto.PautaDto;
import sicredi.negocio.excecao.NegocioException;
import sicredi.negocio.excecao.enuns.MensagemEnum;
import sicredi.persistencia.dao.PautaDao;
import sicredi.persistencia.entidade.Associado;
import sicredi.persistencia.entidade.Pauta;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class PautaNegocio {
	
	@Inject
	private PautaDao dao;
	
	@Inject
	private AssociadoNegocio associadoNegocio;
	
	public Pauta obterPor(Long id){
		
		Pauta pauta =  dao.obterPor(id);
		
		if(Objects.isNull(pauta))
			throw new NegocioException(MensagemEnum.MSG016);
		
		return pauta;
	}
	
	public void salvar(Pauta pauta ) {
		
		validarNovaPauta(pauta);
		dao.salvar(pauta);
		
	}
	
	public List<PautaDto> obterPautasEmVotacao(){
		
		return dao.obterPautasEmVotacao()
					.stream()
					.filter(Objects::nonNull)
					.map(pauta-> PautaDto.toDto(pauta))
					.collect(Collectors.toList());
	}
	
	public List<PautaDto> obterTodas(){
		
		return dao.obterTodos()
					.stream()
						.filter(Objects::nonNull)
						.map(pauta-> PautaDto.toDto(pauta))
						.collect(Collectors.toList());
	}
	
	private void validarNovaPauta(Pauta pauta ) {
		
		validarProponente(pauta.getProponente());
		
		if(StringUtils.isEmpty(pauta.getNome()))
			throw new NegocioException(MensagemEnum.MSG010);
		if(Objects.isNull(pauta.getDataInicioVotacao()))
			throw new NegocioException(MensagemEnum.MSG011);
		if(pauta.getDataInicioVotacao().isBefore(LocalDateTime.now())) {
			throw new NegocioException(MensagemEnum.MSG012);
		}

			
	}
	
	private void validarProponente(Associado associado ) {
		
		if(Objects.isNull(associado) || Objects.isNull(associado.getId()) ) 
			throw new NegocioException(MensagemEnum.MSG009);
		
		associadoNegocio.obterAssociadoPor(associado.getId());
		
		
	}

}
