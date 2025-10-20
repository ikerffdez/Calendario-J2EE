package edu.ucam.dao;

import java.sql.SQLException;
import java.util.List;

import edu.ucam.modelo.Reserva;

public interface ReservaDAO {
	public void insertarReserva(Reserva reserva) throws SQLException;
    public void eliminarReserva(Reserva reserva) throws SQLException;
    public void editarReserva(Reserva reserva) throws SQLException;
    public List<Reserva> obtenerListaReserva() throws SQLException;
}
