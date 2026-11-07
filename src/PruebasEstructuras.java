import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class PruebasEstructuras {
    static int fallos = 0;

    static void verificar(String que, String esperado, String obtenido) {
        boolean ok = esperado.equals(obtenido);
        if (!ok) fallos++;
        System.out.println("  " + que + ": esperado=" + esperado + " obtenido=" + obtenido
                + "  -> " + (ok ? "COINCIDE" : "NO COINCIDE"));
    }

    // Captura lo que imprimen metodos como inorden()
    static String capturar(Runnable r) {
        PrintStream original = System.out;
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        System.setOut(new PrintStream(buf));
        try { r.run(); } finally { System.setOut(original); }
        return buf.toString().trim();
    }

    static Registro reg(int id, int prioridad) {
        return new Registro(id, "R" + id, prioridad);
    }

    // ---------- CASO D ----------
    static void casoD(String nombre, int a, int b, int c, String rotacion) {
        AVL t = new AVL();
        t.insertar(reg(a, 1)); t.insertar(reg(b, 1)); t.insertar(reg(c, 1));
        System.out.println("Caso D (" + nombre + "): inserciones " + a + ", " + b + ", " + c
                + "  [rotacion esperada: " + rotacion + "]");
        verificar("raiz", "20", "" + t.raizId());
        verificar("inorden", "10 20 30", capturar(t::inorden));
        verificar("altura", "1", "" + t.altura());
        verificar("balanceado", "true", "" + t.verificarBalance());
    }

    // ---------- CASO E ----------
    static void casoE() {
        BST bst = new BST();
        AVL avl = new AVL();
        for (int i = 1; i <= 7; i++) { bst.insertar(reg(i, 1)); avl.insertar(reg(i, 1)); }
        System.out.println("Caso E: claves 1..7");
        verificar("altura BST", "6", "" + bst.altura());
        verificar("altura AVL", "2", "" + avl.altura());
        verificar("inorden BST", "1 2 3 4 5 6 7", capturar(bst::inorden));
        verificar("inorden AVL", "1 2 3 4 5 6 7", capturar(avl::inorden));
        bst.eliminar(4);
        avl.eliminar(4);
        System.out.println("  -- despues de eliminar la clave 4 --");
        verificar("buscar(4) en BST es null", "true", "" + (bst.buscar(4) == null));
        verificar("buscar(4) en AVL es null", "true", "" + (avl.buscar(4) == null));
        verificar("inorden BST", "1 2 3 5 6 7", capturar(bst::inorden));
        verificar("inorden AVL", "1 2 3 5 6 7", capturar(avl::inorden));
        verificar("AVL balanceado", "true", "" + avl.verificarBalance());
    }

    // ---------- CASO F ----------
    static String prio(Registro[] v) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < v.length; i++) { if (i > 0) sb.append(", "); sb.append(v[i].prioridad); }
        return sb.append("]").toString();
    }

    static void casoF() {
        int[] p = {4, 10, 3, 5, 1, 8, 9, 2};
        BinaryHeap h = new BinaryHeap();
        for (int i = 0; i < p.length; i++) h.insertar(reg(i + 1, p[i]));
        System.out.println("Caso F: prioridades [4,10,3,5,1,8,9,2]");
        System.out.println("  arreglo interno del heap: " + h);
        verificar("raiz", "10", "" + h.consultarMaximo().prioridad);
        String antes = h.toString();
        verificar("Heap-Sort ascendente", "[1, 2, 3, 4, 5, 8, 9, 10]", prio(HeapSort.ordenar(h.elementos())));
        verificar("heap intacto tras Heap-Sort", antes, h.toString());
        h.extraerMaximo();
        verificar("raiz tras extraer", "9", "" + h.consultarMaximo().prioridad);
    }

    public static void main(String[] args) {
        System.out.println("=== CASO D: Rotaciones AVL ===");
        casoD("LL", 30, 20, 10, "rotacion derecha en 30");
        casoD("RR", 10, 20, 30, "rotacion izquierda en 10");
        casoD("LR", 30, 10, 20, "izquierda en 10, luego derecha en 30");
        casoD("RL", 10, 30, 20, "derecha en 30, luego izquierda en 10");
        System.out.println("\n=== CASO E: BST frente a AVL ===");
        casoE();
        System.out.println("\n=== CASO F: Heap y Heap-Sort ===");
        casoF();
        System.out.println("\nRESUMEN: " + (fallos == 0 ? "todo coincide" : fallos + " verificaciones NO coinciden"));
    }
}