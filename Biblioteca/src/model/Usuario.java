package model;

import java.util.ArrayList;

public class Usuario {
    private String nome;
    private int cpf;
    private ArrayList<Item> itensEmprestados = new ArrayList<>();

    public Usuario(String nome, int cpf) {
        this.nome = nome;
        this.cpf = cpf;
    }

    public void adicionarEmprestimo(Item item){
        itensEmprestados.add(item);
    }

     public void removerEmprestimo(Item item){
        itensEmprestados.remove(item);
    }

    public void exibirEmprestimos() {
        if (itensEmprestados.isEmpty()) {
            System.out.println("Nenhum item emprestado.");
            return;
        }
        for (Item item : itensEmprestados) {
            System.out.println(" - " + item.getNome());
        }
    }

    public String getNome() {
        return nome;
    }
    public int getCpf() {
        return cpf;
    }

    
}
