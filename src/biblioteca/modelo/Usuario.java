package biblioteca.modelo;

import biblioteca.patrones.Observador;
import java.util.ArrayList;

public class Usuario implements Observador {
    private String username;
    private String password;
    private String nombre;
    private boolean administrador;
    private ArrayList<String> notificaciones;

    public Usuario(String username, String password, String nombre, boolean administrador) {
        this.username = username;
        this.password = password;
        this.nombre = nombre;
        this.administrador = administrador;
        this.notificaciones = new ArrayList<>();
    }

    @Override
    public void notificar(String mensaje) {
        notificaciones.add(mensaje);
    }

    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getNombre() { return nombre; }
    public boolean esAdministrador() { return administrador; }
    public ArrayList<String> getNotificaciones() { return notificaciones; }

    @Override
    public String toString() {
        String rol = administrador ? "Administrador" : "Usuario";
        return nombre + " (" + username + " - " + rol + ")";
    }
}
