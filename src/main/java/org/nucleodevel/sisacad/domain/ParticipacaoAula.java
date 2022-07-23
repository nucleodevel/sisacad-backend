package org.nucleodevel.sisacad.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class ParticipacaoAula extends AbstractEntity<Integer> {

	private static final long serialVersionUID = -8806725119654390505L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "id_aula")
	private Aula aula;

	@ManyToOne
	@JoinColumn(name = "id_discente")
	private Discente discente;

	public ParticipacaoAula() {
		super();
	}

	public ParticipacaoAula(Aula aula, Discente discente) {
		super();
		this.aula = aula;
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

	public Aula getAula() {
		return aula;
	}

	public void setAula(Aula aula) {
		this.aula = aula;
	}

	public Discente getDiscente() {
		return discente;
	}

	public void setDiscente(Discente discente) {
		this.discente = discente;
	}

}
