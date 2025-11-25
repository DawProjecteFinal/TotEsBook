<%-- 
    Author     : Equip TotEsBook
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="ca">
    <head>
        <meta charset="UTF-8">
        <title>Afegir Nova Biblioteca</title>
        <!-- CSS de Bootstrap, Bootstrap Icons i estils personalitzats -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/styles.css">
    </head>
    <body class="bg-light">

        <div class="container py-5">
            <h1 class="text-center mb-4 text-tot-bold">Afegir Nova Biblioteca</h1>

            <form action="${pageContext.request.contextPath}/gestio/biblioteques/afegir" method="POST"
                  class="card shadow-sm p-4">

                <div class="mb-3">
                    <label class="form-label">Nom:</label>
                    <input type="text" name="nom" class="form-control" required>
                </div>

                <div class="mb-3">
                    <label class="form-label">Adreça:</label>
                    <input type="text" name="adreca" class="form-control">
                </div>

                <div class="mb-3">
                    <label class="form-label">Telèfon:</label>
                    <input type="text" name="telefon" class="form-control">
                </div>

                <div class="mb-3">
                    <label class="form-label">Email:</label>
                    <input type="email" name="email" class="form-control">
                </div>

                <div class="mb-3">
                    <label class="form-label">Bibliotecari Responsable:</label>
                    <select name="idBibliotecari" class="form-select">
                        <option value="">-- Sense assignar --</option>
                        <c:forEach var="a" items="${llistaBibliotecaris}">
                            <option value="${a.idAgent}">${a.nom} ${a.cognoms}</option>
                        </c:forEach>
                    </select>
                </div>

                <div class="text-end">
                    <button type="submit" class="btn btn-tot">Crear Biblioteca</button>
                    <a href="${pageContext.request.contextPath}/gestio/biblioteques" class="btn btn-secondary">Cancel·lar</a>
                </div>
            </form>
        </div>

    </body>
</html>
