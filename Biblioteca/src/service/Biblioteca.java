package service;

import interfaces.Emprestavel;
import interfaces.OperacoesItem;
import model.Item;
import model.Usuario;

public class Biblioteca implements OperacoesItem {

    private Repositorio repositorio = new Repositorio();

    private Usuario[] usuarios = new Usuario[4];
    private int qtdUsuarios = 0;


    @Override
    public boolean inserir(Item item) {
        return repositorio.inserir(item);
    }

    @Override
    public Item pesquisar(Item item) {
        return repositorio.pesquisar(item);
    }

    @Override
    public boolean remover(int id) {
        return repositorio.remover(id);
    }

    @Override
    public boolean atualizar(int id, String novoValor) {
        return repositorio.atualizar(id, novoValor);
    }

    public void listarItens() {
        if (repositorio.getQuantidade() == 0) {
            System.out.println("Nenhum item cadastrado.");
            return;
        }
        for (int i = 0; i < repositorio.getQuantidade(); i++) {
            System.out.println("---------------------");
            repositorio.getItens()[i].exibirDetalhes();
            System.out.println("---------------------");
        }
    }

    

    public void adicionarUsuario(Usuario usuario) {
        for (int i = 0; i < qtdUsuarios; i++) {
            if (usuarios[i].getCpf() == usuario.getCpf()) {
                System.out.println("Usuário já cadastrado.");
                return;
            }
        }
        
        if (qtdUsuarios == usuarios.length) {
            int novoTamanho = (int)(usuarios.length * 1.5);
            Usuario[] novoArray = new Usuario[novoTamanho];
            for (int i = 0; i < qtdUsuarios; i++) novoArray[i] = usuarios[i];
            usuarios = novoArray;
        }
        usuarios[qtdUsuarios] = usuario;
        qtdUsuarios++;
        System.out.println("Usuário cadastrado com sucesso.");
    }

    public Usuario buscarUsuario(int cpf) {
        for (int i = 0; i < qtdUsuarios; i++) {
            if (usuarios[i].getCpf() == cpf) return usuarios[i];
        }
        return null;
    }


    public void realizarEmprestimo(int itemId, int cpfUsuario) {
        Item itemBusca = new model.Livro(itemId); // construtor só com id
        Item item = repositorio.pesquisar(itemBusca);
        Usuario usuario = buscarUsuario(cpfUsuario);

        if (item == null) { System.out.println("Item não encontrado."); return; }
        if (usuario == null) { System.out.println("Usuário não encontrado."); return; }
        if (!(item instanceof Emprestavel)) { System.out.println("Este item não pode ser emprestado."); return; }

        Emprestavel emprestavel = (Emprestavel) item;
        if (!emprestavel.estaDisponivel()) { System.out.println("Item indisponível."); return; }

        emprestavel.emprestar(usuario);
        usuario.adicionarEmprestimo(item);
        System.out.println("Empréstimo realizado: " + item.getNome() + " -> " + usuario.getNome());
    }


    public void realizarDevolucao(int itemId, int cpfUsuario) {
        Item itemBusca = new model.Livro(itemId);
        Item item = repositorio.pesquisar(itemBusca);
        Usuario usuario = buscarUsuario(cpfUsuario);

        if (item == null || usuario == null) { System.out.println("Item ou usuário não encontrado."); return; }
        if (!(item instanceof Emprestavel)) { System.out.println("Este item não suporta devolução."); return; }

        ((Emprestavel) item).devolver();
        usuario.removerEmprestimo(item);
        System.out.println("Devolução registrada: " + item.getNome());
    }
}