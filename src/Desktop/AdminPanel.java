package Desktop;

import Data.EstudianteDAO;
import Data.ConexionBD;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AdminPanel extends JFrame {

    private JTextField txtNombre, txtCodigo, txtPin, txtCarrera, txtFoto, txtBuscar;
    private JTable tablaEstudiantes;
    private DefaultTableModel modeloTabla;
    private EstudianteDAO dao;

    // Colores y Fuentes
    private final Color PRIMARY_COLOR = new Color(50, 100, 200);
    private final Color ACCENT_COLOR = new Color(70, 130, 180);
    private final Color BG_COLOR = new Color(245, 245, 250);
    private final Font MAIN_FONT = new Font("Segoe UI", Font.PLAIN, 16);
    private final Font HEADER_FONT = new Font("Segoe UI", Font.BOLD, 14);

    public AdminPanel() {
        // Initialize DB
        ConexionBD.inicializar();
        dao = new EstudianteDAO();

        setTitle("Panel Administrativo - Control de Accesos");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(BG_COLOR);

        // --- Top Panel: Form ---
        JPanel panelFormWrapper = new JPanel(new BorderLayout());
        panelFormWrapper.setBackground(BG_COLOR);
        panelFormWrapper.setBorder(new EmptyBorder(20, 20, 10, 20));

        JPanel panelForm = new JPanel(new GridLayout(6, 2, 20, 15)); // Más espacio entre elementos
        panelForm.setBackground(BG_COLOR);
        panelForm.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(PRIMARY_COLOR),
                " Datos del Estudiante ", 0, 0, HEADER_FONT, PRIMARY_COLOR));

        txtNombre = createStyledTextField();
        txtCodigo = createStyledTextField();
        txtPin = createStyledTextField();
        txtCarrera = createStyledTextField();
        txtFoto = createStyledTextField();
        txtFoto.setText("perfil_john.jpg");
        txtFoto.setEditable(false);

        addFormRow(panelForm, "Código Único (9 dígitos):", txtCodigo);
        addFormRow(panelForm, "Nombre Completo (Solo letras):", txtNombre);
        addFormRow(panelForm, "PIN de Acceso:", txtPin);
        addFormRow(panelForm, "Carrera:", txtCarrera);
        addFormRow(panelForm, "Foto:", createPhotoPanel());

        panelFormWrapper.add(panelForm, BorderLayout.CENTER);
        add(panelFormWrapper, BorderLayout.NORTH);

        // --- Buttons Panel ---
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panelBotones.setBackground(BG_COLOR);

        JButton btnAgregar = createStyledButton("Agregar", new Color(46, 204, 113));
        JButton btnEditar = createStyledButton("Editar", new Color(52, 152, 219));
        JButton btnEliminar = createStyledButton("Eliminar", new Color(231, 76, 60));
        JButton btnLimpiar = createStyledButton("Limpiar Campos", new Color(149, 165, 166));

        panelBotones.add(btnAgregar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnLimpiar);

        panelFormWrapper.add(panelBotones, BorderLayout.SOUTH);

        // --- Center Panel: Table ---
        String[] columnas = { "Código", "PIN", "Nombre", "Carrera", "Foto" };
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaEstudiantes = new JTable(modeloTabla);
        tablaEstudiantes.setRowHeight(30);
        tablaEstudiantes.setFont(MAIN_FONT);
        tablaEstudiantes.getTableHeader().setFont(HEADER_FONT);
        tablaEstudiantes.getTableHeader().setBackground(new Color(230, 230, 230));

        JScrollPane scrollPane = new JScrollPane(tablaEstudiantes);
        scrollPane.setBorder(new EmptyBorder(0, 20, 0, 20));
        add(scrollPane, BorderLayout.CENTER);

        // --- Bottom Panel: Search ---
        JPanel panelBuscar = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 15));
        panelBuscar.setBackground(BG_COLOR);

        JLabel lblBuscar = new JLabel("Buscar por Código/Nombre:");
        lblBuscar.setFont(HEADER_FONT);

        txtBuscar = createStyledTextField();
        txtBuscar.setPreferredSize(new Dimension(300, 35));

        JButton btnBuscar = createStyledButton("Buscar", PRIMARY_COLOR);

        panelBuscar.add(lblBuscar);
        panelBuscar.add(txtBuscar);
        panelBuscar.add(btnBuscar);

        add(panelBuscar, BorderLayout.SOUTH);

        // Listeners
        btnAgregar.addActionListener(e -> agregarEstudiante());
        btnEditar.addActionListener(e -> editarEstudiante());
        btnEliminar.addActionListener(e -> eliminarEstudiante());
        btnLimpiar.addActionListener(e -> limpiarFormulario());
        btnBuscar.addActionListener(e -> listarEstudiantes(txtBuscar.getText()));

        // Table Selection
        tablaEstudiantes.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tablaEstudiantes.getSelectedRow() != -1) {
                cargarDatosFormulario();
            }
        });

        // Initial Load
        listarEstudiantes(null);
    }

    // --- Estilos ---
    private JTextField createStyledTextField() {
        JTextField tf = new JTextField();
        tf.setFont(MAIN_FONT);
        tf.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        return tf;
    }

    private void addFormRow(JPanel panel, String labelText, Component field) {
        JLabel label = new JLabel(labelText);
        label.setFont(MAIN_FONT);
        panel.add(label);
        panel.add(field);
    }

    private JPanel createPhotoPanel() {
        JPanel p = new JPanel(new BorderLayout(5, 0));
        p.setBackground(BG_COLOR);

        JButton btnFoto = new JButton("...");
        btnFoto.setFont(HEADER_FONT);
        btnFoto.addActionListener(e -> seleccionarFoto());

        p.add(txtFoto, BorderLayout.CENTER);
        p.add(btnFoto, BorderLayout.EAST);
        return p;
    }

    private JButton createStyledButton(String text, Color bg) {
        JButton btn = new JButton(text);
        btn.setFont(HEADER_FONT);
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    // --- Lógica del Negocio ---

    private void listarEstudiantes(String filtro) {
        modeloTabla.setRowCount(0);
        List<String[]> lista = dao.listar(filtro);
        for (String[] row : lista) {
            modeloTabla.addRow(row);
        }
    }

    private void agregarEstudiante() {
        if (validarCampos()) {
            if (dao.insertar(txtCodigo.getText(), txtPin.getText(), txtNombre.getText(), txtCarrera.getText(),
                    txtFoto.getText())) {
                showMessage("Estudiante agregado correctamente.", JOptionPane.INFORMATION_MESSAGE);
                listarEstudiantes(null);
                limpiarFormulario();
            } else {
                showMessage("Error al agregar.\nEl código podría estar duplicado.", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void editarEstudiante() {
        if (tablaEstudiantes.getSelectedRow() == -1) {
            showMessage("Seleccione un estudiante a editar.", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (validarCampos()) {
            if (dao.actualizar(txtCodigo.getText(), txtPin.getText(), txtNombre.getText(), txtCarrera.getText(),
                    txtFoto.getText())) {
                showMessage("Estudiante actualizado.", JOptionPane.INFORMATION_MESSAGE);
                listarEstudiantes(null);
                limpiarFormulario();
            } else {
                showMessage("Error al actualizar.", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void eliminarEstudiante() {
        int row = tablaEstudiantes.getSelectedRow();
        if (row == -1) {
            showMessage("Seleccione un estudiante para eliminar.", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String codigo = (String) modeloTabla.getValueAt(row, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "¿Seguro borrar al estudiante " + codigo + "?", "Confirmar",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            if (dao.eliminar(codigo)) {
                showMessage("Estudiante eliminado.", JOptionPane.INFORMATION_MESSAGE);
                listarEstudiantes(null);
                limpiarFormulario();
            } else {
                showMessage("Error al eliminar.", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void cargarDatosFormulario() {
        int row = tablaEstudiantes.getSelectedRow();
        txtCodigo.setText((String) modeloTabla.getValueAt(row, 0));
        txtCodigo.setEditable(false);
        txtPin.setText((String) modeloTabla.getValueAt(row, 1));
        txtNombre.setText((String) modeloTabla.getValueAt(row, 2));
        txtCarrera.setText((String) modeloTabla.getValueAt(row, 3));
        txtFoto.setText((String) modeloTabla.getValueAt(row, 4));
    }

    private void limpiarFormulario() {
        txtCodigo.setText("");
        txtCodigo.setEditable(true);
        txtPin.setText("");
        txtNombre.setText("");
        txtCarrera.setText("");
        txtFoto.setText("perfil_john.jpg");
        tablaEstudiantes.clearSelection();
    }

    private boolean validarCampos() {
        String codigo = txtCodigo.getText().trim();
        String nombre = txtNombre.getText().trim();

        // Campos Vacios
        if (codigo.isEmpty() || nombre.isEmpty()) {
            showMessage("Complete Código y Nombre obligatoriamente.", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        // Validación: Solo Números y longitud 9
        if (!codigo.matches("\\d{9}")) {
            showMessage("El CÓDIGO debe contener exactamente 9 dígitos numéricos.", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        // Validación: Solo Letras y espacios
        if (!nombre.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+")) {
            showMessage("El NOMBRE solo debe contener letras y espacios.", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        return true;
    }

    private void showMessage(String msg, int type) {
        JOptionPane.showMessageDialog(this, msg, "Control de Accesos", type);
    }

    private void seleccionarFoto() {
        if (txtCodigo.getText().isEmpty()) {
            showMessage("Primero ingrese el Código del Estudiante para nombrar la foto.", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            java.io.File selectedFile = fileChooser.getSelectedFile();
            try {
                java.nio.file.Path targetDir = java.nio.file.Paths.get("assets");
                if (!java.nio.file.Files.exists(targetDir)) {
                    java.nio.file.Files.createDirectories(targetDir);
                }

                String originalName = selectedFile.getName();
                String extension = ".jpg";
                int i = originalName.lastIndexOf('.');
                if (i > 0) {
                    extension = originalName.substring(i);
                }

                String timestamp = String.valueOf(System.currentTimeMillis());
                String newFileName = txtCodigo.getText() + "_" + timestamp + extension;
                java.nio.file.Path targetPath = targetDir.resolve(newFileName);

                java.nio.file.Files.copy(selectedFile.toPath(), targetPath,
                        java.nio.file.StandardCopyOption.REPLACE_EXISTING);

                txtFoto.setText(newFileName);
                showMessage("Foto cargada: " + newFileName + "\nRecuerde guardar los cambios con 'Editar'.",
                        JOptionPane.INFORMATION_MESSAGE);

            } catch (Exception ex) {
                ex.printStackTrace();
                showMessage("Error copiando foto: " + ex.getMessage(), JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new AdminPanel().setVisible(true);
        });
    }
}
