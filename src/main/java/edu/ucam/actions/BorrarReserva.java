package edu.ucam.actions;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import edu.ucam.commons.Constantes;
import edu.ucam.commons.Singleton;
import edu.ucam.modelo.Reserva;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class BorrarReserva extends Actions{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		// TODO Auto-generated method stub
		int idReserva = Integer.parseInt(request.getParameter(Constantes.ID_SLOT));
		List<Reserva> reservas = (List<Reserva>)Singleton.getReservaDAO().obtenerListaReserva();
        
		if(reservas != null) {
			for(Reserva r : reservas) {
				if(r.getIdReserva()==idReserva) {
					Singleton.getReservaDAO().eliminarReserva(r);
					request.setAttribute(Constantes.MENSAJE, "Reserva eliminada con exito");
					break;
				}
			}
		}else {
			request.setAttribute(Constantes.MENSAJE_ERROR, "Ha sido imposible acceder a la base de datos para borrar la reserva");
		}
        
        try {
			request.getRequestDispatcher("WEB-INF//jsp//reservas.jsp").forward(request, response);
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
