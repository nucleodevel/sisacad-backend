package org.nucleodevel.sisacad.dto;

import org.nucleodevel.sisacad.domain.Vestibular;

public class VestibularDto extends AbstractDto<Vestibular, Integer> {

	private static final long serialVersionUID = 8835147583838231255L;

	private Integer ano;

	@Override
	public void copyFromEntity(Vestibular entity) {
		this.id = entity.getId();
		this.ano = entity.getAno();
	}

	public Integer getAno() {
		return ano;
	}

	public void setAno(Integer ano) {
		this.ano = ano;
	}

}
