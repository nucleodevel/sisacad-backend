package org.nucleodevel.sisacad.dto;

import org.nucleodevel.sisacad.domain.Docente;

public class DocenteDto extends AbstractDto<Docente, Integer> {

	private static final long serialVersionUID = 8835147583838231255L;

	private String cpf;
	private String nome;
	private Long dataNascimento;
	private String endereco;
	private String telefones;

	@Override
	public void copyFromEntity(Docente entity) {
		this.id = entity.getId();
		this.cpf = entity.getCpf();
		this.nome = entity.getNome();
		this.dataNascimento = entity.getDataNascimento() == null ? null : entity.getDataNascimento().getTime();
		this.endereco = entity.getEndereco();
		this.telefones = entity.getTelefones();
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

	public Long getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Long dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getTelefones() {
		return telefones;
	}

	public void setTelefones(String telefones) {
		this.telefones = telefones;
	}

}
