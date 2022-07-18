package org.nucleodevel.sisacad.resources;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.nucleodevel.sisacad.domain.OfertaDisciplina;
import org.nucleodevel.sisacad.domain.Turma;
import org.nucleodevel.sisacad.dto.OfertaDisciplinaDto;
import org.nucleodevel.sisacad.dto.TurmaDto;
import org.nucleodevel.sisacad.repositories.TurmaRepository;
import org.nucleodevel.sisacad.services.OfertaCursoService;
import org.nucleodevel.sisacad.services.OfertaDisciplinaService;
import org.nucleodevel.sisacad.services.TurmaService;
import org.nucleodevel.sisacad.services.exceptions.FieldValidationException;
import org.nucleodevel.sisacad.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/turma")
public class TurmaResource extends AbstractResource<Turma, Integer, TurmaDto, TurmaRepository, TurmaService> {

	@Autowired
	private OfertaCursoService ofertaCursoService;
	@Autowired
	private OfertaDisciplinaService ofertaDisciplinaService;

	@Override
	public Turma mergeDtoIntoEntity(TurmaDto dto, Turma entity) {
		entity.setId(dto.getId());
		entity.setOfertaCurso(ofertaCursoService.find(dto.getOfertaCurso()));

		return entity;
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

	@RequestMapping(value = "/{id}/oferta-disciplina", method = RequestMethod.GET)
	public ResponseEntity<List<OfertaDisciplinaDto>> findAllOfertaDisciplina(@PathVariable Integer id)
			throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {

		return findAllItem(OfertaDisciplina.class, OfertaDisciplinaDto.class, id, "getListaOfertaDisciplina");
	}

	@RequestMapping(value = "/{id}/oferta-disciplina/{itemId}", method = RequestMethod.POST)
	public ResponseEntity<Void> insertOfertaDisciplina(@PathVariable Integer id, @PathVariable Integer itemId)
			throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {

		return insertItem(OfertaDisciplina.class, OfertaDisciplinaDto.class, id, "getListaOfertaDisciplina",
				ofertaDisciplinaService.find(itemId));
	}

	@RequestMapping(value = "/{id}/oferta-disciplina/{itemId}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteOfertaDisciplina(@PathVariable Integer id, @PathVariable Integer itemId)
			throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {

		return deleteItem(OfertaDisciplina.class, OfertaDisciplinaDto.class, id, "getListaOfertaDisciplina",
				ofertaDisciplinaService.find(itemId));
	}

}
