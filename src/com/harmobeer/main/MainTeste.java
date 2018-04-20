package com.harmobeer.main;

import com.harmobeer.mvc.controller.UsuarioController;
import com.harmobeer.vo.Usuario;

public class MainTeste {

	public static void main(String[] args) {
		/*
		Harmonizacao harmo = new Harmonizacao(1);
		Cerveja cerveja = new Cerveja(4);
		CervejaDAO cvd = new CervejaDAO();
		Prato prato = new Prato(5);
		Avaliacao aval = new Avaliacao(1,3,4,"Nao tomaria de novo");
		AvaliacaoDAO ad = new AvaliacaoDAO();
		UsuarioDAO ud =new UsuarioDAO();
		Usuario user = new Usuario(4);
		ArrayList<Cerveja> cv = new ArrayList<Cerveja>();

		cv=(ArrayList<Cerveja>) cvd.buscarCerv("oru");

		for (Cerveja cerv:cv){
			System.out.println(cerv.getNm_cerv());
		}



		*/
		UsuarioController uc = new UsuarioController();
		Usuario usuarioLogado = new Usuario();
		usuarioLogado = uc.logar("Jose","jose123");

		System.out.println(usuarioLogado.getId_user());
		System.out.println(usuarioLogado.getEmail());


	}

}
