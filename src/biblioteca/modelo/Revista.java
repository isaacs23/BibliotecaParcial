package biblioteca.modelo;

public class Revista extends Recurso {
    private String edicion;

    public Revista(String id, String titulo, int copias, String edicion) {
        super(id, titulo, "Revista", copias, 7, 2);
        this.edicion = edicion;
    }

    @Override
    public String getDatoExtra() {
        return edicion;
    }

    @Override
    protected void actualizarDatoExtra(String nuevoDatoExtra) {
        this.edicion = nuevoDatoExtra;
    }

    @Override
    public String getDetalle() {
        return "Edición: " + edicion;
    }
}
