package org.nucleodevel.sisacad.dto;

import org.nucleodevel.sisacad.domain.ParticipacaoAvaliacao;

public class ParticipacaoAvaliacaoDto extends AbstractDto<ParticipacaoAvaliacao, Integer> {

	private static final long serialVersionUID = 8835147583838231255L;

	private Double conceitoFinal;
	private Integer avaliacao;
	private Integer discente;

	@Override
	public void copyFromEntity(ParticipacaoAvaliacao entity) {
		this.id = entity.getId();
		this.conceitoFinal = entity.getConceitoFinal();
		this.avaliacao = entity.getAvaliacao().getId();
		this.discente = entity.getDiscente().getId();
	}

	public Double getConceitoFinal() {
		return conceitoFinal;
	}

	public void setConceitoFinal(Double conceitoFinal) {
		this.conceitoFinal = conceitoFinal;
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
