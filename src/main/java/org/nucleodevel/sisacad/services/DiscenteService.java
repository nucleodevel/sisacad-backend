package org.nucleodevel.sisacad.services;

import java.util.List;
import java.util.Optional;

import org.nucleodevel.sisacad.domain.Discente;
import org.nucleodevel.sisacad.domain.Docente;
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
				if (myVestibulando != null && myVestibulando.getAvaliacaoVestibulando() == null) {
					error += "Avaliação do vestibulando pendente; ";
				}
			} catch (ObjectNotFoundException e) {
				error += "Vestibulando com ID " + entity.getVestibulando().getId() + " não existente; ";
			}
		}

		if (!error.isEmpty()) {
			throw new FieldValidationException(entity.getId(), getEntityClass(), error);
		}

		Optional<Discente> similar = repository.findSimilarByVestibulando(entity.getId(), myVestibulando);
		similar.ifPresent(obj -> {
			throw new DataIntegrityException("Este vestibulando já foi matriculado como discente!");
		});
	}

	@Override
	public List<Discente> findAll() {
		return repository.findByOrderByVestibulandoAsc();
	}

	public Discente findByUsername(String username) {
		Optional<Discente> entity = repository.findByUsername(username);
		return entity.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado: " + username));
	}

	public List<Discente> findAllByDocente(Docente item) {
		return repository.findByDocente(item);
	}

}
