package service;

import java.util.Scanner;
import model.Livro;

public class CadastroLivro {
    private Scanner scanner = new Scanner(System.in);

    public Livro coletarDados() {
        System.out.print("Nome: ");
        String nome = scanner.nextLine();

        System.out.print("Código: ");
        int codigo = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Gênero: ");
        String genero = scanner.nextLine();

        System.out.print("Descrição: ");
        String descricao = scanner.nextLine();

        System.out.print("Número de páginas: ");
        int paginas = scanner.nextInt();
        scanner.nextLine();

        return new Livro(nome, codigo, genero, "Disponivel", descricao, paginas);
    }
}