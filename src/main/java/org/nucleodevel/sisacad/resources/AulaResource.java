package org.nucleodevel.sisacad.resources;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.nucleodevel.sisacad.dto.AulaDto;
import org.nucleodevel.sisacad.dto.DiscenteDto;
import org.nucleodevel.sisacad.dto.ParticipacaoAulaDto;
import org.nucleodevel.sisacad.services.AulaService;
import org.nucleodevel.sisacad.services.DiscenteService;
import org.nucleodevel.sisacad.services.ParticipacaoAulaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/aula")
public class AulaResource extends AbstractResource<AulaDto, Integer, AulaService> {

	@Autowired
	private DiscenteService discenteService;

	@RequestMapping(value = "/{id}/participacao-aula", method = RequestMethod.GET)
	public ResponseEntity<List<ParticipacaoAulaDto>> findAllParticipacaoAula(@PathVariable Integer id)
			throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {

		return findAllSubList(ParticipacaoAulaService.class, ParticipacaoAulaDto.class, id);
	}

	@RequestMapping(value = "/{id}/discente-participante", method = RequestMethod.GET)
	public ResponseEntity<List<DiscenteDto>> findAllDiscenteParticipante(@PathVariable Integer id)
			throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {

		List<DiscenteDto> listDiscenteDto = new ArrayList<>();
		List<ParticipacaoAulaDto> listParticipacaoAulaDto = findAllParticipacaoAula(id).getBody();

		for (ParticipacaoAulaDto participacaoAulaDto : listParticipacaoAulaDto) {
			listDiscenteDto.add(discenteService.findDto(participacaoAulaDto.getDiscente()));
		}

		return ResponseEntity.ok().body(listDiscenteDto);
	}

}
