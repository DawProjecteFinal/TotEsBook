<%-- 
    Document   : mostrarUsuaris
    Created on : 24 oct 2025, 11:48:48
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
        <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <h1>Aquesta és la pàgina dels usuaris que hi ha a la Base de dades!</h1>
        <!--<h2>${usuaris}</h2>-->
        <c:forEach var="usuari" items="${usuaris}">
            <div>
                <p><strong>Nom:</strong> ${usuari.nom}</p>
                <p><strong>Cognoms:</strong> ${usuari.cognoms}</p>
                <p><strong>Telèfon:</strong> ${usuari.telefon}</p>
                <p><strong>Email:</strong> ${usuari.email}</p>
                <p><strong>Llibres favorits:</strong> ${usuari.llibresFavorits}</p>
             
                <hr>
            </div>
        </c:forEach>
    </body>
</html>
     