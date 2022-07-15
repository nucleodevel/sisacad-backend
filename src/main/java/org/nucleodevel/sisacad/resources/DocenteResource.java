package org.nucleodevel.sisacad.resources;

import org.nucleodevel.sisacad.domain.Docente;
import org.nucleodevel.sisacad.dto.DocenteDto;
import org.nucleodevel.sisacad.repositories.DocenteRepository;
import org.nucleodevel.sisacad.services.DocenteService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/docente")
public class DocenteResource extends AbstractResource<Docente, Integer, DocenteDto, DocenteRepository, DocenteService> {

	@Override
	public Docente mergeDtoIntoEntity(DocenteDto dto, Docente entity) {
		entity.setId(dto.getId());
		entity.setNome(dto.getNome());

		return entity;
	}

}
