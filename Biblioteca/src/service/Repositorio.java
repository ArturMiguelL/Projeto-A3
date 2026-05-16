package service;

import interfaces.OperacoesItem;
import model.Item;

public class Repositorio implements OperacoesItem {
    private Item[] itens;
    private int quantidade;

    public Repositorio() {
        this.itens = new Item[4];
        this.quantidade = 0;
    }

    @Override
    public boolean inserir(Item item) {
        if (quantidade == itens.length) {
            redimensionar();
        }
        itens[quantidade] = item;
        quantidade++;
        return true;
    }

    @Override
    public Item pesquisar(Item item) {
        int indice = buscarIndice(item.getId());
        return indice >= 0 ? itens[indice] : null;
    }

    @Override
    public boolean remover(int id) {
        int indice = buscarIndice(id);
        if (indice < 0) return false;

        for (int i = indice; i < quantidade - 1; i++) {
            itens[i] = itens[i + 1];
        }
        itens[quantidade - 1] = null;
        quantidade--;
        return true;
    }

    @Override
    public boolean atualizar(int id, String novoValor) {
        int indice = buscarIndice(id);
        if (indice < 0) return false;
        itens[indice].setNome(novoValor);
        return true;
    }

    private int buscarIndice(int id) {
        for (int i = 0; i < quantidade; i++) {
            if (itens[i] != null && itens[i].getId() == id) {
                return i;
            }
        }
        return -1;
    }

    private void redimensionar() {
        int novoTamanho = (int) (itens.length * 1.5);
        Item[] novoArray = new Item[novoTamanho];
        for (int i = 0; i < quantidade; i++) {
            novoArray[i] = itens[i];
        }
        itens = novoArray;
    }

    public int getQuantidade() { return quantidade; }
    public Item[] getItens() { return itens; }
}