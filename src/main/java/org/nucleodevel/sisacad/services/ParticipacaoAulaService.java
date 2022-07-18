package org.nucleodevel.sisacad.services;

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
	public void validadeForInsertUpdate(ParticipacaoAula entity) {
		String error = "";

		if (entity.getAula() == null) {
			error += "Aula pendente; ";
		}

		if (entity.getDiscente() == null) {
			error += "Discente pendente; ";
		}

		if (!error.isEmpty()) {
			throw new FieldValidationException(entity.getId(), getEntityClass(), error);
		}

		Aula myAula = entity.getAula();
		Discente myDiscente = entity.getDiscente();

		Optional<ParticipacaoAula> similar = repo.findDifferentByAulaAndDiscente(entity.getId(), myAula, myDiscente);
		similar.ifPresent(obj -> {
			throw new DataIntegrityException("Já existe uma participação desse discente nessa aula!");
		});
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
	}

}
