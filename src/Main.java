import java.util.Scanner;

public class Main {
    static ArbolGeneral arbol = new ArbolGeneral();
    static BST bst = new BST();
    static AVL avl = new AVL();
    static BinaryHeap heap = new BinaryHeap();

    // O1
    static void registrar(int id, String nombre, int prioridad, int padre) {
        if (id <= 0)                          { System.out.println("ERROR: el id debe ser positivo (" + id + ")"); return; }
        if (prioridad < 1 || prioridad > 10)  { System.out.println("ERROR: prioridad invalida (" + prioridad + ")"); return; }
        if (avl.buscar(id) != null)           { System.out.println("ERROR: ID duplicado (" + id + ")"); return; }
        if (padre == 0 && !arbol.isEmpty())   { System.out.println("ERROR: ya existe una raiz"); return; }
        if (padre != 0 && avl.buscar(padre) == null) { System.out.println("ERROR: el padre " + padre + " no existe"); return; }

        // Todas las validaciones pasaron: recien ahora se modifican las estructuras
        Registro r = new Registro(id, nombre, prioridad, padre);
        arbol.agregarHijo(r, padre);
        bst.insertar(r);
        avl.insertar(r);
        heap.insertar(r);
        System.out.println("OK: registro " + id);
    }

    // O2
    static void consultar(int id) {
        Registro r = avl.buscar(id);
        if (r == null) { System.out.println("ERROR: el id " + id + " no existe"); return; }
        System.out.println("ID=" + r.id + " NOMBRE=" + r.nombre + " PRIORIDAD=" + r.prioridad
                + " PADRE=" + r.padre + " ESTADO=" + r.estado);
    }

    // O3
    static void siguiente() {
        if (heap.isEmpty()) { System.out.println("SIN PENDIENTES"); return; }
        Registro r = heap.extraerMaximo();
        r.estado = Registro.Estado.ATENDIDO;
        System.out.println("ATENDIDO: " + r.id + " (" + r.nombre + ")");
    }

    // O4
    static void cancelar(int id) {
        if (avl.buscar(id) == null) { System.out.println("ERROR: el id " + id + " no existe"); return; }
        int n = arbol.cancelarSubarbol(id);
        heap.quitarNoPendientes();          // los cancelados ya no pueden ser atendidos
        System.out.println("CANCELADOS: " + n);
    }

    // O5: Heap-Sort sobre una copia; es ascendente, asi que se imprime al reves
    static void reporte() {
        Registro[] v = HeapSort.ordenar(heap.elementos());
        if (v.length == 0) { System.out.println("REPORTE VACIO"); return; }
        StringBuilder sb = new StringBuilder();
        for (int i = v.length - 1; i >= 0; i--)
            sb.append(v[i].id).append("(").append(v[i].prioridad).append(") ");
        System.out.println(sb.toString().trim());
    }

    // O6
    static void estadisticas() {
        System.out.print("Preorden: ");     arbol.preorden();
        System.out.print("Postorden: ");    arbol.postorden();
        System.out.print("Por niveles: ");  arbol.porNiveles();
        System.out.println("Altura arbol general: " + arbol.height());
        System.out.println("Hojas: " + arbol.contarHojas());
        System.out.println("Altura BST: " + bst.altura());
        System.out.println("Altura AVL: " + avl.altura());
        System.out.println("Pendientes: " + heap.size());
    }

    static void ejecutar(String linea) {
        String[] t = linea.trim().split("\\s+");
        String cmd = t[0].toUpperCase();
        try {
            switch (cmd) {
                case "REGISTRAR":
                    if (t.length != 5) { System.out.println("ERROR: uso REGISTRAR id nombre prioridad padre"); break; }
                    registrar(Integer.parseInt(t[1]), t[2], Integer.parseInt(t[3]), Integer.parseInt(t[4]));
                    break;
                case "CONSULTAR":
                    if (t.length != 2) { System.out.println("ERROR: uso CONSULTAR id"); break; }
                    consultar(Integer.parseInt(t[1]));
                    break;
                case "SIGUIENTE":     siguiente(); break;
                case "CANCELAR":
                    if (t.length != 2) { System.out.println("ERROR: uso CANCELAR id"); break; }
                    cancelar(Integer.parseInt(t[1]));
                    break;
                case "REPORTE":       reporte(); break;
                case "ESTADISTICAS":  estadisticas(); break;
                default: System.out.println("ERROR: comando desconocido (" + t[0] + ")");
            }
        } catch (NumberFormatException e) {
            System.out.println("ERROR: id, prioridad y padre deben ser numeros enteros");
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNextLine()) {
            String linea = sc.nextLine().trim();
            if (linea.isEmpty()) continue;
            if (linea.equalsIgnoreCase("SALIR")) break;
            ejecutar(linea);
        }
    }
}