package org.nucleodevel.sisacad.dto;

import org.nucleodevel.sisacad.domain.Turma;

public class TurmaDto extends AbstractDto<Turma, Integer> {

	private static final long serialVersionUID = 8835147583838231255L;

	private Integer ofertaCurso;

	@Override
	public void copyFromEntity(Turma entity) {
		this.id = entity.getId();
		this.ofertaCurso = entity.getOfertaCurso().getId();
	}

	public Integer getOfertaCurso() {
		return ofertaCurso;
	}

	public void setOfertaCurso(Integer ofertaCurso) {
		this.ofertaCurso = ofertaCurso;
	}

}
