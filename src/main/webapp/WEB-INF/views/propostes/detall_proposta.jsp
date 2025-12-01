<%-- 
    Document   : detall_proposta
    Created on : 27 nov 2025, 15:57:03
    Author     : eqip TotEsBook
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<!DOCTYPE html>
<html lang="ca">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Detall proposta - TotEsBook</title>
        <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/assets/favicon.ico" />
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/styles.css">
    </head>


    <body class="d-flex flex-column min-vh-100">
        <!-- Mostrar logo al inici de la pàgina -->
        <header class="bg-tot py-1">
            <div class="container px-4 px-lg-5 my-1">
                <div class="text-center text-white">
                    <img src="${pageContext.request.contextPath}/assets/images/logo-gran.jpeg" alt="Logo" class="logo-header"> 
                </div>
            </div>
        </header>

        <section>
            <h3>Gestió de Proposta</h3>

            <div class="card p-3">
                <h5>${proposta.titol}</h5>
                <p><strong>Autor:</strong> ${proposta.autor}</p>
                <p><strong>ISBN:</strong> ${proposta.isbn}</p>
                <p><strong>Usuari ID:</strong> ${proposta.idUsuari}</p>

                <form action="${pageContext.request.contextPath}/propostes/actualitzar" method="POST">
                    <input type="hidden" name="idProposta" value="${proposta.idProposta}"/>

                    <div class="mb-3">
                        <label class="form-label">Estat</label>
                        <select class="form-select" name="estat">
                            <c:forEach items="${estats}" var="e">
                                <option value="${e}" ${proposta.estat == e ? 'selected' : ''}>${e}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="mb-3">
                        <label class="form-label">Resposta / Observacions</label>
                        <textarea class="form-control" name="resposta">${proposta.resposta}</textarea>
                    </div>

                    <button type="submit" class="btn btn-primary">Guardar canvis</button>
                    <a href="${pageContext.request.contextPath}/dashboard_administrador" class="btn btn-secondary">Cancel·lar</a>
                </form>
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
                            <li><a href="#" class="text-decoration-none text-secondary">Contacte</a></li> 
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

        <!-- Script de Bootstrap Bundle -->

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

    </body>
</html>    








