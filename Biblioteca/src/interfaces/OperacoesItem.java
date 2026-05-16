package interfaces;
import model.Item;

public interface OperacoesItem {
    public boolean inserir(Item item);
    public Item pesquisar(Item item);
    public boolean remover(int id);
    public boolean atualizar(int id, String novoValor);
}
    
