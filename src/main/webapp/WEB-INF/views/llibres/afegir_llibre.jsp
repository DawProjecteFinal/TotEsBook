<%-- 
    Author     :  Equip TotEsBook
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="ca">
    <head>
        <meta charset="UTF-8">
        <title>Afegir Llibre</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/styles.css">
    </head>
    <body class="bg-light d-flex flex-column min-vh-100">

        <div class="container mt-5">
            <h5>Afegir Llibre a una Biblioteca</h5>


            <c:if test="${not empty error}">
                <div class="alert alert-danger mt-3">${error}</div>
            </c:if>
            <c:if test="${not empty success}">
                <div class="alert alert-success mt-3">${success}</div>
            </c:if>


            <form action="${pageContext.request.contextPath}/gestio/llibres/afegir" method="post" class="mt-3">

                <div class="mb-3">
                    <label class="form-label">ISBN</label>
                    <input type="text" name="isbn" class="form-control"
                           placeholder="Introdueix l'ISBN" required>
                </div>

                <div class="mb-3">
                    <label class="form-label">Biblioteca</label>
                    <select name="idBiblioteca" class="form-select" required>
                        <option value="">-- Selecciona una biblioteca --</option>
                        <c:forEach var="b" items="${biblioteques}">
                            <option value="${b.idBiblioteca}">${b.nom}</option>
                        </c:forEach>
                    </select>
                </div>

                <div class="mb-3">
                    <label class="form-label">Exemplars</label>
                    <input type="number" name="exemplars" class="form-control"
                           min="1" value="1" required>
                </div>

                <button type="submit" class="btn btn-tot">Afegir Llibre</button>
                <a href="${pageContext.request.contextPath}/dashboard_administrador"
                   class="btn btn-secondary">Cancel·lar</a>
            </form>


            <c:if test="${not empty llibreAfegit}">
                <hr class="my-4">

                <h5>Llibre afegit</h5>

                <div class="card mt-3" style="max-width: 450px;">
                    <div class="row g-0">
                        <div class="col-4">
                            <img src="${llibreAfegit.imatgeUrl}" 
                                 class="img-fluid rounded-start"
                                 alt="Portada de ${llibreAfegit.titol}">
                        </div>
                        <div class="col-8">
                            <div class="card-body">
                                <h5 class="card-title mb-1">
                                    <c:out value="${llibreAfegit.titol}"/>
                                </h5>
                                <p class="card-text text-muted mb-2">
                                    <c:out value="${llibreAfegit.autor}"/>
                                </p>
                                <p class="card-text mb-0">
                                    <small class="text-muted">
                                        ISBN: <c:out value="${llibreAfegit.isbn}"/>
                                    </small>
                                </p>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="mt-4">
                    <a href="${pageContext.request.contextPath}/dashboard_administrador"
                       class="btn btn-tot">
                        Tornar al meu panell de gestió
                    </a>
                </div>
            </c:if>

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

