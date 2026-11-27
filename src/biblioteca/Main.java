package biblioteca;

import biblioteca.patrones.BibliotecaFacade;
import biblioteca.vista.VentanaBiblioteca;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        BibliotecaFacade facade = new BibliotecaFacade();
        facade.cargarDatosIniciales();

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new VentanaBiblioteca(facade).setVisible(true);
            }
        });
    }
}
