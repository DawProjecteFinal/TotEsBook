<%-- 
    Author     : Equip TotEsBook
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Llista de propostes</title>
        <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/assets/favicon.ico" />
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/styles.css">


    </head>
    <body class="bg-light d-flex flex-column min-vh-100">        
        <!-- ===== INICI CAPÇALERA INCRUSTADA ===== -->
        <nav class="navbar navbar-expand-lg navbar-light bg-totlight sticky-top shadow-sm">
            <div class="container px-4 px-lg-5">

                <a class="navbar-brand" href="${pageContext.request.contextPath}">
                    <img src="${pageContext.request.contextPath}/assets/images/logo-gran.jpeg"
                         alt="Logo TotEsBook"
                         height="30"
                         class="d-inline-block align-text-top logo">
                </a>
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
                        data-bs-target="#navbarSupportedContent"
                        aria-controls="navbarSupportedContent"
                        aria-expanded="false"
                        aria-label="Menú">
                    <span class="navbar-toggler-icon"></span>
                </button>

                <!-- MENÚ -->
                <div class="collapse navbar-collapse" id="navbarSupportedContent">
                    <ul class="navbar-nav me-auto mb-2 mb-lg-0 ms-lg-4">
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}">Inici</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/biblioteques">Biblioteques</a></li>
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/mostrarLlibres">Catàleg</a></li>
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/propostes/llista_propostes">Propostes</a></li>
                    </ul>

                    <!-- Menú d’usuari -->
                    <div class="d-flex align-items-center ms-lg-auto">

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

        <section class="flex-grow-1">
            <div class="container mt-5 mb-4">

                <!-- Botó Tornar, alineat amb el contenidor principal -->
                <a href="${pageContext.request.contextPath}/dashboard_usuari"
                   class="btn btn-outline-secondary mb-4">
                    <i class="bi bi-arrow-left"></i> Tornar al Panell
                </a>

                <!-- Contenidor centrat per Títol + Botó + Taula -->
                <div class="mx-auto" style="max-width: 1100px;">

                    <!-- Títol + botó Nova proposta, alineats amb la taula -->
                    <div class="d-flex justify-content-between align-items-center mb-4 flex-wrap gap-3">
                        <h2 class="text-tot-bold mb-0">
                            📚 Propostes d’adquisició
                        </h2>

                        <a href="${pageContext.request.contextPath}/propostes/formulari_proposta"
                           class="btn btn-primary text-nowrap">
                            <i class="bi bi-plus"></i> Nova proposta
                        </a>
                    </div>

                    <!-- Missatge si no hi ha cap proposta -->
                    <c:if test="${empty propostes}">
                        <div class="alert alert-info">
                            Encara no has realitzat cap proposta d’adquisició.
                        </div>
                    </c:if>

                    <!-- Taula -->
                    <c:if test="${not empty propostes}">
                        <div class="table-responsive shadow-sm">
                            <table class="table table-hover align-middle bg-white rounded">
                                <thead class="table-dark">
                                    <tr>
                                        <th>ID</th>
                                        <th>Títol</th>
                                        <th>Autor</th>
                                        <th>ISBN</th>
                                        <th>Data</th>
                                        <th>Estat</th>
                                        <th>Accions</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="p" items="${propostes}">
                                        <tr>
                                            <td>${p.idProposta}</td>
                                            <td>${p.titol}</td>
                                            <td>${p.autor}</td>
                                            <td>${p.isbn}</td>
                                            <td>${p.dataPropostaFormatted}</td>
                                            <td>
                                                <span class="badge badge-${p.estat}">
                                                    ${p.estat}
                                                </span>
                                            </td>
                                            <td>
                                                <c:if test="${p.estat == 'pendent'}">
                                                    <a href="${pageContext.request.contextPath}/propostes/eliminar?id=${p.idProposta}"
                                                       class="btn btn-sm btn-outline-danger"
                                                       onclick="return confirm('Segur que vols eliminar aquesta proposta?');">
                                                        <i class="bi bi-trash"></i>Eliminar
                                                    </a>
                                                </c:if>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </c:if>

                </div> 

            </div>
        </section>

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
    </body>
</html>