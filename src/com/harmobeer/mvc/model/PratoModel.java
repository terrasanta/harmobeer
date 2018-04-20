/**
 * 
 */
package com.harmobeer.mvc.model;

import java.util.List;

import com.harmobeer.db.dao.PratoDAO;
import com.harmobeer.interfaces.IPratoDAO;
import com.harmobeer.vo.Prato;

/**
 * Classe responsavel pelo Modelo dos objetos Prato
 * 
 * @author Jose Carlos Soares da Cruz Junior / Luan Henrique Cunha Alves
 * 
 */
public class PratoModel implements IPratoDAO {

	private PratoDAO pratoDAO;

	/**
	 * Construtor da classe Prato Model, utilizando a criacaSo de um novo objeto
	 * da classe PratoDAO.
	 */
	public PratoModel() {
		pratoDAO = new PratoDAO();
	}

	/**
	 * Metodo que inclui um prato no banco, utilizando o objeto pratoDAO para
	 * acessar o banco
	 * 
	 * @param Prato
	 *            a ser incluido
	 * @return boolean true para transacao bem sucedida e false para transacao
	 *         interrompida.
	 * 
	 */
	@Override
	public boolean incluir(Prato prato) {
		return pratoDAO.incluir(prato);
	}

	/**
	 * Metodo que edita uma prato no banco, utilizando o objeto pratoDAO para
	 * acessar o banco
	 * 
	 * @param Prato
	 *            a ser editada
	 * @return boolean true para transação bem sucedida e false para transação
	 *         interrompida.
	 */

	@Override
	public boolean editar(Prato prato) {
		return pratoDAO.editar(prato);
	}

	/**
	 * Metodo que exclui uma prato no banco, utilizando o objeto pratoDAO para
	 * acessar o banco
	 * 
	 * @param Prato
	 *            a ser excluida
	 * @return boolean true para transacao bem sucedida e false para transacao
	 *         interrompida.
	 */

	@Override
	public boolean deletar(Prato prato) {
		return pratoDAO.deletar(prato);
	}

	/**
	 * Metodo responsavel por realizar a listagem de todas os Pratos cadastradas
	 * no banco.
	 * 
	 * @return ArrayList com os objetos da Classe Prato gerados com os dados
	 *         recebidos do banco de dados.
	 * 
	 */

	@Override
	public List<Prato> listarTodos() {
		return pratoDAO.listarTodos();
	}

	/**
	 * Metodo responsavel por buscar e trazer o objeto de um Prato, com os dados
	 * do banco de dados.
	 * 
	 * @param id
	 *            ID do prato cadastrado no banco
	 * @return Prato selecionado
	 */

	public Prato selecionarPrato(int id) {
		return pratoDAO.selecionarPrato(id);
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
		return pratoDAO.buscarPrato(busca);
	}

}
