package biblioteca.patrones;

import biblioteca.modelo.Recurso;

public abstract class CreadorRecurso {
    public abstract Recurso crearRecurso(String id, String titulo, int copias, String datoExtra);
}
