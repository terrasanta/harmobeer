/**
 * 
 */
package com.harmobeer.mvc.controller;

import java.util.List;

import com.harmobeer.mvc.model.AvaliacaoModel;
import com.harmobeer.vo.Avaliacao;
import com.harmobeer.vo.Harmonizacao;
import com.harmobeer.vo.Usuario;

/**
 * Classe responsavel pelo controller para o objeto Avaliacao
 *
 * @author Jose Carlos Soares da Cruz Junior / Luan Henrique Cunha Alves
 *
 */
public class AvaliacaoController {
	private AvaliacaoModel avaliacaoModel;

	public AvaliacaoController() {
		avaliacaoModel = new AvaliacaoModel();
	}

	/**
	 * 
	 * Metodo para incluir uma avaliacao no banco de dados, de um determinado
	 * usuario em uma determinada harmonizacao.
	 * 
	 * @param aval
	 * @param user
	 * @param harmo
	 * @return boolean
	 */
	public boolean incluirAvaliacao(Avaliacao aval, Usuario user, Harmonizacao harmo) {
		if (validarAvaliacao(aval)) {
			return avaliacaoModel.incluirAvaliacao(aval, user, harmo);
		} else {
			return false;
		}
	}

	/**
	 * Metodo para editar uma avaliacao nos itens de nota e comentario.
	 * 
	 * @param aval
	 * @return boolean
	 */
	public boolean editarAvaliacao(Avaliacao aval) {
		if (validarAvaliacao(aval)) {
			return avaliacaoModel.editarAvaliacao(aval);
		} else {
			return false;
		}
	}

	/**
	 * Metodo para deletar uma avaliacao.
	 * 
	 * @param aval
	 * @return boolean
	 */
	public boolean deletarAvaliacao(Avaliacao aval) {
		return avaliacaoModel.deletarAvaliacao(aval);
	}

	/**
	 * Metodo que obtem do banco de dados uma lista de avaliacoes feitas por determinado usuario
	 * @param idUser
	 * @return List<Avaliacao>
	 */
		public List<Avaliacao> listarAvalporUser(int idUser) {
			return avaliacaoModel.listarAvalporUser(idUser);

		}

	/**
		 * Metodo que obtem do banco de dados uma lista de avaliacoes feitas para determinada harmonizacao
		 * @param idUser
		 * @return List<Avaliacao>
		 */
		public List<Avaliacao> listarAvalporHarmo(int idHarmo) {
			return avaliacaoModel.listarAvalporHarmo(idHarmo);
			}
		
		/**
		 * Metodo responsavel por buscar e retornar um objeto da classe Avaliacao no
		 * banco
		 *
		 * @param id
		 *            ID da avaliacao cadastrada no banco
		 * @return Avaliacao selecionada
		 */
		public Avaliacao selecionarAval(int id) {
			return avaliacaoModel.selecionarAval(id);
		}

	/**
	 * Método para validar se uma avaliação é válida para entrar no banco de
	 * dados.
	 * 
	 * @param aval
	 * @return
	 */
	private boolean validarAvaliacao(Avaliacao aval) {

		int nota = aval.getNota();
		String comentario = aval.getComentario();

		if (comentario.length() > 140) {
			System.out.println("Comentario muito longo");
			return false;
		}

		if (nota > 10 || nota < 1) {
			System.out.println("Nota Inválida");
			return false;
		}

		return true;

	}
}
