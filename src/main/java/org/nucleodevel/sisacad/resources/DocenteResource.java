package org.nucleodevel.sisacad.resources;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;

import org.nucleodevel.sisacad.domain.Docente;
import org.nucleodevel.sisacad.dto.DocenteDto;
import org.nucleodevel.sisacad.dto.OfertaDisciplinaDto;
import org.nucleodevel.sisacad.security.Role;
import org.nucleodevel.sisacad.services.DocenteService;
import org.nucleodevel.sisacad.services.OfertaDisciplinaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/docente")
public class DocenteResource extends AbstractResource<Docente, DocenteDto, Integer, DocenteService> {

	@Override
	public List<Role> getListAllowedRoleToRead() {
		return List.of(Role.DISCENTE, Role.DOCENTE);
	}

	@Override
	public List<Role> getListAllowedRoleToWrite() {
		return List.of(Role.PEDAGOGICO);
	}

	@Override
	public Docente mergeDtoIntoEntity(DocenteDto dto, Docente entity) {
		entity.setId(dto.getId());
		entity.setCpf(dto.getCpf());
		entity.setNome(dto.getNome());
		entity.setDataNascimento(dto.getDataNascimento() == null ? null : new Date(dto.getDataNascimento()));
		entity.setEndereco(dto.getEndereco());
		entity.setTelefones(dto.getTelefones());

		return entity;
	}

	@RequestMapping(value = "/{id}/oferta-disciplina", method = RequestMethod.GET)
	public ResponseEntity<List<OfertaDisciplinaDto>> findAllOfertaDisciplina(@PathVariable Integer id)
			throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {

		validatePermissionsToRead();
		return findAllSubList(OfertaDisciplinaService.class, OfertaDisciplinaDto.class, id);
	}

}
