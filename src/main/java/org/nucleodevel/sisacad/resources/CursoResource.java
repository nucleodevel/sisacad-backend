package org.nucleodevel.sisacad.resources;

import org.nucleodevel.sisacad.domain.Curso;
import org.nucleodevel.sisacad.dto.CursoDto;
import org.nucleodevel.sisacad.repositories.CursoRepository;
import org.nucleodevel.sisacad.services.CursoService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/curso")
public class CursoResource extends AbstractResource<Curso, Integer, CursoDto, CursoRepository, CursoService> {

	@Override
	public Curso mergeDtoIntoEntity(CursoDto dto, Curso entity) {
		entity.setId(dto.getId());
		entity.setNome(dto.getNome());

		return entity;
	}

}
