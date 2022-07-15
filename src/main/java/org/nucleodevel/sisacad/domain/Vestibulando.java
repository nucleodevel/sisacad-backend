package org.nucleodevel.sisacad.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Vestibulando extends AbstractEntity<Integer> {

	private static final long serialVersionUID = -4581509764218441013L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "nome")
	private String nome;

	@OneToOne
	@JoinColumn(name = "id_oferta_turma")
	private OfertaCurso ofertaCurso;

	@OneToOne(mappedBy = "vestibulando")
	private AvaliacaoVestibulando avaliacaoVestibulando;

	@OneToOne(mappedBy = "vestibulando")
	private Discente discente;

	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
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
