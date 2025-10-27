<%-- 
    Document   : mostrarLlibres.jsp
    Created on : 28 sept 2025, 7:01:42
    Author     : jmiro
--%>

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
                    <img src="${pageContext.request.contextPath}/images/logo-gran.jpeg" alt="Logo">
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
                                <li><a class="dropdown-item" href="${pageContext.request.contextPath}/mostrarLlibres">Totes les categories</a></li>
                                <li>
                                    <hr class="dropdown-divider" />
                                </li>
                                <li><a class="dropdown-item active" href="#!">Autoajuda</a></li>
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

                        <a href="#" class="btn btn-tot btn-sm my-2 my-lg-0">Inicia sessió</a>
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

