/**
 *
 */
package com.harmobeer.mvc.model;

import java.util.ArrayList;

import com.harmobeer.db.dao.HarmonizacaoDAO;
import com.harmobeer.vo.Cerveja;
import com.harmobeer.vo.Harmonizacao;
import com.harmobeer.vo.Prato;

/**
 *
 * Classe responsavel pelo model do objeto Harmonizacao
 *
 * @author Jose Carlos Soares da Cruz Junior / Luan Henrique Cunha Alves
 *
 */
public class HarmonizacaoModel {
	private HarmonizacaoDAO harmonizacaoDAO;

	/**
	 * Construtor da classe HarmonizacaoModel utilizando a classe
	 * HarmonizacaoDAO
	 */
	public HarmonizacaoModel() {
		harmonizacaoDAO = new HarmonizacaoDAO();
	}

	/**
	 * Metodo que inclui uma harmonizacao no banco de dados caso ela ja nao
	 * exista.
	 *
	 * @param cerveja
	 * @param prato
	 * @return boolean
	 */
	public boolean incluirHarmonizacao(Cerveja cerveja, Prato prato) {
		return harmonizacaoDAO.incluirHarmonizacao(cerveja, prato);
	}

	/**
	 * Metodo para calcular a media de determinada harmonizacao baseada nas
	 * avaliacoes feitas por ela.
	 * 
	 * @param harmo
	 * @return boolean
	 */
	public boolean calcularMedia(Harmonizacao harmo) {
		return harmonizacaoDAO.calcularMedia(harmo);
	}

	/**
	 * Metodo para gerar um ranking em um ArrayList das melhores harmonizacoes
	 * para determinada cerveja.
	 * 
	 * @param cerveja
	 * @return ArrayList<Harmonizacao> em ordem decrescente das harmonizacoes
	 *         pela media
	 */
	public ArrayList<Harmonizacao> gerarRanking(Cerveja cerveja) {
		return harmonizacaoDAO.gerarRanking(cerveja);
	}

	/**
	 * Metodo para gerar um ranking em um ArrayList das melhores harmonizacoes
	 * para determinado prato.
	 * 
	 * @param cerveja
	 * @return ArrayList<Harmonizacao> em ordem decrescente das harmonizacoes
	 *         pela media
	 */
	public ArrayList<Harmonizacao> gerarRanking(Prato prato) {
		return harmonizacaoDAO.gerarRanking(prato);
	}
	/**
	 * Método que devolve a id de uma harmonização com base na cerveja e no prato
	 * @param cerveja
	 * @param prato
	 * @return int id_harmo
	 */
	public int selecionaridHarmonizacao(Cerveja cerveja, Prato prato) {
		return harmonizacaoDAO.selecionaridHarmonizacao(cerveja, prato);
	}
	/**
	 * Metodo para selecionar uma harmonizacao no banco de dados
	 * @param id_harmo
	 * @return objeto harmonizacao 
	 */
	public Harmonizacao selecionarHarmo(int id_harmo) {
		return harmonizacaoDAO.selecionarHarmo(id_harmo);
	}
	/**
	 * Metodo que devolve uma ArrayList com toda as harmonizacoes cadastradas.
	 * @return ArrayList com todas as harmonizacoes
	 */
	public ArrayList<Harmonizacao> listarTodos() {
		return harmonizacaoDAO.listarTodos();
	}
	
}
