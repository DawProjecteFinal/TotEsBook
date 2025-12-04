<%-- 
    Author     : Equip TotEsBook
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ca">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Editar Perfil - TotEsBook</title>
    <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/assets/favicon.ico" />
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/styles.css">
</head>
<body class="d-flex flex-column min-vh-100">

    <!-- ===== INICI CAPÇALERA INCRUSTADA ===== -->
    <nav class="navbar navbar-expand-lg navbar-light bg-totlight sticky-top shadow-sm">
        <div class="container px-4 px-lg-5">
            <a class="navbar-brand" href="${pageContext.request.contextPath}/">
                <img src="${pageContext.request.contextPath}/assets/images/logo-gran.jpeg" alt="Logo TotEsBook" height="30" class="d-inline-block align-text-top logo">
            </a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
                    data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false"
                    aria-label="Menú">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav me-auto mb-2 mb-lg-0 ms-lg-4">
                    <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/">Inici</a></li>
                    <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/biblioteques">Biblioteques</a></li>
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
                     <c:if test="${not empty sessionScope.sessioUsuari}">
                          <li class="nav-item"><a class="nav-link" href="#">Propostes</a></li>
                          <c:if test="${sessionScope.sessioUsuari.rol == 'ADMIN'}">
                               <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/mostrarUsuaris">Gestió Usuaris</a></li>
                          </c:if>
                     </c:if>
                </ul>
                <div class="d-flex align-items-center ms-lg-auto">
                    <!-- Sessió (copiat de dashboard_usuari.jsp) -->
                    <c:choose>
                        <c:when test="${empty sessionScope.sessioUsuari}">
                            <a href="${pageContext.request.contextPath}/login" class="btn btn-tot btn-sm my-2 my-lg-0">
                                Inicia sessió <i class="bi bi-person-circle"></i>
                            </a>
                        </c:when>
                        <c:otherwise>
                            <div class="dropdown">
                                <button class="btn btn-tot btn-sm dropdown-toggle active" type="button" id="dropdownUsuari" data-bs-toggle="dropdown" aria-expanded="false">
                                    <i class="bi bi-person-fill"></i> <c:out value="${sessionScope.sessioUsuari.nomComplet}"/> 
                                </button>
                                <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="dropdownUsuari">
                                    <c:choose>
                                        <c:when test="${sessionScope.sessioUsuari.rol == 'USUARI'}">
                                            <li><a class="dropdown-item active" href="${pageContext.request.contextPath}/dashboard_usuari">El Meu Panell</a></li>
                                        </c:when>
                                        <c:when test="${sessionScope.sessioUsuari.rol == 'BIBLIOTECARI'}">
                                            <li><a class="dropdown-item" href="${pageContext.request.contextPath}/dashboard_bibliotecari">Panell Bibliotecari</a></li>
                                        </c:when>
                                        <c:when test="${sessionScope.sessioUsuari.rol == 'ADMIN'}">
                                            <li><a class="dropdown-item" href="${pageContext.request.contextPath}/dashboard_admin">Panell Admin</a></li>
                                        </c:when>
                                    </c:choose>
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
    <!-- ===== FI CAPÇALERA INCRUSTADA ===== -->

    <!-- Secció Principal de Contingut -->
    <section class="py-5 flex-grow-1">
        <div class="container px-4 px-lg-5 mt-5">
            <div class="row justify-content-center">
                <div class="col-lg-6">
                    <div class="card shadow-sm form-page-container">
                        <div class="card-body p-4 p-md-5">
                            <h2 class="text-center text-tot-bold mb-4">Editar Perfil</h2>
                            
                            <!-- Missatges d'error o èxit (rebuts des de RedirectAttributes) -->
                            <c:if test="${not empty error}">
                                <div class="alert alert-danger" role="alert">
                                    <c:out value="${error}" />
                                </div>
                            </c:if>
                            <c:if test="${not empty success}">
                                <div class="alert alert-success" role="alert">
                                    <c:out value="${success}" />
                                </div>
                            </c:if>

                            <!-- 
                                El formulari envia per POST a la URL /perfil
                                Els camps s'omplen amb l'objecte ${usuari} rebut del controlador
                            -->
                            <form action="${pageContext.request.contextPath}/perfil" method="POST">
                                
                                <div class="mb-3">
                                    <label for="nom" class="form-label fw-bold">Nom</label>
                                    <input type="text" class="form-control" id="nom" name="nom" value="<c:out value='${usuari.nom}'/>" required>
                                </div>
                                
                                <div class="mb-3">
                                    <label for="cognoms" class="form-label fw-bold">Cognoms</label>
                                    <input type="text" class="form-control" id="cognoms" name="cognoms" value="<c:out value='${usuari.cognoms}'/>" required>
                                </div>
                                
                                <div class="mb-3">
                                    <label for="email" class="form-label fw-bold">Correu Electrònic</label>
                                    <input type="email" class="form-control" id="email" name="email" value="<c:out value='${usuari.email}'/>" required>
                                </div>
                                
                                <div class="mb-3">
                                    <label for="telefon" class="form-label fw-bold">Telèfon</label>
                                    <input type="tel" class="form-control" id="telefon" name="telefon" value="<c:out value='${usuari.telefon}'/>">
                                </div>
                                
                                <hr class="my-4">
                                
                                <p class="text-muted small">
                                    Per canviar la contrasenya, fes-ho des de la secció "Canviar Contrasenya".
                                </p>
                                
                                <div class="d-grid gap-2 d-md-flex justify-content-md-between">
                                    <a href="${pageContext.request.contextPath}/dashboard_usuari" class="btn btn-outline-secondary">Cancel·lar</a>
                                    <button type="submit" class="btn btn-tot">Desar Canvis</button>
                                </div>
                            </form>
                        </div>
                    </div>
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
    <!-- ===== FI PEU DE PÀGINA INCRUSTAT ===== -->

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>