<%-- 
    Author     : Equip TotEsBook
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

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
                        <c:if test="${not empty sessionScope.sessioUsuari}">
                            <li class="nav-item"><a class="nav-link" href="#">Propostes</a></li>
                                <%-- Enllaços específics per a bibliotecari/admin --%>
                            <li class="nav-item"><a class="nav-link" href="#">Gestionar Préstecs</a></li>
                            <!-- SPRINT 3 (TEA 5): ENLLAÇ ESTADÍSTIQUES -->
                            <li class="nav-item">
                                <a class="nav-link" href="${pageContext.request.contextPath}/bibliotecari/autors-populars">
                                    <i class="bi bi-graph-up"></i> Estadístiques
                                </a>
                            </li>
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
                                <a href="${pageContext.request.contextPath}/login" class="btn btn-tot btn-sm my-2 my-lg-0">
                                    Inicia sessió <i class="bi bi-person-circle"></i>
                                </a>
                            </c:when>
                            <c:otherwise>
                                <div class="dropdown">
                                    <button class="btn btn-tot btn-sm dropdown-toggle active" type="button" id="dropdownUsuari" data-bs-toggle="dropdown" aria-expanded="false">
                                        <%-- Marcat com actiu --%>
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

        <section class="py-5 flex-grow-1">
            <div class="container px-4 px-lg-5 mt-5">
                <h1 class="text-center text-tot-bold mb-3">Panell de Bibliotecari</h1>

                <p class="lead text-center mb-5">
                    Benvingut/da, <strong><c:out value="${sessionScope.sessioUsuari.nomComplet}"/></strong><br>
                    <small class="text-muted">
                        Biblioteca: <strong><c:out value="${sessionScope.sessioUsuari.bibliotecaNom}"/></strong>
                    </small>
                </p>

                <!-- Targeta resum de prestecs -->
                <div class="row text-center mb-5 g-3">
                    <div class="col-6 col-md-3">
                        <div class="card shadow-sm border-start border-4 border-primary">
                            <div class="card-body">
                                <h2 class="fw-bold mb-0">${numPrestecsActius}</h2>
                                <p class="text-muted small mb-0">Préstecs actius</p>
                            </div>
                        </div>
                    </div>
                    <!-- Targeta resum de devolucions -->
                    <div class="col-6 col-md-3">
                        <div class="card shadow-sm border-start border-4 border-success">
                            <div class="card-body">
                                <h2 class="fw-bold mb-0">${numDevolucionsPendents}</h2>
                                <p class="text-muted small mb-0">Devolucions</p>
                            </div>
                        </div>
                    </div>
                    <!-- Targeta resum de reserves -->
                    <div class="col-6 col-md-3">
                        <div class="card shadow-sm border-start border-4 border-warning">
                            <div class="card-body">
                                <h2 class="fw-bold mb-0">${numReservesPendents}</h2>
                                <p class="text-muted small mb-0">Reserves pendents de recollir</p>
                            </div>
                        </div>
                    </div>
                    <!-- Targeta resum de llibres que no s'han retornat a temps -->
                    <div class="col-6 col-md-3">
                        <div class="card shadow-sm border-start border-4 border-danger">
                            <div class="card-body">
                                <h2 class="fw-bold mb-0">${numLlibresRetard}</h2>
                                <p class="text-muted small mb-0">Llibres amb retard</p>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- BOTÓ PER AFEGIR USUARI AL PANELL DE BIBLIOTECARI -->
                <div class="d-flex justify-content-end mb-3">
                    <a href="${pageContext.request.contextPath}/bibliotecari/nou-usuari" class="btn btn-success shadow-sm">
                        <i class="bi bi-person-plus-fill"></i> Registrar Nou Lector
                    </a>
                </div>

                <!-- ===== Pestanyes ===== -->
                <ul class="nav nav-tabs mb-4" id="tabsBibliotecari" role="tablist">
                    <li class="nav-item">
                        <button class="nav-link ${empty sancioError ? 'active' : ''}"
                                data-bs-toggle="tab" data-bs-target="#prestecs">
                            <img src="${pageContext.request.contextPath}/assets/icons/prestecs_actius.png"
                                 alt="Propostes" style="width:40px; height:40px;">Préstecs actius
                        </button>
                    </li>
                    <li class="nav-item">
                        <button class="nav-link" data-bs-toggle="tab" data-bs-target="#reserves">
                            <img src="${pageContext.request.contextPath}/assets/icons/reserves.png"
                                 alt="Propostes" style="width:40px; height:40px;">Reserves pendents
                        </button>
                    </li>
                    <li class="nav-item">
                        <button class="nav-link" data-bs-toggle="tab" data-bs-target="#devolucions">
                            <img src="${pageContext.request.contextPath}/assets/icons/devolucions.png"
                                 alt="Propostes" style="width:40px; height:40px;">Devolucions
                        </button>
                    </li>
                    <li class="nav-item">
                        <button class="nav-link ${not empty sancioError ? 'active' : ''}"
                                data-bs-toggle="tab" data-bs-target="#registrar-prestec"><strong>
                                <img src="${pageContext.request.contextPath}/assets/icons/registrar_prestec.png"
                                     alt="Propostes" style="width:40px; height:40px;">Registrar Préstec</strong>
                        </button>
                    </li>
                    <li class="nav-item">
                        <button class="nav-link" data-bs-toggle="tab" data-bs-target="#retards">
                            <img src="${pageContext.request.contextPath}/assets/icons/retards.png"
                                 alt="Propostes" style="width:40px; height:40px;">Llibres amb retard
                        </button>
                    </li>
                    <li class="nav-item">
                        <button class="nav-link" data-bs-toggle="tab" data-bs-target="#sancions">
                            <img src="${pageContext.request.contextPath}/assets/icons/sancio.png"
                                 alt="Propostes" style="width:40px; height:40px;">Sancions actives
                        </button>
                    </li>
                </ul>

                <!-- ===== Contingut pestanyes ===== -->
                <div class="tab-content">
                    <div class="tab-pane fade ${empty sancioError ? 'show active' : ''}" id="prestecs">
                        <h4 class="mb-3">Préstecs actius a la teva biblioteca</h4>
                        <div class="table-responsive">
                            <table class="table table-striped mt-4">
                                <thead>
                                    <tr>
                                        <th>ISBN</th>
                                        <th>Usuari</th>
                                        <th>Data Préstec</th>
                                        <th>Data Devolució</th>
                                        <th>Data Venciment</th>
                                        <th>Accions</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="p" items="${prestecsActius}">
                                        <tr>
                                            <td>${p.llibre.isbn}</td>
                                            <td>${p.usuari.nom} ${p.usuari.cognoms}</td>
                                            <td>${p.dataPrestecFormatted}</td>
                                            <td>
                                                <span class="badge bg-success text-dark">
                                                    ${p.dataDevolucioFormatted}
                                                </span>
                                            </td>
                                            <td>
                                                <span class="badge bg-danger text-dark">
                                                    ${p.dataVencimentCalculada}
                                                </span>
                                            </td>
                                            <td>
                                                <!-- Gestionar -->
                                                <form action="${pageContext.request.contextPath}/gestionarPrestec" method="GET" class="d-inline">
                                                    <input type="hidden" name="idPrestec" value="${p.idPrestec}">
                                                    <button type="submit" class="btn btn-sm btn-primary">Gestionar</button>
                                                </form>
                                            </td>
                                        </tr>
                                    </c:forEach>

                                    <c:if test="${empty prestecsActius}">
                                        <tr>
                                            <td colspan="6" class="text-center text-muted fst-italic">
                                                No hi ha préstecs actius.
                                            </td>
                                        </tr>
                                    </c:if>
                                </tbody>
                            </table>

                        </div>
                    </div>

                    <div class="tab-pane fade" id="reserves">
                        <h4 class="mb-3">Reserves pendents de recollida</h4>
                        <div class="table-responsive">
                            <table class="table table-striped mt-4">
                                <thead>
                                    <tr>
                                        <th>Llibre</th>
                                        <th>Usuari</th>
                                        <th>Data Reserva</th>
                                        <th>Accions</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:choose>
                                        <c:when test="${empty reservesPendents}">
                                            <tr>
                                                <td colspan="6" class="text-center text-muted fst-italic">No hi ha reserves pendents.</td>
                                            </tr>
                                        </c:when>

                                        <c:otherwise>
                                            <c:forEach var="r" items="${reservesPendents}">
                                                <tr>
                                                    <td>${r.llibre.titol}</td>
                                                    <td>${r.usuari.nom} ${r.usuari.cognoms}</td>
                                                    <td>${r.dataReservaFormatted}</td>
                                                    <td>
                                                        <a href="${pageContext.request.contextPath}/bibliotecari/reserves/gestionar?id=${r.idReserva}"
                                                           class="btn btn-sm btn-primary">
                                                            Crear prèstec
                                                        </a>
                                                        <a href="${pageContext.request.contextPath}/bibliotecari/reserves/eliminar?id=${r.idReserva}"
                                                           class="btn btn-sm btn-danger"
                                                           onclick="return confirm('Segur que vols eliminar aquesta reserva?');">
                                                            Eliminar
                                                        </a>

                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </c:otherwise>
                                    </c:choose>
                                </tbody>

                            </table>
                        </div>
                    </div>

                    <!-- Secció de les devolucions -->
                    <div class="tab-pane fade p-3" id="devolucions">
                        <h4 class="mb-3">Contingut per a la gestió de devolucions.</h4>
                        <div class="card">
                            <div class="card-body">
                                <p class="card-text small text-muted">Introdueix l'ISBN del llibre i l'email de l'usuari per registrar una devolució. En acabar el, prèstec es donarà per finalitzat.</p>
                                <form action="${pageContext.request.contextPath}/gestionarDevolucio" method="POST">
                                    <div class="row g-3">
                                        <div class="col-md-6">
                                            <label for="isbn" class="form-label">ISBN del Llibre</label>
                                            <input type="text" class="form-control" id="isbn" name="isbn" required>
                                        </div>
                                        <div class="col-md-6">
                                            <label for="emailUsuari" class="form-label">Email de l'Usuari</label>
                                            <input type="email" class="form-control" id="emailUsuari" name="emailUsuari" required>
                                        </div>
                                        <div class="col-12 text-end">
                                            <button type="submit" class="btn btn-primary">Registrar Devolució</button>
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </div>  
                        <hr class="my-4"/>

                        <h5 class="mt-4">Devolucions registrades</h5>

                        <div class="table-responsive mt-3">
                            <table class="table table-striped">
                                <thead>
                                    <tr>
                                        <th>Llibre</th>
                                        <th>Usuari</th>
                                        <th>Data Prèstec</th>
                                        <th>Data Devolució</th>
                                        <th>Bibliotecari</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:choose>
                                        <c:when test="${empty devolucions}">
                                            <tr><td colspan="6" class="text-center text-muted fst-italic">No hi ha devolucions registrades.</td></tr>
                                        </c:when>
                                        <c:otherwise>
                                            <c:forEach var="dev" items="${devolucions}">
                                                <tr>
                                                    <td>${dev.llibre.titol}</td>
                                                    <td>${dev.usuari.email}</td>
                                                    <td>${dev.dataPrestecFormatted}</td>
                                                    <td>${dev.dataDevolucioFormatted}</td>
                                                    <td>${dev.agentDevolucio.nom}</td>
                                                </tr>
                                            </c:forEach>
                                        </c:otherwise>
                                    </c:choose>
                                </tbody>
                            </table>
                        </div>

                    </div>

                    <div class="tab-pane fade ${not empty sancioError ? 'show active' : ''}" id="registrar-prestec">
                        <h4 class="mb-3">Registrar un nou préstec</h4>
                        <c:if test="${not empty sancioError}">
                            <div id="alertSancio"
                                 class="alert alert-danger alert-dismissible fade show"
                                 role="alert">
                                <i class="bi bi-exclamation-octagon-fill"></i>
                                ${sancioError}
                                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                            </div>
                        </c:if>
                        <div class="card">
                            <div class="card-body">
                                <p class="card-text small text-muted">Introdueix l'ISBN del llibre i l'email de l'usuari per registrar el préstec. Si existeix una reserva per a aquest llibre i usuari, es completarà automàticament.</p>
                                <form action="${pageContext.request.contextPath}/gestionarPrestec" method="POST">
                                    <div class="row g-3">
                                        <div class="col-md-6">
                                            <label for="isbn" class="form-label">ISBN del Llibre</label>
                                            <input type="text" class="form-control" id="isbn" name="isbn" required>
                                        </div>
                                        <div class="col-md-6">
                                            <label for="emailUsuari" class="form-label">Email de l'Usuari</label>
                                            <input type="email" class="form-control" id="emailUsuari" name="emailUsuari" required>
                                        </div>
                                        <div class="col-12 text-end">
                                            <button type="submit" class="btn btn-primary">Registrar Préstec</button>
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>

                    <div class="tab-pane fade p-3" id="retards">
                        <h4 class="mb-3">Llibres amb retard a la teva biblioteca</h4>

                        <div class="table-responsive">
                            <table class="table table-striped mt-3 align-middle">
                                <thead>
                                    <tr>
                                        <th>ISBN</th>
                                        <th>Usuari</th>
                                        <th>Data Préstec</th>
                                        <th>Data Venciment</th>
                                        <th>Accions</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="p" items="${prestecsRetard}">
                                        <tr>
                                            <td>${p.llibre.isbn}</td>
                                            <td>${p.usuari.nom} ${p.usuari.cognoms}</td>
                                            <td>${p.dataPrestecFormatted}</td>
                                            <td>
                                                <span class="badge bg-danger text-light">
                                                    ${p.dataVencimentCalculada}
                                                </span>
                                            </td>
                                            <td>
                                                <form action="${pageContext.request.contextPath}/gestionarPrestec"
                                                      method="GET" class="d-inline">
                                                    <input type="hidden" name="idPrestec" value="${p.idPrestec}">
                                                    <button type="submit" class="btn btn-sm btn-outline-danger">
                                                        Gestionar
                                                    </button>
                                                </form>
                                            </td>
                                        </tr>
                                    </c:forEach>

                                    <c:if test="${empty prestecsRetard}">
                                        <tr>
                                            <td colspan="5" class="text-center text-muted fst-italic">
                                                No hi ha llibres amb retard.
                                            </td>
                                        </tr>
                                    </c:if>
                                </tbody>
                            </table>
                        </div>
                    </div>

                    <!-- Sancions actives -->
                    <div class="tab-pane fade p-3" id="sancions">
                        <h4 class="mb-3">Sancions actives</h4>

                        <div class="table-responsive">
                            <table class="table table-striped mt-4">
                                <thead>
                                    <tr>
                                        <th>Usuari</th>
                                        <th>Data fi sanció</th>
                                        <th>Motiu</th>
                                    </tr>
                                </thead>

                                <tbody>
                                    <c:choose>
                                        <c:when test="${empty sancionsActives}">
                                            <tr>
                                                <td colspan="3" class="text-center text-muted fst-italic">
                                                    No hi ha sancions actives.
                                                </td>
                                            </tr>
                                        </c:when>

                                        <c:otherwise>
                                            <c:forEach var="u" items="${sancionsActives}">
                                                <tr>
                                                    <td>${u.nom} ${u.cognoms}</td>

                                                    <!-- Formato badge en rojo igual que ‘Data venciment’ -->
                                                    <td>
                                                        <span class="badge bg-danger text-light">
                                                            ${u.dataFiSancioFormatted}
                                                        </span>
                                                    </td>

                                                    <td>${u.motiuSancio}</td>
                                                </tr>
                                            </c:forEach>
                                        </c:otherwise>
                                    </c:choose>
                                </tbody>
                            </table>
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

        <script src="${pageContext.request.contextPath}/assets/js/alerts.js"></script>
    </body>
</html>
