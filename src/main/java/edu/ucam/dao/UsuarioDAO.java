package edu.ucam.dao;

import java.sql.SQLException;
import java.util.HashMap;

import edu.ucam.modelo.Usuario;

public interface UsuarioDAO {
	public void insertarUsuario(Usuario usuario) throws SQLException;
    public void eliminarUsuario(Usuario usuario) throws SQLException;
    public void editarUsuario (Usuario usuario) throws SQLException;
    public void ascenderAdministrador(Usuario usuario) throws SQLException;
    public void degradarUsuario(Usuario usuario) throws SQLException;
    public HashMap<String, Usuario> obtenerListaUsuarios() throws SQLException;
}
