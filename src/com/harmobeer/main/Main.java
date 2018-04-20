/**
 * 
 */
package com.harmobeer.main;

import com.harmobeer.mvc.view.CervejaView;

/**
 * Classe respons�vel por carregar o menu, puxando diretamente da View 
 * @author Jos� Carlos Soares da Cruz Junior / Luan Henrique Cunha Alves
 * 
 *
 */
public class Main {

	/**
	 * M�todo para invocar a View da classe Cerveja
	 * @param args
	 */
	public static void main(String[] args) {
		CervejaView cv = new CervejaView();
		cv.menu();
		

	}

}
