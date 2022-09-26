package org.nucleodevel.sisacad.resources;

import java.util.List;

import org.nucleodevel.sisacad.domain.Usuario;
import org.nucleodevel.sisacad.dto.UsuarioDto;
import org.nucleodevel.sisacad.security.Role;
import org.nucleodevel.sisacad.services.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/auth")
public class AuthResource extends AbstractResource<Usuario, UsuarioDto, Integer, UsuarioService> {

	@Override
	public List<Role> getListAllowedRoleToRead() {
		return List.of(Role.USER);
	}

	@Override
	public List<Role> getListAllowedRoleToWrite() {
		return List.of(Role.USER);
	}

	@Override
	public Usuario mergeDtoIntoEntity(UsuarioDto dto, Usuario entity) {
		entity.setId(dto.getId());
		entity.setUsername(dto.getUsername());
		entity.setPassword(dto.getPassword());

		return entity;
	}

	@GetMapping(produces = "application/json")
	@RequestMapping({ "/validate" })
	public ResponseEntity<UsuarioDto> validate() {
		UserDetails userDetails = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = userDetails.getUsername();

		Usuario entity = service.findByUsername(username);
		UsuarioDto dto = getDtoFromEntity(entity);
		return ResponseEntity.ok().body(dto);
	}

}
