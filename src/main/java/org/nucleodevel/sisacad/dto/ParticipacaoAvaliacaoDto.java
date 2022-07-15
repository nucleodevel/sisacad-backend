package org.nucleodevel.sisacad.dto;

import org.nucleodevel.sisacad.domain.ParticipacaoAvaliacao;

public class ParticipacaoAvaliacaoDto extends AbstractDto<ParticipacaoAvaliacao, Integer> {

	private static final long serialVersionUID = 8835147583838231255L;

	private Integer avaliacao;
	private Integer discente;

	@Override
	public void copyFromEntity(ParticipacaoAvaliacao entity) {
		this.id = entity.getId();
		this.avaliacao = entity.getAvaliacao().getId();
		this.discente = entity.getDiscente().getId();
	}

	public Integer getAvaliacao() {
		return avaliacao;
	}

	public void setAvaliacao(Integer avaliacao) {
		this.avaliacao = avaliacao;
	}

	public Integer getDiscente() {
		return discente;
	}

	public void setDiscente(Integer discente) {
		this.discente = discente;
	}

}
