package App.DesktopApp.Forms;

import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import App.DesktopApp.CustomControl.PatButton;
import BusinessLogic.Entities.BLRegistroIngreso;
import DataAccess.DTOs.RegistroIngresoDTO;
import Infrastructure.AppException;

public class AdminPanel extends JFrame {
    private JTable table;
    private DefaultTableModel model;
    private BLRegistroIngreso blRegistro = new BLRegistroIngreso();

    public AdminPanel() {
        setTitle("EPN - Panel de Administración");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centrar ventana
        setLayout(new BorderLayout(10, 10));

        // --- Panel Norte: Título ---
        JLabel lblTitle = new JLabel("HISTORIAL DE INGRESOS A LA FACULTAD", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        add(lblTitle, BorderLayout.NORTH);

        // --- Panel Central: Tabla ---
        // Definimos las columnas según tu DTO
        String[] columns = {"ID", "Estudiante", "Guardia", "Resultado", "Fecha Creación", "Estado"};
        model = new DefaultTableModel(columns, 0);
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // --- Panel Sur: Botones ---
        JPanel panelButtons = new JPanel();
        PatButton btnRefresh = new PatButton("Actualizar Datos");
        PatButton btnExit = new PatButton("Cerrar");

        btnRefresh.addActionListener(e -> loadData());
        btnExit.addActionListener(e -> System.exit(0));

        panelButtons.add(btnRefresh);
        panelButtons.add(btnExit);
        add(panelButtons, BorderLayout.SOUTH);

        // Carga inicial de datos
        loadData();
    }

    private void loadData() {
        try {
            model.setRowCount(0); // Limpiar tabla
            List<RegistroIngresoDTO> registros = blRegistro.obtenerTodos();
            
            for (RegistroIngresoDTO r : registros) {
                Object[] row = {
                    r.getIdRegistroIngreso(),
                    r.getIdEstudiante(),
                    r.getIdUsuario(),
                    r.getResultado(),
                    r.getFechaCreacion(),
                    r.getEstado()
                };
                model.addRow(row);
            }
        } catch (AppException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar datos: " + e.getMessage());
        }
    }
}