package org.nucleodevel.sisacad.resources;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.nucleodevel.sisacad.domain.EstruturaCurricular;
import org.nucleodevel.sisacad.domain.OfertaCurso;
import org.nucleodevel.sisacad.domain.Vestibular;
import org.nucleodevel.sisacad.dto.OfertaCursoDto;
import org.nucleodevel.sisacad.dto.VestibulandoDto;
import org.nucleodevel.sisacad.security.Role;
import org.nucleodevel.sisacad.services.EstruturaCurricularService;
import org.nucleodevel.sisacad.services.OfertaCursoService;
import org.nucleodevel.sisacad.services.VestibulandoService;
import org.nucleodevel.sisacad.services.VestibularService;
import org.nucleodevel.sisacad.services.exceptions.FieldValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/oferta-curso")
public class OfertaCursoResource extends AbstractResource<OfertaCurso, OfertaCursoDto, Integer, OfertaCursoService> {

	@Autowired
	private EstruturaCurricularService estruturaCurricularService;
	@Autowired
	private VestibularService vestibularService;

	@Override
	public List<Role> getListAllowedRoleToRead() {
		return List.of(Role.DISCENTE, Role.DOCENTE);
	}

	@Override
	public List<Role> getListAllowedRoleToWrite() {
		return List.of(Role.PEDAGOGICO);
	}

	@Override
	public OfertaCurso mergeDtoIntoEntity(OfertaCursoDto dto, OfertaCurso entity) {
		String error = "";

		entity.setId(dto.getId());
		entity.setCodigo(dto.getCodigo());
		entity.setAno(dto.getAno());

		if (dto.getEstruturaCurricular() != null) {
			EstruturaCurricular estruturaCurricular = estruturaCurricularService.find(dto.getEstruturaCurricular());
			if (estruturaCurricular == null) {
				error += "EstruturaCurricular com ID " + entity.getEstruturaCurricular().getId() + " não existente; ";
			}
			entity.setEstruturaCurricular(estruturaCurricular);
		} else {
			entity.setEstruturaCurricular(null);
		}

		if (dto.getVestibular() != null) {
			Vestibular vestibular = vestibularService.find(dto.getVestibular());
			if (vestibular == null) {
				error += "Vestibular com ID " + entity.getVestibular().getId() + " não existente; ";
			}
			entity.setVestibular(vestibular);
		} else {
			entity.setVestibular(null);
		}

		if (!error.isEmpty()) {
			throw new FieldValidationException(entity.getId(), getEntityClass(), error);
		}

		return entity;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<OfertaCursoDto> find(@PathVariable Integer id) {
		return super.find(id);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<OfertaCursoDto> insert(@RequestBody OfertaCursoDto dto) {
		return super.insert(dto);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@RequestBody OfertaCursoDto dto, @PathVariable Integer id) {
		return super.update(dto, id);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		return super.delete(id);
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<OfertaCursoDto>> findAll() {
		return super.findAll();
	}

	@RequestMapping(value = "/{id}/vestibulando", method = RequestMethod.GET)
	public ResponseEntity<List<VestibulandoDto>> findAllVestibulando(@PathVariable Integer id)
			throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {

		validatePermissionsToRead();
		return findAllSubList(VestibulandoService.class, VestibulandoDto.class, id);
	}

}
