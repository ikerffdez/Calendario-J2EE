<%@page import="edu.ucam.commons.Singleton"%>
<%@page import="edu.ucam.modelo.Usuario"%>
<%@page import="java.util.HashMap"%>
<%@page import="edu.ucam.commons.Constantes"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page session="true" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Editar usuario</title>
</head>
<body>
	<%
		HashMap<String, Usuario> usuarios = (HashMap<String, Usuario>)Singleton.getUsuarioDAO().obtenerListaUsuarios();
		String email = (String)request.getParameter(Constantes.EMAIL);
		
		if(usuarios != null && email != null){
			Usuario u = usuarios.get(email);
	%>
	<h2 style="display: flex; justify-content: space-between; align-items: center;">
		Edición de usuario
		<a style="text-decoration: none;" href="redireccionarpanel">
			<button style="font-size: 16px; padding: 5px; color: white; background: blue;">Volver</button>
		</a>
	</h2>
	
	<form action="control">
		<input type="hidden" name="<%=Constantes.ACTION%>" value="PUTUSER">
		Email: <input name="<%=Constantes.EMAIL%>" value="<%=email%>" title="No puede editar su email" readonly><br><br>
		Nombre: <input name="<%=Constantes.NUEVO_NOMBRE%>" value="<%=u.getNombre()%>" required><br><br>
		Contraseña: <input name="<%=Constantes.NUEVA_PASSWORD%>" value="<%=u.getPassword()%>" required><br><br>
		<input type="submit" value="Editar">
	</form>
	
	<%
		}else{
			out.print("Ha ocurrido un error con la página<br>");
		}
	%>
</body>
</html>