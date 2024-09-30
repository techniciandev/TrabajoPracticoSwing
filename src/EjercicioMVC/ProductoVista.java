package EjercicioMVC;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class ProductoVista extends JFrame {

    private JTable tablaProductos;
    private DefaultTableModel modeloTabla;
    private JTextField campoRutaArchivo;
    private JButton botonCargar;
    private JButton botonBuscar;

    public ProductoVista() {
        setTitle("Lista de Productos");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Panel superior
        JPanel panelArchivo = new JPanel(new BorderLayout(5, 5));
        panelArchivo.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));

        JLabel etiquetaArchivo = new JLabel("Seleccione un archivo .txt: ");
        panelArchivo.add(etiquetaArchivo, BorderLayout.NORTH);

        campoRutaArchivo = new JTextField();
        campoRutaArchivo.setEditable(false);
        panelArchivo.add(campoRutaArchivo, BorderLayout.CENTER);

        botonBuscar = new JButton("...");
        panelArchivo.add(botonBuscar, BorderLayout.EAST);

        add(panelArchivo, BorderLayout.NORTH);

        // Botón carga de datos
        botonCargar = new JButton("Importar datos de txt");
        JPanel panelBotonCargar = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBotonCargar.add(botonCargar);
        panelArchivo.add(panelBotonCargar, BorderLayout.SOUTH);

        // Modelo de tabla
        modeloTabla = new DefaultTableModel(new Object[]{"Nombre", "Descripción", "Precio"}, 0) {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return columna == 2;
            }
        };
        tablaProductos = new JTable(modeloTabla);

        // Agrego la tabla a panel scrolleable
        JScrollPane panelScrolleable = new JScrollPane(tablaProductos);
        add(panelScrolleable, BorderLayout.CENTER);
    }
    
    //Unos getters
    public DefaultTableModel getModeloTabla() {
        return modeloTabla;
    }
    
    public JTextField getCampoRutaArchivo() {
        return campoRutaArchivo;
    }
    
    public JButton getBotonBuscar() {
        return botonBuscar;
    }
    
    public JButton getBotonCargar() {
        return botonCargar;
    }
}

