<%@ page import="edu.ucam.commons.Constantes"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Reservas</title>
</head>
<body>
    <h2>Iniciar Sesión</h2>
    <form action="login" method="post">
        <label>Email:</label>
        <input type="text" name="<%=Constantes.EMAIL%>" value="admin" required><br>
        <label>Contraseña:</label>
        <input type="password" name="<%=Constantes.PASSWORD%>" value="admin" required><br><br>
        <input type="submit" value="Iniciar sesión">
    </form>
    <p>¿No tienes cuenta?<a href="redireccionarregistro"> Regístrate</a></p>
    <p style="color:blue;"><%= request.getAttribute(Constantes.MENSAJE) != null ? request.getAttribute(Constantes.MENSAJE) : "" %></p>
    <p style="color:red;"><%= request.getAttribute(Constantes.MENSAJE_ERROR) != null ? request.getAttribute(Constantes.MENSAJE_ERROR) : "" %></p>
</body>
</html>