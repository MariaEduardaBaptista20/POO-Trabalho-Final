package Data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Model.Estudante;

public class BancoDeDados {

	private Connection conexao;
	private int alterado = 0;

	public BancoDeDados(Connection conexao) {
		super();
		this.conexao = conexao;
	}

	public void criarTabelaEstudantes() throws InterruptedException, SQLException {
		PreparedStatement preparedStatement = null;
		if (conexao != null) {
			try {
				String sql = "CREATE TABLE IF NOT EXISTS estudantes (" + "id SERIAL PRIMARY KEY,"
						+ "nome VARCHAR(255) NOT NULL," + "curso VARCHAR(255) NOT NULL" + ")";
				preparedStatement = conexao.prepareStatement(sql);
				preparedStatement.execute();
				System.out.println("Tabela criada com sucesso!");
				Thread.sleep(2000);
				preparedStatement.close();
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("Erro ao criar tabela.");
				Thread.sleep(2000);
			} finally {
				preparedStatement.close();
			}
		}
	}

	public void adicionarEstudante(Estudante estudante) throws InterruptedException, SQLException {
		String sql = "INSERT INTO estudantes (nome, curso) VALUES (?, ?)";
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = conexao.prepareStatement(sql);
			preparedStatement.setString(1, estudante.getNome());
			preparedStatement.setString(2, estudante.getCurso());

			alterado = preparedStatement.executeUpdate();
			if (alterado > 0) {
				System.out.println("Estudante adicionado!");
				Thread.sleep(2000);
			} else {
				System.out.println("Falha ao adicionar estudante.");
				Thread.sleep(2000);
			}
		} catch (SQLException e) {

			e.printStackTrace();
			System.out.println("Tente novamente. Erro.");
			Thread.sleep(2000);
		} finally {
			preparedStatement.close();
		}
	}

	public void editarEstudante(int id, String novoNome, String novoCurso) throws SQLException {
		int alterado = 0;
		boolean editado = false;
		if (conexao != null) {
			PreparedStatement preparedStatementNome = null, preparedStatementCurso = null;
			try {
				if (novoNome != null) {
					String sqlNome = "UPDATE estudantes SET nome = ? WHERE id = ?";
					preparedStatementNome = conexao.prepareStatement(sqlNome);
					preparedStatementNome.setString(1, novoNome);
					preparedStatementNome.setInt(2, id);
					alterado = preparedStatementNome.executeUpdate();
					if (alterado > 0) {
						editado = true;
					} else if (alterado == 0) {
						System.out.println("Estudante não encontrado ou falha ao editar estudante.");

					}

				}
				if (novoCurso != null) {
					String sqlCurso = "UPDATE estudantes SET curso = ? WHERE id = ?";
					preparedStatementCurso = conexao.prepareStatement(sqlCurso);
					preparedStatementCurso.setString(1, novoCurso);
					preparedStatementCurso.setInt(2, id);
					alterado = preparedStatementCurso.executeUpdate();
					if (alterado > 0) {
						editado = true;
					} else if (alterado == 0) {
						System.out.println("Estudante não encontrado ou falha ao editar estudante.");

					}
				}
				if (editado) {
					System.out.println("Estudante editado com sucesso!");
				}
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("Tente novamente. Erro.");
			} finally {
				if (preparedStatementNome != null) {
					try {
						preparedStatementNome.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				if (preparedStatementCurso != null) {
					try {
						preparedStatementCurso.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}

		}
	}

	public void removerEstudante(int id) throws InterruptedException, SQLException {
		PreparedStatement preparedStatement = null;
		if (conexao != null) {
			try {
				String sql = "DELETE FROM estudantes WHERE id = ?";
				preparedStatement = conexao.prepareStatement(sql);
				preparedStatement.setInt(1, id);

				alterado = preparedStatement.executeUpdate();

				if (alterado > 0) {
					System.out.println("Estudante removido com sucesso!");
					Thread.sleep(2000);
				} else if (alterado == 0) {
					System.out.println("Estudante não encontrado ou falha ao remover estudante.");
					Thread.sleep(2000);
				}

			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("Erro ao remover estudante.");
				Thread.sleep(2000);
			} finally {
				preparedStatement.close();
			}
		}

	}

	public boolean verificarExistencia(int idEstudante) throws InterruptedException, SQLException {
		PreparedStatement preparedStatement = null;
		if (conexao != null) {
			try {
				String sql = "SELECT id FROM estudantes WHERE id = ?";
				preparedStatement = conexao.prepareStatement(sql);
				preparedStatement.setInt(1, idEstudante);
				ResultSet resultSet = preparedStatement.executeQuery();

				// Se houver pelo menos uma linha no resultado, o estudante existe
				if (resultSet.next()) {
					return true;
				}

				resultSet.close();
				preparedStatement.close();
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("Erro ao verificar a existência do estudante.");
				Thread.sleep(2000);
			} finally {
				preparedStatement.close();
			}
		}
		return false;
	}

	public List<Estudante> listarEstudantes() throws InterruptedException, SQLException {
		List<Estudante> estudantes = new ArrayList<>();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		if (conexao != null) {
			try {
				String sql = "SELECT id, nome, curso FROM estudantes";
				preparedStatement = conexao.prepareStatement(sql);
				resultSet = preparedStatement.executeQuery();

				while (resultSet.next()) {
					int id = resultSet.getInt("id");
					String nome = resultSet.getString("nome");
					String curso = resultSet.getString("curso");

					Estudante estudante = new Estudante(id, nome, curso);
					estudantes.add(estudante);
				}

			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("Erro ao listar estudantes.");
				Thread.sleep(2000);
			} finally {
				preparedStatement.close();
				resultSet.close();
			}

		}

		return estudantes;
	}
}