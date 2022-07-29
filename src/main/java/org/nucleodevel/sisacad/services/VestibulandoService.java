package org.nucleodevel.sisacad.services;

import java.util.Optional;

import org.nucleodevel.sisacad.domain.OfertaCurso;
import org.nucleodevel.sisacad.domain.Vestibulando;
import org.nucleodevel.sisacad.dto.VestibulandoDto;
import org.nucleodevel.sisacad.repositories.VestibulandoRepository;
import org.nucleodevel.sisacad.services.exceptions.DataIntegrityException;
import org.nucleodevel.sisacad.services.exceptions.FieldValidationException;
import org.nucleodevel.sisacad.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VestibulandoService
		extends AbstractService<Vestibulando, Integer, VestibulandoDto, VestibulandoRepository> {

	@Autowired
	private OfertaCursoService ofertaCursoService;

	@Override
	public Vestibulando mergeDtoIntoEntity(VestibulandoDto dto, Vestibulando entity) {
		entity.setId(dto.getId());
		entity.setNome(dto.getNome());
		entity.setOfertaCurso(ofertaCursoService.find(dto.getOfertaCurso()));

		return entity;
	}

	@Override
	public void validadeForInsertUpdate(VestibulandoDto dto) {
		String error = "";

		if (dto.getNome() == null) {
			error += "Nome pendente; ";
		}

		if (dto.getOfertaCurso() == null) {
			error += "Oferta de curso pendente; ";
		} else {
			try {
				ofertaCursoService.find(dto.getOfertaCurso());
			} catch (ObjectNotFoundException e) {
				error += "Oferta de curso com ID " + dto.getOfertaCurso() + " não existente; ";
			}
		}

		if (!error.isEmpty()) {
			throw new FieldValidationException(dto.getId(), getEntityClass(), error);
		}

		OfertaCurso myOfertaCurso = ofertaCursoService.find(dto.getOfertaCurso());
		String myNome = dto.getNome();

		Optional<Vestibulando> similar = repository.findSimilarByOfertaCursoAndNome(dto.getId(), myOfertaCurso, myNome);
		similar.ifPresent(obj -> {
			throw new DataIntegrityException("Já existe um cadastro com esse nome nessa oferta de curso!");
		});
	}

}
