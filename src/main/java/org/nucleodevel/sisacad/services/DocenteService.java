package org.nucleodevel.sisacad.services;

import java.util.Optional;

import org.nucleodevel.sisacad.domain.Docente;
import org.nucleodevel.sisacad.repositories.DocenteRepository;
import org.nucleodevel.sisacad.services.exceptions.DataIntegrityException;
import org.springframework.stereotype.Service;

@Service
public class DocenteService extends AbstractService<Docente, Integer, DocenteRepository> {

	@Override
	public void validadeForInsertUpdate(Docente entity) {
		String myNome = entity.getNome();

		Optional<Docente> similar = repo.findDifferentByNome(entity.getId(), myNome);
		similar.ifPresent(obj -> {
			throw new DataIntegrityException("Já existe um docente com este nome!");
		});
	}

}
