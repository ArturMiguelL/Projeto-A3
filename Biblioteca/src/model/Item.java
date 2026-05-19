package model;

public abstract class Item {
     private static int contadorId = 0;

    private int id;
    private String nome;
    private String genero;   
    private String status;
    private String descricao;

    public Item() {
        this.id = ++contadorId;
    }

    public Item(String nome,  String genero, String status, String descricao) {
        this.id = ++contadorId;
        this.nome = nome;      
        this.genero = genero;
        this.status = status;
        this.descricao = descricao;
    }

    public abstract void exibirDetalhes();

    public Item(int id){
        this.id = id;
    }

    //get and set

    public static int getContadorId() {
        return contadorId;
    }

    public static void setContadorId(int contadorId) {
        Item.contadorId = contadorId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }
    

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
     
    
    
}