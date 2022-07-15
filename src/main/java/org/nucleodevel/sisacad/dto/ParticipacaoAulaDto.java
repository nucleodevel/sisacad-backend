package org.nucleodevel.sisacad.dto;

import org.nucleodevel.sisacad.domain.ParticipacaoAula;

public class ParticipacaoAulaDto extends AbstractDto<ParticipacaoAula, Integer> {

	private static final long serialVersionUID = 8835147583838231255L;

	private Integer aula;
	private Integer discente;

	@Override
	public void copyFromEntity(ParticipacaoAula entity) {
		this.id = entity.getId();
		this.aula = entity.getAula().getId();
		this.discente = entity.getDiscente().getId();
	}

	public Integer getAula() {
		return aula;
	}

	public void setAula(Integer aula) {
		this.aula = aula;
	}

	public Integer getDiscente() {
		return discente;
	}

	public void setDiscente(Integer discente) {
		this.discente = discente;
	}

}
