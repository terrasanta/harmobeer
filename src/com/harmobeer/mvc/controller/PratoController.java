/**
 * 
 */
package com.harmobeer.mvc.controller;

import java.util.List;

import com.harmobeer.interfaces.IPratoDAO;
import com.harmobeer.mvc.model.PratoModel;
import com.harmobeer.vo.Prato;

/**
 * 
 * Classe responsavel pelo controlador dos objetos Prato
 * 
 * @author Jose Carlos Soares da Cruz Junior / Luan Henrique Cunha Alves
 *
 */
public class PratoController implements IPratoDAO {
	private PratoModel pratoModel;

	/**
	 * Construtor da classe Prato Controller, utilizando a criacao de um novo
	 * objeto da classe PratoModel.
	 */
	public PratoController() {
		pratoModel = new PratoModel();
	}

	/**
	 * Metodo que passa um objeto prato, caso ela esteja de acordo com os
	 * limites do banco de dados, para ser incluída no banco de dados pela
	 * classe PratoModel.
	 * 
	 * @param Prato
	 *            a ser incluido
	 * @return boolean true para transacao bem sucedida e false para transacao
	 *         interrompida, quando o objeto prato está fora do padrão do banco.
	 * 
	 */
	@Override
	public boolean incluir(Prato prato) {
		if (validarPrato(prato)) {
			return pratoModel.incluir(prato);
		}
		return false;
	}

	/**
	 * Metodo que passa um objeto prato, caso ela esteja de acordo com os
	 * limites do banco de dados, para ser atualizado no banco de dados pela
	 * classe PratoModel.
	 * 
	 * @param Prato
	 *            a ser editado
	 * @return boolean true para transacao bem sucedida e false para transacao
	 *         interrompida, quando o objeto prato está fora do padrão do banco.
	 * 
	 */
	@Override
	public boolean editar(Prato prato) {
		if (validarPrato(prato)) {
			return pratoModel.editar(prato);
		}
		return false;
	}

	/**
	 * Metodo que passa um objeto prato para ser deletado no banco de dados pela
	 * classe PratoModel.
	 * 
	 * @param prato
	 *            a ser deletada
	 * @return boolean true para transacao bem sucedida e false para transacao
	 *         interrompida.
	 * 
	 */
	@Override
	public boolean deletar(Prato prato) {
		return pratoModel.deletar(prato);
	}

	/**
	 * Metodo responsavel acionar a listagem de pratos no banco de dados,
	 * atraves do objeto da classe PratoModel.
	 * 
	 * @return ArrayList com os objetos da Classe Prato gerados com os dados
	 *         recebidos do banco de dados.
	 * 
	 */
	@Override
	public List<Prato> listarTodos() {
		return pratoModel.listarTodos();
	}

	/**
	 * Metodo responsavel por buscar e retornar o objeto de uma Prato, com os
	 * dados do banco de dados atraves do método contido na classe PratoModel.
	 * 
	 * @param id
	 *            ID da prato cadastrada no banco
	 * @return Prato selecionada
	 */
	public Prato selecionarPrato(int id) {
		return pratoModel.selecionarPrato(id);
	}

	/**
	 * Metodo responsavel por validar o objeto prato, garantindo que seus dados
	 * estejam dentro do permitido pelo banco de dados.
	 * 
	 * @param prato
	 * @return boolean true para objeto dentro dos limites e false caso
	 *         contrário
	 */
	private boolean validarPrato(Prato prato) {

		String nm_prato = prato.getNm_prato();

		if (nm_prato.length() > 30) {
			System.out.println("Nome de prato muito longo.");
			return false;
		}

		return true;

	}

	/**
	 * Metodo responsavel por buscar e retornar uma lista de pratos que contem
	 * uma string pre-determinada.
	 *
	 * @param String
	 *            busca
	 * @return List<Prato> com pratos que contem busca
	 */
	@Override
	public List<Prato> buscarPrato(String busca) {
		return pratoModel.buscarPrato(busca);
	}
}
