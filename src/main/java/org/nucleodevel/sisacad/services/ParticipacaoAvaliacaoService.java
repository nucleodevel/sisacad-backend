package org.nucleodevel.sisacad.services;

import java.util.Optional;

import org.nucleodevel.sisacad.domain.Avaliacao;
import org.nucleodevel.sisacad.domain.Discente;
import org.nucleodevel.sisacad.domain.ParticipacaoAvaliacao;
import org.nucleodevel.sisacad.dto.ParticipacaoAvaliacaoDto;
import org.nucleodevel.sisacad.repositories.ParticipacaoAvaliacaoRepository;
import org.nucleodevel.sisacad.services.exceptions.DataIntegrityException;
import org.nucleodevel.sisacad.services.exceptions.FieldValidationException;
import org.nucleodevel.sisacad.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ParticipacaoAvaliacaoService extends
		AbstractService<ParticipacaoAvaliacao, Integer, ParticipacaoAvaliacaoDto, ParticipacaoAvaliacaoRepository> {

	@Autowired
	private AvaliacaoService avaliacaoService;
	@Autowired
	private DiscenteService discenteService;

	@Override
	public void validadeForInsertUpdate(ParticipacaoAvaliacao entity) {
		String error = "";

		if (entity.getAvaliacao() == null) {
			error += "Avaliação pendente; ";
		}

		if (entity.getDiscente() == null) {
			error += "Discente pendente; ";
		}

		if (!error.isEmpty()) {
			throw new FieldValidationException(entity.getId(), getEntityClass(), error);
		}

		Avaliacao myAvaliacao = entity.getAvaliacao();
		Discente myDiscente = entity.getDiscente();

		Optional<ParticipacaoAvaliacao> similar = repo.findDifferentByAvaliacaoAndDiscente(entity.getId(), myAvaliacao,
				myDiscente);
		similar.ifPresent(obj -> {
			throw new DataIntegrityException("Já existe uma participação desse discente nessa avaliação!");
		});
	}

	@Override
	public void validadeForInsertUpdate(ParticipacaoAvaliacaoDto dto) {
		String error = "";

		if (dto.getAvaliacao() == null) {
			error += "Avaliação pendente; ";
		} else {
			try {
				avaliacaoService.find(dto.getAvaliacao());
			} catch (ObjectNotFoundException e) {
				error += "Avaliação com ID " + dto.getAvaliacao() + " não existente; ";
			}
		}

		if (dto.getDiscente() == null) {
			error += "Discente pendente; ";
		} else {
			try {
				discenteService.find(dto.getDiscente());
			} catch (ObjectNotFoundException e) {
				error += "Discente com ID " + dto.getDiscente() + " não existente; ";
			}
		}

		if (!error.isEmpty()) {
			throw new FieldValidationException(dto.getId(), getEntityClass(), error);
		}
	}

}
