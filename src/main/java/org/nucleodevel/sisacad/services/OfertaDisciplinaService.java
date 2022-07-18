package org.nucleodevel.sisacad.services;

import org.nucleodevel.sisacad.domain.OfertaDisciplina;
import org.nucleodevel.sisacad.dto.OfertaDisciplinaDto;
import org.nucleodevel.sisacad.repositories.OfertaDisciplinaRepository;
import org.nucleodevel.sisacad.services.exceptions.FieldValidationException;
import org.nucleodevel.sisacad.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OfertaDisciplinaService
		extends AbstractService<OfertaDisciplina, Integer, OfertaDisciplinaDto, OfertaDisciplinaRepository> {

	@Autowired
	private DisciplinaService disciplinaService;
	@Autowired
	private DocenteService docenteService;

	@Override
	public void validadeForInsertUpdate(OfertaDisciplina entity) {
		String error = "";

		if (entity.getDisciplina() == null) {
			error += "Disciplina pendente; ";
		}

		if (entity.getDocente() == null) {
			error += "Docente pendente; ";
		}

		if (!error.isEmpty()) {
			throw new FieldValidationException(entity.getId(), getEntityClass(), error);
		}
	}

	@Override
	public void validadeForInsertUpdate(OfertaDisciplinaDto dto) {
		String error = "";

		if (dto.getDisciplina() == null) {
			error += "Disciplina pendente; ";
		} else {
			try {
				disciplinaService.find(dto.getDisciplina());
			} catch (ObjectNotFoundException e) {
				error += "Disciplina com ID " + dto.getDisciplina() + " não existente; ";
			}
		}

		if (dto.getDocente() == null) {
			error += "Docente pendente; ";
		} else {
			try {
				docenteService.find(dto.getDocente());
			} catch (ObjectNotFoundException e) {
				error += "Docente com ID " + dto.getDocente() + " não existente; ";
			}
		}

		if (!error.isEmpty()) {
			throw new FieldValidationException(dto.getId(), getEntityClass(), error);
		}
	}

}
