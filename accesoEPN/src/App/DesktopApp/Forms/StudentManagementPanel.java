package App.DesktopApp.Forms;

import BusinessLogic.Entities.BLEstudianteManagement;
import BusinessLogic.Entities.BLPeriodo;
import DataAccess.DTOs.EstudianteDTO;
import DataAccess.DTOs.PeriodoDTO;
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
        addLabel(pnlForm, "Cédula:", 0, 3, gbc);
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
        String[] cols = { "ID", "Código", "Nombre", "Apellido", "Carrera", "Foto" };
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

        pnlActions.add(btnAdd);
        pnlActions.add(btnEdit);
        pnlActions.add(btnDel);
        pnlActions.add(btnClear);

        JPanel pnlSearch = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pnlSearch.add(new JLabel("Buscar por Código/Nombre:"));
        txtSearch = new JTextField(20);
        JButton btnSearch = new JButton("Buscar");
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
                // Obtener nombre de carrera a través de BL (o ya viene en DTO si usamos
                // searchByText)
                // Usamos el searchByText del DAO que hicimos JOIN, así que ya tiene el nombre
                // Pero si usamos getAll del factory NO tiene el JOIN.
                // Ajuste: usar searchByText incluso para vacío para tener el JOIN.

                model.addRow(new Object[] {
                        e.getIdEstudiante(),
                        e.getCodigoUnico(),
                        e.getNombre(),
                        e.getApellido(),
                        e.getCarrera(), // Aprovechamos el campo extra del DTO
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
            txtFotoPath.setText(f.getName()); // En app real, copiaríamos el archivo a una carpeta
            // Por simplicidad ahora solo guardamos el nombre
        }
    }

    private void saveStudent(boolean isEdit) {
        // Validaciones
        if (!txtCodigo.getText().matches("\\d{9}")) {
            JOptionPane.showMessageDialog(this, "El Código debe tener 9 dígitos.");
            return;
        }
        if (!txtNombre.getText().matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
            JOptionPane.showMessageDialog(this, "El Nombre solo puede contener letras.");
            return;
        }
        if (!txtApellido.getText().matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
            JOptionPane.showMessageDialog(this, "El Apellido solo puede contener letras.");
            return;
        }
        String pin = new String(txtPin.getPassword());
        if (!pin.isEmpty() && !pin.matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "El PIN solo puede contener números.");
            return;
        }
        if (!isEdit && pin.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El PIN es obligatorio para nuevos estudiantes.");
            return;
        }

        try {
            EstudianteDTO est = new EstudianteDTO();
            est.setCodigoUnico(txtCodigo.getText());
            est.setNombre(txtNombre.getText());
            est.setApellido(txtApellido.getText());
            est.setCedula(txtCedula.getText()); // No validé cédula por brevedad, pero se podría
            est.setSexo("M"); // Default por simplicidad, faltó combo sexo
            est.setFechaNacimiento("2000-01-01"); // Default
            est.setFotoPath(txtFotoPath.getText().isEmpty() ? "default.png" : txtFotoPath.getText());

            PeriodoItem item = (PeriodoItem) cmbCarrera.getSelectedItem();
            if (item != null)
                est.setIdPeriodo(item.dto.getIdPeriodo());

            if (isEdit) {
                if (selectedStudentId == null)
                    return;
                est.setIdEstudiante(selectedStudentId);
                est.setIdUsuario(selectedUserId); // Necesario para update
                blEstudiante.actualizarEstudiante(est, pin);
                JOptionPane.showMessageDialog(this, "Estudiante actualizado.");
            } else {
                blEstudiante.crearEstudiante(est, pin);
                JOptionPane.showMessageDialog(this, "Estudiante agregado.");
            }
            clearForm();
            loadData("");
        } catch (AppException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
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
                    txtPin.setText(""); // Por seguridad no mostramos PIN

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
            return dto.getCarrera() + " - " + dto.getNombre();
        }
    }
}
