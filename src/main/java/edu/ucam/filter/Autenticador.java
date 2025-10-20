package edu.ucam.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import edu.ucam.commons.Constantes;

/**
 * Servlet Filter implementation class Autenticador
 */
@WebFilter("/jsp")
public class Autenticador extends HttpFilter implements Filter {
       
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	/**
     * @see HttpFilter#HttpFilter()
     */
    public Autenticador() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String uri = req.getRequestURI();

        HttpSession sesion = req.getSession(false);
        boolean logeado = (sesion != null && sesion.getAttribute(Constantes.EMAIL) != null);

        //Recursos accesibles por cualquier usuario sin loggear
        boolean recursoPublico = uri.contains("index.jsp") || uri.contains("/login") || uri.contains("/redireccionarregistro") || (uri.contains("/control") && request.getParameter(Constantes.ACTION).equals("POSTUSER"));
        
        if (logeado || recursoPublico) {
            chain.doFilter(request, response); // deja pasar
        } else {
            // Redirige al login si intenta acceder sin estar logueado
            req.setAttribute(Constantes.MENSAJE_ERROR, "Identifíquese antes de entrar a la aplicación");
        	req.getRequestDispatcher("index.jsp").forward(req, res);
        }
    }


	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
