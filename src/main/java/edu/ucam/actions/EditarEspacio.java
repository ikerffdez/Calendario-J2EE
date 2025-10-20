package edu.ucam.actions;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

import edu.ucam.commons.Constantes;
import edu.ucam.commons.Singleton;
import edu.ucam.modelo.Espacio;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class EditarEspacio extends Actions{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		// TODO Auto-generated method stub
		HashMap<Integer, Espacio> espacios = (HashMap<Integer, Espacio>)Singleton.getEspacioDAO().obtenerListaEspacios();
		int id = Integer.parseInt((String)request.getParameter(Constantes.ID_ESPACIO));
		String ciudad = (String)request.getParameter(Constantes.CIUDAD_ESPACIO);
	    String edificio = (String)request.getParameter(Constantes.EDIFICIO_ESPACIO);
	    int planta = Integer.parseInt((String)request.getParameter(Constantes.PLANTA_ESPACIO));
	    int numeroPuerta = Integer.parseInt((String)request.getParameter(Constantes.NPUERTA_ESPACIO));
	    String descripcion = (String)request.getParameter(Constantes.DESCRIPCION_ESPACIO);
		
		if(espacios.containsKey(id)) {
			Espacio espacio = new Espacio(id, ciudad, edificio, planta, numeroPuerta, descripcion);
			Espacio aux = espacios.get(id);
			if(aux.getCiudad().equals(ciudad) && aux.getEdificio().equals(edificio) && aux.getPlanta()==planta 
					&& aux.getNumeroPuerta()==numeroPuerta && aux.getDescripcion().equals(descripcion)) {
				request.setAttribute(Constantes.MENSAJE_ERROR, "Usted no ha editado ninguna característica del espacio");
			}else {
				Singleton.getEspacioDAO().editarEspacio(espacio);
				request.setAttribute(Constantes.MENSAJE, "El usuario ha sido editado con éxito");
			}
		}else {
			request.setAttribute(Constantes.MENSAJE_ERROR, "Error al intentar editar el espacio");
		}
		
		try {
			request.getRequestDispatcher("WEB-INF//jsp//espacios.jsp").forward(request, response);
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
