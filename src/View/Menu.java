package View;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Data.BancoDeDados;
import Model.Estudante;

public class Menu {

	private BancoDeDados bancoDeDados;
	private Scanner scanner;

	public Menu(BancoDeDados bancoDeDados, Scanner scanner) {
		super();
		this.bancoDeDados = bancoDeDados;
		this.scanner = scanner;
	}

	public void exibirMenu() throws InterruptedException {
		boolean continuar = true;

		System.out.println("Bem-vindo ao Sistema de Gerenciamento de Estudantes!\n");
		Thread.sleep(3000);
		while (continuar) {
			System.out.println("\nMenu de Opções:");
			System.out.println("1. Adicionar Estudante");
			System.out.println("2. Editar Estudante");
			System.out.println("3. Remover Estudante");
			System.out.println("4. Listar Estudantes");
			System.out.println("5. Sair");
			System.out.print("\nEscolha uma opção: ");
			String entrada = scanner.nextLine();

			int resposta = 0;

			try {
				int escolha = Integer.parseInt(entrada);
				switch (escolha) {
				case 1:
					chamarMetodo(resposta, () -> {
						try {
							adicionarEstudante();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					});
					break;
				case 2:
					chamarMetodo(resposta, () -> {
						try {
							editarEstudante();
						} catch (InterruptedException e) {
							e.printStackTrace();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					});
					break;
				case 3:
					chamarMetodo(resposta, () -> {
						try {
							removerEstudante();
						} catch (InterruptedException e) {
							e.printStackTrace();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					});
					break;
				case 4:
					chamarMetodo(resposta, () -> {
						try {
							listarEstudantes();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					});
					break;
				case 5:
					continuar = false;
					sair();
					break;
				default:
					System.out.println("Opção inválida.");
					Thread.sleep(2000);
				}

			} catch (Exception e) {
				System.out.println("\nErro: " + e.getMessage());
				Thread.sleep(3000);
			}

		}
		scanner.close();
	}

	private void chamarMetodo(int resposta, RealizarAcao acao) {
		acao.executarAcao();
		while (resposta == 1 || resposta == 2 || resposta == 0) {
			System.out.println("Continuar(1)  Voltar ao menu principal(2)");
			String resp = scanner.nextLine();
			try {
				int escolha = Integer.parseInt(resp);
				switch (escolha) {
				case 1:
					acao.executarAcao();
					break;

				case 2:
					resposta = 3;
					break;

				default:
					System.out.println("Opção inválida.");
				}
			} catch (Exception e) {
				System.out.println("Erro: " + e.getMessage());

			}

		}
	}

	private void adicionarEstudante() throws InterruptedException {
		try {
			Estudante estudante = new Estudante();

			System.out.println("Nome: ");
			estudante.setNome(scanner.nextLine());
			if (verificaSemNumeros(estudante.getNome()) || estudante.getNome().length() < 2) {
				System.out.println("Erro. Nome deve conter mais de um caractere e não deve conter número.");
				Thread.sleep(3000);
				return;
			}
			System.out.println("\nCurso: ");
			estudante.setCurso(scanner.nextLine());
			if (estudante.getCurso().length() == 0) {
				System.out.println("curso inválido!");
				Thread.sleep(2000);
				return;
			}
			bancoDeDados.adicionarEstudante(estudante);
		} catch (Exception e) {
			System.out.println("Erro ao adicionar estudante: " + e.getMessage());
			Thread.sleep(3000);
		}
	}

	private void editarEstudante() throws InterruptedException, SQLException {
		Estudante estudante = new Estudante();
		List<Estudante> estudantes = bancoDeDados.listarEstudantes();
		for (int i = 0; i < estudantes.size(); i++) {
			Estudante e = estudantes.get(i);
			if (i % 6 == 0 && i != 0) {
				System.out.println();
			}
			System.out.printf("(" + e.getId() + ")" + e.getNome() + " | ");
		}
		System.out.println("\n\nDigite o ID do estudante que deseja editar: ");
		String entrada = scanner.nextLine();

		try {
			int id = Integer.parseInt(entrada);
			if (bancoDeDados.verificarExistencia(id)) {
				System.out.println("O que você gostaria de editar?");
				System.out.println("1. Nome");
				System.out.println("2. Curso");
				System.out.println("3. Ambos");
				int opcao = scanner.nextInt();
				scanner.nextLine();
				if (opcao == 1) {
					System.out.println("Novo nome: ");
					String novoNome = scanner.nextLine();
					if (verificaSemNumeros(novoNome) || novoNome.length() < 2) {
						System.out.println("Erro. Nome deve conter mais de um caracter e não deve conter número.");
						Thread.sleep(2000);
						return;
					}
					estudante.setId(id); // Definindo o ID para o objeto estudante
					estudante.setNome(novoNome); // Definindo o nome para o objeto estudante
					bancoDeDados.editarEstudante(estudante.getId(), estudante.getNome(), null);
				} else if (opcao == 2) {
					System.out.println("Novo curso: ");
					String novoCurso = scanner.nextLine();
					if (novoCurso.length() == 0) {
						System.out.println("curso inválido!");
						Thread.sleep(2000);
						return;
					}
					estudante.setId(id);
					estudante.setCurso(novoCurso);
					bancoDeDados.editarEstudante(estudante.getId(), null, estudante.getCurso());
				} else if (opcao == 3) {
					System.out.println("Novo nome: ");
					String novoNome = scanner.nextLine();
					if (verificaSemNumeros(novoNome) || novoNome.length() < 2) {
						System.out.println("Erro. Nome deve conter mais de um caracter e não deve conter número.");
						Thread.sleep(2000);
						return;
					}
					System.out.println("Novo curso: ");
					String novoCurso = scanner.nextLine();
					if (novoCurso.length() == 0) {
						System.out.println("curso inválido!");
						Thread.sleep(2000);
						return;
					}
					estudante.setId(id);
					estudante.setNome(novoNome);
					estudante.setCurso(novoCurso);
					bancoDeDados.editarEstudante(estudante.getId(), estudante.getNome(), estudante.getCurso());
				} else {
					System.out.println("Opção inválida!");
					Thread.sleep(2000);
				}

			} else {
				System.out.println("Estudante não encontrado.");
				Thread.sleep(2000);
			}
		} catch (Exception e) {
			System.out.println("Erro ao editar estudante: " + e.getMessage());
			Thread.sleep(2000);
		}
	}

	private void removerEstudante() throws InterruptedException, SQLException {
		List<Estudante> estudantes = bancoDeDados.listarEstudantes();
		for (int i = 0; i < estudantes.size(); i++) {
			Estudante estudante = estudantes.get(i);
			if (i % 6 == 0 && i != 0) {
				System.out.println();
			}
			System.out.printf("(" + estudante.getId() + ")" + estudante.getNome() + " | ");
		}
		System.out.println("\n\nDigite o ID do estudante que deseja remover: ");
		String entrada = scanner.nextLine();
		try {
			int id = Integer.parseInt(entrada);
			bancoDeDados.removerEstudante(id);

		} catch (Exception e) {
			System.out.println("Erro ao remover estudante: " + e.getMessage());
			Thread.sleep(2000);
		}
	}

	private void listarEstudantes() throws InterruptedException {
		try {
			List<Estudante> estudantes = bancoDeDados.listarEstudantes();

			if (estudantes.isEmpty()) {
				System.out.println("Nenhum estudante encontrado.");
			} else {
				System.out.println("\nLista de estudantes:");
				System.out.println("\n/////////////////////////////////////////\n");
				for (Estudante estudante : estudantes) {
					System.out.println("ID: " + estudante.getId());
					System.out.println("Nome: " + estudante.getNome());
					System.out.println("Curso: " + estudante.getCurso());
					System.out.println();
					Thread.sleep(300);
				}
			}
			System.out.println("\n/////////////////////////////////////////\n");
		} catch (Exception e) {
			System.out.println("Erro ao listar estudantes: " + e.getMessage());
			Thread.sleep(2000);
		}
	}

	public static void sair() {
		System.out.println("Obrigado por usar o Sistema de Gerenciamento de Estudante!");
		System.out.println("Programa Finalizado!");
		System.exit(0);
	}

	public static boolean verificaSemNumeros(String texto) {
		// Use uma expressão regular para verificar se não há dígitos na string
		Pattern pattern = Pattern.compile(".*\\d.*");
		Matcher matcher = pattern.matcher(texto);
		return matcher.matches();
	}

}

interface RealizarAcao {
	void executarAcao();
}
