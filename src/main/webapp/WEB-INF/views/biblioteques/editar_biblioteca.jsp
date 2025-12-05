<%-- 
    Author     : Equip TotEsBook
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="ca">
    <head>
        <meta charset="UTF-8">
        <title>Editar Biblioteca</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css">
    </head>
    <body class="bg-light d-flex flex-column min-vh-100">

        <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

        <div class="container mt-5">
            <h2 class="mb-4">Editar Biblioteca</h2>

            <form action="${pageContext.request.contextPath}/gestio/biblioteques/${biblioteca.idBiblioteca}/editar" method="post">
                <input type="hidden" name="idBiblioteca" value="${biblioteca.idBiblioteca}" />
                <div class="mb-3">
                    <label class="form-label">Nom</label>
                    <input type="text" name="nom" class="form-control" value="${biblioteca.nom}" required>
                </div>

                <div class="mb-3">
                    <label class="form-label">Adreça</label>
                    <input type="text" name="adreca" class="form-control" value="${biblioteca.adreca}">
                </div>

                <div class="mb-3">
                    <label class="form-label">Telèfon</label>
                    <input type="text" name="telefon" class="form-control" value="${biblioteca.telefon}">
                </div>

                <div class="mb-3">
                    <label class="form-label">Email</label>
                    <input type="email" name="email" class="form-control" value="${biblioteca.email}">
                </div>

                <div class="mb-3">
                    <label class="form-label">Bibliotecari Responsable</label>
                    <select name="idBibliotecari" class="form-select">
                        <option value="">-- Selecciona un bibliotecari --</option>

                        <c:forEach var="b" items="${llistaBibliotecaris}">
                            <option value="${b.idAgent}"
                                    ${biblioteca.bibliotecari != null && biblioteca.bibliotecari.idAgent == b.idAgent ? 'selected' : ''}>
                                ${b.nom} ${b.cognoms}
                            </option>
                        </c:forEach>
                    </select>

                </div>

                <button type="submit" class="btn btn-tot">Guardar Canvis</button>
                <a href="${pageContext.request.contextPath}/gestio/biblioteques" class="btn btn-secondary">Cancel·lar</a>
            </form>
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
