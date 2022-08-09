package org.nucleodevel.sisacad.services;

import java.util.List;
import java.util.Optional;

import org.nucleodevel.sisacad.domain.Avaliacao;
import org.nucleodevel.sisacad.domain.Discente;
import org.nucleodevel.sisacad.domain.ParticipacaoAvaliacao;
import org.nucleodevel.sisacad.repositories.ParticipacaoAvaliacaoRepository;
import org.nucleodevel.sisacad.services.exceptions.DataIntegrityException;
import org.nucleodevel.sisacad.services.exceptions.FieldValidationException;
import org.nucleodevel.sisacad.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ParticipacaoAvaliacaoService
		extends AbstractService<ParticipacaoAvaliacao, Integer, ParticipacaoAvaliacaoRepository> {

	@Autowired
	private AvaliacaoService avaliacaoService;
	@Autowired
	private DiscenteService discenteService;

	@Override
	public void validadeForInsertUpdate(ParticipacaoAvaliacao entity) {
		String error = "";

		if (entity.getConceitoFinal() == null) {
			error += "Conceito Final pendente; ";
		}

		Avaliacao myAvaliacao = null;
		if (entity.getAvaliacao() == null) {
			error += "Avaliação pendente; ";
		} else {
			try {
				myAvaliacao = avaliacaoService.find(entity.getAvaliacao().getId());
			} catch (ObjectNotFoundException e) {
				error += "Avaliação com ID " + entity.getAvaliacao().getId() + " não existente; ";
			}
		}

		Discente myDiscente = null;
		if (entity.getDiscente() == null) {
			error += "Discente pendente; ";
		} else {
			try {
				myDiscente = discenteService.find(entity.getDiscente().getId());
			} catch (ObjectNotFoundException e) {
				error += "Discente com ID " + entity.getDiscente().getId() + " não existente; ";
			}
		}

		if (!error.isEmpty()) {
			throw new FieldValidationException(entity.getId(), getEntityClass(), error);
		}

		Optional<ParticipacaoAvaliacao> similar = repository.findSimilarByAvaliacaoAndDiscente(entity.getId(),
				myAvaliacao, myDiscente);
		similar.ifPresent(obj -> {
			throw new DataIntegrityException("Já existe uma participação desse discente nessa avaliação!");
		});
	}

	@Override
	public List<ParticipacaoAvaliacao> findAll() {
		return repository.findByOrderByAvaliacaoDescDiscenteAsc();
	}

}
