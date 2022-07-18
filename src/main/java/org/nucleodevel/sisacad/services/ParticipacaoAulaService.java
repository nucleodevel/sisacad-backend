package org.nucleodevel.sisacad.services;

import java.util.Optional;

import org.nucleodevel.sisacad.domain.Aula;
import org.nucleodevel.sisacad.domain.Discente;
import org.nucleodevel.sisacad.domain.ParticipacaoAula;
import org.nucleodevel.sisacad.repositories.ParticipacaoAulaRepository;
import org.nucleodevel.sisacad.services.exceptions.DataIntegrityException;
import org.springframework.stereotype.Service;

@Service
public class ParticipacaoAulaService extends AbstractService<ParticipacaoAula, Integer, ParticipacaoAulaRepository> {

	@Override
	public void validadeForInsertUpdate(ParticipacaoAula entity) {
		Aula myAula = entity.getAula();
		Discente myDiscente = entity.getDiscente();

		Optional<ParticipacaoAula> similar = repo.findDifferentByAulaAndDiscente(entity.getId(), myAula, myDiscente);
		similar.ifPresent(obj -> {
			throw new DataIntegrityException("Já existe uma participação desse discente nessa aula!");
		});
	}

}
