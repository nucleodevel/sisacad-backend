package org.nucleodevel.sisacad.services;

import java.util.List;
import java.util.Optional;

import org.nucleodevel.sisacad.domain.Curso;
import org.nucleodevel.sisacad.dto.CursoDto;
import org.nucleodevel.sisacad.repositories.CursoRepository;
import org.nucleodevel.sisacad.services.exceptions.DataIntegrityException;
import org.nucleodevel.sisacad.services.exceptions.FieldValidationException;
import org.springframework.stereotype.Service;

@Service
public class CursoService extends AbstractService<Curso, Integer, CursoDto, CursoRepository> {

	@Override
	public Curso mergeDtoIntoEntity(CursoDto dto, Curso entity) {
		entity.setId(dto.getId());
		entity.setCodigo(dto.getCodigo());
		entity.setNome(dto.getNome());

		return entity;
	}

	@Override
	public void validadeForInsertUpdate(CursoDto dto) {
		String error = "";

		if (dto.getCodigo() == null) {
			error += "Código pendente; ";
		}

		if (dto.getNome() == null) {
			error += "Nome pendente; ";
		}

		if (!error.isEmpty()) {
			throw new FieldValidationException(dto.getId(), getEntityClass(), error);
		}

		String myCodigo = dto.getCodigo();

		Optional<Curso> similar = repository.findSimilarByCodigo(dto.getId(), myCodigo);
		similar.ifPresent(obj -> {
			throw new DataIntegrityException("Já existe um curso com este nome!");
		});
	}

	@Override
	protected List<Curso> findAll() {
		return repository.findByOrderByNome();
	}

}
