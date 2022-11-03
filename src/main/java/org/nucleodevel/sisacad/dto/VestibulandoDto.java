package org.nucleodevel.sisacad.dto;

import org.nucleodevel.sisacad.domain.Vestibulando;

public class VestibulandoDto extends AbstractDto<Vestibulando, Integer> {

	private static final long serialVersionUID = 8835147583838231255L;

	private String cpf;
	private Long dataNascimento;
	private String endereco;
	private String telefones;
	private Integer ofertaCurso;
	private Integer usuario;
	private Integer avaliacaoVestibulando;
	private Integer discente;

	@Override
	public void copyFromEntity(Vestibulando entity) {
		this.id = entity.getId();
		this.cpf = entity.getCpf();
		this.dataNascimento = entity.getDataNascimento() == null ? null : entity.getDataNascimento().getTime();
		this.endereco = entity.getEndereco();
		this.telefones = entity.getTelefones();
		this.ofertaCurso = entity.getOfertaCurso().getId();

		if (entity.getUsuario() != null) {
			this.usuario = entity.getUsuario().getId();
		}

		this.avaliacaoVestibulando = entity.getAvaliacaoVestibulando() == null ? null
				: entity.getAvaliacaoVestibulando().getId();
		this.discente = entity.getDiscente() == null ? null : entity.getDiscente().getId();
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
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

	public Integer getOfertaCurso() {
		return ofertaCurso;
	}

	public void setOfertaCurso(Integer ofertaCurso) {
		this.ofertaCurso = ofertaCurso;
	}

	public Integer getUsuario() {
		return usuario;
	}

	public void setUsuario(Integer usuario) {
		this.usuario = usuario;
	}

	public Integer getAvaliacaoVestibulando() {
		return avaliacaoVestibulando;
	}

	public void setAvaliacaoVestibulando(Integer avaliacaoVestibulando) {
		this.avaliacaoVestibulando = avaliacaoVestibulando;
	}

	public Integer getDiscente() {
		return discente;
	}

	public void setDiscente(Integer discente) {
		this.discente = discente;
	}

}
