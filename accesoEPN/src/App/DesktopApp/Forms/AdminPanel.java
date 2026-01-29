package App.DesktopApp.Forms;

import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import App.DesktopApp.CustomControl.PatButton;
import BusinessLogic.Entities.BLRegistroIngreso;
import BusinessLogic.Entities.BLEstudiante; // Necesario para buscar datos del estudiante
import BusinessLogic.Entities.BLPeriodo; // Necesario para buscar la carrera
import DataAccess.DTOs.RegistroIngresoDTO;
import DataAccess.DTOs.EstudianteDTO;
import DataAccess.DTOs.PeriodoDTO;
import Infrastructure.AppException;

public class AdminPanel extends JFrame {
    private JTable table;
    private DefaultTableModel model;

    // Capas de Negocio para realizar las búsquedas cruzadas
    private BLRegistroIngreso blRegistro = new BLRegistroIngreso();
    private BLEstudiante blEstudiante = new BLEstudiante();
    private BLPeriodo blPeriodo = new BLPeriodo();

    public AdminPanel() {
        setTitle("EPN - Panel de Administración");
        setSize(1100, 650); // Aumentado altura para tabs
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();

        // ---------------- TAB 1: HISTORIAL (Existing Code) ----------------
        JPanel pnlHistory = new JPanel(new BorderLayout(10, 10));

        // --- Panel Norte: Título ---
        JLabel lblTitle = new JLabel("HISTORIAL DE INGRESOS A LA FACULTAD", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        pnlHistory.add(lblTitle, BorderLayout.NORTH);

        // --- Panel Central: Tabla ---
        String[] columns = {
                "ID",
                "Código Único",
                "Estudiante",
                "Carrera",
                "Guardia (ID)",
                "Resultado",
                "Fecha Creación",
                "Estado"
        };

        model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hacer la tabla de solo lectura
            }
        };

        table = new JTable(model);
        table.getTableHeader().setReorderingAllowed(false);
        pnlHistory.add(new JScrollPane(table), BorderLayout.CENTER);

        // --- Panel Sur: Botones ---
        JPanel panelButtons = new JPanel();
        PatButton btnRefresh = new PatButton("Actualizar Datos");
        PatButton btnExit = new PatButton("Cerrar");

        btnRefresh.addActionListener(e -> loadData());
        btnExit.addActionListener(e -> System.exit(0));

        panelButtons.add(btnRefresh);
        panelButtons.add(btnExit);
        pnlHistory.add(panelButtons, BorderLayout.SOUTH);

        // Add to Tabs
        tabbedPane.addTab("Historial de Accesos", pnlHistory);

        // ---------------- TAB 2: GESTIÓN ESTUDIANTES (New Code) ----------------
        tabbedPane.addTab("Gestión de Estudiantes", new StudentManagementPanel());

        // Add Tabs to Frame
        add(tabbedPane);

        // Carga inicial
        loadData();
    }

    private void loadData() {
        try {
            model.setRowCount(0);
            List<RegistroIngresoDTO> registros = blRegistro.obtenerTodos();

            for (RegistroIngresoDTO r : registros) {
                // Variables por defecto si no se encuentra relación
                String codigoUnico = "N/A";
                String nombreCompleto = "Desconocido (ID: " + r.getIdEstudiante() + ")";
                String carrera = "N/A";

                // 1. Identificar al estudiante por su ID
                EstudianteDTO estudiante = blEstudiante.getById(r.getIdEstudiante());
                if (estudiante != null) {
                    codigoUnico = estudiante.getCodigoUnico();
                    nombreCompleto = estudiante.getNombre() + " " + estudiante.getApellido();

                    // 2. Identificar la carrera a través del ID de Periodo del estudiante
                    PeriodoDTO periodo = blPeriodo.getById(estudiante.getIdPeriodo());
                    if (periodo != null) {
                        carrera = periodo.getCarrera();
                    }
                }

                // 3. Crear la fila con los datos combinados
                Object[] row = {
                        r.getIdRegistroIngreso(),
                        codigoUnico, // Extraído de Estudiante
                        nombreCompleto, // Extraído de Estudiante
                        carrera, // Extraído de Periodo
                        r.getIdUsuario(), // ID del Guardia
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