<%-- 
    Document   : llibres_resultats_api
    Created on : 1 dic 2025, 11:42:20
    Author     : equip TotEsBook
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Llibres Google Books</title>
        <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/assets/favicon.ico" />
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/styles.css">
    </head>
    <body class="d-flex flex-column min-vh-100">

        <!-- Logo gran al principi de la pàgina -->
        <header class="bg-tot py-1">
            <div class="container px-4 px-lg-5 my-1">
                <div class="text-center text-white">
                    <img src="${pageContext.request.contextPath}/assets/images/logo-gran.jpeg" alt="Logo" class="logo-header"> 
                </div>
            </div>
        </header>        

        <section>
            <div class="container mt-4">

                <div class="card shadow-sm mb-4">
                    <div class="card-header bg-totlight">
                        <h4 class="mb-0 text-tot-bold">
                            <i class="bi bi-book me-2"></i> Resultats trobats a Google Books
                        </h4>
                    </div>

                    <!-- Informació dels criteris de cerca -->
                    <div class="mb-3 p-3 bg-light border rounded">

                        <h5 class="mb-2 text-secondary">
                            <i class="bi bi-funnel-fill me-2"></i> Criteris de la cerca:
                        </h5>

                        <ul class="mb-0">
                            <c:if test="${not empty titol}">
                                <li><strong>Títol:</strong> ${titol}</li>
                                </c:if>

                            <c:if test="${not empty autor}">
                                <li><strong>Autor:</strong> ${autor}</li>
                                </c:if>

                            <c:if test="${not empty isbn}">
                                <li><strong>ISBN:</strong> ${isbn}</li>
                                </c:if>
                        </ul>
                    </div>

                    <div class="card-body">

                        <!-- Si no hi ha resultats -->
                        <c:if test="${empty resultats}">
                            <div class="alert alert-warning">
                                <i class="bi bi-exclamation-triangle-fill me-2"></i>
                                No s'ha trobat cap llibre amb els criteris indicats.
                            </div>

                            <a href="${pageContext.request.contextPath}/propostes/buscar_proposta" 
                               class="btn btn-secondary mt-2">
                                <i class="bi bi-arrow-left"></i> Nova cerca
                            </a>
                        </c:if>

                        <!-- Llista de resultats -->
                        <c:if test="${not empty resultats}">
                            <p class="text-muted">
                                Resultats trobats: <strong>${fn:length(resultats)}</strong>
                            </p>

                            <div class="row row-cols-1 row-cols-md-2 row-cols-lg-3 g-4">

                                <c:forEach var="llibre" items="${resultats}">
                                    <div class="col">

                                        <div class="card h-100 shadow-sm">

                                            <!-- Miniatura -->
                                            <c:if test="${not empty llibre.imatgeUrl}">
                                                <img src="${llibre.imatgeUrl}"
                                                     class="card-img-top object-fit-contain p-2"
                                                     style="height: 220px;"
                                                     alt="Imatge del llibre">
                                            </c:if>

                                            <c:if test="${empty llibre.imatgeUrl}">
                                                <div class="text-center p-4 text-muted">
                                                    <i class="bi bi-book" style="font-size: 3rem;"></i><br>
                                                    Sense imatge
                                                </div>
                                            </c:if>

                                            <div class="card-body">

                                                <h5 class="card-title text-tot-bold">${llibre.titol}</h5>

                                                <p class="card-text mb-1">
                                                    <strong>Autor:</strong> ${llibre.autor}
                                                </p>

                                                <p class="card-text mb-1">
                                                    <strong>Editorial:</strong> ${llibre.editorial}
                                                </p>

                                                <p class="card-text mb-1">
                                                    <strong>ISBN:</strong> 
                                                    <c:choose>
                                                        <c:when test="${not empty llibre.isbn}">
                                                            ${llibre.isbn}
                                                        </c:when>
                                                        <c:otherwise>
                                                            <span class="text-muted">No disponible</span>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </p>

                                                <p class="card-text">
                                                    <strong>Categoria:</strong> ${llibre.categoria}
                                                </p>

                                            </div>

                                            <div class="card-footer text-center bg-white">

                                                <input type="hidden" name="isbn" value="${llibre.isbn}">
                                                <input type="hidden" name="titol" value="${llibre.titol}">
                                                <input type="hidden" name="autor" value="${llibre.autor}">
                                                <input type="hidden" name="editorial" value="${llibre.editorial}">
                                                <input type="hidden" name="categoria" value="${llibre.categoria}">
                                                <input type="hidden" name="imatgeUrl" value="${llibre.imatgeUrl}">
                                                <input type="hidden" name="idioma" value="${llibre.idioma}">

                                                <a href="${pageContext.request.contextPath}/llibres/fitxa_api?isbn=${fn:escapeXml(fn:trim(llibre.isbn))}&titol=${fn:escapeXml(fn:trim(titol))}&autor=${fn:escapeXml(fn:trim(autor))}&isbnCerca=${fn:escapeXml(fn:trim(isbn))}&mode=api"
                                                   class="btn btn-primary w-100">
                                                    Veure
                                                </a>


                                            </div>
                                        </div>
                                    </div>

                                </c:forEach>
                            </div>

                            <!-- Botó per tornar a la cerca -->
                            <div class="mt-4">
                                <a href="${pageContext.request.contextPath}/dashboard_usuari" 
                                   class="btn btn-secondary">
                                    <i class="bi bi-arrow-left"></i> Pàgina inicial
                                </a>
                            </div>

                        </c:if>

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

    </body>
</html>
