<%-- 
    Document   : gestionar_biblioteques
    Created on : 9 nov 2025, 11:06:59
    Author     : jmiro
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="ca">
    <head>
        <meta charset="UTF-8">
        <title>Gestió de Biblioteca</title>
        <!-- CSS de Bootstrap, Bootstrap Icons i estils personalitzats -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/styles.css">
    </head>
    <body class="bg-light">

        <div class="container py-5">
            <h1 class="mb-4 text-center text-tot-bold">Gestió de la Biblioteca</h1>

            <div class="card shadow-sm mb-4">
                <div class="card-body">
                    <h4 class="card-title">${biblioteca.nom}</h4>
                    <p><strong>Adreça:</strong> ${biblioteca.adreca}</p>
                    <p><strong>Telèfon:</strong> ${biblioteca.telefon}</p>
                    <p><strong>Email:</strong> ${biblioteca.email}</p>
                    <p><strong>Bibliotecari responsable:</strong>
                        <c:choose>
                            <c:when test="${not empty biblioteca.bibliotecari}">
                                ${biblioteca.bibliotecari.nom} ${biblioteca.bibliotecari.cognoms}
                            </c:when>
                            <c:otherwise><em>Sense assignar</em></c:otherwise>
                        </c:choose>
                    </p>
                </div>
            </div>

            <div class="d-flex justify-content-between">
                <a href="${pageContext.request.contextPath}/gestio/biblioteques/${biblioteca.idBiblioteca}/editar"
                   class="btn btn-warning"><i class="bi bi-pencil"></i> Editar</a>

                <a href="${pageContext.request.contextPath}/gestio/biblioteques" class="btn btn-secondary">
                    <i class="bi bi-arrow-left"></i> Tornar al llistat
                </a>
            </div>

            <hr class="my-5">

            <h5 class="mb-3 text-tot-bold">Altres opcions de gestió</h5>
            <div class="list-group shadow-sm">
                <a href="${pageContext.request.contextPath}/llibres/biblioteca/${biblioteca.idBiblioteca}" class="list-group-item list-group-item-action">
                    Veure i gestionar llibres
                </a>
                <a href="${pageContext.request.contextPath}/prestecs/biblioteca/${biblioteca.idBiblioteca}" class="list-group-item list-group-item-action">
                    Veure préstecs
                </a>
            </div>
        </div>

    </body>
</html>
