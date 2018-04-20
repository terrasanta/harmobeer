/**
 *
 */
package com.harmobeer.mvc.controller;

import java.util.ArrayList;

import com.harmobeer.mvc.model.HarmonizacaoModel;
import com.harmobeer.vo.Cerveja;
import com.harmobeer.vo.Harmonizacao;
import com.harmobeer.vo.Prato;

/**
 *
 * Classe responsavel pelo controller do objeto Harmonizacao
 *
 * @author Jose Carlos Soares da Cruz Junior / Luan Henrique Cunha Alves
 *
 */
public class HarmonizacaoController {
	private HarmonizacaoModel harmonizacaoModel;

	/**
	 * Construtor do controller da classe Harmonizacao
	 */

	public HarmonizacaoController() {
		harmonizacaoModel = new HarmonizacaoModel();
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
		return harmonizacaoModel.incluirHarmonizacao(cerveja, prato);
	}
	/**
	 * Metodo para calcular a media de determinada harmonizacao baseada nas
	 * avaliacoes feitas por ela.
	 * 
	 * @param harmo
	 * @return boolean
	 */
	public boolean calcularMedia(Harmonizacao harmo) {
		return harmonizacaoModel.calcularMedia(harmo);
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
		return harmonizacaoModel.gerarRanking(cerveja);
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
		return harmonizacaoModel.gerarRanking(prato);
	}
	/**
	 * Método que devolve a id de uma harmonização com base na cerveja e no prato
	 * @param cerveja
	 * @param prato
	 * @return int id_harmo
	 */
	public int selecionaridHarmonizacao(Cerveja cerveja, Prato prato) {
		return harmonizacaoModel.selecionaridHarmonizacao(cerveja, prato);
	}
	/**
	 * Metodo para selecionar uma harmonizacao no banco de dados
	 * @param id_harmo
	 * @return objeto harmonizacao 
	 */
	public Harmonizacao selecionarHarmo(int id_harmo) {
		return harmonizacaoModel.selecionarHarmo(id_harmo);
	}
	/**
	 * Metodo que devolve uma ArrayList com toda as harmonizacoes cadastradas.
	 * @return ArrayList com todas as harmonizacoes
	 */
	public ArrayList<Harmonizacao> listarTodos() {
		return harmonizacaoModel.listarTodos();
	}
}
