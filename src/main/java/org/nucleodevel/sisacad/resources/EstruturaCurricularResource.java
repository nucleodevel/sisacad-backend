package org.nucleodevel.sisacad.resources;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.nucleodevel.sisacad.domain.Disciplina;
import org.nucleodevel.sisacad.domain.EstruturaCurricular;
import org.nucleodevel.sisacad.domain.OfertaCurso;
import org.nucleodevel.sisacad.dto.DisciplinaDto;
import org.nucleodevel.sisacad.dto.EstruturaCurricularDto;
import org.nucleodevel.sisacad.dto.OfertaCursoDto;
import org.nucleodevel.sisacad.repositories.EstruturaCurricularRepository;
import org.nucleodevel.sisacad.services.CursoService;
import org.nucleodevel.sisacad.services.DisciplinaService;
import org.nucleodevel.sisacad.services.EstruturaCurricularService;
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
public class EstruturaCurricularResource extends
		AbstractResource<EstruturaCurricular, Integer, EstruturaCurricularDto, EstruturaCurricularRepository, EstruturaCurricularService> {

	@Autowired
	private CursoService cursoService;
	@Autowired
	private DisciplinaService disciplinaService;

	@Override
	public EstruturaCurricular mergeDtoIntoEntity(EstruturaCurricularDto dto, EstruturaCurricular entity) {
		entity.setId(dto.getId());
		entity.setAnoInicio(dto.getAnoInicio());
		entity.setAnoTermino(dto.getAnoTermino());
		entity.setCurso(cursoService.find(dto.getCurso()));

		return entity;
	}

	@RequestMapping(value = "/{id}/oferta-curso", method = RequestMethod.GET)
	public ResponseEntity<List<OfertaCursoDto>> findAllOfertaCurso(@PathVariable Integer id)
			throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {

		return findAllItem(OfertaCurso.class, OfertaCursoDto.class, id, "getListaOfertaCurso");
	}

	@RequestMapping(value = "/{id}/disciplina", method = RequestMethod.GET)
	public ResponseEntity<List<DisciplinaDto>> findAllDisciplina(@PathVariable Integer id)
			throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {

		return findAllItem(Disciplina.class, DisciplinaDto.class, id, "getListaDisciplina");
	}

	@RequestMapping(value = "/{id}/disciplina/{itemId}", method = RequestMethod.POST)
	public ResponseEntity<Void> insertDisciplina(@PathVariable Integer id, @PathVariable Integer itemId)
			throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {

		return insertItem(Disciplina.class, DisciplinaDto.class, id, "getListaDisciplina",
				disciplinaService.find(itemId));
	}

	@RequestMapping(value = "/{id}/disciplina/{itemId}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteDisciplina(@PathVariable Integer id, @PathVariable Integer itemId)
			throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {

		return deleteItem(Disciplina.class, DisciplinaDto.class, id, "getListaDisciplina",
				disciplinaService.find(itemId));
	}

}
