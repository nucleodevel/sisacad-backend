package org.nucleodevel.sisacad.services;

import java.util.List;
import java.util.Optional;

import org.nucleodevel.sisacad.domain.Discente;
import org.nucleodevel.sisacad.domain.Vestibulando;
import org.nucleodevel.sisacad.repositories.DiscenteRepository;
import org.nucleodevel.sisacad.services.exceptions.DataIntegrityException;
import org.nucleodevel.sisacad.services.exceptions.FieldValidationException;
import org.nucleodevel.sisacad.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DiscenteService extends AbstractService<Discente, Integer, DiscenteRepository> {

	@Autowired
	private VestibulandoService vestibulandoService;

	@Override
	public void validadeForInsertUpdate(Discente entity) {
		String error = "";

		Vestibulando myVestibulando = null;
		if (entity.getVestibulando() == null) {
			error += "Vestibulando pendente; ";
		} else {
			try {
				myVestibulando = vestibulandoService.find(entity.getVestibulando().getId());
			} catch (ObjectNotFoundException e) {
				error += "Vestibulando com ID " + entity.getVestibulando().getId() + " não existente; ";
			}
		}

		if (!error.isEmpty()) {
			throw new FieldValidationException(entity.getId(), getEntityClass(), error);
		}

		Optional<Discente> similar = repository.findSimilarByVestibulando(entity.getId(), myVestibulando);
		similar.ifPresent(obj -> {
			throw new DataIntegrityException("Já existe um discente para este vestibulando!");
		});
	}

	@Override
	public List<Discente> findAll() {
		return repository.findByOrderByVestibulandoAsc();
	}

}
