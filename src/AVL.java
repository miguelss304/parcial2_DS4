public class AVL {
    private static class Node {
        Registro reg;
        Node left;
        Node right;
        int height = 0;                         // hoja = 0
        Node(Registro reg) { this.reg = reg; } // Constructor
    }

    private Node root;

    public boolean isEmpty() {
        return root == null;
    }

    // ---------- utilidades de altura y balance ----------
    private int h(Node p) { return p == null ? -1 : p.height; } // vacio = -1

    private void actualizar(Node p) {
        p.height = 1 + Math.max(h(p.left), h(p.right));
    }

    // factor de balance = altura(izq) - altura(der)
    private int fb(Node p) { return p == null ? 0 : h(p.left) - h(p.right); }

    // ---------- rotaciones ----------
    // LL: desbalance a la izquierda del hijo izquierdo -> rotacion simple a la derecha
    //       y             x
    //      / \           / \
    //     x   C   -->   A   y
    //    / \               / \
    //   A   B             B   C
    private Node rotarDerecha(Node y) {
        Node x = y.left;
        y.left = x.right;
        x.right = y;
        actualizar(y);      // primero y (ahora es hijo), luego x
        actualizar(x);
        return x;
    }

    // RR: desbalance a la derecha del hijo derecho -> rotacion simple a la izquierda
    //     x                 y
    //    / \               / \
    //   A   y     -->     x   C
    //      / \           / \
    //     B   C         A   B
    private Node rotarIzquierda(Node x) {
        Node y = x.right;
        x.right = y.left;
        y.left = x;
        actualizar(x);
        actualizar(y);
        return y;
    }

    // LR: desbalance a la derecha del hijo izquierdo
    // -> rotacion izquierda sobre el hijo izquierdo (queda LL) y luego derecha sobre p
    private Node rotarLR(Node p) {
        p.left = rotarIzquierda(p.left);
        return rotarDerecha(p);
    }

    // RL: desbalance a la izquierda del hijo derecho
    // -> rotacion derecha sobre el hijo derecho (queda RR) y luego izquierda sobre p
    private Node rotarRL(Node p) {
        p.right = rotarDerecha(p.right);
        return rotarIzquierda(p);
    }

    // Actualiza la altura de p y, si |fb| > 1, aplica la rotacion que corresponda
    private Node rebalancear(Node p) {
        actualizar(p);
        int f = fb(p);
        if (f > 1) {                                   // pesa la izquierda
            if (fb(p.left) >= 0) return rotarDerecha(p); // LL (>= 0 tambien cubre eliminar)
            return rotarLR(p);                           // LR
        }
        if (f < -1) {                                  // pesa la derecha
            if (fb(p.right) <= 0) return rotarIzquierda(p); // RR
            return rotarRL(p);                              // RL
        }
        return p;
    }

    // ---------- operaciones ----------
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
    public boolean insertar(Registro r) {
        if (buscar(r.id) != null) return false;
        root = insertar(root, r);
        return true;
    }
    private Node insertar(Node p, Registro r) {
        if (p == null) return new Node(r);
        if (r.id < p.reg.id) p.left = insertar(p.left, r);
        else p.right = insertar(p.right, r);
        return rebalancear(p);      // se rebalancea al volver de la recursion
    }

    // Devuelve false si el id no existe
    public boolean eliminar(int id) {
        if (buscar(id) == null) return false;
        root = eliminar(root, id);
        return true;
    }
    private Node eliminar(Node p, int id) {
        if (p == null) return null;
        if (id < p.reg.id) p.left = eliminar(p.left, id);
        else if (id > p.reg.id) p.right = eliminar(p.right, id);
        else {
            if (p.left == null) return p.right;
            if (p.right == null) return p.left;
            Node s = p.right;                  // sucesor inorden
            while (s.left != null) s = s.left;
            p.reg = s.reg;
            p.right = eliminar(p.right, s.reg.id);
        }
        return rebalancear(p);
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
    public int altura() { return h(root); }

    public int raizId() {
        if (root == null) throw new java.util.NoSuchElementException();
        return root.reg.id;
    }

    // ---------- verificacion ----------
    // Comprueba en cada nodo, con alturas recalculadas desde cero,
    // que el factor de balance este entre -1 y 1 y que la altura guardada sea correcta
    public boolean verificarBalance() { return alturaReal(root) != Integer.MIN_VALUE; }
    private int alturaReal(Node p) {
        if (p == null) return -1;
        int a = alturaReal(p.left);
        int b = alturaReal(p.right);
        if (a == Integer.MIN_VALUE || b == Integer.MIN_VALUE) return Integer.MIN_VALUE;
        if (Math.abs(a - b) > 1) return Integer.MIN_VALUE;   // desbalanceado
        int alt = 1 + Math.max(a, b);
        if (alt != p.height) return Integer.MIN_VALUE;       // altura guardada incorrecta
        return alt;
    }

    // ---------- pruebas ----------
    private static void probar(String caso, int a, int b, int c) {
        AVL t = new AVL();
        t.insertar(new Registro(a, "T" + a, 5));
        t.insertar(new Registro(b, "T" + b, 5));
        t.insertar(new Registro(c, "T" + c, 5));
        System.out.println(caso + ": raiz=" + t.raizId() + " altura=" + t.altura()
                + " balanceado=" + t.verificarBalance());
    }

    public static void main(String[] args) {
        probar("LL", 30, 20, 10);   // raiz=20 altura=1 balanceado=true
        probar("RR", 10, 20, 30);   // raiz=20 altura=1 balanceado=true
        probar("LR", 30, 10, 20);   // raiz=20 altura=1 balanceado=true
        probar("RL", 10, 30, 20);   // raiz=20 altura=1 balanceado=true

        AVL t = new AVL();
        for (int i = 1; i <= 7; i++) t.insertar(new Registro(i, "T" + i, 5));
        t.inorden();                                               // 1 2 3 4 5 6 7
        System.out.println(t.raizId() + " " + t.altura());         // 4 2 (en un BST normal seria altura 6)
        System.out.println(t.insertar(new Registro(3, "x", 1)));   // false (repetida)
        t.eliminar(5);
        t.eliminar(7);
        t.eliminar(6);                                             // al eliminar 6 la raiz 4 queda con fb=2 -> rotacion LL
        t.inorden();                                               // 1 2 3 4
        System.out.println(t.raizId() + " " + t.verificarBalance()); // 2 true
    }
}