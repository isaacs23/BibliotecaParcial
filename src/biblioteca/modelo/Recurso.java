package biblioteca.modelo;

import biblioteca.patrones.Observador;
import biblioteca.patrones.Sujeto;
import java.util.ArrayList;

public abstract class Recurso implements Sujeto {
    private String id;
    private String titulo;
    private String tipo;
    private int copiasTotales;
    private int copiasDisponibles;
    private int plazoDias;
    private int maxPorUsuario;
    private int maxRenovaciones;
    private int vecesSolicitado;
    private ArrayList<Observador> listaEspera;

    public Recurso(String id, String titulo, String tipo, int copias,
                   int plazoDias, int maxPorUsuario, int maxRenovaciones) {
        this.id = id;
        this.titulo = titulo;
        this.tipo = tipo;
        this.copiasTotales = copias;
        this.copiasDisponibles = copias;
        this.plazoDias = plazoDias;
        this.maxPorUsuario = maxPorUsuario;
        this.maxRenovaciones = maxRenovaciones;
        this.vecesSolicitado = 0;
        this.listaEspera = new ArrayList<>();
    }

    public boolean estaDisponible() {
        return copiasDisponibles > 0;
    }

    public void prestar() {
        copiasDisponibles--;
        vecesSolicitado++;
    }

    public void registrarIntentoFallido() {
        vecesSolicitado++;
    }

    public void devolver() {
        if (copiasDisponibles < copiasTotales) {
            copiasDisponibles++;
        }
        if (estaDisponible() && !listaEspera.isEmpty()) {
            notificarSiguiente();
        }
    }

    @Override
    public void agregarObservador(Observador observador) {
        if (!listaEspera.contains(observador)) {
            listaEspera.add(observador);
        }
    }

    @Override
    public void notificarSiguiente() {
        if (!listaEspera.isEmpty()) {
            Observador primero = listaEspera.remove(0);
            primero.notificar("Ya hay una copia disponible de: " + titulo + " (" + id + ")");
        }
    }

    public abstract String getDetalle();

    public String getId() { return id; }
    public String getTitulo() { return titulo; }
    public String getTipo() { return tipo; }
    public int getCopiasTotales() { return copiasTotales; }
    public int getCopiasDisponibles() { return copiasDisponibles; }
    public int getPlazoDias() { return plazoDias; }
    public int getMaxPorUsuario() { return maxPorUsuario; }
    public int getMaxRenovaciones() { return maxRenovaciones; }
    public int getVecesSolicitado() { return vecesSolicitado; }
    public int getCantidadEnEspera() { return listaEspera.size(); }

    @Override
    public String toString() {
        return "[" + id + "] " + titulo + " (" + tipo + ") | Disp: "
                + copiasDisponibles + "/" + copiasTotales
                + " | Plazo: " + plazoDias + "d | Espera: " + listaEspera.size();
    }
}
