package com.harmobeer.mvc.view;

import java.util.ArrayList;

import com.harmobeer.mvc.controller.CervejaController;
import com.harmobeer.util.Leitor;
import com.harmobeer.vo.Cerveja;

/**
 * Classe responsável pelo Visualizador dos objetos Cerveja.
 * 
 * @author José Carlos Soares da Cruz Junior / Luan Henrique Cunha Alves 
 */
public class CervejaView {

	private CervejaController cervejaController;

	public CervejaView() {
		cervejaController = new CervejaController();
	}

	/**
	 * Método para carregar o menu principal
	 */
	public void menu() {

		int op = 0;

		do {
			System.out.println("-= Menu =-");
			System.out.println("1 - Incluir nova cerveja");
			System.out.println("2 - Editar cerveja existente");
			System.out.println("3 - Deletar cerveja");
			System.out.println("4 - Listar cervejas");
			System.out.println("0 - Sair");
			System.out.println("-=-=-=-=-=-");

			op = Leitor.readInt("Digite a opção desejada: ");
			switch (op) {
			case 1:
				incluir();
				break;
			case 2:
				editar();
				break;
			case 3:
				deletar();
				break;
			case 4:
				listarTodos();
				break;
			case 0:
				System.out.println("Obrigado por usar nossas funções, volte sempre!");
				break;
			default:
				System.out.println("Opção inválida! Tente novamente!");
			}
		} while (op != 0);
	}

	/**
	 * Método responsável por realizar a inclusão de cervejas
	 */
	public void incluir() {

		System.out.println("Vamos incluir uma cerveja no banco de dados!");
		String nm_cerv = Leitor.readString("Digite o nome da cerveja: ");
		while (nm_cerv.length() > 60) {
			System.out.println("Oops! Nome de cerveja muito longo!");
			nm_cerv = Leitor.readString("Faça abreviações para que seja possível adicionar: ");

		}
		String nm_estilo = Leitor.readString("Digite o estilo da cerveja: ");
		while (nm_estilo.length() > 60) {
			System.out.println("Oops! Nome do estilo da cerveja muito longo!");
			nm_estilo = Leitor.readString("Faça abreviações para que seja possível adicionar: ");
		}
		double teor_alcool = Leitor.readDouble("Declare seu teor alcóolico: ");

		if (teor_alcool > 70 || teor_alcool < 0) {
			System.out.println(
					"Ainda não existe uma cerveja com esse nivel alcoolico... Provavelmente você digitou errado!");
			teor_alcool = Leitor.readDouble("Corrija a seguir: ");
		}

		Cerveja c = new Cerveja(nm_cerv, nm_estilo, teor_alcool);
		if (cervejaController.incluir(c)) {
			System.out.println("Cerveja incluída com sucesso!");
		} else {
			System.out.println("Não foi possível incluir a cerveja... Tente novamente mais tarde.");
		}

	}

	/**
	 * Método responsável por realizar a edição de cervejas cadastradas
	 */
	public void editar() {
		String nm_cerv = "";
		String nm_estilo = "";
		double teor = 0.0;

		System.out.println("Selecione a cerveja que deseja editar");
		listarTodos();
		int escolhaID = Leitor.readInt("Qual o identificador da cerveja?");
		Cerveja c = cervejaController.selecionarCerveja(escolhaID);
		int escolhaEditar = Leitor.readInt("Deseja editar o nome?  1 - Sim 2 - Nao");
		if (escolhaEditar == 1) {
			nm_cerv = Leitor.readString("Digite o nome: ");
			while (nm_cerv.length() > 60) {
				System.out.println("Oops! Nome de cerveja muito longo!");
				nm_cerv = Leitor.readString("Faça abreviações para que seja possível adicionar: ");
			}
			c.setNm_cerv(nm_cerv);
		} else if (escolhaEditar == 2) {
			System.out.println("Você optou por não alterar o nome");
		} else {
			System.out.println("Você digitou algo fora do padrão. Vamos supor que quis dizer NÃO");
		}
		escolhaEditar = Leitor.readInt("Deseja editar o estilo?  1 - Sim 2 - Nao");
		if (escolhaEditar == 1) {
			nm_estilo = Leitor.readString("Digite o nome do estilo: ");
			while (nm_estilo.length() > 60) {
				System.out.println("Oops! Nome de estilo muito longo!");
				nm_estilo = Leitor.readString("Faça abreviações para que seja possível adicionar: ");
			}
			c.setNm_estilo(nm_estilo);
		} else if (escolhaEditar == 2) {
			System.out.println("Você optou por não alterar o estilo");
		} else {
			System.out.println("Você digitou algo fora do padrão. Vamos supor que quis dizer NÃO");
		}
		escolhaEditar = Leitor.readInt("Deseja alterar o teor alcóolico?  1 - Sim 2 - Nao");
		if (escolhaEditar == 1) {
			teor = Leitor.readDouble("Digite o novo valor: ");
			while (teor > 70 || teor < 0) {
				System.out.println(
						"Ainda não existe uma cerveja com esse nivel alcoolico... Provavelmente você digitou errado!");
				teor = Leitor.readDouble("Corrija a seguir: ");
			}
			c.setTeor_alcool(teor);
		} else if (escolhaEditar == 2) {
			System.out.println("Você optou por não alterar o teor alcoolico");
		} else {
			System.out.println("Você digitou algo fora do padrão. Vamos supor que quis dizer NÃO");
		}
		if (cervejaController.editar(c)) {

			System.out.println("Sua Cerveja foi atualizada para: ");
			System.out.println("Identificador: " + c.getId_cerv());
			System.out.println("Nome da Cerveja: " + c.getNm_cerv());
			System.out.println("Estilo: " + c.getNm_estilo());
			System.out.println("Teor Alcóolico: " + c.getTeor_alcool());
			System.out.println("+-=-+-=-+");

		} else {
			System.out.println("Não foi possível alterar os dados da cerveja. Tente novamente mais tarde.");
		}
	}

	/**
	 * Método responsável por realizar a exclusão de cervejas cadastradas
	 */
	public void deletar() {

		System.out.println("Selecione a cerveja que deseja deletar do banco de dados");
		listarTodos();
		int escolhaID = Leitor.readInt("Qual o identificador da cerveja? ");
		Cerveja c = cervejaController.selecionarCerveja(escolhaID);
		if (cervejaController.deletar(c)) {
			System.out.println("Cerveja completamente deletada!");
		} else {
			System.out.println("Não foi possível deletar a cerveja."
					+ "Tente novamente mais tarde");
		}

	}

	/**
	 * Método responsável pela listagem de todas as cervejas cadastradas
	 */
	public void listarTodos() {
		ArrayList<Cerveja> cerv = new ArrayList<Cerveja>();
		cerv = (ArrayList<Cerveja>) cervejaController.listarTodos();

		for (Cerveja c : cerv) {
			System.out.println(" ");
			System.out.println("x-=-x-=-x");
			System.out.println("ID: " + c.getId_cerv() + "   Nome: " + c.getNm_cerv());
			System.out.println("Estilo: " + c.getNm_estilo() + "      " + "Teor Alcoolico: " + c.getTeor_alcool() + " %");
			System.out.println("x-=-x-=-x");
			System.out.println(" ");
		}

	}
}
