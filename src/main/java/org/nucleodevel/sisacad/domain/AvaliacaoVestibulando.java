package org.nucleodevel.sisacad.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class AvaliacaoVestibulando extends AbstractEntity<Integer> {

	private static final long serialVersionUID = 3280027092636858840L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "conceito_final")
	private Double conceitoFinal;

	@OneToOne
	@JoinColumn(name = "id_vestibulando")
	private Vestibulando vestibulando;

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

	public Vestibulando getVestibulando() {
		return vestibulando;
	}

	public void setVestibulando(Vestibulando vestibulando) {
		this.vestibulando = vestibulando;
	}

}
