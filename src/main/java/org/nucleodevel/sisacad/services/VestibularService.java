package org.nucleodevel.sisacad.services;

import java.util.List;
import java.util.Optional;

import org.nucleodevel.sisacad.domain.Vestibular;
import org.nucleodevel.sisacad.repositories.VestibularRepository;
import org.nucleodevel.sisacad.services.exceptions.DataIntegrityException;
import org.nucleodevel.sisacad.services.exceptions.FieldValidationException;
import org.springframework.stereotype.Service;

@Service
public class VestibularService extends AbstractService<Vestibular, Integer, VestibularRepository> {

	@Override
	public void validadeForInsertUpdate(Vestibular entity) {
		String error = "";

		if (entity.getAno() == null) {
			error += "Ano pendente; ";
		}

		if (!error.isEmpty()) {
			throw new FieldValidationException(entity.getId(), getEntityClass(), error);
		}

		Integer myAno = entity.getAno();

		Optional<Vestibular> similar = repository.findSimilarByAno(entity.getId(), myAno);
		similar.ifPresent(obj -> {
			throw new DataIntegrityException("Já existe um vestibular para este ano!");
		});
	}

	@Override
	public List<Vestibular> findAll() {
		return repository.findByOrderByAnoDesc();
	}

}
