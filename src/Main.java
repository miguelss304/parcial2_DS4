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
        //Ejemplos de uso en heap y arbol general
        HeapSort heap = new HeapSort();
        ArbolGeneral arbolGeneral = new ArbolGeneral();
        
        // O3 SIGUIENTE
        if (heap.isEmpty()) System.out.println("SIN PENDIENTES");
        else {
            Registro r = heap.extractMax();
            r.estado = Registro.Estado.ATENDIDO;
            System.out.println(r);
        }

        // O4 CANCELAR id
        int n = arbolGeneral.cancelarSubarbol(id);
        heap.quitarNoPendientes();
        System.out.println(n + " cancelados");

        // O5 REPORTE: heapSort() es ascendente, el orden de atención es de mayor a menor
        Registro[] v = heap.heapSort();
        for (int i = v.length - 1; i >= 0; i--) System.out.println(v[i]);
    }
}