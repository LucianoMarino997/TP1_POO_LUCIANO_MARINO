import javax.swing.JOptionPane;
import java.util.List;

public class Main {
	public static void main(String[] args) {
		GestorEquipos gestor = new GestorEquipos();

		while (true) {
			String[] options = { "Crear Jugador", "Crear Equipo", "Agregar Jugador a Equipo", "Buscar Jugador",
					"Eliminar Jugador", "Jugar Partido", "Generar Jugadores para Equipos", "Salir" };
			String opcion = (String) JOptionPane.showInputDialog(null, "Seleccione una opción",
					"Gestión de Equipos de Fútbol", JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

			if (opcion == null || opcion.equals("Salir")) {
				break;
			}

			switch (opcion) {
			case "Crear Jugador":
				String nombreJugador = gestor.validarEntradaString("Ingrese el nombre del jugador:");
				String posicionJugador = gestor.validarEntradaString("Ingrese la posición del jugador:");
				int numeroCamisetaJugador = gestor.validarEntradaInt("Ingrese el número de camiseta del jugador:", 1,
						99);
				int edadJugador = gestor.validarEntradaInt("Ingrese la edad del jugador:", 18, 40);

				Jugador jugador = new Jugador(nombreJugador, posicionJugador, numeroCamisetaJugador, edadJugador);
				gestor.agregarJugadorGlobal(jugador); // Agregar jugador a la lista global
				JOptionPane.showMessageDialog(null, "Jugador creado y agregado a la lista global: " + nombreJugador);
				break;

			case "Crear Equipo":
				String nombreEquipo = gestor.validarEntradaString("Ingrese el nombre del equipo:");
				String ciudadEquipo = gestor.validarEntradaString("Ingrese la ciudad del equipo:");

				Equipo equipo = new Equipo(nombreEquipo, ciudadEquipo);
				gestor.agregarEquipo(equipo);
				JOptionPane.showMessageDialog(null, "Equipo creado: " + nombreEquipo);
				break;

			case "Agregar Jugador a Equipo":
				String nombreEquipoAgregar = gestor.validarEntradaString("Ingrese el nombre del equipo:");
				Equipo equipoAgregar = gestor.buscarEquipoPorNombre(nombreEquipoAgregar);
				if (equipoAgregar == null) {
					JOptionPane.showMessageDialog(null, "Equipo no encontrado.");
				} else {
					List<Jugador> jugadoresGlobales = gestor.obtenerJugadoresGlobales();
					if (jugadoresGlobales.isEmpty()) {
						JOptionPane.showMessageDialog(null, "No hay jugadores disponibles en la lista global.");
					} else {
						String[] nombresJugadores = jugadoresGlobales.stream().map(Jugador::getNombre)
								.toArray(String[]::new);
						String nombreJugadorSeleccionado = (String) JOptionPane.showInputDialog(null,
								"Seleccione un jugador:", "Jugadores Disponibles", JOptionPane.QUESTION_MESSAGE, null,
								nombresJugadores, nombresJugadores[0]);
						Jugador jugadorSeleccionado = jugadoresGlobales.stream()
								.filter(j -> j.getNombre().equals(nombreJugadorSeleccionado)).findFirst().orElse(null);
						if (jugadorSeleccionado != null) {
							equipoAgregar.agregarJugador(jugadorSeleccionado);
							JOptionPane.showMessageDialog(null,
									"Jugador agregado al equipo: " + jugadorSeleccionado.getNombre());
						}
					}
				}
				break;

			case "Buscar Jugador":
				String nombreEquipoBuscar = gestor.validarEntradaString("Ingrese el nombre del equipo:");
				Equipo equipoBuscar = gestor.buscarEquipoPorNombre(nombreEquipoBuscar);
				if (equipoBuscar == null) {
					JOptionPane.showMessageDialog(null, "Equipo no encontrado.");
				} else {
					String nombreJugadorBuscar = gestor.validarEntradaString("Ingrese el nombre del jugador:");
					Jugador jugadorBuscar = equipoBuscar.buscarJugadorPorNombre(nombreJugadorBuscar);
					if (jugadorBuscar == null) {
						JOptionPane.showMessageDialog(null, "Jugador no encontrado.");
					} else {
						JOptionPane.showMessageDialog(null, "Jugador encontrado: " + jugadorBuscar.getNombre());
					}
				}
				break;

			case "Eliminar Jugador":
				String nombreEquipoEliminar = gestor.validarEntradaString("Ingrese el nombre del equipo:");
				Equipo equipoEliminar = gestor.buscarEquipoPorNombre(nombreEquipoEliminar);
				if (equipoEliminar == null) {
					JOptionPane.showMessageDialog(null, "Equipo no encontrado.");
				} else {
					String nombreJugadorEliminar = gestor.validarEntradaString("Ingrese el nombre del jugador:");
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
				String nombreEquipo1 = gestor.validarEntradaString("Ingrese el nombre del primer equipo:");
				Equipo equipo1 = gestor.buscarEquipoPorNombre(nombreEquipo1);
				if (equipo1 == null) {
					JOptionPane.showMessageDialog(null, "Primer equipo no encontrado.");
					break;
				}

				String nombreEquipo2 = gestor.validarEntradaString("Ingrese el nombre del segundo equipo:");
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
