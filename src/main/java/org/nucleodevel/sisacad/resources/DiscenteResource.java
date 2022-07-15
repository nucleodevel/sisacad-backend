package org.nucleodevel.sisacad.resources;

import org.nucleodevel.sisacad.domain.Discente;
import org.nucleodevel.sisacad.dto.DiscenteDto;
import org.nucleodevel.sisacad.repositories.DiscenteRepository;
import org.nucleodevel.sisacad.services.DiscenteService;
import org.nucleodevel.sisacad.services.VestibulandoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/discente")
public class DiscenteResource
		extends AbstractResource<Discente, Integer, DiscenteDto, DiscenteRepository, DiscenteService> {

	@Autowired
	private VestibulandoService vestibulandoService;

	@Override
	public Discente mergeDtoIntoEntity(DiscenteDto dto, Discente entity) {
		entity.setId(dto.getId());
		entity.setVestibulando(vestibulandoService.find(dto.getVestibulando()));

		return entity;
	}

}
