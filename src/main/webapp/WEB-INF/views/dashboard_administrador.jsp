<%-- 
    Author     : Equip TotEsBook
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="cat.totesbook.repository.UsuariRepository" %>
<%@ page import="cat.totesbook.repository.AgentRepository" %>
<%@ page import="cat.totesbook.repository.impl.UsuariDAO" %>
<%@ page import="cat.totesbook.repository.impl.AgentDAO" %>
<%@ page import="java.util.List" %>
<%@ page import="cat.totesbook.domain.Usuari" %>
<%@ page import="cat.totesbook.domain.Agent" %>
<%@ page import="cat.totesbook.domain.Rol" %>


<!DOCTYPE html>
<html lang="ca">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Panell d'Administració - TotEsBook</title>
        <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/assets/favicon.ico" />

        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css">

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
                        <li class="nav-item"><a class="nav-link active" aria-current="page" href="${pageContext.request.contextPath}/mostrarUsuaris">Gestió Usuaris</a></li> 
                        <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/admin/estadistiques">Estadístiques</a></li> 
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
        <!-- ===== FI CAPÇALERA INCRUSTADA ===== -->

        <!-- ===== Panell d'Administració ===== -->
        <section class="py-5 flex-grow-1">
            <div class="container px-4 px-lg-5 mt-5">
                <h1 class="text-center text-tot-bold mb-5">Panell d'Administració</h1>
                <c:if test="${not empty feedbackMessage}">
                    <div class="alert alert-${messageType == 'success' ? 'success' : 'danger'} alert-dismissible fade show" role="alert">
                        <c:out value="${feedbackMessage}"/>
                        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                    </div>
                </c:if>

                <c:if test="${not empty errorCarrega}">
                    <div class="alert alert-danger" role="alert">
                        Error carregant dades: <c:out value="${errorCarrega}"/>
                    </div>
                </c:if>

                <!-- ===== Pestanyes ===== -->
                <ul class="nav nav-tabs mb-4" id="adminTabs" role="tablist">
                    <li class="nav-item" role="presentation">
                        <button class="nav-link active" id="biblioteques-tab" data-bs-toggle="tab" data-bs-target="#biblioteques" type="button" role="tab">
                            <img src="${pageContext.request.contextPath}/assets/icons/biblioteques.png"
                                 alt="Propostes" style="width:40px; height:40px;">
                            Biblioteques
                        </button>
                    </li>
                    <li class="nav-item" role="presentation">
                        <button class="nav-link" id="comptes-tab" data-bs-toggle="tab" data-bs-target="#comptes" type="button" role="tab">
                            <img src="${pageContext.request.contextPath}/assets/icons/usuaris.png"
                                 alt="Propostes" style="width:40px; height:40px;">
                            Comptes d'Usuari
                        </button>
                    </li>
                    <li class="nav-item" role="presentation">
                        <button class="nav-link" id="llibres-tab" data-bs-toggle="tab" data-bs-target="#llibres" type="button" role="tab">
                            <img src="${pageContext.request.contextPath}/assets/icons/llibres.png"
                                 alt="Propostes" style="width:40px; height:40px;">
                            Llibres
                        </button>
                    </li>
                    <li class="nav-item" role="presentation">
                        <button class="nav-link" id="propostes-tab" data-bs-toggle="tab" data-bs-target="#propostes" type="button" role="tab">
                            <img src="${pageContext.request.contextPath}/assets/icons/propostes.png"
                                 alt="Propostes" style="width:40px; height:40px;">
                            Propostes d'adquisició
                        </button>
                    </li>
                    <!-- SPRINT 3 (TEA 5): Nova pestanya Estadístiques -->
                    <li class="nav-item" role="presentation">
                        <button class="nav-link" id="stats-tab" data-bs-toggle="tab" data-bs-target="#estadistiques" type="button" role="tab">
                            <img src="${pageContext.request.contextPath}/assets/icons/estadistiques.png"
                                 alt="estadistiques" style="width:40px; height:40px;">
                            Estadístiques
                        </button>
                    </li>
                </ul>

                <div class="tab-content" id="adminTabsContent">

                    <!-- ===== PESTANYA 1: GESTIÓ DE BIBLIOTEQUES ===== -->
                    <div class="tab-pane fade show active" id="biblioteques" role="tabpanel">
                        <div class="card shadow-sm mb-5">
                            <div class="card-header bg-totlight d-flex justify-content-between align-items-center">
                                <h4 class="mb-0 text-tot-bold">
                                    <i class="bi bi-building me-2"></i> Gestió de Biblioteques
                                </h4>
                                <a href="${pageContext.request.contextPath}/gestio/biblioteques/afegir" class="btn btn-sm btn-tot">
                                    <i class="bi bi-plus-circle-fill me-1"></i> Afegir Biblioteca
                                </a>
                            </div>

                            <div class="card-body">
                                <p>Aquí pots veure totes les biblioteques del sistema, així com afegir, modificar o eliminar-ne.</p>

                                <div class="table-responsive">
                                    <table class="table table-striped table-hover caption-top small align-middle">
                                        <caption>Llista general de biblioteques del sistema</caption>
                                        <thead class="table-light">
                                            <tr>
                                                <th>ID</th>
                                                <th>Nom</th>
                                                <th>Adreça</th>
                                                <th>Bibliotecari</th>
                                                <th>Llibres</th>
                                                <th>Préstecs Actius</th>
                                                <th>Accions</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="b" items="${llistaBiblioteques}">
                                                <tr>
                                                    <td>${b.idBiblioteca}</td>
                                                    <td><c:out value="${b.nom}"/></td>
                                                    <td><c:out value="${b.adreca}"/></td>
                                                    <td>
                                                        <c:choose>
                                                            <c:when test="${not empty b.bibliotecari}">
                                                                <c:out value="${b.bibliotecari.nom} ${b.bibliotecari.cognoms}"/>
                                                            </c:when>
                                                            <c:otherwise><span class="text-muted fst-italic">No assignat</span></c:otherwise>
                                                        </c:choose>
                                                    </td>
                                                    <td>${b.numLlibres}</td>
                                                    <td>${b.numPrestecs}</td>
                                                    <td>
                                                        <div class="btn-accio-group">

                                                            <a href="${pageContext.request.contextPath}/gestio/biblioteques/${b.idBiblioteca}" 
                                                               class="btn btn-outline-primary btn-accio-panell">
                                                                <i class="bi bi-gear"></i>
                                                            </a>

                                                            <a href="${pageContext.request.contextPath}/gestio/biblioteques/${b.idBiblioteca}/editar" 
                                                               class="btn btn-outline-warning btn-accio-panell">
                                                                <i class="bi bi-pencil"></i>
                                                            </a>

                                                            <form action="${pageContext.request.contextPath}/gestio/biblioteques/${b.idBiblioteca}/eliminar"
                                                                  method="POST" onsubmit="return confirm('Segur que vols eliminar aquesta biblioteca?')">
                                                                <button type="submit" class="btn btn-outline-danger btn-accio-panell">
                                                                    <i class="bi bi-trash"></i>
                                                                </button>
                                                            </form>

                                                        </div>

                                                    </td>
                                                </tr>
                                            </c:forEach>

                                            <c:if test="${empty llistaBiblioteques}">
                                                <tr><td colspan="7" class="text-center text-muted">No hi ha biblioteques registrades.</td></tr>
                                            </c:if>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- ===== PESTANYA 2: GESTIÓ DE COMPTES ===== -->
                    <div class="tab-pane fade" id="comptes" role="tabpanel">
                        <div class="card shadow-sm mb-5">
                            <div class="card-header bg-totlight d-flex justify-content-between align-items-center">
                                <h4 class="mb-0 text-tot-bold">
                                    <i class="bi bi-people-fill me-2"></i> Gestió de Comptes
                                </h4>
                                <button class="btn btn-sm btn-tot" onclick="alert('Funcionalitat per crear nou usuari/agent no implementada')">
                                    <i class="bi bi-plus-circle-fill me-1"></i> Crear Compte
                                </button>
                            </div>

                            <div class="card-body">
                                <p>Aquí pots veure i modificar els rols dels usuaris i agents del sistema.</p>

                                <!-- Taula Agents -->
                                <h5 class="mt-4">Agents (Bibliotecaris i Administradors)</h5>
                                <div class="table-responsive">
                                    <table class="table table-striped table-hover caption-top small">
                                        <caption>Llista d'agents del sistema</caption>
                                        <thead class="table-light">
                                            <tr>
                                                <th>ID</th>
                                                <th>Nom</th>
                                                <th>Email</th>
                                                <th>Rol</th>
                                                <th>Canviar Rol</th>
                                                <th>Accions</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="agent" items="${llistaAgents}">
                                                <tr>
                                                    <td>${agent.idAgent}</td>
                                                    <td><c:out value="${agent.nom} ${agent.cognoms}"/></td>
                                                    <td><c:out value="${agent.email}"/></td>
                                                    <td>
                                                        <c:choose>
                                                            <c:when test="${agent.tipus == 'administrador'}">
                                                                <span class="badge bg-danger">Admin</span>
                                                            </c:when>
                                                            <c:otherwise><span class="badge bg-info text-dark">Bibliotecari</span></c:otherwise>
                                                        </c:choose>
                                                    </td>
                                                    <td>
                                                        <form action="${pageContext.request.contextPath}/canviRol" method="POST" class="d-flex align-items-center">
                                                            <input type="hidden" name="idCompte" value="${agent.idAgent}">
                                                            <input type="hidden" name="tipusCompte" value="AGENT">
                                                            <select name="nouRol" class="form-select form-select-sm me-2" style="width:auto;">
                                                                <option value="BIBLIOTECARI" ${agent.tipus == 'bibliotecari' ? 'selected' : ''}>Bibliotecari</option>
                                                                <option value="ADMIN" ${agent.tipus == 'administrador' ? 'selected' : ''}>Admin</option>
                                                            </select>
                                                            <button type="submit" class="btn btn-sm btn-tot">Canviar</button>
                                                        </form>
                                                    </td>
                                                    <td><button class="btn btn-sm btn-outline-danger" onclick="alert('Eliminació no implementada')"><i class="bi bi-trash"></i></button></td>
                                                </tr>
                                            </c:forEach>

                                            <c:if test="${empty llistaAgents}">
                                                <tr><td colspan="6" class="text-center text-muted">No hi ha agents registrats.</td></tr>
                                            </c:if>
                                        </tbody>
                                    </table>
                                </div>

                                <!-- Taula Usuaris -->
                                <h5 class="mt-5">Usuaris (Lectors)</h5>
                                <div class="table-responsive">
                                    <table class="table table-striped table-hover caption-top small">
                                        <caption>Llista d'usuaris lectors</caption>
                                        <thead class="table-light">
                                            <tr>
                                                <th>ID</th>
                                                <th>Nom</th>
                                                <th>Email</th>
                                                <th>Rol</th>
                                                <th>Ascendir</th>
                                                <th>Accions</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="usuari" items="${llistaUsuaris}">
                                                <tr>
                                                    <td>${usuari.id}</td>
                                                    <td><c:out value="${usuari.nom} ${usuari.cognoms}"/></td>
                                                    <td><c:out value="${usuari.email}"/></td>
                                                    <td><span class="badge bg-secondary">Usuari</span></td>
                                                    <td>
                                                        <form action="${pageContext.request.contextPath}/canviRol" method="POST" class="d-flex align-items-center">
                                                            <input type="hidden" name="idCompte" value="${usuari.id}">
                                                            <input type="hidden" name="tipusCompte" value="USUARI">
                                                            <select name="nouRol" class="form-select form-select-sm me-2" style="width:auto;">
                                                                <option value="USUARI" selected disabled>-- Ascendir a --</option>
                                                                <option value="BIBLIOTECARI">Bibliotecari</option>
                                                                <option value="ADMIN">Admin</option>
                                                            </select>
                                                            <button type="submit" class="btn btn-sm btn-tot" disabled title="Funcionalitat pendent">Canviar</button>
                                                        </form>
                                                    </td>
                                                    <td><div class="btn-accio-group">
                                                            <button class="btn btn-outline-danger btn-accio-panell"
                                                                    onclick="alert('Eliminació no implementada')">
                                                                <i class="bi bi-trash"></i>
                                                            </button>
                                                        </div></button></td>
                                                </tr>
                                            </c:forEach>

                                            <c:if test="${empty llistaUsuaris}">
                                                <tr><td colspan="6" class="text-center text-muted">No hi ha usuaris registrats.</td></tr>
                                            </c:if>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- ===== PESTANYA 3: GESTIÓ DE LLIBRES ===== -->
                    <div class="tab-pane fade" id="llibres" role="tabpanel">
                        <div class="card shadow-sm mb-5">
                            <div class="card-header bg-totlight d-flex justify-content-between align-items-center">
                                <h4 class="mb-0 text-tot-bold">
                                    <i class="bi bi-bookshelf me-2"></i>Gestió de Llibres
                                </h4>
                                <a href="${pageContext.request.contextPath}/gestio/llibres/afegir" class="btn btn-sm btn-tot">
                                    <i class="bi bi-plus-circle-fill me-1"></i> Afegir Llibre
                                </a>
                            </div>

                            <div class="card-body">
                                <p>Aquí pots veure tots els llibres registrats, així com afegir, modificar o eliminar-ne.</p>

                                <div class="table-responsive">
                                    <table class="table table-striped table-hover caption-top small align-middle">
                                        <caption>Llista general de libres registrats</caption>
                                        <thead class="table-light">
                                            <tr>
                                                <th>Títol</th>
                                                <th>Autor</th>
                                                <th>ISBN</th>
                                                <th>Idioma</th>
                                                <th>Biblioteca</th>
                                                <th>Exemplars</th>
                                                <th>Accions</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <!-- Llistat de llibres -->
                                            <c:forEach var="llibre" items="${llibres}">

                                                <tr>
                                                    <td>${llibre.titol}</td>
                                                    <td><c:out value="${llibre.autor}"/></td>
                                                    <td><c:out value="${llibre.isbn}"/></td>
                                                    <td><c:out value="${llibre.idioma}"/></td>
                                                    <td>
                                                        <c:choose>
                                                            <c:when test="${not empty bibliosPerIsbn[llibre.isbn]}">
                                                                <c:forEach var="rel" items="${bibliosPerIsbn[llibre.isbn]}" varStatus="st">
                                                                    ${rel.biblioteca.nom}
                                                                    <c:if test="${!st.last}">, </c:if>
                                                                </c:forEach>
                                                            </c:when>
                                                            <c:otherwise>Pendent d'assignar</c:otherwise>
                                                        </c:choose>
                                                    </td>
                                                    <td>
                                                        <c:choose>
                                                            <c:when test="${not empty bibliosPerIsbn[llibre.isbn]}">
                                                                <c:forEach var="rel" items="${bibliosPerIsbn[llibre.isbn]}" varStatus="st">
                                                                    ${rel.exemplars}
                                                                    <c:if test="${!st.last}">, </c:if>
                                                                </c:forEach>
                                                            </c:when>
                                                            <c:otherwise>0</c:otherwise>
                                                        </c:choose>
                                                    </td>
                                                    <td>
                                                        <div class="btn-accio-group">
                                                            <a href="${pageContext.request.contextPath}/gestio/llibres/${llibre.isbn}/editar" class="btn btn-outline-warning btn-accio-panell">
                                                                <i class="bi bi-pencil"></i>
                                                            </a>

                                                            <form action="${pageContext.request.contextPath}/gestio/llibres/${llibre.isbn}/eliminar" method="POST" onsubmit="return confirm('Segur que vols eliminar aquest llibre?')">
                                                                <button type="submit" class="btn btn-outline-danger btn-accio-panell">
                                                                    <i class="bi bi-trash"></i>
                                                                </button>
                                                            </form>
                                                        </div>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Secció de les propostes d'adquisició -->       
                    <div class="tab-pane fade" id="propostes" role="tabpanel">
                        <div class="card shadow-sm mb-5">

                            <div class="card-header bg-totlight d-flex justify-content-between align-items-center">
                                <h4 class="mb-0 text-tot-bold">
                                    <i class="bi bi-journal-plus me-2"></i> Propostes d'Adquisició
                                </h4>
                            </div>

                            <div class="card-body">
                                <p>Aquí pots revisar totes les propostes d’adquisició enviades pels usuaris.</p>

                                <div class="table-responsive">
                                    <table class="table table-striped table-hover caption-top small align-middle">
                                        <caption>Llista de propostes enviades pels usuaris</caption>

                                        <thead class="table-light">
                                            <tr>
                                                <th>Títol proposat</th>
                                                <th>Autor</th>
                                                <th>ISBN</th>
                                                <th>Usuari</th>
                                                <th>Data Proposta</th>
                                                <th>Estat</th>
                                                <th>Accions</th>
                                            </tr>
                                        </thead>

                                        <tbody>
                                            <c:choose>
                                                <c:when test="${empty propostes}">
                                                    <tr>
                                                        <td colspan="7" class="text-center text-muted fst-italic">
                                                            No hi ha propostes d'adquisició.
                                                        </td>
                                                    </tr>
                                                </c:when>

                                                <c:otherwise>
                                                    <c:forEach var="p" items="${propostes}">
                                                        <tr>
                                                            <td><c:out value="${p.titol}"/></td>
                                                            <td><c:out value="${p.autor}"/></td>
                                                            <td><c:out value="${p.isbn}"/></td>

                                                            <td>Usuari #${p.idUsuari}</td>

                                                            <td>${p.dataPropostaFormatted}</td>

                                                            <td>
                                                                <span class="badge 
                                                                      ${p.estat == 'pendent' ? 'bg-warning' :
                                                                        (p.estat == 'acceptada' ? 'bg-success' :
                                                                        (p.estat == 'rebutjada' ? 'bg-danger' : 'bg-primary'))}">
                                                                          ${p.estat}
                                                                      </span>
                                                                </td>

                                                                <td>
                                                                    <a href="${pageContext.request.contextPath}/propostes/detall?id=${p.idProposta}"
                                                                       class="btn btn-sm btn-outline-primary">
                                                                        <i class="bi bi-gear"></i> Gestionar
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

                            </div>
                        </div>

                        <!-- ===== PESTANYA 4: ESTADÍSTIQUES ===== -->
                        <div class="tab-pane fade" id="estadistiques" role="tabpanel">
                            <div class="card shadow-sm mb-5">

                                <!-- Header amb fons blau clar -->
                                <div class="card-header bg-totlight d-flex justify-content-between align-items-center flex-wrap gap-2">
                                    <div>
                                        <h4 class="mb-0 text-tot-bold">
                                            <i class="bi bi-bar-chart-line-fill me-2"></i> Resum d'Estadístiques
                                        </h4>
                                        <small class="text-muted">
                                            Vista ràpida del Centre d'Estadístiques de la biblioteca.
                                        </small>
                                    </div>
                                </div>

                                <!-- Cos blanc -->
                                <div class="card-body bg-white">

                                    <!-- Tres caixetes gris clar -->
                                    <div class="row g-4 mb-4 text-center">

                                        <div class="col-md-4">
                                            <div class="border rounded-4 p-4 h-100 bg-light">
                                                <i class="bi bi-book-half fs-1 text-muted mb-2 d-block"></i>
                                                <h5 class="text-tot-bold mb-2">Llibres més prestats</h5>
                                                <p class="small text-muted mb-0">
                                                    Consulta el rànquing dels títols amb més préstecs,
                                                    filtrant per <strong>autor</strong> i <strong>categoria/gènere</strong>.
                                                </p>
                                            </div>
                                        </div>

                                        <div class="col-md-4">
                                            <div class="border rounded-4 p-4 h-100 bg-light">
                                                <i class="bi bi-people-fill fs-1 text-muted mb-2 d-block"></i>
                                                <h5 class="text-tot-bold mb-2">Lectors més actius</h5>
                                                <p class="small text-muted mb-0">
                                                    Visualitza el Top 20 d'usuaris amb més préstecs i revisa ràpidament
                                                    el seu <strong>nom</strong>, <strong>correu</strong> i activitat.
                                                </p>
                                            </div>
                                        </div>

                                        <div class="col-md-4">
                                            <div class="border rounded-4 p-4 h-100 bg-light">
                                                <i class="bi bi-stars fs-1 text-muted mb-2 d-block"></i>
                                                <h5 class="text-tot-bold mb-2">Autors destacats</h5>
                                                <p class="small text-muted mb-0">
                                                    Accedeix al rànquing dels <strong>autors més prestats</strong>
                                                    (Top 10) segons el nombre total de préstecs registrats.
                                                </p>
                                            </div>
                                        </div>

                                    </div>

                                    <!-- Botons d'accés -->
                                    <div class="d-flex justify-content-center gap-3">
                                        <a href="${pageContext.request.contextPath}/admin/estadistiques"
                                           class="btn btn-lg btn-tot">
                                            <i class="bi bi-bar-chart-line-fill me-2"></i>
                                            Veure Estadístiques Detallades
                                        </a>
                                        <a href="${pageContext.request.contextPath}/admin/estadistiques/autors"
                                           class="btn btn-lg btn-outline-primary">
                                            <i class="bi bi-stars me-2"></i>
                                            Autors Destacats
                                        </a>
                                    </div>

                                </div>
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
