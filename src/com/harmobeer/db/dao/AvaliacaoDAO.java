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

import com.harmobeer.vo.Avaliacao;
import com.harmobeer.vo.Harmonizacao;
import com.harmobeer.vo.Usuario;

/**
 * Classe responsavel pelo acesso ao banco de dados para Avaliacao
 *
 * @author Jose Carlos Soares da Cruz Junior / Luan Henrique Cunha Alves
 *
 */
public class AvaliacaoDAO {
	private static final String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";
	private static final String LOCAL_HOST = "jdbc:oracle:thin:@//localhost:1521/xe";
	private static final String DB_USER = "harmobeer";
	private static final String DB_PASSWORD = "harmobeer";
	private static final String ERRO = "Nao foi possivel completar sua requisicao.";

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
		Connection connection = null;
		PreparedStatement sttm = null;

		try {
			Class.forName(JDBC_DRIVER);

			connection = DriverManager.getConnection(LOCAL_HOST, DB_USER, DB_PASSWORD);

			sttm = connection.prepareStatement(
					"insert into avaliacao(id_aval,id_user, id_harmo, nota, comentario) values (seqaval.nextval,?,?,?,?)");
			sttm.setInt(1, user.getId_user());
			sttm.setInt(2, harmo.getId_harmo());
			sttm.setInt(3, aval.getNota());
			sttm.setString(4, aval.getComentario());

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
	 * Metodo para editar uma avaliacao nos itens de nota e comentario.
	 * 
	 * @param aval
	 * @return boolean
	 */
	public boolean editarAvaliacao(Avaliacao aval) {
		Connection connection = null;
		PreparedStatement sttm = null;

		try {
			Class.forName(JDBC_DRIVER);

			connection = DriverManager.getConnection(LOCAL_HOST, DB_USER, DB_PASSWORD);

			sttm = connection.prepareStatement("UPDATE avaliacao set nota=?, comentario=? where id_aval=?");
			sttm.setInt(1, aval.getNota());
			sttm.setString(2, aval.getComentario());
			sttm.setInt(3, aval.getId_aval());

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
	 * Metodo para deletar uma avaliacao.
	 * 
	 * @param aval
	 * @return boolean
	 */
	public boolean deletarAvaliacao(Avaliacao aval) {
		Connection connection = null;
		PreparedStatement sttm = null;
		try {
			Class.forName(JDBC_DRIVER);

			connection = DriverManager.getConnection(LOCAL_HOST, DB_USER, DB_PASSWORD);
			sttm = connection.prepareStatement("DELETE from avaliacao where id_aval = ?");
			sttm.setInt(1, aval.getId_aval());
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
	 * Metodo que obtem do banco de dados uma lista de avaliacoes feitas por
	 * determinado usuario
	 * 
	 * @param idUser
	 * @return List<Avaliacao>
	 */
	public List<Avaliacao> listarAvalporUser(int idUser) {
		ArrayList<Avaliacao> listaAval = new ArrayList<Avaliacao>();
		Connection connection = null;
		PreparedStatement sttm = null;
		try {
			Class.forName(JDBC_DRIVER);

			connection = DriverManager.getConnection(LOCAL_HOST, DB_USER, DB_PASSWORD);

			sttm = connection.prepareStatement("select * from avaliacao where id_user = ?");
			sttm.setInt(1, idUser);
			ResultSet rs = sttm.executeQuery();

			while (rs.next()) {

				int id_aval = rs.getInt("id_aval");
				int id_harmo = rs.getInt("id_harmo");
				int nota = rs.getInt("nota");
				String comentario = rs.getString("comentario");

				Avaliacao aval = new Avaliacao(id_aval, id_harmo, idUser, nota, comentario);

				listaAval.add(aval);
			}

			return listaAval;

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
	 * Metodo que obtem do banco de dados uma lista de avaliacoes feitas para
	 * determinada harmonizacao
	 * 
	 * @param idUser
	 * @return List<Avaliacao>
	 */
	public List<Avaliacao> listarAvalporHarmo(int idHarmo) {
		ArrayList<Avaliacao> listaAval = new ArrayList<Avaliacao>();
		Connection connection = null;
		PreparedStatement sttm = null;

		try {
			Class.forName(JDBC_DRIVER);

			connection = DriverManager.getConnection(LOCAL_HOST, DB_USER, DB_PASSWORD);

			sttm = connection.prepareStatement("select * from avaliacao where id_harmo = ?");
			sttm.setInt(1, idHarmo);
			ResultSet rs = sttm.executeQuery();

			while (rs.next()) {

				int id_aval = rs.getInt("id_aval");
				int id_user = rs.getInt("id_user");
				int nota = rs.getInt("nota");
				String comentario = rs.getString("comentario");

				Avaliacao aval = new Avaliacao(id_aval, idHarmo, id_user, nota, comentario);

				listaAval.add(aval);
			}

			return listaAval;

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
	 * Metodo responsavel por buscar e retornar um objeto da classe Avaliacao no
	 * banco
	 *
	 * @param id
	 *            ID da avaliacao cadastrada no banco
	 * @return Avaliacao selecionada
	 */
	public Avaliacao selecionarAval(int id) {
		Avaliacao aval = new Avaliacao(id);
		Connection connection = null;
		PreparedStatement sttm = null;
		try {
			Class.forName(JDBC_DRIVER);

			connection = DriverManager.getConnection(LOCAL_HOST, DB_USER, DB_PASSWORD);

			sttm = connection.prepareStatement("select * from avaliacao where id_aval = ?");
			sttm.setInt(1, id);
			ResultSet rs = sttm.executeQuery();

			while (rs.next()) {

				aval.setId_user(rs.getInt("id_user"));
				aval.setId_harmo(rs.getInt("id_harmo"));
				aval.setNota(rs.getInt("nota"));
				aval.setComentario(rs.getString("comentario"));

			}
			return aval;
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
