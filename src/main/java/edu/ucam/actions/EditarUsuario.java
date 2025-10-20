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

public class EditarUsuario extends Actions{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		// TODO Auto-generated method stub
		String email = request.getParameter(Constantes.EMAIL);
		String nuevoNombre = request.getParameter(Constantes.NUEVO_NOMBRE);
        String nuevaPassword = request.getParameter(Constantes.NUEVA_PASSWORD);
        
        HashMap<String, Usuario> usuarios = (HashMap<String, Usuario>)Singleton.getUsuarioDAO().obtenerListaUsuarios();
        
        if(usuarios != null && usuarios.containsKey(email)) { //existe la lista y contiene el email que se quiere editar
        	Usuario u = new Usuario(nuevoNombre, email, nuevaPassword);
        	Usuario aux = usuarios.get(email);
        	if(aux.getNombre().equals(nuevoNombre) && aux.getPassword().equals(nuevaPassword)) {
        		request.setAttribute(Constantes.MENSAJE_ERROR, "Usted no ha editado ningún campo del usuario");
        	}else {
		        	Singleton.getUsuarioDAO().editarUsuario(u);
		        	request.getSession().setAttribute(Constantes.NOMBRE, nuevoNombre);
		        	request.setAttribute(Constantes.MENSAJE, "Usuario editado con éxito");
        	}
        }else {
        	request.setAttribute(Constantes.MENSAJE_ERROR, "Ha sido imposible editar el usuario por un fallo con la BBDD");
        }
        
        try {
        	Rol rol = (Rol)request.getSession().getAttribute(Constantes.ROL);
        	
        	if(rol.equals(Rol.ADMIN))
        		request.getRequestDispatcher("WEB-INF//jsp//usuarios.jsp").forward(request, response);
        	else
        		request.getRequestDispatcher("WEB-INF//jsp//paneldecontrol.jsp").forward(request, response);
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
