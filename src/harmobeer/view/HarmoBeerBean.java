package harmobeer.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

import com.harmobeer.mvc.controller.AvaliacaoController;
import com.harmobeer.mvc.controller.CervejaController;
import com.harmobeer.mvc.controller.HarmonizacaoController;
import com.harmobeer.mvc.controller.PratoController;
import com.harmobeer.mvc.controller.UsuarioController;
import com.harmobeer.vo.Avaliacao;
import com.harmobeer.vo.Cerveja;
import com.harmobeer.vo.Harmonizacao;
import com.harmobeer.vo.Prato;
import com.harmobeer.vo.Usuario;

@ManagedBean(name = "harmoBeerBean")

@SessionScoped
public class HarmoBeerBean implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private String username;
	private String senha;
	private String senhaconf;
	private String senhaant;
	private String info;
	private String email;
	private int idUsuarioSelecionado;

	private String usernameSelec;
	private String infoSelec;

	private Usuario usuarioLogado;
	private Usuario usuarioCadastrado;
	private Usuario usuarioSelecionado;

	private UsuarioController usuarioController;

	private String buscar;

	private List<SelectItem> listaCerv = new ArrayList<SelectItem>();
	private List<SelectItem> listaPrato = new ArrayList<SelectItem>();
	private List<SelectItem> listaUser = new ArrayList<SelectItem>();
	private List<SelectItem> listaAval = new ArrayList<SelectItem>();

	private List<Cerveja> adminListaCerv = new ArrayList<Cerveja>();
	private List<Prato> adminListaPrato = new ArrayList<Prato>();
	private List<Usuario> adminListaUser = new ArrayList<Usuario>();
	private List<Avaliacao> adminListaAval = new ArrayList<Avaliacao>();

	private boolean editado = false;
	private boolean deletado = false;
	private boolean cadastrado = false;
	private boolean renderizar = false;
	private boolean selecionado = false;
	private String nomeAdmin;

	private String nm_cerv;
	private String estilo_cerv;
	private double teor_alcoolico;

	private Cerveja cervejaSelecionada;
	private int idCervejaSelecionada;
	private Cerveja cervejaAdmin;
	private CervejaController cervejaController;

	private String nm_prato;

	private Prato pratoSelecionado;
	private int idPratoSelecionado;
	private Prato pratoAdmin;
	private PratoController pratoController;

	private double media;
	private ArrayList<Harmonizacao> ranking;

	private Harmonizacao harmonizacaoSelecionada;
	private HarmonizacaoController harmonizacaoController;

	private String comentario;
	private int nota;
	private int idAvalSelec;
	private String nmCervAvalSelec;
	private String nmPratoAvalSelec;
	private String usernameAvalSelec;
	private int notaAvalSelec;
	private String comentarioAvalSelec;
	private ArrayList<Avaliacao> avaliacoes = new ArrayList<Avaliacao>();

	private Avaliacao avaliacaoSelecionada;

	private AvaliacaoController avaliacaoController;

	/**
	 * Construtor que inicializa os controllers utilizados no bean
	 */

	public HarmoBeerBean() {
		usuarioLogado = new Usuario();

		usuarioController = new UsuarioController();

		cervejaController = new CervejaController();

		pratoController = new PratoController();

		harmonizacaoController = new HarmonizacaoController();

		avaliacaoController = new AvaliacaoController();

	}

	/**
	 * Metodo para logar no sistema. Antes de checar as credenciais de entrada,
	 * o metodo atualiza as medias das harmonizacoes.
	 * 
	 * @return acesso ao menu principal ou mensagem de erro ao logar.
	 */
	public String entrar() {

		// Atualizar as harmonizacoes ao logar no sistema
		ArrayList<Harmonizacao> h = new ArrayList<Harmonizacao>();
		h = harmonizacaoController.listarTodos();
		for (Harmonizacao ha : h) {
			harmonizacaoController.calcularMedia(ha);
		}

		usuarioLogado = usuarioController.logar(getUsername(), getSenha());

		if (usuarioLogado != null) {
			setEmail(usuarioLogado.getEmail());
			setInfo(usuarioLogado.getInfo());
			setSenhaconf(usuarioLogado.getSenha());

			return "/harmobeer/menuPrincipal";
		} else {
			zerarUsuario();
			return "/harmobeer/errorpage";
		}

	}

	/**
	 * Metodo para cadastrar o usuario no banco de dados.
	 * 
	 * @return pagina de confirmacao de cadastro ou de erro de cadastro.
	 */
	public String cadastrarUsuario() {

		if (senha.compareTo(senhaconf) == 0) {
			usuarioCadastrado = new Usuario(getUsername(), getEmail(), getSenha(), getInfo());
			if (usuarioController.incluir(usuarioCadastrado)) {
				zerarUsuario();
				return "/harmobeer/confirmaCadastro";
			} else {

				zerarUsuario();
				return "/usuario/erroCadastro";
			}
		} else {
			zerarUsuario();
			return "/usuario/erroCadastro";
		}

	}

	/**
	 * Metodo para alterar a senha do usuario logado
	 * 
	 * @return pagina de confirmacao de alteracao ou erro na alteração
	 */
	public String alterarSenha() {

		String senhaAnterior = usuarioLogado.getSenha();

		if (senha.compareTo(senhaconf) == 0 && senhaAnterior.compareTo(senhaant) == 0) {
			usuarioLogado.setSenha(senha);

			if (usuarioController.editar(usuarioLogado)) {
				return "/harmobeer/confirmaEdicao";
			} else {
				usuarioLogado.setSenha(senhaAnterior);
				return "/usuario/erroEdicao";
			}
		} else {
			usuarioLogado.setSenha(senhaAnterior);
			return "/usuario/erroEdicao";
		}

	}

	/**
	 * Metodo para editar o perfil do usuario logado, mais especificamente o
	 * username, info e email
	 * 
	 * @return pagina de alteracao confirmada ou pagina de erro durante
	 *         alteracao.
	 */
	public String editarUsuario() {
		String usernameAnterior = usuarioLogado.getUsername();
		String infoAnterior = usuarioLogado.getInfo();
		String emailAnterior = usuarioLogado.getEmail();

		Usuario u = new Usuario(usuarioLogado.getId_user(), username, email, senha, info);
		if (usuarioController.editar(u)) {
			usuarioLogado.setUsername(username);
			usuarioLogado.setEmail(email);
			usuarioLogado.setInfo(info);
			return "/harmobeer/confirmaEdicao";
		} else {
			username = usernameAnterior;
			info = infoAnterior;
			email = emailAnterior;
			usuarioLogado.setEmail(emailAnterior);
			usuarioLogado.setInfo(infoAnterior);
			usuarioLogado.setUsername(usernameAnterior);
			return "/usuario/erroEdicao";
		}
	}

	/**
	 * Metodo para verificar se o usuario tem privilegio de administrador,
	 * permitindo que esses componentes aparecam em sala.
	 * 
	 * @return boolean
	 */
	public boolean verificarPrivilegio() {

		return usuarioController.verificarPrivilegio(usuarioLogado);

	}

	/**
	 * Metodo que seta uma array list para retornar as cervejas que contem
	 * determinada busca.
	 * 
	 * @return array list de cervejas
	 */
	public String buscarCerv() {

		ArrayList<SelectItem> listaCerv = new ArrayList<SelectItem>();
		ArrayList<Cerveja> cervejas = new ArrayList<Cerveja>();
		cervejas = (ArrayList<Cerveja>) cervejaController.buscarCerv(buscar);
		for (Cerveja c : cervejas) {
			SelectItem cerv = new SelectItem(c.getId_cerv(), c.getNm_cerv());
			listaCerv.add(cerv);

		}
		setListaCerv(listaCerv);
		if (listaCerv.isEmpty()) {
			return listarTodosCerv();
		}
		return "/cerveja/selecionarCerveja";
	}

	/**
	 * Metodo que seta uma Array List para listar todos as cervejas no banco de
	 * dados
	 * 
	 * @return Array List com todas as cervejas.
	 */
	public String listarTodosCerv() {

		ArrayList<SelectItem> listaCerv = new ArrayList<SelectItem>();
		ArrayList<Cerveja> cervejas = new ArrayList<Cerveja>();
		cervejas = (ArrayList<Cerveja>) cervejaController.listarTodos();
		for (Cerveja c : cervejas) {
			SelectItem cerv = new SelectItem(c.getId_cerv(), c.getNm_cerv());
			listaCerv.add(cerv);

		}
		setListaCerv(listaCerv);
		return "/cerveja/selecionarCerveja";
	}

	/**
	 * Metodo que seleciona uma cerveja no banco de dados, passando ela para um
	 * objeto criado no bean e setando seus parametros. Gera um Array com o
	 * ranking das harmonizacoes dessa cerveja.
	 */
	public void selecionarCerv() {
		setCervejaSelecionada(cervejaController.selecionarCerveja(idCervejaSelecionada));
		setNm_cerv(cervejaSelecionada.getNm_cerv());
		setEstilo_cerv(cervejaSelecionada.getNm_estilo());
		setTeor_alcoolico(cervejaSelecionada.getTeor_alcool());
		setRanking(harmonizacaoController.gerarRanking(cervejaSelecionada));
		ArrayList<Harmonizacao> rankingProv = new ArrayList<Harmonizacao>();
		for (Harmonizacao h : ranking) {
			Cerveja c = cervejaController.selecionarCerveja(h.getId_cerv());
			Prato p = pratoController.selecionarPrato(h.getId_prato());
			Harmonizacao hr = new Harmonizacao(h.getId_harmo(), h.getId_cerv(), c.getNm_cerv(), h.getId_prato(),
					p.getNm_prato(), h.getMedia());
			rankingProv.add(hr);
		}
		setRanking(rankingProv);
	}

	/**
	 * Metodo que seta um Array List <Prato> com os pratos que contem
	 * determinada String.
	 * 
	 * @return Pagina de selecao de pratos com a lista dos pratos.
	 */
	public String buscarPrato() {

		ArrayList<SelectItem> listaPrato = new ArrayList<SelectItem>();
		ArrayList<Prato> pratos = new ArrayList<Prato>();
		pratos = (ArrayList<Prato>) pratoController.buscarPrato(buscar);
		for (Prato p : pratos) {
			SelectItem prato = new SelectItem(p.getId_prato(), p.getNm_prato());
			listaPrato.add(prato);

		}
		setListaPrato(listaPrato);
		if (listaPrato.isEmpty()) {
			return listarTodosPrato();
		}
		return "/prato/selecionarPrato";
	}

	/**
	 * Metodo que guarda todos os pratos em uma ArrayList<Prato>
	 * 
	 * @return pagina de selecao de pratos com a ArrayList
	 */
	public String listarTodosPrato() {

		ArrayList<SelectItem> listaPrato = new ArrayList<SelectItem>();
		ArrayList<Prato> pratos = new ArrayList<Prato>();
		pratos = (ArrayList<Prato>) pratoController.listarTodos();
		for (Prato p : pratos) {
			SelectItem prato = new SelectItem(p.getId_prato(), p.getNm_prato());
			listaPrato.add(prato);

		}
		setListaPrato(listaPrato);
		return "/prato/selecionarPrato";
	}

	/**
	 * Metodo que guarda os usuarios que contem uma determinada String em seu
	 * username
	 * 
	 * @return pagina de selecao de usuario com a Array List
	 */
	public String buscarUser() {

		ArrayList<SelectItem> listaUsuario = new ArrayList<SelectItem>();
		ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
		usuarios = (ArrayList<Usuario>) usuarioController.buscarUser(buscar);
		for (Usuario u : usuarios) {
			SelectItem usuario = new SelectItem(u.getId_user(), u.getUsername());
			listaUsuario.add(usuario);

		}
		setListaUser(listaUsuario);
		if (listaUser.isEmpty()) {
			return listarTodosUser();
		}
		return "/usuario/selecionarUsuario";
	}

	/**
	 * Metodo que guarda todos os usuarios do banco de dados em um
	 * ArrayList<Usuario>
	 * 
	 * @return
	 */
	public String listarTodosUser() {

		ArrayList<SelectItem> listaUsuario = new ArrayList<SelectItem>();
		ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
		usuarios = (ArrayList<Usuario>) usuarioController.listarTodos();
		for (Usuario u : usuarios) {
			SelectItem usuario = new SelectItem(u.getId_user(), u.getUsername());
			listaUsuario.add(usuario);

		}
		setListaUser(listaUsuario);
		return "/usuario/selecionarUsuario";
	}

	/**
	 * Metodo que seleciona um prato em uma lista, setando os parametros
	 * necessarios para a tela e gerando um ranking para esse prato.
	 */
	public void selecionarPrato() {
		setPratoSelecionado(pratoController.selecionarPrato(idPratoSelecionado));
		setNm_prato(pratoSelecionado.getNm_prato());
		setRanking(harmonizacaoController.gerarRanking(pratoSelecionado));
		ArrayList<Harmonizacao> rankingProv = new ArrayList<Harmonizacao>();
		for (Harmonizacao h : ranking) {
			Cerveja c = cervejaController.selecionarCerveja(h.getId_cerv());
			Prato p = pratoController.selecionarPrato(h.getId_prato());
			Harmonizacao hr = new Harmonizacao(h.getId_harmo(), h.getId_cerv(), c.getNm_cerv(), h.getId_prato(),
					p.getNm_prato(), h.getMedia());
			rankingProv.add(hr);
		}
		setRanking(rankingProv);

	}

	/**
	 * Metodo que seta a lista de avaliacoes e os parametros de determinada
	 * harmonizacao
	 * 
	 * @return pagina de harmonizacao ou de erro.
	 */
	public String retornarHarmonizacao() {
		if (harmonizacaoController.incluirHarmonizacao(cervejaSelecionada, pratoSelecionado)) {
			int idHarmo = harmonizacaoController.selecionaridHarmonizacao(cervejaSelecionada, pratoSelecionado);
			setHarmonizacaoSelecionada(harmonizacaoController.selecionarHarmo(idHarmo));
			setMedia(harmonizacaoSelecionada.getMedia());
			ArrayList<Avaliacao> listaAvalProv = (ArrayList<Avaliacao>) avaliacaoController.listarAvalporHarmo(idHarmo);
			ArrayList<Avaliacao> listaAvalComp = new ArrayList<Avaliacao>();
			for (Avaliacao a : listaAvalProv) {

				Usuario u = usuarioController.selecionarUser(a.getId_user());
				Harmonizacao h = harmonizacaoController.selecionarHarmo(a.getId_harmo());
				Cerveja c = cervejaController.selecionarCerveja(h.getId_cerv());
				Prato p = pratoController.selecionarPrato(h.getId_prato());
				String nm_user = u.getUsername();
				String prato = p.getNm_prato();
				String cerveja = c.getNm_cerv();
				Avaliacao av = new Avaliacao(a.getId_aval(), a.getId_harmo(), cerveja, prato, a.getId_user(), nm_user,
						a.getNota(), a.getComentario());
				listaAvalComp.add(av);

			}
			setAvaliacoes(listaAvalComp);
			setComentario("");

			return "/harmonizacao/paginaDaHarmo";
		} else {
			return "/harmobeer/errorpage";
		}
	}

	/**
	 * Metodo privado que retorna as avaliacoes de determinada usuario,
	 * utilizado quando se seleciona um usuario para criar a lista de
	 * avaliacoes. .
	 */
	private void retornarAval() {

		ArrayList<Avaliacao> listaAvalProv = (ArrayList<Avaliacao>) avaliacaoController
				.listarAvalporUser(idUsuarioSelecionado);
		ArrayList<Avaliacao> listaAvalComp = new ArrayList<Avaliacao>();
		for (Avaliacao a : listaAvalProv) {
			Usuario u = usuarioController.selecionarUser(a.getId_user());
			Harmonizacao h = harmonizacaoController.selecionarHarmo(a.getId_harmo());
			Cerveja c = cervejaController.selecionarCerveja(h.getId_cerv());
			Prato p = pratoController.selecionarPrato(h.getId_prato());
			String nm_user = u.getUsername();
			String prato = p.getNm_prato();
			String cerveja = c.getNm_cerv();
			Avaliacao av = new Avaliacao(a.getId_aval(), a.getId_harmo(), cerveja, prato, a.getId_user(), nm_user,
					a.getNota(), a.getComentario());
			listaAvalComp.add(av);

		}
		setAvaliacoes(listaAvalComp);
	}

	/**
	 * Metodo que retorna as avaliacoes feitas pelo usuario logado
	 * 
	 * @return pagina com as avaliacoes do usuario logado
	 */
	public String retornarSuasAval() {
		{
			ArrayList<Avaliacao> listaAvalProv = (ArrayList<Avaliacao>) avaliacaoController
					.listarAvalporUser(usuarioLogado.getId_user());
			ArrayList<Avaliacao> listaAvalComp = new ArrayList<Avaliacao>();
			List<SelectItem> listaAval = new ArrayList<SelectItem>();
			String labelAval = "";
			for (Avaliacao a : listaAvalProv) {
				Usuario u = usuarioController.selecionarUser(a.getId_user());
				Harmonizacao h = harmonizacaoController.selecionarHarmo(a.getId_harmo());
				Cerveja c = cervejaController.selecionarCerveja(h.getId_cerv());
				Prato p = pratoController.selecionarPrato(h.getId_prato());
				String nm_user = u.getUsername();
				String prato = p.getNm_prato();
				String cerveja = c.getNm_cerv();
				Avaliacao av = new Avaliacao(a.getId_aval(), a.getId_harmo(), cerveja, prato, a.getId_user(), nm_user,
						a.getNota(), a.getComentario());
				listaAvalComp.add(av);

			}
			for (Avaliacao a : listaAvalComp) {
				labelAval = "CERVEJA: " + a.getNm_cerv() + " PRATO: " + a.getNm_prato() + " NOTA: "
						+ Integer.toString(a.getNota()) + " COMENTARIO: " + a.getComentario();
				SelectItem avalItem = new SelectItem(a.getId_aval(), labelAval);
				listaAval.add(avalItem);
			}
			setListaAval(listaAval);
			setAvaliacoes(listaAvalComp);
			setAvaliacaoSelecionada(null);
			return "/usuario/usuarioAvaliacoes";
		}

	}

	/**
	 * Metodo utilizado para incluir uma avaliacao no banco de dados pelo
	 * usuario logado
	 * 
	 * @return confirmacao da avaliacao ou erro ao avaliar.
	 */
	public String avaliarHarmonizacao() {
		Avaliacao aval = new Avaliacao(nota, comentario);
		if (avaliacaoController.incluirAvaliacao(aval, usuarioLogado, harmonizacaoSelecionada)) {
			harmonizacaoController.calcularMedia(harmonizacaoSelecionada);
			setHarmonizacaoSelecionada(harmonizacaoController.selecionarHarmo(
					harmonizacaoController.selecionaridHarmonizacao(cervejaSelecionada, pratoSelecionado)));
			setMedia(harmonizacaoSelecionada.getMedia());
			return "/harmonizacao/confirmaAvaliacao";
		} else {
			return "/harmobeer/erroAvaliarNova";
		}
	}

	/**
	 * Metodo para selecionar um usuario em uma lista, setando as avaliacoes
	 * feitas por ele para colocar em tela quando necessario.
	 */
	public void selecionarUsuario() {
		setUsuarioSelecionado(usuarioController.selecionarUser(idUsuarioSelecionado));
		retornarAval();
		setUsernameSelec(usuarioSelecionado.getUsername());
		setInfoSelec(usuarioSelecionado.getInfo());

	}

	/**
	 * Metodo para selecionar uma avaliacao, setando os parametros necessarios
	 * em tela para edicao.
	 */
	public void selecionarAvaliacao() {
		setAvaliacaoSelecionada(avaliacaoController.selecionarAval(idAvalSelec));
		Usuario u = usuarioController.selecionarUser(avaliacaoSelecionada.getId_user());
		Harmonizacao h = harmonizacaoController.selecionarHarmo(avaliacaoSelecionada.getId_harmo());
		Cerveja c = cervejaController.selecionarCerveja(h.getId_cerv());
		Prato p = pratoController.selecionarPrato(h.getId_prato());
		setUsernameAvalSelec(u.getUsername());
		setNmPratoAvalSelec(p.getNm_prato());
		setNmCervAvalSelec(c.getNm_cerv());
		avaliacaoSelecionada.setNm_cerv(c.getNm_cerv());
		avaliacaoSelecionada.setNm_prato(p.getNm_prato());
		avaliacaoSelecionada.setUsername(u.getUsername());

	}

	/**
	 * Metodo para editar uma avaliacao feita pelo usuario.
	 * 
	 * @return pagina de avaliacoes do usuario logado ou de erro
	 */
	public String editarAvaliacao() {
		avaliacaoSelecionada.setNota(nota);
		avaliacaoSelecionada.setComentario(comentario);
		if (avaliacaoController.editarAvaliacao(avaliacaoSelecionada)) {
			setAvaliacaoSelecionada(null);
			return retornarSuasAval();
		} else {

			return "/usuario/erroAvaliar";
		}

	}

	/**
	 * Metodo para o usuario deletar uma avaliacao feita por ele
	 * 
	 * @return pagina de avaliacoes ou de erro.
	 */
	public String deletarAvaliacao() {
		if (avaliacaoController.deletarAvaliacao(avaliacaoSelecionada)) {
			setAvaliacaoSelecionada(null);
			return retornarSuasAval();
		} else {

			return "/usuario/erroAvaliar";
		}

	}

	/**
	 * Metodo de administracao para buscar uma lista de cervejas.
	 * 
	 * @return pagina de administrador
	 */
	public String adminBuscarCerv() {

		ArrayList<Cerveja> cervejas = new ArrayList<Cerveja>();
		cervejas = (ArrayList<Cerveja>) cervejaController.buscarCerv(buscar);
		setCervejaAdmin(null);
		nm_cerv = "";
		estilo_cerv = "";
		teor_alcoolico = 0.0;
		setSelecionado(false);
		setAdminListaCerv(cervejas);
		if (cervejas.isEmpty()) {

			cervejas = (ArrayList<Cerveja>) cervejaController.listarTodos();
			setAdminListaCerv(cervejas);

		}
		return "/cerveja/administrarCerv";
	}

	/**
	 * Metodo para selecionar uma cerveja dentro do sistema de administracao.
	 * 
	 * @param idCerveja
	 * @return pagina de administracao ou de erro
	 */
	public String adminSelecionarCerv(int idCerveja) {
		try {

			setDeletado(false);
			setEditado(false);
			setCadastrado(false);
			setRenderizar(false);
			setSelecionado(true);
			setCervejaAdmin(cervejaController.selecionarCerveja(idCerveja));
			nm_cerv = cervejaAdmin.getNm_cerv();
			estilo_cerv = cervejaAdmin.getNm_estilo();
			teor_alcoolico = cervejaAdmin.getTeor_alcool();
			return "/cerveja/administrarCerv";
		} catch (Exception e) {
			e.printStackTrace();
			return "/cerveja/erroCerveja";
		}

	}

	/**
	 * Metodo para editar uma cerveja existente no banco de dados
	 * 
	 * @return pagina da administracao ou de erro
	 */
	public String adminEditarCerv() {
		cervejaAdmin.setNm_cerv(nm_cerv);
		cervejaAdmin.setNm_estilo(estilo_cerv);
		cervejaAdmin.setTeor_alcool(teor_alcoolico);

		if (cervejaController.editar(cervejaAdmin)) {
			setDeletado(false);
			setEditado(true);
			setCadastrado(false);
			setRenderizar(true);
			setSelecionado(false);
			setAdminListaCerv(null);
			nomeAdmin = cervejaAdmin.getNm_cerv();
			setCervejaAdmin(null);
			nm_cerv = "";
			estilo_cerv = "";
			teor_alcoolico = 0.0;
			return "/cerveja/administrarCerv";
		} else {
			return "/cerveja/erroCerveja";
		}

	}

	/**
	 * Metodo para cadastrar uma cerveja no banco de dados.
	 *
	 * @return pagina de administracao ou de erro.
	 */
	public String adminCadastrarCerv() {
		try {

			Cerveja ca = new Cerveja(nm_cerv, estilo_cerv, teor_alcoolico);

			if (cervejaController.incluir(ca)) {
				setDeletado(false);
				setEditado(false);
				setCadastrado(true);
				setRenderizar(true);
				setAdminListaCerv(null);
				setSelecionado(false);
				setCervejaAdmin(null);
				nomeAdmin = ca.getNm_cerv();
				nm_cerv = "";
				estilo_cerv = "";
				teor_alcoolico = 0.0;
				return "/cerveja/administrarCerv";
			} else {
				return "/cerveja/erroCerveja";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "/cerveja/erroCerveja";
		}
	}

	/**
	 * Metodo para deletar cervejas no banco de dados pelo administrador
	 * 
	 * @param idCerveja
	 * @return pagina de adminstracao ou de erro.
	 */
	public String adminDeletarCerv(int idCerveja) {
		setCervejaAdmin(cervejaController.selecionarCerveja(idCerveja));
		if (cervejaController.deletar(cervejaAdmin)) {
			setEditado(false);
			setDeletado(true);
			setCadastrado(false);
			setRenderizar(true);
			setAdminListaCerv(null);
			nm_cerv = cervejaAdmin.getNm_cerv();
			setCervejaAdmin(null);
			return "/cerveja/administrarCerv";
		} else {
			return "/cerveja/erroCerveja";
		}
	}

	/**
	 * Metodo para buscar uma lista de pratos na pagina de administracao
	 * 
	 * @return pagina da administracao
	 */
	public String adminBuscarPrato() {

		ArrayList<Prato> pratos = new ArrayList<Prato>();
		pratos = (ArrayList<Prato>) pratoController.buscarPrato(buscar);
		setPratoAdmin(null);
		nm_prato = "";
		setSelecionado(false);
		setAdminListaPrato(pratos);
		if (pratos.isEmpty()) {

			pratos = (ArrayList<Prato>) pratoController.listarTodos();
			setAdminListaPrato(pratos);

		}
		return "/prato/administrarPrato";
	}

	/**
	 * Metodo que seleciona um prato em uma lista de pratos
	 * 
	 * @param idPrato
	 * @return pagina de administracao ou de erro.
	 */
	public String adminSelecionarPrato(int idPrato) {
		try {

			setDeletado(false);
			setEditado(false);
			setCadastrado(false);
			setRenderizar(false);
			setSelecionado(true);
			setPratoAdmin(pratoController.selecionarPrato(idPrato));
			nm_prato = pratoAdmin.getNm_prato();
			return "/prato/administrarPrato";
		} catch (Exception e) {
			return "/prato/erroPrato";
		}

	}

	/**
	 * Metodo para edicao de um prato existente no banco de dados
	 * 
	 * @return pagina de administracao ou erro
	 */
	public String adminEditarPrato() {
		pratoAdmin.setNm_prato(nm_prato);

		if (pratoController.editar(pratoAdmin)) {
			setDeletado(false);
			setEditado(true);
			setCadastrado(false);
			setRenderizar(true);
			setSelecionado(false);
			setAdminListaPrato(null);
			nomeAdmin = pratoAdmin.getNm_prato();
			setPratoAdmin(null);
			nm_prato = "";

			return "/prato/administrarPrato";
		} else {
			return "/prato/erroPrato";
		}

	}

	/**
	 * Metodo para cadastro de prato pelo administrador
	 * 
	 * @return pagina de administracao ou de erro
	 */
	public String adminCadastrarPrato() {
		try {

			Prato pa = new Prato(nm_prato);

			if (pratoController.incluir(pa)) {
				setDeletado(false);
				setEditado(false);
				setCadastrado(true);
				setRenderizar(true);
				setAdminListaPrato(null);
				setSelecionado(false);
				setPratoAdmin(null);
				nomeAdmin = pa.getNm_prato();
				nm_prato = "";

				return "/prato/administrarPrato";
			} else {
				return "/prato/erroPrato";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "/prato/erroPrato";
		}
	}

	/**
	 * Metodo para delecao de pratos pelo administrador
	 * 
	 * @param idPrato
	 * @return pagina de administracao ou erro de transacao
	 */
	public String adminDeletarPrato(int idPrato) {
		setPratoAdmin(pratoController.selecionarPrato(idPrato));
		if (pratoController.deletar(pratoAdmin)) {
			setEditado(false);
			setDeletado(true);
			setCadastrado(false);
			setRenderizar(true);
			setAdminListaPrato(null);
			nomeAdmin = pratoAdmin.getNm_prato();
			setPratoAdmin(null);
			return "/prato/administrarPrato";
		} else {
			return "/prato/erroPrato";
		}
	}

	/**
	 * Ferramenta de busca específica para a pagina de administracao, setando um
	 * ArrayList semelhante a uma busca normal.
	 * 
	 * @return pagina de administracao
	 */
	public String adminBuscarUser() {

		ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
		usuarios = (ArrayList<Usuario>) usuarioController.buscarUser(buscar);
		setUsuarioSelecionado(null);
		setSelecionado(false);
		setRenderizar(false);
		setDeletado(false);
		setAdminListaUser(usuarios);
		if (usuarios.isEmpty()) {

			usuarios = (ArrayList<Usuario>) usuarioController.listarTodos();
			setAdminListaUser(usuarios);

		}
		return "/usuario/administrarUsuario";
	}

	/**
	 * Selecao de usuario na pagina de administrador.
	 * 
	 * @param idUser
	 * @return pagina de administracao ou erro
	 */
	public String adminSelecionarUser(int idUser) {
		try {

			setDeletado(false);
			setRenderizar(false);
			setSelecionado(true);
			setUsuarioSelecionado(usuarioController.selecionarUser(idUser));
			ArrayList<Avaliacao> listaAvalProv = new ArrayList<Avaliacao>(
					(avaliacaoController.listarAvalporUser(idUser)));
			ArrayList<Avaliacao> listaAvalComp = new ArrayList<Avaliacao>();
			for (Avaliacao a : listaAvalProv) {
				Usuario u = usuarioController.selecionarUser(a.getId_user());
				Harmonizacao h = harmonizacaoController.selecionarHarmo(a.getId_harmo());
				Cerveja c = cervejaController.selecionarCerveja(h.getId_cerv());
				Prato p = pratoController.selecionarPrato(h.getId_prato());
				String nm_user = u.getUsername();
				String prato = p.getNm_prato();
				String cerveja = c.getNm_cerv();
				Avaliacao av = new Avaliacao(a.getId_aval(), a.getId_harmo(), cerveja, prato, a.getId_user(), nm_user,
						a.getNota(), a.getComentario());
				listaAvalComp.add(av);
			}
			setAdminListaAval(listaAvalComp);
			setAdminListaUser(null);
			return "/usuario/administrarUsuario";
		} catch (Exception e) {
			return "/harmobeer/errorpage";
		}

	}

	/**
	 * Delecao de usuario pelo administrador, transformando-o em um usuario
	 * anonimo.
	 * 
	 * @param idUser
	 * @return pagina de administracao ou de erro
	 */
	public String adminDeletarUser(int idUser) {
		setUsuarioSelecionado(usuarioController.selecionarUser(idUser));
		if (usuarioController.deletar(usuarioSelecionado)) {
			setEditado(false);
			setDeletado(true);
			setCadastrado(false);
			setRenderizar(false);
			setAdminListaPrato(null);
			setSelecionado(false);
			nomeAdmin = usuarioSelecionado.getUsername();
			setUsuarioSelecionado(null);
			return "/usuario/administrarUsuario";
		} else {
			return "/harmobeer/errorpage";
		}
	}

	/**
	 * Deleção de avaliacao de outro usuario pelo administrador. Seta comandos
	 * de telas.
	 * 
	 * @param idAval
	 *            - aval a ser deletada
	 * @return pagina de administracao com o sucesso da delecao.
	 */
	public String adminDeletarAval(int idAval) {
		setAvaliacaoSelecionada(avaliacaoController.selecionarAval(idAval));
		if (avaliacaoController.deletarAvaliacao(avaliacaoSelecionada)) {
			setEditado(false);
			setDeletado(false);
			setRenderizar(true);
			setAdminListaAval(null);
			return "/usuario/administrarUsuario";
		} else {
			return "/harmobeer/errorpage";
		}
	}

	/**
	 * Seta varios parametros para um estado neutro e retorna para o menu
	 * principal.
	 * 
	 * @return pagina do menu principal
	 */
	public String retornarMenuPrincipal() {
		nm_prato = null;

		nm_cerv = null;
		estilo_cerv = null;
		teor_alcoolico = 0.0;

		comentario = null;
		nota = 0;
		ranking = null;

		usernameSelec = null;
		infoSelec = null;

		idCervejaSelecionada = 0;
		idPratoSelecionado = 0;
		idUsuarioSelecionado = 0;

		pratoSelecionado = null;
		cervejaSelecionada = null;
		harmonizacaoSelecionada = null;
		usuarioSelecionado = null;
		avaliacaoSelecionada = null;

		setDeletado(false);
		setEditado(false);
		setCadastrado(false);
		setRenderizar(false);
		setAdminListaCerv(null);
		setAdminListaPrato(null);

		buscar = null;

		return "/harmobeer/menuPrincipal";
	}

	/**
	 * 
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * 
	 * @param pUsername
	 */
	public void setUsername(String pUsername) {
		username = pUsername;
	}

	/**
	 * 
	 * @return the senha
	 */
	public String getSenha() {
		return senha;
	}

	/**
	 * 
	 * @param pSenha
	 */
	public void setSenha(String pSenha) {
		senha = pSenha;
	}

	/**
	 * @return the senhaconf
	 */
	public String getSenhaconf() {
		return senhaconf;
	}

	/**
	 * @param pSenhaconf
	 *            the senhaconf to set
	 */
	public void setSenhaconf(String pSenhaconf) {
		senhaconf = pSenhaconf;
	}

	/**
	 * @return the senhaant
	 */
	public String getSenhaant() {
		return senhaant;
	}

	/**
	 * @param senhaant
	 *            the senhaant to set
	 */
	public void setSenhaant(String senhaant) {
		this.senhaant = senhaant;
	}

	/**
	 * @return the info
	 */
	public String getInfo() {
		return info;
	}

	/**
	 * @param pInfo
	 *            the info to set
	 */
	public void setInfo(String pInfo) {
		info = pInfo;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param pEmail
	 *            the email to set
	 */
	public void setEmail(String pEmail) {
		email = pEmail;
	}

	/**
	 * @return the idUsuarioSelecionado
	 */
	public int getIdUsuarioSelecionado() {
		return idUsuarioSelecionado;
	}

	/**
	 * @param pIdUsuarioSelecionado
	 *            the idUsuarioSelecionado to set
	 */
	public void setIdUsuarioSelecionado(int pIdUsuarioSelecionado) {
		idUsuarioSelecionado = pIdUsuarioSelecionado;
	}

	/**
	 * @return the usernameSelec
	 */
	public String getUsernameSelec() {
		return usernameSelec;
	}

	/**
	 * @param pUsernameSelec
	 *            the usernameSelec to set
	 */
	public void setUsernameSelec(String pUsernameSelec) {
		usernameSelec = pUsernameSelec;
	}

	/**
	 * @return the infoSelec
	 */
	public String getInfoSelec() {
		return infoSelec;
	}

	/**
	 * @param pInfoSelec
	 *            the infoSelec to set
	 */
	public void setInfoSelec(String pInfoSelec) {
		infoSelec = pInfoSelec;
	}

	/**
	 * @return the buscar
	 */
	public String getBuscar() {
		return buscar;
	}

	/**
	 * @param buscar
	 *            the buscar to set
	 */
	public void setBuscar(String buscar) {
		this.buscar = buscar;
	}

	/**
	 * @return the listaCerv
	 */
	public List<SelectItem> getListaCerv() {
		return listaCerv;
	}

	/**
	 * @param listaCerv
	 *            the listaCerv to set
	 */
	public void setListaCerv(List<SelectItem> listaCerv) {
		this.listaCerv = listaCerv;
	}

	/**
	 * @return the listaPrato
	 */
	public List<SelectItem> getListaPrato() {
		return listaPrato;
	}

	/**
	 * @param listaPrato
	 *            the listaPrato to set
	 */
	public void setListaPrato(List<SelectItem> listaPrato) {
		this.listaPrato = listaPrato;
	}

	/**
	 * @return the listaUser
	 */
	public List<SelectItem> getListaUser() {
		return listaUser;
	}

	/**
	 * @param listaUser
	 *            the listaUser to set
	 */
	public void setListaUser(List<SelectItem> listaUser) {
		this.listaUser = listaUser;
	}

	/**
	 * @return the listaAval
	 */
	public List<SelectItem> getListaAval() {
		return listaAval;
	}

	/**
	 * @param listaAval
	 *            the listaAval to set
	 */
	public void setListaAval(List<SelectItem> listaAval) {
		this.listaAval = listaAval;
	}

	/**
	 * @return the adminListaCerv
	 */
	public List<Cerveja> getAdminListaCerv() {
		return adminListaCerv;
	}

	/**
	 * @param adminListaCerv
	 *            the adminListaCerv to set
	 */
	public void setAdminListaCerv(List<Cerveja> adminListaCerv) {
		this.adminListaCerv = adminListaCerv;
	}

	/**
	 * @return the adminListaPrato
	 */
	public List<Prato> getAdminListaPrato() {
		return adminListaPrato;
	}

	/**
	 * @param adminListaPrato
	 *            the adminListaPrato to set
	 */
	public void setAdminListaPrato(List<Prato> adminListaPrato) {
		this.adminListaPrato = adminListaPrato;
	}

	/**
	 * @return the adminListaUser
	 */
	public List<Usuario> getAdminListaUser() {
		return adminListaUser;
	}

	/**
	 * @param adminListaUser
	 *            the adminListaUser to set
	 */
	public void setAdminListaUser(List<Usuario> adminListaUser) {
		this.adminListaUser = adminListaUser;
	}

	/**
	 * @return the adminListaAval
	 */
	public List<Avaliacao> getAdminListaAval() {
		return adminListaAval;
	}

	/**
	 * @param adminListaAval
	 *            the adminListaAval to set
	 */
	public void setAdminListaAval(List<Avaliacao> adminListaAval) {
		this.adminListaAval = adminListaAval;
	}

	/**
	 * @return the editado
	 */
	public boolean isEditado() {
		return editado;
	}

	/**
	 * @param editado
	 *            the editado to set
	 */
	public void setEditado(boolean editado) {
		this.editado = editado;
	}

	/**
	 * @return the deletado
	 */
	public boolean isDeletado() {
		return deletado;
	}

	/**
	 * @param deletado
	 *            the deletado to set
	 */
	public void setDeletado(boolean deletado) {
		this.deletado = deletado;
	}

	/**
	 * @return the cadastrado
	 */
	public boolean isCadastrado() {
		return cadastrado;
	}

	/**
	 * @param cadastrado
	 *            the cadastrado to set
	 */
	public void setCadastrado(boolean cadastrado) {
		this.cadastrado = cadastrado;
	}

	/**
	 * @return the renderizar
	 */
	public boolean isRenderizar() {
		return renderizar;
	}

	/**
	 * @param renderizar
	 *            the renderizar to set
	 */
	public void setRenderizar(boolean renderizar) {
		this.renderizar = renderizar;
	}

	/**
	 * @return the selecionado
	 */
	public boolean isSelecionado() {
		return selecionado;
	}

	/**
	 * @param selecionado
	 *            the selecionado to set
	 */
	public void setSelecionado(boolean selecionado) {
		this.selecionado = selecionado;
	}

	/**
	 * @return the nomeAdmin
	 */
	public String getNomeAdmin() {
		return nomeAdmin;
	}

	/**
	 * @param nomeAdmin
	 *            the nomeAdmin to set
	 */
	public void setNomeAdmin(String nomeAdmin) {
		this.nomeAdmin = nomeAdmin;
	}

	/**
	 * @return the nm_cerv
	 */
	public String getNm_cerv() {
		return nm_cerv;
	}

	/**
	 * @param nm_cerv
	 *            the nm_cerv to set
	 */
	public void setNm_cerv(String nm_cerv) {
		this.nm_cerv = nm_cerv;
	}

	/**
	 * @return the estilo_cerv
	 */
	public String getEstilo_cerv() {
		return estilo_cerv;
	}

	/**
	 * @param estilo_cerv
	 *            the estilo_cerv to set
	 */
	public void setEstilo_cerv(String estilo_cerv) {
		this.estilo_cerv = estilo_cerv;
	}

	/**
	 * @return the teor_alcoolico
	 */
	public double getTeor_alcoolico() {
		return teor_alcoolico;
	}

	/**
	 * @param teor_alcoolico
	 *            the teor_alcoolico to set
	 */
	public void setTeor_alcoolico(double teor_alcoolico) {
		this.teor_alcoolico = teor_alcoolico;
	}

	/**
	 * @return the nm_prato
	 */
	public String getNm_prato() {
		return nm_prato;
	}

	/**
	 * @param nm_prato
	 *            the nm_prato to set
	 */
	public void setNm_prato(String nm_prato) {
		this.nm_prato = nm_prato;
	}

	/**
	 * @return the usuarioLogado
	 */
	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}

	/**
	 * @param pUsuarioLogado
	 *            the usuarioLogado to set
	 */
	public void setUsuarioLogado(Usuario pUsuarioLogado) {
		usuarioLogado = pUsuarioLogado;
	}

	/**
	 * @return the usuarioCadastrado
	 */
	public Usuario getUsuarioCadastrado() {
		return usuarioCadastrado;
	}

	/**
	 * @param pUsuarioCadastrado
	 *            the usuarioCadastrado to set
	 */
	public void setUsuarioCadastrado(Usuario pUsuarioCadastrado) {
		usuarioCadastrado = pUsuarioCadastrado;
	}

	/**
	 * @return the usuarioSelecionado
	 */
	public Usuario getUsuarioSelecionado() {
		return usuarioSelecionado;
	}

	/**
	 * @param pUsuarioSelecionado
	 *            the usuarioSelecionado to set
	 */
	public void setUsuarioSelecionado(Usuario pUsuarioSelecionado) {
		usuarioSelecionado = pUsuarioSelecionado;
	}

	/**
	 * @return the cervejaSelecionada
	 */
	public Cerveja getCervejaSelecionada() {
		return cervejaSelecionada;
	}

	/**
	 * @param cervejaSelecionada
	 *            the cervejaSelecionada to set
	 */
	public void setCervejaSelecionada(Cerveja cervejaSelecionada) {
		this.cervejaSelecionada = cervejaSelecionada;
	}

	/**
	 * @return the idCervejaSelecionada
	 */
	public int getIdCervejaSelecionada() {
		return idCervejaSelecionada;
	}

	/**
	 * @param idCervejaSelecionada
	 *            the idCervejaSelecionada to set
	 */
	public void setIdCervejaSelecionada(int idCervejaSelecionada) {
		this.idCervejaSelecionada = idCervejaSelecionada;
	}

	/**
	 * @return the cervejaAdmin
	 */
	public Cerveja getCervejaAdmin() {
		return cervejaAdmin;
	}

	/**
	 * @param cervejaAdmin
	 *            the cervejaAdmin to set
	 */
	public void setCervejaAdmin(Cerveja cervejaAdmin) {
		this.cervejaAdmin = cervejaAdmin;
	}

	/**
	 * @return the pratoSelecionado
	 */
	public Prato getPratoSelecionado() {
		return pratoSelecionado;
	}

	/**
	 * @param pratoSelecionado
	 *            the pratoSelecionado to set
	 */
	public void setPratoSelecionado(Prato pratoSelecionado) {
		this.pratoSelecionado = pratoSelecionado;
	}

	/**
	 * @return the idPratoSelecionado
	 */
	public int getIdPratoSelecionado() {
		return idPratoSelecionado;
	}

	/**
	 * @param idPratoSelecionado
	 *            the idPratoSelecionado to set
	 */
	public void setIdPratoSelecionado(int idPratoSelecionado) {
		this.idPratoSelecionado = idPratoSelecionado;
	}

	/**
	 * @return the pratoAdmin
	 */
	public Prato getPratoAdmin() {
		return pratoAdmin;
	}

	/**
	 * @param pratoAdmin
	 *            the pratoAdmin to set
	 */
	public void setPratoAdmin(Prato pratoAdmin) {
		this.pratoAdmin = pratoAdmin;
	}

	/**
	 * @return the media
	 */
	public double getMedia() {
		return media;
	}

	/**
	 * @param media
	 *            the media to set
	 */
	public void setMedia(double media) {
		this.media = media;
	}

	/**
	 * @return the ranking
	 */
	public ArrayList<Harmonizacao> getRanking() {
		return ranking;
	}

	/**
	 * @param pRanking
	 *            the ranking to set
	 */
	public void setRanking(ArrayList<Harmonizacao> pRanking) {
		ranking = pRanking;
	}

	/**
	 * @return the harmonizacaoSelecionada
	 */
	public Harmonizacao getHarmonizacaoSelecionada() {
		return harmonizacaoSelecionada;
	}

	/**
	 * @param harmonizacaoSelecionada
	 *            the harmonizacaoSelecionada to set
	 */
	public void setHarmonizacaoSelecionada(Harmonizacao harmonizacaoSelecionada) {
		this.harmonizacaoSelecionada = harmonizacaoSelecionada;
	}

	/**
	 * @return the comentario
	 */
	public String getComentario() {
		return comentario;
	}

	/**
	 * @param comentario
	 *            the comentario to set
	 */
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	/**
	 * @return the nota
	 */
	public int getNota() {
		return nota;
	}

	/**
	 * @param nota
	 *            the nota to set
	 */
	public void setNota(int nota) {
		this.nota = nota;
	}

	/**
	 * @return the idAvalSelec
	 */
	public int getIdAvalSelec() {
		return idAvalSelec;
	}

	/**
	 * @param idAvalSelec
	 *            the idAvalSelec to set
	 */
	public void setIdAvalSelec(int idAvalSelec) {
		this.idAvalSelec = idAvalSelec;
	}

	/**
	 * @return the usernameAvalSelec
	 */
	public String getUsernameAvalSelec() {
		return usernameAvalSelec;
	}

	/**
	 * @param usernameAvalSelec
	 *            the usernameAvalSelec to set
	 */
	public void setUsernameAvalSelec(String usernameAvalSelec) {
		this.usernameAvalSelec = usernameAvalSelec;
	}

	/**
	 * @return the nmCervAvalSelec
	 */
	public String getNmCervAvalSelec() {
		return nmCervAvalSelec;
	}

	/**
	 * @param nmCervAvalSelec
	 *            the nmCervAvalSelec to set
	 */
	public void setNmCervAvalSelec(String nmCervAvalSelec) {
		this.nmCervAvalSelec = nmCervAvalSelec;
	}

	/**
	 * @return the nmPratoAvalSelec
	 */
	public String getNmPratoAvalSelec() {
		return nmPratoAvalSelec;
	}

	/**
	 * @param nmPratoAvalSelec
	 *            the nmPratoAvalSelec to set
	 */
	public void setNmPratoAvalSelec(String nmPratoAvalSelec) {
		this.nmPratoAvalSelec = nmPratoAvalSelec;
	}

	/**
	 * @return the notaAvalSelec
	 */
	public int getNotaAvalSelec() {
		return notaAvalSelec;
	}

	/**
	 * @param notaAvalSelec
	 *            the notaAvalSelec to set
	 */
	public void setNotaAvalSelec(int notaAvalSelec) {
		this.notaAvalSelec = notaAvalSelec;
	}

	/**
	 * @return the comentarioAvalSelec
	 */
	public String getComentarioAvalSelec() {
		return comentarioAvalSelec;
	}

	/**
	 * @param comentarioAvalSelec
	 *            the comentarioAvalSelec to set
	 */
	public void setComentarioAvalSelec(String comentarioAvalSelec) {
		this.comentarioAvalSelec = comentarioAvalSelec;
	}

	/**
	 * @return the avaliacoes
	 */
	public ArrayList<Avaliacao> getAvaliacoes() {
		return avaliacoes;
	}

	/**
	 * @param avaliacoes
	 *            the avaliacoes to set
	 */
	public void setAvaliacoes(ArrayList<Avaliacao> avaliacoes) {
		this.avaliacoes = avaliacoes;
	}

	/**
	 * @return the avaliacaoSelecionada
	 */
	public Avaliacao getAvaliacaoSelecionada() {
		return avaliacaoSelecionada;
	}

	/**
	 * @param avaliacaoSelecionada
	 *            the avaliacaoSelecionada to set
	 */
	public void setAvaliacaoSelecionada(Avaliacao avaliacaoSelecionada) {
		this.avaliacaoSelecionada = avaliacaoSelecionada;
	}

	/**
	 * Metodo que zera os tipos envolvidos com o usuario para fins de limpeza de
	 * tela.
	 */
	private void zerarUsuario() {
		setUsername("");
		setSenha("");
		setEmail("");
		setInfo("");
	}

}
