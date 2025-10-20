<%@page import="edu.ucam.modelo.Espacio"%>
<%@page import="edu.ucam.redireccionamiento.RedireccionarEspacios"%>
<%@page import="edu.ucam.modelo.Usuario"%>
<%@page import="java.util.HashMap"%>
<%@page import="edu.ucam.modelo.Rol"%>
<%@page import="edu.ucam.commons.Singleton"%>
<%@page import="edu.ucam.modelo.Reserva"%>
<%@page import="java.util.List"%>
<%@page import="edu.ucam.commons.Constantes"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Gestión de reservas</title>
</head>
<body>

<h2 style="display: flex; justify-content: space-between; align-items: center;">
	Listado de usuarios
	<button type="button" onclick="location.href='redireccionarpanel'" style="font-size:16px; color: white; background: blue; padding: 5px;">Volver al panel</button>
</h2>

<p style="color:blue;"><%= request.getAttribute(Constantes.MENSAJE) != null ? request.getAttribute(Constantes.MENSAJE) : "" %></p>
<p style="color:red;"><%= request.getAttribute(Constantes.MENSAJE_ERROR) != null ? request.getAttribute(Constantes.MENSAJE_ERROR) : "" %></p>

<%
	List<Reserva> reservas = (List<Reserva>) Singleton.getReservaDAO().obtenerListaReserva();
	Rol rol = (Rol) request.getSession().getAttribute(Constantes.ROL);
	String email = (String) request.getSession().getAttribute(Constantes.EMAIL);
	HashMap<String, Usuario> usuarios = (HashMap<String, Usuario>) Singleton.getUsuarioDAO().obtenerListaUsuarios();
	HashMap<Integer, Espacio> espacios = (HashMap<Integer, Espacio>) Singleton.getEspacioDAO().obtenerListaEspacios();

	if (!email.equals("admin")) {
		if(!espacios.isEmpty()){
%>

		<form action="control">
			<input type="hidden" name="<%= Constantes.ACTION %>" value="POSTRES">
			<input type="hidden" name="<%= Constantes.EMAIL %>" value="<%= email %>">
			
			<p><strong>Selecciona un espacio:</strong></p>
			<select name="<%=Constantes.ID_ESPACIO %>">
			    <%
			        for (Integer idEspacio : espacios.keySet()) {
			            Espacio espacio = espacios.get(idEspacio);
			    %>
			        <option value="<%= espacio.getId() %>">
			            <%= espacio.getCiudad() %> - <%= espacio.getEdificio() %> - Planta <%= espacio.getPlanta() %>, Puerta <%= espacio.getNumeroPuerta() %>
			        </option>
			    <%
			        }
			    %>
			</select>
			
			
			<p><strong>Fecha de entrada: </strong></p><input type="datetime-local" name="<%= Constantes.FECHA_INICIO %>" required>
			<p><strong>Fecha de salida: </strong></p><input type="datetime-local" name="<%= Constantes.FECHA_FIN %>" required>
			<button type="submit">Reservar espacio</button>
		</form><br>
<%
		}else{
			out.print("No hay espacios que usted pueda reservar<br>");
		}
	}

	if (reservas != null && !reservas.isEmpty()) {
		for (Reserva r : reservas) {
			boolean esAdmin = rol.equals(Rol.ADMIN);
			boolean esUsuario = email.equals(r.getIdUsuario());
			int idReserva = r.getIdReserva();

			if (esUsuario || esAdmin) {
				Espacio espacio = espacios.get(r.getIdEspacio());
				Usuario usuario = usuarios.get(r.getIdUsuario());
%>
				<div style="border: 3px solid black; padding: 10px; margin-bottom: 10px;">
					<form action="control">
						<input type="hidden" name="<%=Constantes.ID_SLOT%>" value="<%=idReserva%>">
						<input type="hidden" name="<%=Constantes.ID_ESPACIO%>" value="<%=espacio.getId()%>">
						<input type="hidden" name="<%=Constantes.EMAIL%>" value="<%=usuario.getEmail()%>">
						
						<p><strong>ID Slot:</strong> <%= idReserva %></p>
						<p><strong>Hora de entrada:</strong></p><input type="datetime-local" name="<%= Constantes.FECHA_INICIO %>" value="<%= r.getFechaInicio() %>" required>
						<p><strong>Hora de salida:</strong></p><input type="datetime-local" name="<%= Constantes.FECHA_FIN %>" value="<%= r.getFechaFin() %>" required>
						<button type="submit" name="<%=Constantes.ACTION%>" value="PUTRES" >Editar</button>
						<button type="submit" name="<%=Constantes.ACTION%>" value="DELRES" onclick="return confirm('¿Estás seguro de eliminar esta reserva?');">Eliminar espacio</button>
					</form>
					<hr>

					<!-- Información del espacio -->
					<p><strong>Ciudad:</strong> <%= espacio.getCiudad() %></p>
					<p><strong>Edificio:</strong> <%= espacio.getEdificio() %></p>
					<p><strong>Planta:</strong> <%= espacio.getPlanta() %></p>
					<p><strong>Número de puerta:</strong> <%= espacio.getNumeroPuerta() %></p>
					<p><strong>Descripción:</strong> <%= espacio.getDescripcion() %></p>

<%
				if (esAdmin) {
%>
					<hr>
					<!-- Información del usuario -->
					<p><strong>Nombre:</strong> <%= usuario.getNombre() %></p>
					<p><strong>Email:</strong> <%= usuario.getEmail() %></p>
<%
				}
%>
				</div>
<%
			}
		}
	} else {
%>
	<p>No hay reservas en la base de datos.</p>
<%
		}
%>

</body>
</html>
