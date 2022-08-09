package org.nucleodevel.sisacad.services;

import java.util.List;
import java.util.Optional;

import org.nucleodevel.sisacad.domain.Discente;
import org.nucleodevel.sisacad.domain.Vestibulando;
import org.nucleodevel.sisacad.dto.DiscenteDto;
import org.nucleodevel.sisacad.repositories.DiscenteRepository;
import org.nucleodevel.sisacad.services.exceptions.DataIntegrityException;
import org.nucleodevel.sisacad.services.exceptions.FieldValidationException;
import org.nucleodevel.sisacad.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DiscenteService extends AbstractService<Discente, Integer, DiscenteDto, DiscenteRepository> {

	@Autowired
	private VestibulandoService vestibulandoService;

	@Override
	public Discente mergeDtoIntoEntity(DiscenteDto dto, Discente entity) {
		entity.setId(dto.getId());
		entity.setVestibulando(vestibulandoService.find(dto.getVestibulando()));

		return entity;
	}

	@Override
	public void validadeForInsertUpdate(DiscenteDto dto) {
		String error = "";

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

		Vestibulando myVestibulando = vestibulandoService.find(dto.getVestibulando());

		Optional<Discente> similar = repository.findSimilarByVestibulando(dto.getId(), myVestibulando);
		similar.ifPresent(obj -> {
			throw new DataIntegrityException("Já existe um discente para este vestibulando!");
		});
	}

	@Override
	protected List<Discente> findAll() {
		return repository.findByOrderByVestibulandoAsc();
	}

}
