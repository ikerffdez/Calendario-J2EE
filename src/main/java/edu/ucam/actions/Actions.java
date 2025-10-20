package edu.ucam.actions;

import java.sql.SQLException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public abstract class Actions {
	public abstract void execute(HttpServletRequest request, HttpServletResponse response) throws SQLException;
}
