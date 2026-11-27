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
    private int vecesSolicitado;
    private int vecesUtilizado;
    private ArrayList<Observador> listaEspera;

    public Recurso(String id, String titulo, String tipo, int copias,
                   int plazoDias, int maxPorUsuario) {
        this.id = id;
        this.titulo = titulo;
        this.tipo = tipo;
        this.copiasTotales = copias;
        this.copiasDisponibles = copias;
        this.plazoDias = plazoDias;
        this.maxPorUsuario = maxPorUsuario;
        this.vecesSolicitado = 0;
        this.vecesUtilizado = 0;
        this.listaEspera = new ArrayList<>();
    }

    public boolean estaDisponible() {
        return copiasDisponibles > 0;
    }

    public void prestar() {
        copiasDisponibles--;
        vecesSolicitado++;
        vecesUtilizado++;
    }

    public void registrarSolicitud() {
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

    public void actualizarDatos(String nuevoTitulo, int nuevasCopiasTotales, String nuevoDatoExtra) {
        int prestadas = copiasTotales - copiasDisponibles;
        if (nuevasCopiasTotales < prestadas) {
            throw new RuntimeException("No puede poner menos copias que las actualmente prestadas (" + prestadas + ").");
        }
        this.titulo = nuevoTitulo;
        this.copiasTotales = nuevasCopiasTotales;
        this.copiasDisponibles = nuevasCopiasTotales - prestadas;
        actualizarDatoExtra(nuevoDatoExtra);
        if (estaDisponible() && !listaEspera.isEmpty()) {
            notificarSiguiente();
        }
    }

    @Override
    public void agregarObservador(Observador observador) {
        if (!listaEspera.contains(observador)) {
            listaEspera.add(observador);
            vecesSolicitado++;
        }
    }

    @Override
    public void notificarSiguiente() {
        if (!listaEspera.isEmpty()) {
            Observador primero = listaEspera.remove(0);
            primero.notificar("El recurso '" + titulo + "' (" + id + ") ya está disponible para préstamo.");
        }
    }

    public abstract String getDatoExtra();
    protected abstract void actualizarDatoExtra(String nuevoDatoExtra);
    public abstract String getDetalle();

    public String getId() { return id; }
    public String getTitulo() { return titulo; }
    public String getTipo() { return tipo; }
    public int getCopiasTotales() { return copiasTotales; }
    public int getCopiasDisponibles() { return copiasDisponibles; }
    public int getPlazoDias() { return plazoDias; }
    public int getMaxPorUsuario() { return maxPorUsuario; }
    public int getVecesSolicitado() { return vecesSolicitado; }
    public int getVecesUtilizado() { return vecesUtilizado; }
    public int getCantidadEnEspera() { return listaEspera.size(); }

    @Override
    public String toString() {
        String estado = estaDisponible() ? "DISPONIBLE" : "AGOTADO";
        return "[" + id + "] " + titulo + " (" + tipo + ")\n\t"
                + "Estado: " + estado + " (" + copiasDisponibles + "/" + copiasTotales + ")"
                + " | Plazo: " + plazoDias + "d | Max/Usuario: " + maxPorUsuario
                + " | Espera: " + listaEspera.size() + " | " + getDetalle();
    }
}
