package org.nucleodevel.sisacad.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class Vestibulando extends AbstractEntity<Integer> {

	private static final long serialVersionUID = -4581509764218441013L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "cpf")
	private String cpf;

	@Column(name = "nome")
	private String nome;

	@Column(name = "data_nascimento")
	private Date dataNascimento;

	@Column(name = "endereco")
	private String endereco;

	@Column(name = "telefones")
	private String telefones;

	@ManyToOne
	@JoinColumn(name = "id_oferta_curso")
	private OfertaCurso ofertaCurso;

	@OneToOne(mappedBy = "vestibulando")
	private AvaliacaoVestibulando avaliacaoVestibulando;

	@OneToOne(mappedBy = "vestibulando")
	private Discente discente;

	public Vestibulando() {
		super();
	}

	public Vestibulando(String cpf, String nome, Date dataNascimento, String endereco, String telefones,
			OfertaCurso ofertaCurso) {
		super();
		this.cpf = cpf;
		this.nome = nome;
		this.dataNascimento = dataNascimento;
		this.endereco = endereco;
		this.telefones = telefones;
		this.ofertaCurso = ofertaCurso;
	}

	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;
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

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
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

	public OfertaCurso getOfertaCurso() {
		return ofertaCurso;
	}

	public void setOfertaCurso(OfertaCurso ofertaCurso) {
		this.ofertaCurso = ofertaCurso;
	}

	public AvaliacaoVestibulando getAvaliacaoVestibulando() {
		return avaliacaoVestibulando;
	}

	public Discente getDiscente() {
		return discente;
	}

}
