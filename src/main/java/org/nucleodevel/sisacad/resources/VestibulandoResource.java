package org.nucleodevel.sisacad.resources;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.nucleodevel.sisacad.domain.OfertaCurso;
import org.nucleodevel.sisacad.domain.Vestibulando;
import org.nucleodevel.sisacad.dto.VestibulandoDto;
import org.nucleodevel.sisacad.security.Role;
import org.nucleodevel.sisacad.services.OfertaCursoService;
import org.nucleodevel.sisacad.services.VestibulandoService;
import org.nucleodevel.sisacad.services.exceptions.FieldValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/vestibulando")
public class VestibulandoResource
		extends AbstractResource<Vestibulando, VestibulandoDto, Integer, VestibulandoService> {

	@Autowired
	private OfertaCursoService ofertaCursoService;

	@Override
	public List<Role> getListAllowedRoleToRead() {
		return List.of(Role.VESTIBULANDO);
	}

	@Override
	public List<Role> getListAllowedRoleToWrite() {
		return List.of(Role.PEDAGOGICO);
	}

	@Override
	public Vestibulando mergeDtoIntoEntity(VestibulandoDto dto, Vestibulando entity) {
		String error = "";

		entity.setId(dto.getId());
		entity.setCpf(dto.getCpf());
		entity.setNome(dto.getNome());
		entity.setDataNascimento(dto.getDataNascimento() == null ? null : new Date(dto.getDataNascimento()));
		entity.setEndereco(dto.getEndereco());
		entity.setTelefones(dto.getTelefones());

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

		if (!error.isEmpty()) {
			throw new FieldValidationException(entity.getId(), getEntityClass(), error);
		}

		return entity;
	}

	@RequestMapping(value = "/is-not-discente", method = RequestMethod.GET)
	public ResponseEntity<List<VestibulandoDto>> findByAulaAndDiscente()
			throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {

		validatePermissionsToRead();

		List<Vestibulando> listEntity = service.findListByIsNotDiscente();
		List<VestibulandoDto> listDto = new ArrayList<>();

		listEntity.forEach((entity) -> {
			VestibulandoDto dto = new VestibulandoDto();
			dto.copyFromEntity(entity);
			listDto.add(dto);
		});

		return ResponseEntity.ok().body(listDto);
	}

}
