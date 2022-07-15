package org.nucleodevel.sisacad.dto;

import java.util.Date;

import org.nucleodevel.sisacad.domain.Aula;

public class AulaDto extends AbstractDto<Aula, Integer> {

	private static final long serialVersionUID = 8835147583838231255L;

	private Date inicio;
	private Date termino;
	private Integer ofertaDisciplina;

	@Override
	public void copyFromEntity(Aula entity) {
		this.id = entity.getId();
		this.inicio = entity.getInicio();
		this.termino = entity.getTermino();
		this.ofertaDisciplina = entity.getOfertaDisciplina().getId();
	}

	public Date getInicio() {
		return inicio;
	}

	public void setInicio(Date inicio) {
		this.inicio = inicio;
	}

	public Date getTermino() {
		return termino;
	}

	public void setTermino(Date termino) {
		this.termino = termino;
	}

	public Integer getOfertaDisciplina() {
		return ofertaDisciplina;
	}

	public void setOfertaDisciplina(Integer ofertaDisciplina) {
		this.ofertaDisciplina = ofertaDisciplina;
	}

}
