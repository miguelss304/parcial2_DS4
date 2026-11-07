import java.util.Arrays;
import java.util.NoSuchElementException;

// Max-heap: la raiz es el registro que se atiende primero (ver Registro.antes)
public class BinaryHeap {
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

    public Registro consultarMaximo() {
        if (size == 0) throw new NoSuchElementException();
        return a[0];
    }

    public void insertar(Registro r) {
        if (size == a.length) a = Arrays.copyOf(a, 2 * size);
        a[size] = r;
        size++;
        siftUp(a, size - 1);
    }

    static void siftUp(Registro[] a, int i) {
        while (i > 0 && Registro.antes(a[i], a[parent(i)])) {
            swap(a, i, parent(i));
            i = parent(i);
        }
    }

    public Registro extraerMaximo() {
        Registro top = consultarMaximo();
        size--;
        a[0] = a[size];
        a[size] = null;
        siftDown(a, 0, size);
        return top;
    }

    static void siftDown(Registro[] a, int i, int n) {
        while (left(i) < n) {
            int c = left(i);
            if (c + 1 < n && Registro.antes(a[c + 1], a[c])) c++;   // hijo que va primero
            if (Registro.antes(a[i], a[c])) return;                 // el padre ya va antes
            swap(a, i, c);
            i = c;
        }
    }

    // buildHeap: convierte los primeros n elementos de v en heap, de abajo hacia arriba
    static void buildHeap(Registro[] v, int n) {
        for (int i = n / 2 - 1; i >= 0; i--) siftDown(v, i, n);
    }

    // Deja solo los PENDIENTE y reconstruye el heap
    public void quitarNoPendientes() {
        int j = 0;
        for (int i = 0; i < size; i++)
            if (a[i].estado == Registro.Estado.PENDIENTE) a[j++] = a[i];
        for (int i = j; i < size; i++) a[i] = null;
        size = j;
        buildHeap(a, size);
    }

    // COPIA de los elementos (para que Heap-Sort no toque este heap)
    public Registro[] elementos() { return Arrays.copyOf(a, size); }

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
}