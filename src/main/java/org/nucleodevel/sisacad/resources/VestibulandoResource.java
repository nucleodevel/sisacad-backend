package org.nucleodevel.sisacad.resources;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.nucleodevel.sisacad.domain.OfertaCurso;
import org.nucleodevel.sisacad.domain.Usuario;
import org.nucleodevel.sisacad.domain.Vestibulando;
import org.nucleodevel.sisacad.dto.VestibulandoDto;
import org.nucleodevel.sisacad.security.Role;
import org.nucleodevel.sisacad.services.OfertaCursoService;
import org.nucleodevel.sisacad.services.UsuarioService;
import org.nucleodevel.sisacad.services.VestibulandoService;
import org.nucleodevel.sisacad.services.exceptions.FieldValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/vestibulando")
public class VestibulandoResource
		extends AbstractResource<Vestibulando, VestibulandoDto, Integer, VestibulandoService> {

	@Autowired
	private OfertaCursoService ofertaCursoService;
	@Autowired
	private UsuarioService usuarioService;

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
		entity.setDataNascimento(dto.getDataNascimento() == null ? null : new Date(dto.getDataNascimento()));
		entity.setEndereco(dto.getEndereco());
		entity.setTelefones(dto.getTelefones());

		if (dto.getUsuario() != null) {
			Usuario usuario = usuarioService.find(dto.getUsuario());
			if (usuario == null) {
				error += "Usuario com ID " + entity.getUsuario().getId() + " não existente; ";
			}
			entity.setUsuario(usuario);
		} else {
			entity.setUsuario(null);
		}

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

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<VestibulandoDto> find(@PathVariable Integer id) {
		return super.find(id);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<VestibulandoDto> insert(@RequestBody VestibulandoDto dto) {
		return super.insert(dto);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@RequestBody VestibulandoDto dto, @PathVariable Integer id) {
		return super.update(dto, id);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		return super.delete(id);
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<VestibulandoDto>> findAll() {
		return super.findAll();
	}

	@RequestMapping(value = "/by-username/{username}", method = RequestMethod.GET)
	public ResponseEntity<List<VestibulandoDto>> findByUsername(@PathVariable String username) {
		validatePermissionsToRead();

		if (username != null && !username.equals("")) {
			Vestibulando entity = service.findByUsername(username);
			VestibulandoDto dto = getDtoFromEntity(entity);

			List<VestibulandoDto> listAllDto = new ArrayList<>();
			listAllDto.add(dto);

			return ResponseEntity.ok().body(listAllDto);
		}

		return super.findAll();
	}

	@RequestMapping(value = "/aprovado", method = RequestMethod.GET)
	public ResponseEntity<List<VestibulandoDto>> findAprovado() throws NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		validatePermissionsToRead();

		List<Vestibulando> listEntity = service.findListByAprovado();
		List<VestibulandoDto> listDto = new ArrayList<>();

		listEntity.forEach((entity) -> {
			VestibulandoDto dto = new VestibulandoDto();
			dto.copyFromEntity(entity);
			listDto.add(dto);
		});

		return ResponseEntity.ok().body(listDto);
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

	@RequestMapping(value = "/aprovado-not-discente", method = RequestMethod.GET)
	public ResponseEntity<List<VestibulandoDto>> findAprovadoNotDiscente()
			throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {

		validatePermissionsToRead();

		List<Vestibulando> listEntity = service.findListByAprovadoAndIsNotDiscente();
		List<VestibulandoDto> listDto = new ArrayList<>();

		listEntity.forEach((entity) -> {
			VestibulandoDto dto = new VestibulandoDto();
			dto.copyFromEntity(entity);
			listDto.add(dto);
		});

		return ResponseEntity.ok().body(listDto);
	}

}
