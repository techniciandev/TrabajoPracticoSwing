/**
 *
 * @author Carlos I. M., Tatiana L., Carolina A., Facundo K., Gabriel Z.
 * Un gran equipo
 
 */
package EjercicioMVC;

public class Inventario {
    public static void main(String[] args) {
        ProductoModelo modelo = new ProductoModelo("", "", 0.0);
        ProductoVista vista = new ProductoVista();
        ProductoControlador controlador = new ProductoControlador(modelo, vista);
        
        controlador.iniciarTabla();
    }
}
