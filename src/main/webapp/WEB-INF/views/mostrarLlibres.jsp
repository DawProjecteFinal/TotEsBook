<%-- 
    Document   : mostrarLlibres.jsp
    Created on : 28 sept 2025, 7:01:42
    Author     : equip TotEsBook
--%>

<<<<<<< HEAD
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ca">
    <head>
        <meta charset="UTF-8">
        <title>Llibres</title>

        <link rel="icon" type="image/x-icon" href="assets/favicon.ico" />
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">

    </head>
    <body  class="bg-light">

        <!-- Encapçalat -->
        <header class="bg-tot py-5">
            <div class="container px-4 px-lg-5 my-5">
                <div class="text-center text-white">
                    <h1 class="display-4 fw-bolder">TotEsBook</h1>
                    <p class="lead fw-normal text-white-50 mb-0">La biblioteca de tothom</p>
                    <!-- <img src="${pageContext.request.contextPath}/images/logo-gran.jpeg" alt="Logo">-->
                </div>
            </div>
        </header>


        <!-- Menu -->
        <nav class="navbar navbar-expand-lg navbar-light bg-totlight">
            <div class="container px-4 px-lg-5">

                <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
                        data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false"
                        aria-label="Menú">
                    <span class="navbar-toggler-icon"></span>
                </button>


                <div class="collapse navbar-collapse" id="navbarSupportedContent">

                    <ul class="navbar-nav me-auto mb-2 mb-lg-0 ms-lg-4">
                        <li class="nav-item"><a class="nav-link" aria-current="page" href="${pageContext.request.contextPath}">Inici</a></li>
                        <li class="nav-item"><a class="nav-link" href="#!">Biblioteques</a></li>
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle active" id="navbarDropdown" href="#" role="button"
                               data-bs-toggle="dropdown" aria-expanded="false">Categories</a>
                            <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                                <li><a class="dropdown-item active" href="${pageContext.request.contextPath}/mostrarLlibres">Totes les categories</a></li>
                                <li>
                                    <hr class="dropdown-divider" />
                                </li>
                                <li><a class="dropdown-item" href="#!">Autoajuda</a></li>
                                <li><a class="dropdown-item" href="#!">Ficció</a></li>
                                <li><a class="dropdown-item" href="#!">Juvenil</a></li>
                                <li><a class="dropdown-item" href="#!">Novel·la</a></li>
                                <li><a class="dropdown-item" href="#!">True crime</a></li>
                            </ul>
                        </li>
                    </ul>

                    <div class="d-flex align-items-center ms-lg-auto">
                        <form class="d-flex me-3 my-2 my-lg-0" role="search">
                            <input class="form-control form-control-sm me-2" type="search"
                                   placeholder="Cerca per títol, autor..." aria-label="Search">
                            <button class="btn btn-tot btn-sm" type="submit">
                                <i class="bi bi-search-heart"></i>
                            </button>
                        </form>

                        <a href="#" class="btn btn-tot btn-sm my-2 my-lg-0">Inicia sessió <i class="bi bi-person-circle"></i></a>
                    </div>
                </div>
            </div>
        </nav>

        <div class="container py-4">
            <h1 class="mb-4 text-center text-tot-principal">Explora, tria i gaudeix</h1>
            <div class="row row-cols-1 row-cols-sm-2 row-cols-lg-4 g-4">
                <c:forEach var="llibre" items="${llibres}">
                    <div class="col">
                        <div class="card h-100 shadow-sm">
                            <img src="${llibre.imatgeUrl}" class="card-img-top img-fixed mx-auto d-block" alt="Portada de ${llibre.titol}">
                            <div class="card-body d-flex flex-column">
                                <h5 class="card-title mb-1 text-tot-bold"><c:out value="${llibre.titol}"/></h5>
                                <p class="text-muted mb-2 text-tot-light"><c:out value="${llibre.autor}"/></p>                            
                                <ul class="list-unstyled small mb-3 text-tot-isbn"> 
                                    <li><strong>ISBN:</strong> <c:out value="${llibre.isbn}"/></li>
                                </ul>
                                <div class="mt-auto">
                                    <a href="#" class="btn btn-tot w-100">Més informació</a>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>

        <footer class="bg-tot text-center text-lg-start border-top mt-5 py-3">
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
                        <div class="d-flex justify-content-center">
                            <a href="#"><i class="bi bi-twitter mx-2 text-secondary"></i></a>
                            <a href="#"><i class="bi bi-facebook mx-2 text-secondary"></i></a>
                            <a href="#"><i class="bi bi-instagram mx-2 text-secondary"></i></a>
                        </div>
                        <p class="fst-italic small mt-2 mb-0">“Llegir és viure mil vides.”</p>
                    </div>
                </div>

                <hr class="my-3">
                <p class="text-center small text-muted mb-0">© 2025 TotEsBook. Tots els drets reservats.</p>
            </div>
        </footer>


        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>

=======
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="ca">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Catàleg de Llibres - TotEsBook</title>
    <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/assets/favicon.ico" />
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body class="d-flex flex-column min-vh-100">

    <!-- ===== INICI CAPÇALERA INCRUSTADA ===== -->
    <nav class="navbar navbar-expand-lg navbar-light bg-totlight sticky-top shadow-sm">
        <div class="container px-4 px-lg-5">
            <a class="navbar-brand" href="${pageContext.request.contextPath}">
                <img src="${pageContext.request.contextPath}/img/logo.jpg" alt="Logo TotEsBook" height="30" class="d-inline-block align-text-top logo">
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
                        <a class="nav-link dropdown-toggle active" id="navbarDropdown" href="#" role="button"
                           data-bs-toggle="dropdown" aria-expanded="false">Catàleg</a>
                        <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                            <li><a class="dropdown-item active" href="${pageContext.request.contextPath}/cataleg">Veure Tot</a></li>
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
                         <c:if test="${sessionScope.sessioUsuari.rol == 'ADMIN'}">
                            <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/mostrarUsuaris">Gestió Usuaris</a></li>
                         </c:if>
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
                            <a href="${pageContext.request.contextPath}/login.jsp" class="btn btn-tot btn-sm my-2 my-lg-0">
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
                                             <li><a class="dropdown-item" href="${pageContext.request.contextPath}/dashboard_usuari.jsp">El Meu Panell</a></li>
                                         </c:when>
                                         <c:when test="${sessionScope.sessioUsuari.rol == 'BIBLIOTECARI'}">
                                              <li><a class="dropdown-item" href="${pageContext.request.contextPath}/dashboard_bibliotecari.jsp">Panell Bibliotecari</a></li>
                                         </c:when>
                                         <c:when test="${sessionScope.sessioUsuari.rol == 'ADMIN'}">
                                              <li><a class="dropdown-item" href="${pageContext.request.contextPath}/dashboard_administrador.jsp">Panell Admin</a></li>
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

    <!-- Secció Principal -->
    <section class="py-5 flex-grow-1">
        <div class="container px-4 px-lg-5 mt-5">
            <h1 class="text-center text-tot-bold mb-4">Catàleg Complet</h1>

            <c:choose>
                <c:when test="${not empty llibres}">
                    <p class="text-center text-muted mb-5">S'han trobat ${fn:length(llibres)} llibres.</p>
                    <div class="row gx-4 gx-lg-5 row-cols-2 row-cols-md-3 row-cols-xl-4 justify-content-center">
                        <c:forEach var="llibre" items="${llibres}">
                            <div class="col mb-5">
                                <div class="card h-100 shadow-sm">
                                    <c:choose>
                                        <c:when test="${not empty llibre.imatgeUrl}">
                                             <img class="card-img-top" src="<c:url value='${llibre.imatgeUrl}'/>" alt="Portada de <c:out value='${llibre.titol}'/>" style="height: 250px; object-fit: contain; padding: 10px;">
                                        </c:when>
                                        <c:otherwise>
                                             <img class="card-img-top" src="https://dummyimage.com/450x300/dee2e6/6c757d.jpg" alt="Portada no disponible" style="height: 250px; object-fit: contain; padding: 10px;" />
                                        </c:otherwise>
                                    </c:choose>
                                    <div class="card-body p-4">
                                        <div class="text-center">
                                            <h5 class="text-tot-bold"><c:out value="${llibre.titol}"/></h5>
                                            <p class="text-tot-light mb-0"><c:out value="${llibre.autor}"/></p>
                                        </div>
                                    </div>
                                    <div class="card-footer p-4 pt-0 border-top-0 bg-transparent">
                                        <div class="text-center">
                                            <%-- Enllaç corregit al LlibreServlet --%>
                                            <a class="btn btn-tot mt-auto" href="${pageContext.request.contextPath}/llibre?isbn=${llibre.isbn}">Més informació</a>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </c:when>
                <c:otherwise>
                     <div class="alert alert-info text-center" role="alert">
                         Actualment no hi ha llibres disponibles al catàleg o hi ha hagut un error en carregar-los.
                     </div>
                </c:otherwise>
            </c:choose>
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

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
>>>>>>> feature-login
