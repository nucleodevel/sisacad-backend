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

	public <IS extends AbstractService<?, ?, IDTO, ?>, IDTO extends AbstractDto<?, ?>> ResponseEntity<List<IDTO>> findAllSubList(
			Class<IS> subServiceClass, Class<IDTO> subDtoClass, ID id) throws NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		List<IDTO> idtoList = service.findAllSubDtoList(subServiceClass, subDtoClass, id);
		return ResponseEntity.ok().body(idtoList);
	}

	public <IS extends AbstractService<?, ID, ?, ?>> ResponseEntity<Void> insertSubList(ID id, ID subEntityId,
			IS subService) throws NoSuchMethodException, SecurityException, InstantiationException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		service.insertSubDtoList(id, subEntityId, subService);
		return ResponseEntity.noContent().build();
	}

	public <IS extends AbstractService<?, ID, ?, ?>> ResponseEntity<Void> deleteSubList(ID id, ID subEntityId,
			IS subService) throws NoSuchMethodException, SecurityException, InstantiationException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		service.deleteSubDtoList(id, subEntityId, subService);
		return ResponseEntity.noContent().build();
	}

}
