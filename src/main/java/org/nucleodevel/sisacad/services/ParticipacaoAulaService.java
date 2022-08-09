package org.nucleodevel.sisacad.services;

import java.util.List;
import java.util.Optional;

import org.nucleodevel.sisacad.domain.Aula;
import org.nucleodevel.sisacad.domain.Discente;
import org.nucleodevel.sisacad.domain.ParticipacaoAula;
import org.nucleodevel.sisacad.repositories.ParticipacaoAulaRepository;
import org.nucleodevel.sisacad.services.exceptions.DataIntegrityException;
import org.nucleodevel.sisacad.services.exceptions.FieldValidationException;
import org.nucleodevel.sisacad.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ParticipacaoAulaService extends AbstractService<ParticipacaoAula, Integer, ParticipacaoAulaRepository> {

	@Autowired
	private AulaService aulaService;
	@Autowired
	private DiscenteService discenteService;

	@Override
	public void validadeForInsertUpdate(ParticipacaoAula entity) {
		String error = "";

		Aula myAula = null;
		if (entity.getAula() == null) {
			error += "Aula pendente; ";
		} else {
			try {
				myAula = aulaService.find(entity.getAula().getId());
			} catch (ObjectNotFoundException e) {
				error += "Aula com ID " + entity.getAula().getId() + " não existente; ";
			}
		}

		Discente myDiscente = null;
		if (entity.getDiscente() == null) {
			error += "Discente pendente; ";
		} else {
			try {
				myDiscente = discenteService.find(entity.getDiscente().getId());
			} catch (ObjectNotFoundException e) {
				error += "Discente com ID " + entity.getDiscente().getId() + " não existente; ";
			}
		}

		if (!error.isEmpty()) {
			throw new FieldValidationException(entity.getId(), getEntityClass(), error);
		}

		Optional<ParticipacaoAula> similar = repository.findSimilarByAulaAndDiscente(entity.getId(), myAula,
				myDiscente);
		similar.ifPresent(obj -> {
			throw new DataIntegrityException("Já existe uma participação desse discente nessa aula!");
		});
	}

	@Override
	public List<ParticipacaoAula> findAll() {
		return repository.findByOrderByAulaDescDiscenteAsc();
	}

	public ParticipacaoAula findByAulaAndDiscente(Aula aula, Discente discente) {
		Optional<ParticipacaoAula> entity = repository.findByAulaAndDiscente(aula, discente);
		return entity.isPresent() ? entity.get() : null;
	}

}
