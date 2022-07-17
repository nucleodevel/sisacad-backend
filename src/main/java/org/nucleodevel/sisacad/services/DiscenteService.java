package org.nucleodevel.sisacad.services;

import java.util.Optional;

import org.nucleodevel.sisacad.domain.Discente;
import org.nucleodevel.sisacad.domain.Vestibulando;
import org.nucleodevel.sisacad.repositories.DiscenteRepository;
import org.nucleodevel.sisacad.services.exceptions.DataIntegrityException;
import org.springframework.stereotype.Service;

@Service
public class DiscenteService extends AbstractService<Discente, Integer, DiscenteRepository> {

	@Override
	public void validadeForInsertUpdate(Discente entity) {
		Vestibulando myVestibulando = entity.getVestibulando();

		Optional<Discente> similar = repo.findByNotIdAndVestibulando(entity.getId(), myVestibulando);
		similar.ifPresent(obj -> {
			throw new DataIntegrityException("Já existe um discente para este vestibulando!");
		});
	}

}
