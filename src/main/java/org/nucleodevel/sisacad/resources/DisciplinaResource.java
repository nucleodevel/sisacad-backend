package org.nucleodevel.sisacad.resources;

import org.nucleodevel.sisacad.domain.Disciplina;
import org.nucleodevel.sisacad.dto.DisciplinaDto;
import org.nucleodevel.sisacad.repositories.DisciplinaRepository;
import org.nucleodevel.sisacad.services.DisciplinaService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/disciplina")
public class DisciplinaResource
		extends AbstractResource<Disciplina, Integer, DisciplinaDto, DisciplinaRepository, DisciplinaService> {

	@Override
	public Disciplina mergeDtoIntoEntity(DisciplinaDto dto, Disciplina entity) {
		entity.setId(dto.getId());
		entity.setNome(dto.getNome());

		return entity;
	}

}
