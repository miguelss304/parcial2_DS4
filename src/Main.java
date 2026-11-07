import java.util.Scanner;

public class Main {
    public static int inputOpcion() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Ingrese una opción (1-5): ");
            if (!scanner.hasNextLine()) {
                return 5;
            }
            try {
                int opcion = Integer.parseInt(scanner.nextLine().trim());
                if (opcion >= 1 && opcion <= 5) {
                    return opcion;
                }
                System.out.println("Opción no válida. Ingrese un número del 1 al 5.");
            } catch (NumberFormatException e) {
                System.out.println("Entrada no válida. Ingrese un número del 1 al 5.");
            }
        }
    }
    public static void main(String[] args) {
        System.out.println("Bienvenido al sistema de gestión de registros.");
        System.out.println("Por favor, seleccione una opción del menú:");
        System.out.println("1. Insertar un registro");
        System.out.println("2. Eliminar un registro");
        System.out.println("3. Buscar un registro");
        System.out.println("4. Mostrar todos los registros");
        System.out.println("5. Salir");

        int opcion = inputOpcion();

        switch (opcion) {
            case 1:
                // Lógica para insertar un registro
                System.out.println("Insertando un registro...");
                break;
            case 2:
                // Lógica para eliminar un registro
                System.out.println("Eliminando un registro...");
                break;
            case 3:
                // Lógica para buscar un registro
                System.out.println("Buscando un registro...");
                break;
            case 4:
                // Lógica para mostrar todos los registros
                System.out.println("Mostrando todos los registros...");
                break;
            case 5:
                // Lógica para salir del programa
                System.out.println("Saliendo del programa...");
                break;
            default:
                System.out.println("Opción no válida.");
                break;
        }
    }
}