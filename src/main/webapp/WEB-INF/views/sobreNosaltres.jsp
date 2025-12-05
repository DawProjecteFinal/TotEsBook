<%-- 
    Document   : sobreNosaltres
    Created on : 1 dic 2025, 8:51:43
    Author     : Diana Martin Vilá
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ca">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Sobre nosaltres</title>
        <link rel="icon" type="image/x-icon" href="assets/favicon.ico" />
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/styles.css">
    </head>

    <body class="d-flex flex-column min-vh-100">
        <!-- ===== Encapçalat ===== -->
        <header class="bg-tot py-1">
            <div class="container px-4 px-lg-5 my-1">
                <div class="text-center text-white">
                    <img src="${pageContext.request.contextPath}/assets/images/logo-gran.jpeg" alt="Logo" class="logo-header"> 
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

                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/biblioteques">Biblioteques</a>
                        </li>
                        <!-- Dropdown + categories -->
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle" id="navbarDropdown" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">Categories</a>
                            <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                                <li><a class="dropdown-item" href="${pageContext.request.contextPath}/mostrarLlibres">Totes les categories</a></li>
                                <li><hr class="dropdown-divider" /></li>
                                <li><a class="dropdown-item" href="${pageContext.request.contextPath}/mostrarLlibres?categoria=Self-Help">Autoajuda</a></li>
                                <li><a class="dropdown-item" href="${pageContext.request.contextPath}/mostrarLlibres?categoria=Biography%20%26%20Autobiography">Biografíes i Memòries</a></li>                                   
                                <li><a class="dropdown-item" href="${pageContext.request.contextPath}/mostrarLlibres?categoria=True Crime">Crims reals</a></li>
                                <li><a class="dropdown-item" href="${pageContext.request.contextPath}/mostrarLlibres?categoria=Cooking">Cuina i gastronomia</a></li>
                                <li><a class="dropdown-item" href="${pageContext.request.contextPath}/mostrarLlibres?categoria=Juvenile Fiction">Ficció juvenil</a></li>
                                <li><a class="dropdown-item" href="${pageContext.request.contextPath}/mostrarLlibres?categoria=Fiction">Novel·la i ficció</a></li>
                                <li><a class="dropdown-item" href="${pageContext.request.contextPath}/mostrarLlibres?categoria=Young Adult Fiction">Novel·la juvenil</a></li>
                                <li><a class="dropdown-item" href="${pageContext.request.contextPath}/mostrarLlibres?categoria=Psychology">Psicologia</a></li>
                            </ul>
                        </li>
                        <!-- Dropdown + formulari cerca avançada -->
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle" id="dropdownAdvanced" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                                Cerca avançada
                            </a>
                            <ul class="dropdown-menu" aria-labelledby="dropdownAdvanced">
                                <li><a class="dropdown-item" href="#" data-field="autor">Autor</a></li>
                                <li><hr class="dropdown-divider" /></li>
                                <li><a class="dropdown-item" href="#" data-field="idioma">Idioma</a></li>
                                <li><hr class="dropdown-divider" /></li>
                                <li><a class="dropdown-item" href="#" data-field="isbn">ISBN</a></li>
                            </ul>
                        </li>
                        <form id="advancedSearch" class="d-flex me-3 my-2 my-lg-0" method="get" action="<c:url value='/cercar'/>">
                            <input type="hidden" name="field" id="field" value="">

                            <div id="searchGroup" class="input-group d-none">
                                <!-- Input de text per autor / isbn -->
                                <input id="searchInput" class="form-control form-control-sm me-2" name="q" type="search" placeholder="" aria-label="Advanced search" autocomplete="off"
                                       required oninvalid="this.setCustomValidity('Aquest camp és obligatori')" oninput="this.setCustomValidity('')" />
                                <select id="idiomaSelect" class="form-select form-select-sm me-2 d-none">
                                    <option value="">Tria l'idioma</option>
                                    <option value="ca">Català</option>
                                    <option value="es">Castellà</option>
                                </select>

                                <button class="btn btn-tot btn-sm" type="submit">
                                    <i class="bi bi-search"></i>
                                </button>
                            </div>
                        </form>

                        <c:if test="${not empty sessionScope.sessioUsuari}">
                
                                <%-- Enllaç a Gestió d'Usuaris (només per a Admin) --%>
                                <c:if test="${sessionScope.sessioUsuari.rol == 'ADMIN'}">
                                <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/mostrarUsuaris">Gestió Usuaris</a></li>
                                </c:if>
                            </c:if>
                    </ul>



                    <!-- Cerca per títol -->        
                    <div class="d-flex align-items-center ms-lg-auto">
                        <form class="d-flex me-3 my-2 my-lg-0" role="search" method="GET" action="${pageContext.request.contextPath}/mostrarLlibres">
                            <input class="form-control form-control-sm me-2" type="search" name="q" 
                                   placeholder="Cerca per titol" aria-label="Search" autocomplete="off" required
                                   oninvalid="this.setCustomValidity('Aquest camp és obligatori')"
                                   oninput="this.setCustomValidity('')" />
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
        <div class="container mt-5">
            <h2>Descobreix del projecte de TotEsBook</h2>
            <p>TotEsBook és un projecte del mòdul 12, Projecte de desenvolupament d’aplicacions web, del cicle superior de Desenvolupament d’Aplicacions Web de l’Institut Obert de Catalunya. El projecte ha estat proposat per l’equip 5 i està format per l’Edinson Javier Intriago Romero, la Diana Martin Vilá, en Jose Andres Miro Ferre i la Roser Ruiz Rojas.
            </p>
            <p>Aquest grup es va formar perquè ens van interessar els perfils dels integrants del fòrum. Vam valorar experiències prèvies i personals, com per exemple, l'experiència que tenim cadascú amb companys al treball amb perfils semblants dels perfils de la resta d’integrants del grup de l’assignatura.</p>
            <p>El servei que volem oferir es tracta de la gestió integral de préstec de llibres de biblioteques. Aquesta aplicació permetrà gestionar una base de dades de llibres i també la gestió de diferents usuaris amb els seus rols. La nostra aplicació permetrà gestionar l’estoc de llibres disponibles, els préstecs, les estadístiques dels llibres i els usuaris, d’entre altres funcions. </p>
        </div>
        <div class="container mt-5">
        <h2>Coneix els membres de l'equip TotEsBook</h2>
        <div class="row row-cols-1 row-cols-md-4 g-4">
          <!-- Columna 1 -->
           <div class="col text-center">
            <img src="${pageContext.request.contextPath}/assets/images/edinson.jpg"
                 class="rounded-circle"
                 width="140" height="140"
                 alt="Edinson">
            <h3 class="fw-normal mt-3">Edinson</h3>
            <p>Perfil de gestió d’equips, amb més experiència d’administrador de sistemes, intervenció en el desplegament, implementació dels sistemes i l’administració dels serveis.</p>
          </div>

          <!-- Columna 2 -->
          <div class="col text-center">
          <img src="${pageContext.request.contextPath}/assets/images/diana.jpg"
                 class="rounded-circle"
                 width="140" height="140"
                 alt="Diana">
            <h3 class="fw-normal mt-3">Diana</h3>
            <p>Perfil de dissenyador d'interfície que pot aportar idees sobre el disseny de l’aplicatiu.</p>
          </div>

          <!-- Columna 3 -->
          <div class="col text-center">
          <img src="${pageContext.request.contextPath}/assets/images/josep.jpg"
                 class="rounded-circle"
                 width="140" height="140"
                 alt="Josep">
            <h3 class="fw-normal mt-3">Josep</h3>
            <p>Perfil de desenvolupador de software que pot aportar idees amb relació a la codificació del programari.</p>
          </div>

          <!-- Columna 4 -->
          <div class="col text-center">
          <img src="${pageContext.request.contextPath}/assets/images/roser.jpg"
                 class="rounded-circle"
                 width="140" height="140"
                 alt="Roser">
            <h3 class="fw-normal mt-3">Roser</h3>
            <p>Perfil de tester, aportant una mirada clau per adaptar l’aplicació a qualsevol usuari.</p>
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

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets/js/cerca-avancada.js"></script>
    </body>
</html>
