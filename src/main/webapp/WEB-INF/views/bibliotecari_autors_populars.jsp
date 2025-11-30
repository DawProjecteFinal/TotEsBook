<%-- 
    Document   : bibliotecari_autors_populars
    Created on : 30 nov 2025, 19:20:08
    Author     : edinsonioc
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="ca">
<head>
    <title>Autors Populars</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-5">
        <a href="${pageContext.request.contextPath}/dashboard_bibliotecari" class="btn btn-outline-secondary mb-3">&larr; Tornar al Panell</a>
        
        <h2 class="mb-4">Autors Més Populars (Top 10)</h2>
        
        <ul class="list-group">
            <c:forEach var="item" items="${stats}" varStatus="status">
                <li class="list-group-item d-flex justify-content-between align-items-center">
                    <span>
                        <span class="badge bg-primary rounded-pill me-2">#${status.count}</span>
                        <span class="fw-bold">${item.autor}</span>
                    </span>
                    <span class="badge bg-secondary rounded-pill">${item.totalPrestecs} préstecs</span>
                </li>
            </c:forEach>
        </ul>
    </div>
</body>
</html>