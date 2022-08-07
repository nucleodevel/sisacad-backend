package org.nucleodevel.sisacad.dto;

import org.nucleodevel.sisacad.domain.AvaliacaoVestibulando;

public class AvaliacaoVestibulandoDto extends AbstractDto<AvaliacaoVestibulando, Integer> {

	private static final long serialVersionUID = 8835147583838231255L;

	private Double conceitoBiologicas;
	private Double conceitoExatas;
	private Double conceitoHumanas;
	private Double conceitoFinal;
	private Integer vestibulando;

	@Override
	public void copyFromEntity(AvaliacaoVestibulando entity) {
		this.id = entity.getId();
		this.conceitoBiologicas = entity.getConceitoBiologicas();
		this.conceitoExatas = entity.getConceitoExatas();
		this.conceitoHumanas = entity.getConceitoHumanas();
		this.conceitoFinal = entity.getConceitoFinal();
		this.vestibulando = entity.getVestibulando().getId();
	}

	public Double getConceitoBiologicas() {
		return conceitoBiologicas;
	}

	public void setConceitoBiologicas(Double conceitoBiologicas) {
		this.conceitoBiologicas = conceitoBiologicas;
	}

	public Double getConceitoExatas() {
		return conceitoExatas;
	}

	public void setConceitoExatas(Double conceitoExatas) {
		this.conceitoExatas = conceitoExatas;
	}

	public Double getConceitoHumanas() {
		return conceitoHumanas;
	}

	public void setConceitoHumanas(Double conceitoHumanas) {
		this.conceitoHumanas = conceitoHumanas;
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
