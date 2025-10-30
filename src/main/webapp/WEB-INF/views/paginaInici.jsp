<%-- 
    Document   : paginaInici
    Created on : 28 sept 2025, 17:43:00
    Author     : equip totEsBook
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ca">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>TotEsBook</title>
        <link rel="icon" type="image/x-icon" href="assets/favicon.ico" />
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/styles.css">
    </head>
    
    <body>

        <!-- ===== Encapçalat ===== -->
        <header class="bg-tot py-1">
            <div class="container px-4 px-lg-5 my-1">
                <div class="text-center text-white">
                    <img src="${pageContext.request.contextPath}/assets/images/logo-gran.jpeg" alt="Logo"> 
                </div>
            </div>
        </header>
        <!-- ===== FI Encapçalat ===== -->
                
        <!-- ===== Menu ===== -->
        <nav class="navbar navbar-expand-lg navbar-light bg-totlight">
            <div class="container px-4 px-lg-5">

                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Menú">
                    <span class="navbar-toggler-icon"></span>
                </button>
                
                <div class="collapse navbar-collapse" id="navbarSupportedContent">
                    
                    <ul class="navbar-nav me-auto mb-2 mb-lg-0 ms-lg-4">
                        <li class="nav-item"><a class="nav-link active" aria-current="page" href="${pageContext.request.contextPath}">Inici</a></li>
                        <li class="nav-item"><a class="nav-link" href="#">Biblioteques</a></li>
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle" id="navbarDropdown" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">Categories</a>
                            <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                                <li><a class="dropdown-item" href="${pageContext.request.contextPath}/mostrarLlibres">Totes les categories</a></li>
                                <li><hr class="dropdown-divider" /></li>
                                <li><a class="dropdown-item" href="#">Autoajuda</a></li>
                                <li><a class="dropdown-item" href="#">Ficció</a></li>
                                <li><a class="dropdown-item" href="#">Juvenil</a></li>
                                <li><a class="dropdown-item" href="#">Novel·la</a></li>
                                <li><a class="dropdown-item" href="#">True crime</a></li>
                            </ul>
                        </li>
                         <c:if test="${not empty sessionScope.sessioUsuari}">
                             <li class="nav-item"><a class="nav-link" href="#">Propostes</a></li>
                             <%-- Enllaç a Gestió d'Usuaris (només per a Admin) --%>
                             <c:if test="${sessionScope.sessioUsuari.rol == 'ADMIN'}">
                                <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/mostrarUsuaris">Gestió Usuaris</a></li>
                             </c:if>
                         </c:if>
                    </ul>
                                
                    <div class="d-flex align-items-center ms-lg-auto">
                        <form class="d-flex me-3 my-2 my-lg-0" role="search" method="GET">
                            <input class="form-control form-control-sm me-2" type="search" name="q" 
                                   placeholder="Cerca per titol, autor... " aria-label="Search">
                            <button class="btn btn-tot btn-sm" type="submit">
                                <i class="bi bi-search"></i>
                            </button>
                        </form>
                            
                        <%-- Lògica de Sessió per a Login/Logout --%>
                        <c:choose>
                            <c:when test="${empty sessionScope.sessioUsuari}">
                                <a href="${pageContext.request.contextPath}/login" class="btn btn-tot btn-sm my-2 my-lg-0">
                                    Inicia sessió <i class="bi bi-person-circle"></i>
                                </a>
                            </c:when>
                            <c:otherwise>
                                <div class="dropdown">
                                    <button class="btn btn-tot btn-sm dropdown-toggle" type="button" id="dropdownUsuari" data-bs-toggle="dropdown" aria-expanded="false">
                                        <i class="bi bi-person-fill"></i> <c:out value="${sessionScope.sessioUsuari.nomComplet}"/> 
                                    </button>
                                     <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="dropdownUsuari">
                                    <c:choose>
                                         <c:when test="${sessionScope.sessioUsuari.rol == 'USUARI'}">
                                             <li><a class="dropdown-item" href="${pageContext.request.contextPath}/dashboard_usuari">El Meu Panell</a></li>
                                         </c:when>
                                         <c:when test="${sessionScope.sessioUsuari.rol == 'BIBLIOTECARI'}">
                                              <li><a class="dropdown-item" href="${pageContext.request.contextPath}/dashboard_bibliotecari">Panell Bibliotecari</a></li>
                                         </c:when>
                                         <c:when test="${sessionScope.sessioUsuari.rol == 'ADMIN'}">
                                              <li><a class="dropdown-item" href="${pageContext.request.contextPath}/dashboard_administrador">Panell Admin</a></li>
                                         </c:when>
                                    </c:choose>
                                    <li><hr class="dropdown-divider"></li>
                                        <li><hr class="dropdown-divider"></li>
                                        <li><a class="dropdown-item text-danger" href="${pageContext.request.contextPath}/logout">
                                            <i class="bi bi-box-arrow-right"></i> Tancar Sessió
                                        </a></li>
                                    </ul>
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </div>
        </nav>
        <!-- ===== FI Menu ===== -->

        <!-- ===== Secció Principal de Contingut ===== -->
        <section class="py-5 flex-grow-1">
            <div class="container px-4 px-lg-5 mt-5">
                 <div class="row gx-4 gx-lg-5 justify-content-center mb-5">
                     <c:choose>
                         <c:when test="${not empty sessionScope.sessioUsuari}">
                             <div class="col-lg-6 text-center">
                                <h2 class="text-tot-bold">Benvingut/da de nou, <c:out value="${sessionScope.sessioUsuari.nomComplet}"/>!</h2>
                                <p class="text-tot-light">Explora el nostre catàleg o gestiona els teus préstecs.</p>
                                 <a href="${pageContext.request.contextPath}/cataleg" class="btn btn-tot">Veure Catàleg</a>
                             </div>
                         </c:when>
                         <c:otherwise>
                              <div class="col-lg-6 text-center">
                                <h2 class="text-tot-bold">Descobreix un món de llibres</h2>
                                <p class="text-tot-light">Cerca al nostre catàleg o inicia sessió per accedir als teus préstecs.</p>
                                <a href="${pageContext.request.contextPath}/login" class="btn btn-tot me-2">Inicia Sessió</a>
                                <a href="${pageContext.request.contextPath}/registre" class="btn btn-outline-secondary">Registra't</a>
                             </div>
                         </c:otherwise>
                     </c:choose>
                 </div>

                <h2 class="text-center text-tot-bold mb-4">Novetats Destacades</h2>

                <div class="row gx-4 gx-lg-5 row-cols-2 row-cols-md-3 row-cols-xl-4 justify-content-center">
                    <c:forEach var="i" begin="1" end="8"> 
                        <div class="col mb-5">
                            <div class="card h-100 shadow-sm">
                                <img class="card-img-top" src="https://dummyimage.com/189x290/cccccc/fff.jpg&text=Portada" alt="..." />
                                <div class="card-body p-4">
                                    <div class="text-center">
                                        <h5 class="fw-bolder text-tot-bold">Títol Llibre ${i}</h5>
                                        <p class="text-tot-light mb-0">Autor ${i}</p>
                                    </div>
                                </div>
                                <div class="card-footer p-4 pt-0 border-top-0 bg-transparent">
                                    <div class="text-center">
                                        <a class="btn btn-tot mt-auto" href="#">Més informació</a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </section>
        <!-- ===== FI Secció Principal de Contingut ===== -->

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
                            <li><a href="${pageContext.request.contextPath}/contacte.jsp" class="text-decoration-none text-secondary">Contacte</a></li> 
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
        <!-- ===== FI Peu de pàgina ===== -->

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
        
    </body>
</html>