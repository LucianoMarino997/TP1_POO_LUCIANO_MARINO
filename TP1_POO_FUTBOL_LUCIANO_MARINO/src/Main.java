import javax.swing.JOptionPane;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        GestorEquipos gestor = new GestorEquipos();

        String[] mainMenuOptions = { "Gestión de Jugadores", "Gestión de Equipos", "Simular Partido", "Salir" };
        String[] jugadorMenuOptions = { "Crear Jugador", "Ver Jugadores Globales", "Volver" };
        String[] equipoMenuOptions = { "Crear Equipo", "Agregar Jugador a Equipo", "Buscar Jugador", "Eliminar Jugador", "Generar Jugadores para Equipos", "Eliminar Equipo", "Volver" };

        int mainOption = 0;

        do {
            mainOption = JOptionPane.showOptionDialog(null, "Seleccione una opción", "Gestión de Equipos de Fútbol", 0, 0, null, mainMenuOptions, mainMenuOptions[0]);

            switch (mainOption) {
                case 0: // Gestión de Jugadores
                    int jugadorOption = 0;
                    do {
                        jugadorOption = JOptionPane.showOptionDialog(null, "Seleccione una opción", "Gestión de Jugadores", 0, 0, null, jugadorMenuOptions, jugadorMenuOptions[0]);

                        switch (jugadorOption) {
                            case 0: // Crear Jugador
                                String nombreJugador = gestor.validarEntradaString("Ingrese el nombre del jugador:");
                                String posicionJugador = gestor.validarEntradaString("Ingrese la posición del jugador:");
                                int numeroCamisetaJugador = gestor.validarEntradaInt("Ingrese el número de camiseta del jugador:", 1, 99);
                                int edadJugador = gestor.validarEntradaInt("Ingrese la edad del jugador:", 18, 40);

                                Jugador jugador = new Jugador(nombreJugador, posicionJugador, numeroCamisetaJugador, edadJugador);
                                gestor.agregarJugadorGlobal(jugador);
                                JOptionPane.showMessageDialog(null, "Jugador creado: " + nombreJugador);
                                break;

                            case 1: // Ver Jugadores Globales
                                List<Jugador> jugadoresGlobales = gestor.obtenerJugadoresGlobales();
                                if (jugadoresGlobales.isEmpty()) {
                                    JOptionPane.showMessageDialog(null, "No hay jugadores creados.");
                                } else {
                                    StringBuilder sb = new StringBuilder("Jugadores Globales:\n");
                                    for (Jugador j : jugadoresGlobales) {
                                        sb.append(j.getNombre()).append(" - ").append(j.getPosicion()).append(" - ").append(j.getNumeroCamiseta()).append("\n");
                                    }
                                    JOptionPane.showMessageDialog(null, sb.toString());
                                }
                                break;
                        }
                    } while (jugadorOption != 2);
                    break;

                case 1: // Gestion de equipos
                    int equipoOption = 0;
                    do {
                        equipoOption = JOptionPane.showOptionDialog(null, "Seleccione una opción", "Gestión de Equipos", 0, 0, null, equipoMenuOptions, equipoMenuOptions[0]);

                        switch (equipoOption) {
                            case 0: // Crear equipo
                                String nombreEquipo = gestor.validarEntradaString("Ingrese el nombre del equipo:");
                                String ciudadEquipo = gestor.validarEntradaString("Ingrese la ciudad del equipo:");

                                Equipo equipo = new Equipo(nombreEquipo, ciudadEquipo);
                                gestor.agregarEquipo(equipo);
                                JOptionPane.showMessageDialog(null, "Equipo creado: " + nombreEquipo);
                                break;

                            case 1: // Agregar Jugador a equipo
                                if (gestor.obtenerEquipos().isEmpty()) {
                                    JOptionPane.showMessageDialog(null, "No hay equipos disponibles para agregar jugadores.");
                                    break;
                                }

                                String[] equiposStringAgregar = gestor.obtenerEquipos().stream().map(Equipo::getNombre).toArray(String[]::new);
                                String nombreEquipoAgregar = (String) JOptionPane.showInputDialog(null, "Seleccione el equipo", "Agregar Jugador", JOptionPane.QUESTION_MESSAGE, null, equiposStringAgregar, equiposStringAgregar[0]);

                                Equipo equipoAgregar = gestor.buscarEquipoPorNombre(nombreEquipoAgregar);
                                if (equipoAgregar != null) {
                                    List<Jugador> jugadoresGlobales = gestor.obtenerJugadoresGlobales();
                                    if (jugadoresGlobales.isEmpty()) {
                                        JOptionPane.showMessageDialog(null, "No hay jugadores disponibles para agregar.");
                                    } else {
                                        String[] jugadoresString = jugadoresGlobales.stream().map(Jugador::getNombre).toArray(String[]::new);
                                        String nombreJugadorAgregar = (String) JOptionPane.showInputDialog(null, "Seleccione el jugador a agregar", "Agregar Jugador", JOptionPane.QUESTION_MESSAGE, null, jugadoresString, jugadoresString[0]);
                                        Jugador jugadorSeleccionado = jugadoresGlobales.stream().filter(j -> j.getNombre().equals(nombreJugadorAgregar)).findFirst().orElse(null);

                                        if (jugadorSeleccionado != null) {
                                            equipoAgregar.agregarJugador(jugadorSeleccionado);
                                            JOptionPane.showMessageDialog(null, "Jugador agregado: " + jugadorSeleccionado.getNombre());
                                        }
                                    }
                                }
                                break;

                            case 2: // Buscar Jugador
                                if (gestor.obtenerEquipos().isEmpty()) {
                                    JOptionPane.showMessageDialog(null, "No hay equipos disponibles.");
                                    break;
                                }

                                String[] equiposStringBuscar = gestor.obtenerEquipos().stream().map(Equipo::getNombre).toArray(String[]::new);
                                String nombreEquipoBuscar = (String) JOptionPane.showInputDialog(null, "Seleccione el equipo", "Buscar Jugador", JOptionPane.QUESTION_MESSAGE, null, equiposStringBuscar, equiposStringBuscar[0]);
                                Equipo equipoBuscar = gestor.buscarEquipoPorNombre(nombreEquipoBuscar);

                                if (equipoBuscar != null) {
                                    if (equipoBuscar.obtenerJugadores().isEmpty()) {
                                        JOptionPane.showMessageDialog(null, "No hay jugadores en el equipo.");
                                    } else {
                                        String[] jugadoresEquipoString = equipoBuscar.obtenerJugadores().stream().map(Jugador::getNombre).toArray(String[]::new);
                                        String nombreJugadorBuscar = (String) JOptionPane.showInputDialog(null, "Seleccione el jugador", "Buscar Jugador", JOptionPane.QUESTION_MESSAGE, null, jugadoresEquipoString, jugadoresEquipoString[0]);
                                        Jugador jugadorBuscar = equipoBuscar.buscarJugadorPorNombre(nombreJugadorBuscar);

                                        if (jugadorBuscar != null) {
                                            JOptionPane.showMessageDialog(null, "Jugador encontrado: " + jugadorBuscar.getNombre());
                                        } else {
                                            JOptionPane.showMessageDialog(null, "Jugador no encontrado.");
                                        }
                                    }
                                }
                                break;

                            case 3: // Eliminar Jugador
                                if (gestor.obtenerEquipos().isEmpty()) {
                                    JOptionPane.showMessageDialog(null, "No hay equipos disponibles.");
                                    break;
                                }

                                String[] equiposStringEliminarJugador = gestor.obtenerEquipos().stream().map(Equipo::getNombre).toArray(String[]::new);
                                String nombreEquipoEliminarJugador = (String) JOptionPane.showInputDialog(null, "Seleccione el equipo", "Eliminar Jugador", JOptionPane.QUESTION_MESSAGE, null, equiposStringEliminarJugador, equiposStringEliminarJugador[0]);
                                Equipo equipoEliminarJugador = gestor.buscarEquipoPorNombre(nombreEquipoEliminarJugador);

                                if (equipoEliminarJugador != null) {
                                    if (equipoEliminarJugador.obtenerJugadores().isEmpty()) {
                                        JOptionPane.showMessageDialog(null, "No hay jugadores en el equipo.");
                                    } else {
                                        String[] jugadoresEquipoString = equipoEliminarJugador.obtenerJugadores().stream().map(Jugador::getNombre).toArray(String[]::new);
                                        String nombreJugadorEliminar = (String) JOptionPane.showInputDialog(null, "Seleccione el jugador", "Eliminar Jugador", JOptionPane.QUESTION_MESSAGE, null, jugadoresEquipoString, jugadoresEquipoString[0]);
                                        Jugador jugadorEliminar = equipoEliminarJugador.buscarJugadorPorNombre(nombreJugadorEliminar);

                                        if (jugadorEliminar != null) {
                                            equipoEliminarJugador.eliminarJugador(jugadorEliminar);
                                            JOptionPane.showMessageDialog(null, "Jugador eliminado: " + jugadorEliminar.getNombre());
                                        } else {
                                            JOptionPane.showMessageDialog(null, "Jugador no encontrado.");
                                        }
                                    }
                                }
                                break;

                            case 4: // Generar Jugadores para equipos
                                gestor.completarEquiposConJugadoresAleatorios();
                                break;

                            case 5: // Eliminar equipo
                                if (gestor.obtenerEquipos().isEmpty()) {
                                    JOptionPane.showMessageDialog(null, "No hay equipos disponibles.");
                                    break;
                                }

                                String[] equiposStringEliminarEquipo = gestor.obtenerEquipos().stream().map(Equipo::getNombre).toArray(String[]::new);
                                String nombreEquipoEliminar = (String) JOptionPane.showInputDialog(null, "Seleccione el equipo", "Eliminar Equipo", JOptionPane.QUESTION_MESSAGE, null, equiposStringEliminarEquipo, equiposStringEliminarEquipo[0]);
                                Equipo equipoEliminar = gestor.buscarEquipoPorNombre(nombreEquipoEliminar);

                                if (equipoEliminar != null) {
                                    gestor.eliminarEquipo(equipoEliminar);
                                    JOptionPane.showMessageDialog(null, "Equipo eliminado: " + nombreEquipoEliminar);
                                }
                                break;
                        }
                    } while (equipoOption != 6);
                    break;

                case 2: // Simular Partido
                    if (gestor.obtenerEquipos().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "No hay equipos disponibles.");
                        break;
                    }

                    String[] equiposString = gestor.obtenerEquipos().stream().map(Equipo::getNombre).toArray(String[]::new);
                    String nombreEquipo1 = (String) JOptionPane.showInputDialog(null, "Seleccione el primer equipo", "Simular Partido", JOptionPane.QUESTION_MESSAGE, null, equiposString, equiposString[0]);
                    Equipo equipo1 = gestor.buscarEquipoPorNombre(nombreEquipo1);

                    if (equipo1 == null) {
                        JOptionPane.showMessageDialog(null, "Primer equipo no encontrado.");
                        break;
                    }

                    String nombreEquipo2 = (String) JOptionPane.showInputDialog(null, "Seleccione el segundo equipo", "Simular Partido", JOptionPane.QUESTION_MESSAGE, null, equiposString, equiposString[0]);
                    Equipo equipo2 = gestor.buscarEquipoPorNombre(nombreEquipo2);

                    if (equipo2 == null) {
                        JOptionPane.showMessageDialog(null, "Segundo equipo no encontrado.");
                        break;
                    }

                    gestor.jugarPartido(equipo1, equipo2);
                    break;
            }
        } while (mainOption != 3);
    }
}

