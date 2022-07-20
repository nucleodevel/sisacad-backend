package org.nucleodevel.sisacad.dto;

import org.nucleodevel.sisacad.domain.Aula;

public class AulaDto extends AbstractDto<Aula, Integer> {

	private static final long serialVersionUID = 8835147583838231255L;

	private Long inicio;
	private Long termino;
	private Integer ofertaDisciplina;

	@Override
	public void copyFromEntity(Aula entity) {
		this.id = entity.getId();
		this.inicio = entity.getInicio() == null ? null : entity.getInicio().getTime();
		this.termino = entity.getTermino() == null ? null : entity.getTermino().getTime();
		this.ofertaDisciplina = entity.getOfertaDisciplina().getId();
	}

	public Long getInicio() {
		return inicio;
	}

	public void setInicio(Long inicio) {
		this.inicio = inicio;
	}

	public Long getTermino() {
		return termino;
	}

	public void setTermino(Long termino) {
		this.termino = termino;
	}

	public Integer getOfertaDisciplina() {
		return ofertaDisciplina;
	}

	public void setOfertaDisciplina(Integer ofertaDisciplina) {
		this.ofertaDisciplina = ofertaDisciplina;
	}

}
