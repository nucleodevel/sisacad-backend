package org.nucleodevel.sisacad.services;

import java.util.Optional;

import org.nucleodevel.sisacad.domain.OfertaCurso;
import org.nucleodevel.sisacad.domain.Vestibulando;
import org.nucleodevel.sisacad.repositories.VestibulandoRepository;
import org.nucleodevel.sisacad.services.exceptions.DataIntegrityException;
import org.springframework.stereotype.Service;

@Service
public class VestibulandoService extends AbstractService<Vestibulando, Integer, VestibulandoRepository> {

	@Override
	public void validadeForInsertUpdate(Vestibulando entity) {
		OfertaCurso myOfertaCurso = entity.getOfertaCurso();
		String myNome = entity.getNome();

		Optional<Vestibulando> similar = repo.findDifferentByOfertaCursoAndNome(entity.getId(), myOfertaCurso, myNome);
		similar.ifPresent(obj -> {
			throw new DataIntegrityException("Já existe um cadastro com esse nome nessa oferta de curso!");
		});
	}

}
