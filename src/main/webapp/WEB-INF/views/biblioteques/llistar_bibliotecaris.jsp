<%-- 
    Author     : Equip TotEsBook
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="ca">
    <head>
        <meta charset="UTF-8">
        <title>Bibliotecaris</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body class="bg-light">
        <div class="container py-4">
            <h2 class="mb-3 text-center text-tot-bold">Bibliotecaris disponibles</h2>

            <table class="table table-striped table-hover">
                <thead class="table-light">
                    <tr>
                        <th>ID</th>
                        <th>Nom</th>
                        <th>Email</th>
                        <th>Telèfon</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="b" items="${llistaBibliotecaris}">
                        <tr>
                            <td>${b.idAgent}</td>
                            <td>${b.nom} ${b.cognoms}</td>
                            <td>${b.email}</td>
                            <td>${b.telefon}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>

            <div class="text-center mt-4">
                <a href="${pageContext.request.contextPath}/biblioteques" class="btn btn-secondary">Tornar</a>
            </div>
        </div>
    </body>
</html>
