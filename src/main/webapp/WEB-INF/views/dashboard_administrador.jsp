<%-- 
    Document   : dashboard_administrador
    Created on : 23 oct 2025, 14:34:02
    Author     : edinsonioc
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%-- Imports necessaris per carregar les dades directament (millor fer-ho en un Servlet) --%>
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
                        <li class="nav-item"><a class="nav-link" href="#">Biblioteques</a></li>
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle" id="navbarDropdown" href="#" role="button"
                               data-bs-toggle="dropdown" aria-expanded="false">Categories</a>
                            <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                                <li><a class="dropdown-item" href="${pageContext.request.contextPath}/mostrarLlibres">Totes les categories</a></li>
                                <li><hr class="dropdown-divider" /></li>
                                <li><a class="dropdown-item" href="#">Autoajuda</a></li>
                                <li><a class="dropdown-item" href="#">Ficció</a></li>
                                <li><a class="dropdown-item" href="#">Juvenil</a></li>
                                <li><a class="dropdown-item" href="#">Novel·la</a></li>
                                <li><a class="dropdown-item" href="#">True crime</a></li>
                            </ul>
                        </li>
                        <%-- Només mostrem gestió d'usuaris si l'usuari és ADMIN --%>
                        <c:if test="${not empty sessionScope.sessioUsuari && sessionScope.sessioUsuari.rol == 'ADMIN'}">
                            <li class="nav-item"><a class="nav-link active" aria-current="page" href="${pageContext.request.contextPath}/mostrarUsuaris">Gestió Usuaris</a></li> <%-- Enllaç al Servlet --%>
                            <li class="nav-item"><a class="nav-link" href="#">Estadístiques</a></li> <%-- TODO: Implementar --%>
                            </c:if>
                            <c:if test="${not empty sessionScope.sessioUsuari}">
                            <li class="nav-item"><a class="nav-link" href="#">Propostes</a></li> <%-- TODO: Implementar --%>
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
                                <%-- Això no hauria de passar si el filtre funciona bé, ja que aquesta pàgina és protegida --%>
                                <a href="${pageContext.request.contextPath}/login" class="btn btn-tot btn-sm my-2 my-lg-0">
                                    Inicia sessió <i class="bi bi-person-circle"></i>
                                </a>
                            </c:when>
                            <c:otherwise>
                                <div class="dropdown">
                                    <button class="btn btn-tot btn-sm dropdown-toggle active" type="button" id="dropdownUsuari" data-bs-toggle="dropdown" aria-expanded="false">
                                        <i class="bi bi-person-fill"></i> <c:out value="${sessionScope.sessioUsuari.nomComplet}"/> (Admin)
                                    </button>
                                    <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="dropdownUsuari">
                                        <li><a class="dropdown-item active" href="${pageContext.request.contextPath}/dashboard_administrador">Panell Admin</a></li>
                                        <li><a class="dropdown-item" href="${pageContext.request.contextPath}/dashboard_bibliotecari">Panell Bibliotecari</a></li> <%-- Accés ràpid si també és biblio --%>
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

                <%-- Missatges de feedback o errors --%>
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
                            🏛️ Biblioteques
                        </button>
                    </li>
                    <li class="nav-item" role="presentation">
                        <button class="nav-link" id="comptes-tab" data-bs-toggle="tab" data-bs-target="#comptes" type="button" role="tab">
                            👥 Comptes d'Usuari
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
                                                        <div class="btn-group btn-group-sm" role="group">
                                                            <a href="${pageContext.request.contextPath}/gestio/biblioteques/${b.idBiblioteca}" 
                                                               class="btn btn-outline-primary"><i class="bi bi-gear"></i> Gestionar</a>
                                                            <a href="${pageContext.request.contextPath}/gestio/biblioteques/${b.idBiblioteca}/editar" 
                                                               class="btn btn-outline-warning"><i class="bi bi-pencil"></i></a>
                                                            <form action="${pageContext.request.contextPath}/gestio/biblioteques/${b.idBiblioteca}/eliminar" 
                                                                  method="POST" style="display:inline;"
                                                                  onsubmit="return confirm('Segur que vols eliminar aquesta biblioteca?')">
                                                                <button type="submit" class="btn btn-outline-danger"><i class="bi bi-trash"></i></button>
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
                                                    <td><button class="btn btn-sm btn-outline-danger" onclick="alert('Eliminació no implementada')"><i class="bi bi-trash"></i></button></td>
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
                </div> <!-- fi tab-content -->
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

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
