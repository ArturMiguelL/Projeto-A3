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

            if (escolha == -1 || escolha == 10) {
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
                case 7 -> listarUsuarios();
                case 8 -> realizarEmprestimo();
                case 9 -> realizarDevolucao();
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
            "8. Listar Usuários",
            "── EMPRÉSTIMOS ──",
            "9. Realizar Empréstimo",
            "10. Devolver Item",
            "0. Sair"
        };

        JList<String> lista = new JList<>(opcoes);
        lista.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        lista.setSelectedIndex(1);
        lista.setVisibleRowCount(opcoes.length);

        panel.add(new JScrollPane(lista));

        int resultado = JOptionPane.showConfirmDialog(
            null, panel, "Sistema de Biblioteca",
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE
        );

        if (resultado != JOptionPane.OK_OPTION) return -1;

        String selecionado = lista.getSelectedValue();
        if (selecionado == null || selecionado.startsWith("──")) return mostrarMenu();

         if (selecionado.startsWith("1."))  return 0;
        if (selecionado.startsWith("2."))  return 1;
        if (selecionado.startsWith("3."))  return 2;
        if (selecionado.startsWith("4."))  return 3;
        if (selecionado.startsWith("5."))  return 4;
        if (selecionado.startsWith("6."))  return 5;
        if (selecionado.startsWith("7."))  return 6;
        if (selecionado.startsWith("8."))  return 7;
        if (selecionado.startsWith("9."))  return 8;
        if (selecionado.startsWith("10.")) return 9;
        return -1;
    }

    static String lerTexto(String titulo, String mensagem) {
        while (true) {
            String valor = JOptionPane.showInputDialog(null, mensagem, titulo,
                    JOptionPane.PLAIN_MESSAGE);
            if (valor == null) return null; 
            if (!valor.trim().isEmpty()) return valor.trim();
            JOptionPane.showMessageDialog(null,
                "⚠️ O campo não pode estar vazio. Tente novamente.",
                "Campo obrigatório", JOptionPane.WARNING_MESSAGE);
        }
    }
 
    static Integer lerInteiro(String titulo, String mensagem) {
        while (true) {
            String valor = JOptionPane.showInputDialog(null, mensagem, titulo,
                    JOptionPane.PLAIN_MESSAGE);
            if (valor == null) return null; 
            try {
                return Integer.parseInt(valor.trim());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null,
                    "Valor inválido. Digite apenas números inteiros.",
                    "Entrada inválida", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
 
    static String lerCpf() {
        while (true) {
            String valor = JOptionPane.showInputDialog(null,
                "CPF (somente 11 dígitos numéricos):",
                "Cadastrar Usuário", JOptionPane.PLAIN_MESSAGE);
            if (valor == null) return null; 
 
            String cpfLimpo = valor.trim();
            if (cpfLimpo.matches("\\d{11}")) {
                return cpfLimpo;
            }
            JOptionPane.showMessageDialog(null,
                "CPF inválido!\nDigite exatamente 11 dígitos numéricos (sem pontos ou traços).",
                "CPF inválido", JOptionPane.ERROR_MESSAGE);
        }
    }




   static void adicionarLivro() {
        String nome = lerTexto("Adicionar Livro", "Nome do livro:");
        if (nome == null) return;
 
        String genero = lerTexto("Adicionar Livro", "Gênero:");
        if (genero == null) return;
 
        String descricao = lerTexto("Adicionar Livro", "Descrição:");
        if (descricao == null) return;
 
        Integer paginas = lerInteiro("Adicionar Livro", "Número de páginas:");
        if (paginas == null) return;
 
        Livro livro = new Livro(nome, genero, "Disponível", descricao, paginas);
        biblioteca.inserir(livro);
        JOptionPane.showMessageDialog(null,
            "Livro inserido com sucesso!\nID atribuído: " + livro.getId(),
            "Livro Adicionado", JOptionPane.INFORMATION_MESSAGE);
    }

     static void adicionarRevista() {
        String nome = lerTexto("Adicionar Revista", "Nome da revista:");
        if (nome == null) return;
 
        String genero = lerTexto("Adicionar Revista", "Gênero:");
        if (genero == null) return;
 
        String editora = lerTexto("Adicionar Revista", "Editora:");
        if (editora == null) return;
 
        String descricao = lerTexto("Adicionar Revista", "Descrição:");
        if (descricao == null) return;
 
        Integer edicao = lerInteiro("Adicionar Revista", "Número da edição:");
        if (edicao == null) return;
 
        Revista revista = new Revista(nome, genero, "Somente no local", descricao, edicao, editora);
        biblioteca.inserir(revista);
        JOptionPane.showMessageDialog(null,
            "Revista inserida com sucesso!\nID atribuído: " + revista.getId(),
            "Revista Adicionada", JOptionPane.INFORMATION_MESSAGE);
    }

     static void listarTudo() {
        if (biblioteca.getRepositorio().getQuantidade() == 0) {
            JOptionPane.showMessageDialog(null,
                "Nenhum item cadastrado no acervo.",
                "Acervo vazio", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        StringBuilder sb = new StringBuilder();
        model.Item[] itens = biblioteca.getRepositorio().getItens();
        int qtd = biblioteca.getRepositorio().getQuantidade();

         sb.append(String.format("%-6s %-30s %-15s %-12s%n",
            "ID", "Nome", "Gênero", "Status"));
        sb.append("─".repeat(65)).append("\n");
         
         for (int i = 0; i < qtd; i++) {
            model.Item item = itens[i];
            sb.append(String.format("%-6d %-30s %-15s %-12s%n",
                item.getId(),
                item.getNome(),
                item.getGenero(),
                item.getStatus()));
        }

       JTextArea textArea = new JTextArea(sb.toString());
        textArea.setEditable(false);
        textArea.setFont(new java.awt.Font("Monospaced", java.awt.Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new java.awt.Dimension(600, 300));
 
        JOptionPane.showMessageDialog(null, scrollPane,
            "Acervo da Biblioteca (" + qtd + " item(ns))",
            JOptionPane.INFORMATION_MESSAGE);
    }

    static void pesquisar() {
        Integer id = lerInteiro("Pesquisar Item", "Digite o ID do item:");
        if (id == null) return;
 
        model.Item resultado = biblioteca.pesquisar(new Livro(id));
 
        if (resultado == null) {
            JOptionPane.showMessageDialog(null,
                "Nenhum item encontrado com o ID: " + id,
                "Não encontrado", JOptionPane.WARNING_MESSAGE);
        } else {
            String info = "ID:       " + resultado.getId() + "\n"
                + "Nome:     " + resultado.getNome() + "\n"
                + "Gênero:   " + resultado.getGenero() + "\n"
                + "Descrição: " + resultado.getDescricao() + "\n"
                + "Status:   " + resultado.getStatus();
            JOptionPane.showMessageDialog(null, info,
                "Resultado da Pesquisa", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    static void atualizarNome() {
        Integer id = lerInteiro("Atualizar Nome", "ID do item a atualizar:");
        if (id == null) return;
 
        String novoNome = lerTexto("Atualizar Nome", "Novo nome:");
        if (novoNome == null) return;
 
        boolean ok = biblioteca.atualizar(id, novoNome);
        if (ok) {
            JOptionPane.showMessageDialog(null, "Nome atualizado com sucesso!");
        } else {
            JOptionPane.showMessageDialog(null,
                "Item com ID " + id + " não encontrado.",
                "Não encontrado", JOptionPane.WARNING_MESSAGE);
        }
    }

    static void remover() {
        Integer id = lerInteiro("Remover Item", "ID do item a remover:");
        if (id == null) return;
 
        int confirm = JOptionPane.showConfirmDialog(null,
            "Tem certeza que deseja remover o item de ID " + id + "?",
            "Confirmar Remoção", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
 
        if (confirm != JOptionPane.YES_OPTION) return;
 
        boolean ok = biblioteca.remover(id);
        if (ok) {
            JOptionPane.showMessageDialog(null, "Item removido com sucesso!");
        } else {
            JOptionPane.showMessageDialog(null,
                "Item com ID " + id + " não encontrado.",
                "Não encontrado", JOptionPane.WARNING_MESSAGE);
        }
    }

   static void cadastrarUsuario() {
        String nome = lerTexto("Cadastrar Usuário", "Nome do usuário:");
        if (nome == null) return;
 
        String cpf = lerCpf();
        if (cpf == null) return;
 
        biblioteca.adicionarUsuario(new Usuario(nome, cpf));
        JOptionPane.showMessageDialog(null,
            "Usuário cadastrado com sucesso!\nNome: " + nome + "\nCPF: " + cpf,
            "Usuário Cadastrado", JOptionPane.INFORMATION_MESSAGE);
    }
    
 
    static void listarUsuarios() {
        Usuario[] usuarios = biblioteca.getUsuarios();
        int qtd = biblioteca.getQtdUsuarios();
 
        if (qtd == 0) {
            JOptionPane.showMessageDialog(null,
                "Nenhum usuário cadastrado.",
                "Lista vazia", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
 
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-30s %-15s%n", "Nome", "CPF"));
        sb.append("─".repeat(47)).append("\n");
 
        for (int i = 0; i < qtd; i++) {
            sb.append(String.format("%-30s %-15s%n", usuarios[i].getNome(), usuarios[i].getCpf()));
        }
 
        JTextArea textArea = new JTextArea(sb.toString());
        textArea.setEditable(false);
        textArea.setFont(new java.awt.Font("Monospaced", java.awt.Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new java.awt.Dimension(450, 250));
 
        JOptionPane.showMessageDialog(null, scrollPane,
            "Usuários Cadastrados (" + qtd + ")",
            JOptionPane.INFORMATION_MESSAGE);
    }

    static void realizarEmprestimo() {
        Integer id = lerInteiro("Empréstimo", "ID do item:");
        if (id == null) return; 
        String cpf = lerCpf();
        if (cpf == null) return; 
        boolean ok = biblioteca.realizarEmprestimo(id, cpf);
        if (ok) {
            JOptionPane.showMessageDialog(null,
                "Empréstimo realizado com sucesso!",
                "Empréstimo", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null,
                "Não foi possível realizar o empréstimo.\n\n"
                + "Verifique se:\n"
                + "  • O ID do item existe no acervo\n"
                + "  • O item está com status 'Disponível'\n"
                + "  • O CPF está cadastrado no sistema",
                "Erro no Empréstimo", JOptionPane.ERROR_MESSAGE);
        }
    }

     static void realizarDevolucao() {
        Integer id = lerInteiro("Devolução", "ID do item:");
        if (id == null) return;
 
        String cpf = lerCpf();
        if (cpf == null) return;
 
        boolean ok = biblioteca.realizarDevolucao(id, cpf);
        if (ok) {
            JOptionPane.showMessageDialog(null,
                "Devolução registrada com sucesso!",
                "Devolução", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null,
                "Não foi possível registrar a devolução.\n\n"
                + "Verifique se:\n"
                + "  • O ID do item está correto\n"
                + "  • O CPF informado corresponde ao usuário que realizou o empréstimo",
                "Erro na Devolução", JOptionPane.ERROR_MESSAGE);
        }
    }
}
