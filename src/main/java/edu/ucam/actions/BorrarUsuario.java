package edu.ucam.actions;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import edu.ucam.commons.Constantes;
import edu.ucam.commons.Singleton;
import edu.ucam.modelo.Reserva;
import edu.ucam.modelo.Rol;
import edu.ucam.modelo.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class BorrarUsuario extends Actions{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		// TODO Auto-generated method stub
		String email = request.getParameter(Constantes.EMAIL);
		HashMap<String, Usuario> usuarios = (HashMap<String, Usuario>)Singleton.getUsuarioDAO().obtenerListaUsuarios();
        
        if(usuarios != null) {
        	if(request.getSession().getAttribute(Constantes.ROL).equals(Rol.ADMIN)) {
	        	Usuario usuario = usuarios.get(email);
	        	
	        	//Eliminamos las respectivas reservas en las que está registrado el usuario.
	        	List<Reserva> reservas = (List<Reserva>)Singleton.getReservaDAO().obtenerListaReserva();
	        	for(Reserva r : reservas) {
	        		if(r.getIdUsuario().equals(email)) {
	        			//encontramos una reservas de este usuario. Y la eliminamos.
	        			Singleton.getReservaDAO().eliminarReserva(r);
	        		}
	        	}
	        	//Una vez eliminadas sus reservas, eliminamos al usuario
	        	Singleton.getUsuarioDAO().eliminarUsuario(usuario);
	        	request.setAttribute(Constantes.MENSAJE, "Usuario eliminado con exito");
        	}else {//no es accesible a borrar mediante url a los no administradores
        		request.setAttribute(Constantes.MENSAJE_ERROR, "No tiene permisos para realizar esar operacion.");
        		try {
					request.getRequestDispatcher("index.jsp").forward(request, response);
				} catch (ServletException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}
        }else {
        	request.setAttribute(Constantes.MENSAJE_ERROR, "Ha sido imposible acceder a la base de datos para borrar el usuario");
        }
        
        try {
			request.getRequestDispatcher("WEB-INF//jsp//usuarios.jsp").forward(request, response);
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
