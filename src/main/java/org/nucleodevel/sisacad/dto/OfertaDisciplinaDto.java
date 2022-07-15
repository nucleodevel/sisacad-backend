package org.nucleodevel.sisacad.dto;

import org.nucleodevel.sisacad.domain.OfertaDisciplina;

public class OfertaDisciplinaDto extends AbstractDto<OfertaDisciplina, Integer> {

	private static final long serialVersionUID = 8835147583838231255L;

	private Integer disciplina;
	private Integer docente;

	@Override
	public void copyFromEntity(OfertaDisciplina entity) {
		this.id = entity.getId();
		this.disciplina = entity.getDisciplina().getId();
		this.docente = entity.getDocente().getId();
	}

	public Integer getDisciplina() {
		return disciplina;
	}

	public void setDisciplina(Integer disciplina) {
		this.disciplina = disciplina;
	}

	public Integer getDocente() {
		return docente;
	}

	public void setDocente(Integer docente) {
		this.docente = docente;
	}

}
