package org.nucleodevel.sisacad.services;

import java.util.List;
import java.util.Optional;

import org.nucleodevel.sisacad.domain.AvaliacaoVestibulando;
import org.nucleodevel.sisacad.domain.Vestibulando;
import org.nucleodevel.sisacad.repositories.AvaliacaoVestibulandoRepository;
import org.nucleodevel.sisacad.services.exceptions.DataIntegrityException;
import org.nucleodevel.sisacad.services.exceptions.FieldValidationException;
import org.nucleodevel.sisacad.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AvaliacaoVestibulandoService
		extends AbstractService<AvaliacaoVestibulando, Integer, AvaliacaoVestibulandoRepository> {

	@Autowired
	private VestibulandoService vestibulandoService;

	@Override
	public void validadeForInsertUpdate(AvaliacaoVestibulando entity) {
		String error = "";

		if (entity.getConceitoBiologicas() == null) {
			error += "Conceito Biológicas pendente; ";
		}

		if (entity.getConceitoExatas() == null) {
			error += "Conceito Exatas pendente; ";
		}

		if (entity.getConceitoHumanas() == null) {
			error += "Conceito Humanas pendente; ";
		}

		if (entity.getConceitoFinal() == null) {
			error += "Conceito Final pendente; ";
		}

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

		Optional<AvaliacaoVestibulando> similar = repository.findSimilarByVestibulando(entity.getId(), myVestibulando);
		similar.ifPresent(obj -> {
			throw new DataIntegrityException("Já existe uma avaliação para este vestibulando!");
		});
	}

	@Override
	public List<AvaliacaoVestibulando> findAll() {
		return repository.findByOrderByVestibulandoAsc();
	}

	public AvaliacaoVestibulando findByVestibulando(Vestibulando vestibulando) {
		Optional<AvaliacaoVestibulando> entity = repository.findByVestibulando(vestibulando);
		return entity.orElseThrow(() -> new ObjectNotFoundException(
				"Avaliação não encontrada para o Vestibulando ID " + vestibulando.getId()));
	}

}
