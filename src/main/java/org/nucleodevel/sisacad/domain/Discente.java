package org.nucleodevel.sisacad.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Discente extends AbstractEntity<Integer> {

	private static final long serialVersionUID = -7713772334666831699L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@OneToOne
	@JoinColumn(name = "id_vestibulando")
	private Vestibulando vestibulando;

	@OneToMany(mappedBy = "discente")
	private List<ParticipacaoAula> listaParticipacaoAula = new ArrayList<>();

	@OneToMany(mappedBy = "discente")
	private List<ParticipacaoAvaliacao> listaParticipacaoAvaliacao = new ArrayList<>();

	@ManyToMany
	@JoinTable(name = "oferta_disciplina_discente", joinColumns = @JoinColumn(name = "id_discente"), inverseJoinColumns = @JoinColumn(name = "id_oferta_disciplina"))
	private List<OfertaDisciplina> listaOfertaDisciplina = new ArrayList<>();

	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;
	}

	public Vestibulando getVestibulando() {
		return vestibulando;
	}

	public void setVestibulando(Vestibulando vestibulando) {
		this.vestibulando = vestibulando;
	}

	public List<ParticipacaoAula> getListaParticipacaoAula() {
		return listaParticipacaoAula;
	}

	public List<ParticipacaoAvaliacao> getListaParticipacaoAvaliacao() {
		return listaParticipacaoAvaliacao;
	}

	public List<OfertaDisciplina> getListaOfertaDisciplina() {
		return listaOfertaDisciplina;
	}

	public void setListaOfertaDisciplina(List<OfertaDisciplina> listaOfertaDisciplina) {
		this.listaOfertaDisciplina = listaOfertaDisciplina;
	}

}
