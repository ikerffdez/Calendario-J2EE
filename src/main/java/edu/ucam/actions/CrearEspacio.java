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

public class CrearEspacio extends Actions{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		// TODO Auto-generated method stub
		boolean existe = false;
		
		HashMap<Integer, Espacio> espacios = (HashMap<Integer, Espacio>)Singleton.getEspacioDAO().obtenerListaEspacios();
		String ciudad = (String)request.getParameter(Constantes.CIUDAD_ESPACIO);
	    String edificio = (String)request.getParameter(Constantes.EDIFICIO_ESPACIO);
	    int planta = Integer.parseInt((String)request.getParameter(Constantes.PLANTA_ESPACIO));
	    int numeroPuerta = Integer.parseInt((String)request.getParameter(Constantes.NPUERTA_ESPACIO));
	    String descripcion = (String)request.getParameter(Constantes.DESCRIPCION_ESPACIO);
		
		if(espacios != null) {
			if(!espacios.isEmpty()) { //existen ya espacios
				for(Espacio e : espacios.values()) {
					if(e.getCiudad().equals(ciudad) && e.getEdificio().equals(edificio) && e.getPlanta()==planta && e.getNumeroPuerta()==numeroPuerta) {
						//ya existe un espacio con esas caracterÃ­sticas
						existe = true;
						break;
					}
				}
				if(existe) {
					request.setAttribute(Constantes.MENSAJE_ERROR, "Ya existe un espacio con esas caracteristicas");
				}else {
					Espacio espacio = new Espacio(ciudad, edificio, planta, numeroPuerta, descripcion);
					Singleton.getEspacioDAO().insertarEspacio(espacio);
					request.setAttribute(Constantes.MENSAJE, "El espacio ha sido añadido con éxito");
				}
			}else { //es el primer espacio
				Espacio espacio = new Espacio(ciudad, edificio, planta, numeroPuerta, descripcion);
				Singleton.getEspacioDAO().insertarEspacio(espacio);
				request.setAttribute(Constantes.MENSAJE, "El espacio ha sido añadido con exito");
			}
		}else {
			request.setAttribute(Constantes.MENSAJE_ERROR, "Ha sido imposible recuperar la lista de espacios de la BBDD");
		}
		
		try {
			request.getRequestDispatcher("WEB-INF//jsp//espacios.jsp").forward(request, response);
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
