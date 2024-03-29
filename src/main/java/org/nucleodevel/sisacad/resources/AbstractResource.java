package org.nucleodevel.sisacad.resources;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.nucleodevel.sisacad.domain.AbstractEntity;
import org.nucleodevel.sisacad.dto.AbstractDto;
import org.nucleodevel.sisacad.security.Role;
import org.nucleodevel.sisacad.services.AbstractService;
import org.nucleodevel.sisacad.services.exceptions.ForbiddenException;
import org.nucleodevel.sisacad.utils.ReflectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public abstract class AbstractResource<E extends AbstractEntity<ID>, DTO extends AbstractDto<E, ID>, ID, S extends AbstractService<E, ID, ?>> {

	/*
	 * 
	 * Constants and attributes
	 * 
	 */

	@Autowired
	protected S service;

	/*
	 * 
	 * Abstract methods
	 * 
	 */

	public abstract E mergeDtoIntoEntity(DTO dto, E entity);

	/*
	 * 
	 * Getters and setters
	 * 
	 */

	protected S getService() {
		return service;
	}

	public abstract List<Role> getListAllowedRoleToRead();

	public abstract List<Role> getListAllowedRoleToWrite();

	public List<Role> getListCurrentRole() {

		Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication()
				.getAuthorities();

		List<Role> listCurrentRole = new ArrayList<>();
		for (GrantedAuthority authority : authorities) {
			listCurrentRole.add(Role.getByTag(authority.getAuthority()));
		}

		return listCurrentRole;
	}

	public boolean hasRole(Role role) {
		return getListCurrentRole().contains(role);
	}

	/*
	 * 
	 * Generic type methods
	 * 
	 */

	public Class<E> getEntityClass() {
		return service.getEntityClass();
	}

	public Constructor<E> getEntityContructor() {
		return service.getEntityContructor();
	}

	@SuppressWarnings("unchecked")
	public Class<DTO> getDtoClass() {
		Class<DTO> entityClass = (Class<DTO>) ReflectUtil.getParameterClassFromParameterizedClass(getClass(), 1);
		return entityClass;
	}

	public Constructor<DTO> getDtoContructor() {
		try {
			return getDtoClass().getDeclaredConstructor();
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		return null;
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

	public void validadeForInsertUpdate(DTO dto) {
		service.validadeForInsertUpdate(getEntityFromDto(dto));
	}

	/*
	 * 
	 * Copy methods
	 * 
	 */

	public E getEntityFromDto(DTO dto) {
		try {
			E entity = dto.getId() == null ? getEntityContructor().newInstance() : service.find(dto.getId());
			return mergeDtoIntoEntity(dto, entity);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}

	public DTO getDtoFromEntity(E entity) {
		try {
			DTO dto = getDtoContructor().newInstance();
			dto.copyFromEntity(entity);
			return dto;
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * 
	 * Response operations
	 * 
	 */

	public ResponseEntity<DTO> find(ID id) {
		validatePermissionsToRead();

		E entity = service.find(id);
		DTO dto = getDtoFromEntity(entity);
		return ResponseEntity.ok().body(dto);
	}

	public ResponseEntity<DTO> insert(DTO dto) {
		validatePermissionsToWrite();

		E entity = service.insert(getEntityFromDto(dto));
		dto = getDtoFromEntity(entity);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
		return ResponseEntity.created(uri).body(dto);
	}

	public ResponseEntity<Void> update(DTO dto, ID id) {
		validatePermissionsToWrite();

		E oldEntity = service.find(id);
		dto.setId(oldEntity.getId());

		E entity = getEntityFromDto(dto);
		service.update(entity);

		return ResponseEntity.noContent().build();
	}

	public ResponseEntity<Void> delete(ID id) {
		validatePermissionsToWrite();

		service.delete(id);
		return ResponseEntity.noContent().build();
	}

	public ResponseEntity<List<DTO>> findAll() {
		validatePermissionsToRead();

		List<E> listAllEntity = service.findAll();
		List<DTO> listAllDto = listAllEntity.stream().map(entity -> getDtoFromEntity(entity))
				.collect(Collectors.toList());

		return ResponseEntity.ok().body(listAllDto);
	}

	public <IS extends AbstractService<IE, ?, ?>, IE extends AbstractEntity<?>, IDTO extends AbstractDto<IE, ?>> ResponseEntity<List<IDTO>> findAllSubList(
			Class<IS> subServiceClass, Class<IE> subEntityClass, Class<IDTO> subDtoClass, ID id)
			throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {

		List<IE> subEntityList = service.findAllSubList(subEntityClass, id);
		Collections.sort(subEntityList);

		List<IDTO> subDtoList = new ArrayList<>();
		Constructor<IDTO> idtoConstructor = subDtoClass.getDeclaredConstructor();

		for (Object subEntity : subEntityList) {
			IDTO subDto = idtoConstructor.newInstance();
			subDto.copyFromObject(subEntityClass.cast(subEntity));
			subDtoList.add(subDto);
		}

		return ResponseEntity.ok().body(subDtoList);
	}

	public <IS extends AbstractService<IE, IID, ?>, IE extends AbstractEntity<IID>, IID> ResponseEntity<Void> insertSubList(
			ID id, IID subEntityId, IS subService, Class<IE> subEntityClass)
			throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {

		service.insertIntoSubList(id, subEntityId, subService, subEntityClass);
		return ResponseEntity.noContent().build();
	}

	public <IS extends AbstractService<IE, IID, ?>, IE extends AbstractEntity<IID>, IID> ResponseEntity<Void> deleteSubList(
			ID id, IID subEntityId, IS subService, Class<IE> subEntityClass)
			throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {

		service.deleteFromSubList(id, subEntityId, subService, subEntityClass);
		return ResponseEntity.noContent().build();
	}

}
