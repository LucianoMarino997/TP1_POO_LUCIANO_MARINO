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
        "Lionel Messi", "Cristiano Ronaldo", "Neymar Jr", "Kylian Mbappé", "Luka Modric",
        "Sergio Ramos", "Virgil van Dijk", "Mohamed Salah", "Eden Hazard", "Kevin De Bruyne",
        "Robert Lewandowski", "Harry Kane", "Paulo Dybala", "Sadio Mane", "Raheem Sterling",
        "Antoine Griezmann", "Luis Suarez", "Gareth Bale", "Paul Pogba", "Karim Benzema",
        "Manuel Neuer", "Jan Oblak", "Ederson Moraes", "Alisson Becker", "Thibaut Courtois",
        "Gerard Pique", "Sergio Busquets", "Marcelo Vieira", "Trent Alexander-Arnold", "Andrew Robertson",
        "Kalidou Koulibaly", "Leonardo Bonucci", "Matthijs de Ligt", "Joshua Kimmich", "Frenkie de Jong",
        "Thomas Muller", "Jadon Sancho", "Joao Felix", "Pierre-Emerick Aubameyang", "Romelu Lukaku",
        "Son Heung-min", "Philippe Coutinho", "Christian Eriksen", "David Silva", "Marco Verratti",
        "Ciro Immobile", "Lorenzo Insigne", "Dries Mertens", "Zlatan Ibrahimovic", "Edinson Cavani"
    };
    private static final String[] POSICIONES = {
        "Arquero", "Defensor", "Mediocampista", "Delantero"
    };
    private static final Random RANDOM = new Random();
    private static final Set<Integer> NUMEROS_CAMISETA = new HashSet<>();

    public GestorEquipos() {
        this.equipos = new LinkedList<>();
        this.jugadoresGlobales = new ArrayList<>();  // Inicializa la lista global de jugadores para poder ser utilizados
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
    public void jugarPartido(Equipo equipo1, Equipo equipo2, String fase) {
        if (equipo1.obtenerCantidadJugadores() < 11 || equipo2.obtenerCantidadJugadores() < 11) {
            JOptionPane.showMessageDialog(null, "Ambos equipos deben tener al menos 11 jugadores para jugar un partido.");
            return;
        }

        // Mostrar alineaciones
        StringBuilder alineacion1 = new StringBuilder("Alineación de " + equipo1.getNombre() + ":\n");
        for (Jugador j : equipo1.obtenerJugadores()) {
            alineacion1.append(j.getNombre()).append(" - ").append(j.getPosicion()).append(" - ").append(j.getNumeroCamiseta()).append("\n");
        }

        StringBuilder alineacion2 = new StringBuilder("Alineación de " + equipo2.getNombre() + ":\n");
        for (Jugador j : equipo2.obtenerJugadores()) {
            alineacion2.append(j.getNombre()).append(" - ").append(j.getPosicion()).append(" - ").append(j.getNumeroCamiseta()).append("\n");
        }

        JOptionPane.showMessageDialog(null, "Partido de " + fase + ":\n" + alineacion1.toString() + "\n" + alineacion2.toString());

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
                numeroCamiseta = 1 + RANDOM.nextInt(99); 
            } while (NUMEROS_CAMISETA.contains(numeroCamiseta));
            NUMEROS_CAMISETA.add(numeroCamiseta);

            Jugador jugador = new Jugador(nombre, posicion, numeroCamiseta, edad);
            jugadores.add(jugador);
            agregarJugadorGlobal(jugador); // Agregar el jugador a la lista global
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

    // Método para jugar un torneo
    public void jugarTorneo() {
        if (equipos.size() < 4) {
            JOptionPane.showMessageDialog(null, "Se necesitan al menos cuatro equipos para jugar un torneo.");
            return;
        }

        // Verificar si el número de equipos es 4, 8, 16 o 32
        int numEquipos = equipos.size();
        if (numEquipos != 4 && numEquipos != 8 && numEquipos != 16 && numEquipos != 32) {
            JOptionPane.showMessageDialog(null, "El número de equipos debe ser 4, 8, 16 o 32 para un torneo válido.");
            return;
        }

        LinkedList<Equipo> equiposTorneo = new LinkedList<>(equipos);
        while (equiposTorneo.size() > 1) {
            String fase = determinarFase(equiposTorneo.size());
            LinkedList<Equipo> ganadores = new LinkedList<>();
            for (int i = 0; i < equiposTorneo.size(); i += 2) {
                if (i + 1 < equiposTorneo.size()) {
                    Equipo equipo1 = equiposTorneo.get(i);
                    Equipo equipo2 = equiposTorneo.get(i + 1);
                    jugarPartido(equipo1, equipo2, fase);

                    int golesEquipo1 = (int) (Math.random() * 5);
                    int golesEquipo2 = (int) (Math.random() * 5);
                    Equipo equipoGanador;
                    if (golesEquipo1 > golesEquipo2) {
                        equipoGanador = equipo1;
                    } else {
                        equipoGanador = equipo2;
                    }
                    ganadores.add(equipoGanador);

                    if (equiposTorneo.size() > 2) { // Solo mostrar el mensaje si no es la final
                        JOptionPane.showMessageDialog(null, "El equipo " + equipoGanador.getNombre() + " pasa de ronda en " + fase + "!");
                    }
                } else {
                    ganadores.add(equiposTorneo.get(i));
                }
            }
            equiposTorneo = ganadores;
        }
        Equipo campeon = equiposTorneo.getFirst();
        JOptionPane.showMessageDialog(null, "El campeón del torneo es: " + campeon.getNombre()+"!!");
    }
    
    private String determinarFase(int numEquipos) {
        switch (numEquipos) {
            case 32:
                return "16avos de final";
            case 16:
                return "octavos de final";
            case 8:
                return "cuartos de final";
            case 4:
                return "semifinal";
            case 2:
                return "final";
            default:
                return "";
        }
    }



    // Métodos de validación de entrada
    public String validarEntradaString(String mensaje) {
        String entrada;
        do {
            entrada = JOptionPane.showInputDialog(mensaje);
            if (entrada == null) return null;
            if (entrada.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Entrada no válida. Intente de nuevo.");
            }
        } while (entrada.trim().isEmpty());
        return entrada;
    }

    public Integer validarEntradaInt(String mensaje, int min, int max) {
        Integer entrada;
        do {
            try {
                String input = JOptionPane.showInputDialog(mensaje);
                if (input == null) return null;
                entrada = Integer.parseInt(input);
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

        		