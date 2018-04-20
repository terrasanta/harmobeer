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

import com.harmobeer.interfaces.ICervejaDAO;
import com.harmobeer.vo.Cerveja;

/**
 *
 * Classe responsavel pelo acesso ao banco de dados do objeto cerveja
 * 
 * @author Jose Carlos Soares da Cruz Junior / Luan Henrique Cunha Alves
 *
 */
public class CervejaDAO implements ICervejaDAO {

	private static final String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";
	private static final String LOCAL_HOST = "jdbc:oracle:thin:@//localhost:1521/xe";
	private static final String DB_USER = "harmobeer";
	private static final String DB_PASSWORD = "harmobeer";
	private static final String ERRO = "Nao foi possivel completar sua requisicao.";

	/**
	 * Metodo responsavel por realizar a inclusao de Cervejas no banco.
	 *
	 * @param Cerveja
	 *            cerveja a ser incluida
	 * @return boolean true para transacao bem sucedida e false para
	 *         transacao interrompida.
	 *
	 */
	@Override
	public boolean incluir(Cerveja cerveja) {
		Connection connection = null;
		PreparedStatement sttm = null;

		try {
			Class.forName(JDBC_DRIVER);

			connection = DriverManager.getConnection(LOCAL_HOST, DB_USER, DB_PASSWORD);

			sttm = connection.prepareStatement(
					"insert into cerveja(id_cerv, nm_cerv, nm_estilo, teor_alcoolico) values(seqcerv.nextval,?,?,?)");
			sttm.setString(1, cerveja.getNm_cerv());
			sttm.setString(2, cerveja.getNm_estilo());
			sttm.setDouble(3, cerveja.getTeor_alcool());

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
	 * Metodo responsavel por realizar a edicao de Cervejas cadastradas
	 * no banco.
	 *
	 * @param Cerveja
	 *            cerveja a ser editada
	 * @return boolean true para transacao bem sucedida e false para
	 *         transacao interrompida.
	 */
	@Override
	public boolean editar(Cerveja cerveja) {
		Connection connection = null;
		PreparedStatement sttm = null;
		try {
			Class.forName(JDBC_DRIVER);

			connection = DriverManager.getConnection(LOCAL_HOST, DB_USER, DB_PASSWORD);

			sttm = connection.prepareStatement(
					"UPDATE cerveja SET nm_cerv = ?, nm_estilo = ?, teor_alcoolico = ? where id_cerv = ?");
			sttm.setString(1, cerveja.getNm_cerv());
			sttm.setString(2, cerveja.getNm_estilo());
			sttm.setDouble(3, cerveja.getTeor_alcool());
			sttm.setInt(4, cerveja.getId_cerv());

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
	 * Metodo responsavel por realizar a exclusao de Cervejas cadastradas
	 * no banco
	 *
	 * @param Cerveja
	 *            cerveja a deletada
	 * @return boolean true para transacao bem sucedida e false para
	 *         transacao interrompida.
	 */
	@Override
	public boolean deletar(Cerveja cerveja) {
		Connection connection = null;
		PreparedStatement sttm = null;
		PreparedStatement sttmHarmo = null;
		try {
			Class.forName(JDBC_DRIVER);

			connection = DriverManager.getConnection(LOCAL_HOST, DB_USER, DB_PASSWORD);
			sttmHarmo = connection.prepareStatement("select id_harmo from harmonizacao where id_cerv=?");
			sttmHarmo.setInt(1, cerveja.getId_cerv());
			ResultSet rs = sttmHarmo.executeQuery();
			while (rs.next()) {
				int id_harmo = rs.getInt("id_harmo");
				sttm = connection.prepareStatement("DELETE from avaliacao where id_harmo = ?");
				sttm.setInt(1, id_harmo);
				sttm.executeUpdate();
				if (sttm != null) {
					sttm.close();
				}
			}
			if (sttmHarmo != null) {
				sttmHarmo.close();
			}
			if (sttm != null) {
				sttm.close();
			}
			sttm = connection.prepareStatement("DELETE from harmonizacao where id_cerv = ?");
			sttm.setInt(1, cerveja.getId_cerv());
			sttm.executeUpdate();
			if (sttm != null) {
				sttm.close();
			}
			sttm = connection.prepareStatement("DELETE from cerveja where id_cerv = ?");
			sttm.setInt(1, cerveja.getId_cerv());
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
	 * Metodo responsavel por realizar a listagem de todas as Cervejas
	 * cadastradas no banco.
	 *
	 * @return ArrayList com os objetos da Classe Cerveja gerados com os dados
	 *         recebidos do banco de dados.
	 *
	 */
	@Override
	public List<Cerveja> listarTodos() {
		ArrayList<Cerveja> listaCerv = new ArrayList<Cerveja>();
		Connection connection = null;
		PreparedStatement sttm = null;
		try {
			Class.forName(JDBC_DRIVER);

			connection = DriverManager.getConnection(LOCAL_HOST, DB_USER, DB_PASSWORD);

			sttm = connection.prepareStatement("select * from cerveja");
			ResultSet rs = sttm.executeQuery();

			while (rs.next()) {

				int id_cerv = rs.getInt("id_cerv");
				String nm_cerv = rs.getString("nm_cerv");
				String nm_estilo = rs.getString("nm_estilo");
				double teor_alcool = rs.getDouble("teor_alcoolico");

				Cerveja cerv = new Cerveja(id_cerv, nm_cerv, nm_estilo, teor_alcool);

				listaCerv.add(cerv);
			}

			return listaCerv;

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
	 * Metodo responsavel por buscar e retornar um objeto da classe Cerveja no
	 * banco
	 *
	 * @param id
	 *            ID da cerveja cadastrada no banco
	 * @return Cerveja selecionada
	 */
	public Cerveja selecionarCerv(int id) {
		Cerveja cerv = new Cerveja(id);
		Connection connection = null;
		PreparedStatement sttm = null;
		try {
			Class.forName(JDBC_DRIVER);

			connection = DriverManager.getConnection(LOCAL_HOST, DB_USER, DB_PASSWORD);

			sttm = connection.prepareStatement("select * from cerveja where id_cerv = ?");
			sttm.setInt(1, id);
			ResultSet rs = sttm.executeQuery();

			while (rs.next()) {

				cerv.setNm_cerv(rs.getString("nm_cerv"));
				cerv.setNm_estilo(rs.getString("nm_estilo"));
				cerv.setTeor_alcool(rs.getDouble("teor_alcoolico"));

			}
			return cerv;
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
	 * Metodo responsavel por buscar e retornar uma lista de cervejas que contem
	 * uma string pre-determinada.
	 *
	 * @param String
	 *            busca
	 * @return List<Cerveja> com cervejas que contem busca no nm_cerv
	 */
	public List<Cerveja> buscarCerv(String busca) {
		ArrayList<Cerveja> listaCerv = new ArrayList<Cerveja>();
		Connection connection = null;
		PreparedStatement sttm = null;
		try {
			Class.forName(JDBC_DRIVER);

			connection = DriverManager.getConnection(LOCAL_HOST, DB_USER, DB_PASSWORD);

			sttm = connection.prepareStatement("select * from cerveja where regexp_like(nm_cerv, '"+ busca+"', 'i')");

			ResultSet rs = sttm.executeQuery();

			while (rs.next()) {

				int id_cerv = rs.getInt("id_cerv");
				String nm_cerv = rs.getString("nm_cerv");
				String nm_estilo = rs.getString("nm_estilo");
				double teor_alcool = rs.getDouble("teor_alcoolico");

				Cerveja cerv = new Cerveja(id_cerv, nm_cerv, nm_estilo, teor_alcool);

				listaCerv.add(cerv);
			}

			return listaCerv;

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
