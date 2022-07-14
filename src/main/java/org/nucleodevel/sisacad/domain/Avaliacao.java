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

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Avaliacao extends AbstractEntity<Integer> {

	private static final long serialVersionUID = 9009979602652888043L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "descricao")
	private String descricao;

	@Column(name = "inicio")
	private Date inicio;

	@Column(name = "termino")
	private Date termino;

	@ManyToOne
	@JoinColumn(name = "id_oferta_disciplina")
	private OfertaDisciplina ofertaDisciplina;

	@JsonIgnore
	@OneToMany(mappedBy = "avaliacao")
	private List<ParticipacaoAvaliacao> listaParticipacaoAvaliacao = new ArrayList<>();

	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
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

	public List<ParticipacaoAvaliacao> getListaParticipacaoAvaliacao() {
		return listaParticipacaoAvaliacao;
	}

	public void setListaParticipacaoAvaliacao(List<ParticipacaoAvaliacao> listaParticipacaoAvaliacao) {
		this.listaParticipacaoAvaliacao = listaParticipacaoAvaliacao;
	}

}
