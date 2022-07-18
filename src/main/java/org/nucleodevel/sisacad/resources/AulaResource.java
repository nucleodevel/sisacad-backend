package org.nucleodevel.sisacad.resources;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.nucleodevel.sisacad.domain.Aula;
import org.nucleodevel.sisacad.domain.ParticipacaoAula;
import org.nucleodevel.sisacad.dto.AulaDto;
import org.nucleodevel.sisacad.dto.ParticipacaoAulaDto;
import org.nucleodevel.sisacad.services.AulaService;
import org.nucleodevel.sisacad.services.OfertaDisciplinaService;
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
public class AulaResource extends AbstractResource<Aula, Integer, AulaDto, AulaService> {

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

	@RequestMapping(value = "/{id}/participacao-aula", method = RequestMethod.GET)
	public ResponseEntity<List<ParticipacaoAulaDto>> findAllParticipacaoAula(@PathVariable Integer id)
			throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {

		return findAllItem(ParticipacaoAula.class, ParticipacaoAulaDto.class, id, "getListaParticipacaoAula");
	}

}
