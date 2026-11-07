public class Registro {
    public final int id;            // clave del arbol
    public final String nombre;
    public final int prioridad;     // 1 a 10, 10 = mayor urgencia
    public final int padre;         // id del padre; 0 = raiz
    public enum Estado { PENDIENTE, ATENDIDO, CANCELADO }
    public Estado estado = Estado.PENDIENTE;   // todo registro nuevo nace PENDIENTE

    public Registro(int id, String nombre, int prioridad, int padre) {
        if (prioridad < 1 || prioridad > 10) throw new IllegalArgumentException("Prioridad de 1 a 10");
        this.id = id;
        this.nombre = nombre;
        this.prioridad = prioridad;
        this.padre = padre;
    }

    // Para las pruebas de estructuras sueltas (casos D, E, F): padre = 0
    public Registro(int id, String nombre, int prioridad) {
        this(id, nombre, prioridad, 0);
    }

    // Desempate: true si x se atiende ANTES que y
    // (mayor prioridad primero; si empatan, menor id)
    public static boolean antes(Registro x, Registro y) {
        if (x.prioridad != y.prioridad) return x.prioridad > y.prioridad;
        return x.id < y.id;                      // <-- linea del desempate por ID
    }

    @Override
    public String toString() {
        return "#" + id + " " + nombre + " (p=" + prioridad + ") " + estado;
    }
}