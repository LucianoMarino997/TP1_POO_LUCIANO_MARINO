import java.util.LinkedList;

public class Equipo {
    private String nombre;
    private String ciudad;
    private LinkedList<Jugador> jugadores;

    public Equipo(String nombre, String ciudad) {
        this.nombre = nombre;
        this.ciudad = ciudad;
        this.jugadores = new LinkedList<>();
    }

    // Métodos para gestionar los jugadores
    public void agregarJugador(Jugador jugador) {
        jugadores.add(jugador);
    }

    public void eliminarJugador(Jugador jugador) {
        jugadores.remove(jugador);
    }

    public Jugador buscarJugadorPorNombre(String nombre) {
        for (Jugador jugador : jugadores) {
            if (jugador.getNombre().equalsIgnoreCase(nombre)) {
                return jugador;
            }
        }
        return null;
    }

    public int obtenerCantidadJugadores() {
        return jugadores.size();
    }

    public LinkedList<Jugador> obtenerJugadores() {
        return jugadores;
    }

    // Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }
}

   
