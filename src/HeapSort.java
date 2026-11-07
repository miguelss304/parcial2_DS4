import java.util.Arrays;
import java.util.NoSuchElementException;

public class HeapSort {
    private Registro[] a = new Registro[16];
    private int size = 0;

    static int parent(int i) { return (i - 1) / 2; }
    static int left(int i)   { return 2 * i + 1; }
    static int right(int i)  { return 2 * i + 2; }

    static void swap(Registro[] a, int i, int j) {
        Registro t = a[i]; a[i] = a[j]; a[j] = t;
    }

    public int size() { return size; }
    public boolean isEmpty() { return size == 0; }

    // Registro de mayor prioridad (la raiz)
    public Registro max() {
        if (size == 0)
            throw new NoSuchElementException();
        return a[0];
    }

    public void insert(Registro r) {
        if (size == a.length)
            a = Arrays.copyOf(a, 2 * size);   // crece al doble
        a[size] = r;
        size++;
        siftUp(a, size - 1);
    }
    static void siftUp(Registro[] a, int i) {
        while (i > 0 && a[i].prioridad > a[parent(i)].prioridad) {
            swap(a, i, parent(i));
            i = parent(i);
        }
    }

    public Registro extractMax() {
        Registro top = max();       // lanza excepcion si esta vacio
        size--;
        a[0] = a[size];
        a[size] = null;             // libera la posicion que quedo fuera del heap
        siftDown(a, 0, size);
        return top;
    }
    static void siftDown(Registro[] a, int i, int n) {
        while (left(i) < n) {
            int c = left(i);
            if (c + 1 < n && a[c + 1].prioridad > a[c].prioridad) c++;
            if (a[i].prioridad >= a[c].prioridad) return;
            swap(a, i, c);
            i = c;
        }
    }

    // Heap-Sort ascendente por prioridad. Trabaja sobre una copia: no modifica este heap
    public Registro[] heapSort() {
        int n = size;
        Registro[] b = Arrays.copyOf(a, n);
        for (int i = n / 2 - 1; i >= 0; i--)   // construye el max-heap (de abajo hacia arriba)
            siftDown(b, i, n);
        for (int fin = n - 1; fin > 0; fin--) {
            swap(b, 0, fin);                   // el maximo va al final
            siftDown(b, 0, fin);               // se restaura el heap sin contar la parte ya ordenada
        }
        return b;
    }

        // Deja en el heap solo los PENDIENTE y reconstruye el heap
    public void quitarNoPendientes() {
        int j = 0;
        for (int i = 0; i < size; i++)
            if (a[i].estado == Registro.Estado.PENDIENTE) a[j++] = a[i];
        for (int i = j; i < size; i++) a[i] = null;
        size = j;
        for (int i = size / 2 - 1; i >= 0; i--)   // heapify de abajo hacia arriba
            siftDown(a, i, size);
    }

    // Prioridades en el orden del arreglo interno
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < size; i++) {
            if (i > 0) sb.append(", ");
            sb.append(a[i].prioridad);
        }
        return sb.append("]").toString();
    }

    private static String prioridades(Registro[] v) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < v.length; i++) {
            if (i > 0) sb.append(", ");
            sb.append(v[i].prioridad);
        }
        return sb.append("]").toString();
    }

    public static void main(String[] args) {
        int[] prioridades = {4, 10, 3, 5, 1, 8, 9, 2};
        HeapSort h = new HeapSort();
        for (int i = 0; i < prioridades.length; i++)
            h.insert(new Registro(i + 1, "Tarea " + (i + 1), prioridades[i]));  // ids 1..8, todos distintos

        System.out.println(h);                                  // [10, 5, 9, 4, 1, 3, 8, 2]
        System.out.println(h.max().prioridad);                  // 10
        System.out.println(prioridades(h.heapSort()));          // [1, 2, 3, 4, 5, 8, 9, 10]
        System.out.println(h.extractMax().prioridad);           // 10
        System.out.println(h.max().prioridad);                  // 9
        System.out.println(h);                                  // [9, 5, 8, 4, 1, 3, 2]
    }
}