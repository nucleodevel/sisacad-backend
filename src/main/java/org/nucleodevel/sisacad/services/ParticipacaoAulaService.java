package org.nucleodevel.sisacad.services;

import java.util.List;
import java.util.Optional;

import org.nucleodevel.sisacad.domain.Aula;
import org.nucleodevel.sisacad.domain.Discente;
import org.nucleodevel.sisacad.domain.ParticipacaoAula;
import org.nucleodevel.sisacad.dto.ParticipacaoAulaDto;
import org.nucleodevel.sisacad.repositories.ParticipacaoAulaRepository;
import org.nucleodevel.sisacad.services.exceptions.DataIntegrityException;
import org.nucleodevel.sisacad.services.exceptions.FieldValidationException;
import org.nucleodevel.sisacad.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ParticipacaoAulaService
		extends AbstractService<ParticipacaoAula, Integer, ParticipacaoAulaDto, ParticipacaoAulaRepository> {

	@Autowired
	private AulaService aulaService;
	@Autowired
	private DiscenteService discenteService;

	@Override
	public ParticipacaoAula mergeDtoIntoEntity(ParticipacaoAulaDto dto, ParticipacaoAula entity) {
		entity.setId(dto.getId());
		entity.setAula(aulaService.find(dto.getAula()));
		entity.setDiscente(discenteService.find(dto.getDiscente()));

		return entity;
	}

	@Override
	public void validadeForInsertUpdate(ParticipacaoAulaDto dto) {
		String error = "";

		if (dto.getAula() == null) {
			error += "Aula pendente; ";
		} else {
			try {
				aulaService.find(dto.getAula());
			} catch (ObjectNotFoundException e) {
				error += "Aula com ID " + dto.getAula() + " não existente; ";
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

		Aula myAula = aulaService.find(dto.getAula());
		Discente myDiscente = discenteService.find(dto.getDiscente());

		Optional<ParticipacaoAula> similar = repository.findSimilarByAulaAndDiscente(dto.getId(), myAula, myDiscente);
		similar.ifPresent(obj -> {
			throw new DataIntegrityException("Já existe uma participação desse discente nessa aula!");
		});
	}

	@Override
	protected List<ParticipacaoAula> findAll() {
		return repository.findByOrderByAulaDescDiscenteAsc();
	}

	public ParticipacaoAulaDto findDtoByAulaIdAndDiscenteId(Integer aulaId, Integer discenteId) {
		Aula aula = aulaService.find(aulaId);
		Discente discente = discenteService.find(discenteId);

		Optional<ParticipacaoAula> similar = repository.findByAulaAndDiscente(aula, discente);
		return similar.isPresent() ? getDtoFromEntity(similar.get()) : null;
	}

}
