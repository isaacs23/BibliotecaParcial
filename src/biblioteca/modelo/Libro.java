package biblioteca.modelo;

public class Libro extends Recurso {
    private String autor;

    public Libro(String id, String titulo, int copias, String autor) {
        super(id, titulo, "Libro", copias, 14, 3);
        this.autor = autor;
    }

    @Override
    public String getDatoExtra() {
        return autor;
    }

    @Override
    protected void actualizarDatoExtra(String nuevoDatoExtra) {
        this.autor = nuevoDatoExtra;
    }

    @Override
    public String getDetalle() {
        return "Autor: " + autor;
    }
}
