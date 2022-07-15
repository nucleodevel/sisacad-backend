package org.nucleodevel.sisacad.dto;

import org.nucleodevel.sisacad.domain.AvaliacaoVestibulando;

public class AvaliacaoVestibulandoDto extends AbstractDto<AvaliacaoVestibulando, Integer> {

	private static final long serialVersionUID = 8835147583838231255L;

	private Double conceitoFinal;
	private Integer vestibulando;

	@Override
	public void copyFromEntity(AvaliacaoVestibulando entity) {
		this.id = entity.getId();
		this.conceitoFinal = entity.getConceitoFinal();
		this.vestibulando = entity.getVestibulando().getId();
	}

	public Double getConceitoFinal() {
		return conceitoFinal;
	}

	public void setConceitoFinal(Double conceitoFinal) {
		this.conceitoFinal = conceitoFinal;
	}

	public Integer getVestibulando() {
		return vestibulando;
	}

	public void setVestibulando(Integer vestibulando) {
		this.vestibulando = vestibulando;
	}

}
