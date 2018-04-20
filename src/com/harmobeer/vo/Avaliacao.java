/**
 * 
 */
package com.harmobeer.vo;

/** 
 * Classe que modela o objeto Avaliacao
 * 
 * @author Jose Carlos Soares da Cruz Junior / Luan Henrique Cunha Alves
 */
public class Avaliacao {
	
	private int id_aval;
	private int id_harmo;
	private String nm_cerv;
	private String nm_prato;
	private int id_user;
	private String username;
	private int nota;
	private String comentario;
	
	/**
	 * @param id_aval
	 
	 */
	public Avaliacao(int id_aval) {
		this.id_aval = id_aval;
		
	}
	/**
	 * @param id_aval
	 * @param id_harmo
	 * @param nm_cerv
	 * @param nm_prato
	 * @param id_user
	 * @param username
	 * @param nota
	 * @param comentario
	 */
	public Avaliacao(int id_aval, int id_harmo, String nm_cerv, String nm_prato, int id_user, String username, int nota,
			String comentario) {
		this.id_aval = id_aval;
		this.id_harmo = id_harmo;
		this.nm_cerv = nm_cerv;
		this.nm_prato = nm_prato;
		this.id_user = id_user;
		this.username = username;
		this.nota = nota;
		this.comentario = comentario;
	}


	
	
	/**
	 * @param id_aval
	 * @param id_harmo
	 * @param id_user
	 * @param nota
	 * @param comentario
	 */
	public Avaliacao(int id_aval, int id_harmo, int id_user, int nota, String comentario) {
		this.id_aval = id_aval;
		this.id_harmo = id_harmo;
		this.id_user = id_user;
		this.nota = nota;
		this.comentario = comentario;
	}
	
	
	/**
	 * @param id_harmo
	 * @param id_user
	 * @param nota
	 * @param comentario
	 */
	public Avaliacao(int id_harmo, int id_user, int nota, String comentario) {
		this.id_harmo = id_harmo;
		this.id_user = id_user;
		this.nota = nota;
		this.comentario = comentario;
	}


	/**
	 * @param nota
	 * @param comentario
	 */
	public Avaliacao(int nota, String comentario) {
		this.nota = nota;
		this.comentario = comentario;
	}


	/**
	 * @return the id_aval
	 */
	public int getId_aval() {
		return id_aval;
	}
	/**
	 * @param id_aval the id_aval to set
	 */
	public void setId_aval(int id_aval) {
		this.id_aval = id_aval;
	}
	/**
	 * @return the id_harmo
	 */
	public int getId_harmo() {
		return id_harmo;
	}
	/**
	 * @param id_harmo the id_harmo to set
	 */
	public void setId_harmo(int id_harmo) {
		this.id_harmo = id_harmo;
	}
	/**
	 * @return the id_user
	 */
	public int getId_user() {
		return id_user;
	}
	/**
	 * @param id_user the id_user to set
	 */
	public void setId_user(int id_user) {
		this.id_user = id_user;
	}
	/**
	 * @return the nota
	 */
	public int getNota() {
		return nota;
	}
	/**
	 * @param nota the nota to set
	 */
	public void setNota(int nota) {
		this.nota = nota;
	}
	/**
	 * @return the comentario
	 */
	public String getComentario() {
		return comentario;
	}
	/**
	 * @param comentario the comentario to set
	 */
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}


	/**
	 * @return the nm_cerv
	 */
	public String getNm_cerv() {
		return nm_cerv;
	}


	/**
	 * @param nm_cerv the nm_cerv to set
	 */
	public void setNm_cerv(String nm_cerv) {
		this.nm_cerv = nm_cerv;
	}


	/**
	 * @return the nm_prato
	 */
	public String getNm_prato() {
		return nm_prato;
	}


	/**
	 * @param nm_prato the nm_prato to set
	 */
	public void setNm_prato(String nm_prato) {
		this.nm_prato = nm_prato;
	}


	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}


	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	

}
