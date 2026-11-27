package biblioteca.patrones;

import biblioteca.modelo.Recurso;
import java.util.ArrayList;

public class CatalogoRecursos {
    private static CatalogoRecursos instancia;
    private ArrayList<Recurso> recursos;

    private CatalogoRecursos() {
        recursos = new ArrayList<>();
    }

    public static CatalogoRecursos getInstancia() {
        if (instancia == null) {
            instancia = new CatalogoRecursos();
        }
        return instancia;
    }

    public void agregar(Recurso recurso) {
        if (buscarPorId(recurso.getId()) != null) {
            throw new RuntimeException("Ya existe un recurso con el ID: " + recurso.getId());
        }
        recursos.add(recurso);
    }

    public void eliminar(Recurso recurso) {
        recursos.remove(recurso);
    }

    public Recurso buscarPorId(String id) {
        for (Recurso r : recursos) {
            if (r.getId().equalsIgnoreCase(id)) {
                return r;
            }
        }
        return null;
    }

    public ArrayList<Recurso> buscar(String texto) {
        ArrayList<Recurso> resultado = new ArrayList<>();
        String filtro = texto.toLowerCase();
        for (Recurso r : recursos) {
            if (r.getTitulo().toLowerCase().contains(filtro)
                    || r.getId().toLowerCase().contains(filtro)
                    || r.getTipo().toLowerCase().contains(filtro)) {
                resultado.add(r);
            }
        }
        return resultado;
    }

    public ArrayList<Recurso> getTodos() {
        return recursos;
    }
}
