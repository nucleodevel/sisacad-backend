package org.nucleodevel.sisacad.resources;

import org.nucleodevel.sisacad.dto.AvaliacaoVestibulandoDto;
import org.nucleodevel.sisacad.services.AvaliacaoVestibulandoService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/avaliacao-vestibulando")
public class AvaliacaoVestibulandoResource
		extends AbstractResource<AvaliacaoVestibulandoDto, Integer, AvaliacaoVestibulandoService> {

}
