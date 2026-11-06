public class BST {
    private static class Node {
        Registro reg;
        Node left;
        Node right;
        Node(Registro reg) { this.reg = reg; } // Constructor
    }

    private Node root;

    public boolean isEmpty() {
        return root == null;
    }

    // Devuelve el registro con ese id, o null si no existe
    public Registro buscar(int id) {
        Node p = root;
        while (p != null) {
            if (id == p.reg.id) return p.reg;
            if (id > p.reg.id) p = p.right;
            else p = p.left;
        }
        return null;
    }

    // Devuelve false si la clave ya existe (no se admiten repetidas)
    public void insertar(Registro r) {
        if (buscar(r.id) != null) throw new IllegalArgumentException("Id repetido");
        root = insertar(root, r);
    }
    private Node insertar(Node p, Registro r) {
        if (p == null) return new Node(r);
        if (r.id < p.reg.id) p.left = insertar(p.left, r);
        else p.right = insertar(p.right, r);
        return p;
    }

    // Devuelve false si el id no existe
    public boolean eliminar(int id) {
        if (buscar(id) == null) throw new IllegalArgumentException("Id no encontrado");
        root = eliminar(root, id);
        return true;
    }
    private Node eliminar(Node p, int id) {
        if (p == null) return null;
        if (id < p.reg.id) p.left = eliminar(p.left, id);
        else if (id > p.reg.id) p.right = eliminar(p.right, id);
        else {
            if (p.left == null) return p.right;   // 0 hijos o solo hijo derecho
            if (p.right == null) return p.left;   // solo hijo izquierdo
            Node s = p.right;                     // 2 hijos: sucesor inorden
            while (s.left != null) s = s.left;
            p.reg = s.reg;
            p.right = eliminar(p.right, s.reg.id);
        }
        return p;
    }

    public void inorden() {
        inorden(root);
        System.out.println();
    }
    private void inorden(Node p) {
        if (p == null) return;
        inorden(p.left);
        System.out.print(p.reg.id + " ");
        inorden(p.right);
    }

    // arbol vacio = -1, hoja = 0
    public int altura() { return altura(root); }
    private int altura(Node p) {
        if (p == null) return -1;
        return 1 + Math.max(altura(p.left), altura(p.right));
    }

    public static void main(String[] args) {
        BST t = new BST();
        int[] ids = {50, 30, 70, 20, 40, 60, 80};
        for (int id : ids) t.insertar(new Registro(id, "Tarea " + id, 5));

        t.inorden();                                              // 20 30 40 50 60 70 80
        System.out.println(t.altura());                           // 2
        try {
            t.insertar(new Registro(40, "x", 1));
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        System.out.println(t.buscar(60));                         // #60 Tarea 60 (p=5)
        System.out.println(t.buscar(99));                         // null
        System.out.println(t.eliminar(50));                       // true (2 hijos)
        t.inorden();                                              // 20 30 40 60 70 80
        try {
            t.eliminar(99);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}