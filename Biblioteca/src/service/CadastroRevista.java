package service;

import java.util.Scanner;
import model.Revista;

public class CadastroRevista {
    private Scanner scanner = new Scanner(System.in);

    public Revista coletarDados() {
        System.out.print("Nome: ");
        String nome = scanner.nextLine();


        System.out.print("Gênero: ");
        String genero = scanner.nextLine();

        System.out.print("Editora: ");
        String editora = scanner.nextLine();

        System.out.print("Descrição: ");
        String descricao = scanner.nextLine();

        System.out.print("Número da Edição: ");
        int edicao = scanner.nextInt();
        scanner.nextLine();

        return new Revista(nome, genero, "Somente no local", descricao, edicao, editora);
    }
}