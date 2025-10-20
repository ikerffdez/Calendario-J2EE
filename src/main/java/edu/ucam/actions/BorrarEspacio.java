package edu.ucam.actions;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import edu.ucam.commons.Constantes;
import edu.ucam.commons.Singleton;
import edu.ucam.modelo.Espacio;
import edu.ucam.modelo.Reserva;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class BorrarEspacio extends Actions{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		// TODO Auto-generated method stub
		HashMap<Integer, Espacio> espacios = (HashMap<Integer, Espacio>)Singleton.getEspacioDAO().obtenerListaEspacios();
		int id = Integer.parseInt((String)request.getParameter(Constantes.ID_ESPACIO));
		
		if(espacios != null) {
			//Eliminamos todas las reservas a las que esta asociado
			List<Reserva> reservas = (List<Reserva>)Singleton.getReservaDAO().obtenerListaReserva();
			for(Reserva r : reservas) {
				if(r.getIdEspacio()==id) {
					//Encontramos una reserva de este espacio. La borramos
					Singleton.getReservaDAO().eliminarReserva(r);
				}
			}
			
			Singleton.getEspacioDAO().eliminarEspacio(espacios.get(id));
			request.setAttribute(Constantes.MENSAJE, "El espacio ha sido borrado con exito");
		}else {
			request.setAttribute(Constantes.MENSAJE_ERROR, "Ha sido imposible borrar el espacio por un fallo en la BBDD");
		}
		
		try {
			request.getRequestDispatcher("WEB-INF//jsp//espacios.jsp").forward(request, response);
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
