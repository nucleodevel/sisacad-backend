package org.nucleodevel.sisacad.resources;

import org.nucleodevel.sisacad.domain.OfertaDisciplina;
import org.nucleodevel.sisacad.dto.OfertaDisciplinaDto;
import org.nucleodevel.sisacad.repositories.OfertaDisciplinaRepository;
import org.nucleodevel.sisacad.services.DisciplinaService;
import org.nucleodevel.sisacad.services.DocenteService;
import org.nucleodevel.sisacad.services.OfertaDisciplinaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/oferta-disciplina")
public class OfertaDisciplinaResource extends
		AbstractResource<OfertaDisciplina, Integer, OfertaDisciplinaDto, OfertaDisciplinaRepository, OfertaDisciplinaService> {

	@Autowired
	private DisciplinaService disciplinaService;
	@Autowired
	private DocenteService docenteService;

	@Override
	public OfertaDisciplina mergeDtoIntoEntity(OfertaDisciplinaDto dto, OfertaDisciplina entity) {
		entity.setId(dto.getId());
		entity.setDisciplina(disciplinaService.find(dto.getDisciplina()));
		entity.setDocente(docenteService.find(dto.getDocente()));

		return entity;
	}

}
