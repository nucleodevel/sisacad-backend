package org.nucleodevel.sisacad.services;

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
	public void validadeForInsertUpdate(AvaliacaoVestibulando entity) {
		String error = "";

		if (entity.getConceitoFinal() == null) {
			error += "Conceito final pendente; ";
		}

		if (entity.getVestibulando() == null) {
			error += "Vestibulando pendente; ";
		}

		if (!error.isEmpty()) {
			throw new FieldValidationException(entity.getId(), getEntityClass(), error);
		}

		Vestibulando myVestibulando = entity.getVestibulando();

		Optional<AvaliacaoVestibulando> similar = repo.findDifferentByVestibulando(entity.getId(), myVestibulando);
		similar.ifPresent(obj -> {
			throw new DataIntegrityException("Já existe uma avaliação para este vestibulando!");
		});
	}

	@Override
	public void validadeForInsertUpdate(AvaliacaoVestibulandoDto dto) {
		String error = "";

		if (dto.getConceitoFinal() == null) {
			error += "Conceito final pendente; ";
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
	}

}
