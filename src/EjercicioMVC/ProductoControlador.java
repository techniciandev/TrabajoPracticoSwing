package EjercicioMVC;

import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;

public class ProductoControlador {

    private ProductoModelo modelo;
    private ProductoVista vista;
    private String rutaArchivo;

    public ProductoControlador(ProductoModelo modelo, ProductoVista vista) {
        this.modelo = modelo;
        this.vista = vista;

        this.vista.getBotonBuscar().addActionListener(e -> seleccionarArchivo());
        this.vista.getBotonCargar().addActionListener(e -> cargarProductosEnTabla());
        this.vista.getModeloTabla().addTableModelListener(e -> {
            if (e.getType() == TableModelEvent.UPDATE) {
                actualizarArchivo();
            }
        });
    }

    public void iniciarTabla() {
        vista.setVisible(true);
    }

    private void seleccionarArchivo() {
        JFileChooser menuArchivos = new JFileChooser();
        int seleccion = menuArchivos.showOpenDialog(vista);

        if (seleccion == JFileChooser.APPROVE_OPTION) {
            File archivo = menuArchivos.getSelectedFile();

            if (!archivo.getName().endsWith(".txt")) {
                JOptionPane.showMessageDialog(vista, "Solo puede seleccionar archivos .txt");
                return;
            }

            vista.getCampoRutaArchivo().setText(archivo.getAbsolutePath());
            this.rutaArchivo = archivo.getAbsolutePath();
        }
    }

    private void cargarProductosEnTabla() {
        if (rutaArchivo == null || rutaArchivo.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Seleccione un archivo primero.");
            return;
        }
        try {
            List<ProductoModelo> productos = ProductoModelo.leerProductosDesdeArchivo(rutaArchivo);
            DefaultTableModel tablaModelo = vista.getModeloTabla();
            tablaModelo.setRowCount(0);
            for (ProductoModelo producto : productos) {
                tablaModelo.addRow(new Object[]{
                    producto.getNombre(),
                    producto.getDescripcion(),
                    producto.getPrecio()
                });
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(vista, "Error al cargar productos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizarArchivo() {
        try {
            DefaultTableModel tablaModelo = vista.getModeloTabla();
            List<ProductoModelo> productos = ProductoModelo.leerProductosDesdeArchivo(rutaArchivo);
            productos.clear();

            for (int i = 0; i < tablaModelo.getRowCount(); i++) {
                String nombre = (String) tablaModelo.getValueAt(i, 0);
                String descripcion = (String) tablaModelo.getValueAt(i, 1);
                double precio = Double.parseDouble(tablaModelo.getValueAt(i, 2).toString());

                if (Double.parseDouble(tablaModelo.getValueAt(i, 2).toString()) < 0) {
                    JOptionPane.showMessageDialog(vista, "El precio no puede ser negativo en la fila " + (i + 1), "Error", JOptionPane.ERROR_MESSAGE);
                    // Si el valor es negativo, se asigna 0.0 directamente en la tabla
                    tablaModelo.setValueAt(0.0, i, 2);
                    continue;
                }
                productos.add(new ProductoModelo(nombre, descripcion, precio));
            }

            ProductoModelo.guardarProductosEnArchivo(rutaArchivo, productos);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(vista, "Error al actualizar el archivo " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
