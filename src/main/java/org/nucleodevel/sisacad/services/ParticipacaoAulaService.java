package org.nucleodevel.sisacad.services;

import org.nucleodevel.sisacad.domain.ParticipacaoAula;
import org.nucleodevel.sisacad.repositories.ParticipacaoAulaRepository;
import org.springframework.stereotype.Service;

@Service
public class ParticipacaoAulaService extends AbstractService<ParticipacaoAula, Integer, ParticipacaoAulaRepository> {

	@Override
	public void validadeForInsertUpdate(ParticipacaoAula entity) {
		// TODO Auto-generated method stub

	}

}
