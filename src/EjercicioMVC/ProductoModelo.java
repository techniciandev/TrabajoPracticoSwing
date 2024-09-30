package EjercicioMVC;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProductoModelo {

    private String nombre;
    private String descripcion;
    private double precio;

    // Constructor
    public ProductoModelo(String nombre, String descripcion, double precio) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
    }

    // Getters
    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    // Método para cargar desde archivo
    public static List<ProductoModelo> leerProductosDesdeArchivo(String rutaArchivo) throws IOException {
        List<ProductoModelo> productos = new ArrayList<>(); // Uso arraylist porque un mismo producto puede estar repetido
        File archivoProductos = new File(rutaArchivo);

        if (archivoProductos.length() == 0) {
            throw new IOException("El archivo está vacío. No se pueden cargar datos.");
        }

        try {
            BufferedReader lector = new BufferedReader(new FileReader(rutaArchivo));
            String lineaLeida;
            while ((lineaLeida = lector.readLine()) != null && !lineaLeida.isEmpty()) {
                String[] infoProductos = lineaLeida.split(",");

                if (infoProductos.length != 3) {
                    throw new IOException("Formato de archivo inválido. Revise el contenido.");
                }

                String nombre = infoProductos[0];
                String descripcion = infoProductos[1];
                double precio;

                try {
                    precio = Double.parseDouble(infoProductos[2]);
                } catch (NumberFormatException e) {
                    throw new IOException("Formato de precio inválido. Revise la línea " + lineaLeida);
                }

                productos.add(new ProductoModelo(nombre, descripcion, precio));
            }
            lector.close();
        } catch(IOException e) {
            throw new IOException("Error en lectura de archivo.");
        }
        return productos;
    }

    // Método para guardar productos en un archivo
    public static void guardarProductosEnArchivo(String rutaArchivo, List<ProductoModelo> productos) throws IOException {
        BufferedWriter escritor = new BufferedWriter(new FileWriter(rutaArchivo));
        for (ProductoModelo producto : productos) {
            escritor.write(producto.getNombre() + "," + producto.getDescripcion() + "," + producto.getPrecio());
            escritor.newLine();
        }
        escritor.close();
    }
}

