package edu.ucam.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import edu.ucam.modelo.Rol;
import edu.ucam.modelo.Usuario;

public class MySQLUsuarioDAO implements UsuarioDAO{
	 Connection conn = null;
	 PreparedStatement ps = null;
	 ResultSet rs = null;
	
	@Override
	public void insertarUsuario(Usuario usuario) throws SQLException {
		// TODO Auto-generated method stub
	   try {
	        // Obtener conexión desde el contexto JNDI
	        Context initContext = new InitialContext();
	        Context envContext  = (Context) initContext.lookup("java:/comp/env");
	        DataSource ds = (DataSource) envContext.lookup("jdbc/dad2_77168527P_49305680M");
	        conn = ds.getConnection();

	        // Preparar la consulta SQL con placeholders
	        String sql = "INSERT INTO usuarios (email, nombre, password, rol) VALUES (?, ?, ?, 'USUARIO')";
	        ps = conn.prepareStatement(sql);
	        ps.setString(1, usuario.getEmail());
	        ps.setString(2, usuario.getNombre());
	        ps.setString(3, usuario.getPassword());

	        ps.executeUpdate();

	    } catch (NamingException e) {
	        e.printStackTrace();
	        throw new SQLException("Error al obtener la conexion desde JNDI.");
	    } finally {
	        if (ps != null) ps.close();
	        if (conn != null) conn.close();
	    }
	}


	@Override
	public void eliminarUsuario(Usuario usuario) throws SQLException {
		// TODO Auto-generated method stub
	    try {
	        // Obtener conexión desde el contexto JNDI
	        Context initContext = new InitialContext();
	        Context envContext  = (Context) initContext.lookup("java:/comp/env");
	        DataSource ds = (DataSource) envContext.lookup("jdbc/dad2_77168527P_49305680M");
	        conn = ds.getConnection();

	        // Sentencia SQL para eliminar el usuario por email
	        String sql = "DELETE FROM usuarios WHERE email = ?";
	        ps = conn.prepareStatement(sql);
	        ps.setString(1, usuario.getEmail());

	        ps.executeUpdate();

	    } catch (NamingException e) {
	        e.printStackTrace();
	        throw new SQLException("Error al obtener la conexión desde JNDI.");
	    } finally {
	        if (ps != null) ps.close();
	        if (conn != null) conn.close();
	    }
	}

	@Override
	public HashMap<String, Usuario> obtenerListaUsuarios() throws SQLException {
		// TODO Auto-generated method stub
	    HashMap<String, Usuario> usuarios = new HashMap<String, Usuario>();

	    try {
	        // Obtener conexión desde el contexto JNDI
	        Context initContext = new InitialContext();
	        Context envContext  = (Context) initContext.lookup("java:/comp/env"); //busca los recurso del datasource
	        DataSource ds = (DataSource) envContext.lookup("jdbc/dad2_77168527P_49305680M");//busca la bbdd entre los recursos
	        conn = ds.getConnection();

	        // Sentencia SQL para obtener todos los usuarios
	        String sql = "SELECT email, nombre, password, rol FROM usuarios";
	        ps = conn.prepareStatement(sql);
	        rs = ps.executeQuery();

	        // Iterar sobre los resultados y crear la lista de usuarios
	        while (rs.next()) {
	            Usuario usuario = new Usuario();
	            usuario.setEmail(rs.getString("email"));
	            usuario.setNombre(rs.getString("nombre"));
	            usuario.setPassword(rs.getString("password"));
	            usuario.setRol(Rol.valueOf(rs.getString("rol")));
	            usuarios.put(usuario.getEmail(), usuario);
	        }

	    } catch (NamingException e) {
	        e.printStackTrace();
	        throw new SQLException("Error al obtener la conexión desde JNDI.");
	    } finally {
	        if (rs != null) rs.close();
	        if (ps != null) ps.close();
	        if (conn != null) conn.close();
	    }

	    return usuarios;  // Devuelve la lista de usuarios
	}


	@Override
	public void editarUsuario(Usuario usuario) throws SQLException {
		// TODO Auto-generated method stub
		try {
	        // Obtener conexión desde el contexto JNDI
	        Context initContext = new InitialContext();
	        Context envContext  = (Context) initContext.lookup("java:/comp/env");
	        DataSource ds = (DataSource) envContext.lookup("jdbc/dad2_77168527P_49305680M");
	        conn = ds.getConnection();

	        String sql = "UPDATE usuarios SET nombre = ?, password = ? WHERE email = ?";

		    Connection conn = ds.getConnection();
		    PreparedStatement ps = conn.prepareStatement(sql);

		    ps.setString(1, usuario.getNombre());
		    ps.setString(2, usuario.getPassword());
		    ps.setString(3, usuario.getEmail()); // usamos el email como identificador único

		    ps.executeUpdate();

	    } catch (NamingException e) {
	        e.printStackTrace();
	        throw new SQLException("Error al obtener la conexión desde JNDI.");
	    } finally {
	        if (ps != null) ps.close();
	        if (conn != null) conn.close();
	    }
	}



	@Override
	public void ascenderAdministrador(Usuario usuario) throws SQLException {
		// TODO Auto-generated method stub
		try {
	        // Obtener conexión desde el contexto JNDI
	        Context initContext = new InitialContext();
	        Context envContext  = (Context) initContext.lookup("java:/comp/env");
	        DataSource ds = (DataSource) envContext.lookup("jdbc/dad2_77168527P_49305680M");
	        conn = ds.getConnection();

	        String sql = "UPDATE usuarios SET rol = 'ADMIN' WHERE email = ?";

		    Connection conn = ds.getConnection();
		    PreparedStatement ps = conn.prepareStatement(sql);

		    ps.setString(1, usuario.getEmail()); // usamos el email como identificador único

		    ps.executeUpdate();

	    } catch (NamingException e) {
	        e.printStackTrace();
	        throw new SQLException("Error al obtener la conexión desde JNDI.");
	    } finally {
	        if (ps != null) ps.close();
	        if (conn != null) conn.close();
	    }
	}


	@Override
	public void degradarUsuario(Usuario usuario) throws SQLException {
		// TODO Auto-generated method stub
		try {
	        // Obtener conexión desde el contexto JNDI
	        Context initContext = new InitialContext();
	        Context envContext  = (Context) initContext.lookup("java:/comp/env");
	        DataSource ds = (DataSource) envContext.lookup("jdbc/dad2_77168527P_49305680M");
	        conn = ds.getConnection();

	        String sql = "UPDATE usuarios SET rol = 'USUARIO' WHERE email = ?";

		    Connection conn = ds.getConnection();
		    PreparedStatement ps = conn.prepareStatement(sql);

		    ps.setString(1, usuario.getEmail()); // usamos el email como identificador único

		    ps.executeUpdate();

	    } catch (NamingException e) {
	        e.printStackTrace();
	        throw new SQLException("Error al obtener la conexión desde JNDI.");
	    } finally {
	        if (ps != null) ps.close();
	        if (conn != null) conn.close();
	    }
	}


}
