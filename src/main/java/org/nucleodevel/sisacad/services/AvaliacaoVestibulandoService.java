package org.nucleodevel.sisacad.services;

import java.util.List;
import java.util.Optional;

import org.nucleodevel.sisacad.domain.AvaliacaoVestibulando;
import org.nucleodevel.sisacad.domain.Vestibulando;
import org.nucleodevel.sisacad.dto.AvaliacaoVestibulandoDto;
import org.nucleodevel.sisacad.repositories.AvaliacaoVestibulandoRepository;
import org.nucleodevel.sisacad.services.exceptions.DataIntegrityException;
import org.nucleodevel.sisacad.services.exceptions.FieldValidationException;
import org.nucleodevel.sisacad.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AvaliacaoVestibulandoService extends
		AbstractService<AvaliacaoVestibulando, Integer, AvaliacaoVestibulandoDto, AvaliacaoVestibulandoRepository> {

	@Autowired
	private VestibulandoService vestibulandoService;

	@Override
	public AvaliacaoVestibulando mergeDtoIntoEntity(AvaliacaoVestibulandoDto dto, AvaliacaoVestibulando entity) {
		entity.setId(dto.getId());
		entity.setConceitoBiologicas(dto.getConceitoBiologicas());
		entity.setConceitoExatas(dto.getConceitoExatas());
		entity.setConceitoHumanas(dto.getConceitoHumanas());
		entity.setConceitoFinal(dto.getConceitoFinal());
		entity.setVestibulando(vestibulandoService.find(dto.getVestibulando()));

		return entity;
	}

	@Override
	public void validadeForInsertUpdate(AvaliacaoVestibulandoDto dto) {
		String error = "";

		if (dto.getConceitoBiologicas() == null) {
			error += "Conceito Biológicas pendente; ";
		}

		if (dto.getConceitoExatas() == null) {
			error += "Conceito Exatas pendente; ";
		}

		if (dto.getConceitoHumanas() == null) {
			error += "Conceito Humanas pendente; ";
		}

		if (dto.getConceitoFinal() == null) {
			error += "Conceito Final pendente; ";
		}

		if (dto.getVestibulando() == null) {
			error += "Vestibulando pendente; ";
		} else {
			try {
				vestibulandoService.find(dto.getVestibulando());
			} catch (ObjectNotFoundException e) {
				error += "Vestibulando com ID " + dto.getVestibulando() + " não existente; ";
			}
		}

		if (!error.isEmpty()) {
			throw new FieldValidationException(dto.getId(), getEntityClass(), error);
		}

		Vestibulando myVestibulando = vestibulandoService.find(dto.getVestibulando());

		Optional<AvaliacaoVestibulando> similar = repository.findSimilarByVestibulando(dto.getId(), myVestibulando);
		similar.ifPresent(obj -> {
			throw new DataIntegrityException("Já existe uma avaliação para este vestibulando!");
		});
	}

	@Override
	protected List<AvaliacaoVestibulando> findAll() {
		return repository.findByOrderByVestibulandoAsc();
	}

	public AvaliacaoVestibulandoDto findDtoByVestibulandoId(Integer vestibulandoId) {
		Vestibulando vestibulando = vestibulandoService.find(vestibulandoId);
		Optional<AvaliacaoVestibulando> entity = repository.findByVestibulando(vestibulando);
		return entity.isPresent() ? getDtoFromEntity(entity.get()) : null;
	}

}
