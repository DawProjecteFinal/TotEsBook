<%-- 
    Document   : contacte
    Created on : 1 dic 2025, 8:27:38
    Author     : Equip TotEsBook
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ca">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Contacte</title>
        <link rel="icon" type="image/x-icon" href="assets/favicon.ico" />
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/styles.css">
    </head>

    <body class="d-flex flex-column min-vh-100">
        <!-- ===== Encapçalat ===== -->
        <header class="bg-tot py-1">
            <div class="container px-4 px-lg-5 my-1">
                <div class="text-center text-white">
                    <img src="${pageContext.request.contextPath}/assets/images/logo-gran.jpeg" alt="Logo" class="logo-header"> 
                </div>
            </div>
        </header>
        <!-- ===== FI Encapçalat ===== -->

        <!-- ===== Menu ===== -->
        <nav class="navbar navbar-expand-lg navbar-light bg-totlight">
            <div class="container px-4 px-lg-5">

                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Menú">
                    <span class="navbar-toggler-icon"></span>
                </button>

                <div class="collapse navbar-collapse" id="navbarSupportedContent">

                    <ul class="navbar-nav me-auto mb-2 mb-lg-0 ms-lg-4">
                        <li class="nav-item"><a class="nav-link active" aria-current="page" href="${pageContext.request.contextPath}">Inici</a></li>
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/biblioteques">Biblioteques</a>
                        </li>
                        <c:if test="${not empty sessionScope.sessioUsuari && sessionScope.sessioUsuari.rol == 'ADMIN'}">
                            <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/mostrarUsuaris">Gestió Usuaris</a></li> 
                            <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/admin/estadistiques">Estadístiques</a></li>
                            </c:if>
                            <c:if test="${not empty sessionScope.sessioUsuari && sessionScope.sessioUsuari.rol == 'BIBLIOTECARI'}">
                            <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/admin/estadistiques">Estadístiques</a></li>
                            </c:if>
                            <c:if test="${not empty sessionScope.sessioUsuari && sessionScope.sessioUsuari.rol == 'USUARI'}">
                            <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/mostrarLlibres">Catàleg</a></li> 
                            <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/propostes/llista_propostes">Propostes</a></li>
                            </c:if>
                    </ul>

                    <!-- Cerca per títol -->        
                    <div class="d-flex align-items-center ms-lg-auto">
                        <form class="d-flex me-3 my-2 my-lg-0" role="search" method="GET" action="${pageContext.request.contextPath}/mostrarLlibres">
                            <input class="form-control form-control-sm me-2" type="search" name="q" 
                                   placeholder="Cerca per titol" aria-label="Search" autocomplete="off" required
                                   oninvalid="this.setCustomValidity('Aquest camp és obligatori')"
                                   oninput="this.setCustomValidity('')" />
                            <button class="btn btn-tot btn-sm" type="submit">
                                <i class="bi bi-search"></i>
                            </button>
                        </form>

                        <%-- Lògica de Sessió per a Login/Logout --%>
                        <c:choose>

                            <c:when test="${empty sessionScope.sessioUsuari}">
                                <a href="${pageContext.request.contextPath}/login" class="btn btn-tot btn-sm my-2 my-lg-0">
                                    Inicia sessió <i class="bi bi-person-circle"></i>
                                </a>
                            </c:when>

                            <c:otherwise>
                                <div class="dropdown">
                                    <button class="btn btn-tot btn-sm dropdown-toggle" type="button" id="dropdownUsuari"
                                            data-bs-toggle="dropdown">
                                        <i class="bi bi-person-fill"></i>
                                        <c:out value="${sessionScope.sessioUsuari.nomComplet}"/>
                                    </button>

                                    <ul class="dropdown-menu dropdown-menu-end">
                                        <c:if test="${sessionScope.sessioUsuari.rol == 'ADMIN'}">
                                            <li><a class="dropdown-item" href="${pageContext.request.contextPath}/dashboard_administrador">Panell Admin</a></li>
                                            </c:if>

                                        <c:if test="${sessionScope.sessioUsuari.rol == 'BIBLIOTECARI'}">
                                            <li><a class="dropdown-item" href="${pageContext.request.contextPath}/dashboard_bibliotecari">Panell Bibliotecari</a></li>
                                            </c:if>

                                        <c:if test="${sessionScope.sessioUsuari.rol == 'USUARI'}">
                                            <li><a class="dropdown-item" href="${pageContext.request.contextPath}/dashboard_usuari">Panell Usuari</a></li>
                                            </c:if>

                                        <li><hr class="dropdown-divider"></li>

                                        <li><a class="dropdown-item text-danger" href="${pageContext.request.contextPath}/logout">
                                                <i class="bi bi-box-arrow-right"></i> Tancar Sessió
                                            </a></li>
                                    </ul>
                                </div>
                            </c:otherwise>
                        </c:choose>

                    </div>
                </div>
            </div>
        </nav>
        <!-- ===== FI Menu ===== -->

        <!-- ===== Secció Principal de Contingut ===== -->
        <div class="container mt-5">
            <h2 class="text-center text-tot-bold mb-3">Contacta amb l'equip tècnic de TotEsBook</h2>

            <p class="text-center fw-bold"><strong>Email de suport</strong> 
                <a href="mailto:suport@totesbook.com">suport@totesbook.com</a></p>

            <div class="container mt-5 mb-5" style="max-width: 600px;">
                <form action="#" method="post">
                    <div class="mb-3">
                        <label>Nom</label>
                        <input type="text" name="nom" class="form-control" required>
                    </div>

                    <div class="mb-3">
                        <label>Cognoms</label>
                        <input type="text" name="cognoms" class="form-control" required>
                    </div>

                    <div class="mb-3">
                        <label>Correu electrònic</label>
                        <input type="email" name="correu" class="form-control" required>
                    </div>

                    <div class="mb-3">
                        <label>Missatge</label>
                        <textarea name="missatge" class="form-control" rows="5" required></textarea>
                    </div>

                    <div class="form-check mb-3">
                        <input class="form-check-input" type="checkbox" required>
                        <label class="form-check-label">
                            Accepto la <a href="${pageContext.request.contextPath}/informacio-privacitat">política de privacitat</a>
                        </label>
                    </div>

                    <button type="submit" class="btn btn-primary">Enviar</button>
                </form>
            </div>
        </div>


        <!-- ===== Peu de pàgina ===== -->
        <footer class="bg-tot text-center text-lg-start border-top mt-auto py-3"> 
            <div class="container">
                <div class="row align-items-center">
                    <div class="col-md-4 mb-3 mb-md-0">
                        <h6 class="fw-bold">TotEsBook</h6>
                        <p class="mb-0 small">Projecte de gestió de biblioteques · DAW M12</p>
                    </div>
                    <div class="col-md-4 mb-3 mb-md-0">
                        <ul class="list-unstyled mb-0">
                            <li><a href="${pageContext.request.contextPath}/contacte" class="text-decoration-none text-secondary">Contacte</a></li>
                            <li><a href="${pageContext.request.contextPath}/sobre-nosaltres" class="text-decoration-none text-secondary">Sobre nosaltres</a></li>
                            <li><a href="${pageContext.request.contextPath}/informacio-legal" class="text-decoration-none text-secondary">Informació legal</a></li>
                            <li><a href="${pageContext.request.contextPath}/informacio-privacitat" class="text-decoration-none text-secondary">Política de privacitat</a></li>
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
        <!-- ===== FI Peu de pàgina ===== -->

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets/js/cerca-avancada.js"></script>
    </body>
</html>
