# Trabalho-POO-Grupo5
Trabalho final da disciplina de Programação orientada a objetos - SERRATEC

Crie um sistema de gerenciamento de estudantes.

Passo 1: Definir as Classes

● Crie uma classe principal chamada “SistemaGerenciamentoEstudantes” que
será responsável por iniciar o programa e interagir com o usuário pelo
console.

● Crie uma classe “Estudante” para representar os dados de cada estudante,
com atributos como nome, ID e curso, e métodos construtores, getters e
setters.

Passo 2: Implementar a Classe de Banco de Dados

● Crie uma classe “BancoDeDados” para lidar com a conexão e operações no
banco de dados, configurando a conexão no construtor da classe.

● Implemente métodos na classe “BancoDeDados” para adicionar, editar,
remover e listar estudantes no banco de dados.

Passo 3: Implementar o Menu do Usuário

● Crie uma classe “Menu” que interaja com o usuário pelo console, usando a
classe Scanner para ler as entradas do usuário e System.out.println para
imprimir as saídas.

● Implemente um menu de opções que permita ao usuário escolher entre
“Adicionar Estudante”, “Editar Estudante”, “Remover Estudante”, “Listar
Estudantes” e “Sair”.

● Para cada opção do menu, implemente métodos que usem as classes
“Estudante” e “BancoDeDados” para realizar as operações correspondentes.

Passo 4: Tratamento de Exceções

● Implemente tratamento de exceções nas operações de banco de dados,
capturando exceções como falhas na conexão ou consultas mal-sucedidas, e
fornecendo feedback adequado ao usuário em caso de erros.

Passo 5: Organização em Pacotes
● Organize seu código em pacotes, colocando a classe “Estudante” em um
pacote chamado “model”, a classe “BancoDeDados” em um pacote chamado
“data”, e a classe “Menu” em um pacote chamado “view”.
