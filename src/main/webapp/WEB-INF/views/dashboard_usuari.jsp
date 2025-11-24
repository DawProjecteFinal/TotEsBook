<%-- 
    Document   : dashboard_usuari.jsp
    Created on : 23 oct 2025, 14:26:50
    Author     : edinsonioc
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


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
                    <img src="${pageContext.request.contextPath}/assets/images/logo-gran.jpeg" alt="Logo TotEsBook" height="30" class="d-inline-block align-text-top logo">
                </a>
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
                        data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false"
                        aria-label="Menú">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse" id="navbarSupportedContent">
                    <ul class="navbar-nav me-auto mb-2 mb-lg-0 ms-lg-4">
                        <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}">Inici</a></li>
                        <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/biblioteques">Biblioteques</a></li>
                        <!-- Dropdown + categories -->
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle" id="navbarDropdown" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">Categories</a>
                            <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                                <li><a class="dropdown-item" href="${pageContext.request.contextPath}/mostrarLlibres">Totes les categories</a></li>
                                <li><hr class="dropdown-divider" /></li>
                                <li><a class="dropdown-item" href="${pageContext.request.contextPath}/mostrarLlibres?categoria=Self-Help">Autoajuda</a></li>
                                <li><a class="dropdown-item" href="${pageContext.request.contextPath}/mostrarLlibres?categoria=Biography%20%26%20Autobiography">Biografíes i Memòries</a></li>                                   
                                <li><a class="dropdown-item" href="${pageContext.request.contextPath}/mostrarLlibres?categoria=True Crime">Crims reals</a></li>
                                <li><a class="dropdown-item" href="${pageContext.request.contextPath}/mostrarLlibres?categoria=Cooking">Cuina i gastronomia</a></li>
                                <li><a class="dropdown-item" href="${pageContext.request.contextPath}/mostrarLlibres?categoria=Juvenile Fiction">Ficció juvenil</a></li>
                                <li><a class="dropdown-item" href="${pageContext.request.contextPath}/mostrarLlibres?categoria=Fiction">Novel·la i ficció</a></li>
                                <li><a class="dropdown-item" href="${pageContext.request.contextPath}/mostrarLlibres?categoria=Young Adult Fiction">Novel·la juvenil</a></li>
                                <li><a class="dropdown-item" href="${pageContext.request.contextPath}/mostrarLlibres?categoria=Psychology">Psicologia</a></li>
                            </ul>
                        </li>
                        <!-- Dropdown + formulari cerca avançada -->
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle" id="dropdownAdvanced" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                                Cerca avançada
                            </a>
                            <ul class="dropdown-menu" aria-labelledby="dropdownAdvanced">
                                <li><a class="dropdown-item" href="#" data-field="autor">Autor</a></li>
                                <li><hr class="dropdown-divider" /></li>
                                <li><a class="dropdown-item" href="#" data-field="idioma">Idioma</a></li>
                                <li><hr class="dropdown-divider" /></li>
                                <li><a class="dropdown-item" href="#" data-field="isbn">ISBN</a></li>
                            </ul>
                        </li>
                        <form id="advancedSearch" class="d-flex me-3 my-2 my-lg-0" method="get" action="<c:url value='/cercar'/>">
                            <input type="hidden" name="field" id="field" value="">

                            <div id="searchGroup" class="input-group d-none">
                                <!-- Input de text per autor / isbn -->
                                <input id="searchInput" class="form-control form-control-sm me-2" name="q" type="search" placeholder="" aria-label="Advanced search" autocomplete="off"
                                       required oninvalid="this.setCustomValidity('Aquest camp és obligatori')" oninput="this.setCustomValidity('')" />
                                <select id="idiomaSelect" class="form-select form-select-sm me-2 d-none">
                                    <option value="">Tria l'idioma</option>
                                    <option value="ca">Català</option>
                                    <option value="es">Castellà</option>
                                </select>

                                <button class="btn btn-tot btn-sm" type="submit">
                                    <i class="bi bi-search"></i>
                                </button>
                            </div>
                        </form>
                    
                        <c:if test="${not empty sessionScope.sessioUsuari}">
                            <li class="nav-item"><a class="nav-link" href="#">Propostes</a></li>
                            </c:if>
                    </ul>
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
                <h1 class="text-center text-tot-bold mb-5">El Meu Panell d'Usuari</h1>

                <c:if test="${not empty sessionScope.sessioUsuari}">
                    <p class="lead text-center mb-5">Benvingut/da, <c:out value="${sessionScope.sessioUsuari.nomComplet}"/>!</p>
                </c:if>

                <div class="row gx-lg-5">
                    <%-- Columna Esquerra: Préstecs i Reserves --%>
                    <div class="col-lg-8">
                        <!-- Columna Esquerra: Préstecs i Reserves -->
                        <div class="col-lg-8">

                            <!-- PRÉSTECS ACTIUS -->
                            <div class="card shadow-sm mb-4">
                                <div class="card-header bg-totlight">
                                    <h4 class="mb-0 text-tot-bold"><i class="bi bi-book-fill me-2"></i> Els Meus Préstecs Actius</h4>
                                </div>
                                <div class="card-body">

                                    <c:choose>
                                        <c:when test="${not empty meusPrestecs}">
                                            <ul class="list-group list-group-flush">

                                                <c:forEach var="prestec" items="${meusPrestecs}">
                                                    <li class="list-group-item d-flex justify-content-between align-items-center flex-wrap">

                                                        <div class="me-3 mb-2 mb-md-0">
                                                            <strong><c:out value="${prestec.llibre.titol}"/></strong>

                                                            <small class="d-block text-muted">
                                                                Data préstec:
                                                                ${prestec.dataPrestecFormatada}                                                            </small>

                                                            <small class="d-block text-danger fw-bold">
                                                                Retornar abans de:
                                                                ${prestec.dataVencimentFormatada}
                                                            </small>
                                                        </div>

                                                        <!-- Botó renovar (encara no implementat) -->
                                                        <button class="btn btn-sm btn-tot">Renovar</button>
                                                    </li>
                                                </c:forEach>

                                            </ul>
                                        </c:when>

                                        <c:otherwise>
                                            <p class="text-muted">Actualment no tens cap préstec actiu.</p>
                                        </c:otherwise>
                                    </c:choose>

                                </div>
                            </div>

                            <!-- HISTORIAL DE PRÉSTECS -->
                            <div class="card shadow-sm mb-4">
                                <div class="card-header bg-totlight">
                                    <h4 class="mb-0 text-tot-bold">
                                        <i class="bi bi-clock-history me-2"></i> Historial de Préstecs
                                    </h4>
                                </div>

                                <div class="card-body">

                                    <c:choose>

                                        <c:when test="${not empty historialPrestecs}">
                                            <ul class="list-group list-group-flush">

                                                <c:forEach var="prestec" items="${historialPrestecs}">

                                                    <li class="list-group-item">

                                                        <strong>${prestec.llibre.titol}</strong>

                                                        <div class="small text-muted">
                                                            Prestat el:
                                                            ${prestec.dataPrestecFormatada}
                                                        </div>

                                                        <div class="small">
                                                            Retornat el:
                                                            ${prestec.dataDevolucioFormatada}
                                                        </div>

                                                    </li>
                                                </c:forEach>

                                            </ul>
                                        </c:when>

                                        <c:otherwise>
                                            <p class="text-muted">Encara no tens cap préstec retornat.</p>
                                        </c:otherwise>

                                    </c:choose>

                                </div>
                            </div>


                            <!-- RESERVES -->
                            <div class="card shadow-sm mb-4">
                                <div class="card-header bg-totlight">
                                    <h4 class="mb-0 text-tot-bold"><i class="bi bi-bookmark-fill me-2"></i> Les Meves Reserves</h4>
                                </div>
                                <div class="card-body">

                                    <c:choose>

                                        <c:when test="${not empty mevesReserves}">
                                            <ul class="list-group list-group-flush">

                                                <c:forEach var="reserva" items="${mevesReserves}">
                                                    <li class="list-group-item d-flex justify-content-between align-items-center flex-wrap">

                                                        <div class="me-3 mb-2 mb-md-0">

                                                            <strong><c:out value="${reserva.llibre.titol}"/></strong>

                                                            <small class="d-block text-muted">
                                                                Data reserva:
                                                                <fmt:formatDate value="${reserva.dataReserva}" pattern="dd/MM/yyyy HH:mm"/>
                                                            </small>

                                                            <!-- BADGE segons estat -->
                                                            <span class="badge
                                                                  ${reserva.estat == 'pendent' ? 'bg-warning text-dark' :
                                                                    reserva.estat == 'disponible' ? 'bg-success' :
                                                                    reserva.estat == 'cancelada' ? 'bg-danger' : 
                                                                    'bg-secondary'}">

                                                                Estat: <c:out value="${reserva.estat}"/>
                                                            </span>
                                                        </div>

                                                        <!-- Botó cancel·lar reserva -->
                                                        <form action="${pageContext.request.contextPath}/cancelReserva" method="POST">
                                                            <input type="hidden" name="idReserva" value="${reserva.idReserva}">
                                                            <button class="btn btn-sm btn-outline-danger">Cancel·lar</button>
                                                        </form>

                                                    </li>
                                                </c:forEach>

                                            </ul>
                                        </c:when>

                                        <c:otherwise>
                                            <p class="text-muted">No tens cap reserva activa.</p>
                                        </c:otherwise>

                                    </c:choose>

                                </div>
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
                                <a href="${pageContext.request.contextPath}/perfil" class="btn btn-sm btn-outline-secondary mt-2">
                                    Editar Perfil
                                </a>

                            </div>
                        </div>
                        <div class="card shadow-sm mb-4">
                            <div class="card-header bg-totlight">
                                <h4 class="mb-0 text-tot-bold"><i class="bi bi-lightbulb-fill me-2"></i> Propostes d'Adquisició</h4>
                            </div>
                            <div class="card-body">
                                <p>Has trobat a faltar algun llibre? Fes-nos una proposta!</p>

                                <button class="btn btn-sm btn-tot w-100">Fer una Proposta</button>
                            </div>
                        </div>
                    </div>

                    <!-- Secció de mostra de llibres destacats -->
                    <h2 class="text-center text-tot-bold mb-4">Novetats Destacades</h2>

                    <c:if test="${not empty errorCarregantLlibres}">
                        <div class="alert alert-warning text-center">
                            <c:out value="${errorCarregantLlibres}"/>
                        </div>
                    </c:if>

                    <div class="row gx-4 gx-lg-5 row-cols-2 row-cols-md-3 row-cols-xl-4 justify-content-center">

                        <c:forEach var="llibre" items="${llibres}"> 
                            <div class="col mb-5">
                                <div class="card h-100 shadow-sm">
                                    <img src="<c:url value='${llibre.imatgeUrl}'/>" class="card-img-top img-fixed mx-auto d-block" alt="Portada de <c:out value='${llibre.titol}'/>">
                                    <div class="card-body d-flex flex-column">
                                        <h5 class="card-title mb-1 text-tot-bold"><c:out value="${llibre.titol}"/></h5>
                                        <p class="text-muted mb-2 text-tot-light"><c:out value="${llibre.autor}"/></p>
                                        <ul class="list-unstyled small mb-3 text-tot-isbn"> 
                                            <li><strong>ISBN:</strong> <c:out value="${llibre.isbn}"/></li>
                                        </ul>
                                        <div class="mt-auto text-center">
                                            <a class="btn btn-tot mt-auto w-100" href="${pageContext.request.contextPath}/llibre?isbn=${llibre.isbn}">Més informació</a>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>

                        <c:if test="${empty llibres && empty errorCarregantLlibres}">
                            <div class="col-12 text-center text-muted">
                                <p>No s'han trobat llibres destacats en aquest moment.</p>
                            </div>
                        </c:if>
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

        <!-- ============================================= -->
        <!-- ===== INICI CODI NOU: Toast (Pop-up) ===== -->
        <!-- ============================================= -->

        <!-- 1. Contenidor del Toast (Pop-up) -->
        <!-- El situem a la cantonada inferior dreta -->
        <div class="toast-container position-fixed bottom-0 end-0 p-3">

            <!-- Toast per a missatges d'èxit (p.ex. perfil guardat) -->
            <c:if test="${not empty success}">
                <div id="successToast" class="toast" role="alert" aria-live="assertive" aria-atomic="true">
                    <div class="toast-header bg-success text-white">
                        <i class="bi bi-check-circle-fill me-2"></i>
                        <strong class="me-auto">Èxit!</strong>
                        <button type="button" class="btn-close btn-close-white" data-bs-dismiss="toast" aria-label="Close"></button>
                    </div>
                    <div class="toast-body">
                        ${success}
                    </div>
                </div>
            </c:if>

            <!-- Toast per a missatges d'error -->
            <c:if test="${not empty error}">
                <div id="errorToast" class="toast" role="alert" aria-live="assertive" aria-atomic="true">
                    <div class="toast-header bg-danger text-white">
                        <i class="bi bi-exclamation-triangle-fill me-2"></i>
                        <strong class="me-auto">Error</strong>
                        <button type="button" class="btn-close btn-close-white" data-bs-dismiss="toast" aria-label="Close"></button>
                    </div>
                    <div class="toast-body">
                        ${error}
                    </div>
                </div>
            </c:if>
        </div>
        <!-- ===== FI CODI NOU: Toast (Pop-up) ===== -->


        <!-- Script de Bootstrap Bundle -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

        <!-- ==================================================== -->
        <!-- ===== INICI CODI NOU: Script per activar el Toast ===== -->
        <!-- ==================================================== -->
        <script>
            document.addEventListener("DOMContentLoaded", function () {

                // Buscar el toast d'èxit
                const successToastEl = document.getElementById('successToast');
                if (successToastEl) {
                    const successToast = new bootstrap.Toast(successToastEl);
                    successToast.show();
                }

                // Buscar el toast d'error
                const errorToastEl = document.getElementById('errorToast');
                if (errorToastEl) {
                    const errorToast = new bootstrap.Toast(errorToastEl);
                    errorToast.show();
                }
            });
        </script>
        <script src="${pageContext.request.contextPath}/assets/js/cerca-avancada.js"></script>
        <!-- ===== FI CODI NOU: Script per activar el Toast ===== -->

    </body>
</html>