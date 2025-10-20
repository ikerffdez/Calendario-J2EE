<%@page import="edu.ucam.commons.Constantes"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page session="true" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Registro</title>
</head>
<body>
    <h2>Registro de Usuario</h2>
    <form action="control" method="post">
    	<input type="hidden" name="<%=Constantes.ACTION%>" value="POSTUSER">
        <label>Nombre:</label>
        <input type="text" name="<%=Constantes.NOMBRE%>" required><br><br>
        <label>Email:</label>
        <input type="email" name="<%=Constantes.EMAIL%>" required><br><br>
        <label>Contraseña:</label>
        <input type="password" name="<%=Constantes.PASSWORD%>" required><br><br>
        <input type="submit" value="Registrarse"> &emsp;
       	¿Ya tienes cuenta? <a href="index.jsp">Iniciar sesión</a>
    </form>
    <p style="color:red;"><%= request.getAttribute(Constantes.MENSAJE) != null ? request.getAttribute(Constantes.MENSAJE) : "" %></p>
</body>
</html>