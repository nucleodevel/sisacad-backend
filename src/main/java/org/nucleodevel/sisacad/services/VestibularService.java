package org.nucleodevel.sisacad.services;

import java.util.List;
import java.util.Optional;

import org.nucleodevel.sisacad.domain.Vestibular;
import org.nucleodevel.sisacad.dto.VestibularDto;
import org.nucleodevel.sisacad.repositories.VestibularRepository;
import org.nucleodevel.sisacad.services.exceptions.DataIntegrityException;
import org.nucleodevel.sisacad.services.exceptions.FieldValidationException;
import org.springframework.stereotype.Service;

@Service
public class VestibularService extends AbstractService<Vestibular, Integer, VestibularDto, VestibularRepository> {

	@Override
	public Vestibular mergeDtoIntoEntity(VestibularDto dto, Vestibular entity) {
		entity.setId(dto.getId());
		entity.setAno(dto.getAno());

		return entity;
	}

	@Override
	public void validadeForInsertUpdate(VestibularDto dto) {
		String error = "";

		if (dto.getAno() == null) {
			error += "Ano pendente; ";
		}

		if (!error.isEmpty()) {
			throw new FieldValidationException(dto.getId(), getEntityClass(), error);
		}

		Integer myAno = dto.getAno();

		Optional<Vestibular> similar = repository.findSimilarByAno(dto.getId(), myAno);
		similar.ifPresent(obj -> {
			throw new DataIntegrityException("Já existe um vestibular para este ano!");
		});
	}

	@Override
	protected List<Vestibular> findAll() {
		return repository.findByOrderByAnoDesc();
	}

}
