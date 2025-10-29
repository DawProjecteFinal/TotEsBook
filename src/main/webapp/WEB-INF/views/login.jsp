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
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Inici de Sessió - TotEsBook</title>
    <%-- Enllaços corregits amb contextPath --%>
    <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/assets/favicon.ico" />
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body class="d-flex flex-column min-vh-100">

    <!-- ===== INICI CAPÇALERA INCRUSTADA ===== -->
    <nav class="navbar navbar-expand-lg navbar-light bg-totlight sticky-top shadow-sm">
        <div class="container px-4 px-lg-5">
            <a class="navbar-brand" href="${pageContext.request.contextPath}/paginaInici.jsp">
                <img src="${pageContext.request.contextPath}/img/logo.jpg" alt="Logo TotEsBook" height="30" class="d-inline-block align-text-top logo">
            </a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
                    data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false"
                    aria-label="Menú">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav me-auto mb-2 mb-lg-0 ms-lg-4">
                    <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/paginaInici">Inici</a></li>
                    <li class="nav-item"><a class="nav-link" href="#">Biblioteques</a></li>
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" id="navbarDropdown" href="#" role="button"
                           data-bs-toggle="dropdown" aria-expanded="false">Catàleg</a>
                        <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                            <li><a class="dropdown-item" href="${pageContext.request.contextPath}/cataleg">Veure Tot</a></li>
                            <li><hr class="dropdown-divider" /></li>
                            <li><a class="dropdown-item" href="#">Autoajuda</a></li>
                            <li><a class="dropdown-item" href="#">Ficció</a></li>
                            <li><a class="dropdown-item" href="#">Juvenil</a></li>
                            <li><a class="dropdown-item" href="#">Novel·la</a></li>
                            <li><a class="dropdown-item" href="#">True crime</a></li>
                        </ul>
                    </li>
                     <%-- L'usuari no està loguejat, no mostrem enllaços de sessió --%>
                </ul>
                <div class="d-flex align-items-center ms-lg-auto">
                    <form class="d-flex me-3 my-2 my-lg-0" role="search" action="${pageContext.request.contextPath}/cerca" method="GET">
                        <input class="form-control form-control-sm me-2" type="search" name="q"
                               placeholder="Cerca ràpida..." aria-label="Search">
                        <button class="btn btn-tot btn-sm" type="submit">
                            <i class="bi bi-search"></i>
                        </button>
                    </form>
                    <%-- Lògica de Sessió (mostra "Iniciar Sessió" com a actiu) --%>
                    <a href="${pageContext.request.contextPath}/login" class="btn btn-tot btn-sm my-2 my-lg-0 active">
                        Inicia sessió <i class="bi bi-person-circle"></i>
                    </a>
                </div>
            </div>
        </div>
    </nav>
    <!-- ===== FI CAPÇALERA INCRUSTADA ===== -->

    <!-- Secció Principal de Contingut -->
    <section class="py-5 flex-grow-1">
        <div class="container px-4 px-lg-5">
            <div class="row justify-content-center">
                <div class="col-md-8 col-lg-6 col-xl-5">

                    <div class="form-page-container mt-5">
                        <h2>Inici de Sessió</h2>

                        <%-- Mostra error si el LoginServlet en passa un --%>
                        <c:if test="${not empty error}">
                            <div class="alert alert-danger" role="alert">
                                <i class="bi bi-exclamation-triangle-fill me-2"></i> <c:out value="${error}" />
                            </div>
                        </c:if>

                        <%-- Formulari que apunta al LoginServlet (URL: /login) --%>
                        <form action="${pageContext.request.contextPath}/login" method="POST">
                            <div class="mb-3">
                                <label for="email" class="form-label fw-bold">Correu Electrònic</label>
                                <input type="email" class="form-control form-control-lg" id="email" name="email" required>
                            </div>

                            <div class="mb-4">
                                <label for="contrasenya" class="form-label fw-bold">Contrasenya</label>
                                <%-- CORRECCIÓ: El 'name' ha de ser 'contrasenya' per coincidir amb el servlet --%>
                                <input type="password" class="form-control form-control-lg" id="contrasenya" name="contrasenya" required>
                            </div>

                            <button type="submit" class="btn btn-primari-custom btn-lg w-100">Entrar</button>
                        </form>

                        <div class="text-center mt-4">
                            <a href="${pageContext.request.contextPath}/recuperarPass.jsp">Has oblidat la contrasenya?</a>
                            <p class="mt-2">No tens un compte? <a href="${pageContext.request.contextPath}/registre.jsp">Registra't</a></p>
                        </div>
                    </div>

                </div>
            </div>
        </div>
    </section>

    <!-- ===== INICI PEU DE PÀGINA INCRUSTAT ===== -->
    <footer class="bg-tot text-center text-lg-start border-top mt-auto py-3">
        <div class="container">
            <div class="row align-items-center">
                <div class="col-md-4 mb-3 mb-md-0">
                    <h6 class="fw-bold">TotEsBook</h6>
                    <p class="mb-0 small">Projecte de gestió de biblioteques · DAW M12</p>
                </div>
                <div class="col-md-4 mb-3 mb-md-0">
                    <ul class="list-unstyled mb-0">
                        <li><a href="${pageContext.request.contextPath}/contacte.jsp" class="text-decoration-none text-secondary">Contacte</a></li>
                        <li><a href="#" class="text-decoration-none text-secondary">Informació legal</a></li>
                        <li><a href="#" class="text-decoration-none text-secondary">Política de privacitat</a></li>
                    </ul>
                </div>
                <div class="col-md-4">
                    <div class="d-flex justify-content-center justify-content-md-end">
                        <a href="#"><i class="bi bi-twitter mx-2 text-secondary"></i></a>
                        <a href="#"><i class="bi bi-facebook mx-2 text-secondary"></i></a>
                        <a href="#"><i class="bi bi-instagram mx-2 text-secondary"></i></a>
                    </div>
                    <p class="fst-italic small mt-2 mb-0 text-center text-md-end">“Llegir és viure mil vides.”</p>
                </div>
            </div>
            <hr class="my-3">
            <p class="text-center small text-muted mb-0">© 2025 TotEsBook. Tots els drets reservats.</p>
        </div>
    </footer>
    <!-- ===== FI PEU DE PÀGINA INCRUSTAT ===== -->

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>