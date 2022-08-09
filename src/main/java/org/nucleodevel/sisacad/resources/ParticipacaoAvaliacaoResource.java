package org.nucleodevel.sisacad.resources;

import org.nucleodevel.sisacad.domain.Avaliacao;
import org.nucleodevel.sisacad.domain.Discente;
import org.nucleodevel.sisacad.domain.ParticipacaoAvaliacao;
import org.nucleodevel.sisacad.dto.ParticipacaoAvaliacaoDto;
import org.nucleodevel.sisacad.services.AvaliacaoService;
import org.nucleodevel.sisacad.services.DiscenteService;
import org.nucleodevel.sisacad.services.ParticipacaoAvaliacaoService;
import org.nucleodevel.sisacad.services.exceptions.FieldValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/participacao-avaliacao")
public class ParticipacaoAvaliacaoResource extends
		AbstractResource<ParticipacaoAvaliacao, ParticipacaoAvaliacaoDto, Integer, ParticipacaoAvaliacaoService> {

	@Autowired
	private AvaliacaoService avaliacaoService;
	@Autowired
	private DiscenteService discenteService;

	@Override
	public ParticipacaoAvaliacao mergeDtoIntoEntity(ParticipacaoAvaliacaoDto dto, ParticipacaoAvaliacao entity) {
		String error = "";

		entity.setId(dto.getId());
		entity.setConceitoFinal(dto.getConceitoFinal());

		if (dto.getAvaliacao() != null) {
			Avaliacao avaliacao = avaliacaoService.find(dto.getAvaliacao());
			if (avaliacao == null) {
				error += "Avaliacao com ID " + entity.getAvaliacao().getId() + " não existente; ";
			}
			entity.setAvaliacao(avaliacao);
		} else {
			entity.setAvaliacao(null);
		}

		if (dto.getDiscente() != null) {
			Discente discente = discenteService.find(dto.getDiscente());
			if (discente == null) {
				error += "Discente com ID " + entity.getDiscente().getId() + " não existente; ";
			}
			entity.setDiscente(discente);
		} else {
			entity.setDiscente(null);
		}

		if (!error.isEmpty()) {
			throw new FieldValidationException(entity.getId(), getEntityClass(), error);
		}

		return entity;
	}

}
