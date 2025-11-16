<%-- 
    Document   : editar_biblioteca
    Created on : 9 nov 2025, 11:35:43
    Author     : jmiro
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="ca">
    <head>
        <meta charset="UTF-8">
        <title>Editar Biblioteca</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body class="bg-light">

        <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

        <div class="container mt-5">
            <h2 class="mb-4">Editar Biblioteca</h2>

            <form action="${pageContext.request.contextPath}/gestio/biblioteques/${biblioteca.idBiblioteca}/editar" method="post">
                <input type="hidden" name="idBiblioteca" value="${biblioteca.idBiblioteca}" />
                <div class="mb-3">
                    <label class="form-label">Nom</label>
                    <input type="text" name="nom" class="form-control" value="${biblioteca.nom}" required>
                </div>

                <div class="mb-3">
                    <label class="form-label">Adreça</label>
                    <input type="text" name="adreca" class="form-control" value="${biblioteca.adreca}">
                </div>

                <div class="mb-3">
                    <label class="form-label">Telèfon</label>
                    <input type="text" name="telefon" class="form-control" value="${biblioteca.telefon}">
                </div>

                <div class="mb-3">
                    <label class="form-label">Email</label>
                    <input type="email" name="email" class="form-control" value="${biblioteca.email}">
                </div>

                <div class="mb-3">
                    <label class="form-label">Bibliotecari Responsable</label>
                    <select name="idBibliotecari" class="form-select">
                        <option value="">-- Selecciona un bibliotecari --</option>

                        <c:forEach var="b" items="${llistaBibliotecaris}">
                            <option value="${b.idAgent}"
                                    ${biblioteca.bibliotecari != null && biblioteca.bibliotecari.idAgent == b.idAgent ? 'selected' : ''}>
                                ${b.nom} ${b.cognoms}
                            </option>
                        </c:forEach>
                    </select>

                </div>

                <button type="submit" class="btn btn-tot">Guardar Canvis</button>
                <a href="${pageContext.request.contextPath}/gestio/biblioteques" class="btn btn-secondary">Cancel·lar</a>
            </form>
        </div>

    </body>
</html>
