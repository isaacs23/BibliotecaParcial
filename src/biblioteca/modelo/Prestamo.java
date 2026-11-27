package biblioteca.modelo;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Prestamo {
    private String idPrestamo;
    private Usuario usuario;
    private Recurso recurso;
    private LocalDate fechaPrestamo;
    private LocalDate fechaVencimiento;
    private double multa;
    private boolean activo;

    public Prestamo(String idPrestamo, Usuario usuario, Recurso recurso, LocalDate fechaPrestamo) {
        this.idPrestamo = idPrestamo;
        this.usuario = usuario;
        this.recurso = recurso;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaVencimiento = fechaPrestamo.plusDays(recurso.getPlazoDias());
        this.multa = 0;
        this.activo = true;
    }

    public long calcularDiasRetraso(LocalDate fechaActual) {
        if (!fechaActual.isAfter(fechaVencimiento)) {
            return 0;
        }
        return ChronoUnit.DAYS.between(fechaVencimiento, fechaActual);
    }

    public void cerrarDevolucion(double multaCobrada) {
        this.multa = multaCobrada;
        this.activo = false;
    }

    public String getIdPrestamo() { return idPrestamo; }
    public Usuario getUsuario() { return usuario; }
    public Recurso getRecurso() { return recurso; }
    public LocalDate getFechaVencimiento() { return fechaVencimiento; }
    public double getMulta() { return multa; }
    public boolean isActivo() { return activo; }

    @Override
    public String toString() {
        String estado = activo ? "ACTIVO (Vence: " + fechaVencimiento + ")" : "DEVUELTO";
        return idPrestamo + " | " + recurso.getTitulo() + " [" + recurso.getId() + "]"
                + " | " + estado + " | Multa: $" + multa;
    }
}
