package org.nucleodevel.sisacad.services;

import org.nucleodevel.sisacad.domain.ParticipacaoAvaliacao;
import org.nucleodevel.sisacad.repositories.ParticipacaoAvaliacaoRepository;
import org.springframework.stereotype.Service;

@Service
public class ParticipacaoAvaliacaoService
		extends AbstractService<ParticipacaoAvaliacao, Integer, ParticipacaoAvaliacaoRepository> {

	@Override
	public void validadeForInsertUpdate(ParticipacaoAvaliacao entity) {
		// TODO Auto-generated method stub
		
	}

}
