package biblioteca.patrones;

import biblioteca.modelo.Recurso;
import biblioteca.modelo.Revista;

public class CreadorRevista extends CreadorRecurso {
    @Override
    public Recurso crearRecurso(String id, String titulo, int copias, String edicion) {
        return new Revista(id, titulo, copias, edicion);
    }
}
