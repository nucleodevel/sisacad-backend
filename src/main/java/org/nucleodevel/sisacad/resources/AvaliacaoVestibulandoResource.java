package org.nucleodevel.sisacad.resources;

import java.lang.reflect.InvocationTargetException;

import org.nucleodevel.sisacad.dto.AvaliacaoVestibulandoDto;
import org.nucleodevel.sisacad.services.AvaliacaoVestibulandoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/avaliacao-vestibulando")
public class AvaliacaoVestibulandoResource
		extends AbstractResource<AvaliacaoVestibulandoDto, Integer, AvaliacaoVestibulandoService> {

	@RequestMapping(value = "/vestibulando/{vestibulandoId}", method = RequestMethod.GET)
	public ResponseEntity<AvaliacaoVestibulandoDto> findByVestibulando(@PathVariable Integer vestibulandoId)
			throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {

		AvaliacaoVestibulandoDto dto = service.findDtoByVestibulandoId(vestibulandoId);
		return ResponseEntity.ok().body(dto);
	}

}
