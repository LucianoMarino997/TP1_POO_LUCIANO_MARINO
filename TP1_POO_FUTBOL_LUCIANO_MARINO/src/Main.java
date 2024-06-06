import javax.swing.JOptionPane;

public class Main {
    public static void main(String[] args) {
        GestorEquipos gestor = new GestorEquipos();

        while (true) {
            String[] options = { "Crear Jugador", "Crear Equipo", "Agregar Jugador a Equipo", "Buscar Jugador", "Eliminar Jugador", "Jugar Partido", "Generar Jugadores para Equipos", "Salir" };
            String opcion = (String) JOptionPane.showInputDialog(null, "Seleccione una opción", "Gestión de Equipos de Fútbol", JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

            if (opcion.equals("Salir")) {
                break;
            }

            switch (opcion) {
                case "Crear Jugador":
                    String nombreJugador = JOptionPane.showInputDialog("Ingrese el nombre del jugador:");
                    String posicionJugador = JOptionPane.showInputDialog("Ingrese la posición del jugador:");
                    int numeroCamisetaJugador = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el número de camiseta del jugador:"));
                    int edadJugador = Integer.parseInt(JOptionPane.showInputDialog("Ingrese la edad del jugador:"));

                    @SuppressWarnings("unused") Jugador jugador = new Jugador(nombreJugador, posicionJugador, numeroCamisetaJugador, edadJugador);
                    JOptionPane.showMessageDialog(null, "Jugador creado: " + nombreJugador);
                    break;

                case "Crear Equipo":
                    String nombreEquipo = JOptionPane.showInputDialog("Ingrese el nombre del equipo:");
                    String ciudadEquipo = JOptionPane.showInputDialog("Ingrese la ciudad del equipo:");

                    Equipo equipo = new Equipo(nombreEquipo, ciudadEquipo);
                    gestor.agregarEquipo(equipo);
                    JOptionPane.showMessageDialog(null, "Equipo creado: " + nombreEquipo);
                    break;

                case "Agregar Jugador a Equipo":
                    String nombreEquipoAgregar = JOptionPane.showInputDialog("Ingrese el nombre del equipo:");
                    Equipo equipoAgregar = gestor.buscarEquipoPorNombre(nombreEquipoAgregar);
                    if (equipoAgregar == null) {
                        JOptionPane.showMessageDialog(null, "Equipo no encontrado.");
                    } else {
                        String nombreJugadorAgregar = JOptionPane.showInputDialog("Ingrese el nombre del jugador:");
                        Jugador jugadorAgregar = equipoAgregar.buscarJugadorPorNombre(nombreJugadorAgregar);
                        if (jugadorAgregar != null) {
                            JOptionPane.showMessageDialog(null, "El jugador ya está en el equipo.");
                        } else {
                            String posicionJugadorAgregar = JOptionPane.showInputDialog("Ingrese la posición del jugador:");
                            int numeroCamisetaJugadorAgregar = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el número de camiseta del jugador:"));
                            int edadJugadorAgregar = Integer.parseInt(JOptionPane.showInputDialog("Ingrese la edad del jugador:"));

                            Jugador nuevoJugador = new Jugador(nombreJugadorAgregar, posicionJugadorAgregar, numeroCamisetaJugadorAgregar, edadJugadorAgregar);
                            equipoAgregar.agregarJugador(nuevoJugador);
                            JOptionPane.showMessageDialog(null, "Jugador agregado: " + nombreJugadorAgregar);
                        }
                    }
                    break;

                case "Buscar Jugador":
                    String nombreEquipoBuscar = JOptionPane.showInputDialog("Ingrese el nombre del equipo:");
                    Equipo equipoBuscar = gestor.buscarEquipoPorNombre(nombreEquipoBuscar);
                    if (equipoBuscar == null) {
                        JOptionPane.showMessageDialog(null, "Equipo no encontrado.");
                    } else {
                        String nombreJugadorBuscar = JOptionPane.showInputDialog("Ingrese el nombre del jugador:");
                        Jugador jugadorBuscar = equipoBuscar.buscarJugadorPorNombre(nombreJugadorBuscar);
                        if (jugadorBuscar == null) {
                            JOptionPane.showMessageDialog(null, "Jugador no encontrado.");
                        } else {
                            JOptionPane.showMessageDialog(null, "Jugador encontrado: " + jugadorBuscar.getNombre());
                        }
                    }
                    break;

                case "Eliminar Jugador":
                    String nombreEquipoEliminar = JOptionPane.showInputDialog("Ingrese el nombre del equipo:");
                    Equipo equipoEliminar = gestor.buscarEquipoPorNombre(nombreEquipoEliminar);
                    if (equipoEliminar == null) {
                        JOptionPane.showMessageDialog(null, "Equipo no encontrado.");
                    } else {
                        String nombreJugadorEliminar = JOptionPane.showInputDialog("Ingrese el nombre del jugador:");
                        Jugador jugadorEliminar = equipoEliminar.buscarJugadorPorNombre(nombreJugadorEliminar);
                        if (jugadorEliminar == null) {
                            JOptionPane.showMessageDialog(null, "Jugador no encontrado.");
                        } else {
                            equipoEliminar.eliminarJugador(jugadorEliminar);
                            JOptionPane.showMessageDialog(null, "Jugador eliminado: " + nombreJugadorEliminar);
                        }
                    }
                    break;

                case "Jugar Partido":
                    String nombreEquipo1 = JOptionPane.showInputDialog("Ingrese el nombre del primer equipo:");
                    Equipo equipo1 = gestor.buscarEquipoPorNombre(nombreEquipo1);
                    if (equipo1 == null) {
                        JOptionPane.showMessageDialog(null, "Primer equipo no encontrado.");
                        break;
                    }

                    String nombreEquipo2 = JOptionPane.showInputDialog("Ingrese el nombre del segundo equipo:");
                    Equipo equipo2 = gestor.buscarEquipoPorNombre(nombreEquipo2);
                    if (equipo2 == null) {
                        JOptionPane.showMessageDialog(null, "Segundo equipo no encontrado.");
                        break;
                    }

                    gestor.jugarPartido(equipo1, equipo2);
                    break;

                case "Generar Jugadores para Equipos":
                    gestor.completarEquiposConJugadoresAleatorios();
                    break;
            }
        }
    }
}

