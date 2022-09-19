package org.nucleodevel.sisacad.resources;

import java.util.List;

import org.nucleodevel.sisacad.domain.Usuario;
import org.nucleodevel.sisacad.dto.UsuarioDto;
import org.nucleodevel.sisacad.security.Role;
import org.nucleodevel.sisacad.services.UsuarioService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(value = "/usuario")
public class UsuarioResource extends AbstractResource<Usuario, UsuarioDto, Integer, UsuarioService> {

	@Override
	public List<Role> getSpecificListAllowedRole() {
		return List.of(Role.USER);
	}

	@Override
	public Usuario mergeDtoIntoEntity(UsuarioDto dto, Usuario entity) {
		entity.setId(dto.getId());
		entity.setUsername(dto.getUsername());
		entity.setPassword(dto.getPassword());

		return entity;
	}

}
