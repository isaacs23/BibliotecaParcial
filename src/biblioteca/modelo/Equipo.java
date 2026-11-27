package biblioteca.modelo;

public class Equipo extends Recurso {
    private String marca;

    public Equipo(String id, String titulo, int copias, String marca) {
        super(id, titulo, "Equipo", copias, 3, 1);
        this.marca = marca;
    }

    @Override
    public String getDatoExtra() {
        return marca;
    }

    @Override
    protected void actualizarDatoExtra(String nuevoDatoExtra) {
        this.marca = nuevoDatoExtra;
    }

    @Override
    public String getDetalle() {
        return "Marca: " + marca;
    }
}
