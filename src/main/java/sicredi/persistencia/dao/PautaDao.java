package sicredi.persistencia.dao;

import java.util.List;

import javax.enterprise.context.RequestScoped;

import sicredi.persistencia.entidade.Pauta;

@RequestScoped
public class PautaDao extends AbstractDao<Pauta, Long> {

	public PautaDao() {
		super(Pauta.class);
	}
	
	public List<Pauta> obterPautasEmVotacao(){
		
		return dao.getEntityManager()
					.createNamedQuery("Pauta.obterPautasAtivas",Pauta.class)
					.getResultList();
	}
}
