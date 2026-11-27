package biblioteca.vista;

import biblioteca.modelo.Prestamo;
import biblioteca.modelo.Recurso;
import biblioteca.modelo.Usuario;
import biblioteca.patrones.BibliotecaFacade;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class VentanaBiblioteca extends JFrame {
    private BibliotecaFacade facade;
    private Usuario usuarioActual;

    private CardLayout tarjetas;
    private JPanel contenedor;

    private JTextField txtLoginUser;
    private JPasswordField txtLoginPass;

    private JTextField txtRegNombre;
    private JTextField txtRegUser;
    private JPasswordField txtRegPass;

    private JLabel lblBienvenida;
    private JLabel lblTituloAdmin;
    private JButton btnIrNotificaciones;
    private JButton btnIrAdminRecursos;
    private JButton btnIrAdminReportes;

    private JTextField txtBuscarCatalogo;
    private JTextArea areaCatalogo;

    private JTextArea areaCatalogoPrestamo;
    private JTextField txtIdRecursoPrestamo;

    private JTextArea areaMisPrestamos;
    private JTextField txtIdPrestamoDevolver;
    private JTextField txtDiasRetraso;

    private JTextArea areaNotificaciones;

    private JComboBox<String> comboTipoAdmin;
    private JTextField txtAdminId;
    private JTextField txtAdminTitulo;
    private JTextField txtAdminCopias;
    private JTextField txtAdminExtra;
    private JTextArea areaAdminCatalogo;

    private JTextArea areaReporte;

    public VentanaBiblioteca(BibliotecaFacade facade) {
        this.facade = facade;
        setTitle("Sistema de Biblioteca");
        setSize(760, 520);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        tarjetas = new CardLayout();
        contenedor = new JPanel(tarjetas);

        contenedor.add(crearPanelLogin(), "LOGIN");
        contenedor.add(crearPanelRegistro(), "REGISTRO");
        contenedor.add(crearPanelMenu(), "MENU");
        contenedor.add(crearPanelCatalogo(), "CATALOGO");
        contenedor.add(crearPanelPrestamos(), "PRESTAMOS");
        contenedor.add(crearPanelDevoluciones(), "DEVOLUCIONES");
        contenedor.add(crearPanelNotificaciones(), "NOTIFICACIONES");
        contenedor.add(crearPanelAdminRecursos(), "ADMIN_RECURSOS");
        contenedor.add(crearPanelAdminReportes(), "ADMIN_REPORTES");

        add(contenedor);
        tarjetas.show(contenedor, "LOGIN");
    }

    private JPanel crearPanelLogin() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));

        JLabel titulo = new JLabel("SISTEMA DE BIBLIOTECA - INICIO DE SESIÓN", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(titulo, BorderLayout.NORTH);

        JPanel centro = new JPanel(new GridLayout(4, 2, 10, 10));
        txtLoginUser = new JTextField();
        txtLoginPass = new JPasswordField();

        JButton btnIngresar = new JButton("Iniciar Sesión");
        JButton btnIrRegistro = new JButton("Registrarse");

        btnIngresar.addActionListener(e -> {
            try {
                String u = txtLoginUser.getText();
                String p = new String(txtLoginPass.getPassword());
                usuarioActual = facade.iniciarSesion(u, p);
                txtLoginUser.setText("");
                txtLoginPass.setText("");
                abrirMenu();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        });

        btnIrRegistro.addActionListener(e -> tarjetas.show(contenedor, "REGISTRO"));

        centro.add(new JLabel("Usuario:"));
        centro.add(txtLoginUser);
        centro.add(new JLabel("Contraseña:"));
        centro.add(txtLoginPass);
        centro.add(btnIngresar);
        centro.add(btnIrRegistro);

        JPanel envoltorio = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 60));
        envoltorio.add(centro);
        panel.add(envoltorio, BorderLayout.CENTER);

        return panel;
    }

    private JPanel crearPanelRegistro() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));

        JLabel titulo = new JLabel("REGISTRO DE USUARIO NUEVO", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(titulo, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridLayout(4, 2, 10, 10));
        txtRegNombre = new JTextField();
        txtRegUser = new JTextField();
        txtRegPass = new JPasswordField();

        JButton btnGuardar = new JButton("Crear Cuenta");
        JButton btnVolver = new JButton("<- Volver al Login");

        btnGuardar.addActionListener(e -> {
            try {
                facade.registrarUsuario(
                        txtRegUser.getText(),
                        new String(txtRegPass.getPassword()),
                        txtRegNombre.getText()
                );
                JOptionPane.showMessageDialog(this, "Usuario registrado. Ya puedes iniciar sesión.");
                txtRegNombre.setText("");
                txtRegUser.setText("");
                txtRegPass.setText("");
                tarjetas.show(contenedor, "LOGIN");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        });

        btnVolver.addActionListener(e -> tarjetas.show(contenedor, "LOGIN"));

        form.add(new JLabel("Nombre completo:"));
        form.add(txtRegNombre);
        form.add(new JLabel("Nombre de usuario:"));
        form.add(txtRegUser);
        form.add(new JLabel("Contraseña:"));
        form.add(txtRegPass);
        form.add(btnGuardar);
        form.add(btnVolver);

        JPanel envoltorio = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 60));
        envoltorio.add(form);
        panel.add(envoltorio, BorderLayout.CENTER);

        return panel;
    }

    private JPanel crearPanelMenu() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));

        lblBienvenida = new JLabel("", SwingConstants.CENTER);
        lblBienvenida.setFont(new Font("Arial", Font.BOLD, 15));
        panel.add(lblBienvenida, BorderLayout.NORTH);

        JPanel botones = new JPanel(new GridLayout(0, 1, 8, 8));

        JButton btnCatalogo = new JButton("1. Consultar y Buscar Recursos en Catálogo");
        JButton btnPrestar = new JButton("2. Solicitar Préstamo o Unirse a Lista de Espera");
        JButton btnDevolver = new JButton("3. Mis Préstamos y Registrar Devolución");
        btnIrNotificaciones = new JButton("4. Ver Mis Notificaciones");

        lblTituloAdmin = new JLabel("--- APARTADO DE ADMINISTRACIÓN ---", SwingConstants.CENTER);
        btnIrAdminRecursos = new JButton("5. [ADMIN] Registrar, Actualizar y Eliminar Recursos");
        btnIrAdminReportes = new JButton("6. [ADMIN] Generar Reportes de Uso y Demanda");

        JButton btnSalir = new JButton("Cerrar Sesión");

        btnCatalogo.addActionListener(e -> {
            mostrarListaEnArea(areaCatalogo, facade.getCatalogo());
            tarjetas.show(contenedor, "CATALOGO");
        });

        btnPrestar.addActionListener(e -> {
            mostrarListaEnArea(areaCatalogoPrestamo, facade.getCatalogo());
            tarjetas.show(contenedor, "PRESTAMOS");
        });

        btnDevolver.addActionListener(e -> {
            actualizarAreaMisPrestamos();
            tarjetas.show(contenedor, "DEVOLUCIONES");
        });

        btnIrNotificaciones.addActionListener(e -> {
            actualizarAreaNotificaciones();
            tarjetas.show(contenedor, "NOTIFICACIONES");
        });

        btnIrAdminRecursos.addActionListener(e -> {
            mostrarListaEnArea(areaAdminCatalogo, facade.getCatalogo());
            tarjetas.show(contenedor, "ADMIN_RECURSOS");
        });

        btnIrAdminReportes.addActionListener(e -> {
            areaReporte.setText(facade.generarReporte(usuarioActual));
            tarjetas.show(contenedor, "ADMIN_REPORTES");
        });

        btnSalir.addActionListener(e -> {
            usuarioActual = null;
            tarjetas.show(contenedor, "LOGIN");
        });

        botones.add(btnCatalogo);
        botones.add(btnPrestar);
        botones.add(btnDevolver);
        botones.add(btnIrNotificaciones);
        botones.add(lblTituloAdmin);
        botones.add(btnIrAdminRecursos);
        botones.add(btnIrAdminReportes);
        botones.add(btnSalir);

        JPanel envoltorio = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 20));
        envoltorio.add(botones);
        panel.add(envoltorio, BorderLayout.CENTER);

        return panel;
    }

    private void abrirMenu() {
        String rol = usuarioActual.esAdministrador() ? "Administrador" : "Usuario Normal";
        lblBienvenida.setText("Sesión: " + usuarioActual.getNombre() + " [" + rol + "]");
        btnIrNotificaciones.setText("4. Ver Mis Notificaciones (" + usuarioActual.getNotificaciones().size() + ")");

        boolean esAdmin = usuarioActual.esAdministrador();
        lblTituloAdmin.setVisible(esAdmin);
        btnIrAdminRecursos.setVisible(esAdmin);
        btnIrAdminReportes.setVisible(esAdmin);

        tarjetas.show(contenedor, "MENU");
    }

    private JPanel crearPanelCatalogo() {
        JPanel panel = new JPanel(new BorderLayout(8, 8));

        JPanel arriba = new JPanel(new FlowLayout(FlowLayout.LEFT));
        txtBuscarCatalogo = new JTextField(20);
        JButton btnBuscar = new JButton("Buscar");
        JButton btnTodos = new JButton("Ver Todos");

        btnBuscar.addActionListener(e -> mostrarListaEnArea(areaCatalogo, facade.buscarRecursos(txtBuscarCatalogo.getText())));
        btnTodos.addActionListener(e -> {
            txtBuscarCatalogo.setText("");
            mostrarListaEnArea(areaCatalogo, facade.getCatalogo());
        });

        arriba.add(new JLabel("Buscar por título, ID o tipo:"));
        arriba.add(txtBuscarCatalogo);
        arriba.add(btnBuscar);
        arriba.add(btnTodos);
        panel.add(arriba, BorderLayout.NORTH);

        areaCatalogo = new JTextArea();
        areaCatalogo.setEditable(false);
        areaCatalogo.setTabSize(4);
        panel.add(new JScrollPane(areaCatalogo), BorderLayout.CENTER);

        JPanel abajo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnVolver = new JButton("<- Volver al Menú");
        btnVolver.addActionListener(e -> abrirMenu());
        abajo.add(btnVolver);
        panel.add(abajo, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel crearPanelPrestamos() {
        JPanel panel = new JPanel(new BorderLayout(8, 8));

        panel.add(new JLabel("  Catálogo disponible para solicitar préstamo o lista de espera:"), BorderLayout.NORTH);

        areaCatalogoPrestamo = new JTextArea();
        areaCatalogoPrestamo.setEditable(false);
        areaCatalogoPrestamo.setTabSize(4);
        panel.add(new JScrollPane(areaCatalogoPrestamo), BorderLayout.CENTER);

        JPanel abajo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        txtIdRecursoPrestamo = new JTextField(10);
        JButton btnPrestar = new JButton("Solicitar Préstamo");
        JButton btnEspera = new JButton("Unirse a Lista de Espera");
        JButton btnVolver = new JButton("<- Volver al Menú");

        btnPrestar.addActionListener(e -> {
            try {
                Prestamo p = facade.prestarRecurso(usuarioActual, txtIdRecursoPrestamo.getText(), LocalDate.now());
                mostrarListaEnArea(areaCatalogoPrestamo, facade.getCatalogo());
                JOptionPane.showMessageDialog(this, "Préstamo registrado: " + p.getIdPrestamo()
                        + "\nFecha de vencimiento: " + p.getFechaVencimiento());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        });

        btnEspera.addActionListener(e -> {
            try {
                facade.unirseListaEspera(usuarioActual, txtIdRecursoPrestamo.getText());
                mostrarListaEnArea(areaCatalogoPrestamo, facade.getCatalogo());
                JOptionPane.showMessageDialog(this, "Te has unido a la lista de espera.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        });

        btnVolver.addActionListener(e -> abrirMenu());

        abajo.add(new JLabel("ID Recurso:"));
        abajo.add(txtIdRecursoPrestamo);
        abajo.add(btnPrestar);
        abajo.add(btnEspera);
        abajo.add(btnVolver);
        panel.add(abajo, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel crearPanelDevoluciones() {
        JPanel panel = new JPanel(new BorderLayout(8, 8));

        panel.add(new JLabel("  Mis Préstamos (Multa por retraso: $2.000 diarios):"), BorderLayout.NORTH);

        areaMisPrestamos = new JTextArea();
        areaMisPrestamos.setEditable(false);
        areaMisPrestamos.setTabSize(4);
        panel.add(new JScrollPane(areaMisPrestamos), BorderLayout.CENTER);

        JPanel abajo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        txtIdPrestamoDevolver = new JTextField(8);
        txtDiasRetraso = new JTextField("0", 4);
        JButton btnDevolver = new JButton("Devolver Recurso");
        JButton btnVolver = new JButton("<- Volver al Menú");

        btnDevolver.addActionListener(e -> {
            try {
                int dias = Integer.parseInt(txtDiasRetraso.getText().trim());
                if (dias < 0) {
                    throw new RuntimeException("Los días de retraso no pueden ser negativos.");
                }
                Prestamo p = facade.devolverRecurso(usuarioActual, txtIdPrestamoDevolver.getText(), dias);
                actualizarAreaMisPrestamos();
                JOptionPane.showMessageDialog(this, "Devolución completada.\nMulta generada: $" + p.getMulta());
            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(this, "Ingrese un número válido en días de retraso.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        });

        btnVolver.addActionListener(e -> abrirMenu());

        abajo.add(new JLabel("ID Préstamo:"));
        abajo.add(txtIdPrestamoDevolver);
        abajo.add(new JLabel("Días retraso:"));
        abajo.add(txtDiasRetraso);
        abajo.add(btnDevolver);
        abajo.add(btnVolver);
        panel.add(abajo, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel crearPanelNotificaciones() {
        JPanel panel = new JPanel(new BorderLayout(8, 8));

        panel.add(new JLabel("  Avisos recibidos de listas de espera:"), BorderLayout.NORTH);

        areaNotificaciones = new JTextArea();
        areaNotificaciones.setEditable(false);
        areaNotificaciones.setTabSize(4);
        panel.add(new JScrollPane(areaNotificaciones), BorderLayout.CENTER);

        JPanel abajo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnVolver = new JButton("<- Volver al Menú");
        btnVolver.addActionListener(e -> abrirMenu());
        abajo.add(btnVolver);
        panel.add(abajo, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel crearPanelAdminRecursos() {
        JPanel panel = new JPanel(new BorderLayout(8, 8));

        JPanel arriba = new JPanel(new BorderLayout(5, 5));
        JPanel campos = new JPanel(new GridLayout(5, 2, 5, 5));

        comboTipoAdmin = new JComboBox<>(new String[]{"LIBRO", "EQUIPO", "REVISTA"});
        txtAdminId = new JTextField();
        txtAdminTitulo = new JTextField();
        txtAdminCopias = new JTextField("1");
        txtAdminExtra = new JTextField();

        campos.add(new JLabel("Tipo de recurso:"));
        campos.add(comboTipoAdmin);
        campos.add(new JLabel("ID del recurso:"));
        campos.add(txtAdminId);
        campos.add(new JLabel("Título:"));
        campos.add(txtAdminTitulo);
        campos.add(new JLabel("Cantidad de copias:"));
        campos.add(txtAdminCopias);
        campos.add(new JLabel("Autor / Marca / Edición:"));
        campos.add(txtAdminExtra);

        JPanel botonesCrud = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnCargar = new JButton("Consultar ID");
        JButton btnRegistrar = new JButton("Registrar");
        JButton btnActualizar = new JButton("Actualizar");
        JButton btnEliminar = new JButton("Eliminar");

        btnCargar.addActionListener(e -> {
            Recurso r = facade.buscarRecursoPorId(txtAdminId.getText());
            if (r == null) {
                JOptionPane.showMessageDialog(this, "No se encontró el recurso.");
                return;
            }
            comboTipoAdmin.setSelectedItem(r.getTipo().toUpperCase());
            txtAdminTitulo.setText(r.getTitulo());
            txtAdminCopias.setText(String.valueOf(r.getCopiasTotales()));
            txtAdminExtra.setText(r.getDatoExtra());
        });

        btnRegistrar.addActionListener(e -> {
            try {
                int copias = Integer.parseInt(txtAdminCopias.getText().trim());
                facade.registrarRecurso(usuarioActual, comboTipoAdmin.getSelectedItem().toString(),
                        txtAdminId.getText(), txtAdminTitulo.getText(), copias, txtAdminExtra.getText());
                mostrarListaEnArea(areaAdminCatalogo, facade.getCatalogo());
                JOptionPane.showMessageDialog(this, "Recurso registrado.");
            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(this, "Ingrese un número válido en copias.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        });

        btnActualizar.addActionListener(e -> {
            try {
                int copias = Integer.parseInt(txtAdminCopias.getText().trim());
                facade.actualizarRecurso(usuarioActual, txtAdminId.getText(),
                        txtAdminTitulo.getText(), copias, txtAdminExtra.getText());
                mostrarListaEnArea(areaAdminCatalogo, facade.getCatalogo());
                JOptionPane.showMessageDialog(this, "Recurso actualizado.");
            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(this, "Ingrese un número válido en copias.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        });

        btnEliminar.addActionListener(e -> {
            try {
                facade.eliminarRecurso(usuarioActual, txtAdminId.getText());
                mostrarListaEnArea(areaAdminCatalogo, facade.getCatalogo());
                JOptionPane.showMessageDialog(this, "Recurso eliminado.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        });

        botonesCrud.add(btnCargar);
        botonesCrud.add(btnRegistrar);
        botonesCrud.add(btnActualizar);
        botonesCrud.add(btnEliminar);

        arriba.add(campos, BorderLayout.CENTER);
        arriba.add(botonesCrud, BorderLayout.SOUTH);
        panel.add(arriba, BorderLayout.NORTH);

        areaAdminCatalogo = new JTextArea();
        areaAdminCatalogo.setEditable(false);
        areaAdminCatalogo.setTabSize(4);
        panel.add(new JScrollPane(areaAdminCatalogo), BorderLayout.CENTER);

        JPanel abajo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnVolver = new JButton("<- Volver al Menú");
        btnVolver.addActionListener(e -> abrirMenu());
        abajo.add(btnVolver);
        panel.add(abajo, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel crearPanelAdminReportes() {
        JPanel panel = new JPanel(new BorderLayout(8, 8));

        areaReporte = new JTextArea();
        areaReporte.setEditable(false);
        areaReporte.setTabSize(4);
        panel.add(new JScrollPane(areaReporte), BorderLayout.CENTER);

        JPanel abajo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnRefrescar = new JButton("Actualizar Reporte");
        JButton btnVolver = new JButton("<- Volver al Menú");

        btnRefrescar.addActionListener(e -> areaReporte.setText(facade.generarReporte(usuarioActual)));
        btnVolver.addActionListener(e -> abrirMenu());

        abajo.add(btnRefrescar);
        abajo.add(btnVolver);
        panel.add(abajo, BorderLayout.SOUTH);

        return panel;
    }

    private void mostrarListaEnArea(JTextArea area, ArrayList<Recurso> lista) {
        String texto = "";
        for (Recurso r : lista) {
            texto += r.toString() + "\n\n";
        }
        area.setText(texto.isEmpty() ? "No se encontraron recursos." : texto);
    }

    private void actualizarAreaMisPrestamos() {
        String texto = "";
        for (Prestamo p : facade.getPrestamosUsuario(usuarioActual)) {
            texto += p.toString() + "\n";
        }
        areaMisPrestamos.setText(texto.isEmpty() ? "No tienes préstamos registrados." : texto);
    }

    private void actualizarAreaNotificaciones() {
        String texto = "";
        for (String n : usuarioActual.getNotificaciones()) {
            texto += "- " + n + "\n";
        }
        areaNotificaciones.setText(texto.isEmpty() ? "No tienes notificaciones." : texto);
    }
}
