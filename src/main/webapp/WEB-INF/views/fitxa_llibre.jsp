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
        <title>Fitxa: <c:out value="${llibre.titol}" default="Llibre no trobat"/> - TotEsBook</title>
        <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/assets/favicon.ico" />
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/styles.css">
        <style>
            .btn-nowrap {
                white-space: nowrap;
            }
        </style>
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
                            <c:if test="${sessionScope.sessioUsuari.rol == 'ADMIN'}">
                                <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/mostrarUsuaris">Gestió Usuaris</a></li>
                                </c:if>
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

        <!-- Secció Principal -->
        <section class="py-5 flex-grow-1">
            <div class="container px-4 px-lg-5 mt-5">

                <c:choose>
                    <c:when test="${not empty llibre}">

                        <div class="mb-4">
                            <c:choose>
                                <c:when test="${mode == 'reserva'}">
                                    <a href="${pageContext.request.contextPath}/mostrarLlibres" 
                                       class="btn btn-sm btn-outline-secondary">
                                        <i class="bi bi-arrow-left"></i> Tornar al catàleg
                                    </a>
                                </c:when>

                                <c:when test="${mode == 'proposta'}">
                                    <a href="${pageContext.request.contextPath}/propostes/buscar_proposta?titol=${titol}&autor=${autor}&isbn=${isbn}"
                                       class="btn btn-sm btn-outline-secondary">
                                        <i class="bi bi-arrow-left"></i> Tornar als resultats
                                    </a>
                                </c:when>

                                <c:when test="${mode == 'api'}">
                                    <a href="${pageContext.request.contextPath}/llibres/cercar_api?titol=${titol}&autor=${autor}"
                                       class="btn btn-sm btn-outline-secondary">
                                        <i class="bi bi-arrow-left"></i> Tornar als resultats
                                    </a>

                                </c:when>

                                <c:otherwise>
                                    <a href="${pageContext.request.contextPath}/" 
                                       class="btn btn-sm btn-outline-secondary">
                                        <i class="bi bi-arrow-left"></i> Tornar
                                    </a>
                                </c:otherwise>

                            </c:choose>
                        </div>

                        <div class="row gx-4 gx-lg-5 align-items-start">
                            <div class="col-md-5 text-center mb-4 mb-md-0">

                                <c:choose>
                                    <c:when test="${not empty llibre.imatgeUrl}">
                                        <img class="img-fluid rounded shadow-sm"
                                             src="<c:url value='${llibre.imatgeUrl}'/>"
                                             alt="Portada de ${llibre.titol}"
                                             style="max-width: 100%; height: auto; max-height: 500px; object-fit: contain;">
                                    </c:when>

                                    <c:otherwise>
                                        <div class="border rounded bg-light d-flex align-items-center justify-content-center" style="min-height: 400px;">
                                            <span class="text-muted fs-3">Imatge no disponible</span>
                                        </div>
                                    </c:otherwise>
                                </c:choose>

                            </div>

                            <div class="col-md-7">

                                <div class="small mb-1 text-muted">
                                    ISBN: <c:out value="${llibre.isbn}"/>
                                </div>

                                <h1 class="display-5 fw-bolder text-tot-bold">
                                    <c:out value="${llibre.titol}"/>
                                </h1>

                                <div class="fs-5 mb-3">
                                    <span class="text-tot-light">per <c:out value="${llibre.autor}"/></span>
                                </div>

                                <h5 class="mt-4">Sinopsi</h5>

                                <c:choose>
                                    <c:when test="${not empty llibre.sinopsis}">
                                        <p class="lead"><c:out value="${llibre.sinopsis}" escapeXml="false"/></p>
                                    </c:when>
                                    <c:otherwise>
                                        <p class="lead text-muted"><em>No hi ha sinopsi disponible.</em></p>
                                    </c:otherwise>
                                </c:choose>

                                <hr>

                                <p><strong>Editorial:</strong> <c:out value="${llibre.editorial}" default="No especificada"/></p>
                                <p><strong>Idioma:</strong> <c:out value="${llibre.idioma}" default="No especificat"/></p>

                                <c:if test="${mode != 'api'}">
                                    <p><strong>Es troba a:</strong>
                                        <c:choose>
                                            <c:when test="${not empty ubicacions}">
                                                <c:forEach var="rel" items="${ubicacions}" varStatus="st">
                                                    <c:out value="${rel.biblioteca.nom}"/>
                                                    <c:if test="${!st.last}"> · </c:if>
                                                </c:forEach>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="text-muted">No disponible a cap biblioteca</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </p>
                                </c:if>


                                <c:if test="${mode == 'reserva'}">
                                    <div class="d-flex align-items-center mb-3">
                                        <strong class="me-3">Disponibilitat:</strong>

                                        <c:choose>
                                            <c:when test="${llibre.disponibles > 0}">
                                                <span class="badge bg-success fs-6">
                                                    Disponible (<c:out value="${llibre.disponibles}"/> exemplars)
                                                </span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="badge bg-danger fs-6">No disponible</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                </c:if>

                                <div class="d-flex mt-4">

                                    <c:if test="${mode == 'reserva'}">

                                        <c:if test="${not empty sessionScope.sessioUsuari}">

                                            <c:if test="${llibre.disponibles > 0}">
                                                <form action="${pageContext.request.contextPath}/reservar" method="POST">
                                                    <input type="hidden" name="isbn" value="${llibre.isbn}">
                                                    <button type="submit" class="btn btn-accent-custom btn-lg">
                                                        <i class="bi bi-bookmark-plus-fill me-1"></i>
                                                        Reservar
                                                    </button>
                                                </form>
                                            </c:if>

                                        </c:if>

                                        <c:if test="${empty sessionScope.sessioUsuari}">
                                            <p class="text-muted"><em><a href="${pageContext.request.contextPath}/login">Inicia sessió</a> per reservar.</em></p>
                                                    </c:if>
                                                </c:if>


                                    <c:if test="${mode == 'proposta'}">
                                        <a href="${pageContext.request.contextPath}/propostes/confirmar?isbn=${llibre.isbn}&titol=${titol}&autor=${autor}&isbn_cerca=${isbn}"
                                           class="btn btn-warning btn-lg w-100">
                                            <i class="bi bi-lightbulb"></i> Fer proposta d’adquisició
                                        </a>
                                    </c:if>
                                    <c:if test="${mode == 'api'}">
                                        <!-- En el cas de fer la consulta a la Api no tenim cap botó -->
                                    </c:if>

                                </div>

                            </div>
                        </div>

                    </c:when>

                    <c:otherwise>
                        <div class="alert alert-warning text-center" role="alert">
                            <h4 class="alert-heading">Llibre no trobat</h4>
                            <p>No s'ha pogut trobar la informació del llibre sol·licitat.</p>
                            <hr>
                            <a href="${pageContext.request.contextPath}/mostrarLlibres" class="btn btn-secondary">Tornar al catàleg</a>
                        </div>
                    </c:otherwise>

                </c:choose>

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


        <c:if test="${not empty error}">
            <div class="modal fade" id="errorReservaModal" tabindex="-1" aria-hidden="true">
                <div class="modal-dialog modal-dialog-centered">
                    <div class="modal-content">

                        <div class="modal-header bg-danger text-white">
                            <h5 class="modal-title">
                                <i class="bi bi-exclamation-triangle-fill me-2"></i>
                                No s'ha pogut fer la reserva
                            </h5>
                            <button type="button" class="btn-close btn-close-white"
                                    data-bs-dismiss="modal" aria-label="Tancar"></button>
                        </div>

                        <div class="modal-body">
                            ${error}
                        </div>

                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">
                                Tancar
                            </button>
                        </div>

                    </div>
                </div>
            </div>
        </c:if>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets/js/cerca-avancada.js"></script>
        <script src="${pageContext.request.contextPath}/assets/js/fitxa-llibre.js"></script>
    </body>
</html>
