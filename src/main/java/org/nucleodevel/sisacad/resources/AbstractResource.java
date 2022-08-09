package org.nucleodevel.sisacad.resources;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.nucleodevel.sisacad.domain.AbstractEntity;
import org.nucleodevel.sisacad.dto.AbstractDto;
import org.nucleodevel.sisacad.services.AbstractService;
import org.nucleodevel.sisacad.utils.ReflectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<DTO> find(@PathVariable ID id) {
		E entity = service.find(id);
		DTO dto = getDtoFromEntity(entity);
		return ResponseEntity.ok().body(dto);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<DTO> insert(@RequestBody DTO dto) {
		E entity = service.insert(getEntityFromDto(dto));
		dto = getDtoFromEntity(entity);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
		return ResponseEntity.created(uri).body(dto);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@RequestBody DTO dto, @PathVariable ID id) {
		E oldEntity = service.find(id);
		dto.setId(oldEntity.getId());

		E entity = getEntityFromDto(dto);
		service.update(entity);

		return ResponseEntity.noContent().build();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable ID id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<DTO>> findAll() {
		List<E> listAllEntity = service.findAll();
		List<DTO> listAllDto = listAllEntity.stream().map(entity -> getDtoFromEntity(entity))
				.collect(Collectors.toList());

		return ResponseEntity.ok().body(listAllDto);
	}

	public <IS extends AbstractService<?, ?, ?>, IDTO extends AbstractDto<?, ?>> ResponseEntity<List<IDTO>> findAllSubList(
			Class<IS> subServiceClass, Class<IDTO> subDtoClass, ID id) throws NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		Constructor<IS> subServiceConstructor = subServiceClass.getDeclaredConstructor();
		IS subService = subServiceConstructor.newInstance();
		Class<?> subEntityClass = subService.getEntityClass();

		List<Object> subEntityList = service.findAllSubObjectList(subEntityClass, id);

		List<IDTO> subDtoList = new ArrayList<>();
		Constructor<IDTO> idtoConstructor = subDtoClass.getDeclaredConstructor();

		for (Object subEntity : subEntityList) {
			IDTO subDto = idtoConstructor.newInstance();
			subDto.copyFromObject(subEntityClass.cast(subEntity));
			subDtoList.add(subDto);
		}

		return ResponseEntity.ok().body(subDtoList);
	}

	public <IS extends AbstractService<?, ID, ?>> ResponseEntity<Void> insertSubList(ID id, ID subEntityId,
			IS subService) throws NoSuchMethodException, SecurityException, InstantiationException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		service.insertSubObjectList(id, subEntityId, subService);
		return ResponseEntity.noContent().build();
	}

	public <IS extends AbstractService<?, ID, ?>> ResponseEntity<Void> deleteSubList(ID id, ID subEntityId,
			IS subService) throws NoSuchMethodException, SecurityException, InstantiationException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		service.deleteSubObjectList(id, subEntityId, subService);
		return ResponseEntity.noContent().build();
	}

}
