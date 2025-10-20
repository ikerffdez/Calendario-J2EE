package edu.ucam.commons;

import edu.ucam.dao.EspacioDAO;
import edu.ucam.dao.MySQLEspacioDAO;
import edu.ucam.dao.MySQLReservas;
import edu.ucam.dao.MySQLUsuarioDAO;
import edu.ucam.dao.ReservaDAO;
import edu.ucam.dao.UsuarioDAO;

public class Singleton {
	
	public static UsuarioDAO usuarioDAO;
	public static EspacioDAO espacioDAO;
	public static ReservaDAO reservaDAO;

	public static UsuarioDAO getUsuarioDAO() {
		if(Singleton.usuarioDAO == null)
			Singleton.usuarioDAO = new MySQLUsuarioDAO();
		return Singleton.usuarioDAO;
	}
	
	public static EspacioDAO getEspacioDAO() {
		if(Singleton.espacioDAO == null) {
			Singleton.espacioDAO = new MySQLEspacioDAO();
		}
		
		return Singleton.espacioDAO;
	}
	
	public static ReservaDAO getReservaDAO() {
		if(Singleton.reservaDAO == null) {
			Singleton.reservaDAO = new MySQLReservas();
		}
		return Singleton.reservaDAO;
	}
}
