<%-- 
    Author     : Equip TotEsBook

--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

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
                                            <li><a class="dropdown-item" href="${pageContext.request.contextPath}/perfil">Perfil</a></li>
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
        <!-- ===== FI CAPÇALERA INCRUSTADA ===== -->

        <!-- Secció Principal de Contingut -->
        <section class="py-5 flex-grow-1">
            <div class="container px-4 px-lg-5 mt-5">

                <!-- Salutació compacta -->
                <h3 class="text-center text-tot-bold mb-3">
                    <img src="${pageContext.request.contextPath}/assets/icons/books.png"
                         alt="" style="width:60px; height:60px;">
                    Hola <c:out value="${sessionScope.sessioUsuari.nomComplet}"/>
                    <img src="${pageContext.request.contextPath}/assets/icons/book_pen.png" 
                         style="width:60px; height:60px;">
                </h3>

                <c:if test="${teSancioActiva}">
                    <div class="alert alert-danger d-flex align-items-center justify-content-between mb-4" role="alert">
                        <div>
                            <strong>Atenció!</strong><br>
                            Recorda que tens una sanció activa fins al 
                            <strong>${dataFiSancio}</strong> per 
                            <strong>${motiuSancio}</strong>.<br>
                            No podràs fer cap reserva fins aquesta data.
                        </div>
                        <i class="bi bi-exclamation-triangle-fill fs-3 ms-3"></i>
                    </div>
                </c:if>

                <!-- BLOC DE CERCA PRINCIPAL -->
                <div class="mb-5">
                    <div class="p-4 shadow-sm bg-white rounded-3">
                        <form class="row g-3 align-items-center justify-content-center"
                              method="GET"
                              action="${pageContext.request.contextPath}/mostrarLlibres">

                            <!-- Cercar per títol -->
                            <div class="col-12 col-md-6">
                                <input type="text"
                                       name="q"
                                       class="form-control form-control-lg"
                                       placeholder="Cerca per títol..."
                                       autocomplete="off">
                            </div>

                            <!-- Selecció de categoria -->
                            <div class="col-12 col-md-4">
                                <select name="categoria" class="form-select form-select-lg">
                                    <option value="">Totes les categories</option>
                                    <option value="Self-Help">Autoajuda</option>
                                    <option value="Biography &amp; Autobiography">Biografies i memòries</option>
                                    <option value="True Crime">Crims reals</option>
                                    <option value="Cooking">Cuina i gastronomia</option>
                                    <option value="Juvenile Fiction">Ficció juvenil</option>
                                    <option value="Fiction">Novel·la i ficció</option>
                                    <option value="Young Adult Fiction">Novel·la juvenil</option>
                                    <option value="Psychology">Psicologia</option>
                                </select>
                            </div>

                            <!-- Botó de cerca -->
                            <div class="col-12 col-md-2 d-grid">
                                <button type="submit" class="btn btn-tot btn-lg d-flex align-items-center">
                                    <img src="${pageContext.request.contextPath}/assets/icons/buscar.png"
                                         alt="" style="width:30px; height:30px;">  Cercar
                                </button>
                            </div>
                        </form>
                    </div>
                </div>

                <!-- COS DEL PANELL -->
                <div class="row gx-lg-5">

                    <!-- Columna Esquerra: Préstecs i Reserves -->
                    <div class="col-lg-8 mb-4 mb-lg-0">

                        <!-- PRÉSTECS ACTIUS -->
                        <div class="card shadow-sm mb-4">
                            <div class="card-header bg-totlight d-flex justify-content-between align-items-center">
                                <h4 class="mb-0 text-tot-bold">
                                    <img src="${pageContext.request.contextPath}/assets/icons/prestec_usuari.png"
                                         alt="Prèstecs actius" style="width:40px; height:40px;"> Els Meus Préstecs Actius
                                </h4>

                                <!-- Enllaç a historial (modal) -->
                                <button type="button"
                                        class="btn btn-link btn-sm text-decoration-none"
                                        data-bs-toggle="modal"
                                        data-bs-target="#modalHistorialPrestecs">
                                    <img src="${pageContext.request.contextPath}/assets/icons/historial.png"
                                         alt="Historial" style="width:40px; height:40px;">
                                    Veure historial
                                </button>
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
                                                            Data préstec: ${prestec.dataPrestecFormatada}
                                                        </small>
                                                        <small class="d-block text-muted">
                                                            Retornar a:
                                                            <c:choose>
                                                                <c:when test="${not empty bibliosPerIsbn[prestec.llibre.isbn]}">
                                                                    <c:forEach var="rel" items="${bibliosPerIsbn[prestec.llibre.isbn]}" varStatus="st">
                                                                        <c:out value="${rel.biblioteca.nom}"/>
                                                                        <c:if test="${!st.last}"> · </c:if>
                                                                    </c:forEach>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <span class="text-muted">Consultar biblioteca</span>
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </small>
                                                        <small class="d-block text-danger fw-bold">
                                                            Retornar abans de: ${prestec.dataVencimentFormatada}
                                                        </small>
                                                    </div>
                                                    <!-- Botó renovar (placeholder) -->
                                                    <button class="btn btn-sm btn-tot">Renovar</button>
                                                </li>
                                            </c:forEach>
                                        </ul>
                                    </c:when>
                                    <c:otherwise>
                                        <p class="text-muted mb-0">Actualment no tens cap préstec actiu.</p>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>

                        <!-- RESERVES -->
                        <div class="card shadow-sm mb-4">
                            <div class="card-header bg-totlight">
                                <h4 class="mb-0 text-tot-bold">
                                    <img src="${pageContext.request.contextPath}/assets/icons/reservat_usuari.png"
                                         alt="Les meves reserves" style="width:40px; height:40px;"> Les Meves Reserves
                                </h4>
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
                                                            ${reserva.dataReservaFormatted}
                                                        </small>
                                                        <small class="d-block text-muted">
                                                            Recollida a:
                                                            <c:choose>
                                                                <c:when test="${not empty bibliosPerIsbn[reserva.llibre.isbn]}">
                                                                    <c:forEach var="rel" items="${bibliosPerIsbn[reserva.llibre.isbn]}" varStatus="st">
                                                                        <c:out value="${rel.biblioteca.nom}"/>
                                                                        <c:if test="${!st.last}"> · </c:if>
                                                                    </c:forEach>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <span class="text-muted">Consultar biblioteca</span>
                                                                </c:otherwise>
                                                            </c:choose>
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
                                                    <form id="formCancel_${reserva.idReserva}" 
                                                          action="${pageContext.request.contextPath}/cancelReserva" 
                                                          method="POST">
                                                        <input type="hidden" name="idReserva" value="${reserva.idReserva}">
                                                        <button type="button" 
                                                                class="btn btn-sm btn-outline-danger"
                                                                onclick="confirmarCancelacio(${reserva.idReserva})">
                                                            Cancel·lar
                                                        </button>
                                                    </form>

                                                </li>
                                            </c:forEach>
                                        </ul>
                                    </c:when>
                                    <c:otherwise>
                                        <p class="text-muted mb-0">No tens cap reserva activa.</p>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>

                    </div>

                    <!-- Columna Dreta: Propostes -->
                    <div class="col-lg-4">

                        <!-- PROPOSTES D'ADQUISICIÓ -->
                        <div class="card shadow-sm mb-4">
                            <div class="card-header bg-totlight">
                                <h4 class="mb-0 text-tot-bold">
                                    <img src="${pageContext.request.contextPath}/assets/icons/sugerencies_usuari.png"
                                         alt="Propostes usuari" style="width:40px; height:40px;"> Propostes d'Adquisició
                                </h4>
                            </div>

                            <div class="card-body">
                                <p>Has trobat a faltar algun llibre? Fes-nos una proposta!</p>

                                <!-- Botó per fer una proposta -->
                                <a href="${pageContext.request.contextPath}/propostes/formulari_proposta"
                                   class="btn btn-sm btn-tot w-100 mb-2">
                                    <i class="bi bi-plus-circle"></i> Fer una Proposta
                                </a>

                                <!-- Botó per veure les teves propostes -->
                                <a href="${pageContext.request.contextPath}/propostes/llista_propostes"
                                   class="btn btn-sm btn-outline-secondary w-100">
                                    <i class="bi bi-list-ul"></i> Veure les meves Propostes
                                </a>
                            </div>
                        </div>


                    </div>
                </div>

                <!-- MODAL: HISTORIAL DE PRÉSTECS -->
                <div class="modal fade" id="modalHistorialPrestecs" tabindex="-1" aria-hidden="true">
                    <div class="modal-dialog modal-lg modal-dialog-scrollable">
                        <div class="modal-content">
                            <div class="modal-header bg-totlight">
                                <h5 class="modal-title">
                                    <i class="bi bi-clock-history me-2"></i>Historial de Préstecs
                                </h5>
                                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Tancar"></button>
                            </div>
                            <div class="modal-body">
                                <c:choose>
                                    <c:when test="${not empty historialPrestecs}">
                                        <ul class="list-group">
                                            <c:forEach var="prestec" items="${historialPrestecs}">
                                                <li class="list-group-item">
                                                    <strong><c:out value="${prestec.llibre.titol}"/></strong>
                                                    <div class="small text-muted">
                                                        Prestat el: ${prestec.dataPrestecFormatada}
                                                    </div>
                                                    <div class="small">
                                                        Retornat el: ${prestec.dataDevolucioFormatada}
                                                    </div>
                                                </li>
                                            </c:forEach>
                                        </ul>
                                    </c:when>
                                    <c:otherwise>
                                        <p class="text-muted mb-0">
                                            Encara no tens cap préstec retornat.
                                        </p>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Secció de mostra de llibres destacats -->
                <h2 class="text-center text-tot-bold my-5">Novetats Destacades</h2>

                <c:if test="${not empty errorCarregantLlibres}">
                    <div class="alert alert-warning text-center">
                        <c:out value="${errorCarregantLlibres}"/>
                    </div>
                </c:if>

                <div class="row gx-4 gx-lg-5 row-cols-2 row-cols-md-3 row-cols-xl-4 justify-content-center">
                    <c:forEach var="llibre" items="${llibres}">
                        <div class="col mb-5">
                            <div class="card h-100 shadow-sm">
                                <img src="<c:url value='${llibre.imatgeUrl}'/>"
                                     class="card-img-top img-fixed mx-auto d-block"
                                     alt="Portada de ${llibre.titol}">

                                <div class="card-body d-flex flex-column">

                                    <h5 class="card-titlealeat mb-1 text-tot-bold text-center">
                                        <c:out value="${llibre.titol}"/>
                                    </h5>

                                    <p class="text-muted mb-2 text-center">
                                        <c:out value="${llibre.autor}"/>
                                    </p>

                                    <ul class="list-unstyled small mb-3 text-center">
                                        <li><strong>ISBN:</strong> <c:out value="${llibre.isbn}"/></li>
                                    </ul>

                                    <a class="btn btn-tot mt-auto w-100"
                                       href="${pageContext.request.contextPath}/llibre?isbn=${llibre.isbn}">
                                        Més informació
                                    </a>

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


        <!-- ===== TOASTS ===== -->
        <div class="toast-container position-fixed bottom-0 end-0 p-3">

            <!-- Toast d'èxit -->
            <c:if test="${not empty success}">
                <div id="successToast" class="toast" role="alert" aria-live="assertive" aria-atomic="true">
                    <div class="toast-header bg-success text-white">
                        <i class="bi bi-check-circle-fill me-2"></i>
                        <strong class="me-auto">Èxit!</strong>
                        <button type="button" class="btn-close btn-close-white"
                                data-bs-dismiss="toast" aria-label="Close"></button>
                    </div>
                    <div class="toast-body">
                        ${success}
                    </div>
                </div>
            </c:if>

            <!-- Toast d'error -->
            <c:if test="${not empty error}">
                <div id="errorToast" class="toast" role="alert" aria-live="assertive" aria-atomic="true">
                    <div class="toast-header bg-danger text-white">
                        <i class="bi bi-exclamation-triangle-fill me-2"></i>
                        <strong class="me-auto">Error</strong>
                        <button type="button" class="btn-close btn-close-white"
                                data-bs-dismiss="toast" aria-label="Close"></button>
                    </div>
                    <div class="toast-body">
                        ${error}
                    </div>
                </div>
            </c:if>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets/js/alerts.js"></script>
        <script src="${pageContext.request.contextPath}/assets/js/dashboard_bibliotecari.js"></script>
    </body>
</html>
