<%-- 
    Document   : fitxa_llibre
    Created on : 27 oct 2025, 18:44:37
    Author     : edinsonioc
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="ca">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    
    <title>Fitxa: <c:out value="${llibre.titol}" /> - TotEsBook</title>
    
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
    
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

    <jsp:include page="/WEB-INF/header.jsp" />

    <section class="featured-section">
        <div class="container">
            <div class="row justify-content-center">
                <div class="col-lg-10">
                    
                    <a href="${pageContext.request.contextPath}/paginaInici.jsp" class="btn btn-sm btn-outline-secondary mb-3">
                        <i class="bi bi-arrow-left"></i> Tornar al catàleg
                    </a>

                    <div class="featured-card p-4">
                        <div class="row g-4">
                            <div class="col-md-4 text-center">
                                <img src="<c:url value='${llibre.imatgeUrl}' />" 
                                     class="img-fluid rounded shadow-sm" 
                                     alt="Portada de <c:out value='${llibre.titol}' />"
                                     style="max-height: 450px; object-fit: cover;">
                            </div>
                            
                            <div class="col-md-8">
                                <h1 class="h2"><c:out value="${llibre.titol}" /></h1>
                                <h4 class="h5 text-muted"><c:out value="${llibre.autor}" /></h4>
                                <hr>
                                
                                <h4>Sinopsi</h4>
                                <c:choose>
                                    <c:when test="${not empty llibre.sinopsis}">
                                        <p><c:out value="${llibre.sinopsis}" /></p>
                                    </c:when>
                                    <c:otherwise>
                                        <p class="text-muted"><em>No hi ha sinopsi disponible.</em></p>
                                    </c:otherwise>
                                </c:choose>
                                
                                <ul class="list-group list-group-flush mt-3">
                                    <li class="list-group-item"><strong>ISBN:</strong> <c:out value="${llibre.isbn}" /></li>
                                    <li class="list-group-item"><strong>Editorial:</strong> <c:out value="${llibre.editorial}" /></li>
                                    <li class="list-group-item"><strong>Idioma:</strong> <c:out value="${llibre.idioma}" /></li>
                                    <li class="list-group-item">
                                        <strong>Disponibilitat:</strong> 
                                        <c:if test="${llibre.disponibles > 0}">
                                            <span class="badge bg-success">Disponible (${llibre.disponibles} exemplars)</span>
                                        </c:if>
                                        <c:if test="${llibre.disponibles <= 0}">
                                            <span class="badge bg-danger">No disponible</span>
                                        </c:if>
                                    </li>
                                </ul>
                                
                                <c:if test="${llibre.disponibles > 0}">
                                    <button class="btn btn-primari-custom mt-4">Demanar en préstec</button>
                                </c:if>
                                <c:if test="${llibre.disponibles <= 0}">
                                    <button class="btn btn-accent-custom mt-4">Reservar</button>
                                </c:if>
                            </div>
                        </div>
                    </div>

                </div>
            </div>
        </div>
    </section>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>