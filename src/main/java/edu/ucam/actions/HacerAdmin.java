package edu.ucam.actions;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

import edu.ucam.commons.Constantes;
import edu.ucam.commons.Singleton;
import edu.ucam.modelo.Rol;
import edu.ucam.modelo.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class HacerAdmin extends Actions{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		// TODO Auto-generated method stub
		HashMap<String, Usuario> usuarios = (HashMap<String, Usuario>)Singleton.getUsuarioDAO().obtenerListaUsuarios();
		String email = (String)request.getParameter(Constantes.EMAIL);
		
		if(usuarios!=null && email!=null) {
			if(request.getSession().getAttribute(Constantes.ROL).equals(Rol.ADMIN)) {
				Usuario usuario = usuarios.get(email);
				Singleton.getUsuarioDAO().ascenderAdministrador(usuario);
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
			request.setAttribute(Constantes.MENSAJE_ERROR, "Ha sido imposible ascender al usuario");
		}
		
		try {
			request.getRequestDispatcher("WEB-INF//jsp//usuarios.jsp").forward(request, response);
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
