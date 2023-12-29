package projeto;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import Data.BancoDeDados;
import Data.Conexao;
import View.Menu;

public class SistemaDeGerenciamentoDeEstudante {

	public static void main(String[] args) throws SQLException, InterruptedException {

		Connection conexao = Conexao.obterConexao();
		BancoDeDados bancoDeDados = new BancoDeDados(conexao);
		Scanner scanner = new Scanner(System.in);
		Menu menu = new Menu(bancoDeDados, scanner);

		bancoDeDados.criarTabelaEstudantes();

		menu.exibirMenu();

		conexao.close();

	}

}
