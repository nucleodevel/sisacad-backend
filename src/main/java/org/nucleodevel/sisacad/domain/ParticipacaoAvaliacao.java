package org.nucleodevel.sisacad.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class ParticipacaoAvaliacao extends AbstractEntity<Integer> {

	private static final long serialVersionUID = -1901027316830428328L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "conceito_final")
	private Double conceitoFinal;

	@ManyToOne
	@JoinColumn(name = "id_avaliacao")
	private Avaliacao avaliacao;

	@ManyToOne
	@JoinColumn(name = "id_discente")
	private Discente discente;

	public ParticipacaoAvaliacao() {
		super();
	}

	public ParticipacaoAvaliacao(Double conceitoFinal, Avaliacao avaliacao, Discente discente) {
		super();
		this.conceitoFinal = conceitoFinal;
		this.avaliacao = avaliacao;
		this.discente = discente;
	}

	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;
	}

	public Double getConceitoFinal() {
		return conceitoFinal;
	}

	public void setConceitoFinal(Double conceitoFinal) {
		this.conceitoFinal = conceitoFinal;
	}

	public Avaliacao getAvaliacao() {
		return avaliacao;
	}

	public void setAvaliacao(Avaliacao avaliacao) {
		this.avaliacao = avaliacao;
	}

	public Discente getDiscente() {
		return discente;
	}

	public void setDiscente(Discente discente) {
		this.discente = discente;
	}

}
