package org.nucleodevel.sisacad.services;

import java.util.Optional;

import org.nucleodevel.sisacad.domain.AvaliacaoVestibulando;
import org.nucleodevel.sisacad.domain.Vestibulando;
import org.nucleodevel.sisacad.repositories.AvaliacaoVestibulandoRepository;
import org.nucleodevel.sisacad.services.exceptions.DataIntegrityException;
import org.springframework.stereotype.Service;

@Service
public class AvaliacaoVestibulandoService
		extends AbstractService<AvaliacaoVestibulando, Integer, AvaliacaoVestibulandoRepository> {

	@Override
	public void validadeForInsertUpdate(AvaliacaoVestibulando entity) {
		Vestibulando myVestibulando = entity.getVestibulando();

		Optional<AvaliacaoVestibulando> similar = repo.findByNotIdAndVestibulando(entity.getId(), myVestibulando);
		similar.ifPresent(obj -> {
			throw new DataIntegrityException("Já existe uma avaliação para este vestibulando!");
		});
	}

}
