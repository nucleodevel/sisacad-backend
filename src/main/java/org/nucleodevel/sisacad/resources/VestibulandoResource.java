package org.nucleodevel.sisacad.resources;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.nucleodevel.sisacad.dto.VestibulandoDto;
import org.nucleodevel.sisacad.services.VestibulandoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/vestibulando")
public class VestibulandoResource extends AbstractResource<VestibulandoDto, Integer, VestibulandoService> {

	@RequestMapping(value = "/is-not-discente", method = RequestMethod.GET)
	public ResponseEntity<List<VestibulandoDto>> findByAulaAndDiscente()
			throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {

		List<VestibulandoDto> listDto = service.findListDtoByIsNotDiscente();
		return ResponseEntity.ok().body(listDto);
	}

}
