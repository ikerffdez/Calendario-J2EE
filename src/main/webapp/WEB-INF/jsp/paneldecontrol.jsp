<%@page import="edu.ucam.commons.Singleton"%>
<%@page import="edu.ucam.modelo.Usuario"%>
<%@page import="java.util.HashMap"%>
<%@page import="edu.ucam.commons.Constantes"%>
<%@page import="edu.ucam.modelo.Rol"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page session="true" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Panel de control</title>
</head>
<body>
	<%
	    String nombre = (String) session.getAttribute(Constantes.NOMBRE);
	    Rol rol = (Rol) session.getAttribute(Constantes.ROL);
	%>
	
    <h2>Bienvenido, <%=nombre%></h2>
    <a href="logout">Cerrar sesión</a><br><br>

	<!-- GESTION DE USUARIOS -->
    <% if (Rol.ADMIN.equals(rol)) { %>
        <a href="redireccionarusuarios">Gestión de Usuarios</a><br>
    <% } else{
    	String email = (String) session.getAttribute(Constantes.EMAIL);
    	%>
    		<a href="redireccionareditarusuarios?<%=Constantes.EMAIL%>=<%=email%>">Editar mi cuenta</a><br>
    	<%
    	
    }
    %>
    <!-- GESTION DE ESPACIOS -->
    <a href="redireccionarespacios">Gestión de Espacios</a><br>
    
    <!-- GESTION DE RESERVAS -->
    <% if (Rol.ADMIN.equals(rol)) { %>
        <a href="redireccionarreservas">Gestión de Reservas</a><br>
    <% } else{
    	%>
    		<a href="redireccionarreservas">Mis reservas</a><br>
    	<%
    	
    }
    %>
    
   	<p style="color:blue;"><%= request.getAttribute(Constantes.MENSAJE) != null ? request.getAttribute(Constantes.MENSAJE) : "" %></p>
    <p style="color:red;"><%= request.getAttribute(Constantes.MENSAJE_ERROR) != null ? request.getAttribute(Constantes.MENSAJE_ERROR) : "" %></p>
   	
</body>
</html>