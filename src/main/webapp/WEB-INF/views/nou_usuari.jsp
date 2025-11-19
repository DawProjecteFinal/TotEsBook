<%-- 
    Document   : nou_usuari
    Created on : 19 nov 2025, 14:30:19
    Author     : edinsonioc
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="ca">
<head>
    <meta charset="UTF-8">
    <title>Registrar Nou Lector - TotEsBook</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css">
</head>
<body class="bg-light">
    
    <div class="container mt-5">
        <div class="row justify-content-center">
            <div class="col-md-6 col-lg-5">
                
                <div class="card shadow-sm border-0">
                    <div class="card-header bg-success text-white text-center py-3">
                        <h4 class="mb-0"><i class="bi bi-person-plus-fill"></i> Nou Lector</h4>
                    </div>
                    <div class="card-body p-4">
                        
                        <!-- Missatges d'error -->
                        <c:if test="${not empty error}">
                            <div class="alert alert-danger" role="alert">
                                ${error}
                            </div>
                        </c:if>

                        <form action="${pageContext.request.contextPath}/bibliotecari/crear-usuari" method="POST">
                            <div class="row mb-3">
                                <div class="col-md-6">
                                    <label for="nom" class="form-label">Nom</label>
                                    <input type="text" class="form-control" id="nom" name="nom" required>
                                </div>
                                <div class="col-md-6">
                                    <label for="cognoms" class="form-label">Cognoms</label>
                                    <input type="text" class="form-control" id="cognoms" name="cognoms" required>
                                </div>
                            </div>
                            
                            <div class="mb-3">
                                <label for="telefon" class="form-label">Telèfon</label>
                                <input type="text" class="form-control" id="telefon" name="telefon" required>
                            </div>

                            <div class="mb-3">
                                <label for="email" class="form-label">Correu Electrònic</label>
                                <input type="email" class="form-control" id="email" name="email" placeholder="client@exemple.com" required>
                            </div>

                            <div class="mb-3">
                                <label for="password" class="form-label">Contrasenya</label>
                                <input type="password" class="form-control" id="password" name="password" required>
                            </div>

                            <div class="d-grid gap-2 mt-4">
                                <button type="submit" class="btn btn-success">Registrar Usuari</button>
                                <a href="${pageContext.request.contextPath}/dashboard_bibliotecari" class="btn btn-outline-secondary">Cancel·lar</a>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
