import javax.swing.*;
import model.Livro;
import model.Revista;
import model.Usuario;
import service.*;

public class Main {

    static Biblioteca biblioteca = new Biblioteca();

    public static void main(String[] args) {
        String[] opcoes = {
            "Adicionar Livro",
            "Adicionar Revista",
            "Listar Tudo",
            "Pesquisar por ID",
            "Atualizar Nome",
            "Remover por ID",
            "Cadastrar Usuário",
            "Realizar Empréstimo",
            "Devolver Item",
            "Sair"
        };

        while (true) {
            int escolha = JOptionPane.showOptionDialog(
                null,
                "Selecione uma operação:",
                "Biblioteca",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null, opcoes, opcoes[0]
            );

            if (escolha == -1 || escolha == 9) {
                JOptionPane.showMessageDialog(null, "Encerrando...");
                break;
            }

            switch (escolha) {
                case 0 -> adicionarLivro();
                case 1 -> adicionarRevista();
                case 2 -> listarTudo();
                case 3 -> pesquisar();
                case 4 -> atualizarNome();
                case 5 -> remover();
                case 6 -> cadastrarUsuario();
                case 7 -> realizarEmprestimo();
                case 8 -> realizarDevolucao();
            }
        }
    }

    static void adicionarLivro() {
        try {
            String nome = JOptionPane.showInputDialog("Nome do livro:");
            if (nome == null) return;         
            String genero = JOptionPane.showInputDialog("Gênero:");
            String descricao = JOptionPane.showInputDialog("Descrição:");
            int paginas = Integer.parseInt(JOptionPane.showInputDialog("Número de páginas:"));

            Livro livro = new Livro(nome,genero, "Disponivel", descricao, paginas);
            biblioteca.inserir(livro);
            JOptionPane.showMessageDialog(null, "Livro inserido! ID: " + livro.getId());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Valor inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    static void adicionarRevista() {
        try {
            String nome = JOptionPane.showInputDialog("Nome da revista:");
            if (nome == null) return;           
            String genero = JOptionPane.showInputDialog("Gênero:");
            String editora = JOptionPane.showInputDialog("Editora:");
            String descricao = JOptionPane.showInputDialog("Descrição:");
            int edicao = Integer.parseInt(JOptionPane.showInputDialog("Número da edição:"));

            Revista revista = new Revista(nome, genero, "Disponivel", descricao, edicao, editora);
            biblioteca.inserir(revista);
            JOptionPane.showMessageDialog(null, "Revista inserida! ID: " + revista.getId());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Valor inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    static void listarTudo() {
        biblioteca.listarItens();
        JOptionPane.showMessageDialog(null, "Listagem exibida no console.");
    }

    static void pesquisar() {
        try {
            int id = Integer.parseInt(JOptionPane.showInputDialog("Digite o ID:"));
            model.Item resultado = biblioteca.pesquisar(new Livro(id));

            if (resultado == null) {
                JOptionPane.showMessageDialog(null, "Item não encontrado.");
            } else {
                String info = "ID: " + resultado.getId() + "\n"
                    + "Nome: " + resultado.getNome() + "\n"
                    + "Gênero: " + resultado.getGenero() + "\n"                  
                    + "Descrição: " + resultado.getDescricao() + "\n"
                    + "Status: " + resultado.getStatus();
                JOptionPane.showMessageDialog(null, info, "Resultado", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "ID inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    static void atualizarNome() {
        try {
            int id = Integer.parseInt(JOptionPane.showInputDialog("ID do item a atualizar:"));
            String novoNome = JOptionPane.showInputDialog("Novo nome:");
            if (novoNome == null) return;

            boolean ok = biblioteca.atualizar(id, novoNome);
            JOptionPane.showMessageDialog(null, ok ? "Nome atualizado!" : "Item não encontrado.");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "ID inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    static void remover() {
        try {
            int id = Integer.parseInt(JOptionPane.showInputDialog("ID do item a remover:"));
            boolean ok = biblioteca.remover(id);
            JOptionPane.showMessageDialog(null, ok ? "Item removido!" : "tem não encontrado.");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, " ID inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    static void cadastrarUsuario() {
        try {
            String nome = JOptionPane.showInputDialog("Nome do usuário:");
            if (nome == null) return;
            int cpf = Integer.parseInt(JOptionPane.showInputDialog("CPF (só números):"));
            biblioteca.adicionarUsuario(new Usuario(nome, cpf));
            JOptionPane.showMessageDialog(null, "Usuário cadastrado!");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "CPF inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    static void realizarEmprestimo() {
        try {
            int id = Integer.parseInt(JOptionPane.showInputDialog("ID do item:"));
            int cpf = Integer.parseInt(JOptionPane.showInputDialog("CPF do usuário:"));
            biblioteca.realizarEmprestimo(id, cpf);
            JOptionPane.showMessageDialog(null, "Operação concluída.");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Valor inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    static void realizarDevolucao() {
        try {
            int id = Integer.parseInt(JOptionPane.showInputDialog("ID do item:"));
            int cpf = Integer.parseInt(JOptionPane.showInputDialog("CPF do usuário:"));
            biblioteca.realizarDevolucao(id, cpf);
            JOptionPane.showMessageDialog(null, "Operação concluída.");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Valor inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}