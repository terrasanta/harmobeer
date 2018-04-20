/**
 *
 */
package com.harmobeer.mvc.model;

import java.util.List;

import com.harmobeer.db.dao.UsuarioDAO;
import com.harmobeer.interfaces.IUsuarioDAO;
import com.harmobeer.vo.Usuario;

/**
 * Classe responsavel pelo modelo do objeto Usuario
 *
 * @author Jose Carlos Soares da Cruz Junior / Luan Henrique Cunha Alves
 *
 */
public class UsuarioModel implements IUsuarioDAO
{

    private UsuarioDAO usuarioDAO;

    /**
     * Construtor de UsuarioModel utilizando a classe UsuarioDAO
     */
    public UsuarioModel()
    {
        usuarioDAO = new UsuarioDAO();
    }

    /**
     * Inclui um usuario no banco de dados.
     *
     * @param user
     * @return boolean
     */
    @Override
    public boolean incluir(Usuario user)
    {
        return usuarioDAO.incluir(user);
    }

    /**
     * Edita um usuario no banco de dados, podendo atualizar as seguintes
     * informacoes: username, email, info e senha.
     *
     * @param user
     * @return boolean
     */
    @Override
    public boolean editar(Usuario user)
    {
        return usuarioDAO.editar(user);
    }

    /**
     *  Transforma um usuario em um usuario anonimo, mantendo as suas avaliacoes
	 * e contribuicoes para a plataforma intactas.
     *
     * @param user
     * @return boolean
     */
    @Override
    public boolean deletar(Usuario user)
    {
        return usuarioDAO.deletar(user);
    }

    /**
     * Metodo responsavel por buscar e retornar uma lista de usuariso que contem
     * uma string pre-determinada.
     *
     * @param String
     *            busca
     * @return List<Usuario> com usuarios que contem busca no username
     */
    @Override
    public List<Usuario> buscarUser(String busca)
    {
        return usuarioDAO.buscarUser(busca);

    }

    /**
     * Metodo responsavel por retornar um usuario cujas Strings para username e
     * senha tenham um match no banco de dados
     *
     * @param Usuario
     *            usuario
     * @return Usuario com username e senha correspondentes ou null se nao
     *         houver correspondente
     */
    @Override
    public Usuario logar(String username, String senha)
    {
        return usuarioDAO.logar(username, senha);
    }

    /**
     * Verifica se o usuario recebido possui privilegio de administrador.
     *
     * @param usuario
     * @return boolean
     */
    @Override
    public boolean verificarPrivilegio(Usuario usuario)
    {
        return usuarioDAO.verificarPrivilegio(usuario);
    }

    /**
     * Metodo para retornar um usuario do banco de dados utilizando seu id
     *
     * @param id_user
     * @return
     */
    public Usuario selecionarUser(int id_user)
    {
        return usuarioDAO.selecionarUser(id_user);
    }

    /**
     * Metodo responsavel por realizar a listagem de todos os Usuarios
     * cadastradas no banco.
     *
     * @return ArrayList com os objetos da Classe Usuario gerados com os dados
     *         recebidos do banco de dados.
     *
     */

    public List<Usuario> listarTodos()
    {
        return usuarioDAO.listarTodos();
    }

}
