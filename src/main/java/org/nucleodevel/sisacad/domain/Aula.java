package org.nucleodevel.sisacad.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Aula extends AbstractEntity<Integer> {

	private static final long serialVersionUID = 604653008558080322L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "inicio")
	private Date inicio;

	@Column(name = "termino")
	private Date termino;

	@ManyToOne
	@JoinColumn(name = "id_oferta_disciplina")
	private OfertaDisciplina ofertaDisciplina;

	@OneToMany(mappedBy = "aula")
	private List<ParticipacaoAula> listParticipacaoAula = new ArrayList<>();

	public Aula() {
		super();
	}

	public Aula(Date inicio, Date termino, OfertaDisciplina ofertaDisciplina) {
		super();
		this.inicio = inicio;
		this.termino = termino;
		this.ofertaDisciplina = ofertaDisciplina;
	}

	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;
	}

	public Date getInicio() {
		return inicio;
	}

	public void setInicio(Date inicio) {
		this.inicio = inicio;
	}

	public Date getTermino() {
		return termino;
	}

	public void setTermino(Date termino) {
		this.termino = termino;
	}

	public OfertaDisciplina getOfertaDisciplina() {
		return ofertaDisciplina;
	}

	public void setOfertaDisciplina(OfertaDisciplina ofertaDisciplina) {
		this.ofertaDisciplina = ofertaDisciplina;
	}

	public List<ParticipacaoAula> getListParticipacaoAula() {
		return listParticipacaoAula;
	}

	@Override
	public String toComparableString() {
		return getOfertaDisciplina().toComparableString() + "//" + getInicio();
	}

}
