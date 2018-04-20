/**
 * 
 */
package com.harmobeer.mvc.controller;

import java.util.List;

import com.harmobeer.interfaces.ICervejaDAO;
import com.harmobeer.mvc.model.CervejaModel;
import com.harmobeer.vo.Cerveja;

/**
 * 
 * Classe responsavel pelo controlador dos objetos Cerveja
 * 
 * @author Jose Carlos Soares da Cruz Junior / Luan Henrique Cunha Alves
 *
 */
public class CervejaController implements ICervejaDAO {
	private CervejaModel cervejaModel;

	/**
	 * Construtor da classe Cerveja Controller, utilizando a criação de um novo
	 * objeto da classe CervejaModel.
	 */
	public CervejaController() {
		cervejaModel = new CervejaModel();
	}

	/**
	 * Metodo que passa um objeto cerveja, caso ela esteja de acordo com os
	 * limites do banco de dados, para ser incluida no banco de dados pela
	 * classe CervejaModel.
	 * 
	 * @param Cerveja
	 *            a ser incluida
	 * @return boolean true para transacao bem sucedida e false para transacao
	 *         interrompida, quando o objeto cerveja está fora do padrão do
	 *         banco.
	 * 
	 */
	@Override
	public boolean incluir(Cerveja cerveja) {
		if (validarCerveja(cerveja)) {
			return cervejaModel.incluir(cerveja);
		}
		return false;
	}

	/**
	 * Metodo que passa um objeto cerveja, caso ela esteja de acordo com os
	 * limites do banco de dados, para ser atualizado no banco de dados pela
	 * classe CervejaModel.
	 * 
	 * @param Cerveja
	 *            a ser editada
	 * @return boolean true para transacao bem sucedida e false para transacao
	 *         interrompida, quando o objeto cerveja está fora do padrão do
	 *         banco.
	 * 
	 */
	@Override
	public boolean editar(Cerveja cerveja) {
		if (validarCerveja(cerveja)) {
			return cervejaModel.editar(cerveja);
		}
		return false;
	}

	/**
	 * Método que passa um objeto cerveja para ser deletado no banco de dados
	 * pela classe CervejaModel.
	 * 
	 * @param cerveja
	 *            a ser deletada
	 * @return boolean true para transacao bem sucedida e false para transacao
	 *         interrompida.
	 * 
	 */
	@Override
	public boolean deletar(Cerveja cerveja) {
		return cervejaModel.deletar(cerveja);
	}

	/**
	 * Metodo responsavel acionar a listagem de cervejas no banco de dados,
	 * atraves do objeto da classe CervejaModel.
	 * 
	 * @return ArrayList com os objetos da Classe Cerveja gerados com os dados
	 *         recebidos do banco de dados.
	 * 
	 */
	@Override
	public List<Cerveja> listarTodos() {
		return cervejaModel.listarTodos();
	}

	/**
	 * Metodo responsavel por buscar e retornar o objeto de uma Cerveja, com os
	 * dados do banco de dados atraves do metodo contido na classe CervejaModel.
	 * 
	 * @param id
	 *            ID da cerveja cadastrada no banco
	 * @return Cerveja selecionada
	 */
	public Cerveja selecionarCerveja(int id) {
		return cervejaModel.selecionarCerv(id);
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
		return cervejaModel.buscarCerv(busca);
	}

	/**
	 * Metodo responsavel por validar o objeto cerveja, garantindo que seus
	 * dados estejam dentro do permitido pelo banco de dados.
	 * 
	 * @param cerveja
	 * @return boolean true para objeto dentro dos limites e false caso
	 *         contrário
	 */
	private boolean validarCerveja(Cerveja cerveja) {

		String nm_cerv = cerveja.getNm_cerv();
		String nm_estilo = cerveja.getNm_estilo();
		double teor_alcool = cerveja.getTeor_alcool();

		if (nm_cerv.length() > 60) {
			System.out.println("Nome de cerveja muito longo.");
			return false;
		}

		if (nm_estilo.length() > 60) {
			System.out.println("Nome do estilo da cerveja muito longo.");
			return false;
		}

		if (teor_alcool > 70 || teor_alcool < 0) {
			System.out.println("Teor Alcoolico fora dos valores permitidos.");
			return false;
		}

		return true;

	}

}
