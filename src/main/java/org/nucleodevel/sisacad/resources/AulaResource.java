package org.nucleodevel.sisacad.resources;

import org.nucleodevel.sisacad.domain.Aula;
import org.nucleodevel.sisacad.dto.AulaDto;
import org.nucleodevel.sisacad.repositories.AulaRepository;
import org.nucleodevel.sisacad.services.AulaService;
import org.nucleodevel.sisacad.services.OfertaDisciplinaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/aula")
public class AulaResource extends AbstractResource<Aula, Integer, AulaDto, AulaRepository, AulaService> {

	@Autowired
	private OfertaDisciplinaService ofertaDisciplinaService;

	@Override
	public Aula mergeDtoIntoEntity(AulaDto dto, Aula entity) {
		entity.setId(dto.getId());
		entity.setInicio(dto.getInicio());
		entity.setTermino(dto.getTermino());
		entity.setOfertaDisciplina(ofertaDisciplinaService.find(dto.getOfertaDisciplina()));

		return entity;
	}

}
