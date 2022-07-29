package org.nucleodevel.sisacad.dto;

import org.nucleodevel.sisacad.domain.Turma;

public class TurmaDto extends AbstractDto<Turma, Integer> {

	private static final long serialVersionUID = 8835147583838231255L;

	private String codigo;
	private Integer ofertaCurso;

	@Override
	public void copyFromEntity(Turma entity) {
		this.id = entity.getId();
		this.codigo = entity.getCodigo();
		this.ofertaCurso = entity.getOfertaCurso().getId();
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public Integer getOfertaCurso() {
		return ofertaCurso;
	}

	public void setOfertaCurso(Integer ofertaCurso) {
		this.ofertaCurso = ofertaCurso;
	}

}
