package org.nucleodevel.sisacad.resources;

import java.util.List;

import org.nucleodevel.sisacad.domain.Usuario;
import org.nucleodevel.sisacad.dto.UsuarioDto;
import org.nucleodevel.sisacad.security.Role;
import org.nucleodevel.sisacad.services.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/usuario")
public class UsuarioResource extends AbstractResource<Usuario, UsuarioDto, Integer, UsuarioService> {

	@Override
	public List<Role> getListAllowedRoleToRead() {
		return List.of(Role.ADMIN);
	}

	@Override
	public List<Role> getListAllowedRoleToWrite() {
		return List.of(Role.ADMIN);
	}

	@Override
	public Usuario mergeDtoIntoEntity(UsuarioDto dto, Usuario entity) {
		entity.setId(dto.getId());
		entity.setUsername(dto.getUsername());
		entity.setPassword(dto.getPassword());
		entity.setNome(dto.getNome());
		entity.setEmail(dto.getEmail());

		return entity;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<UsuarioDto> find(@PathVariable Integer id) {
		return super.find(id);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<UsuarioDto> insert(@RequestBody UsuarioDto dto) {
		return super.insert(dto);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@RequestBody UsuarioDto dto, @PathVariable Integer id) {
		return super.update(dto, id);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		return super.delete(id);
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<UsuarioDto>> findAll() {
		return super.findAll();
	}

}
