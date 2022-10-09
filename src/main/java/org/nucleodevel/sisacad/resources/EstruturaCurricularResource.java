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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/estrutura-curricular")
public class EstruturaCurricularResource
		extends AbstractResource<EstruturaCurricular, EstruturaCurricularDto, Integer, EstruturaCurricularService> {

	@Autowired
	private CursoService cursoService;
	@Autowired
	private DisciplinaService disciplinaService;

	@Override
	public List<Role> getListAllowedRoleToRead() {
		return List.of(Role.DISCENTE, Role.DOCENTE);
	}

	@Override
	public List<Role> getListAllowedRoleToWrite() {
		return List.of(Role.PEDAGOGICO);
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

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<EstruturaCurricularDto> find(@PathVariable Integer id) {
		return super.find(id);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<EstruturaCurricularDto> insert(@RequestBody EstruturaCurricularDto dto) {
		return super.insert(dto);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@RequestBody EstruturaCurricularDto dto, @PathVariable Integer id) {
		return super.update(dto, id);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		return super.delete(id);
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<EstruturaCurricularDto>> findAll() {
		return super.findAll();
	}

	@RequestMapping(value = "/{id}/oferta-curso", method = RequestMethod.GET)
	public ResponseEntity<List<OfertaCursoDto>> findAllOfertaCurso(@PathVariable Integer id)
			throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {

		validatePermissionsToRead();
		return findAllSubList(OfertaCursoService.class, OfertaCursoDto.class, id);
	}

	@RequestMapping(value = "/{id}/disciplina", method = RequestMethod.GET)
	public ResponseEntity<List<DisciplinaDto>> findAllDisciplina(@PathVariable Integer id)
			throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {

		validatePermissionsToRead();
		return findAllSubList(DisciplinaService.class, DisciplinaDto.class, id);
	}

	@RequestMapping(value = "/{id}/disciplina/{itemId}", method = RequestMethod.POST)
	public ResponseEntity<Void> insertDisciplina(@PathVariable Integer id, @PathVariable Integer itemId)
			throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {

		validatePermissionsToWrite();
		return insertSubList(id, itemId, disciplinaService);
	}

	@RequestMapping(value = "/{id}/disciplina/{itemId}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteDisciplina(@PathVariable Integer id, @PathVariable Integer itemId)
			throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {

		validatePermissionsToWrite();
		return deleteSubList(id, itemId, disciplinaService);
	}

}
