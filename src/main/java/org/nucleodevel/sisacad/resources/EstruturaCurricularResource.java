package org.nucleodevel.sisacad.resources;

import org.nucleodevel.sisacad.domain.EstruturaCurricular;
import org.nucleodevel.sisacad.dto.EstruturaCurricularDto;
import org.nucleodevel.sisacad.repositories.EstruturaCurricularRepository;
import org.nucleodevel.sisacad.services.CursoService;
import org.nucleodevel.sisacad.services.EstruturaCurricularService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/estrutura-curricular")
public class EstruturaCurricularResource extends
		AbstractResource<EstruturaCurricular, Integer, EstruturaCurricularDto, EstruturaCurricularRepository, EstruturaCurricularService> {

	@Autowired
	private CursoService cursoService;

	@Override
	public EstruturaCurricular mergeDtoIntoEntity(EstruturaCurricularDto dto, EstruturaCurricular entity) {
		entity.setId(dto.getId());
		entity.setAnoInicio(dto.getAnoInicio());
		entity.setAnoTermino(dto.getAnoTermino());
		entity.setCurso(cursoService.find(dto.getCurso()));

		return entity;
	}

}
