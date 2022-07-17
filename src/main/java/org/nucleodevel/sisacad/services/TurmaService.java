package org.nucleodevel.sisacad.services;

import java.util.Optional;

import org.nucleodevel.sisacad.domain.OfertaCurso;
import org.nucleodevel.sisacad.domain.Turma;
import org.nucleodevel.sisacad.repositories.TurmaRepository;
import org.nucleodevel.sisacad.services.exceptions.DataIntegrityException;
import org.springframework.stereotype.Service;

@Service
public class TurmaService extends AbstractService<Turma, Integer, TurmaRepository> {

	@Override
	public void validadeForInsertUpdate(Turma entity) {
		OfertaCurso myOfertaCurso = entity.getOfertaCurso();

		Optional<Turma> similar = repo.findByNotIdAndOfertaCurso(entity.getId(), myOfertaCurso);
		similar.ifPresent(obj -> {
			throw new DataIntegrityException("Já existe uma turma para esta oferta de curso!");
		});
	}

}
