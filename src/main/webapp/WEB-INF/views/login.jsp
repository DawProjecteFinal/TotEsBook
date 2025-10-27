<%-- 
    Document   : login
    Created on : 23 oct 2025, 15:19:16
    Author     : edinsonioc
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ca">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Inici de Sessió - TotEsBook</title>
    
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="css/style.css">
</head>
<body>

    <jsp:include page="/WEB-INF/header.jsp" />

    <div class="container">
        <div class="row justify-content-center">
            <div class="col-md-8 col-lg-6">
                
                <div class="form-page-container">
                    <h2>Inici de Sessió</h2>
                    
                    <c:if test="${not empty error}">
                        <div class="alert alert-danger" role="alert">
                            <c:out value="${error}" />
                        </div>
                    </c:if>

                    <form action="login" method="POST">
                        <div class="mb-3">
                            <label for="email" class="form-label fw-bold">Correu Electrònic</label>
                            <input type="email" class="form-control form-control-lg" id="email" name="email" required>
                        </div>
                        
                        <div class="mb-3">
                            <label for="password" class="form-label fw-bold">Contrasenya</label>
                            <input type="password" class="form-control form-control-lg" id="password" name="password" required>
                        </div>
                        
                        <button type="submit" class="btn btn-primari-custom mt-3">Entrar</button>
                    </form>
                    
                    <div class="text-center mt-4">
                        <a href="recuperarPass.jsp">Has oblidat la contrasenya?</a>
                        <p class="mt-2">No tens un compte? <a href="registro.jsp">Registra't</a></p>
                    </div>
                </div>

            </div>
        </div>
    </div>
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>