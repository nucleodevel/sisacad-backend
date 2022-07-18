package org.nucleodevel.sisacad.resources;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.nucleodevel.sisacad.domain.Curso;
import org.nucleodevel.sisacad.domain.EstruturaCurricular;
import org.nucleodevel.sisacad.dto.CursoDto;
import org.nucleodevel.sisacad.dto.EstruturaCurricularDto;
import org.nucleodevel.sisacad.services.CursoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/curso")
public class CursoResource extends AbstractResource<Curso, Integer, CursoDto, CursoService> {

	@Override
	public Curso mergeDtoIntoEntity(CursoDto dto, Curso entity) {
		entity.setId(dto.getId());
		entity.setNome(dto.getNome());

		return entity;
	}

	@RequestMapping(value = "/{id}/estrutura-curricular", method = RequestMethod.GET)
	public ResponseEntity<List<EstruturaCurricularDto>> findAllEstruturaCurricular(@PathVariable Integer id)
			throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {

		return findAllItem(EstruturaCurricular.class, EstruturaCurricularDto.class, id, "getListaEstruturaCurricular");
	}

}
