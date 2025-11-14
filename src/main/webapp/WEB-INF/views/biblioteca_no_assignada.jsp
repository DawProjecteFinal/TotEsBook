<%-- 
    Document   : biblioteca_no_assignada
    Created on : 14 nov 2025, 7:34:07
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
        <div class="alert alert-warning shadow-sm mt-5">
            <h4 class="alert-heading">Biblioteca no assignada</h4>
            <p>El teu compte de bibliotecari encara no té cap biblioteca assignada.</p>
            <p>Si us plau, contacta amb un administrador.</p>
            <a href="${pageContext.request.contextPath}/logout" class="btn btn-secondary mt-3">Tancar sessió</a>
        </div>
    </body>
</html>
