import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;

public class ArbolGeneral {
    private static class Node {
        int id;
        String nombre;
        int prioridad;          // 1 a 10, 10 = mayor urgencia
        boolean cancelado;      // false = pendiente
        List<Node> hijos = new ArrayList<>();

        Node(int id, String nombre, int prioridad) { // Constructor
            this.id = id;
            this.nombre = nombre;
            this.prioridad = prioridad;
        }
    }

    private Node root;

    public boolean isEmpty() {
        return root == null;
    }

    // Busca un nodo por id en todo el arbol
    private Node find(Node p, int id) {
        if (p == null) return null;
        if (p.id == id) return p;
        for (Node h : p.hijos) {
            Node r = find(h, id);
            if (r != null) return r;
        }
        return null;
    }

    // idPadre = 0 significa que el registro es la raiz
    public void agregarHijo(int id, String nombre, int prioridad, int idPadre) {
        if (prioridad < 1 || prioridad > 10) throw new IllegalArgumentException("Prioridad de 1 a 10");
        if (find(root, id) != null) throw new IllegalArgumentException("Id repetido");
        Node nuevo = new Node(id, nombre, prioridad);
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
        System.out.print(p.id + " ");          // primero el nodo
        for (Node h : p.hijos) preorden(h);    // Imprime el arreglo de los hijos
    }

    public void postorden() {
        postorden(root);
        System.out.println();
    }
    private void postorden(Node p) {
        if (p == null) return;
        for (Node h : p.hijos) postorden(h);   // primero los hijos
        System.out.print(p.id + " ");          // despues el nodo
    }

    public void porNiveles() {
        if (root == null) { System.out.println(); return; }
        Queue<Node> cola = new LinkedList<>();
        cola.add(root);
        while (!cola.isEmpty()) {
            Node p = cola.poll();
            System.out.print(p.id + " ");
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
        if (!p.cancelado) {                    // solo si estaba pendiente
            p.cancelado = true;
            total = 1;
        }
        for (Node h : p.hijos) total += cancelar(h);
        return total;
    }


}