<%@page import="edu.ucam.commons.Singleton"%>
<%@page import="edu.ucam.modelo.Espacio"%>
<%@page import="java.util.HashMap"%>
<%@page import="edu.ucam.commons.Constantes"%>
<%@page import="org.apache.tomcat.jakartaee.bcel.Const"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page session="true" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Espacios</title>
</head>
<body>
    <h2 style="display: flex; justify-content: space-between; align-items: center;">
    Gestión de Espacios
    <a style="text-decoration: none;" href="redireccionarpanel">
		<button style="font-size: 16px; padding: 5px; color: white; background: blue;">Volver al panel</button>
	</a>
	</h2>
	    
    <p style="color:blue;"><%= request.getAttribute(Constantes.MENSAJE) != null ? request.getAttribute(Constantes.MENSAJE) : "" %></p>
    <p style="color:red;"><%= request.getAttribute(Constantes.MENSAJE_ERROR) != null ? request.getAttribute(Constantes.MENSAJE_ERROR) : "" %></p>
    <form action="control" method="post">
        <input type="hidden" name="<%=Constantes.ACTION %>" value="POSTESP">
        Ciudad: <input type="text" name="<%=Constantes.CIUDAD_ESPACIO %>" required><br>
        Edificio: <input type="text" name="<%=Constantes.EDIFICIO_ESPACIO%>" required><br>
        Planta: <input type="number" name="<%=Constantes.PLANTA_ESPACIO %>" min="0" required><br>
        Número de puerta: <input type="number" name="<%=Constantes.NPUERTA_ESPACIO %>" min="0" required><br>
        Descripción: <input type="text" name="<%=Constantes.DESCRIPCION_ESPACIO%>"><br>
        <input type="submit" value="Crear espacio">
    </form>

    <hr>
    <%
    	HashMap<Integer, Espacio> espacios = (HashMap<Integer, Espacio>)Singleton.getEspacioDAO().obtenerListaEspacios();
    	if(espacios!=null){
    		if(!espacios.isEmpty()){
    			%>
    			<h3>Listado de Espacios</h3>
    			<%
    			for(Espacio e : espacios.values()){
    				%>
    				<div style="border: 1px solid #ccc; padding: 10px; margin-bottom: 10px;">
	    				<form action="control" method="post">
	    				<input type="hidden" name="<%=Constantes.ID_ESPACIO %>" value="<%=e.getId()%>">
						<p><strong>ID: </strong><%=e.getId()%></p>
						<p><strong>Ciudad: </strong><input type="text" name="<%=Constantes.CIUDAD_ESPACIO %>" value="<%=e.getCiudad() %>" required><br></p>
						<p><strong>Edificio: </strong><input type="text" name="<%=Constantes.EDIFICIO_ESPACIO %>" value="<%=e.getEdificio()%>" required><br></p>
						<p><strong>Planta: </strong><input type="number" name="<%=Constantes.PLANTA_ESPACIO %>" value="<%=e.getPlanta() %>" min="0" required><br></p>
						<p><strong>Número de puerta: </strong><input type="number" name="<%=Constantes.NPUERTA_ESPACIO %>" value="<%=e.getNumeroPuerta() %>" min="0" required><br></p>
						<p><strong>Descripción: </strong><input type="text" name="<%=Constantes.DESCRIPCION_ESPACIO %>" value="<%=e.getDescripcion() %>"><br></p>
						
						<button type="submit" name="<%=Constantes.ACTION%>" value="PUTESP" >Editar</button>
						<button type="submit" name="<%=Constantes.ACTION%>" value="DELESP" onclick="return confirm('¿Estás seguro de eliminar este espacio? Se eliminarán también todas sus reservas');">Eliminar espacio</button>
						
						</form>
					</div>
					<%
    			}
    		}else{
    			%>
    			<h4>Aún no hay espacios en la base de datos</h4>
    			<%
    		}
    	}else{
    		%>
    		<h6 style="color:red;">Hubo un error con la base de datos al intentar recuperar la lista de usuarios</h6>
    		<%
    	}
    %>
</body>
</html>