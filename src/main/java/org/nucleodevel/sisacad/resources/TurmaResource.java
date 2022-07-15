package org.nucleodevel.sisacad.resources;

import org.nucleodevel.sisacad.domain.Turma;
import org.nucleodevel.sisacad.dto.TurmaDto;
import org.nucleodevel.sisacad.repositories.TurmaRepository;
import org.nucleodevel.sisacad.services.OfertaCursoService;
import org.nucleodevel.sisacad.services.TurmaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/turma")
public class TurmaResource extends AbstractResource<Turma, Integer, TurmaDto, TurmaRepository, TurmaService> {

	@Autowired
	private OfertaCursoService ofertaCursoService;

	@Override
	public Turma mergeDtoIntoEntity(TurmaDto dto, Turma entity) {
		entity.setId(dto.getId());
		entity.setOfertaCurso(ofertaCursoService.find(dto.getOfertaCurso()));

		return entity;
	}

}
