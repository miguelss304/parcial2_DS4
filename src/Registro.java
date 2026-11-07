public class Registro {
    public final int id;            // clave del arbol
    public final String nombre;
    public final int prioridad;     // 1 a 10, 10 = mayor urgencia
    public enum Estado { PENDIENTE, ATENDIDO,CANCELADO };  // false = pendiente (la usa ArbolGeneral)
    public Estado estado;

    public Registro(int id, String nombre, int prioridad) {
        if (prioridad < 1 || prioridad > 10) throw new IllegalArgumentException("Prioridad de 1 a 10");
        this.id = id;
        this.nombre = nombre;
        this.prioridad = prioridad;
    }

    @Override
    public String toString() {
        return "#" + id + " " + nombre + " (p=" + prioridad + ")" + estado;
    }
}