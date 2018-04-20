/**
 *
 */
package com.harmobeer.db.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.harmobeer.interfaces.IUsuarioDAO;
import com.harmobeer.vo.Usuario;

/**
 * Classe responsavel pelo acesso ao banco de dados para Usuario
 *
 * @author Jose Carlos Soares da Cruz Junior / Luan Henrique Cunha Alves
 */
public class UsuarioDAO implements IUsuarioDAO {
	private static final String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";
	private static final String LOCAL_HOST = "jdbc:oracle:thin:@//localhost:1521/xe";
	private static final String DB_USER = "harmobeer";
	private static final String DB_PASSWORD = "harmobeer";
	private static final String ERRO = "Nao foi possivel completar sua requisicao.";

	/**
	 * Inclui um usuario no banco de dados.
	 *
	 * @param user
	 * @return boolean
	 */
	@Override
	public boolean incluir(Usuario user) {
		Connection connection = null;
		PreparedStatement sttm = null;

		try {
			Class.forName(JDBC_DRIVER);

			connection = DriverManager.getConnection(LOCAL_HOST, DB_USER, DB_PASSWORD);

			sttm = connection.prepareStatement(
					"insert into usuario(id_user, username, email, senha, privilegio, info) values (sequsuario.nextval,?,?,?, 0,?)");
			sttm.setString(1, user.getUsername());
			sttm.setString(2, user.getEmail());
			sttm.setString(3, user.getSenha());
			sttm.setString(4, user.getInfo());

			sttm.executeUpdate();

			return true;

		} catch (ClassNotFoundException e) {
			System.out.println(ERRO);
			e.printStackTrace();
			return false;
		} catch (SQLException Except) {
			System.out.println(ERRO);
			Except.printStackTrace();
			return false;
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
				if (sttm != null) {
					sttm.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Edita um usuario no banco de dados, podendo atualizar as seguintes
	 * informacoes: username, email, info e senha.
	 *
	 * @param user
	 * @return boolean
	 */
	@Override
	public boolean editar(Usuario user) {
		Connection connection = null;
		PreparedStatement sttm = null;

		try {
			Class.forName(JDBC_DRIVER);

			connection = DriverManager.getConnection(LOCAL_HOST, DB_USER, DB_PASSWORD);

			sttm = connection
					.prepareStatement("UPDATE usuario set username=?, email=?, senha=?, info=? where id_user=?");

			sttm.setString(1, user.getUsername());
			sttm.setString(2, user.getEmail());
			sttm.setString(3, user.getSenha());
			sttm.setString(4, user.getInfo());
			sttm.setInt(5, user.getId_user());

			sttm.executeUpdate();

			return true;

		} catch (ClassNotFoundException e) {
			System.out.println(ERRO);
			e.printStackTrace();
			return false;
		} catch (SQLException Except) {
			System.out.println(ERRO);
			Except.printStackTrace();
			return false;
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
				if (sttm != null) {
					sttm.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Transforma um usuario em um usuario anonimo, mantendo as suas avaliacoes
	 * e contribuicoes para a plataforma intactas.
	 *
	 * @param user
	 * @return boolean
	 */
	@Override
	public boolean deletar(Usuario user) {
		Connection connection = null;
		PreparedStatement sttm = null;
		try {
			Class.forName(JDBC_DRIVER);

			connection = DriverManager.getConnection(LOCAL_HOST, DB_USER, DB_PASSWORD);
			sttm = connection.prepareStatement(
					"UPDATE usuario set username = 'Anonimo', email='anonimo@harmobeer.com', senha='anonimo', info='Anonimo' where id_user=?");
			sttm.setInt(1, user.getId_user());
			sttm.executeUpdate();
			
			return true;

		} catch (ClassNotFoundException e) {
			System.out.println(ERRO);
			e.printStackTrace();
			return false;
		} catch (SQLException Except) {
			System.out.println(ERRO);
			Except.printStackTrace();
			return false;
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
				if (sttm != null) {
					sttm.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
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
	public List<Usuario> buscarUser(String busca) {
		ArrayList<Usuario> listaUser = new ArrayList<Usuario>();
		Connection connection = null;
		PreparedStatement sttm = null;
		try {
			Class.forName(JDBC_DRIVER);

			connection = DriverManager.getConnection(LOCAL_HOST, DB_USER, DB_PASSWORD);

			sttm = connection
					.prepareStatement("select * from usuario where regexp_like(username, '" + busca + "', 'i')");

			ResultSet rs = sttm.executeQuery();

			while (rs.next()) {

				int id_user = rs.getInt("id_user");
				String username = rs.getString("username");
				String email = rs.getString("email");
				String senha = rs.getString("senha");
				int privilegio = rs.getInt("privilegio");
				String info = rs.getString("info");
				Usuario user = new Usuario(id_user, username, email, senha, privilegio, info);

				listaUser.add(user);
			}

			return listaUser;

		} catch (ClassNotFoundException e) {
			System.out.println(ERRO);
			e.printStackTrace();
			return null;
		} catch (SQLException Except) {
			System.out.println(ERRO);
			Except.printStackTrace();
			return null;
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
				if (sttm != null) {
					sttm.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

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
	public Usuario logar(String username, String senha) {
		Connection connection = null;
		PreparedStatement sttm = null;
		Usuario user = null;
		try {
			Class.forName(JDBC_DRIVER);

			connection = DriverManager.getConnection(LOCAL_HOST, DB_USER, DB_PASSWORD);

			sttm = connection.prepareStatement("select id_user, email, privilegio, info from usuario where username ='"
					+ username + "' and senha='" + senha + "' ");

			ResultSet rs = sttm.executeQuery();

			while (rs.next()) {

				int id_user = rs.getInt("id_user");
				String email = rs.getString("email");
				int privilegio = rs.getInt("privilegio");
				String info = rs.getString("info");
				user = new Usuario(id_user, username, email, senha, privilegio, info);

			}

			return user;

		} catch (ClassNotFoundException e) {
			System.out.println(ERRO);
			e.printStackTrace();
			return null;
		} catch (SQLException Except) {
			System.out.println(ERRO);
			Except.printStackTrace();
			return null;
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
				if (sttm != null) {
					sttm.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * Verifica se o usuario recebido possui privilegio de administrador.
	 *
	 * @param usuario
	 * @return boolean
	 */
	@Override
	public boolean verificarPrivilegio(Usuario usuario) {
		if (usuario.getPrivilegio() == 0) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Metodo para retornar um usuario do banco de dados utilizando seu id
	 *
	 * @param id_user
	 * @return
	 */
	public Usuario selecionarUser(int id_user) {
		Connection connection = null;
		PreparedStatement sttm = null;
		Usuario user = null;
		try {
			Class.forName(JDBC_DRIVER);

			connection = DriverManager.getConnection(LOCAL_HOST, DB_USER, DB_PASSWORD);

			sttm = connection.prepareStatement("select * from usuario where id_user = ?");

			sttm.setInt(1, id_user);

			ResultSet rs = sttm.executeQuery();

			while (rs.next()) {

				String username = rs.getString("username");
				String senha = rs.getString("senha");
				String email = rs.getString("email");
				int privilegio = rs.getInt("privilegio");
				String info = rs.getString("info");
				user = new Usuario(id_user, username, email, senha, privilegio, info);

			}

			return user;

		} catch (ClassNotFoundException e) {
			System.out.println(ERRO);
			e.printStackTrace();
			return null;
		} catch (SQLException Except) {
			System.out.println(ERRO);
			Except.printStackTrace();
			return null;
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
				if (sttm != null) {
					sttm.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Metodo responsavel por realizar a listagem de todos os Usuarios
	 * cadastradas no banco.
	 *
	 * @return ArrayList com os objetos da Classe Usuario gerados com os dados
	 *         recebidos do banco de dados.
	 *
	 */

	public List<Usuario> listarTodos() {
		ArrayList<Usuario> listaUsuario = new ArrayList<Usuario>();
		Connection connection = null;
		PreparedStatement sttm = null;
		try {
			Class.forName(JDBC_DRIVER);

			connection = DriverManager.getConnection(LOCAL_HOST, DB_USER, DB_PASSWORD);

			sttm = connection.prepareStatement("select * from usuario");
			ResultSet rs = sttm.executeQuery();

			while (rs.next()) {

				int id_user = rs.getInt("id_user");
				String username = rs.getString("username");
				String senha = rs.getString("senha");
				String email = rs.getString("email");
				int privilegio = rs.getInt("privilegio");
				String info = rs.getString("info");
				Usuario user = new Usuario(id_user, username, email, senha, privilegio, info);
				listaUsuario.add(user);
			}

			return listaUsuario;

		} catch (ClassNotFoundException e) {
			System.out.println(ERRO);
			e.printStackTrace();
			return null;
		} catch (SQLException Except) {
			System.out.println(ERRO);
			Except.printStackTrace();
			return null;
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
				if (sttm != null) {
					sttm.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

}
