/**
 *
 */
package com.harmobeer.interfaces;

import java.util.List;

import com.harmobeer.vo.Usuario;

/**
 * Interface responsavel pelos DAO da classe Usuario
 *
 * @author Jose Carlos Soares da Cruz Junior / Luan Henrique Cunha Alves
 *
 */
public interface IUsuarioDAO {
	public boolean incluir(Usuario usuario);

	public boolean editar(Usuario usuario);

	public boolean deletar(Usuario usuario);

	public List<Usuario> buscarUser(String busca);

	public Usuario logar (String username, String senha);

	public boolean verificarPrivilegio(Usuario usuario);
}