import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;

public class ArbolGeneral {
    private static class Node {
        Registro reg;
        List<Node> hijos = new ArrayList<>();
        Node(Registro reg) { this.reg = reg; } // Constructor
    }

    private Node root;

    public boolean isEmpty() {
        return root == null;
    }

    // Busca un nodo por id en todo el arbol
    private Node find(Node p, int id) {
        if (p == null) return null;
        if (p.reg.id == id) return p;
        for (Node h : p.hijos) {
            Node r = find(h, id);
            if (r != null) return r;
        }
        return null;
    }

    // idPadre = 0 significa que el registro es la raiz
    public void agregarHijo(Registro r, int idPadre) {
        if (find(root, r.id) != null) throw new IllegalArgumentException("Id repetido");
        Node nuevo = new Node(r);
        if (idPadre == 0) {
            if (root != null) throw new IllegalStateException("Ya existe la raiz");
            root = nuevo;
        } else {
            Node padre = find(root, idPadre);
            if (padre == null) throw new NoSuchElementException("No existe el padre");
            padre.hijos.add(nuevo);
        }
    }

    public void preorden() {
        preorden(root);
        System.out.println();
    }
    private void preorden(Node p) {
        if (p == null) return;
        System.out.print(p.reg.id + " ");      // primero el nodo
        for (Node h : p.hijos) preorden(h);    // despues los hijos
    }

    public void postorden() {
        postorden(root);
        System.out.println();
    }
    private void postorden(Node p) {
        if (p == null) return;
        for (Node h : p.hijos) postorden(h);   // primero los hijos
        System.out.print(p.reg.id + " ");      // despues el nodo
    }

    public void porNiveles() {
        if (root == null) { System.out.println(); return; }
        Queue<Node> cola = new LinkedList<>();
        cola.add(root);
        while (!cola.isEmpty()) {
            Node p = cola.poll();
            System.out.print(p.reg.id + " ");
            cola.addAll(p.hijos);
        }
        System.out.println();
    }

    public int height() { return height(root); }
    private int height(Node p) {
        if (p == null) return -1;              // arbol vacio
        int max = -1;                          // sin hijos: -1 + 1 = 0
        for (Node h : p.hijos) max = Math.max(max, height(h));
        return 1 + max;
    }

    public int contarHojas() { return contarHojas(root); }
    private int contarHojas(Node p) {
        if (p == null) return 0;
        if (p.hijos.isEmpty()) return 1;       // hoja
        int total = 0;
        for (Node h : p.hijos) total += contarHojas(h);
        return total;
    }

    // Cancela el nodo y sus descendientes pendientes. Devuelve cuantos cancelo
    public int cancelarSubarbol(int id) {
        Node p = find(root, id);
        if (p == null) return 0;
        return cancelar(p);
    }
    private int cancelar(Node p) {
        int total = 0;
        if (p.reg.estado == Registro.Estado.PENDIENTE) {   // los ATENDIDO conservan su estado
            p.reg.estado = Registro.Estado.CANCELADO;
            total = 1;
        }
        for (Node h : p.hijos) total += cancelar(h);       // siempre baja a los hijos
        return total;
    }

    public static void main(String[] args) {
        ArbolGeneral t = new ArbolGeneral();
        t.agregarHijo(new Registro(1, "Proyecto", 8), 0);
        t.agregarHijo(new Registro(2, "Backend", 7), 1);
        t.agregarHijo(new Registro(3, "Frontend", 6), 1);
        t.agregarHijo(new Registro(4, "Docs", 3), 1);
        t.agregarHijo(new Registro(5, "API", 9), 2);
        t.agregarHijo(new Registro(6, "BD", 8), 2);
        t.agregarHijo(new Registro(7, "UI", 5), 3);

        t.preorden();                          // 1 2 5 6 3 7 4
        t.postorden();                         // 5 6 2 7 3 4 1
        t.porNiveles();                        // 1 2 3 4 5 6 7
        System.out.println(t.height());        // 2
        System.out.println(t.contarHojas());   // 4
        System.out.println(t.cancelarSubarbol(2)); // 3
        System.out.println(t.cancelarSubarbol(2)); // 0
    }
}