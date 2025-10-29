<%-- 
    Document   : dashboard_bibliotecari.jsp
    Created on : 23 oct 2025, 14:27:27
    Author     : edinsonioc
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%-- TODO: Necessitaràs un Servlet que carregui dades rellevants per al bibliotecari --%>
<%-- (Ex: préstecs pendents de devolució, reserves per preparar, etc.) --%>
<!DOCTYPE html>
<html lang="ca">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Panell de Bibliotecari - TotEsBook</title>
    <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/assets/favicon.ico" />
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/styles.css">
</head>
<body class="d-flex flex-column min-vh-100">

    <!-- ===== INICI CAPÇALERA INCRUSTADA ===== -->
    <nav class="navbar navbar-expand-lg navbar-light bg-totlight sticky-top shadow-sm">
        <div class="container px-4 px-lg-5">
            <a class="navbar-brand" href="${pageContext.request.contextPath}/paginaInici">
                <img src="${pageContext.request.contextPath}/assets/img/logo.jpg" alt="Logo TotEsBook" height="30" class="d-inline-block align-text-top logo">
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
                            <li><a class="dropdown-item" href="${pageContext.request.contextPath}/mostrarLlibres">Veure Tot</a></li>
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
                         <%-- Enllaços específics per a bibliotecari/admin --%>
                         <li class="nav-item"><a class="nav-link" href="#">Gestionar Préstecs</a></li>
                     </c:if>
                </ul>
                <div class="d-flex align-items-center ms-lg-auto">
                    <form class="d-flex me-3 my-2 my-lg-0" role="search" action="${pageContext.request.contextPath}/cerca" method="GET">
                        <input class="form-control form-control-sm me-2" type="search" name="q" 
                               placeholder="Cerca ràpida..." aria-label="Search">
                        <button class="btn btn-tot btn-sm" type="submit">
                            <i class="bi bi-search"></i>
                        </button>
                    </form>
                    <c:choose>
                        <c:when test="${empty sessionScope.sessioUsuari}">
                            <a href="${pageContext.request.contextPath}/login" class="btn btn-tot btn-sm my-2 my-lg-0">
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
                                             <li><a class="dropdown-item" href="${pageContext.request.contextPath}/dashboard_usuari">El Meu Panell</a></li>
                                         </c:when>
                                         <c:when test="${sessionScope.sessioUsuari.rol == 'BIBLIOTECARI'}">
                                              <li><a class="dropdown-item active" href="${pageContext.request.contextPath}/dashboard_bibliotecari">Panell Bibliotecari</a></li> <%-- Marcat com actiu --%>
                                         </c:when>
                                         <c:when test="${sessionScope.sessioUsuari.rol == 'ADMIN'}">
                                              <li><a class="dropdown-item" href="${pageContext.request.contextPath}/dashboard_administrador">Panell Admin</a></li>
                                         </c:when>
                                    </c:choose>
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
    <!-- ===== FI CAPÇALERA INCRUSTADA ===== -->

    <!-- Secció Principal de Contingut -->
    <section class="py-5 flex-grow-1">
        <div class="container px-4 px-lg-5 mt-5">
            <h1 class="text-center text-tot-bold mb-5">Panell de Bibliotecari</h1>

             <c:if test="${not empty sessionScope.sessioUsuari}">
                <p class="lead text-center mb-5">Benvingut/da, <c:out value="${sessionScope.sessioUsuari.nomComplet}"/>!</p>
             </c:if>

             <%-- Contingut específic del bibliotecari --%>
             <div class="row gx-lg-5">
                 <%-- Columna Esquerra: Préstecs/Devolucions --%>
                 <div class="col-lg-6 mb-4">
                     <div class="card shadow-sm h-100">
                          <div class="card-header bg-totlight">
                             <h4 class="mb-0 text-tot-bold"><i class="bi bi-arrow-down-up me-2"></i> Registrar Préstec / Devolució</h4>
                          </div>
                          <div class="card-body">
                            <%-- TODO: Crear un servlet per gestionar aquest formulari --%>
                            <form action="${pageContext.request.contextPath}/gestionarPrestec" method="POST">
                                <div class="mb-3">
                                    <label for="isbnPrestec" class="form-label">ISBN del Llibre</label>
                                    <input type="text" class="form-control" id="isbnPrestec" name="isbn" required>
                                </div>
                                <div class="mb-3">
                                    <label for="emailUsuari" class="form-label">Email de l'Usuari</label>
                                    <input type="email" class="form-control" id="emailUsuari" name="emailUsuari" required>
                                </div>
                                <div class="d-grid gap-2 d-md-flex justify-content-md-end">
                                    <button type="submit" name="accio" value="prestar" class="btn btn-primari-custom">Registrar Préstec</button>
                                    <button type="submit" name="accio" value="retornar" class="btn btn-secondary">Registrar Devolució</button>
                                </div>
                            </form>
                          </div>
                     </div>
                 </div>
                 
                 <%-- Columna Dreta: Reserves Pendents --%>
                 <div class="col-lg-6 mb-4">
                     <div class="card shadow-sm h-100">
                          <div class="card-header bg-totlight">
                             <h4 class="mb-0 text-tot-bold"><i class="bi bi-bookmark-check-fill me-2"></i> Reserves Pendents de Recollida</h4>
                          </div>
                          <div class="card-body">
                            <%-- TODO: Carregar 'reservesPendents' des d'un Servlet --%>
                             <c:choose>
                                <c:when test="${not empty reservesPendents}">
                                     <ul class="list-group list-group-flush">
                                        <%-- <c:forEach var="reserva" items="${reservesPendents}"> --%>
                                             <li class="list-group-item">
                                                 <strong>Llibre Reservat 1 (Exemple)</strong> <%-- ${reserva.llibre.titol} --%>
                                                 <small class="d-block text-muted">Usuari: Nom Usuari 1</small> <%-- ${reserva.usuari.nomComplet} --%>
                                                  <small class="d-block text-muted">Data reserva: DD/MM/AAAA</small>
                                             </li>
                                              <li class="list-group-item">Exemple 2...</li>
                                        <%-- </c:forEach> --%>
                                    </ul>
                                </c:when>
                                <c:otherwise>
                                    <p class="text-muted">No hi ha reserves pendents de recollida.</p>
                                </c:otherwise>
                            </c:choose>
                          </div>
                     </div>
                 </div>
             </div>
             
             <%-- Altres funcionalitats: Gestionar catàleg, Propostes --%>

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

    <!-- Script de Bootstrap Bundle -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>