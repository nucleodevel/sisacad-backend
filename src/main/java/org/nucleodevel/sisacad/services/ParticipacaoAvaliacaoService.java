package org.nucleodevel.sisacad.services;

import java.util.Optional;

import org.nucleodevel.sisacad.domain.Avaliacao;
import org.nucleodevel.sisacad.domain.Discente;
import org.nucleodevel.sisacad.domain.ParticipacaoAvaliacao;
import org.nucleodevel.sisacad.repositories.ParticipacaoAvaliacaoRepository;
import org.nucleodevel.sisacad.services.exceptions.DataIntegrityException;
import org.springframework.stereotype.Service;

@Service
public class ParticipacaoAvaliacaoService
		extends AbstractService<ParticipacaoAvaliacao, Integer, ParticipacaoAvaliacaoRepository> {

	@Override
	public void validadeForInsertUpdate(ParticipacaoAvaliacao entity) {
		Avaliacao myAvaliacao = entity.getAvaliacao();
		Discente myDiscente = entity.getDiscente();

		Optional<ParticipacaoAvaliacao> similar = repo.findDifferentByAvaliacaoAndDiscente(entity.getId(), myAvaliacao,
				myDiscente);
		similar.ifPresent(obj -> {
			throw new DataIntegrityException("Já existe uma participação desse discente nessa avaliação!");
		});
	}

}
