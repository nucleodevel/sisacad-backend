package org.nucleodevel.sisacad.services;

import java.util.List;
import java.util.Optional;

import org.nucleodevel.sisacad.domain.OfertaCurso;
import org.nucleodevel.sisacad.domain.Turma;
import org.nucleodevel.sisacad.repositories.TurmaRepository;
import org.nucleodevel.sisacad.services.exceptions.DataIntegrityException;
import org.nucleodevel.sisacad.services.exceptions.FieldValidationException;
import org.nucleodevel.sisacad.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class TurmaService extends AbstractService<Turma, Integer, TurmaRepository> {

	@Autowired
	private OfertaCursoService ofertaCursoService;

	@Override
	public void validadeForInsertUpdate(Turma entity) {
		String error = "";

		if (!StringUtils.hasText(entity.getCodigo())) {
			error += "Código pendente; ";
		}

		OfertaCurso myOfertaCurso = null;
		if (entity.getOfertaCurso() == null) {
			error += "Oferta de curso pendente; ";
		} else {
			try {
				myOfertaCurso = ofertaCursoService.find(entity.getOfertaCurso().getId());
			} catch (ObjectNotFoundException e) {
				error += "Oferta de curso com ID " + entity.getOfertaCurso().getId() + " não existente; ";
			}
		}

		if (!error.isEmpty()) {
			throw new FieldValidationException(entity.getId(), getEntityClass(), error);
		}

		String myCodigo = entity.getCodigo();

		Optional<Turma> similar = repository.findSimilarByCodigoOrOfertaCurso(entity.getId(), myCodigo, myOfertaCurso);
		similar.ifPresent(obj -> {
			throw new DataIntegrityException("Já existe uma turma com esse código ou para essa oferta de curso!");
		});
	}

	@Override
	public List<Turma> findAll() {
		return repository.findByOrderByOfertaCursoAsc();
	}

}
