<%-- 
    Document   : confirmar_proposta
    Created on : 24 nov 2025, 09:06:47
    Author     : equip totesbook
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Confirmar roposta</title>
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
            <div class="container d-flex justify-content-center align-items-center" style="min-height: 80vh;">
                <div class="card shadow-lg" style="max-width: 650px; width: 100%;">

                    <div class="card-header bg-primary text-white text-center">
                        <h4 class="mb-0">Confirmar proposta d’adquisició</h4>
                    </div>

                    <div class="card-body p-4">

                        <div class="text-center mb-4">
                            <img src="${llibre.imatgeUrl}" 
                                 alt="Portada llibre"
                                 class="img-fluid rounded"
                                 style="max-height: 250px;">
                        </div>

                        <h5 class="text-center fw-bold mb-3">${llibre.titol}</h5>

                        <p><strong>Autor:</strong> ${llibre.autor}</p>
                        <p><strong>ISBN:</strong> ${llibre.isbn}</p>
                        <p><strong>Editorial:</strong> ${llibre.editorial}</p>

                        <hr>

                        <form action="${pageContext.request.contextPath}/propostes/guardar" method="POST">

                            <input type="hidden" name="titol" value="${llibre.titol}">
                            <input type="hidden" name="autor" value="${llibre.autor}">
                            <input type="hidden" name="isbn" value="${llibre.isbn}">
                            <input type="hidden" name="editorial" value="${llibre.editorial}">
                            <input type="hidden" name="sinopsis" value="${llibre.sinopsis}">

                            <div class="mb-3">
                                <label class="form-label fw-bold">Motiu de la proposta</label>
                                <textarea class="form-control" name="motiu" rows="4"
                                          placeholder="Explica breument perquè proposes adquirir aquest llibre..." required></textarea>
                            </div>

                            <button type="submit" class="btn btn-success w-100 mb-3">
                                <i class="bi bi-check-circle"></i> Confirmar proposta
                            </button>

                        </form>

                        <c:url var="urlTornar" value="/propostes/buscar_proposta">
                            <c:param name="titol" value="${titol}" />
                            <c:param name="autor" value="${autor}" />

                            <c:if test="${not empty isbn_cerca}">
                                <c:param name="isbn" value="${isbn_cerca}" />
                            </c:if>
                        </c:url>

                        <a href="${urlTornar}" class="btn btn-secondary w-100 mt-3">
                            <i class="bi bi-arrow-left"></i> Tornar als resultats
                        </a>


                    </div>
                </div>
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
    </body>
</html>
