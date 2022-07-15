package org.nucleodevel.sisacad.resources;

import org.nucleodevel.sisacad.domain.Vestibular;
import org.nucleodevel.sisacad.dto.VestibularDto;
import org.nucleodevel.sisacad.repositories.VestibularRepository;
import org.nucleodevel.sisacad.services.VestibularService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
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

}
