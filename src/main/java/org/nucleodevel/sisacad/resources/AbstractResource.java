package org.nucleodevel.sisacad.resources;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.nucleodevel.sisacad.domain.AbstractEntity;
import org.nucleodevel.sisacad.dto.AbstractDto;
import org.nucleodevel.sisacad.repositories.AbstractRepository;
import org.nucleodevel.sisacad.services.AbstractService;
import org.nucleodevel.sisacad.utils.ReflectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public abstract class AbstractResource<E extends AbstractEntity<ID>, ID, DTO extends AbstractDto<E, ID>, R extends AbstractRepository<E, ID>, S extends AbstractService<E, ID, R>> {

	@Autowired
	protected S service;

	protected S getService() {
		return service;
	}

	public E makeEntityFromDto(DTO dto) {
		try {
			E entity = getEntityContructor().newInstance();
			return mergeDtoIntoEntity(dto, entity);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public abstract E mergeDtoIntoEntity(DTO dto, E entity);

	@SuppressWarnings("unchecked")
	public Class<E> getEntityClass() {
		Class<E> entityClass = (Class<E>) ReflectUtil.getParameterClassFromParameterizedClass(getClass(), 0);
		return entityClass;
	}

	@SuppressWarnings("unchecked")
	public Class<DTO> getDtoClass() {
		Class<DTO> entityClass = (Class<DTO>) ReflectUtil.getParameterClassFromParameterizedClass(getClass(), 2);
		return entityClass;
	}

	public Constructor<E> getEntityContructor() {
		try {
			return getEntityClass().getDeclaredConstructor();
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Constructor<DTO> getDtoContructor() {
		try {
			return getDtoClass().getDeclaredConstructor();
		} catch (NoSuchMethodException | SecurityException e) {
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<DTO> find(@PathVariable ID id) {
		E entity = service.find(id);
		DTO dto = getDtoFromEntity(entity);
		return ResponseEntity.ok().body(dto);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<DTO> insert(@RequestBody DTO dto) {
		E entity = makeEntityFromDto(dto);
		entity = service.insert(entity);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(entity.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@RequestBody DTO dto, @PathVariable ID id) {
		E oldEntity = service.find(id);
		E entity = mergeDtoIntoEntity(dto, oldEntity);
		entity.setId(id);
		entity = service.update(entity);
		return ResponseEntity.noContent().build();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable ID id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<DTO>> findAll() {
		List<E> lista = service.findAll();

		List<DTO> listDto = lista.stream().map(entity -> getDtoFromEntity(entity)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDto);
	}

	@SuppressWarnings("unchecked")
	public <IE extends AbstractEntity<?>, IDTO extends AbstractDto<IE, ?>> ResponseEntity<List<IDTO>> findAllItem(
			Class<IE> itemEntityClass, Class<IDTO> itemDtoClass, ID id, String itemListMethodName)
			throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {

		E entity = service.find(id);
		Method itemListMethod = entity.getClass().getMethod(itemListMethodName);
		List<IE> itemList = (List<IE>) itemListMethod.invoke(entity);

		List<IDTO> listDto = new ArrayList<>();
		Constructor<IDTO> idtoConstructor = itemDtoClass.getDeclaredConstructor();

		for (IE item : itemList) {
			IDTO itemDto = idtoConstructor.newInstance();
			itemDto.copyFromEntity(item);
			listDto.add(itemDto);
		}

		return ResponseEntity.ok().body(listDto);
	}

}
