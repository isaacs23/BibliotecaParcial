package biblioteca.patrones;

import biblioteca.modelo.Prestamo;
import biblioteca.modelo.Recurso;
import biblioteca.modelo.Usuario;
import java.time.LocalDate;
import java.util.ArrayList;

public class BibliotecaFacade {
    private CatalogoRecursos catalogo;
    private ArrayList<Usuario> usuarios;
    private ArrayList<Prestamo> prestamos;
    private PoliticaMulta politicaMulta;
    private int contadorPrestamos;

    public BibliotecaFacade() {
        this.catalogo = CatalogoRecursos.getInstancia();
        this.usuarios = new ArrayList<>();
        this.prestamos = new ArrayList<>();
        this.politicaMulta = new MultaFija(2000);
        this.contadorPrestamos = 1;
    }

    public void setPoliticaMulta(PoliticaMulta politicaMulta) {
        this.politicaMulta = politicaMulta;
    }

    public PoliticaMulta getPoliticaMulta() {
        return politicaMulta;
    }

    public Usuario iniciarSesion(String username, String password) {
        for (Usuario u : usuarios) {
            if (u.getUsername().equalsIgnoreCase(username.trim())
                    && u.getPassword().equals(password)) {
                return u;
            }
        }
        throw new RuntimeException("Usuario o contraseña incorrectos.");
    }

    public Usuario registrarUsuario(String username, String password, String nombre) {
        if (username.trim().isEmpty() || password.trim().isEmpty() || nombre.trim().isEmpty()) {
            throw new RuntimeException("Todos los campos son obligatorios.");
        }
        for (Usuario u : usuarios) {
            if (u.getUsername().equalsIgnoreCase(username.trim())) {
                throw new RuntimeException("Ese nombre de usuario ya está registrado.");
            }
        }
        Usuario nuevo = new Usuario(username.trim(), password, nombre.trim(), false);
        usuarios.add(nuevo);
        return nuevo;
    }

    public ArrayList<Recurso> getCatalogo() {
        return catalogo.getTodos();
    }

    public Recurso buscarRecursoPorId(String id) {
        return catalogo.buscarPorId(id.trim());
    }

    public ArrayList<Recurso> buscarRecursos(String texto) {
        if (texto == null || texto.trim().isEmpty()) {
            return catalogo.getTodos();
        }
        return catalogo.buscar(texto.trim());
    }

    public Recurso registrarRecurso(Usuario admin, String tipo, String id,
                                    String titulo, int copias, String datoExtra) {
        validarAdmin(admin);
        if (id.trim().isEmpty() || titulo.trim().isEmpty() || datoExtra.trim().isEmpty()) {
            throw new RuntimeException("Complete todos los campos del recurso.");
        }
        if (copias <= 0) {
            throw new RuntimeException("La cantidad de copias debe ser mayor a 0.");
        }

        CreadorRecurso creador;
        if (tipo.equalsIgnoreCase("LIBRO")) {
            creador = new CreadorLibro();
        } else if (tipo.equalsIgnoreCase("EQUIPO")) {
            creador = new CreadorEquipo();
        } else if (tipo.equalsIgnoreCase("REVISTA")) {
            creador = new CreadorRevista();
        } else {
            throw new RuntimeException("Tipo de recurso no válido.");
        }

        Recurso nuevo = creador.crearRecurso(id.trim(), titulo.trim(), copias, datoExtra.trim());
        catalogo.agregar(nuevo);
        return nuevo;
    }

    public void actualizarRecurso(Usuario admin, String id, String nuevoTitulo,
                                  int nuevasCopias, String nuevoDatoExtra) {
        validarAdmin(admin);
        Recurso r = catalogo.buscarPorId(id.trim());
        if (r == null) {
            throw new RuntimeException("No existe un recurso con ID: " + id);
        }
        if (nuevoTitulo.trim().isEmpty() || nuevoDatoExtra.trim().isEmpty()) {
            throw new RuntimeException("Título y detalle no pueden estar vacíos.");
        }
        if (nuevasCopias <= 0) {
            throw new RuntimeException("Las copias deben ser mayores a 0.");
        }
        r.actualizarDatos(nuevoTitulo.trim(), nuevasCopias, nuevoDatoExtra.trim());
    }

    public void eliminarRecurso(Usuario admin, String id) {
        validarAdmin(admin);
        Recurso r = catalogo.buscarPorId(id.trim());
        if (r == null) {
            throw new RuntimeException("No existe un recurso con ID: " + id);
        }
        if (r.getCopiasDisponibles() < r.getCopiasTotales()) {
            throw new RuntimeException("No se puede eliminar porque tiene copias prestadas actualmente.");
        }
        catalogo.eliminar(r);
    }

    private void validarAdmin(Usuario u) {
        if (u == null || !u.esAdministrador()) {
            throw new RuntimeException("Acceso denegado: solo el administrador puede realizar esta acción.");
        }
    }

    public Prestamo prestarRecurso(Usuario usuario, String idRecurso, LocalDate fecha) {
        Recurso recurso = catalogo.buscarPorId(idRecurso.trim());
        if (usuario == null || recurso == null) {
            throw new RuntimeException("Recurso no encontrado.");
        }

        int activosDelMismoTipo = 0;
        for (Prestamo p : prestamos) {
            if (p.isActivo() && p.getUsuario().getUsername().equalsIgnoreCase(usuario.getUsername())) {
                if (p.getRecurso().getId().equalsIgnoreCase(recurso.getId())) {
                    throw new RuntimeException("Ya tienes un préstamo activo de este mismo recurso.");
                }
                if (p.getRecurso().getTipo().equals(recurso.getTipo())) {
                    activosDelMismoTipo++;
                }
            }
        }

        if (activosDelMismoTipo >= recurso.getMaxPorUsuario()) {
            throw new RuntimeException("Límite alcanzado: máximo " + recurso.getMaxPorUsuario()
                    + " préstamo(s) simultáneos de tipo " + recurso.getTipo() + ".");
        }

        if (!recurso.estaDisponible()) {
            recurso.registrarSolicitud();
            throw new RuntimeException("Recurso agotado. Puedes unirte a la lista de espera.");
        }

        recurso.prestar();
        String idPrestamo = "P-" + contadorPrestamos++;
        Prestamo nuevo = new Prestamo(idPrestamo, usuario, recurso, fecha);
        prestamos.add(nuevo);
        return nuevo;
    }

    public Prestamo devolverRecurso(Usuario usuario, String idPrestamo, int diasRetrasoSimulados) {
        Prestamo p = buscarPrestamo(idPrestamo.trim());
        if (p == null || !p.isActivo()) {
            throw new RuntimeException("Préstamo no encontrado o ya devuelto.");
        }
        if (!p.getUsuario().getUsername().equalsIgnoreCase(usuario.getUsername())) {
            throw new RuntimeException("Ese préstamo pertenece a otro usuario.");
        }

        long diasNaturales = p.calcularDiasRetraso(LocalDate.now());
        long diasFinales = Math.max(diasNaturales, diasRetrasoSimulados);
        double multa = politicaMulta.calcularMulta(diasFinales);

        p.cerrarDevolucion(multa);
        p.getRecurso().devolver();
        return p;
    }

    public void unirseListaEspera(Usuario usuario, String idRecurso) {
        Recurso recurso = catalogo.buscarPorId(idRecurso.trim());
        if (recurso == null) {
            throw new RuntimeException("Recurso no encontrado.");
        }
        if (recurso.estaDisponible()) {
            throw new RuntimeException("El recurso tiene copias disponibles; solicítalo en préstamo.");
        }
        recurso.agregarObservador(usuario);
    }

    public ArrayList<Prestamo> getPrestamosUsuario(Usuario usuario) {
        ArrayList<Prestamo> lista = new ArrayList<>();
        for (Prestamo p : prestamos) {
            if (p.getUsuario().getUsername().equalsIgnoreCase(usuario.getUsername())) {
                lista.add(p);
            }
        }
        return lista;
    }

    private Prestamo buscarPrestamo(String idPrestamo) {
        for (Prestamo p : prestamos) {
            if (p.getIdPrestamo().equalsIgnoreCase(idPrestamo)) {
                return p;
            }
        }
        return null;
    }

    public String generarReporte(Usuario admin) {
        validarAdmin(admin);
        ArrayList<Recurso> todos = catalogo.getTodos();

        ArrayList<Recurso> porSolicitudes = new ArrayList<>(todos);
        for (int i = 0; i < porSolicitudes.size() - 1; i++) {
            for (int j = i + 1; j < porSolicitudes.size(); j++) {
                if (porSolicitudes.get(j).getVecesSolicitado() > porSolicitudes.get(i).getVecesSolicitado()) {
                    Recurso temp = porSolicitudes.get(i);
                    porSolicitudes.set(i, porSolicitudes.get(j));
                    porSolicitudes.set(j, temp);
                }
            }
        }

        ArrayList<Recurso> porUtilizacion = new ArrayList<>(todos);
        for (int i = 0; i < porUtilizacion.size() - 1; i++) {
            for (int j = i + 1; j < porUtilizacion.size(); j++) {
                if (porUtilizacion.get(j).getVecesUtilizado() > porUtilizacion.get(i).getVecesUtilizado()) {
                    Recurso temp = porUtilizacion.get(i);
                    porUtilizacion.set(i, porUtilizacion.get(j));
                    porUtilizacion.set(j, temp);
                }
            }
        }

        int activos = 0;
        double multas = 0;
        for (Prestamo p : prestamos) {
            if (p.isActivo()) {
                activos++;
            } else {
                multas += p.getMulta();
            }
        }

        String rep = "=== REPORTE DE RECURSOS MÁS SOLICITADOS Y MÁS UTILIZADOS ===\n\n";
        rep += "Total de recursos en catálogo : " + todos.size() + "\n";
        rep += "Total de préstamos históricos : " + prestamos.size() + " (Activos: " + activos + ")\n";
        rep += "Multas recaudadas ($2.000/día): $" + multas + "\n\n";

        rep += "--- 1. RECURSOS MÁS SOLICITADOS (Demanda total) ---\n";
        for (int i = 0; i < porSolicitudes.size(); i++) {
            Recurso r = porSolicitudes.get(i);
            rep += (i + 1) + ". [" + r.getId() + "] " + r.getTitulo() + " (" + r.getTipo() + ")\n\t"
                    + "Solicitudes: " + r.getVecesSolicitado()
                    + " | En espera: " + r.getCantidadEnEspera() + "\n";
        }

        rep += "\n--- 2. RECURSOS MÁS UTILIZADOS (Préstamos efectivos) ---\n";
        for (int i = 0; i < porUtilizacion.size(); i++) {
            Recurso r = porUtilizacion.get(i);
            rep += (i + 1) + ". [" + r.getId() + "] " + r.getTitulo() + " (" + r.getTipo() + ")\n\t"
                    + "Veces prestado: " + r.getVecesUtilizado() + "\n";
        }

        return rep;
    }

    public void cargarDatosIniciales() {
        if (!usuarios.isEmpty()) return;

        Usuario admin = new Usuario("admin", "admin123", "Administrador General", true);
        Usuario ana = new Usuario("ana", "1234", "Ana Gómez", false);
        Usuario carlos = new Usuario("carlos", "1234", "Carlos Ruiz", false);

        usuarios.add(admin);
        usuarios.add(ana);
        usuarios.add(carlos);

        registrarRecurso(admin, "LIBRO", "LIB-01", "Ingeniería de Software", 2, "Ian Sommerville");
        registrarRecurso(admin, "LIBRO", "LIB-02", "Patrones de Diseño", 1, "Erich Gamma");
        registrarRecurso(admin, "EQUIPO", "EQP-01", "Portátil Dell Latitude", 1, "Dell");
        registrarRecurso(admin, "REVISTA", "REV-01", "Revista IEEE Software", 3, "Edición 43");

        prestarRecurso(ana, "EQP-01", LocalDate.now().minusDays(1));
        prestarRecurso(carlos, "LIB-02", LocalDate.now().minusDays(16));
    }
}
