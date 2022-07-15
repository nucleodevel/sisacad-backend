package org.nucleodevel.sisacad.resources;

import org.nucleodevel.sisacad.domain.Avaliacao;
import org.nucleodevel.sisacad.dto.AvaliacaoDto;
import org.nucleodevel.sisacad.repositories.AvaliacaoRepository;
import org.nucleodevel.sisacad.services.AvaliacaoService;
import org.nucleodevel.sisacad.services.OfertaDisciplinaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/avaliacao")
public class AvaliacaoResource
		extends AbstractResource<Avaliacao, Integer, AvaliacaoDto, AvaliacaoRepository, AvaliacaoService> {

	@Autowired
	private OfertaDisciplinaService ofertaDisciplinaService;

	@Override
	public Avaliacao mergeDtoIntoEntity(AvaliacaoDto dto, Avaliacao entity) {
		entity.setId(dto.getId());
		entity.setDescricao(dto.getDescricao());
		entity.setInicio(dto.getInicio());
		entity.setTermino(dto.getTermino());
		entity.setOfertaDisciplina(ofertaDisciplinaService.find(dto.getOfertaDisciplina()));

		return entity;
	}

}
