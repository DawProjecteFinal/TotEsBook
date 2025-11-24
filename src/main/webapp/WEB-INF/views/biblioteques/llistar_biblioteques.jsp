
<%--
    Document   : llistar_biblioteques
    Created on : 9 nov 2025, 11:06:03
    Author     : jmiro
    Created on : 10 nov 2025
    Author     : Equip TotEsBook
    Description: Llista totes les biblioteques per a la gestió (accessible per ADMIN i BIBLIOTECARI).
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="ca">
<head>
    <meta charset="UTF-8">
    <title>Gestió de Biblioteques</title>
    <!-- CSS de Bootstrap, Bootstrap Icons i estils personalitzats -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/styles.css">
</head>
<body class="bg-light">

<div class="container py-5">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h1 class="text-tot-bold">Gestió de Biblioteques</h1>
        <c:if test="${rol == 'ADMIN'}">
            <a href="${pageContext.request.contextPath}/gestio/biblioteques/afegir" class="btn btn-tot">
                <i class="bi bi-plus-circle-fill me-1"></i> Afegir Biblioteca
            </a>
        </c:if>
    </div>

    <%-- Missatges de feedback (success o error) --%>
    <c:if test="${not empty success}">
        <div class="alert alert-success alert-dismissible fade show" role="alert">
            ${success}
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
    </c:if>
    <c:if test="${not empty error}">
        <div class="alert alert-danger alert-dismissible fade show" role="alert">
            ${error}
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
    </c:if>

    <div class="card shadow-sm">
        <div class="card shadow-sm">
            <div class="card-body">
                <div class="table-responsive">
                    <table class="table table-striped table-hover align-middle">
                        <thead class="table-light">
                            <tr>
                                <th>ID</th>
                                <th>Nom</th>
                                <th>Adreça</th>
                                <th>Bibliotecari</th>
                                <th class="text-center">Accions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="b" items="${llistaBiblioteques}">
                                <tr>
                                    <td>${b.idBiblioteca}</td>
                                    <td><c:out value="${b.nom}"/></td>
                                    <td><c:out value="${b.adreca}"/></td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${not empty b.bibliotecari}">${b.bibliotecari.nom} ${b.bibliotecari.cognoms}</c:when>
                                            <c:otherwise><span class="text-muted fst-italic">No assignat</span></c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td class="text-center">
                                        <div class="btn-group btn-group-sm" role="group">
                                            <a href="${pageContext.request.contextPath}/gestio/biblioteques/${b.idBiblioteca}" class="btn btn-outline-primary" title="Gestionar">
                                                <i class="bi bi-gear"></i> Gestionar
                                            </a>
                                            <a href="${pageContext.request.contextPath}/gestio/biblioteques/${b.idBiblioteca}/editar" class="btn btn-outline-warning" title="Editar"><i class="bi bi-pencil"></i></a>
                                            <c:if test="${rol == 'ADMIN'}">
                                                <form action="${pageContext.request.contextPath}/gestio/biblioteques/${b.idBiblioteca}/eliminar" method="POST" class="d-inline" onsubmit="return confirm('Segur que vols eliminar aquesta biblioteca?');">
                                                    <button type="submit" class="btn btn-outline-danger" title="Eliminar"><i class="bi bi-trash"></i></button>
                                                </form>
                                            </c:if>
                                        </div>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>
