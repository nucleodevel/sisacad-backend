package org.nucleodevel.sisacad.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
public class Disciplina extends AbstractEntity<Integer> {

	private static final long serialVersionUID = -477986579635110766L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "nome")
	private String nome;

	@OneToMany(mappedBy = "disciplina")
	private List<OfertaDisciplina> listOfertaDisciplina = new ArrayList<>();

	@ManyToMany
	@JoinTable(name = "estrutura_curricular_disciplina", joinColumns = @JoinColumn(name = "id_disciplina"), inverseJoinColumns = @JoinColumn(name = "id_estrutura_curricular"))
	private List<EstruturaCurricular> listEstruturaCurricular = new ArrayList<>();

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

	public List<OfertaDisciplina> getListOfertaDisciplina() {
		return listOfertaDisciplina;
	}

	public List<EstruturaCurricular> getListEstruturaCurricular() {
		return listEstruturaCurricular;
	}

	public void setListEstruturaCurricular(List<EstruturaCurricular> listEstruturaCurricular) {
		this.listEstruturaCurricular = listEstruturaCurricular;
	}

	public void insertEstruturaCurricular(EstruturaCurricular estruturaCurricular) {
		boolean exists = false;

		for (EstruturaCurricular subItem : listEstruturaCurricular) {
			if (subItem.getId().equals(estruturaCurricular.getId())) {
				exists = true;
			}
		}

		if (!exists) {
			listEstruturaCurricular.add(estruturaCurricular);
		}
	}

	public void deleteEstruturaCurricular(EstruturaCurricular estruturaCurricular) {
		Integer position = null;

		for (int i = 0; i < listEstruturaCurricular.size(); i++) {
			EstruturaCurricular subItem = listEstruturaCurricular.get(i);
			if (subItem.getId().equals(estruturaCurricular.getId())) {
				position = i;
			}
		}

		if (position != null) {
			listEstruturaCurricular.remove(position.intValue());
		}
	}

}
