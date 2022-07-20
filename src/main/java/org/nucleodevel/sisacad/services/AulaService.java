package org.nucleodevel.sisacad.services;

import java.util.Date;

import org.nucleodevel.sisacad.domain.Aula;
import org.nucleodevel.sisacad.dto.AulaDto;
import org.nucleodevel.sisacad.repositories.AulaRepository;
import org.nucleodevel.sisacad.services.exceptions.FieldValidationException;
import org.nucleodevel.sisacad.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AulaService extends AbstractService<Aula, Integer, AulaDto, AulaRepository> {

	@Autowired
	private OfertaDisciplinaService ofertaDisciplinaService;

	@Override
	public Aula mergeDtoIntoEntity(AulaDto dto, Aula entity) {
		entity.setId(dto.getId());
		entity.setInicio(dto.getInicio() == null ? null : new Date(dto.getInicio()));
		entity.setTermino(dto.getTermino() == null ? null : new Date(dto.getTermino()));
		entity.setOfertaDisciplina(ofertaDisciplinaService.find(dto.getOfertaDisciplina()));

		return entity;
	}

	@Override
	public void validadeForInsertUpdate(AulaDto dto) {
		String error = "";

		if (dto.getInicio() == null) {
			error += "Data e hora de início pendente; ";
		}

		if (dto.getTermino() == null) {
			error += "Data e hora de término pendente; ";
		}

		if (dto.getInicio() != null && dto.getTermino() == null && dto.getInicio() > dto.getTermino()) {
			error += "Data e hora de início posterior à de término; ";
		}

		if (dto.getOfertaDisciplina() == null) {
			error += "Oferta de disciplina pendente; ";
		} else {
			try {
				ofertaDisciplinaService.find(dto.getOfertaDisciplina());
			} catch (ObjectNotFoundException e) {
				error += "Oferta de disciplina com ID " + dto.getOfertaDisciplina() + " não existente; ";
			}
		}

		if (!error.isEmpty()) {
			throw new FieldValidationException(dto.getId(), getEntityClass(), error);
		}
	}

}
