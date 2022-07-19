package org.nucleodevel.sisacad.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.annotation.PostConstruct;

import org.nucleodevel.sisacad.domain.Aula;
import org.nucleodevel.sisacad.domain.Avaliacao;
import org.nucleodevel.sisacad.domain.AvaliacaoVestibulando;
import org.nucleodevel.sisacad.domain.Curso;
import org.nucleodevel.sisacad.domain.Discente;
import org.nucleodevel.sisacad.domain.Disciplina;
import org.nucleodevel.sisacad.domain.Docente;
import org.nucleodevel.sisacad.domain.EstruturaCurricular;
import org.nucleodevel.sisacad.domain.OfertaCurso;
import org.nucleodevel.sisacad.domain.OfertaDisciplina;
import org.nucleodevel.sisacad.domain.ParticipacaoAula;
import org.nucleodevel.sisacad.domain.ParticipacaoAvaliacao;
import org.nucleodevel.sisacad.domain.Turma;
import org.nucleodevel.sisacad.domain.Vestibulando;
import org.nucleodevel.sisacad.domain.Vestibular;
import org.nucleodevel.sisacad.repositories.AulaRepository;
import org.nucleodevel.sisacad.repositories.AvaliacaoRepository;
import org.nucleodevel.sisacad.repositories.AvaliacaoVestibulandoRepository;
import org.nucleodevel.sisacad.repositories.CursoRepository;
import org.nucleodevel.sisacad.repositories.DiscenteRepository;
import org.nucleodevel.sisacad.repositories.DisciplinaRepository;
import org.nucleodevel.sisacad.repositories.DocenteRepository;
import org.nucleodevel.sisacad.repositories.EstruturaCurricularRepository;
import org.nucleodevel.sisacad.repositories.OfertaCursoRepository;
import org.nucleodevel.sisacad.repositories.OfertaDisciplinaRepository;
import org.nucleodevel.sisacad.repositories.ParticipacaoAulaRepository;
import org.nucleodevel.sisacad.repositories.ParticipacaoAvaliacaoRepository;
import org.nucleodevel.sisacad.repositories.TurmaRepository;
import org.nucleodevel.sisacad.repositories.VestibulandoRepository;
import org.nucleodevel.sisacad.repositories.VestibularRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PopulaDados {

	@Autowired
	private AulaRepository aulaRepository;
	@Autowired
	private AvaliacaoRepository avaliacaoRepository;
	@Autowired
	private AvaliacaoVestibulandoRepository avaliacaoVestibulandoRepository;
	@Autowired
	private CursoRepository cursoRepository;
	@Autowired
	private DiscenteRepository discenteRepository;
	@Autowired
	private DisciplinaRepository disciplinaRepository;
	@Autowired
	private DocenteRepository docenteRepository;
	@Autowired
	private EstruturaCurricularRepository estruturaCurricularRepository;
	@Autowired
	private OfertaCursoRepository ofertaCursoRepository;
	@Autowired
	private OfertaDisciplinaRepository ofertaDisciplinaRepository;
	@Autowired
	private ParticipacaoAulaRepository participacaoAulaRepository;
	@Autowired
	private ParticipacaoAvaliacaoRepository participacaoAvaliacaoRepository;
	@Autowired
	private TurmaRepository turmaRepository;
	@Autowired
	private VestibulandoRepository vestibulandoRepository;
	@Autowired
	private VestibularRepository vestibularRepository;

	@PostConstruct
	public void cadastrar() throws ParseException {

		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");

		Curso cur1 = new Curso();
		cur1.setNome("Engenharia da Computação");

		cursoRepository.saveAll(List.of(cur1));

		Disciplina dcp1 = new Disciplina();
		dcp1.setNome("Compiladores");

		disciplinaRepository.saveAll(List.of(dcp1));

		EstruturaCurricular ec1 = new EstruturaCurricular();
		ec1.setAnoInicio(2010);
		ec1.setAnoTermino(2025);
		ec1.setCurso(cur1);
		ec1.setListDisciplina(List.of(dcp1));

		estruturaCurricularRepository.saveAll(List.of(ec1));

		Vestibular vst1 = new Vestibular();
		vst1.setAno(2022);

		vestibularRepository.saveAll(List.of(vst1));

		OfertaCurso oc1 = new OfertaCurso();
		oc1.setAno(2023);
		oc1.setEstruturaCurricular(ec1);
		oc1.setVestibular(vst1);

		ofertaCursoRepository.saveAll(List.of(oc1));

		Turma trm1 = new Turma();
		trm1.setOfertaCurso(oc1);

		turmaRepository.saveAll(List.of(trm1));

		Docente doc1 = new Docente();
		doc1.setNome("João Santos");

		docenteRepository.saveAll(List.of(doc1));

		Vestibulando vnd1 = new Vestibulando();
		vnd1.setNome("Renato Limeira");
		vnd1.setOfertaCurso(oc1);

		vestibulandoRepository.saveAll(List.of(vnd1));

		AvaliacaoVestibulando av1 = new AvaliacaoVestibulando();
		av1.setConceitoFinal(8.5);
		av1.setVestibulando(vnd1);

		avaliacaoVestibulandoRepository.saveAll(List.of(av1));

		Discente dct1 = new Discente();
		dct1.setVestibulando(vnd1);

		discenteRepository.saveAll(List.of(dct1));

		OfertaDisciplina od1 = new OfertaDisciplina();
		od1.setDisciplina(dcp1);
		od1.setDocente(doc1);
		od1.setListTurma(List.of(trm1));
		od1.setListDiscente(List.of(dct1));

		ofertaDisciplinaRepository.saveAll(List.of(od1));

		Avaliacao avl1 = new Avaliacao();
		avl1.setInicio(sdfDate.parse("2022-05-20"));
		avl1.setTermino(sdfDate.parse("2022-05-30"));
		avl1.setDescricao("Projeto Integrado");
		avl1.setOfertaDisciplina(od1);

		avaliacaoRepository.saveAll(List.of(avl1));

		ParticipacaoAvaliacao pav1 = new ParticipacaoAvaliacao();
		pav1.setAvaliacao(avl1);
		pav1.setDiscente(dct1);

		participacaoAvaliacaoRepository.saveAll(List.of(pav1));

		Aula aul1 = new Aula();
		aul1.setInicio(sdfDate.parse("2022-05-20"));
		aul1.setTermino(sdfDate.parse("2022-05-30"));
		aul1.setOfertaDisciplina(od1);

		aulaRepository.saveAll(List.of(aul1));

		ParticipacaoAula pal1 = new ParticipacaoAula();
		pal1.setAula(aul1);
		pal1.setDiscente(dct1);

		participacaoAulaRepository.saveAll(List.of(pal1));
	}

}
