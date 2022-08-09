package org.nucleodevel.sisacad.services;

import java.util.List;
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
	public ParticipacaoAvaliacao mergeDtoIntoEntity(ParticipacaoAvaliacaoDto dto, ParticipacaoAvaliacao entity) {
		entity.setId(dto.getId());
		entity.setConceitoFinal(dto.getConceitoFinal());
		entity.setAvaliacao(avaliacaoService.find(dto.getAvaliacao()));
		entity.setDiscente(discenteService.find(dto.getDiscente()));

		return entity;
	}

	@Override
	public void validadeForInsertUpdate(ParticipacaoAvaliacaoDto dto) {
		String error = "";

		if (dto.getConceitoFinal() == null) {
			error += "Conceito Final pendente; ";
		}

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

		Avaliacao myAvaliacao = avaliacaoService.find(dto.getAvaliacao());
		Discente myDiscente = discenteService.find(dto.getDiscente());

		Optional<ParticipacaoAvaliacao> similar = repository.findSimilarByAvaliacaoAndDiscente(dto.getId(), myAvaliacao,
				myDiscente);
		similar.ifPresent(obj -> {
			throw new DataIntegrityException("Já existe uma participação desse discente nessa avaliação!");
		});
	}

	@Override
	protected List<ParticipacaoAvaliacao> findAll() {
		return repository.findByOrderByAvaliacaoDescDiscenteAsc();
	}

}
