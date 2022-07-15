package org.nucleodevel.sisacad.dto;

import org.nucleodevel.sisacad.domain.Discente;

public class DiscenteDto extends AbstractDto<Discente, Integer> {

	private static final long serialVersionUID = 8835147583838231255L;

	private Integer vestibulando;

	@Override
	public void copyFromEntity(Discente entity) {
		this.id = entity.getId();
		this.vestibulando = entity.getVestibulando().getId();
	}

	public Integer getVestibulando() {
		return vestibulando;
	}

	public void setVestibulando(Integer vestibulando) {
		this.vestibulando = vestibulando;
	}

}
