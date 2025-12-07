<%-- 
    Document   : admin_estadistiques
    Created on : 30 nov 2025, 19:28:25
    Author     : edinsonioc
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="ca">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Estadístiques - Admin - TotEsBook</title>
        <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/assets/favicon.ico" />

        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/styles.css">
    </head>
    <body class="d-flex flex-column min-vh-100">

        <!-- ===== INICI CAPÇALERA (NAVBAR) ===== -->
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
                        <!-- Links d'Admin -->
                        <c:if test="${not empty sessionScope.sessioUsuari && sessionScope.sessioUsuari.rol == 'ADMIN'}">
                            <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/mostrarUsuaris">Gestió Usuaris</a></li> 
                            </c:if>
                        <li class="nav-item"><a class="nav-link active fw-bold" href="${pageContext.request.contextPath}/admin/estadistiques">Estadístiques</a></li>
                    </ul>


                    <div class="d-flex align-items-center ms-lg-auto">
                        <form class="d-flex me-3 my-2 my-lg-0" role="search" method="GET" action="${pageContext.request.contextPath}/mostrarLlibres">
                            <input class="form-control form-control-sm me-2" type="search" name="q" placeholder="Cerca per titol" aria-label="Search" required />
                            <button class="btn btn-tot btn-sm" type="submit"><i class="bi bi-search"></i></button>
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
            <!-- ===== FI CAPÇALERA ===== -->

            <!-- ===== CONTINGUT PRINCIPAL ===== -->
            <section class="py-5 flex-grow-1">
                <div class="container px-4 px-lg-5">

                    <div class="mt-4 mb-4">
                        <c:choose>
                            <c:when test="${sessionScope.sessioUsuari.rol == 'ADMIN'}">
                                <a href="${pageContext.request.contextPath}/dashboard_administrador" class="btn btn-outline-secondary">
                                    <i class="bi bi-arrow-left"></i> Tornar al Panell d'Administració
                                </a>
                            </c:when>

                            <c:when test="${sessionScope.sessioUsuari.rol == 'BIBLIOTECARI'}">
                                <a href="${pageContext.request.contextPath}/dashboard_bibliotecari" class="btn btn-outline-secondary">
                                    <i class="bi bi-arrow-left"></i> Tornar al Panell de Bibliotecari
                                </a>
                            </c:when>
                        </c:choose>
                    </div>

                    <!-- Encapçalament i Botons -->
                    <div class="d-flex justify-content-between align-items-center mb-4 flex-wrap gap-2">
                        <h2 class="text-tot-bold mb-0"><i class="bi bi-bar-chart-line-fill text-primary"></i> Centre d'Estadístiques</h2>
                        <div>
                            <a href="${pageContext.request.contextPath}/admin/estadistiques/excel?autor=${param.autor}&categoria=${param.categoria}" class="btn btn-success shadow-sm">
                                <i class="bi bi-file-earmark-excel me-1"></i> Exportar Excel
                            </a>
                            <a href="${pageContext.request.contextPath}/admin/estadistiques/pdf?autor=${param.autor}&categoria=${param.categoria}" class="btn btn-danger shadow-sm ms-2">
                                <i class="bi bi-file-earmark-pdf me-1"></i> Exportar PDF
                            </a>
                            <a href="${pageContext.request.contextPath}/bibliotecari/autors-populars" 
                               class="btn btn-outline-primary shadow-sm ms-2">
                                <i class="bi bi-stars me-1"></i> Autors destacats
                            </a>
                        </div>
                    </div>

                    <!-- Filtres -->
                    <div class="card mb-4 bg-light border-0 shadow-sm">
                        <div class="card-body">
                            <h5 class="card-title mb-3 text-muted"><i class="bi bi-filter"></i> Filtres per Llibres</h5>
                            <form action="" method="GET" class="row g-3 align-items-end">
                                <div class="col-md-4">
                                    <label class="form-label fw-bold small">Autor</label>
                                    <div class="input-group">
                                        <span class="input-group-text bg-white"><i class="bi bi-person"></i></span>
                                        <input type="text" name="autor" class="form-control" value="${filtreAutor}" placeholder="Nom de l'autor...">
                                    </div>
                                </div>
                                <div class="col-md-4">
                                    <label class="form-label fw-bold small">Categoria / Gènere</label>
                                    <div class="input-group">
                                        <span class="input-group-text bg-white"><i class="bi bi-tag"></i></span>
                                        <input type="text" name="categoria" class="form-control" value="${filtreCategoria}" placeholder="Ficció, Història...">
                                    </div>
                                </div>
                                <div class="col-md-4">
                                    <button type="submit" class="btn btn-primary w-100 shadow-sm"><i class="bi bi-search me-1"></i> Aplicar Filtres</button>
                                </div>
                            </form>
                        </div>
                    </div>

                    <div class="row">
                        <!-- TAULA 1: LLIBRES -->
                        <div class="col-lg-7 mb-4">
                            <div class="card shadow-sm h-100 border-0">
                                <div class="card-header bg-dark text-white d-flex justify-content-between align-items-center">
                                    <h5 class="mb-0"><i class="bi bi-book me-2"></i> Llibres Més Prestats</h5>
                                    <span class="badge bg-secondary">${stats.size()} resultats</span>
                                </div>
                                <div class="table-responsive">
                                    <table class="table table-striped table-hover mb-0 align-middle">
                                        <thead class="table-secondary">
                                            <tr>
                                                <th>Títol</th>
                                                <th>Autor</th>
                                                <th>Gènere</th>
                                                <th class="text-center">Total</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="item" items="${stats}">
                                                <tr>
                                                    <td class="fw-medium">${item.llibre.titol}</td>
                                                    <td class="text-muted small">${item.llibre.autor}</td>
                                                    <td><span class="badge bg-light text-dark border">${item.llibre.categoria}</span></td>
                                                    <td class="text-center">
                                                        <span class="badge bg-primary rounded-pill fs-6">${item.totalPrestecs}</span>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                            <c:if test="${empty stats}">
                                                <tr><td colspan="4" class="text-center py-5 text-muted fst-italic">No s'han trobat llibres amb aquests filtres.</td></tr>
                                            </c:if>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>

                        <!-- TAULA 2: USUARIS -->
                        <div class="col-lg-5 mb-4">
                            <div class="card shadow-sm h-100 border-0">
                                <div class="card-header bg-info text-white d-flex justify-content-between align-items-center">
                                    <h5 class="mb-0 text-dark"><i class="bi bi-people-fill me-2"></i> Lectors Més Actius</h5>
                                    <span class="badge bg-light text-dark">Top 20</span>
                                </div>
                                <div class="table-responsive">
                                    <table class="table table-hover mb-0 align-middle">
                                        <thead class="table-light">
                                            <tr>
                                                <th>Lector</th>
                                                <th class="text-center">Préstecs</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="usuariStat" items="${statsUsuaris}" varStatus="status">
                                                <tr>
                                                    <td>
                                                        <div class="d-flex align-items-center">
                                                            <div class="rounded-circle bg-secondary text-white d-flex justify-content-center align-items-center me-2" style="width: 30px; height: 30px; font-size: 0.8em;">
                                                                ${status.count}
                                                            </div>
                                                            <div>
                                                                <div class="fw-bold text-dark">${usuariStat.usuari.nom} ${usuariStat.usuari.cognoms}</div>
                                                                <small class="text-muted" style="font-size: 0.85em;">${usuariStat.usuari.email}</small>
                                                            </div>
                                                        </div>
                                                    </td>
                                                    <td class="text-center">
                                                        <span class="badge bg-success rounded-pill fs-6">${usuariStat.totalPrestecs}</span>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                            <c:if test="${empty statsUsuaris}">
                                                <tr><td colspan="2" class="text-center py-5 text-muted fst-italic">Encara no hi ha activitat d'usuaris suficient.</td></tr>
                                            </c:if>
                                        </tbody>
                                    </table>
                                </div>
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