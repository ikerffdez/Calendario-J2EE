package edu.ucam.actions;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import edu.ucam.commons.Constantes;
import edu.ucam.commons.Singleton;
import edu.ucam.modelo.Reserva;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class EditarReserva extends Actions {
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		List<Reserva> reservas = (List<Reserva>) Singleton.getReservaDAO().obtenerListaReserva();
		int idReserva = Integer.parseInt(request.getParameter(Constantes.ID_SLOT));
		int idEspacio = Integer.parseInt(request.getParameter(Constantes.ID_ESPACIO));
		String idUsuario = request.getParameter(Constantes.EMAIL);
		LocalDateTime fechaInicio = LocalDateTime.parse(request.getParameter(Constantes.FECHA_INICIO));
		LocalDateTime fechaFin = LocalDateTime.parse(request.getParameter(Constantes.FECHA_FIN));
		
		boolean horarioIncorrecto = false;
		boolean encontrado = false;

		if (reservas != null) {
			for (Reserva r : reservas) {
				if (r.getIdReserva() == idReserva) {
					encontrado = true;

					//si no hay cambios...
					if (r.getFechaInicio().equals(fechaInicio) && r.getFechaFin().equals(fechaFin)) {
						request.setAttribute(Constantes.MENSAJE_ERROR, "Usted no ha editado ninguna característica de la reserva");
					} else {
						// Verificar conflicto con otras reservas (excepto la actual)
						for (Reserva otra : reservas) {
							if (otra.getIdReserva() != idReserva && otra.getIdEspacio() == idEspacio) {
								//no es la misma reserva, pero si el mismo espacio
								System.out.println("Inicio: "+fechaInicio.toString());
								System.out.println("Fin: "+fechaFin.toString());
								if((fechaInicio.isAfter(otra.getFechaInicio()) && fechaInicio.isBefore(otra.getFechaFin())) || 
										(fechaFin.isAfter(otra.getFechaInicio()) && fechaFin.isBefore(otra.getFechaFin())) ||
										(fechaInicio.isBefore(otra.getFechaInicio()) && fechaFin.isAfter(otra.getFechaFin()))) {
									horarioIncorrecto = true;
									break;
								}
							}else {
								if(fechaInicio.equals(fechaFin) || 
									fechaInicio.isAfter(fechaFin)) {
									horarioIncorrecto = true;
									break;
								}
							}
						}

						if (!horarioIncorrecto) {
							Reserva nuevaReserva = new Reserva(idReserva, idEspacio, idUsuario, fechaInicio, fechaFin);
							Singleton.getReservaDAO().editarReserva(nuevaReserva);
							request.setAttribute(Constantes.MENSAJE, "La reserva ha sido editada con éxito");
						} else {
							request.setAttribute(Constantes.MENSAJE_ERROR, "Imposible cambiar el horario, el nuevo horario invade otra reserva o tiene alguna incongruencia");
						}
					}
					break;
				}
			}

			if (!encontrado) {
				request.setAttribute(Constantes.MENSAJE_ERROR, "Reserva no encontrada");
			}
		} else {
			request.setAttribute(Constantes.MENSAJE_ERROR, "Error al intentar editar la reserva");
		}

		try {
			request.getRequestDispatcher("WEB-INF//jsp//reservas.jsp").forward(request, response);
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}
	}
}

