package org.nucleodevel.sisacad.services;

import java.util.List;
import java.util.Optional;

import org.nucleodevel.sisacad.domain.Curso;
import org.nucleodevel.sisacad.repositories.CursoRepository;
import org.nucleodevel.sisacad.services.exceptions.DataIntegrityException;
import org.nucleodevel.sisacad.services.exceptions.FieldValidationException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class CursoService extends AbstractService<Curso, Integer, CursoRepository> {

	@Override
	public void validadeForInsertUpdate(Curso entity) {
		String error = "";

		if (!StringUtils.hasText(entity.getCodigo())) {
			error += "Código pendente; ";
		}

		if (!StringUtils.hasText(entity.getNome())) {
			error += "Nome pendente; ";
		}

		if (!error.isEmpty()) {
			throw new FieldValidationException(entity.getId(), getEntityClass(), error);
		}

		String myCodigo = entity.getCodigo();

		Optional<Curso> similarByCodigo = repository.findSimilarByCodigo(entity.getId(), myCodigo);
		similarByCodigo.ifPresent(obj -> {
			throw new DataIntegrityException("Já existe um curso com este código!");
		});

		String myNome = entity.getNome();

		Optional<Curso> similarByNome = repository.findSimilarByNome(entity.getId(), myNome);
		similarByNome.ifPresent(obj -> {
			throw new DataIntegrityException("Já existe um curso com este nome!");
		});
	}

	@Override
	public List<Curso> findAll() {
		return repository.findByOrderByNomeAsc();
	}

}
