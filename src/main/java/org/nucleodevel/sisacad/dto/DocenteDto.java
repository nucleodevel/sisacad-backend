package org.nucleodevel.sisacad.dto;

import org.nucleodevel.sisacad.domain.Docente;

public class DocenteDto extends AbstractDto<Docente, Integer> {

	private static final long serialVersionUID = 8835147583838231255L;

	private String cpf;
	private String nome;

	@Override
	public void copyFromEntity(Docente entity) {
		this.id = entity.getId();
		this.cpf = entity.getCpf();
		this.nome = entity.getNome();
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}
