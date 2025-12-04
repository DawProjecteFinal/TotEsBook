<%-- 
    Author     :  Equip TotEsBook
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html lang="ca">
    <head>
        <meta charset="UTF-8">
        <title>Gestionar Préstec</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/styles.css">
    </head>
    <body class="bg-light">

        <div class="container mt-5">

            <h5 class="mb-4">Gestionar Préstec</h5>

            <c:if test="${not empty missatge}">
                <c:choose>
                    <c:when test="${fn:contains(missatge, 'Sanció')}">
                        <div class="alert alert-danger">
                            ${missatge}
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="alert alert-success">
                            ${missatge}
                        </div>
                    </c:otherwise>
                </c:choose>
            </c:if>

            <c:if test="${not empty error}">
                <div class="alert alert-danger">
                    ${error}
                </div>
            </c:if>


            <div class="card shadow-sm">
                <div class="card-body">


                    <div class="mb-3">
                        <label class="form-label">ISBN</label>
                        <input type="text" class="form-control" value="${prestec.llibre.isbn}" readonly>
                    </div>

                    <div class="mb-3">
                        <label class="form-label">Títol</label>
                        <input type="text" class="form-control" value="${prestec.llibre.titol}" readonly>
                    </div>

                    <div class="mb-3">
                        <label class="form-label">Usuari</label>
                        <input type="text" class="form-control"
                               value="${prestec.usuari.nom} ${prestec.usuari.cognoms}" readonly>
                    </div>

                    <div class="mb-3">
                        <label class="form-label">Data préstec</label>
                        <input type="text" class="form-control" value="${prestec.dataPrestecFormatted}" readonly>
                    </div>

                    <div class="mb-3">
                        <label class="form-label">Data venciment</label>
                        <input type="text" class="form-control" value="${prestec.dataVencimentCalculada}" readonly>
                    </div>




                    <h6 class="mb-3">Accions</h6>

                    <div class="d-flex flex-wrap gap-2">

                        <form action="${pageContext.request.contextPath}/gestionarPrestec/retornar"
                              method="POST" class="d-inline">
                            <input type="hidden" name="idPrestec" value="${prestec.idPrestec}">
                            <button type="submit" class="btn btn-success">
                                <i class="bi bi-box-arrow-in-down-left"></i> Tornar
                            </button>
                        </form>


                        <form action="${pageContext.request.contextPath}/gestionarPrestec/renovar"
                              method="POST" class="d-inline">
                            <input type="hidden" name="idPrestec" value="${prestec.idPrestec}">
                            <button type="submit" class="btn btn-warning"
                                    <c:if test="${usuariSancionat}">disabled</c:if>>
                                        <i class="bi bi-arrow-repeat"></i> Renovar +30 dies
                                    </button>
                            </form>


                            <form action="${pageContext.request.contextPath}/gestionarPrestec/sancionar"
                              method="POST" class="d-inline">
                            <input type="hidden" name="idPrestec" value="${prestec.idPrestec}">

                            <button type="submit" name="tipus" value="RETARD"
                                    class="btn btn-outline-danger"
                                    <c:if test="${usuariSancionat}">disabled</c:if>>
                                        Sanció per retard
                                    </button>

                                    <button type="submit" name="tipus" value="MAL_ESTAT"
                                            class="btn btn-danger"
                                    <c:if test="${usuariSancionat}">disabled</c:if>>
                                        Sanció per mal estat
                                    </button>
                            </form>

                        </div>

                        <hr>

                        <a href="${pageContext.request.contextPath}/dashboard_bibliotecari"
                       class="btn btn-secondary">
                        &laquo; Tornar al llistat de préstecs
                    </a>

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

