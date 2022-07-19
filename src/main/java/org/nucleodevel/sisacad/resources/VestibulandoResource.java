package org.nucleodevel.sisacad.resources;

import org.nucleodevel.sisacad.dto.VestibulandoDto;
import org.nucleodevel.sisacad.services.VestibulandoService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/vestibulando")
public class VestibulandoResource extends AbstractResource<VestibulandoDto, Integer, VestibulandoService> {

}
