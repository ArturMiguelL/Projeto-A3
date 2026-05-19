package model;

import interfaces.Emprestavel;

public class Livro extends Item implements Emprestavel {
    private int numeroDePaginas;
    private boolean disponivel = true;
    private Usuario usuarioAtual;

    public Livro() {
        super();
    }

    public Livro(String nome,  String genero, String status, String descricao, int numeroDePaginas) {
        super(nome, genero, status, descricao);
        this.numeroDePaginas = numeroDePaginas;
    }

    public Livro(int id) {
        super(id);
    }

    @Override
public void emprestar(Usuario usuario) {
    if (!disponivel) {
        System.out.println("Livro já está emprestado.");
        return;
    }
    this.usuarioAtual = usuario;
    this.disponivel = false;
    setStatus("Emprestado"); 
}

   @Override
public void devolver() {
    this.usuarioAtual = null;
    this.disponivel = true;
    setStatus("Disponivel"); 
}

    @Override
    public boolean estaDisponivel() {
        return disponivel;
    }

    @Override
    public void exibirDetalhes() {
        System.out.println("ID: " + getId());
        System.out.println("Nome: " + getNome());       
        System.out.println("Gênero: " + getGenero());
        System.out.println("Descrição: " + getDescricao());
        System.out.println("Páginas: " + numeroDePaginas);
        System.out.println("Status: " + getStatus());
    }

    public int getNumeroDePaginas() { return numeroDePaginas; }
    public void setNumeroDePaginas(int n) { this.numeroDePaginas = n; }
}