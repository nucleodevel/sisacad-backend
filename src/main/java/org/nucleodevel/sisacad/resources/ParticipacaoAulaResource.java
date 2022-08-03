package org.nucleodevel.sisacad.resources;

import java.lang.reflect.InvocationTargetException;

import org.nucleodevel.sisacad.dto.ParticipacaoAulaDto;
import org.nucleodevel.sisacad.services.ParticipacaoAulaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/participacao-aula")
public class ParticipacaoAulaResource extends AbstractResource<ParticipacaoAulaDto, Integer, ParticipacaoAulaService> {

	@RequestMapping(value = "/aula/{aulaId}/discente/{discenteId}", method = RequestMethod.GET)
	public ResponseEntity<ParticipacaoAulaDto> findByAulaAndDiscente(@PathVariable Integer aulaId,
			@PathVariable Integer discenteId) throws NoSuchMethodException, SecurityException, InstantiationException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		ParticipacaoAulaDto dto = service.findDtoByAulaIdAndDiscenteId(aulaId, discenteId);
		return ResponseEntity.ok().body(dto);
	}

}
