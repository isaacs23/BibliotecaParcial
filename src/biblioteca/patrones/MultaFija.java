package biblioteca.patrones;

public class MultaFija implements PoliticaMulta {
    private double tarifaDiaria;

    public MultaFija(double tarifaDiaria) {
        this.tarifaDiaria = tarifaDiaria;
    }

    @Override
    public double calcularMulta(long diasRetraso) {
        if (diasRetraso <= 0) {
            return 0;
        }
        return diasRetraso * tarifaDiaria;
    }

    @Override
    public double getTarifaDiaria() {
        return tarifaDiaria;
    }
}
