package org.nucleodevel.sisacad.resources;

import org.nucleodevel.sisacad.domain.AvaliacaoVestibulando;
import org.nucleodevel.sisacad.dto.AvaliacaoVestibulandoDto;
import org.nucleodevel.sisacad.repositories.AvaliacaoVestibulandoRepository;
import org.nucleodevel.sisacad.services.AvaliacaoVestibulandoService;
import org.nucleodevel.sisacad.services.VestibulandoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/avaliacao-vestibulando")
public class AvaliacaoVestibulandoResource extends
		AbstractResource<AvaliacaoVestibulando, Integer, AvaliacaoVestibulandoDto, AvaliacaoVestibulandoRepository, AvaliacaoVestibulandoService> {

	@Autowired
	private VestibulandoService vestibulandoService;

	@Override
	public AvaliacaoVestibulando mergeDtoIntoEntity(AvaliacaoVestibulandoDto dto, AvaliacaoVestibulando entity) {
		entity.setId(dto.getId());
		entity.setConceitoFinal(dto.getConceitoFinal());
		entity.setVestibulando(vestibulandoService.find(dto.getVestibulando()));

		return entity;
	}

}
