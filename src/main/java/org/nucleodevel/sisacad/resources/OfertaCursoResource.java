package org.nucleodevel.sisacad.resources;

import org.nucleodevel.sisacad.domain.OfertaCurso;
import org.nucleodevel.sisacad.dto.OfertaCursoDto;
import org.nucleodevel.sisacad.repositories.OfertaCursoRepository;
import org.nucleodevel.sisacad.services.EstruturaCurricularService;
import org.nucleodevel.sisacad.services.OfertaCursoService;
import org.nucleodevel.sisacad.services.TurmaService;
import org.nucleodevel.sisacad.services.VestibularService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/oferta-curso")
public class OfertaCursoResource
		extends AbstractResource<OfertaCurso, Integer, OfertaCursoDto, OfertaCursoRepository, OfertaCursoService> {

	@Autowired
	private EstruturaCurricularService estruturaCurricularService;
	@Autowired
	private VestibularService vestibularService;
	@Autowired
	private TurmaService turmaService;

	@Override
	public OfertaCurso mergeDtoIntoEntity(OfertaCursoDto dto, OfertaCurso entity) {
		entity.setId(dto.getId());
		entity.setAno(dto.getAno());
		entity.setEstruturaCurricular(estruturaCurricularService.find(dto.getEstruturaCurricular()));
		entity.setVestibular(vestibularService.find(dto.getVestibular()));
		entity.setTurma(turmaService.find(dto.getTurma()));

		return entity;
	}

}
