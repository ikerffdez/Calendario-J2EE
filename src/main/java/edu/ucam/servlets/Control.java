package edu.ucam.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

import edu.ucam.actions.Actions;
import edu.ucam.actions.BorrarEspacio;
import edu.ucam.actions.BorrarReserva;
import edu.ucam.actions.BorrarUsuario;
import edu.ucam.actions.CrearEspacio;
import edu.ucam.actions.CrearReserva;
import edu.ucam.actions.CrearUsuario;
import edu.ucam.actions.DegradarUsuario;
import edu.ucam.actions.EditarEspacio;
import edu.ucam.actions.EditarReserva;
import edu.ucam.actions.EditarUsuario;
import edu.ucam.actions.HacerAdmin;
import edu.ucam.commons.Constantes;

/**
 * Servlet implementation class Control
 */
@WebServlet("/control")
public class Control extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private HashMap<String, Actions> actions;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Control() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String action = (String)request.getParameter(Constantes.ACTION);
		
		if(action != null) {
			System.out.println("Acción a realizar: "+action);
			try {
				actions.get(action).execute(request, response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else {
			request.setAttribute(Constantes.MENSAJE, "No se ha podido realizar la operaciÃ³n.");
			request.getRequestDispatcher("WEB-INF//jsp//paneldecontrol.jsp").forward(request, response);;
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		
		if (actions == null) {
			actions = new HashMap<String, Actions>();
			actions.put("POSTUSER", new CrearUsuario ());
			actions.put("PUTUSER", new EditarUsuario ());
			actions.put("DELUSER", new BorrarUsuario ());
			actions.put("PUTADMIN", new HacerAdmin());
			actions.put("PUTNOTADMIN", new DegradarUsuario());
			actions.put("POSTESP", new CrearEspacio());
			actions.put("PUTESP", new EditarEspacio());
			actions.put("DELESP", new BorrarEspacio());
			actions.put("POSTRES", new CrearReserva());
			actions.put("PUTRES", new EditarReserva());
			actions.put("DELRES", new BorrarReserva());
		}
	}

}
