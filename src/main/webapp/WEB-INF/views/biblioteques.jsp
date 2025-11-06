<%-- 
    Document   : mostrarBiblioteques
    Created on : 6 nov 2025, 15:11:59
    Author     : jmiro
--%>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html lang="ca">
    <head>
        <meta charset="UTF-8">
        <title>Biblioteques</title>

        <link rel="icon" type="image/x-icon" href="assets/favicon.ico" />
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/styles.css">

    </head>
    <body  class="bg-light">

        <!-- Encapçalat -->
        <header class="bg-tot py-1">
            <div class="container px-4 px-lg-5 my-1">
                <div class="text-center text-white">
                    <img src="${pageContext.request.contextPath}/assets/images/logo-gran.jpeg" alt="Logo" class="logo-header">
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
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/biblioteques">Biblioteques</a>
                        </li>
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

        <div class="container py-5">
            <h1 class="mb-4 text-center text-tot-principal">Xarxa de Biblioteques TotEsBook</h1>

            <div class="row justify-content-center text-center">
                <c:forEach var="biblioteca" items="${biblioteques}">
                    <div class="col-md-4 mb-4">
                        <div class="card border-0 shadow-lg p-4 h-100">
                            <i class="bi bi-book-half display-3 text-tot-principal mb-3"></i>
                            <h4 class="fw-bold mb-2"><c:out value="${biblioteca.nom}"/></h4>
                            <p class="text-muted mb-1"><c:out value="${biblioteca.adreca}"/></p>
                            <p class="small text-tot-light"><i class="bi bi-telephone"></i> <c:out value="${biblioteca.telefon}"/></p>
                            <a href="${pageContext.request.contextPath}/biblioteques/${biblioteca.idBiblioteca}/llibres" 
                               class="btn btn-tot w-100">
                                Entra a la biblioteca
                            </a>
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