package org.nucleodevel.sisacad.services;

import java.util.Optional;

import org.nucleodevel.sisacad.domain.Docente;
import org.nucleodevel.sisacad.dto.DocenteDto;
import org.nucleodevel.sisacad.repositories.DocenteRepository;
import org.nucleodevel.sisacad.services.exceptions.DataIntegrityException;
import org.nucleodevel.sisacad.services.exceptions.FieldValidationException;
import org.springframework.stereotype.Service;

@Service
public class DocenteService extends AbstractService<Docente, Integer, DocenteDto, DocenteRepository> {

	@Override
	public void validadeForInsertUpdate(Docente entity) {
		String error = "";

		if (entity.getNome() == null) {
			error += "Nome pendente; ";
		}

		if (!error.isEmpty()) {
			throw new FieldValidationException(entity.getId(), getEntityClass(), error);
		}

		String myNome = entity.getNome();

		Optional<Docente> similar = repo.findDifferentByNome(entity.getId(), myNome);
		similar.ifPresent(obj -> {
			throw new DataIntegrityException("Já existe um docente com este nome!");
		});
	}

	@Override
	public void validadeForInsertUpdate(DocenteDto dto) {
		String error = "";

		if (dto.getNome() == null) {
			error += "Nome pendente; ";
		}

		if (!error.isEmpty()) {
			throw new FieldValidationException(dto.getId(), getEntityClass(), error);
		}
	}

}
