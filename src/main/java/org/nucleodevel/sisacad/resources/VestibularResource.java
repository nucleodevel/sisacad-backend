package org.nucleodevel.sisacad.resources;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.nucleodevel.sisacad.domain.Vestibular;
import org.nucleodevel.sisacad.dto.OfertaCursoDto;
import org.nucleodevel.sisacad.dto.VestibularDto;
import org.nucleodevel.sisacad.security.Role;
import org.nucleodevel.sisacad.services.OfertaCursoService;
import org.nucleodevel.sisacad.services.VestibularService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/vestibular")
public class VestibularResource extends AbstractResource<Vestibular, VestibularDto, Integer, VestibularService> {

	@Override
	public List<Role> getListAllowedRoleToRead() {
		return List.of(Role.PEDAGOGICO);
	}

	@Override
	public List<Role> getListAllowedRoleToWrite() {
		return List.of(Role.PEDAGOGICO);
	}

	@Override
	public Vestibular mergeDtoIntoEntity(VestibularDto dto, Vestibular entity) {
		entity.setId(dto.getId());
		entity.setAno(dto.getAno());

		return entity;
	}

	@RequestMapping(value = "/{id}/oferta-curso", method = RequestMethod.GET)
	public ResponseEntity<List<OfertaCursoDto>> findAllOfertaCurso(@PathVariable Integer id)
			throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {

		validatePermissionsToRead();
		return findAllSubList(OfertaCursoService.class, OfertaCursoDto.class, id);
	}

}
