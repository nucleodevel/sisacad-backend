package org.nucleodevel.sisacad.resources;

import org.nucleodevel.sisacad.domain.ParticipacaoAula;
import org.nucleodevel.sisacad.dto.ParticipacaoAulaDto;
import org.nucleodevel.sisacad.repositories.ParticipacaoAulaRepository;
import org.nucleodevel.sisacad.services.AulaService;
import org.nucleodevel.sisacad.services.DiscenteService;
import org.nucleodevel.sisacad.services.ParticipacaoAulaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/participacao-aula")
public class ParticipacaoAulaResource extends
		AbstractResource<ParticipacaoAula, Integer, ParticipacaoAulaDto, ParticipacaoAulaRepository, ParticipacaoAulaService> {

	@Autowired
	private AulaService aulaService;
	@Autowired
	private DiscenteService discenteService;

	@Override
	public ParticipacaoAula mergeDtoIntoEntity(ParticipacaoAulaDto dto, ParticipacaoAula entity) {
		entity.setId(dto.getId());
		entity.setAula(aulaService.find(dto.getAula()));
		entity.setDiscente(discenteService.find(dto.getDiscente()));

		return entity;
	}

}
