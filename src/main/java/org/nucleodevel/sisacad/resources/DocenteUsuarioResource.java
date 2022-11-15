package org.nucleodevel.sisacad.resources;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.nucleodevel.sisacad.domain.Docente;
import org.nucleodevel.sisacad.domain.Usuario;
import org.nucleodevel.sisacad.dto.DocenteUsuarioDto;
import org.nucleodevel.sisacad.security.Role;
import org.nucleodevel.sisacad.services.DocenteService;
import org.nucleodevel.sisacad.services.MailService;
import org.nucleodevel.sisacad.services.UsuarioService;
import org.nucleodevel.sisacad.services.exceptions.FieldValidationException;
import org.nucleodevel.sisacad.services.exceptions.ForbiddenException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(value = "/docente-usuario")
public class DocenteUsuarioResource {

	@Autowired
	private MailService mailService;

	@Autowired
	private DocenteService docenteService;
	@Autowired
	private UsuarioService usuarioService;

	public List<Role> getListAllowedRoleToRead() {
		return List.of(Role.DOCENTE);
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

	public Docente mergeDtoIntoDocenteEntity(DocenteUsuarioDto dto, Docente docente) {
		String error = "";

		if (dto.getDocente() != null) {
			docente.setId(dto.getDocente().getId());
			docente.setCpf(dto.getDocente().getCpf());
			docente.setDataNascimento(dto.getDocente().getDataNascimento() == null ? null
					: new Date(dto.getDocente().getDataNascimento()));
			docente.setEndereco(dto.getDocente().getEndereco());
			docente.setTelefones(dto.getDocente().getTelefones());

			if (dto.getUsuario() != null && dto.getUsuario().getId() != null) {
				Usuario usuario = usuarioService.find(dto.getUsuario().getId());
				if (usuario == null) {
					error += "Usuario com ID " + docente.getUsuario().getId() + " não existente; ";
				}
				docente.setUsuario(usuario);
			} else {
				docente.setUsuario(null);
			}
		}

		if (!error.isEmpty()) {
			throw new FieldValidationException(docente.getId(), Docente.class, error);
		}

		if (!error.isEmpty()) {
			throw new FieldValidationException(docente.getId(), Docente.class, error);
		}

		return docente;
	}

	public Usuario mergeDtoIntoUsuarioEntity(DocenteUsuarioDto dto, Usuario usuario) {
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

	public Docente getDocenteEntityFromDto(DocenteUsuarioDto dto) {
		Docente entity = new Docente();
		return mergeDtoIntoDocenteEntity(dto, entity);
	}

	public Usuario getUsuarioEntityFromDto(DocenteUsuarioDto dto) {
		Usuario entity = new Usuario();
		return mergeDtoIntoUsuarioEntity(dto, entity);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<DocenteUsuarioDto> find(@PathVariable Integer id) {
		validatePermissionsToRead();

		Docente docenteEntity = docenteService.find(id);
		DocenteUsuarioDto dto = new DocenteUsuarioDto();
		dto.copyFromEntity(docenteEntity, docenteEntity.getUsuario());
		return ResponseEntity.ok().body(dto);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<DocenteUsuarioDto> insert(@RequestBody DocenteUsuarioDto dto) {
		validatePermissionsToWrite();

		Usuario usuarioEntity = getUsuarioEntityFromDto(dto);
		usuarioEntity.setRoles("ROLE_DOCENTE");

		Docente docenteEntity = getDocenteEntityFromDto(dto);

		String errorMsg = "";

		try {
			usuarioService.validadeForInsertUpdate(usuarioEntity);
		} catch (FieldValidationException e) {
			errorMsg += e.getMsg();
		}

		try {
			docenteService.validadeForInsertUpdate(docenteEntity);
		} catch (FieldValidationException e) {
			errorMsg += e.getMsg();
		}

		if (StringUtils.hasText(errorMsg)) {
			throw new FieldValidationException(errorMsg);
		}

		usuarioEntity = usuarioService.insert(usuarioEntity);
		dto.getUsuario().setId(usuarioEntity.getId());

		docenteEntity.setUsuario(usuarioEntity);
		docenteEntity = docenteService.insert(docenteEntity);

		dto = new DocenteUsuarioDto();
		dto.copyFromEntity(docenteEntity, usuarioEntity);

		String mailText = "Inscrição realizada com sucesso\n" + "\nNome: " + usuarioEntity.getNome() + "\nE-mail: "
				+ usuarioEntity.getEmail() + "\nCPF: " + docenteEntity.getCpf() + "\nUsername: "
				+ usuarioEntity.getUsername() + "\nPassword: " + usuarioEntity.getPassword();

		mailService.send(usuarioEntity.getEmail(), "Inscrição realizada com sucesso", mailText);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
		return ResponseEntity.created(uri).body(dto);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@RequestBody DocenteUsuarioDto dto, @PathVariable Integer id) {
		validatePermissionsToWrite();

		Usuario usuarioEntity = getUsuarioEntityFromDto(dto);
		usuarioEntity.setRoles("ROLE_DOCENTE");

		Docente docenteEntity = getDocenteEntityFromDto(dto);

		String errorMsg = "";

		try {
			usuarioService.validadeForInsertUpdate(usuarioEntity);
		} catch (FieldValidationException e) {
			errorMsg += e.getMsg();
		}

		try {
			docenteService.validadeForInsertUpdate(docenteEntity);
		} catch (FieldValidationException e) {
			errorMsg += e.getMsg();
		}

		if (StringUtils.hasText(errorMsg)) {
			throw new FieldValidationException(errorMsg);
		}

		usuarioEntity = usuarioService.update(usuarioEntity);

		docenteEntity.setUsuario(usuarioEntity);
		docenteEntity = docenteService.update(docenteEntity);

		dto = new DocenteUsuarioDto();
		dto.copyFromEntity(docenteEntity, usuarioEntity);

		return ResponseEntity.noContent().build();
	}

}
