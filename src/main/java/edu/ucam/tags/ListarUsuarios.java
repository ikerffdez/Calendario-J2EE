package edu.ucam.tags;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

import edu.ucam.commons.Constantes;
import edu.ucam.commons.Singleton;
import edu.ucam.modelo.Rol;
import edu.ucam.modelo.Usuario;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.JspWriter;
import jakarta.servlet.jsp.tagext.BodyTagSupport;

public class ListarUsuarios extends BodyTagSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	public int doStartTag() throws JspException {
	    JspWriter out = pageContext.getOut();
	    HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
	    HttpSession session = request.getSession();

	    try {
	        out.write("<h2 style=\"display: flex; justify-content: space-between; align-items: center;\">");
	        out.write("Listado de usuarios");
	        out.write("<button type=\"button\" onclick=\"location.href='redireccionarpanel'\" style=\"font-size:16px; color: white; background: blue; padding: 5px;\">Volver al panel</button>");
	        out.write("</h2>");

	        Object mensaje = request.getAttribute(Constantes.MENSAJE);
	        Object mensajeError = request.getAttribute(Constantes.MENSAJE_ERROR);

	        out.write("<p style=\"color:blue;\">" + (mensaje != null ? mensaje.toString() : "") + "</p>");
	        out.write("<p style=\"color:red;\">" + (mensajeError != null ? mensajeError.toString() : "") + "</p>");

	        HashMap<String, Usuario> usuarios = (HashMap<String, Usuario>) Singleton.getUsuarioDAO().obtenerListaUsuarios();

	        if (usuarios != null && !usuarios.isEmpty()) {
	            for (Usuario u : usuarios.values()) {
	                out.write("<div style=\"border: 1px solid #ccc; padding: 10px; margin-bottom: 10px;\">");
	                out.write("<p><strong>Nombre: </strong>" + u.getNombre() + " &emsp; <strong>Rol: </strong>" + u.getRol() + "</p>");
	                out.write("<p><strong>Email: </strong>" + u.getEmail() + "</p>");
	                out.write("<p><strong>Contraseña: </strong>" + u.getPassword() + "</p>");

	                if (!u.getEmail().equals("admin")) {
	                    out.write("<a style=\"text-decoration: none;\" href=\"redireccionareditarusuarios?" + Constantes.EMAIL + "=" + u.getEmail() + "\">");
	                    out.write("<button type=\"button\">Editar usuario</button>");
	                    out.write("</a>");

	                    if (!u.getEmail().equals(session.getAttribute(Constantes.EMAIL))) {
	                    	out.write("<a style=\"text-decoration: none;\" href=\"control?" + Constantes.ACTION + "=DELUSER&" + Constantes.EMAIL + "=" + u.getEmail() + "\" "
	                    	        + "onclick=\"return confirm('¿Estás seguro de que deseas eliminar este usuario? Se elimnarán todas sus reservas.');\">");
	                    	out.write("<button>Eliminar usuario</button>");
	                    	out.write("</a>");


	                        if (u.getRol().equals(Rol.ADMIN)) {
	                            out.write("<a style=\"text-decoration: none;\" href=\"control?" + Constantes.ACTION + "=PUTNOTADMIN&" + Constantes.EMAIL + "=" + u.getEmail() + "\">");
	                            out.write("<button>Degradar a usuario</button>");
	                            out.write("</a>");
	                        } else {
	                            out.write("<a style=\"text-decoration: none;\" href=\"control?" + Constantes.ACTION + "=PUTADMIN&" + Constantes.EMAIL + "=" + u.getEmail() + "\">");
	                            out.write("<button>Ascender a administrador</button>");
	                            out.write("</a>");
	                        }
	                    }
	                }
	                out.write("</div><br><br>");
	            }
	        } else {
	            out.write("No hay usuarios en la aplicación");
	        }

	    } catch (IOException | SQLException e) {
	        throw new JspException("Error al generar la lista de usuarios", e);
	    }

	    return SKIP_BODY;
	}


}
