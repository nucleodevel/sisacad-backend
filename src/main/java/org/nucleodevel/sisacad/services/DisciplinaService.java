package org.nucleodevel.sisacad.services;

import java.util.List;
import java.util.Optional;

import org.nucleodevel.sisacad.domain.Disciplina;
import org.nucleodevel.sisacad.repositories.DisciplinaRepository;
import org.nucleodevel.sisacad.services.exceptions.DataIntegrityException;
import org.nucleodevel.sisacad.services.exceptions.FieldValidationException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class DisciplinaService extends AbstractService<Disciplina, Integer, DisciplinaRepository> {

	@Override
	public void validadeForInsertUpdate(Disciplina entity) {
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

		Optional<Disciplina> similarByCodigo = repository.findSimilarByCodigo(entity.getId(), myCodigo);
		similarByCodigo.ifPresent(obj -> {
			throw new DataIntegrityException("Já existe uma disciplina com este código!");
		});

		String myNome = entity.getNome();

		Optional<Disciplina> similarByNome = repository.findSimilarByNome(entity.getId(), myNome);
		similarByNome.ifPresent(obj -> {
			throw new DataIntegrityException("Já existe uma disciplina com este nome!");
		});
	}

	@Override
	public List<Disciplina> findAll() {
		return repository.findByOrderByNomeAsc();
	}

}
