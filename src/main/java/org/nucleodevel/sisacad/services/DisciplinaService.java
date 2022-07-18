package org.nucleodevel.sisacad.services;

import java.util.Optional;

import org.nucleodevel.sisacad.domain.Disciplina;
import org.nucleodevel.sisacad.dto.DisciplinaDto;
import org.nucleodevel.sisacad.repositories.DisciplinaRepository;
import org.nucleodevel.sisacad.services.exceptions.DataIntegrityException;
import org.nucleodevel.sisacad.services.exceptions.FieldValidationException;
import org.springframework.stereotype.Service;

@Service
public class DisciplinaService extends AbstractService<Disciplina, Integer, DisciplinaDto, DisciplinaRepository> {

	@Override
	public void validadeForInsertUpdate(Disciplina entity) {
		String error = "";

		if (entity.getNome() == null) {
			error += "Nome pendente; ";
		}

		if (!error.isEmpty()) {
			throw new FieldValidationException(entity.getId(), getEntityClass(), error);
		}

		String myNome = entity.getNome();

		Optional<Disciplina> similar = repo.findDifferentByNome(entity.getId(), myNome);
		similar.ifPresent(obj -> {
			throw new DataIntegrityException("Já existe uma disciplina com este nome!");
		});
	}

	@Override
	public void validadeForInsertUpdate(DisciplinaDto dto) {
		String error = "";

		if (dto.getNome() == null) {
			error += "Nome pendente; ";
		}

		if (!error.isEmpty()) {
			throw new FieldValidationException(dto.getId(), getEntityClass(), error);
		}
	}

}
