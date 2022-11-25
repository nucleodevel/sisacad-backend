package org.nucleodevel.sisacad.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import org.nucleodevel.sisacad.domain.Usuario;
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
import org.nucleodevel.sisacad.repositories.UsuarioRepository;
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
	private UsuarioRepository usuarioRepository;
	@Autowired
	private VestibulandoRepository vestibulandoRepository;
	@Autowired
	private VestibularRepository vestibularRepository;

	@PostConstruct
	public void cadastrar() throws ParseException {

		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdfDatetime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		List<Curso> listaCurso = cursoRepository.findAll();
		boolean preencherBase = listaCurso.size() == 0;

		if (preencherBase) {

			Usuario usr00 = new Usuario("frontend.key", "frontend.secret", "Siscadad- Frontend",
					"sisacad.frontend@email.com", "ROLE_ADMIN");

			Usuario usr01 = new Usuario("joao.santos", "pass.joao", "João Santos", "joao.santos@email.com",
					"ROLE_DOCENTE");
			Usuario usr02 = new Usuario("carlos.pereira", "pass.carlos", "Carlos Pereira", "carlos.pereira@email.com",
					"ROLE_DOCENTE");
			Usuario usr03 = new Usuario("marcos.benevides", "pass.marcos", "Marcos Benevides",
					"marcos.benevides@email.com", "ROLE_DOCENTE");
			Usuario usr04 = new Usuario("rosana.martins", "pass.rosana", "Rosana Martins", "rosana.martins@email.com",
					"ROLE_DISCENTE,ROLE_VESTIBULANDO");
			Usuario usr05 = new Usuario("osvaldo.picci", "pass.osvaldo", "Osvaldo Picci", "osvaldo.picci@email.com",
					"ROLE_DISCENTE,ROLE_VESTIBULANDO");
			Usuario usr06 = new Usuario("jose.albertino", "passjose", "José Albertino", "jose.albertino@email.com",
					"ROLE_DISCENTE,ROLE_VESTIBULANDO");
			Usuario usr07 = new Usuario("kelvin.smith", "pass.kelvin", "Kelvin Smith", "kelvin.smith@email.com",
					"ROLE_VESTIBULANDO");
			Usuario usr08 = new Usuario("melinda.flores", "pass.melinda", "Melinda Flores", "melinda.flores@email.com",
					"ROLE_DISCENTE,ROLE_VESTIBULANDO");
			Usuario usr09 = new Usuario("guilherme.toledo", "pass.guilherme", "Guilherme Toledo",
					"guilherme.toledo@email.com", "ROLE_DISCENTE,ROLE_VESTIBULANDO");
			Usuario usr10 = new Usuario("homero.assis", "pass.homero", "Homero Assis", "homero.assis@email.com",
					"ROLE_VESTIBULANDO");
			Usuario usr11 = new Usuario("carlos.balbuena", "pass.carlos", "Carlos Balbuena",
					"carlos.balbuena@email.com", "ROLE_DISCENTE,ROLE_VESTIBULANDO");
			Usuario usr12 = new Usuario("teo.lee", "pass.teo", "Teo Lee", "teo.lee@email.com",
					"ROLE_DISCENTE,ROLE_VESTIBULANDO");
			Usuario usr13 = new Usuario("neusa.feltrin", "pass.neusa", "Neusa Feltrin", "neusa.feltrin@email.com",
					"ROLE_DISCENTE,ROLE_VESTIBULANDO");
			Usuario usr14 = new Usuario("rolando.neves", "pass.rolando", "Rolando Neves", "rolando.neves@email.com",
					"ROLE_ADMIN");
			Usuario usr15 = new Usuario("paulo.tavarez", "pass.paulo", "Paulo Tavarez", "paulo.tavarez@email.com",
					"ROLE_PEDAGOGICO");
			Usuario usr16 = new Usuario("marina.coelho", "pass.marina", "Marina Coelho", "marina.coelho@email.com",
					"ROLE_GRADUACAO");

			usuarioRepository.saveAll(List.of(usr00, usr01, usr02, usr03, usr04, usr05, usr06, usr07, usr08, usr09,
					usr10, usr11, usr12, usr13, usr14, usr15, usr16));

			Curso cur1 = new Curso("CRS01", "Engenharia da Computação");
			Curso cur2 = new Curso("CRS02", "Ciência da Computação");

			cursoRepository.saveAll(List.of(cur1, cur2));

			Disciplina dcp1 = new Disciplina("DISC001", "Compiladores");
			Disciplina dcp2 = new Disciplina("DISC002", "Banco de Dados");
			Disciplina dcp3 = new Disciplina("DISC003", "Engenharia de Software");
			Disciplina dcp4 = new Disciplina("DISC004", "Programação Orientada a Objetos");

			disciplinaRepository.saveAll(List.of(dcp1, dcp2, dcp3, dcp4));

			EstruturaCurricular ec1 = new EstruturaCurricular("EC01", 1999, 2012, cur1);
			ec1.setListDisciplina(List.of(dcp1, dcp2));
			EstruturaCurricular ec2 = new EstruturaCurricular("EC02", 2013, 2026, cur1);
			ec2.setListDisciplina(List.of(dcp1, dcp2, dcp4));
			EstruturaCurricular ec3 = new EstruturaCurricular("EC03", 2018, 2031, cur2);
			ec3.setListDisciplina(List.of(dcp1, dcp2, dcp3, dcp4));

			estruturaCurricularRepository.saveAll(List.of(ec1, ec2, ec3));

			Vestibular vst1 = new Vestibular(2021);

			vestibularRepository.saveAll(List.of(vst1));

			OfertaCurso oc1 = new OfertaCurso("OC001", 2022, ec2, vst1);
			OfertaCurso oc2 = new OfertaCurso("OC002", 2022, ec3, vst1);

			ofertaCursoRepository.saveAll(List.of(oc1, oc2));

			Turma trm1 = new Turma("TRM001", oc1);
			Turma trm2 = new Turma("TRM002", oc2);

			turmaRepository.saveAll(List.of(trm1, trm2));

			Docente doc1 = new Docente("42262698023", sdfDate.parse("1981-02-23"),
					"Rua Pereira, Centro - Presidente Prudente/SP", "(18) 976543456", usr01);
			Docente doc2 = new Docente("57482695036", sdfDate.parse("1972-09-09"),
					"Rua Curitiba, Centro - Santo Anastácio/SP", "(18) 982736454", usr02);
			Docente doc3 = new Docente("13200083050", sdfDate.parse("1966-05-28"),
					"Rua Amapá, Centro - Martinópolis/SP", "(18) 999948888", usr03);

			docenteRepository.saveAll(List.of(doc1, doc2, doc3));

			Vestibulando vnd01 = new Vestibulando("12198374072", sdfDate.parse("2000-02-25"),
					"Rua A, Centro - Rosana/SP", "(18) 983667462", usr04, oc1);
			Vestibulando vnd02 = new Vestibulando("27411575020", sdfDate.parse("1992-01-01"),
					"Rua B, Centro - Primavera/SP", "(18) 981547812", usr05, oc1);
			Vestibulando vnd03 = new Vestibulando("43169615009", sdfDate.parse("2001-03-21"),
					"Rua C, Centro - Teodoro Sampaio/SP", "(18) 901635248", usr06, oc1);
			Vestibulando vnd04 = new Vestibulando("62635262039", sdfDate.parse("1997-08-15"),
					"Rua D, Centro - Piquerobi/SP", "(18) 992352412", usr07, oc1);
			Vestibulando vnd05 = new Vestibulando("75592129050", sdfDate.parse("1995-12-25"),
					"Rua E, Centro - Pirapozinho/SP", "(18) 900266153", usr08, oc1);
			Vestibulando vnd06 = new Vestibulando("43166274065", sdfDate.parse("2003-06-11"),
					"Rua F, Centro - Guarulhos/SP", "(11) 988776651", usr09, oc2);
			Vestibulando vnd07 = new Vestibulando("58351444073", sdfDate.parse("2001-11-02"),
					"Rua G, Centro - Osasco/SP", "(11) 901999283", usr10, oc2);
			Vestibulando vnd08 = new Vestibulando("78151416092", sdfDate.parse("1989-04-01"), "Rua H, Centro - Mauá/SP",
					"(11) 911223344", usr11, oc2);
			Vestibulando vnd09 = new Vestibulando("91370952007", sdfDate.parse("1998-07-12"),
					"Rua I, Centro - Diadema/SP", "(11) 911456689", usr12, oc2);
			Vestibulando vnd10 = new Vestibulando("40843830077", sdfDate.parse("2000-09-13"),
					"Rua J, Centro - São Paulo/SP", "(11) 900887624", usr13, oc2);

			vestibulandoRepository
					.saveAll(List.of(vnd01, vnd02, vnd03, vnd04, vnd05, vnd06, vnd07, vnd08, vnd09, vnd10));

			AvaliacaoVestibulando av01 = new AvaliacaoVestibulando(9.0, 8.0, 8.5, 8.5, vnd01);
			AvaliacaoVestibulando av02 = new AvaliacaoVestibulando(9.0, 7.0, 8.0, 8.0, vnd02);
			AvaliacaoVestibulando av03 = new AvaliacaoVestibulando(9.0, 9.2, 8.8, 9.0, vnd03);
			AvaliacaoVestibulando av04 = new AvaliacaoVestibulando(2.5, 6.0, 5.0, 4.5, vnd04);
			AvaliacaoVestibulando av05 = new AvaliacaoVestibulando(10.0, 5.0, 7.5, 7.5, vnd05);
			AvaliacaoVestibulando av06 = new AvaliacaoVestibulando(6.0, 6.0, 5.4, 5.8, vnd06);
			AvaliacaoVestibulando av07 = new AvaliacaoVestibulando(4.0, 3.5, 0.0, 2.5, vnd07);
			AvaliacaoVestibulando av08 = new AvaliacaoVestibulando(9.8, 9.7, 9.6, 9.7, vnd08);
			AvaliacaoVestibulando av09 = new AvaliacaoVestibulando(4.0, 8.0, 6.6, 6.2, vnd09);
			AvaliacaoVestibulando av10 = new AvaliacaoVestibulando(9.6, 8.0, 6.7, 8.1, vnd10);

			avaliacaoVestibulandoRepository
					.saveAll(List.of(av01, av02, av03, av04, av05, av06, av07, av08, av09, av10));

			Discente dct1 = new Discente(vnd01);
			Discente dct2 = new Discente(vnd02);
			Discente dct3 = new Discente(vnd03);
			Discente dct4 = new Discente(vnd05);
			Discente dct5 = new Discente(vnd06);
			Discente dct6 = new Discente(vnd08);
			Discente dct7 = new Discente(vnd09);
			Discente dct8 = new Discente(vnd10);

			discenteRepository.saveAll(List.of(dct1, dct2, dct3, dct4, dct5, dct6, dct7, dct8));

			OfertaDisciplina od1 = new OfertaDisciplina("OD0001", dcp1, doc1);
			od1.setListTurma(List.of(trm1));
			od1.setListDiscente(List.of(dct1, dct2, dct3, dct4));
			OfertaDisciplina od2 = new OfertaDisciplina("OD0002", dcp1, doc1);
			od2.setListTurma(List.of(trm2));
			od2.setListDiscente(List.of(dct5, dct6, dct7, dct8));
			OfertaDisciplina od3 = new OfertaDisciplina("OD0003", dcp2, doc3);
			od3.setListTurma(List.of(trm1, trm2));
			od3.setListDiscente(List.of(dct1, dct2, dct3, dct4, dct5, dct7, dct8));
			OfertaDisciplina od4 = new OfertaDisciplina("OD0004", dcp3, doc2);
			od4.setListTurma(List.of(trm2));
			od4.setListDiscente(List.of(dct5, dct6, dct7, dct8));
			OfertaDisciplina od5 = new OfertaDisciplina("OD0005", dcp4, doc2);
			od5.setListTurma(List.of(trm1, trm2));
			od5.setListDiscente(List.of(dct1, dct2, dct4, dct6, dct7, dct8));

			ofertaDisciplinaRepository.saveAll(List.of(od1, od2, od3, od4, od5));

			Aula aul01 = new Aula(sdfDatetime.parse("2022-05-20 13:00:00"), sdfDatetime.parse("2022-05-20 18:00:00"),
					od1);
			Aula aul02 = new Aula(sdfDatetime.parse("2022-05-30 08:00:00"), sdfDatetime.parse("2022-05-30 12:00:00"),
					od1);
			Aula aul03 = new Aula(sdfDatetime.parse("2022-05-28 19:00:00"), sdfDatetime.parse("2022-05-28 19:00:00"),
					od2);
			Aula aul04 = new Aula(sdfDatetime.parse("2022-04-20 13:00:00"), sdfDatetime.parse("2022-04-20 16:00:00"),
					od3);
			Aula aul05 = new Aula(sdfDatetime.parse("2022-06-02 10:00:00"), sdfDatetime.parse("2022-06-02 13:00:00"),
					od3);
			Aula aul06 = new Aula(sdfDatetime.parse("2022-05-18 09:00:00"), sdfDatetime.parse("2022-05-18 12:00:00"),
					od4);
			Aula aul07 = new Aula(sdfDatetime.parse("2022-06-15 14:00:00"), sdfDatetime.parse("2022-06-15 17:00:00"),
					od4);
			Aula aul08 = new Aula(sdfDatetime.parse("2022-05-01 20:00:00"), sdfDatetime.parse("2022-05-01 22:00:00"),
					od5);
			Aula aul09 = new Aula(sdfDatetime.parse("2022-06-30 14:00:00"), sdfDatetime.parse("2022-06-30 19:00:00"),
					od5);

			aulaRepository.saveAll(List.of(aul01, aul02, aul03, aul04, aul05, aul06, aul07, aul08, aul09));

			List<ParticipacaoAula> listParticipacaoAula = new ArrayList<>();

			listParticipacaoAula.add(new ParticipacaoAula(aul01, dct1));
			listParticipacaoAula.add(new ParticipacaoAula(aul01, dct2));
			listParticipacaoAula.add(new ParticipacaoAula(aul01, dct3));
			listParticipacaoAula.add(new ParticipacaoAula(aul01, dct4));

			listParticipacaoAula.add(new ParticipacaoAula(aul02, dct1));
			listParticipacaoAula.add(new ParticipacaoAula(aul02, dct2));
			listParticipacaoAula.add(new ParticipacaoAula(aul02, dct3));
			listParticipacaoAula.add(new ParticipacaoAula(aul02, dct4));

			listParticipacaoAula.add(new ParticipacaoAula(aul03, dct5));
			listParticipacaoAula.add(new ParticipacaoAula(aul03, dct6));
			listParticipacaoAula.add(new ParticipacaoAula(aul03, dct7));
			listParticipacaoAula.add(new ParticipacaoAula(aul03, dct8));

			listParticipacaoAula.add(new ParticipacaoAula(aul04, dct1));
			listParticipacaoAula.add(new ParticipacaoAula(aul04, dct2));
			listParticipacaoAula.add(new ParticipacaoAula(aul04, dct3));
			listParticipacaoAula.add(new ParticipacaoAula(aul04, dct4));
			listParticipacaoAula.add(new ParticipacaoAula(aul04, dct5));
			listParticipacaoAula.add(new ParticipacaoAula(aul04, dct7));
			listParticipacaoAula.add(new ParticipacaoAula(aul04, dct8));

			listParticipacaoAula.add(new ParticipacaoAula(aul05, dct1));
			listParticipacaoAula.add(new ParticipacaoAula(aul05, dct2));
			listParticipacaoAula.add(new ParticipacaoAula(aul05, dct3));
			listParticipacaoAula.add(new ParticipacaoAula(aul05, dct4));
			listParticipacaoAula.add(new ParticipacaoAula(aul05, dct5));
			listParticipacaoAula.add(new ParticipacaoAula(aul05, dct7));
			listParticipacaoAula.add(new ParticipacaoAula(aul05, dct8));

			listParticipacaoAula.add(new ParticipacaoAula(aul06, dct5));
			listParticipacaoAula.add(new ParticipacaoAula(aul06, dct6));
			listParticipacaoAula.add(new ParticipacaoAula(aul06, dct7));
			listParticipacaoAula.add(new ParticipacaoAula(aul06, dct8));

			listParticipacaoAula.add(new ParticipacaoAula(aul07, dct5));
			listParticipacaoAula.add(new ParticipacaoAula(aul07, dct6));
			listParticipacaoAula.add(new ParticipacaoAula(aul07, dct7));
			listParticipacaoAula.add(new ParticipacaoAula(aul07, dct8));

			listParticipacaoAula.add(new ParticipacaoAula(aul08, dct1));
			listParticipacaoAula.add(new ParticipacaoAula(aul08, dct2));
			listParticipacaoAula.add(new ParticipacaoAula(aul08, dct4));
			listParticipacaoAula.add(new ParticipacaoAula(aul08, dct6));
			listParticipacaoAula.add(new ParticipacaoAula(aul08, dct7));
			listParticipacaoAula.add(new ParticipacaoAula(aul08, dct8));

			listParticipacaoAula.add(new ParticipacaoAula(aul09, dct1));
			listParticipacaoAula.add(new ParticipacaoAula(aul09, dct2));
			listParticipacaoAula.add(new ParticipacaoAula(aul09, dct4));
			listParticipacaoAula.add(new ParticipacaoAula(aul09, dct6));
			listParticipacaoAula.add(new ParticipacaoAula(aul09, dct7));
			listParticipacaoAula.add(new ParticipacaoAula(aul09, dct8));

			participacaoAulaRepository.saveAll(listParticipacaoAula);

			Avaliacao avl01 = new Avaliacao("Projeto Integrado", sdfDate.parse("2022-05-20"),
					sdfDate.parse("2022-06-30"), od1);
			Avaliacao avl02 = new Avaliacao("Prova Final", sdfDate.parse("2022-05-30"), sdfDate.parse("2022-05-30"),
					od1);
			Avaliacao avl03 = new Avaliacao("Prova Final", sdfDate.parse("2022-05-28"), sdfDate.parse("2022-05-28"),
					od2);
			Avaliacao avl04 = new Avaliacao("Projeto Integrado", sdfDate.parse("2022-04-20"),
					sdfDate.parse("2022-05-28"), od3);
			Avaliacao avl05 = new Avaliacao("Prova Final", sdfDate.parse("2022-06-02"), sdfDate.parse("2022-06-02"),
					od3);
			Avaliacao avl06 = new Avaliacao("Projeto Integrado", sdfDate.parse("2022-05-18"),
					sdfDate.parse("2022-06-18"), od4);
			Avaliacao avl07 = new Avaliacao("Prova Final", sdfDate.parse("2022-06-15"), sdfDate.parse("2022-06-15"),
					od4);
			Avaliacao avl08 = new Avaliacao("Projeto Integrado", sdfDate.parse("2022-05-01"),
					sdfDate.parse("2022-06-14"), od5);
			Avaliacao avl09 = new Avaliacao("Prova Final", sdfDate.parse("2022-06-30"), sdfDate.parse("2022-06-30"),
					od5);

			avaliacaoRepository.saveAll(List.of(avl01, avl02, avl03, avl04, avl05, avl06, avl07, avl08, avl09));

			List<ParticipacaoAvaliacao> listParticipacaoAvaliacao = new ArrayList<>();

			listParticipacaoAvaliacao.add(new ParticipacaoAvaliacao(9.0, avl01, dct1));
			listParticipacaoAvaliacao.add(new ParticipacaoAvaliacao(8.5, avl01, dct2));
			listParticipacaoAvaliacao.add(new ParticipacaoAvaliacao(10.0, avl01, dct3));
			listParticipacaoAvaliacao.add(new ParticipacaoAvaliacao(6.5, avl01, dct4));

			listParticipacaoAvaliacao.add(new ParticipacaoAvaliacao(7.0, avl02, dct1));
			listParticipacaoAvaliacao.add(new ParticipacaoAvaliacao(6.9, avl02, dct2));
			listParticipacaoAvaliacao.add(new ParticipacaoAvaliacao(4.8, avl02, dct3));
			listParticipacaoAvaliacao.add(new ParticipacaoAvaliacao(7.7, avl02, dct4));

			listParticipacaoAvaliacao.add(new ParticipacaoAvaliacao(6.8, avl03, dct5));
			listParticipacaoAvaliacao.add(new ParticipacaoAvaliacao(2.4, avl03, dct6));
			listParticipacaoAvaliacao.add(new ParticipacaoAvaliacao(9.2, avl03, dct7));
			listParticipacaoAvaliacao.add(new ParticipacaoAvaliacao(8.7, avl03, dct8));

			listParticipacaoAvaliacao.add(new ParticipacaoAvaliacao(5.5, avl04, dct1));
			listParticipacaoAvaliacao.add(new ParticipacaoAvaliacao(6.9, avl04, dct2));
			listParticipacaoAvaliacao.add(new ParticipacaoAvaliacao(7.4, avl04, dct3));
			listParticipacaoAvaliacao.add(new ParticipacaoAvaliacao(3.2, avl04, dct4));
			listParticipacaoAvaliacao.add(new ParticipacaoAvaliacao(3.8, avl04, dct5));
			listParticipacaoAvaliacao.add(new ParticipacaoAvaliacao(8.1, avl04, dct7));
			listParticipacaoAvaliacao.add(new ParticipacaoAvaliacao(9.0, avl04, dct8));

			listParticipacaoAvaliacao.add(new ParticipacaoAvaliacao(6.0, avl05, dct1));
			listParticipacaoAvaliacao.add(new ParticipacaoAvaliacao(4.8, avl05, dct2));
			listParticipacaoAvaliacao.add(new ParticipacaoAvaliacao(4.7, avl05, dct3));
			listParticipacaoAvaliacao.add(new ParticipacaoAvaliacao(6.9, avl05, dct4));
			listParticipacaoAvaliacao.add(new ParticipacaoAvaliacao(9.8, avl05, dct5));
			listParticipacaoAvaliacao.add(new ParticipacaoAvaliacao(3.7, avl05, dct7));
			listParticipacaoAvaliacao.add(new ParticipacaoAvaliacao(7.8, avl05, dct8));

			listParticipacaoAvaliacao.add(new ParticipacaoAvaliacao(5.4, avl06, dct5));
			listParticipacaoAvaliacao.add(new ParticipacaoAvaliacao(2.7, avl06, dct6));
			listParticipacaoAvaliacao.add(new ParticipacaoAvaliacao(6.5, avl06, dct7));
			listParticipacaoAvaliacao.add(new ParticipacaoAvaliacao(6.9, avl06, dct8));

			listParticipacaoAvaliacao.add(new ParticipacaoAvaliacao(8.6, avl07, dct5));
			listParticipacaoAvaliacao.add(new ParticipacaoAvaliacao(3.7, avl07, dct6));
			listParticipacaoAvaliacao.add(new ParticipacaoAvaliacao(9.1, avl07, dct7));
			listParticipacaoAvaliacao.add(new ParticipacaoAvaliacao(7.0, avl07, dct8));

			listParticipacaoAvaliacao.add(new ParticipacaoAvaliacao(8.0, avl08, dct1));
			listParticipacaoAvaliacao.add(new ParticipacaoAvaliacao(6.7, avl08, dct2));
			listParticipacaoAvaliacao.add(new ParticipacaoAvaliacao(5.9, avl08, dct4));
			listParticipacaoAvaliacao.add(new ParticipacaoAvaliacao(5.4, avl08, dct6));
			listParticipacaoAvaliacao.add(new ParticipacaoAvaliacao(4.2, avl08, dct7));
			listParticipacaoAvaliacao.add(new ParticipacaoAvaliacao(3.7, avl08, dct8));

			listParticipacaoAvaliacao.add(new ParticipacaoAvaliacao(5.7, avl09, dct1));
			listParticipacaoAvaliacao.add(new ParticipacaoAvaliacao(6.9, avl09, dct2));
			listParticipacaoAvaliacao.add(new ParticipacaoAvaliacao(6.1, avl09, dct4));
			listParticipacaoAvaliacao.add(new ParticipacaoAvaliacao(9.5, avl09, dct6));
			listParticipacaoAvaliacao.add(new ParticipacaoAvaliacao(8.7, avl09, dct7));
			listParticipacaoAvaliacao.add(new ParticipacaoAvaliacao(7.3, avl09, dct8));

			participacaoAvaliacaoRepository.saveAll(listParticipacaoAvaliacao);
		}
	}

}
