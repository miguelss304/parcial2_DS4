public class Main {
    public static void main(String[] args) {
        //Ejemplos de uso en heap y arbol general
        
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