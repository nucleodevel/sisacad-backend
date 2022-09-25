package org.nucleodevel.sisacad.resources;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;

import org.nucleodevel.sisacad.domain.Avaliacao;
import org.nucleodevel.sisacad.domain.OfertaDisciplina;
import org.nucleodevel.sisacad.dto.AvaliacaoDto;
import org.nucleodevel.sisacad.dto.ParticipacaoAvaliacaoDto;
import org.nucleodevel.sisacad.security.Role;
import org.nucleodevel.sisacad.services.AvaliacaoService;
import org.nucleodevel.sisacad.services.OfertaDisciplinaService;
import org.nucleodevel.sisacad.services.ParticipacaoAvaliacaoService;
import org.nucleodevel.sisacad.services.exceptions.FieldValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/avaliacao")
public class AvaliacaoResource extends AbstractResource<Avaliacao, AvaliacaoDto, Integer, AvaliacaoService> {

	@Autowired
	private OfertaDisciplinaService ofertaDisciplinaService;

	@Override
	public List<Role> getListAllowedRoleToRead() {
		return List.of(Role.DISCENTE, Role.DOCENTE);
	}

	@Override
	public List<Role> getListAllowedRoleToWrite() {
		return List.of(Role.DOCENTE);
	}

	@Override
	public Avaliacao mergeDtoIntoEntity(AvaliacaoDto dto, Avaliacao entity) {
		String error = "";

		entity.setId(dto.getId());
		entity.setDescricao(dto.getDescricao());
		entity.setInicio(dto.getInicio() == null ? null : new Date(dto.getInicio()));
		entity.setTermino(dto.getTermino() == null ? null : new Date(dto.getTermino()));

		if (dto.getOfertaDisciplina() != null) {
			OfertaDisciplina ofertaDisciplina = ofertaDisciplinaService.find(dto.getOfertaDisciplina());
			if (ofertaDisciplina == null) {
				error += "OfertaDisciplina com ID " + entity.getOfertaDisciplina().getId() + " não existente; ";
			}
			entity.setOfertaDisciplina(ofertaDisciplina);
		} else {
			entity.setOfertaDisciplina(null);
		}

		if (!error.isEmpty()) {
			throw new FieldValidationException(entity.getId(), getEntityClass(), error);
		}

		if (!error.isEmpty()) {
			throw new FieldValidationException(entity.getId(), getEntityClass(), error);
		}

		return entity;
	}

	@RequestMapping(value = "/{id}/participacao-avaliacao", method = RequestMethod.GET)
	public ResponseEntity<List<ParticipacaoAvaliacaoDto>> findAllParticipacaoAvaliacao(@PathVariable Integer id)
			throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {

		validatePermissionsToRead();
		return findAllSubList(ParticipacaoAvaliacaoService.class, ParticipacaoAvaliacaoDto.class, id);
	}

}
