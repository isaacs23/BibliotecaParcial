package biblioteca.patrones;

import biblioteca.modelo.Equipo;
import biblioteca.modelo.Recurso;

public class CreadorEquipo extends CreadorRecurso {
    @Override
    public Recurso crearRecurso(String id, String titulo, int copias, String marca) {
        return new Equipo(id, titulo, copias, marca);
    }
}
