package edu.ucam.actions;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

import edu.ucam.commons.Constantes;
import edu.ucam.commons.Singleton;
import edu.ucam.modelo.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CrearUsuario extends Actions{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		// TODO Auto-generated method stub
		String nombre = request.getParameter(Constantes.NOMBRE);
        String email = request.getParameter(Constantes.EMAIL);
        String password = request.getParameter(Constantes.PASSWORD);
        
        Usuario u = new Usuario(nombre, email, password);
        
        HashMap<String, Usuario> usuarios = (HashMap<String, Usuario>)Singleton.getUsuarioDAO().obtenerListaUsuarios();
        
        if(usuarios != null) {
        	if(!usuarios.containsKey(email)) {
        		Singleton.getUsuarioDAO().insertarUsuario(u);
	        	request.setAttribute(Constantes.MENSAJE, "Usuario añadido con éxito");
        	}else {
        		request.setAttribute(Constantes.MENSAJE_ERROR, "Un usuario ya ha sido registrado con ese email");
        	}
        }else {
        	request.setAttribute(Constantes.MENSAJE_ERROR, "Ha ocurrido un problema con la base de datos para introducir el usuario");
        }
        
        try {
			request.getRequestDispatcher("index.jsp").forward(request, response);
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
