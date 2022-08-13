package org.nucleodevel.sisacad.resources;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.nucleodevel.sisacad.domain.OfertaCurso;
import org.nucleodevel.sisacad.domain.Turma;
import org.nucleodevel.sisacad.dto.OfertaDisciplinaDto;
import org.nucleodevel.sisacad.dto.TurmaDto;
import org.nucleodevel.sisacad.services.OfertaCursoService;
import org.nucleodevel.sisacad.services.OfertaDisciplinaService;
import org.nucleodevel.sisacad.services.TurmaService;
import org.nucleodevel.sisacad.services.exceptions.FieldValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(value = "/turma")
public class TurmaResource extends AbstractResource<Turma, TurmaDto, Integer, TurmaService> {

	@Autowired
	private OfertaCursoService ofertaCursoService;
	@Autowired
	private OfertaDisciplinaService ofertaDisciplinaService;

	@Override
	public Turma mergeDtoIntoEntity(TurmaDto dto, Turma entity) {
		String error = "";

		entity.setId(dto.getId());
		entity.setCodigo(dto.getCodigo());

		if (dto.getOfertaCurso() != null) {
			OfertaCurso ofertaCurso = ofertaCursoService.find(dto.getOfertaCurso());
			if (ofertaCurso == null) {
				error += "OfertaCurso com ID " + entity.getOfertaCurso().getId() + " não existente; ";
			}
			entity.setOfertaCurso(ofertaCurso);
		} else {
			entity.setOfertaCurso(null);
		}

		if (!error.isEmpty()) {
			throw new FieldValidationException(entity.getId(), getEntityClass(), error);
		}

		return entity;
	}

	@RequestMapping(value = "/{id}/oferta-disciplina", method = RequestMethod.GET)
	public ResponseEntity<List<OfertaDisciplinaDto>> findAllOfertaDisciplina(@PathVariable Integer id)
			throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {

		return findAllSubList(OfertaDisciplinaService.class, OfertaDisciplinaDto.class, id);
	}

	@RequestMapping(value = "/{id}/oferta-disciplina/{itemId}", method = RequestMethod.POST)
	public ResponseEntity<Void> insertOfertaDisciplina(@PathVariable Integer id, @PathVariable Integer itemId)
			throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {

		return insertSubList(id, itemId, ofertaDisciplinaService);
	}

	@RequestMapping(value = "/{id}/oferta-disciplina/{itemId}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteOfertaDisciplina(@PathVariable Integer id, @PathVariable Integer itemId)
			throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {

		return deleteSubList(id, itemId, ofertaDisciplinaService);
	}

}
