package org.nucleodevel.sisacad.resources;

import org.nucleodevel.sisacad.dto.ParticipacaoAvaliacaoDto;
import org.nucleodevel.sisacad.services.ParticipacaoAvaliacaoService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/participacao-avaliacao")
public class ParticipacaoAvaliacaoResource
		extends AbstractResource<ParticipacaoAvaliacaoDto, Integer, ParticipacaoAvaliacaoService> {

}
