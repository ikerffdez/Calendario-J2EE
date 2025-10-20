package edu.ucam.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

import edu.ucam.commons.Constantes;
import edu.ucam.commons.Singleton;
import edu.ucam.modelo.Usuario;

/**
 * Servlet implementation class Login
 */
@WebServlet("/login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String email = request.getParameter(Constantes.EMAIL);
        String password = request.getParameter(Constantes.PASSWORD);

        HashMap<String, Usuario> usuarios;
		try {
			usuarios = (HashMap<String, Usuario>)Singleton.getUsuarioDAO().obtenerListaUsuarios();
		
	        if(usuarios!=null) {
	        	Usuario usuario = usuarios.get(email);
		        if (usuario != null && usuario.getPassword().equals(password)) { //credenciales correctas
		            HttpSession sesion = request.getSession();
		            sesion.setAttribute(Constantes.EMAIL, usuario.getEmail());
		            sesion.setAttribute(Constantes.NOMBRE, usuario.getNombre());
		            sesion.setAttribute(Constantes.ROL, usuario.getRol());
		            request.getRequestDispatcher("WEB-INF//jsp//paneldecontrol.jsp").forward(request, response);
		        } else {
		            request.setAttribute(Constantes.MENSAJE, "Credenciales incorrectas");
		            request.getRequestDispatcher("index.jsp").forward(request, response);
		        }
	        }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
