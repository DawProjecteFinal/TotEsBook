<%-- 
    Author     : Equip TotEsBook
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="ca">
    <head>
        <title>Autors Populars</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/styles.css">
    </head>
    <body class="d-flex flex-column min-vh-100">

        <div class="container px-4 px-lg-5 mt-5 mb-5">

            <div class="mb-3">
                <a href="${pageContext.request.contextPath}/dashboard_bibliotecari"
                   class="btn btn-outline-secondary btn-sm">
                    <i class="bi bi-arrow-left"></i> Tornar al Panell
                </a>
            </div>


            <div class="card shadow-sm border-0">
                <div class="card-header bg-totlight d-flex justify-content-between align-items-center">
                    <div>
                        <h4 class="mb-0 text-tot-bold">Autors més populars</h4>
                        <p class="mb-0 small text-muted">
                            Rànquing Top 10 segons el nombre de préstecs registrats.
                        </p>
                    </div>
                    <span class="badge bg-primary-subtle text-primary border border-primary small">
                        <i class="bi bi-bar-chart-line me-1"></i>
                        Estadística de préstecs
                    </span>
                </div>

                <div class="card-body">

                    <c:choose>
                        <c:when test="${empty stats}">
                            <div class="text-center text-muted fst-italic py-4">
                                <i class="bi bi-info-circle me-2"></i>
                                Encara no hi ha dades d'autors populars.
                            </div>
                        </c:when>

                        <c:otherwise>
                            <ul class="list-group list-group-flush">
                                <c:forEach var="item" items="${stats}" varStatus="status">
                                    <li class="list-group-item d-flex justify-content-between align-items-center">
                                        <div class="d-flex align-items-center">
                                            <span class="badge rounded-pill bg-primary me-3 px-3 py-2">
                                                #${status.count}
                                            </span>
                                            <div>
                                                <div class="fw-bold">${item.autor}</div>
                                                <div class="small text-muted">
                                                    Autor/a destacat/da de la teva biblioteca
                                                </div>
                                            </div>
                                        </div>

                                        <span class="badge bg-secondary rounded-pill px-3 py-2">
                                            <i class="bi bi-book-half me-1"></i>
                                            ${item.totalPrestecs} préstecs
                                        </span>
                                    </li>
                                </c:forEach>
                            </ul>
                        </c:otherwise>
                    </c:choose>

                </div>
            </div>
        </div>
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