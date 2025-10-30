<%-- 
    Document   : dashboard_usuari.jsp
    Created on : 23 oct 2025, 14:26:50
    Author     : edinsonioc
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%-- TODO: Necessitaràs un Servlet que carregui els préstecs de l'usuari --%>
<%-- <%@ page import="java.util.List" %> --%>
<%-- <%@ page import="cat.totesbook.domain.Prestec" %> --%>
<%-- 
    // Lògica (en un Servlet) per obtenir préstecs de l'usuari loguejat:
    cat.totesbook.domain.SessioUsuari sessio = (cat.totesbook.domain.SessioUsuari) session.getAttribute("sessioUsuari");
    // PrestecRepository prestecRepo = new PrestecDAO();
    // List<Prestec> meusPrestecs = prestecRepo.getPrestecsByUsuariId(sessio.getId());
    // request.setAttribute("meusPrestecs", meusPrestecs);
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%-- TODO: Necessitaràs un Servlet que carregui els préstecs ('meusPrestecs') i reserves ('mevesReserves') de l'usuari --%>
<!DOCTYPE html>
<html lang="ca">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>El Meu Panell - TotEsBook</title>
    <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/assets/favicon.ico" />
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/styles.css">
</head>
<body class="d-flex flex-column min-vh-100">

    <!-- ===== INICI CAPÇALERA INCRUSTADA ===== -->
    <nav class="navbar navbar-expand-lg navbar-light bg-totlight sticky-top shadow-sm">
        <div class="container px-4 px-lg-5">
            <a class="navbar-brand" href="${pageContext.request.contextPath}">
                <img src="${pageContext.request.contextPath}/assets/images/logo-gran.jpg" alt="Logo TotEsBook" height="30" class="d-inline-block align-text-top logo">
            </a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
                    data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false"
                    aria-label="Menú">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav me-auto mb-2 mb-lg-0 ms-lg-4">
                    <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}">Inici</a></li>
                    <li class="nav-item"><a class="nav-link" href="#">Biblioteques</a></li>
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" id="navbarDropdown" href="#" role="button"
                           data-bs-toggle="dropdown" aria-expanded="false">Catàleg</a>
                        <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                            <li><a class="dropdown-item" href="${pageContext.request.contextPath}/mostrarLlibres">Totes les categoriest</a></li>
                            <li><hr class="dropdown-divider" /></li>
                            <li><a class="dropdown-item" href="#">Autoajuda</a></li>
                            <li><a class="dropdown-item" href="#">Ficció</a></li>
                            <li><a class="dropdown-item" href="#">Juvenil</a></li>
                            <li><a class="dropdown-item" href="#">Novel·la</a></li>
                            <li><a class="dropdown-item" href="#">True crime</a></li>
                        </ul>
                    </li>
                     <c:if test="${not empty sessionScope.sessioUsuari}">
                         <li class="nav-item"><a class="nav-link" href="#">Propostes</a></li>
                     </c:if>
                </ul>
                <div class="d-flex align-items-center ms-lg-auto">
                    <form class="d-flex me-3 my-2 my-lg-0" role="search" method="GET">
                        <input class="form-control form-control-sm me-2" type="search" name="q" 
                               placeholder="Cerca ràpida..." aria-label="Search">
                        <button class="btn btn-tot btn-sm" type="submit">
                            <i class="bi bi-search"></i>
                        </button>
                    </form>
                    <c:choose>
                        <c:when test="${empty sessionScope.sessioUsuari}">
                            <a href="${pageContext.request.contextPath}/login.jsp" class="btn btn-tot btn-sm my-2 my-lg-0">
                                Inicia sessió <i class="bi bi-person-circle"></i>
                            </a>
                        </c:when>
                        <c:otherwise>
                            <div class="dropdown">
                                <button class="btn btn-tot btn-sm dropdown-toggle active" type="button" id="dropdownUsuari" data-bs-toggle="dropdown" aria-expanded="false"> <%-- Marcat com actiu --%>
                                    <i class="bi bi-person-fill"></i> <c:out value="${sessionScope.sessioUsuari.nomComplet}"/> 
                                </button>
                                <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="dropdownUsuari">
                                    <c:choose>
                                         <c:when test="${sessionScope.sessioUsuari.rol == 'USUARI'}">
                                             <li><a class="dropdown-item active" href="${pageContext.request.contextPath}/dashboard_usuari">El Meu Panell</a></li> <%-- Marcat com actiu --%>
                                         </c:when>
                                         <c:when test="${sessionScope.sessioUsuari.rol == 'BIBLIOTECARI'}">
                                              <li><a class="dropdown-item" href="${pageContext.request.contextPath}/dashboard_bibliotecari">Panell Bibliotecari</a></li>
                                         </c:when>
                                         <c:when test="${sessionScope.sessioUsuari.rol == 'ADMIN'}">
                                              <li><a class="dropdown-item" href="${pageContext.request.contextPath}/dashboard_administrador">Panell Admin</a></li>
                                         </c:when>
                                    </c:choose>
                                    <li><hr class="dropdown-divider"></li>
                                    <li><a class="dropdown-item text-danger" href="${pageContext.request.contextPath}">
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
    <!-- ===== FI CAPÇALERA INCRUSTADA ===== -->

    <!-- Secció Principal de Contingut -->
    <section class="py-5 flex-grow-1">
        <div class="container px-4 px-lg-5 mt-5">
            <h1 class="text-center text-tot-bold mb-5">El Meu Panell d'Usuari</h1>

            <c:if test="${not empty sessionScope.sessioUsuari}">
                <p class="lead text-center mb-5">Benvingut/da, <c:out value="${sessionScope.sessioUsuari.nomComplet}"/>!</p>
            </c:if>

            <div class="row gx-lg-5">
                <%-- Columna Esquerra: Préstecs i Reserves --%>
                <div class="col-lg-8">
                    <div class="card shadow-sm mb-4">
                         <div class="card-header bg-totlight">
                            <h4 class="mb-0 text-tot-bold"><i class="bi bi-book-fill me-2"></i> Els Meus Préstecs Actius</h4>
                         </div>
                         <div class="card-body">
                            <c:choose>
                                <c:when test="${not empty meusPrestecs}">
                                    <ul class="list-group list-group-flush">
                                        <%-- <c:forEach var="prestec" items="${meusPrestecs}"> --%>
                                            <li class="list-group-item d-flex justify-content-between align-items-center flex-wrap">
                                                <div class="me-3 mb-2 mb-md-0">
                                                    <strong>Títol del Llibre (Exemple 1)</strong> <%-- ${prestec.llibre.titol} --%>
                                                    <small class="d-block text-muted">Data préstec: 20/10/2025</small>
                                                    <small class="d-block text-danger fw-bold">Retornar abans de: 03/11/2025</small> 
                                                </div>
                                                <%-- TODO: Implementar Servlet/JS per a renovar --%>
                                                <button class="btn btn-sm btn-tot">Renovar</button> 
                                            </li>
                                            <li class="list-group-item d-flex justify-content-between align-items-center flex-wrap">
                                                <div class="me-3 mb-2 mb-md-0">
                                                    <strong>Un altre Llibre (Exemple 2)</strong> 
                                                    <small class="d-block text-muted">Data préstec: 15/10/2025</small>
                                                    <small class="d-block text-danger fw-bold">Retornar abans de: 29/10/2025</small> 
                                                </div>
                                                <button class="btn btn-sm btn-tot">Renovar</button> 
                                            </li>
                                        <%-- </c:forEach> --%>
                                    </ul>
                                </c:when>
                                <c:otherwise>
                                    <p class="text-muted">Actualment no tens cap préstec actiu.</p>
                                </c:otherwise>
                            </c:choose>
                         </div>
                    </div>

                    <div class="card shadow-sm mb-4">
                         <div class="card-header bg-totlight">
                            <h4 class="mb-0 text-tot-bold"><i class="bi bi-bookmark-fill me-2"></i> Les Meves Reserves</h4>
                         </div>
                         <div class="card-body">
                             <%-- TODO: Iterar sobre la llista 'mevesReserves' --%>
                              <c:choose>
                                <c:when test="${not empty mevesReserves}">
                                    <ul class="list-group list-group-flush">
                                        <%-- <c:forEach var="reserva" items="${mevesReserves}"> --%>
                                            <li class="list-group-item d-flex justify-content-between align-items-center flex-wrap">
                                                <div class="me-3 mb-2 mb-md-0">
                                                     <strong>Llibre Reservat (Exemple)</strong> <%-- ${reserva.llibre.titol} --%>
                                                     <small class="d-block text-muted">Data reserva: 25/10/2025</small> <%-- ${reserva.dataReserva} --%>
                                                     <span class="badge bg-warning text-dark">Estat: PENDENT</span> <%-- ${reserva.estat} (Adaptar color segons estat) --%>
                                                </div>
                                                 <%-- TODO: Implementar Servlet/JS per a cancel·lar --%>
                                                <button class="btn btn-sm btn-outline-danger">Cancel·lar</button>
                                            </li>
                                        <%-- </c:forEach> --%>
                                    </ul>
                                </c:when>
                                <c:otherwise>
                                    <p class="text-muted">No tens cap reserva activa.</p>
                                </c:otherwise>
                            </c:choose>
                         </div>
                    </div>
                </div>

                <%-- Columna Dreta: Accions Ràpides / Informació --%>
                <div class="col-lg-4">
                    <div class="card shadow-sm mb-4">
                         <div class="card-header bg-totlight">
                            <h4 class="mb-0 text-tot-bold"><i class="bi bi-person-badge me-2"></i> El Meu Compte</h4>
                         </div>
                         <div class="card-body">
                            <p><strong>Nom:</strong> <c:out value="${sessionScope.sessioUsuari.nomComplet}"/></p>
                            <p><strong>Email:</strong> <c:out value="${sessionScope.sessioUsuari.email}"/></p>
                             <%-- TODO: Enllaç a una pàgina per editar perfil? --%>
                            <button class="btn btn-sm btn-outline-secondary mt-2">Editar Perfil</button>
                         </div>
                    </div>
                     <div class="card shadow-sm mb-4">
                         <div class="card-header bg-totlight">
                            <h4 class="mb-0 text-tot-bold"><i class="bi bi-lightbulb-fill me-2"></i> Propostes d'Adquisició</h4>
                         </div>
                         <div class="card-body">
                            <p>Has trobat a faltar algun llibre? Fes-nos una proposta!</p>
                             <%-- TODO: Enllaç/Formulari per a noves propostes --%>
                            <button class="btn btn-sm btn-tot w-100">Fer una Proposta</button>
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
                        <li><a href="#" class="text-decoration-none text-secondary">Contacte</a></li> 
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

    <!-- Script de Bootstrap Bundle -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>