package org.nucleodevel.sisacad.services;

import java.util.Optional;

import org.nucleodevel.sisacad.domain.OfertaCurso;
import org.nucleodevel.sisacad.domain.Turma;
import org.nucleodevel.sisacad.dto.TurmaDto;
import org.nucleodevel.sisacad.repositories.TurmaRepository;
import org.nucleodevel.sisacad.services.exceptions.DataIntegrityException;
import org.nucleodevel.sisacad.services.exceptions.FieldValidationException;
import org.nucleodevel.sisacad.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TurmaService extends AbstractService<Turma, Integer, TurmaDto, TurmaRepository> {

	@Autowired
	private OfertaCursoService ofertaCursoService;

	@Override
	public void validadeForInsertUpdate(Turma entity) {
		String error = "";

		if (entity.getOfertaCurso() == null) {
			error += "Oferta de curso pendente; ";
		}

		if (!error.isEmpty()) {
			throw new FieldValidationException(entity.getId(), getEntityClass(), error);
		}

		OfertaCurso myOfertaCurso = entity.getOfertaCurso();

		Optional<Turma> similar = repo.findByNotIdAndOfertaCurso(entity.getId(), myOfertaCurso);
		similar.ifPresent(obj -> {
			throw new DataIntegrityException("Já existe uma turma para esta oferta de curso!");
		});
	}

	@Override
	public void validadeForInsertUpdate(TurmaDto dto) {
		String error = "";

		if (dto.getOfertaCurso() == null) {
			error += "Oferta de curso pendente; ";
		} else {
			try {
				ofertaCursoService.find(dto.getOfertaCurso());
			} catch (ObjectNotFoundException e) {
				error += "Oferta de curso com ID " + dto.getOfertaCurso() + " não existente; ";
			}
		}

		if (!error.isEmpty()) {
			throw new FieldValidationException(dto.getId(), getEntityClass(), error);
		}
	}

}
