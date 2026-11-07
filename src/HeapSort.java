public class HeapSort {
    // Ordena el arreglo recibido (hay que pasarle una COPIA, p. ej. heap.elementos()).
    // Resultado: el que se atiende ULTIMO queda primero y el que se atiende PRIMERO queda al final.
    // Para el reporte se recorre al reves.
    public static Registro[] ordenar(Registro[] b) {
        int n = b.length;
        BinaryHeap.buildHeap(b, n);
        for (int fin = n - 1; fin > 0; fin--) {
            BinaryHeap.swap(b, 0, fin);        // la raiz va al final
            BinaryHeap.siftDown(b, 0, fin);    // se restaura el heap sin la parte ya ordenada
        }
        return b;
    }
}