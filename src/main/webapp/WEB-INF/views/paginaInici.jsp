<%-- 
    Document   : paginaInici
    Created on : 28 sept 2025, 17:43:00
    Author     : jmiro
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hola equip de TotEsBook. Aquesta és la pàgina d'inici</h1>
        <h1> ${benvinguda} </h1>
        <form action="${pageContext.request.contextPath}/mostrarLlibres" method="get">
            <button type="submit">Veure el llistat de llibres</button>
        </form>
            
        <form action="${pageContext.request.contextPath}/mostrarUsuaris" method="get">
            <button type="submit">Veure el llistat de usuaris</button>
        </form>
    </body>
</html>
