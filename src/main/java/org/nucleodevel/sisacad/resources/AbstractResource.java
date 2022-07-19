package org.nucleodevel.sisacad.resources;

import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.util.List;

import org.nucleodevel.sisacad.dto.AbstractDto;
import org.nucleodevel.sisacad.services.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public abstract class AbstractResource<DTO extends AbstractDto<?, ID>, ID, S extends AbstractService<?, ID, DTO, ?>> {

	@Autowired
	protected S service;

	protected S getService() {
		return service;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<DTO> find(@PathVariable ID id) {
		DTO dto = service.findDto(id);
		return ResponseEntity.ok().body(dto);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<DTO> insert(@RequestBody DTO dto) {
		dto = service.insertDto(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@RequestBody DTO dto, @PathVariable ID id) {
		DTO oldDto = service.findDto(id);
		dto.setId(oldDto.getId());

		dto = service.updateDto(dto);
		return ResponseEntity.noContent().build();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable ID id) {
		service.deleteDto(id);
		return ResponseEntity.noContent().build();
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<DTO>> findAll() {
		List<DTO> listDto = service.findAllDto();
		return ResponseEntity.ok().body(listDto);
	}

	public <IS extends AbstractService<?, ?, IDTO, ?>, IDTO extends AbstractDto<?, ?>> ResponseEntity<List<IDTO>> findAllItem(
			Class<IS> itemServiceClass, Class<IDTO> itemDtoClass, ID id)
			throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {

		List<IDTO> idtoList = service.findAllDtoItem(itemServiceClass, itemDtoClass, id);
		return ResponseEntity.ok().body(idtoList);
	}

	public <IS extends AbstractService<?, ID, ?, ?>> ResponseEntity<Void> insertItem(ID id, ID itemEntityId,
			IS itemService) throws NoSuchMethodException, SecurityException, InstantiationException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		service.insertDtoItem(id, itemEntityId, itemService);
		return ResponseEntity.noContent().build();
	}

	public <IS extends AbstractService<?, ID, ?, ?>> ResponseEntity<Void> deleteItem(ID id, ID itemEntityId,
			IS itemService) throws NoSuchMethodException, SecurityException, InstantiationException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		service.deleteDtoItem(id, itemEntityId, itemService);
		return ResponseEntity.noContent().build();
	}

}
