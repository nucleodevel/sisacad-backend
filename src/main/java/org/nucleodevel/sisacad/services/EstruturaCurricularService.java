package org.nucleodevel.sisacad.services;

import java.util.List;
import java.util.Optional;

import org.nucleodevel.sisacad.domain.EstruturaCurricular;
import org.nucleodevel.sisacad.repositories.EstruturaCurricularRepository;
import org.nucleodevel.sisacad.services.exceptions.DataIntegrityException;
import org.nucleodevel.sisacad.services.exceptions.FieldValidationException;
import org.nucleodevel.sisacad.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class EstruturaCurricularService
		extends AbstractService<EstruturaCurricular, Integer, EstruturaCurricularRepository> {

	@Autowired
	private CursoService cursoService;

	@Override
	public void validadeForInsertUpdate(EstruturaCurricular entity) {
		String error = "";

		if (!StringUtils.hasText(entity.getCodigo())) {
			error += "Código pendente; ";
		}

		if (entity.getAnoInicio() == null) {
			error += "Ano de início pendente; ";
		}

		if (entity.getAnoTermino() == null) {
			error += "Ano de término pendente; ";
		}

		if (entity.getAnoInicio() > entity.getAnoTermino()) {
			error += "Ano de início posterior ao de término; ";
		}

		if (entity.getCurso() == null) {
			error += "Curso pendente; ";
		} else {
			try {
				cursoService.find(entity.getCurso().getId());
			} catch (ObjectNotFoundException e) {
				error += "Curso com ID " + entity.getCurso().getId() + " não existente; ";
			}
		}

		if (!error.isEmpty()) {
			throw new FieldValidationException(entity.getId(), getEntityClass(), error);
		}

		String myCodigo = entity.getCodigo();

		Optional<EstruturaCurricular> similarByCodigo = repository.findSimilarByCodigo(entity.getId(), myCodigo);
		similarByCodigo.ifPresent(obj -> {
			throw new DataIntegrityException("Já existe uma estrutura curricular com esse código!");
		});
	}

	@Override
	public List<EstruturaCurricular> findAll() {
		return repository.findByOrderByAnoInicioDescAnoTerminoDesc();
	}

}
