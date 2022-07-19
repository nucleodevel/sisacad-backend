package org.nucleodevel.sisacad.services;

import org.nucleodevel.sisacad.domain.Avaliacao;
import org.nucleodevel.sisacad.dto.AvaliacaoDto;
import org.nucleodevel.sisacad.repositories.AvaliacaoRepository;
import org.nucleodevel.sisacad.services.exceptions.FieldValidationException;
import org.nucleodevel.sisacad.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AvaliacaoService extends AbstractService<Avaliacao, Integer, AvaliacaoDto, AvaliacaoRepository> {

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

	@Override
	public void validadeForInsertUpdate(AvaliacaoDto dto) {
		String error = "";

		if (dto.getDescricao() == null) {
			error += "Descrição pendente; ";
		}

		if (dto.getInicio() == null) {
			error += "Data e hora de início pendente; ";
		}

		if (dto.getTermino() == null) {
			error += "Data e hora de término pendente; ";
		}

		if (dto.getInicio().getTime() > dto.getTermino().getTime()) {
			error += "Data e hora de início posterior à de término; ";
		}

		if (dto.getOfertaDisciplina() == null) {
			error += "Oferta de disciplina pendente; ";
		} else {
			try {
				ofertaDisciplinaService.find(dto.getOfertaDisciplina());
			} catch (ObjectNotFoundException e) {
				error += "Oferta de disciplina com ID " + dto.getOfertaDisciplina() + " não existente; ";
			}
		}

		if (!error.isEmpty()) {
			throw new FieldValidationException(dto.getId(), getEntityClass(), error);
		}
	}

}
