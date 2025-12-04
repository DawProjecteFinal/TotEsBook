<%-- 
    Document   : llista_propostes
    Created on : 24 nov 2025, 10:39:12
    Author     : equip TotEsBook
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Llista de propostes</title>
        <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/assets/favicon.ico" />
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/styles.css">

        <style>
            .badge-pendent {
                background-color: #ffc107;
            }
            .badge-acceptada {
                background-color: #28a745;
            }
            .badge-rebutjada {
                background-color: #dc3545;
            }
            .badge-comprat {
                background-color: #0d6efd;
            }
        </style>
    </head>
    <body class="bg-light d-flex flex-column min-vh-100">        <!-- Logo gran al principi de la pàgina -->
        <header class="bg-tot py-1">
            <div class="container px-4 px-lg-5 my-1">
                <div class="text-center text-white">
                    <img src="${pageContext.request.contextPath}/assets/images/logo-gran.jpeg" alt="Logo" class="logo-header"> 
                </div>
            </div>
        </header>  

        <section  class="flex-grow-1">
            <div class="container mt-5">

                <div class="d-flex justify-content-between align-items-center mb-4">

                    <div>
                        <h2 class="text-tot-bold">📚 Propostes d’adquisició</h2>
                    </div>

                    <div class="d-flex gap-2">
                        <a href="${pageContext.request.contextPath}/dashboard_usuari"
                           class="btn btn-secondary">
                            <i class="bi bi-arrow-left"></i> Tornar al panell
                        </a>

                        <a href="${pageContext.request.contextPath}/propostes/formulari_proposta"
                           class="btn btn-primary">
                            <i class="bi bi-plus"></i> Nova proposta
                        </a>
                    </div>

                </div>


                <!-- Missatge si no hi ha cap proposta -->
                <c:if test="${empty propostes}">
                    <div class="alert alert-info">
                        Encara no has realitzat cap proposta d’adquisició.
                    </div>
                </c:if>

                <!-- Taula -->
                <c:if test="${not empty propostes}">
                    <div class="table-responsive shadow-sm">
                        <table class="table table-hover align-middle bg-white rounded">
                            <thead class="table-dark">
                                <tr>
                                    <th>ID</th>
                                    <th>Títol</th>
                                    <th>Autor</th>
                                    <th>ISBN</th>
                                    <th>Data</th>
                                    <th>Estat</th>
                                    <th>Accions</th>
                                </tr>
                            </thead>

                            <tbody>
                                <c:forEach var="p" items="${propostes}">
                                    <tr>
                                        <td>${p.idProposta}</td>
                                        <td>${p.titol}</td>
                                        <td>${p.autor}</td>
                                        <td>${p.isbn}</td>
                                        <td>${p.dataPropostaFormatted}</td>
                                        <!-- Informació de l’estat -->
                                        <td>
                                            <span class="badge badge-${p.estat}">
                                                ${p.estat}
                                            </span>
                                        </td>

                                        <!-- Accions -->
                                        <td>
                                            <!--<a href="${pageContext.request.contextPath}/propostes/detall?id=${p.idProposta}"
                                               class="btn btn-sm btn-outline-primary">
                                                <i class="bi bi-eye"></i>Veure
                                            </a>-->

                                            <c:if test="${p.estat == 'pendent'}">
                                                <a href="${pageContext.request.contextPath}/propostes/eliminar?id=${p.idProposta}"
                                                   class="btn btn-sm btn-outline-danger"
                                                   onclick="return confirm('Segur que vols eliminar aquesta proposta?');">
                                                    <i class="bi bi-trash"></i>Eliminar
                                                </a>
                                            </c:if>

                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </c:if>

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


    </body>
</html>
