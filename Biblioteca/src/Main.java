import javax.swing.*;
import model.Livro;
import model.Revista;
import model.Usuario;
import service.*;

public class Main {

    static Biblioteca biblioteca = new Biblioteca();

    public static void main(String[] args) {
        while (true) {
            int escolha = mostrarMenu();

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

    static int mostrarMenu() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        String[] opcoes = {
            "── ACERVO ──",
            "1. Adicionar Livro",
            "2. Adicionar Revista",
            "3. Listar Tudo",
            "4. Pesquisar por ID",
            "5. Atualizar Nome",
            "6. Remover por ID",
            "── USUÁRIOS ──",
            "7. Cadastrar Usuário",
            "── EMPRÉSTIMOS ──",
            "8. Realizar Empréstimo",
            "9. Devolver Item",
            "0. Sair"
        };

        JList<String> lista = new JList<>(opcoes);
        lista.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        lista.setSelectedIndex(1);
        lista.setVisibleRowCount(13);

        panel.add(new JScrollPane(lista));

        int resultado = JOptionPane.showConfirmDialog(
            null, panel, "Biblioteca",
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE
        );

        if (resultado != JOptionPane.OK_OPTION) return -1;

        String selecionado = lista.getSelectedValue();
        if (selecionado == null || selecionado.startsWith("──")) return mostrarMenu();

        if (selecionado.startsWith("1")) return 0;
        if (selecionado.startsWith("2")) return 1;
        if (selecionado.startsWith("3")) return 2;
        if (selecionado.startsWith("4")) return 3;
        if (selecionado.startsWith("5")) return 4;
        if (selecionado.startsWith("6")) return 5;
        if (selecionado.startsWith("7")) return 6;
        if (selecionado.startsWith("8")) return 7;
        if (selecionado.startsWith("9")) return 8;
        if (selecionado.startsWith("0")) return 9;

        return -1;
    }

    static void adicionarLivro() {
        try {
            String nome = JOptionPane.showInputDialog("Nome do livro:");
            if (nome == null) return;
            String genero = JOptionPane.showInputDialog("Gênero:");
            String descricao = JOptionPane.showInputDialog("Descrição:");
            int paginas = Integer.parseInt(JOptionPane.showInputDialog("Número de páginas:"));

            Livro livro = new Livro(nome, genero, "Disponivel", descricao, paginas);
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

            Revista revista = new Revista(nome,genero, "Disponivel", descricao, edicao, editora);
            biblioteca.inserir(revista);
            JOptionPane.showMessageDialog(null, "Revista inserida! ID: " + revista.getId());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Valor inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    static void listarTudo() {
        if (biblioteca.getRepositorio().getQuantidade() == 0) {
            JOptionPane.showMessageDialog(null, "Nenhum item cadastrado.");
            return;
        }

        StringBuilder sb = new StringBuilder();
        model.Item[] itens = biblioteca.getRepositorio().getItens();
        int qtd = biblioteca.getRepositorio().getQuantidade();

        for (int i = 0; i < qtd; i++) {
            model.Item item = itens[i];
            sb.append("ID: ").append(item.getId())
              .append(" | Nome: ").append(item.getNome())
              .append(" | Genero: ").append(item.getGenero())
              .append(" | Status: ").append(item.getStatus())
              .append("\n");
        }

        JOptionPane.showMessageDialog(null, sb.toString(), "Todos os Itens", JOptionPane.INFORMATION_MESSAGE);
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
                    + "Genero: " + resultado.getGenero() + "\n"
                    + "Descricao: " + resultado.getDescricao() + "\n"
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
            JOptionPane.showMessageDialog(null, ok ? "Item removido!" : "Item não encontrado.");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "ID inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
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
            boolean ok = biblioteca.realizarEmprestimo(id, cpf);
            if (ok) {
                JOptionPane.showMessageDialog(null, "Empréstimo realizado!");
            } else {
                JOptionPane.showMessageDialog(null, "Não foi possível realizar o empréstimo.\nVerifique se o item existe, está disponível e o usuário está cadastrado.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Valor inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    static void realizarDevolucao() {
        try {
            int id = Integer.parseInt(JOptionPane.showInputDialog("ID do item:"));
            int cpf = Integer.parseInt(JOptionPane.showInputDialog("CPF do usuário:"));
            boolean ok = biblioteca.realizarDevolucao(id, cpf);
            if (ok) {
                JOptionPane.showMessageDialog(null, "Devolução registrada!");
            } else {
                JOptionPane.showMessageDialog(null, "Não foi possível realizar a devolução.\nVerifique o ID do item e o CPF do usuário.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Valor inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
}
}