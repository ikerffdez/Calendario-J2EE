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

public class CrearReserva extends Actions{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		// TODO Auto-generated method stub
		List<Reserva> reservas = (List<Reserva>)Singleton.getReservaDAO().obtenerListaReserva();
		
		int idEspacio = Integer.parseInt(request.getParameter(Constantes.ID_ESPACIO));
		String idUsuario = (String)	request.getParameter(Constantes.EMAIL);
		LocalDateTime fechaInicio = LocalDateTime.parse(request.getParameter(Constantes.FECHA_INICIO));
		LocalDateTime fechaFin = LocalDateTime.parse(request.getParameter(Constantes.FECHA_FIN));
		boolean horarioIncorrecto = false;
		
		if(reservas != null) {
			for(Reserva r : reservas) {
				if (r.getIdEspacio() == idEspacio) {
					//comprobamos disponibilidad del espacio
					if((fechaInicio.isAfter(r.getFechaInicio()) && fechaInicio.isBefore(r.getFechaFin())) || 
							(fechaFin.isAfter(r.getFechaInicio()) && fechaFin.isBefore(r.getFechaFin())) || 
							(fechaInicio.isBefore(r.getFechaInicio()) && fechaFin.isAfter(r.getFechaFin())) ||
							fechaInicio.isEqual(fechaFin) || 
							fechaInicio.isAfter(fechaFin)) {
						horarioIncorrecto = true;
						break;
					}
				}
			}
			
			if(!horarioIncorrecto) {//El horario de la reserva es válido
				Reserva reserva = new Reserva(idEspacio, idUsuario, fechaInicio, fechaFin);
				Singleton.getReservaDAO().insertarReserva(reserva);
				request.setAttribute(Constantes.MENSAJE, "Usted ha reservado el espacio");
			}else {
				request.setAttribute(Constantes.MENSAJE_ERROR, "El horario que intenta reservar está ocupado o contiene alguna incongruencia");
			}
		}else {
			request.setAttribute(Constantes.MENSAJE_ERROR, "Ha ocurrido un error con la BBDD");
		}
		
		try {
			request.getRequestDispatcher("WEB-INF//jsp//reservas.jsp").forward(request, response);
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
