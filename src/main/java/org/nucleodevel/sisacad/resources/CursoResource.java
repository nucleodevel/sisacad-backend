package org.nucleodevel.sisacad.resources;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.nucleodevel.sisacad.domain.Curso;
import org.nucleodevel.sisacad.domain.EstruturaCurricular;
import org.nucleodevel.sisacad.dto.CursoDto;
import org.nucleodevel.sisacad.dto.EstruturaCurricularDto;
import org.nucleodevel.sisacad.security.Role;
import org.nucleodevel.sisacad.services.CursoService;
import org.nucleodevel.sisacad.services.EstruturaCurricularService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/curso")
public class CursoResource extends AbstractResource<Curso, CursoDto, Integer, CursoService> {

	@Override
	public List<Role> getListAllowedRoleToRead() {
		return List.of(Role.USER);
	}

	@Override
	public List<Role> getListAllowedRoleToWrite() {
		return List.of(Role.PEDAGOGICO);
	}

	@Override
	public Curso mergeDtoIntoEntity(CursoDto dto, Curso entity) {
		entity.setId(dto.getId());
		entity.setCodigo(dto.getCodigo());
		entity.setNome(dto.getNome());

		return entity;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<CursoDto> find(@PathVariable Integer id) {
		return super.find(id);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<CursoDto> insert(@RequestBody CursoDto dto) {
		return super.insert(dto);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@RequestBody CursoDto dto, @PathVariable Integer id) {
		return super.update(dto, id);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		return super.delete(id);
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<CursoDto>> findAll() {
		return super.findAll();
	}

	@RequestMapping(value = "/{id}/estrutura-curricular", method = RequestMethod.GET)
	public ResponseEntity<List<EstruturaCurricularDto>> findAllEstruturaCurricular(@PathVariable Integer id)
			throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {

		validatePermissionsToRead();
		return findAllSubList(EstruturaCurricularService.class, EstruturaCurricular.class, EstruturaCurricularDto.class,
				id);
	}

}
