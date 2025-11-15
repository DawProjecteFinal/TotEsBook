<%-- 
    Document   : paginaInici
    Created on : 28 sept 2025, 17:43:00
    Author     : equip totEsBook
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ca">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>TotEsBook</title>
        <link rel="icon" type="image/x-icon" href="assets/favicon.ico" />
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/styles.css">
    </head>

    <body>

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
                            <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                                <li><a class="dropdown-item" href="#" data-field="author">Autor</a></li>
                                <li><hr class="dropdown-divider" /></li>
                                <li><a class="dropdown-item" href="#" data-field="idioma">Idioma</a></li>
                                <li><hr class="dropdown-divider" /></li>
                                <li><a class="dropdown-item" href="#" data-field="isbn">ISBN</a></li>
                            </ul>
                        </li>
                        <form id="advancedSearch" class="d-flex me-3 my-2 my-lg-0" method="get" action="<c:url value='/buscar'/>">
                            <input type="hidden" name="field" id="field" value="">
                            <div id="searchGroup" class="input-group d-none">
                                <input id="searchInput" class="form-control form-control-sm me-2" name="q" type="search" placeholder="" aria-label="Advanced search" autocomplete="off" required
                                       oninvalid="this.setCustomValidity('Aquest camp és obligatori')"
                                       oninput="this.setCustomValidity('')" />
                                <button class="btn btn-tot btn-sm" type="submit">
                                    <i class="bi bi-search"></i>
                                </button>
                            </div>
                        </form>

                        <c:if test="${not empty sessionScope.sessioUsuari}">
                            <li class="nav-item"><a class="nav-link" href="#">Propostes</a></li>
                                <%-- Enllaç a Gestió d'Usuaris (només per a Admin) --%>
                                <c:if test="${sessionScope.sessioUsuari.rol == 'ADMIN'}">
                                <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/mostrarUsuaris">Gestió Usuaris</a></li>
                                </c:if>
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
                                    <button class="btn btn-tot btn-sm dropdown-toggle" type="button" id="dropdownUsuari" data-bs-toggle="dropdown" aria-expanded="false">
                                        <i class="bi bi-person-fill"></i> <c:out value="${sessionScope.sessioUsuari.nomComplet}"/> 
                                    </button>
                                    <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="dropdownUsuari">
                                        <c:choose>
                                            <c:when test="${sessionScope.sessioUsuari.rol == 'USUARI'}">
                                                <li><a class="dropdown-item" href="${pageContext.request.contextPath}/dashboard_usuari">El Meu Panell</a></li>
                                                </c:when>
                                                <c:when test="${sessionScope.sessioUsuari.rol == 'BIBLIOTECARI'}">
                                                <li><a class="dropdown-item" href="${pageContext.request.contextPath}/dashboard_bibliotecari">Panell Bibliotecari</a></li>
                                                </c:when>
                                                <c:when test="${sessionScope.sessioUsuari.rol == 'ADMIN'}">
                                                <li><a class="dropdown-item" href="${pageContext.request.contextPath}/dashboard_administrador">Panell Admin</a></li>
                                                </c:when>
                                            </c:choose>
                                        <li><hr class="dropdown-divider"></li>
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
        <section class="py-5 flex-grow-1">
            <div class="container px-4 px-lg-5 mt-5">
                <div class="row gx-4 gx-lg-5 justify-content-center mb-5">
                    <c:choose>
                        <c:when test="${not empty sessionScope.sessioUsuari}">
                            <div class="col-lg-6 text-center">
                                <h2 class="text-tot-bold">Benvingut/da de nou, <c:out value="${sessionScope.sessioUsuari.nomComplet}"/>!</h2>
                                <p class="text-tot-light">Explora el nostre catàleg o gestiona els teus préstecs.</p>
                                <a href="${pageContext.request.contextPath}/cataleg" class="btn btn-tot">Veure Catàleg</a>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="col-lg-6 text-center">
                                <h2 class="text-tot-bold">Descobreix un món de llibres</h2>
                                <p class="text-tot-light">Cerca al nostre catàleg o inicia sessió per accedir als teus préstecs.</p>
                                <a href="${pageContext.request.contextPath}/login" class="btn btn-tot me-2">Inicia Sessió</a>
                                <a href="${pageContext.request.contextPath}/registre" class="btn btn-outline-secondary">Registra't</a>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>

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
                                        <%-- CORRECCIÓ: L'enllaç ha d'apuntar al controlador /llibre --%>
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
        </section>
        <!-- ===== FI Secció Principal de Contingut ===== -->

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
        <!-- ===== FI Peu de pàgina ===== -->

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets/js/cerca-avancada.js"></script>
    </body>
</html>