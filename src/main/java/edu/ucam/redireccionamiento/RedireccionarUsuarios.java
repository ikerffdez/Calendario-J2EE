package edu.ucam.redireccionamiento;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import edu.ucam.commons.Constantes;
import edu.ucam.modelo.Rol;

/**
 * Servlet implementation class RedireccionarUsuarios
 */
@WebServlet("/redireccionarusuarios")
public class RedireccionarUsuarios extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RedireccionarUsuarios() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Rol rol = (Rol)request.getSession().getAttribute(Constantes.ROL);
		
		if(rol.equals(Rol.ADMIN))
			request.getRequestDispatcher("WEB-INF//jsp//usuarios.jsp").forward(request, response);
		else {
			request.setAttribute(Constantes.MENSAJE_ERROR, "Usted no tiene autorización para acceder");
			request.getRequestDispatcher("WEB-INF//jsp//paneldecontrol.jsp").forward(request, response);
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
