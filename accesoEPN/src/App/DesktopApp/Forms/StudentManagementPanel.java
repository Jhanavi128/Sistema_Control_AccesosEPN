package App.DesktopApp.Forms;

import BusinessLogic.Entities.BLEstudianteManagement;
import BusinessLogic.Entities.BLPeriodo;
import BusinessLogic.FactoryBL;
import DataAccess.DAOs.UsuarioDAO;
import DataAccess.DTOs.EstudianteDTO;
import DataAccess.DTOs.PeriodoDTO;
import DataAccess.DTOs.UsuarioDTO;
import Infrastructure.AppException;
import java.awt.*;
import java.io.File;
import java.util.List;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

public class StudentManagementPanel extends JPanel {

    private BLEstudianteManagement blEstudiante = new BLEstudianteManagement();
    private BLPeriodo blPeriodo = new BLPeriodo();
    private FactoryBL<UsuarioDTO> userFactory;

    // Components
    private JTextField txtCodigo, txtNombre, txtApellido, txtCedula;
    private JPasswordField txtPin;
    private JComboBox<PeriodoItem> cmbCarrera; // Wrapper class for ComboBox
    private JTextField txtFotoPath;
    private JLabel lblFotoPreview;
    private JTable table;
    private DefaultTableModel model;
    private JTextField txtSearch;

    private Integer selectedStudentId = null;
    private Integer selectedUserId = null; // To manage PIN updates

    public StudentManagementPanel() {
        // Init User Factory
        try {
            userFactory = new FactoryBL<>(UsuarioDAO.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        setLayout(new BorderLayout(10, 10));

        // --- TOP: Form ---
        JPanel pnlForm = new JPanel(new GridBagLayout());
        pnlForm.setBorder(BorderFactory.createTitledBorder("Datos del Estudiante"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Código
        addLabel(pnlForm, "Código Único (9 dígitos):", 0, 0, gbc);
        txtCodigo = new JTextField(15);
        addField(pnlForm, txtCodigo, 1, 0, gbc);

        // Nombre
        addLabel(pnlForm, "Nombre (Solo letras):", 0, 1, gbc);
        txtNombre = new JTextField(15);
        addField(pnlForm, txtNombre, 1, 1, gbc);

        // Apellido
        addLabel(pnlForm, "Apellido (Solo letras):", 0, 2, gbc);
        txtApellido = new JTextField(15);
        addField(pnlForm, txtApellido, 1, 2, gbc);

        // Cédula
        addLabel(pnlForm, "Cédula (10 dígitos):", 0, 3, gbc);
        txtCedula = new JTextField(15);
        addField(pnlForm, txtCedula, 1, 3, gbc);

        // PIN
        addLabel(pnlForm, "PIN de Acceso (Solo números):", 0, 4, gbc);
        txtPin = new JPasswordField(15);
        addField(pnlForm, txtPin, 1, 4, gbc);

        // Carrera (Periodo)
        addLabel(pnlForm, "Carrera / Periodo:", 0, 5, gbc);
        cmbCarrera = new JComboBox<>();
        addField(pnlForm, cmbCarrera, 1, 5, gbc);

        // Foto
        addLabel(pnlForm, "Foto:", 0, 6, gbc);
        JPanel pnlFoto = new JPanel(new BorderLayout(5, 0));
        txtFotoPath = new JTextField();
        txtFotoPath.setEditable(false);
        JButton btnBrowse = new JButton("...");
        btnBrowse.addActionListener(e -> selectPhoto());
        pnlFoto.add(txtFotoPath, BorderLayout.CENTER);
        pnlFoto.add(btnBrowse, BorderLayout.EAST);

        gbc.gridx = 1;
        gbc.gridy = 6;
        pnlForm.add(pnlFoto, gbc);

        add(pnlForm, BorderLayout.NORTH);

        // --- CENTER: Table ---
        String[] cols = { "ID", "Código", "Nombre", "Apellido", "Cédula", "Carrera", "PIN", "Foto" };
        model = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        table = new JTable(model);
        table.getSelectionModel().addListSelectionListener(e -> loadSelectedStudent());
        add(new JScrollPane(table), BorderLayout.CENTER);

        // --- SOUTH: Buttons ---
        JPanel pnlSouth = new JPanel(new BorderLayout());

        JPanel pnlActions = new JPanel(new FlowLayout());
        JButton btnAdd = new JButton("Agregar");
        JButton btnEdit = new JButton("Editar");
        JButton btnDel = new JButton("Eliminar");
        JButton btnClear = new JButton("Limpiar Campos");

        btnAdd.setBackground(new Color(46, 204, 113));
        btnAdd.setForeground(Color.WHITE);
        btnEdit.setBackground(new Color(52, 152, 219));
        btnEdit.setForeground(Color.WHITE);
        btnDel.setBackground(new Color(231, 76, 60));
        btnDel.setForeground(Color.WHITE);

        btnAdd.addActionListener(e -> saveStudent(false));
        btnEdit.addActionListener(e -> saveStudent(true));
        btnDel.addActionListener(e -> deleteStudent());
        btnClear.addActionListener(e -> clearForm());

        // STYLING UPDATES
        Font mainFont = new Font("Segoe UI", Font.BOLD, 14);
        Font btnFont = new Font("Segoe UI", Font.BOLD, 14);

        // Apply fonts to inputs
        txtCodigo.setFont(mainFont);
        txtNombre.setFont(mainFont);
        txtApellido.setFont(mainFont);
        txtCedula.setFont(mainFont);
        txtPin.setFont(mainFont);
        cmbCarrera.setFont(mainFont);

        // Apply fonts to buttons
        btnAdd.setFont(btnFont);
        btnEdit.setFont(btnFont);
        btnDel.setFont(btnFont);
        btnClear.setFont(btnFont);

        // Increase Button Size
        btnAdd.setPreferredSize(new Dimension(120, 40));
        btnEdit.setPreferredSize(new Dimension(120, 40));
        btnDel.setPreferredSize(new Dimension(120, 40));
        btnClear.setPreferredSize(new Dimension(150, 40));

        pnlActions.add(btnAdd);
        pnlActions.add(btnEdit);
        pnlActions.add(btnDel);
        pnlActions.add(btnClear);

        JPanel pnlSearch = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lblSearch = new JLabel("Buscar por Código/Nombre:");
        lblSearch.setFont(mainFont);
        pnlSearch.add(lblSearch);
        txtSearch = new JTextField(20);
        txtSearch.setFont(mainFont);
        JButton btnSearch = new JButton("Buscar");
        btnSearch.setFont(btnFont);
        btnSearch.setBackground(new Color(52, 73, 94));
        btnSearch.setForeground(Color.WHITE);

        btnSearch.addActionListener(e -> loadData(txtSearch.getText()));
        pnlSearch.add(txtSearch);
        pnlSearch.add(btnSearch);

        pnlSouth.add(pnlActions, BorderLayout.CENTER);
        pnlSouth.add(pnlSearch, BorderLayout.SOUTH);

        add(pnlSouth, BorderLayout.SOUTH);

        // Load Initial Data
        loadPeriodos();
        loadData("");
    }

    private void addLabel(JPanel p, String text, int x, int y, GridBagConstraints gbc) {
        gbc.gridx = x;
        gbc.gridy = y;
        p.add(new JLabel(text), gbc);
    }

    private void addField(JPanel p, JComponent field, int x, int y, GridBagConstraints gbc) {
        gbc.gridx = x;
        gbc.gridy = y;
        p.add(field, gbc);
    }

    private void loadPeriodos() {
        try {
            cmbCarrera.removeAllItems();
            List<PeriodoDTO> periodos = blPeriodo.getAll();
            for (PeriodoDTO p : periodos) {
                if ("A".equals(p.getEstado())) // Solo periodos activos
                    cmbCarrera.addItem(new PeriodoItem(p));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error cargando periodos: " + e.getMessage());
        }
    }

    private void loadData(String query) {
        try {
            model.setRowCount(0);
            List<EstudianteDTO> list = blEstudiante.buscarEstudiantes(query);
            for (EstudianteDTO e : list) {

                // Obtener el PIN desde UsuarioDTO
                String pin = "N/A";
                if (e.getIdUsuario() != null) {
                    UsuarioDTO u = userFactory.getBy(e.getIdUsuario());
                    if (u != null) {
                        pin = u.getContrasena();
                    }
                }

                model.addRow(new Object[] {
                        e.getIdEstudiante(),
                        e.getCodigoUnico(),
                        e.getNombre(),
                        e.getApellido(),
                        e.getCedula(),
                        e.getCarrera(),
                        pin, // MOSTRAR PIN
                        e.getFotoPath()
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void selectPhoto() {
        JFileChooser fc = new JFileChooser();
        fc.setFileFilter(new FileNameExtensionFilter("Imágenes (JPG, PNG)", "jpg", "png", "jpeg"));
        if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File f = fc.getSelectedFile();
            txtFotoPath.setText(f.getAbsolutePath()); // Store full path for copying
            // Por simplicidad ahora solo guardamos el nombre
        }
    }

    private void saveStudent(boolean isEdit) {
        // Validaciones de campos vacíos
        if (txtCodigo.getText().trim().isEmpty()) {
            String msg = "El Código Único es obligatorio.";
            AppException.logValidationError(msg, getClass(), "saveStudent");
            JOptionPane.showMessageDialog(this, msg, "Validación",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (txtNombre.getText().trim().isEmpty()) {
            String msg = "El Nombre es obligatorio.";
            AppException.logValidationError(msg, getClass(), "saveStudent");
            JOptionPane.showMessageDialog(this, msg, "Validación",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (txtApellido.getText().trim().isEmpty()) {
            String msg = "El Apellido es obligatorio.";
            AppException.logValidationError(msg, getClass(), "saveStudent");
            JOptionPane.showMessageDialog(this, msg, "Validación",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (txtCedula.getText().trim().isEmpty()) {
            String msg = "La Cédula es obligatoria.";
            AppException.logValidationError(msg, getClass(), "saveStudent");
            JOptionPane.showMessageDialog(this, msg, "Validación",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Validaciones de formato
        if (!txtCodigo.getText().matches("\\d{9}")) {
            String msg = "El Código Único debe tener exactamente 9 dígitos.";
            AppException.logValidationError(msg, getClass(), "saveStudent");
            JOptionPane.showMessageDialog(this, msg, "Validación",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!txtNombre.getText().matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
            String msg = "El Nombre solo puede contener letras y espacios.";
            AppException.logValidationError(msg, getClass(), "saveStudent");
            JOptionPane.showMessageDialog(this, msg, "Validación",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!txtApellido.getText().matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
            String msg = "El Apellido solo puede contener letras y espacios.";
            AppException.logValidationError(msg, getClass(), "saveStudent");
            JOptionPane.showMessageDialog(this, msg, "Validación",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!txtCedula.getText().matches("\\d{10}")) {
            String msg = "La Cédula debe tener exactamente 10 dígitos.";
            AppException.logValidationError(msg, getClass(), "saveStudent");
            JOptionPane.showMessageDialog(this, msg, "Validación",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        String pin = new String(txtPin.getPassword());
        if (!pin.isEmpty() && !pin.matches("\\d+")) {
            String msg = "El PIN solo puede contener números.";
            AppException.logValidationError(msg, getClass(), "saveStudent");
            JOptionPane.showMessageDialog(this, msg, "Validación",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!isEdit && pin.isEmpty()) {
            String msg = "El PIN es obligatorio para nuevos estudiantes.";
            AppException.logValidationError(msg, getClass(), "saveStudent");
            JOptionPane.showMessageDialog(this, msg, "Validación",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Validar selección para edición
        if (isEdit && selectedStudentId == null) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un estudiante de la tabla para editar.",
                    "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            EstudianteDTO est;
            if (isEdit && selectedStudentId != null) {
                // Preservar datos existentes (Estado, FechaCreacion, etc.)
                est = blEstudiante.obtenerTodos().stream()
                        .filter(e -> e.getIdEstudiante().equals(selectedStudentId))
                        .findFirst()
                        .orElse(new EstudianteDTO());
            } else {
                est = new EstudianteDTO();
                est.setSexo("M");
                est.setFechaNacimiento("2000-01-01");
            }

            est.setCodigoUnico(txtCodigo.getText().trim());
            est.setNombre(txtNombre.getText().trim());
            est.setApellido(txtApellido.getText().trim());
            est.setCedula(txtCedula.getText().trim());

            // Logic to copy photo
            String sourcePath = txtFotoPath.getText();
            String fileName = "default.png";
            if (!sourcePath.isEmpty() && !sourcePath.equals("default.png")) {
                File sourceFile = new File(sourcePath);
                if (sourceFile.exists()) {
                    fileName = sourceFile.getName();
                    // Destination: Web/Public
                    // Assuming running from project root or checks two locations
                    File destDir = new File("accesoEPN/src/Web/Public");
                    if (!destDir.exists())
                        destDir = new File("src/Web/Public"); // Fallback

                    if (destDir.exists()) {
                        try {
                            java.nio.file.Files.copy(sourceFile.toPath(), new File(destDir, fileName).toPath(),
                                    java.nio.file.StandardCopyOption.REPLACE_EXISTING);
                        } catch (java.io.IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                } else {
                    fileName = sourcePath; // It might be just a name already
                }
            }
            est.setFotoPath(fileName);

            PeriodoItem item = (PeriodoItem) cmbCarrera.getSelectedItem();
            if (item != null)
                est.setIdPeriodo(item.dto.getIdPeriodo());

            if (isEdit) {
                est.setIdEstudiante(selectedStudentId);
                est.setIdUsuario(selectedUserId); // Necesario para update
                blEstudiante.actualizarEstudiante(est, pin);
                JOptionPane.showMessageDialog(this,
                        "✓ Estudiante actualizado exitosamente.\nCódigo: " + est.getCodigoUnico(),
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                blEstudiante.crearEstudiante(est, pin);
                JOptionPane.showMessageDialog(this,
                        "✓ Estudiante agregado exitosamente.\nCódigo: " + est.getCodigoUnico(),
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);
            }
            clearForm();
            loadData("");
        } catch (AppException e) {
            // Mostrar mensaje de error específico
            JOptionPane.showMessageDialog(this,
                    e.getMessage(),
                    "Error de Validación",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteStudent() {
        if (selectedStudentId == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un estudiante.");
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "¿Seguro de eliminar este estudiante?", "Confirmar",
                JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                blEstudiante.eliminarEstudiante(selectedStudentId);
                loadData("");
                clearForm();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error al eliminar: " + e.getMessage());
            }
        }
    }

    private void loadSelectedStudent() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            Integer id = (Integer) model.getValueAt(row, 0);
            try {
                EstudianteDTO est = blEstudiante.obtenerTodos().stream()
                        .filter(e -> e.getIdEstudiante().equals(id)).findFirst().orElse(null);

                if (est != null) {
                    selectedStudentId = est.getIdEstudiante();
                    selectedUserId = est.getIdUsuario();
                    txtCodigo.setText(est.getCodigoUnico());
                    txtNombre.setText(est.getNombre());
                    txtApellido.setText(est.getApellido());
                    txtCedula.setText(est.getCedula());
                    txtFotoPath.setText(est.getFotoPath());

                    // MOSTRAR PIN TAMBIÉN EN EL CAMPO AL SELECCIONAR
                    try {
                        UsuarioDTO u = userFactory.getBy(est.getIdUsuario());
                        if (u != null) {
                            txtPin.setText(u.getContrasena());
                        }
                    } catch (Exception ex) {
                        txtPin.setText("");
                    }

                    // Seleccionar carrera en combo
                    for (int i = 0; i < cmbCarrera.getItemCount(); i++) {
                        if (cmbCarrera.getItemAt(i).dto.getIdPeriodo().equals(est.getIdPeriodo())) {
                            cmbCarrera.setSelectedIndex(i);
                            break;
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void clearForm() {
        txtCodigo.setText("");
        txtNombre.setText("");
        txtApellido.setText("");
        txtCedula.setText("");
        txtPin.setText("");
        txtFotoPath.setText("");
        if (cmbCarrera.getItemCount() > 0)
            cmbCarrera.setSelectedIndex(0);
        selectedStudentId = null;
        selectedUserId = null;
        table.clearSelection();
    }

    // Helper class for ComboBox
    private static class PeriodoItem {
        PeriodoDTO dto;

        public PeriodoItem(PeriodoDTO dto) {
            this.dto = dto;
        }

        @Override
        public String toString() {
            String cleanNombre = dto.getNombre();
            if (cleanNombre != null && cleanNombre.contains(" - ")) {
                cleanNombre = cleanNombre.split(" - ")[0];
            }
            return dto.getCarrera() + " - " + cleanNombre;
        }
    }
}
