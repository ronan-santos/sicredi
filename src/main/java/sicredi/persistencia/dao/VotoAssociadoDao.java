package sicredi.persistencia.dao;

import javax.enterprise.context.RequestScoped;

import sicredi.persistencia.entidade.VotoAssociado;
import sicredi.persistencia.entidade.VotoAssociadoId;

@RequestScoped
public class VotoAssociadoDao extends AbstractDao<VotoAssociado, VotoAssociadoId> {
	
	public VotoAssociadoDao() {
		super(VotoAssociado.class);
	}

}
