package biblioteca.patrones;

import biblioteca.modelo.Libro;
import biblioteca.modelo.Recurso;

public class CreadorLibro extends CreadorRecurso {
    @Override
    public Recurso crearRecurso(String id, String titulo, int copias, String autor) {
        return new Libro(id, titulo, copias, autor);
    }
}
