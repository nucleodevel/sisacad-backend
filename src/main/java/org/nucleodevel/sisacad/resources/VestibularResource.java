package org.nucleodevel.sisacad.resources;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.nucleodevel.sisacad.domain.OfertaCurso;
import org.nucleodevel.sisacad.domain.Vestibular;
import org.nucleodevel.sisacad.dto.OfertaCursoDto;
import org.nucleodevel.sisacad.dto.VestibularDto;
import org.nucleodevel.sisacad.repositories.VestibularRepository;
import org.nucleodevel.sisacad.services.VestibularService;
import org.nucleodevel.sisacad.services.exceptions.FieldValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/vestibular")
public class VestibularResource
		extends AbstractResource<Vestibular, Integer, VestibularDto, VestibularRepository, VestibularService> {

	@Override
	public Vestibular mergeDtoIntoEntity(VestibularDto dto, Vestibular entity) {
		entity.setId(dto.getId());
		entity.setAno(dto.getAno());

		return entity;
	}

	@Override
	public void validadeForInsertUpdate(VestibularDto dto) {
		String error = "";

		if (dto.getAno() == null) {
			error += "Ano pendente; ";
		}

		if (!error.isEmpty()) {
			throw new FieldValidationException(dto.getId(), getEntityClass(), error);
		}
	}

	@RequestMapping(value = "/{id}/oferta-curso", method = RequestMethod.GET)
	public ResponseEntity<List<OfertaCursoDto>> findAllOfertaCurso(@PathVariable Integer id)
			throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {

		return findAllItem(OfertaCurso.class, OfertaCursoDto.class, id, "getListaOfertaCurso");
	}

}
