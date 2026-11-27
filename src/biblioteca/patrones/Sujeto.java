package biblioteca.patrones;

public interface Sujeto {
    void agregarObservador(Observador observador);
    void notificarSiguiente();
}
