package org.nucleodevel.sisacad.resources;

import org.nucleodevel.sisacad.dto.ParticipacaoAulaDto;
import org.nucleodevel.sisacad.services.ParticipacaoAulaService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/participacao-aula")
public class ParticipacaoAulaResource extends AbstractResource<ParticipacaoAulaDto, Integer, ParticipacaoAulaService> {

}
