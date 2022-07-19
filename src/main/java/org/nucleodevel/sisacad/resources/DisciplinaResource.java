package org.nucleodevel.sisacad.resources;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.nucleodevel.sisacad.dto.DisciplinaDto;
import org.nucleodevel.sisacad.dto.EstruturaCurricularDto;
import org.nucleodevel.sisacad.dto.OfertaDisciplinaDto;
import org.nucleodevel.sisacad.services.DisciplinaService;
import org.nucleodevel.sisacad.services.EstruturaCurricularService;
import org.nucleodevel.sisacad.services.OfertaDisciplinaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/disciplina")
public class DisciplinaResource extends AbstractResource<DisciplinaDto, Integer, DisciplinaService> {

	@Autowired
	private EstruturaCurricularService estruturaCurricularService;

	@RequestMapping(value = "/{id}/estrutura-curricular", method = RequestMethod.GET)
	public ResponseEntity<List<EstruturaCurricularDto>> findAllEstruturaCurricular(@PathVariable Integer id)
			throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {

		return findAllSubList(EstruturaCurricularService.class, EstruturaCurricularDto.class, id);
	}

	@RequestMapping(value = "/{id}/estrutura-curricular/{itemId}", method = RequestMethod.POST)
	public ResponseEntity<Void> insertEstruturaCurricular(@PathVariable Integer id, @PathVariable Integer itemId)
			throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {

		return insertSubList(id, itemId, estruturaCurricularService);
	}

	@RequestMapping(value = "/{id}/estrutura-curricular/{itemId}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteEstruturaCurricular(@PathVariable Integer id, @PathVariable Integer itemId)
			throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {

		return deleteSubList(id, itemId, estruturaCurricularService);
	}

	@RequestMapping(value = "/{id}/oferta-disciplina", method = RequestMethod.GET)
	public ResponseEntity<List<OfertaDisciplinaDto>> findAllOfertaDisciplina(@PathVariable Integer id)
			throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {

		return findAllSubList(OfertaDisciplinaService.class, OfertaDisciplinaDto.class, id);
	}

}
