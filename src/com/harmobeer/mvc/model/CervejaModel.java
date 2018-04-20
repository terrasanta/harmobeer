/**
 * 
 */
package com.harmobeer.mvc.model;

import java.util.List;

import com.harmobeer.db.dao.CervejaDAO;
import com.harmobeer.interfaces.ICervejaDAO;
import com.harmobeer.vo.Cerveja;

/**
 * Classe responsavel pelo Modelo dos objetos Cerveja
 * 
 * @author Jose Carlos Soares da Cruz Junior / Luan Henrique Cunha Alves
 * 
 */
public class CervejaModel implements ICervejaDAO {

	private CervejaDAO cervejaDAO;

	/**
	 * Construtor da classe Cerveja Model, utilizando a criacao de um novo
	 * objeto da classe CervejaDAO.
	 */
	public CervejaModel() {
		cervejaDAO = new CervejaDAO();
	}

	/**
	 * Metodo que inclui uma cerveja no banco, utilizando o objeto cervejaDAO
	 * para acessar o banco
	 * 
	 * @param Cerveja
	 *            a ser incluida
	 * @return boolean true para transacao bem sucedida e false para transacao
	 *         interrompida.
	 * 
	 */
	@Override
	public boolean incluir(Cerveja cerveja) {
		return cervejaDAO.incluir(cerveja);
	}

	/**
	 * Metodo que edita uma cerveja no banco, utilizando o objeto cervejaDAO
	 * para acessar o banco
	 * 
	 * @param Cerveja
	 *            a ser editada
	 * @return boolean true para transacao bem sucedida e false para transacao
	 *         interrompida.
	 */

	@Override
	public boolean editar(Cerveja cerveja) {
		return cervejaDAO.editar(cerveja);
	}

	/**
	 * Metodo que exclui uma cerveja no banco, utilizando o objeto cervejaDAO
	 * para acessar o banco
	 * 
	 * @param Cerveja
	 *            a ser excluída
	 * @return boolean true para transacao bem sucedida e false para transacao
	 *         interrompida.
	 */

	@Override
	public boolean deletar(Cerveja cerveja) {
		return cervejaDAO.deletar(cerveja);
	}

	/**
	 * Metodo responsavel por realizar a listagem de todas as Cervejas
	 * cadastradas no banco.
	 * 
	 * @return ArrayList com os objetos da Classe Cerveja gerados com os dados
	 *         recebidos do banco de dados.
	 * 
	 */

	@Override
	public List<Cerveja> listarTodos() {
		return cervejaDAO.listarTodos();
	}

	/**
	 * Metodo responsavel por buscar e trazer o objeto de uma Cerveja, com os
	 * dados do banco de dados.
	 * 
	 * @param id
	 *            ID da cerveja cadastrada no banco
	 * @return Cerveja selecionada
	 */

	public Cerveja selecionarCerv(int id) {
		return cervejaDAO.selecionarCerv(id);
	}

	/**
	 * Metodo responsavel por buscar cerveja que contem uma determinada string
	 * em seu nome.
	 * 
	 * @param String
	 *            busca
	 * @return List<Cerveja> com cervejas que contenham busca
	 */
	@Override
	public List<Cerveja> buscarCerv(String busca) {
		return cervejaDAO.buscarCerv(busca);
	}

}
