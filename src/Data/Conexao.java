package Data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

	public static Connection obterConexao() throws InterruptedException {

		String url = "jdbc:postgresql://localhost:5432/estudante";
		String usuario = "postgres";
		String senha = "123456";

		Connection conexao = null;

		try {
			conexao = DriverManager.getConnection(url, usuario, senha);
			System.out.println("A conexão com o banco de dados foi estabelecida!");
			Thread.sleep(2000);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Não foi possível estabelecer a conexao com o banco de dados!");
			Thread.sleep(2000);
		}

		return conexao;
	}
}
