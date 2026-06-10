package model;

public class Usuario {
    private String nome;
    private String cpf;
    private Item[] itensEmprestados;
    private int qtdEmprestados;

    public Usuario(String nome, String cpf) {
     this.nome = nome;
        this.cpf = cpf;
        this.itensEmprestados = new Item[4];
        this.qtdEmprestados = 0;
    }

    public void adicionarEmprestimo(Item item){
        if (qtdEmprestados == itensEmprestados.length) {
            int novoTamanho = (int) (itensEmprestados.length * 1.5);
            Item[] novoArray = new Item[novoTamanho];
            for (int i = 0; i < qtdEmprestados; i++) {
                novoArray[i] = itensEmprestados[i];
            }
            itensEmprestados = novoArray;
        }
        itensEmprestados[qtdEmprestados] = item;
        qtdEmprestados++;
    }

     public void removerEmprestimo(Item item){
        for (int i = 0; i < qtdEmprestados; i++) {
            if (itensEmprestados[i] == item) {
                for (int j = i; j < qtdEmprestados - 1; j++) {
                    itensEmprestados[j] = itensEmprestados[j + 1];
                }
                itensEmprestados[qtdEmprestados - 1] = null;
                qtdEmprestados--;
                return;
            }
        }
    }

    public void exibirEmprestimos() {
        if (qtdEmprestados == 0) {
            System.out.println("Nenhum item emprestado.");
            return;
        }
         for (int i = 0; i < qtdEmprestados; i++) {
            System.out.println(" - " + itensEmprestados[i].getNome());
        }
    }

    public String getNome() {
        return nome;
    }
    public String getCpf() {
        return cpf;
    }

    
}
