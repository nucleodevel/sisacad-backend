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

	@Column(name = "conceito_biologicas")
	private Double conceitoBiologicas;

	@Column(name = "conceito_exatas")
	private Double conceitoExatas;

	@Column(name = "conceito_humanas")
	private Double conceitoHumanas;

	@Column(name = "conceito_final")
	private Double conceitoFinal;

	@OneToOne
	@JoinColumn(name = "id_vestibulando")
	private Vestibulando vestibulando;

	public AvaliacaoVestibulando() {
		super();
	}

	public AvaliacaoVestibulando(Double conceitoBiologicas, Double conceitoExatas, Double conceitoHumanas,
			Double conceitoFinal, Vestibulando vestibulando) {
		super();
		this.conceitoBiologicas = conceitoBiologicas;
		this.conceitoExatas = conceitoExatas;
		this.conceitoHumanas = conceitoHumanas;
		this.conceitoFinal = conceitoFinal;
		this.vestibulando = vestibulando;
	}

	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;
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

	public Vestibulando getVestibulando() {
		return vestibulando;
	}

	public void setVestibulando(Vestibulando vestibulando) {
		this.vestibulando = vestibulando;
	}

	@Override
	public String toComparableString() {
		return getVestibulando().toComparableString();
	}

}
