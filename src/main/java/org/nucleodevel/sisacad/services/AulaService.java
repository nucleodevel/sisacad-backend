package org.nucleodevel.sisacad.services;

import java.util.List;

import org.nucleodevel.sisacad.domain.Aula;
import org.nucleodevel.sisacad.domain.Discente;
import org.nucleodevel.sisacad.domain.Docente;
import org.nucleodevel.sisacad.repositories.AulaRepository;
import org.nucleodevel.sisacad.services.exceptions.FieldValidationException;
import org.nucleodevel.sisacad.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AulaService extends AbstractService<Aula, Integer, AulaRepository> {

	@Autowired
	private OfertaDisciplinaService ofertaDisciplinaService;

	@Override
	public void validadeForInsertUpdate(Aula entity) {
		String error = "";

		if (entity.getInicio() == null) {
			error += "Data e hora de início pendente; ";
		}

		if (entity.getTermino() == null) {
			error += "Data e hora de término pendente; ";
		}

		if (entity.getInicio() != null && entity.getTermino() != null
				&& entity.getInicio().getTime() > entity.getTermino().getTime()) {
			error += "Data e hora de início posterior à de término; ";
		}

		if (entity.getOfertaDisciplina() == null) {
			error += "Oferta de disciplina pendente; ";
		} else {
			try {
				ofertaDisciplinaService.find(entity.getOfertaDisciplina().getId());
			} catch (ObjectNotFoundException e) {
				error += "Oferta de disciplina com ID " + entity.getOfertaDisciplina().getId() + " não existente; ";
			}
		}

		if (!error.isEmpty()) {
			throw new FieldValidationException(entity.getId(), getEntityClass(), error);
		}
	}

	@Override
	public List<Aula> findAll() {
		return repository.findByOrderByInicioDescTerminoDesc();
	}

	public List<Aula> findAllByDocente(Docente item) {
		return repository.findByDocente(item);
	}

	public List<Aula> findAllByDiscente(Discente item) {
		return repository.findByDiscente(item);
	}

}
