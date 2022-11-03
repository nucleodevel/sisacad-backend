package org.nucleodevel.sisacad.resources;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.nucleodevel.sisacad.domain.OfertaCurso;
import org.nucleodevel.sisacad.domain.Usuario;
import org.nucleodevel.sisacad.domain.Vestibulando;
import org.nucleodevel.sisacad.dto.VestibulandoUsuarioDto;
import org.nucleodevel.sisacad.security.Role;
import org.nucleodevel.sisacad.services.OfertaCursoService;
import org.nucleodevel.sisacad.services.UsuarioService;
import org.nucleodevel.sisacad.services.VestibulandoService;
import org.nucleodevel.sisacad.services.exceptions.FieldValidationException;
import org.nucleodevel.sisacad.services.exceptions.ForbiddenException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(value = "/vestibulando-usuario")
public class VestibulandoUsuarioResource {

	@Autowired
	private VestibulandoService vestibulandoService;
	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private OfertaCursoService ofertaCursoService;

	public List<Role> getListAllowedRoleToRead() {
		return List.of(Role.VESTIBULANDO);
	}

	public List<Role> getListAllowedRoleToWrite() {
		return List.of(Role.PEDAGOGICO);
	}

	public List<Role> getListCurrentRole() {

		Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication()
				.getAuthorities();

		List<Role> listCurrentRole = new ArrayList<>();
		for (GrantedAuthority authority : authorities) {
			listCurrentRole.add(Role.getByTag(authority.getAuthority()));
		}

		return listCurrentRole;
	}

	/*
	 * 
	 * Validate methods
	 * 
	 */

	public void validatePermissionsToRead() {

		boolean isAllowed = false;
		for (Role currentRole : getListCurrentRole()) {

			for (Role allowedRole : getListAllowedRoleToRead())
				if (currentRole == allowedRole || currentRole.getPriority() > allowedRole.getPriority())
					isAllowed = true;

			for (Role allowedRole : getListAllowedRoleToWrite())
				if (currentRole == allowedRole || currentRole.getPriority() > allowedRole.getPriority())
					isAllowed = true;
		}

		if (!isAllowed)
			throw new ForbiddenException();

	}

	public void validatePermissionsToWrite() {

		boolean isAllowed = false;
		for (Role currentRole : getListCurrentRole()) {
			for (Role allowedRole : getListAllowedRoleToWrite())
				if (currentRole == allowedRole || currentRole.getPriority() > allowedRole.getPriority())
					isAllowed = true;
		}

		if (!isAllowed)
			throw new ForbiddenException();

	}

	public Vestibulando mergeDtoIntoVestibulandoEntity(VestibulandoUsuarioDto dto, Vestibulando vestibulando) {
		String error = "";

		if (dto.getVestibulando() != null) {
			vestibulando.setId(dto.getVestibulando().getId());
			vestibulando.setCpf(dto.getVestibulando().getCpf());
			vestibulando.setDataNascimento(dto.getVestibulando().getDataNascimento() == null ? null
					: new Date(dto.getVestibulando().getDataNascimento()));
			vestibulando.setEndereco(dto.getVestibulando().getEndereco());
			vestibulando.setTelefones(dto.getVestibulando().getTelefones());

			if (dto.getUsuario() != null) {
				Usuario usuario = usuarioService.find(dto.getUsuario().getId());
				if (usuario == null) {
					error += "Usuario com ID " + vestibulando.getUsuario().getId() + " não existente; ";
				}
				vestibulando.setUsuario(usuario);
			} else {
				vestibulando.setUsuario(null);
			}

			if (dto.getVestibulando().getOfertaCurso() != null) {
				OfertaCurso ofertaCurso = ofertaCursoService.find(dto.getVestibulando().getOfertaCurso());
				if (ofertaCurso == null) {
					error += "OfertaCurso com ID " + vestibulando.getOfertaCurso().getId() + " não existente; ";
				}
				vestibulando.setOfertaCurso(ofertaCurso);
			} else {
				vestibulando.setOfertaCurso(null);
			}
		}

		if (!error.isEmpty()) {
			throw new FieldValidationException(vestibulando.getId(), Vestibulando.class, error);
		}

		if (!error.isEmpty()) {
			throw new FieldValidationException(vestibulando.getId(), Vestibulando.class, error);
		}

		return vestibulando;
	}

	public Usuario mergeDtoIntoUsuarioEntity(VestibulandoUsuarioDto dto, Usuario usuario) {
		if (dto.getUsuario() != null) {
			usuario.setId(dto.getUsuario().getId());
			usuario.setUsername(dto.getUsuario().getUsername());
			usuario.setPassword(dto.getUsuario().getPassword());
			usuario.setNome(dto.getUsuario().getNome());
			usuario.setEmail(dto.getUsuario().getEmail());
			usuario.setRoles(dto.getUsuario().getRoles());
		}

		return usuario;
	}

	public Vestibulando getVestibulandoEntityFromDto(VestibulandoUsuarioDto dto) {
		Vestibulando entity = new Vestibulando();
		return mergeDtoIntoVestibulandoEntity(dto, entity);
	}

	public Usuario getUsuarioEntityFromDto(VestibulandoUsuarioDto dto) {
		Usuario entity = new Usuario();
		return mergeDtoIntoUsuarioEntity(dto, entity);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<VestibulandoUsuarioDto> find(@PathVariable Integer id) {
		validatePermissionsToRead();

		Vestibulando vestibulandoEntity = vestibulandoService.find(id);
		VestibulandoUsuarioDto dto = new VestibulandoUsuarioDto();
		dto.copyFromEntity(vestibulandoEntity, vestibulandoEntity.getUsuario());
		return ResponseEntity.ok().body(dto);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<VestibulandoUsuarioDto> insert(@RequestBody VestibulandoUsuarioDto dto) {
		validatePermissionsToWrite();

		Usuario usuarioEntity = getUsuarioEntityFromDto(dto);
		usuarioEntity.setRoles("ROLE_VESTIBULANDO");

		usuarioEntity = usuarioService.insert(usuarioEntity);
		dto.getUsuario().setId(usuarioEntity.getId());

		Vestibulando vestibulandoEntity = vestibulandoService.insert(getVestibulandoEntityFromDto(dto));

		dto = new VestibulandoUsuarioDto();
		dto.copyFromEntity(vestibulandoEntity, usuarioEntity);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
		return ResponseEntity.created(uri).body(dto);
	}

	/*
	 * @RequestMapping(value = "/{id}", method = RequestMethod.PUT) public
	 * ResponseEntity<Void> update(@RequestBody VestibulandoUsuarioDto
	 * dto, @PathVariable Integer id) { return super.update(dto, id); }
	 * 
	 * @RequestMapping(value = "/{id}", method = RequestMethod.DELETE) public
	 * ResponseEntity<Void> delete(@PathVariable Integer id) { return
	 * super.delete(id); }
	 * 
	 * @RequestMapping(method = RequestMethod.GET) public
	 * ResponseEntity<List<VestibulandoUsuarioDto>> findAll() { return
	 * super.findAll(); }
	 */

}
