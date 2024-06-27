import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import javax.swing.JOptionPane;

public class GestorEquipos {
    private LinkedList<Equipo> equipos;
    private List<Jugador> jugadoresGlobales;  // Lista global de jugadores
    private static final String[] NOMBRES = {
        "Juan Pérez", "Luis Gómez", "Carlos Díaz", "Miguel Martínez", "José Rodríguez",
        "Pedro Fernández", "Javier García", "Andrés López", "David Sánchez", "Fernando Torres",
        "Alejandro Morales", "Ricardo Ortiz", "Manuel Romero", "Francisco Ríos", "Santiago Campos",
        "Mario Castro", "Jorge Herrera", "Hugo Gutiérrez", "Raúl Ruiz", "Alberto Vega",
        "Iván Pardo", "Oscar Molina", "Gabriel Peña", "Víctor Navarro", "Emilio Escobar",
        "Tomás Ortega", "Rafael Núñez", "Felipe Cruz", "Rubén Castro", "Eduardo Soto",
        "Ángel Flores", "Martín Serrano", "Diego Gil", "Ignacio Delgado", "Daniel Luna",
        "Adrián Suárez", "Héctor Guzmán", "Cristian Morales", "Sebastián Aguilar", "Fabián Jiménez",
        "Nicolás Salinas", "Gonzalo Vázquez", "Lucas Soto", "Ismael Maldonado", "Leandro Márquez",
        "Matías Ramos", "Pablo Valencia", "Samuel Paredes", "Joaquín Figueroa", "Rodrigo Campos"
    };
    private static final String[] POSICIONES = {
        "Arquero", "Defensor", "Mediocampista", "Delantero"
    };
    private static final Random RANDOM = new Random();
    private static final Set<Integer> NUMEROS_CAMISETA = new HashSet<>();

    public GestorEquipos() {
        this.equipos = new LinkedList<>();
        this.jugadoresGlobales = new ArrayList<>();  // Inicializa la lista global de jugadores
    }

    // Métodos para gestionar los equipos
    public void agregarEquipo(Equipo equipo) {
        equipos.add(equipo);
    }

    public void eliminarEquipo(Equipo equipo) {
        equipos.remove(equipo);
    }

    public Equipo buscarEquipoPorNombre(String nombre) {
        for (Equipo equipo : equipos) {
            if (equipo.getNombre().equalsIgnoreCase(nombre)) {
                return equipo;
            }
        }
        return null;
    }

    public int obtenerCantidadEquipos() {
        return equipos.size();
    }

    public LinkedList<Equipo> obtenerEquipos() {
        return equipos;
    }

    // Método para gestionar jugadores globales
    public void agregarJugadorGlobal(Jugador jugador) {
        jugadoresGlobales.add(jugador);
    }

    public List<Jugador> obtenerJugadoresGlobales() {
        return jugadoresGlobales;
    }

    // Método para simular un partido entre dos equipos con desempate
    public void jugarPartido(Equipo equipo1, Equipo equipo2) {
        if (equipo1.obtenerCantidadJugadores() < 11 || equipo2.obtenerCantidadJugadores() < 11) {
            JOptionPane.showMessageDialog(null, "Ambos equipos deben tener al menos 11 jugadores para jugar un partido.");
            return;
        }

        int golesEquipo1 = (int) (Math.random() * 5);
        int golesEquipo2 = (int) (Math.random() * 5);

        String resultado = equipo1.getNombre() + " " + golesEquipo1 + " - " + golesEquipo2 + " " + equipo2.getNombre();
        JOptionPane.showMessageDialog(null, "Resultado del partido:\n" + resultado);

        if (golesEquipo1 == golesEquipo2) {
            JOptionPane.showMessageDialog(null, "El partido terminó en empate. Vamos a los penales.");

            int penalesEquipo1 = 0;
            int penalesEquipo2 = 0;

            // Cada equipo tiene 5 oportunidades
            for (int i = 0; i < 5; i++) {
                if (Math.random() < 0.7) { // 70% de probabilidad de anotar
                    penalesEquipo1++;
                }
                if (Math.random() < 0.7) {
                    penalesEquipo2++;
                }
            }

            // Si sigue empate, se sigue con muerte súbita
            while (penalesEquipo1 == penalesEquipo2) {
                if (Math.random() < 0.7) {
                    penalesEquipo1++;
                }
                if (Math.random() < 0.7) {
                    penalesEquipo2++;
                }
            }

            if (penalesEquipo1 > penalesEquipo2) {
                resultado = equipo1.getNombre() + " ganó en penales " + penalesEquipo1 + " - " + penalesEquipo2 + " contra " + equipo2.getNombre();
            } else {
                resultado = equipo2.getNombre() + " ganó en penales " + penalesEquipo2 + " - " + penalesEquipo1 + " contra " + equipo1.getNombre();
            }
            JOptionPane.showMessageDialog(null, resultado);
        }
    }

    // Método para generar jugadores aleatorios
    public List<Jugador> generarJugadoresAleatorios(int cantidad) {
        List<Jugador> jugadores = new ArrayList<>();
        while (jugadores.size() < cantidad) {
            String nombre = NOMBRES[RANDOM.nextInt(NOMBRES.length)];
            String posicion = POSICIONES[RANDOM.nextInt(POSICIONES.length)];
            int edad = 18 + RANDOM.nextInt(23); // Edad entre 18 y 40
            int numeroCamiseta;
            do {
                numeroCamiseta = 1 + RANDOM.nextInt(99); // Número de camiseta entre 1 y 99
            } while (NUMEROS_CAMISETA.contains(numeroCamiseta));
            NUMEROS_CAMISETA.add(numeroCamiseta);

            jugadores.add(new Jugador(nombre, posicion, numeroCamiseta, edad));
        }
        return jugadores;
    }

    // Método para completar equipos con jugadores aleatorios
    public void completarEquiposConJugadoresAleatorios() {
        for (Equipo equipo : equipos) {
            int jugadoresFaltantes = 11 - equipo.obtenerCantidadJugadores();
            if (jugadoresFaltantes > 0) {
                List<Jugador> jugadoresAleatorios = generarJugadoresAleatorios(jugadoresFaltantes);
                for (Jugador jugador : jugadoresAleatorios) {
                    equipo.agregarJugador(jugador);
                }
                JOptionPane.showMessageDialog(null, "Se han agregado " + jugadoresFaltantes + " jugadores al equipo " + equipo.getNombre());
            }
        }
    }

    // Métodos de validación de entrada
    public String validarEntradaString(String mensaje) {
        String entrada;
        do {
            entrada = JOptionPane.showInputDialog(mensaje);
            if (entrada == null || entrada.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Entrada no válida. Intente de nuevo.");
            }
        } while (entrada == null || entrada.trim().isEmpty());
        return entrada;
    }

    public int validarEntradaInt(String mensaje, int min, int max) {
        int entrada;
        do {
            try {
                entrada = Integer.parseInt(JOptionPane.showInputDialog(mensaje));
                if (entrada < min || entrada > max) {
                    JOptionPane.showMessageDialog(null, "Entrada fuera de rango. Intente de nuevo.");
                    continue;
                }
                return entrada;
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Entrada no válida. Intente de nuevo.");
            }
        } while (true);
    }
}

        		