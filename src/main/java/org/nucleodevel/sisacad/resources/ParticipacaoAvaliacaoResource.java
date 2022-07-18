package org.nucleodevel.sisacad.resources;

import org.nucleodevel.sisacad.domain.ParticipacaoAvaliacao;
import org.nucleodevel.sisacad.dto.ParticipacaoAvaliacaoDto;
import org.nucleodevel.sisacad.services.AvaliacaoService;
import org.nucleodevel.sisacad.services.DiscenteService;
import org.nucleodevel.sisacad.services.ParticipacaoAvaliacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/participacao-avaliacao")
public class ParticipacaoAvaliacaoResource extends
		AbstractResource<ParticipacaoAvaliacao, Integer, ParticipacaoAvaliacaoDto, ParticipacaoAvaliacaoService> {

	@Autowired
	private AvaliacaoService avaliacaoService;
	@Autowired
	private DiscenteService discenteService;

	@Override
	public ParticipacaoAvaliacao mergeDtoIntoEntity(ParticipacaoAvaliacaoDto dto, ParticipacaoAvaliacao entity) {
		entity.setId(dto.getId());
		entity.setAvaliacao(avaliacaoService.find(dto.getAvaliacao()));
		entity.setDiscente(discenteService.find(dto.getDiscente()));

		return entity;
	}

}
