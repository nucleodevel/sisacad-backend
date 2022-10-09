package org.nucleodevel.sisacad.services;

import java.util.List;
import java.util.Optional;

import org.nucleodevel.sisacad.domain.OfertaDisciplina;
import org.nucleodevel.sisacad.repositories.OfertaDisciplinaRepository;
import org.nucleodevel.sisacad.services.exceptions.DataIntegrityException;
import org.nucleodevel.sisacad.services.exceptions.FieldValidationException;
import org.nucleodevel.sisacad.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OfertaDisciplinaService extends AbstractService<OfertaDisciplina, Integer, OfertaDisciplinaRepository> {

	@Autowired
	private DisciplinaService disciplinaService;
	@Autowired
	private DocenteService docenteService;

	@Override
	public void validadeForInsertUpdate(OfertaDisciplina entity) {
		String error = "";

		if (entity.getCodigo() == null) {
			error += "Código pendente; ";
		}

		if (entity.getSemestre() == null) {
			error += "Semestre pendente; ";
		}

		if (entity.getDisciplina() == null) {
			error += "Disciplina pendente; ";
		} else {
			try {
				disciplinaService.find(entity.getDisciplina().getId());
			} catch (ObjectNotFoundException e) {
				error += "Disciplina com ID " + entity.getDisciplina().getId() + " não existente; ";
			}
		}

		if (entity.getDocente() == null) {
			error += "Docente pendente; ";
		} else {
			try {
				docenteService.find(entity.getDocente().getId());
			} catch (ObjectNotFoundException e) {
				error += "Docente com ID " + entity.getDocente().getId() + " não existente; ";
			}
		}

		if (!error.isEmpty()) {
			throw new FieldValidationException(entity.getId(), getEntityClass(), error);
		}

		String myCodigo = entity.getCodigo();

		Optional<OfertaDisciplina> similar = repository.findSimilarByCodigo(entity.getId(), myCodigo);
		similar.ifPresent(obj -> {
			throw new DataIntegrityException("Já existe uma oferta de disciplina com esse código!");
		});
	}

	@Override
	public List<OfertaDisciplina> findAll() {
		return repository.findByOrderByDisciplinaAscDocenteAsc();
	}

}
