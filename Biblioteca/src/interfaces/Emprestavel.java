package interfaces;

import model.Usuario;

public interface Emprestavel {
    void emprestar(Usuario usuario);
    void devolver();
    boolean estaDisponivel();
}
