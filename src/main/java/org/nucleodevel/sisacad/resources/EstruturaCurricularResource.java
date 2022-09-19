package org.nucleodevel.sisacad.resources;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.nucleodevel.sisacad.domain.Curso;
import org.nucleodevel.sisacad.domain.EstruturaCurricular;
import org.nucleodevel.sisacad.dto.DisciplinaDto;
import org.nucleodevel.sisacad.dto.EstruturaCurricularDto;
import org.nucleodevel.sisacad.dto.OfertaCursoDto;
import org.nucleodevel.sisacad.security.Role;
import org.nucleodevel.sisacad.services.CursoService;
import org.nucleodevel.sisacad.services.DisciplinaService;
import org.nucleodevel.sisacad.services.EstruturaCurricularService;
import org.nucleodevel.sisacad.services.OfertaCursoService;
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
@RequestMapping(value = "/estrutura-curricular")
public class EstruturaCurricularResource
		extends AbstractResource<EstruturaCurricular, EstruturaCurricularDto, Integer, EstruturaCurricularService> {

	@Autowired
	private CursoService cursoService;
	@Autowired
	private DisciplinaService disciplinaService;

	@Override
	public List<Role> getSpecificListAllowedRole() {
		return List.of(Role.USER);
	}

	@Override
	public EstruturaCurricular mergeDtoIntoEntity(EstruturaCurricularDto dto, EstruturaCurricular entity) {
		String error = "";

		entity.setId(dto.getId());
		entity.setCodigo(dto.getCodigo());
		entity.setAnoInicio(dto.getAnoInicio());
		entity.setAnoTermino(dto.getAnoTermino());

		if (dto.getCurso() != null) {
			Curso curso = cursoService.find(dto.getCurso());
			if (curso == null) {
				error += "Curso com ID " + entity.getCurso().getId() + " não existente; ";
			}
			entity.setCurso(curso);
		} else {
			entity.setCurso(null);
		}

		if (!error.isEmpty()) {
			throw new FieldValidationException(entity.getId(), getEntityClass(), error);
		}

		return entity;
	}

	@RequestMapping(value = "/{id}/oferta-curso", method = RequestMethod.GET)
	public ResponseEntity<List<OfertaCursoDto>> findAllOfertaCurso(@PathVariable Integer id)
			throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {

		validatePermissions();
		return findAllSubList(OfertaCursoService.class, OfertaCursoDto.class, id);
	}

	@RequestMapping(value = "/{id}/disciplina", method = RequestMethod.GET)
	public ResponseEntity<List<DisciplinaDto>> findAllDisciplina(@PathVariable Integer id)
			throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {

		validatePermissions();
		return findAllSubList(DisciplinaService.class, DisciplinaDto.class, id);
	}

	@RequestMapping(value = "/{id}/disciplina/{itemId}", method = RequestMethod.POST)
	public ResponseEntity<Void> insertDisciplina(@PathVariable Integer id, @PathVariable Integer itemId)
			throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {

		validatePermissions();
		return insertSubList(id, itemId, disciplinaService);
	}

	@RequestMapping(value = "/{id}/disciplina/{itemId}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteDisciplina(@PathVariable Integer id, @PathVariable Integer itemId)
			throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {

		validatePermissions();
		return deleteSubList(id, itemId, disciplinaService);
	}

}
