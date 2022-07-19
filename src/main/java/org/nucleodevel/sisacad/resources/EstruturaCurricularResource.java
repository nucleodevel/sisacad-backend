package org.nucleodevel.sisacad.resources;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.nucleodevel.sisacad.dto.DisciplinaDto;
import org.nucleodevel.sisacad.dto.EstruturaCurricularDto;
import org.nucleodevel.sisacad.dto.OfertaCursoDto;
import org.nucleodevel.sisacad.services.DisciplinaService;
import org.nucleodevel.sisacad.services.EstruturaCurricularService;
import org.nucleodevel.sisacad.services.OfertaCursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/estrutura-curricular")
public class EstruturaCurricularResource
		extends AbstractResource<EstruturaCurricularDto, Integer, EstruturaCurricularService> {

	@Autowired
	private DisciplinaService disciplinaService;

	@RequestMapping(value = "/{id}/oferta-curso", method = RequestMethod.GET)
	public ResponseEntity<List<OfertaCursoDto>> findAllOfertaCurso(@PathVariable Integer id)
			throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {

		return findAllItem(OfertaCursoService.class, OfertaCursoDto.class, id);
	}

	@RequestMapping(value = "/{id}/disciplina", method = RequestMethod.GET)
	public ResponseEntity<List<DisciplinaDto>> findAllDisciplina(@PathVariable Integer id)
			throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {

		return findAllItem(DisciplinaService.class, DisciplinaDto.class, id);
	}

	@RequestMapping(value = "/{id}/disciplina/{itemId}", method = RequestMethod.POST)
	public ResponseEntity<Void> insertDisciplina(@PathVariable Integer id, @PathVariable Integer itemId)
			throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {

		return insertItem(DisciplinaService.class, DisciplinaDto.class, id, itemId, disciplinaService);
	}

	@RequestMapping(value = "/{id}/disciplina/{itemId}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteDisciplina(@PathVariable Integer id, @PathVariable Integer itemId)
			throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {

		return deleteItem(DisciplinaService.class, DisciplinaDto.class, id, itemId, disciplinaService);
	}

}
