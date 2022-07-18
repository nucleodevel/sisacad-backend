package org.nucleodevel.sisacad.services;

import java.util.Optional;

import org.nucleodevel.sisacad.domain.Disciplina;
import org.nucleodevel.sisacad.repositories.DisciplinaRepository;
import org.nucleodevel.sisacad.services.exceptions.DataIntegrityException;
import org.springframework.stereotype.Service;

@Service
public class DisciplinaService extends AbstractService<Disciplina, Integer, DisciplinaRepository> {

	@Override
	public void validadeForInsertUpdate(Disciplina entity) {
		String myNome = entity.getNome();

		Optional<Disciplina> similar = repo.findDifferentByNome(entity.getId(), myNome);
		similar.ifPresent(obj -> {
			throw new DataIntegrityException("Já existe uma disciplina com este nome!");
		});
	}

}
