package model;

public class Revista extends Item {
    private int edicao;
    private String editora;

    public Revista() {
        super();
    }

    public Revista(String nome,  String genero, String status, String descricao, int edicao, String editora) {
        super(nome,  genero, status, descricao);
        this.edicao = edicao;
        this.editora = editora;
    }

    public Revista(int id) {
        super(id);
    }

    @Override
    public void exibirDetalhes() {
        System.out.println("ID: " + getId());
        System.out.println("Nome: " + getNome());
        System.out.println("Editora: " + editora);        
        System.out.println("Gênero: " + getGenero());
        System.out.println("Edição: " + edicao);
        System.out.println("Descrição: " + getDescricao());
        System.out.println("Status: Apenas consulta local");
    }

    public int getEdicao() { return edicao; }
    public void setEdicao(int edicao) { this.edicao = edicao; }

    public String getEditora() { return editora; }
    public void setEditora(String editora) { this.editora = editora; }
}