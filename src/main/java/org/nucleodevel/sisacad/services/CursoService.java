package org.nucleodevel.sisacad.services;

import java.util.List;
import java.util.Optional;

import org.nucleodevel.sisacad.domain.Curso;
import org.nucleodevel.sisacad.repositories.CursoRepository;
import org.nucleodevel.sisacad.services.exceptions.DataIntegrityException;
import org.nucleodevel.sisacad.services.exceptions.FieldValidationException;
import org.springframework.stereotype.Service;

@Service
public class CursoService extends AbstractService<Curso, Integer, CursoRepository> {

	@Override
	public void validadeForInsertUpdate(Curso entity) {
		String error = "";

		if (entity.getCodigo() == null) {
			error += "Código pendente; ";
		}

		if (entity.getNome() == null) {
			error += "Nome pendente; ";
		}

		if (!error.isEmpty()) {
			throw new FieldValidationException(entity.getId(), getEntityClass(), error);
		}

		String myCodigo = entity.getCodigo();

		Optional<Curso> similar = repository.findSimilarByCodigo(entity.getId(), myCodigo);
		similar.ifPresent(obj -> {
			throw new DataIntegrityException("Já existe um curso com este nome!");
		});
	}

	@Override
	public List<Curso> findAll() {
		return repository.findByOrderByNomeAsc();
	}

}
