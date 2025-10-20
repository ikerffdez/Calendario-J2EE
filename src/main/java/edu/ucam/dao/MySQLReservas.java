package edu.ucam.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import edu.ucam.modelo.Reserva;

public class MySQLReservas implements ReservaDAO{
	Connection conn = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	
	@Override
	public void insertarReserva(Reserva reserva) throws SQLException {
		// TODO Auto-generated method stub
		try {
	        // Obtener conexión desde el contexto JNDI
	        Context initContext = new InitialContext();
	        Context envContext  = (Context) initContext.lookup("java:/comp/env");
	        DataSource ds = (DataSource) envContext.lookup("jdbc/dad2_77168527P_49305680M");
	        conn = ds.getConnection();

	        // Preparar la consulta SQL con placeholders
	        String sql = "INSERT INTO reservas (id_espacio, id_usuario, fecha_inicio, fecha_fin) VALUES (?, ?, ?, ?)";
	        ps = conn.prepareStatement(sql);
	        ps.setInt(1, reserva.getIdEspacio());
	        ps.setString(2, reserva.getIdUsuario());
	        ps.setTimestamp(3, Timestamp.valueOf(reserva.getFechaInicio()));
	        ps.setTimestamp(4, Timestamp.valueOf(reserva.getFechaFin()));
	        
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
	public void eliminarReserva(Reserva reserva) throws SQLException {
		// TODO Auto-generated method stub
		try {
	        // Obtener conexion desde el contexto JNDI
	        Context initContext = new InitialContext();
	        Context envContext  = (Context) initContext.lookup("java:/comp/env");
	        DataSource ds = (DataSource) envContext.lookup("jdbc/dad2_77168527P_49305680M");
	        conn = ds.getConnection();

	        // Preparar la consulta SQL con placeholders
	        String sql = "DELETE FROM reservas WHERE id_reserva = ?";
	        ps = conn.prepareStatement(sql);
	        ps.setInt(1, reserva.getIdReserva());
	        
	        
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
	public void editarReserva(Reserva reserva) throws SQLException {
		// TODO Auto-generated method stub
		try {
	        // Obtener conexión desde el contexto JNDI
	        Context initContext = new InitialContext();
	        Context envContext  = (Context) initContext.lookup("java:/comp/env");
	        DataSource ds = (DataSource) envContext.lookup("jdbc/dad2_77168527P_49305680M");
	        conn = ds.getConnection();

	        // Preparar la consulta SQL con placeholders
	        String sql = "UPDATE reservas SET fecha_inicio = ?, fecha_fin = ? WHERE id_reserva = ?";
	        ps = conn.prepareStatement(sql);
	        ps.setTimestamp(1, Timestamp.valueOf(reserva.getFechaInicio()));
	        ps.setTimestamp(2, Timestamp.valueOf(reserva.getFechaFin()));
	        ps.setInt(3, reserva.getIdReserva());
	        
	        ps.executeUpdate();

	    } catch (NamingException e) {
	        e.printStackTrace();
	        throw new SQLException("Error al obtener la conexiÃ³n desde JNDI.");
	    } finally {
	        if (ps != null) ps.close();
	        if (conn != null) conn.close();
	    }
	}

	@Override
	public List<Reserva> obtenerListaReserva() throws SQLException {
		// TODO Auto-generated method stub
				List<Reserva> reservas = new ArrayList<Reserva>();

			    try {
			        // Obtener conexión desde el contexto JNDI
			        Context initContext = new InitialContext();
			        Context envContext  = (Context) initContext.lookup("java:/comp/env"); //busca los recurso del datasource
			        DataSource ds = (DataSource) envContext.lookup("jdbc/dad2_77168527P_49305680M");//busca la bbdd entre los recursos
			        conn = ds.getConnection();

			        // Sentencia SQL para obtener todos los usuarios
			        String sql = "SELECT id_reserva, id_espacio, id_usuario, fecha_inicio, fecha_fin FROM reservas";
			        ps = conn.prepareStatement(sql);
			        rs = ps.executeQuery();

			        // Iterar sobre los resultados y crear la lista de usuarios
			        while (rs.next()) {
			        	Reserva reserva = new Reserva();
			        	reserva.setIdReserva(rs.getInt("id_reserva"));
			        	reserva.setIdEspacio(rs.getInt("id_espacio"));
			        	reserva.setIdUsuario(rs.getString("id_usuario"));
			        	reserva.setFechaInicio(rs.getTimestamp("fecha_inicio").toLocalDateTime());
			        	reserva.setFechaFin(rs.getTimestamp("fecha_fin").toLocalDateTime());
			        	reservas.add(reserva);
			        }

			    } catch (NamingException e) {
			        e.printStackTrace();
			        throw new SQLException("Error al obtener la conexiÃ³n desde JNDI.");
			    } finally {
			        if (rs != null) rs.close();
			        if (ps != null) ps.close();
			        if (conn != null) conn.close();
			    }

			    return reservas;  // Devuelve la lista de reservas
	}
}
