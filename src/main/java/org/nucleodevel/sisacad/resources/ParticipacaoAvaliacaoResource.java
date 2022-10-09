package org.nucleodevel.sisacad.resources;

import java.util.List;

import org.nucleodevel.sisacad.domain.Avaliacao;
import org.nucleodevel.sisacad.domain.Discente;
import org.nucleodevel.sisacad.domain.ParticipacaoAvaliacao;
import org.nucleodevel.sisacad.dto.ParticipacaoAvaliacaoDto;
import org.nucleodevel.sisacad.security.Role;
import org.nucleodevel.sisacad.services.AvaliacaoService;
import org.nucleodevel.sisacad.services.DiscenteService;
import org.nucleodevel.sisacad.services.ParticipacaoAvaliacaoService;
import org.nucleodevel.sisacad.services.exceptions.FieldValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/participacao-avaliacao")
public class ParticipacaoAvaliacaoResource extends
		AbstractResource<ParticipacaoAvaliacao, ParticipacaoAvaliacaoDto, Integer, ParticipacaoAvaliacaoService> {

	@Autowired
	private AvaliacaoService avaliacaoService;
	@Autowired
	private DiscenteService discenteService;

	@Override
	public List<Role> getListAllowedRoleToRead() {
		return List.of(Role.DISCENTE);
	}

	@Override
	public List<Role> getListAllowedRoleToWrite() {
		return List.of(Role.DOCENTE);
	}

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

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<ParticipacaoAvaliacaoDto> find(@PathVariable Integer id) {
		return super.find(id);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<ParticipacaoAvaliacaoDto> insert(@RequestBody ParticipacaoAvaliacaoDto dto) {
		return super.insert(dto);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@RequestBody ParticipacaoAvaliacaoDto dto, @PathVariable Integer id) {
		return super.update(dto, id);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		return super.delete(id);
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<ParticipacaoAvaliacaoDto>> findAll() {
		return super.findAll();
	}

}
