package App.DesktopApp.Forms;

import BusinessLogic.FactoryBL;
import DataAccess.DAOs.UsuarioDAO;
import DataAccess.DTOs.UsuarioDTO;
import Infrastructure.AppException;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class GuardManagementPanel extends JPanel {

    private FactoryBL<UsuarioDTO> userFactory;
    private JTable table;
    private DefaultTableModel model;

    // Components
    private JTextField txtCodigo;
    private JPasswordField txtPin;
    private JLabel lblId;

    private Integer selectedUserId = null;

    public GuardManagementPanel() {
        try {
            userFactory = new FactoryBL<>(UsuarioDAO.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        setLayout(new BorderLayout(10, 10));

        // --- TOP: Form ---
        JPanel pnlForm = new JPanel(new GridBagLayout());
        pnlForm.setBorder(BorderFactory.createTitledBorder("Gestión de Guardias"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // ID Label (readonly)
        addLabel(pnlForm, "ID de Guardia:", 0, 0, gbc);
        lblId = new JLabel("-");
        addField(pnlForm, lblId, 1, 0, gbc);

        // Codigo (Usuario)
        addLabel(pnlForm, "Código Único (Usuario):", 0, 1, gbc);
        txtCodigo = new JTextField(15);
        addField(pnlForm, txtCodigo, 1, 1, gbc);

        // PIN
        addLabel(pnlForm, "PIN (Contraseña):", 0, 2, gbc);
        txtPin = new JPasswordField(15);
        addField(pnlForm, txtPin, 1, 2, gbc);

        add(pnlForm, BorderLayout.NORTH);

        // --- CENTER: Table ---
        String[] cols = { "ID", "Código Único", "PIN", "Rol", "Estado" };
        model = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        table = new JTable(model);
        table.getSelectionModel().addListSelectionListener(e -> loadSelectedGuard());
        add(new JScrollPane(table), BorderLayout.CENTER);

        // --- SOUTH: Buttons ---
        JPanel pnlActions = new JPanel(new FlowLayout());
        JButton btnAdd = new JButton("Agregar");
        JButton btnEdit = new JButton("Actualizar");
        JButton btnDel = new JButton("Eliminar");
        JButton btnClear = new JButton("Limpiar");

        btnAdd.setBackground(new Color(46, 204, 113));
        btnAdd.setForeground(Color.WHITE);
        btnEdit.setBackground(new Color(52, 152, 219));
        btnEdit.setForeground(Color.WHITE);
        btnDel.setBackground(new Color(231, 76, 60));
        btnDel.setForeground(Color.WHITE);

        btnAdd.addActionListener(e -> saveGuard(false));
        btnEdit.addActionListener(e -> saveGuard(true));
        btnDel.addActionListener(e -> deleteGuard());
        btnClear.addActionListener(e -> clearForm());

        pnlActions.add(btnAdd);
        pnlActions.add(btnEdit);
        pnlActions.add(btnDel);
        pnlActions.add(btnClear);

        add(pnlActions, BorderLayout.SOUTH);

        loadData();
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

    private void loadData() {
        try {
            model.setRowCount(0);
            List<UsuarioDTO> allUsers = userFactory.getAll();

            // Filter only Guards
            List<UsuarioDTO> guards = allUsers.stream()
                    .filter(u -> "Guardia".equalsIgnoreCase(u.getRol()) && "A".equals(u.getEstado()))
                    .collect(Collectors.toList());

            for (UsuarioDTO u : guards) {
                model.addRow(new Object[] {
                        u.getIdUsuario(),
                        u.getCodigoUnico(),
                        u.getContrasena(),
                        u.getRol(),
                        u.getEstado()
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadSelectedGuard() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            Integer id = (Integer) model.getValueAt(row, 0);
            try {
                UsuarioDTO u = userFactory.getBy(id);
                if (u != null) {
                    selectedUserId = u.getIdUsuario();
                    lblId.setText(String.valueOf(u.getIdUsuario()));
                    txtCodigo.setText(u.getCodigoUnico());
                    txtPin.setText(u.getContrasena());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void saveGuard(boolean isEdit) {
        if (txtCodigo.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese un Código Único.");
            return;
        }
        String pin = new String(txtPin.getPassword());
        if (pin.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese un PIN.");
            return;
        }

        try {
            UsuarioDTO u;
            String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

            if (isEdit) {
                if (selectedUserId == null)
                    return;
                u = userFactory.getBy(selectedUserId);
                if (u == null)
                    return;

                u.setCodigoUnico(txtCodigo.getText().trim());
                u.setContrasena(pin);
                u.setFechaModifica(now);

                if (userFactory.upd(u)) {
                    JOptionPane.showMessageDialog(this, "Guardia actualizado.");
                }
            } else {
                u = new UsuarioDTO();
                u.setCodigoUnico(txtCodigo.getText().trim());
                u.setContrasena(pin);
                u.setRol("Guardia");
                u.setEstado("A");
                u.setFechaCreacion(now);
                u.setFechaModifica(now);

                if (userFactory.add(u)) {
                    JOptionPane.showMessageDialog(this, "Guardia creado.");
                }
            }
            clearForm();
            loadData();
        } catch (AppException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private void deleteGuard() {
        if (selectedUserId == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un guardia.");
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "¿Seguro de eliminar este guardia?", "Confirmar",
                JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                userFactory.del(selectedUserId);
                clearForm();
                loadData();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
            }
        }
    }

    private void clearForm() {
        selectedUserId = null;
        lblId.setText("-");
        txtCodigo.setText("");
        txtPin.setText("");
        table.clearSelection();
    }
}
