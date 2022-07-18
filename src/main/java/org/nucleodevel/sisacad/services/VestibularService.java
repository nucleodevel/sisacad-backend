package org.nucleodevel.sisacad.services;

import java.util.Optional;

import org.nucleodevel.sisacad.domain.Vestibular;
import org.nucleodevel.sisacad.repositories.VestibularRepository;
import org.nucleodevel.sisacad.services.exceptions.DataIntegrityException;
import org.springframework.stereotype.Service;

@Service
public class VestibularService extends AbstractService<Vestibular, Integer, VestibularRepository> {

	@Override
	public void validadeForInsertUpdate(Vestibular entity) {
		Integer myAno = entity.getAno();

		Optional<Vestibular> similar = repo.findDifferentByAno(entity.getId(), myAno);
		similar.ifPresent(obj -> {
			throw new DataIntegrityException("Já existe um vestibular para este ano!");
		});
	}

}
