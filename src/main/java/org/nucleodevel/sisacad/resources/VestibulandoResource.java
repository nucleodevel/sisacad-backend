package org.nucleodevel.sisacad.resources;

import org.nucleodevel.sisacad.domain.Vestibulando;
import org.nucleodevel.sisacad.dto.VestibulandoDto;
import org.nucleodevel.sisacad.repositories.VestibulandoRepository;
import org.nucleodevel.sisacad.services.OfertaCursoService;
import org.nucleodevel.sisacad.services.VestibulandoService;
import org.nucleodevel.sisacad.services.exceptions.FieldValidationException;
import org.nucleodevel.sisacad.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/vestibulando")
public class VestibulandoResource
		extends AbstractResource<Vestibulando, Integer, VestibulandoDto, VestibulandoRepository, VestibulandoService> {

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
	}

}
