<%@page import="edu.ucam.commons.Singleton"%>
<%@page import="edu.ucam.modelo.Rol"%>
<%@page import="edu.ucam.commons.Constantes"%>
<%@page import="edu.ucam.modelo.Usuario"%>
<%@page import="java.util.HashMap"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page session="true" %>
<%@taglib prefix="listado" uri="tag" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Gestión de usuarios</title>
</head>
<body>
	<listado:ListarUsuarios></listado:ListarUsuarios>
</body>
</html>