/**
 *
 */
package com.harmobeer.vo;

/**
 * Classe que modela o objeto Usuario
 *
 * @author Jos� Carlos Soares da Cruz Junior / Luan Henrique Cunha Alves
 */
public class Usuario {

	private int id_user;
	private String username;
	private String email;
	private String senha;
	private int privilegio;
	private String info;


	public Usuario() {

	}
	/**
	 * @param id_user
	 * @param username
	 * @param email
	 * @param senha
	 * @param privilegio
	 * @param info
	 */
	public Usuario(int id_user, String username, String email, String senha, int privilegio, String info) {
		this.id_user = id_user;
		this.username = username;
		this.email = email;
		this.senha = senha;
		this.privilegio = privilegio;
		this.info = info;
	}

	/**
	 * @param username
	 * @param email
	 * @param senha
	 * @param info
	 */
	public Usuario(String username, String email, String senha, String info) {
		this.username = username;
		this.email = email;
		this.senha = senha;
		this.info = info;
	}

	/**
	 * @param username
	 * @param senha
	 */
	public Usuario(String username, String senha) {
		this.username = username;
		this.senha = senha;

	}

	/**
	 * @param username
	 * @param email
	 * @param senha
	 * @param privilegio
	 * @param info
	 */
	public Usuario(String username, String email, String senha, int privilegio, String info) {
		this.username = username;
		this.email = email;
		this.senha = senha;
		this.privilegio = privilegio;
		this.info = info;
	}

	/**
	 * @param id_user
	 * @param username
	 * @param email
	 * @param senha
	 * @param info
	 */
	public Usuario(int id_user, String username, String email, String senha, String info) {
		this.id_user = id_user;
		this.username = username;
		this.email = email;
		this.senha = senha;
		this.info = info;
	}

	/**
	 * @param id_user
	 */
	public Usuario(int id_user) {
		this.id_user = id_user;

	}

	/**
	 * @return the id_user
	 */
	public int getId_user() {
		return id_user;
	}

	/**
	 * @param id_user
	 *            the id_user to set
	 */
	public void setId_user(int id_user) {
		this.id_user = id_user;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username
	 *            the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the senha
	 */
	public String getSenha() {
		return senha;
	}

	/**
	 * @param senha
	 *            the senha to set
	 */
	public void setSenha(String senha) {
		this.senha = senha;
	}

	/**
	 * @return the privilegio
	 */
	public int getPrivilegio() {
		return privilegio;
	}

	/**
	 * @param privilegio
	 *            the privilegio to set
	 */
	public void setPrivilegio(int privilegio) {
		this.privilegio = privilegio;
	}

	/**
	 * @return the info
	 */
	public String getInfo() {
		return info;
	}

	/**
	 * @param info
	 *            the info to set
	 */
	public void setInfo(String info) {
		this.info = info;
	}

}
